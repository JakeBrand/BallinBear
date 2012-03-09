package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import ca.ualberta.ca.c301.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class GenerateBogoPicActivity extends Activity
{

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
      
        setContentView(R.layout.take_photo);

        /*
        ImageButton imageButton = (ImageButton) findViewById(R.id.TakePhotoButton);
        OnClickListener listener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                setBogoPic();

            }

        };
        imageButton.setOnClickListener(listener);
      */  
        /*
        Button acceptButton = (Button) findViewById(R.id.AcceptButton);
        OnClickListener listener2 = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                acceptBogoPic();

            }

        };
        acceptButton.setOnClickListener(listener2);
        */
        /*
        Button cancelButton = (Button) findViewById(R.id.CancelButton);
        OnClickListener listener3 = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                cancelBogoPic();

            }

        };
        cancelButton.setOnClickListener(listener3);
        */
    }

    protected void cancelBogoPic()
    {

        Log.d("TEST", "cancelBogoPic() entered");

        Intent intent = getIntent();
        if (intent == null)
        {
            return;
        }
        setResult(RESULT_CANCELED);
        finish();
        return;
    }

    protected void acceptBogoPic()
    {
        // get intent that starts the activity
        Intent intent = getIntent();
        if (intent == null)
        {
            return;
        }

        if (intent.getExtras() != null)
        {
            File intentFile = getPicturePath(intent);
            saveBMP(intentFile, ourBMP);
            setResult(RESULT_OK);
        }
        finish();

    }

    private void saveBMP(File intentFile, Bitmap ourBMP2)
    {

        OutputStream stream;
        try
        {
            stream = new FileOutputStream(intentFile);
            ourBMP.compress(Bitmap.CompressFormat.JPEG, 75, stream);
            stream.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private File getPicturePath(Intent intent)
    {

        Uri uri = (Uri) intent.getExtras().get(MediaStore.EXTRA_OUTPUT);
        File file = new File(uri.getPath());

        return file;
    }

    private Bitmap ourBMP;
/*
    protected void setBogoPic()
    {


        ImageButton button = (ImageButton) findViewById(R.id.TakePhotoButton);
         ourBMP = BogoPicGen.generateBitmap(400,400);
        button.setImageBitmap(ourBMP);
    }

    public int getCurrentState()
    {

        // TODO Auto-generated method stub
        return 7;
    }
*/
}
