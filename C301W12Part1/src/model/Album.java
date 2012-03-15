package model;
import java.util.ArrayList;




public class Album{
    private  String albumName;
    private ArrayList<Photo> photos;
    
    
    // Constructor only needs name
    public Album(String albumName) {
        this.albumName = albumName;
        this.photos = new ArrayList<Photo>();
    }
    
    
    
    public  String getAlbumName() {
        return albumName;
    }
    
    public void setAlbumName(String newAlbumName)  {
        this.albumName = newAlbumName;
    }
    
    public ArrayList<Photo> getPhotos() {
        return photos;
    }
    
    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }
    
    
    public Photo getPhoto(int i){
        return photos.get(i);
    }
    
    public void addPhoto(Photo p){
        this.photos.add(p);
    }
    
    public void deletePhoto(int i){
        this.photos.remove(i);
    }
    
    public void updatePhoto(int i, Photo p){
        this.photos.set(i, p);
    }

}
