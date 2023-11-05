<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/header_admin.jsp" />
<head>
<title>관리자 페이지 - 애니타임</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common/common.css">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/container.modal.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/admin.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common/modal.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/post/postlist.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/post/postwrite.css">

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.7.0.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/post/write.js"></script>
</head>
<script>
	$(function() {
		var page = 1; //페이지 변수를 1로 초기화
		var searchKey = 0;//검색기능을 위해 검색 변수 초기화
		var keyword = 'null';

		go(page, searchKey, keyword);

	});
	function go(page, searchKey, keyword) {
		var output = '';
		$
				.ajax({
					type : 'get',
					url : 'adminNoticeList',
					data : {
						page : page,
						searchKey : searchKey,
						keyword : keyword
					},
					dataType : 'json',
					success : function(rdata) {
						$('.articles  article.list').empty();
						$('ul.pagination').empty();
						if (rdata.notice.length > 0) {
							let output = "";
							keyword = "'" + keyword + "'";
							$(rdata.notice)
									.each(
											function() {
												output += '<a class="article" href="post/detail?post_id='
														+ this.post_ID
														+ '"><div class="desc">'
														+ '<h2 class="medium bold">'
														+ this.subject
														+ '</h2><p class="small">'
														+ this.content
														+ '</p><div class="info"><ul class="status"><li title="공감" class="vote">'
														+ this.like_COUNT
														+ '</li></ul><time class="small">'
														+ this.post_DATE
														+ '</time><h3 class="admin bold small">애니타임</h3></div><hr></div></a>'
											})//each end
							$('.articles  article.list').append(output)

							let maxpage = rdata.maxpage;
							let startpage = rdata.startpage;
							let endpage = rdata.endpage;
							let listcount = rdata.listcount;
							let limit = rdata.limit;

							nextpage = page + 1;
							beforepage = page - 1;

							output = '';

							if (page <= 1) {
								output += '<li class="page-item"><li class="page-item hidden"><a class="page-link gray">'
										+ '이전&nbsp;&nbsp;</a></li>																';
							} else if (page > 1) {
								output = '<li class="page-item"><a href="javascript:go('
										+ beforepage
										+ ','
										+ searchKey
										+ ','
										+ keyword
										+ ')" class="page-link">이전</a>&nbsp;</li>';
							}

							for (let i = startpage; i <= endpage; i++) {
								if (page == i) {
									output += '<li class="page-item active"><a class="page-link">'
											+ i + '</a></li>';
								} else {
									output += '<li class="page-item"><a href="javascript:go('
											+ i
											+ ','
											+ searchKey
											+ ','
											+ keyword
											+ ')" class="page-link">'
											+ i + '</a></li>';
								}
							}
							if (page >= maxpage) {
								output += '<li class="page-item"><a class="page-link gray hidden">&nbsp;다음</a></li>';
							} else if (page < maxpage) {
								output += '<li class="page-item"><a href="javascript:go('
										+ nextpage
										+ ','
										+ searchKey
										+ ','
										+ keyword
										+ ')" class="page-link">&nbsp;다음</a></li>';
							}
							$('ul.pagination').append(output);

						}

					}
				})
	}
</script>
<style>
#container>div.articles>article {
	border: 0px solid #e3e3e3 !important;
}
</style>
<body>
	<div id="wrapper">
		<jsp:include page="../common/left_admin.jsp" />
		<div id="container" class="article notice"
			style="margin-top: 85px; margin-left: 55px;">
			<div class="wrap title">
				<h1>
					<a href="${pageContext.request.contextPath}/notice">공지사항</a>
				</h1>
				<hr>
			</div>
			<div id="board_id" style="display: none;"><%= session.getAttribute("board_id") %></div>
			<div class="wrap articles" id="writeBoardContainer">
				<label class="postwriteform"> </label> <a id="writeArticleButton">새
					글을 작성해주세요! <img
					src="${pageContext.request.contextPath}/resources/image/post/write.button.png">
				</a>
				<div class="wrap bubbles none"></div>
				<article class="list"></article>
			</div>
			<div class="center-block" style="margin-left: -385px;">
				<ul class="pagination justify-content-center">
				</ul>
			</div>
		</div>
	</div>
</body>
</html>