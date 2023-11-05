package com.naver.anytime.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.School;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.SchoolService;

@Controller
@RequestMapping(value = "/main")
public class MainController {

	private  SchoolService schoolService;
	private  MemberService memberservice;
	
	@Autowired
	public MainController(SchoolService schoolService, MemberService memberservice) {
		this.schoolService =schoolService;
		this.memberservice = memberservice;
	}
	
	
	   // http://localhost:9700/anytime/main/home
	   // 회원가입 폼 이동
	   @RequestMapping(value = "/home", method = RequestMethod.GET)
	   public ModelAndView main( 
			   			  ModelAndView mv,
			              Principal userPrincipal,
			              HttpSession session
			              ) {
		   
		   List<School> schools = schoolService.getSchoolList(); 
		   mv.addObject("schoolList", schools);
		   
		   if (userPrincipal != null) {
				String schoolDomain = memberservice.getSchoolDomain(userPrincipal.getName());
				// getschoolDomain : 데이터베이스 접근하여 로그인 유저의 학교 주소 호출

				mv.setViewName("redirect:/" + schoolDomain);
			} else {
				mv.setViewName("main/anytimeMain");
			}
	      return mv;// WEB-IF/views/main/anytimeMain.jsp
	   }
	
	
	
}
