package com.stonesoup.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.stonesoup.R;
import com.stonesoup.model.Event;
import com.stonesoup.model.EventsAgenda;
import com.stonesoup.utilities.ParseApplication;

public class EventsListFragment extends ListFragment {

	private static final int BLACK = Color.rgb(43, 43, 43);
	private static final int LIGHT_BLUE = Color.rgb(31, 172, 255);
	
	ListView listView;
	List<ParseObject> ob;
	ProgressDialog mProgressDialog;
	private static Typeface typeface;
	private ArrayList<Event> mEvents = new ArrayList<Event>();
	private ArrayList<Event> mSelectedDateEvents = new ArrayList<Event>();
	
	public void updateUI(){
		String selectedDay = ParseApplication.getDaySelected();
		if(selectedDay.isEmpty()) return;
		mSelectedDateEvents.clear();
		for(Event e : mEvents){
			if(e.getDate().equals(selectedDay))
				mSelectedDateEvents.add(e);
		}
		((EventsAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mEvents = EventsAgenda.get().getEvents();
		
		EventsAdapter adapter = new EventsAdapter(mSelectedDateEvents);
		setListAdapter(adapter);
		setRetainInstance(true);
		}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_events_list, null);
		v.setBackgroundColor(BLACK);
		return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updateUI();
	}
	
private class EventsAdapter extends ArrayAdapter<Event>{
		
		public EventsAdapter(ArrayList<Event> events){
			super(getActivity(), 0, events);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null){
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_event, null);
				}
			
			Event e = getItem(position);
			
			LinearLayout rowLinearLayout = (LinearLayout) convertView.findViewById(R.id.event_list_item);
			ImageView timezoneImageView = (ImageView) convertView.findViewById(R.id.timezone);
			ImageView workerImageView = (ImageView) convertView.findViewById(R.id.worker);
			TextView userNameTextView = (TextView) convertView.findViewById(R.id.user);
			userNameTextView.setTypeface(typeface);
			userNameTextView.setText(e.getName());
			
			if (e.getTimezone().equalsIgnoreCase("Night")) {
				rowLinearLayout.setBackgroundResource(R.drawable.background_view_rounded_night);
				userNameTextView.setTextColor(LIGHT_BLUE);
				timezoneImageView.setBackgroundResource(R.drawable.moon);
				workerImageView.setBackgroundResource(R.drawable.worker);
			} else {
				rowLinearLayout.setBackgroundResource(R.drawable.background_view_rounded_single);
				userNameTextView.setTextColor(BLACK);
				timezoneImageView.setBackgroundResource(R.drawable.sun);
				workerImageView.setBackgroundResource(R.drawable.worker_blue);
			}

			if (e.getPosition().equalsIgnoreCase("Bar")) {
				workerImageView.setBackgroundResource(R.drawable.bartender);
			} else if (e.getPosition().equalsIgnoreCase("Service")) {
				workerImageView.setBackgroundResource(R.drawable.wairtress);
			} else if (e.getPosition().equalsIgnoreCase("Service 2nd")) {
				workerImageView.setBackgroundResource(R.drawable.wairtress2nd);
			} else if (e.getPosition().equalsIgnoreCase("Kitchen")) {
				workerImageView.setBackgroundResource(R.drawable.chef);
			} else if (e.getPosition().equalsIgnoreCase("Dj")) {
				workerImageView.setBackgroundResource(R.drawable.turntable);
			}
			
			return convertView;
		}
}
}
