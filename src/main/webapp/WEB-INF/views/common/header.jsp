<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/resources/image/common/nav.logo.png">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/common.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/common.partial.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/container.article.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/container.community.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/container.modal.css"
	rel="stylesheet">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/post/postlist.css">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.7.0.js"></script>
<script>
	$(function() {

		var currentPage = location.pathname;
		var newPath = currentPage.replace('/anytime/', '');
		console.log("currentPage" + currentPage);

		$("#menu li a").each(function() {
			var menuURL = $(this).attr("href");
			if (currentPage === menuURL) {
				$(this).parent().addClass("active");
			} else {
				$(this).parent().removeClass("active");
			}
		});

		$("li > a").on("click", function() {
			$(this).parent().addClass("active");
		})

		$(".my").on("click", function() {
			$("#menu > li").removeClass("active");
		})

	});
</script>

<nav>
	<div class="wrap">
		<div id="logo">
			<a href="${pageContext.request.contextPath}/${school.domain}"><img
				src="${pageContext.request.contextPath}/resources/image/common/nav.logo.png"></a>
			<p>
				<span class="name multiple">애니타임</span> <span class="subname">
					${school.name} </span>
			</p>
		</div>
		<sec:authorize access="isAuthenticated()">
			<sec:authentication property="principal" var="pinfo" />
			<div id="account">
				<a href="${pageContext.request.contextPath}/message" title="쪽지함"
					class="icon message">쪽지함</a> <a
					href="${pageContext.request.contextPath}/my" title="내 정보"
					class="icon my">내 정보</a> <input type="hidden" id="login_id"
					value="${pinfo.username}"> <input type="hidden"
					id="school_id" value="${school.id}">
			</div>
		</sec:authorize>
		<sec:authorize access="isAnonymous()">
			<div id="account">
				<a href="${pageContext.request.contextPath}/member/login"
					class="button">로그인</a> <a
					href="${pageContext.request.contextPath}/member/register"
					class="button red">회원가입</a> <input type="hidden" id="school_id"
					value="${school.id}">
			</div>
		</sec:authorize>
		<ul id="menu">
			<li class="board"><a
				href="${pageContext.request.contextPath}/${school.domain}">게시판</a></li>
			<li><a href="${pageContext.request.contextPath}/timetable">시간표</a></li>
			<li><a href="${pageContext.request.contextPath}/calculator">학점계산기</a></li>
			<li><a href="${pageContext.request.contextPath}/calendar">캘린더
			</a></li>
		</ul>
	</div>
</nav>
