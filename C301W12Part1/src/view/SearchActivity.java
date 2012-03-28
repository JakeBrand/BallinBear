package view;

import view.GalleryActivity.ImageAdapter;
import ca.ualberta.ca.c301.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import control.Controller;
import java.util.ArrayList;

import model.SearchItem;

/**
 * First called activity using the welcome_view.xml to redirect the user to
 * either take a new photo or view all albums
 * 
 * @author J-Tesseract
 * 
 */
public class SearchActivity extends Activity 
{

    ArrayList<SearchItem> results;
    
    /**
     * onCreate
     * Initialize UI components
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.search_view);
            
            final EditText tagET = (EditText) findViewById(R.id.tagEditText);
            final Gallery tagGallery = (Gallery) findViewById(R.id.tagGallery);
            
            
            
            final Context ctx  = this.getApplicationContext();

            tagGallery.setOnItemClickListener(new OnItemClickListener() { // if a photo is selected, open it in editPhoto

                            @Override
                            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

                                Intent editPhotoIntent = new Intent(ctx, EditPhotoActivity.class);
                                   Controller.setCurrentAlbum(results.get(position).getAlbumIndex());
                                   Controller.setCurrentPhoto(results.get(position).getPhotoIndex());
                                   editPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Controller.getCurrentPhoto().getPicture());
                                   finish();
                                   startActivity(editPhotoIntent);
                                  
                            }
            });
            
            
            
            
            
            
            Button searchButton = (Button) findViewById(R.id.searchButton);
            searchButton.setOnClickListener(new OnClickListener()
            {
                
                @Override
                public void onClick(View v)
                {
            
                   results = Controller.findPhotos(tagET.getText().toString());
                   tagGallery.setAdapter(new ImageAdapter(SearchActivity.this));
                    
                }
            });
            
    
}


    /**
     * ImageAdapter Class
     * 
     * Is used to set the Gallery with the currently selected album
     *
     */
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
                   try
                {
                    return results.size();
                } catch (NullPointerException e)
                {
                   return 0;
                }
            }

            @Override
            public Object getItem(int arg0) {
                   
                    return results.get(arg0);
            }

            @Override
            public long getItemId(int arg0) {
                   
                    return arg0;
            }
           
           
      
           /**
            * Return the ImageView with the current element Uri
            * 
            * @param arg0 (int)
            * @param arg1 (View)
            * @param arg2 (ViewGroup)
            * @return ImageView
            */
            @Override
            public View getView(int arg0, View arg1, ViewGroup arg2) {
                    ImageView iv = new ImageView(ctx);
                    Uri pic = results.get(arg0).getPictureUri();
                    iv.setImageDrawable(Drawable.createFromPath(pic.getPath()));
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    iv.setLayoutParams(new Gallery.LayoutParams(300,300));
                    iv.setBackgroundResource(imageBackground);
                    return iv;
            }

        }
       



}