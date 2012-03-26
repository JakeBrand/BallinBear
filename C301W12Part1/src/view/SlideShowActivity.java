package view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import model.Album;
import sun.awt.image.ImageAccessException;
import view.GalleryActivity.ImageAdapter;
import control.Controller;
import ca.ualberta.ca.c301.R;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class SlideShowActivity extends Activity {

	private Album alb;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.slideshowview);

		alb = Controller.getCurrentAlbum();

		// TODO SlideShowActivity: Actually implement slideshow given Album

		Button back = (Button) findViewById(R.id.backToAlbumButton);

		OnClickListener backListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
			    finish();
			}

		};
		back.setOnClickListener(backListener);

		Gallery ssg = (Gallery) findViewById(R.id.slideshowgallery);

		ssg.setAdapter(new ImageAdapter(this));
		ssg.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				Toast.makeText(SlideShowActivity.this, "" + position, Toast.LENGTH_SHORT).show();
			}
		});

	}

	public void onPause() {
		super.onPause();
		Controller.saveObject();

	}

	public void onResume() {
		super.onResume();

		if (Controller.getCurrentAlbumIndex() == -1) {
			finish();
			return;
		}
		alb = Controller.getCurrentAlbum();

	}

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;



		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return alb.getPhotos().size();
		}

		public Object getItem(int position) {
			return alb.getPhotos().get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			ImageView i = new ImageView(mContext);
			Uri pic = alb.getPhotos().get(position).getPicture();
			i.setImageURI(pic);

			Bitmap bm = BitmapFactory.decodeFile(pic.getPath());

			// i.setImageResource((int) ContentUris.parseId(pic));
			i.setImageBitmap(bm);
			i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT - 50, LayoutParams.FILL_PARENT - 50));
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			return i;
		}

	}

}
