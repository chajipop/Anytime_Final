package com.naver.anytime.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.TimeTable_detail;

@Mapper
public interface TimeTable_detailMapper {

	List<TimeTable_detail> getsubject(String timetableId);

	List<TimeTable_detail> getTimetableDetails(int timetable_id);
	
	public int addSubject(TimeTable_detail detail);

	public void deleteTimetableDetail(int timetable_id);
	
	void deleteSubject(int userId, int timetable_id, int subject_id);
}
