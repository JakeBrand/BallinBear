package view;



import java.io.File;
import control.Controller;
import model.Album;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

//#################################### This activity uses the albumthumbnail layout and is used to display the photos
public class GalleryActivity extends Activity{

    Album alb;
    ImageView imageView;
       
    String currentAlbumName;
        /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        


        
        
        
        alb = Controller.getCurrentAlbum();
       

     
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

                                  Controller.setCurrentPhoto(position);
                               //TODO: This is where the issue was on March 13 at 1:00am
                               editPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Controller.getCurrentPhoto().getPicture());
                               startActivityForResult(editPhotoIntent, requestCode);
                              
                        }
        });
       
        Button comparePhotosButton = (Button) findViewById(R.id.ComparePhotosButton);
        OnClickListener comparePhotosListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Context context = getApplicationContext();
                CharSequence text = "Please select two photos you wish to compare";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Gallery comparegal = (Gallery) findViewById(R.id.albumGallery);      
                comparegal.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                    // TODO GalleryActivity: give intent photo object, get result from intent as a cancel or accept photo objects into album
                           Log.e("Photo clicked", "Position = " + position);
                           int requestCode = 0;
                           Log.e("About to go to firstSelectPopup", "request code: " + requestCode);
                           Controller.setComparePhoto1(position);
                           //TODO: This is where the issue was on March 13 at 1:00am
                           Context context2 = getApplicationContext();
                           CharSequence text2 = "Please select the second photo";
                           int duration = Toast.LENGTH_SHORT;
                           Toast toast2 = Toast.makeText(context2, text2, duration);
                           toast2.show();
                           Gallery comparegal = (Gallery) findViewById(R.id.albumGallery);
                           comparegal.setOnItemClickListener(new OnItemClickListener() {
                               @Override
                               public void onItemClick(AdapterView<?> arg0, View view, int position2, long id) {
                                   // TODO Auto-generated method stub
                                   Log.e("Photo clicked", "Position = " + position2);
                                   int requestCode = 0;
                                   Log.e("About to go to firstSelectPopup", "request code: " + requestCode);
                                   Controller.setComparePhoto2(position2);
                                   confirmPopup();
                            }
                        });
                    }
                });
            }
        };
        comparePhotosButton.setOnClickListener(comparePhotosListener);
    }
   
    private void confirmPopup()
    {
        // TODO Fix the Dialog not cancelling
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Continue?");
        alert.setMessage("Are you sure you wish to continue?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int yesButton)
            {

                Intent compareIntent = new Intent(GalleryActivity.this, CompareActivity.class);
                startActivity(compareIntent);
                    }
            });
        // Dialog (popup) Cancel button clicked. Save nothing. Return.
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Fix the returning to self on cancel. Want it to just end dialog.
                Intent resetintent = new Intent(GalleryActivity.this, GalleryActivity.class);
                startActivity(resetintent);
            }
        });
        alert.show();
    }
   
    public void onResume(){
        super.onResume();
       
        if(Controller.getCurrentAlbumIndex() == -1)
            finish();
        
        
//        if(Controller.getAlbum(Controller.getPreviousAlbum()).getAlbumName() == alb.getAlbumName()) 
//            finish();
        
        Gallery ga = (Gallery) findViewById(R.id.albumGallery);       
        ga.setAdapter(new ImageAdapter(this));
       
        TextView AlbumName = (TextView) findViewById(R.id.AlbumNameLabel);     
        AlbumName.setText(Controller.getCurrentAlbum().getAlbumName());
    }
//   
//    protected void onActivityReslt(Intent intent){
// Bundle bundle = getIntent().getExtras();
//       
//        try
//        {
//            albumArrayIndex = (Integer) bundle.get("albumArrayIndex");
//        } catch (NullPointerException e)
//        {
//            Log.e("albumArrayIndex", "is null");
//        }
//       
//        alb = Controller.getAlbum(albumArrayIndex);
//       
//        Log.e("Displaying Album", alb.getAlbumName());
//        Log.e("album " + alb.getAlbumName() + " has " + alb.getPhotos().size() + " photos", "####");
//        setContentView(R.layout.galleryview);
//       
//        Gallery ga = (Gallery) findViewById(R.id.albumGallery);
//       
//        TextView AlbumName = (TextView) findViewById(R.id.AlbumNameLabel);
//       
//        AlbumName.setText(alb.getAlbumName());
//       
//       
//        ga.setAdapter(new ImageAdapter(this));
//       
//        final Context ctx  = this.getApplicationContext();
//
//        ga.setOnItemClickListener(new OnItemClickListener() {
//
//                        @Override
//                        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
//                        // TODO GalleryActivity: give intent photo object, get result from intent as a cancel or accept photo objects into album
//
//                            Intent editPhotoIntent = new Intent(ctx, EditPhotoActivity.class);
//                               Log.e("Photo clicked", "Position = " + position);
//                               int requestCode = 0;
//                               Log.e("About to go to editPhoto", "request code: " + requestCode);
//
//                               Bundle bundle = new Bundle();
//                               bundle.putInt("albumArrayIndex", albumArrayIndex);
//                               bundle.putInt("photoIndex", position);
//                               editPhotoIntent.putExtras(bundle);
//                               startActivityForResult(editPhotoIntent, requestCode);
//                              
//                              
//                        }
//        });
//    
//    }
   
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