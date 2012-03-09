package view;

import android.app.Activity;
import android.os.Bundle;
import ca.ualberta.ca.c301.R;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;




//#################################### This activity uses the edit_photo layout and is used to edit aspects of the photos
public class EditPhotoActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
      setContentView(R.layout.editphotoview);
      // TODO EditPhotoActivity: set the imageview to have the photo
      //TODO EditPhotoActivity: set the change Album to have the list of all albums plus an option to Create New Album (set the current album as the default)
      //TODO EditPhotoActivity: Update comments if there are any
      // TODO EditPhotoActivity: Set the PhotoDateLabel to have the current photos date
      
      
      
      
      // Get a hold of button 
      Button PhotoViewSave = (Button) findViewById(R.id.PhotoViewSave);
      

      OnClickListener PhotoViewSaveListener = new OnClickListener(){

          @Override
          public void onClick(View v)
          {
              // if clicked, get the edittext
             EditText commentET = (EditText) findViewById(R.id.commentEditText);
             String comment = commentET.getText().toString();        // get the string inside
             // TODO EditPhotoActivity: add comment to new Photo object
             Spinner albumNameSpinner = (Spinner) findViewById(R.id.albumNameSpinner);
             //TODO EditPhotoActivity: get the id of the current album albumNameSpinner.get, if (new album) selected go to edit album to create new album, if existing album selected
             //                                                                 add this photo to the album and go to the album view after finishing the current activity
          }
          
      };
      PhotoViewSave.setOnClickListener(PhotoViewSaveListener);
      
      
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
      
        
      
      
      Button PhotoViewCancel = (Button) findViewById(R.id.PhotoDelete);
      

      OnClickListener PhotoViewCancelListener = new OnClickListener(){

          @Override
          public void onClick(View v)
          {
             
             //TODO EditPhotoActivity: go back to Album Gallery
                                                              
          }
          
      };
      PhotoViewCancel.setOnClickListener(PhotoViewCancelListener);
}
}