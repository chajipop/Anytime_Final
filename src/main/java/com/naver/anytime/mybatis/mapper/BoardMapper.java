package com.naver.anytime.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Board;

/*
 *  Mapper 인터페이스란 매퍼 파일에 기재된 SQL을 호출하기 위한 인터페이스입니다.
 *  MyBatis-Spring은 Mapper 인터페이스를 이용해서 실제 SQL 처리가 되는 클래스를 자동으로 생성합니다.
 */
@Mapper
public interface BoardMapper {

	// 보드 이름 구하기
	public List<Board> getBoardName(int board_id);

	// 보드 리스트
	public List<Board> getBoardList(HashMap<String, Integer> map);

	public Board getBoardDetail(int board_id);

	// 보드 익명 체크
	public int getBoardAnonymous(int board_id);

	// ********************************= 윤희 =********************************
	public int[] getBoardIds(String id);

	public int[] getBoardIdsByDomain(String schoolDomain);

	public int insertBoard(Board board);

	public List<Board> getBoardRequest();

	public int updateBoardStatus(int board_id, int approvalStatus);

	public int updateApprovalStatus(HashMap<String, Object> map);

	public List<Board> getBoardTotalList(HashMap<String, Object> map);

	public int getListCount(HashMap<String, Object> map);

	public int updateBoardStatusImmediately(int board_id, int approvalStatus, String rejectionreason);

	public int getBoardStatus(int board_id);

	public String getBoardnameByBoardId(int board_id);

	// ********************************= 윤희 =********************************

	public List<Board> getBoardContent(int board_id);

	public int updateBoardContent(int board_id, String content);

	public int getBoardManager(int board_id, int user_id);

	public int deleteBoard(String board_name, int user_id);

	public int getBoardName2(String board_name, int board_id);

	public int updateBoardUserId(int am_user_id_num, int tf_user_id_num, int board_id);

	public List<Board> getBoardlist(String login_id);

	public int getBoardAdminCount(int user_id);

	public int getBoardAnonymous2(int num);

	public int isBoardStatusCheck(int board_id);

	public int updateBoardStatusComplete();

	public int getBoardTypeCheck(int board_id);

	public int[] getBoardIdsBoardRequest();

	public int getUserIdByBoardId(int board_id);

	public String getRejectionReason(int board_id);

}