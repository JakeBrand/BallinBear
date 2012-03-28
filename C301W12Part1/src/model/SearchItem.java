package model;

import android.net.Uri;

/**
 * SearchItem
 * 
 * A SeachItem is used to connect searched-for pictures with their Photos, Albums, and Uris
 * 
 * It is only ever created in the SearchActivity
 *
 */
public class SearchItem
{
    private int albumIndex;
    private int photoIndex;
    private Uri pictureUri;
    
    
    public SearchItem(int albI, int photoI, Uri pic){
        this.albumIndex = albI;
        this.photoIndex = photoI;
        this.pictureUri = pic;
    }


    
    public int getAlbumIndex()
    {
    
        return albumIndex;
    }


    
    public int getPhotoIndex()
    {
    
        return photoIndex;
    }


    
    public Uri getPictureUri()
    {
    
        return pictureUri;
    }
    
    
}
