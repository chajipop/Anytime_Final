package com.naver.anytime.controller;

import java.security.Principal;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naver.anytime.domain.Credit;
import com.naver.anytime.domain.Member;
import com.naver.anytime.domain.School;
import com.naver.anytime.domain.Semester;
import com.naver.anytime.domain.Semester_detail;
import com.naver.anytime.service.CreditService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.SchoolService;
import com.naver.anytime.service.SemesterService;
import com.naver.anytime.service.Semester_detailService;
import com.naver.anytime.task.SendMail;
import com.naver.constants.AnytimeConstants;

// 이 컨트롤러는 회원 가입시 비밀번호 암호화를 하는 컨트롤러입니다.

@Controller
@RequestMapping(value = "/member") // http://localhost:9700/anytime/member/로 시작하는 주소 매핑
public class MemberController {
	// import org.slf4j.Logger;
	// import org.slf4j.LoggerFactory;

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	private SchoolService schoolService;
	private MemberService memberservice;
	private CreditService creditservice;
	private SemesterService semesterservice;
	private Semester_detailService semester_detailservice;
	private PasswordEncoder passwordEncoder;
	private SendMail sendMail;

	@Autowired
	public MemberController(MemberService memberservice, CreditService creditservice, SemesterService semesterservice,
			Semester_detailService semester_detailservice, PasswordEncoder passwordEncoder, SendMail sendMail,
			SchoolService schoolService) {
		this.memberservice = memberservice;
		this.schoolService = schoolService;
		this.creditservice = creditservice;
		this.semesterservice = semesterservice;
		this.semester_detailservice = semester_detailservice;
		this.passwordEncoder = passwordEncoder;
		this.sendMail = sendMail;
	}

	/*
	 * @CookieValue(value="saveid", required=false) Cookie readCookie 이름이 saveid인
	 * 쿠키를 Cookie 타입으로 전달받습니다. 지정한 이름의 쿠키가 존재하지 않을 수도 있기 때문에 required=false로 설정해야
	 * 합니다. 즉 id 기억하기를 선택하지 않을 수도 있기 때문에 required=false로 설정해야 합니다. required=true
	 * 상태에서 지정한 이름을 가진 쿠키가 존재하지 않으면 스프링 MVC는 익셉션을 발생시킵니다.
	 */

	// http://localhost:9700/anytime/member/login
	// 로그인 폼이동
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv, @CookieValue(value = "autologin", required = false) Cookie readCookie,
			HttpSession session, Principal userPrincipal) {
		logger.info("로그인전");// principal.getName() : 로그인한 아이디 값을 알 수 있어요
		if (readCookie != null) {
			logger.info("저장된 아이디 :" + userPrincipal.getName());// principal.getName() : 로그인한 아이디 값을 알 수 있어요

			String schoolDomain = memberservice.getSchoolDomain(userPrincipal.getName());
			// getschoolDomain : 데이터베이스 접근하여 로그인 유저의 학교 주소 호출
			logger.info("학교 도메인 : " + schoolDomain);

			mv.setViewName("redirect:/" + schoolDomain);
		} else {
			mv.setViewName("member/loginForm");
			mv.addObject("loginfail", session.getAttribute("loginfail"));// 세션에 저장된 값을 한 번만 실행될 수 있도록 mv에 저장합니다
			session.removeAttribute("loginfail");// 세션의 값은 제거합니다.
		}
		return mv;
	}

	// http://localhost:9700/anytime/member/register
	// 학교,학번등록 폼 이동
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		List<School> schools = schoolService.getSchoolList();
		model.addAttribute("schoolList", schools);

		return "member/registerForm";// WEB-IF/views/member/registerForm.jsp
	}

	// http://localhost:9700/anytime/member/agreement
	// 이용약관 폼 이동
	@RequestMapping(value = "/agreement", method = RequestMethod.POST)
	public String agreement(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("enter_year", request.getParameter("enter_year"));
		session.setAttribute("campus_name", request.getParameter("campus_name"));

		return "member/agreementForm";// WEB-IF/views/member/agreementForm.jsp
	}

	// http://localhost:9700/anytime/member/mailcheckForm
	// 이메일 본인인증 폼 이동
	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public String email() {
		logger.info("mail 화면 출력 성공");
		return "member/mailcheckForm";// WEB-IF/views/member/mailcheckForm.jsp
	}

	@ResponseBody // 응답으로 문자열 반환
	@RequestMapping(value = "/emailsend", method = RequestMethod.POST)
	public String emailsend(@RequestParam("email") String email, HttpSession session) {
		// 난수(인증번호) 생성 (예: 6자리 난수)
		logger.info("email :" + email);
		String authCode = generateAuthCode();
		logger.info("authCode : " + authCode);

		// 세션에 인증번호 저장 (나중에 검증할 때 사용)
		session.setAttribute("authCode", authCode);

		// 이메일 발송
		String subject = "애니타임 가입 인증번호 입니다. ";

		try {
			sendMail.sendAuthEmail(email, subject, authCode);
		} catch (Exception e) {
			logger.error("메일 전송 실패", e);
		}

		return authCode;
	}

	// 6자리 인증코드(난수) 생성 메서드
	private String generateAuthCode() {
		Random random = new Random();
		int range = (int) Math.pow(10, 6); // 10의 6승
		int trim = (int) Math.pow(10, 5); // 10의 5승
		int result = random.nextInt(range) + trim;

		if (result > range) {
			result -= trim;
		}
		logger.info(Integer.toString(result));
		return Integer.toString(result);
	}

	// http://localhost:9700/anytime/member/join
	// 회원가입 입력폼 이동
	@RequestMapping(value = "/join", method = { RequestMethod.GET, RequestMethod.POST })
	public String join() {
		return "member/joinForm";// WEB-IF/views/member/joinForm.jsp
	}

	// 회원가입폼에서 아이디 중복 검사
	@ResponseBody // @ResponseBody를 이용해서 각 메서드의 실행 결과는 JSON으로 변환되어
					// HTTP Response BODY에 설정됩니다.
	@RequestMapping(value = "/idcheck", method = RequestMethod.GET)
	public int idcheck(@RequestParam("login_id") String id) {
		return memberservice.isId(id);// WEB-IF/views/member/oinForm.jsp
	}


	// 회원가입폼에서 메일 중복 검사
	@ResponseBody // @ResponseBody를 이용해서 각 메서드의 실행 결과는 JSON으로 변환되어 HTTP Response BODY에 설정됩니다.
	@RequestMapping(value = "/mailcheck", method = RequestMethod.GET)
	public int emailcheck(@RequestParam("email") String email, Principal principal) {
		int result = memberservice.isEmail(email);
		logger.info("result :" + result);
		
		if (principal != null) {
			String login_id = principal.getName();
			String oldEmail = memberservice.getEmail(login_id);

			if (email.equals(oldEmail))
				result = AnytimeConstants.EMAIL_NOT_EXISTS;
		}
		return result;
	}
	
	// 회원가입폼에서 닉네임 중복 검사
	@ResponseBody // @ResponseBody를 이용해서 각 메서드의 실행 결과는 JSON으로 변환되어
					// HTTP Response BODY에 설정됩니다.
	@RequestMapping(value = "/nicknamecheck", method = RequestMethod.GET)
	public int nicknamecheck(@RequestParam("nickname") String nickname, Principal principal) {
		int result = memberservice.isNickname(nickname);

		if (principal != null) {
			String login_id = principal.getName();
			String oldNickname = memberservice.getNickname(login_id);

			if (nickname.equals(oldNickname))
				result = AnytimeConstants.NICKNAME_NOT_EXISTS;
		}

		return result;
	}
	
	
	// 회원가입 처리
	@RequestMapping(value = "/joinProcess", method = RequestMethod.POST)
	public String joinProcess(Member member, Credit credit, RedirectAttributes rattr,
			Model model, HttpServletRequest request) {

		// 비밀번호 암호화 추가
		String encPassword = passwordEncoder.encode(member.getPassword());
		logger.info(encPassword);
		member.setPassword(encPassword);
		// HttpSession 객체를 가져오기
		HttpSession session = request.getSession();

		String enterYear = (String) session.getAttribute("enter_year");
		String campusName = (String) session.getAttribute("campus_name");

		int admission_year = Integer.parseInt(enterYear);
		member.setAdmission_year(admission_year);

		// MemberService를 사용하여 회원을 데이터베이스에 추가합니다.
		int school_id = memberservice.getSchoolIdByName(campusName);
		member.setSchool_id(school_id);

		int result_member = memberservice.insert(member);

		// result=0;

		/*
		 * 스프링에서 제공하는 RedirectAttributes는 기존의 Servlet에서 사용되던 response.sendRedirect()를
		 * 사용할 때와 동일한 용도로 사용합니다. 리다이렉트로 전송하면 파라미터를 전달하고자 할 때 addAttribute()나
		 * addFlashAttribute()를 사용합니다. 예) response.sendRedirect("/test?result=1"); =>
		 * rattr.addAttribute("result",1);
		 */

		// member삽입이 된 경우
		// 회원 가입 결과에 따라 리다이렉트할 페이지를 결정합니다.
		if (result_member == AnytimeConstants.INSERT_COMPLETE) {

			// credit삽입
			credit.setUser_id(member.getUser_id());
			int result_credit = creditservice.insert(credit);
			// credit삽입 성공시
			if (result_credit == 1) {
				String sem[] = { "1학년 1학기", "1학년 2학기", "2학년 1학기", "2학년 2학기", "3학년 1학기", "3학년 2학기", 
						"4학년 1학기", "4학년 2학기", "5학년 1학기", "5학년 2학기", "6학년 1학기", "6학년 2학기", "기타 학기" };
				
				for(int i=0; i<sem.length; i++) {
				Semester semester = new Semester();
				semester.setCredit_id(credit.getCredit_id());
				semester.setSemester_name(sem[i]);
			//semester삽입	
			int result_semester	= semesterservice.insert(semester);
			// semester삽입 성공시
			if(result_semester ==1) {
			     for(int j =0; j <10; j++ ) {
			    	 Semester_detail semester_detail = new Semester_detail();
			    	 semester_detail.setSemester_id(semester.getSemester_id());
			    // semester_detail 삽입
		          semester_detailservice.insert(semester_detail);    	 
			  
			     }
				}
			}
		}
		rattr.addFlashAttribute("result", "joinSuccess");
		model.addAttribute("message", "회원가입 성공입니다.");
		return "redirect:login"; // 로그인 페이지로 이동
	}else

	{
		model.addAttribute("url", request.getRequestURL());
		model.addAttribute("message", "회원가입 실패입니다.");
		return "error/error"; // 회원 가입 폼 페이지로 이동
	}
	}

	// http://localhost:9700/anytime/member/forgotid
	// 아이디 찾기 폼 이동
	@RequestMapping(value = "/forgotid", method = RequestMethod.GET)
	public String forgotid() {
		return "member/forgotId";// WEB-IF/views/member/forgorId.jsp
	}

	// 아이디 찾기 이메일로 아이디 보내기
	@RequestMapping(value = "/forgotid_email", method = RequestMethod.POST)
	public String forgotid_email(@RequestParam("email") String email, Model model, RedirectAttributes rattr) {
		logger.info("email :" + email);

		// db에서 해당 email로 가입되어진 id 조회
		String foundId = memberservice.findIdByEmail(email);
		logger.info("foundId : " + foundId);

		if (foundId != null) {
			// 조회된 ID가 있으면, 해당 ID를 포함한 이메일 전송
			String subject = "애니타임 아이디 찾기";
			try {
				sendMail.sendFindIdEmail(email, subject, foundId);

				rattr.addFlashAttribute("result", "Success");
				model.addAttribute("message", "아이디 정보가 전송되었습니다.");
				return "redirect:/member/login"; // 로그인 페이지로 이동
			} catch (Exception e) {
				logger.error("아이디 찾기 메일 전송 실패", e);
				return "error/error";
			}
		} else {
			return "해당 이메일에 가입된 정보가 없습니다.";
		}
	}

	// http://localhost:9700/anytime/member/forgotpwd
	// 비밀번호 찾기 폼 이동
	@RequestMapping(value = "/forgotpwd", method = RequestMethod.GET)
	public String forgotpwd() {
		return "member/forgotPwd";// WEB-IF/views/member/forgotpwd.jsp
	}

	// http://localhost:9700/anytime/member/forgotpwd_mailcheck
	// 비밀번호 찾기 본인인증 jsp 이동
	@RequestMapping(value = "/forgotpwd_mailcheck", method = RequestMethod.POST)
	public String forgotpwd_mailcheck(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("id", request.getParameter("login_id"));

		return "member/forgotPwd_mailcheck";// WEB-IF/views/member/forgotpwd_mailcheck.jsp
	}

	// 비밀번호 찾기 본인인증 진행
	@ResponseBody
	@RequestMapping(value = "/forgotpwd_mailsend", method = RequestMethod.POST)
	public String forgotpwd_mailcheck(@RequestParam("email") String email, HttpSession session) {
		// 난수(인증번호 )생성
		String authCode = generateAuthCode();

		// 세션에 인증번호 저장
		session.setAttribute("authCode", authCode);

		// 이메일 발송
		String subject = "애니타임 비밀번호 찾기 본인 인증";
		try {
			sendMail.sendAuthEmail(email, subject, authCode);
		} catch (Exception e) {
			logger.error("메일 전송 실패", e);

		}
		return authCode;

	}

	// http://localhost:9700/anytime/member/forgotpwd_result
	// 비밀번호 찾기 비번 변경폼 이동
	@RequestMapping(value = "/forgotpwd_result", method = RequestMethod.POST)
	public String forgotpwd_result() {
		return "member/forgotPwd_result";// WEB-IF/views/member/forgotpwd_result.jsp
	}

	// 비밀번호 찾기 비번 변경
	@RequestMapping(value = "/forgotpwd_resultProcess", method = RequestMethod.POST)
	public String forgotpwdresultProcess(@RequestParam("password") String password, Member member,
			RedirectAttributes rattr, Model model, HttpServletRequest request) {
		// 비밀번호 암호화 추가
		String encPassword = passwordEncoder.encode(password);
		logger.info(encPassword);

		// HttpSession 객체 가져오기
		HttpSession session = request.getSession();

		String id = (String) session.getAttribute("id");

		// 비밀번호 변경 작업 수행
		memberservice.changePassword(id, encPassword);

		rattr.addFlashAttribute("changePassword", "Success");
		model.addAttribute("message", "비밀번호 수정이 완료되었습니다.");
		return "redirect:login";

	}

	// http://localhost:9700/anytime/member/memberAuth
	// 웹메일 인증 폼 이동
	@RequestMapping(value = "/certificate", method = RequestMethod.GET)
	public String certificate() {
		return "member/memberAuth";// WEB-IF/views/member/memberAuth.jsp
	}

}