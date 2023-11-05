<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">		
	
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/image/common/nav.logo.png" >
<title>재학생 인증 - 애니타임</title>
<jsp:include page="../common/header.jsp" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/member/info/body.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/member/info/auth2.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/member/info/auth.css">
<script src="<%=request.getContextPath()%>/js/jquery-3.7.0.js"></script>
<script>

//학교 address 일치 검사
function isValidDomain(webmail, schoolDomain) {
	var emailDomain = webmail.substring(webmail.lastIndexOf("@") + 1);
    return emailDomain === schoolDomain;
}

 $(document).ready(function () {
	   let token = $("meta[name='_csrf']").attr("content");
	   let header = $("meta[name='_csrf_header']").attr("content");
	 
        $('#submit-button').click(function (event) {
            event.preventDefault(); // 폼 제출 동작 차단

            const webmail = $('input[name="webemail"]').val();
            const schoolDomain = '${school.address}';

            
            if(webmail.trim()===''){
            	alert('학교 이메일을 입력해주세요');
            	return;
            }

            if(!isValidDomain(webmail, schoolDomain)){
            	alert('학교 웹메일을 입력해주세요.')
            	return;
            }
             alert('인증메일을 발송하였습니다. 이메일을 확인해주세요.');
            
            // 서버에 인증 메일 전송 요청 보내기
            $.ajax({
                url:   'certificate_mailsend', 
                method:"post",            
                data: {"webemail": webmail},
                dataType: 'json',
                beforeSend: function(xhr)
                { // 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
                  xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    if (data.verificationCode) {
                        location.href = "certificate_mailcheck";
                        // redirect to another page
                        $.ajax({
                            url:   'certificate_mailcheck', 
                            method:"post",
                            data: {verificationCode : data.verificationCode},
                            beforeSend: function(xhr)
                            { // 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
                              xhr.setRequestHeader(header, token);
                            },
                        });
                    } else {
                        alert('메일발송 실패, 학교 이메일이 아닙니다.');
                    }
                }
            });
        });
    });
</script> 
<style>
nav {
	border-bottom: 0 !important;
}
</style>   
</head>
<body id="my">
	<div data-v-703578ac="">
		<div data-v-ffaea544="" class="navbar">
			<a data-v-ffaea544="" class="hamburger">메뉴 열기</a>
			<h1 data-v-ffaea544="">재학생 인증</h1>
				<!-- 인증 받은 경우 해당 태그 내 내용 변경-->
		</div>
		<form data-v-703578ac="" class="container">
			<section data-v-703578ac="">
				<h2 data-v-703578ac="">학교 웹메일 인증 안내</h2>
				<p data-v-703578ac="" class="description">학교에서 학내 구성원에게 발급하는
					웹메일을 이용한 인증 방법입니다. 이메일 수신 후 본문에 포함된 링크를 클릭하면 즉시 인증이 완료됩니다.</p>
				<h2 data-v-703578ac="">웹메일 발급 방법</h2>
				<ol data-v-703578ac="" class="description">
					<li data-v-703578ac="">1. 학교 포털 시스템 로그인</li>
					<li data-v-703578ac="">2. [메일계정신청] &gt; [학생메일 가입] 선택</li>
					<li data-v-703578ac="">3. 이용 약관에 모두 동의 후 아이디 입력</li>
					<li data-v-703578ac="">4. [계정생성] 선택</li>
					<li data-v-703578ac="">5. 라이선스 인증 완료 후 비밀번호 설정 </li>
				</ol>
				<h2 data-v-703578ac="">이메일 주소 입력</h2>
				<!---->
				<div data-v-703578ac="" class="input">
					<div data-v-703578ac="" class="label">
						<label data-v-703578ac="">이메일</label>
						<p data-v-703578ac="">${school.address}</p>
					</div>
					<input data-v-703578ac="" type="email" name ="webemail" maxlength="255"
						placeholder="이메일" autocomplete="off">
				</div>
				
				<input data-v-703578ac=""  type="submit" value="인증 메일 발송하기" id="submit-button" >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			</section>
		</form>
	
	</div>
</body>
</body>

</html>