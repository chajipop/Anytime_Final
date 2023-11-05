package com.naver.anytime.domain;

public class DailyData {
	private String DAY;
	private int SCHOOLS;
	private int MEMBERS;
	private int BOARDS;
	private int POSTS;
	private int NEW_MEMBERS;
	private int WITHDRAWN_MEMBERS;

	private int SCHOOL_RATIO;
	private int POST_RATIO;
	private int MEMBER_RATIO;

	private int PRE;
	private String DAY_STRING;

	private int TO_DO_BOARD;
	private int DONE_BOARD;
	private int TO_DO_REPORT;
	private int DONE_REPORT;
	private int BOARD_PERCENT;
	private int REPORT_PERCENT;

	public int getBOARD_PERCENT() {
		return BOARD_PERCENT;
	}

	public void setBOARD_PERCENT(int bOARD_PERCENT) {
		BOARD_PERCENT = bOARD_PERCENT;
	}

	public int getREPORT_PERCENT() {
		return REPORT_PERCENT;
	}

	public void setREPORT_PERCENT(int rEPORT_PERCENT) {
		REPORT_PERCENT = rEPORT_PERCENT;
	}

	public int getTO_DO_BOARD() {
		return TO_DO_BOARD;
	}

	public void setTO_DO_BOARD(int tO_DO_BOARD) {
		TO_DO_BOARD = tO_DO_BOARD;
	}

	public int getDONE_BOARD() {
		return DONE_BOARD;
	}

	public void setDONE_BOARD(int dONE_BOARD) {
		DONE_BOARD = dONE_BOARD;
	}

	public int getTO_DO_REPORT() {
		return TO_DO_REPORT;
	}

	public void setTO_DO_REPORT(int tO_DO_REPORT) {
		TO_DO_REPORT = tO_DO_REPORT;
	}

	public int getDONE_REPORT() {
		return DONE_REPORT;
	}

	public void setDONE_REPORT(int dONE_REPORT) {
		DONE_REPORT = dONE_REPORT;
	}

	public String getDAY_STRING() {
		return DAY_STRING;
	}

	public void setDAY_STRING(String dAY_STRING) {
		DAY_STRING = dAY_STRING;
	}

	public int getSCHOOL_RATIO() {
		return SCHOOL_RATIO;
	}

	public void setSCHOOL_RATIO(int sCHOOL_RATIO) {
		SCHOOL_RATIO = sCHOOL_RATIO;
	}

	public int getPOST_RATIO() {
		return POST_RATIO;
	}

	public void setPOST_RATIO(int pOST_RATIO) {
		POST_RATIO = pOST_RATIO;
	}

	public int getMEMBER_RATIO() {
		return MEMBER_RATIO;
	}

	public void setMEMBER_RATIO(int mEMBER_RATIO) {
		MEMBER_RATIO = mEMBER_RATIO;
	}

	public String getDAY() {
		return DAY;
	}

	public void setDAY(String dAY) {
		DAY = dAY;
	}

	public int getSCHOOLS() {
		return SCHOOLS;
	}

	public void setSCHOOLS(int sCHOOLS) {
		SCHOOLS = sCHOOLS;
	}

	public int getMEMBERS() {
		return MEMBERS;
	}

	public void setMEMBERS(int mEMBERS) {
		MEMBERS = mEMBERS;
	}

	public int getBOARDS() {
		return BOARDS;
	}

	public void setBOARDS(int bOARDS) {
		BOARDS = bOARDS;
	}

	public int getPOSTS() {
		return POSTS;
	}

	public void setPOSTS(int pOSTS) {
		POSTS = pOSTS;
	}

	public int getNEW_MEMBERS() {
		return NEW_MEMBERS;
	}

	public void setNEW_MEMBERS(int nEW_MEMBERS) {
		NEW_MEMBERS = nEW_MEMBERS;
	}

	public int getWITHDRAWN_MEMBERS() {
		return WITHDRAWN_MEMBERS;
	}

	public void setWITHDRAWN_MEMBERS(int wITHDRAWN_MEMBERS) {
		WITHDRAWN_MEMBERS = wITHDRAWN_MEMBERS;
	}

	public int getPRE() {
		return PRE;
	}

	public void setPRE(int pRE) {
		PRE = pRE;
	}

}