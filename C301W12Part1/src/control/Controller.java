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
public class Controller 
{

    static ArrayList<Album> albums = new ArrayList<Album>();
    private static final String fileName = "albumsfile.data";
    
//    public Controller()
//    {
//
//        albums = null;
//    }

    
    // get album i
    public static Album getAlbum(int i)
    {

        return albums.get(i);

    }

 // add album i with name s with its first photo p
    public static void addAlbum(String albName, Uri imageUri, String comment){
        
        Album temp = new Album(albName);
        temp.addPhoto(new Photo(comment,imageUri));
        albums.add(temp);

    } 

 // delete album i
    public static void deleteAlbum(int i)
    {
        albums.remove(i);
    } 

 // change the name of album i to s
    public static void updateAlbum(int i, String s)
    {   Album temp = albums.get(i);
        temp.setAlbumName(s);
        albums.set(i, temp);
    } 

 // get photo j from album i
    public static Photo getPhoto(int i, int j){
      return  albums.get(i).getPhoto(j);
    } 

 // add photo p to i
    public static void addPhoto(int i, Uri imageUri, String comment)
    {
        Album temp = albums.get(i);
        temp.addPhoto(new Photo(comment, imageUri));
        albums.set(i, temp);
    } 

 // delete photo j
    public static void deletePhoto(int i, int j)
    {
        Album temp = albums.get(i);
        temp.deletePhoto(j);
        albums.set(i, temp);
    } 

 // update the comments on this photo
    public static void updatePhoto(int i, int j, String s)
    {
        Album tempAlbum = albums.get(i);
        Photo tempPhoto = tempAlbum.getPhoto(j);
        tempPhoto.setComment(s);
        tempAlbum.updatePhoto(j, tempPhoto);
        albums.set(i, tempAlbum);
    } 

    
    public static String[] getAlbumNames(){
        Log.e("albums.size", albums.size() + "");
       
        if(albums.size() == 0)
            return new String[] {"NO ALBUMS"};
        
        String[] albNames = new String[albums.size()];
        
        for (int i = 0; i < albums.size(); i++){
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
