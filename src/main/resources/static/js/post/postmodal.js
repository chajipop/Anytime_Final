	$(document).ready(function() {
		
		$('#manageMoim').click(function(){
			getBoardContentAjax();
			$('form#manageMoimForm').css('display', 'block');
			$('form#manageMoimForm').before('<div class="modalwrap"></div>');
			$('#manageMoimForm').show();
		});
		
		$('#transferButton').click(function(){
			$('form#manageMoimForm').css('display', 'none');
			$('#transferMoimForm').show();
		});
		
		$('#deleteButton').click(function(){
			$('form#manageMoimForm').css('display', 'none');			
			$('#deleteCheckForm').show();
		});		
		
		$('#updateButton').click(function(){
			
		});
		
		$('a.close').click(function() {
			$('#manageMoimForm').css('display', 'none');
			$('#transferMoimForm').css('display', 'none');
			$('#deleteCheckForm').css('display', 'none');
			$('div.modalwrap').remove();
		});
	
	});		
	function getBoardContentAjax(){
		var infoInput = $("input[name='info']");
		var urlParams = new URLSearchParams(window.location.search);
		var board_id = urlParams.get('board_id');
		$.ajax({
			url: "${pageContext.request.contextPath}/board/boardcontent",
			data: {
				"board_id": board_id,
			},
			dataType: "json",
			success: function (boardContentData){
				infoInput.val(boardContentData);
			}
		});
	}