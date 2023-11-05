<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/image/common/nav.logo.png">
<title>애니타임</title>
<link
	href="${pageContext.request.contextPath}/resources/css/common/common.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/member/login/main.common.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/member/login/main.login.css"
	rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/jquery-3.7.0.js"></script>

<script>
	if ("${result}" == 'joinSuccess') {
		alert("회원가입을 축하합니다.")
	} else if ("${loginfail}" == 'loginFailMsg') {
		alert("아이디나 비밀번호 오류 입니다.")
	} else if ("${result}" == 'Success') {
		alert("아이디 정보가 발송되었습니다..")
	} else if ("${changePassword}" == "Success") {
		alert("비밀번호 수정이 완료 되었습니다.")
	}

	$(function() {

		const userid = '${userid}';
		if (userid) {
			$("#userid").val(userid);
			$("#autologin").prop('checked', true);
		}
	})
	
  function frmSubmit(token) {
    // reCAPTCHA 응답 토큰을 폼에 추가
    document.getElementsByName('grecaptcha')[0].value = token;

    // 폼 제출
    document.forms[0].submit();
}
	
	
</script>



</head>
<body style="">
	<div id="container" class="login">
		<h1 class="logo">
			<a href="${pageContext.request.contextPath}/main/home">애니타임</a>
		</h1>
		<script src='https://www.google.com/recaptcha/api.js'></script>
		<form action="${pageContext.request.contextPath}/member/loginProcess"
			method="post" data-gtm-form-interact-id="0">
			<p class="input">
				<input type="text" id="login_id" name="login_id" maxlength="20"
					class="text" placeholder="아이디" data-gtm-form-interact-field-id="0">
			</p>
			<p class="input">
				<input type="password" name="password" maxlength="20" class="text"
					placeholder="비밀번호" data-gtm-form-interact-field-id="1">
			</p>
			<input type="hidden" name="redirect" value="/">
			<p class="submit">
				<input type="submit" value="로그인" class="g-recaptcha" data-sitekey="6LfKpMkoAAAAAIZoMNWQqdTTbGJnIwD8FLgZ88eL" 
				data-callback='frmSubmit' data-action="submit" >
			</p>
			 <input type="hidden" name="grecaptcha" />
			<label class="autologin"> <input type="checkbox"
				name="autologin" id="autologin"><span>로그인 유지</span></label>

			<p class="find">
				<a href="forgotid">아이디/비밀번호 찾기</a>
			</p>
			<p class="register">
				<span>애니타임에 처음이신가요?</span> <a href="register">회원가입</a>
			</p>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}">
		</form>
	</div>


</body>
</html>