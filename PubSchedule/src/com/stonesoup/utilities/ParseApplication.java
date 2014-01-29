package com.stonesoup.utilities;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseUser;
import com.testflightapp.lib.TestFlight;

public class ParseApplication extends Application{

	private static final String YOUR_APPLICATION_ID = "ph2pneKQy22P3wQMnVeufRZfPzf69ZajhyAUH6e1";
	private static final String YOUR_CLIENT_KEY = "5mIvqvTCez5LrynMYzg25A1XXwOXT9YDJUrZ216A";
	public static String sDaySelected;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
		ParseUser.enableAutomaticUser();
		TestFlight.takeOff(this, "1d9c1f09-332d-40b5-8b7d-024529b4a2c5");
	
	}

	public static String getDaySelected() {
		return sDaySelected;
	}

	public static void setDaySelected(String daySelected) {
		sDaySelected = daySelected;
	}
	
}
