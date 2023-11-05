<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">
<head>
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/image/common/nav.logo.png">
<title>애니타임</title>
<meta charset="utf-8">
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/common.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/common.partial.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/container.modal.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/calculator/calculator.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/credit/extensions.jquery-1.10.2.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/credit/extensions.underscore-min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/credit/credit.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body style="">

	<jsp:include page="../common/header.jsp" />

	<div id="container" class="calculator" style="overflow: hidden;">
		<input type="hidden" id="userId" value="542509"> <input
			type="hidden" id="userRequiredCredit" value="150"> <input
			type="hidden" id="userGradeType" value="">
		<aside class="none">
			<div class="title">
				<a class="hamburger"></a>
				<h1>학점 계산기</h1>
			</div>
		</aside>
		<div class="section">
			<div class="chart" style="padding-bottom: 0; margin-bottom: 0;">
				<article class="overview" >
					<div class="column gpa" >
						<h4>전체 평점</h4>
						<p class="value">${totalgpa}</p>
						<p class="total">/ ${school.credit}</p>
					</div>
					<div class="column major">
						<h4>전공 평점</h4>
						<p class="value">${totalmajor}</p>
						<p class="total">/ ${school.credit}</p>
					</div>
					<div class="column acquisition">
						<h4>취득 학점</h4>
						<p class="value">${totalAcquisition}</p>
						<p class="total" title="졸업 학점 설정">/ ${credit.graduate_credit}
						</p>
					</div>
				</article>
				<article class="semester" style="margin-bottom: -60px; padding: 0;">
					<div class="series">

						<div class="legend">
							<table style="font-size: smaller; color: #545454">
								<tbody>
									<tr>
										<td class="legendColorBox"><div
												style="border: 1px solid transparent; padding: 1px">
												<div
													style="width: 4px; height: 0; border: 5px solid #7869e6; overflow: hidden"></div>
											</div></td>
										<td class="legendLabel"><span style="color: #7869e6">전체</span></td>
										<td class="legendColorBox"><div
												style="border: 1px solid transparent; padding: 1px">
												<div
													style="width: 4px; height: 0; border: 5px solid rgb(166, 166, 166); overflow: hidden"></div>
											</div></td>
										<td class="legendLabel"><span
											style="color: rgb(166, 166, 166)">전공</span></td>
									</tr>
								</tbody>
							</table>
						</div>


						<div id="plot" class="plot"
							style="padding: 0px; position: relative;">
							<div class="chart-view" data-v-6a145b43="">
								<canvas id="myChart" width="365" height="150"
									style=" box-sizing: border-box; height: 274px; width: 100px;"></canvas>
							</div>
							<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
							<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
							<script>
								
							   var labelData = [
							        <c:forEach var="semestername" items="${semestername}" varStatus="i">
							            '${semestername.semester_name}'<c:if test="${!i.last}">,</c:if>
							        </c:forEach>
							        ];
							    
							   var gpaData = [
							        <c:forEach var="gpaMap" items="${gpa}" varStatus="i">
							            ${gpaMap.Gpa}<c:if test="${!i.last}">,</c:if>
							        </c:forEach>
							        ];

							    var majorData = [
							        <c:forEach var="gpaMap" items="${gpa}" varStatus="i">
							            ${gpaMap.Major}<c:if test="${!i.last}">,</c:if>
							        </c:forEach>
							        ];
							    
								var ctx = document.getElementById('myChart')
										.getContext('2d');
                              
								// 실제 데이터와 설정을 추가하여 data 변수 정의
								var data = {
									labels : labelData, // X축 레이블
									datasets : [
											{
												label : '전체',
												data :gpaData, // Y축 데이터
												borderColor : '#7869e6',
												backgroundColor : '#7869e6',
												yAxisID : 'y'
											},
											{
												label : '전공',
												data :majorData, // Y축 데이터
												borderColor : 'rgb(166, 166, 166)',
												backgroundColor : 'rgb(166, 166, 166)',
												yAxisID : 'y'
											} ]
								};

								var myChart = new Chart(ctx, {
									type : 'line',
									data : data,
									options : {
										responsive : true,
										interaction : {
											mode : 'index',
											intersect : false
										},
										stacked : false,
										plugins : {
										},
										scales : {
											y : {
												type : 'linear',
												display : true,
												position : 'left'
											},
											y1 : {
												type : 'linear',
												display : false,
												position : 'right',
												grid : {
													drawOnChartArea : false
												}
											}
										}
									}
								});
							</script>
						</div>
					</div>
					
					
						<div id="donutchart" style="  width: 380px; height: 250px; position:relative; top:-60px;">
						 <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load("current", {packages:["corechart"]});
      google.charts.setOnLoadCallback(drawChart);
      function drawChart() {
    	  var data = new google.visualization.DataTable();
    	    data.addColumn('string', 'Grade');
    	    data.addColumn('number', 'Count');
    	    
    	    <c:forEach var="row" items="${gradeData}">
    	    data.addRow(['${row.GRADE}', ${row.COUNT}]);
    	</c:forEach>
    	
        var options = {
          title: '학점 비율',
          pieHole: 0.35,
          colors: ['rgb(242, 133, 114)', 'rgb(236, 197, 92)', 'rgb(160, 198, 97)', 'rgb(130, 209, 194)', 'rgb(122, 158, 224)'],
          pieSliceText: "label",
          chartArea:{left:'10%',right:'10%',top:'10%',width:'70%',height:'70%'},
         titleTextStyle: {fontSize: 13}
        };

        var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
        chart.draw(data, options);
       
      }
     	</script>
      </div>
	

</article>
</div>
			<div class="menu">
				<ol>
					<c:forEach var="semester" items="${semesters}">
						<li class="" data-id="${semester.semester_id}"><a>${semester.semester_name}</a></li>
					</c:forEach>
				</ol>
			</div>

			<table class="subjects">
				<caption>
					<h3></h3>
					<dl class="information">
						<dt>평점</dt>
						<dd class="gpa"></dd>
						<dt>전공</dt>
						<dd class="major"></dd>
						<dt>취득</dt>
						<dd class="acquisition"></dd>
					</dl>
					<a class="import" style="display: inline;">시간표 불러오기</a>
				</caption>
				<thead>
					<tr>
						<th class="name">과목명</th>
						<th class="credit">학점</th>
						<th class="grade">성적</th>
						<th class="major">전공</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4"><a class="new">더 입력하기</a> <a class="reset">초기화</a>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
		<hr>
		<form id="importForm" class="modal"
			style="margin-left: -200px; margin-top: -93px; display: none;"
			data-gtm-form-interact-id="0">
			<a title="닫기" class="close"></a>
			<h3>내 시간표 가져오기</h3>
			<p>
				<label>시간표 선택</label> 
				<select name="semester" value=""></select> 
			</p>
			<input type="submit" value="가져오기" class="button">
		</form>
		<form id="requiredCreditForm" class="modal"
			style="display: none; margin-left: -200px; margin-top: -92.5px;">
			<a title="닫기" class="close"></a>
			<h3>졸업 학점 설정</h3>
			<p>
				<label>졸업 학점</label> <input type="number" name="required_credit"
					maxlength="3" class="text" value="${credit.graduate_credit}">
			</p>
			<input type="submit" value="저장" class="button">
		</form>
	</div>

</body>
</html>