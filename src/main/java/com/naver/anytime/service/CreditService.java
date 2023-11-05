package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Credit;
import com.naver.anytime.domain.Semester_detail;

public interface CreditService {

	public int insert(Credit crdit);

	public List<Semester_detail> getSemesterDetailsByUserId(int user_id);

	public List<Semester_detail> getSemesterDetailsBySemesterId(int semester_id);

	public Credit getCreditByUserId(int user_id);

	public int updateGraduateCredit(int graduate_credit);

	public int getTotalAcquisition(int user_id);

	
}
