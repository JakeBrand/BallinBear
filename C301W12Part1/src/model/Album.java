package model;
import java.io.Serializable;
import java.util.ArrayList;




public class Album implements Serializable{
    private  String albumName;
    private ArrayList<Photo> photos;
    
    
    // Constructor only needs name
    public Album(String albumName) {
        this.albumName = albumName;
        this.photos = new ArrayList<Photo>();
    }
    
    public void deleteAll(){
        for(int i = 0; i < size(); i++){
            deletePhoto(i);
        }
    }
    
    
    public int size(){
        return photos.size();
    }
    
    public  String getAlbumName() {
        return albumName;
    }
    
    public void setAlbumName(String albumName)  {
        this.albumName = albumName;
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
        photos.add(p);
    }
    
    public void deletePhoto(int i){
        
        photos.remove(i);
    }
    
    public void updatePhoto(int i, Photo p){
        photos.set(i, p);
    }

}
