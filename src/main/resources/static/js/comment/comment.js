
function getList() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");

	console.log("post_id : " + $("#post_id").val());
	console.log("anonymous : " + anonymous);
	console.log("currentUserId : " + currentUserId);
	console.log("writerId : " + writerId);
	$.ajax({
		type: "post",
		url: "../comment/list",
		data: {
			"post_id": $("#post_id").val(),
			"anonymous": anonymous,
			"currentUserId": currentUserId,
			"writerId": writerId,
		},
		beforeSend: function(xhr)
		{	// 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
			xhr.setRequestHeader(header, token);
		},
		dataType: "json",
		success: function(rdata) {
			let output = "";
			let writername = "";

			if (rdata.commentlist.length > 0) {
				$(rdata.commentlist).each(function() {
					const lev = this.re_lev;
					const status = this.status;

					let comment_reply = '';
					if (lev == 0) {
						comment_reply = 'parent';
					} else if (lev == 1) {
						comment_reply = 'child';
					} else if (lev == 2) {
						comment_reply = 'grandchild';
					}
					console.log("comment_reply : " + comment_reply);
					if (anonymous == 0) {
						writername = this.nickname;
					} else if (anonymous == 1 && this.user_id == writerId) {
						writername = "익명(글쓴이)";
					} else {
						writername = "익명";
					}
					
					if(status == 0) {
						writername ="(삭제)";
						this.content="삭제된 댓글입니다.";
					}
					console.log("writername : " + writername);

					$('div.comments').children().not('form').remove();

					output += '<article id="' + this.comment_id + '" class="' + comment_reply + '">   							'
						+ '  <img src="https://cf-fpi.everytime.kr/0.png" class="picture medium">      							'
						+ '  <h3 class="medium">' + writername + '</h3>                           								'
						+ '       <ul class="status">                                          									'
					if (lev < 2 && status !=0) {
						output += '          <li class="childcomment">                           								'
							+ '            <a href="javascript:replyform(' + this.comment_id + ',' + this.re_lev + 		  ',' 
							+ 				this.re_seq + ',' + this.re_ref + ')" class="reply">   								'
							+ '            대댓글</a></li>                                    										'
					}
					if(status == 0) {
					}else if (currentUserId == this.user_id) { // 작성자만 수정 삭제가 되도록 //admin도 접근 가능하게 하려면 변경해줘야함
						output += '         <li class="modify">                                  								'

							+ '            <a class="modify" href="javascript:updateForm(' + this.comment_id + ')">      		'
							+ '             수정</a></li>                                          '
							+ '         <li class="del"><a class="del" href="javascript:del(' + this.comment_id + ')">			'
							+ '            삭제</a></li>                                                							'
					} else {
						output += '         <li class="commentvote">공감</li>                              '
						output += '         <li class="messagesend" data-modal="messageSend" data-comment-id="1423439802" data-is-anonym="1">쪽지</li>'
						output += '         <li class="abuse">신고</li>'
					}
					output += '      </ul>                                                   '
						+ '      <hr>                                                   '
						+ '      <p class="large">' + this.content + '</p>                        '
						+ '      <time class="medium">' + this.comment_date + '</time>               '
						+ '      <ul class="status commentvotestatus">                           '
						+ '         <li class="vote commentvote" style="display: none;">            '
						+ this.like_count + '</li>                              '
						+ '      </ul>                                                   '
						+ '      </article>                                                ';

				})//each end
				$('.writecomment').before(output);
				
			}//if(rdata.postlist.length>0)
			else { //댓글 1개가 있는 상태에서 삭제하는 경우 갯수는 0이라  if문을 수행하지 않고 이곳으로 옵니다.
				//이곳에서 아래의 두 영역을 없앱니다.
				$('.comments').find('article').remove();
			}
		}//success end
	});//ajax end
}//function(getList) end

function del(comment_id) {//comment_id : 댓글 번호
	console.log("삭제할 코멘트 : " + comment_id);
	if (confirm('정말 삭제하시겠습니까')) {
		$.ajax({
			url: '../comment/delete',
			data: { comment_id: comment_id },
			success: function(rdata) {
				if (rdata == 1) {
					getList();
				}
			}
		});
	}
}//function(del) end

//답글 달기 폼
function replyform(comment_id, lev, seq, ref) {
	$(this).off('click'); // 중복 클릭 방지
	$('.status').addClass('disabled');
	
	console.log($('.status').attr('class'))
	
	let comment_reply = '';
	if (lev == 0) {
		comment_reply = ' parent';
	} else if (lev == 1) {
		comment_reply = ' child';
	} else if (lev == 2) {
		comment_reply = ' grandchild';
	}

	// 클릭된 a 요소의 상위에 위치한 article 엘리먼트를 찾음
	const $comment_id = $('#' + comment_id); // 수정된 부분

	// article 엘리먼트의 id 속성 값을 가져와서 출력
	console.log($comment_id.attr('id'));

	$('.writecomment input').val('');

	//글쓰기 영역 복사합니다.
	const output = $('.writecomment').clone().addClass(comment_reply).addClass('clone');

	console.log(output.attr('class'));
	//선택한 글 뒤에 답글 폼 생성합니다.
	$comment_id.after(output); // 이 부분이 수정되었습니다.

	const $comment_id_next = $comment_id.next();

	console.log($comment_id_next.find('.text').attr('placeholder'));
	//답글 폼의  <textarea>의 속성 'placeholder'를 '답글을 남겨보세요'로 바꾸어 줍니다.
	$comment_id_next.find('.text').attr('placeholder', '대댓글을 남겨보세요');

	//답글 폼의  '.btn-cancel'을 보여주고 클래스 'reply-cancel'를 추가합니다.
	$comment_id_next.find('.cancel').addClass('reply-cancel').attr('title', '대댓글 취소');
	$('.reply-cancel').css('display','');
	console.log($comment_id_next.find('.cancel').attr('class'));

	//답글 폼의 '.btn-register'에  클래스 'reply' 추가합니다.
	//속성 'data-ref'에 ref, 'data-lev'에 lev, 'data-seq'에 seq값을 설정합니다.
	//등록을 답글 완료로 변경합니다.
	$comment_id_next.find('.submit').attr('data-ref',ref).attr('data-lev', lev).attr('data-seq', seq).attr('title', '대댓글 완료').removeClass('submit_origin').addClass('reply_submit');

	$('li.cancel').click(function() {
		$(this).closest('.writecomment.clone').remove();

		$('.status').removeClass('disabled');

	});
}//function(replyform) end

function updateForm(comment_id) { //comment_id : 수정할 댓글 글번호
	$(this).off('click');
	$('.status').addClass('disabled');

	let $comment_id = $('#' + comment_id);
	let lev_class = $comment_id.attr('class');

	//선택한 내용을 구합니다.
	let content = $comment_id.find('p.large').text();

	//$('.writecomment').clone() : 기본 글쓰기 영역 복사합니다.
	//글이 있던 영역에 글을 수정할 수 있는 폼으로 바꿉니다.
	$comment_id.after($('.writecomment').clone().addClass('clone').addClass(lev_class).attr('data-id', comment_id));
	$('.clone .cancel').css('display','');
	//수정 폼의 <textarea>에 내용을 나타냅니다.
	$('.clone .text').val(content);
	$('.writecomment.clone li.submit').attr('title', '수정완료');

	//'.modify' 영역에 수정할 글 번호를 속성 'data-id'에 나타내고 클래스 'update'를 추가합니다.
	$('.writecomment.clone li.submit').attr('data-id', comment_id).addClass('update').removeClass('submit_origin');

	//수정 후 수정완료를 클릭한 경우
	$('.writecomment').off('click', '.submit_origin').on('click', '.update', function() {
		const content = $(this).parent().parent().find('.text').val();
		if (!content) {//내용없이 등록 클릭한 경우
			alert("수정할 글을 입력하세요");
			return;
		}
		const comment_id = $(this).attr('data-id');
		console.log("수정할 comment_id : " + comment_id);
		console.log("수정할 content : " + content);

		if (confirm("댓글을 수정하시겠습니까?")) {
			$.ajax({
				url: '../comment/update',
				data: { comment_id: comment_id, content: content },
				success: function(rdata) {
					if (rdata == 1) {
						$('.article').find('.clone').remove();
						$('div.comments article').remove();

						getList();
					}//if
				}//success
			});//ajax
		}
	});//수정 후 수정완료를 클릭한 경우
	
	// 취소 버튼을 클릭할 때 실행될 함수
	$('.comments form:not(.clone) .cancel').click(function() {
	    // 취소 버튼이 속한 form을 찾아서, 그 안에 있는 input 요소를 찾아 값을 초기화합니다.
	    $(this).closest('form').find('input[name="text"]').val('');
	});

	//수정 후 취소 버튼을 클릭한 경우
	$('.clone li.cancel').click(function() {
		//댓글 번호를 구합니다.
		const comment_id = $(this).next().attr('data-id');
		const selector = '#' + comment_id;

		$(this).closest('.writecomment.clone').remove();

		//숨겨두었던 댓글 영역 보여줍니다.
		$(selector).css('display', 'block');

		$('.status').removeClass('disabled');

	});
}//function(updateForm) end

$(document).ready(function() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");

	getList();

	$('article').off('click', 'li.reply_submit').on('click', 'li.submit_origin', function() {
		const content = $(this).parent().parent().find('.text').val();
		if (!content) {//내용없이 등록 클릭한 경우
			alert("댓글을 입력하세요");
			return;
		}

		if (confirm("댓글을 등록하시겠습니까?")) {
			$(this).off('click'); // 중복 클릭 방지
			$.ajax({
				url: '../comment/add',  //원문 등록
				data: {
					"content": content,
					"post_id": $("#post_id").val()
				},
				beforeSend: function(xhr)
				{	// 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
					xhr.setRequestHeader(header, token);
				},
				type: 'post',
				success: function(rdata) {
					if (rdata == 1) {
						location.reload(); 
						getList();
					}
				}
			})//ajax
			$('.text').val('');//text 초기화
		}
	});// $('article li.submit').click(function()


	//답글쓰기 후 취소 버튼을 클릭한 경우
	$('.clone > li.cancel').click(function() {
		$(this).parent().parent().remove();
		$(".status").removeClass('disabled'); //더 보기 영역 보이도록 합니다.
	})//답글쓰기  후 취소 버튼을 클릭한 경우

	$(document).on('click', '.clone .reply_submit', function(e) {
		const content = $(this).parent().parent().find('.text').val();
		if (!content) {
			alert("대댓글을 입력하세요");
			return;
		}
		const re_ref = $(this).attr('data-ref');
		const re_lev = $(this).attr('data-lev');
		const re_seq = $(this).attr('data-seq');
		if (confirm("대댓글을 등록하시겠습니까?")) {
			$(this).off('click'); // 중복 클릭 방지
			$.ajax({
				url: '../comment/reply',
				data: {
					content: content,
					post_id: $("#post_id").val(),
					re_lev: re_lev,
					re_ref: re_ref,
					re_seq: re_seq
				},
				type: 'post',
				beforeSend: function(xhr)
				{	// 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
					xhr.setRequestHeader(header, token);
				},
				success: function(rdata) {
					if (rdata == 1) {
						$('div.comments').children().not('form').remove();
						$('.reply_submit').parent().parent().remove();
						getList();
					} else {
						alert('댓글 등록에 실패하였습니다.');
					}
				}
			})
		}
	})

});