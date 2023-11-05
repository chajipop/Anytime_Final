package com.naver.anytime.domain;

public class Calendar {
//	private int CALENDAR_ID;
//	private int USER_ID;
//	private String SUBJECT;
//	private String TYPE;
//	private String START_TIME;
//	private String END_TIME;
//	private String CONTENT;
//	private int ALL_TIME;
	
	private int id;
	private int USER_ID;
	private String title;
	private String type;
	private String start;
	private String end;
	private String description;
	private int ALL_TIME;
	private boolean allday;
	private String color;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(int uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isAllday() {
		return allday;
	}
	public void setAllday(boolean allday) {
		this.allday = allday;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getALL_TIME() {
		return ALL_TIME;
	}
	public void setALL_TIME(int aLL_TIME) {
		ALL_TIME = aLL_TIME;
	}
	
	
	
	
	
	
	
	
	
}
