package control;

import model.Album;


public class ControlState{
    
//    boolean COMPAREABLE = false;
//    boolean SLIDESHOW = false;
//    boolean ARE_ALBUMS = false;
    
    public ControlState(){
        
    }
    
    
    public boolean compareable(Album a){
        return (a.getPhotos().size() > 1);
    }
    
    
    public boolean slideshow(Album a){
        return (a.getPhotos().size() > 2);
    }
    

    public boolean showAlbums(){
        return true; // balls
        
     //   return (there is at least one valid album);
    }
    
    
}
