package net.treehousetech.tf2finance;

import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchFragment extends Fragment {

	Global global;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
		  if(container == null){
			  return null;
		  }
		  
		  global = (Global)getActivity().getApplicationContext();
		  
		 
		  
	    return inflater.inflate(R.layout.search_fragment,
	        container, false);
	  }
	
	public void onStart(){
		super.onStart();
		EasyTracker.getInstance().activityStart(getActivity());
		
	}
	
}
