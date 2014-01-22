package com.stonesoup.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

import com.parse.ParseAnalytics;
import com.stonesoup.R;
import com.stonesoup.activities.CalendarFragment.Callbacks;

public class MainActivity extends FragmentActivity
	implements Callbacks{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//track statistics around application opens
		ParseAnalytics.trackAppOpened(getIntent());
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onDaySelected() {
		FragmentManager fm = getSupportFragmentManager();
		EventsListFragment listFragment = (EventsListFragment) fm.findFragmentById(R.id.list_fragment);
		listFragment.updateUI();
	}
}
