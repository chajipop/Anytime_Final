package com.naver.anytime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Photo;
import com.naver.anytime.mybatis.mapper.PostPhotoMapper;

@Service
public class PostPhotoServiceImpl implements PostPhotoService {
    private PostPhotoMapper postPhotoDao;
    private PostService postService;
    
    @Autowired
    public PostPhotoServiceImpl(PostPhotoMapper postPhotoDao, PostService postService) {
    	this.postPhotoDao = postPhotoDao;
    	this.postService = postService;
    }
    
    @Override	
    public void insertPhoto(Photo photo) { 
    	postPhotoDao.insertPhoto(photo); 
      }

	@Override
	public List<Photo> getPhotosByPostId(int post_id) {
		return postPhotoDao.getPhotosByPostId(post_id);
	}

	@Override
	public Photo getPhotoByPath(String path) {
		return postPhotoDao.getPhotoByPath(path);
	}

	@Override
	public Photo getPhotoById(int photo_id) {
		return postPhotoDao.getPhotoById(photo_id);
	}

	@Override
    public void deletePhoto(int photo_id) {
		Photo photo = this.getPhotoById(photo_id);
        if(photo != null) {
        	postService.deletePhoto(photo.getPHOTO_ID());
        }
    }

	@Override
	public List<Photo> getPhotosPostlist(int board_id) {
		return postPhotoDao.getPhotosPostlist(board_id);
	}


    
    
}
