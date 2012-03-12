package view;



import java.sql.Blob;
import java.sql.SQLException;

import control.Controller;

import model.Album;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import ca.ualberta.ca.c301.R;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
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
        
        Log.e(null, "albName " + alb.getAlbumName());
        
        setContentView(R.layout.galleryview);
        Log.e(null, "got here 4");
        
        Gallery ga = (Gallery)findViewById(R.id.albumGallery);
        
        TextView AlbumName = (TextView) findViewById(R.id.AlbumNameLabel);
        Log.e(null, "is gallery null: " + ga.toString());
        Log.e(null, "is tv null: "  + AlbumName.toString());
        AlbumName.setText(alb.getAlbumName());
        
        
        ga.setAdapter(new ImageAdapter(this));
        
        final Context ctx  = this.getApplicationContext();

        ga.setOnItemClickListener(new OnItemClickListener() {

                        @Override 
                        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                        // TODO GalleryActivity: give intent photo object, get result from intent as a cancel or accept photo objects into album

                            Log.e(null, "what is this: "  + toString());
                          //  Log.e(null, "is parent: "  + getParent().toString());
                            Intent editPhotoIntent = new Intent(ctx, EditPhotoActivity.class); 
                               editPhotoIntent.putExtra("position", position);
                               
                               int requestCode = 0;
                               Log.e("About to go to editPhoto", "request code: " + requestCode);
                               
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
                
                Uri pic = alb.getPhotos().get(arg0).getPicture();

                
                iv.setImageDrawable(Drawable.createFromPath(pic.getPath())); 
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
