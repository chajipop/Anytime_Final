<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>애니타임</title>
	<jsp:include page="../common/header.jsp" />
	
<%-- <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.partial.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/container.modal.css"> --%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/message/message.css">
<link href="/favicon.ico" rel="shortcut icon">
<script src="https://code.jquery.com/jquery-latest.js"></script>
</head>

<body>
  
<div id="container" class="message" data-box-id="0">
    <aside class="none">
      <div class="title">
        <a class="hamburger"></a>
        <h1>쪽지함</h1>
      </div>
    </aside>
    <div class="messageboxes">
      <h2>쪽지함</h2>
      <div class="items" id="laft">
      			

        <div class="loading" style="display: none;"></div>
      </div>
    </div>
    
    <div class="messages visible">
    	<div class="title">
    		<a class="back">뒤로</a>
    			<h2></h2>
    				<a class="send" data-modal="messageSend">쪽지 보내기</a>
    				<a class="refresh">새로고침</a>
    				<!-- <a class="more">더보기</a> -->
    	</div>
	    <div class="items" id="right">

                <div class="item">
                <p class="messageempty">쪽지가 없습니다.</p>
               	</div>
    	</div>
    </div>
    
    <form id="messageSend" class="modal" style="margin-left: -200px; margin-top: -92.5px; display: none;">
      <a title="닫기" class="close"></a>
      <h3>쪽지 보내기</h3>
      <p>
        <textarea name="message" class="text" placeholder="내용을 입력해주세요."></textarea>
      </p>
      <input type="submit" value="전송" class="button">
    </form>
    
    <form id="messageMore" class="modal">
      <a title="닫기" class="close"></a>
      <h3>쪽지함</h3>
      <ul>
        <li><a data-menu="removeAll">전체 삭제</a></li>
        <li><a data-menu="block">차단</a></li>
        <li><a data-menu="reportSpam">스팸 신고</a></li>
      </ul>
    </form>
    
  </div>

  
</body>

<script>

$(document).ready(function() {
		
		getMessageLastListAjax();
		
		//쪽지폼
		$("a.send").click(function(){
			$('form#messageSend').css('display', 'block');
			$('form#messageSend').before('<div class="modalwrap"></div>');
			var messageall_id = $("#laft > a.active").data("id");
		})
		
		
		
		//쪽지보내기
		$("#messageSend").submit(function(){
			sendMessage();
		})
		
		//새로고침
		$("a.refresh").click(function(){
			location.reload();
		})
		
		//모달 닫기
		$('a.close').click(function() {
			$('#messageSend').css('display', 'none');
			$('div.modalwrap').remove();
		});
		
});

$(document).on("click", "a.item", function (event) {
	
	$("a.item.active").removeClass("active");
	
	var id = $(this).data("id");
	
	$(this).removeClass("item").addClass("item active");
	
	getMessageListAjax(id);
	
});

function getMessageListAjax(id) {
    $.ajax({
        url: "${pageContext.request.contextPath}/messagelist",
        data: {
        	messageallid: id
        },
        dataType: "json",
        success: function (messageList) {
        	console.log("Ajax 요청 성공: ", messageList);
			
        	//사용자와 주고받은 쪽지 리스트 출력 칸
            var messageListItems = $("#right");
		
            let messageItem = "";
                        
            if(messageList != null){
            
            $("#right").empty();
            	
            $.each(messageList, function (index, ms) {
                messageItem += '<div class="item">';
                messageItem += '<time>' + ms.message_DATE + '</time>';
                	if(ms.direction == 2){
                		messageItem += '<p class="type type2">보낸 쪽지</p>';
                	}else if(ms.direction == 1){
                		messageItem += '<p class="type type1">받은 쪽지</p>';
                	}else if(ms.direction == 3){
                		messageItem += '<p class="type type0">안내</p>';
                	}
                messageItem += '<p class="text">' + ms.content + '</p>';
                messageItem += '</div>';

                messageListItems.append(messageItem);
                
                messageItem = "";
                
                if (index === 0) {
                	const newname = document.querySelector("a.item.active h3");
                	const oldname = document.querySelector("#container > div.messages.visible > div.title > h2");

                	// 새로운 값으로 변경합니다.
                	oldname.textContent = newname.textContent;
                }
            });
            
            } else {
            	
            }
            
        }
    });
}

function getMessageLastListAjax() {
    $.ajax({
        url: "${pageContext.request.contextPath}/messagelastlist",
        dataType: "json",
        success: function (messageLastList) {
        	console.log("Ajax 요청 성공: ", messageLastList);
        	
            //쪽지 리스트 선택
            var messageLastListItems = $("#laft");
			var context = "${pageContext.request.contextPath}"
            let messageItem = "";
            
            if(messageLastList != null){
            
            $.each(messageLastList, function (index, ms) {
                  	
				messageItem += '<a class="item" data-id="' + ms.messageall_ID + '">';
                messageItem += '<time>' + ms.message_DATE + '</time>';
                messageItem += '<h3>' + ms.nickname + '</h3>';
                messageItem += '<p class="text">' + ms.content + '</p>';
                messageItem += '</a>';

                messageLastListItems.append(messageItem);
                
                messageItem = "";
                
                // 처음 요소에 "active" 클래스 추가 및 data-id 가져와서 처리
                if (index === 0) {
                    var firstItem = messageLastListItems.find('a.item:first');
                    firstItem.addClass('active');
                    var id = firstItem.data('id');
                    getMessageListAjax(id);
                }
                
            });
            
            
            
            }
            
        }
    });
}

function sendMessage(){
	var messageall_id = $("#laft > a.active").data("id");
	var content = document.querySelector('#messageSend textarea').value;
	/* var text = $("textarea[name='message']").val(); */
	
	$.ajax({
		url: "${pageContext.request.contextPath}/sendmessage",
		data: {
			"messageall_id": messageall_id,
			"content": content,
		},
		dataType: "json",
		success: function (sendResult){
			if(sendResult == 1){
				alert("쪽지가 송신되었습니다.");
				location.reload();
			}else{
				alert("쪽지 송신에 실패했습니다.")
			}
		}
	})
	
	
}
</script>

</html>