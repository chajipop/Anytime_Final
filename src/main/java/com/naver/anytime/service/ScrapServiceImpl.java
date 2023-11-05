package com.naver.anytime.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Scrap;
import com.naver.anytime.mybatis.mapper.ScrapMapper;

@Service
public class ScrapServiceImpl implements ScrapService {
	
	private ScrapMapper dao;

	@Autowired
	public ScrapServiceImpl(ScrapMapper dao) {
		this.dao = dao;
	}

	@Override
	public int isScrap(int user_id, int post_id) {
		return dao.isScrap(user_id, post_id);
	}

	@Override
	public void insertMyScrap(int user_id, int post_id) {
		dao.insertMyScrap(user_id, post_id);
		
	}

	@Override
	public void deleteMyScrap(int user_id, int post_id) {
		dao.deleteMyScrap(user_id, post_id);
		
	}

	@Override
	public int getMyScrapCount(int user_id) {
		return dao.getMyScrapCount(user_id);
	}

	
}
