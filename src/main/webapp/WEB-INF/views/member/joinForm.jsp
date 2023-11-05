<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>애니타임</title>
<script src="<%=request.getContextPath()%>/resources/js/jquery-3.7.0.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
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
		let checkid = false;
		let checkpassword = false;
		let checkpasswordmatch = false;
		let checkemail = false;
		let checknickname = false;
		let checkPhoneNumber = false;

		//id 유효성 검사
		$("input[name=login_id]").on(
				'keyup',
				function() {

					//[A-Za-z0-9_]의 의미가 \w
					const pattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{4,20}$/;
					const id = $(this).val();
					if (!pattern.test(id)) {
						$("#message").css('color', '#e54787').html(
								"&nbsp;&nbsp;&nbsp;영문자,숫자로 4~20자 가능합니다.")
						checkid = false;
						return;
					}

					$.ajax({
						url : "idcheck",
						data : {"login_id" : id},
						success : function(resp) {
							if (resp == -1) { //db에 해당 id가 없는 경우
								$("#message").css('color', '#624cff').html(
										"&nbsp;&nbsp;&nbsp;사용 가능한 아이디 입니다.");
								checkid = true;
							} else { //db에 해당 id가 있는 경우(0)
								$("#message").css('color', '#e54787').html(
										"&nbsp;&nbsp;&nbsp;사용중인 아이디 입니다.");
								checkid = false;
							}
						}
					}); //ajax end
				}); //keyup end

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
								$("#password_message1").css('color', '#624cff')
										.html("&nbsp;&nbsp;&nbsp;유효한 비밀번호 형식입니다.");
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

		//e-mail 유효성 검사		
		$("input[name=email]").on(
				'keyup',
				function() {
					//[A-Za-z0-9_]와 동일한 것이 \w 입니다.
					//+는 1회 이상 반복을 의미하고 {1,}와 동일합니다.
					//\w+는 [A-Za-z0-9_]를 1개이상 사용하라는 의미입니다.
					const pattern = /^\w+@\w+[.]\w{3}$/;
					const email = $(this).val();
					
					if (!pattern.test(email)) {
						$("#email_message").css('color', '#e54787').html(
								"&nbsp;&nbsp;&nbsp;이메일형식이 맞지 않습니다.");
						checkemail = false;
						return;
					} 
					$.ajax({
						url : "mailcheck",
						data : {
							"email" : email
						},
						success : function(resp) {
							if (resp == -1) {//db에 해당 email이  없는 경우
								$("#email_message").css('color', '#624cff')
										.html("&nbsp;&nbsp;&nbsp;사용 가능한 이메일 입니다.");
								checkemail = true;
							} else { //db에 해당 email이 있는 경우 
								$("#email_message").css('color', '#e54787')
										.html("&nbsp;&nbsp;&nbsp;사용중인 이메일 입니다.");
								checkemail = false;
							}
						}
					});
					
				}); //email keyup end

		//닉네임 중복 유효성 검사
		$("input[name=nickname]").on(
				'keyup',
				function() {
					const nickname = $(this).val().trim(); // 입력한 닉네임의 양쪽 공백을 제거
					const forbiddenNicknames = [ '(알수없음)', '(삭제)' ];

					if (nickname === '') {
						$("#nickname_message").css('color', '#e54787').html(
								"&nbsp;&nbsp;&nbsp;닉네임을 입력해주세요.");
						checknickname = false;
						return;
					}

					if (forbiddenNicknames.includes(nickname)) {
						$("#nickname_message").css('color', '#e54787').html(
								"&nbsp;&nbsp;&nbsp;사용할 수 없는 닉네임입니다.");
						checknickname = false;
						return;
					}

					$.ajax({
						url : "nicknamecheck",
						data : {
							"nickname" : nickname
						},
						success : function(resp) {
							if (resp == -1) {
								$("#nickname_message").css('color', '#624cff')
										.html("&nbsp;&nbsp;&nbsp;사용 가능한 닉네임입니다.");
								checknickname = true;
							} else {
								$("#nickname_message").css('color', '#e54787')
										.html("&nbsp;&nbsp;&nbsp;사용중인 닉네임입니다.");
								checknickname = false;
							}
						}
					});
				});

		//휴대전화 유효성 검사
		$("input[name=phone_num]").on(
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
						$("#phone_message").css('color', '#e54787').html(
								"&nbsp;&nbsp;&nbsp;휴대전화 번호 형식이 맞지 않습니다. (010********)");
						checkPhoneNumber = false;
					} else {
						$("#phone_message").css('color', '#624cff').html(
								"&nbsp;&nbsp;&nbsp;유효한 휴대전화 번호입니다.");
						checkPhoneNumber = true;
					}
				});

		$('form').submit(function() {

			if (!checkid) {
				alert("사용 가능한 아이디를 입력하세요.");
				$("input[name=userid]").val('').focus();
				return false;
			}

			if (!checkpassword) {
				alert("영문, 숫자, 특수문자를 각각 1개 이상 포함한 8~20자의 비밀번호를 입력하세요.");
				$("input[name=password]").val('').focus();
				return false;
			}

			if (!checkpasswordmatch) {
				alert("비밀번호가 일치하지 않습니다.");
				$("input[name=password2]").val('').focus();
				return false;
			}
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
				$("input[name=phonenumber]").val('').focus();
				return false;
			}

		}); //submit end
	}); //ready function() end
</script>

<link href="${pageContext.request.contextPath}/resources/css/member/join/register.css"
	rel="stylesheet">
</head>
<body style="">
	<form name="joinform" action="joinProcess" method="post"
		id="container" data-adagreement="1" data-redirecturl="/">

		<h2>회원 정보</h2>
		<div class="input">
			<div class="label">
				<label>아이디<span>*</span></label>
				<p>영문, 숫자, 4~20자</p>
			</div>
			<input type="text" name="login_id" maxlength="20" placeholder="아이디"
				autocomplete="off">
			<div class="caution"></div>
			<span id="message" style="font-size: 12px; "></span>
		</div>
		<div class="input">
			<div class="label">
				<label>비밀번호<span>*</span></label>
				<p>영문,숫자,특문이 각각 1개 이상 포함된 8~20자</p>
			</div>
			<input type="password" name="password" maxlength="20"
				placeholder="비밀번호">
			<div class="caution"></div>
			<span id="password_message1" style="font-size: 12px"></span> <input
				type="password" name="password2" maxlength="20"
				placeholder="비밀번호 확인">
			<div class="caution"></div>
			<span id="password_message2" style="font-size: 12px"></span>
		</div>
		<div class="input">
			<div class="label">
				<label>이메일<span>*</span></label>
				<p>아이디/비밀번호 찾기에 필요</p>
			</div>
			<input type="email" name="email" placeholder="이메일" autocomplete="off">
			<div class="caution"></div>
			<span id="email_message" style="font-size: 12px"></span>
		</div>
		<div class="input">
			<div class="label">
				<label>닉네임<span>*</span></label>
				<p>커뮤니티 활동에 필요</p>
			</div>
			<input type="text" name="nickname" maxlength="10" placeholder="닉네임"
				autocomplete="off">
			<div class="caution"></div>
			<span id="nickname_message" style="font-size: 12px"></span>
		</div>
		<div class="input postal">
			<div class="label">
				<label>우편번호</label>
				<p></p>
			</div>
			<input type="text" id="address_num" name="address_num" maxlength="10"
				placeholder="우편번호" autocomplete="off"> <input type="button"
				id="postnum" value="우편검색" onclick="Postcode()">
			<div class="caution"></div>
		</div>
		<div class="input">
			<div class="label">
				<label>주소</label>
				<p></p>
			</div>
			<input type="text" name="address1" id="address1" maxlength="100"
				placeholder="기본주소" autocomplete="off"> <input type="text"
				name="address2" id="address2" maxlength="100" placeholder="나머지주소"
				autocomplete="off">
			<div class="caution"></div>
		</div>
		<div class="input">
			<div class="label">
				<label>휴대전화<span>*</span></label>
				<p>본인 인증시 필요</p>
			</div>
			<input type="text" name="phone_num" maxlength="11"
				placeholder="010********" autocomplete="off">
			<div class="caution"></div>
			<span id="phone_message" style="font-size: 12px"></span>
		</div>

		<input type="submit" value="회원가입">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	</form>

</body>
</html>