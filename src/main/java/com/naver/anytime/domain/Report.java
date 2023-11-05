package com.naver.anytime.domain;

public class Report {
	private int REPORT_ID;
	private String REPORTER;
	private int REPORTED_ORIGIN;
	private int REASON;
	private String NOTE_DATE;
	private int ADMIN_ID;
	private String ADMIN_LOGIN_ID;
	private String POST_R_ACTION;
	private String USER_R_ACTION;
	private String ACTION_DATE;

	// 신고 관리 페이지
	private int POST_ID;
	private String BOARD_NAME;
	private int CONTENT_ID;
	private String LOGIN_ID;
	private String NICKNAME;
	private String CONTENT;
	private String SUBJECT;
	private String WRITE_DATE;
	private int LIKE_COUNT;
	private int REPORT_COUNT;
	private int STATUS;
	private String REPORTER_LOGIN_ID;
	
	private int COUNT_BY_REASON;

	public int getPOST_ID() {
		return POST_ID;
	}

	public void setPOST_ID(int pOST_ID) {
		POST_ID = pOST_ID;
	}

	public String getBOARD_NAME() {
		return BOARD_NAME;
	}

	public void setBOARD_NAME(String bOARD_NAME) {
		BOARD_NAME = bOARD_NAME;
	}

	public int getCONTENT_ID() {
		return CONTENT_ID;
	}

	public void setCONTENT_ID(int cONTENT_ID) {
		CONTENT_ID = cONTENT_ID;
	}

	public String getLOGIN_ID() {
		return LOGIN_ID;
	}

	public void setLOGIN_ID(String lOGIN_ID) {
		LOGIN_ID = lOGIN_ID;
	}

	public String getCONTENT() {
		return CONTENT;
	}

	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}

	public String getSUBJECT() {
		return SUBJECT;
	}

	public void setSUBJECT(String sUBJECT) {
		SUBJECT = sUBJECT;
	}

	public String getWRITE_DATE() {
		return WRITE_DATE;
	}

	public void setWRITE_DATE(String wRITE_DATE) {
		WRITE_DATE = wRITE_DATE;
	}

	public int getLIKE_COUNT() {
		return LIKE_COUNT;
	}

	public void setLIKE_COUNT(int lIKE_COUNT) {
		LIKE_COUNT = lIKE_COUNT;
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

	public int getREPORT_ID() {
		return REPORT_ID;
	}

	public void setREPORT_ID(int rEPORT_ID) {
		REPORT_ID = rEPORT_ID;
	}

	public String getREPORTER() {
		return REPORTER;
	}

	public void setREPORTER(String rEPORTER) {
		REPORTER = rEPORTER;
	}

	public int getREPORTED_ORIGIN() {
		return REPORTED_ORIGIN;
	}

	public void setREPORTED_ORIGIN(int rEPORTED_ORIGIN) {
		REPORTED_ORIGIN = rEPORTED_ORIGIN;
	}

	public int getREASON() {
		return REASON;
	}

	public void setREASON(int rEASON) {
		REASON = rEASON;
	}

	public String getNOTE_DATE() {
		return NOTE_DATE;
	}

	public void setNOTE_DATE(String nOTE_DATE) {
		NOTE_DATE = nOTE_DATE;
	}

	public int getADMIN_ID() {
		return ADMIN_ID;
	}

	public void setADMIN_ID(int aDMIN_ID) {
		ADMIN_ID = aDMIN_ID;
	}

	public String getPOST_R_ACTION() {
		return POST_R_ACTION;
	}

	public void setPOST_R_ACTION(String pOST_R_ACTION) {
		POST_R_ACTION = pOST_R_ACTION;
	}

	public String getUSER_R_ACTION() {
		return USER_R_ACTION;
	}

	public void setUSER_R_ACTION(String uSER_R_ACTION) {
		USER_R_ACTION = uSER_R_ACTION;
	}

	public String getNICKNAME() {
		return NICKNAME;
	}

	public void setNICKNAME(String nICKNAME) {
		NICKNAME = nICKNAME;
	}

	public String getREPORTER_LOGIN_ID() {
		return REPORTER_LOGIN_ID;
	}

	public void setREPORTER_LOGIN_ID(String rEPORTER_LOGIN_ID) {
		REPORTER_LOGIN_ID = rEPORTER_LOGIN_ID;
	}

	public String getADMIN_LOGIN_ID() {
		return ADMIN_LOGIN_ID;
	}

	public void setADMIN_LOGIN_ID(String aDMIN_LOGIN_ID) {
		ADMIN_LOGIN_ID = aDMIN_LOGIN_ID;
	}

	public int getCOUNT_BY_REASON() {
		return COUNT_BY_REASON;
	}

	public void setCOUNT_BY_REASON(int cOUNT_BY_REASON) {
		COUNT_BY_REASON = cOUNT_BY_REASON;
	}

	public String getACTION_DATE() {
		return ACTION_DATE;
	}

	public void setACTION_DATE(String aCTION_DATE) {
		ACTION_DATE = aCTION_DATE;
	}

}
