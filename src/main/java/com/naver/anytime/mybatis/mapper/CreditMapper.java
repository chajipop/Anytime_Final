package com.naver.anytime.mybatis.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Credit;
import com.naver.anytime.domain.Semester_detail;

@Mapper
public interface CreditMapper {

	public int insert(Credit c);

	public List<Semester_detail> getSemesterDetailsByUserId(int user_id);

	public List<Semester_detail> getSemesterDetailsBySemesterId(int semester_id);
	
    public Credit getCreditByUserId(int user_id);
	
    public int updateGraduateCredit(int graduate_credit);

	public int getTotalAcquisition(int user_id);
	

}
