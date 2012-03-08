package view;

import android.app.Activity;
import android.os.Bundle;
import ca.ualberta.ca.c301.R;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;




//#################################### This activity uses the edit_photo layout and is used to edit aspects of the photos
public class PhotoEditActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
      setContentView(R.layout.editphotoview);
      
      // Get a hold of button 
      Button doneButton = (Button) findViewById(R.id.doneButton);
      OnClickListener listener = new OnClickListener(){

          @Override
          public void onClick(View v)
          {
              // if clicked, get the edittext
             EditText albName = (EditText) findViewById(R.id.albumNameEditText);
             String newAlbumName = albName.getText().toString();        // get the string inside
             
             
             
              
          }
          
      };
      doneButton.setOnClickListener(listener);
        
}
}