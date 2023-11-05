package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Report;

public interface ReportService {

	int insertReport(int id, int report_user_id, int reason);	// id = post_id or comment_id

	int updatePostReportCount(int post_id);

	int updateCommentReportCount(int comment_id);

	int isReportCheck(int id, int report_user_id);
	
	List<Report> getReportRequest(int order);

	int getListCount();

	List<Report> getReportTotalList(int order, int page, int limit);

	int updateReport(int content_id, String content_action, String user_action, int admin_id, String admin_login_id);

	List<Report> getReportCount();


}
