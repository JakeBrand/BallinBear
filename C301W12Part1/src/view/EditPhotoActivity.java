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


// TODO EditPhotoActivity: set the imageview to have the photo
//TODO EditPhotoActivity: set the change Album to have the list of all albums plus an option to Create New Album (set the current album as the default)
//TODO EditPhotoActivity: Update comments if there are any
// TODO EditPhotoActivity: Set the PhotoDateLabel to have the current photos date



public class EditPhotoActivity extends Activity
{
    
    private Bitmap BMPphoto;
    String[] albumNames;

    Spinner albumNameSpinner;
    
  int albumArrayIndex;
    int photoIndex;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Bundle bundle = getIntent().getExtras();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editphotoview);
       //TODO: instantiate the controller from bundle
        // controller = (Controller) savedInstanceState.get("controller");
       //  albumArrayIndex = (int) savedInstanceState.get("albumArrayIndex");
        // photoIndex = (int) savedInstanceState.get("photoIndex");
        
        albumArrayIndex = -1;
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
        OnClickListener newAlbumListener = new OnClickListener(){
            @Override
            public void onClick(View v){
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
        OnClickListener PhotoDeleteListener = new OnClickListener(){

            @Override
            public void onClick(View v)
            {
               
               //TODO EditPhotoActivity: delete the Photo from the Current Album, if there are still photos in this album stay in its gallery view
                                                                // else go to AlbumListView
            }
            
        };
        PhotoDelete.setOnClickListener(PhotoDeleteListener);
        
       
        //TODO EditPhotoActivity: get the id of the current album albumNameSpinner.get, if (new album) selected go to edit album to create new album, if existing album selected
        //                                                                 add this photo to the album and go to the album view after finishing the current activity
        albumNameSpinner = (Spinner) findViewById(R.id.albumNameSpinner);
        albumNames = Controller.getAlbumNames();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(EditPhotoActivity.this, android.R.layout.simple_spinner_item, albumNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        albumNameSpinner.setAdapter(spinnerAdapter);
        
    }

    private void inflatePopup(){

         AlertDialog.Builder alert = new AlertDialog.Builder(this);

         alert.setTitle("Title");
         alert.setMessage("Message");  

         // Set an EditText view to get user input
         final EditText input = new EditText(this);
         alert.setView(input);

         alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int whichButton) {
           Editable value = input.getText();
           Log.e("value", value.toString());

             Log.e("InflatePopup","Button Clicked");
             String newAlbumName = value.toString();
             albumNames[albumNames.length-1] = newAlbumName;
             ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(EditPhotoActivity.this, android.R.layout.simple_spinner_item, albumNames);
             spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
             albumNameSpinner.setAdapter(spinnerAdapter);
             albumNameSpinner.setSelection(albumNames.length -1, false);
           }
         });

         alert.setNegativeButton("Cancel", new
 DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int whichButton) {
             // Canceled.
           }
         });

         alert.show();
        
    }
    
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

    protected void acceptBogoPic()
    {
        // get intent that starts the activity
        Intent intent = getIntent();
        if (intent == null)
        {
            Log.d("acceptBogoPic", "intent is null");
            return;
        }

          //  File intentFile = getPicturePath(intent);
            Uri imageUri = getImageUri(intent);
            
            //saveBMP(intentFile, BMPphoto);

            // if clicked, get the edittext
            EditText commentET = (EditText) findViewById(R.id.commentEditText);
            String comment = commentET.getText().toString();        // get the string inside

            if(albumArrayIndex == -1)
                Controller.addAlbum(albumNameSpinner.getSelectedItem().toString(), imageUri, comment);
            else
                Controller.addPhoto(albumArrayIndex, imageUri, comment);
            //intent.putExtra("BMPphoto", BMPphoto);
            
            setResult(RESULT_OK, intent);
        
        finish();

    }
// TODO: Save the BMP or create a Photo and save the Photo?
    /*
    private void saveBMP(File intentFile, Bitmap ourBMP2)
    {

        OutputStream stream;
        try
        {
            stream = new FileOutputStream(intentFile);
            BMPphoto.compress(Bitmap.CompressFormat.JPEG, 75, stream);
            stream.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
*/
    private File getPicturePath(Intent intent)
    {

        Uri uri = (Uri) intent.getExtras().get(MediaStore.EXTRA_OUTPUT);
        File file = new File(uri.getPath());

        return file;
    }
    private Uri getImageUri(Intent intent)
    {

        Uri uri = (Uri) intent.getExtras().get(MediaStore.EXTRA_OUTPUT);
        return uri;
    }



    protected void setBogoPic()
    {

        ImageButton button = (ImageButton) findViewById(R.id.generated_pic);
         BMPphoto = BogoPicGen.generateBitmap(350,350);
        button.setImageBitmap(BMPphoto);
    }

    public void onPause(){
        super.onPause();
        Controller.saveObject(this);
        
    }
    
    
    
}
