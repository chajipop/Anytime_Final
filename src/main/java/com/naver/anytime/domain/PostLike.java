package com.naver.anytime.domain;

public class PostLike {
	private int POST_LIKE_ID; // 게시물 공감 고유번호
	private int POST_ID; //게시물 고유번호
	private int USER_ID; //유저 고유번호
	
	public int getPOST_LIKE_ID() {
		return POST_LIKE_ID;
	}
	public void setPOST_LIKE_ID(int pOST_LIKE_ID) {
		POST_LIKE_ID = pOST_LIKE_ID;
	}
	public int getPOST_ID() {
		return POST_ID;
	}
	public void setPOST_ID(int pOST_ID) {
		POST_ID = pOST_ID;
	}
	public int getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(int uSER_ID) {
		USER_ID = uSER_ID;
	}
	
	
	
	
	
}
