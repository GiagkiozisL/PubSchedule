package com.stonesoup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;
import com.stonesoup.R;
import com.stonesoup.activities.CalendarFragment.Callbacks;

public class MainActivity extends FragmentActivity
	implements Callbacks{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//track statistics around application opens
		Parse.initialize(this, "ph2pneKQy22P3wQMnVeufRZfPzf69ZajhyAUH6e1", "5mIvqvTCez5LrynMYzg25A1XXwOXT9YDJUrZ216A");
		PushService.setDefaultPushCallback(this, MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return true;
	}

	@Override
	public void onDaySelected() {
		FragmentManager fm = getSupportFragmentManager();
		EventsListFragment listFragment = (EventsListFragment) fm.findFragmentById(R.id.list_fragment);
		listFragment.updateUI();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.log_out:
			Intent begin = new Intent(this, RegistrationActivity.class);
			startActivity(begin);
			finish();
			return true;
		case R.id.unavailability:
			Intent setUnavailabilityDays = new Intent(this, UnavailabilityDialog.class);
			startActivity(setUnavailabilityDays);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
//	private void showUnavailabilityDialog() {
//		Intent unavailable = new Intent();
//		startActivity(unavailable);
//		finish();
//		
//	}
}
