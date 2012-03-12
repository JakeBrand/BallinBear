package view;

import control.Controller;
import model.Album;
import model.Photo;
import ca.ualberta.ca.c301.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class CompareActivity  extends Activity {
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        
        Bundle bundle = getIntent().getExtras();
        
        
        
      setContentView(R.layout.compareview);
      
      EditText photo1EditText = (EditText) findViewById(R.id.photo1EditText);
      EditText photo2EditText = (EditText) findViewById(R.id.photo2EditText);
      
      
      ImageView photo1ImageView = (ImageView) findViewById(R.id.photo1ImageView);
      ImageView photo2ImageView = (ImageView) findViewById(R.id.photo2ImageView);
      
      
      
      TextView photo1DateLabel = (TextView) findViewById(R.id.photo1DateLabel);
      TextView photo2DateLabel = (TextView) findViewById(R.id.photo2DateLabel);
      

      
      
      Photo photo1 = (Photo) bundle.get("Photo1");
      Photo photo2 = (Photo) bundle.get("Photo2");
      
      photo1EditText.setText(photo1.getComment());
      photo2EditText.setText(photo2.getComment());
      
      photo1DateLabel.setText("" + photo1.getpTimeStamp());
      photo2DateLabel.setText("" + photo2.getpTimeStamp());
      
      
      
      
      // TODO CompareActivity: Set imageviews with there appropriate data

      // TODO CompareActivity: SPECIAL Allow for the user to move finger left to right to zoom in on the indicated photo.
      
    }

    public void onPause(){
        super.onPause();
        Controller.saveObject(this);
        
    }
    
}
