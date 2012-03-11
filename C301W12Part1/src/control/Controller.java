package control;

import java.util.ArrayList;


import android.graphics.Bitmap;
import android.net.Uri;

import model.Album;
import model.Photo;

public class Controller
{

    ArrayList<Album> albums;

    
    public Controller(ArrayList<Album> albs)
    {

        albums = albs;
    }

    
    // get album i
    public Album getAlbum(int i)
    {

        return albums.get(i);

    }

 // add album i with name s with its first photo p
    public void addAlbum(String albName, Uri imageUri, String comment){
        
        Album temp = new Album(albName);
        temp.addPhoto(new Photo(comment,imageUri));
        albums.add(temp);

    } 

 // delete album i
    public void deleteAlbum(int i)
    {
        albums.remove(i);
    } 

 // change the name of album i to s
    public void updateAlbum(int i, String s)
    {   Album temp = albums.get(i);
        temp.setAlbumName(s);
        albums.set(i, temp);
    } 

 // get photo j from album i
    public Photo getPhoto(int i, int j){
      return  albums.get(i).getPhoto(j);
    } 

 // add photo p to i
    public void addPhoto(int i, Photo p)
    {
        Album temp = albums.get(i);
        temp.addPhoto(p);
        albums.set(i, temp);
    } 

 // delete photo j
    public void deletePhoto(int i, int j)
    {
        Album temp = albums.get(i);
        temp.deletePhoto(j);
        albums.set(i, temp);
    } 

 // update the comments on this photo
    public void updatePhoto(int i, int j, String s)
    {
        Album tempAlbum = albums.get(i);
        Photo tempPhoto = tempAlbum.getPhoto(j);
        tempPhoto.setComment(s);
        tempAlbum.updatePhoto(j, tempPhoto);
        albums.set(i, tempAlbum);
    } 

    
    
    
}
