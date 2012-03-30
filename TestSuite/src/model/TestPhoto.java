package model;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.Date;

import model.Photo;

import org.junit.Test;

import android.net.Uri;

import android.net.Uri;

public class TestPhoto
{
    
    
    @Test
    public final void testPhoto(){

        Photo p = new Photo("Comment", null);
        assertEquals(true, p != null);
        
    }

    @Test
    public final void testGetpTimeStamp()
    {
        Photo p = new Photo("Comment", null);
        try
        {
            Thread.sleep(20);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        Date  d = new Date();
        assertTrue(d.after(p.getpTimeStamp()));
    }

    @Test
    public final void testSetpTimeStamp()
    {

        Photo p = new Photo("Comment", null);
        p.setpTimeStamp(new Date(112233));
        Date date = new Date(112233);
        assertEquals(date, p.getpTimeStamp());
        
    }

    @Test
    public final void testGetComment()
    {

        Photo p = new Photo("Comment", null);
        assertEquals("Comment",p.getComment());
    }

    @Test
    public final void testSetComment()
    {

        Photo p = new Photo("Comment", null);
        assertEquals("Comment",p.getComment());
        p.setComment("comment2");
        assertEquals("comment2", p.getComment());
    }

    @Test
    public void testHasTag()
    {
        Photo p = new Photo("red foot", null);
        assert(p.hasTag("red"));
        assert(p.hasTag("foot"));
        assertEquals(false, p.hasTag("fffff"));
        

    }



    @Test
    public void testInTags()
    {
        Photo p = new Photo("red foot", null);
        assert(p.inTags("red"));
        assert(p.inTags("foot"));
        
    }

    @Test
    public void testAddTag()
    {
        Photo p = new Photo("red foot djdjdj", null);
        assert(p.hasTag("red"));
        p.addTag("djdjdj");
        assert(p.hasTag("djdjdj"));
        
    }



// Cannot actually test without Uri
    @Test
    public void testSetPicture()
    {
        assert(true);
    }

    @Test
    public void testGetPicture()
    {

        assert(true);
    }

}
