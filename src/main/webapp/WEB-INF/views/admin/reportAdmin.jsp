<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/post/postlist.css">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.7.0.js"></script>

</head>
<script>
	$(function() {
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");

		var order = 1;
		var page = 1;
		go(order);

		$(".order input[type=radio]").on('click', function() {
			$('.order input[type=radio]').not($(this)).prop('checked', false);
			order = $(this).val();

			if ($('.reportadmin .reportrequest > a').hasClass('active')) {
				go(order);
			} else if ('.reportadmin .reportrequest') {
				getList(order, page);
			}
		})
		$(".report_content").on('click', "input.content_reject", function(e) {
			$(".content_disabled_reason").val('반려');
			$(".content_disabled_reason").prop('readonly', true);
		});
		$(".report_content").on(
				'click',
				"input.content_disabled",
				function(e) {
					$(".content_disabled_reason").val('');
					$(".content_disabled_reason").attr('placeholder',
							'비공개 사유를 입력하세요.');
					$(".content_disabled_reason").prop('readonly', false);
				});

		$("input.member_reject").on('click', function(e) {
			$(".member_disabled_reason").val('반려');
			$(".member_disabled_reason").prop('readonly', true);
		});
		$("input.member_disabled").on('click', function(e) {
			$(".member_disabled_reason").val('');
			$(".member_disabled_reason").attr('placeholder', '정지 사유를 입력하세요.');
			$(".member_disabled_reason").prop('readonly', false);
		});

		$("div.center-block").hide();

		$("div.reportadmin div.menu a").click(function() {
			$("div.reportadmin a").removeClass("active");
			$("div.reportadmin li").removeClass("active");
			$(this).addClass("active");
		});

		$(".menu li.reportrequest a").click(function() {
			$("table.request").show();
			$("table.list").hide();
			$('input[type=radio]').prop('checked', false);
			$('input[name=latest]').prop('checked', true);
			$('ul.pagination').empty();
		});

		$(".menu li.reportlist").click(function() {
			$("table.list").show();
			$("table.request").hide();
			$("div.center-block").show();
			$('input[type=radio]').prop('checked', false);
			$('input[name=latest]').prop('checked', true);
			order = 1;
			getList(order, page);

		});

		$('.request').on(
				'click',
				'button.process',
				function(event) {
					event.preventDefault();
					$('form#reportprocess').css('display', 'block');
					$('form#reportprocess').before(
							'<div class="modalwrap"></div>');

					$('.content_reject').prop('checked', true);
					$('.member_reject').prop('checked', true);

					let user = $(this).parent().parent().find(
							'.profile span:nth-child(1)').text();
					$('#reportprocess tr:nth-child(3) input').val(user);

					let content_id = $(this).find("input[type=hidden]").val();
					$('#reportprocess input[type=hidden]').val(content_id);
				});

		$('#reportprocess')
				.on(
						'click',
						'a.button',
						function() {
							let content_id = $(
									'#reportprocess a.button input[type=hidden]')
									.val();
							let content_action = $(
									'#reportprocess .content_disabled_reason')
									.val();
							let user_action = $(
									'#reportprocess .member_disabled_reason')
									.val();
							let reported_id =$('#reportprocess .reported').val();

							$.ajax({
								type : "post",
								data : {
									content_id : content_id,
									content_action : content_action,
									user_action : user_action,
									reported_id : reported_id
								},
								url : "reportProcess",
								beforeSend : function(xhr) { // 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
									xhr.setRequestHeader(header, token);
								},
								success : function(rdata) {
									if (rdata > 0) {
										alert('신고가 처리되었습니다.');
										location.reload();
									}
								}
							})//ajax end
						})

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

		$('.list tbody').on(
				'click',
				'td',
				function() {
					$('form#reportInfo').css('display', 'block');
					$('form#reportInfo')
							.before('<div class="modalwrap"></div>');

					let writer = $(this).parent().find('td.writer').text();
					let note_date = $(this).parent().find('td.date').text();
					let date = note_date.substr(5, 5) + ' '
							+ note_date.substr(11, 5);
					date = date.replace('-', '/');

					let reporter = $(this).parent().find('td.reporter').text();
					let content = $(this).parent().find('input[name=content]')
							.val();
					let reason = $(this).parent().find('td.reason').text();
					let status = $(this).parent().find('td.status').text();
					let admin = $(this).parent().find(
							'input[name=admin_login_id]').val();

					$('form#reportInfo input.writer').val(writer);
					$('form#reportInfo input.date').val(date);
					$('form#reportInfo input.reporter').val(reporter);
					$('form#reportInfo textarea.content').val(content);
					$('form#reportInfo input.reason').val(reason);
					$('form#reportInfo input.status').val(status);
					$('form#reportInfo input.admin').val(admin);
				});

	});

	function go(order) {
		$
				.ajax({
					type : "get",
					data : {
						order : order
					},
					url : "reportListAdmin",
					dataType : "json",
					cache : false,
					success : function(rdata) {
						$('table.request tbody').empty();

						if (rdata != null) {
							var output = '';
							$(rdata)
									.each(
											function() {
												var reason = '';
												switch (this.reason) {
												case 1:
													reason = '게시판 성격에 부적절함';
													break;
												case 2:
													reason = '욕설/비하';
													break;
												case 3:
													reason = '음란물/불건전한 만남 및 대화';
													break;
												case 4:
													reason = '상업적 광고 및 판매';
													break;
												case 5:
													reason = '유출/사칭/사기';
													break;
												case 6:
													reason = '낚시/놀람/도배';
													break;
												case 7:
													reason = '정당/정치인 비하 및 선거운동';
													break;
												}
												output += '<tr><td colspan="4"><article>'
														+ '<a class="article" href="post/detail?post_id='
														+ this.post_ID
														+ '">'
														+ '<img src="/anytime/resources/image/common/profile.png" class="picture">'
														+ '<div class="profile"><span class="large">'
														+ this.login_ID
														+ '</span>'
														+ '<span class="large">'
														+ this.write_DATE
														+ '</span></div>'
														+ '<button class="process">처리<img src="/anytime/resources/image/admin/process.png" class="process">'
														+ '<input type="hidden" name="content_id" value="'+this.content_ID+'"><input type="hidden" name="admin_login_id" value="'+this.admin_LOGIN_ID+'"> </button><br><h2 class="medium">';
												if (this.subject != null) {
													output += this.subject;
												} else {
													output + '댓글 신고';
												}
												output += '</h2><p class="small">'
														+ this.content
														+ '</p><br><br>'
														+ '<p class="last">'
														+ this.board_NAME
														+ '</p></a></article></td></tr>'
														+ '<tr><td>신고 횟수 : '
														+ this.report_COUNT
														+ ' 회</td>'
														+ '<td colspan="2">신고사유 : '
														+ reason
														+ '</td><td>시간 : '
														+ this.note_DATE
														+ '</td></tr>';
											})//$(rdata.boardTotal).each(function() { end
							$('table.request tbody').append(output);
						} else {
							output = '<tr><td class="empty" colspan="9">신고 처리 요청이 없습니다</td></tr>';
							$('table.request tbody').append(output);
						}

					}
				})
	}//go function end

	function getList(order, page) {

		$
				.ajax({
					type : 'get',
					url : 'reportTotal',
					data : {
						order : order,
						page : page
					},
					success : function(rdata) {
						if (rdata != null) {
							$('table.list tbody').empty();
							$('ul.pagination').empty();
							let rownum = (page - 1) * 10 + 1;
							let output = "";
							$(rdata.reportTotal)
									.each(
											function() {
												var reason = '';
												switch (this.reason) {
												case 1:
													reason = '게시판 성격에 부적절함';
													break;
												case 2:
													reason = '욕설/비하';
													break;
												case 3:
													reason = '음란물/불건전한 만남 및 대화';
													break;
												case 4:
													reason = '상업적 광고 및 판매';
													break;
												case 5:
													reason = '유출/사칭/사기';
													break;
												case 6:
													reason = '낚시/놀람/도배';
													break;
												case 7:
													reason = '정당/정치인 비하 및 선거운동';
													break;
												}
												output += '<tr><input type="hidden" name="report_id" value="'+this.report_ID
								+ '"><td>'
														+ rownum
														+ '</td><td class="writer">'
														+ this.login_ID
														+ '</td><td class="count">'
														+ this.report_COUNT
														+ '</td><td class="reason">'
														+ reason
														+ '</td><input type="hidden" name="content" value="'
								+ this.content+'"><td class="reporter">'
														+ this.reporter_LOGIN_ID
														+ '</td><td class="date">'
														+ this.note_DATE
														+ '</td>';
												if (this.post_R_ACTION == null) {
													output += '<td class="continue status">처리 진행중</td>';
												} else {
													output += '<td class="status"> 게시글 : ';
													if (this.post_R_ACTION === '반려') {
														output += '반려, ';
													} else {
														output += '정지, ';
													}
													output += '사용자 : ';
													if (this.user_R_ACTION === '반려') {
														output += '반려, ';
													} else {
														output += '정지, ';
													}
												}
												output += '<input type="hidden" name="admin_login_id" ';
												if (this.post_R_ACTION != null)
													output += 'value="'
															+ this.admin_LOGIN_ID
															+ '"';
												output += '></td></tr>';
												rownum++;
											});
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
								output += '<li class="page-item"><li class="page-item hidden"><a class="page-link gray">'
										+ '이전&nbsp;&nbsp;</a></li>																';
							} else if (page > 1) {
								output = '<li class="page-item"><a href="javascript:getList('
										+ order
										+ ','
										+ beforepage
										+ ')" class="page-link">이전</a>&nbsp;</li>';
							}

							for (let i = startpage; i <= endpage; i++) {
								if (page == i) {
									output += '<li class="page-item active"><a class="page-link">'
											+ i + '</a></li>';
								} else {
									output += '<li class="page-item"><a href="javascript:getList('
											+ order
											+ ','
											+ i
											+ ')" class="page-link">'
											+ i
											+ '</a></li>';
								}
							}

							if (page >= maxpage) {
								output += '<li class="page-item"><a class="page-link gray hidden">&nbsp;다음</a></li>';
							} else if (page < maxpage) {
								output += '<li class="page-item"><a href="javascript:getList('
										+ order
										+ ','
										+ nextpage
										+ ')" class="page-link">&nbsp;다음</a></li>';
							}
							$('ul.pagination').append(output);
						} else {
							output = '<tr><td class="empty" colspan="9">신고 목록이 없습니다</td></tr>';
							$('table.list tbody').append(output);
						}
					}
				});//ajax end
	}//getList() end
</script>
<body>
	<!-- 
<sec:authorize access="isAnonymous()">
   <script>
     location.href = "${pageContext.request.contextPath}/member/login";
   </script>
</sec:authorize>
 -->
	<div id="wrapper">
		<jsp:include page="../common/left_admin.jsp" />
		<div class="reportadmin">
			<div class="menu">
				<ol>
					<li class="reportrequest menu"><a class="active">신고 처리 요청</a></li>
					<li class="reportlist menu"><a>신고 목록</a></li>
				</ol>
				<div class="order">
					<label> <input type="radio" name="latest" value="1" checked>
						최신순
					</label> <label> <input type="radio" name="mostReported" value="2">
						신고순
					</label>
				</div>
			</div>
			<div class="section report">
				<table class="request">
					<thead>
						<tr>
							<th colspan="4"></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				<table class="list" style="display: none;">
					<thead>
						<tr>
							<th class="rownum"></th>
							<th class="writer">작성자</th>
							<th class="count">신고 횟수</th>
							<th class="reason">신고 사유</th>
							<th class="reporter">신고자</th>
							<th class="date">신고 날짜</th>
							<th class="status">처리상태</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			<%-------------------------------- ▼페이지네이션▼ --------------------------------%>
			<div class="center-block" style="margin-left: -210px;">
				<ul class="pagination justify-content-center">
				</ul>
			</div>
		</div>
	</div>
	<div id="container">
		<form id="reportprocess" class="modal"
			style="margin-left: -200px; margin-top: -220.5px; display: none; width: 500px;">
			<a title="닫기" class="close"></a> <span class="new">신고 처리</span>
			<p></p>
			<table>
				<tr>
					<td>게시물</td>
					<td class="box">
						<div class="report_content">
							<label> <input type="radio" class="content_reject"
								name="report_content" value="1" checked> 반려
							</label> <label> <input type="radio" name="report_content"
								class="content_disabled" value="2"> 게시물 비공개
							</label>
						</div>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>비공개 사유</td>
					<td colspan="3" class="box"><input type="text" value="반려"
						ReadOnly style="width: 345px;" class="content_disabled_reason"></td>
				</tr>
				<tr>
					<td>작성자</td>
					<td class="box"><input type="text" class="first reported" readOnly></td>
					<td><div class="report_member">
							<label> <input type="radio" class="member_reject"
								name="report_member" value="1" checked> 반려
							</label> <label> <input type="radio" name="report_member"
								class="member_disabled" value="2"> 정지
							</label>
						</div></td>
				</tr>
				<tr>
					<td>정지 사유</td>
					<td colspan="5" class="box"><input type="text"
						class="first member_disabled_reason" value="반려" ReadOnly
						style="width: 345px;"></td>
				</tr>
			</table>
			<a class="button" data-toggle="modal">확인 <input type="hidden"
				name="content_id"></a> <input type="hidden"
				name="${_csrf.parameterName}" value="${_csrf.token}">
		</form>
		<form id="reportInfo" class="modal"
			style="margin-left: -240px; margin-top: -220.5px; display: none; width: 550px;">
			<a title="닫기" class="close"></a> <span class="info">신고 내용 상세보기</span>
			<p></p>
			<table style="width: 500px;">
				<tr>
					<td>작성자</td>
					<td class="box" style="width: 0px;"><input type="text"
						class="box writer" readOnly style="width: 100px;"></td>
					<td style="width: 75px; padding: 0 7px 0 10px;">신고 날짜</td>
					<td class="box" colspan="2"><input type="text"
						class="right date" readOnly style="width: 100px;"></td>
					<td style="width: 50px; padding: 0 2px 0 6px;">신고자</td>
					<td class="box"><input type="text"
						class="first right reporter" readOnly
						style="width: 80px; margin-right: 6px;"></td>
				</tr>
				<tr>
					<td>내 용</td>
					<td colspan="6" class="box"><textarea readOnly class="content"
							style="width: 420px;">
						</textarea></td>
				</tr>
				<tr>
					<td>신고사유</td>
					<td colspan="6" class="box"><input type="text"
						class="full reason" readOnly style="width: 420px;"></td>
				</tr>
				<tr>
					<td>처리 상태</td>
					<td colspan="4" class="box"><input type="text"
						class="first status" readOnly style="width: 270px;"></td>
					<td style="padding: 0;">담당자</td>
					<td colspan="2" class="box"><input type="text"
						class="right admin" readOnly
						style="width: 80px; margin-right: 10px;"></td>
				</tr>
			</table>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}">
		</form>
	</div>
</body>
</html>