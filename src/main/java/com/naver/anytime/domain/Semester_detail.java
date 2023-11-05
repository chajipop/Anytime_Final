package com.naver.anytime.domain;

public class Semester_detail {

	private int semester_detail_id;
	private int semester_id;
	private String subject="";
	private int credit = 0;
	private String grade = "A+";
	private boolean major = false;
	
	private String semester_name;
	
	
	public int getSemester_detail_id() {
		return semester_detail_id;
	}
	public void setSemester_detail_id(int semester_detail_id) {
		this.semester_detail_id = semester_detail_id;
	}
	
	public boolean isMajor() {
		return major;
	}
	public void setMajor(boolean major) {
		this.major = major;
	}
	public int getSemester_id() {
		return semester_id;
	}
	public void setSemester_id(int semester_id) {
		this.semester_id = semester_id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getSemester_name() {
		return semester_name;
	}
	public void setSemester_name(String semester_name) {
		this.semester_name = semester_name;
	}
	
	
}
