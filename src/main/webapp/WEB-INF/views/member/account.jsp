<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>내 정보 - 애니타임</title>
<c:if test="${auth eq 'ROLE_MEMBER'}">
	<jsp:include page="../common/header.jsp" />
</c:if>
<c:if test="${auth eq 'ROLE_ADMIN'}">
	<jsp:include page="../common/header_admin.jsp" />
</c:if>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/info/total.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/info/body.css">

<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">
<sec:authorize access="isAnonymous()">
	<script>
		location.href = "${pageContext.request.contextPath}/member/login";
	</script>
</sec:authorize>
<script>
	$(function() {
		$("#logout").click(function(event) {
			event.preventDefault();
			$("form[name=logout]").submit();
		})
	})
</script>
<style>
nav {
	border-bottom: 0 !important;
}
</style>
</head>
<body>
	<div>
		<div class="container">
			<section>
				<sec:authorize access="isAuthenticated()">
					<form action="${pageContext.request.contextPath}/member/logout"
						method="post" name="logout">
						<div class="title">
							<h1>내 정보</h1>
							<a href="" class="logout" id="logout">로그아웃</a>
						</div>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}">
					</form>
					<div class="profile">
						<img
							src="${pageContext.request.contextPath}/resources/image/common/profile.png">
						<h3>${member.login_id}</h3>
						<p>
							<span>${member.nickname}</span>
						</p>
						<p>
							<span>${school.name}</span> 
							<c:if test="${member.auth eq 'ROLE_MEMBER' }">
							<span>${member.admission_year}</span>
							<span>학번</span>
							</c:if>
						</p>
					</div>
				</sec:authorize>
			</section>
			<section>
				<h2>계정</h2>
				<c:if test="${member.auth eq 'ROLE_MEMBER' }">
					<a href="${pageContext.request.contextPath}/certificate"
						class="item">학교 인증</a>
				</c:if>
				<a href="${pageContext.request.contextPath}/password" class="item">비밀번호
					변경</a> <a href="${pageContext.request.contextPath}/update" class="item">내정보
					변경</a>
			</section>
			<c:if test="${member.auth eq 'ROLE_MEMBER' }">
				<section>
					<h2>커뮤니티</h2>
					<a href="${pageContext.request.contextPath}/boardlist" class="item">게시판
						관리</a>
				</section>
			</c:if>
			<section>
				<h2>기타</h2>
				<a href="${pageContext.request.contextPath}/delete" class="item">회원
					탈퇴</a>
			</section>
		</div>
	</div>
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}">
</body>
</html>