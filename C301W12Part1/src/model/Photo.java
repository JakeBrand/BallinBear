package model;

import java.io.Serializable;
import java.util.Date;
import android.net.Uri;

/**
 * 
 * @author J-Tesseract
 * 
 */
public class Photo implements Serializable
{

    /**
     * fields
     */
    private static final long serialVersionUID = -2561097008925968276L;
    private Date              pTimeStamp;
    private String            comment;
    private Uri               imageUri;

    /**
     * constructor
     * 
     * @param comm
     *            String of comments associated to the Photo
     * @param imageU
     *            Uri that the picture is stored at
     */
    public Photo(String comm, Uri imageU)
    {

        this.pTimeStamp = new Date(System.currentTimeMillis());
        this.comment = comm;
        this.imageUri = imageU;
    }

    /**
     * getpTimeStamp
     * 
     * @return pTimeStamp The Date object representing the timestamp of the
     *         Photo
     */
    public Date getpTimeStamp()
    {

        return pTimeStamp;
    }

    /**
     * setpTimeStamp
     * 
     * @param pTimeStamp
     *            The Date object representing the timestamp of the Photo
     */
    public void setpTimeStamp(Date pTimeStamp)
    {

        this.pTimeStamp = pTimeStamp;
    }

    /**
     * getComent
     * 
     * @return comment The String comment associated with the Photo
     */
    public String getComment()
    {

        return this.comment;
    }

    /**
     * setComment
     * 
     * @param com
     *            The String comment associated with the Photo
     */
    public void setComment(String com)
    {

        this.comment = com;
    }

    /**
     * setPicture
     * 
     * @param imageU
     *            The Uri that the picture is saved at
     */
    public void setPicture(Uri imageU)
    {

        this.imageUri = imageU;
    }

    /**
     * getPicture
     * 
     * @return imageUri the Uri that the picture is saved at
     */
    public Uri getPicture()
    {

        return this.imageUri;
    }

}
