<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/image/common/nav.logo.png">
<title>애니타임</title>
<link type="text/css"
	rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/member/join/register.css" >
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function () {
  // 학교 목록 - 처음에는 숨김
  $(".campuses li").hide();

  // 검색 필드에 키 입력 이벤트
  $("input[name='campus_name']").on("keyup", function () {
    const searchValue = $(this).val().toLowerCase();

    // 검색어가 비어있다면 학교 목록을 숨기고 반환
    if (!searchValue) {
      $(".campuses li").hide();
      return;
    }

    // 각 학교 목록에 대해 반복
    $(".campuses li").each(function () {
      const schoolName = $(this).text().toLowerCase();

      // 검색어가 학교 이름에 포함되어 있으면 표시, 그렇지 않으면 숨김
      if (schoolName.indexOf(searchValue) > -1) {
        $(this).show();
      } else {
        $(this).hide();
      }
      
    });
  });
//학교 목록의 각 항목 클릭 시 이벤트
  $(".campuses li a").on("click", function (e) {
    e.preventDefault(); // 링크의 기본 동작을 막음

    const selectedSchool = $(this).text(); // 선택된 학교의 이름을 가져옴
    $("input[name='campus_name']").val(selectedSchool); // input 필드에 선택된 학교 이름 설정

    // 선택된 학교 목록을 숨김 (필요하다면)
    $(".campuses li").hide();
  });
  
//폼 이벤트 리스너 추가
  $("form").on("submit", function (e) {
    let isValid = true;

    // 입학년도 선택 검사
    if (!$("select[name='enter_year']").val()) {
      alert("입학년도를 입력하세요");
      isValid = false;
    } else if (!$("input[name='campus_name']").val()) { // else if를 이용해 최초의 조건만 기능하도록 함
      // 학교 검사
      alert("가입할 학교를 선택해주세요");
      isValid = false;
    }

    // 입력 검사에 실패한 경우, 폼 제출을 막음
    if (!isValid) {
      e.preventDefault();
    }
  });
});
</script>
</head>
<body>
	<form id="container" method="post" action="${pageContext.request.contextPath}/member/agreement"
		data-gtm-form-interact-id="0">
		<h2>애니타임 회원가입</h2>
		<p class="description">
			애니타임 계정으로 다양한 대학생 서비스를 모두 이용하실
			수 있습니다.
		</p>
		<h2>학교 선택</h2>
		<div class="input">
			<div class="label">
				<label>입학년도</label>
			</div>
			<select name="enter_year" data-gtm-form-interact-field-id="0">
				<option disabled selected>연도 선택 (학번)</option>
				<option value="2023">2023학번</option>
				<option value="2022">2022학번</option>
				<option value="2021">2021학번</option>
				<option value="2020">2020학번</option>
				<option value="2019">2019학번</option>
				<option value="2018">2018학번</option>
				<option value="2017">2017학번</option>
				<option value="2016">2016학번</option>
				<option value="2015">2015학번</option>
				<option value="2014">2014학번</option>
				<option value="2013">2013학번</option>
				<option value="2012">2012학번</option>
				<option value="2011">2011학번</option>
				<option value="2010">2010학번</option>
				<option value="2009">2009학번</option>
				<option value="2008">2008학번</option>
				<option value="2007">2007학번</option>
				<option value="2006">2006학번</option>
				<option value="2005">2005학번</option>
				<option value="2004">2004학번</option>
				<option value="2003">2003학번</option>
				<option value="2002">2002학번</option>
				<option value="2001">2001학번</option>
				<option value="2000">2000학번</option>
				<option value="1999">1999학번</option>
				<option value="1998">1998학번</option>
				<option value="1997">1997학번</option>
				<option value="1996">1996학번</option>
				<option value="1995">1995학번</option>
				<option value="1994">1994학번</option>
				
			</select>
		</div>
		<div class="input">
			<div class="label">
				<label>학교</label>
			</div>
			<input type="text" name="campus_name" maxlength="20"
				placeholder="학교 이름을 검색하세요." autocomplete="off" class="search"
				data-gtm-form-interact-field-id="1"> <input type="hidden"
				name="campus_id" value="341">
		</div>
		<ol class="campuses" >
			<!-- 검색된 학교 리스트 출력 -->
			<c:forEach var="school" items="${schoolList}">
				<li><a>${school.name}</a></li>
			</c:forEach>
			<!--  필터링하기 -->
		
		</ol>
		<input type="submit" value="다음">
		
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		
	</form>

</body>
</html>