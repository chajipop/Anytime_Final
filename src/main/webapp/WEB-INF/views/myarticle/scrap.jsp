<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>애니타임</title>
	<jsp:include page="../common/header.jsp" />
	<jsp:include page="../common/submenu.jsp" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/common.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/common.partial.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/container.article.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/container.community.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/container.modal.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/myarticle/myarticle.css">
<script src="${pageContext.request.contextPath}/resources/js/myarticle/scrap.js"></script>
</head>
<body>
	<div id="container" class="article">

		<aside class="none">
			<div class="title">
				<a class="hamburger"></a>
				<h1>
					<a href="${pageContext.request.contextPath}/myscrap">내 스크랩</a>
				</h1>
			</div>
		</aside>
		<div class="wrap title">
			<h1>
				<a href="${pageContext.request.contextPath}/myscrap">내 스크랩</a>
			</h1>
			<hr>
		</div>
		<div class="wrap bubbles none"></div>
		<div class="wrap articles">

			
		<div class="clearBothOnly"></div>
			<div class="center-block">
				
				<ul class="pagination justify-content-center">

				</ul>
						
			</div>
		
		
		<hr>
	</div>
		<jsp:include page="../common/rightside3.jsp" />
	</div>
	

</body>
</html>