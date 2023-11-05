package com.naver.anytime.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Board;
import com.naver.anytime.domain.Member;
import com.naver.anytime.domain.School;
import com.naver.anytime.mybatis.mapper.BoardMapper;
import com.naver.anytime.mybatis.mapper.MemberMapper;
import com.naver.constants.AnytimeConstants;

@Service
public class MemberServiceImpl implements MemberService {
	private MemberMapper dao;
	private PasswordEncoder passwordEncoder;

	private BoardMapper boardDao;

	@Autowired
	public MemberServiceImpl(MemberMapper dao, BoardMapper boardDao, PasswordEncoder passwordEncoder) {
		this.dao = dao;
		this.passwordEncoder = passwordEncoder;

		this.boardDao = boardDao;
	}

	@Override
	public int isLoginId(String id, String password) {
		Member dbmember = dao.isLoginId(id);
		int result = AnytimeConstants.ID_NOT_EXISTS; // 아이디가 존재하지 않는 경우 - rmember가 null인 경우
		if (dbmember != null) {// 아이디가 존재하는 경우
			// passwordEncoder.matches(rawPassword,encodedPassword)
			// 사용자에게 입력받은 패스워드를 비교하고자 할 때 사용하는 메서드입니다.
			// rawPassword : 사용자가 입력한 패스워드
			// encodedPassword : DB에 저장된 패스워드
			if (passwordEncoder.matches(password, dbmember.getPassword())) {
				result = AnytimeConstants.ID_PASSWORD_MATCH;// 아이디와 비밀번호가 일치하는 경우
			} else
				result = AnytimeConstants.ID_PASSWORD_MISMATCH;// 아이디는 존재하지만 비밀번호가 일치하지 않는 경우
		}
		return result;
	}

	@Override
	public int isId(String id, String password) {
		Member dbmember = dao.isId(id);
		int result = AnytimeConstants.ID_NOT_EXISTS; // 아이디가 존재하지 않는 경우 - rmember가 null인 경우
		if (dbmember != null) {// 아이디가 존재하는 경우
			// passwordEncoder.matches(rawPassword,encodedPassword)
			// 사용자에게 입력받은 패스워드를 비교하고자 할 때 사용하는 메서드입니다.
			// rawPassword : 사용자가 입력한 패스워드
			// encodedPassword : DB에 저장된 패스워드
			if (passwordEncoder.matches(password, dbmember.getPassword())) {
				result = AnytimeConstants.ID_PASSWORD_MATCH;// 아이디와 비밀번호가 일치하는 경우
			} else
				result = AnytimeConstants.ID_PASSWORD_MISMATCH;// 아이디는 존재하지만 비밀번호가 일치하지 않는 경우
		}
		return result;
	}

	@Override
	public int isId(String id) {
		Member rmember = dao.isId(id);
		return (rmember == null) ? AnytimeConstants.ID_NOT_EXISTS : AnytimeConstants.ID_EXISTS;
	}

	@Override
	public int isNickname(String nickname) {
		Member rmember = dao.isNickname(nickname);
		return (rmember == null) ? AnytimeConstants.NICKNAME_NOT_EXISTS : AnytimeConstants.NICKNAME_EXISTS;
	}

	@Override
	public String getNickname(String login_id) {
		return dao.getNickname(login_id);
	}

	@Override
	public int isEmail(String email) {
		Member rmember = dao.isEmail(email);
		return (rmember == null) ? AnytimeConstants.EMAIL_NOT_EXISTS : AnytimeConstants.EMAIL_EXISTS;
	}

	@Override
	public String getEmail(String login_id) {
		return dao.getEmail(login_id);
	}

	@Override
	public int getSchoolIdByName(String campusName) {
		return dao.getSchoolIdByName(campusName);
	}

	@Override
	public int insert(Member m) {
		return dao.insert(m);
	}

	@Override
	public String findIdByEmail(String email) {
		return dao.findIdByEmail(email);
	}

	@Override
	public void changePassword(String login_id, String password) {
		dao.updatePassword(login_id, password);

	}

	@Override
	public Member member_info(String id) {
		return dao.isId(id);
	}

	@Override
	public void delete(String id) {
		dao.delete(id);
	}

	@Override
	public List<Member> getSearchList(int index, String search_word, int page, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();

		// http://localhost:8088/myhome4/member/list로 접속하는 경우
		// select를 선택하지 않아 index는 "-1"의 값을 갖습니다.
		// 이 경우 아래의 문장을 수행하지 않기 때문에 "search_word" 키에 대한
		// map.get("search_field")의 값은 null이 됩니다.
		if (index != -1) {
			String[] search_field = new String[] { "id", "name", "age", "gender" };
			map.put("search_field", search_field[index]);
			map.put("search_word", "%" + search_word + "%");
		}
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("start", startrow);
		map.put("end", endrow);
		return dao.getSearchList(map);
	}

	@Override
	public int getSearchListCount(int index, String search_word) {
		Map<String, String> map = new HashMap<String, String>();
		if (index != -1) {
			String[] search_field = new String[] { "id", "name", "age", "gender" };
			map.put("search_field", search_field[index]);
			map.put("search_word", "%" + search_word + "%");
		}
		return dao.getSearchListCount(map);

	}

	@Override
	public Member findMemberByUserId(int user_id) {
		return dao.findMemberByUserId(user_id);
	}

	@Override
	public int updateschoolcheck(String id) {

		return dao.updateschoolcheck(id);
	}

	@Override
	public int getuserIdByBoardId(int board_id) {
		return dao.getUserIdByBoardId(board_id);
	}

	// ----------------------------윤희----------------------------

	@Override
	public String getSchoolDomain(String id) {
		return dao.getSchoolDomain(id);
	}

	@Override
	public Member getLoginMember(String id) {
		return dao.getLoginMember(id);
	}

	@Override
	public int getSchoolId(String id) {
		return dao.getSchoolId(id);
	}

	@Override
	public School getSchool(String id) {
		return dao.getSchool(id);
	}

	@Override
	public int getUserId(String login_id) {
		return dao.getUserId(login_id);
	}

	public int updateBoardAdmin(int user_id) {
		return dao.updateBoardAdmin(user_id);
	}

	@Override
	public String getPwd(String login_id) {
		return dao.getPwd(login_id);
	}

	@Override
	public int updateMember(Member member) {
		return dao.updateMember(member);
	}

	@Override
	public int updateStatusInactive(String login_id) {
		return dao.updateStatusInactive(login_id);
	}

	@Override
	public int isBoardAdmin(String login_id) {
		return dao.getBoardAdmin(login_id);
	}

	@Override
	public List<Board> getBoardlist(String login_id) {
		return boardDao.getBoardlist(login_id);
	}

	@Override
	public void updateStatusByContentId(int content_id, int isContent) {
		dao.updateStatusByContentId(content_id, isContent);
	}

	@Override
	public String isAdmin(String username) {
		return dao.isAdmin(username);
	}

	@Override
	public int IsBoard_admin(String login_id) {
		return dao.IsBoard_admin(login_id);
	}

	// * * * * * * * * * * * * * * * < ok > * * * * * * * * * * * * * * * * * *
	@Override
	public Integer getSchoolId2(String id) {
		return dao.getSchoolId2(id);
	}

	@Override
	public int updateBoardAdminDelete(int user_id) {
		return dao.updateBoardAdminDelete(user_id);
	}

	@Override
	public int updateBoardAdminAdd(int user_id) {
		return dao.updateBoardAdminAdd(user_id);
	}

	@Override
	public Integer getStatusCheck(String login_id) {
		return dao.getStatusCheck(login_id);
	}

	@Override
	public String getNickName2(int user_id) {
		return dao.getNickName2(user_id);
	}

	@Override
	public int getUserIdConversion(int num) {
		return dao.getUserIdConversion(num); // num = post_id or comment_id
	}

	@Override
	public Integer getStatusCheck2(int user_id) {
		return dao.getStatusCheck2(user_id);
	}

	@Override
	public int getUserSchoolCheck(int user_id) {
		return dao.getUserSchoolCheck(user_id);
	}
	// * * * * * * * * * * * * * * * < ok > * * * * * * * * * * * * * * * * * *

}
