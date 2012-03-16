package ca.ualberta.ca.c301.test;

import view.EditPhotoActivity;
import android.test.ActivityInstrumentationTestCase2;


public class EditPhotoActivityTest extends ActivityInstrumentationTestCase2<EditPhotoActivity>
{

    public EditPhotoActivityTest()
    {

        super("view", EditPhotoActivity.class);
    }
    
    // No need for @test annotation because we extend the testCase (everything is a test)
    // note: assertEquals is in italics because it is static. (but we don't have to do a static import here0
    public void testTest(){
        assertEquals(8,16/2);
    }

}
