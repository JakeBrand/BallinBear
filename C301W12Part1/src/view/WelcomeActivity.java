package view;

import java.io.File;

import control.Controller;


import ca.ualberta.ca.c301.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class WelcomeActivity extends Activity {
    private Uri imageUri;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST = 100;
    private static final int TAKE_PICTURE_ACTIVITY_REQUEST = 200;
    
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        
        Controller.loadObject(this);
        
      setContentView(R.layout.welcome_view);
      
      
      Button newPhoto = (Button) findViewById(R.id.takeNewPhotoButton);
      newPhoto.setBackgroundColor(Color.GREEN);
      
      OnClickListener newPhotoListener = new OnClickListener(){

          @Override
          public void onClick(View v){
            takeAPhoto();
        }
          
      };
      newPhoto.setOnClickListener(newPhotoListener);
      
      Button viewAlbums = (Button) findViewById(R.id.viewAlbumsButton);
      viewAlbums.setBackgroundColor(Color.CYAN);
      
      OnClickListener viewAlbumsListener = new OnClickListener(){

          @Override
          public void onClick(View v){
              
            // TODO WelcomeActivity: view Albums
        	  Intent myIntent = new Intent(v.getContext(), AlbumListActivity.class);
              startActivityForResult(myIntent, 0);
              
              
        }
          
      };
      viewAlbums.setOnClickListener(viewAlbumsListener);
             
    }
    
    
    
    public void onPause(){
        super.onPause();
        Controller.saveObject(this);
        
    }
    

  protected void takeAPhoto()
  {
      
      //TODO: Do we want to use the camera? If not we will spoof the camera.
      
      // Create intent instance to be used to take photo
     // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      
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
      
      
      //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
      
      // start intent to take picture
     // startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST);    
      
      
      Intent takePhotoIntent = new Intent(this, EditPhotoActivity.class);
      //takePhotoIntent.putExtra  ("Controller", controller);
      takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
      startActivityForResult(takePhotoIntent, TAKE_PICTURE_ACTIVITY_REQUEST);
  }
  
  
  @Override                 // if requestCodeis 100 we are good, result code is to see if a picture was actually taken
  protected void onActivityResult(int requestCode, int resultCode, Intent intent ){
      Log.e("onActivityResult", "Got here");
      if(requestCode == TAKE_PICTURE_ACTIVITY_REQUEST){
          Log.e("onActivityResult", "requestCode = " + requestCode);
          if(resultCode == RESULT_OK){
              Log.e("onActivityResult", "resultCode = " + resultCode);
              Bundle bundle = intent.getExtras();
              int albumArrayIndex = bundle.getInt("albumArrayIndex");
              Log.e("albumArrayIndex", "" + albumArrayIndex);
              
              Intent galleryActivityIntent = new Intent(this, GalleryActivity.class); // getParent(),
              // set button to look like photo just taken
             // intent2.putExtra("imagePath",imageUri.getPath()); // How do you pass around albums
              
              
              galleryActivityIntent.putExtras(bundle);
              startActivity(galleryActivityIntent);
          }
      }
      
//      if(requestCode == TAKE_PICTURE_ACTIVITY_REQUEST){
//       if(resultCode == RESULT_OK){
//           Log.d("TEST", "resultCode == RESULT_OK");
//           if (intent != null)
//           {
//               Bitmap BMPphoto = (Bitmap) intent
//                       .getParcelableExtra("BMPphoto");
//               Log.d("intent is", intent.toString());
//               if (BMPphoto != null)
//               {
//                   Log.d("BMPphoto", "BMPphoto is NOT null");
//               } else{
//                   Log.d("BMPhoto", "BMPphoto is null");
//               }
//           } else {
//               Log.d("intent is", "null");
//           }  
//       }
//      }
  }
    
    
    
    
    
}
