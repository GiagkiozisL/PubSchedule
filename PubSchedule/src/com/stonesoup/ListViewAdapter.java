package com.stonesoup;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter{

	Context context;
	LayoutInflater inflater;
	ImageLoader imageLoader;
	
	private List<EventPopulation> eventPopulationList = null;
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
 
    public class ViewHolder {
        TextView username;
        TextView position;
        TextView date;
        ImageView flag;
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
            // Locate the TextViews in listview_item.xml
            holder.username = (TextView) view.findViewById(R.id.user);
            holder.position = (TextView) view.findViewById(R.id.position);
            holder.date = (TextView) view.findViewById(R.id.date);
            // Locate the ImageView in listview_item.xml
            holder.flag = (ImageView) view.findViewById(R.id.flag);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.username.setText(eventPopulationList.get(position).getName());
        holder.position.setText(eventPopulationList.get(position).getPosition());
        holder.position.setText(eventPopulationList.get(position)
                .getPosition());
        // Set the results into ImageView
//        imageLoader.DisplayImage(eventPopulationList.get(position).getFlag(),
//                holder.flag);
        // Listen for ListView Item Click
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