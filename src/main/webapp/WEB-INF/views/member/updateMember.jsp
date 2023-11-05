<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>회원 정보 변경 - 애니타임</title>
<c:if test="${auth eq 'ROLE_MEMBER'}">
	<jsp:include page="../common/header.jsp" />
</c:if>
<c:if test="${auth eq 'ROLE_ADMIN'}">
	<jsp:include page="../common/header_admin.jsp" />
</c:if>
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/member/info/info.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.7.0.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
	if ("${result}" == "passwordFail") {
		alert("현재 비밀번호와 일치하지 않습니다.");
	} else if ("${result}" == "changeSuccess") {
		alert("회원 정보가 수정되었습니다.");
	} else if ("${result}" == "changeFail") {
		alert("회원 정보 수정이 실패했습니다.");
	}

	function Postcode() {
		new daum.Postcode({
			oncomplete : function(data) {
				console.log(data.zonecode);

				var fullRoadAddr = data.roadAddress;
				var extraRoadAddr = '';

				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraRoadAddr += data.bname;
				}
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraRoadAddr += (extraRoadAddr !== '' ? ', '
							+ data.buildingName : data.buildingName);
				}
				if (extraRoadAddr !== '') {
					extraRoadAddr = ' (' + extraRoadAddr + ')';
				}
				if (fullRoadAddr !== '') {
					fullRoadAddr += extraRoadAddr;
				}

				$('#address_num').val(data.zonecode); // 우편번호 입력 필드 id를 "post"로 가정
				$('#address1').val(fullRoadAddr); // 기본주소 입력 필드 id를 "address1"로 가정
				// 기타 주소 입력 필드 처리 (나머지 주소)
				// $('#address2').val(...);
			}
		}).open();
	}

	$(function() {
		let checkemail = true;
		let checknickname = true;
		let checkPhoneNumber = true;

		//e-mail 유효성 검사		
		$("input[name=email]").on(
				'keyup',
				function() {
					//[A-Za-z0-9_]와 동일한 것이 \w 입니다.
					//+는 1회 이상 반복을 의미하고 {1,}와 동일합니다.
					//\w+는 [A-Za-z0-9_]를 1개이상 사용하라는 의미입니다.
					const pattern = /^\w+@\w+[.]\w{3}$/;
					const email_value = $(this).val();

					if (!pattern.test(email_value)) {
						$("#email_message").css('color', '#e54787').html(
								"&nbsp;&nbsp;&nbsp;이메일형식이 맞지 않습니다.");
						checkemail = false;
					} else {
						$("#email_message").css('color', '#624cff').html(
								"&nbsp;&nbsp;&nbsp;이메일형식에 맞습니다.");
						checkemail = true;
					}
				}); //email keyup end

		//닉네임 중복 유효성 검사
		$("input[name=nickname]")
				.on(
						'keyup',
						function() {
							const nickname = $(this).val().trim(); // 입력한 닉네임의 양쪽 공백을 제거

							if (nickname === '') {
								$("#nickname_message").css('color', '#e54787')
										.html("&nbsp;&nbsp;&nbsp;닉네임을 입력해주세요.");
								checknickname = false;
								return;
							}

							$
									.ajax({
										url : "member/nicknamecheck",
										data : {
											"nickname" : nickname
										},
										success : function(resp) {
											if (resp == -1) {
												$("#nickname_message")
														.css('color', '#624cff')
														.html(
																"&nbsp;&nbsp;&nbsp;사용 가능한 닉네임입니다.");
												checknickname = true;
											} else {
												$("#nickname_message")
														.css('color', '#e54787')
														.html(
																"&nbsp;&nbsp;&nbsp;사용중인 닉네임입니다.");
												checknickname = false;
											}
										}
									});
						});

		//휴대전화 유효성 검사
		$("input[name=phone_num]")
				.on(
						'keyup',
						function() {
							let phoneNumber = $(this).val().replace(/-/g, ''); // '-' 제거

							// 맨 앞글자를 010으로 강제로 지정
							if (phoneNumber.length >= 3) {
								phoneNumber = "010" + phoneNumber.substring(3);
							}

							// 휴대전화 형식 확인
							const pattern = /^010\d{8}$/;

							if (!pattern.test(phoneNumber)) {
								$("#phone_message")
										.css('color', '#e54787')
										.html(
												"&nbsp;&nbsp;&nbsp;휴대전화 번호 형식이 맞지 않습니다. (010********)");
								checkPhoneNumber = false;
							} else {
								$("#phone_message")
										.css('color', '#624cff')
										.html(
												"&nbsp;&nbsp;&nbsp;유효한 휴대전화 번호입니다.");
								checkPhoneNumber = true;
							}
						});

		$('form').submit(function() {

			if (!checkemail) {
				alert("이메일 형식에 맞는 이메일을 입력하세요.");
				$("input[name=email]").val('').focus();
				return false;
			}

			if (!checknickname) {
				alert("사용 가능한 닉네임을 입력하세요.");
				$("input[name=nickname]").val('').focus();
				return false;
			}

			if (!checkPhoneNumber) {
				alert("유효한 휴대전화 번호를 입력하세요. (010********)");
				$("input[name=phone_num]").val('').focus();
				return false;
			}

			if ($("input[name=password]").val() == '') {
				alert("현재 비밀번호를 입력하세요.");
				$("input[name=password]").focus();
				return false;
			}

		}); //submit end
	}); //ready function() end
</script>
<style>
nav {
	border-bottom: 0 !important;
}

body {
	background-color: #EEEEF6 !important;
}
</style>
</head>
<body>
	<div>
		<form id="container" action="updateProcess" method="post"
			data-adagreement="1" data-redirecturl="/">
			<section>
				<h2>회원 정보</h2>
				<div class="input">
					<div class="label">
						<label>이메일<span>*</span></label>
						<p>아이디/비밀번호 찾기에 필요</p>
					</div>
					<input type="email" name="email" placeholder="이메일"
						autocomplete="off" value="${member.email}">
					<div class="caution"></div>
					<span id="email_message" style="font-size: 12px"></span>
				</div>
				<div class="input">
					<div class="label">
						<label>현재 비밀번호<span>*</span></label>
					</div>
					<input type="password" name="password" maxlength="20"
						placeholder="비밀번호">
					<div class="caution"></div>
				</div>
				<div class="input">
					<div class="label">
						<label>닉네임<span>*</span></label>
						<p>커뮤니티 활동에 필요</p>
					</div>
					<input type="text" name="nickname" maxlength="10" placeholder="닉네임"
						autocomplete="off" value="${member.nickname}">
					<div class="caution"></div>
					<span id="nickname_message" style="font-size: 12px"></span>
				</div>
				<div class="input postal">
					<div class="label">
						<label>우편번호</label>
						<p></p>
					</div>
					<input type="text" name="address_num" maxlength="10"
						placeholder="우편번호" autocomplete="off"
						value="${member.address_num}"> <input type="button"
						id="postnum" value="우편검색" onclick="Postcode()">
					<div class="caution"></div>
				</div>
				<div class="input">
					<div class="label">
						<label>주소</label>
						<p></p>
					</div>
					<input type="text" name="address1" maxlength="10"
						placeholder="기본주소" autocomplete="off" value="${member.address1}">
					<input type="text" name="address2" maxlength="10"
						placeholder="나머지주소" autocomplete="off" value="${member.address2}">
					<div class="caution"></div>
				</div>
				<div class="input">
					<div class="label">
						<label>휴대전화<span>*</span></label>
						<p>본인 인증시 필요</p>
					</div>
					<input type="text" name="phone_num" maxlength="11"
						placeholder="010-****-****" autocomplete="off"
						value="${member.phone_num}">
					<div class="caution"></div>
					<span id="phone_message" style="font-size: 12px"></span>
				</div>
				<input type="submit" value="내 정보 변경">
			</section>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}">
		</form>
	</div>
</body>
</html>