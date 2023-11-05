<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/rightside1.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/common/rightside.js"></script>
<script>
$(function() {
    document.getElementById("searchButton").addEventListener("click", function() {
        document.getElementById("searchArticleForm").submit();
    });
});
</script>
</head>
<body>
	<div class="rightside">
	<!-- <div class="card"> -->
		<div class="rightside1">
		<form id="searchArticleForm" class="search" 
				action="${pageContext.request.contextPath}/post/search" method="get">
				<input type="hidden" name="search_field" value="4">
				<input name="search_word" placeholder="전체 게시판의 글을 검색하세요." value="${search_word}" type="text">
				<button type="button" id="searchButton">&nbsp;&nbsp;</button>
		</form>
		</div>
		<div class="card rightside">

		</div>
        
	</div>
</body>
</html>