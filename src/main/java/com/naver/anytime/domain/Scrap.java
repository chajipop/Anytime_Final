package com.naver.anytime.domain;

public class Scrap {
	
	private int SCRAP_ID;
	private int USER_ID;
	private int POST_ID;
	
	

	private int    BOARD_ID;   		// 게시판 고유번호
	private String SUBJECT;			// 글 제목
	private String CONTENT;			// 글 내용
	private String POST_DATE; 		// 글 작성 시간
	private int    LIKE_COUNT; 		// 공감 수
	private int    SCRAP_COUNT;		// 스크랩 수
	
	
	
	
	
	public int getBOARD_ID() {
		return BOARD_ID;
	}
	public void setBOARD_ID(int bOARD_ID) {
		BOARD_ID = bOARD_ID;
	}
	public String getSUBJECT() {
		return SUBJECT;
	}
	public void setSUBJECT(String sUBJECT) {
		SUBJECT = sUBJECT;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public String getPOST_DATE() {
		return POST_DATE;
	}
	public void setPOST_DATE(String pOST_DATE) {
		POST_DATE = pOST_DATE;
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
	public int getSCRAP_ID() {
		return SCRAP_ID;
	}
	public void setSCRAP_ID(int sCRAP_ID) {
		SCRAP_ID = sCRAP_ID;
	}
	public int getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(int uSER_ID) {
		USER_ID = uSER_ID;
	}
	public int getPOST_ID() {
		return POST_ID;
	}
	public void setPOST_ID(int pOST_ID) {
		POST_ID = pOST_ID;
	}
	
	
}
