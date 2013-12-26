package com.stonesoup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartupActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		Intent registrationActivity = new Intent(getApplicationContext(),RegistrationActivity.class);
		startActivity(registrationActivity);
		finish();
	}
}
