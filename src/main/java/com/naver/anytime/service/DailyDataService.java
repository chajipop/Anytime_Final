package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Board;
import com.naver.anytime.domain.DailyData;
import com.naver.anytime.domain.School;

public interface DailyDataService {

	DailyData getDataTrend();

	List<DailyData> getRegistrationTrend();

	List<School> getSchoolRanking();

	List<Board> getBoardRanking();

	DailyData getTodoList();

	int insertDailyData();

}