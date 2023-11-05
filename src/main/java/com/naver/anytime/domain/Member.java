package com.naver.anytime.domain;

public class Member {

	private int user_id;
	private int school_id;
	private String login_id;
	private String nickname;
	// member_joinForm.jsp에서 비밀번호 name속성 값을 확인해 주세요
	private String password;
	private String email;
	private int school_check = 0;
	private String address_num;
	private String address1;
	private String address2;
	private String phone_num;
	private int board_admin = 0;
	private int account_status = 1;
	private int admission_year;

	private String auth = "ROLE_MEMBER";

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getSchool_id() {
		return school_id;
	}

	public void setSchool_id(int school_id) {
		this.school_id = school_id;
	}

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSchool_check() {
		return school_check;
	}

	public void setSchool_check(int school_check) {
		this.school_check = school_check;
	}

	public String getAddress_num() {
		return address_num;
	}

	public void setAddress_num(String address_num) {
		this.address_num = address_num;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public int getBoard_admin() {
		return board_admin;
	}

	public void setBoard_admin(int board_admin) {
		this.board_admin = board_admin;
	}

	public int getAccount_status() {
		return account_status;
	}

	public void setAccount_status(int account_status) {
		this.account_status = account_status;
	}

	public int getAdmission_year() {
		return admission_year;
	}

	public void setAdmission_year(int admission_year) {
		this.admission_year = admission_year;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

}
