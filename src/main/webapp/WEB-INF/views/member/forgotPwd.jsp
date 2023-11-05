<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>비밀번호 찾기 - 애니타임</title>


<link data-vue-meta="ssr" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/login/0a14fb2000808afce700.css">
<link data-vue-meta="ssr" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/login/79230e2021cfc26431e8.css">
	
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function(){
	$('form').on('submit', function(event){
	  event.preventDefault();
	  
	  var id = $('#login_id').val();
	  
	  if(id.trim()===""){
		  alert("가입된 아이디를 입력해주세요.");
	  }else{
		  this.submit();
	  }
	  
	});
});
</script>	
</head>
<body>
	<div data="">
		<div data="" class="container">
			<section data="">
				<div data="" class="menu">
					<a data="" href="forgotid"> <span
						data="">아이디 찾기</span></a> <a data=""
						class="active"> <span data="">비밀번호 찾기</span></a>
				</div>
				<form data="" action="forgotpwd_mailcheck" method="post">
					<input data="" type="text" name="login_id" id ="login_id"
						placeholder="가입된 아이디" autocomplete="off"> 
						<input data="" type="submit" value="이메일 본인 인증하기">
						<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}">
				</form>
			</section>
		</div>
	</div>
	
</body>
</html>