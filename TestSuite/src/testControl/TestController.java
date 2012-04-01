package testControl;


import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Album;
import model.Photo;

import org.junit.Test;

import controller.Controller;


public class TestController
{
    Photo photo1;
    Photo photo2;
    Photo photo3;
    Photo photo4;
    Photo photo5;
    Photo photo6;
    Album album1;
    Album album2;
    Album album3;
    ArrayList<Album> albs;
    //Controller c = new Controller();
    
    private void setup()
    {

        

        
//        photo1 = new Photo("comment1", null);
//        photo2 = new Photo("comment2", null);
//        photo3 = new Photo("comment3", null);
//        photo4 = new Photo("comment4", null);
//        photo5 = new Photo("comment5", null);
//        photo6 = new Photo("comment6", null);
//        
//        album1 = new Album("Album1");
//        album2 = new Album("Album2");
//        album3 = new Album("Album3");
//        
//        album1.addPhoto(photo1);
//        album1.addPhoto(photo2);
//        album2.addPhoto(photo3);
//        album2.addPhoto(photo4);
//        album3.addPhoto(photo5);
//        album3.addPhoto(photo6);
        
        
    }
    
    private void teardown(){
        while(Controller.getAlbumNames().length != 0)
            Controller.deleteAlbum(0);
    }
    
    
    @Test
    public void testControl()
    {
        assertEquals(true, Controller.class != null);
        teardown();
    }

    @Test
    public void testAddAlbum()
    {
        setup();
        Controller.addAlbum("Album1", null, "Comment");
        System.out.println("" + Controller.getAlbumNames().length);
        assertEquals(1, Controller.getAlbumNames().length);
        assertEquals("Album1", Controller.getAlbum(0).getAlbumName());
        teardown();
    }
    
    @Test
    public void testGetAlbum()
    {
        setup();
        Controller.addAlbum("Album1", null, null);
        assertEquals("Album1", Controller.getAlbum(0).getAlbumName());
        teardown();
        
    }



    @Test
    public void testDeleteAlbum()
    {

        setup();
        Controller.addAlbum("Album1", null, null);
        assertEquals(1, Controller.getAlbumNames().length);
        Controller.deleteAlbum(0);
        assertEquals(0, Controller.getAlbumNames().length);
        Controller.addAlbum("Album1", null, null);
        Controller.addAlbum("Album2", null, null);
        Controller.addAlbum("Album3", null, null);
        assertEquals(3, Controller.getAlbumNames().length);
        Controller.deleteAlbum(2);
        Controller.deleteAlbum(1);
        Controller.deleteAlbum(0);
        assertEquals(0, Controller.getAlbumNames().length);
        teardown();
    }

    @Test
    public void testUpdateAlbum()
    {

        setup();
        Controller.addAlbum("Album1", null, null);
        assertEquals(1, Controller.getAlbumNames().length);
        Controller.deleteAlbum(0);
        assertEquals(0, Controller.getAlbumNames().length);
        Controller.addAlbum("Album1", null, null);
        Controller.addAlbum("Album2", null, null);
        Controller.addAlbum("Album3", null, null);
        Controller.updateAlbum(2, "NewAlbum3");
        assertEquals("NewAlbum3", Controller.getAlbumNames()[2]);
        teardown();
    }
    
    
    @Test
    public void testAddPhoto()
    {

        setup();
        Controller.addAlbum("Album1", null, null);
        assertEquals(1, Controller.getAlbumNames().length);
        Controller.deleteAlbum(0);
        assertEquals(0, Controller.getAlbumNames().length);
        Controller.addAlbum("Album1", null, null);
        Controller.addAlbum("Album2", null, null);
        Controller.addAlbum("Album3", null, null);
        Controller.addPhoto(1, null, null);
        assertEquals(2, Controller.getAlbum(1).getPhotos().size());
        teardown();
    }

    @Test
    public void testGetPhoto()
    {

        setup();
        Controller.addAlbum("Album1", null, null);
        assertEquals(1, Controller.getAlbumNames().length);
        Controller.deleteAlbum(0);
        assertEquals(0, Controller.getAlbumNames().length);
        Controller.addAlbum("Album1", null, null);
        Controller.addAlbum("Album2", null, "Comment");
        Controller.addAlbum("Album3", null, null);
        assertEquals("Comment", Controller.getPhoto(1, 0).getComment());
        teardown();
    }



    @Test
    public void testDeletePhoto()
    {
        setup();
        Controller.addAlbum("Album1", null, null);
        assertEquals(1, Controller.getAlbumNames().length);
        Controller.deleteAlbum(0);
        assertEquals(0, Controller.getAlbumNames().length);
        Controller.addAlbum("Album1", null, null);
        Controller.addAlbum("Album2", null, "Comment");
        Controller.addAlbum("Album3", null, null);
        Controller.deletePhoto(1, 0);
        assertEquals(0, Controller.getAlbum(1).getPhotos().size());
        teardown();
        
    }

    @Test
    public void testUpdatePhoto()
    {

        setup();
        Controller.addAlbum("Album1", null, null);
        assertEquals(1, Controller.getAlbumNames().length);
        Controller.deleteAlbum(0);
        assertEquals(0, Controller.getAlbumNames().length);
        Controller.addAlbum("Album1", null, null);
        Controller.addAlbum("Album2", null, "Comment");
        Controller.addAlbum("Album3", null, null);
        assertEquals("Comment", Controller.getPhoto(1, 0).getComment());
        Controller.updatePhoto(1, 0, "New Comment");
        assertEquals("New Comment", Controller.getPhoto(1, 0).getComment());
        teardown();
    }

}
