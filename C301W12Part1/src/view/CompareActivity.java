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

    private Bitmap BMPphoto;
    int            photo1Index;
    int            photo2Index;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

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

        setResult(RESULT_OK);
        finish();
    }

}