package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import model.Album;
import model.Photo;
// Controller is a static class and can be accessed from both model and view
public class Controller {
    
    private static int currentAlbum;
    private static int currentPhoto;
    private static int comparePhoto1;
    private static int comparePhoto2;
    
    private static final int ALBUM_EDIT = 1;
    private static final int ALBUM_LIST = 2;
    private static final int COMPARE = 3;
    private static final int EDIT_PHOTO = 4;
    private static final int GALLERY = 5;
    private static final int SLIDESHOW = 6;
    private static final int WELCOME = 7;
    

    static ArrayList<Album> albums = new ArrayList<Album>();
    private static int previousAlbum;
    private static final String fileName = "albumsfile.data";
    

    
    // get album i
    public static Album getAlbum(int i)
    {
        Log.e("getting Album", "album " + albums.get(i).getAlbumName());
        return albums.get(i);

    }
    
    public static void setCurrentPhoto(int currentP){
        currentPhoto = currentP;
    }
    
    public static int getCurrentPhotoIndex(){
        return currentPhoto;
    }
    
    
    public static Photo getCurrentPhoto(){
        return albums.get(currentAlbum).getPhoto(currentPhoto);
    }
    
    public static void setComparePhoto1(int photo1){
        comparePhoto1 = photo1;
    }
    
    public static void setComparePhoto2(int photo2){
        comparePhoto2 = photo2;
    }

    public static Photo getComparePhoto1(){
        return albums.get(currentAlbum).getPhoto(comparePhoto1);
    }
    
    public static Photo getComparePhoto2(){
        return albums.get(currentAlbum).getPhoto(comparePhoto2);
    }
    
    public static Album getCurrentAlbum(){
        return albums.get(currentAlbum);
    }
    
    public static void setCurrentAlbum(int newCurrentAlbum){
        previousAlbum = currentAlbum; 
        currentAlbum = newCurrentAlbum;
         
    }
    
    public static int getPreviousAlbum()
    {
    
        return previousAlbum;
    }

    public static void setCurrentAlbumName(String newName){
        albums.get(currentAlbum).setAlbumName(newName);
    }
    
    public static int getCurrentAlbumIndex(){
        return currentAlbum;
    }
    
    
 // add album i with name s with its first photo p
    public static void addAlbum(String albName, Uri imageUri, String comment){
//        if(albums.size() > 0)
//        Log.e("adding Album", "adding album " + albName + ", other album has name " + albums.get(0).getAlbumName());
        Album temp = new Album(albName);
        temp.addPhoto(new Photo(comment,imageUri));
        albums.add(0,temp); 
        Controller.setCurrentAlbum(0);
//        if(albums.size() > 1)
//          Log.e("adding Album", "adding album " + albName + ", other album has name " + albums.get(1).getAlbumName());

    } 

 // delete album i
    public static void deleteAlbum(int albumIndex)
    {
        Log.e("Deleting Album", "album " + albumIndex);
        albums.get(albumIndex).deleteAll();
        albums.remove(albumIndex);
        Controller.setCurrentAlbum(-1);
    } 

 // change the name of album i to s
    public static void updateAlbum(int albumIndex, String newName)
    {   
        Log.e("updating Album", "album " + albumIndex + " changing name from " + albums.get(albumIndex).getAlbumName() + " to " + newName);
        Album temp = albums.get(albumIndex);
        temp.setAlbumName(newName);
        albums.set(albumIndex, temp);
    } 

 // get photo j from album i
    public static Photo getPhoto(int albumIndex, int photoIndex){
        Log.e("getting photo", "get " + photoIndex + " photo from album " + albumIndex);
      return  albums.get(albumIndex).getPhoto(photoIndex);
    } 

 // add photo p to i
    public static void addPhoto(int albumIndex, Uri imageUri, String comment)
    {
        
        Log.e("addPhoto", "add photo with comment " + comment + " to album " + albumIndex);
        Album temp = albums.get(albumIndex);
        temp.addPhoto(new Photo(comment, imageUri));
        setCurrentPhoto(temp.size());
        setCurrentAlbum(albumIndex);
        albums.set(albumIndex, temp);
    } 

 // delete photo j
    public static void deletePhoto(int albumIndex, int photoIndex)
    {
        Log.e("deletePhoto", "delete photo " + photoIndex + " from album " + albumIndex);
        Album temp = albums.get(albumIndex);
        temp.deletePhoto(photoIndex);
        Log.d("tempSize", "temp=" + temp.size() + "from deletephoto from album" + albumIndex);
        if(temp.size() == 0){
            deleteAlbum(albumIndex);
            
        }
        else
        albums.set(albumIndex, temp);
        
    } 

 // update the comments on this photo
    public static void updatePhoto(int albumIndex, int photoIndex, String newComment)
    {
        Log.e("updatePhoto", "update photo " + photoIndex + " in album " + albumIndex + " with comment " + newComment);
        Album tempAlbum = albums.get(albumIndex);
        Photo tempPhoto = tempAlbum.getPhoto(photoIndex);
        tempPhoto.setComment(newComment);
        tempAlbum.updatePhoto(photoIndex, tempPhoto);
        albums.set(albumIndex, tempAlbum);
    } 

    
    public static String[] getAlbumNames(){
        
        
        
        Log.e("getting album names", "albums.size " + albums.size());
        
        if(albums.size() == 0) // Cant cast albums to string[] using .toArray because albums isnt an arraylist of strings
            return new String[] {};
        
        String[] albNames = new String[albums.size()];
        
        for (int i = 0; i < albums.size(); i++){
            Log.e("", "album " + i + " has name " + albums.get(i).getAlbumName());
            albNames[i] = albums.get(i).getAlbumName();
        }
        return albNames;
    }
    
 // Save the ArrayList<HashMap<String,String>> to a file
    public static void saveObject(Context c)
    {

        FileOutputStream stream = null;
        try
        {
            
            stream = c.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(stream);
            out.writeObject(albums);
            out.flush();
            stream.getFD().sync();
            stream.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }




    // Load the previously stored ArrayList (logsList)
    @SuppressWarnings("unchecked")
    public static void loadObject(Context c)
    {

        FileInputStream stream = null;

        try
        {
            stream = c.openFileInput(fileName);
            if (stream == null)
            {
            return;    
            }
            
            ObjectInputStream in = new ObjectInputStream(stream);
            ArrayList<Album> list;
            Object temp;
            temp = in.readObject();
            if (temp != null)
            {
                list = (ArrayList<Album>) temp;
                stream.close();
                albums = list;
            }

        } catch (FileNotFoundException e)
        {
        } catch (OptionalDataException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    
}
