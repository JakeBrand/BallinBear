package view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;

import model.Photo;

import control.BogoPicGen;
import control.Controller;

import ca.ualberta.ca.c301.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

public class EditPhotoActivity extends Activity
{

    private Bitmap BMPphoto;
    String[]       albumNames;
    Spinner        albumNameSpinner;
    int            albumArrayIndex;
    int            photoIndex;
    boolean        newAlbumCreated;
    private static final int INDEX_NOT_IN_BUNDLE =     -1;
    private static final int NULL_BMP =                 0;
    private static final int NEW_ALBUM_SELECTED =       1;
    private static final int NEW_ALBUM_NOT_SELECTED =   2;
    private static final int NO_ALBUM_SELECTED =        3;
    private static final int USE_SELECTED_ALBUM =       4;
    private static final int UPDATING_PHOTO =           5;
    private static final int MOVING_PHOTO_TO_EXISTING = 6;
    private static final int MOVING_PHOTO_TO_NEW =      7;
    

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        Bundle bundle = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editphotoview);

        initIndices(bundle);

        ImageButton imageButton = (ImageButton) findViewById(R.id.generated_pic);
        OnClickListener generateListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                setBogoPic();

            }

        };
        imageButton.setOnClickListener(generateListener);

        Button newAlbumButton = (Button) findViewById(R.id.newAlbumButton);
        OnClickListener newAlbumListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                inflatePopup();
            }
        };
        newAlbumButton.setOnClickListener(newAlbumListener);

        Button acceptButton = (Button) findViewById(R.id.PhotoViewSave);
        OnClickListener caputreListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                acceptBogoPic();

            }

        };
        acceptButton.setOnClickListener(caputreListener);

        Button backButton = (Button) findViewById(R.id.PhotoViewCancel);
        OnClickListener backListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                cancelBogoPic();

            }

        };
        backButton.setOnClickListener(backListener);

        Button PhotoDelete = (Button) findViewById(R.id.PhotoDelete);
        OnClickListener PhotoDeleteListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // Delete album's photo
                if(Controller.getAlbum(albumArrayIndex).getPhotos().size() == 0){
                    //TODO: Go to list of albums
                } else{
                    Controller.deletePhoto(albumArrayIndex, photoIndex);
                    setResult(RESULT_CANCELED);
                    finish();
                }

            }

        };
        PhotoDelete.setOnClickListener(PhotoDeleteListener);

        // TODO EditPhotoActivity: get the id of the current album
        // albumNameSpinner.get, if (new album) selected go to edit album to
        // create new album, if existing album selected
        // add this photo to the album and go to the album view after finishing
        // the current activity
        
        newAlbumCreated = false;
        albumNameSpinner = (Spinner) findViewById(R.id.albumNameSpinner);
        albumNames = Controller.getAlbumNames();
        ArrayAdapter<String> spinnerAdapter;
        if (albumNames.length != 0)
        {
            spinnerAdapter = new ArrayAdapter<String>(EditPhotoActivity.this,
                    android.R.layout.simple_spinner_item, albumNames);

        } else
        {
            spinnerAdapter = new ArrayAdapter<String>(EditPhotoActivity.this,
                    android.R.layout.simple_spinner_item,
                    new String[] { "No Albums" });
        }
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        albumNameSpinner.setAdapter(spinnerAdapter);

    }
    
    /**
     * Initialize global indices
     * @param bundle
     */
    private void initIndices(Bundle bundle){

        if (bundle.containsKey("albumArrayIndex"))
        {
            albumArrayIndex = (int) bundle.getInt("albumArrayIndex");
        } else
        {
            Log.d("EditPhotoActivity:", "albumArrayIndex was not in the bundle");
            albumArrayIndex = INDEX_NOT_IN_BUNDLE;
        }
        if (bundle.containsKey("photoIndex"))
        {
            photoIndex = (int) bundle.getInt("photoIndex");
        } else
        {
            Log.d("EditPhotoActivity:", "photoIndex was not in the bundle");
            photoIndex = INDEX_NOT_IN_BUNDLE;
        }
        if (albumArrayIndex != INDEX_NOT_IN_BUNDLE && photoIndex != INDEX_NOT_IN_BUNDLE)
        {
            Log.d("EditPhotoActivity", "Setting provided pic");
            setProvidedPic();
        }
    }

    /**
     * New Album has been clicked. Bring Dialog (popup) to front.
     */
    private void inflatePopup()
    {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("New Album");
        alert.setMessage("Please enter the name a the new Album");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        // Accept New Album button clicked. Add new option to spinner
        // (dropdown),
        // set newAlbumSelected flag to true, rebuild spinner, and return
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int whichButton)
            {

                newAlbumCreated = true;
                Editable value = input.getText();
                Log.e("value", value.toString());
                Log.e("InflatePopup", "Button Clicked");
                String newAlbumName = value.toString();

                String[] temp = new String[albumNames.length + 1];
                temp[0] = newAlbumName;
                for (int i = 0; i < albumNames.length; i++)
                {
                    Log.d("AlbumNames[i] = ", "" + albumNames[i]);
                    temp[i + 1] = albumNames[i];

                }
                albumNames = temp;
                temp = null;

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                        EditPhotoActivity.this,
                        android.R.layout.simple_spinner_item, albumNames);
                spinnerAdapter
                        .setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                albumNameSpinner.setAdapter(spinnerAdapter);
                albumNameSpinner.setSelection(0, false);
            }
        });

        // Dialog (popup) Cancel button clicked. Save nothing. Return.
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int whichButton)
            {

            }
        });

        alert.show();

    }

    // Cancel button is clicked. Save no changes. Return.
    protected void cancelBogoPic()
    {

        Intent intent = getIntent();
        if (intent == null)
        {
            return;
        }
        setResult(RESULT_CANCELED);
        finish();
        return;
    }

    // Accept button is clicked. Tell controller to save new
    // photo and/or album
    private void acceptBogoPic()
    {

        Intent intent = getIntent();
        if (intent == null)
        {
            Log.d("acceptBogoPic", "intent is null");
            return;
        }
        Uri imageUri = getImageUri(intent);
        if(imageUri==null){
        }

        Bundle bundle = new Bundle();
        EditText commentET = (EditText) findViewById(R.id.commentEditText);
        String comment = commentET.getText().toString();

        int validityState = verifyAccept();

        switch (validityState)
        {
            // Updating photo in same album
            case UPDATING_PHOTO:
                Controller.updatePhoto(albumArrayIndex, photoIndex, comment);
                finishIntent(bundle, intent, imageUri);
                break;
            
            // Transferring photo to new album
            case MOVING_PHOTO_TO_EXISTING:
                Controller.deletePhoto(albumArrayIndex, photoIndex);
                Controller.addPhoto((int) albumNameSpinner.getSelectedItemId(), imageUri, comment);
                finishIntent(bundle, intent, imageUri);
                break;
                
            case MOVING_PHOTO_TO_NEW:
                Controller.deletePhoto(albumArrayIndex, photoIndex);
                Controller.addAlbum(albumNameSpinner.getSelectedItem().toString(), imageUri, comment);
                finishIntent(bundle, intent, imageUri);
                break;
            // No picture has been taken. Must take a picture.
            case NULL_BMP:
                Toast toast = Toast.makeText(getApplicationContext(),
                        "You must take a picture", Toast.LENGTH_SHORT);
                toast.show();
                break;

            // Use the newly created album name (spinner[0]) and photo taken.
            case NEW_ALBUM_SELECTED:
                bundle.putInt("albumArrayIndex", 0);
                Controller.addAlbum(albumNameSpinner.getSelectedItem()
                        .toString(), imageUri, comment);
                finishIntent(bundle, intent, imageUri);
                break;

            // Use selected album which isn't the newly made album, ignore the
            // spinner[0] (new album)
            case NEW_ALBUM_NOT_SELECTED:
                albumArrayIndex = albumNameSpinner.getSelectedItemPosition() - 1;
                bundle.putInt("albumArrayIndex", albumArrayIndex);
                Controller.addPhoto(albumArrayIndex, imageUri, comment);
                finishIntent(bundle, intent, imageUri);
                break;

            // There are no albums. Must create one before progressing.
            case NO_ALBUM_SELECTED:
                inflatePopup();
                break;

            // Use selected album.
            case USE_SELECTED_ALBUM:
                albumArrayIndex = albumNameSpinner.getSelectedItemPosition();
                bundle.putInt("albumArrayIndex", albumArrayIndex);
                Controller.addPhoto(albumArrayIndex, imageUri, comment);
                finishIntent(bundle, intent, imageUri);
                break;
            default:
                Log.e("validityCode", "Invalid");
                break;
        }

    }

    /**
     * Return a verification code based on state of completion
     * @return validityState
     */
    private int verifyAccept()
    {

        if(photoIndex!= INDEX_NOT_IN_BUNDLE){
            if(!newAlbumCreated){
                if(albumNameSpinner.getSelectedItemPosition() == albumArrayIndex){
                    return UPDATING_PHOTO;
                }
                if(albumNameSpinner.getSelectedItemPosition() != albumArrayIndex){
                    return MOVING_PHOTO_TO_EXISTING;
                }
            }
            if(newAlbumCreated){
                if(albumNameSpinner.getSelectedItemPosition()-1 == albumArrayIndex){
                    return UPDATING_PHOTO;
                }
                if(albumNameSpinner.getSelectedItemId() == 0){
                    return MOVING_PHOTO_TO_NEW;
                }
                return MOVING_PHOTO_TO_EXISTING;
            }
        }
        
        if (BMPphoto == null)
        {
            return NULL_BMP;
        }

        if (newAlbumCreated && albumNameSpinner.getSelectedItemPosition() == 0)
        {
            return NEW_ALBUM_SELECTED;
        }

        else if (newAlbumCreated)
        {
            return NEW_ALBUM_NOT_SELECTED;

        }
        
        else
        {
            if (Controller.getAlbumNames().length == 0)
            {
                return NO_ALBUM_SELECTED;
            }
            return USE_SELECTED_ALBUM;
        }
    }

    /**
     * Put the updated bundle back into the intent Extras and finish the intent
     * 
     * @param bundle
     * @param intent
     * @param imageUri
     */
    private void finishIntent(Bundle bundle, Intent intent, Uri imageUri)
    {

        intent.putExtras(bundle);
        saveBMP(intent, imageUri);

        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Save the BMP or create a Photo and save the Photo?
     * 
     * @param intent
     * @param imageUri
     */
    private void saveBMP(Intent intent, Uri imageUri)
    {

        OutputStream stream;
        try
        {
            if (imageUri == null)
            {
                Log.d("INTENT IS NOW", intent.toString());
                Log.d("IMAGE URI is null", "In SaveBMP");
            }
            File intentFile = new File(imageUri.getPath());
            stream = new FileOutputStream(intentFile);
            BMPphoto.compress(Bitmap.CompressFormat.JPEG, 75, stream);
            stream.close();
        } catch (FileNotFoundException e)
        {
            Log.d("FILE NOT FOUND", "WHILE SAVING PHOTO");
            e.printStackTrace();
        } catch (IOException e)
        {
            Log.d("IOException", "Couldn't saveBMP");
            e.printStackTrace();
        }

    }

    /**
     * Return the Uri to the image with the given intent
     * @param intent
     */
    private Uri getImageUri(Intent intent)
    {

        Log.d("Intent in getImageUri is ", intent.toString());
        Uri imageUri = (Uri) intent.getExtras().get(MediaStore.EXTRA_OUTPUT);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        if (imageUri == null)
        {
            Log.d("URI null from beginning in ", "getImageUri");
        } else
            Log.d("ImageUri NOT null in getImageUri = ", imageUri.toString());

        return imageUri;
    }

    /**
     * Set the imageButton to the generated picture
     */
    protected void setBogoPic()
    {

        ImageButton button = (ImageButton) findViewById(R.id.generated_pic);
        BMPphoto = BogoPicGen.generateBitmap(350, 350);
        button.setImageBitmap(BMPphoto);

    }

    /**
     * Set the image to the Bitmap from the Photo calling EditPhotoActivity
     */
    protected void setProvidedPic()
    {

        ImageButton imageButton = (ImageButton) findViewById(R.id.generated_pic);
        TextView photoDate = (TextView) findViewById(R.id.photoDateText);
        Photo providedPhoto = Controller.getPhoto(albumArrayIndex, photoIndex);
        Uri uri = providedPhoto.getPicture();
        EditText commentET = (EditText) findViewById(R.id.commentEditText);
        String comment = providedPhoto.getComment();
        commentET.setText(comment);
        try
        {
            
            Date date = providedPhoto.getpTimeStamp();
            String formatedDate = DateFormat.getDateInstance().format(date) + " -- " + DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
            photoDate.setText(formatedDate);
            
            String imageFilePath = uri.getPath();
            FileInputStream inputStream;
            inputStream = new FileInputStream(imageFilePath);
            BufferedInputStream bufferedInput = new BufferedInputStream(
                    inputStream);
            BMPphoto = BitmapFactory.decodeStream(bufferedInput);
            imageButton.setImageBitmap(BMPphoto);
        } catch (FileNotFoundException e)
        {
            Log.d("FileNotFound", "EditPhotoActivity");
        }

    }

    // //TODO: find out how to save!
    // public void onPause()
    // {
    //
    // super.onPause();
    // Controller.saveObject(this);
    //
    // }

}
