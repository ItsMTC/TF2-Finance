package net.treehousetech.tf2finance;

import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class KeyFragment extends Fragment{

	Global global;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
		  if(container == null){
			  return null;
		  }
		  
		  global = (Global)getActivity().getApplicationContext();
		  
		 // WebView web = (WebView) this.getActivity().findViewById(R.id.webView1);
		  //web.loadUrl("http://backpack.tf/classifieds");
	    return inflater.inflate(R.layout.key_fragment,
		        container, false);
	  }
	
	public void showAd(){
		AdView adView = (AdView)this.getActivity().findViewById(R.id.keyAd);
	    AdRequest adRequest = new AdRequest.Builder()
	        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	        .addTestDevice("6B03994C8F5BFE2724566E2A66946919")
	        .addTestDevice("1C734B95CC0B093539435C66235FCB45")
	        .build();
	    adView.loadAd(adRequest);
	}
	
	@Override
	public void onActivityCreated(Bundle bund){
		super.onActivityCreated(bund);
		EasyTracker.getInstance().activityStart(getActivity());
		setText();
		showLoading();
		if(global.showAds){
			showAd();
		}
		new updateKeys(this.getActivity()).execute();
	}
	
	public void setText(){
		
		Typeface tf2 = Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/TF2.ttf");
		TextView budPrice = (TextView) this.getActivity().findViewById(R.id.keyPrice);
		TextView budTime = (TextView) this.getActivity().findViewById(R.id.keyTime);
		TextView usdPrice = (TextView) this.getActivity().findViewById(R.id.usdPrice);
		TextView usdTime = (TextView) this.getActivity().findViewById(R.id.usdTime);
		TextView title = (TextView) this.getActivity().findViewById(R.id.keyTitle);
		TextView market = (TextView) this.getActivity().findViewById(R.id.keyMarket);
		TextView keyChange = (TextView) this.getActivity().findViewById(R.id.keyChange);
		TextView keyUsdChange = (TextView) this.getActivity().findViewById(R.id.keyUsdChange);
		budPrice.setTypeface(tf2);
		budTime.setTypeface(tf2);
		usdPrice.setTypeface(tf2);
		usdTime.setTypeface(tf2);
		title.setTypeface(tf2);
		market.setTypeface(tf2);
		keyChange.setTypeface(tf2);
		keyUsdChange.setTypeface(tf2);
	}
	
	public void showLoading(){
		ProgressBar spin = (ProgressBar) this.getActivity().findViewById(R.id.keySpinner);
		spin.setVisibility(View.VISIBLE);
		TextView keyPrice = (TextView) this.getActivity().findViewById(R.id.keyPrice);
		keyPrice.setText("Loading...");
		TextView keyTime = (TextView) this.getActivity().findViewById(R.id.keyTime);
		keyTime.setText("Loading...");
		TextView usdPrice = (TextView) this.getActivity().findViewById(R.id.usdPrice);
		usdPrice.setText("Loading...");
		TextView usdTime = (TextView) this.getActivity().findViewById(R.id.usdTime);
		usdTime.setText("Loading...");
	}
	
	private class ImgDownload extends AsyncTask<String, Void, String> {
	    private String requestUrl;
	    private ImageView view;
	    private Bitmap pic;
	    private Activity a;
	    private boolean dled;
	    
	    private ImgDownload(String requestUrl, ImageView view, Activity a) {
	        this.requestUrl = requestUrl;
	        this.view = view;
	        this.a = a;
	    }

	    @Override
	    protected String doInBackground(String... objects) {
	        try {
	            URL url = new URL(requestUrl);
	            URLConnection conn = url.openConnection();
	            pic = BitmapFactory.decodeStream(conn.getInputStream());
	            dled = true;
	        } catch (Exception ex) {
	        	dled = false;
	        }
	        return null;
	    }

	    @Override
	    protected void onPostExecute(String o) {
	    	if(dled){
	    	Display display = a.getWindowManager().getDefaultDisplay();
	    	Point size = new Point();
	    	display.getSize(size);
	    	int width = pic.getWidth();
	    	int height = pic.getHeight();
	    	float scaleWidth = ((float) size.x) / width;
	    	Matrix ma = new Matrix();
	    	ma.postScale(scaleWidth, scaleWidth);
	    	Bitmap tmp = Bitmap.createBitmap(pic, 0, 0, width, height, ma, false);
	        view.setImageBitmap(tmp);
	    	}
	        /*view.setOnClickListener(new OnClickListener() {
	        	public void onClick(View v){
	        		
	        	}
	        });*/
	    }
	}
	
	private class updateKeys extends AsyncTask<String, Void, String> {
		
		private String varTime;
		private String usdTime1;
		private String varPrice;
		private String usdPrice1;
		private Activity a;
		private boolean dled;
		
		private updateKeys(Activity t){
			a = t;
		}
		
		@Override
	    protected String doInBackground(String... objects) {
	        try {
	        	String rets = global.getRawItemData();
	    		String[] ret = rets.split("!");
	    		varPrice = ret[2].split(":")[0];
	    		varTime = ret[3].split(":")[0];
	    		usdPrice1 = ret[2].split(":")[1];
	    		usdTime1 = ret[3].split(":")[1];
	    		dled = true;
	        } catch (Exception ex) {
	        	dled = false;
	        }
	        return null;
	    }

	    @Override
	    protected void onPostExecute(String o) {
	    	SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
	    	SharedPreferences.Editor editor = sharedPref.edit();
	    	TextView keyPrice = (TextView) a.findViewById(R.id.keyPrice);
	    	TextView keyTime = (TextView) a.findViewById(R.id.keyTime);
	    	TextView usdPrice = (TextView) a.findViewById(R.id.usdPrice);
	    	TextView usdTime = (TextView) a.findViewById(R.id.usdTime);
	    	TextView keyChange = (TextView) a.findViewById(R.id.keyChange);
			TextView keyUsdChange = (TextView) a.findViewById(R.id.keyUsdChange);
			ProgressBar spin = (ProgressBar) a.findViewById(R.id.keySpinner);
	    	if(dled){
			keyPrice.setText("1 Key = " + varPrice + " Ref");
			keyTime.setText(varTime);
			editor.putFloat("keyLast", Float.parseFloat(varPrice));
			usdPrice.setText("1 Key = " + usdPrice1 + " USD");
			editor.putFloat("keyUsdLast", 0f);
			usdTime.setText(usdTime1);
			calcChanged(sharedPref.getFloat("keyLast", Float.parseFloat(varPrice)), Float.parseFloat(varPrice), keyChange);
			calcChanged(sharedPref.getFloat("keyUsdLast", 0f), 0f, keyUsdChange);
			ImageView img = (ImageView) a.findViewById(R.id.keyImage);
			new ImgDownload("http://tf2finance.com/live_keys.png", img, a).execute();
	    	} else {
	    		keyPrice.setText("Cannot Load Data");
	    		usdPrice.setText("Cannot Load Data");
	    		usdTime.setText("Cannot Load Data");
	    		keyTime.setText("Cannot Load Data");
	    	}
	    	editor.commit();
			spin.setVisibility(View.INVISIBLE);
	    }
	    
	    private void calcChanged(Float last, Float now, TextView text){
	    	Float a = now - last;
	    	DecimalFormat df = new DecimalFormat("#.##");
	    	double s = (double)Math.round(a * 100) / 100;
	    	if(s > 0){
	    		text.setTextColor(0xFF00FF00);
	    		text.setText("+" + df.format(s));
	    	} else if(s < 0){
	    		text.setTextColor(0xFFFF0000);
	    		text.setText(df.format(s));
	    	} else if(s == 0f){
	    		text.setTextColor(0xFF000000);
	    		text.setText("= 0.00");
	    	}
	    }
		
	}
	
}
