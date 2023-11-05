package com.naver.anytime.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Scrap;

@Mapper
public interface ScrapMapper {

	public int isScrap(int user_id, int post_id);

	public void insertMyScrap(int user_id, int post_id);

	public void deleteMyScrap(int user_id, int post_id);

	public int getMyScrapCount(int user_id);

}
