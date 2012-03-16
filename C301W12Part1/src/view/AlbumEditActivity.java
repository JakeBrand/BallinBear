package view;


import java.io.File;
import java.util.ArrayList;

import model.Album;
import model.Photo;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;


import java.io.File;

import control.Controller;

import ca.ualberta.ca.c301.R;




import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;

//#################################### This activity uses the album_edit layout and is used to edit albums (duh!)
public class AlbumEditActivity extends Activity {
	/**
	 *  Album Editting Activity (Displays the album name and
	 *  allows you to change it, delete the album, or do nothing)
	 */
    
    EditText albName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	/**
    	 * On Create
    	 * Loads the layout of the album_edit
    	 * Sets the on click listener for the 3 buttons available
    	 * deleteButton allows you to delete an album
    	 * backToAlbumButton returns you to the list of albums (main.xml)
    	 * and done button allows you to save any changes made to the album name
    	 */
      super.onCreate(savedInstanceState);
      setContentView(R.layout.album_edit);
      
      albName = (EditText) findViewById(R.id.albumNameEditText);

      
          albName.setText(Controller.getCurrentAlbum().getAlbumName());
      
      
      Button deleteButton = (Button) findViewById(R.id.deleteButton);
      OnClickListener deleteListener = new OnClickListener(){

          @Override
          public void onClick(View v){

            // TODO AlbumEditActivity: delete album; show a warning of all the photos that will be deleted
              // if only album go to welcome, else go to albumlist
        	  Controller.deleteAlbum(Controller.getCurrentAlbumIndex());
        	  finish();
        }
          
      };
      deleteButton.setOnClickListener(deleteListener);
       
      
      Button backToAlbumButton = (Button) findViewById(R.id.backToAlbumButton);
      OnClickListener backToAlbumButtonListener = new OnClickListener(){

          @Override
          public void onClick(View v){

           finish();
        }
          
      };
      backToAlbumButton.setOnClickListener(backToAlbumButtonListener);
      
      
      
      Button doneButton = (Button) findViewById(R.id.doneButton);
      OnClickListener doneListener = new OnClickListener(){
          @Override
          public void onClick(View v){
              
              // TODO AlbumEditActivity: change album name if edit text has changed
              Controller.setCurrentAlbumName(albName.getText().toString()); 
              finish();
        }
          
      };
      doneButton.setOnClickListener(doneListener);
      
    }
    
    public void onPause(){
    	/**
    	 * on Pause
    	 * On pause saves current object using the controller
    	 */
        super.onPause();
        Controller.saveObject(this);
    }
}
