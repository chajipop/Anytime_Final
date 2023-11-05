package com.naver.anytime.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Photo;
import com.naver.anytime.domain.Post;
import com.naver.anytime.mybatis.mapper.PostMapper;
import com.naver.anytime.mybatis.mapper.PostPhotoMapper;
import com.naver.constants.AnytimeConstants;

@Service
public class PostServiceImpl implements PostService {
	private PostMapper postDao;
	private SqlSession sqlSession;
	private PostPhotoMapper PostPhotoDao;

	@Autowired
	public PostServiceImpl(PostMapper postDao, SqlSession sqlSession, PostPhotoMapper PostPhotoDao) {
		this.postDao = postDao;
		this.sqlSession = sqlSession;
		this.PostPhotoDao = PostPhotoDao;
	}

	@Override
	public String getPostlist(String SchoolName) {
		return postDao.getPostlist(SchoolName);
	}

	// 게시물 리스트 총 수
	@Override
	public int getListCount(int board_id) {
		return postDao.getListCount(board_id);
	}

	// 게시물 리스트 결과
	@Override
	public List<Post> getPostList(int page, int limit, int board_id) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("start", startrow);
		map.put("end", endrow);
		map.put("board_id", board_id);
		return postDao.getPostList(map);
	}

	// 포스트 닉네임 확인용
	public List<Post> getUserNickname() {
		Map<String, String> map = new HashMap<String, String>();
		return postDao.getUserNickname(map);
	}

	// 검색용 게시물 리스트 총 수
	public int getSearchListCount(int board_id, int search_field, String search_word) {
		return postDao.getSearchListCount(board_id, search_field, search_word);
	}

	// 검색용 게시물 리스트 결과
	@Override
	public List<Post> getSearchPostList(int page, int limit, int board_id, int search_field, String search_word) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("start", startrow);
		map.put("end", endrow);
		map.put("board_id", board_id);
		map.put("search_field", search_field);
		map.put("search_word", search_word);

		return postDao.getSearchPostList(map);
	}

	// 전체 검색용 리스트 총 수
	public int getAllSearchListCount(int school_id, String search_word) {
		return postDao.getAllSearchListCount(school_id, search_word);
	}

	// 전체 검색용 리스트 결과
	public List<Post> getAllSearchPostList(int page, int limit, int school_id, String search_word) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("start", startrow);
		map.put("end", endrow);
		map.put("school_id", school_id);
		map.put("search_word", search_word);

		return postDao.getAllSearchPostList(map);
	}

	@Override
	public Post getDetail(int post_id) {
		return postDao.getDetail(post_id);
	}

	@Override
	public int getHotListCount(int school_num) {
		return 0;
	}

	@Override
	public List<Post> getHotList(int school_num, int page, int limit) {
		return null;
	}

	@Override
	public int getBestListCount(int school_num) {
		return 0;
	}

	@Override
	public List<Post> getBestList(int school_num, int page, int limit) {
		return null;
	}

	@Override
	public boolean postInsert(Post postdata, String userid, String filename) {
		return false;
	}

	@Override
	public boolean postDataInsert(Connection con, Post postdata, String userid) {
		return false;
	}

	@Override
	public boolean photoDataInsert(Connection con, int post_num, String filename) {
		return false;
	}

	@Override
	public boolean postModify(Post modifypost, String filename) {
		return false;
	}

	// 글삭제 => STATUS로 접근불가
	@Override
	public int updatePostStatus(int post_id) {
		return postDao.updatePostStatus(post_id);
	}

	// 글쓰기 
	@Override
	public void insertPost(Post post) {
		if (post.getSUBJECT() == null || post.getSUBJECT().isEmpty()) {
			throw new IllegalArgumentException("Subject cannot be null or empty");
		}
		postDao.insertPost(post);

	}

	// 글수정 
	@Override
	public void updatePost(Post post) {
		postDao.updatePost(post);
	}

	@Override
	public int getPostLikes(int post_id) {
		return postDao.getPostLikes(post_id);
	}

	@Override
	public void updatePostFile(int post_id, String post_file) {
		Map<String, Object> params = new HashMap<>();
		params.put("post_id", post_id);
		params.put("post_file", post_file);
		this.postDao.updatePostFile(params);
	}

	@Override
	public void updatePostFile(Map<String, Object> params) {
		sqlSession.update("post.updatePostFile", params);

	}

	@Override
	public void incrementLikes(int post_id) {
	}

	@Override
	public void decrementLikes(int post_id) {
	}

	@Override
	public void deletePhoto(int photo_id) {
		Photo photo = PostPhotoDao.getPhotoById(photo_id);
		if (photo != null) {
			photo.deleteFile();
			PostPhotoDao.deletePhoto(photo_id);
		}
	}

	@Override
	public int getPostReportCount(int post_id) {
		return postDao.getPostReportCount(post_id);
	}

	@Override
	public List<Post> getMyScrapList(int page, int limit, int user_id) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("start", startrow);
		map.put("end", endrow);
		map.put("user_id", user_id);
		return postDao.getMyScrapList(map);
	}

	@Override
	public int getMyArticlesListCount(int user_id) {
		return postDao.getMyArticlesListCount(user_id);
	}

	@Override
	public List<Post> getMyArticlesList(int page, int limit, int user_id) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("start", startrow);
		map.put("end", endrow);
		map.put("user_id", user_id);
		return postDao.getMyArticlesList(map);
	}

	@Override
	public int getMyCommentArticlesListCount(int user_id) {
		return postDao.getMyCommentArticlesListCount(user_id);
	}

	@Override
	public List<Post> getMyCommentArticlesList(int page, int limit, int user_id) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("start", startrow);
		map.put("end", endrow);
		map.put("user_id", user_id);
		return postDao.getMyCommentArticlesList(map);
	}

	// ********************************= 윤희 =********************************
	@Override
	public List<List<Post>> getPostListByBoard(int[] board_ids) {

		List<List<Post>> result = new ArrayList<List<Post>>();

		for (int i = 0; i < board_ids.length; i++) {
			List<Post> list = postDao.getPostListByBoard(board_ids[i]);
			result.add(new ArrayList<>(list));
		}
		return result;
	}

	@Override
	public List<Post> getPostTotalList(int board_id, int page, int limit, int searchKey, String keyword) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;

		map.put("board_id", board_id);
		map.put("start", startrow);
		map.put("end", endrow);
		map.put("search_field", searchKey);
		map.put("search_word", keyword);

		return postDao.getPostTotalList(map);
	}

	@Override
	public int getPostTotalListCount(int board_id, int searchKey, String keyword) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("search_field", searchKey);
		map.put("search_word", keyword);
		map.put("board_id", board_id);

		return postDao.getPostTotalListCount(map);
	}

	@Override
	public int getPost(int content_id) {
		int result = -1;
		if (postDao.getPost(content_id) == 0) {
			result = AnytimeConstants.NON_EXISTENT;
		} else {
			result = AnytimeConstants.EXISTENT;
		}
		return result;
	}

	@Override
	public int getHotPostListCount(int school_id) {
		return postDao.getHotPostListCount(school_id);
	}

	@Override
	public int getBestPostListCount(int school_id) {
		return postDao.getBestPostListCount(school_id);
	}

	@Override
	public List<Post> getHotPostList(int page, int limit, int school_id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("school_id", school_id);
		map.put("start", startrow);
		map.put("end", endrow);
		return postDao.getHotPostList(map);
	}

	@Override
	public List<Post> getBestPostList(int page, int limit, int school_id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("school_id", school_id);
		map.put("start", startrow);
		map.put("end", endrow);
		return postDao.getBestPostList(map);
	}

	@Override
	public List<Post> getHotPostSampleList(int school_id) {
		return postDao.getHotPostSampleList(school_id);
	}

	@Override
	public List<Post> getBestPostSampleList(int school_id) {
		return postDao.getBestPostSampleList(school_id);
	}

	@Override
	public void updateScrapUpCount(int post_id) {
		postDao.updateScrapUpCount(post_id);
	}

	@Override
	public void updateScrapDownCount(int post_id) {
		postDao.updateScrapDownCount(post_id);
	}

}
