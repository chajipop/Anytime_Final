$(document).ready(function () {
    $(".posvote").on('click', function(e) { 
        e.preventDefault();
		
		var confirmResult = confirm('이 글에 공감하십니까?');
		
		if(confirmResult){
        var post_id= $(this).data('post_id');

        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: 'likePost',
            type: 'POST',
            data: {POST_ID: post_id},
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token)
            },
            success:function(data){
			    if(data.statusCode == 1){
			        alert('공감 완료!');
			        $('.vote').text(data.like_count); // 서버로부터 받은 공감 수를 표시
			    } else if(data.statusCode == 2){
			        alert('공감 취소!');
			        $('.vote').text(data.like_count); 
			    } else{
			        alert('오류 발생 : ' + data.errorMessage);
			    }
			},
            error:function(e){
                console.log(e);
                alert('오류 발생!');
            }
        });
        }
    });
});
