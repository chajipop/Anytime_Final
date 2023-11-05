package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Scrap;

public interface ScrapService {

	public int isScrap(int user_id, int post_id);

	public void insertMyScrap(int user_id, int post_id);

	public void deleteMyScrap(int user_id, int post_id);

	public int getMyScrapCount(int user_id);

}
