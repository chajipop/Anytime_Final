package com.naver.anytime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Semester_detail;
import com.naver.anytime.mybatis.mapper.Semester_detailMapper;

@Service
public class Semester_detailServiceImpl implements Semester_detailService {
	private Semester_detailMapper dao;
	
	@Autowired
	public Semester_detailServiceImpl(Semester_detailMapper dao) {
		this.dao  = dao;
	}

	public int insert(Semester_detail semester_detail) {
		return dao.insert(semester_detail);
	}

	@Override
	public int update(Semester_detail semester_detail) {
		return dao.update(semester_detail);
	}

	@Override
	public List<Semester_detail> getSemesterDetailsBySemesterId(int semester_id) {
		return dao.getSemesterDetailsBySemesterId(semester_id);
	}

	@Override
	public List<Semester_detail> getDetailPerSemester(int semester_id) {
		return dao.getDetailPerSemester(semester_id);
	}

	@Override
	public List<Object[]> findTop5Grades(int user_id) {
		return dao.findTop5Grades(user_id);
	}

	@Override
	public List<Semester_detail> getdetail(String semesterId) {
		return dao.getdetail(semesterId);
	}
}
