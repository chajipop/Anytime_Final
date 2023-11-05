package com.naver.anytime.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Semester;
import com.naver.anytime.domain.Semester_detail;

@Mapper
public interface SemesterMapper {

	public int insert(Semester semester);

	public List<Semester> getSemestersByUserId(int userId);

    public List<Semester> getSemesternameByUserId(int userId);

	}
