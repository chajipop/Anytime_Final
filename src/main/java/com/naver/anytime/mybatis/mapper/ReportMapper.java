package com.naver.anytime.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Report;

@Mapper
public interface ReportMapper {

	int insertReport(int id, int report_user_id, int reason); // id = post_id or comment_id

	int updatePostReportCount(int post_id);

	int updateCommentReportCount(int comment_id);

	int isReportCheck(int id, int report_user_id);
	
	/* =============================== 윤희 =============================== */

	List<Report> getReportRequest(int order);

	int getListCount();

	List<Report> getReportTotalList(HashMap<String, Object> map);

	int updateReport(HashMap<String, Object> map);

	List<Report> getReportCount();


}
