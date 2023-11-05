package com.naver.anytime.domain;

public class Credit {
   
	 private int credit_id;
	 private int user_id;
	 private int graduate_credit =150;
	 
	 
	 
	 
	public int getCredit_id() {
		return credit_id;
	}
	public void setCredit_id(int credit_id) {
		this.credit_id = credit_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getGraduate_credit() {
		return graduate_credit;
	}
	public void setGraduate_credit(int graduate_credit) {
		this.graduate_credit = graduate_credit;
	}
	
	
}
