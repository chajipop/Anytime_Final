package com.naver.anytime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Board;
import com.naver.anytime.domain.DailyData;
import com.naver.anytime.domain.School;
import com.naver.anytime.mybatis.mapper.DailyDataMapper;

@Service
public class DailyDataServiceImpl implements DailyDataService {

	private DailyDataMapper dao;

	@Autowired
	public DailyDataServiceImpl(DailyDataMapper dao) {
		this.dao = dao;
	}

	@Override
	public DailyData getDataTrend() {
		return dao.getDataTrend();
	}

	@Override
	public List<DailyData> getRegistrationTrend() {
		return dao.getRegistrationTrend();
	}

	@Override
	public List<School> getSchoolRanking() {
		return dao.getSchoolRanking();
	}

	@Override
	public List<Board> getBoardRanking() {
		return dao.getBoardRanking();
	}

	@Override
	public DailyData getTodoList() {
		return dao.getTodoList();
	}

	@Override
	public int insertDailyData() {
		return dao.insertDailyData();
	}

}