package com.naver.anytime.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naver.anytime.domain.Board;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.MemberService;
import com.naver.constants.AnytimeConstants;

@RestController
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	private BoardService boardService;
	private MemberService memberService;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public BoardController(BoardService boardService, MemberService memberService, PasswordEncoder passwordEncoder) {
		this.boardService = boardService;
		this.memberService = memberService;
		this.passwordEncoder = passwordEncoder;
	}

	@RequestMapping(value = "/submenulist", method = RequestMethod.GET)
	@ResponseBody
	public List<Board> getBoardList(@RequestParam("school_id") int school_id) {

		List<Board> responseData = boardService.getBoardList(school_id);
		// 생성된 JSON 데이터를 클라이언트에 응답으로 전송
		return responseData;
	}

	@PostMapping(value = "/create")
	public String insertBoard(Board board, String LOGIN_ID, Model mv, RedirectAttributes rattr,
			HttpServletRequest request) {
		String url = "";
		int user_id = memberService.getUserId(LOGIN_ID);
		board.setUSER_ID(user_id);

		if (board.getTYPE() == AnytimeConstants.GROUP_BOARD || board.getTYPE() == AnytimeConstants.DEPARTMENT_BOARD) {
			board.setSTATUS(AnytimeConstants.STATUS_INACTIVE);
		} else {
			board.setSTATUS(AnytimeConstants.STATUS_ACTIVE);
		}

		int result = boardService.insertBoard(board);
		if (result == AnytimeConstants.INSERT_COMPLETE) {
			logger.info("게시판 생성 완료");
			rattr.addFlashAttribute("result", "insertBoardSuccess");
			url = "redirect:boardlist";
		} else {
			logger.info("게시판 생성 실패");
			mv.addAttribute("url", request.getRequestURL());
			mv.addAttribute("message", "게시판 생성 실패");
			url = "error/error";
		}

		return url;
	}

	@RequestMapping(value = "/getboardcontent", method = RequestMethod.GET)
	@ResponseBody
	public List<Board> getBoardContent(@RequestParam("board_id") int board_id) {
		System.out.println("BoardController 에서 content 받아오는 메서드 테스트 [board_id] = " + board_id);
		List<Board> boardContentData = boardService.getBoardContent(board_id);
		return boardContentData;
	}

	@RequestMapping(value = "/updateboardcontent", method = RequestMethod.GET)
	@ResponseBody
	public int updateBoardSetting(@RequestParam("board_id") int board_id, @RequestParam("content") String content) {

		int updateData = boardService.updateBoardContent(board_id, content);
		System.out.println("BoardController에서 content 업데이트 성공 여부 테스트 [0 / 1] = " + updateData);
		return updateData;
	}

	@RequestMapping(value = "/managercheck", method = RequestMethod.GET)
	@ResponseBody
	public int boardManagerCheck(@RequestParam("board_id") int board_id, @RequestParam("LOGIN_ID") String login_id) {
		int managerCheck = 0;
		int user_id = memberService.getUserId(login_id);
		int check = boardService.getBoardManager(board_id, user_id);

		if (check != 0) {
			managerCheck = 1;
		}

		System.out.println("BoardController에서 보드 권한 체크 테스트 [0 / 1] = " + managerCheck);

		return managerCheck;
	}

	@RequestMapping(value = "/deleteboard", method = RequestMethod.GET)
	@ResponseBody
	public int deleteBoard(@RequestParam(name = "board_id", defaultValue = "-1") int board_id,
			@RequestParam("board_name") String board_name, @RequestParam("LOGIN_ID") String login_id) {
		int deleteResult = 0;
		int boardnamecheck = boardService.getBoardName2(board_name, board_id);

		if (board_id > 0) {
			if (boardnamecheck == 1) {
				System.out.println("[1/3] board_id 체크" + board_id);
				int user_id = memberService.getUserId(login_id);
				int check_auth = boardService.deleteBoardAuth(board_id);

				System.out.println("[1/3] 유저 아이디 [" + user_id + "] 체크 권한 [" + check_auth + "]");

				if (check_auth == 1) {
					System.out.println("[2/3] board_auth 삭제 체크");
					int check = boardService.deleteBoard(board_name, user_id);
					System.out.println("[2/3] 보드 삭제 [" + check + "]");

					if (check_auth == 1 && check == 1) {
						System.out.println("[3/3] 최종 완료");
						deleteResult = 1;

						int boardadmincheck = boardService.getBoardAdminCount(user_id);
						if (boardadmincheck == 0) {
							memberService.updateBoardAdminDelete(user_id);
							System.out.println("해당 유저가 관리하는 보드가 없으므로 board_admin -> 0 으로 변경");
						}
					}
				} else {
					deleteResult = 3; // 권한 삭제 문제 생겼을때
				}
			} else {
				deleteResult = 2; // 보드 이름이 다를때
			}
		}

		System.out.println("보드네임 체크 [" + boardnamecheck + "]");
		System.out.println("보내는값 체크 [" + deleteResult + "]");
		System.out.println("BoardController 삭제 테스트에서 가져온 보드id [" + board_id + "] 가져온 보드name [" + board_name
				+ "] 가져온 로그인id [" + login_id + "]");

		return deleteResult;
	}

	@RequestMapping(value = "/updatemanagerboard", method = RequestMethod.GET)
	@ResponseBody
	public int updateManagerBoard(@RequestParam(name = "board_id", defaultValue = "-1") int board_id,
			@RequestParam("password") String password, @RequestParam("userid") String userid, Principal principal,
			HttpSession session) {
		int updateManagerBoardResult = 0;
		String loginid = principal.getName(); // 양도인(로그인한) 유저 아이디 ex)uniuni
		String dbPwd = memberService.getPwd(loginid); // 양도인(로그인한) 유저 db 보안 비밀번호 ex)$1b$592qa.Ql2 ...
		Integer idcheck = memberService.isId(userid); // 피양도인 존재 유저 확인
		int schooltest = 0;

		Integer am_school_num = memberService.getSchoolId2(loginid); // 양도인 유저의 스쿨 번호
		Integer tf_school_num = memberService.getSchoolId2(userid); // 피양도인 유저의 스쿨 번호
		if (am_school_num == null) {
			am_school_num = 0;
		}
		if (tf_school_num == null) {
			tf_school_num = 0;
		}

		System.out.println("양도 유저 학교 번호 체크 / 양도인 [" + am_school_num + "] 피양도인 [" + tf_school_num + "]");
		if (am_school_num == tf_school_num) { // 같은 학교 학생 확인
			schooltest = 1;
		}

		if (idcheck == 1) {
			if (schooltest == 1) {
				if (passwordEncoder.matches(password, dbPwd)) {
					int am_user_id_num = memberService.getUserId(loginid); // 양도인 유저번호 구하기
					int tf_user_id_num = memberService.getUserId(userid); // 피양도인 유저번호 구하기
					int result1 = boardService.updateBoardAuth(am_user_id_num, tf_user_id_num, board_id);

					if (am_user_id_num != 0 && tf_user_id_num != 0 && result1 == 1) {
						int result2 = boardService.updateBoardUserId(am_user_id_num, tf_user_id_num, board_id);
						if (result2 == 1) {
							updateManagerBoardResult = 1; // board 테이블 업데이트 성공
						}
					} else {
						System.out.println("양도인 체크[" + am_user_id_num + "] 피양도인 체크 [" + tf_user_id_num + "] 권한업데이트 체크 ["
								+ result1 + "]");
						updateManagerBoardResult = 2; // board_auth 테이블 업데이트 실패
					}
				} else {
					updateManagerBoardResult = 3; // db password 와 입력 password 매칭 실패
				}
			} else {
				updateManagerBoardResult = 4; // 같은 학교 학생이 아님
			}
		} else {
			updateManagerBoardResult = 5; // 존재 유저가 아님
		}
		System.out.println("최종 승인 값 [" + updateManagerBoardResult + "]");
		return updateManagerBoardResult;
	}

}