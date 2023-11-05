package com.naver.anytime.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Post;
import com.naver.anytime.domain.UserCustom;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.PostService;
import com.naver.anytime.service.SchoolService;
import com.naver.anytime.service.ScrapService;


@RestController
public class TopPicksPostController {

	private static final Logger logger = LoggerFactory.getLogger(TopPicksPostController.class);

	private PostService postService;	   
	private BoardService boardService;
	private MemberService memberService;
	private SchoolService schoolService;
	
	@Autowired
	public TopPicksPostController(ScrapService scrapService, PostService postService, BoardService boardService, MemberService memberService, SchoolService schoolService) {

		this.postService = postService;
	    this.boardService = boardService;
	    this.memberService = memberService;
	    this.schoolService = schoolService;
	}
	
	
	//내가 쓴 글 페이지 보내기
	@RequestMapping(value = "/hotpost")
	@ResponseBody
	public ModelAndView MyArticle(
			@AuthenticationPrincipal UserCustom user,
			ModelAndView mv,
			Principal principal
			) {
		// 학교 인증 체크
		int user_id = memberService.getUserId(principal.getName());
		
		int schoolcheck = memberService.getUserSchoolCheck(user_id);
		
		Map<String, Object> school = new HashMap<String, Object>();
		String school_name = schoolService.getSchoolNameById(user.getSchool_id());

		school.put("id", user.getSchool_id());
		school.put("name", school_name);
		school.put("domain", schoolService.getSchoolDomain(school_name));
		
		mv.setViewName("post/hotPostList");
		if(schoolcheck == 1) {
			mv.addObject("school", school);
		}
		mv.addObject("school_check", schoolcheck);
		return mv;
	}
	
	//내가 댓글 단 글 페이지 보내기
	@RequestMapping(value = "/bestpost")
	@ResponseBody
	public ModelAndView MyCommentArticle(
			@AuthenticationPrincipal UserCustom user,
			ModelAndView mv,
			Principal principal
			) {
		// 학교 인증 체크
		int user_id = memberService.getUserId(principal.getName());
		
		int schoolcheck = memberService.getUserSchoolCheck(user_id);

		
		Map<String, Object> school = new HashMap<String, Object>();
		String school_name = schoolService.getSchoolNameById(user.getSchool_id());

		school.put("id", user.getSchool_id());
		school.put("name", school_name);
		school.put("domain", schoolService.getSchoolDomain(school_name));
		
		mv.setViewName("post/bestPostList");
		if(schoolcheck == 1) {
			mv.addObject("school", school);
		}
		mv.addObject("school_check", schoolcheck);
		return mv;
	}
	
	@RequestMapping(value = "/hotpostlist")
	@ResponseBody
	public Map<String, Object> getMyArticleList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "school_id") int school_id
			) {

		// 제한 수
		int limit = 10;
		
		// 총 리스트 수
		int listcount = postService.getHotPostListCount(school_id);

		// 총 페이지 수
	    int maxpage = (listcount + limit - 1) / limit;

	    // 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
	    int startpage = ((page - 1) / 10) * 10 + 1;

	    // 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
	    int endpage = startpage + 10 - 1;

	    if (endpage > maxpage)
	       endpage = maxpage;

	    // 핫 게시물 리스트
		List<Post> list = postService.getHotPostList(page, limit, school_id);
				

		// JSON에 추가
	    Map<String, Object> response = new HashMap<>();
	    response.put("page", page);
	    response.put("list", list);
	    response.put("start", startpage);
	    response.put("end", endpage);
	    response.put("max", maxpage);
		
	    System.out.println("핫 게시글 불러오기 체크");
		return response;
	}
	
	
	@RequestMapping(value = "/bestpostlist")
	@ResponseBody
	public Map<String, Object> getMyCommentArticleList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "school_id") int school_id
			) {

		// 제한 수
		int limit = 10;
		
		// 총 리스트 수
		int listcount = postService.getBestPostListCount(school_id);

		// 총 페이지 수
	    int maxpage = (listcount + limit - 1) / limit;

	    // 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
	    int startpage = ((page - 1) / 10) * 10 + 1;

	    // 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
	    int endpage = startpage + 10 - 1;

	    if (endpage > maxpage)
	       endpage = maxpage;

	    // 베스트 게시물 리스트
		List<Post> list = postService.getBestPostList(page, limit, school_id);
				
		// JSON에 추가
	    Map<String, Object> response = new HashMap<>();
	    response.put("page", page);
	    response.put("list", list);
	    response.put("start", startpage);
	    response.put("end", endpage);
	    response.put("max", maxpage);
		
	    System.out.println("베스트 게시글 불러오기 체크");
		return response;
	}
	
	@RequestMapping(value = "/rightsidelist_sample")
	@ResponseBody
	public Map<String, Object> getHotPostSampleList(
			@RequestParam(value = "login_id", required = false) String login_id,
			@RequestParam(value = "school_id") int school_id,
			Principal principal
			) {
		int schoolcheck = 0;
		// 학교 인증 체크
		if(login_id != null) {
			int user_id = memberService.getUserId(login_id);
			schoolcheck = memberService.getUserSchoolCheck(user_id);
		}

		List<Post> hotlist = postService.getHotPostSampleList(school_id);
		List<Post> bestlist = postService.getBestPostSampleList(school_id);
				
		// JSON에 추가
	    Map<String, Object> response = new HashMap<>();
	    response.put("hotlist", hotlist);
	    response.put("bestlist", bestlist);
	    response.put("school_check", schoolcheck);    

	    if(login_id == null) {
	    	response.put("login_check", 1);
	    }
	    
	    System.out.println("핫 게시글 (4개) 불러오기 체크");
		return response;
	}
	

}
