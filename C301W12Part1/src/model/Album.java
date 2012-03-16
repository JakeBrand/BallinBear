package model;
import java.io.Serializable;
import java.util.ArrayList;




public class Album implements Serializable{
    
    

    private static final long serialVersionUID = 2277251080502459333L;
    
    /**
     * album name and the list of photos are fields
     */
    private  String albumName;
    private ArrayList<Photo> photos;
    
    
    /**
     * Contructor
     * 
     * @param albumName
     */
    public Album(String albumName) {
        this.albumName = albumName;
        this.photos = new ArrayList<Photo>();
    }
    
    
    /**
     * deleteAll
     * deletes all Photos in album
     */
    public void deleteAll(){
        for(int i = 0; i < size(); i++){
            deletePhoto(i);
        }
    }
    
    
    /**
     * size 
     * @return size of photos
     */
    public int size(){
        return photos.size();
    }
    
    
    /**
     * getAlbumName
     * 
     * @return albumName
     */
    public  String getAlbumName() {
        return albumName;
    }
    
    /**
     * setAlbumName
     * 
     * @param albumName
     */
    public void setAlbumName(String albumName)  {
        this.albumName = albumName;
    }
    
    
    /**
     * getPhotos
     * 
     * @return
     */
    public ArrayList<Photo> getPhotos() {
        return photos;
    }
    
    /**
     * setPhotos
     * 
     * @param photos
     */
    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }
    
    /**
     * getPhoto
     * 
     * @param photoIndex
     * @return Photo at photoIndex in photos
     */
    public Photo getPhoto(int photoIndex){
        return photos.get(photoIndex);
    }
    
    
    /**
     * addPhoto
     * 
     * @param p
     */
    public void addPhoto(Photo p){
        photos.add(p);
    }
    
    /**
     * deletePhoto
     * 
     * @param photoIndex
     */
    public void deletePhoto(int photoIndex){
        photos.remove(photoIndex);
    }
    
    
    /**
     * updatePhoto
     * 
     * @param photoIndex
     * @param photo
     */
    public void updatePhoto(int photoIndex, Photo photo){
        photos.set(photoIndex, photo);
    }

}
