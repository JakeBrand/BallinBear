package view;



import java.io.File;

import ca.ualberta.ca.c301.R;

import model.Album;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import control.Controller;

//#################################### This activity uses the albumthumbnail layout and is used to display the photos
public class GalleryActivity extends Activity implements OnClickListener{

    Album alb;
    ImageView imageView;
    String currentAlbumName;
    boolean comparing_photo;
    
        /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.galleryview);
        alb = Controller.getCurrentAlbum();

        Button newPhotoButton = (Button) findViewById(R.id.NewPhotoButton);
        newPhotoButton.setOnClickListener(this);
        
        Button comparePhotosButton = (Button) findViewById(R.id.ComparePhotosButton);
        comparePhotosButton.setOnClickListener(this);
        Gallery ga = (Gallery) findViewById(R.id.albumGallery);       
        ga.setAdapter(new ImageAdapter(this));
       


    }
    
    private void comparePhotos(){
        Context context = getApplicationContext();
        CharSequence text = "Please select two photos you wish to compare";
        int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(context, text, duration); //Final so that it can be canceled on second click
        toast.show();
        Gallery comparegal = (Gallery) findViewById(R.id.albumGallery);      
        comparegal.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {toast.cancel();
                   Controller.setComparePhoto1(position);
                   Context context2 = getApplicationContext();
                   CharSequence text2 = "Please select the second photo";
                   int duration = Toast.LENGTH_SHORT;
                   Toast toast2 = Toast.makeText(context2, text2, duration);
                   toast2.show();
                   Gallery comparegal = (Gallery) findViewById(R.id.albumGallery);
                   comparegal.setOnItemClickListener(new OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> arg0, View view, int position2, long id) {
                           Log.e("Photo clicked", "Position = " + position2);
                           int requestCode = 0;
                           Log.e("About to go to firstSelectPopup", "request code: " + requestCode);
                           Controller.setComparePhoto2(position2);
                           if(comparing_photo == true){
                           confirmPopup();
                           } else{
                               onResume();
                           }

                    }
                });
            }
        });
    }
   
    private void confirmPopup()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Continue?");
        alert.setMessage("Are you sure you wish to continue?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int yesButton)
            {
                comparing_photo=false;
                Intent compareIntent = new Intent(GalleryActivity.this, CompareActivity.class);
                startActivity(compareIntent);
                    }
            });
     
        // Dialog (popup) Cancel button clicked. Save nothing. Return.
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                comparing_photo = false;
                onResume();
                return;
            }

        });
        alert.show();
        

    }
        
   
    public void onResume(){
        super.onResume();

        if(Controller.getCurrentAlbumIndex() == -1){
            finish();
            return;
        }
        alb = Controller.getCurrentAlbum();
        Gallery ga = (Gallery) findViewById(R.id.albumGallery);       
        ga.setAdapter(new ImageAdapter(this));
        final Context ctx  = this.getApplicationContext();

        ga.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

                            Intent editPhotoIntent = new Intent(ctx, EditPhotoActivity.class);
                               Log.e("Photo clicked", "Position = " + position);

                               Controller.setCurrentPhoto(position);
                               editPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Controller.getCurrentPhoto().getPicture());
                               startActivity(editPhotoIntent);
                              
                        }
        });
        
       
        TextView AlbumName = (TextView) findViewById(R.id.AlbumNameLabel);     
        AlbumName.setText(Controller.getCurrentAlbum().getAlbumName());
    }

    public class ImageAdapter extends BaseAdapter {

        private Context ctx;
        int imageBackground;
       
        public ImageAdapter(Context c) {
                        ctx = c;
                        TypedArray ta = obtainStyledAttributes(R.styleable.Gallery1);
                        imageBackground = ta.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
                        ta.recycle();
                }

                @Override
        public int getCount() {
               
                return alb.getPhotos().size();
        }

        @Override
        public Object getItem(int arg0) {
               
                return alb.getPhotos().get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
               
                return arg0;
        }
       
       
  
       
        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
                ImageView iv = new ImageView(ctx);
                Uri pic = alb.getPhotos().get(arg0).getPicture();
                Log.e("Uri of pic " + arg0,"" + pic );
                iv.setImageURI(pic);//Drawable.createFromPath(pic.getPath()));
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setLayoutParams(new Gallery.LayoutParams(150,120));
                iv.setBackgroundResource(imageBackground);
                return iv;
        }

    }
   
    public void onPause(){
        super.onPause();
        Controller.saveObject(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            
            case R.id.NewPhotoButton:
                final Context ctx  = GalleryActivity.this.getApplicationContext();
                Intent editPhotoIntent = new Intent(ctx, EditPhotoActivity.class);
                Uri imageUri = generateNewUri();
                editPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                Controller.setCurrentPhoto(-1);
                startActivity(editPhotoIntent);
            break;
            
            case R.id.ComparePhotosButton:

                comparing_photo = true;
                comparePhotos();
            break;
        }
    }
    
    public Uri generateNewUri(){
        
        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp";
        File folderF = new File(folder);
        if(!folderF.exists()){
            folderF.mkdir();
        }
        String imageFilePath = folder + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        Uri imageUri = Uri.fromFile(new File(imageFilePath));
        return imageUri;
    }

}