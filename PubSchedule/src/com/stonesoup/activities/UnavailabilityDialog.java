package com.stonesoup.activities;

import android.app.Activity;
import android.content.Intent;
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

import com.stonesoup.R;
import com.stonesoup.controller.DatePicker;



public class UnavailabilityDialog extends Activity {
	
	public interface EditMeetingDialogListener {
		void onFinishEditMeetingDialog(String date);
	}
	
	
	static Typeface typeface;
	private DatePicker datePicker;
	String date;
	private EditText year_display,month_display,date_display;
	Button createBtn,cancelBtn;
	
	@Override
	public void  onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.unavailability_activity);
		typeface = Typeface.createFromAsset(getAssets(), "Exo-SemiBold.otf");
		year_display = (EditText) findViewById(R.id.year_display);
		year_display.setTypeface(typeface);
		month_display = (EditText) findViewById(R.id.month_display);
		month_display.setTypeface(typeface);
		date_display = (EditText) findViewById(R.id.date_display);
		date_display.setTypeface(typeface);
		date_display.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
					EditMeetingDialogListener activity = (EditMeetingDialogListener)getApplicationContext();
                    activity.onFinishEditMeetingDialog(datePicker.year_watcher+"-"+datePicker.months+"-"+datePicker.date_watcher.toString());
                    return true;
                }
				return false;
			}
		});

		datePicker = (DatePicker) findViewById(R.id.dateEvent);
		
		  createBtn = (Button) findViewById(R.id.unavSend);
		createBtn.setTypeface(typeface);
		createBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				date = date_display.toString();
				Intent back = new Intent(UnavailabilityDialog.this ,MainActivity.class);
				startActivity(back);
				finish();
			
		
				
			}
		});
		cancelBtn = (Button) findViewById(R.id.unavCancel);
		cancelBtn.setTypeface(typeface);
		cancelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent back = new Intent(UnavailabilityDialog.this ,MainActivity.class);
				startActivity(back);
				finish();
				
			}
		});
	super.onCreate(savedInstanceState);
	}
//	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		 if (EditorInfo.IME_ACTION_DONE == actionId) {
	            // Return input text to activity
	            EditMeetingDialogListener activity = (EditMeetingDialogListener) getApplicationContext();
	            activity.onFinishEditMeetingDialog(date.toString());
	            this.finish();
	            return true;
	        }
	        return false;
	}	
}