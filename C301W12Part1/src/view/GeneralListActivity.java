package view;


import java.util.ArrayList;

import control.Controller;

import model.Album;
import model.GenralListAdapter;
import model.Item;


//import android.R;
import android.app.ListActivity;
import android.os.Bundle;
import ca.ualberta.ca.c301.R;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;


//#################################### This activity can be used to list anything and react when an item is selected
public class GeneralListActivity extends ListActivity {
        /** Called when the activity is first created. */
    
    ArrayList<Item> items;
    
    
        @SuppressWarnings({ "unchecked", "static-access" })
        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
               // setContentView(R.layout.genlist);
                
                
                Bundle bundle = getIntent().getExtras();
                
                
//                String hi = (String) bundle.get("hello");
//                Log.e("hey", hi);
                
                try{
                    items = toItem((ArrayList<Album>) bundle.get("gal"));
                   
                } catch(Exception e){Log.i("Error at bundle", e.toString());}
                
                registerForContextMenu(getListView());
                
                
                ListView lv = getListView();
                
                
                
                
                //
                // Create our own version of the list adapter
                
                
                ListAdapter adapter;
                Log.e("hey", items.get(0).KEY_STRING1); 
                
                
                
                if(items.get(0).KEY_STRING2 != null){
                adapter = new GenralListAdapter(this, items, android.R.layout.simple_list_item_2, new String[] {
                                Item.KEY_STRING1, Item.KEY_STRING2 }, new int[] { android.R.id.text1, android.R.id.text2 });
                
                }
                else{
                adapter = new GenralListAdapter(this, items, android.R.layout.simple_list_item_2, new String[] {
                            Item.KEY_STRING1}, new int[] { android.R.id.text1});
            
                }
                
                this.setListAdapter(adapter);
            
          lv.setOnItemClickListener(new OnItemClickListener() {
              public void onItemClick(AdapterView<?> parent, View view,
                  int position, long id) {
                // When clicked, show a toast with the TextView text
                  Log.e(null, "You clicked an item with position:" + position + " and id: " + id);
                  Log.e(null, "So you clicked:" + items.get(position).string1); 
                  
                  // TODO Now that I can get stuff back from this object, I need to send it back to know what to do with it.
              }
            });
        }
        
        
        
        private ArrayList<Item> toItem(ArrayList<Album> gal){
            ArrayList<Item> items = new ArrayList<Item>();
            for(int i = 0; i < gal.size(); i++){
                items.add(new Item(gal.get(i), gal.get(i).getAlbumName(), "" + gal.get(i).getPhotos().size()));
                
            }
            return items;
            
        }





            @Override
            public boolean onMenuItemSelected(int featureId, MenuItem item) {
                 // created a switch to open to more functionality
                    Log.e(null, "You clicked me!");
                        return true;

            }
        
            
            
            
            public void onPause(){
                super.onPause();
                Controller.saveObject(this);
                
            }
            
            
        
        

}