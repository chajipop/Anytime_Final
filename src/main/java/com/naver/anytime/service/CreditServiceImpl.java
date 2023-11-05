package com.naver.anytime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Credit;
import com.naver.anytime.domain.Semester_detail;
import com.naver.anytime.mybatis.mapper.CreditMapper;

@Service
public class CreditServiceImpl implements CreditService {
	
	private CreditMapper dao;

	@Autowired
	public CreditServiceImpl(CreditMapper dao) {
		this.dao = dao;
	}
	
	
	@Override
	public int insert(Credit c) {
		return dao.insert(c);
	}

	@Override
	public List<Semester_detail> getSemesterDetailsByUserId(int user_id) {
		return dao.getSemesterDetailsByUserId(user_id);
	}


	@Override
	public List<Semester_detail> getSemesterDetailsBySemesterId(int semester_id) {
		return dao.getSemesterDetailsBySemesterId(semester_id);
	}


	@Override
	public Credit getCreditByUserId(int user_id){
		return dao.getCreditByUserId(user_id);
	}


	@Override
	public int updateGraduateCredit(int graduate_credit) {
		return dao.updateGraduateCredit(graduate_credit);
	}


	@Override
	public int getTotalAcquisition(int user_id) {
		return dao.getTotalAcquisition(user_id);
	}




}
