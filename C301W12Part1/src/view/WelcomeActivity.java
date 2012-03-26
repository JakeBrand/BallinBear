package view;

import java.io.File;

import control.Controller;

import ca.ualberta.ca.c301.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * First called activity using the welcome_view.xml to redirect the user to
 * either take a new photo or view all albums
 * 
 * @author J-Tesseract
 * 
 */
public class WelcomeActivity extends Activity
{

    /**
     * Fields called in too
     */

    private static final int TAKE_PICTURE_ACTIVITY_REQUEST = 200;
    String                   fileName                      = "fileSave.data";

    /**
     * Initialize UI components
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        Log.e("wecome", "START");

        try
        {
            Controller.loadObject(this);
        } catch (Exception e)
        {
            Log.e(null, "NOTHING LOADED FROM FILE");
        }
        setContentView(R.layout.welcome_view);

        Button newPhoto = (Button) findViewById(R.id.takeNewPhotoButton);
        newPhoto.setBackgroundColor(Color.GREEN);

        OnClickListener newPhotoListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                takeAPhoto();
            }

        };
        newPhoto.setOnClickListener(newPhotoListener);

        Button viewAlbums = (Button) findViewById(R.id.viewAlbumsButton);
        viewAlbums.setBackgroundColor(Color.CYAN);

        OnClickListener viewAlbumsListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                // TODO WelcomeActivity: view Albums
                Intent albumListIntent = new Intent(v.getContext(),
                        AlbumListActivity.class);

                startActivity(albumListIntent);
                // WelcomeActivity.this.startActivity(albumListIntent);
            }

        };
        viewAlbums.setOnClickListener(viewAlbumsListener);

    }

    /**
     * Save state before taking a new photo
     */
    public void onPause()
    {

        super.onPause();
        Controller.saveObject();

    }

    /**
     * Start intent to take a new picture and save it at the designated Uri
     * 
     */
    protected void takeAPhoto()
    {

        String folder = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/tmp";

        File folderF = new File(folder);
        if (!folderF.exists())
        {
            folderF.mkdir();
        }

        String imageFilePath = folder + "/"
                + String.valueOf(System.currentTimeMillis()) + ".jpg";
        File imageFile = new File(imageFilePath);
        Uri imageUri = Uri.fromFile(imageFile);

        Intent takePhotoIntent = new Intent(this, EditPhotoActivity.class);

        Bundle bundle = new Bundle();
        takePhotoIntent.putExtras(bundle);

        Controller.setCurrentPhoto(-1);
        Controller.setCurrentAlbum(-1);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(takePhotoIntent, TAKE_PICTURE_ACTIVITY_REQUEST);
    }

    @Override
    /**
     * OnResult confirm the result is ok and start intent to switch to gallery view
     * 
     * @param requestCode The code indicating what called the Activity for result
     * @param resultCode The code indicating how the calling activity resulted
     * @param intent The intent that started the activity for result
     */
    protected void onActivityResult(int requestCode, int resultCode,
            Intent intent)
    {

        Log.e("onActivityResult", "Got here");
        if (requestCode == TAKE_PICTURE_ACTIVITY_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {

                Intent galleryActivityIntent = new Intent(this,
                        GalleryActivity.class);

                startActivity(galleryActivityIntent);
            }
        }

    }

}
