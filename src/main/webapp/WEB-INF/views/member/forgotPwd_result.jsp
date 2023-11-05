<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>애니타임</title>

<link data-vue-meta="ssr" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/login/0a14fb2000808afce700.css">
<link data-vue-meta="ssr" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/login/d7ec1914a4608cfbd829.css">
<script src="<%=request.getContextPath()%>/js/jquery-3.7.0.js"></script>
<script>
$(document).ready(function () {
    $('form').submit(function(e) {
        e.preventDefault();

        const password = $("input[name=password]").val();
        const password2 = $("input[name=password2]").val();

        // 영문자, 숫자, 특수문자 각각 1개 이상 포함, 8~20자
        const pattern = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$/;

        if (!password || !password2) {
            alert("비밀번호를 입력해주세요.");
            $("input[name=password]").val('').focus();
            return false;
        }

        if (!pattern.test(password)) {
            alert("영문, 숫자, 특수문자를 각각 1개이상 포함한 8~20자의 비밀번호를 입력하세요.");
            $("input[name=password]").val('').focus();
            return false;
        }

        if (password !== password2) {
            alert("비밀번호가 일치하지 않습니다.");
            $("input[name=password2]").val('').focus();
            return false;
        }

        // 형식 검사가 모두 완료되면, 이 부분에서 폼 제출 처리를 해주세요.
       this.submit();
    });
});
</script>
</head>
<body>
	<div data="">
		<div data="" class="container">
			<section data="">
				<form data= "" action="forgotpwd_resultProcess" method="post">
					<h1 data="">에브리타임 비밀번호 변경</h1>
					<div data="" class="description">
						<p data="">※ 영문, 숫자, 특문이 각각 1개 이상 조합된 8~20자</p>
					</div>
					<input data="" type="password" name ="password" maxlength="20"
						placeholder="새 비밀번호">
				
				  <input data=""
						type="password" maxlength="20" name="password2" placeholder="새 비밀번호 확인"> 
				
						<input data="" type="submit" value="비밀번호 변경">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
				</form>
			</section>
		</div>
	</div>

</body>
</html>