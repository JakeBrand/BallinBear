package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.StringTokenizer;

import control.Controller;

import android.net.Uri;
import android.util.Log;

/**
 * @author J-Tesseract
 */

public class Photo implements Serializable
{

    /**
     * Fields of photo
     */
    private static final long serialVersionUID = -2561097008925968276L;
    private Date              pTimeStamp;
    private String            comment;
    transient private Uri     imageUri;
    private ArrayList<String> tags;
    private String            uriBackUp;

    /**
     * hasTag
     * 
     * given a String tag, determines if the Photo has been 'auto-tagged' with
     * tag
     * 
     * @param tag
     *            The tag being searched for
     * @return True if the photo has the provided tag
     */
    public boolean hasTag(String tag)
    {

        int i = 0;
        boolean hasT = false;
        while (i < tags.size() && !hasT)
        {
            hasT = (tags.get(i).equalsIgnoreCase(tag));
            i++;
        }
        return hasT;

    }

    /**
     * constructor
     * 
     * @param comm
     *            The comment atssociated with the Photo
     * @param imageU
     *            The Uri the Bitmap is stored at
     */
    public Photo(String comm, Uri imageU)
    {

        this.pTimeStamp = new Date(System.currentTimeMillis());
        this.comment = comm;
        this.imageUri = imageU;
        this.tags = generateTags(comm);
        this.uriBackUp = imageU.toString();
        this.tags = generateTags(comm);
        Collections.sort(tags);

    }

    /**
     * generateTags
     * 
     * generates an ArrayList of Strings using the comment and common words to
     * 'auto-tag' the photo.
     * 
     * @param comm
     *            The comment used to generate tags
     * @return The ArrayList<String> of all tags
     */
    private ArrayList<String> generateTags(String comm)
    {

        StringTokenizer token = new StringTokenizer(comm);
        String str;
        ArrayList<String> tags = new ArrayList<String>();
        while (token.hasMoreTokens())
        {
            str = token.nextToken();
            if (Controller.inTags(str))
            {
                Log.e(null, str);
                tags.add(str.toLowerCase());
            }

        }

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        tags.add(formatter.format(pTimeStamp));

        return tags;
    }

    /**
     * inTags
     * 
     * given a String s, determines if s is one of the given tags
     * 
     * @param tagToFind
     *            The tag to find
     * @return inTag True if the tag is found in known tags. False otherwise.
     */
    public boolean inTags(String tagToFind)
    {

        boolean inTag = false;
        int i = 0;
        while (i < tags.size() && !inTag)
        {
            inTag = (tags.get(i).equalsIgnoreCase(tagToFind));
            i++;
        }

        return inTag;
    }

    /**
     * addTag
     * 
     * Add a new tag to the list of known tags
     * 
     * @param tag
     *            The new tag to add
     * @return hasNewTag True if the comment has the tag. False otherwise.
     */
    public boolean addTag(String tag)
    {

        StringTokenizer token = new StringTokenizer(comment);
        String str;
        boolean hasNewTag = false;

        if (inTags(tag))
        {
            return true;
        }
        while (token.hasMoreTokens())
        {

            str = token.nextToken();
            if (str.equals(tag))
            {
                tags.add(str.toLowerCase());
                hasNewTag = true;
            }
        }

        return hasNewTag;
    }

    /**
     * getpTimeStamp
     * 
     * @return pTimeStamp The current timestamp of the Photo
     */
    public Date getpTimeStamp()
    {

        return pTimeStamp;
    }

    /**
     * setpTimeStamp
     * 
     * @param pTimeStamp
     *            The new timestamp for the Photo
     */
    public void setpTimeStamp(Date pTimeStamp)
    {

        this.pTimeStamp = pTimeStamp;
    }

    /**
     * getComent
     * 
     * @return comment The current comment of the Photo
     */
    public String getComment()
    {

        return this.comment;
    }

    /**
     * setComment
     * 
     * @param com
     *            The new comment of the Photo
     */
    public void setComment(String com)
    {

        this.comment = com;
        this.tags = generateTags(com);
    }

    /**
     * setPicture
     * 
     * @param imageU
     *            The Uri the Bitmap is stored at
     */
    public void setPicture(Uri imageU)
    {

        this.imageUri = imageU;
        this.uriBackUp = imageU.toString();
    }

    /**
     * getPicture
     * 
     * @return imageUri The Uri the Bitmap is stored at
     */
    public Uri getPicture()
    {

        this.imageUri = Uri.parse(this.uriBackUp);
        return this.imageUri;
    }

}
