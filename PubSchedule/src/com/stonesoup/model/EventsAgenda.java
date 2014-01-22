package com.stonesoup.model;

import java.util.ArrayList;


public class EventsAgenda {
	private static EventsAgenda sEventsAgenda;
	private ArrayList<Event> mEvents = new ArrayList<Event>();
	
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
				if (event.getName().equals(e.getName())) {
					found = true;
					break;
				}
		}
		if (!found)
			mEvents.add(e);
	}
	
	public void deleteEvent(Event e){
		mEvents.remove(e);
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
}
