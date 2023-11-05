<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>게시판 관리 - 애니타임</title>
<jsp:include page="../common/header.jsp" />
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/container.modal.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/modal.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/member/info/total.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/member/info/boardlist.css">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.7.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/common/modal.js"></script>
<script>
	if ("${result}" == "insertBoardSuccess") {
		alert("게시판이 생성되었습니다. 단체/학과 게시판은, 승인 심사에 통과되면 바로 이용가능합니다.");
	} else if ("${result}" == "Is_boardAdmin_deleteFail") {
		alert("회원 탈퇴를 위해서는 게시판 양도가 진행되어야 합니다. \n게시판 양도 완료 후, 재진행 부탁드립니다.");

	}
</script>
<style>
body {
	background-color: #EEEEF6 !important;
}

nav {
	border-bottom: 0 !important;
}

#boardSection {
	margin-top: -76px;
}
</style>
</head>
<body>
	<div class="boardcontrol">
		<div class="navbar">
			<a class="hamburger">메뉴 열기</a>
			<h1>게시판 관리</h1>
		</div>
		<div class="container">
			<c:if test="${school_check == 1 }">
				<section id="boardSection">
					<h1>관리 중인 게시판</h1>
					<c:choose>
						<c:when test="${boardAdmin == 1 }">
							<c:forEach var="b" items="${boardlist}">
								<a href="post/list?board_id=${b.BOARD_ID}">${b.NAME } <c:choose>
										<c:when
											test="${b.STATUS == 0 || b.STATUS == 3 || b.STATUS == 4 }">
											<p>승인 심사 진행 중</p>
										</c:when>
										<c:when test="${b.STATUS == 2 }">
											<p class="reject">승인 거부</p>
										</c:when>
									</c:choose>
								</a>
								<hr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<p class="noboard">관리 중인 게시판이 없어요</p>
						</c:otherwise>
					</c:choose>
					<a class="button createboard" href="#createboard"
						data-toggle="modal">게시판 생성하기</a>
				</section>
			</c:if>
			<c:if test="${school_check == 0 }">
				<section id="boardSection" class="empty">
					<p>학교 인증을 거친 학생들만 이용할 수 있어요!</p>
				</section>
			</c:if>
		</div>
	</div>

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
		<label id="boarddetail">게시판 이름</label> <input type="text" class="info"
			name="name" placeholder="게시판 이름" class="text boardinfo"> <label
			id="boarddetail">게시판 소개</label> <input type="text" class="info"
			name="content" placeholder="게시판 소개" class="text boardinfo">
		<div class="purpose" style="display: none;">
			<label id="boarddetail" class="purpose">개설 목적</label>
			<p id="explain" class="purpose">승인 심사시 필요</p>
		</div>
		<input type="text" name="purpose" placeholder="개설 목적"
			class="text info boardinfo purpose" style="display: none;"> <label
			class="custom-checkbox-label"> <input type="checkbox"
			class="custom-checkbox" name="anony"> <span
			class="custom-checkbox-image"></span>
		</label> <input type="submit" value="완료" class="button">
	</form>
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}">
</body>
</html>