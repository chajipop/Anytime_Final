package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Photo;

public interface PostPhotoService {
    
	public void insertPhoto(Photo photo);
	
	public List<Photo> getPhotosByPostId(int post_id);
	
	public Photo getPhotoByPath(String path);
    
	public Photo getPhotoById(int photo_id);
	public void deletePhoto(int photo_id);

	public List<Photo> getPhotosPostlist(int board_id);
}
