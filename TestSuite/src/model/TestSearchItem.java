package model;

import static org.junit.Assert.*;

import org.junit.Test;


public class TestSearchItem
{

    @Test
    public void testSearchItem()
    {

        SearchItem s = new SearchItem(0, 0, null);
        assertEquals(true, (s != null));
    }

    @Test
    public void testGetAlbumIndex()
    {

        SearchItem s = new SearchItem(2, 3, null);
        assertEquals(2, s.getAlbumIndex());
    }

    @Test
    public void testGetPhotoIndex()
    {

        SearchItem s = new SearchItem(2, 3, null);
        assertEquals(3, s.getPhotoIndex());
    }

    @Test
    public void testGetPictureUri()
    {

        SearchItem s = new SearchItem(2, 3, null);
        assertEquals(null, s.getPictureUri());
    }

}
