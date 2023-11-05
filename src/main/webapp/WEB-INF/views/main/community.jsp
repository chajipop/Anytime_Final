<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/resources/image/common/nav.logo.png">
<title>애니타임</title>
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/resources/image/common/nav.logo.png">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/common.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/common.partial.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/container.community.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/modal.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/footer2.css"
	rel="stylesheet">

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.7.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/common/modal.js"></script>
</head>

<script>
	if ("${result}" == 'SchoolCk_fail') {
		alert("학교 승인을 받아야 볼 수 있습니다.");
	} else if ("${result}" == 'postStatus_fail') {
		alert("삭제된 게시물입니다.");
	}
	$(function() {
		$("#logout").click(function(event) {
			event.preventDefault();
			$("form[name=logout]").submit();
		})
	})
</script>
<style>
#container.community {
	height: 825px !important;
}
</style>
<body style="">
	<jsp:include page="../common/header.jsp" />
	<jsp:include page="../common/submenu.jsp" />

	<div id="container" class="community">
		<aside class="none">
			<form class="search">
				<input type="search" name="keyword" class="text"
					placeholder="전체 게시판의 글을 검색하세요!">
			</form>
			<div class="title">
				<a class="hamburger"></a>
				<h1>애니타임</h1>
				<ol class="buttons">
					<li><a id="searchArticle">글 검색</a></li>
				</ol>
			</div>
		</aside>
		<div class="banners">
			<a
				href="https://ad.everytime.kr/adClick?adToken=yj25o47dUEdaGWIfaYHQSntu4pJm3%2F6s2%2Fgsu4OwnMpOuuGckaITHzWM%2BgrA0BjvozqCg2G3ptHhUCRi7xFUVSKVoZ3M7a8nh4AL3ORyj3ImaYocFqZKmO0jlvPKz37AiQmRvXix56rFasRWheUHQHyF%2FFL1pc0gK%2BIPAM%2FC9vGRUrINUPoxiQjSJHf8%2BHdeIa7%2BS9ujYtBPfz0%2FxasvvNjpQbqxTR%2FF5iQIm%2F5hkkEdw6C7KzIP%2BBynXAgU7nAs"><img
				src="https://cf-eba.everytime.kr/20230731_GalaxyTabS9Ultra_256GB_home.png"></a>
		</div>
		<div class="leftside">
			<div class="card pconly">
				<sec:authorize access="isAnonymous()">
					<form class="login">
						<h3>
							커뮤니티 이용을 위해<br> <strong>로그인</strong>이 필요합니다!
						</h3>
						<a href="${pageContext.request.contextPath}/member/login"
							class="button login">로그인</a> <a
							href="${pageContext.request.contextPath}/member/register"
							class="button register">애니타임 회원가입</a>
					</form>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<form class="logged"
						action="${pageContext.request.contextPath}/member/logout"
						method="post" name="logout">
						<img
							src="${pageContext.request.contextPath}/resources/image/common/profile.png"
							class="picture">
						<p class="nickname">${member.nickname}</p>
						<p class="userid">${member.login_id}</p>
						<ul class="buttons">
							<li><a href="${pageContext.request.contextPath}/my">내 정보</a></li>
							<li><a class="nav-link" href="#" id="logout">로그아웃</a></li>
						</ul>
						<hr>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}">
					</form>
				</sec:authorize>
			</div>
			<sec:authorize access="isAuthenticated()">
				<div class="card">
					<div class="menus">
						<a href="${pageContext.request.contextPath}/myarticle"
							class="myarticle">내가 쓴 글</a> <a
							href="${pageContext.request.contextPath}/mycommentarticle"
							class="mycommentarticle">댓글 단 글</a> <a
							href="${pageContext.request.contextPath}/myscrap" class="myscrap">내
							스크랩</a>
						<c:if
							test="${member.board_admin eq 1 && member.school_check eq 1}">
							<a href="${pageContext.request.contextPath}/boardlist"
								class="myboard">게시판 관리</a>
						</c:if>
						<c:if
							test="${member.board_admin eq 0 && member.school_check eq 1}">
							<p>
								새로운 게시판을<br>생성할수 있어요! <a class="button createboard"
									data-toggle="modal">게시판 생성하기</a>
							</p>
						</c:if>
						<c:if
							test="${member.board_admin eq 0 && member.school_check eq 0}">
							<p>
								학교 인증 후<br>이용할 수 있어요! <a class="button"
									href="${pageContext.request.contextPath}/my">학교 인증하기</a>
							</p>
						</c:if>
						<hr>
					</div>
				</div>
			</sec:authorize>
		</div>
		<jsp:include page="../common/rightside.jsp" />

		<c:forEach var="outerList" items="${list}">
			<div class="main">
				<div class="card">
					<div class="board">
						<c:forEach var="board" items="${outerList}" begin="0" end="0">
							<h3>
								<a href="post/list?board_id=${board.BOARD_ID}">
									${board.BOARDNAME}</a>
							</h3>
							<c:choose>
								<c:when test="${board.BOARDNAME eq '익명 게시판' }">
									<sec:authorize access="isAnonymous()">
										<div class="needauth">
											<p>
												로그인을 한 학생들만<br>이용할 수 있어요!
											</p>
											<a class="button"
												href="${pageContext.request.contextPath}/member/login">로그인</a>
										</div>
									</sec:authorize>
									<sec:authorize access="isAuthenticated()">
										<c:choose>
											<c:when test="${member.school_check eq 0}">
												<div class="needauth">
													<p>
														학교 인증을 거친 학생들만<br>이용할 수 있어요!
													</p>
													<a class="button"
														href="${pageContext.request.contextPath}/my">학교 인증하기</a>
												</div>
											</c:when>
											<c:otherwise>
												<c:forEach var="post" items="${outerList}">
													<a class="list" href="post/detail?post_id=${post.POST_ID}">
														<time>${post.POST_DATE}</time>
														<p>${post.SUBJECT}</p>
														<hr>
													</a>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</sec:authorize>
								</c:when>
								<c:otherwise>
									<c:forEach var="post" items="${outerList}">
										<a class="list" href="post/detail?post_id=${post.POST_ID}">
											<time>${post.POST_DATE}</time>
											<p>${post.SUBJECT}</p>
											<hr>
										</a>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:forEach>

		<form id="createBoard" class="modal"
			style="margin-left: -200px; margin-top: -220.5px; display: none;">
			<a title="닫기" class="close"></a> <span class="new">새 게시판 만들기</span>
			<p>
				<label>단체/학과 게시판 생성에는 관리자 승인이 필요합니다.</label> <label id="boarddetail">유형</label>
			<div id="option">
				<label> <input type="radio" name="boardtype" value="2">
					단체
				</label> <label> <input type="radio" name="boardtype" value="3">
					학과
				</label> <label> <input type="radio" name="boardtype" value="4">
					커스텀
				</label>
			</div>
			</p>
			<p></p>
			<label id="boarddetail">게시판 이름</label> <input type="text"
				class="info" name="name" placeholder="게시판 이름" class="text boardinfo">
			<label id="boarddetail">게시판 소개</label> <input type="text"
				class="info" name="content" placeholder="게시판 소개"
				class="text boardinfo">
			<div class="purpose" style="display: none;">
				<label id="boarddetail" class="purpose">개설 목적</label>
				<p id="explain" class="purpose">승인 심사시 필요</p>
			</div>
			<input type="text" name="purpose" placeholder="개설 목적"
				class="text info boardinfo purpose"
				style="display: none; margin-right: 0px;"> <label
				class="custom-checkbox-label"> <input type="checkbox"
				class="custom-checkbox" name="anony"> <span
				class="custom-checkbox-image"></span>
			</label> <input type="submit" value="완료" class="button">
		</form>
	</div>

	<div id="bottom">
		<section class="footer white">
			<ul class="links">
				<li>이용약관</li>
				<li class="privacy">개인정보처리방침</li>
				<li>청소년보호정책</li>
				<li>커뮤니티이용규칙</li>
				<li><a href="${pageContext.request.contextPath}/notice"
					style="font-weight: bold;">공지사항</a></li>
				<li>문의하기</li>
				<li class="copyright" style="font-weight: bold;">© 애니타임</li>
			</ul>
		</section>
	</div>

</body>
</html>