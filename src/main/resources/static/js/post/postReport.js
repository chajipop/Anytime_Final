$(document).ready(function () {

	var comment_id = null;
	
	//닫기
	$('a.close').click(function() {
		$('#abuseForm').css('display', 'none');
		$('div.modalwrap').remove();
	});
	
	//신고
	$(document).on("click", "li.abuse", function(){
	    $('form#abuseForm').css('display', 'block');
	    $('form#abuseForm').before('<div class="modalwrap"></div>');
	    const article = event.target.closest('article');
	    comment_id = article.getAttribute('id');
	});
		
	//신고하기
	$("#abuseForm li a").click(function(){
		var urlParams = new URLSearchParams(window.location.search);
	    var post_id = urlParams.get('post_id');
	    
	    if(comment_id > 0){
	    	post_id = 0;
	    }else{
	    	comment_id = 0;
	    }
		var reportnum = $(this).data("reason");
		
		reportAjax(post_id,comment_id,reportnum);
	});
	
	function reportAjax(post_id, comment_id, reportnum){
		console.log("(ajax) post_id = " + post_id + "/ comment_id = " + comment_id + " / reportnum = " + reportnum)
		$.ajax({
			url: "../report",
			data:{
				"post_id": post_id,
				"comment_id": comment_id,
				"reportnum": reportnum
			},
			success: function (reportResult){
				if(reportResult == 1){
					alert("신고가 완료되었습니다.");
					location.reload();	
				} else if(reportResult == 2) {
					alert("이미 신고한 항목입니다.");
				} else {
					alert("신고 오류가 발생했습니다.")
				}
			}
		
		})
	}

});
