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
    private Bitmap BMPphoto;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
      
        setContentView(R.layout.editphotoview);

        
        ImageButton imageButton = (ImageButton) findViewById(R.id.generated_pic);
        OnClickListener generateListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                setBogoPic();

            }

        };
        imageButton.setOnClickListener(generateListener);
        
        
        Button capture = (Button) findViewById(R.id.PhotoViewSave);
        OnClickListener caputreListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                acceptBogoPic();

            }

        };
        capture.setOnClickListener(caputreListener);
        
        Button backButton = (Button) findViewById(R.id.PhotoViewCancel);
        OnClickListener backListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                cancelBogoPic();

            }

        };
        backButton.setOnClickListener(backListener);
        
    }

    protected void cancelBogoPic()
    {

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
            Log.d("acceptBogoPic", "intent is null");
            return;
        }

  //          File intentFile = getPicturePath(intent);
  //          saveBMP(intentFile, BMPphoto);
            Log.d("acceptBogoPic", "putting BMPphoto in Extra");
            intent.putExtra("BMPphoto", BMPphoto);
            setResult(RESULT_OK);
        
        finish();

    }
// TODO: Save the BMP or create a Photo and save the Photo?
    /*
    private void saveBMP(File intentFile, Bitmap ourBMP2)
    {

        OutputStream stream;
        try
        {
            stream = new FileOutputStream(intentFile);
            BMPphoto.compress(Bitmap.CompressFormat.JPEG, 75, stream);
            stream.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
*/
    private File getPicturePath(Intent intent)
    {

        Uri uri = (Uri) intent.getExtras().get(MediaStore.EXTRA_OUTPUT);
        File file = new File(uri.getPath());

        return file;
    }



    protected void setBogoPic()
    {

        ImageButton button = (ImageButton) findViewById(R.id.generated_pic);
         BMPphoto = BogoPicGen.generateBitmap(350,350);
        button.setImageBitmap(BMPphoto);
    }

    public int getCurrentState()
    {

        return 7;
    }

}
