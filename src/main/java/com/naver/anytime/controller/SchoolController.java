package com.naver.anytime.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Member;
import com.naver.anytime.domain.Post;
import com.naver.anytime.domain.UserCustom;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.PostService;
import com.naver.anytime.service.SchoolService;

@Controller
public class SchoolController {

	private static final Logger logger = LoggerFactory.getLogger(SchoolController.class);

	private SchoolService schoolService;
	private BoardService boardService;
	private PostService postService;
	private MemberService memberService;

	@Autowired
	public SchoolController(SchoolService schoolService, BoardService boardService, PostService postService,
			MemberService memberService) {

		this.schoolService = schoolService;
		this.boardService = boardService;
		this.postService = postService;
		this.memberService = memberService;
	}

	@RequestMapping(value = "/{schoolDomain}", method = RequestMethod.GET)
	public ModelAndView getLoginCommunityPage(@PathVariable("schoolDomain") String schoolDomain, HttpSession session,
			ModelAndView mv, @AuthenticationPrincipal UserCustom user) {

		int school_id;

		mv.setViewName("main/community");

		int isDomain = schoolService.isDomain(schoolDomain);

		if (isDomain > 0) {
			if (user != null) {
				String id = user.getUsername();
				logger.info("인증된 사용자 : " + user.getUsername());
				logger.info("유저 학교 도메인 : " + memberService.getSchoolDomain(id));

				if (user.getAuth().equals("ROLE_MEMBER") && !schoolDomain.equals(memberService.getSchoolDomain(id))) {
					mv.setViewName("redirect:/" + memberService.getSchoolDomain(id));
					logger.info("다른학교 출입");
				}

				int[] board_ids = boardService.getBoardIds(id);
				List<List<Post>> commonPostsByBoard = postService.getPostListByBoard(board_ids);

				Member m = memberService.getLoginMember(id);

				school_id = user.getSchool_id();

				mv.addObject("member", m);
				mv.addObject("list", commonPostsByBoard);

			} else {
				int[] board_ids = boardService.getBoardIdsByDomain(schoolDomain);
				List<List<Post>> commonPostsByBoard = postService.getPostListByBoard(board_ids);
				school_id = schoolService.getSchoolId(schoolDomain);
				mv.addObject("list", commonPostsByBoard);
			}

			Map<String, Object> school = new HashMap<String, Object>();
			school.put("name", schoolService.getSchoolName(schoolDomain));
			school.put("id", school_id);
			school.put("domain", schoolDomain);

			mv.addObject("school", school);
		} else {
			mv.setViewName("redirect:/");
		}

		return mv;
	}

}
