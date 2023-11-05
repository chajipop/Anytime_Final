$(document).ready(function () {

sendAjaxRequest();

function sendAjaxRequest(school_id) {
	
	const school_id2 = $("#school_id").val();
	var contextPath = window.location.origin + '/anytime';
	
	  $.ajax({
	    url: contextPath + "/submenulist",
	    data: {
	      school_id: school_id2,
	    },
	    dataType: "json", // JSON 데이터로 응답을 예상함
	    success: function (responseData) {
			// 서버로부터의 응답 데이터는 response 매개변수에 JSON 객체로 저장됨
		    console.log("Ajax 요청 성공: ", responseData);

		  	// JSON 데이터를 JavaScript 객체로 파싱
		    var boardlist = responseData;
		
		    // submenu 요소 선택
		    var submenu = $("#submenu");
		    var wrap = $("#wrap");
		 
		    // submenu 초기화 (기존 내용 삭제)
		    submenu.empty();
		    submenu.append('<div class="wrap">');
			
		    // type (1~4)를 나눠서 출력
		    for (var type = 1; type <= 4; type++) {
		    	// type을 나누는 구분
		    	submenu.find('.wrap').append('<div class="divider"></div>');
		    	// 3개씩 출력을 위해 그룹을 나누는 구분
		    	submenu.find('.wrap').append('<div class="group"><ul>');
		    	// 게시판 카운터 초기화
		    	var itemCount = 0;
		    	
		    	// boardlist를 반복하고 메뉴 생성
		    	for (var i = 0; i < boardlist.length; i++){
		    		var menu = boardlist[i];
		    		
		    		// type이 일치하고 status가 1인 경우
		    		if (menu.type == type){
		                
		                // itemConut가 3의 배수일 때 ul 요소 닫기
		                if (itemCount % 3 == 0 && itemCount != 0){
		                	submenu.find('.group:last').append('</ul></div>'); // 그룹 닫기
		                    submenu.find('.wrap').append('<div class="group"><ul>');                
		    		}
		    			// boardlist 출력
		    			submenu.find('.group:last ul').append('<li><a href="'+ contextPath +'/post/list?board_id=' + menu.board_ID + '">' + menu.name + '</a></li>');
		                itemCount++;
		    	}	    
		    		submenu.append('</ul></div>'); // 그룹 닫기
		    }
		    	submenu.append('<hr></div>');	// 타입 닫기
		    }
	    	submenu.find('.wrap').append('<div class="divider"></div>');	// 마지막 닫기
		    
		    
		    console.log("sendAjaxRequest 실행 완료");
	    }
	  		
	  });
	  
	}


});