$(document).ready(function () {

	$(document).on("click", ".scrap.add", function(){
		scrapAjax()
	});

	
	function scrapAjax(){
		
		var urlParams = new URLSearchParams(window.location.search);
		var post_id = urlParams.get('post_id');
		// 보안 토큰
		const token = $("meta[name='_csrf']").attr("content");
		const header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			url: "../scrap",
			type: "POST",
			data:{
				"post_id": post_id,
			},
			beforeSend: function(xhr)
			{	// 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
				xhr.setRequestHeader(header, token);
			},
			success: function (scrapResult){
				if(scrapResult == 1){
					alert("스크랩이 완료 되었습니다.");
					location.reload();
				} else if(scrapResult == 2) {
					alert("스크랩이 취소 되었습니다.");
					location.reload();
				} else {
					alert("스크랩중 오류가 발생했습니다.");
				}
			}	
		})
	}

});
