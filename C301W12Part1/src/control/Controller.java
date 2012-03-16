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
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import model.Album;
import model.Photo;
// Controller is a static class and can be accessed from both model and view
public class Controller {
    
    
    
    /**
     * When moving between Activities, these 4 variables help the Controller
     * know what Album/Photo(s) it should be passing.
     */
    private static int currentAlbum;
    private static int currentPhoto;
    private static int comparePhoto1;
    private static int comparePhoto2;
    
    
    /**
     * albums holds all of the Albums
     */
    private static ArrayList<Album> albums = new ArrayList<Album>();
    private static final String fileName = "albumsfile.data";
    

    
    /**
     *  getAlbum
     *  
     * @param albumIndex
     * @return the Album at index albumIndex
     */
    public static Album getAlbum(int albumIndex){
        return albums.get(albumIndex);
    }
    
    
    /**
     * setCurrentPhoto
     * 
     * @param currentP
     */
    public static void setCurrentPhoto(int currentP){
        currentPhoto = currentP;
    }
    
    
    /**
     * getCurrentPhotoIndex
     * 
     * @return currentPhoto
     */
    public static int getCurrentPhotoIndex(){
        return currentPhoto;
    }
    
    
    /**
     * getCurrentPhoto
     * 
     * @return Photo in the currentAlbum at the currentPhotoIndex
     */
    public static Photo getCurrentPhoto(){
        return albums.get(currentAlbum).getPhoto(currentPhoto);
    }
    
    
    /**
     * setComparePhoto1
     * 
     * @param photo1
     */
    public static void setComparePhoto1(int photo1){
        comparePhoto1 = photo1;
    }
    
    
    /**
     * setComparePhoto2
     * 
     * @param photo2
     */
    public static void setComparePhoto2(int photo2){
        comparePhoto2 = photo2;
    }

    
    /**
     * getComparePhoto1
     * 
     * @return Photo in currentAlbum and comparePhoto1 index
     */
    public static Photo getComparePhoto1(){
        return albums.get(currentAlbum).getPhoto(comparePhoto1);
    }
    
    /**
     * getComparePhoto2
     * 
     * @return Photo in currentAlbum and comparePhoto2 index
     */
    public static Photo getComparePhoto2(){
        return albums.get(currentAlbum).getPhoto(comparePhoto2);
    }
    
    
    
    /**
     * getCurrentAlbum
     * 
     * @return Album at currentAlbum index
     */
    public static Album getCurrentAlbum(){
        return albums.get(currentAlbum);
    }
    
    
    /**
     * setCurrentAlbum
     * 
     * @param newCurrentAlbum
     */
    public static void setCurrentAlbum(int newCurrentAlbum){
        currentAlbum = newCurrentAlbum;     
    }
    

    /**
     * setCurrentAlbumName
     * 
     * @param newName
     */
    public static void setCurrentAlbumName(String newName){
        albums.get(currentAlbum).setAlbumName(newName);
    }
    
    /**
     * getCurrentAlbumIndex
     * 
     * @return currentAlbumIndex
     */
    public static int getCurrentAlbumIndex(){
        return currentAlbum;
    }
    
    /**
     * addAlbum
     * add new album with string albName and add a Photo to it with imageUri and comment
     * 
     * @param albName
     * @param imageUri
     * @param comment
     */
    public static void addAlbum(String albName, Uri imageUri, String comment){
        Album temp = new Album(albName);
        temp.addPhoto(new Photo(comment,imageUri));
        albums.add(0,temp); 
        Controller.setCurrentAlbum(0);
    } 

    /**
     * deleteAlbum
     * 
     * @param albumIndex
     */
    public static void deleteAlbum(int albumIndex){
        Log.e("Deleting Album", "album " + albumIndex);
        albums.get(albumIndex).deleteAll();
        albums.remove(albumIndex);
        Controller.setCurrentAlbum(-1);
    } 

    
    /**
     * updateAlbum 
     * changes the name of the Album at albumIndex with newName
     * 
     * @param albumIndex
     * @param newName
     */
    public static void updateAlbum(int albumIndex, String newName){   
        Album temp = albums.get(albumIndex);
        temp.setAlbumName(newName);
        albums.set(albumIndex, temp);
    } 

    
    /**
     * getPhoto
     * @param albumIndex
     * @param photoIndex
     * @return
     */
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
        Album temp =       albums.get(albumIndex);
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
    
    
    
    public static int checkAlbumNames(String s){
        int i = 0;
       while (i<albums.size() && albums.get(i).getAlbumName() != s) {
        i++;
    }
       if(albums.get(i).getAlbumName() == s){
           return i;
       }
       else
           return -1;
       
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
