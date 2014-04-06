package com.stonesoup.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.Parse;
import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

public class StartupActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		Parse.initialize(this, 
				"ph2pneKQy22P3wQMnVeufRZfPzf69ZajhyAUH6e1", 
				"5mIvqvTCez5LrynMYzg25A1XXwOXT9YDJUrZ216A");
		checkForUpdates();
		Intent registrationActivity = new Intent(getApplicationContext(),RegistrationActivity.class);
		startActivity(registrationActivity);
		finish();
	}
	 @Override
	 protected void onResume() {
	   super.onResume();
	   checkForCrashes();
	 }

	 private void checkForCrashes() {
	   CrashManager.register(this, "77c16b18981b9e4aeb4534c884d3eea2");
	 }

	 private void checkForUpdates() {
	   // Remove this for store builds!
	   UpdateManager.register(this, "77c16b18981b9e4aeb4534c884d3eea2");
	 }
}
