package com.stonesoup.activities;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.stonesoup.R;
import com.stonesoup.model.Event;
import com.stonesoup.model.EventsAgenda;

public class RegistrationActivity extends Activity{

	private EditText username,passEditText;
	final Context context = this;
	String user,pass;
	private Button submit;
	private Event eventPopulation;
	private ProgressDialog mProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
		eventPopulation = new Event();
		eventPopulation.setCurrentDate(currentDateTimeString);
		username = (EditText) findViewById(R.id.usernameEditTxt);
		passEditText = (EditText) findViewById(R.id.passwordEditTxt);
		user = username.toString();
		pass = passEditText.toString();
		submit = (Button) findViewById(R.id.settingsSubmitBtn);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkNetworkStatus();
				user = username.getText().toString();
				pass = passEditText.getText().toString();
				
				 // Send data to Parse.com for verification
                ParseUser.logInInBackground(user, pass,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    // If user exist and authenticated, send user to Welcome.class
                                    Intent intent = new Intent(RegistrationActivity.this,
                                            MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully Logged in",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "No such user exist, please signup",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
			}
		});
	}
	
	private Event createEvent(ParseObject p) {
		Event event = new Event();
		if (p.get("username") != null)
			event.setName((String) p.get("username"));
		if (p.get("position") != null)
			event.setPosition((String) p.get("position"));
		if (p.get("date") != null)
			event.setDate((String) p.get("date"));
		if (p.get("situation") != null)
			event.setTimezone((String) p.get("situation"));
		
		return event;
	}
	
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(RegistrationActivity.this);
			mProgressDialog.setTitle("Retrieving Events from Database");
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Program");
				query.orderByAscending("createdAt");
				List<ParseObject> ob = query.find();
				for (ParseObject p : ob) {
					Event event = createEvent(p);
					EventsAgenda.get().addEvent(event);
					Log.i("JSON  ", event.getDate());
				}
			} catch (ParseException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mProgressDialog.dismiss();
			ArrayList<Event> mEv = EventsAgenda.get().getEvents();
			Intent main  = new Intent(RegistrationActivity.this, MainActivity.class);
			startActivity(main);
			RegistrationActivity.this.finish();
		}
	}
	
	public void  checkNetworkStatus(){

	    final ConnectivityManager connMgr = (ConnectivityManager)
	    this.getSystemService(Context.CONNECTIVITY_SERVICE);
	    final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	
	    if( wifi.isAvailable() ){
	    	Toast.makeText(this, "Wifi" , Toast.LENGTH_LONG).show();
	     }
	     else if( mobile.isAvailable() ){
	    	 Toast.makeText(this, "Mobile 3G " , Toast.LENGTH_LONG).show();
	     }
	   else
	    {
	     Toast.makeText(this, "No Network Connection" , Toast.LENGTH_LONG).show();
	 	  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	
	     alertDialogBuilder.setTitle("No Internet Connection");
	    alertDialogBuilder
	    .setMessage("Please check your Wi-Fi or Cellular settings!")
	    .setCancelable(false)
	    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
	   public void onClick(DialogInterface dialog,int id) {
	      startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
	
	    }
	    });
	
	    AlertDialog alertDialog = alertDialogBuilder.create();
	    alertDialog.show();
	    Toast.makeText(this, "No Network " , Toast.LENGTH_LONG).show();
	   }
	  }

}
