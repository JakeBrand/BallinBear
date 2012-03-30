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
public class WelcomeActivity extends Activity {
    
        /**
         * Constant
         */
	private static final int TAKE_PICTURE_ACTIVITY_REQUEST = 200;

	/**
	 * onCreate
	 * Initialize UI components
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		try {
			Controller.loadObject(this);
		} catch (Exception e) {
			Log.e(null, "NOTHING LOADED FROM FILE");
		}
		
		
		Controller.setTags();
		
		
		
		setContentView(R.layout.welcome_view);

		Button newPhoto = (Button) findViewById(R.id.takeNewPhotoButton);
		newPhoto.setBackgroundColor(Color.GREEN);

		OnClickListener newPhotoListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				takeAPhoto();       // if new photo button clicked, take a photo
			}

		};
		newPhoto.setOnClickListener(newPhotoListener);

		Button viewAlbums = (Button) findViewById(R.id.viewAlbumsButton);
		viewAlbums.setBackgroundColor(Color.CYAN);

		OnClickListener viewAlbumsListener = new OnClickListener() {

			@Override
			public void onClick(View v) { 

				Intent albumListIntent = new Intent(v.getContext(),
						AlbumListActivity.class);

				startActivity(albumListIntent); // if albums button clicked, go to album activity
			}

		};
		viewAlbums.setOnClickListener(viewAlbumsListener);
		
		
		Button searchPhotos = (Button) findViewById(R.id.searchButton);
		searchPhotos.setBackgroundColor(Color.RED);
		
		OnClickListener searchListener = new OnClickListener()
                {
                    
                    @Override
                    public void onClick(View v)
                    {
                
                        Intent searchIntent = new Intent(v.getContext(), SearchActivity.class);
                        startActivity(searchIntent);
                        
                    }
                };
                searchPhotos.setOnClickListener(searchListener);
		

	}

	/**
	 * takeAPhoto
	 * 
	 * Start intent to take a new picture and save it at the designated Uri
	 */
	protected void takeAPhoto() {

		String folder = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/tmp";

		File folderF = new File(folder);
		if (!folderF.exists()) {
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
	 * onActivityResult
	 * after returning from editphoto go to the gallery where the photo was placed
	 * 
	 * @param requestCode
	 * 
	 */
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == TAKE_PICTURE_ACTIVITY_REQUEST) {
			if (resultCode == RESULT_OK) {

				Intent galleryActivityIntent = new Intent(this,
						GalleryActivity.class);

				startActivity(galleryActivityIntent);
			}
		}

	}

}
