<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>회원 탈퇴 - 애니타임</title>
<c:if test="${auth eq 'ROLE_MEMBER'}">
	<jsp:include page="../common/header.jsp" />
</c:if>
<c:if test="${auth eq 'ROLE_ADMIN'}">
	<jsp:include page="../common/header_admin.jsp" />
</c:if>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/info/total.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/member/info/changePwd.css">

<script>
	if ("${result}" == "deleteFail") {
		alert("회원 탈퇴를 실패했습니다. 다시 시도해주세요");
	} else if ("${result}" == "passwordFail") {
		alert("현재 비밀번호와 일치하지 않습니다.");
	}

	$(function() {
		$("#withdrawButton").on('click', function(event) {
			if ($("input[name='password']").val() == "") {
				alert("현재 비밀번호를 입력하세요");
				$("input[name='oldpassword']").focus();
				return false;
			}
		})

	})
</script>
<style>
body {
	background-color: #EEEEF6 !important;
}

nav {
	border-bottom: 0 !important;
}
</style>
</head>
<body>
	<div>
		<form action="deleteProcess" class="container" method="post"
			data-adagreement="1" data-redirecturl="/">
			<section>
				<h1>회원 탈퇴</h1>
				<div class="input">
					<div class="label">
						<label>계정 비밀번호</label>
					</div>
					<input type="password" name="password" maxlength="20"
						placeholder="계정 비밀번호">
				</div>
				<div class="rules">
					<p>
						※ 탈퇴 후 개인 정보, 시간표 등의 데이터가 삭제되며, 복구할 수 없습니다.<br> ※ 다시 가입하여도,
						게시판 등 이용 제한 기록은 초기화되지 않습니다.<br> ※ 작성한 게시물은 삭제되지 않으며, (알수없음)으로
						닉네임이 표시됩니다.<br>
					</p>
				</div>
				<input type="submit" value="회원 탈퇴" id="withdrawButton">
			</section>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}">
		</form>
	</div>
</body>
</html>