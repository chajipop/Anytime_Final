package com.naver.anytime.mybatis.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.naver.anytime.domain.TimeTable;

@Mapper
public interface TimeTableMapper {

	public List<TimeTable> gettimetable(int user_id);

	public List<TimeTable> getTimetableByUserId(int user_id);

	public void updateTimetable(TimeTable timetable);

	public TimeTable getLastTimeTable(int user_id);

	public TimeTable createNewTimeTable(int user_id);

	public Integer getLastTimeTableId(int user_id);

	public void insert(TimeTable newTimeTable);

	List<TimeTable> getTimetableByUserIdAndSemester(int user_id, String semester);

	public int createDefaultTimeTable(int user_id, String semester);

	public int insertNewTimetable(int user_id, String newName, String semester);
	
	public int getLastInsertId();

	public String getLastTimeTableName(int user_id, String semester);

	public TimeTable getNewTimetable(int key);

	public int checkTimetable(int user_id, String semester);
	
	TimeTable getTimeTableById(int timetable_id);

	public void updateStatus(int user_id, int timetable_id);
	
	public void deleteTimetable(int user_id, int timetable_id, int status);

	public int findPrimary(int user_id);

	public Integer findNextTimetable(int user_id);

	public void updateToPrimary(int user_id);

}
