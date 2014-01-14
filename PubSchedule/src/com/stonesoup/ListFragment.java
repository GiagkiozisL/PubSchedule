package com.stonesoup;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ListFragment extends Fragment{

	private static final int BLACK = Color.rgb(43, 43, 43);
	ListView listView;
	List<ParseObject> ob;
	ProgressDialog mProgressDialog;
	ListViewAdapter adapter;
	private List<EventPopulation> eventPopulationList = null;
	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		//setContentView(R.layout.list_fragment);
//	//	new RemoteDataTask().execute;
//	}
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.list_fragment,
	        container, false);
	    view.setBackgroundColor(BLACK);
	    new RemoteDataTask().execute();
	    return view;
	  }
	
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle("Retrieving Events from Database");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			
			eventPopulationList = new ArrayList<EventPopulation>();
			try {
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Program");
				query.orderByAscending("createdAt");
				ob = query.find();
				for (ParseObject Program : ob) {
					ParseFile image = (ParseFile) Program.get("flag");
					
					EventPopulation map = new EventPopulation();
					map.setName((String) Program.get("username"));
					map.setPosition((String) Program.get("position"));
					map.setDate((String) Program.get("date"));
					map.setTimezone((String) Program.get("situation"));
					eventPopulationList.add(map);
					Log.i("JSON  ", map.getDate());
				}
			} catch (ParseException e) {
				Log.e("Error" ,e.getMessage());
				e.printStackTrace();
			}
			return null;
		}	
		@Override
		protected void onPostExecute(Void result) {
	
			listView = (ListView) getView().findViewById(R.id.listView);        
		    adapter = new ListViewAdapter(getActivity(), eventPopulationList); 

		    for (ParseObject country : ob) {
		    	Log.i("TAG", "ouououou");
//		    	adapter.
//		        adapter.add((String) country.get("country"));
		    }       
		    listView.setAdapter(adapter);       
		    mProgressDialog.dismiss();
			
//	View rootaView = inflater.inflate(R.layout.list_fragment,
//			container, false);
//			listView = (ListView) rootaView.findViewById(R.id.list_fragment);
//			
//			adapter = new ListViewAdapter(getActivity(),eventPopulationList);
//			listView.setAdapter(adapter);
//			mProgressDialog.dismiss();
			
		}
	}
	
}
