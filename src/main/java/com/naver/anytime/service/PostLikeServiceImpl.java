package com.naver.anytime.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.PostLike;
import com.naver.anytime.mybatis.mapper.PostLikeMapper;
import com.naver.anytime.mybatis.mapper.PostMapper;

@Service
public class PostLikeServiceImpl implements PostLikeService{
    private PostLikeMapper postLikeDao;
    private PostMapper postDao;
    
    @Autowired
    public PostLikeServiceImpl(PostLikeMapper postLikeDao, PostMapper postDao) {
    	this.postLikeDao = postLikeDao;
    	this.postDao = postDao;
    }
    
    @Override 
    public void addNewlike(PostLike postLikе) { 
    	int uniquePostLikeId = UUID.randomUUID().hashCode();
    	postLikе.setPOST_LIKE_ID(uniquePostLikeId);
    	// 공감을 추가하고 POST 테이블의 LIKE_COUNT 컬럼 값을 증가시킴
        try {
        	postLikeDao.addNewlike(postLikе);
            postDao.incrementLikes(postLikе.getPOST_ID());
        } catch (Exception e) {
            throw e;  
        }
     } 

    @Override 
    public void removeExistinglike(PostLike postLikе) { 
        // 공감을 제거하고 POST 테이블의 LIKE_COUNT 컬럼 값을 감소시킴
        try {
        	postLikeDao.removeExistinglike(postLikе);
        	postDao.decrementLikes(postLikе.getPOST_ID());
        } catch (Exception e) {
            throw e;  
        }
    }

    @Override  
    public int checkIfUserAlreadyLiked(PostLike postLikе) {  
        return postLikeDao.checkIfUserAlreadyLiked(postLikе);  
   }
	
	
	
}
