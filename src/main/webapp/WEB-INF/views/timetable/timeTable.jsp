<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<html> 
<head> 
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/image/common/nav.logo.png"> 
<title>애니타임</title> 
<jsp:include page="../common/header.jsp" /> 
 
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/common.css" rel="stylesheet"> 
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/common.partial.css" rel="stylesheet"> 
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/container.modal.css" rel="stylesheet"> 
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/timetable/container.table.css" rel="stylesheet"> 
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/timetable/subjects.css" rel="stylesheet">
 
<meta name="_csrf" content="${_csrf.token}"> 
<meta name="_csrf_header" content="${_csrf.headerName}"> 
<script src="https://code.jquery.com/jquery-latest.js"></script> 
 
</head> 
<style>
    #canvas {
        margin-top: 23px;  
    }
    #tableSetting, #customsubjects {
    	border-radius: 12px;
	}
	#container form.modal{
		margin-left: -200px;
	    margin-top: -153px;
	}
</style>	
<body style="height: 850px;"> 
 
	<div id="container" class="timetable"> 
		<hr> 
 
		<aside style="margin-top: 23px;"> 
			<form class="select"> 
				<select id="semesters"> 
					<option>2023년 2학기</option> 
					<option>2023년 1학기</option> 
					<option>2022년 2학기</option> 
					<option>2022년 1학기</option> 
					<option>2021년 2학기</option> 
					<option>2021년 1학기</option> 
					<option>2020년 2학기</option> 
					<option>2020년 1학기</option> 
					<option>2019년 2학기</option> 
					<option>2019년 1학기</option> 
					<option>2018년 2학기</option> 
					<option>2018년 1학기</option> 
					<option>2017년 2학기</option> 
					<option>2017년 1학기</option> 
					<option>2016년 2학기</option> 
					<option>2016년 1학기</option> 
					<option>2015년 2학기</option> 
					<option>2015년 1학기</option> 
				</select> 
			</form> 
			<div class="title"> 
				<h1 id="tableName" data-id=""></h1> 
				<div class="description"> 
					<ul class="info"> 
						<li><time id="tableUpdatedAt">시간표 수정날짜</time> 변경</li> 
					</ul> 
				</div> 
				<hr> 
 
				<ol class="buttons threecols"> 
					<li data-modal="tableSetting"><a id="settingBtn" 
						class="light image setting">설정</a></li> 
				</ol> 
 
				<hr> 
			</div> 
 
			<div class="menu"> 
				<ol> 
					<li class="active"><a href="/anytime/timetable/8599353" 
						class="primary"></a></li> 
					<li class="extension"><a class="create">새 시간표 만들기</a></li> 
				</ol> 
			</div> 
		</aside> 
		
 		<canvas id="canvas" width="1200" height="860"></canvas> 
 
		<form id="tableSetting" class="modal"> 
			<a title="닫기" class="close"></a> 
			<h3>시간표 설정</h3> 
			<p> 
				<label>이름</label> <input type="text" name="name" value="" 
					maxlength="40" class="text"> 
			</p> 
			<p> 
				<label>기본</label> <input type="checkbox" 
					id="tableSetting_is_primary" name="is_primary" disabled><label 
					for="tableSetting_is_primary" class="checkbox">기본시간표 설정</label> 
			</p> 
			<input type="button" value="삭제" id="deleteBtn" class="button light floatLeft"> 
			<input type="submit" value="설정 저장" class="button"> 
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"> 
		</form> 
 
		<ul class="floating" style="left: 651px;"> 
			<li id="addNewClassBtn" class="button custom only">새 수업 추가</li> 
		</ul> 
	</div> 
 
	<form id="customsubjects" style="display: none;"> 
		<input type="hidden" name="subject_id" value=""> <a title="닫기" 
			class="close"></a> 
			
		<h2>새 수업 추가</h2> 
		<dl> 
			<dt>과목명 (필수)</dt> 
			<dd> 
				<input type="text" name="subject" placeholder="예) 자료구조" 
					maxlength="40" class="text"> 
			</dd> 
			<dt>교수명</dt> 
			<dd> 
				<input type="text" name="professor" placeholder="예) 홍길동" 
					maxlength="40" class="text"> 
			</dd> 
			<dt>시간/장소</dt> 
			<dd class="timeplaces"> 
				<div class="timeplace"> 
					<ol class="weeks"> 
						<li class="active">월</li> 
						<li>화</li> 
						<li>수</li> 
						<li>목</li> 
						<li>금</li> 
					</ol> 
					<p> 
						<select class="starthour">
							<option value="8">오전 8시</option> 
							<option value="9" selected="selected">오전 9시</option> 
							<option value="10">오전 10시</option> 
							<option value="11">오전 11시</option> 
							<option value="12">오후 12시</option> 
							<option value="13">오후 1시</option> 
							<option value="14">오후 2시</option> 
							<option value="15">오후 3시</option> 
							<option value="16">오후 4시</option> 
							<option value="17">오후 5시</option> 
							<option value="18">오후 6시</option> 
							<option value="19">오후 7시</option> 
							<option value="20">오후 8시</option>
							</select> <span>~</span>
							<select class="endhour">
							<option value="8">오전 8시</option> 
							<option value="9">오전 9시</option> 
							<option value="10" selected="selected">오전 10시</option> 
							<option value="11">오전 11시</option> 
							<option value="12">오후 12시</option> 
							<option value="13">오후 1시</option> 
							<option value="14">오후 2시</option> 
							<option value="15">오후 3시</option> 
							<option value="16">오후 4시</option> 
							<option value="17">오후 5시</option> 
							<option value="18">오후 6시</option> 
							<option value="19">오후 7시</option> 
							<option value="20">오후 8시</option>
							</select> 
							<input type="text" placeholder="예) L3" class="text place"> 
					</p> 
				</div> 
			</dd> 
		</dl> 
		<div class="clearBothOnly"></div> 
		<div class="submit"> 
			<input type="submit" value="저장" class="button"> 
		</div> 
		<input type="hidden" name="${_csrf.parameterName}" 
			value="${_csrf.token}"> 
	</form> 
	<div id="subjects"></div> 
	<script src="${pageContext.request.contextPath}/resources/js/timetable/timeTable.js"></script> 
</body> 
</html>