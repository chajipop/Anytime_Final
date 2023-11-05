package com.naver.anytime.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Comments;

/*
 *  Mapper 인터페이스란 매퍼 파일에 기재된 SQL을 호출하기 위한 인터페이스입니다.
 *  MyBatis-Spring은 Mapper 인터페이스를 이용해서 실제 SQL 처리가 되는 클래스를 자동으로 생성합니다.
 */
@Mapper
public interface CommentMapper {

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

	// 댓글 삭제
	public int deleteComment(int comment_id);

	public int selectReply(int comment_id);

	public int updateCommentStatus(int comment_id);

	public int selectReportCount(int comment_id);
	
	// 댓글 신고수
	public int getCommentReportCount(int comment_id);

}
