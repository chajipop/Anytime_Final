$(document).ready(function(){

	$("#writeBoardContainer").prepend(
	`<form class="write" id ="writeBoard" enctype="multipart/form-data">
	
	<p><input id="title" placeholder="글 제목" class="title"></p>
	<p style="margin-bottom: 0px !important;">
	<textarea id="content" placeholder="애니타임은 누구나 기분 좋게 참여할 수 있는 커뮤니티를 만들기 위해 커뮤니티 이용규칙을 제정하여 운영하고 있습니다. 
위반 시 게시물이 삭제되고 서비스 이용이 일정 기간 제한될 수 있습니다. 
	
아래는 이 게시판에 해당하는 핵심 내용에 대한 요약 사항이며, 게시물 작성 전 커뮤니티 이용규칙 전문을 반드시 확인하시기 바랍니다. 

※ 정치·사회 관련 행위 금지 
- 국가기관, 정치 관련 단체, 언론, 시민단체에 대한 언급 혹은 이와 관련한 행위 
- 정책·외교 또는 정치·정파에 대한 의견, 주장 및 이념, 가치관을 드러내는 행위 
- 성별, 종교, 인종, 출신, 지역, 직업, 이념 등 사회적 이슈에 대한 언급 혹은 이와 관련한 행위 
- 위와 같은 내용으로 유추될 수 있는 비유, 은어 사용 행위 

※ 홍보 및 판매 관련 행위 금지 
- 영리 여부와 관계 없이 사업체·기관·단체·개인에게 직간접적으로 영향을 줄 수 있는 게시물 작성 행위 
- 위와 관련된 것으로 의심되거나 예상될 수 있는 바이럴 홍보 및 명칭·단어 언급 행위 

※ 불법촬영물 유통 금지
불법촬영물등을 게재할 경우 전기통신사업법에 따라 삭제 조치 및 서비스 이용이 영구적으로 제한될 수 있으며 관련 법률에 따라 처벌받을 수 있습니다.

※ 그 밖의 규칙 위반 
- 타인의 권리를 침해하거나 불쾌감을 주는 행위 
- 범죄, 불법 행위 등 법령을 위반하는 행위 
- 욕설, 비하, 차별, 혐오, 자살, 폭력 관련 내용을 포함한 게시물 작성 행위 
- 음란물, 성적 수치심을 유발하는 행위 
- 스포일러, 공포, 속임, 놀라게 하는 행위 " class="smallplaceholder large">
</textarea></p>
<input class="file" type="file" name="file" multiple="multiple">
<ol class="thumbnails">
<li class="new">
	<input type="file" id="new_file" name="file" multiple style='display:none;'>
</li>
</ol>
<div class="clearBothOnly"></div>
<ul class="option">

<!-- ▼해시태그 미사용 주석처리▼ 
	<li title="해시태그" class="hashtag"></li> 
-->
<li title="첨부" class="attach">
  <label class="file-label">
    <span class="file-name" id="file-name"></span> 파일 선택
    <input type="file" id="post_file" name="post_file" class="file-input">
  </label>
</li>
<li title="완료" class="submit" id="writePost"></li>
<!-- ▼익명체크박스 미사용 주석처리▼
<li class="anonym">
<input type="checkbox" class="form-check-input" id="isAnonymous" name="isAnonymous">
 <label class="form-check-label" for="exampleCheck1"> 익명 </label>
</li>
-->

</ul>
<div class="clearBothOnly"></div>
</form>
`);


$("#writeBoard").hide(); // Hide
var toggle = false;

$("#writeArticleButton").on("click", function(e) {	
	
	toggle = !toggle;
	if(toggle == true){
		
		$("#writeBoard").show(); //Show
		$('#writeArticleButton').hide();
	}else{		
		
		$("#writeBoard").hide(); // Hide
		$('#writeArticleButton').show();
	}
});

/*글쓰기 완료*/
var filesToUpload = []; // 업로드할 파일들을 저장하는 배열

$('body').on('click', 'li.new', function() {
	    $('#post_file').trigger('click');
	});

	$('#post_file, #new_file').on('change', function() {
		var newFiles = this.files;
	
		// 이미 업로드된 파일의 개수
		var uploadedFileCount = $('.thumbnails li').length - 1; //'new' li 제외
	
		if(uploadedFileCount + newFiles.length > 5) {
			alert("최대 5개의 파일만 첨부 가능합니다.");
			this.value=''; // input 태그의 value 초기화
			return;
		}
	
		for(var i=0; i<newFiles.length; i++){
			if(!newFiles[i].type.startsWith('image/')){
				alert('현재 지원되지 않는 형식이거나, \n파일이 손상되었기 때문에 사진을 열 수 없습니다.');
				this.value='';
				continue;
			}
	
			filesToUpload.push(newFiles[i]); // 새롭게 추가된 파일들도 배열에 포함시킴
	
			var reader = new FileReader();
	
			reader.onloadend = function(e) {
				// 여기서 썸네일 미리보기를 생성합니다.
				var thumbnailItem=$('<li><img src="' + e.target.result + '" style="width:85px; height:85px;"></li>');
				
				$('.thumbnails .new').before(thumbnailItem);
				
				// 첨부파일이 선택되었으므로, thumbnails 영역을 보여줍니다.
				$('#container>div.articles>form.write ol.thumbnails').css('display', 'block');
			};
			
			reader.readAsDataURL(newFiles[i]);
		}
		
		// input 태그의 value 초기화
		this.value='';
	});

	$('body').on('click', '.thumbnails li:not(.new)', function() {
		var index = $(this).index();
		
		// 썸네일 삭제
		$(this).remove();
	
		// 배열에서 파일제거
		filesToUpload.splice(index, 1);
	
		 // 업로드된 파일의 개수가 0이면 thumbnails 영역을 숨깁니다.
		 if($('.thumbnails li').length == 1) { //'new' li만 남았다면
			 $('#container>div.articles>form.write ol.thumbnails').css('display', 'none');
		 }
	});


    $("#writePost").on("click", function(e) {
        e.preventDefault();

        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");

	    var board_id = parseInt($("#board_id").html(), 10); 

	    var formData=new FormData();
	    formData.append('BOARD_ID', board_id);
	    formData.append('LOGIN_ID', $('#login_id').val());
	    formData.append('SUBJECT', $("#title").val());
	    formData.append('CONTENT', $("#content").val());

	    for (var i=0; i<filesToUpload.length; i++) { 
		    // 모든 첨부파일을 formData에 추가합니다.
		    formData.append("file[]", filesToUpload[i]);
	    }

	  	$.ajax({
	    	type: "POST",
	    	url: "/anytime/post/write",
	    	data: formData,
	    	processData: false,  
	  		contentType: false,
	  		beforeSend: function(xhr) {
	  			xhr.setRequestHeader(header, token);
	  		},
	  	}).done(function(res) {
	      	if (res.statusCode == 1){
	          	alert("게시글 작성에 성공하였습니다.");
	          	location.reload();
	      	} else {
	          	alert("게시글 작성에 실패하였습니다. 오류: " + res.errorMessage);
	      	}
	  	});
    });
/*글쓰기 완료*/
})