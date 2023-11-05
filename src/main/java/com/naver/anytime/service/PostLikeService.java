package com.naver.anytime.service;

import org.springframework.transaction.annotation.Transactional;

import com.naver.anytime.domain.PostLike;

public interface PostLikeService {
    
	@Transactional
    public void addNewlike(PostLike postLike);
	
    public void removeExistinglike(PostLike postLike);
    public int checkIfUserAlreadyLiked(PostLike postLike);
    
    
}
