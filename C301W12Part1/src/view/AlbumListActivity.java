package view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.ca.c301.R;
import control.Controller;

/**
 * AlbumListActivity
 * 
 * Uses layout.main to display albums
 * 
 * @author J-Tesseract
 * 
 */
public class AlbumListActivity extends Activity
{

    // TODO Make more than album name appear. Some items could be album length,
    // the most current
    // photo could be as a thumbnail, last updated?
    // TODO: Make long click no longer needed

    /**
     * On Create
     * 
     * Calls onResume to update the Album list
     * 
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
    }

    /**
     * onResume
     * 
     * Sets the layout to the main.xml. Loads the list view into albumlistView.
     * Gets the count on the number of albums we currently have, if albcounter
     * is 0 then change the string to show that we have no albums. Create an
     * adapter for our albumList.
     */
    public void onResume()
    {

        super.onResume();
        setContentView(R.layout.main);

        ListView albumlistView = (ListView) findViewById(R.id.albumlist);
        int albcounter = Controller.getAlbumNames().length;
        String[] albumNames = Controller.getAlbumNames();

        ArrayAdapter<String> albListAdapter = new ArrayAdapter<String>(
                AlbumListActivity.this, android.R.layout.simple_list_item_1,
                albumNames);
        albumlistView.setAdapter(albListAdapter);

        if (albcounter == 0)
        {
            TextView albumsText = (TextView) findViewById(R.id.albumTextView);
            albumsText.setText("No Albums Available");
        }

        albumlistView.setClickable(true);
        albumlistView.setOnItemClickListener(new OnItemClickListener()
        {

            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {

                Intent intent = new Intent(AlbumListActivity.this,
                        GalleryActivity.class);
                Controller.setCurrentAlbum(position);
                startActivity(intent);
            }
        });
        Button editAlbumButton = (Button) findViewById(R.id.editAlbum);
        if (Controller.getAlbumNames().length > 0)
        {

            OnClickListener editAlbumsListener = new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {

                    editAlbum();

                }
            };
            editAlbumButton.setOnClickListener(editAlbumsListener);
        } else
        {
            editAlbumButton.setBackgroundColor(Color.TRANSPARENT);
            editAlbumButton.setText("");
        }
    }

    /**
     * editAlbum
     * 
     * Allows the user to edit the album and add an alarm to it
     * 
     */
    private void editAlbum()
    {

        Context context = getApplicationContext();
        CharSequence text = "Please select the album you wish to edit";
        int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(context, text, duration);// Final so
                                                                    // that it
                                                                    // can be
                                                                    // canceled
                                                                    // on second
                                                                    // click
        toast.show();
        ListView albListView = (ListView) findViewById(R.id.albumlist);
        albListView.setOnItemClickListener(new OnItemClickListener()
        {

            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {

                Intent editIntent = new Intent(AlbumListActivity.this,
                        AlbumEditActivity.class);
                Controller.setCurrentAlbum(position);
                startActivity(editIntent);
            }
        });
    }

}