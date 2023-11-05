package com.naver.anytime.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naver.anytime.domain.Comments;
import com.naver.anytime.service.CommentService;
import com.naver.anytime.service.MemberService;

@RestController // @Controller + @ResponseBody
@RequestMapping(value = "/comment")
public class CommentController {
	private CommentService commentService;
	private MemberService memberService;

	@Autowired
	public CommentController(CommentService commentService, MemberService memberService) {
		this.commentService = commentService;
		this.memberService = memberService;
	}

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

	@PostMapping(value = "/list")
	public Map<String, Object> CommentList(int post_id) {
		List<Comments> commentlist = commentService.getCommentList(post_id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commentlist", commentlist);
		return map;
	}

	@PostMapping(value = "/add")
	public int CommentAdd(Comments co, Principal principal) {
		co.setUser_id(memberService.getUserId(principal.getName()));
		return commentService.insertComment(co);
	}

	@GetMapping(value = "/update")
	public int CommentUpdate(Comments co) {
		return commentService.updateComment(co);
	}

	@GetMapping(value = "/delete")
	public int CommentDelete(int comment_id) {
		return commentService.updateCommentStatus(comment_id);
	}

	@PostMapping(value = "/reply")
	public int CommentReply(Principal principal, Comments co) {
		int result = 0;
		co.setUser_id(memberService.getUserId(principal.getName()));

		Map<String, Object> map = new HashMap<>();

		map.put("re_ref", co.getRe_ref());
		map.put("re_seq", co.getRe_seq());
		int updateResult = commentService.updateDepth(map);
		logger.info("update : " + String.valueOf(updateResult));
		result = commentService.insertReplyComment(co);
		return result;
	}
}
