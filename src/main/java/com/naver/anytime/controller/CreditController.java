package com.naver.anytime.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naver.anytime.domain.Credit;
import com.naver.anytime.domain.School;
import com.naver.anytime.domain.Semester;
import com.naver.anytime.domain.Semester_detail;
import com.naver.anytime.domain.TimeTable;
import com.naver.anytime.domain.TimeTable_detail;
import com.naver.anytime.domain.UserCustom;
import com.naver.anytime.service.CreditService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.SchoolService;
import com.naver.anytime.service.SemesterService;
import com.naver.anytime.service.Semester_detailService;
import com.naver.anytime.service.TimeTableService;
import com.naver.anytime.service.TimeTable_detailService;

@Controller
public class CreditController {

	private static final Logger logger = LoggerFactory.getLogger(CreditController.class);

	private MemberService memberservice;
	private SchoolService schoolservice;
	private CreditService creditservice;
	private SemesterService semesterservice;
	private Semester_detailService semester_detailservice;
	private TimeTableService timetableservice;
	private TimeTable_detailService timetable_detailservice;

	@Autowired
	public CreditController(MemberService memberservice, SchoolService schoolservice, CreditService creditservice,
			SemesterService semesterservice, Semester_detailService semester_detailservice,
			TimeTableService timetableservice, TimeTable_detailService timetable_detailservice) {

		this.memberservice = memberservice;
		this.schoolservice = schoolservice;
		this.creditservice = creditservice;
		this.semesterservice = semesterservice;
		this.semester_detailservice = semester_detailservice;
		this.timetableservice = timetableservice;
		this.timetable_detailservice = timetable_detailservice;

	}

	@RequestMapping(value = "/calculator", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView main(ModelAndView mv, @AuthenticationPrincipal UserCustom user) {
		int user_id = memberservice.getUserId(user.getUsername());

		Map<String, Object> school = new HashMap<String, Object>();
		String school_name = schoolservice.getSchoolNameById(user.getSchool_id());

		school.put("id", user.getSchool_id());
		school.put("name", school_name);
		school.put("domain", schoolservice.getSchoolDomain(school_name));
		school.put("credit", schoolservice.getCredit(school_name));

		List<Semester> semesters = semesterservice.getSemestersByUserId(user_id);
		List<Semester_detail> details = creditservice.getSemesterDetailsByUserId(user_id);

		int totalAcquisition = creditservice.getTotalAcquisition(user_id);
		mv.addObject("totalAcquisition", totalAcquisition);
		mv.addObject("semesters", semesters);

		Credit credit = creditservice.getCreditByUserId(user_id);

		if (school != null) {
			mv.addObject("school", school);
		}
		if (credit != null) {
			mv.addObject("credit", credit);
		}

		// 학점 계산 로직 추가
		double totalGradePoints = 0.0;
		double majorGradePoints = 0.0;
		int majorCredits = 0;

		Map<String, Double> gradeValueMap = new HashMap<>() {
			{
				put("A+", 4.5);
				put("A0", 4.3);
				put("A-", 4.0);
				put("B+", 3.5);
				put("B0", 3.3);
				put("B-", 3.0);
				put("C+", 2.5);
				put("C0", 2.3);
				put("C-", 2.0);
				put("D+", 1.5);
				put("D0", 1.3);
				put("D-", 1.0);
				put("F", 0.0);
				put("P", 0.0);
				put("NP", 0.0);

			}
		};

		for (Semester_detail detail : details) {
			double gradeValue = gradeValueMap.get(detail.getGrade());
			totalGradePoints += gradeValue * detail.getCredit();

			if (detail.isMajor()) {
				majorGradePoints += gradeValue * detail.getCredit();
				majorCredits += detail.getCredit();
			}
		}

		double totalGpa = totalAcquisition > 0 ? (totalGradePoints / totalAcquisition) : 0;
		double majorGpa = majorCredits > 0 ? (majorGradePoints / majorCredits) : 0;

		// 소수점 두 자리까지 반올림하여 문자열로 변환
		String formattedTotalGpa = String.format("%.2f", totalGpa);
		String formattedMajorGpa = String.format("%.2f", majorGpa);

		mv.addObject("totalgpa", formattedTotalGpa);
		mv.addObject("totalmajor", formattedMajorGpa);

		// 각 학기별 성적 계산 로직 시작(chart1 학기별 전체 평점, 전공 평점 조회)
		List<Map<String, String>> maplist = new ArrayList<>();

		for (Semester semester : semesters) {
			Map<String, String> map = new HashMap<>();
			double totalCredit = 0;
			double weightedSum = 0;// 학점 * 점수
			double weightedMajorSum = 0;
			double totalMajorCredit = 0;

			int semester_id = semester.getSemester_id();

			List<Semester_detail> detailPerSemester = semester_detailservice.getDetailPerSemester(semester_id); // 학기별

			for (Semester_detail detail : detailPerSemester) {
				double gradeValue = gradeValueMap.get(detail.getGrade());

				totalCredit += detail.getCredit();
				weightedSum += gradeValue * detail.getCredit();// 학점 * 점수
				totalGradePoints += gradeValue * detail.getCredit();

				if (detail.isMajor()) {
					totalMajorCredit += detail.getCredit();
					weightedMajorSum += gradeValue * detail.getCredit();
				}
			}
			double gpa = (totalCredit > 0) ? (weightedSum / totalCredit) : 0; // 평점
			double major = (totalMajorCredit > 0) ? (weightedMajorSum / totalMajorCredit) : 0;// 전공

			map.put("Gpa", String.format("%.2f", gpa));
			map.put("Major", String.format("%.2f", major));

			if (gpa != 0.00 || major != 0.00) {
				maplist.add(map);
			}

		}
		// 학기별 전체평점, 전공평범 list를 mv에 넣기
		mv.addObject("gpa", maplist);
		mv.setViewName("credit/credit");
		// chart1 x축 라벨 이름 출력
		List<Semester> semestername = semesterservice.getSemesternameByUserId(user_id);
		mv.addObject("semestername", semestername);

		// chart2 상위 5개 성적 출력하기
		List<Object[]> gradeData = semester_detailservice.findTop5Grades(user_id);
		mv.addObject("gradeData", gradeData);

		return mv;
	}

	@RequestMapping(value = "/getsemester_detail", method = RequestMethod.GET)
	@ResponseBody
	public List<Semester_detail> getsemester_detail(Principal principal, int semester_id) {

		List<Semester_detail> details = creditservice.getSemesterDetailsBySemesterId(semester_id);

		return details;
	}

	// update 경우 비동기되는 값 불러오기
	@RequestMapping(value = "/updatesemester_detail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatesemester_detail(@ModelAttribute Semester_detail semester_detail, ModelAndView mv,
			HttpServletRequest request, Principal principal, RedirectAttributes rattr) {

		int result = semester_detailservice.update(semester_detail);
		Map<String, Object> map = new HashMap<>();

		// 업데이트가 성공적으로 수행된 경우
		if (result == 1) {
			int user_id = memberservice.getUserId(principal.getName());
			int totalAcquisition = creditservice.getTotalAcquisition(user_id);
			List<Semester_detail> details = creditservice.getSemesterDetailsByUserId(user_id);

			double totalGradePoints = 0.0;
			double majorGradePoints = 0.0;
			int majorCredits = 0;

			Map<String, Double> gradeValueMap = new HashMap<>() {
				{
					put("A+", 4.5);
					put("A0", 4.3);
					put("A-", 4.0);
					put("B+", 3.5);
					put("B0", 3.3);
					put("B-", 3.0);
					put("C+", 2.5);
					put("C0", 2.3);
					put("C-", 2.0);
					put("D+", 1.5);
					put("D0", 1.3);
					put("D-", 1.0);
					put("F", 0.0);
					put("P", 0.0);
					put("NP", 0.0);

				}
			};

			for (Semester_detail detail : details) {
				double gradeValue = gradeValueMap.get(detail.getGrade());
				totalGradePoints += gradeValue * detail.getCredit();

				if (detail.isMajor()) {
					majorGradePoints += gradeValue * detail.getCredit();
					majorCredits += detail.getCredit();
				}
			}

			double totalGpa = totalAcquisition > 0 ? (totalGradePoints / totalAcquisition) : 0;
			double majorGpa = majorCredits > 0 ? (majorGradePoints / majorCredits) : 0;

			// 소수점 두 자리까지 반올림하여 문자열로 변환
			String formattedTotalGpa = String.format("%.2f", totalGpa);
			String formattedMajorGpa = String.format("%.2f", majorGpa);

			map.put("totalgpa", formattedTotalGpa);
			map.put("totalmajor", formattedMajorGpa);
			map.put("totalAcquisition", Integer.toString(totalAcquisition));

			// 각 학기별 성적 계산 로직 시작
			List<Map<String, String>> maplist = new ArrayList<>();
			List<Semester> semesters = semesterservice.getSemestersByUserId(user_id);

			for (Semester semester : semesters) {
				// ... 학기별 성적 계산 ...
				Map<String, String> gpamap = new HashMap<>();
				double totalCredit = 0;
				double weightedSum = 0;// 학점 * 점수
				double weightedMajorSum = 0;
				double totalMajorCredit = 0;

				int semester_id = semester.getSemester_id();

				List<Semester_detail> detailPerSemester = semester_detailservice.getDetailPerSemester(semester_id); // 학기별

				for (Semester_detail detail : detailPerSemester) {
					double gradeValue = gradeValueMap.get(detail.getGrade());

					totalCredit += detail.getCredit();
					weightedSum += gradeValue * detail.getCredit();// 학점 * 점수
					totalGradePoints += gradeValue * detail.getCredit();

					if (detail.isMajor()) {
						totalMajorCredit += detail.getCredit();
						weightedMajorSum += gradeValue * detail.getCredit();
					}
				}
				double gpa = (totalCredit > 0) ? (weightedSum / totalCredit) : 0; // 평점
				double major = (totalMajorCredit > 0) ? (weightedMajorSum / totalMajorCredit) : 0;// 전공

				gpamap.put("Gpa", String.format("%.2f", gpa));
				gpamap.put("Major", String.format("%.2f", major));

				if (gpa != 0.00 || major != 0.00) {
					maplist.add(gpamap);
				}
			}

			map.put("semestername", semesters);
			map.put("gpa", maplist);

			// chart2 상위 5개 성적 출력하기
			List<Object[]> gradeData = semester_detailservice.findTop5Grades(user_id);
			map.put("gradeData", gradeData);

		}
		int user_id = memberservice.getUserId(principal.getName());
		List<Semester> semestername = semesterservice.getSemesternameByUserId(user_id);
		map.put("semestername", semestername);

		return map;
	}

	@RequestMapping(value = "/updateGraduateCredit")
	public String uodateGraduateCredit(@RequestParam("graduate_credit") int graduate_credit, Model model) {

		int result = creditservice.updateGraduateCredit(graduate_credit);
		if (result == 1) {
			return "redirect:/calculator";
		} else {
			// 실패한 경우 에러 메시지를 모델에 추가하고 에러 페이지로 이동
			model.addAttribute("message", "Update failed");
			return "error";
		}
	}

	@RequestMapping(value = "/gettimetable", method = RequestMethod.GET)
	@ResponseBody
	public List<TimeTable> gettimetable(Principal principal) {
		int user_id = memberservice.getUserId(principal.getName());

		List<TimeTable> timetable = timetableservice.gettimetable(user_id);

		return timetable;

	}

	@RequestMapping(value = "/gettimetable_detail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> gettimetable_detail(@RequestParam("timetable_id") String timetableId,
			@RequestParam("semester_id") String semesterId) {

		Map<String, Object> response = new HashMap<>();

		// 시간표 상세 정보 조회
		List<TimeTable_detail> timetableDetail = timetable_detailservice.getsubject(timetableId);
		response.put("timetable_detail", timetableDetail);

		// 학기 상세 정보 조회
		List<Semester_detail> semesterDetail = semester_detailservice.getdetail(semesterId);
		response.put("semester_detail", semesterDetail);

		return response;

	}

}
