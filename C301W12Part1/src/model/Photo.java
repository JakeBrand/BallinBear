package model;

import java.io.Serializable;
import java.util.Date;
import android.net.Uri;

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

    
    /**
     * constructor
     * 
     * @param comments
     * @param imageU
     */
    public Photo(String comm, Uri imageU){
        this.pTimeStamp = new Date(System.currentTimeMillis());
        this.comment = comm;
        this.imageUri = imageU;
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
