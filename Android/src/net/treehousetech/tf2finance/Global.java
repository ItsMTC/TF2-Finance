package net.treehousetech.tf2finance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Application;

public class Global extends Application {
	public boolean showAds = true;
	
	public String getRawItemData(){
		return getData("http://backend.treehousetech.net/tf2finance/tf2financeprice.php");
	}
	
	public String getRawItemData(int itemid){
		return getData("http://backend.treehousetech.net/tf2finance/tf2financeprice.php?itemid="+itemid);
	}
	
	public String getData(String urls){
		try {
		URL url = new URL(urls);
        HttpURLConnection conn = 
                (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        
		return reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	
}
