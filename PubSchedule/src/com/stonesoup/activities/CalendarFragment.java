package com.stonesoup.activities;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stonesoup.R;
import com.stonesoup.controller.CalendarAdapter;
import com.stonesoup.utilities.ParseApplication;

public class CalendarFragment extends Fragment {

	public GregorianCalendar month, itemmonth;// calendar instances.
	public CalendarAdapter adapter;// adapter instance
	public Handler handler;// for grabbing some event values for showing the dot marker.
	public ArrayList<String> items; // container to store calendar items which needs showing the event marker
	private Callbacks mCallbacks;
	
	public interface Callbacks{
		void onDaySelected();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mCallbacks = (Callbacks) activity;
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.calendar, null);
	
		Locale.setDefault(Locale.US);
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		items = new ArrayList<String>();
		adapter = new CalendarAdapter(getActivity(), month);		
		
		GridView gridview = (GridView) view.findViewById(R.id.gridview);
		gridview.setAdapter(adapter);

		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

		RelativeLayout previous = (RelativeLayout) view.findViewById(R.id.previous);
		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});

		RelativeLayout next = (RelativeLayout) view.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();
			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				String selectedGridDate = CalendarAdapter.dayString.get(position);
				ParseApplication.setDaySelected(selectedGridDate);
				mCallbacks.onDaySelected();
				String[] gridvalueString = selectedGridDate.split("/");
				int dayValue = Integer.parseInt(gridvalueString[1]);
				// navigate to next or previous month on clicking offdays.
				if ((dayValue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((dayValue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
			}
		});
		return view;
	}

	protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1), month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH, month.get(GregorianCalendar.MONTH) + 1);
		}

	}

	protected void setPreviousMonth() {
		if (month.get(GregorianCalendar.MONTH) == month.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1), month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH, month.get(GregorianCalendar.MONTH) - 1);
		}
	}

	public void refreshCalendar() {
		TextView title = (TextView) getActivity().findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
		adapter.refreshDays();
		adapter.notifyDataSetChanged();
	}
}