package com.naver.anytime.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.TimeTable;
import com.naver.anytime.domain.TimeTable_detail;
import com.naver.anytime.domain.UserCustom;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.SchoolService;
import com.naver.anytime.service.TimeTableService;
import com.naver.anytime.service.TimeTable_detailService;

@Controller
public class TimeTableController {

	private static final Logger logger = LoggerFactory.getLogger(TimeTableController.class);

	private TimeTableService timeTableService;
	private TimeTable_detailService timeTable_detailService;
	private MemberService memberService;
	private SchoolService schoolService;

	@Autowired
	public TimeTableController(TimeTableService timeTableService, TimeTable_detailService timeTable_detailService,
			MemberService memberService, SchoolService schoolService) {
		this.timeTableService = timeTableService;
		this.timeTable_detailService = timeTable_detailService;
		this.memberService = memberService;
		this.schoolService = schoolService;
	}

	// 시간표 View 출력
	@RequestMapping(value = "/timetable", method = RequestMethod.GET)
	public ModelAndView timeTable(
			@AuthenticationPrincipal UserCustom user,
			ModelAndView mv) {
		
		Map<String, Object> school = new HashMap<String, Object>();
		String school_name = schoolService.getSchoolNameById(user.getSchool_id());

		school.put("id", user.getSchool_id());
		school.put("name", school_name);
		school.put("domain", schoolService.getSchoolDomain(school_name));
		mv.setViewName("timetable/timeTable");
		mv.addObject("school", school);
		return mv;
		
	}
	
	// 시간표 학기별로 출력
	@RequestMapping(value = "/getTimetableByUserIdAndSemester", method = RequestMethod.POST)
	@ResponseBody
	public List<TimeTable> getTimetableBySemester(
			@RequestParam(value = "semester", defaultValue = "2023년 2학기", required = false) String semester,
			Principal userPrincipal) {
		String id = userPrincipal.getName();
		int user_id = memberService.getUserId(id);
		logger.info("유저아이디" + user_id);

		if (id == null) {
			throw new RuntimeException("사용자가 로그인하지 않았습니다.");
		}

		int timetable_check = timeTableService.checkTimetable(user_id, semester);
		List<TimeTable> timetable = new ArrayList<TimeTable>();

		if (timetable_check > 0) {
			timetable = timeTableService.getTimetableByUserIdAndSemester(user_id, semester);

		} else {
			int key = timeTableService.createDefaultTimeTable(user_id, semester);

			TimeTable new_timetable = timeTableService.getNewTimetable(key);
			logger.info("key 값 출력 : " + key);
			timetable.add(new_timetable);
		}
		return timetable;
	}
	
	// 시간표 이름 출력
	@RequestMapping(value = "/updateTimetable", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> updateTimetable(@RequestParam int timetable_id, 
												  @RequestParam String newName,
												  @RequestParam int  status,
												  Principal userPrincipal) {
		try {
			logger.info("기본시간표 : "+status);
			String id = userPrincipal.getName();
			timeTableService.updateTimetable(memberService.getUserId(id), timetable_id, newName, status);
			return new ResponseEntity<>("이름 변경 성공", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("이름 변경 실패: ", e);
			return new ResponseEntity<>("이름 변경 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 새 시간표 생성
	@RequestMapping(value = "/createNewTimeTable", method = RequestMethod.POST)
	@ResponseBody
	public TimeTable createNewTimeTable(@RequestParam(value = "semester") String semester, Principal userPrincipal) {
		String id = userPrincipal.getName();
		int key = timeTableService.createNewTimeTable(memberService.getUserId(id), semester);

		return timeTableService.getNewTimetable(key);
	}
	
	// 생성 시간표 선택 
	@RequestMapping(value = "/loadTimetableDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> loadTimetableDetails(@RequestParam(value = "timetable_id") int timetable_id) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<TimeTable_detail> timetalbeDetails =timeTable_detailService.getTimetableDetails(timetable_id);
		TimeTable timetable = timeTableService.getTimeTableById(timetable_id);
		map.put("timetalbeDetails", timetalbeDetails);
		map.put("timetable", timetable);
		
		return map;
	}
	
	// 새 수업 추가
	@RequestMapping(value="/addSubject", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> addSubject(TimeTable_detail detail) {
	   Map<String, Object> result = new HashMap<>();
	   try {
	      
	      timeTable_detailService.addSubject(detail); 
	      
	      result.put("newClass", detail);
	      result.put("subject_id", detail.getSubject_id());
	      
	      result.put("message", "새로운 과목이 성공적으로 추가되었습니다.");
	      result.put("status", "success");
	      return new ResponseEntity<>(result, HttpStatus.OK);
	  } catch(Exception e) {
	     logger.error("과목 추가 실패 : ", e);

	     result.put("message", "과목을 추가하는 동안 오류가 발생했습니다.");
	     result.put("status", "error");
	     return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}

	// 생성 시간표 삭제
	@RequestMapping(value = "/deleteTimetable", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, String>> deleteTimetable(@RequestParam(value = "timetable_id") int timetable_id,
															   @RequestParam(value = "status") int status,
															   Principal userPrincipal) {
	    HashMap<String, String> result = new HashMap<>();
	    try {
	    	String id = userPrincipal.getName();
	         timeTableService.deleteTimetable(memberService.getUserId(id), timetable_id, status);
	        
	        result.put("message", "시간표 삭제 성공");
	        return new ResponseEntity<>(result, HttpStatus.OK);
	    } catch (Exception e) {
	        logger.error("시간표 삭제 실패: ", e);
	        
	        result.put("message", "시간표 삭제 실패");
	        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	// 추가수업 삭제
	@RequestMapping(value = "/deleteSubject", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> deleteSubject(@RequestParam("timetable_id") Integer timetable_id, 
											@RequestParam("subject_id") Integer subject_id,
											Principal userPrincipal) {
		try {
	        String id = userPrincipal.getName();
	        timeTable_detailService.deleteSubject(memberService.getUserId(id), timetable_id, subject_id);
	        
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "수업 삭제 성공");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "수업 삭제 실패");
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}
