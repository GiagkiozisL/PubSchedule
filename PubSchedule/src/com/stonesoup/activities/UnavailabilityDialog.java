package com.stonesoup.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.stonesoup.R;
import com.stonesoup.controller.DatePicker;



public class UnavailabilityDialog extends Activity {
	
	
	static Typeface typeface;
	private DatePicker datePicker;
	private EditText year_display,month_display,date_display,hour_display,min_display;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.datepicker);
		
		typeface = Typeface.createFromAsset(getAssets(), "Exo-SemiBold.otf");
		year_display = (EditText) findViewById(R.id.year_display);
		year_display.setTypeface(typeface);
		month_display = (EditText) findViewById(R.id.month_display);
		month_display.setTypeface(typeface);
		date_display = (EditText) findViewById(R.id.date_display);
		date_display.setTypeface(typeface);
		
			

		
	}
	
}