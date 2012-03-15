package view;



import java.io.File;
import java.sql.Blob;
import java.sql.SQLException;

import control.Controller;

import model.Album;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import ca.ualberta.ca.c301.R;
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

//#################################### This activity uses the albumthumbnail layout and is used to display the photos
public class GalleryActivity extends Activity{

    Album alb;
    ImageView imageView;
    int albumArrayIndex;
        
        /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle bundle = getIntent().getExtras();
        
        try
        {
            albumArrayIndex = (Integer) bundle.get("albumArrayIndex");
        } catch (NullPointerException e)
        {
            Log.e("albumArrayIndex", "is null");
        }
        
        alb = Controller.getAlbum(albumArrayIndex);
        
        
      
        Log.e("Displaying Album", alb.getAlbumName());
        Log.e("album " + alb.getAlbumName() + " has " + alb.getPhotos().size() + " photos", "####");
        setContentView(R.layout.galleryview);

        Button newPhotoButton = (Button) findViewById(R.id.NewPhotoButton);
        newPhotoButton.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                //TODO: Make this code into a function, not a huge inner class!
                final Context ctx  = GalleryActivity.this.getApplicationContext();
                Intent editPhotoIntent = new Intent(ctx, EditPhotoActivity.class);
                int requestCode = 0;
                Bundle bundle = new Bundle();
                bundle.putInt("albumArrayIndex", albumArrayIndex);
                editPhotoIntent.putExtras(bundle);
                String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp";
                File folderF = new File(folder);
                
                if(!folderF.exists()){
                    folderF.mkdir();
                }
                // make file path                                       // name                         // type
                String imageFilePath = folder + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                Uri imageUri = Uri.fromFile(new File(imageFilePath));
                editPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(editPhotoIntent, requestCode);
                
            }
        });

        TextView AlbumName = (TextView) findViewById(R.id.AlbumNameLabel);      
        AlbumName.setText(alb.getAlbumName());
        
        Gallery ga = (Gallery) findViewById(R.id.albumGallery);        
        ga.setAdapter(new ImageAdapter(this));
        
        final Context ctx  = this.getApplicationContext();

        ga.setOnItemClickListener(new OnItemClickListener() {

                        @Override 
                        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                        // TODO GalleryActivity: give intent photo object, get result from intent as a cancel or accept photo objects into album

                            Intent editPhotoIntent = new Intent(ctx, EditPhotoActivity.class); 
                               Log.e("Photo clicked", "Position = " + position);
                               int requestCode = 0;
                               Log.e("About to go to editPhoto", "request code: " + requestCode);
                               Bundle bundle = new Bundle();
                               bundle.putInt("albumArrayIndex", albumArrayIndex);
                               bundle.putInt("photoIndex", position);
                               editPhotoIntent.putExtras(bundle);
                               //TODO: This is where the issue was on March 13 at 1:00am
                               editPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Controller.getPhoto(albumArrayIndex, position).getPicture());
                               startActivityForResult(editPhotoIntent, requestCode);
                               
                        }
        });
        
    }
    
    public void onResume(){
        super.onResume();
        Gallery ga = (Gallery) findViewById(R.id.albumGallery);        
        ga.setAdapter(new ImageAdapter(this));
        
        if(Controller.getAlbum(albumArrayIndex).getPhotos().size() == 0){
         //   finish();
        }
    }
    
    protected void onActivityReslt(Intent intent){
 Bundle bundle = getIntent().getExtras();
        
        try
        {
            albumArrayIndex = (Integer) bundle.get("albumArrayIndex");
        } catch (NullPointerException e)
        {
            Log.e("albumArrayIndex", "is null");
        }
        
        alb = Controller.getAlbum(albumArrayIndex);
        
        Log.e("Displaying Album", alb.getAlbumName());
        Log.e("album " + alb.getAlbumName() + " has " + alb.getPhotos().size() + " photos", "####");
        setContentView(R.layout.galleryview);
        
        Gallery ga = (Gallery) findViewById(R.id.albumGallery);
        
        TextView AlbumName = (TextView) findViewById(R.id.AlbumNameLabel);
        
        AlbumName.setText(alb.getAlbumName());
        
        
        ga.setAdapter(new ImageAdapter(this));
        
        final Context ctx  = this.getApplicationContext();

        ga.setOnItemClickListener(new OnItemClickListener() {

                        @Override 
                        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                        // TODO GalleryActivity: give intent photo object, get result from intent as a cancel or accept photo objects into album

                            Intent editPhotoIntent = new Intent(ctx, EditPhotoActivity.class); 
                               Log.e("Photo clicked", "Position = " + position);
                               int requestCode = 0;
                               Log.e("About to go to editPhoto", "request code: " + requestCode);
                               Bundle bundle = new Bundle();
                               bundle.putInt("albumArrayIndex", albumArrayIndex);
                               bundle.putInt("photoIndex", position);
                               editPhotoIntent.putExtras(bundle);
                               startActivityForResult(editPhotoIntent, requestCode);
                               
                               
                        }
        });
     
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
//                Log.e("In getView", "arg0 = " + arg0);
//                Log.e(null," arg1 = " + arg1.toString());
//                Log.e(null," arg2 = " + arg2.toString());
                Uri pic = alb.getPhotos().get(arg0).getPicture();
                Log.e("Uri of pic " + arg0,"" + pic );
                //Bitmap myBitmap = BitmapFactory.decodeFile(pic.getPath());///sdcard/myImages/myImage.jp);
                
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
    
}