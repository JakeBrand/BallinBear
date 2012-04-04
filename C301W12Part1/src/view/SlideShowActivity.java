package view;

import java.util.Timer;
import java.util.TimerTask;

import model.Album;
import control.Controller;
import ca.ualberta.ca.c301.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * SlideShowActivity
 * 
 * Progresses through all elements of the album in a Slide Show motion
 * 
 * @author J-Tesseract
 * 
 */
public class SlideShowActivity extends Activity {

	/**
	 * private fields used in methods they can't be passed to
	 */
	private Album alb;
	private Gallery ssg;
	private Handler autoGalleryHandler;

	/**
	 * onCreate
	 * 
	 * Update GUI and create timer
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.slideshowview);

		alb = Controller.getCurrentAlbum();

		Button back = (Button) findViewById(R.id.backToAlbumButton);
		OnClickListener backListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		};
		back.setOnClickListener(backListener);

		ssg = (Gallery) findViewById(R.id.slideshowgallery);
		ssg.setAdapter(new ImageAdapter(this));
		ssg.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				Toast.makeText(SlideShowActivity.this, "" + position, Toast.LENGTH_SHORT).show();
			}
		});

		Timer autoGallery = new Timer();
		autoGalleryHandler = new Handler() {
			public void handleMessage(Message message) {
				super.handleMessage(message);
				switch (message.what) {
				case 1:
					ssg.setSelection(message.getData().getInt("pos"));
					break;
				}
			}
		};
		autoGallery.schedule(new RemindTask(),0,1500);		

	}

	/**
	 * onResume
	 * 
	 * Refresh the album adapter if the current album has not been deleted
	 */
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

			i.setImageBitmap(bm);
			i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT - 50, LayoutParams.FILL_PARENT - 50));
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			return i;
		}

	}

	/**
	 * 
	 * @author J-Tesseract
	 * 
	 * Task to be run when timer is fired
	 *
	 */
	public class RemindTask extends TimerTask {
		int galleryposition = 0;

		public void run() {
			if (galleryposition < alb.getPhotos().size() - 1) {
				galleryposition = galleryposition + 1;
			} else {
				galleryposition = 0;
			}

			Message msg = new Message();
			Bundle date = new Bundle();
			date.putInt("pos", galleryposition);
			msg.setData(date);
			msg.what = 1;
			autoGalleryHandler.sendMessage(msg);
		}
	}

}
