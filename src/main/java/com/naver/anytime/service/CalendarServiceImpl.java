package com.naver.anytime.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Calendar;
import com.naver.anytime.mybatis.mapper.CalendarMapper;

@Service
public class CalendarServiceImpl implements CalendarService {
	
	private CalendarMapper dao;
	
	public CalendarServiceImpl(CalendarMapper dao) {
		this.dao = dao;
	}

	@Override
	public List<Calendar> getCalendarList(int user_id) {
		return dao.getCalendarList(user_id);
	}

	@Override
	public int insertCalendar(String title, int user_id, String color, String start, String end, int allday,
			String description) {
		return dao.insertCalendar(title, user_id, color, start, end, allday, description);
	}

	@Override
	public int updateCalendar(int id, String title, int user_id, String color, String start, String end, int allday,
			String description) {
		return dao.updateCalendar(id, title, user_id, color, start, end, allday, description);
	}

	@Override
	public int deleteCalendar(int calendar_id, int user_id) {
		return dao.deleteCalendar(calendar_id,user_id);
	}

	@Override
	public int updateDropCalendar(int calendar_id, int user_id, String start, String end) {
		return dao.updateDropCalendar(calendar_id, user_id, start, end);
	}
	
	

}
