$(document).ready(function () {
	
	const page = 1;
	
	myScrapListAjax(page)
	
	function myScrapListAjax(page){
		
		// 보안 토큰
		const token = $("meta[name='_csrf']").attr("content");
		const header = $("meta[name='_csrf_header']").attr("content");
		
		$.ajax({
			url: "scraplist",
			data:{
				page: page,
			},
			dataType: "json",
			success: function (response){
			console.log(response);
			
			var pagetag = $(".pagination.justify-content-center");	
			var scrapArticles = $(".wrap.articles");
			
			scrapArticles.children().not(".clearBothOnly, .center-block, .pagination.justify-content-center").remove();
			
			pagetag.empty();
			
			
			let scrapList = "";
			
			$.each(response.list, function (index, s) {	
				
				scrapList += '<article class="list">';
				scrapList += '<a class="article" href="post/detail?post_id='+ s.post_ID + '">';
				scrapList += '<div class="desc">';
				scrapList += '<p class="boardname" href="post/list?board_id=' + s.board_ID + '">' + s.boardname + '</p>';
				scrapList += '<h2 class="medium bold">' + s.subject + '</h2>';
				scrapList += '<p class="medium">' + s.content + '</p>';
				scrapList += '<div class="info">';
				scrapList += '<ul class="status">';
				scrapList += '<li title="공감" class="vote">' + s.like_COUNT + '</li>';
				scrapList += '<li title="댓글" class="comment">' + s.comment_COUNT + '</li>';
				scrapList += '</ul>';
				scrapList += '<time class="small">' + s.formattedPOST_DATE + '</time>';
				scrapList += '<h3 class="small">' + s.nickname + '</h3>';
				scrapList += '</div><hr></div></a><div class="comments"></div></article>';
				
			});
			
			if(response.list.length == 0){
				scrapList += '<article class="list empty">';
				scrapList += '스크랩한 글이 없습니다.</article>';
			}
			
			scrapArticles.prepend(scrapList);
			
			let pageination = "";
			var a = 0;
			if(response.page <= 1) {
				pageination += '<li class="page-item hidden"><a class="page-link gray">이전&nbsp;&nbsp;</a></li>';
			} else if(response.page > 1) {
				pageination += '<li class="page-item">';
				pageination += '<a href="#" data-page="'+ (page-1) +'" class="page-link">&nbsp;&nbsp;&nbsp;이전</a></li>';
			}
			
			for(var a = response.start; a <= response.end; a++) {
				
				if(a == response.page) {
					pageination += '<li class="page-item active"><a class="page-link">' + a + '</a>';
				}else if(a != response.page) {
					pageination += '<li class="page-item"><a href="#" data-page="'+ a +'" class="page-link">' + a + '</a></li>';
				}
			}
			
			if(response.page >= response.max) {
				pageination += '<li class="page-item hidden"><a class="page-link hidden">다음&nbsp;&nbsp;&nbsp;</a></li>';
			} else if (response.page < response.max) {
				pageination += '<li class="page-item"><a href="#" data-page="'+ (page+1) +'" class="page-link">다음&nbsp;&nbsp;&nbsp;</a></li>';
			}
			
			pagetag.append(pageination);
			console.log("ajax 실행 완료");		
			}		
		})
		
	}
	
	$('.pagination.justify-content-center').on('click', '.page-link', function(e) {
	    e.preventDefault();
	   
	   	var data_page = $(this).data('page');
   		
    	myScrapListAjax(data_page);
    });
    


});
