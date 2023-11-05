
package com.naver.anytime.service;

import java.util.List;
import java.util.Map;

import com.naver.anytime.domain.Comments;

public interface CommentService {

	// 댓글 목록 가져오기
	public List<Comments> getCommentList(int post_id);

	// 댓글 등록하기
	public int insertComment(Comments co);

	// 대댓글 depth 업데이트
	public int updateDepth(Map<String, Object> map);

	// 대댓글 달기
	public int insertReplyComment(Comments co);

	// 댓글 수정
	public int updateComment(Comments co);

	public int updateCommentStatus(int comment_id);
	
	// 댓글 신고수
	public int getCommentReportCount(int comment_id);

}
