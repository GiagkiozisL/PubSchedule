package com.stonesoup;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseUser;

public class ParseApplication extends Application{

	private static final String YOUR_APPLICATION_ID = "ph2pneKQy22P3wQMnVeufRZfPzf69ZajhyAUH6e1";
	private static final String YOUR_CLIENT_KEY = "5mIvqvTCez5LrynMYzg25A1XXwOXT9YDJUrZ216A";
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
		ParseUser.enableAutomaticUser();
	//	ParseACL defaultACL = new ParseACL;
		
	//	defaultACL.setPublicReadAccess(true);
	//	ParseACL.setDefaultACL(defaultACL, true);
	}
}
