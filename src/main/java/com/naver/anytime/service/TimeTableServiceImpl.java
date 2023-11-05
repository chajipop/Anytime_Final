package com.naver.anytime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.TimeTable;
import com.naver.anytime.mybatis.mapper.TimeTableMapper;
import com.naver.anytime.mybatis.mapper.TimeTable_detailMapper;

@Service
public class TimeTableServiceImpl implements TimeTableService {
	private TimeTableMapper timeDao;
	private TimeTable_detailMapper timeDetailDao;

	@Autowired
	public TimeTableServiceImpl(TimeTableMapper timeDao, TimeTable_detailMapper timeDetailDao) {
		this.timeDao = timeDao;
		this.timeDetailDao = timeDetailDao;
	}

	@Override
	public List<TimeTable> gettimetable(int user_id) {
		return timeDao.gettimetable(user_id);
	}

	@Override
	public List<TimeTable> getTimetableByUserId(int user_id) {
		return timeDao.getTimetableByUserId(user_id);
	}

	@Override
	public void updateTimetable(int user_id, int timetable_id, String newName, int status) {
		
		if(status ==1) {
			timeDao.updateStatus(user_id, timetable_id);
		}
		
		TimeTable timetable = new TimeTable();
		timetable.setTIMETABLE_ID(timetable_id);
		timetable.setNAME(newName);
		timetable.setSTATUS(status);
		timeDao.updateTimetable(timetable);
	}

	@Override
	public int createNewTimeTable(int user_id, String semester) {

		String lastName = getLastTimeTableName(user_id, semester);
		int lastIndex = 0;

		if (lastName != null) {
			lastIndex = Integer.parseInt(lastName.replaceAll("[^0-9]", ""));
		}

		String newName = "시간표 " + (lastIndex + 1);

		timeDao.insertNewTimetable(user_id, newName, semester);
		return timeDao.getLastInsertId();

	}

	private String getLastTimeTableName(int user_id, String semester) {
		return timeDao.getLastTimeTableName(user_id, semester);
	}

	@Override
	public TimeTable getNewTimetable(int key) {
		return timeDao.getNewTimetable(key);
	}

	private TimeTable getLastTimeTable(int user_id) {
		return timeDao.getLastTimeTable(user_id);
	}

	@Override
	public List<TimeTable> getTimetableByUserIdAndSemester(int user_id, String semester) {
		return timeDao.getTimetableByUserIdAndSemester(user_id, semester);
	}

	@Override
	public int createDefaultTimeTable(int user_id, String semester) {
		timeDao.createDefaultTimeTable(user_id, semester);
		return timeDao.getLastInsertId();
	}

	@Override
	public int getLastTimeTableId(int user_id) {
		return timeDao.getLastTimeTableId(user_id);
	}

	@Override
	public int checkTimetable(int user_id, String semester) {
		return timeDao.checkTimetable(user_id, semester);
	}
	
	@Override
	public TimeTable getTimeTableById(int timetable_id) {
		return timeDao.getTimeTableById(timetable_id);
	}
	
	@Override
    public void deleteTimetable(int user_id, int timetable_id, Integer status) {
		timeDetailDao.deleteTimetableDetail(timetable_id);
		timeDao.deleteTimetable(user_id, timetable_id, status);
		
		if (status == 1) {
            Integer nextId = timeDao.findNextTimetable(user_id);
            if (nextId != null) {
                // 다음 시간표를 기본 시간표로 업데이트
                timeDao.updateToPrimary(nextId);
            }
        }
    }
}
