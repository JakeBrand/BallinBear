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
      Button deleteButton = (Button) findViewById(R.id.deleteButton);
      Button doneButton = (Button) findViewById(R.id.doneButton);
      Bundle bundle = getIntent().getExtras();
      
      if(bundle.get("Alb") == null)
          albName.setText("Album Name");
      else
          albName.setText(Album.getAlbumName());
      
      OnClickListener deleteListener = new OnClickListener(){

          @Override
          public void onClick(View v){

            // TODO delete album
        }
          
      };
      deleteButton.setOnClickListener(deleteListener);
       
      
      
      OnClickListener doneListener = new OnClickListener(){
          @Override
          public void onClick(View v){
              
              // TODO back to album
        }
          
      };
      doneButton.setOnClickListener(doneListener);
      
    }
}