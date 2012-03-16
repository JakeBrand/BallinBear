/*
* Code pertaining to the random generation of a Bitmap (BogoPic) are based on the below cited source.
 */

/*
* Copyright 2012 Bryan Liles <iam@smartic.us> and Abram Hindle <abram.hindle@softwareprocess.es> . All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of
conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list
of conditions and the following disclaimer in the documentation and/or other materials
provided with the distribution.

THIS SOFTWARE IS PROVIDED BY Bryan Liles <iam@smartic.us> and Abram Hindle <abram.hindle@softwareprocess.es> ''AS IS'' AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the
authors and should not be interpreted as representing official policies, either expressed
or implied, of Bryan Liles <iam@smartic.us> and Abram Hindle <abram.hindle@softwareprocess.es>.

*
*/

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
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

public class EditPhotoActivity extends Activity implements OnClickListener
{

    private Bitmap           BMPphoto;
    String[]                 albumNames;
    Spinner                  albumNameSpinner;
    boolean                  newAlbumCreated;
    private static final int INDEX_NOT_IN_BUNDLE      = -1;
    private static final int NULL_BMP                 = 0;
    private static final int NEW_ALBUM_SELECTED       = 1;
    private static final int NEW_ALBUM_NOT_SELECTED   = 2;
    private static final int NO_ALBUM_SELECTED        = 3;
    private static final int USE_SELECTED_ALBUM       = 4;
    private static final int UPDATING_PHOTO           = 5;
    private static final int MOVING_PHOTO_TO_EXISTING = 6;
    private static final int MOVING_PHOTO_TO_NEW      = 7;

    /**
     * Set view to editphotoview.xml, set global variables, call initIndicies,
     * prepare all buttons, prepare spinner
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.editphotoview);




    }
    
    public void onResume(){
        super.onResume();
        setContentView(R.layout.editphotoview);

        newAlbumCreated = false;
        albumNameSpinner = (Spinner) findViewById(R.id.albumNameSpinner);
        albumNames = Controller.getAlbumNames();
        ArrayAdapter<String> spinnerAdapter;


        ImageButton imageButton = (ImageButton) findViewById(R.id.generated_pic);
        imageButton.setOnClickListener(this);

        Button newAlbumButton = (Button) findViewById(R.id.newAlbumButton);
        newAlbumButton.setOnClickListener(this);

        Button acceptButton = (Button) findViewById(R.id.PhotoViewSave);
        acceptButton.setOnClickListener(this);

        Button backButton = (Button) findViewById(R.id.PhotoViewCancel);
        backButton.setOnClickListener(this);

        Button PhotoDelete = (Button) findViewById(R.id.PhotoDelete);
        PhotoDelete.setOnClickListener(this);
        
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
        if(Controller.getCurrentPhotoIndex() != INDEX_NOT_IN_BUNDLE)
        setProvidedPic();
      //  albumNameSpinner.setSelection(Controller.getCurrentAlbumIndex()); //TODO
    }



    /**
     * perform action depending on button clicked
     *
     * @param view V
     */
    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.generated_pic:
                setBogoPic();
                break;

            case R.id.newAlbumButton:
                inflateNewAlbumDialog();
                break;

            case R.id.PhotoViewSave:
                acceptBogoPic();
                break;

            case R.id.PhotoViewCancel:
                cancelBogoPic();
                break;

            case R.id.PhotoDelete:
                deletePhoto();
                break;
        }
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
     * New Album has been clicked. Bring new album dialog to front.
     */
    private void inflateNewAlbumDialog()

    {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("New Album");
        alert.setMessage("Please enter the name a the new Album");


        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        // Accept button clicked. Add new option to spinner (dropdown),
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
            //int requestCode = 0;
            //Log.e("About to go to comparePhoto", "request code: " + requestCode);
        });

        alert.show();

    }

    /**
     * Accept button clicked. If state is valid save a new photo/album if state
     * is invalid, prompt the user to validate it.
     */
    private void acceptBogoPic()
    {

        Intent intent = getIntent();
        Uri imageUri = getImageUri(intent);

        EditText commentET = (EditText) findViewById(R.id.commentEditText);
        String comment = commentET.getText().toString();

        int validityState = verifyAccept();
        int offset = 0;

        switch (validityState)
        {
            // Updating photo in same album
            case UPDATING_PHOTO:
                Controller.updatePhoto(Controller.getCurrentAlbumIndex(), Controller.getCurrentPhotoIndex(), comment);
                finishIntent(intent, imageUri);
                break;

            // Transferring photo to new album
                
            case MOVING_PHOTO_TO_EXISTING:
                Controller.deletePhoto(Controller.getCurrentAlbumIndex(), Controller.getCurrentPhotoIndex());
                Log.e("Current Album index", "" + Controller.getCurrentAlbumIndex());
                Controller.addPhoto(Controller.checkAlbumNames(albumNameSpinner.getSelectedItem().toString()),  imageUri, comment);   
              
                
 
                finishIntent(intent, imageUri);
                break;

            case MOVING_PHOTO_TO_NEW:
                Controller.deletePhoto(Controller.getCurrentAlbumIndex(), Controller.getCurrentPhotoIndex());
                Controller.addAlbum(albumNameSpinner.getSelectedItem()
                        .toString(), imageUri, comment);
                finishIntent(intent, imageUri);
                break;
            // No picture has been taken. Must take a picture.
            case NULL_BMP:
                Toast toast = Toast.makeText(getApplicationContext(),
                        "You must take a picture", Toast.LENGTH_SHORT);
                toast.show();
                break;

            // Use the newly created album name (spinner[0]) and photo taken.
            case NEW_ALBUM_SELECTED:
                Controller.addAlbum(albumNameSpinner.getSelectedItem()
                        .toString(), imageUri, comment);
                finishIntent(intent, imageUri);
                break;

            // Use selected album which isn't the newly made album, ignore the
            // spinner[0] (new album)
            case NEW_ALBUM_NOT_SELECTED:
                Controller.addPhoto(albumNameSpinner.getSelectedItemPosition() - 1, imageUri, comment);
                finishIntent(intent, imageUri);
                break;

            // There are no albums. Must create one before progressing.
            case NO_ALBUM_SELECTED:
                inflateNewAlbumDialog();
                break;

            // Use selected album.
            case USE_SELECTED_ALBUM:
                Controller.addPhoto(albumNameSpinner.getSelectedItemPosition(), imageUri, comment);
                finishIntent(intent, imageUri);
                break;
            default:
                Log.e("validityCode", "Invalid");
                break;
        }

    }

    /**
     * Return a verification code based on state of completion
     *
     * @return validityState
     */
    private int verifyAccept()
    {

        if (Controller.getCurrentPhotoIndex() != INDEX_NOT_IN_BUNDLE)
        {
            if (!newAlbumCreated)
            {
                if (albumNameSpinner.getSelectedItemPosition() == Controller.getCurrentAlbumIndex())
                {
                    return UPDATING_PHOTO;
                }
                if (albumNameSpinner.getSelectedItemPosition() != Controller.getCurrentAlbumIndex())
                {
                    return MOVING_PHOTO_TO_EXISTING;
                }
            }
            if (newAlbumCreated)
            {
                if (albumNameSpinner.getSelectedItemPosition() - 1 == Controller.getCurrentAlbumIndex())
                {
                    return UPDATING_PHOTO;
                }
                if (albumNameSpinner.getSelectedItemPosition() == 0)
                {
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
     * Cancel button clicked. Save no chances and return.
     */
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

    /**
     * Remove the photo from the selected album
     */
    public void deletePhoto()
    {

        if (Controller.getCurrentAlbumIndex() == -1)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "This picture is not in an album", Toast.LENGTH_SHORT);
            toast.show();
        } else if (Controller.getCurrentAlbum().getPhotos().size() == 1)
        {
            Controller.deleteAlbum(Controller.getCurrentAlbumIndex());
           
            finish();
        } else
        {
            Controller.deletePhoto(Controller.getCurrentAlbumIndex(), Controller.getCurrentPhotoIndex());
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    /**
     * Return the Uri to the image with the given intent
     *
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
     * Set the image to the Bitmap from the Photo calling EditPhotoActivity
     */
    protected void setProvidedPic()
    {

        ImageButton imageButton = (ImageButton) findViewById(R.id.generated_pic);
        TextView photoDate = (TextView) findViewById(R.id.photoDate);
        Photo providedPhoto = Controller.getCurrentPhoto();
        Uri uri = providedPhoto.getPicture();
        EditText commentET = (EditText) findViewById(R.id.commentEditText);
        String comment = providedPhoto.getComment();
        commentET.setText(comment);
        try
        {

            Date date = providedPhoto.getpTimeStamp();
            String formatedDate = DateFormat.getDateInstance().format(date)
                    + " -- "
                    + DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
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

    /**
     * Put the updated bundle back into the intent Extras and finish the intent
     *
     * @param bundle
     * @param intent
     * @param imageUri
     */
    private void finishIntent(Intent intent, Uri imageUri)
    {

        saveBMP(intent, imageUri);
        Controller.saveObject(this);

        setResult(RESULT_OK, intent);
        finish();
    }

}