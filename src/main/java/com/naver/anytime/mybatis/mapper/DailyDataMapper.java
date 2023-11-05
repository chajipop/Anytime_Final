package com.naver.anytime.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Board;
import com.naver.anytime.domain.DailyData;
import com.naver.anytime.domain.School;

@Mapper
public interface DailyDataMapper {

	DailyData getDataTrend();

	List<DailyData> getRegistrationTrend();

	List<School> getSchoolRanking();

	List<Board> getBoardRanking();

	DailyData getTodoList();

	int insertDailyData();

}
