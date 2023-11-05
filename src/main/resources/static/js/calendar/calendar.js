	
	//캘린더 고유 번호
	var idcheck;

    // YYYY-MM-DD W HH:mm:ss 형식으로 날씨 변환 ( 일정 보기 )
	const formatDate = (date) => {
		const year = date.getFullYear();
		const month = (date.getMonth() + 1).toString().padStart(2, '0');
		const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
  		const dayOfWeek = daysOfWeek[date.getDay()];
		const day = date.getDate().toString().padStart(2, '0');
		const hours = date.getHours().toString().padStart(2, '0');
		const minutes = date.getMinutes().toString().padStart(2, '0');
		const seconds = date.getSeconds().toString().padStart(2, '0');
	  
	  return `${year}-${month}-${day} (${dayOfWeek}) ${hours}:${minutes}`;
	};

	// YYYY-MM-DD 형식으로 날씨 변환 ( 일정 보기의 종일 )
	const formatDateAllDayStart = (date) => {
	  	const year = date.getFullYear();
	  	const month = (date.getMonth() + 1).toString().padStart(2, '0');
	  	const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
		const dayOfWeek = daysOfWeek[date.getDay()];
	  	const day = date.getDate().toString().padStart(2, '0');
	  
	  return `${year}-${month}-${day} (${dayOfWeek})`;
	};
	
	// YYYY-MM-DD 형식으로 날씨 변환 ( 일정 보기의 종일 )
	const formatDateAllDayEnd = (date) => {

		//하루를 빼고 포맷 ( end 일자가 + 1 되어 있어서 강제로 - 1 )
		date.setDate(date.getDate() - 1);
		
	  	const year = date.getFullYear();
	  	const month = (date.getMonth() + 1).toString().padStart(2, '0');
	  	const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
		const dayOfWeek = daysOfWeek[date.getDay()];
	  	const day = date.getDate().toString().padStart(2, '0');
	  
	  return `${year}-${month}-${day} (${dayOfWeek})`;
	};
	
 
 	// YYYY-MM-DDTHH:mm:ss 형식으로 날씨 변환 ( 일정 추가 )
 	const formatDate2 = (date) => {
		const year = date.getFullYear();
		const month = (date.getMonth() + 1).toString().padStart(2, '0');
		const day = date.getDate().toString().padStart(2, '0');
		const hours = date.getHours().toString().padStart(2, '0');
		const minutes = date.getMinutes().toString().padStart(2, '0');
		const seconds = date.getSeconds().toString().padStart(2, '0');
	  
	  return `${year}-${month}-${day}T${hours}:${minutes}`;
	};
	
	// YYYY-MM-DD 형식으로 날씨 변환 ( 일정 추가의 종일 )
	const formatDate3 = (date) => {
	  	const year = date.getFullYear();
	  	const month = (date.getMonth() + 1).toString().padStart(2, '0');
	  	const day = date.getDate().toString().padStart(2, '0');
	  
	  return `${year}-${month}-${day}`;
	};
	
	// YYYY-MM-DD 형식으로 날씨 변환 ( 업데이트에서 일 - 1 )
	const formatDateUpdateEnd = (date) => {
		
		//하루를 빼고 포맷
		date.setDate(date.getDate() - 1);
		
	  	const year = date.getFullYear();
	  	const month = (date.getMonth() + 1).toString().padStart(2, '0');
	  	const day = date.getDate().toString().padStart(2, '0');
	  
	  return `${year}-${month}-${day}`;
	};
	
	const format00 = (date) => {
		const hours = date.getHours().toString().padStart(2, '0');
		const minutes = date.getMinutes().toString().padStart(2, '0');

		
		return `${hours}:${minutes}`;
	};
	

document.addEventListener('DOMContentLoaded', function() {
	
	
	var dayTopElements = document.querySelectorAll(".fc-daygrid-day-top");
    
    dayTopElements.forEach(function(element) {
        element.addEventListener("click", function(event) {
            // 클릭 이벤트를 막음
            event.preventDefault();
            event.stopPropagation();
        });
    });
	
	
	
  	var calendarEl = document.getElementById('calendar');
  
  	var calendar2El = document.getElementById('calendar2');
        
	var calendar2 = new FullCalendar.Calendar(calendar2El, {
		headerToolbar: {
			left: 'prevYear,nextYear',
			center: 'title',
		},
		locale: 'ko',
		selectable: true,
	select: function(arg){
	console.log(arg);
	}
	});
    
  
  var calendar = new FullCalendar.Calendar(calendarEl, {
	  customButtons: {
		    addCalendar: {
		      text: '일정 등록',
		      click: function() {
		      		
		      		//폼 불러오기
		      		$("form#calendarModal").show();
		      		$('form#calendarModal').css('display', 'block');
					$('body').before('<div class="modalwrap"></div>');
					
					//초기화
					$('#all_check').val("");
					$('#all_check2').val("");
		      }
		    }
		  },
	headerToolbar: {
		left: 'prevYear,prev,next,nextYear today',
		center: 'title',
		right: 'addCalendar',
	},
    titleFormat: {
    	year: 'numeric',
		hour12: false,
		month: 'long',	
    },
    initialView: 'dayGridMonth',
    displayEventEnd: true,					//시작 ~ 종료 일 까지 표시
    editable: true,							//일정 수정 허용
    dayMaxEvents: 3,						//하루 최대 일정
    locale: 'ko',							//지역 한국 설정
    droppable: true,						//일정 drop 수정 허용
    selectable: true,						//일정 선택
    eventResize: true,
    eventResizableFromStart: true,			//이틀 이상 일정 늘이기 기능
	defaultTimedEventDuration: '00:00:00',	//start ~ end 기본값
    events: function(info, successCallback, failureCallback) {
      $.ajax({
        url: "calendarlist",
        dataType: "json",
        success: function(Result) {
        var events = [];
                  
          if(Result != null){
	          	$.each(Result, function(index, data) {	
	          	
	          	data.end = new Date(data.end);
	          	 
	          	//종일 일때, 1일 추가 표시 ( allDay 일때 하루가 모자람 )
	          	if(data.allday){
	          		//data.end.setDate(data.end.getDate() + 1);	
	          	}
	          	
				//자정 일때, 1초 추가 표시 ( end 가 다음날의 00:00:00 일때 익일로 넘어가지 않음 )
          		var test00 = format00(data.end);
	          	if(test00 == '00:00'){
	          		data.end.setSeconds(data.end.getSeconds() + 1);
	          	}
        			          
	          	events.push({
	        		id: data.id,
	            	title: data.title,
	            	start: data.start,
	            	end: data.end,
	            	description: data.description,
	            	borderColor: data.color,
	            	eventBorderColor: 'white',
	            	backgroundColor: data.color,
	            	allDay: data.allday
	          	});
	          	         		
	          });
          }
          console.log(events)
          successCallback(events);
        },
        error: function(Result) {
          console.log("에러가 발생했습니다.");
          failureCallback(Result);
        }
      });
    },
    eventResize: function(info) {
    	const eventStartDate = new Date(info.event.start);
    	var formattedStartDate = formatDate2(eventStartDate);
    	if(info.event.end != null) {
			const eventEndDate = new Date(info.event.end);
			var formattedEndDate = formatDate2(eventEndDate);	
    	}
		
    	var data = {
    		calendar_id: info.event.id,
    		start: formattedStartDate,
    		end: formattedEndDate
    	}
    	
    	calendarDropUpdateAjax(data);  	
    },
    eventDrop: function(info){
    	console.log("일정 이동 id" + info.event.id);
    	console.log("일정 이동 스타트" + info.event.start);
    	console.log("일정 이동 엔드" + info.event.end);
    	
    	console.log(info);
    	
    	const eventStartDate = new Date(info.event.start);
    	var formattedStartDate = formatDate2(eventStartDate);
    	if(info.event.end != null) {
			const eventEndDate = new Date(info.event.end);
			var formattedEndDate = formatDate2(eventEndDate);	
    	}
		
    	var data = {
    		calendar_id: info.event.id,
    		start: formattedStartDate,
    		end: formattedEndDate
    	}
    	
    	calendarDropUpdateAjax(data);
    },
    
    select: function(arg){
		console.log(arg);
		
	    // 종료 날짜 - 1일 ( end 가 자동으로 + 1일 추가되어 강제로 - 1일 )
	    arg.end.setDate(arg.end.getDate() - 1);

		
		const selectStart = new Date(arg.start);
		const selectEnd = new Date(arg.end);
		var formattedselectStart = formatDate3(selectStart);
		var formattedselectEnd = formatDate3(selectEnd);
				
	    $("form#calendarModal").show();
  		$('form#calendarModal').css('display', 'block');
		$('form#calendarModal').before('<div class="modalwrap"></div>');
		
		$('input[name="allday"]').prop('checked', true);
		$("#all_check").attr("type", "data");
		$("#all_check2").attr("type", "data");
		$('#all_check').val(formattedselectStart);
		$('#all_check2').val(formattedselectEnd);
		
    },
    
    eventClick: function(info) {
	    var event = info.event;

	    console.log(event);
	    console.log("클릭한 일정 시작시간 :" + event.start);
	    console.log("클릭한 일정 종료시간 :" + event.end);
	    
		/* * * * * * * * * * * * * * * * * 디테일 * * * * * * * * * * * * * * * * */	    
				
		$("form#calendarDetail").show();
  		$('form#calendarDetail').css('display', 'block');
		$('form#calendarDetail').before('<div class="modalwrap"></div>');

		//디테일 모달창 제목 변경
		const calendarDetailTitle = document.getElementById('calendar_detail_title');
		calendarDetailTitle.textContent = event.title;

		//디테일 모달창 날짜 변경
		const calendarDetailDate = document.getElementById('calendar_detail_date');

		const eventStartDate = new Date(event.start);
		const eventEndDate = new Date(event.end);	
		
		if(event.allDay == false) {
			var formattedStartDate = formatDate(eventStartDate);
			var formattedEndDate = formatDate(eventEndDate);				
		} else {
			var formattedStartDate = formatDateAllDayStart(eventStartDate);
			var formattedEndDate = formatDateAllDayEnd(eventEndDate);
		}
		
			
		if(event.end != null){
			calendarDetailDate.textContent = formattedStartDate + " ~ " + formattedEndDate;
		} else {
			calendarDetailDate.textContent = formattedStartDate;
		}
		
		console.log('디테일에서의 시작 시간:', formattedStartDate);
		console.log('디테일에서의 끝 시간:', formattedEndDate);
		
		//디테일 설명 변경
		const calendarDetailDescription = document.getElementById('calendar_detail_description');
		calendarDetailDescription.textContent = event.extendedProps.description;
		
		//디테일 색상 변경
		const calendarDetailSpanStyle = document.querySelector('#calendar_detail_title');
		calendarDetailSpanStyle.style.borderLeft = '20px solid' + event.backgroundColor;
		
		
		/* * * * * * * * * * * * * * * * * 업데이트 * * * * * * * * * * * * * * * * */
		
		//수정 모달창 제목 변경
		const calendarTitleUpdate = document.getElementById('calendar_title_update');
		calendarTitleUpdate.value = event.title;

		//수정 모달창 날짜 변경
		const calendarDateUpdate = document.getElementById('all_check_update');
		const calendarDateUpdate2 = document.getElementById('all_check2_update2');
		calendarDateUpdate.value = event.start;
		calendarDateUpdate2.value = event.end;
		
		const allDayCheck = document.getElementById('allday_check_update');
		
		const eventStartDateUpdate = new Date(event.start);
		const eventEndDateUpdate = new Date(event.end);	
		
		if(event.allDay === false) {
			allDayCheck.checked = false;
			calendarDateUpdate.type = 'datetime-local';
			calendarDateUpdate2.type = 'datetime-local';
			var formattedStartDateUpdate = formatDate2(eventStartDateUpdate);
			var formattedEndDateUpdate = formatDate2(eventEndDateUpdate);				
		} else {
			allDayCheck.checked = true;
			calendarDateUpdate.type = 'date';
			calendarDateUpdate2.type = 'date';
			var formattedStartDateUpdate = formatDate3(eventStartDateUpdate);
			var formattedEndDateUpdate = formatDateUpdateEnd(eventEndDateUpdate);
		}
		
		
		if(event.end != null){
			calendarDateUpdate.value = formattedStartDateUpdate;
			calendarDateUpdate2.value = formattedEndDateUpdate;
		} else {
			calendarDateUpdate.value = formattedStartDateUpdate;
		}
	
		console.log('수정에서의 시작 시간:', formattedStartDateUpdate);
		console.log('수정에서의 끝 시간:', formattedEndDateUpdate);
		
		//수정 모달 설명 변경
		const calendarUpdateDescription = document.getElementById('calendar_description_update');
		calendarUpdateDescription.textContent = event.extendedProps.description;
		
		//수정 색상 변경
		// <input> 요소의 값을 변경하고 jscolor 업데이트
		const colorInput = document.getElementById('color_update');
		colorInput.value = event.backgroundColor; // 원하는 색상으로 변경
		colorInput.jscolor.fromString(colorInput.value); // jscolor 업데이트
		
		//캘린더 고유 번호
		idcheck = event.id;
		
		//수정 폼 연결
		$("#calendar_update").click(function(event) {
			event.preventDefault();	  
			$('#calendarDetail').css('display', 'none');
			$("form#calendarUpdateModal").show();			
		});
		
		
		/* * * * * * * * * * * * * * * * * 삭제 * * * * * * * * * * * * * * * * */
		
		//삭제 실행
		$("#calendar_delete").click(function() {
			const test = false;	//confirm 중복 발생 방지
			if(!test){
				if(confirm("일정을 삭제하시겠습니까?")) {
					calendarDeleteAjax(idcheck);
				} else {
					console.log("삭제 취소");
				}
				test = true;
			}
		});
		
		
		
     },
  }); // calendar 끝
  
  
  calendar.render();
  calendar2.render(); 	
  	  	
	// 종일 선택 여부 (등록 폼)
	document.getElementById('allday_check').addEventListener('change', function() {
		const datetimeField = document.getElementById('all_check'); // 날짜 및 시간 입력 필드
		const datetimeField2 = document.getElementById('all_check2'); // 날짜 및 시간 입력 필드
	
		// 기존 값 저장
		var datetimeFieldValue = datetimeField.value;
		var datetimeField2Value = datetimeField2.value;
		
		// 체크박스가 체크된 경우 datetime 입력 필드를 날짜 선택 모드로 변경
		if (this.checked) {
			datetimeField.type = 'date';
			datetimeField2.type = 'date';
		
		    // 이전 값 복원
		 	datetimeField.value = datetimeField.value.split("T")[0];
		  	datetimeField2.value = datetimeField2.value.split("T")[0];
		} else {
			datetimeField.type = 'datetime-local';
			datetimeField2.type = 'datetime-local';
			
			// 이전 값 복원
		 	datetimeField.value = datetimeField.Value;
			datetimeField2.value = datetimeField2.Value;
		}
	  
	});

	// 종일 선택 여부 (업데이트 폼)
	document.getElementById('allday_check_update').addEventListener('change', function() {
	  const datetimeField = document.getElementById('all_check_update'); // 날짜 및 시간 입력 필드
	  const datetimeField2 = document.getElementById('all_check2_update2'); // 날짜 및 시간 입력 필드
	
	  // 기존 값 저장
	  var datetimeFieldValue = datetimeField.value;
	  var datetimeField2Value = datetimeField2.value;
	
	  // 체크박스가 체크된 경우 datetime 입력 필드를 날짜 선택 모드로 변경
	  if (this.checked) {
	    datetimeField.type = 'date';
	    datetimeField2.type = 'date';
	
	    //	이전 값을 복원
	 	datetimeField.value = datetimeFieldValue.split("T")[0];
	  	datetimeField2.value = datetimeField2Value.split("T")[0];
	  } else {
	    datetimeField.type = 'datetime-local';
	    datetimeField2.type = 'datetime-local';
	
	 	datetimeField.value = datetimeFieldValue;
	 	datetimeField2.value = datetimeField2Value;
	  }
	  
	});



	//calendar1 , 2 연동
	//< 달
	var calendarButton = document.querySelector('#calendar .fc-prev-button');
	var calendar2Button = document.querySelector('#calendar2 .fc-prev-button');
	//> 달
	var calendarButton2 = document.querySelector('#calendar .fc-next-button');
	var calendar2Button2 = document.querySelector('#calendar2 .fc-next-button');
	//< 년
	var calendarButton3 = document.querySelector('#calendar .fc-prevYear-button');
	var calendar2Button3 = document.querySelector('#calendar2 .fc-prevYear-button');
	//> 년
	var calendarButton4 = document.querySelector('#calendar .fc-nextYear-button');
	var calendar2Button4 = document.querySelector('#calendar2 .fc-nextYear-button');
	//투데이
	var calendarButton5 = document.querySelector('#calendar .fc-today-button');
	var calendar2Button5 = document.querySelector('#calendar2 .fc-today-button');

	//버튼 동시 기능
	  calendarButton.addEventListener('click', function() {
	    var clickEvent = new Event('click', { bubbles: true });
	    calendar2Button.dispatchEvent(clickEvent);
	  });
	  
	  calendarButton2.addEventListener('click', function() {
	  	var clickEvent = new Event('click', { bubbles: true });
	    calendar2Button2.dispatchEvent(clickEvent);
	  });
	  
	  calendarButton3.addEventListener('click', function() {
	  	var clickEvent = new Event('click', { bubbles: true });
	    calendar2Button3.dispatchEvent(clickEvent);
	  });
	  
	  calendarButton4.addEventListener('click', function() {
	  	var clickEvent = new Event('click', { bubbles: true });
	    calendar2Button4.dispatchEvent(clickEvent);
	  });
	  
	  calendarButton5.addEventListener('click', function() {
	  	var clickEvent = new Event('click', { bubbles: true });
	    calendar2Button5.dispatchEvent(clickEvent);
	  });
	  


// 모달 창 닫기
$('a.close').click(function() {
	$('#calendarModal').css('display', 'none');
	$('#calendarDetail').css('display', 'none');
	$('#calendarUpdateModal').css('display', 'none');
	$('div.modalwrap').remove();
});

//일정 등록
$("#calendarModal").submit(function(event) {
	event.preventDefault();
	
	// 보안 토큰
	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	
    // 폼 요소 내의 필드 값을 가져오기
    const title = $('input[name="calendar_title"]').val();
    let color = $('input[name="color"]:checked').val();
    const start = $('input[name="calendar_date"]').val();
    const end = $('input[name="calendar_date2"]').val();
    const allday = $('input[name="allday"]:checked').val();
    const description = $('textarea[name="calendar_description"]').val();
	
	// 커스텀 컬러 선택
	if(color == 1){
		color = $('input[name="custom_color"]').val();
	}			
	
    $.ajax({
    	url: "calendaradd",
    	type: "POST",
    	data:{
    		title: title,
    		color: color,
    		start: start,
    		end: end,
    		allday: allday,
    		description: description
    	},
    	beforeSend: function(xhr)
		{	// 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
			xhr.setRequestHeader(header, token);
		},
    	dataType: "json",
    	success: function(Result) {
        	if(Result == 1){
        		alert("일정이 등록 되었습니다.");
        		location.reload();
        	} else {
        		alert("일정 등록에 실패했습니다.");
        	}
    	}
    })

    // 예시: 콘솔에 데이터 출력
    console.log("제목:", title);
    console.log("색상:", color);
    console.log("시작 일자:", start);
    console.log("종료 일자:", end);
    console.log("하루 종일 여부:", allday);
    console.log("설명:", description);
            
});

//일정 수정 확인
$("#calendarUpdateModal").submit(function(event) {
	event.preventDefault();
	
	var data = {
		title: $('#calendar_title_update').val(),
	    color: $('input[name="color_update"]:checked').val(),
	    start: $('input[name="calendar_date_update"]').val(),
	    end: $('input[name="calendar_date2_update"]').val(),
	    allday: $('input[name="allday_update"]:checked').val(),
	    description: $('#calendar_description_update').val(),
	    id: idcheck
	};
	
	if(data.color == 1){
		data.color = $('input[name="custom_color_update"]').val();
	}

	calendarUpdateAjax(data);	
	
});

//일정 수정
function calendarUpdateAjax(data){
	// 보안 토큰
	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
    	url: "calendarupdate",
    	type: "POST",
		data: data,
    	beforeSend: function(xhr)
		{	// 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
			xhr.setRequestHeader(header, token);
		},
    	dataType: "json",
    	success: function(Result) {
        	if(Result == 1){
        		alert("일정이 수정 되었습니다.");
        		location.reload();
        	} else {
        		alert("일정 수정에 실패했습니다.");
        	}
    	}
    })

}

//일정드랍 수정
function calendarDropUpdateAjax(data){
	// 보안 토큰
	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
    	url: "calendardropupdate",
    	type: "POST",
		data: data,
    	beforeSend: function(xhr)
		{	// 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
			xhr.setRequestHeader(header, token);
		},
    	dataType: "json",
    	success: function(Result) {
        	if(Result == 1){
        		alert("일정이 수정 되었습니다.");
        	} else {
        		alert("일정 수정에 실패했습니다.");
        	}
    	}
    })

}

//일정 삭제
function calendarDeleteAjax(id){
	// 보안 토큰
	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url: "calendardelete",
		type: "POST",
		data: {
			id: id,
		},
		beforeSend: function(xhr)
		{	// 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
			xhr.setRequestHeader(header, token);
		},
		dataType: "json",
		success: function(Result) {
			if(Result == 1){
				alert("일정이 삭제 되었습니다.");
        		location.reload();
        	} else {
        		alert("일정 삭제에 실패했습니다.");
        	}
		}
	});
}

});//ready