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
import android.widget.ImageView;
import android.widget.TextView;

public class CompareActivity extends Activity implements OnClickListener
{
/**
 * These are constants used througout CompareActivity
 * These constants help store the bitmap of the images we are comparing
 */
    private Bitmap BMPphoto;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
/**
 * On Create where we load the layout we are using as well
 * as set on click listeners to the 2 photoviews
 * These on click listeners will allow user to go back to 
 * gallery view once clicked.
 * Once these on click listeners are set they will set
 * the two images selected from gallery view 
 */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compareview);

        ImageView firstIV = (ImageView) findViewById(R.id.photo1ImageView);
        firstIV.setOnClickListener(this);

        ImageView secondIV = (ImageView) findViewById(R.id.photo2ImageView);
        secondIV.setOnClickListener(this);

        setProvidedPic1(firstIV);
        setProvidedPic2(secondIV);
        // TODO CompareActivity: SPECIAL Allow for the user to move finger left
        // to right to zoom in on the indicated photo.

    }

    protected void setProvidedPic1(ImageView imageView)
    {
    	/**
    	 * Here it sets the first image you selected into the first
    	 * slot for the photo setting its date (from getpTimeStamp)
    	 * then sending it to handlePic to display the image.
    	 * The date is in the format "Month Day, -- Time"
    	 */

        Photo providedPhoto = Controller.getComparePhoto1();
        Date date = providedPhoto.getpTimeStamp();
        String formatedDate = DateFormat.getDateInstance().format(date)
                + " -- "
                + DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        TextView photo1Date = (TextView) findViewById(R.id.photo1DateLabel);
        photo1Date.setText(formatedDate);
        handlePic(imageView, providedPhoto);

    }

    protected void setProvidedPic2(ImageView imageView)
    {
    	/**
    	 * Sets the second photos date from the
    	 * getpTimeStamp() call of the provided photo
    	 * then send it to handlePic to display the correct image.
    	 * The date is in the format "Month Day, -- Time"
    	 */

        Photo providedPhoto = Controller.getComparePhoto2();
        Date date = providedPhoto.getpTimeStamp();
        String formatedDate = DateFormat.getDateInstance().format(date)
                + " -- "
                + DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        TextView photo2Date = (TextView) findViewById(R.id.photo2DateLabel);
        photo2Date.setText(formatedDate);
        handlePic(imageView, providedPhoto);

    }

    private void handlePic(ImageView imageView, Photo providedPhoto)
    {
    	/**
    	 * Whenever handlePic is called, it will take the provided URI
    	 * from the provided photo then it will try to get the FilePath
    	 * and get the File Input Stream from the image path
    	 * (uri.getPath())
    	 * It will then take the BMPphoto and set the image View.
    	 * If there is a File Not Found Exception then the catch will
    	 * print out the error into logcat Tagged "FileNotFound" 
    	 * with the string "ComparePhotosActivity"
    	 */

        Uri uri = providedPhoto.getPicture();
        try
        {

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

    @Override
    public void onClick(View v)
    {
    	/**
    	 * Sets the result for the on click listeners to RESULT_OK
    	 * then finishes the activity
    	 */
        setResult(RESULT_OK);
        finish();
    }

}