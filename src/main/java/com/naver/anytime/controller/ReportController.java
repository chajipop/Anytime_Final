package com.naver.anytime.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naver.anytime.service.CommentService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.PostService;
import com.naver.anytime.service.ReportService;

@RestController
public class ReportController {

	private ReportService reportService;
	private PostService postService;
	private CommentService commentService;
	private MemberService memberService;

	@Autowired
	public ReportController(ReportService reportService, PostService postService, CommentService commentService,
			MemberService memberService) {
		this.reportService = reportService;
		this.postService = postService;
		this.commentService = commentService;
		this.memberService = memberService;
	}

	@RequestMapping(value = "/report")
	@ResponseBody
	public int insertReport(@RequestParam(value = "post_id", required = false, defaultValue = "0") int post_id,
			@RequestParam(value = "comment_id", required = false, defaultValue = "0") int comment_id,
			@RequestParam("reportnum") int reportnum, Principal principal) {
		String login_id = principal.getName(); // 로그인한 유저 login_id
		int report_user_id = memberService.getUserId(login_id); // 로그인한 유저 user_id 구하기

		int reportResult = 0;

		// 게시물 신고
		if (post_id > 0) {
			int check = reportService.isReportCheck(post_id, report_user_id);
			if (check == 0) {
				int postreport = reportService.insertReport(post_id, report_user_id, reportnum);
				int postreportupdate = reportService.updatePostReportCount(post_id);
				if (postreport == 1 && postreportupdate == 1) {
					reportResult = 1;
				}
			} else {
				reportResult = 2;
			}
			// 신고수 10 이상일때 status = 0
			int statuscheck = postService.getPostReportCount(post_id);
			if (statuscheck >= 10) {
				postService.updatePostStatus(post_id);
			}
			// 댓글 신고
		} else if (comment_id > 0) {
			int check = reportService.isReportCheck(comment_id, report_user_id);
			if (check == 0) {
				int commentreport = reportService.insertReport(comment_id, report_user_id, reportnum);
				int commentreportupdate = reportService.updateCommentReportCount(comment_id);
				if (commentreport == 1 && commentreportupdate == 1) {
					reportResult = 1;
				}
			} else {
				reportResult = 2;
			}
			// 신고수 10 이상일때 status = 0
			int statuscheck = commentService.getCommentReportCount(comment_id);
			if (statuscheck >= 10) {
				commentService.updateCommentStatus(comment_id);
			}
		}
		return reportResult;
	}
}
