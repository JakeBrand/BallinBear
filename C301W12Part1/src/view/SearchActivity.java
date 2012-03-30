package view;

import ca.ualberta.ca.c301.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Spinner;
import android.widget.TextView;
import control.Controller;
import java.util.ArrayList;

import model.SearchItem;

/**
 * Activity using the search_view to find Photos with certain attributes
 * 
 * @author J-Tesseract
 * 
 */
public class SearchActivity extends Activity
{

    ArrayList<SearchItem> results;

    /**
     * onCreate Initialize UI components
     * 
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);

        final EditText tagET = (EditText) findViewById(R.id.tagEditText);
        tagET.setHint("right foot");
        
        
        final Gallery tagGallery = (Gallery) findViewById(R.id.tagGallery);
        final Spinner searchSpinner = (Spinner) findViewById(R.id.searchSpinner);
        String[] options = { "By Tag", "By Date"};
        final TextView status = (TextView) findViewById(R.id.searchStatusTextView);
        status.setText("");
        searchSpinner.setAdapter(new ArrayAdapter<String>(SearchActivity.this,
                android.R.layout.simple_spinner_item, options));

        searchSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {
            /**
             * Perform action depending on selected item
             */
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                    int arg2, long arg3)
            {

                switch (arg2)
                {
                    case 0:

                        tagET.setHint("right foot");
                        break;
                    case 1:

                        tagET.setHint("mm/dd/yyyy");
                        break;
                    default:
                        break;
                }

            }
            
            @Override
            public void onNothingSelected(AdapterView<?> arg0){}
        });

        final Context ctx = this.getApplicationContext();

        tagGallery.setOnItemClickListener(new OnItemClickListener()
        { // if a photo is selected, open it in editPhoto

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                            int position, long id)
                    {
                        // Use the SearchItem at index position to determine the current album
                        Intent editPhotoIntent = new Intent(ctx,
                                EditPhotoActivity.class);
                        Controller.setCurrentAlbum(results.get(position)
                                .getAlbumIndex());
                        Controller.setCurrentPhoto(results.get(position) // and the current photo
                                .getPhotoIndex());
                        editPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Controller.getCurrentPhoto().getPicture());
                        finish();
                        startActivity(editPhotoIntent);

                    }
                });

        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new OnClickListener()
        {

            /**
             * onClick
             * 
             * Perform actions depending on clicked View
             * 
             * @param v The view that has been clicked
             */
            @Override
            public void onClick(View v)
            {

                
                String tag = tagET.getText().toString().trim();
                if(searchSpinner.getSelectedItemPosition() != 0){ // if searching by date
                    if(!goodDate(tag)){                                         // parse it
                        tagET.setHint("Incorrect date format, date must be in mm/dd/yyyy form");
                    tagET.setText("");
                    }
                }
                results = Controller.findPhotos(tag.toLowerCase());                   // search for Photos that have the tag or the given date
                if(results.size() == 0)
                    status.setText("No Results");
                else
                    status.setText("");
                tagGallery.setAdapter(new ImageAdapter(SearchActivity.this));

            }


        });

    }

    
    /**
     * goodDate 
     * 
     * Given a String, determines if it is in mm/dd/yyyy format and if the given date is valid
     * 
     * @param dateInput
     *                 the String to be checked
     * @return
     *          True if string is in mm/dd/yyyy format and if the given date is valid
     */
    private boolean goodDate(String dateInput)
    {
        if(dateInput.length() < 11){
            
            int month;
            int day;
            int year;
            
         try
        {
            month =  Integer.parseInt(dateInput.substring(0, 2));
            day = Integer.parseInt(dateInput.substring(3, 5));
            year = Integer.parseInt(dateInput.substring(6));
            for(int i = 0; i < dateInput.length(); i++)
            System.out.println(dateInput.charAt(i));
            
            assert(dateInput.charAt(2) == '/');
            
            assert(dateInput.charAt(5) == '/');
            
        } catch (NumberFormatException e)
        {
           return false;
        }  
        
        if(month > 0 && month <13 && day > 0 && day < 31 && year > 0){
            System.out.println("m: " + month + " d: " + day + " y: " + year);
            return true;
            
        }

    }
       
        return false;
    }
    
    /**
     * ImageAdapter Class
     * 
     * Is used to set the Gallery with the currently selected album.
     * Still minimally understood but fully functional.
     * 
     */
    public class ImageAdapter extends BaseAdapter
    {

        private Context ctx;
        int             imageBackground;

        public ImageAdapter(Context c)
        {

            ctx = c;
            TypedArray ta = obtainStyledAttributes(R.styleable.Gallery1);
            imageBackground = ta.getResourceId(
                    R.styleable.Gallery1_android_galleryItemBackground, 1);
            ta.recycle();
        }

        @Override
        public int getCount()
        {

            try
            {
                return results.size();
            } catch (NullPointerException e)
            {
                return 0;
            }
        }

        @Override
        public Object getItem(int arg0)
        {

            return results.get(arg0);
        }

        @Override
        public long getItemId(int arg0)
        {

            return arg0;
        }

        /**
         * Return the ImageView with the current element Uri
         * 
         * @param arg0
         *            (int)
         * @param arg1
         *            (View)
         * @param arg2
         *            (ViewGroup)
         * @return ImageView
         */
        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2)
        {

            ImageView iv = new ImageView(ctx);
            Uri pic = results.get(arg0).getPictureUri();
            iv.setImageDrawable(Drawable.createFromPath(pic.getPath()));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(new Gallery.LayoutParams(300, 300));
            iv.setBackgroundResource(imageBackground);
            return iv;
        }

    }

}