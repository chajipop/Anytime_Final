<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/header_admin.jsp" />
<head>
<title>관리자 페이지 - 애니타임</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common/common.css">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/container.modal.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/admin.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common/modal.css">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.7.0.js"></script>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/post/postlist.css">
<script>
	$(function() {
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");

		var approvalStatus = 0;

		var page = 1; //페이지 변수를 1로 초기화
		var searchKey = 0;//검색기능을 위해 검색 변수 초기화
		var keyword = 'null';

		$("form#searchBoard").hide();
		$("div.center-block").hide();

		$("div.boardadmin div.menu a").click(function() {
			$("div.boardadmin a").removeClass("active");
			$(this).addClass("active");
		});

		$(".menu li.boardrequest").click(function() {
			$("table.request").show();
			$("table.list").hide();
			$("form#searchBoard").hide();
			$("#search_word").val('');
			$('ul.pagination').empty();
		});

		$(".menu li.boardlist").click(function() {
			page = 1; //페이지 변수를 1로 초기화
			searchKey = 0;//검색기능을 위해 검색 변수 초기화
			keyword = 'null';

			$("table.list").show();
			$("table.request").hide();
			$("form#searchBoard").show();
			$("div.center-block").show();
			boardTotalList(page, searchKey, keyword);
		});

		$(document).on(
				"click",
				"a.approval",
				function() {
					$('form#approvalreview').css('display', 'block');
					$('form#approvalreview').before(
							'<div class="modalwrap"></div>');
					var board_id = $(this).parent().parent().parent().find(
							'input.approval').val();
					$('form#approvalreview input[name="board_id"]').val(
							board_id);
				});

		// 모달 닫기 버튼을 클릭할 때
		$('a.close').click(function() {
			$('form.modal').css('display', 'none');
			$('div.modalwrap').remove();
		});

		$('#approvalreview > input.button').click(function() {
			let checkreason = $('input[name="rejectreason"]').val();

			if ($.trim(checkreason) == "") {
				alert("게시판 승인 거부 사유를 입력하세요.");
				$('input[name="rejectreason"]').val('').focus();
				return false;
			}
		});

		var clickFlag = true;
		$('table.list').off().on("click", "td.switch", function() {
			if (clickFlag) {
				clickFlag = false; // 클릭 이벤트 비활성화
				var board_id = $(this).children().find('input.approval').val();

				$.ajax({
					type : "get",
					url : 'updateBoardStatusImmediately',
					data : {
						board_id : board_id
					},
					success : function(rdata) {
						if (rdata == 1) {
							alert('상태 변경 완료')
						}
					}
				})
			}
		});

		$('table.list')
				.on(
						"click",
						"td:not(.switch)",
						function() {
							$('form#boardInfo').css('display', 'block');
							var board_id = $(this).parent().find(
									'td.switch input.approval').val();
							var school_name = $(this).parent().find(
									'td:nth-child(2)').text();
							var type = $(this).parent().find('td:nth-child(3)')
									.text();
							var name = $(this).parent().find('td:nth-child(4)')
									.text();
							var purpose = $(this).parent().find(
									'td:nth-child(5)').text();
							var manager = $(this).parent().find(
									'td:nth-child(6)').text();
							$('form#boardInfo input.school_name').val(
									school_name);
							$('form#boardInfo input.type').val(type);
							$('form#boardInfo input.name').val(name);
							$('form#boardInfo input.purpose').val(purpose);
							$('form#boardInfo input.manager').val(manager);
							$('form#boardInfo .switch input').val(board_id);

							if ($(this).parent().find(
									'td.switch input.approval').is(':checked')) {
								$('form#boardInfo .switch input').prop(
										'checked', true);
							} else {
								$('form#boardInfo .switch input').prop(
										'checked', false);
							}
							$('form#boardInfo').before(
									'<div class="modalwrap"></div>');
						});

		$('#boardInfo .switch input')
				.on(
						"change",
						function() {
							var board_id = $(this).parent().find('input').val();
							$
									.ajax({
										type : "get",
										url : 'updateBoardStatusImmediately',
										data : {
											board_id : board_id
										},
										success : function(rdata) {
											if (rdata == 1) {
												alert('상태 변경 완료')

												$('table.list input.approval')
														.each(
																function() {
																	// input 요소의 value 값을 가져옵니다.
																	var value = $(
																			this)
																			.val();

																	if (value == board_id) {
																		$(this)
																				.prop(
																						'checked',
																						!$(
																								this)
																								.prop(
																										'checked'));
																	}
																});
											}
										}
									})
						})

		$("#approvalreview")
				.submit(
						function() {
							var approvalStatus = 4;
							var rejectionreason = $(
									"form#approvalreview input.rejectreason")
									.val();
							var board_id = $(
									'#approvalreview input[name="board_id"]')
									.val();
							updateBoardStatus(board_id, approvalStatus,
									rejectionreason);
							e.preventDefault();
							location.reload();
						});// $("#approvalreview").submit end

		$("table.request input.approval")
				.on(
						"change",
						function() {
							var rejectionreason = ''
							var $this = $(this).parent().parent().siblings(
									'.approvalprocess');
							if (this.checked) {
								approvalStatus = 3;
								var board_id = $(this).val();
								$this.removeClass('pending reject');
								$this.find('p').text('승인완료');
								$this.find('img').remove();
								$this.addClass('complete');
							} else {
								approvalStatus = 0;
								var board_id = $(this).val();
								$this.removeClass('complete');
								$this.find('p').text('승인보류');
								var img = new Image();
								img.src = "${pageContext.request.contextPath}/resources/image/admin/approval.cancel.png";
								img.onload = function() {
									$this
											.find('p')
											.append(
													'<a class="approval"><img src="' + img.src + '"></a>');
								};
								$this.addClass('pending');
							}
							updateBoardStatus(board_id, approvalStatus,
									rejectionreason);
						});

		$('#searchButton').click(function() {
			// <select> 요소에서 선택된 옵션의 value 값을 가져옵니다.
			searchKey = $("#search_field").val();
			keyword = $('#search_word').val();

			$('table.list tbody').empty();
			$('ul.pagination').empty();
			boardTotalList(page, searchKey, keyword);
			//e.preventDefault();
		})

	});

	function updateBoardStatus(board_id, approvalStatus, rejectionreason) {
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			type : "post",
			url : 'updateBoardStatus',
			data : {
				board_id : board_id,
				approvalStatus : approvalStatus,
				rejectionreason : rejectionreason
			},
			beforeSend : function(xhr) { // 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
				xhr.setRequestHeader(header, token);
			},
			success : function(rdata) {
				if (rdata == 1) {
					alert('승인처리완료');
				}
			}
		});
	}

	function boardTotalList(page, searchKey, keyword) {
		var output = '';
		$
				.ajax({
					type : "get",
					url : 'boardtotal',
					data : {
						page : page,
						searchKey : searchKey,
						keyword : keyword
					},
					dataType : "json",
					success : function(rdata) {
						$('table.list tbody').empty();
						$('ul.pagination').empty();

						if (rdata.boardTotal.length > 0) {
							let output = "";
							let rownum = 1;
							keyword = "'" + keyword + "'";
							$(rdata.boardTotal)
									.each(
											function() {
												let board_type = '';
												if (this.type == 1) {
													board_type = '일반';
												} else if (this.type == 2) {
													board_type = '단체';
												} else if (this.type == 3) {
													board_type = '학과';
												} else {
													board_type = '커스텀';
												}
												output += '<tr><td>' + rownum
														+ '</td><td>'
														+ this.school_NAME
														+ '</td><td>'
														+ board_type
														+ '</td><td>'
														+ this.board_NAME
														+ '</td><td>							';
												if (this.purpose != null) {
													output += this.purpose;
												} else {
													output += '';
												}
												output += '</td><td>'
														+ this.login_ID
														+ '</td><td class="switch"><label class="switch">	'
												if (this.status == 1) {
													output += '<input type="checkbox" class="approval checked" value="'+this.board_ID+'" checked>			';
												} else if (this.status == 2) {
													output += '<input type="checkbox" class="approval" value="'+this.board_ID+'">			';
												}
												output += '<span class="slider round"></span></label></td></tr>						';
												rownum++;
											})//$(rdata.boardTotal).each(function()
							$('table.list tbody').append(output);

							let maxpage = rdata.maxpage;
							let startpage = rdata.startpage;
							let endpage = rdata.endpage;
							let listcount = rdata.listcount;
							let limit = rdata.limit;

							nextpage = page + 1;
							beforepage = page - 1;

							output = '';

							if (page <= 1) {
								output += '<li class="page-item"><li class="page-item hidden"><a class="page-link gray first">'
										+ '이전&nbsp;&nbsp;</a></li>																';
							} else if (page > 1) {
								output = '<li class="page-item"><a href="javascript:boardTotalList('
										+ beforepage
										+ ','
										+ searchKey
										+ ','
										+ keyword
										+ ')" class="page-link first">이전</a>&nbsp;</li>';
							}

							for (let i = startpage; i <= endpage; i++) {
								if (page == i) {
									output += '<li class="page-item active"><a class="page-link">'
											+ i + '</a></li>';
								} else {
									output += '<li class="page-item"><a href="javascript:boardTotalList('
											+ i
											+ ','
											+ searchKey
											+ ','
											+ keyword
											+ ')" class="page-link">'
											+ i + '</a></li>';
								}
							}

							if (page >= maxpage) {
								output += '<li class="page-item"><a class="page-link gray hidden last">&nbsp;다음</a></li>';
							} else if (page < maxpage) {
								output += '<li class="page-item"><a href="javascript:boardTotalList('
										+ nextpage
										+ ','
										+ searchKey
										+ ','
										+ keyword
										+ ')" class="page-link last">&nbsp;다음</a></li>';
							}
							$('ul.pagination').append(output);
						}//if (rdata.boardTotal.length > 0) {
						else {
							$('table.list tbody').empty();
							output = '<tr><td class="empty" colspan="9">검색된 결과가 없습니다</td></tr>';
							$('table.list tbody').append(output);
							$('ul.pagination').empty();
						}
					}
				});
	};
</script>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="../common/left_admin.jsp" />
		<div class="boardadmin">
			<div class="menu">
				<ol>
					<li class="boardrequest menu"><a class="active">게시판 승인 요청</a></li>
					<li class="boardlist menu"><a>게시판 목록</a></li>
					<li class="search">
						<form id="searchBoard" class="search" accept-charset="UTF-8">
							<select name="search_field" id="search_field">
								<option value="0">전체</option>
								<option value="1">학교명</option>
								<option value="2">게시판명</option>
							</select> <input name="search_word" id="search_word"
								placeholder="검색어를 입력하세요." value="" type="text">
							<button type="button" id="searchButton">&nbsp;&nbsp;</button>
						</form>
					</li>
				</ol>
			</div>
			<div class="section">
				<table class="request">
					<thead>
						<tr>
							<th class="toggle"></th>
							<th class="school">학교명</th>
							<th class="type">유형</th>
							<th class="name">게시판 이름</th>
							<th class="purpose">게시판 개설 목적</th>
							<th class="manager">운영자</th>
							<th class="approval"></th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${fn:length(boardrequest) == 0}">
							<tr>
								<td class="empty" colspan="9">게시판 승인 요청이 없습니다</td>
							</tr>
						</c:if>
						<c:if test="${fn:length(boardrequest) > 0}">
							<c:forEach var="request" items="${boardrequest}">
								<tr>
									<td><c:choose>
											<c:when test="${request.STATUS ==3 }">
												<label class="switch"> <input type="checkbox"
													class="approval" value="${request.BOARD_ID }" checked>
													<span class="slider round"></span>
												</label>
											</c:when>
											<c:when test="${request.STATUS !=3 }">
												<label class="switch"> <input type="checkbox"
													class="approval" value="${request.BOARD_ID }"> <span
													class="slider round"></span>
												</label>
											</c:when>
										</c:choose></td>
									<td>${request.SCHOOL_NAME}</td>
									<td><c:choose>
											<c:when test="${request.TYPE ==2}">
											단체
										</c:when>
											<c:when test="${request.TYPE ==3}">
											학과
										</c:when>
										</c:choose></td>
									<td>${request.NAME}</td>
									<td>${request.PURPOSE}</td>
									<td>${request.LOGIN_ID}</td>
									<c:choose>
										<c:when test="${request.STATUS ==3 }">
											<td class="approvalprocess complete"><p>승인완료</p></td>
										</c:when>
										<c:when test="${request.STATUS ==0 }">
											<td class="approvalprocess pending"><p>
													승인보류<a class="approval"><img
														src="${pageContext.request.contextPath}/resources/image/admin/approval.cancel.png"></a>
												</p></td>
										</c:when>
										<c:when test="${request.STATUS == 4 }">
											<td class="approvalprocess reject"><p>승인거부</p></td>
										</c:when>
									</c:choose>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<table class="list" style="display: none;">
					<thead>
						<tr>
							<th class="rownum"></th>
							<th class="school">학교명</th>
							<th class="type">유형</th>
							<th class="name">게시판 이름</th>
							<th class="purpose">게시판 개설 목적</th>
							<th class="manager">운영자</th>
							<th class="toggle"></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			<%-------------------------------- ▼페이지네이션▼ --------------------------------%>
			<div class="center-block"
				style="margin-left: -225px; margin-top: 45px;">
				<ul class="pagination justify-content-center">
				</ul>
			</div>
		</div>
	</div>
	<div id="container">
		<form id="approvalreview" class="modal"
			style="margin-left: -200px; margin-top: -220.5px; display: none;">
			<a title="닫기" class="close"></a> <span class="new">게시판 승인 심사</span>
			<p></p>
			<label id="rejectreason">승인 거부 사유</label> <input type="text"
				name="rejectreason" placeholder="승인 거부 사유"
				class="text info rejectreason"> <input type="hidden"
				name="board_id"> <input type="submit" value="확인"
				class="button">
		</form>
		<form id="boardInfo" class="modal"
			style="margin-left: -200px; margin-top: -220.5px; display: none; width: 490px;">
			<a title="닫기" class="close"></a> <span class="info">게시판 정보</span>
			<p></p>
			<table>
				<tr>
					<td>학교명</td>
					<td class="box"><input type="text" class="first school_name"
						readOnly value="x대"></td>
					<td style="padding: 0 19px;">유형</td>
					<td class="box"><input type="text" class="first type" readOnly
						value="단체"></td>
					<td style="padding: 0 20px;">운영자</td>
					<td class="box"><input type="text" class="first manager"
						readOnly value="youni"></td>
				</tr>
				<tr>
					<td>게시판 이름</td>
					<td colspan="5" class="box"><input type="text" class="name"
						readOnly value="게게시피" style="width: 360px;"></td>
				</tr>
				<tr>
					<td>개설 목적</td>
					<td colspan="5" class="box purpose"><textarea readOnly>목적
						</textarea></td>
				</tr>
			</table>
			<label class="switch"> <input type="checkbox"> <span
				class="slider round"></span>
			</label>
		</form>
	</div>
</body>
</html>