package com.stonesoup.activities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.stonesoup.utilities.ParseApplication;

public class RegistrationActivity extends Activity {

	private EditText username, passEditText;

	public static final String PREF_USERNAME = "username";

	private Button submit;
	String user, pass;
	final Context context = this;
	private ProgressDialog mProgressDialog;
	public static String myUserName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		username = (EditText) findViewById(R.id.usernameEditTxt);
		passEditText = (EditText) findViewById(R.id.passwordEditTxt);
		
		SimpleDateFormat sdf = new SimpleDateFormat("d/M/yy", Locale.US);
		String currentDateTime = sdf.format(new Date());
		ParseApplication.setDaySelected(currentDateTime);

		submit = (Button) findViewById(R.id.settingsSubmitBtn);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				user = username.getText().toString();
				pass = passEditText.getText().toString();
				// Send data to Parse.com for verification
				ParseUser.logInInBackground(user, pass, new LogInCallback() {
					public void done(ParseUser user, ParseException e) {
						if (user != null) {
							// If user exist and authenticated, send user to
							// Main.class
							Toast.makeText(getApplicationContext(),
									"Successfully Logged in", Toast.LENGTH_LONG)
									.show();
							EventsAgenda.get().setUsername(
									username.getText().toString());

							new RemoteDataTask().execute();

						} else {
							Toast.makeText(getApplicationContext(),
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
			Intent main = new Intent(RegistrationActivity.this,
					MainActivity.class);
			startActivity(main);
			RegistrationActivity.this.finish();
		}
	}
}
