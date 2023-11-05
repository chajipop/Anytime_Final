package com.naver.anytime.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserCustom extends User {
	private static final long serialVersionUID = 1L;

	private int school_id;
	private String email;
	private String nickname;
	private int board_admin;
	private String auth;
	private int school_check;

	public UserCustom(String username, String password, int school_id, String email, String nickname, int board_admin,
			String auth, int school_check, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.school_id = school_id;
		this.email = email;
		this.nickname = nickname;
		this.board_admin = board_admin;
		this.auth = auth;
		this.school_check = school_check;
	}

	public int getSchool_id() {
		return school_id;
	}

	public void setSchool_id(int school_id) {
		this.school_id = school_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getBoard_admin() {
		return board_admin;
	}

	public void setBoard_admin(int board_admin) {
		this.board_admin = board_admin;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public int getSchool_check() {
		return school_check;
	}

	public void setSchool_check(int school_check) {
		this.school_check = school_check;
	}

}
