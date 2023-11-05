function createBoard(anony) {//익명 or 닉네임 사용하는 게시판을 생성합니다. 0:닉네임, 1:익명 
	console.log("게시판 생성 익명 여부 0:닉네임, 1:익명" + anony);
	option = anony;

	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		type: "post",
		url: "create",
		data: {
			"TYPE": $('input[type="radio"]:checked').val(),
			"NAME": $('input[name="name"]').val(),
			"CONTENT": $('input[name="content"]').val(),
			"PURPOSE": $('input[name="purpose"]').val(),
			"ANONYMOUS": option,
			"LOGIN_ID": $('#login_id').val(),
			"SCHOOL_ID": $('#school_id').val()
		},
		beforeSend: function(xhr)
		{	// 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
			xhr.setRequestHeader(header, token);
		},
		success: function(resp) {
			if (resp == 0) { //게시판 생성이 실패한 경우
				alert("게시판 생성이 실패했습니다.");
			} else {
				alert("게시판이 생성되었습니다. 단체/학과 게시판은, 승인 심사에 통과되면 바로 이용가능합니다.");
				location.href = 'boardlist';
			}
		}
	})
}

$(function() {
	// 모달 열기 버튼을 클릭할 때
    $('a.createboard').click(function() {
        $('form#createBoard').css('display', 'block');
        $('form#createBoard').before('<div class="modalwrap"></div>');
    });

	$('input[name="boardtype"]').change(function() {
		var selectedValue = $(this).val();

		if (selectedValue === '4') {
			$('.purpose').css('display', 'none'); // 개설 목적 설명 숨기기
		} else {
			$('.purpose').css('display', 'block'); // 개설 목적 입력란 보이기
			$('#createBoard > div.purpose').css('display', 'flex');
		}
	});

	$('#createBoard > input.button').click(function() {
		let checktype = $('input[type="radio"]:checked');
		let boardTypeValue = checktype.val();
		let checkname = $('input[name="name"]').val();
		let checkpurpose = $('input[name="purpose"]').val();
		let checkanony = $('label.custom-checkbox-label > input');

		if (checktype.length === 0) {
			alert("생성할 게시판의 유형을 선택하세요.");
			return false;
		}

		if ($.trim(checkname) == "") {
			alert("생성할 게시판의 이름을 입력하세요.");
			$('input[name="name"]').val('').focus();
			return false;
		}

		if (boardTypeValue !== '4') {
			if ($.trim(checkpurpose) == "") {
				alert("생성할 게시판의 개설 목적을 입력하세요.");
				$('input[name="purpose"]').val('').focus();
				return false;
			}
		}

		if (checkanony.is(':checked')) {
			if (confirm('익명 게시판을 새롭게 개설하시겠습니까?')) {
				createBoard(1);
			} else {
				return false;
			}
		}
		else {
			if (confirm('닉네임 게시판을 새롭게 개설하시겠습니까?')) {
				createBoard(0);
			} else {
				return false;
			}
		}
	});

	// 모달 닫기 버튼을 클릭할 때
    $('a.close').click(function() {
        $('form#createBoard').css('display', 'none');
        $('div.modalwrap').remove();
    });
});