package com.naver.anytime.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Update;

import com.naver.anytime.domain.Post;

public interface PostService {

	public String getPostlist(String SchoolName);

	public int getListCount(int board_id);

	public List<Post> getPostList(int page, int limit, int board_id);

	public Post getDetail(int post_id);

	public int getHotListCount(int school_num);

	public List<Post> getHotList(int school_num, int page, int limit);

	public int getBestListCount(int school_num);

	public List<Post> getBestList(int school_num, int page, int limit);

	public boolean postInsert(Post postdata, String userid, String filename);

	public boolean postDataInsert(Connection con, Post postdata, String userid);

	public boolean photoDataInsert(Connection con, int post_num, String filename);

	public boolean postModify(Post modifypost, String filename);
	
	//글삭제 => STATUS로 접근불가
	public int updatePostStatus(int post_id);

	// 포스트 닉네임 확인용
	public List<Post> getUserNickname();
	
	//글쓰기 실험용
	public void insertPost(Post post);
	
	//글수정 실험용
	public void updatePost(Post post);
	
	public int getPostLikes(int post_id);
	
	@Update("UPDATE POST SET LIKE_COUNT = LIKE_COUNT + 1 WHERE POST_ID = #{POST_ID}")
    public void incrementLikes(int post_id);

    @Update("UPDATE POST SET LIKE_COUNT = LIKE_COUNT - 1 WHERE POST_ID = #{POST_ID}")
    public void decrementLikes(int post_id);
    
    public void updatePostFile(int post_id, String post_file);
    public void updatePostFile(Map<String, Object> params);
    
    public void deletePhoto(int photo_id);

	//검색용 리스트 총 수
	public int getSearchListCount(int board_id, int search_field, String search_word);
	
	//검색용 리스트 결과
	public List<Post> getSearchPostList(int page, int limit, int board_id, int search_field, String search_word);
	
	//전체 검색용 리스트 총 수
	public int getAllSearchListCount(int school_id, String search_word);
	
	//전체 검색용 리스트 결과
	public List<Post> getAllSearchPostList(int page, int limit, int school_id, String search_word);
	
	//게시물 신고수
	public int getPostReportCount(int post_id);
	
	//게시물 스크랩
	public List<Post> getMyScrapList(int page, int limit, int user_id);
	
	//내가 쓴 글 리스트 총 수
	public int getMyArticlesListCount(int user_id);
	
	//내가 쓴 글 리스트
	public List<Post> getMyArticlesList(int page, int limit, int user_id);
	
	//내가 댓글 단 글 리스트 총 수
	public int getMyCommentArticlesListCount(int user_id);
	
	//내가 댓글 단 글 리스트
	public List<Post> getMyCommentArticlesList(int page, int limit, int user_id);
	// ********************************= 윤희 =********************************
	public List<List<Post>> getPostListByBoard(int[] board_ids);

	public List<Post> getPostTotalList(int board_id, int page, int limit, int searchKey, String keyword);

	public int getPostTotalListCount(int board_id, int searchKey, String keyword);

	public int getPost(int content_id);
	// ********************************= 윤희 =********************************
	
	//핫 게시물 리스트 카운트
	public int getHotPostListCount(int school_id);
	
	//베스트 게시물 리스트 카운트
	public int getBestPostListCount(int school_id);

	//핫 게시물 리스트
	public List<Post> getHotPostList(int page, int limit, int school_id);

	//베스트 게시물 리스트
	public List<Post> getBestPostList(int page, int limit, int school_id);
	
	// 라이트 사이드 핫 게시물
	public List<Post> getHotPostSampleList(int school_id);
	
	// 라이트 사이드 베스트 게시물
	public List<Post> getBestPostSampleList(int school_id);
	
	// 스크랩 카운트 업
	public void updateScrapUpCount(int post_id);
	
	// 스크랩 카운트 다운
	public void updateScrapDownCount(int post_id);


}
