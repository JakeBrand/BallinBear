package model;
import java.util.ArrayList;




public class Album{
    private static String albumName;
    private ArrayList<Photo> photos;
    
    
    // Constructor only needs name
    public Album(String albumName) {
        this.albumName = albumName;
        this.photos = new ArrayList<Photo>();
    }
    
    
    
    public static String getAlbumName() {
        return albumName;
    }
    
    public void setAlbumName(String albumName)  {
        Album.albumName = albumName;
    }
    
    public ArrayList<Photo> getPhotos() {
        return photos;
    }
    
    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }
    

}
