package view;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.os.Bundle;
import static java.util.concurrent.TimeUnit.*;

import control.Controller;
import ca.ualberta.ca.c301.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * @author J-Tesseract
 * 
 *         Album Editing Activity (Displays the album name and allows you to
 *         change it, delete the album, or do nothing)
 */
public class AlbumEditActivity extends Activity implements OnClickListener
{

    EditText albName;

    /**
     * On Create
     * 
     * Loads the layout of the album_edit Sets the on click listener for the 3
     * buttons available deleteButton allows you to delete an album
     * backToAlbumButton returns you to the list of albums (main.xml) and done
     * button allows you to save any changes made to the album name
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_edit);

        albName = (EditText) findViewById(R.id.albumNameEditText);

        albName.setText(Controller.getCurrentAlbum().getAlbumName());

        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(this);

        Button backToAlbumButton = (Button) findViewById(R.id.backToAlbumButton);
        backToAlbumButton.setOnClickListener(this);

        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(this);

        Button addAlarmButton = (Button) findViewById(R.id.addAlarmButton);
        addAlarmButton.setOnClickListener(this);

    }

    /**
     * onPause
     * 
     * On pause saves current object using the controller
     */
    public void onPause()
    {

        super.onPause();
        // Controller.saveObject();
    }

    /**
     * setAlarm
     * 
     * Set an alarm specific to this album to go off at a specific time and
     * frequency
     */
    private void startAlarm()
    {

        Log.d("TESTING", "In setAlarm");
        final ScheduledExecutorService scheduler = Executors
                .newScheduledThreadPool(1);
        final Runnable toRun = new Runnable()
        {

            public void run()
            {
                //TODO: pop up notification
                System.out.println("beep");
            }
        };

        final ScheduledFuture beeperHandle = scheduler.scheduleWithFixedDelay(
                toRun, 5, 1, SECONDS);
        scheduler.schedule(new Runnable()
        {

            public void run()
            {

                beeperHandle.cancel(true);
            }
        }, 60 * 60, SECONDS);
    }

    private int getDelaySeconds(int week, int day, int hour, int min){
        
        int delaySeconds = 0;
        //TODO: Calculate delay from now to destination
        return delaySeconds;
    }
    
    public void getAlarmStartDate()
    {

        Log.d("TESTING", "In getAlarmStartDate");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Alarm Start Time");
        alert.setMessage("Select the Alarm Start Time");

        final TimePicker timePicker = new TimePicker(this);
        alert.setView(timePicker);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int whichButton)
            {

                int hour = timePicker.getCurrentHour();
                int min = timePicker.getCurrentMinute();
                // TODO: Controller.setAlarm(int hour, int min);
                
                getAlarmFrequency();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int whichButton)
            {

            }
        });

        alert.show();

    }

    public void getAlarmFrequency()
    {

        Log.d("TESTING", "In getAlarmFrequency");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Alarm Frequency");
        alert.setMessage("Select the Alarm Frequency");

        final Spinner frequencySpinner = new Spinner(this);
        alert.setView(frequencySpinner);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int whichButton)
            {

                int position = frequencySpinner.getSelectedItemPosition();
                String selection = (String) frequencySpinner
                        .getItemAtPosition(position);
                // TODO: Controller.setFrequency(String selection);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int whichButton)
            {

            }
        });

        alert.show();

    }

    /**
     * onClick
     * 
     * Perform action depending on the UI clicked
     * 
     * @param v
     *            The View that has just been clicked
     */
    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.addAlarmButton:
                // TODO: Make this work...
                getAlarmStartDate();
               // getAlarmFrequency();
                break;

            case R.id.deleteButton:
                // TODO AlbumEditActivity: delete album; show a warning of all
                // the photos that will be deleted
                // if only album go to welcome, else go to albumlist
                Controller.deleteAlbum(Controller.getCurrentAlbumIndex());
                finish();
                break;

            case R.id.doneButton:
                Controller.setCurrentAlbumName(albName.getText().toString());
                finish();
                break;

            case R.id.backToAlbumButton:
                finish();
                break;

        }

    }
}
