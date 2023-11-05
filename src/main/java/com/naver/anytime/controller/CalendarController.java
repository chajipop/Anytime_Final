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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Calendar;
import com.naver.anytime.domain.UserCustom;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.CalendarService;
import com.naver.anytime.service.CommentService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.PostService;
import com.naver.anytime.service.ReportService;
import com.naver.anytime.service.SchoolService;

@RestController
public class CalendarController {

	private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);
	
	private CalendarService calendarService;
	private ReportService reportService;
	private PostService postService;	   
	private BoardService boardService;
	private CommentService commentService;
	private MemberService memberService;
	private SchoolService schoolService;
	
	@Autowired
	public CalendarController(CalendarService calendarService, ReportService reportService, PostService postService, BoardService boardService, CommentService commentService, MemberService memberService, SchoolService schoolService) {
		this.calendarService = calendarService;
		this.reportService = reportService;
		this.postService = postService;
	    this.boardService = boardService;
	    this.commentService = commentService;
	    this.memberService = memberService;
	    this.schoolService = schoolService;
	}
	
	@RequestMapping(value = "/calendar")
	@ResponseBody
	public ModelAndView Calendar(
			@AuthenticationPrincipal UserCustom user,
			ModelAndView mv
			) {
		Map<String, Object> school = new HashMap<String, Object>();
		String school_name = schoolService.getSchoolNameById(user.getSchool_id());

		school.put("id", user.getSchool_id());
		school.put("name", school_name);
		school.put("domain", schoolService.getSchoolDomain(school_name));
		
		mv.setViewName("calendar/Calendar");
		mv.addObject("school", school);
		return mv;
	}
	
	@RequestMapping(value = "/calendarlist")
	@ResponseBody
	public List<Calendar> CalendarList(
			Principal principal
			) {
		String login_id = principal.getName();						//로그인한 유저 login_id
		int user_id = memberService.getUserId(login_id);			//로그인한 유저 user_id
		
		List<Calendar> Result = calendarService.getCalendarList(user_id);
		
		for(Calendar calendar : Result) {
			if(calendar.getALL_TIME() == 1) {
				calendar.setAllday(true);
			}else {
				calendar.setAllday(false);
			}
		}
		
		if(Result != null) {
			System.out.println("캘린더 리스트 출력 완료");
		}else {
			System.out.println("캘린더 리스트 출력 실패");
		}
		return Result;
	}
	
	@RequestMapping(value = "/calendaradd", method = RequestMethod.POST)
	@ResponseBody
	public int insertCalendar(
			@RequestParam("title") String title,
			@RequestParam("color") String color,
			@RequestParam("start") String start,
			@RequestParam(value = "end", required = false) String end,
			@RequestParam(value = "allday", defaultValue = "0") int allday,
			@RequestParam("description") String description,		
			Principal principal
			) {
		int Result = 0;
		String login_id = principal.getName();						//로그인한 유저 login_id
		int user_id = memberService.getUserId(login_id);			//로그인한 유저 user_id
		
		int addcheck = calendarService.insertCalendar(title, user_id, color, start, end, allday, description);
		
		if(addcheck == 1) {
			Result = 1;
		}
		
		return Result;
	}
	
	@RequestMapping(value = "/calendarupdate", method = RequestMethod.POST)
	@ResponseBody
	public int updateCalendar(
			@RequestParam("id") int id,
			@RequestParam("title") String title,
			@RequestParam("color") String color,
			@RequestParam("start") String start,
			@RequestParam(value = "end", required = false) String end,
			@RequestParam(value = "allday", defaultValue = "0") int allday,
			@RequestParam("description") String description,		
			Principal principal
			) {
		int Result = 0;
		String login_id = principal.getName();						//로그인한 유저 login_id
		int user_id = memberService.getUserId(login_id);			//로그인한 유저 user_id
		
		int updatecheck = calendarService.updateCalendar(id, title, user_id, color, start, end, allday, description);
		
		if(updatecheck == 1) {
			Result = 1;
			System.out.println("캘린더 수정 완료");
		}
		
		return Result;
	}
	
	@RequestMapping(value = "/calendardropupdate", method = RequestMethod.POST)
	@ResponseBody
	public int updateCalendar(
			@RequestParam("calendar_id") int calendar_id,
			@RequestParam("start") String start,
			@RequestParam(value= "end", required = false) String end,
			Principal principal
			) {
		int Result = 0;
		String login_id = principal.getName();						//로그인한 유저 login_id
		int user_id = memberService.getUserId(login_id);			//로그인한 유저 user_id
		
		int updatecheck = calendarService.updateDropCalendar(calendar_id, user_id, start, end);
		
		if(updatecheck == 1) {
			Result = 1;
			System.out.println("캘린더 수정 완료");
		}
		
		return Result;
	}	
	
	@RequestMapping(value = "/calendardelete", method = RequestMethod.POST)
	@ResponseBody
	public int deleteCalendar(
			@RequestParam("id") int calendar_id,
			Principal principal
			) {
		int Result = 0;
		String login_id = principal.getName();						//로그인한 유저 login_id
		int user_id = memberService.getUserId(login_id);			//로그인한 유저 user_id
		
		int deletecheck = calendarService.deleteCalendar(calendar_id,user_id);
		
		if(deletecheck == 1) {
			Result = 1;
			System.out.println("캘린더 삭제 완료");
		}
		return Result;
	}
	
}
