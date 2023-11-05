package com.naver.anytime.controller;

import java.io.File;
import java.security.Principal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naver.anytime.domain.Board;
import com.naver.anytime.domain.Member;
import com.naver.anytime.domain.Photo;
import com.naver.anytime.domain.Post;
import com.naver.anytime.domain.PostLike;
import com.naver.anytime.domain.UserCustom;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.PostLikeService;
import com.naver.anytime.service.PostPhotoService;
import com.naver.anytime.service.PostService;
import com.naver.anytime.service.SchoolService;

@Controller
@RequestMapping(value = "/post")
public class PostController {

	private static final Logger logger = LoggerFactory.getLogger(PostController.class);

	private PostService postService;

	private BoardService boardService;

	private SchoolService schoolService;

	private MemberService memberService;

	private PostLikeService postLikeService;

	private PostPhotoService postPhotoService;

	@Value("${my.savefolder}")
	private String saveFolder;

	@Autowired
	public PostController(PostService postService, BoardService boardService, SchoolService schoolService,
			MemberService memberService, PostLikeService postLikeService, PostPhotoService postPhotoService) {
		this.postService = postService;
		this.boardService = boardService;
		this.schoolService = schoolService;
		this.memberService = memberService;
		this.postLikeService = postLikeService;
		this.postPhotoService = postPhotoService;
	}

	// 게시판 상세페이지
	@GetMapping("/detail") // http://localhost:9700/anytime/post/detail?post_id=1 주소예시입니다.
	public ModelAndView postDetail(@RequestParam(value = "post_id", required = false) Integer post_id, ModelAndView mv,
			HttpServletRequest request, @AuthenticationPrincipal UserCustom user, RedirectAttributes redirectAttrs) {

		// 현재 로그인된 유저아이디를 가져오기 위한 코드입니다.
		String id = user.getUsername();
		Member member = memberService.getLoginMember(id);
		int currentUserId = member.getUser_id();

		Post post = postService.getDetail(post_id); // post테이블 정보 가져오기위한 메소드입니다.

		Map<String, Object> school = new HashMap<String, Object>();
		String school_name = schoolService.getSchoolNameById(user.getSchool_id());

		school.put("id", user.getSchool_id());
		school.put("name", school_name);
		school.put("domain", schoolService.getSchoolDomain(school_name));

		// post = null; //error 페이지 이동 확인하고자 임의로 지정합니다.
		
		
		if (post == null ||( post.getSTATUS() == 0 && member.getAuth().equals("ROLE_MEMBER"))) {
			if (post != null && post.getSTATUS() == 0) {
				redirectAttrs.addFlashAttribute("result", "postStatus_fail");
				mv.setViewName("redirect:/" + schoolService.getSchoolDomain(school_name));
				return mv;
			}
			logger.info("상세보기 실패");
			mv.setViewName("error/error");
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "상세보기 실패입니다.");
		} else if (member.getSchool_check() == 0 && (post.getBOARD_TYPE() == 4 || post.getBOARD_ANONYMOUS() == 1)) {
			redirectAttrs.addFlashAttribute("result", "SchoolCk_fail");
			mv.setViewName("redirect:/" + schoolService.getSchoolDomain(school_name));
		}

		else {
			logger.info("★ 상세보기 성공 ★");

			Board board = boardService.getBoardDetail(post.getBOARD_ID()); // board테이블 정보 가져오기위한 메소드입니다.
			List<Photo> photos = postPhotoService.getPhotosByPostId(post_id);

			logger.info("현재로그인 유저아이디 => " + id);
			logger.info("유저학교(SCHOOL) 고유번호 => " + memberService.getSchoolId(id));

			if (board.getANONYMOUS() == 0) {
				member = memberService.findMemberByUserId(post.getUSER_ID());
				if (member != null) {
					String nickname = member.getNickname();
					mv.addObject("nickname", nickname);
				}
			}

			mv.setViewName("post/postDetail");
			mv.addObject("postdata", post);
			mv.addObject("boardtest", board);
			mv.addObject("currentUserId", currentUserId);
			mv.addObject("anonymous", board.getANONYMOUS());
			mv.addObject("photos", photos);
			mv.addObject("school", school);

			logger.info(photos.toString());

			System.out.println(post.toString());
		}

		return mv;
	}

	// 게시판 글쓰기
	@ResponseBody
	@PostMapping(value = "/write")
	public ResponseEntity<Map<String, Object>> writePost(
			@RequestParam(value = "LOGIN_ID", required = false) String USER_ID,
			@RequestPart(value = "file[]", required = false) MultipartFile[] files, Post post,
			HttpServletRequest request) {

		Map<String, Object> result = new HashMap<>();

		HttpSession session = request.getSession();
		int boardId = (Integer) session.getAttribute("board_id");
		String login_id = USER_ID;

		int user_id = memberService.getUserId(login_id);
		post.setBOARD_ID(boardId);
		post.setUSER_ID(user_id);
		StringBuilder sbFiles = new StringBuilder();

		try {
			postService.insertPost(post);
			// 이미지 파일 저장
			if (files != null && files.length > 0) {
				for (MultipartFile file : files) {
					if (!file.isEmpty()) {
						Photo photo = new Photo();
						photo.setPOST_ID(post.getPOST_ID());

						String originalFilename = file.getOriginalFilename();// 원래 파일명
						System.out.println("Processing file: " + originalFilename);

						sbFiles.append(originalFilename).append(",");

						// 파일 경로 설정 및 실제 파일을 디스크에 저장하는 로직.
						//String saveFolder = "c:/upload/";
						String fileDBName = fileDBName(originalFilename, saveFolder);
						file.transferTo(new File(saveFolder + fileDBName));
//                      photo.setPATH(saveFolder+fileDBName);
						photo.setPATH(fileDBName);

						postPhotoService.insertPhoto(photo);
					}
				}

				if (sbFiles.length() > 0) {
					sbFiles.setLength(sbFiles.length() - 1); // 마지막 콤마 제거
					post.setPOST_FILE(sbFiles.toString()); // 첨부파일 목록을 POST 객체에 설정

					postService.updatePostFile(post.getPOST_ID(), sbFiles.toString());
				}
			}

			result.put("statusCode", 1);

		} catch (Exception e) {
			result.put("statusCode", -1);
			result.put("errorMessage", e.getMessage());
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 업로드시 저장되는 파일명
	private String fileDBName(String fileName, String saveFolder) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);// 오늘 년도 구합니다.
		int month = c.get(Calendar.MONTH) + 1;// 오늘 월 구합니다.
		int date = c.get(Calendar.DATE);// 오늘 일 구합니다.

		String homedir = saveFolder + "/" + year + "-" + month + "-" + date;

		File path1 = new File(homedir);
		if (!(path1.exists())) {
			path1.mkdir();// 새로운 폴더를 생성
		}

		Random r = new Random();
		int random = r.nextInt(100000000);
		int index = fileName.lastIndexOf(".");
		String fileExtension = fileName.substring(index + 1);
		String refileName = "anytime" + year + month + date + random + "." + fileExtension;
		String fileDBName = "/" + year + "-" + month + "-" + date + "/" + refileName;

		return fileDBName;
	}

	// 게시판 글수정 데이터값 받기
	@ResponseBody
	@GetMapping("/updateGet")
	public Map<String, Object> updateGet(@RequestParam(value = "post_id", required = false) Integer post_id,
			HttpServletRequest request, Principal userPrincipal) {

		Post post = postService.getDetail(post_id); // post테이블 정보 가져오기위한 메소드입니다.
		List<Photo> photos = postPhotoService.getPhotosByPostId(post_id);
		List<String> photoPaths = photos.stream().map(Photo::getPATH).collect(Collectors.toList());

		if (post == null) {
			logger.info("수정 상세보기 실패");
			return Collections.singletonMap("error", "수정 상세보기 실패입니다.");
		} else {
			logger.info("★ 수정 상세보기 성공 ★");

			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("SUBJECT", post.getSUBJECT());
			responseMap.put("CONTENT", post.getCONTENT());
			responseMap.put("FILES", photoPaths);

			return responseMap;
		}
	}

	// 게시판 글수정 데이터값 보내기
	@ResponseBody
	@PostMapping("/updatePost")
	public Map<String, Object> updatePost(@RequestParam(value = "LOGIN_ID", required = false) String USER_ID,
			@RequestParam(value = "POST_ID") Integer post_id, @RequestParam(value = "SUBJECT") String subject,
			@RequestParam(value = "CONTENT") String content,
			// 새롭게 추가된 파일들
			@RequestPart(value = "file[]", required = false) MultipartFile[] newFiles,
			// 기존에 업로드된 파일들
			@RequestPart(value = "existingFile[]", required = false) List<MultipartFile> existingFiles,
			// 삭제될 파일들
			@RequestParam(value = "deleteFile[]", required = false) String[] deleteFiles, HttpServletRequest request,
			Principal userPrincipal) {

		// 현재 로그인된 유저아이디를 가져오기 위한 코드입니다.
		String id = userPrincipal.getName();
		Member member = memberService.getLoginMember(id);
		int currentUserId = member.getUser_id();

		Post postToUpdate = postService.getDetail(post_id);

		if (USER_ID != null) {
			int userId = memberService.getUserId(USER_ID);
			if (userId == currentUserId) {
				// 로그인 사용자와 작성자가 동일하면 수정 가능
				postToUpdate.setUSER_ID(userId);
			} else {
				return Collections.singletonMap("error", "작성자만 수정할 수 있습니다.");
			}
		} else {
			return Collections.singletonMap("error", "작성자만 수정할 수 있습니다.");
		}

		if (subject != null && !subject.isEmpty()) {
			postToUpdate.setSUBJECT(subject);
		}

		if (content != null && !content.isEmpty()) {
			postToUpdate.setCONTENT(content);
		}

		try {

			StringBuilder sbFiles;

			if (postToUpdate.getPOST_FILE() != null) {
				sbFiles = new StringBuilder(postToUpdate.getPOST_FILE());
			} else {
				sbFiles = new StringBuilder();
			}

			// 새롭게 추가된 파일 처리
			if (newFiles != null && newFiles.length > 0) {
				for (MultipartFile file : newFiles) {
					if (!file.isEmpty()) {
						Photo photo = new Photo();
						photo.setPOST_ID(postToUpdate.getPOST_ID());

						String originalFilename = file.getOriginalFilename();// 원래 파일명

						if (sbFiles.length() > 0 && sbFiles.charAt(sbFiles.length() - 1) != ',') {
							sbFiles.append(",");
						}

						sbFiles.append(originalFilename).append(",");

						// 파일 경로 설정 및 실제 파일을 디스크에 저장하는 로직.
						//String saveFolder = "c:/upload/";
						String fileDBName = fileDBName(originalFilename, saveFolder);
						file.transferTo(new File(saveFolder + fileDBName));

						photo.setPATH(fileDBName);

						postPhotoService.insertPhoto(photo);
					}
				}

				if (sbFiles.length() > 0) {
					sbFiles.setLength(sbFiles.length() - 1); // 마지막 콤마 제거
					postToUpdate.setPOST_FILE(sbFiles.toString()); // 첨부파일 목록을 POST 객체에 설정

					postService.updatePostFile(postToUpdate.getPOST_ID(), sbFiles.toString()); // POST 테이블의 POST_FILE 컬럼
																								// 업데이트
				}
			}

			// 삭제될 파일 처리
			String[] originalFiles = postToUpdate.getPOST_FILE() != null ? postToUpdate.getPOST_FILE().split(",")
					: new String[0];
			if (deleteFiles != null && deleteFiles.length > 0) {
				for (String filePath : deleteFiles) {
					Photo photo = postPhotoService.getPhotoByPath(filePath);
					if (photo != null) {
						File fileToDelete = new File(filePath); // 실제 파일 삭제
						if (fileToDelete.exists()) {
							boolean isDeleted = fileToDelete.delete();
							if (isDeleted) {
								System.out.println("File deleted successfully: " + filePath);
							} else {
								System.out.println("Failed to delete the file: " + filePath);
							}
						}

						postPhotoService.deletePhoto(photo.getPHOTO_ID());

						// POST 테이블에서 해당 파일 이름 제거
						originalFiles = Arrays.stream(originalFiles).filter(file -> !file.equals(filePath))
								.toArray(String[]::new);
						StringBuilder sbUpdatedPostFile = new StringBuilder();
						for (String file : originalFiles) {
							sbUpdatedPostFile.append(file).append(",");
						}

						if (sbUpdatedPostFile.length() > 0) {
							sbUpdatedPostFile.setLength(sbUpdatedPostFile.length() - 1); // 마지막 콤마 제거
						}

						postToUpdate.setPOST_FILE(sbUpdatedPostFile.toString());

						// POST 테이블의 POST_FILE 컬럼 업데이트 (삭제된 항목까지 반영)
						postService.updatePostFile(postToUpdate.getPOST_ID(), sbUpdatedPostFile.toString());

					}
				}
			}

			try {
				postService.updatePost(postToUpdate);
			} catch (Exception e) {
				Map<String, Object> result = new HashMap<>();
				result.put("statusCode", -1);
				result.put("errorMessage", "Failed to update the Post: " + e.getMessage());
				return result;
			}

			Map<String, Object> result = new HashMap<>();
			result.put("statusCode", 1);

			return result;

		} catch (Exception e) {
			Map<String, Object> result = new HashMap<>();
			result.put("statusCode", -1);
			result.put("errorMessage", e.getMessage());

			return result;
		}
	}

	// 게시판 글삭제
	@ResponseBody
	@GetMapping("/delete")
	public ResponseEntity<?> deletePost(@RequestParam("post_id") int post_id) {
		int result = postService.updatePostStatus(post_id);
		if (result > 0) {
			return new ResponseEntity<>("게시글이 성공적으로 삭제되었습니다.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("게시글 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 게시판 글 공감
	@PostMapping("/likePost")
	public ResponseEntity<Map<String, Object>> likePost(
			@RequestParam(value = "POST_ID", required = false) Integer post_id, Principal userPrincipal) {
		String id = userPrincipal.getName();
		Member member = memberService.getLoginMember(id);
		int currentUserId = member.getUser_id();

		Map<String, Object> resultMap = new HashMap<>();

		PostLike postLike = new PostLike();
		logger.info("Received POST_ID: " + post_id); // POST_ID 값 로그 출력
		logger.info("Initial PostLike: " + postLike); // 초기 PostLike 객체 상태 로그 출력
		postLike.setPOST_ID(post_id);
		postLike.setUSER_ID(currentUserId);

		int likeCount = postLikeService.checkIfUserAlreadyLiked(postLike);

		if (likeCount == 0) {
			try {
				postLikeService.addNewlike(postLike);

				resultMap.put("statusCode", 1);
				resultMap.put("like_count", postService.getPostLikes(post_id));

			} catch (Exception e) {
				resultMap.put("statusCode", -1);
				resultMap.put("errorMessage", e.getMessage());
			}

		} else {
			try {
				postLikeService.removeExistinglike(postLike);

				resultMap.put("statusCode", 2);
				resultMap.put("like_count", postService.getPostLikes(post_id));

			} catch (Exception e) {
				resultMap.put("statusCode", -1);
				resultMap.put("errorMessage", e.getMessage());
			}
		}

		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 게시물 리스트 출력
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView postlist(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "board_id", required = false) int board_id, HttpSession session, Principal principal,
			@AuthenticationPrincipal UserCustom user, ModelAndView mv) {

		Map<String, Object> school = new HashMap<String, Object>();
		String school_name = schoolService.getSchoolNameById(user.getSchool_id());

		school.put("id", user.getSchool_id());
		school.put("name", school_name);
		school.put("domain", schoolService.getSchoolDomain(school_name));

		int check = 0;

		int user_id = memberService.getUserId(principal.getName());

		// 보드 승인 체크
		int statuscheck = boardService.isBoardStatusCheck(board_id);

		// 익명 게시판 체크
		int anonymous = boardService.getBoardAnonymous(board_id);

		// 게시판 타입 체크
		int customcheck = boardService.getBoardTypeCheck(board_id);

		// 학교 인증 체크
		int schoolcheck = memberService.getUserSchoolCheck(user_id);

		if (anonymous == 1 || customcheck == 4) {
			if (schoolcheck == 1) {
				check = 1;
			}
		} else {
			check = 2;
		}

		if (statuscheck == 1 && check > 0) {

			session.setAttribute("board_id", board_id);

			int limit = 10;

			// 총 리스트 수
			int listcount = postService.getListCount(board_id);

			// 게시판 이름
			List<Board> board = boardService.getBoardName(board_id);

			// 총 페이지 수
			int maxpage = (listcount + limit - 1) / limit;

			// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
			int startpage = ((page - 1) / 10) * 10 + 1;

			// 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
			int endpage = startpage + 10 - 1;

			if (endpage > maxpage)
				endpage = maxpage;

			// 게시글 리스트
			List<Post> postlist = postService.getPostList(page, limit, board_id);

			// 유저 닉네임
			List<Post> username = postService.getUserNickname();

			// 포토
			List<Photo> photolist = postPhotoService.getPhotosPostlist(board_id);

			// 익명성 체크
			if (anonymous == 1) {
				for (Post post : postlist) {
					post.setNICKNAME("익명");
				}
			} else {
				for (Post post : postlist) {
					for (Post post2 : username) {
						if (post.getUSER_ID() == post2.getUSER_ID()) {
							if (memberService.getStatusCheck2(post2.getUSER_ID()) == 1) {
								post.setNICKNAME(post2.getNICKNAME());
							} else {
								post.setNICKNAME("(알 수 없음)");
							}
						}
					}
				}
			}

			mv.setViewName("post/postList");
			mv.addObject("school", school);
			mv.addObject("page", page);
			mv.addObject("maxpage", maxpage);
			mv.addObject("startpage", startpage);
			mv.addObject("endpage", endpage);
			mv.addObject("listcount", listcount);
			mv.addObject("postlist", postlist);
			mv.addObject("limit", limit);

			mv.addObject("un", username);
			mv.addObject("boardname", board);
			mv.addObject("allsearchcheck", 0);
			mv.addObject("searchcheck", 0);
			mv.addObject("emptycheck", 0);
			mv.addObject("board_id", board_id);

			mv.addObject("photolist", photolist);
			System.out.println("이거되나?" + photolist);
			// 글이 없을때 체크 (1= 일반, 2= 검색)
			if (postlist != null) {
				mv.addObject("emptycheck", 1);
			}

			System.out.println("board_id 테스트 = [" + board_id + "]");
			if (postlist != null) {
				System.out.println("postlist 값 존재");
			}

		} else if (statuscheck == 0 && check > 0) {
			mv.setViewName("post/postList");
			mv.addObject("statuscheck", 1);
			System.out.println("status 0 인 페이지 접근");
		} else {
			mv.setViewName("post/postList");
			mv.addObject("schoolcheck", 1);
			System.out.println("스쿨확인이 제한된 페이지 접근");
		}

		return mv;
	}

	// 검색기능
	@RequestMapping(value = "/search")
	public ModelAndView postsearch(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "search_field", defaultValue = "0") int search_field,
			@RequestParam(value = "search_word", defaultValue = "") String search_word, HttpSession session,
			@AuthenticationPrincipal UserCustom user, ModelAndView mv, Principal principal) {

		Map<String, Object> school = new HashMap<String, Object>();
		String school_name = schoolService.getSchoolNameById(user.getSchool_id());

		school.put("id", user.getSchool_id());
		school.put("name", school_name);
		school.put("domain", schoolService.getSchoolDomain(school_name));

//		   session.setAttribute("board_id", board_id);
		session.setAttribute("search_word", search_word);
//		   session.setAttribute("search_field", search_field);
		int board_id = (int) session.getAttribute("board_id");
//		   search_field = (int) session.getAttribute("search_field");
		search_word = (String) session.getAttribute("search_word");

		String login_id = principal.getName();

		int school_id = memberService.getSchoolId(login_id);

		int user_id = memberService.getUserId(principal.getName());

		// 학교 인증 체크
		int schoolcheck = memberService.getUserSchoolCheck(user_id);

		int limit = 10;
		int listcount = 0;
		List<Post> postlist = null;
		int searchcheck = 0;
		int allsearchcheck = 0;

		// 일반 리스트 출력 or 검색 리스트 출력 or 전체 검색 리스트 출력
		if (search_word == null || search_word.equals("")) {
			// 총 리스트 수
			listcount = postService.getListCount(board_id);
			// 게시글 리스트
			postlist = postService.getPostList(page, limit, board_id);
		} else if (search_word != null && search_field < 4) {
			searchcheck = 1;
			// 총 리스트 수
			listcount = postService.getSearchListCount(board_id, search_field, search_word);
			// 게시글 리스트
			postlist = postService.getSearchPostList(page, limit, board_id, search_field, search_word);
		} else if (search_field == 4) {
			allsearchcheck = 1;
			// 총 리스트 수
			listcount = postService.getAllSearchListCount(school_id, search_word);
			// 게시글 리스트
			postlist = postService.getAllSearchPostList(page, limit, school_id, search_word);
			System.out.println("전체검색 테스트");
		}

		// 게시판 이름
		List<Board> board = boardService.getBoardName(board_id);

		// 총 페이지 수
		int maxpage = (listcount + limit - 1) / limit;

		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
		int startpage = ((page - 1) / 10) * 10 + 1;

		// 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
		int endpage = startpage + 10 - 1;

		if (endpage > maxpage)
			endpage = maxpage;

		// 유저 닉네임
		List<Post> username = postService.getUserNickname();

		// 익명성
		int anonymous = boardService.getBoardAnonymous(board_id);

		// 익명성 체크
		if (anonymous == 1) {
			for (Post post : postlist) {
				post.setNICKNAME("익명");
			}
		} else {
			for (Post post : postlist) {
				for (Post post2 : username) {
					if (post.getUSER_ID() == post2.getUSER_ID()) {
						post.setNICKNAME(post2.getNICKNAME());
					}
				}
			}
		}

		if (schoolcheck != 1) {
			Iterator<Post> iterator = postlist.iterator();
			while (iterator.hasNext()) {
				Post post = iterator.next();
				int statuscheck = boardService.isBoardStatusCheck(post.getBOARD_ID());
				int customcheck = boardService.getBoardTypeCheck(post.getBOARD_ID());
				if (statuscheck == 1 && customcheck == 4) {
					iterator.remove();
				}
			}
		}

		mv.setViewName("post/postList");
		mv.addObject("school", school);
		mv.addObject("page", page);
		mv.addObject("maxpage", maxpage);
		mv.addObject("startpage", startpage);
		mv.addObject("endpage", endpage);
		mv.addObject("listcount", listcount);
		mv.addObject("postlist", postlist);
		mv.addObject("limit", limit);
		mv.addObject("boardname", board);

		mv.addObject("un", username);
		mv.addObject("allsearchcheck", allsearchcheck);
		mv.addObject("emptycheck", 0);

		mv.addObject("searchcheck", searchcheck);

		// 글이 없을때 체크 (1= 일반, 2= 검색)
		if (postlist != null) {
			mv.addObject("emptycheck", 2);
		}

		System.out.println(
				"!======= 보드넘테스트[" + board_id + "] / 서치체크[" + searchcheck + "] / 올서치체크 [" + allsearchcheck + "]");
		System.out.println("!======= 값테스트" + postlist);
		return mv;
	}
}