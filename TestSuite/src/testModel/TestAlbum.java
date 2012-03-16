package testModel;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Album;
import model.Photo;

import org.junit.Test;

public class TestAlbum
{
    Album album;
    Photo first;
    Photo second;

    public final void setUp(){
        this.album = new Album("Name");
        this.first = new Photo("First Photo", null);
        this.second = new Photo("Second Photo", null);
    }
    
    public final void tearDown(){
        this.album = null;
        this.first = null;
        this.second = null;
    }
    
    @Test
    public final void testAlbum()
    {
        Album testAlbum = new Album("Name");
        assertNotNull(testAlbum);
    }

    @Test
    public final void testDeleteAll()
    {
        setUp();

        album.addPhoto(first);
        album.addPhoto(second);
        album.addPhoto(second);
        assertEquals(3, album.size());

        album.deleteAll();
        assertEquals(0, album.size());
        tearDown();
    }

    @Test
    public final void testSize()
    {

        setUp();
        assertEquals(0, album.size());

        album.addPhoto(first);
        album.addPhoto(second);
        album.addPhoto(first);
        assertEquals(3, album.size());
        tearDown();
    }

    @Test
    public final void testGetAlbumName()
    {
        Album testAlbum = new Album("Name");
        assertEquals("Name", testAlbum.getAlbumName());
    }

    @Test
    public final void testSetAlbumName()
    {
        setUp();
        album.setAlbumName("New Name");
        assertEquals("New Name", album.getAlbumName());
        tearDown();
    }

    @Test
    public final void testGetPhotos()
    {
        setUp();
        album.addPhoto(first);
        album.addPhoto(second);
        assertEquals(first, album.getPhoto(0));
        assertEquals(second, album.getPhoto(1));
        tearDown();
    }

    @Test
    public final void testSetPhotos()
    {
        setUp();
        ArrayList<Photo> testPhotos = new ArrayList<Photo>();
        testPhotos.add(first);
        testPhotos.add(second);
        
        album.setPhotos(testPhotos);
        assertEquals(first, album.getPhoto(0));
        assertEquals(second, album.getPhoto(1));
        tearDown();
    }

    @Test
    public final void testGetPhoto()
    {   
        setUp();
        album.addPhoto(first);
        album.addPhoto(second);
        
        assertEquals(first, album.getPhoto(0));
        assertEquals(second, album.getPhoto(1));
        tearDown();
    }

    @Test
    public final void testAddPhoto()
    {
        setUp();
        assertEquals(0, album.size());
        
        album.addPhoto(first);
        assertEquals(1, album.size());
        
        album.addPhoto(second);
        assertEquals(2, album.size());
        tearDown();
    }

    @Test
    public final void testDeletePhoto()
    {
     
        setUp();
        album.addPhoto(first);
        album.addPhoto(second);
        assertEquals(2,album.size());
        assertEquals(first, album.getPhoto(0));
        
        album.deletePhoto(0);
        assertEquals(1, album.size());
        assertEquals(second, album.getPhoto(0));
        tearDown();
    }

    @Test
    public final void testUpdatePhoto()
    {
        setUp();
        album.addPhoto(first);
        assertEquals(first, album.getPhoto(0));
        album.updatePhoto(0, second);
        assertEquals(second, album.getPhoto(0));
        tearDown();
    }

}
