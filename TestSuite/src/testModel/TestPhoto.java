package testModel;

import static org.junit.Assert.*;

import java.util.Date;

import model.Photo;

import org.junit.Test;

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



}
