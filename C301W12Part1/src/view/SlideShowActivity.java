package view;
import ca.ualberta.ca.c301.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SlideShowActivity extends Activity {

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
      setContentView(R.layout.slideshowview);
      // TODO SlideShowActivity: Actually implement slideshow given Album
      
             
      Button back = (Button) findViewById(R.id.backToAlbumButton);
      
      OnClickListener backListener = new OnClickListener(){

          @Override
          public void onClick(View v)
          {
             // TODO SlideShowActivity: just go back to album
          }
          
      };
      back.setOnClickListener(backListener);
      
      
      
    }
    
    
    
    
}

