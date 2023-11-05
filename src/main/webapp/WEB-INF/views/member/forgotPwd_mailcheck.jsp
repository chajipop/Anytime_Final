<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">		
	
<html>
<head>
<title>애니타임_이메일 본인 인증</title>
<script src="<%=request.getContextPath()%>/js/jquery-3.7.0.js"></script>
<link data-vue-meta="ssr" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/login/mailcheck.css" rel="stylesheet">
	
<script>

let checkemail = false;

$(document).ready(function(){
	   let token = $("meta[name='_csrf']").attr("content");
	   let header = $("meta[name='_csrf_header']").attr("content");
	//이메일 형식체크
	$("input[name=email]").on(
			'keyup',
			function() {
				//[A-Za-z0-9_]와 동일한 것이 \w 입니다.
				//+는 1회 이상 반복을 의미하고 {1,}와 동일합니다.
				//\w+는 [A-Za-z0-9_]를 1개이상 사용하라는 의미입니다.
				const pattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/; 
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
	
	// 타이머 변수를 설정해준다.		
	var timer;		
			
	// 이메일을 입력후 발송 버튼을 클릭했을때 발생		
    $("#emailsend").on('click', function(){
    	   alert('인증번호가 발송되었습니다.'); 
      var email = $("#email").val();
    // 입력받은 이메일을 컨트롤러의 emailsend로 보내줌
    $.ajax({
       url: "../member/forgotpwd_mailsend",
       method:"post",
       data: { "email": email }, 
       beforeSend: function(xhr)
       { // 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
         xhr.setRequestHeader(header, token);
       },
       success: function (resp) {
    	  $("#RandNum").val(resp); // 서버로부터 받은 난수 저장
        
    	  
          // 이메일 발송 버튼을 비활성화하고 타이머를 시작합니다.
          $("#emailsend").prop('disabled', true);
          $("#email_message").css('color', '#e54787').html(
      	"&nbsp;&nbsp;&nbsp;1분 후 다시 발송가능합니다.");   
         
          
         //타이머를 시작합니다. 60초(60000ms)후에 버튼을 다시 활성하고 텍스트로 변경합니다.
          clearTimeout(timer);
          timer = setTimeout(function(){
          	$("#emailsend").prop('disabled', false);
          	 $("#email_message").css('color', '#624cff').html(
          		"&nbsp;&nbsp;&nbsp;이메일발송 가능합니다.");
          }, 60000);
      },
          error:function(jqXHR, textStatus, errorThrown) {
          // 이메일 전송이 실패했을 경우 추가적인 처리를 여기에서 할 수 있습니다.
          console.log(jqXHR);
		  console.log(textStatus);
		  console.log(errorThrown);
          alert('인증번호 발송에 실패했습니다.');
      }
    });    
  });//emailsend keyup
  
    $('form[name=emailform]').submit(function(event){
		  
    	var checknumValue = $("input[name=checknum]").val().trim();
	    if (checkemail == false){
	    	alert("이메일 본인인증을 완료해주세요");	
	    	return false;
	    }
    	if (checknumValue === '') {
	        alert("인증번호를 입력해주세요.");
	        return false;
	    }
    	
    }); //submit end  
  
  
}); //ready

function Verifyemail(){
    var verifynum = $("input[name=checknum]").val();

    if(verifynum == $("#RandNum").val()){
       alert("이메일 인증에 성공하셨습니다.");
       checkemail = true;
    }else{
       alert("이메일 인증에 실패하셨습니다.");
       checkemail = false;
    }
 }
  

</script>

<style>
p {
	margin-top: 8px;
	margin-left: 2px;
	line-height: 20px;
	color: #737373;
	font-size: 14px;
}
</style>
</head>
<body style="">
	<form name="emailform" action="forgotpwd_result" method="post"
		id="container" data-adagreement="1" data-redirecturl="/">

		<h2>이메일 본인 인증</h2>
		<p>이메일을 입력하세요.</p>
		<div class="input">
			<div class="label">
				<label>이메일</label>
			</div>
			<input type="email" name="email" id="email" placeholder="이메일"
				autocomplete="off"> <input type="button" id="emailsend"
				value="발송">
			<div class="caution"></div>
			<span id="email_message" style="font-size: 12px"></span>
		</div>

		<div class="input">
			<div class="label">
				<label>인증번호</label>
			</div>
			<input type="text" name="checknum" maxlength="10" autocomplete="off" placeholder="인증번호">
			<input type="button" id="checknum" value="확인" onclick="Verifyemail()">
			<div class="caution"></div>

			<%-- 응답받은 난수를 받아오기 위해 생성한 히든 인풋 --%>
			<input type="hidden" id="RandNum" name="RandNum" /> <span
				id="nickname_message" style="font-size: 12px"></span>
		</div>

		<input type="submit" value="비밀번호 변경하기">
			<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}">
	</form>
	
</body>
</html>