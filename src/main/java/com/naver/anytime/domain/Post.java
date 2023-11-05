package com.naver.anytime.domain;

public class Post {
	private int POST_ID; // 게시물 고유번호
	private int BOARD_ID; // 게시판 고유번호
	private int USER_ID; // 유저 고유번호
	private String SUBJECT; // 글 제목
	private String CONTENT; // 글 내용
	private String POST_FILE; // 첨부파일
	private String POST_DATE; // 글 작성 시간
	private int LIKE_COUNT; // 공감 수
	private int SCRAP_COUNT; // 스크랩 수
	private int REPORT_COUNT; // 신고 수
	private int STATUS; // 게시물 상태

	/* 게시물(POST) 테이블 별도로 추가부분 */
	private int COMMENT_COUNT; // 코멘트 수
	private String NICKNAME; // 유저 번호로 닉네임 매칭
	private String BOARDNAME; // 게시판 번호로 게시판이름 매칭
	private String LOGIN_ID;

	private String uploadfile;
	private String POST_ORIGINAL; // 첨부될 파일의 이름

	private int school_id;
	private String FORMATTED_DATE;

	// 보드 타입 & 보드 익명 & 작성자 탈퇴 여부
	private int BOARD_TYPE;
	private int BOARD_ANONYMOUS;
	private int USER_STATUS;

	/* ---------- POST테이블 Getter/Setter ---------- */
	public String getUploadfile() {
		return uploadfile;
	}

	public int getBOARD_TYPE() {
		return BOARD_TYPE;
	}

	public void setBOARD_TYPE(int bOARD_TYPE) {
		BOARD_TYPE = bOARD_TYPE;
	}

	public int getBOARD_ANONYMOUS() {
		return BOARD_ANONYMOUS;
	}

	public void setBOARD_ANONYMOUS(int bOARD_ANONYMOUS) {
		BOARD_ANONYMOUS = bOARD_ANONYMOUS;
	}

	public void setUploadfile(String uploadfile) {
		this.uploadfile = uploadfile;
	}

	public String getPOST_ORIGINAL() {
		return POST_ORIGINAL;
	}

	public void setPOST_ORIGINAL(String pOST_ORIGINAL) {
		POST_ORIGINAL = pOST_ORIGINAL;
	}

	public String getLOGIN_ID() {
		return LOGIN_ID;
	}

	public void setLOGIN_ID(String lOGIN_ID) {
		LOGIN_ID = lOGIN_ID;
	}

	public int getPOST_ID() {
		return POST_ID;
	}

	public void setPOST_ID(int pOST_ID) {
		POST_ID = pOST_ID;
	}

	public int getBOARD_ID() {
		return BOARD_ID;
	}

	public void setBOARD_ID(int bOARD_ID) {
		BOARD_ID = bOARD_ID;
	}

	public int getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(int uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getSUBJECT() {
		return SUBJECT;
	}

	public void setSUBJECT(String SUBJECT) {
		this.SUBJECT = SUBJECT;
	}

	public String getCONTENT() {
		return CONTENT;
	}

	public void setCONTENT(String CONTENT) {
		this.CONTENT = CONTENT;
	}

	public String getPOST_FILE() {
		return POST_FILE;
	}

	public void setPOST_FILE(String pOST_FILE) {
		POST_FILE = pOST_FILE;
	}

	public String getPOST_DATE() {
		return POST_DATE;
	}

	public void setPOST_DATE(String pOST_DATE) {
		POST_DATE = pOST_DATE;
	}

	public String getFormattedPOST_DATE() {
		if (POST_DATE != null && POST_DATE.length() >= 16) {
			return POST_DATE.substring(5, 16);
		}
		return POST_DATE;
	}

	public int getLIKE_COUNT() {
		return LIKE_COUNT;
	}

	public void setLIKE_COUNT(int lIKE_COUNT) {
		LIKE_COUNT = lIKE_COUNT;
	}

	public int getSCRAP_COUNT() {
		return SCRAP_COUNT;
	}

	public void setSCRAP_COUNT(int sCRAP_COUNT) {
		SCRAP_COUNT = sCRAP_COUNT;
	}

	public int getREPORT_COUNT() {
		return REPORT_COUNT;
	}

	public void setREPORT_COUNT(int rEPORT_COUNT) {
		REPORT_COUNT = rEPORT_COUNT;
	}

	public int getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}

	public int getCOMMENT_COUNT() {
		return COMMENT_COUNT;
	}

	public void setCOMMENT_COUNT(int cOMMENT_COUNT) {
		COMMENT_COUNT = cOMMENT_COUNT;
	}

	public String getNICKNAME() {
		return NICKNAME;
	}

	public void setNICKNAME(String nICKNAME) {
		NICKNAME = nICKNAME;
	}

	public String getBOARDNAME() {
		return BOARDNAME;
	}

	public void setBOARDNAME(String bOARDNAME) {
		BOARDNAME = bOARDNAME;
	}

	/* ---------- POST테이블 toString ---------- */
	@Override
	public String toString() {
		return "Post [POST_ID=" + POST_ID + ", BOARD_ID=" + BOARD_ID + ", USER_ID=" + USER_ID + ", SUBJECT=" + SUBJECT
				+ ", CONTENT=" + CONTENT + ", POST_FILE=" + POST_FILE + ", POST_DATE=" + POST_DATE + ", LIKE_COUNT="
				+ LIKE_COUNT + ", SCRAP_COUNT=" + SCRAP_COUNT + ", REPORT_COUNT=" + REPORT_COUNT + ", STATUS=" + STATUS
				+ ", COMMENT_COUNT=" + COMMENT_COUNT + ", NICKNAME=" + NICKNAME + ", BOARDNAME=" + BOARDNAME
				+ ", LOGIN_ID=" + LOGIN_ID + ", uploadfile=" + uploadfile + ", POST_ORIGINAL=" + POST_ORIGINAL + "]";
	}

	// ' MM-DD HH:MI ' 포맷
	public String getFORMATTED_DATE() {
		return FORMATTED_DATE;
	}

	public void setFORMATTED_DATE(String fORMATTED_DATE) {
		FORMATTED_DATE = fORMATTED_DATE;
	}

	// hot, best 용
	public int getSchool_id() {
		return school_id;
	}

	public void setSchool_id(int school_id) {
		this.school_id = school_id;
	}

	public int getUSER_STATUS() {
		return USER_STATUS;
	}

	public void setUSER_STATUS(int uSER_STATUS) {
		USER_STATUS = uSER_STATUS;
	}

}
