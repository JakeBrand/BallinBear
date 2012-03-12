package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import control.BogoPicGen;
import control.Controller;

import ca.ualberta.ca.c301.R;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import android.widget.Spinner;

// TODO EditPhotoActivity: Set the PhotoDateLabel to have the current photos date

public class EditPhotoActivity extends Activity
{

    private Bitmap BMPphoto;
    String[]       albumNames;
    Spinner        albumNameSpinner;
    int            albumArrayIndex;
    int            photoIndex;
    boolean        newAlbumSelected;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        Bundle bundle = getIntent().getExtras();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.editphotoview);
        Log.e("ON CREATE IN EDIT PHOTO", "");

        if (bundle.containsKey("albumArrayIndex"))
        {
            albumArrayIndex = (int) bundle.getInt("albumArrayIndex");
        } else
        {
            Log.d("EditPhotoActivity:", "albumArrayIndex was not in the bundle");
            albumArrayIndex = -1;
        }
        if (bundle.containsKey("photoIndex"))
        {
            photoIndex = (int) bundle.getInt("photoIndex");
        } else
        {
            Log.d("EditPhotoActivity:", "photoIndex was not in the bundle");
            photoIndex = -1;
        }

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

                acceptBogoPic(newAlbumSelected);

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

        // TODO: finish implementing!
        // Delete button pushed. Remove photo from current album.
        // If still Photos in Album return to Album.
        // If Album is empty return to AlbumListView
        Button PhotoDelete = (Button) findViewById(R.id.PhotoDelete);
        OnClickListener PhotoDeleteListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                //Delete album photo
                Controller.deletePhoto(albumArrayIndex, photoIndex);
                
            }

        };
        PhotoDelete.setOnClickListener(PhotoDeleteListener);

        // TODO EditPhotoActivity: get the id of the current album
        // albumNameSpinner.get, if (new album) selected go to edit album to
        // create new album, if existing album selected
        // add this photo to the album and go to the album view after finishing
        // the current activity
        newAlbumSelected = false;
        albumNameSpinner = (Spinner) findViewById(R.id.albumNameSpinner);
        albumNames = Controller.getAlbumNames();
        ArrayAdapter<String> spinnerAdapter;
        if (albumNames.length != 0)
        {
            spinnerAdapter = new ArrayAdapter<String>(
                    EditPhotoActivity.this,
                    android.R.layout.simple_spinner_item, albumNames);

        } else {
            spinnerAdapter = new ArrayAdapter<String>(
                    EditPhotoActivity.this,
                    android.R.layout.simple_spinner_item, new String[]{"No Albums"});
        }
            spinnerAdapter
                    .setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            albumNameSpinner.setAdapter(spinnerAdapter);
        
    }

    // New Album has been clicked. Bring Dialog (popup) to front.
    private void inflatePopup()
    {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("New Album");
        alert.setMessage("Enter the name of the new Album");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

            // Accept New Album button clicked. Add new option to spinner (dropdown),
            // set newAlbumSelected flag to true, rebuild spinner, and return
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int whichButton)
            {
                newAlbumSelected =  true;
                Editable value = input.getText();
                Log.e("value", value.toString());
                Log.e("InflatePopup", "Button Clicked");
                String newAlbumName = value.toString();

                String[] temp = new String[albumNames.length + 1];
                temp[0] = newAlbumName;
                for (int i = 0; i < albumNames.length; i++)
                {
                    Log.d("AlbumNames[i] = ", ""+albumNames[i]);
                    temp[i+1] = albumNames[i];

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
    protected void acceptBogoPic(boolean newAlbumSelected)
    {

        Intent intent = getIntent();
        if (intent == null)
        {
            Log.d("acceptBogoPic", "intent is null");
            return;
        }

        Uri imageUri = getImageUri(intent);
        Bundle b = new Bundle();
        EditText commentET = (EditText) findViewById(R.id.commentEditText);
        String comment = commentET.getText().toString(); // get the string
                                                         // inside
        // If newAlbum has been clicked and is chosen in the spinner (drop down)
        // create a new album with picture taken
        if (newAlbumSelected && albumNameSpinner.getSelectedItemPosition()==0)
        {

            b.putInt("albumArrayIndex", 0);
            Controller.addAlbum(albumNameSpinner.getSelectedItem().toString(),
                    imageUri, comment);
            Log.d("number of alums after adding is:" , ""+Controller.getAlbumNames().length);
        } 
        // If no need to create a new album,but must revert to previous spinner
        else if(newAlbumSelected)
        {
            albumArrayIndex = albumNameSpinner.getSelectedItemPosition()-1;
            b.putInt("albumArrayIndex", albumArrayIndex);
            Controller.addPhoto(albumArrayIndex, imageUri, comment);

        } 
        // If no need to create a new album, add the photo to the selected
        // spinner position (drop down)
        else{
            albumArrayIndex = albumNameSpinner.getSelectedItemPosition();
            b.putInt("albumArrayIndex", albumArrayIndex);
            Controller.addPhoto(albumArrayIndex, imageUri, comment);
        }
        // Put the updated bundle back into the intent Extras and finish the intent
        intent.putExtras(b);
        setResult(RESULT_OK, intent);
        finish();

    }

    // TODO: Save the BMP or create a Photo and save the Photo?
    /*
     * private void saveBMP(File intentFile, Bitmap ourBMP2) {
     * 
     * OutputStream stream; try { stream = new FileOutputStream(intentFile);
     * BMPphoto.compress(Bitmap.CompressFormat.JPEG, 75, stream);
     * stream.close(); } catch (FileNotFoundException e) { e.printStackTrace();
     * } catch (IOException e) { e.printStackTrace(); }
     * 
     * }
     * 
     * private File getPicturePath(Intent intent) {
     * 
     * Uri uri = (Uri) intent.getExtras().get(MediaStore.EXTRA_OUTPUT); File
     * file = new File(uri.getPath());
     * 
     * return file; }
     */
    
    // Return the Uri to the image with the given intent
    private Uri getImageUri(Intent intent)
    {

        Uri uri = (Uri) intent.getExtras().get(MediaStore.EXTRA_OUTPUT);
        return uri;
    }
    // Set the imageButton to the generated picture
    protected void setBogoPic()
    {

        ImageButton button = (ImageButton) findViewById(R.id.generated_pic);
        BMPphoto = BogoPicGen.generateBitmap(350, 350);
        button.setImageBitmap(BMPphoto);
    }

//    //TODO: find out how to save!
//    public void onPause()
//    {
//
//        super.onPause();
//        Controller.saveObject(this);
//
//    }

}
