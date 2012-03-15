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
    int               photo2Index;
    private static final int INDEX_NOT_IN_BUNDLE =     -1;
    boolean        newAlbumSelected;

   
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compareview);
       setProvidedPic1();
       setProvidedPic2();
        // TODO CompareActivity: SPECIAL Allow for the user to move finger left to right to zoom in on the indicated photo.
   
       
   
    }
   
    protected void setProvidedPic1()
    {

        ImageView imageView = (ImageView) findViewById(R.id.photo1ImageView);
        Photo providedPhoto = Controller.getComparePhoto1();
        Uri uri = providedPhoto.getPicture();
        try
        {
          
            Date date = providedPhoto.getpTimeStamp();
            String formatedDate = DateFormat.getDateInstance().format(date) + " -- " + DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
            TextView photo1Date = (TextView) findViewById(R.id.photo1DateLabel);
            photo1Date.setText(formatedDate);
          
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
        Photo providedPhoto = Controller.getComparePhoto2();
        Uri uri = providedPhoto.getPicture();
        try
        {
          
            Date date = providedPhoto.getpTimeStamp();
            String formatedDate = DateFormat.getDateInstance().format(date) + " -- " + DateFormat.getTimeInstance(DateFormat.SHORT).format(date);

            TextView photo2Date = (TextView) findViewById(R.id.photo2DateLabel);
            photo2Date.setText(formatedDate);
            
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

   
   
}