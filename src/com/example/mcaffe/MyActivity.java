package com.example.mcaffe;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends ActionBarActivity {
	HashMap<String, String> hashmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		TextView t1,t2,t3,t4;
		RatingBar rb;
        Intent intent = getIntent();    
        hashmap = (HashMap<String, String>) intent.getSerializableExtra("hashMap");
        t1=(TextView)findViewById(R.id.textView);
        t1.setText(hashmap.get("name"));
        t2=(TextView)findViewById(R.id.textView3);
        t2.setText(hashmap.get("type")+"  "+hashmap.get("price"));
        t3=(TextView)findViewById(R.id.textView5);
        t3.setText(hashmap.get("users"));
        t4=(TextView)findViewById(R.id.textView8);
        t4.setText(hashmap.get("last_update"));
        rb=(RatingBar)findViewById(R.id.ratingBar);
        String str = hashmap.get("rating");
        float f=Float.parseFloat(str);
        rb.setRating(f);
        ImageView image = (ImageView) findViewById(R.id.imageView);    
        new DownloadImageTask(image).execute(hashmap.get("imagee"));
       
     //   String image_url = "http://fufuadarsh.bugs3.com/AndroidFileUpload/uploads/IMG_20150417_225657.jpg";
      //  ImageLoader imgLoader = new ImageLoader(getApplicationContext());
      //  imgLoader.DisplayImage(image_url, loader, image);
    }
    public void share(View v)
    {
    	Intent sendIntent = new Intent();
    	sendIntent.setAction(Intent.ACTION_SEND);
    	sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey,Download the McAfee Products App @ "+hashmap.get("url")+"  and Enjoy!");
    	sendIntent.setType("text/plain");
    	startActivity(sendIntent);
    }
    public void store(View v)
    {
    	
        Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(hashmap.get("url")));
        startActivity(browserIntent);
    }
    public void sms(View v)
    {
        Intent i = new Intent(this,SMSActivity.class);
        startActivity(i);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	  ImageView bmImage;

	  public DownloadImageTask(ImageView bmImage) {
	      this.bmImage = bmImage;
	  }

	  protected Bitmap doInBackground(String... urls) {
	      String urldisplay = urls[0];
	      Bitmap mIcon11 = null;
	      try {
	        InputStream in = new java.net.URL(urldisplay).openStream();
	        mIcon11 = BitmapFactory.decodeStream(in);
	      } catch (Exception e) {
	          Log.e("Error", e.getMessage());
	          e.printStackTrace();
	      }
	      return mIcon11;
	  }

	  protected void onPostExecute(Bitmap result) {
	      bmImage.setImageBitmap(result);
	  }
	}
