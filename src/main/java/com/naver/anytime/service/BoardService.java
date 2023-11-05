package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Board;

public interface BoardService {

	// postlist.jsp 에서 post 테이블의 board_id 로 name 구하기
	public List<Board> getBoardName(int board_id);

	// 보드 리스트
	public List<Board> getBoardList(int school_id);

	public Board getBoardDetail(int board_id);

	// 보드 익명 체크
	public int getBoardAnonymous(int board_id);

	// ********************************= 윤희 =********************************
	public int[] getBoardIds(String id);

	public int[] getBoardIdsByDomain(String schoolDomain);

	public int insertBoard(Board board);

	public List<Board> getBoardRequest();

	public int updateBoardStatus(int board_id, int approvalStatus);

	public List<Board> getBoardTotalList(int page, int limit, int searchKey, String keyword);

	public int getListCount(int searchKey, String keyword);

	public int[] getBoardIdsBoardRequest();

	public int getUserIdByBoardId(int i);

	public String getRejectionReason(int i);
	
	// ********************************= 윤희 =********************************

	public List<Board> getBoardContent(int board_id);

	public int updateBoardContent(int board_id, String content);

	public int getBoardManager(int board_id, int user_id);

	public int deleteBoard(String board_name, int user_id);

	public int deleteBoardAuth(int board_id);

	public int getBoardName2(String board_name, int board_id);

	public int updateBoardUserId(int am_user_id_num, int tf_user_id_num, int board_id);

	public int updateBoardAuth(int am_user_id_num, int tf_user_id_num, int board_id);

	public int getBoardAdminCount(int user_id);

	public int getBoardAnonymous2(int num);

	public int isBoardStatusCheck(int board_id);

	public int updateApprovalStatus(int board_id, int approvalStatus, String rejectionreason);

	public int updateBoardStatusComplete();

	public int updateBoardStatusImmediately(int board_id, int approvalStatus, String rejectionreason);

	public int getBoardStatus(int board_id);

	public int getBoardTypeCheck(int board_id);

	public String getBoardnameByBoardId(int board_id);

}