package view;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.util.Date;

import control.Controller;
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

/**
 * @author J-Tesseract
 * 
 *         CompareActivity
 * 
 *         These are constants used througout CompareActivity These constants
 *         help store the bitmap of the images we are comparing
 */
public class CompareActivity extends Activity implements OnClickListener
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        /**
         * On Create
         * 
         * Load the layout and set on click listeners to the 2 photoviews. These
         * on click listeners will allow user to go back to gallery view once
         * clicked. Once these on click listeners are set they will set the two
         * images selected from gallery view
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

    /**
     * SetProvidedPic1
     * 
     * Here it sets the first image you selected into the first slot for the
     * photo setting its date (from getpTimeStamp) then sending it to handlePic
     * to display the image. The date is in the format "Month Day, -- Time"
     * 
     * @param imageView
     *            The ImageView to set the first picture to
     */
    protected void setProvidedPic1(ImageView imageView)
    {

        Photo providedPhoto = Controller.getComparePhoto1();
        Date date = providedPhoto.getpTimeStamp();
        String formatedDate = DateFormat.getDateInstance().format(date)
                + " -- "
                + DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        TextView photo1Date = (TextView) findViewById(R.id.photo1DateLabel);
        photo1Date.setText(formatedDate);
        handlePic(imageView, providedPhoto);

    }

    /**
     * SetProvidedPic2
     * 
     * Sets the second photos date from the getpTimeStamp() call of the provided
     * photo then send it to handlePic to display the correct image. The date is
     * in the format "Month Day, -- Time"
     * 
     * @param imageView
     *            The ImageView to set the second picture to
     */
    protected void setProvidedPic2(ImageView imageView)
    {

        Photo providedPhoto = Controller.getComparePhoto2();
        Date date = providedPhoto.getpTimeStamp();
        String formatedDate = DateFormat.getDateInstance().format(date)
                + " -- "
                + DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        TextView photo2Date = (TextView) findViewById(R.id.photo2DateLabel);
        photo2Date.setText(formatedDate);
        handlePic(imageView, providedPhoto);

    }

    /**
     * handlePic
     * 
     * Take the URI from the provided photo and get the FilePath and File Input
     * Stream from the image path then set imageView.
     * 
     * @param imageView
     *            The imageView to set the provided picture to
     * @param providedPhoto
     *            the Photo containing the Uri with the picture to set
     * @exception FileNotFoundexception
     *                e The FileNotFound exception that may be thrown
     */
    private void handlePic(ImageView imageView, Photo providedPhoto)
    {
        try
        {

            String imageFilePath = providedPhoto.getPicture().getPath();
            FileInputStream inputStream = new FileInputStream(imageFilePath);
            BufferedInputStream bufferedInput = new BufferedInputStream(
                    inputStream);
            Bitmap BMPphoto = BitmapFactory.decodeStream(bufferedInput);
            imageView.setImageBitmap(BMPphoto);
        } catch (FileNotFoundException e)
        {
            Log.d("FileNotFound", "ComparePhotosActivity");
        }
    }

    /**
     * onClick
     * 
     * Sets the result for the on click listeners to RESULT_OK then finishes the
     * activity
     * 
     * @param v
     *            The View that has just been clicked
     */
    @Override
    public void onClick(View v)
    {

        setResult(RESULT_OK);
        finish();
    }

}