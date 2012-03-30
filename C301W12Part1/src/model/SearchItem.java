package model;

import android.net.Uri;

/**
 * SearchItem
 * 
 * A SeachItem is used to connect searched-for pictures with their Photos, Albums, and Uris
 * 
 * It is only ever created in the SearchActivity
 * 
 * @author J-Tesseract
 *
 */
public class SearchItem
{
    /**
     * Private attributes of a SearchItem
     */
    private int albumIndex;
    private int photoIndex;
    private Uri pictureUri;
    
    /**
     * Constructor
     * 
     * @param albI Album index
     * @param photoI Photo index in Album
     * @param pic The Uri of the Photo
     */
    public SearchItem(int albI, int photoI, Uri pic){
        this.albumIndex = albI;
        this.photoIndex = photoI;
        this.pictureUri = pic;
    }


    /**
     * getAlbumIndex
     * 
     * Return the album index of the SearchItem
     * 
     * @return albumIndex The album index of the SearchItem
     */
    public int getAlbumIndex()
    {
    
        return albumIndex;
    }


    /**
     * getPhotoIndex
     * 
     * Return the Photo index in the Album of the SearchItem
     * @return photoIndex The Photo index in the Album of the SearchItem
     */
    public int getPhotoIndex()
    {
    
        return photoIndex;
    }


    /**
     * getPictureUri
     * 
     * Return the Uri of the Photo at the Photo index
     * @return pictureUri The Uri of the Photo at the Photo index
     */
    public Uri getPictureUri()
    {
    
        return pictureUri;
    }
    
    
}
