package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.School;

public interface SchoolService {
	
	List<School> getSchoolList();

	public String getSchoolDomain(String SchoolName);

	String getSchoolName(String schoolDomain);

	School getSchool(String schoolDomain);

	School getSchoolByUserId(int user_id);

	int getSchoolId(String schoolDomain);

	String getSchoolNameById(int school_id);

	int isDomain(String schoolDomain);

	String getAddress(String school_name);

	float getCredit(String school_name);
	
}