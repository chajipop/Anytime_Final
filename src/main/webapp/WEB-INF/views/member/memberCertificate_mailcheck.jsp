<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/image/common/nav.logo.png" >
<title>애니타임_이메일 본인 인증</title>
<script src="<%=request.getContextPath()%>/js/jquery-3.7.0.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/member/info/mailcheck.css">
	
<script>
var isVerified = false; //인증완료 확인

function Verifyemail(){
    var checknum = document.getElementsByName("checknum")[0].value;
    var randnum = document.getElementById("RandNum").value;
    
    if (checknum === '') {
        alert("인증번호를 입력해주세요.");
        isVerified =false;
    } else if (checknum == randnum) {
        alert("인증되었습니다.");
        isVerified = true;
    } else {
        alert("인증번호가 올바르지 않습니다. 다시 시도해주세요. ");
        isVerified =false;
    }
}

function onSubmit() {
    if (!isVerified) {
        alert("학교 웹메일 인증을 완료해주세요.");
    } else {
        document.emailform.submit(); // 여기서 폼 제출을 진행합니다.
    }
}
</script>
<style>
nav {
	border-bottom: 0 !important;
}
</style> 
</head>
<body style="">
	<form name="emailform" action="certificateProcess" method="get"
		id="container" data-adagreement="1" data-redirecturl="/"
		  onsubmit="event.preventDefault(); onSubmit();">

		<h2>학교 웹메일 본인 인증</h2>
		
		<div class="input">
			<div class="label" >
				<label>인증번호</label>
			</div>
			<input type="text" name="checknum" maxlength="10" autocomplete="off" placeholder ="인증번호">
			 <input type="button" id="checknum" value="확인" onclick ="Verifyemail()">
			<div class="caution"></div>
            
            <%-- 응답받은 난수를 받아오기 위해 생성한 히든 인풋 --%>
            <input type="hidden" id = "RandNum" name = "RandNum" value="${authCode}"/>
			
		</div>
		
		<input type="submit" value="학교인증 완료" >
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	</form>
</body>
</html>