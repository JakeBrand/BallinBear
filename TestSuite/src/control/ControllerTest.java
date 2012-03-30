package control;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;

import model.Album;
import model.Photo;

import org.junit.Test;

import android.net.Uri;

public class ControllerTest {
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


	public void setup(){
//		photo1 = new Photo("comment1", null);
//		photo2 = new Photo("comment2", null);
//		photo3 = new Photo("comment3", null);
//		photo4 = new Photo("comment4", null);
//		photo5 = new Photo("comment5", null);
//		photo6 = new Photo("comment6", null);
//		      
//		album1 = new Album("Album1");
//		album2 = new Album("Album2");
//		album3 = new Album("Album3");
//		
//		album1.addPhoto(photo1);
//		album1.addPhoto(photo2);
//		album2.addPhoto(photo3);
//		album2.addPhoto(photo4);
//		album3.addPhoto(photo5);
//		album3.addPhoto(photo6);
//
//		Uri photo1Uri = photo1.getPicture(); 
//		//Uri uri = providedPhoto.getPicture();
//	
//		Controller.addAlbum("Name", photo1Uri, "Comment");
		
	}
    private void teardown(){
        while(Controller.getAlbumNames().length != 0){
            Controller.deleteAlbum(0);
            System.out.print(Controller.getAlbumNames().length);
        }
    }

    @Test
    public void testControl()
    {
        assertEquals(true, Controller.class != null);
        teardown();
    }

    
	@Test
	public void testGetAlbum() {
		setup();
        Controller.addAlbum("Album1", null, "Comment1");
//      Controller.addPhoto(0, null, "Comment");
        assertEquals("Album1", Controller.getAlbum(0).getAlbumName());
        teardown();
	}

	@Test
	public void testSetCurrentPhoto() {
		setup();
		Controller.addAlbum("Album1", null, "Comment");
		Controller.addPhoto(0, null, "Comment");
		Controller.addPhoto(0, null, "Comment2");
		Controller.setCurrentPhoto(2);
		assertEquals("Comment2", Controller.getCurrentPhoto().getComment());
		teardown();
	}

	@Test
	public void testGetCurrentPhotoIndex() {
		setup();
		Controller.addAlbum("Album1", null, "Comment");
		Controller.addPhoto(0, null, "Comment");
		Controller.addPhoto(0, null, "Comment2");
		Controller.setCurrentPhoto(2);
		assertEquals(2, Controller.getCurrentPhotoIndex());
		teardown();
	}

	@Test
	public void testGetCurrentPhoto() {
		setup();
		Controller.addAlbum("Album1", null, "Comment");
		Controller.addPhoto(0, null, "Comment");
		Controller.addPhoto(0, null, "Comment2");
		Controller.setCurrentPhoto(2);
		assertEquals("Comment2", Controller.getCurrentPhoto().getComment());
		teardown();
	}

	@Test
	public void testSetComparePhoto1() {
		setup();
		Controller.addAlbum("Album1", null, "Comment");
		Controller.addPhoto(0, null, "Comment");
		Controller.addPhoto(0, null, "Comment2");
		Controller.setComparePhoto1(0);
		Controller.setCurrentPhoto(0);
		assertEquals(Controller.getComparePhoto1(), Controller.getCurrentPhoto());
		teardown();		
	}

	@Test
	public void testSetComparePhoto2() {
		setup();
		Controller.addAlbum("Album1", null, "Comment");
		Controller.addPhoto(0, null, "Comment");
		Controller.addPhoto(0, null, "Comment2");
		Controller.setComparePhoto2(0);
		Controller.setCurrentPhoto(0);
		assertEquals(Controller.getComparePhoto2(), Controller.getCurrentPhoto());
		teardown();
	}

	@Test
	public void testGetComparePhoto1() {
		setup();
		Controller.addAlbum("Album1", null, "Comment");
		Controller.addPhoto(0, null, "Comment");
		Controller.addPhoto(0, null, "Comment2");
		Controller.setComparePhoto1(0);
		Controller.setCurrentPhoto(0);
		assertEquals(Controller.getComparePhoto1(), Controller.getCurrentPhoto());
		teardown();
	}

	@Test
	public void testGetComparePhoto2() {
		setup();
		Controller.addAlbum("Album1", null, "Comment");
		Controller.addPhoto(0, null, "Comment");
		Controller.addPhoto(0, null, "Comment2");
		Controller.setComparePhoto2(0);
		Controller.setCurrentPhoto(0);
		assertEquals(Controller.getComparePhoto2(), Controller.getCurrentPhoto());
		teardown();
	}

	@Test
	public void testGetCurrentAlbum() {
		setup();
		Controller.addAlbum("Album1", null, "Comment");
		Controller.addPhoto(0, null, "Comment");
		Controller.addPhoto(0, null, "Comment2");
		Controller.setCurrentPhoto(2);
		Controller.setCurrentAlbum(0);
		assertEquals("Album1", Controller.getCurrentAlbum().getAlbumName());
		teardown();
	}

	@Test
	public void testSetCurrentAlbum() {
		setup();
		Controller.addAlbum("Album1", null, "Comment");
		Controller.addPhoto(0, null, "Comment");
		Controller.addPhoto(0, null, "Comment2");
		Controller.setCurrentPhoto(2);
		Controller.setCurrentAlbum(0);
		assertEquals("Album1", Controller.getCurrentAlbum().getAlbumName());
		teardown();
	}

	@Test
	public void testSetCurrentAlbumName() {
		setup();
		Controller.addAlbum("Album1", null, "Comment");
		Controller.addPhoto(0, null, "Comment");
		Controller.addPhoto(0, null, "Comment2");
		Controller.setCurrentPhoto(2);
		Controller.setCurrentAlbum(0);
		Controller.setCurrentAlbumName("Name");
		assertEquals("Name", Controller.getCurrentAlbum().getAlbumName());
		teardown();
	}

	@Test
	public void testGetCurrentAlbumIndex() {
		setup();
		Controller.addAlbum("Album1", null, "Comment");
		Controller.addPhoto(0, null, "Comment");
		Controller.addPhoto(0, null, "Comment2");
		Controller.setCurrentPhoto(2);
		Controller.setCurrentAlbum(0);
		Controller.setCurrentAlbumName("Name");
		assertEquals("Name", Controller.getCurrentAlbum().getAlbumName());
		teardown();
	}

	@Test
	public void testAddAlbum() {
		setup();
		Controller.addAlbum("Album1", null, "Comment");
		Controller.addPhoto(0, null, "Comment");
		Controller.addPhoto(0, null, "Comment2");
		Controller.setCurrentPhoto(2);
		Controller.setCurrentAlbum(0);
		Controller.addAlbum("Hello", null, "Comment3");
		assertEquals("Comment3", Controller.getCurrentAlbum().getPhoto(0).getComment());
		teardown();
	}

	@Test
	public void testDeleteAlbum() {
		setup();
        Controller.addAlbum("Album1", null, "Comment");
        assertEquals(1, Controller.getAlbumNames().length);
        Controller.setCurrentAlbum(0);
        Controller.deleteAlbum(Controller.getCurrentAlbumIndex());
        assertEquals(0, Controller.getAlbumNames().length);
        teardown();
 	}

	@Test
	public void testUpdateAlbum() {
        setup();
        Controller.addAlbum("Album1", null, "Comment");
        assertEquals(1, Controller.getAlbumNames().length);
        Controller.deleteAlbum(0);
        assertEquals(0, Controller.getAlbumNames().length);
        Controller.addAlbum("Album1", null, "Comment2");
        Controller.addAlbum("Album2", null, "Comment3");
        Controller.addAlbum("Album3", null, "Comment4");
        Controller.updateAlbum(2, "NewAlbum3");
        assertEquals("NewAlbum3", Controller.getAlbumNames()[2]);
        teardown();
	}
//	
//    public static void removeAlarm(){
//        ScheduledFuture notifyerHandle = getCurrentAlbum().getNotifyerHandler();
//        if(notifyerHandle!= null){
////            Log.d("NotifyerHandle Not", "Null");
//            getCurrentAlbum().setNotifyerHandle(null,0,0);
//        }
//    }

	@Test
	public void testRemoveAlarm(){
		
		
		
	}
	
	@Test
	public void testGetPhoto() {
        setup();
        Controller.addAlbum("Album1", null, "Comment");
        assertEquals(1, Controller.getAlbumNames().length);
        Controller.deleteAlbum(0);
        assertEquals(0, Controller.getAlbumNames().length);
        Controller.addAlbum("Album1", null, "Comment2");
        Controller.addAlbum("Album2", null, "Comment3");
        Controller.addAlbum("Album3", null, "Comment4");
        assertEquals("Comment3", Controller.getPhoto(1, 0).getComment());
        teardown();
	}

	@Test
	public void testAddPhoto() {
        setup();
        Controller.addAlbum("Album1", null, "Comment");
        assertEquals(1, Controller.getAlbumNames().length);
        Controller.deleteAlbum(0);
        assertEquals(0, Controller.getAlbumNames().length);
        Controller.addAlbum("Album1", null, "Comment2");
        Controller.addAlbum("Album2", null, "Comment3");
        Controller.addAlbum("Album3", null, "Comment4");
        Controller.addPhoto(1, null, "Comment");
        assertEquals(2, Controller.getAlbum(1).getPhotos().size());
        teardown();

	}

	@Test
	public void testDeletePhoto() {
        setup();
        Controller.addAlbum("Album1", null, "Comment");
        assertEquals(1, Controller.getAlbumNames().length);
        Controller.deleteAlbum(0);
        assertEquals(0, Controller.getAlbumNames().length);
        Controller.addAlbum("Album1", null, "Comment2");
        Controller.addPhoto(0, null, "hi");
        Controller.addAlbum("Album2", null, "Comment3");
        Controller.addAlbum("Album3", null, "Comment4");
        Controller.deletePhoto(0, 0);
        assertEquals(1, Controller.getAlbum(0).getPhotos().size());
        teardown();
	}

	@Test
	public void testUpdatePhoto() {
        setup();
        assertEquals(0, Controller.getAlbumNames().length);
        Controller.addAlbum("Album1", null, "Comment");
        assertEquals("Comment", Controller.getPhoto(0, 0).getComment());
        Controller.updatePhoto(0, 0, "New Comment");
        assertEquals("New Comment", Controller.getPhoto(0, 0).getComment());
        teardown();
	}

	@Test
	public void testGetAlbumNames() {
        setup();
        Controller.addAlbum("Album1", null, "Comment");
        assertEquals("Album1", Controller.getAlbum(0).getAlbumName());
        teardown();
	}

//	@Test
//	public void testCheckAlbumNames() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSaveObject() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testLoadObject() {
//		fail("Not yet implemented");
//	}

}
