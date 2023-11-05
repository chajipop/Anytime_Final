package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Semester;
import com.naver.anytime.domain.Semester_detail;

public interface SemesterService {

	public int insert(Semester semester);

	public List<Semester> getSemestersByUserId(int userId);

	public List<Semester> getSemesternameByUserId(int user_id);

	
	
}
