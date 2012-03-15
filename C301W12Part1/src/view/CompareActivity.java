package view;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.util.Date;

import control.Controller;
import model.Album;
import model.Photo;
import ca.ualberta.ca.c301.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


public class CompareActivity  extends Activity {
    
	private Bitmap BMPphoto;
    String[]       albumNames;
    Spinner        albumNameSpinner;
    int            albumArrayIndex;
    int            photo1Index;
    int			   photo2Index;
    private static final int INDEX_NOT_IN_BUNDLE =     -1;
    boolean        newAlbumSelected;

    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	Bundle bundle = getIntent().getExtras();
    	super.onCreate(savedInstanceState);
      
    	setContentView(R.layout.compareview);
      
    	initIndices(bundle);
    	
//    	EditText photo1EditText = (EditText) findViewById(R.id.photo1EditText);
//    	EditText photo2EditText = (EditText) findViewById(R.id.photo2EditText);
//      
//      
//    	ImageView photo1ImageView = (ImageView) findViewById(R.id.photo1ImageView);
//    	ImageView photo2ImageView = (ImageView) findViewById(R.id.photo2ImageView);
// 
//    	TextView photo1DateLabel = (TextView) findViewById(R.id.photo1DateLabel);
//    	TextView photo2DateLabel = (TextView) findViewById(R.id.photo2DateLabel);
//    	
//    	
//
//
//    	Photo photo1 = (Photo) bundle.get("Photo1");
//    	Photo photo2 = (Photo) bundle.get("Photo2");
//      
//    	photo1EditText.setText(photo1.getComment());
//    	photo2EditText.setText(photo2.getComment());
//      
//    	photo1DateLabel.setText("Date: " + photo1.getpTimeStamp());
//    	photo2DateLabel.setText("Date: " + photo2.getpTimeStamp());

    	// TODO CompareActivity: Set imageviews with there appropriate data
    	
    	
    	// TODO CompareActivity: SPECIAL Allow for the user to move finger left to right to zoom in on the indicated photo.
    
    	
    
    }
    
    protected void setProvidedPic1()
    {

    	ImageView imageView = (ImageView) findViewById(R.id.photo1ImageView);
        //TextView photoDate = (TextView) findViewById(R.id.photoDateText);
        Photo providedPhoto = Controller.getPhoto(albumArrayIndex, photo1Index);
        Uri uri = providedPhoto.getPicture();
        //EditText commentET = (EditText) findViewById(R.id.commentEditText);
        //String comment = providedPhoto.getComment();
        //commentET.setText(comment);
        try
        {
           
            //Date date = providedPhoto.getpTimeStamp();
            //String formatedDate = DateFormat.getDateInstance().format(date) + " -- " + DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
            //photoDate.setText(formatedDate);
           
            String imageFilePath = uri.getPath();
            FileInputStream inputStream;
            inputStream = new FileInputStream(imageFilePath);
            BufferedInputStream bufferedInput = new BufferedInputStream(
                    inputStream);
            BMPphoto = BitmapFactory.decodeStream(bufferedInput);
            imageView.setImageBitmap(BMPphoto);
        } catch (FileNotFoundException e)
        {
            Log.d("FileNotFound", "ComparePhotosActivity");
        }

    }
    
    protected void setProvidedPic2()
    {

    	ImageView imageView = (ImageView) findViewById(R.id.photo1ImageView);
        //TextView photoDate = (TextView) findViewById(R.id.photoDateText);
        Photo providedPhoto = Controller.getPhoto(albumArrayIndex, photo2Index);
        Uri uri = providedPhoto.getPicture();
        //EditText commentET = (EditText) findViewById(R.id.commentEditText);
        //String comment = providedPhoto.getComment();
        //commentET.setText(comment);
        try
        {
           
            //Date date = providedPhoto.getpTimeStamp();
            //String formatedDate = DateFormat.getDateInstance().format(date) + " -- " + DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
            //photoDate.setText(formatedDate);
           
            String imageFilePath = uri.getPath();
            FileInputStream inputStream;
            inputStream = new FileInputStream(imageFilePath);
            BufferedInputStream bufferedInput = new BufferedInputStream(
                    inputStream);
            BMPphoto = BitmapFactory.decodeStream(bufferedInput);
            imageView.setImageBitmap(BMPphoto);
        } catch (FileNotFoundException e)
        {
            Log.d("FileNotFound", "ComparePhotosActivity");
        }

    }
/*
 * Taken from Jake Brand C301W12 GitHub repository from this group
 * Taken from the EditPhotoActivity Class under the package view
 */
    private void initIndices(Bundle bundle){

        if (bundle.containsKey("albumArrayIndex"))
        {
            albumArrayIndex = (int) bundle.getInt("albumArrayIndex");
        }
        if (bundle.containsKey("photo1Index"))
        {
            photo1Index = (int) bundle.getInt("photo1Index");
        }else
        {
            Log.d("ComparePhotosActivity:", "photo1Index was not in the bundle");
            photo1Index = INDEX_NOT_IN_BUNDLE;
        }
        if (bundle.containsKey("photo2Index"))
        {
            photo2Index = (int) bundle.getInt("photo2Index");
        }else
        {
            Log.d("ComparePhotosActivity:", "photo2Index was not in the bundle");
            photo2Index = INDEX_NOT_IN_BUNDLE;
        }
        if (albumArrayIndex != INDEX_NOT_IN_BUNDLE && photo1Index != INDEX_NOT_IN_BUNDLE)
        {
            Log.d("ComparePhotosActivity", "Setting provided pic 1");
            setProvidedPic1();
        }
        if (albumArrayIndex != INDEX_NOT_IN_BUNDLE && photo2Index != INDEX_NOT_IN_BUNDLE)
        {
            Log.d("ComparePhotosActivity", "Setting provided pic 2");
            setProvidedPic2();
        }
    }
    
    public void onPause(){
        super.onPause();
        Controller.saveObject(this);
        
    }
    
}
