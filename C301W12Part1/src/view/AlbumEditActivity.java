package view;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.os.Bundle;

import control.Controller;
import ca.ualberta.ca.c301.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * @author J-Tesseract
 * 
 *         Album Editing Activity (Displays the album name and allows you to add
 *         an alarm, change the name, delete the album, or do nothing)
 */
public class AlbumEditActivity extends Activity implements OnClickListener
{

    /**
     * Various fields altered in anonymous methods and used in others.
     */
    private EditText albName;
    boolean          completedAlarm;
    private Context  alarmContext = this;
    int              alarmDay;
    int              alarmHour;
    int              alarmMin;
    int              alarmFrequency;

    /**
     * On Create
     * 
     * Loads the layout of the album_edit.
     * 
     * Sets the on click listener for the buttons available
     * 
     * addAlarmButton starts the sequence to set up an alarm for this Album
     * 
     * removeAlarmButton removes any alarm on this Album
     * 
     * deleteButton allows the user to delete an album
     * 
     * backToAlbumButton returns the user to the list of albums (main.xml)
     * 
     * doneButton allows the user to save any changes made to the album
     * 
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

        Button removeAlarmButton = (Button) findViewById(R.id.removeAlarmButton);
        removeAlarmButton.setOnClickListener(this);
    }

    /**
     * setAlarmStartDay
     * 
     * Set the day of the week to start the alarm and call setAlarmStartTime
     */
    private void setAlarmStartDay()
    {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Alarm Start Day");
        alert.setMessage("Select the Alarm Start Day");

        String[] days = { "Sunday", "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday" };
        final Spinner daySpinner = new Spinner(this);
        alert.setView(daySpinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                AlbumEditActivity.this, android.R.layout.simple_spinner_item,
                days);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        daySpinner.setAdapter(spinnerAdapter);
        daySpinner.setSelection(0, false);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int whichButton)
            {

                int day = daySpinner.getSelectedItemPosition();
                alarmDay = day;
                setAlarmStartTime();
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
     * setAlarmStartTime
     * 
     * Set The time of the day to start the alarm and call setAlarmFrequency
     */
    private void setAlarmStartTime()
    {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Alarm Start Time");
        alert.setMessage("Select the Alarm Start Time");

        final TimePicker timePicker = new TimePicker(this);
        timePicker.setIs24HourView(true);
        alert.setView(timePicker);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int whichButton)
            {

                int hour = timePicker.getCurrentHour();
                int min = timePicker.getCurrentMinute();
                alarmHour = hour;
                alarmMin = min;

                setAlarmFrequency();
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
     * setAlarmFrequency
     * 
     * Set the frequency of the alarm repetition
     */
    public void setAlarmFrequency()
    {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Alarm Frequency");
        alert.setMessage("Select the Alarm Frequency");

        String[] frequencies = { "Weekly", "Daily", "Hourly" };
        final Spinner frequencySpinner = new Spinner(this);
        alert.setView(frequencySpinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                AlbumEditActivity.this, android.R.layout.simple_spinner_item,
                frequencies);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        frequencySpinner.setAdapter(spinnerAdapter);
        frequencySpinner.setSelection(0, false);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int whichButton)
            {

                completedAlarm = true;
                int position = frequencySpinner.getSelectedItemPosition();
                alarmFrequency = position;
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
     * displayWarning
     * 
     * Warn the user that all photos will be deleted If OK clicked, delete the
     * album If Cancel clicked, do nothing
     */
    public void displayWarning()
    {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Warning");
        alert.setMessage("Do you wish to delete the album and all it's photos?");

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int whichButton)
            {

                Controller.deleteAlbum(Controller.getCurrentAlbumIndex());
                Controller.saveObject();
                finish();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int whichButton)
            {

                return;
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
                setAlarmStartDay();
                break;

            case R.id.removeAlarmButton:

                Controller.removeAlarm();
                Toast alarmRemoved = Toast.makeText(this, "Alarm Removed",
                        Toast.LENGTH_SHORT);
                alarmRemoved.show();
                break;

            case R.id.deleteButton:

                displayWarning();
                break;

            case R.id.doneButton:

                Controller.setCurrentAlbumName(albName.getText().toString());
                if (completedAlarm)
                {
                    Controller.addAlarm(alarmDay, alarmHour, alarmMin,
                            alarmFrequency, alarmContext);

                    Toast alarmAdded = Toast.makeText(this, "Alarm Added",
                            Toast.LENGTH_SHORT);
                    alarmAdded.show();
                }
                finish();
                break;

            case R.id.backToAlbumButton:

                finish();
                break;

        }

    }
}
