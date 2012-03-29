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
    private static ArrayList<Album>  albums         = new ArrayList<Album>();
    private static Context           ctx;
    private static final String      fileName       = "albumsfile.data";
    private static final String      passwordFileName       = "ps.data";
    
    private static ArrayList<String> tags           = new ArrayList<String>();

    /**
     * Final variables needed to set an alarm
     */
    private static final int         SECONDSPERMIN  = 60;
    private static final int         SECONDSPERHOUR = SECONDSPERMIN * 60;
    private static final int         SECONDSPERDAY  = SECONDSPERHOUR * 24;
//    private static final int         CICATRIX_ID      = 1;

    /**
     * getAlbum
     * 
     * @param albumIndex
     * @return the Album at index albumIndex
     */
    public static Album getAlbum(int albumIndex)
    {

        return albums.get(albumIndex);
    }

    /**
     * setCurrentPhoto
     * 
     * @param currentP
     * @uml.property name="currentPhoto"
     */
    public static void setCurrentPhoto(int currentP)
    {

        currentPhoto = currentP;
    }

    /**
     * getCurrentPhotoIndex
     * 
     * @return currentPhoto
     */
    public static int getCurrentPhotoIndex()
    {

        return currentPhoto;
    }

    /**
     * getCurrentPhoto
     * 
     * @return Photo in the currentAlbum at the currentPhotoIndex
     * @uml.property name="currentPhoto"
     */
    public static Photo getCurrentPhoto()
    {

        return albums.get(currentAlbum).getPhoto(currentPhoto);
    }

    /**
     * setComparePhoto1
     * 
     * @param photo1
     * @uml.property name="comparePhoto1"
     */
    public static void setComparePhoto1(int photo1)
    {

        comparePhoto1 = photo1;
    }

    /**
     * setComparePhoto2
     * 
     * @param photo2
     * @uml.property name="comparePhoto2"
     */
    public static void setComparePhoto2(int photo2)
    {

        comparePhoto2 = photo2;
    }

    /**
     * getComparePhoto1
     * 
     * @return Photo in currentAlbum and comparePhoto1 index
     * @uml.property name="comparePhoto1"
     */
    public static Photo getComparePhoto1()
    {

        return albums.get(currentAlbum).getPhoto(comparePhoto1);
    }

    /**
     * getComparePhoto2
     * 
     * @return Photo in currentAlbum and comparePhoto2 index
     * @uml.property name="comparePhoto2"
     */
    public static Photo getComparePhoto2()
    {

        return albums.get(currentAlbum).getPhoto(comparePhoto2);
    }

    /**
     * getCurrentAlbum
     * 
     * @return Album at currentAlbum index
     * @uml.property name="currentAlbum"
     */
    public static Album getCurrentAlbum()
    {

        return albums.get(currentAlbum);
    }

    /**
     * setCurrentAlbum
     * 
     * @param newCurrentAlbum
     * @uml.property name="currentAlbum"
     */
    public static void setCurrentAlbum(int newCurrentAlbum)
    {

        currentAlbum = newCurrentAlbum;
    }

    /**
     * setCurrentAlbumName
     * 
     * @param newName
     */
    public static void setCurrentAlbumName(String newName)
    {

        albums.get(currentAlbum).setAlbumName(newName);
    }

    /**
     * getCurrentAlbumName
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
     * @return currentAlbumIndex
     */
    public static int getCurrentAlbumIndex()
    {

        return currentAlbum;
    }

    /**
     * addAlbum add new album with string albName and add a Photo to it with
     * imageUri and comment
     * 
     * @param albName
     * @param imageUri
     * @param comment
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
     * @param albumIndex
     */
    public static void deleteAlbum(int albumIndex)
    {

        Log.e("Deleting Album", "album " + albumIndex);
        albums.get(albumIndex).deleteAll();
        albums.remove(albumIndex);
        Controller.setCurrentAlbum(-1);
    }

    /**
     * updateAlbum changes the name of the Album at albumIndex with newName
     * 
     * @param albumIndex
     * @param newName
     */
    public static void updateAlbum(int albumIndex, String newName)
    {

        Album temp = albums.get(albumIndex);
        temp.setAlbumName(newName);
        albums.set(albumIndex, temp);
    }

    /**
     * 
     * 
     */
    public static void addAlarm(int alarmDay, int alarmHour, int alarmMin,
            int alarmFrequency, final Context sendingContext)
    {
        final Runnable codeToRun = new Runnable()
        {

            public void run()
            {
                Log.d("running", "sendnotification");
                sendNotification(sendingContext);

            }
        };

        int initialDelay = getInitialDelaySeconds(alarmDay, alarmHour, alarmMin);
        Log.d("Adding alarm with Initial Delay", "" + initialDelay);

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
        getCurrentAlbum().setNotifyerHandle(codeToRun, initialDelay, repeatedDelay);
        
    }
    
    public static void removeAlarm(){
        ScheduledFuture notifyerHandle = getCurrentAlbum().getNotifyerHandler();
        if(notifyerHandle!= null){
            Log.d("NotifyerHandle Not", "Null");
            getCurrentAlbum().setNotifyerHandle(null,0,0);
        }
    }
    
    /**
     * sendNotification
     * 
     * Sends a status bar notification to remind the user
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
     *            The day of the week to start the initial notification
     * @param hour
     *            The hour to start the initial notification
     * @param min
     *            The min to start the initial notification
     * @return delaySeconds The number of seconds to delay between current time
     *         and
     */
    private static int getInitialDelaySeconds(int day, int hour, int min)
    {

        Date today = Calendar.getInstance().getTime();

        SimpleDateFormat DAY = new SimpleDateFormat("dd");
        int currentDay = (Integer.parseInt(DAY.format(today))) % 7;
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
     * @param week
     *            The number of weeks to convert to seconds
     * @param day
     *            The number of days to convert to seconds
     * @param hour
     *            The number of hours to convert to seconds
     * @return delaySeconds The number of seconds to delay before repetition
     */
    private static int getRepeatedDelaySeconds(int week, int day, int hour)
    {

        // TODO: Make Controller do this!
        int secInWeek = (SECONDSPERDAY * 7 * week);
        int secInDay = (SECONDSPERDAY * day);
        int secInHour = (SECONDSPERHOUR * hour);
        int delaySeconds = secInWeek + secInDay + secInHour;

        return delaySeconds;
    }

    /**
     * getPhoto
     * 
     * @param albumIndex
     * @param photoIndex
     * @return a_photo
     */
    public static Photo getPhoto(int albumIndex, int photoIndex)
    {

        Log.e("getting photo", "get " + photoIndex + " photo from album "
                + albumIndex);
        return albums.get(albumIndex).getPhoto(photoIndex);
    }

    /**
     * addPhoto add a Photo with Uri imageUri and String comment to the album at
     * albumIndex
     * 
     * @param albumIndex
     * @param imageUri
     * @param comment
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
     * deletePhoto delete photo at photoIndex from album at albumIndex
     * 
     * @param albumIndex
     * @param photoIndex
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
     * @param albumIndex
     * @param photoIndex
     * @param newComment
     */
    public static void updatePhoto(int albumIndex, int photoIndex,
            String newComment)
    {

        Log.e("updatePhoto", "update photo " + photoIndex + " in album "
                + albumIndex + " with comment " + newComment);
        Album tempAlbum = albums.get(albumIndex);

        Photo tempPhoto = tempAlbum.getPhoto(photoIndex);
        tempPhoto.setComment(newComment);
        tempAlbum.updatePhoto(photoIndex, tempPhoto);
        albums.set(albumIndex, tempAlbum);
    }

    /**
     * getAlbumNames
     * 
     * 
     * @return String[] of all the album names in Controller
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
     * checkAlbumNames Given a String s, returns the albumIndex that represents
     * the Album with that name or returns -1 if no album exists.
     * 
     * @param s
     *            The name of the album to check
     * @return index of album if exists, else returns -1
     */
    public static int checkAlbumNames(String s)
    {

        int i = 0;
        while (i < albums.size())
        {
            String name = albums.get(i).getAlbumName();
            if (name.equals(s.trim()))
            {
                return i;
            }
            i++;
        }
        return -1;

    }

    /**
     * saveObject saves the ArrayList albums to file
     * 
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
        Controller.savePassword();
    }
    
    public static void savePassword()
    {

        FileOutputStream stream = null;
        try
        {
            stream = ctx.openFileOutput(passwordFileName, Context.MODE_PRIVATE);
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
     * loadObject loads the ArrayList albums from file
     * 
     * @param c
     */
    @SuppressWarnings("unchecked")
    public static void loadObject(Context c)
    {

        ctx = c;
        FileInputStream stream = null;

        try
        {
            stream = c.openFileInput(fileName);
            if (stream == null)
            {
                return;
            }

            ObjectInputStream in = new ObjectInputStream(stream);
            ArrayList<Album> list;
            Object temp;
            temp = in.readObject();
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
        Controller.loadPassword(c);
    }
    
    public static void loadPassword(Context c)
    {

        FileInputStream stream = null;

        try
        {
            stream = c.openFileInput(passwordFileName);
            if (stream == null)
            {
                return;
            }

            ObjectInputStream in = new ObjectInputStream(stream);
            Object temp;
            temp = in.readObject();
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
     * given a String s, determines if s is one of the given tags
     * 
     * @param s
     * @return inTag
     */
    public static boolean inTags(String s)
    {

        Log.e("In tags", s);
        boolean inTag = false;
        int i = 0;
        while (i < tags.size() && !inTag)
        {
            inTag = (tags.get(i).equals(s));
            Log.e(null, "tag: " + tags.get(i) + " is it equal?: " + inTag);
            i++;
        }

        return inTag;
    }

    /**
     * setTags
     * 
     * updates the Controller with the givens tags
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
     * given a tag (s), searches through all Photos and takes the Uris of the
     * Photos that have that tag. The Uris and their coorosponding Album and
     * Photo indices are placed in a SearchItem and added to the returned
     * ArrayList
     * 
     * @param s
     * @return tagged
     */
    public static ArrayList<SearchItem> findPhotos(String s)
    {

        ArrayList<SearchItem> tagged = new ArrayList<SearchItem>();
        for (int i = 0; i < albums.size(); i++)
        {
            for (int j = 0; j < albums.get(i).size(); j++)
            {
                if (albums.get(i).getPhoto(j).hasTag(s))
                    tagged.add(new SearchItem(i, j, albums.get(i).getPhoto(j)
                            .getPicture()));

            }
        }

        return tagged;

    }

}
