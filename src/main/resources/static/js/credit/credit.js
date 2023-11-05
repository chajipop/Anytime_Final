
$(document).ready(function(){
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
		
	  // 졸업필요 학점 변경 
	  $('#requiredCreditForm').submit(function(e) {
	        e.preventDefault();  // 기본 폼 제출 동작을 막음

	        let newCreditValue = $('input[name="required_credit"]').val();

	        $.ajax({
	            url: 'updateGraduateCredit',  
	            type: 'POST',
	            data: {graduate_credit: newCreditValue},
	            beforeSend: function(xhr)
	            { 
	              xhr.setRequestHeader(header, token);
	            },
	            success: function(response) {
	                // 성공적으로 업데이트 되었을 때 처리
	                console.log('Update successful');
	                $('#requiredCreditForm').hide();
	                $('div.modalwrap').remove();
	                $('div.column.acquisition p.total').text('/ ' + newCreditValue);
	            },
	             error:function(request,status,error){
	                 alert("code:"+request.status+
	    +"message:"+request.responseText+"error:"+error)
	             }
	        });
	    });
	
	
     // 졸업필요학점 모달기능
	 $('#container > div > div.chart > article > div.column.acquisition > p.total').click(function(){
		  $('#requiredCreditForm').show();
		 $('#requiredCreditForm').before('<div class="modalwrap"></div>');
	  });
	  
	  $('#requiredCreditForm .close').click(function(e) {
	      e.preventDefault();
	      $('#requiredCreditForm').hide();
	      $('div.modalwrap').remove();
	  });
	  
//=============================================================================================================//	  
	 // 시간표 불러오기 모달기능
		$('.subjects').on('click','a.import',function() {
	
		
        $.ajax({
        
        url: 'gettimetable',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            var $select = $('#importForm select[name="semester"]');
       
            $select.empty();  // 기존의 select 옵션 제거

            $.each(data, function(index, item) {
              
                var option = $('<option>', { value: item.timetable_ID, text: item.semester + ' (' + item.name + ')'  });
                $select.append(option);
            });
				// 각 <option>의 timetableId 값들을 배열로 저장
				var timetableIds = $.map($select.find('option'), function(option) {
    			return $(option).val();  // 가정: <option>의 value가 timetable_id
				});

              // timetableIds 배열을 form의 data로 저장
			  $('#importForm').data('timetableIds', timetableIds);


               $select.prop('selectedIndex', 0);

             $('#importForm').show();
             $('#importForm').before('<div class="modalwrap"></div>')
            
        },
        error: function(jqXHR, textStatus, errorThrown) {
           console.log(textStatus + ': ' + errorThrown);
           alert('Failed to fetch timetable data.');
        }
    });
});
	
	// 가져오기 버튼 클릭시 
	$('#importForm').submit(function(event) {
    event.preventDefault();  // 기본 submit 동작을 막음

    var selectedSemester = $(this).find('select[name="semester"]').val();
   
   // 선택된 <option>의 인덱스를 구함
    var selectedIndex = $(this).find('select[name="semester"]')[0].selectedIndex;

    // 해당 인덱스에 맞는 timetableId 값을 가져옴
    var timetableIds = $(this).data('timetableIds');
    
	// 선택된 옵션과 동일한 인덱스를 가진 데이터를 얻음 
	var timetableId = (timetableIds && timetableIds[selectedIndex]) ? timetableIds[selectedIndex] : undefined;
	
	// active된 semester_id 다시 선언
	var semesterId = $(".menu li.active").data('id');
    

    $.ajax({
        url: 'gettimetable_detail',
        type: 'GET',
        data: { timetable_id: timetableId, 
                semester_id: semesterId
        },
        dataType: 'json',
        success: function(data) {
            var tbody = $('.subjects tbody');
         
            tbody.empty();  // 기존 행들 삭제

            var grades = ["A+", "A0", "A-", "B+", "B0", "B-", "C+", "C0", "C-", "D+", "D0", "D-", "F", "P", "NP"];

           $.each(data.semester_detail, function(i, detail) {
        var subject = (data.timetable_detail[i] && data.timetable_detail[i].subject) ? data.timetable_detail[i].subject : '';
        var gradeOptions = '';
        $.each(grades, function(i, grade) {
            gradeOptions += '<option value="' + grade + '"' + (grade === detail.grade ? ' selected' : '') + '>' + grade + '</option>';
        });
        
        var row = '<tr data-id="' + detail.semester_detail_id + '">' +
            '<td><input name="name" maxlength="50" value="' + subject+ '"></td>' +
            '<td><input name="credit" type="number" maxlength="4" min="0" value ="' + detail.credit+ '"></td>' +
            '<td><select name="grade">' + gradeOptions+ '</select></td>' +
            '<td><label><input name="major" type="checkbox"' +(detail.major ? ' checked' : '')+'><span></span></label></td>' +
        '</tr>';
        tbody.append(row);
        
             // 새로 추가된 row의 각 필드에 대해 change 이벤트를 수동으로 트리거합니다.
           $(".subjects tbody").find('input[name="name"]').trigger('change');
           
           
			});
			
			 $('#importForm').hide();  // 
   			 $('.modalwrap').remove();  // 
			
			
        },
        error: function(jqXHR, textStatus, errorThrown) {
           console.log(textStatus + ': ' + errorThrown);
           alert('Failed to fetch timetable details.');
        }
  });

});
		
	// 닫기 버튼을 눌렀을 때
		$('#importForm .close').click(function(e) {
    		e.preventDefault();
    	$('#importForm').hide();
    	$('div.modalwrap').remove();
	});

	// 모달 창 외부를 클릭했을 때
	$(document).mouseup(function(e) {
   		var container = $("#importForm");

    	// if the target of the click isn't the container nor a descendant of the container
    	if (!container.is(e.target) && container.has(e.target).length === 0) 
   	 	{
       	 	container.hide();
       		 $('div.modalwrap').remove();  // 추가된 부분
    	}
	});
	  
	  
	
	// 메뉴 선택시 기본 semester_detail이랑 semester_id 불러오기
	var semester_id;
	
	$(".menu li").click(function(){
			var $li = $(this);
			if ($li.data('id')) {
				$li.addClass('active').siblings().removeClass('active');
			scrollToActiveMenu();
			}
			
		semester_id = $(this).data('id');
		
		
	$.ajax({
	url: 'getsemester_detail',
	type: 'GET',
	data: {semester_id: semester_id},
	success: function(data) {
		
		var totalCredit = 0; //전체 학점의 합계
        var totalMajorCredit = 0; // 전공과목에 대한 학점의 합계
        var weightedSum = 0; // 모든 과목에 대한(과목점수 * 해당 과목 학점)의 총합
        var weightedMajorSum = 0; // 전공 과목에 대한 (과목점수 * 해당 전공과목 학점)의 총합 

        // 성적에 대응하는 숫자 값 맵
        var gradeValuesMap = {
            "A+":4.5, "A0":4.3, "A-":4,
            "B+":3.5, "B0":3.3, "B-":3,
            "C+":2.5, "C0":2.3, "C-":2,
            "D+":1.5, "D0":1.3, "D-":1,
            "F": 0, "P": 0, "NP": 0
        };

        $.each(data, function(i, detail) {
    		var credit = detail.credit;
    		
    		// 해당 성적에 대응하는 숫자 값 가져오기
    		var gradeValue = gradeValuesMap[detail.grade];

    		totalCredit += credit;
    		weightedSum += gradeValue * credit;

    		if (detail.major) {
    			totalMajorCredit += credit;
    			weightedMajorSum += gradeValue * credit;
    		}
    	});

    	var gpa = (totalCredit > 0) ? (weightedSum / totalCredit).toFixed(2) : "-";
    	
        var majorGpa = (totalMajorCredit > 0) ? (weightedMajorSum / totalMajorCredit).toFixed(2) : "-";

        $('dd.gpa').text(gpa);
        $('dd.major').text(majorGpa);
        $('dd.acquisition').text(totalCredit);

        // 받아온 데이터로 subject테이블 만들기 
        var tbody = $('.subjects tbody');
        tbody.empty();  // 기존 행들 삭제
        
        var grades = ["A+", "A0", "A-", "B+", "B0", "B-", "C+", "C0", "C-", "D+", "D0", "D-", "F", "P", "NP"];
        
        $.each(data, function(i, detail) {
        $('#container > div > table > caption > h3').text(detail.semester_name);  
        	var gradeOptions = '';
            $.each(grades, function(i, grade) {
                gradeOptions += '<option value="' + grade + '"' + (grade === detail.grade ? ' selected' : '') + '>' + grade + '</option>';
            });
            var row = '<tr data-id="' + detail.semester_detail_id + '">' +
                '<td><input name="name" maxlength="50" value="' + detail.subject + '"></td>' +
                '<td><input name="credit" type="number" maxlength="4" min="0" value ="' + detail.credit + '" ></td>' +
                '<td><select name="grade"><option value="' + detail.grade + '" selected="selected">'+ gradeOptions + '</select></td>' +
                '<td><label><input name="major" type="checkbox"' +(detail.major ? ' checked' : '')+'><span></span></label></td>' +
            '</tr>';
            tbody.append(row);
	         });
            }
         });
	   });
	
  $(".menu li").first().trigger('click');
	
  // 더 입력하기 클릭
  $(".new").click(function() {
	    var tbody = $('.subjects tbody');
	    var grades = ["A+", "A0", "A-", "B+", "B0", "B-", "C+", "C0", "C-", 
	                  "D+", "D0",  "D-", "F", "P", "NP"];
	    
	    var gradeOptions = '';
	    $.each(grades, function(i, grade) {
	        gradeOptions += '<option value="' + grade + '">' + grade + '</option>';
	    });
	    
	    var row = '<tr>' +
	        '<td><input name="name" maxlength="50"></td>' +
	        '<td><input name="credit" type="number" maxlength="4" min="0" value ="0"></td>' +
	        '<td><select name="grade">' + gradeOptions + '</select></td>' +
	        '<td><label><input name="major" type ="checkbox"><span></span></label></td>' +
	    '</tr>';
	    
	    tbody.append(row);
	});	
	
   
  // subject 항목 변경될때 
  function updateField(e) {
      var $target = $(e.target);
      var $row = $target.closest('tr');
      
      var semester_detail_id = $row.data('id');
      
      var subject = $row.find('input[name="name"]').val();
      var credit = $row.find('input[name="credit"]').val();
      var grade = $row.find('select[name="grade"]').val();
      var major = $row.find('input[name="major"]').prop('checked');
   
      
 
      $.ajax({
          url: 'updatesemester_detail',
          type: 'POST',
          async: true, // 동기적으로 일어나라 
          data: {
        	  semester_detail_id: semester_detail_id,
              semester_id: semester_id,
              subject: subject,
              credit: credit,
              grade: grade,
              major: major
          },
          dataType:"json",
          beforeSend: function(xhr)
          { // 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
            xhr.setRequestHeader(header, token);
          },
          success: function(data) {
              console.log(data);
              
              // 성적 업데이트 후 평균 점수 다시 계산
              var totalCredit = 0;
              var totalMajorCredit = 0;
              var weightedSum = 0;
              var weightedMajorSum = 0;
              
              var gradeValueMap = 
            	  { "A+":4.5, "A0":4.3, "A-":4, "B+":3.5, "B0":3.3, "B-":3,
            		"C+":2.5, "C0":2.3, "C-":2, "D+":1.5, "D0":1.3, "D-":1,
            		"F":0, "P":0, "NP":0};
              
              $('.subjects tbody tr').each(function() {
                  var $row = $(this);
                  var credit = Number($row.find('input[name="credit"]').val());
                  var gradeValue = gradeValueMap[$row.find('select[name="grade"]').val()];
                  var major = $row.find('input[name="major"]').prop('checked');
                  
                  totalCredit += credit;
                  weightedSum += gradeValue * credit;

                  if (major){
                      totalMajorCredit += credit;
                      weightedMajorSum += gradeValue * credit;
                  }
              });
              var gpa = (totalCredit > 0) ? (weightedSum / totalCredit).toFixed(2) : "-";
              var majorGpa = (totalMajorCredit > 0) ? (weightedMajorSum / totalMajorCredit).toFixed(2) : "-";

  	        $('dd.gpa').text(gpa);
  	        $('dd.major').text(majorGpa);
  	        $('dd.acquisition').text(totalCredit);
  	        
	  	    $('.column.gpa .value').text(data.totalgpa);
	        $('.column.major .value').text(data.totalmajor);
	        $('.column.acquisition .value').text(data.totalAcquisition);
	        
       // chart1 업데이트
		var newLabelData = data.semestername.map(function(semester) {
           return semester.semester_name;
       });

       var newGpaData = data.gpa.map(function(gpaMap) {
           return parseFloat(gpaMap.Gpa);
       });

       var newMajorData = data.gpa.map(function(gpaMap) {
           return parseFloat(gpaMap.Major);
       });

       myChart.data.labels = newLabelData;
       myChart.data.datasets[0].data = newGpaData;
       myChart.data.datasets[1].data = newMajorData;

       myChart.update();
       
       var gradeData = data.gradeData;  // 서버에서 반환된 상위 5개 성적 데이터

	// chart2 업데이트
	   var newData = new google.visualization.DataTable();
	   newData.addColumn('string', 'Grade');
	   newData.addColumn('number', 'Count');

	   for (var i = 0; i < gradeData.length; i++) {
       newData.addRow([gradeData[i].GRADE, gradeData[i].COUNT]);
	 }

	   var options = {
       title: '학점 비율',
       pieHole: 0.35,
       colors: ['rgb(242,133,114)', 'rgb(236,197,92)', 'rgb(160,198,97)', 'rgb(130,209,194)', 'rgb(122,158,224)'],
       pieSliceText: "label",
       chartArea:{left:'10%',right:'10%',top:'10%',width:'70%',height:'70%'},
       titleTextStyle: {fontSize: 13}
    };

		var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
		chart.draw(newData,options);
          }
     });
  }
  
//초기화 버튼 클릭 이벤트 핸들러
  $('.reset').click(function() {

      // 알림 창 표시 
      var confirmReset = confirm("해당 학기의 정보가 모두 초기화됩니다. 계속하시겠습니까?");
      
      if(confirmReset) {
      $('.subjects tbody tr').each(function() {
          var $row = $(this);
          var semester_detail_id = $row.data('id');

          // 초기값 설정
          var subject = "";
          var credit = "0";
          var grade = "A+";
          var major = false; 

          $.ajax({
              url: 'updatesemester_detail',
              type: 'POST',
              async: false,
              data: {
                  semester_detail_id: semester_detail_id,
                  semester_id: semester_id,
                  subject: subject,
                  credit: credit,
                  grade: grade,
                  major: major
              },
              beforeSend:function(xhr){
                 xhr.setRequestHeader(header,token);
              },
              success: function(data) {
                  console.log(data);
                  
                  // 성적 업데이트 후 평균 점수 다시 계산
                  var totalCredit = 0;
                  var totalMajorCredit = 0;
                  var weightedSum = 0;
                  var weightedMajorSum = 0;
                  
                  var gradeValueMap = 
                	  { "A+":4.5, "A0":4.3, "A-":4, "B+":3.5, "B0":3.3, "B-":3,
                		"C+":2.5, "C0":2.3, "C-":2, "D+":1.5, "D0":1.3, "D-":1,
                		"F":0, "P":0, "NP":0};
                  
                  $('.subjects tbody tr').each(function() {
                      var $row = $(this);
                      var credit = Number($row.find('input[name="credit"]').val());
                      var gradeValue = gradeValueMap[$row.find('select[name="grade"]').val()];
                      var major = $row.find('input[name="major"]').prop('checked');
                      
                      totalCredit += credit;
                      weightedSum += gradeValue * credit;

                      if (major){
                          totalMajorCredit += credit;
                          weightedMajorSum += gradeValue * credit;
                      }
                  });
                  var gpa = (totalCredit > 0) ? (weightedSum / totalCredit).toFixed(2) : "-";
                  var majorGpa = (totalMajorCredit > 0) ? (weightedMajorSum / totalMajorCredit).toFixed(2) : "-";

      	        $('dd.gpa').text(gpa);
      	        $('dd.major').text(majorGpa);
      	        $('dd.acquisition').text(totalCredit);
      	        
    	  	    $('.column.gpa .value').text(data.totalgpa);
    	        $('.column.major .value').text(data.totalmajor);
    	        $('.column.acquisition .value').text(data.totalAcquisition);
    	   
    	   
    	        location.reload();
    	        }
             });
         });
     }
  });
  
  
  // 각 입력 필드에 change 이벤트 핸들러 추가
  $('.subjects').on('change', 'input, select', updateField);
  
});
	
	function scrollToActiveMenu() {
        console.log("scrollTo");
        var $menu = $('.menu');
        if (!$menu.is(':has(li.active)')) {
            return false;
        }
        $menu.scrollLeft(0);
       var left = Math.floor($menu.find('li.active').position().left) - 50;
       $menu.scrollLeft(left);
   }




	