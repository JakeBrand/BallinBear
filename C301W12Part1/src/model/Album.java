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

    private static final long        serialVersionUID = 2277251080502459333L;

    /**
     * album name and the list of photos are fields
     */
    private String                   albumName;
    private ArrayList<Photo>         photos;
    /**
     * Attributes for Album's alarm
     */
    private static ScheduledExecutorService scheduler  =  Executors.newScheduledThreadPool(1);
    private ScheduledFuture         notifyerHandle;

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
     * @return albumName The current name of the Album
     */
    public String getAlbumName()
    {

        return albumName;
    }

    /**
     * setAlbumName
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
     * @return photos The ArrayList<Photo> in this album
     */
    public ArrayList<Photo> getPhotos()
    {

        return photos;
    }

    /**
     * setPhotos
     * 
     * @param photos
     */
    public void setPhotos(ArrayList<Photo> photos)
    {

        this.photos = photos;
    }

    /**
     * getPhoto
     * 
     * @param photoIndex
     * @return Photo at photoIndex in photos
     */
    public Photo getPhoto(int photoIndex)
    {

        return photos.get(photoIndex);
    }

    /**
     * addPhoto
     * 
     * @param p
     */
    public void addPhoto(Photo p)
    {

        photos.add(p);
    }

    /**
     * deletePhoto
     * 
     * @param photoIndex
     */
    public void deletePhoto(int photoIndex)
    {

        photos.remove(photoIndex);
    }

    /**
     * updatePhoto
     * 
     * @param photoIndex
     * @param photo
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
    
    public ScheduledExecutorService geScheduler(){
        return scheduler;
    }
    
    public void setNotifyerHandle(Runnable codeToRun,
            int initialDelay, int repeatedDelay){
        if(codeToRun == null){
            if(notifyerHandle != null){
                notifyerHandle.cancel(true);
                notifyerHandle = null;
                return;
            }
        }
        notifyerHandle = scheduler.scheduleWithFixedDelay(codeToRun,
                initialDelay, repeatedDelay, SECONDS);
    }
    
    public ScheduledFuture getNotifyerHandler(){
        return notifyerHandle;
    }

}
