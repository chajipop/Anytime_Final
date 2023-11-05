package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Board;
import com.naver.anytime.domain.Member;
import com.naver.anytime.domain.School;

public interface MemberService {

	public int isId(String id, String pass);

	public int isId(String id);

	public int isNickname(String nickname);

	public String getNickname(String login_id);

	public int isEmail(String email);

	public String getEmail(String login_id);

	public int getSchoolIdByName(String campusName);

	public int insert(Member m);

	public Member member_info(String id);

	public void delete(String id);

	public List<Member> getSearchList(int index, String search_word, int page, int limit);

	public int getSearchListCount(int index, String search_word);

	public Member findMemberByUserId(int user_id);

	public String findIdByEmail(String email);

	void changePassword(String login_id, String password);

	public int updateschoolcheck(String id);

	int isLoginId(String id, String password);

	// 윤희
	public String getSchoolDomain(String id);

	public Member getLoginMember(String id);

	public int getSchoolId(String id);

	public School getSchool(String id);

	public int getUserId(String login_id);

	public String getPwd(String login_id);

	public int updateMember(Member member);

	public int updateStatusInactive(String login_id);

	public int isBoardAdmin(String login_id);

	public List<Board> getBoardlist(String login_id);

	public void updateStatusByContentId(int content_id, int isContent);

	public String isAdmin(String username);

	public int IsBoard_admin(String login_id);

	public int getuserIdByBoardId(int board_id);

	// * * * * * * * * * * * * * * * < ok > * * * * * * * * * * * * * * * * * *
	// 기존 getSchoolId 가 integer가 아니라 새로 생성
	public Integer getSchoolId2(String id);

	public int updateBoardAdminDelete(int user_id);

	public int updateBoardAdminAdd(int user_id);

	public Integer getStatusCheck(String userid);

	public String getNickName2(int user_id);

	public int getUserIdConversion(int num); // num = post_id or comment_id

	public Integer getStatusCheck2(int user_id);

	public int getUserSchoolCheck(int user_id);
	// * * * * * * * * * * * * * * * < ok > * * * * * * * * * * * * * * * * * *

}
