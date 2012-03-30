package model;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * @author J-Tesseract
 */
public class Album implements Serializable
{

    private static final long               serialVersionUID = 2277251080502459333L;

    /**
     * Album name and the list of photos are fields
     */
    private String                          albumName;
    private ArrayList<Photo>                photos;
    /**
     * Attributes for Album's alarm
     */
    private static ScheduledExecutorService scheduler        = Executors
                                                                     .newScheduledThreadPool(1);
    private ScheduledFuture                 notifyerHandle;

    /**
     * Contructor
     * 
     * @param albumName
     *            The name of the album being constructed
     */
    public Album(String albumName)
    {

        this.albumName = albumName;
        this.photos = new ArrayList<Photo>();
    }

    /**
     * deleteAll
     * 
     * Deletes all Photos in this album
     */
    public void deleteAll()
    {

        for (int i = size() - 1; i >= 0; i--)
        {
            deletePhoto(i);
        }
    }

    /**
     * size
     * 
     * Determine the size of the array holding this Album's Photos
     * 
     * @return Size of array of Photos
     */
    public int size()
    {

        return photos.size();
    }

    /**
     * getAlbumName
     * 
     * Returns the current name of the Album
     * 
     * @return albumName The current name of the Album
     */
    public String getAlbumName()
    {

        return albumName;
    }

    /**
     * setAlbumName
     * 
     * Changes the name of the Album
     * 
     * @param albumName
     *            The new name of the Album
     */
    public void setAlbumName(String albumName)
    {

        this.albumName = albumName;
    }

    /**
     * getPhotos
     * 
     * Returns the ArrayList<Photo> in this album
     * 
     * @return photos The ArrayList<Photo> in this album
     */
    public ArrayList<Photo> getPhotos()
    {

        return photos;
    }

    /**
     * setPhotos
     * 
     * Change the ArrayList<Photo> in this Album
     * 
     * @param photos
     *            The new ArrayList<Photo> to set for this Album
     */
    public void setPhotos(ArrayList<Photo> photos)
    {

        this.photos = photos;
    }

    /**
     * getPhoto
     * 
     * Get the Photo of this album
     * 
     * @param photoIndex
     *            The index of the photo in the Album
     * @return Photo at photoIndex in photos
     */
    public Photo getPhoto(int photoIndex)
    {

        return photos.get(photoIndex);
    }

    /**
     * addPhoto
     * 
     * Add the provided Photo to the Album
     * 
     * @param p
     *            The provided Photo to add to the Album
     */
    public void addPhoto(Photo p)
    {

        photos.add(p);
    }

    /**
     * deletePhoto
     * 
     * Remove a specified Photo from the Album
     * 
     * @param photoIndex
     *            The index of the Photo to be removed
     */
    public void deletePhoto(int photoIndex)
    {

        photos.remove(photoIndex);
    }

    /**
     * updatePhoto
     * 
     * Update the Bitmap and/or Comment of the provided Photo
     * 
     * @param photoIndex
     *            The index of the Photo to update
     * @param photo
     *            The new Photo to update to
     */
    public void updatePhoto(int photoIndex, Photo photo)
    {

        photos.set(photoIndex, photo);
        // if(photo.getPicture().equals(photos.get(photoIndex).getPicture()))
        // photos.set(photoIndex, photo);
        // else{
        // photos.remove(photoIndex);
        // photos.add(photo);
        // }
    }

    /**
     * getScheduler
     * 
     * Return the Album's ScheduledExecutorService for setting up the alarm
     * 
     * @return scheduler The Album's ScheduledExecutorService
     */
    public ScheduledExecutorService geScheduler()
    {

        return scheduler;
    }

    /**
     * setNotifyerHandle
     * 
     * Set the ScheduledFuture "notifyerHandle" with provided parameters
     * 
     * @param codeToRun
     *            The code to run when schedule is met
     * @param initialDelay
     *            The delay (in Seconds) from now to the first notification
     * @param repeatedDelay
     *            The delay (in Seconds) from the first notification to the
     *            repeated notification
     */
    public void setNotifyerHandle(Runnable codeToRun, int initialDelay,
            int repeatedDelay)
    {

        if (codeToRun == null)
        {
            if (notifyerHandle != null)
            {
                notifyerHandle.cancel(true);
                notifyerHandle = null;
                return;
            }
        }
        notifyerHandle = scheduler.scheduleWithFixedDelay(codeToRun,
                initialDelay, repeatedDelay, SECONDS);
    }

    /**
     * getNotifyerHandler
     * 
     * Return the ScheduledFuture "notifyerHandle"
     * 
     * @return notifyerHandle The ScheduledFuture of this Album
     */
    public ScheduledFuture getNotifyerHandler()
    {

        return notifyerHandle;
    }

}
