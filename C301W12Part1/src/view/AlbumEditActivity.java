package view;


import java.io.File;
import java.util.ArrayList;

import model.Album;
import model.Item;
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
    

    @Override
    public void onCreate(Bundle savedInstanceState) {       
      super.onCreate(savedInstanceState);
      setContentView(R.layout.album_edit);
      
      EditText albName = (EditText) findViewById(R.id.albumNameEditText);

      Bundle bundle = getIntent().getExtras();
      
      if(bundle.get("Alb") == null)
          albName.setText("Album Name");
      else
          albName.setText(Album.getAlbumName());
      
      
      Button deleteButton = (Button) findViewById(R.id.deleteButton);
      OnClickListener deleteListener = new OnClickListener(){

          @Override
          public void onClick(View v){

            // TODO AlbumEditActivity: delete album; show a warning of all the photos that will be deleted
              // if only album go to welcome, else go to albumlist
        }
          
      };
      deleteButton.setOnClickListener(deleteListener);
       
      
      Button backToAlbumButton = (Button) findViewById(R.id.backToAlbumButton);
      OnClickListener backToAlbumButtonListener = new OnClickListener(){

          @Override
          public void onClick(View v){

           // TODO AlbumEditActivity: back to album
        }
          
      };
      backToAlbumButton.setOnClickListener(backToAlbumButtonListener);
      
      
      
      Button doneButton = (Button) findViewById(R.id.doneButton);
      OnClickListener doneListener = new OnClickListener(){
          @Override
          public void onClick(View v){
              
              // TODO AlbumEditActivity: change album name if edit text has changed
        }
          
      };
      doneButton.setOnClickListener(doneListener);
      
    }
}