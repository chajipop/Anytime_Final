<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<style>
a {
	text-decoration: none;
}

ul {
	padding-left: 0px;
}
</style>
<script>
	$(function() {
		$("#logout").click(function(event) {
			event.preventDefault();
			$("form[name=logout]").submit();
		})
	})
</script>
<sec:authorize access="isAnonymous()">
   <script>
     location.href = "${pageContext.request.contextPath}/member/login";
   </script>
</sec:authorize>

<div class="left_admin">
	<div class="card">
		<sec:authorize access="isAuthenticated()">
			<form class="admin" action="${pageContext.request.contextPath}/member/logout" method="post" name="logout">
				<img
					src="${pageContext.request.contextPath}/resources/image/common/profile.png"
					class="picture">
				<p class="nickname">관리자</p>
				<p class="userid">${login_id}</p>
				<p class="email">
					<img
						src="${pageContext.request.contextPath}/resources/image/admin/email.png"
						class="picture"> ${email}
				</p>
				<ul class="buttons">
					<li class="info"><a
						href="${pageContext.request.contextPath}/my">내 정보</a></li>
					<li class="logout"><a
						href="#" id="logout">로그아웃</a></li>
				</ul>
				<hr>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}">
			</form>
		</sec:authorize>
	</div>
</div>