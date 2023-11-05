package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Calendar;

public interface CalendarService {

	public List<Calendar> getCalendarList(int user_id);

	public int insertCalendar(String title, int user_id, String color, String start, String end, int allday, String description);

	public int updateCalendar(int id, String title, int user_id, String color, String start, String end, int allday,
			String description);

	public int deleteCalendar(int calendar_id, int user_id);

	public int updateDropCalendar(int calendar_id, int user_id, String start, String end);

}
