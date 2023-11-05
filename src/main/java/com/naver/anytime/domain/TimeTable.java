package com.naver.anytime.domain;

public class TimeTable {
	private int TIMETABLE_ID;
	private int USER_ID;
	private String NAME = "시간표 1";
	private String SEMESTER;
	private String TIMETABLE_DATE;
	private int STATUS;

	private int CNT;

	public int getTIMETABLE_ID() {
		return TIMETABLE_ID;
	}

	public void setTIMETABLE_ID(int tIMETABLE_ID) {
		TIMETABLE_ID = tIMETABLE_ID;
	}

	public int getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(int uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getSEMESTER() {
		return SEMESTER;
	}

	public void setSEMESTER(String sEMESTER) {
		SEMESTER = sEMESTER;
	}

	public String getTIMETABLE_DATE() {
		return TIMETABLE_DATE;
	}

	public void setTIMETABLE_DATE(String tIMETABLE_DATE) {
		TIMETABLE_DATE = tIMETABLE_DATE;
	}

	public int getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}

	public int getCNT() {
		return CNT;
	}

	public void setCNT(int cNT) {
		CNT = cNT;
	}

}
