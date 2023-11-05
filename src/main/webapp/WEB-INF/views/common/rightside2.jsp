<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/rightside2.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-latest.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common/rightside.js"></script>
<script>
$(function() {
    $("#searchButton").click(function() {
        $("#searchArticleForm").submit();
    });
});
</script>
</head>
<body>
	<div class="rightside">
	<!-- <div class="card"> -->
		<div class="rightside2">
		
		 <form id="searchArticleForm" class="search" 
                action="${pageContext.request.contextPath}/post/search" method="get" accept-charset="UTF-8">
                <input type="hidden" name="board_id" value="${board_id}">
                <select name="search_field" id="search_field">
                    <option value="0">전체</option>
                    <option value="1">글 제목</option>
                    <option value="2">글 내용</option>
                </select> 
                <input name="search_word" id="search_word" placeholder="검색어를 입력하세요." 
                       value="${search_word}" type="text">
                <button type="button" id="searchButton">&nbsp;&nbsp;</button>
            </form>
		
		</div>
			<div class="card rightside">
			
			</div>
	</div>
</body>
</html>