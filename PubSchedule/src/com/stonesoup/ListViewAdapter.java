package com.stonesoup;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewAdapter extends BaseAdapter{

	private static final int WHITE = Color.rgb(255, 255, 255);
	private static final int YELLOW = Color.rgb(249, 191, 119);
	private static final int LIGHT_BLUE = Color.rgb(31, 172, 255);
	private static final int RED = Color.rgb(246, 125, 114);
	private static final int BLACK = Color.rgb(43, 43, 43);
	private static Typeface typeface;
	private EventPopulation eventPopulation;
	
	
	Context context;
	LayoutInflater inflater;
	ImageLoader imageLoader;
	
	private List<EventPopulation> eventPopulationList;
	private ArrayList<EventPopulation> arraylist;
	
	public ListViewAdapter(Context context,
            List<EventPopulation> eventPopulationList) {
        this.context = context;
        this.eventPopulationList = eventPopulationList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<EventPopulation>();
        this.arraylist.addAll(eventPopulationList);
    //    imageLoader = new ImageLoader(context);
    }
	
	@Override
	public void notifyDataSetChanged() {
		eventPopulation.getCurrentDate();
		super.notifyDataSetChanged();
	}
 
    public class ViewHolder {
    	LinearLayout row;
        TextView username;
        TextView position;
        TextView date;
        ImageView flag;
        ImageView timezone;
        ImageView worker;
    }
 
    @Override
    public int getCount() {
        return eventPopulationList.size();
    }
 
    @Override
    public Object getItem(int position) {
        return eventPopulationList.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            holder.row = (LinearLayout) view.findViewById(R.id.eventList);
            holder.timezone = (ImageView) view.findViewById(R.id.timezone);
            holder.worker = (ImageView) view.findViewById(R.id.worker);
            holder.username = (TextView) view.findViewById(R.id.user);
            holder.username.setTypeface(typeface);
            holder.date = (TextView) view.findViewById(R.id.date);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //Default Values
        holder.username.setText(eventPopulationList.get(position).getName());
       String selectedDate = eventPopulationList.get(position).getDate();
        
        if (eventPopulationList.get(position).getTimezone().equalsIgnoreCase("Night"))
        {
        holder.row.setBackgroundResource(R.drawable.background_view_rounded_night);
        holder.username.setTextColor(LIGHT_BLUE);
        holder.timezone.setBackgroundResource(R.drawable.moon);
        holder.worker.setBackgroundResource(R.drawable.worker);
        } else {
        	holder.row.setBackgroundResource(R.drawable.background_view_rounded_single);
            holder.username.setTextColor(BLACK);
            holder.timezone.setBackgroundResource(R.drawable.sun);
            holder.worker.setBackgroundResource(R.drawable.worker_blue);
        }
        
        if (eventPopulationList.get(position).getPosition().equalsIgnoreCase("Bar"))
        {
        	holder.worker.setBackgroundResource(R.drawable.bartender);
        } else if (eventPopulationList.get(position).getPosition().equalsIgnoreCase("Service"))
        {
        	holder.worker.setBackgroundResource(R.drawable.wairtress);
        } else if (eventPopulationList.get(position).getPosition().equalsIgnoreCase("Kitchen"))
        {
        	holder.worker.setBackgroundResource(R.drawable.chef);
        } else if (eventPopulationList.get(position).getPosition().equalsIgnoreCase("Dj"))
        {
        	holder.worker.setBackgroundResource(R.drawable.turntable);
        }
      
        // Listen for ListView Item Click  -future item detail view
//        view.setOnClickListener(new OnClickListener() {
// 
//            @Override
//            public void onClick(View arg0) {
//                // Send single item click data to SingleItemView Class
//                Intent intent = new Intent(context, SingleItemView.class);
//                // Pass all data rank
//                intent.putExtra("rank",
//                        (worldpopulationlist.get(position).getRank()));
//                // Pass all data country
//                intent.putExtra("country",
//                        (worldpopulationlist.get(position).getCountry()));
//                // Pass all data population
//                intent.putExtra("population",
//                        (worldpopulationlist.get(position).getPopulation()));
//                // Pass all data flag
//                intent.putExtra("flag",
//                        (worldpopulationlist.get(position).getFlag()));
//                // Start SingleItemView Class
//                context.startActivity(intent);
//            }
//        });
        return view;
    }
 
}