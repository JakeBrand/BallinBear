package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import control.Controller;

import android.net.Uri;
import android.util.Log;

public class Photo implements Serializable
{

	/**
	 *  @author J-Tesseract
     * fields
     */
    private static final long serialVersionUID = -2561097008925968276L;
    private Date              pTimeStamp;
    private String            comment;
    private Uri               imageUri;
    private ArrayList<String> tags;
    
    

    /**
     * hasTag 
     * 
     * given a String s, determines if the Photo has been 'auto-tagged' with the String
     * 
     * @param s
     * @return
     */
    public boolean hasTag(String s){
        int i = 0;
        boolean hasT = false;
        while(i < tags.size() && !hasT){
            hasT = (tags.get(i).equals(s));
            i++;
        }
        return hasT;
   
    }

    
    /**
     * constructor
     * 
     * @param comm
     * @param imageU
     */
    public Photo(String comm, Uri imageU){
        this.pTimeStamp = new Date(System.currentTimeMillis());
        this.comment = comm;
        this.imageUri = imageU;
        this.tags = generateTags(comm);
        
    }

    /**
     * generateTags
     * 
     * generates an ArrayList of Strings using the comment and common words to 'auto-tag' the photo.
     * 
     * @param comm
     * @return
     */
    private ArrayList<String> generateTags(String comm){
        StringTokenizer token = new StringTokenizer(comm);
        String str;
        int count = 0;
        ArrayList<String> tags = new ArrayList<String>();
        while (token.hasMoreTokens()){
        count++;
        
        str = token.nextToken();
        if(Controller.inTags(str)){
            Log.e(null,str);
        tags.add(str);
        }
        
        //Log.e(null,str);
        }
        
        
        return tags;
    }


    /**
     * getpTimeStamp
     * 
     * @return pTimeStamp
     */
    public Date getpTimeStamp()  {
        return pTimeStamp;
    }

    /**
     * setpTimeStamp
     * 
     * @param pTimeStamp
     */
    public void setpTimeStamp(Date pTimeStamp) {
        this.pTimeStamp = pTimeStamp;
    }

    
    /**
     * getComent
     * 
     * @return comment
     */
    public String getComment(){
        return this.comment;
    }

    /**
     * setComment
     * 
     * @param com
     */
    public void setComment(String com){
        this.comment = com;
        this.tags = generateTags(com);
    }


    /**
     * setPicture
     * 
     * @param imageU
     */
    public void setPicture(Uri imageU){
        this.imageUri = imageU;
    }

    
    /**
     * getPicture
     * 
     * @return imageUri
     */
    public Uri getPicture(){

        return this.imageUri;
    }



}
