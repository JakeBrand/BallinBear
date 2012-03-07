package model;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;

import android.net.Uri;



// TODO Photos dont have blobs, they should have paths to the pictures they represent.

public class Photo {

   private Date pTimeStamp;
   private ArrayList<Comment> comments;
   private Uri imageUri;

public Date getpTimeStamp(){

    return pTimeStamp;
}

public void setpTimeStamp(Date pTimeStamp){

    this.pTimeStamp = pTimeStamp;
}



public ArrayList<Comment> getComments(){

    return comments;
}

public void getPicture(Uri uri){
    this.imageUri = uri;
    
}

public Uri getPicture(){

    return this.imageUri;
}

public void addComment(String value){
    Date cd = new Date();
    Comment c = new Comment(cd, value);
    comments.add(c);
}
   
public void removeComment(Comment c){
    comments.remove(c);
}
   
   
    
    
}
