<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/image/common/nav.logo.png">
<title>애니타임</title>
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/common.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/common.partial.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/container.article.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/container.community.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/container.modal.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/modal.css" rel="stylesheet">

<script src="https://code.jquery.com/jquery-latest.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/comment/comment.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/post/postGoList.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/post/update.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/post/postDelete.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/post/postLike.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/post/downloadImage.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/post/postReport.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/post/postMessage.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/post/postScrap.js"></script>
<script>
	var userid = $('#login_id').val();
	var anonymous = ${anonymous};
	var currentUserId = ${currentUserId};  /* 현재 로그인한 유저의 고유번호*/
	var writerId = ${postdata.USER_ID}; /* post(게시물) 작성한 유저의 고유번호 */
</script>
<style>
.status.disabled {
  pointer-events: none; /* 클릭 및 이벤트 무시 */
  color: gray; /* 색상 변경 */
  text-decoration: none; /* 밑줄 제거 */
  cursor: not-allowed; /* 마우스 커서 스타일 변경 */
</style>
</head>
<body>
	<jsp:include page="../common/header.jsp" />
	<jsp:include page="../common/submenu.jsp" />
	
	<div id="container" class="article">
		<input type="hidden" id="isUser" value="1"> <input type="hidden" id="post_id" value="${postdata.POST_ID}"> <br>
		<div class="wrap title">
			<h1>
				<a href="list?board_id=${postdata.BOARD_ID}">${boardtest.NAME}</a>
			</h1>
			<p>${boardtest.CONTENT}</p> 
			<hr>
			
		</div>
		<input type="hidden" id="contextpass" value="${pageContext.request.contextPath}">
		<div class="wrap articles" id="writeBoardContainer">
		<a id="writeArticleButton" style="display: none;">새 글을 작성해주세요!</a>
			<article id="boardInfo" class="item">
				<a class="article"> <img src="${pageContext.request.contextPath}/resources/image/common/profile.png"
					class="picture large">
					<div class="profile">
					    <c:choose>
					        <c:when test="${boardtest.ANONYMOUS eq 0 && postdata.USER_STATUS eq 1}">
					            <h3 class="large">${nickname}</h3>
					        </c:when>
					
					        <c:when test="${boardtest.ANONYMOUS eq 1 && postdata.USER_STATUS eq 1}">
					            <h3 class="large">익명</h3>
					        </c:when>
					        
					        <c:otherwise>
					            <h3 class="large">(알 수 없음)</h3>
					        </c:otherwise>
					    </c:choose>
					
					    <time class="large">${postdata.POST_DATE}</time>
					</div>

					<ul class="status">
					   <!-- 사용자 아이디와 게시물 작성자 아이디가 일치하는 경우에만 수정 버튼 표시 -->
					   <c:choose>
					       <c:when test="${currentUserId eq postdata.USER_ID}">
					           <li class="update" id="updateButton" POST_ID="${postdata.POST_ID}" LOGIN_ID="${currentUserId}">수정</li>
					           <li class="del" data-POST_ID="${postdata.POST_ID}" data-BOARD_ID="${postdata.BOARD_ID}">삭제</li>
					       </c:when>
					       <c:otherwise>
					            <li class="messagesend" data-modal="messageSend"
					               data-article-id="${postdata.POST_ID}" data-is-anonym="${boardtest.ANONYMOUS}">쪽지</li>
					           <li class="abuse">신고</li> 
					       </c:otherwise>
					   </c:choose>
					</ul>
									
					<hr>
					<h2 class="large"> ${postdata.SUBJECT}</h2>
					<p class="large"> ${postdata.CONTENT}</p>
					<div class="attaches multiple">
						<c:forEach var="photo" items="${photos}">
							<figure class="attach">
								<img src="${pageContext.request.contextPath}/upload${photo.PATH}" alt="none" title="${photo.PATH}" 
											onclick="downloadImage('${pageContext.request.contextPath}/upload${photo.PATH}')">
							</figure>
						</c:forEach>
					</div>
					<ul class="status left">
						<li title="공감" class="vote"> ${postdata.LIKE_COUNT} </li>
						<li title="댓글" class="comment"> ${postdata.COMMENT_COUNT} </li>
						<li title="스크랩" class="scrap"> ${postdata.SCRAP_COUNT} </li>
					</ul>
					<hr>
					<div class="buttons">
						<span class="posvote" data-post_id="${postdata.POST_ID}">공감</span>
						<span class="scrap add">스크랩</span>
					</div> 
					<input type="hidden" id="comment_post_id" value="${postdata.POST_ID}">
				</a>
				<%--------------------------------- comment 시작 ---------------------------------------------------%>
				<div class="comments" style="display: block;">

					<form class="writecomment">
						<input type="text" name="text" maxlength="300" autocomplete="off"
							placeholder="댓글을 입력하세요." class="text">
						<ul class="option">
							<li title="취소" class="cancel"
								style="display:none; margin-right: 5px; background-image: url('${pageContext.request.contextPath}/resources/image/common/cancel.png') !important; background-size: 12px;"></li>
							<li title="완료" class="submit submit_origin"></li>
						</ul>
						<div class="clearBothOnly"></div>
					</form>
				</div>
			</article>
			<%--------------------------------- comment 끝 ---------------------------------------------------%>
			<div class="clearBothOnly"></div>
			<div class="pagination">
				<a id="goListButton" class="list" data-board_id="${boardtest.BOARD_ID}">글 목록</a>
				<div class="center-block">
					<ul class="pagination justify-content-center">
					</ul>
				</div> 
			</div>
		</div>
		<hr>
		<jsp:include page="../common/rightside3.jsp" />
		
		
		<%------------------------------------------------ ▼쪽지모달▼ ------------------------------------------------%>
		<form id="messageSend" class="modal" style="margin-left: -200px; margin-top: -92.5px; display: none;">
			<a title="닫기" class="close"></a>
			<h3>쪽지 보내기</h3>
			<p>
				<textarea name="message" class="text" placeholder="내용을 입력해주세요."></textarea>
			</p>
			<input type="submit" value="전송" class="button">
		</form>
		<%------------------------------------------------ ▲쪽지모달▲ ------------------------------------------------%>
		
		<%------------------------------------------------ ▼신고모달▼ ------------------------------------------------%>
		<form id="abuseForm" class="modal" style="margin-left: -200px; margin-top: -92.5px; display: none;">
			<a title="닫기" class="close"></a>
			<h3>신고 사유 선택</h3>
			<ul>
				<li><a data-reason="1">게시판 성격에 부적절함</a></li>
				<li><a data-reason="2">욕설/비하</a></li>
				<li><a data-reason="3">음란물/불건전한 만남 및 대화</a></li>
				<li><a data-reason="4">상업적 광고 및 판매</a></li>
				<li><a data-reason="5">유출/사칭/사기</a></li>
				<li><a data-reason="6">낚시/놀람/도배</a></li>
				<li><a data-reason="7">정당/정치인 비하 및 선거운동</a></li>
			</ul>
		</form>
		<%------------------------------------------------ ▲신고모달▲ ------------------------------------------------%>
	</div>
	
	
	<%-- -------------------------------- ▼footer CSS수정 전이라 임시주석처리중입니다.▼ --------------------------------
	<jsp:include page="../common/footer.jsp" /> 
	--%>

</body>
</html>
