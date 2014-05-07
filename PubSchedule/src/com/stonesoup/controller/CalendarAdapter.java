package com.stonesoup.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stonesoup.R;
import com.stonesoup.model.EventsAgenda;
import com.stonesoup.utilities.ParseApplication;

public class CalendarAdapter extends BaseAdapter {
	private Context mContext;

	private java.util.Calendar month;
	public GregorianCalendar pMonth;
	public GregorianCalendar pMonthMaxSet;
	private int weekDayThatMonthStarts;
	private int calendarWeekSpan;
	private int previousMonthLastDay;
	private int calMaxP;
	private int mnthLength;
	private String curentDateString;
	private DateFormat df =  new SimpleDateFormat("yyyy-dd-MM", Locale.US);
	private static final int LIGHTBLUE = Color.rgb(31, 172, 255);
	private static final int DARKRED = Color.rgb(187, 56, 80);
	private ArrayList<String> items = new ArrayList<String>();
	private ArrayList<String> mUserWorkDays = new ArrayList<String>();
	public static ArrayList<String> dayString = new ArrayList<String>();
	private View previousView;

	public CalendarAdapter(Context c, GregorianCalendar monthCalendar) {
		mContext = c;
		Locale.setDefault(Locale.US);
		month = monthCalendar;
		String d = df.format(month.getTime());
		curentDateString = formatDate(d.split("-"));
		month.set(GregorianCalendar.DAY_OF_MONTH, 1);
		mUserWorkDays = EventsAgenda.get().getUserWorkDays();
		refreshDays();
	}

	public int getCount() {
		return dayString.size();
	}

	public Object getItem(int position) {
		return dayString.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.calendar_item, null);
		}

		boolean isUserWorkDay = false;
		
		String dateString = dayString.get(position);
		for (String s : mUserWorkDays) {
			if(s.equals(dateString))
				isUserWorkDay = true;
		}

		
		// checking whether the day is in current month or not.
		TextView dayView = (TextView) convertView.findViewById(R.id.date);
		String[] str = dateString.split("/");
		int gridValue = Integer.parseInt(str[0]);
		if ((gridValue > 1) && (position < weekDayThatMonthStarts)) {
			// setting offdays to white color.
			dayView.setTextColor(Color.WHITE);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		} else if ((gridValue < 7) && (position > 28)) {
			dayView.setTextColor(Color.WHITE);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		} else if (isUserWorkDay) {
			dayView.setTextColor(DARKRED);
		} else {
			// setting curent month's days in blue color.
			dayView.setTextColor(LIGHTBLUE);
		}
		dayView.setText(str[0]);
		
		if (dayString.get(position).equals(curentDateString)) {
			ParseApplication.setDaySelected(curentDateString);
			setSelected(convertView);
			previousView = convertView;
		} else {
			convertView.setBackgroundResource(R.drawable.list_item_background);
		}
		

		// create date string for comparison
		String date = dayString.get(position);
		if (date.length() == 1) {
			date = "0" + date;
		}
		
		String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
		if (monthStr.length() == 1) {
			monthStr = "0" + monthStr;
		}

		// show icon if date is not empty and it exists in the items array
		ImageView iw = (ImageView) convertView.findViewById(R.id.date_icon);
		if (date.length() > 0 && items != null && items.contains(date)) {
			iw.setVisibility(View.VISIBLE);
		} else {
			iw.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	public View setSelected(View view) {
		if (previousView != null) {
			previousView.setBackgroundResource(R.drawable.list_item_background);
		}
		previousView = view;
		view.setBackgroundResource(R.drawable.calendar_cel_selectl);
		return view;
	}

	public void refreshDays() {
		items.clear();
		dayString.clear();
		pMonth = (GregorianCalendar) month.clone();
		// month start day. ie; sun : 1, mon:2 etc
		weekDayThatMonthStarts = month.get(GregorianCalendar.DAY_OF_WEEK);
		// finding number of weeks in current month.
		calendarWeekSpan = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
		// allocating maximum row number for the gridview.
		mnthLength = calendarWeekSpan * 7;
		previousMonthLastDay = getMaxDayOfPreviousMonth(); // previous month maximum day 31,30....
		/* calendar offday starting 24,25 ...
		 * the -1 is so that there is at least one day in the top week shown
		 */
		calMaxP = previousMonthLastDay - (weekDayThatMonthStarts - 1);
		/*Calendar instance for getting a complete gridview including the three
		 * month's (previous,current,next) dates.
		 */
		pMonthMaxSet = (GregorianCalendar) pMonth.clone();
		/* setting the start date as previous month's required date.
		 */
		pMonthMaxSet.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);
		/* filling calendar gridview.
		 */
		for (int n = 0; n < mnthLength; n++) {
			String[] i = df.format(pMonthMaxSet.getTime()).split("-");
			pMonthMaxSet.add(GregorianCalendar.DATE, 1);
			dayString.add(formatDate(i));
		}
	}
	
	private String formatDate(String[] s){
		StringBuilder l = new StringBuilder();
//		if(s[1].startsWith("0")){
//			l.append(s[1].replaceFirst("0", ""));
//		} else {
			l.append(s[1]);
		//}
		l.append("/");
//		if(s[2].startsWith("0")){
//			l.append(s[2].replaceFirst("0", ""));
//		} else
//		{
			l.append(s[2]);
		//}
		l.append("/");
		l.append(s[0].substring(2, 4));
		return l.toString();
	}

	private int getMaxDayOfPreviousMonth() {
		int maxP;
		if (month.get(GregorianCalendar.MONTH) == month.getActualMinimum(GregorianCalendar.MONTH)) {
			pMonth.set((month.get(GregorianCalendar.YEAR) - 1), month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			pMonth.set(GregorianCalendar.MONTH, month.get(GregorianCalendar.MONTH) - 1);
		}
		maxP = pMonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

		return maxP;
	}
}