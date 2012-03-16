package view;


import java.io.File;
import java.util.ArrayList;

import model.Album;
import model.Photo;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

import control.Controller;

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

/**
 * This activity uses layout.main to display albums
 * 
 * @author ching
 *
 */
public class AlbumListActivity extends Activity
{
	/**
	 * declares two globals
	 * String[] albumNames is used to store the current albumNames from the controller
	 * It is used to set array adapter albListAdapter
	 * albcounter is an album counter to set empty list string
	 */
    String[] albumNames;
    int albcounter;
    // TODO AlbumListActivity: setup android:albumlist with current data, items
    // have album name, sub items could have album length, the most current
    // photo could be as a thumbnail, last updated?

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	/**
    	 * On Create
    	 */
        super.onCreate(savedInstanceState);
    }
    
     public void onResume(){
    	 /**
    	  * Resumes the activity
    	  * Sets the layout to the main.xml
    	  * Loads the list view into albumlistView and finds its ID in R.
    	  * Gets the count on the number of albums we currently have
    	  * if albcounter is 0 then change the string to show that we have no albums
    	  * Create an adapter for our albumList
    	  * Set the clickability of our albums to true and make so if you click 
    	  * you will open the album in gallery view, but if you hold (long click) then it will
    	  * open the Album Edit View to allow you to delete and change the album name
    	  */
    	super.onResume();
        setContentView(R.layout.main);
        ListView albumlistView = (ListView) findViewById(R.id.albumlist);
        albcounter = Controller.getAlbumNames().length;
        albumNames = Controller.getAlbumNames();
        ArrayAdapter<String> albListAdapter = new ArrayAdapter<String>(AlbumListActivity.this, android.R.layout.simple_list_item_1, albumNames);
        albumlistView.setAdapter(albListAdapter);
        if (albcounter == 0){
        	TextView albumsText = (TextView) findViewById(R.id.albumTextView);
        	albumsText.setText("No Albums Available");
        }
        
        albumlistView.setClickable(true);
        
        albumlistView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
            	Intent intent = new Intent (AlbumListActivity.this, GalleryActivity.class);
            	Controller.setCurrentAlbum(position);
            	startActivity(intent);
            }
        });
    
        albumlistView.setOnItemLongClickListener(new OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                int position, long id) {
                Intent intent = new Intent (AlbumListActivity.this, AlbumEditActivity.class);
                Controller.setCurrentAlbum(position);
                startActivity(intent);
                return true;
            }
        });
    
    }    
}