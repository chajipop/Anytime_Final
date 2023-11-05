<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>비밀번호 변경 - 애니타임</title>
<c:if test="${auth eq 'ROLE_MEMBER'}">
	<jsp:include page="../common/header.jsp" />
</c:if>
<c:if test="${auth eq 'ROLE_ADMIN'}">
	<jsp:include page="../common/header_admin.jsp" />
</c:if>
<link data-vue-meta="ssr" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/info/body.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/member/info/changePwd.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/member/info/total.css">
<script src="<%=request.getContextPath()%>/js/jquery-3.7.0.js"></script>
</head>
<script>
	if ("${changePassword}" == "Fail") {
		alert("현재 비밀번호와 일치하지 않습니다.")
	}
	$(function() {
		let checkpassword = false;
		let checkpasswordmatch = false;

		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");

		//비밀번호 유효성 검사
		$("input[name=password], input[name=password2]")
				.on(
						'keyup',
						function() {
							const password = $("input[name=password]").val();
							const password2 = $("input[name=password2]").val();

							// 영문자, 숫자, 특수문자 각각 1개 이상 포함, 8~20자
							const pattern = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$/;

							if (!pattern.test(password)) {
								$("#password_message1")
										.css('color', '#e54787')
										.html(
												"&nbsp;&nbsp;&nbsp;영문, 숫자, 특문이 각각 1개 이상 포함된 8~20자여야 합니다.");
								checkpassword = false;
							} else {
								$("#password_message1")
										.css('color', '#624cff')
										.html(
												"&nbsp;&nbsp;&nbsp;유효한 비밀번호 형식입니다.");
								checkpassword = true;
							}

							if (password2.length >= 1) { // 두 번째 비밀번호 입력부터 검사
								checkPasswordMatch();
							} else {
								$("#password_message2").css('color',
										'transparent').html(""); // 메시지 감춤
								checkpasswordmatch = false;
							}
						});

		// 비밀번호 두 개가 일치하는지 확인하는 함수
		function checkPasswordMatch() {
			const password = $("input[name=password]").val();
			const password2 = $("input[name=password2]").val();

			if (password === password2) {
				$("#password_message2").css('color', '#624cff').html(
						"&nbsp;&nbsp;&nbsp;비밀번호가 일치합니다.");
				checkpasswordmatch = true;
			} else {
				$("#password_message2").css('color', '#e54787').html(
						"&nbsp;&nbsp;&nbsp;비밀번호가 일치하지 않습니다.");
				checkpasswordmatch = false;
			}
		}

		$("#updatepassword").on('click', function(event) {
			if (!checkpassword) {
				alert("비밀번호는 영문, 숫자, 특수문자를 2종류 이상 조합해주세요.");
				$("input[name='password']").val('').focus();
				return false;
			}

			if (!checkpasswordmatch) {
				alert("입력하신 두 비밀번호가 일치하지 않습니다.");
				$("input[name='password2']").val('').focus();
				return false;
			}

			if ($("input[name='oldpassword']").val() == "") {
				alert("현재 비밀번호를 입력하세요");
				$("input[name='oldpassword']").focus();
				return false;

			}
		});
	});
</script>
<style>
body {
	background-color: #EEEEF6 !important;
}

input[type=password] {
	background-color: #f6f6ff !important;
}

input[type=password]:focus {
	background-color: #fff !important;
}

nav {
	border-bottom: 0 !important;
}
</style>
<body id="my">
	<div>
		<form action="${pageContext.request.contextPath}/pwdProcess"
			class="container" name="updatePassword" method="post">
			<section>
				<h1>비밀번호 변경</h1>
				<div class="input">
					<div class="label">
						<label>새 비밀번호</label>
						<p>영문, 숫자, 특문이 2종류 이상 조합된 8~20자</p>
					</div>
					<input type="password" maxlength="20" placeholder="새 비밀번호" class=""
						name="password"> <span id="password_message1"
						style="font-size: 12px; color: #e54787;"></span>
					<!---->
					<input type="password" name="password2" maxlength="20"
						placeholder="새 비밀번호 확인" class=""> <span
						id="password_message2"
						style="font-size: 12px; color: transparent;"></span>

					<!---->
				</div>
				<div class="input">
					<div class="label">
						<label>현재 비밀번호</label>
					</div>
					<input type="password" name="oldpassword" maxlength="20"
						placeholder="현재 비밀번호" class="">
				</div>
				<div class="rules">
					<p>
						<strong>※ 타인에 의한 계정 사용이 의심되시나요?</strong><br> 개인정보 보호를 위해
						비밀번호를 변경하여 주시기 바랍니다. 비밀번호를 변경하면 <span class="caution">모든
							디바이스(앱, 브라우저 등)에서 즉시 로그아웃 처리됩니다.</span>
					</p>
				</div>
				<input type="submit" value="비밀번호 변경" id="updatepassword">
			</section>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}">
		</form>
	</div>
</body>
</html>