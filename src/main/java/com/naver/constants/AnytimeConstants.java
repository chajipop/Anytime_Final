package com.naver.constants;

public class AnytimeConstants {

	public static final int ID_PASSWORD_MISMATCH = 0;
	public static final int ID_PASSWORD_MATCH = 1;

	public static final int ID_EXISTS = 1;
	public static final int ID_NOT_EXISTS = -1;

	public static final int NICKNAME_EXISTS = 1;
	public static final int NICKNAME_NOT_EXISTS = -1;

	public static final int EMAIL_EXISTS = 1;
	public static final int EMAIL_NOT_EXISTS = -1;

	public static final int UPDATE_COMPLETE = 1;
	public static final int INSERT_COMPLETE = 1;
	public static final int DELETE_COMPLETE = 1;

	public static final int COMMON_BOARD = 1;
	public static final int GROUP_BOARD = 2;
	public static final int DEPARTMENT_BOARD = 3;
	public static final int CUSTOM_BOARD = 4;

	public static final int STATUS_ACTIVE = 1;
	public static final int STATUS_INACTIVE = 0;

	public static final int BOARD_ADMIN = 1;
	public static final int NOT_BOARD_ADMIN = 0;

	// 게시판 관리자 승인절차 미진행 상태
	public static final int BOARD_APPROVAL_PENDING = 0;

	// 게시판 관리자 승인 완료 진행 상태(활성화 예정 - 활성화)
	public static final int BOARD_APPROVAL_SCHEDULED = 3;
	public static final int BOARD_APPROVAL_COMPLETED = 1;

	// 게시판 관리자 승인 거부 진행 상태(비활성화 예정 - 비활성화)
	public static final int BOARD_APPROVAL_SCHEDULED_FOR_DENIAL = 4;
	public static final int BOARD_APPROVAL_DENIED = 2;

	public static final int NON_EXISTENT = 0;
	public static final int EXISTENT = 1;

	public static final int IS_POST = 1;
	public static final int IS_COMMENT = 2;

	public static final int ADMIN_NOTICE = 1;
	public static final int ADMIN_ID = 1;
	
	
}
