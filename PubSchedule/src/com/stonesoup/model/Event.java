package com.stonesoup.model;

import java.util.UUID;

public class Event {

	private String name;
	private String date;
	private String position;
	private String flag;
	private String timezone;
	private String currentDate = null;
	private UUID mId;
	
	
	public Event(){
		mId = UUID.randomUUID();
	}
	
	public UUID getId() {
		return mId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

}
