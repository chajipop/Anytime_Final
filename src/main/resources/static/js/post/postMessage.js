$(document).ready(function () {

	var comment_id = null;
	
	//닫기
	$('a.close').click(function() {
		$('#messageSend').css('display', 'none');
		$('div.modalwrap').remove();
	});
		
	//쪽지
	$(document).on("click", "li.messagesend", function(){
	    $('form#messageSend').css('display', 'block');
	    $('form#messageSend').before('<div class="modalwrap"></div>');
	    const article = event.target.closest('article');
	    comment_id = article.getAttribute('id');
	});
	
	//쪽지 보내기
	$("#messageSend").submit(function(e) {
	    e.preventDefault();
	    var urlParams = new URLSearchParams(window.location.search);
	    var post_id = urlParams.get('post_id');

	    if(comment_id > 0){
	    	post_id = 0;
	    }else{
	    	comment_id = 0;
	    }
	    sendMessageAjax(post_id,comment_id);
	});
	
    function sendMessageAjax(post_id, comment_id){
   		var content = document.querySelector('#messageSend textarea').value;
   		$.ajax({
   			url: "../sendmessage",
   			data: {
   				"post_id": post_id,
   				"comment_id": comment_id,
   				"content": content
   			},
   			success: function (sendResult){
   				if(sendResult == 1){
   					alert("쪽지가 송신되었습니다.");
   					location.reload();	
   				}else{
   					alert("쪽지 송신에 실패했습니다.")
   				}
   			}
   		})
	};

});
