package com.naver.anytime.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Calendar;

@Mapper
public interface CalendarMapper {

	public List<Calendar> getCalendarList(int user_id);

	public int insertCalendar(String title, int user_id, String color, String start, String end, int allday,
			String description);

	public int updateCalendar(int id, String title, int user_id, String color, String start, String end, int allday,
			String description);

	public int deleteCalendar(int calendar_id, int user_id);

	public int updateDropCalendar(int calendar_id, int user_id, String start, String end);

}
