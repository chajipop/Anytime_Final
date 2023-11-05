package com.naver.anytime.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Semester_detail;

@Mapper
public interface Semester_detailMapper {

	public int insert(Semester_detail semester_detail);

	public int update(Semester_detail semester_detail);
	
	public List<Semester_detail> getSemesterDetailsBySemesterId(int semester_id);

	public List<Semester_detail> getDetailPerSemester(int semester_id);
	
	public List<Object[]> findTop5Grades(int user_id);

	public List<Semester_detail> getdetail(String semesterId);
	
}
