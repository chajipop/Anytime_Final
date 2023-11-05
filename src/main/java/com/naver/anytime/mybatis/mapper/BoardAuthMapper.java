package com.naver.anytime.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

/*
 *  Mapper 인터페이스란 매퍼 파일에 기재된 SQL을 호출하기 위한 인터페이스입니다.
 *  MyBatis-Spring은 Mapper 인터페이스를 이용해서 실제 SQL 처리가 되는 클래스를 자동으로 생성합니다.
 */
@Mapper
public interface BoardAuthMapper {

	// ********************************= 윤희 =********************************
	public void insertBoardAuth(int board_id, int user_id);

	public int deleteBoardAuth(int board_id);
	
	public int updateBoardAuth(int am_user_id_num, int tf_user_id_num, int board_id);

}
