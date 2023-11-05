package com.naver.anytime.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.naver.anytime.domain.PostLike;

@Mapper
public interface PostLikeMapper {
    
	public void addNewlike(PostLike postLike);
	
    public void removeExistinglike(PostLike postLike);
    public int checkIfUserAlreadyLiked(PostLike postLike);
    

}
