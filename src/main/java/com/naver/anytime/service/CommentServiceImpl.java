package com.naver.anytime.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Comments;
import com.naver.anytime.mybatis.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {
	private CommentMapper dao;

	@Autowired
	public CommentServiceImpl(CommentMapper dao) {
		this.dao = dao;
	}

	@Override
	public List<Comments> getCommentList(int post_id) {
		return dao.getCommentList(post_id);
	}

	@Override
	public int insertComment(Comments co) {
		return dao.insertComment(co);
	}

	@Override
	public int updateDepth(Map<String, Object> map) {
		return dao.updateDepth(map);
	}

	@Override
	public int insertReplyComment(Comments co) {
		return dao.insertReplyComment(co);
	}

	@Override
	public int updateComment(Comments co) {
		return dao.updateComment(co);
	}

	@Override
	public int updateCommentStatus(int comment_id) {
		return dao.updateCommentStatus(comment_id);
	}
	
	//댓글 신고수
	@Override
	public int getCommentReportCount(int comment_id) {
		return dao.getCommentReportCount(comment_id);
	}
	
}
