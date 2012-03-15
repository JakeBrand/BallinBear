package view;


import java.io.File;
import java.util.ArrayList;

import model.Album;
import model.Item;
import model.Photo;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
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

//#################################### This activity uses the main layout and is used to display the albums
public class AlbumListActivity extends Activity
{
    ArrayList<Item>  items;
    ArrayList<Album> albs;
    String[] albumNames;
    // TODO AlbumListActivity: setup android:albumlist with current data, items
    // have album name, sub items could have album length, the most current
    // photo could be as a thumbnail, last updated?

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        
        Bundle bundle = getIntent().getExtras();
        
        setContentView(R.layout.main);
        ListView albumlistView = (ListView) findViewById(R.id.albumlist);
        albumNames = Controller.getAlbumNames();
        ArrayAdapter<String> albListAdapter = new ArrayAdapter<String>(AlbumListActivity.this, android.R.layout.simple_list_item_1, albumNames);
        albumlistView.setAdapter(albListAdapter);
        
        if(albumNames.length == 0){
            TextView title = (TextView) findViewById(R.id.albumTextView);
            title.setText("No Albums to be Listed");
        }
        
        
        albumlistView.setClickable(true);
        
        albumlistView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
                
            	Intent intent = new Intent (AlbumListActivity.this, GalleryActivity.class);
            	intent.putExtra("position", position);
            	startActivity(intent);
            }
        });
    }

    private ArrayList<Item> toItem(ArrayList<Album> gal)
    {

        ArrayList<Item> items = new ArrayList<Item>();
        for (int i = 0; i < gal.size(); i++)
        {
            items.add(new Item(gal.get(i), gal.get(i).getAlbumName(), ""
                    + gal.get(i).getPhotos().size()));

        }
        return items;

    }

/*     private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST = 100;
     protected void takeAPhoto()
     {
     // Create intent instance to be used to take photo
     Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    
     // Create Folder in SD Card // folder name = tmp
     String folder =
     Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp";
    
    
     // give file the folder (wtf)?
     File folderF = new File(folder);
    
     if(!folderF.exists()){ // if folder doesnt exist
     folderF.mkdir(); // make it bloody exist
     }
    
     // make file path // name // type
     String imageFilePath = folder + "/" +
     String.valueOf(System.currentTimeMillis()) + ".jpg";
    
    
     File imageFile = new File(imageFilePath);
    
     // Create URI
     imageUri = Uri.fromFile(imageFile);
    
    
     intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
    
     // start intent to take picture
     startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST);
     }
    
    
    
    //TODO: Find out how to access (non-null) Bitmap from bundle...
    //NOTE: intent that is passed IS NULL
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
            Intent intent)
    {

      if(requestCode == 0){
        if (resultCode == RESULT_OK)
        {
            Log.d("TEST", "resultCode == RESULT_OK");
            if (intent != null)
            {
                Bitmap BMPphoto = (Bitmap) intent
                        .getParcelableExtra("BMPphoto");
                Log.d("intent is", intent.toString());
                if (BMPphoto != null)
                {
                    Log.d("BMPphoto", "BMPphoto is NOT null");
                } else{
                    Log.d("BMPhoto", "BMPphoto is null");
                }
            } else {
                Log.d("intent is", "null");
            }
            // Intent intent2 = new Intent(getParent(),
            // PhotoEditActivity.class);
            // set button to look like photo just taken
            // intent2.putExtra("imagePath",imageUri.getPath());

            // startActivity(intent2);
             }
        }
    }
    public void onPause(){
        super.onPause();
        Controller.saveObject(this);
        
    }*/
    
}