package model;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;
import android.net.Uri;



// TODO Photo: Changed comments to String instead of ArrayList
// TODO dont have blobs, they should have paths to the pictures they represent.

public class Photo {

   private Date pTimeStamp;
 //  private ArrayList<Comment> comments;
   private String comments;
   private Uri imageUri;

   
   
   public Photo(String comments, Uri imageU)
   {

       super();
       this.pTimeStamp = new Date(System.currentTimeMillis());
       this.comments = comments;
       this.imageUri = imageU;
   }

   
   
   
   
   
   
   
   
   
   
public Date getpTimeStamp(){

    return pTimeStamp;
}

public void setpTimeStamp(Date pTimeStamp){

    this.pTimeStamp = pTimeStamp;
}


public String getComment(){
  return  this.comments;
}


public void setComment(String com){
    this.comments = com;
}


//public ArrayList<Comment> getComments(){
//
//    return comments;
//}



public void setPicture(Uri imageU){
    this.imageUri = imageU;
    
}

public Uri getPicture(){

    return this.imageUri;
}






//public void addComment(String value){
//    Date cd = new Date();
//    Comment c = new Comment(cd, value);
//    comments.add(c);
//}
//
//public void removeComment(Comment c){
//    comments.remove(c);
//}
//   
   
    
    
}
