window.updateCancel = function() {
    location.reload();
}

function addThumbnail(filePath) {
	var thumbnailItem = $('<li><img src="' + filePath + '" style="width:85px; height:85px;"></li>');
	$('.thumbnails .new').before(thumbnailItem);
}

$(document).ready(function () {

	$("#writeBoardContainer").prepend(
		`<form class="write" id ="writeBoard" enctype="multipart/form-data"> 
     
    <p><input id="title" placeholder="글 제목" class="title"></p> 
    <p style="margin-bottom: 0px !important;"> 
    <textarea id="content" placeholder="" class="smallplaceholder large"> 
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
    <span class="file-name" id="file-name"></span>
    <input type="file" id="post_file" name="post_file" class="file-input" style='display:none;'>
  </label>
</li>	

<li title="완료" class="submit" id="writePost"></li>
</ul>
<div class="clearBothOnly"></div>
     </form>`);


	 $("#writeBoard").hide(); // Hide 
    var toggle = false;

    $("#writeArticleButton").on("click", function(e) {
        toggle = !toggle;
        if (toggle == true) {
            $("#writeBoard").show(); // Show 
        } else {
            $("#writeBoard").hide(); // Hide 
        }
    });

    var existingFiles = []; // 이미 업로드된 파일들을 저장하는 배열
    var filesToUpload = []; // 업로드할 파일들을 저장하는 배열
    var filesToDelete = []; // 삭제할 파일들을 저장하는 배열

    /*글 수정 시작 => 기존 정보 데이터값 불러오기*/
    $("#updateButton").on("click", function(e) {
        e.preventDefault();

        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");

        let POST_ID = $(this).attr('POST_ID');

        let data = {
            post_id: POST_ID
        };

        $.ajax({
            url: 'updateGet',
            type: 'GET',
            data: data,
            async: false,
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token)
            },
            success: function(data) {
                console.log(data);

                // 값 가져오기
                let SUBJECT = data.SUBJECT;
                let CONTENT = data.CONTENT;

                // 가져온 값으로 입력 필드 업데이트
                $('#title').val(SUBJECT);
                $('#content').val(CONTENT);
                
                let context = $('#contextpass').val();

                // 첨부 파일 데이터 업데이트
                let files = data.FILES;  // 서버에서 반환하는 데이터가 문자열 형태라면, JSON.parse를 사용하여 배열로 변환
                if (files && files.length > 0) {
                    for (let i = 0; i < files.length; i++) {
                        addThumbnail(context+'/upload' + files[i]);  // 상대 경로를 사용하여 썸네일 추가
		                existingFiles.push(files[i]);         // 기존에 있던 파일들도 업로드 대상 배열에 추가.
                    }
                    $('#container>div.articles>form.write ol.thumbnails').css('display', 'block');   // 썸네일 영역 보이기 추가
                }

                $("#writeBoard").show();
                $("#boardInfo").hide();

                $("#goListButton").text("글 수정 취소");
                $("#goListButton").attr('onClick', 'updateCancel()');
                $("#container>div.articles>div.pagination>a.list").css({
                    "float": "left",
                    "background-image": 'url(/anytime/resources/image/post/container.articles.pagination.before.png)'
                });
            },
            error: function(e) {
                alert("게시글 불러오기에 실패하였습니다. 오류 : " + e.errorMessage);
                console.log(e);
            }
        });
    });

    /*글 수정 종료 => 기존 정보 데이터값 불러오기*/

    /*글쓰기 완료*/

    $('body').on('click', 'li.new', function() {
        $('#post_file').trigger('click');
    });

    $('#post_file, #new_file').on('change', function() {
        var newFiles = this.files;

        // 이미 업로드된 파일의 개수
        var uploadedFileCount = $('.thumbnails li').length - 1; //'new' li 제외

        if (uploadedFileCount + newFiles.length > 5) {
            alert("최대 5개의 파일만 첨부 가능합니다.");
            this.value = ''; // input 태그의 value 초기화
            return;
        }

        for (var i = 0; i < newFiles.length; i++) {
            if (!newFiles[i].type.startsWith('image/')) {
                alert('현재 지원되지 않는 형식이거나, \n파일이 손상되었기 때문에 사진을 열 수 없습니다.');
                this.value = '';
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

        if(index < existingFiles.length){
            let deletedFile = existingFiles.splice(index, 1)[0];
            filesToDelete.push(deletedFile);
            console.log('Deleted existing file: ' + deletedFile);
        } else {
            index -= existingFiles.length;
            deletedFile = filesToUpload.splice(index, 1)[0];
            console.log('Deleted new file: ' + deletedFile.name);
        }
 
        $(this).remove();

        // 업로드된 파일의 개수가 0이면 thumbnails 영역을 숨깁니다.
        if($('.thumbnails li').length == 1){ 
            $('#container>div.articles>form.write ol.thumbnails').css('display', 'none');
        }
    });

    $("#writePost").on("click", function(e) {
        e.preventDefault();

        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");

        var post_id = $('#updateButton').attr('POST_ID');

        let formData = new FormData();
        formData.append('POST_ID', post_id);
        formData.append('LOGIN_ID', $('#login_id').val());
        formData.append('SUBJECT', $("#title").val());
        formData.append('CONTENT', $("#content").val());

        for (var i=0; i<existingFiles.length; i++) { 
            formData.append("existingFile[]", existingFiles[i]);
        }
  
        for (var i=0; i<filesToUpload.length; i++) { 
            formData.append("file[]", filesToUpload[i]);
        }
  
        for (var i=0; i<filesToDelete.length; i++) { 
            formData.append("deleteFile[]", filesToDelete[i]);
            console.log('Sending delete request for file: ' + filesToDelete[i]);
        }

        $.ajax({
            type: "POST",
            url: "updatePost",
            data: formData,
            processData: false,
            contentType: false,
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
        }).done(function(res) {
            if (res.statusCode == 1) {
                alert("게시글 수정에 성공하였습니다.");
                location.reload();
            } else {
                alert("게시글 수정에 실패하였습니다. 오류: " + res.errorMessage);
            }
        });
    });
    /*글쓰기 완료*/
});
 ``
