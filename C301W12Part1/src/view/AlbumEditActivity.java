package view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.os.Bundle;
import static java.util.concurrent.TimeUnit.*;

import control.Controller;
import ca.ualberta.ca.c301.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * @author J-Tesseract
 * 
 *         Album Editing Activity (Displays the album name and allows you to
 *         change it, delete the album, or do nothing)
 */
public class AlbumEditActivity extends Activity implements OnClickListener
{

    /**
     * Various fields altered in anonymous methods and used in others.
     */
    EditText                       albName;
    boolean                        completedAlarm;
    int                            alarmWeek;
    int                            alarmDay;
    int                            alarmHour;
    int                            alarmMin;
    int                            alarmFrequency;

    /**
     * The ID sent to the Notification Manager
     */
    private static final int       CICATRIX_ID = 1;
    final ScheduledExecutorService scheduler   = Executors
                                                       .newScheduledThreadPool(Controller.getCurrentAlbumIndex());

    /**
     * On Create
     * 
     * Loads the layout of the album_edit Sets the on click listener for the
     * buttons available
     * 
     * deleteButton allows the user to delete an album
     * 
     * backToAlbumButton returns the user to the list of albums (main.xml) and
     * 
     * doneButton allows the user to save any changes made to the album name
     * 
     * addAlarmButton allows the user to set up an alarm for this album
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
     * onPause
     * 
     * On pause saves current object using the controller
     */
    public void onPause()
    {

        super.onPause();
        // TODO: Save?
        // Controller.saveObject();
    }

    /**
     * sendNotification
     * 
     * Sends a status bar notification to remind the user
     */
    private void sendNotification()
    {

        // TODO: Make Controller do this!
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

        int icon = R.drawable.camera;
        CharSequence tickerText = "CT";
        long time = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, time);

        Context context = getApplicationContext();
        CharSequence contentTitle = "Cicatrix Tracker";
        CharSequence contentText = "Reminder: Take your " + albName.getText()
                + " picture!";
        // TODO: Make NOT WelcomeActivity.class! Make Password check first!
        finish();
        Intent notificationIntent = new Intent(this, WelcomeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText,
                contentIntent);

        mNotificationManager.notify(CICATRIX_ID, notification);

    }

    /**
     * setAlarm
     * 
     * Set an alarm specific to this album to go off at a specific time and
     * frequency
     */
    private void startAlarm()
    {

        // TODO: Make Controller do this!

        final Runnable codeToRun = new Runnable()
        {

            public void run()
            {

                sendNotification();

            }
        };

        // TODO:
        // alarmDay = Controller.getAlarmDay();
        // alarmHour = Controller.getAlarmHour();
        // alarmMin = Controller.getAlarmMin();
        int initialDelay = getInitialDelaySeconds(alarmDay, alarmHour, alarmMin);
        int selection = 1;
        int repeatedDelay = 0;
        if (alarmFrequency == 0)
        {
            repeatedDelay = getRepeatedDelaySeconds(selection, 0, 0);
        } else if (alarmFrequency == 1)
        {
            repeatedDelay = getRepeatedDelaySeconds(0, selection, 0);
        } else if (alarmFrequency == 2)
        {
            repeatedDelay = getRepeatedDelaySeconds(0, 0, selection);
        }

        @SuppressWarnings("rawtypes")
        // final ScheduledFuture notifyerHandle = scheduler
        // .scheduleWithFixedDelay(codeToRun, initialDelay, repeatedDelay,
        // SECONDS);
        // TODO: Remove once tested!
        final ScheduledFuture notifyerHandle = scheduler
                .scheduleWithFixedDelay(codeToRun, initialDelay, 20, SECONDS);
        scheduler.schedule(new Runnable()
        {

            public void run()
            {

                notifyerHandle.cancel(true);
            }
        }, 60 * 60, SECONDS);
        notifyerHandle.cancel(true);
    }

    /**
     * getInitialDelaySeconds
     * 
     * Calculate the number of seconds to delay from the current time to initial
     * notification
     * 
     * @param day
     *            The day of the week to start the initial notification
     * @param hour
     *            The hour to start the initial notification
     * @param min
     *            The min to start the initial notification
     * @return delaySeconds The number of seconds to delay between current time
     *         and
     */
    private int getInitialDelaySeconds(int day, int hour, int min)
    {

        // TODO: Make Controller do this!
        Date today = Calendar.getInstance().getTime();

        SimpleDateFormat DAY = new SimpleDateFormat("dd");
        int currentDay = Integer.parseInt(DAY.format(today));
        SimpleDateFormat HOUR = new SimpleDateFormat("HH");
        int currentHour = Integer.parseInt(HOUR.format(today));
        SimpleDateFormat MIN = new SimpleDateFormat("mm");

        int currentMin = Integer.parseInt(MIN.format(today));
        int secInDay = ((day - currentDay) % 7) * 60 * 60 * 24 * 7;
        int secInHour = ((hour - currentHour) % 24) * 60 * 60;
        int secInMin = ((min - currentMin) % 60) * 60;
        int delaySeconds = secInDay + secInHour + secInMin;

        return delaySeconds;
    }

    /**
     * getRepeatedDelaySeconds
     * 
     * Convert the week, day, or hour into seconds
     * 
     * @param week
     *            The number of weeks to convert to seconds
     * @param day
     *            The number of days to convert to seconds
     * @param hour
     *            The number of hours to convert to seconds
     * @return delaySeconds The number of seconds to delay before repetition
     */
    private int getRepeatedDelaySeconds(int week, int day, int hour)
    {

        // TODO: Make Controller do this!
        int secInWeek = (60 * 60 * 24 * 7 * week);
        int secInDay = (60 * 60 * 24 * day);
        int secInHour = (60 * 60 * hour);
        int delaySeconds = secInWeek + secInDay + secInHour;

        return delaySeconds;
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
                scheduler.shutdownNow();

                Log.d("ShutDown?", "" + scheduler.isShutdown());
                Log.d("Terminated?", "" + scheduler.isTerminated());
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
                if (completedAlarm)
                {
                    // TODO: This V
                    // Controller.addAlarm(alarmDay, alarmHour, alarmMin,
                    // alarmFrequency);
                    // Controller.startAlarm();
                    if (!scheduler.isShutdown())
                    {
                        startAlarm();
                    }
                }
                finish();
                break;

            case R.id.backToAlbumButton:
                finish();
                break;

        }

    }
}
