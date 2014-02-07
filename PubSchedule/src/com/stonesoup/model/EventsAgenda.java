package com.stonesoup.model;

import java.util.ArrayList;


public class EventsAgenda {
	private static EventsAgenda sEventsAgenda;
	private ArrayList<Event> mEvents = new ArrayList<Event>();
	private ArrayList<String> mUserWorkDays = new ArrayList<String>();
	private String mUsername;
	
	private EventsAgenda(){}
	
	public static EventsAgenda get(){
		if(sEventsAgenda == null){
			sEventsAgenda = new EventsAgenda();
		}
		return sEventsAgenda;
	}
	
	public void addEvent(Event e) {
		boolean found = false;
		for (Event event : mEvents) {
				if (event.getId().equals(e.getId())) {
					found = true;
					break;
				}
		}
		if (!found){
			mEvents.add(e);
			
			if(e.getName().equals(mUsername))
				mUserWorkDays.add(e.getDate());
		}
	}
	
	public void deleteEvent(Event e){
		mEvents.remove(e);
	}
	
	public void empryEventsAgenda(){
		mEvents.clear();
	}

	public ArrayList<Event> getEvents() {
		return mEvents;
	}
	
	public Event getEvent(String friendName){
		for(Event e :mEvents){
			if(e.getName().equals(friendName))
				return e;
		}
		return null;
	}

	public ArrayList<String> getUserWorkDays() {
		return mUserWorkDays;
	}
	
	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String username) {
		mUsername = username;
	}

	
}
