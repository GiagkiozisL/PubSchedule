package com.stonesoup;

import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends Activity{

	private EditText username,passEditText;
	private Button submit;
	private EventPopulation eventPopulation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
		eventPopulation = new EventPopulation();
		eventPopulation.setCurrentDate(currentDateTimeString);
		username = (EditText) findViewById(R.id.usernameEditTxt);
		passEditText = (EditText) findViewById(R.id.passwordEditTxt);
		
		submit = (Button) findViewById(R.id.settingsSubmitBtn);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				//just for naw , move on and take me to main
				Intent main  = new Intent(RegistrationActivity.this, MainActivity.class);
				startActivity(main);
				
			}
		});
		
	}
	
}
