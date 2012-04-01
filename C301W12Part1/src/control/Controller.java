package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import view.PasswordActivity;
import ca.ualberta.ca.c301.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import model.Album;
import model.Photo;
import model.SearchItem;

/**
 * Controller is a static singleton that provides the views with the model and
 * updates the model based on the users interactions with the view
 * 
 * @author J-Tesseract
 */
public class Controller
{

    /**
     * the password that lets the user into the app
     */
    private static String password;

    public static String getPassword()
    {

        return password;
    }

    public static void setPassword(String password)
    {

        Controller.password = password;
    }

    /**
     * When moving between Activities, these 4 variables help the Controller
     * know what Album/Photo(s) it should be passing.
     */
    private static int               currentAlbum;
    private static int               currentPhoto;
    private static int               comparePhoto1;
    private static int               comparePhoto2;

    /**
     * albums holds all of the Albums
     */
    private static ArrayList<Album>  albums           = new ArrayList<Album>();
    private static Context           ctx;
    private static final String      fileName         = "albumsfile.data";
    private static final String      passwordFileName = "ps.data";

    private static ArrayList<String> tags             = new ArrayList<String>();

    /**
     * Final variables needed to set an alarm
     */
    private static final int         SECONDSPERMIN    = 60;
    private static final int         SECONDSPERHOUR   = SECONDSPERMIN * 60;
    private static final int         SECONDSPERDAY    = SECONDSPERHOUR * 24;

    /**
     * getAlbum
     * 
     * Return the Album at the given index
     * 
     * @param albumIndex
     *            The index of the album to return
     * @return the Album at index albumIndex
     */
    public static Album getAlbum(int albumIndex)
    {

        return albums.get(albumIndex);
    }

    /**
     * setCurrentPhoto
     * 
     * Set the pointer to the current photo to provided index
     * 
     * @param currentP
     *            The index of the current photo
     */
    public static void setCurrentPhoto(int currentP)
    {

        currentPhoto = currentP;
    }

    /**
     * getCurrentPhotoIndex
     * 
     * Return the index of the current photo
     * 
     * @return currentPhoto The index of the current Photo
     */
    public static int getCurrentPhotoIndex()
    {

        return currentPhoto;
    }

    /**
     * getCurrentPhoto
     * 
     * Return the Photo pointed to by currentPhoto
     * 
     * @return Photo in the currentAlbum at the currentPhotoIndex
     */
    public static Photo getCurrentPhoto()
    {

        return albums.get(currentAlbum).getPhoto(currentPhoto);
    }

    /**
     * setComparePhoto1
     * 
     * Set the first of the two Photos to compare
     * 
     * @param photo1
     *            The index of the first photo to compare
     */
    public static void setComparePhoto1(int photo1)
    {

        comparePhoto1 = photo1;
    }

    /**
     * setComparePhoto2
     * 
     * Set the second of the two Photos to compare
     * 
     * @param photo2
     *            The index of the second photo to compare
     */
    public static void setComparePhoto2(int photo2)
    {

        comparePhoto2 = photo2;
    }

    /**
     * getComparePhoto1 Get the Photo object pointed to by comparePhoto1 index
     * 
     * Get the Photo pointed to by comparePhoto1 index
     * 
     * @return Photo pointed to by comparePhoto1 index
     */
    public static Photo getComparePhoto1()
    {

        return albums.get(currentAlbum).getPhoto(comparePhoto1);
    }

    /**
     * getComparePhoto2
     * 
     * Get the Photo pointed to by comparePhoto2 index
     * 
     * @return Photo pointed to by comparePhoto2 index
     */
    public static Photo getComparePhoto2()
    {

        return albums.get(currentAlbum).getPhoto(comparePhoto2);
    }

    /**
     * getCurrentAlbum
     * 
     * Get the Album pointed to by the currentAlbum index
     * 
     * @return Album at currentAlbum index
     */
    public static Album getCurrentAlbum()
    {

        return albums.get(currentAlbum);
    }

    /**
     * setCurrentAlbum
     * 
     * Set the pointer "currentAlbum" to the provided Album index
     * 
     * @param newCurrentAlbum
     *            The provided Album to set the pointer at
     */
    public static void setCurrentAlbum(int newCurrentAlbum)
    {

        currentAlbum = newCurrentAlbum;
    }

    /**
     * setCurrentAlbumName
     * 
     * Change the current Album's name to provided new name
     * 
     * @param newName
     *            The provided new name to overwrite the old name
     */
    public static void setCurrentAlbumName(String newName)
    {

        albums.get(currentAlbum).setAlbumName(newName);
    }

    /**
     * getCurrentAlbumName
     * 
     * Get the name of the Album pointed to by currentAlbum
     * 
     * @return name of the current album
     */
    public static String getCurrentAlbumName()
    {

        String name = getCurrentAlbum().getAlbumName();
        return name;
    }

    /**
     * getCurrentAlbumIndex
     * 
     * Get the index of teh current Album
     * 
     * @return currentAlbumIndex The index of the album pointed to as the
     *         current album
     */
    public static int getCurrentAlbumIndex()
    {

        return currentAlbum;
    }

    /**
     * addAlbum
     * 
     * Add new album with string albName and add a Photo to it with imageUri and
     * comment
     * 
     * @param albName
     *            The album name being constructed
     * @param imageUri
     *            The image Uri of the first photo in the Album
     * @param comment
     *            The comment of the first Photo in the Album
     */
    public static void addAlbum(String albName, Uri imageUri, String comment)
    {

        Album temp = new Album(albName);
        temp.addPhoto(new Photo(comment, imageUri));
        albums.add(0, temp);
        Controller.setCurrentAlbum(0);
    }

    /**
     * deleteAlbum
     * 
     * Remove the Album at the provided index
     * 
     * @param albumIndex
     *            The index of the Album to remove
     */
    public static void deleteAlbum(int albumIndex)
    {

        albums.get(albumIndex).deleteAll();
        albums.remove(albumIndex);
        Controller.setCurrentAlbum(-1);
    }

    /**
     * updateAlbum C
     * 
     * Changes the name of the Album at albumIndex with newName
     * 
     * @param albumIndex
     *            The index of the album being updated
     * @param newName
     *            The new name assigned to the album
     */
    public static void updateAlbum(int albumIndex, String newName)
    {

        Album temp = albums.get(albumIndex);
        temp.setAlbumName(newName);
        albums.set(albumIndex, temp);
    }

    /**
     * addAlarm
     * 
     * Add an alarm on the selected Album
     * 
     * @param alarmDay
     *            The first day to start the alarm
     * @param alarmHour
     *            The first hour to start the alarm
     * @param alarmMin
     *            The first min to start the alarm
     * @param alarmFrequency
     *            The frequency of the alarms repetition
     * @param sendingContext
     *            The context to generate the alarm with
     */
    public static void addAlarm(int alarmDay, int alarmHour, int alarmMin,
            int alarmFrequency, final Context sendingContext)
    {

        final Runnable codeToRun = new Runnable()
        {

            /**
             * The code to run once alarm is triggered
             */
            public void run()
            {

                Log.d("running", "sendnotification");
                sendNotification(sendingContext);

            }
        };

        int initialDelay = getInitialDelaySeconds(alarmDay, alarmHour, alarmMin);
        Log.d("Adding alarm with Initial Delay", "" + initialDelay);

        int repeatedDelay = getRepeatedDelaySeconds(alarmFrequency);
        getCurrentAlbum().setNotifyerHandle(codeToRun, initialDelay,
                repeatedDelay);

    }

    /**
     * removeAlarm
     * 
     * Remove the alarm from the Album
     */
    public static void removeAlarm()
    {

        ScheduledFuture notifyerHandle = getCurrentAlbum().getNotifyerHandler();
        if (notifyerHandle != null)
        {
            Log.d("NotifyerHandle Not", "Null");
            getCurrentAlbum().setNotifyerHandle(null, 0, 0);
        }
    }

    /**
     * sendNotification
     * 
     * Sends a status bar notification to remind the user to take a photo
     * 
     * @param alarmContext
     *            The Album context the alarm was created in
     */
    private static void sendNotification(Context alarmContext)
    {

        Log.d("Sending Notification with", alarmContext.toString());

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) alarmContext
                .getSystemService(ns);

        int icon = R.drawable.camera;
        CharSequence tickerText = "CT";
        long time = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, time);
        ;
        CharSequence contentTitle = "Cicatrix Tracker";
        String albumName = getCurrentAlbumName();
        CharSequence contentText = "Reminder: Take your " + albumName
                + " picture!";
        Intent notificationIntent = new Intent(alarmContext,
                PasswordActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(alarmContext,
                Controller.getCurrentAlbumIndex(), notificationIntent,
                268435456);
        notification.setLatestEventInfo(ctx, contentTitle, contentText,
                contentIntent);

        notificationManager.notify(currentAlbum, notification);
    }

    /**
     * getInitialDelaySeconds
     * 
     * Calculate the number of seconds to delay from the current time to initial
     * notification
     * 
     * @param day
     *            The first day of the week to start the notification
     * @param hour
     *            The first hour of the day to start the notification
     * @param min
     *            The first min of the hour to start the notification
     * @return delaySeconds The number of seconds to delay before starting the
     *         notification
     */
    private static int getInitialDelaySeconds(int day, int hour, int min)
    {

        Date today = Calendar.getInstance().getTime();

        // NOTE: SimpleDatFormat formats day from 1-7. currentDay should be
        // formatted 0-6
        SimpleDateFormat DAY = new SimpleDateFormat("dd");
        int currentDay = ((Integer.parseInt(DAY.format(today))) % 7 - 1);
        Log.d("day =", "" + day);
        Log.d("currentDay =", "" + currentDay);

        SimpleDateFormat HOUR = new SimpleDateFormat("HH");
        int currentHour = Integer.parseInt(HOUR.format(today));
        SimpleDateFormat MIN = new SimpleDateFormat("mm");
        int currentMin = Integer.parseInt(MIN.format(today));
        SimpleDateFormat SEC = new SimpleDateFormat("ss");
        int currentSec = Integer.parseInt(SEC.format(today));

        int secInDay = ((day + 7) % 7 - currentDay) * SECONDSPERDAY;
        int secInHour = ((hour + 24) % 24 - currentHour) * SECONDSPERHOUR;
        int secInMin = ((min + 60) % 60 - currentMin) * SECONDSPERMIN;
        int delaySeconds = secInDay + secInHour + secInMin - currentSec;

        return delaySeconds;
    }

    /**
     * getRepeatedDelaySeconds
     * 
     * Convert the week, day, or hour into seconds
     * 
     * @param alarmFrequencyCode
     *            The code indicating which alarm frequency was chosen. 0=WEEK,
     *            1=DAY, 2=HOUR;
     * @return delaySeconds The number of seconds to delay before repetition
     */
    private static int getRepeatedDelaySeconds(int alarmFrequencyCode)
    {

        int delaySeconds = 0;
        if (alarmFrequencyCode == 0)
        {
            delaySeconds = (SECONDSPERDAY * 7);
        } else if (alarmFrequencyCode == 1)
        {
            delaySeconds = (SECONDSPERDAY);
        } else
        /* (alarmFrequency == 2) */{
            delaySeconds = (SECONDSPERHOUR);
        }

        return delaySeconds;
    }

    /**
     * getPhoto
     * 
     * Return the Photo at the given album index and photo index
     * 
     * @param albumIndex
     *            The album index of the desired Photo
     * @param photoIndex
     *            The photo index of the desired Photo
     * @return The photo at the given album index and photo index
     */
    public static Photo getPhoto(int albumIndex, int photoIndex)
    {

        Log.e("getting photo", "get " + photoIndex + " photo from album "
                + albumIndex);
        return albums.get(albumIndex).getPhoto(photoIndex);
    }

    /**
     * addPhoto
     * 
     * Add a Photo with Uri imageUri and String comment to the album at
     * albumIndex
     * 
     * @param albumIndex
     *            The index of the album to add the Photo to
     * @param imageUri
     *            The Uri the Bitmap of the Photo is stored at
     * @param comment
     *            The comment of the Photo
     */
    public static void addPhoto(int albumIndex, Uri imageUri, String comment)
    {

        Album temp = albums.get(albumIndex);
        temp.addPhoto(new Photo(comment, imageUri));
        setCurrentPhoto(temp.size());
        setCurrentAlbum(albumIndex);
        albums.set(albumIndex, temp);
    }

    /**
     * deletePhoto
     * 
     * Delete photo at photoIndex from album at albumIndex
     * 
     * @param albumIndex
     *            The Album index from which to delete the photo
     * @param photoIndex
     *            The Photo index to be deleted
     */
    public static void deletePhoto(int albumIndex, int photoIndex)
    {

        Album temp = albums.get(albumIndex);

        temp.deletePhoto(photoIndex);

        if (temp.size() == 0)
        {
            deleteAlbum(albumIndex);
        } else
            albums.set(albumIndex, temp);

    }

    /**
     * updatePhoto
     * 
     * Update the indicated Photo with a new comment
     * 
     * @param albumIndex
     *            The index of the album holding the Photo to update
     * @param photoIndex
     *            The index of the Photo to update
     * @param newComment
     *            The new comment to add to the Photo
     */
    public static void updatePhoto(int albumIndex, int photoIndex,
            String newComment)
    {

        Album tempAlbum = albums.get(albumIndex);

        Photo tempPhoto = tempAlbum.getPhoto(photoIndex);
        tempPhoto.setComment(newComment);
        tempAlbum.updatePhoto(photoIndex, tempPhoto);
        albums.set(albumIndex, tempAlbum);
    }

    /**
     * getAlbumNames
     * 
     * Return a String[] of all Album names
     * 
     * @return albNames A String[] of all the album names
     */
    public static String[] getAlbumNames()
    {

        if (albums.size() == 0)
            return new String[] {};

        String[] albNames = new String[albums.size()];

        for (int i = 0; i < albums.size(); i++)
        {
            albNames[i] = albums.get(i).getAlbumName();
        }
        return albNames;
    }

    /**
     * checkAlbumNames
     * 
     * Given a name, returns the albumIndex that represents the Album with that
     * name or returns -1 if no album exists.
     * 
     * @param nameToCheck
     *            The name of the album to check
     * @return index of album if exists, else returns -1
     */
    public static int checkAlbumNames(String nameToCheck)
    {

        int i = 0;
        while (i < albums.size())
        {
            String name = albums.get(i).getAlbumName();
            if (name.equalsIgnoreCase(nameToCheck.trim()))
            {
                return i;
            }
            i++;
        }
        return -1;

    }

    /**
     * saveObject
     * 
     * Saves the ArrayList albums to file and sets the context
     */
    public static void saveObject()
    {

        FileOutputStream stream = null;
        try
        {
            
            stream = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(stream);
            out.writeObject(albums);
            out.flush();
            stream.getFD().sync();
            stream.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Controller.savePassword(ctx);
    }

    /**
     * savePassword
     * 
     * Saves the password to a file and sets the Context ctx
     */
    public static void savePassword(Context c)
    {

        if (c != null)
        {
            ctx = c;
        }
        try
        {
            FileOutputStream stream = ctx.openFileOutput(passwordFileName,
                    Context.MODE_PRIVATE);

            ObjectOutputStream out = new ObjectOutputStream(stream);
            out.writeObject(password);
            out.flush();
            stream.getFD().sync();
            stream.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * loadObject
     * 
     * Loads the ArrayList albums from file
     */
    @SuppressWarnings("unchecked")
    public static void loadObject()
    {

        try
        {
            FileInputStream stream = ctx.openFileInput(fileName);
            if (stream == null)
            {
                return;
            }

            ObjectInputStream in = new ObjectInputStream(stream);
            ArrayList<Album> list;
            Object temp = in.readObject();
            if (temp != null)
            {
                list = (ArrayList<Album>) temp;
                stream.close();
                albums = list;
            }

        } catch (FileNotFoundException e)
        {
        } catch (OptionalDataException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Controller.loadPassword(ctx);
    }

    /**
     * loadPassword
     * 
     * Load the password from the file and set the context
     * 
     * @param c
     *            The context the password was saved in
     */
    public static void loadPassword(Context c)
    {

        try
        {
            ctx = c;
            FileInputStream stream = c.openFileInput(passwordFileName);
            if (stream == null)
            {
                return;
            }

            ObjectInputStream in = new ObjectInputStream(stream);
            Object temp = in.readObject();
            if (temp != null)
            {
                String tt = (String) temp;
                stream.close();
                Controller.setPassword(tt);
            }

        } catch (FileNotFoundException e)
        {
        } catch (OptionalDataException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * inTags
     * 
     * Given a tag to check, determines if it is one of the given tags
     * 
     * @param tagToCheck
     *            The tag to check
     * @return inTag True if the tag is known. False if the tag is not known.
     */
    public static boolean inTags(String tagToCheck)
    {

        boolean inTag = false;
        int i = 0;
        while (i < tags.size() && !inTag)
        {
            inTag = (tags.get(i).equalsIgnoreCase(tagToCheck));
            i++;
        }

        return inTag;
    }

    /**
     * setTags
     * 
     * Initialize the Controller with the known tags
     */
    public static void setTags()
    {

        tags.add("right");
        tags.add("left");
        tags.add("upper");
        tags.add("lower");
        tags.add("rash");
        tags.add("mold");
        tags.add("red");
        tags.add("boil");
        tags.add("scar");
        tags.add("scab");
        tags.add("forehead");
        tags.add("arm");
        tags.add("wrist");
        tags.add("ear");
        tags.add("cheek");
        tags.add("face");
        tags.add("neck");
        tags.add("back");
        tags.add("front");
        tags.add("chest");
        tags.add("armpit");
        tags.add("elbow");
        tags.add("hand");
        tags.add("thumb");
        tags.add("palm");
        tags.add("sweat");
        tags.add("sweaty");
        tags.add("itch");
        tags.add("itchy");
        tags.add("bruise");
        tags.add("bruised");
        tags.add("zit");
        tags.add("whitehead");
        tags.add("blackhead");
        tags.add("bum");
        tags.add("dry");
        tags.add("hair");
        tags.add("waist");
        tags.add("butt");
        tags.add("crotch");
        tags.add("knee");
        tags.add("shin");
        tags.add("foot");
        tags.add("toe");
        tags.add("top");
        tags.add("crack");
        tags.add("throb");
        tags.add("bump");
        tags.add("lump");
        tags.add("spot");
        tags.add("mark");
        tags.add("blue");
        tags.add("black");
        tags.add("yellow");
        tags.add("green");
        tags.add("white");
        tags.add("puss");
        tags.add("orange");
    }

    /**
     * findPhotos
     * 
     * given a tag to find, searches through all Photos and takes the Uris of
     * the Photos that have that tag. The Uris and their corresponding Album and
     * Photo indices are placed in a SearchItem and added to the returned
     * ArrayList
     * 
     * @param tagToFind
     *            The tag to find
     * @return tagged The ArrayList<SearchItem> with matching tags
     */
    public static ArrayList<SearchItem> findPhotos(String tagToFind)
    {

        ArrayList<SearchItem> tagged = new ArrayList<SearchItem>();
        for (int i = 0; i < albums.size(); i++)
        {
            for (int j = 0; j < albums.get(i).size(); j++)
            {
                if (albums.get(i).getPhoto(j).hasTag(tagToFind))
                    tagged.add(new SearchItem(i, j, albums.get(i).getPhoto(j)
                            .getPicture()));

            }
        }

        return tagged;

    }

}
