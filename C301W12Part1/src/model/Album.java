package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author J-Tesseract
 */
public class Album implements Serializable
{

    private static final long serialVersionUID = 2277251080502459333L;

    /**
     * Album name and the list of photos are fields
     */
    private String            albumName;
    private ArrayList<Photo>  photos;

    /**
     * Contructor
     * 
     * @param albumName
     *            The name of the album being created
     */
    public Album(String albumName)
    {

        this.albumName = albumName;
        this.photos = new ArrayList<Photo>();
    }

    /**
     * deleteAll Deletes all Photos in album
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
     * @return size of the array of Photos
     */
    public int size()
    {

        return photos.size();
    }

    /**
     * getAlbumName
     * 
     * @return albumName The name of the Album
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
     * @return photos An ArrayList<Photo> of Photos in the album
     */
    public ArrayList<Photo> getPhotos()
    {

        return photos;
    }

    /**
     * setPhotos
     * 
     * @param photos
     *            The new ArrayList<Photo> for the album
     */
    public void setPhotos(ArrayList<Photo> photos)
    {

        this.photos = photos;
    }

    /**
     * getPhoto
     * 
     * @param photoIndex
     *            The index of the Photo in the Album to return
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
     *            The new Photo to add to the album
     */
    public void addPhoto(Photo p)
    {

        photos.add(p);
    }

    /**
     * deletePhoto
     * 
     * @param photoIndex
     *            The index of the Photo to be deleted
     */
    public void deletePhoto(int photoIndex)
    {

        photos.remove(photoIndex);
    }

    /**
     * updatePhoto
     * 
     * @param photoIndex
     *            The index of the Photo to be updated
     * @param photo
     *            The new Photo to be updated
     */
    public void updatePhoto(int photoIndex, Photo photo)
    {

        photos.set(photoIndex, photo);
    }

}
