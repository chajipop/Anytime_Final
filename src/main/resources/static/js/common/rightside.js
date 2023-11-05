$(document).ready(function () {


	mainRightSideAjax();
	
	function mainRightSideAjax(){

	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	
	const school_id = $("#school_id").val();
	const login_id = $("#login_id").val();
	var contextPath = window.location.origin + '/anytime';
	
	$.ajax({
		url: contextPath + "/rightsidelist_sample",
		data: {
			school_id: school_id,
			login_id: login_id
		},
		dataType: "json",
		success: function (response){
		console.log(response);
		
		var hotlist = $(".card.rightside");
		
		hotlist.empty();
		
		let rightsideList = "";
		
		rightsideList += '<div class="board">';
		rightsideList += '<h3><a href="' + contextPath + '/hotpost">HOT 게시물<span>더 보기</span></a></h3>';
		
		
		if(response.school_check == 1){
		
			$.each(response.hotlist, function (index, hot) {	
				if(index < 4){
				rightsideList += '<a class="list" href="' + contextPath + '/post/detail?post_id='+ hot.post_ID +'">';
				rightsideList += '<time>' + hot.formattedPOST_DATE +'</time>';
				rightsideList += '<p>' + hot.subject + '</p>';
				rightsideList += '</a>';
				}
			});
			
		if (response.hotlist.length === 0) {
		    rightsideList += '<a class="list">HOT 게시물이 없습니다</a>';
		} 
		
		} else if(response.login_check == 1){
			rightsideList += '<a class="list">로그인 후 이용 가능합니다.</a>';
			
		} else if(response.school_check == 0){
			rightsideList += '<a class="list">학교인증 후 이용 가능합니다.</a>';
		}
		
		rightsideList += '<h3 style="border-top: 1px solid #d6d6d6;"><a href="' + contextPath + '/bestpost">BEST 게시판<span>더 보기</span></a></h3>';

		if(response.school_check == 1){
		

			$.each(response.bestlist, function (index, hot) {	
				if(index < 4){
				rightsideList += '<a class="list" href="' + contextPath + '/post/detail?post_id='+ hot.post_ID +'">';
				rightsideList += '<time>' + hot.formattedPOST_DATE +'</time>';
				rightsideList += '<p>' + hot.subject + '</p>';
				rightsideList += '</a>';
				}
			});
		
			if (response.bestlist.length === 0) {
			    rightsideList += '<a class="list">BEST 게시물이 없습니다</a>';
			}
		
		} else if(response.login_check == 1){
			rightsideList += '<a class="list">로그인 후 이용 가능합니다.</a>';
			
		} else if(response.school_check == 0){
			rightsideList += '<a class="list">학교인증 후 이용 가능합니다.</a>';
		}
		
		rightsideList += '</div>';
		

		hotlist.append(rightsideList);			
		console.log("mainRightSideAjax 실행 완료");
		
		}
		
	});
	
	}

});