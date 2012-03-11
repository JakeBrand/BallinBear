package view;

import java.io.File;

import control.GenerateBogoPicActivity;

import ca.ualberta.ca.c301.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class WelcomeActivity extends Activity {
    private Uri imageUri;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
      setContentView(R.layout.welcome_view);


      
      Button newPhoto = (Button) findViewById(R.id.takeNewPhotoButton);
      newPhoto.setBackgroundColor(Color.GREEN);
      
      OnClickListener newPhotoListener = new OnClickListener(){

          @Override
          public void onClick(View v){
            takeAPhoto();   // TODO WelcomeActivity: Ya, I dont know how to take random pics
        }
          
      };
      newPhoto.setOnClickListener(newPhotoListener);
      
      
      
      
      Button viewAlbums = (Button) findViewById(R.id.viewAlbumsButton);
      viewAlbums.setBackgroundColor(Color.CYAN);
      
      OnClickListener viewAlbumsListener = new OnClickListener(){

          @Override
          public void onClick(View v){
              
            // TODO WelcomeActivity: view Albums

        }
          
      };
      viewAlbums.setOnClickListener(viewAlbumsListener);
      
      
             
    }
    
    
    
  private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST = 100;
  protected void takeAPhoto()
  {
      
      //TODO: Do we want to use the camera? If not we will spoof the camera.
      /*
      // Create intent instance to be used to take photo
      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      
      // Create Folder in SD Card                                              // folder name = tmp
      String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp";
      
      
      // give file the folder (wtf)?
      File folderF = new File(folder);
      
      if(!folderF.exists()){ // if folder doesnt exist
          folderF.mkdir();                    // make it bloody exist
      }
      
      // make file path                                       // name                         // type
      String imageFilePath = folder + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg";
      
      
      File imageFile = new File(imageFilePath);
      
      // Create URI
      imageUri = Uri.fromFile(imageFile);
      
      
      intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
      
      // start intent to take picture
      startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST);    
      
      */
      Intent takePhotoIntent = new Intent(this, GenerateBogoPicActivity.class);
      // Other stuff for storage
      startActivityForResult(takePhotoIntent, 0);
  }
  
  
  @Override                 // if requestCodeis 100 we are good, result code is to see if a picture was actually taken
  protected void onActivityResult(int requestCode, int resultCode,Intent intent ){
      if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST){
          if(resultCode == RESULT_OK){
              Intent intent2 = new Intent(getParent(), EditPhotoActivity.class);
              // set button to look like photo just taken
              intent2.putExtra("imagePath",imageUri.getPath()); // How do you pass around albums
              startActivity(intent2);
          }
      }
  }
    
    
    
    
    
}
