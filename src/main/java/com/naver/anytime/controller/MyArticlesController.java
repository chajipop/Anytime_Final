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
public class MyArticlesController {

	private static final Logger logger = LoggerFactory.getLogger(MyArticlesController.class);

	private PostService postService;	   
	private BoardService boardService;
	private MemberService memberService;
	private SchoolService schoolService;
	
	@Autowired
	public MyArticlesController(ScrapService scrapService, PostService postService, BoardService boardService, MemberService memberService ,SchoolService schoolService) {

		this.postService = postService;
	    this.boardService = boardService;
	    this.memberService = memberService;
	    this.schoolService = schoolService;
	}
	
	
	//내가 쓴 글 페이지 보내기
	@RequestMapping(value = "/myarticle")
	@ResponseBody
	public ModelAndView MyArticle(
			@AuthenticationPrincipal UserCustom user,
			ModelAndView mv
			) {
		
		Map<String, Object> school = new HashMap<String, Object>();
		String school_name = schoolService.getSchoolNameById(user.getSchool_id());

		school.put("id", user.getSchool_id());
		school.put("name", school_name);
		school.put("domain", schoolService.getSchoolDomain(school_name));
		
		mv.setViewName("myarticle/myarticle");
		mv.addObject("school", school);
		return mv;
	}
	
	//내가 댓글 단 글 페이지 보내기
	@RequestMapping(value = "/mycommentarticle")
	@ResponseBody
	public ModelAndView MyCommentArticle(
			@AuthenticationPrincipal UserCustom user,
			ModelAndView mv
			) {
		
		Map<String, Object> school = new HashMap<String, Object>();
		String school_name = schoolService.getSchoolNameById(user.getSchool_id());

		school.put("id", user.getSchool_id());
		school.put("name", school_name);
		school.put("domain", schoolService.getSchoolDomain(school_name));
		
		mv.setViewName("myarticle/mycommentarticle");
		mv.addObject("school", school);
		return mv;
	}
	
	@RequestMapping(value = "/myarticlelist")
	@ResponseBody
	public Map<String, Object> getMyArticleList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Principal principal
			) {
		String login_id = principal.getName();						//로그인한 유저 login_id
		int user_id = memberService.getUserId(login_id);		//로그인한 유저 user_id 구하기

		// 제한 수
		int limit = 10;
		
		// 총 리스트 수
		int listcount = postService.getMyArticlesListCount(user_id);

		// 총 페이지 수
	    int maxpage = (listcount + limit - 1) / limit;

	    // 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
	    int startpage = ((page - 1) / 10) * 10 + 1;

	    // 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
	    int endpage = startpage + 10 - 1;

	    if (endpage > maxpage)
	       endpage = maxpage;

	    // 스크랩 게시물 리스트
		List<Post> list = postService.getMyArticlesList(page, limit, user_id);
				
		// JSON에 추가
	    Map<String, Object> response = new HashMap<>();
	    response.put("page", page);
	    response.put("list", list);
	    response.put("start", startpage);
	    response.put("end", endpage);
	    response.put("max", maxpage);
		
	    System.out.println("내가 쓴 글 불러오기 체크");
		return response;
	}
	
	
	@RequestMapping(value = "/mycommentarticlelist")
	@ResponseBody
	public Map<String, Object> getMyCommentArticleList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Principal principal
			) {
		String login_id = principal.getName();						//로그인한 유저 login_id
		int user_id = memberService.getUserId(login_id);		//로그인한 유저 user_id 구하기

		// 제한 수
		int limit = 10;
		
		// 총 리스트 수
		int listcount = postService.getMyCommentArticlesListCount(user_id);

		// 총 페이지 수
	    int maxpage = (listcount + limit - 1) / limit;

	    // 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
	    int startpage = ((page - 1) / 10) * 10 + 1;

	    // 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
	    int endpage = startpage + 10 - 1;

	    if (endpage > maxpage)
	       endpage = maxpage;

	    // 스크랩 게시물 리스트
		List<Post> list = postService.getMyCommentArticlesList(page, limit, user_id);
				
		// JSON에 추가
	    Map<String, Object> response = new HashMap<>();
	    response.put("page", page);
	    response.put("list", list);
	    response.put("start", startpage);
	    response.put("end", endpage);
	    response.put("max", maxpage);
		
	    System.out.println("내가 쓴 글 불러오기 체크");
		return response;
	}

}
