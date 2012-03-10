package view;

import control.GenerateBogoPicActivity;

import java.io.File;
import java.util.ArrayList;

import model.Album;
import model.Item;
import model.Photo;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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

//#################################### This activity uses the main layout and is used to display the albums
public class AlbumListActivity extends Activity
{

    ArrayList<Item>  items;
    ArrayList<Album> albs;

    // TODO AlbumListActivity: setup android:albumlist with current data, items
    // have album name, sub items could have album length, the most current
    // photo could be as a thumbnail, last updated?

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button button = (Button) findViewById(R.id.NewPhotoButton);
        OnClickListener listener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                takeNewPhoto(v);

            }

        };
        button.setOnClickListener(listener);
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

    private void takeNewPhoto(View v)
    {

        Intent intent = new Intent(this, GenerateBogoPicActivity.class);
        // Other stuff for storage
        startActivityForResult(intent, 0);
    }

    // private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST = 100;
    // protected void takeAPhoto()
    // {
    // // Create intent instance to be used to take photo
    // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    //
    // // Create Folder in SD Card // folder name = tmp
    // String folder =
    // Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp";
    //
    //
    // // give file the folder (wtf)?
    // File folderF = new File(folder);
    //
    // if(!folderF.exists()){ // if folder doesnt exist
    // folderF.mkdir(); // make it bloody exist
    // }
    //
    // // make file path // name // type
    // String imageFilePath = folder + "/" +
    // String.valueOf(System.currentTimeMillis()) + ".jpg";
    //
    //
    // File imageFile = new File(imageFilePath);
    //
    // // Create URI
    // imageUri = Uri.fromFile(imageFile);
    //
    //
    // intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
    //
    // // start intent to take picture
    // startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST);
    // }
    //
    //
    @Override // if requestCode is 100 we are good?, result code is to see if a
    // picture was actually taken
    //TODO: find out how to access the BMPphoto just taken
    protected void onActivityResult(int requestCode, int resultCode,Intent
     intent ){
     //if(requestCode == 0){
     if(resultCode == RESULT_OK){
         Log.d("TEST", "resultCode == RESULT_OK");
         Bundle bundle = intent.getBundleExtra("BMPphoto");
         Bitmap BMPphoto = (Bitmap) bundle.get("BMPphoto");
        Log.d("BMPphoto.toString =", BMPphoto.toString());
         
     //  Intent intent2 = new Intent(getParent(), PhotoEditActivity.class);
     // set button to look like photo just taken
  //   intent2.putExtra("imagePath",imageUri.getPath());

  //  startActivity(intent2);
     //}
     }
     }
}