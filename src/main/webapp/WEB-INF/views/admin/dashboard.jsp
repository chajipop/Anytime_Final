<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/header_admin.jsp" />
<title>관리자 페이지 - 애니타임</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/admin.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/style.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Raleway:400,300,600,800,900"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/easy-pie-chart/2.1.6/jquery.easypiechart.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<style type="text/css">
.container {
	font-family: "맑은 고딕", 돋움, "Apple SD Gothic Neo", tahoma;
	display: grid;
	grid-template-columns: repeat(1, 160px);
	grid-gap: 47px;
	margin: auto 0 20px;
}

.container .box {
	font-family: "맑은 고딕", 돋움, "Apple SD Gothic Neo", tahoma;
	width: 225%;
	display: flex;
	margin-left: 25px;
}

.container .box h2 {
	font-family: "맑은 고딕", 돋움, "Apple SD Gothic Neo", tahoma;
	margin-left: 100px;
	display: block;
	text-align: center;
	color: #666;
	margin-top: 45px;
	font-size: 18px;
	color: #666;
	display: block;
	line-height: 25px;
}

.container .box .chart {
	font-family: "맑은 고딕", 돋움, "Apple SD Gothic Neo", tahoma;
	position: relative;
	width: 45%;
	height: 100%;
	text-align: center;
	font-size: 27px;
	line-height: 162px;
	height: 150px;
	color: #666;
}

.container .box canvas {
	font-family: "맑은 고딕", 돋움, "Apple SD Gothic Neo", tahoma;
	position: absolute;
	top: 0;
	left: -3px;
	width: 100%;
	width: 100%;
}

.daily {
	width: 60px;
	margin-bottom: 3px;
}

a:link, a:visited, a:active, a:hover {
	text-decoration: none; /* 밑줄 제거 */
	color: inherit; /* 기본 텍스트 컬러로 유지 */
}
</style>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="../common/left_admin.jsp" />
		<main id="main" class="main" style="margin-left: 0px;">
			<section class="section dashboard" style="margin-top: 30px;">
				<div class="row">
					<!-- Left side columns -->
					<div class="col-lg-8">
						<div class="row">
							<c:set var="dataTrend" value="${dataTrend}" />
							<!-- Schools Card -->
							<div class="col-xxl-4 col-md-6">
								<div class="card info-card sales-card">
									<div class="card-body">
										<h5 class="card-title">
											Schools <span>| Today</span>
										</h5>
										<div class="d-flex align-items-center">
											<div
												class="card-icon rounded-circle d-flex align-items-center justify-content-center">
												<img class="daily"
													src="${pageContext.request.contextPath}/resources/image/admin/school.png"></img>
											</div>
											<div class="ps-3">
												<h6>${dataTrend.SCHOOLS}</h6>
												<c:choose>
													<c:when test="${dataTrend.SCHOOL_RATIO > 0}">
														<span class="text-success small pt-1 fw-bold data">${dataTrend.SCHOOL_RATIO}%</span>
														<span class="text-muted small pt-2 ps-1">increase</span>
													</c:when>
													<c:when test="${dataTrend.SCHOOL_RATIO == 0}">
														<span class="text-muted small pt-1 fw-bold data">${dataTrend.SCHOOL_RATIO}%</span>
														<span class="text-muted small pt-2 ps-1"></span>
													</c:when>
													<c:otherwise>
														<span class="text-danger small pt-1 fw-bold data">(${dataTrend.SCHOOL_RATIO * -1})%</span>
														<span class="text-muted small pt-2 ps-1">decrease</span>
													</c:otherwise>
												</c:choose>

											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- End Schools Card -->

							<!-- Posts Card -->
							<div class="col-xxl-4 col-md-6">
								<div class="card info-card revenue-card">
									<div class="card-body">
										<h5 class="card-title">
											Posts <span>| Today</span>
										</h5>

										<div class="d-flex align-items-center">
											<div
												class="card-icon rounded-circle d-flex align-items-center justify-content-center">
												<img class="daily"
													src="${pageContext.request.contextPath}/resources/image/admin/post.png"></img>
											</div>
											<div class="ps-3">
												<h6>${dataTrend.POSTS}</h6>
												<c:choose>
													<c:when test="${dataTrend.POST_RATIO > 0}">
														<span class="text-success small pt-1 fw-bold data">${dataTrend.POST_RATIO}%</span>
														<span class="text-muted small pt-2 ps-1">increase</span>
													</c:when>
													<c:when test="${dataTrend.POST_RATIO == 0}">
														<span class="text-muted small pt-1 fw-bold data">${dataTrend.POST_RATIO}%</span>
														<span class="text-muted small pt-2 ps-1"></span>
													</c:when>
													<c:otherwise>
														<span class="text-danger small pt-1 fw-bold data">(${dataTrend.POST_RATIO * -1})%</span>
														<span class="text-muted small pt-2 ps-1">decrease</span>
													</c:otherwise>
												</c:choose>

											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- End Posts Card -->

							<!-- Students Card -->
							<div class="col-xxl-4 col-xl-12">
								<div class="card info-card customers-card">
									<div class="card-body">
										<h5 class="card-title">
											Students <span>| Today</span>
										</h5>
										<div class="d-flex align-items-center">
											<div
												class="card-icon rounded-circle d-flex align-items-center justify-content-center">
												<img class="daily"
													src="${pageContext.request.contextPath}/resources/image/admin/user.png"></img>
											</div>
											<div class="ps-3">
												<h6>${dataTrend.MEMBERS}</h6>
												<c:choose>
													<c:when test="${dataTrend.MEMBER_RATIO > 0}">
														<span class="text-success small pt-1 fw-bold data">${dataTrend.MEMBER_RATIO}%</span>
														<span class="text-muted small pt-2 ps-1">increase</span>
													</c:when>
													<c:when test="${dataTrend.MEMBER_RATIO == 0}">
														<span class="text-muted small pt-1 fw-bold data">${dataTrend.MEMBER_RATIO}%</span>
														<span class="text-muted small pt-2 ps-1"></span>
													</c:when>
													<c:otherwise>
														<span class="text-danger small pt-1 fw-bold data">(${dataTrend.MEMBER_RATIO * -1})%</span>
														<span class="text-muted small pt-2 ps-1">decrease</span>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- End Students Card -->

							<!-- Students Registration Trend -->
							<div class="col-12">
								<div class="card">
									<div class="card-body">
										<h5 class="card-title">
											Students Registration Trend <span>| Week</span>
										</h5>
										<!-- Stack Chart -->
										<div id="registrationChart"
											style="min-height: 365px; text-align: -webkit-center;">
											<div style="padding: 10px">
												<canvas id="stackChart" style="width: 110px; height: 50px;"></canvas>
											</div>
										</div>
										<!-- End Stack Chart -->
									</div>
								</div>
							</div>
							<!-- End Students Registration Trend -->

							<div class="col-6">
								<!-- Boards Ranking -->
								<div class="card">
									<div class="card-body">
										<h5 class="card-title">
											Boards Ranking <span>| Today</span>
										</h5>
										<div class="activity">
											<div id="table_board"></div>
										</div>
									</div>
								</div>
							</div>
							<!-- To do List -->
							<div class="col-6">
								<div class="card">
									<div class="card-body">
										<h5 class="card-title">
											To do List <span>| Today</span>
										</h5>
										<div class="activity">
											<div class="container">
												<div class="box">
													<div class="chart" data-percent="${todoList.BOARD_PERCENT}"
														data-scale-color="#ffb400">${todoList.BOARD_PERCENT}%</div>
													<h2>
														<a href="${pageContext.request.contextPath}/boardAdmin">
															게시판 승인 <br> <br>${todoList.DONE_BOARD}/${todoList.TO_DO_BOARD}
														</a>
													</h2>
												</div>
												<div class="box">
													<div class="chart"
														data-percent="${todoList.REPORT_PERCENT}"
														data-scale-color="#ffb400">${todoList.REPORT_PERCENT}%</div>
													<h2>
														<a href="${pageContext.request.contextPath}/reportAdmin">
															신고 처리<br> <br>${todoList.DONE_REPORT}/${todoList.TO_DO_REPORT}
														</a>
													</h2>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- End To do List -->
						</div>
					</div>
					<!-- End Left side columns -->

					<!-- Right side columns -->
					<div class="col-lg-4">

						<!-- School Ranking  Activity -->
						<div class="card">
							<div class="card-body">
								<h5 class="card-title">
									School Ranking <span>| Today</span>
								</h5>
								<div class="activity">
									<div id="table_school"></div>
								</div>
							</div>
						</div>
						<!-- End Recent Activity -->

						<!-- Report Analysis -->
						<div>
							<div class="card">
								<div class="card-body pb-0">
									<h5 class="card-title">
										Report Analysis <span>| Week</span>
									</h5>
									<div id="reportChart"
										style="min-height: 365px; user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"
										class="echart" _echarts_instance_="ec_1698273089873">
										<div id="piechart"
											style="width: 100%; height: 480px; margin-top: -20px; text-align: -webkit-center;"></div>
									</div>
								</div>
							</div>
						</div>
						<!-- End Report Analysis -->
					</div>
					<!-- End Right side columns -->
				</div>
			</section>
		</main>
	</div>
</body>

<script type="text/javascript">
$(function() {
	google.charts.load('current', {'packages':['corechart', 'table']});
	google.charts.setOnLoadCallback(function() {
	    drawTable();
	    drawReportReasonChart();
	    
	    function drawTable() {
	        var data_school = new google.visualization.DataTable();
	        data_school.addColumn('string', '학교명');
	        data_school.addColumn('number', '가입 학생 수');
	        data_school.addRows([
	            <c:forEach items="${schoolRanking}" var="schoolRanking" varStatus="loop">
	            ["${schoolRanking.name}",  {v: ${schoolRanking.total_count}, f: '${schoolRanking.total_count} 명 '}]
	             <c:if test="${!loop.last}">, </c:if>
	            </c:forEach> 
	        ]);

	        var table_school = new google.visualization.Table(document.getElementById('table_school'));
	        table_school.draw(data_school, {showRowNumber: true, width: '100%', height: '110%'});
	        

	        var data_table = new google.visualization.DataTable();
	        data_table.addColumn('string', '학교명');
	        data_table.addColumn('string', '게시판명');
	        data_table.addColumn('number', '새 게시물');
	        data_table.addRows([
	            <c:forEach items="${boardRanking}" var="boardRanking" varStatus="loop">
	            ["${boardRanking.SCHOOL_NAME}", "${boardRanking.NAME}",${boardRanking.NEW_POST}]
	             <c:if test="${!loop.last}">, </c:if>
	            </c:forEach> 
	        ]);

	        var table_board = new google.visualization.Table(document.getElementById('table_board'));
	        table_board.draw(data_table, {showRowNumber: true, width: '100%', height: '100%'});
        }
    });
    
    $('.chart').easyPieChart({
	    size: 130,
	    barColor: "#7868E6",
	    scaleLength: 0,
	    lineWidth: 13,
	    trackColor: "#d6d6d6",
	    lineCap: "circle",
	    animate: 2000,
	  });
    
    
    // Students Registration Trend - Stack Chart
    var stackChartCanvas = document.getElementById('stackChart');

      const labels = [
    	  <c:forEach items="${registrationTrend}" var="registrationTrend" varStatus="loop">
    	  	"${registrationTrend.DAY_STRING}"<c:if test="${!loop.last}">, </c:if>
    	  </c:forEach>
    	  ]; //day
      const stackdata = {
    		  labels: labels,
    		  datasets: [
    		    {
    		      label: '기존 회원 수',
    		      data: [
    		        <c:forEach items="${registrationTrend}" var="registrationTrend" varStatus="loop">
    		          ${registrationTrend.PRE}<c:if test="${!loop.last}">, </c:if>
    		        </c:forEach>
    		      ],
    		      backgroundColor: '#7F82FF',
    		      borderColor: '#fff',
    		      borderWidth: 3,
    		      stack: 'Stack 0',
    		    },
    		    {
    		      label: '신규 회원 수',
    		      data: [
    		        <c:forEach items="${registrationTrend}" var="registrationTrend" varStatus="loop">
    		          ${registrationTrend.NEW_MEMBERS}<c:if test="${!loop.last}">, </c:if>
    		        </c:forEach>
    		      ],
    		      backgroundColor: '#29CCEF',
    		      borderColor: '#fff',
    		      borderWidth: 3,
    		      stack: 'Stack 0',
    		    },
    		    {
    		      label: '탈퇴 회원 수',
    		      data: [
    		        <c:forEach items="${registrationTrend}" var="registrationTrend" varStatus="loop">
    		          ${registrationTrend.WITHDRAWN_MEMBERS * -1}<c:if test="${!loop.last}">, </c:if>
    		        </c:forEach>
    		      ],
    		      backgroundColor: '#00DFA2',
    		      borderColor: '#fff',
    		      borderWidth: 3,
    		      stack: 'Stack 1',
    		    },
    		  ],
    		};
      const stackChart = new Chart(stackChartCanvas, {
    	  type: 'bar',
    	  data: stackdata,
    	  options: {
    	    plugins: {
    	      title: {
    	        display: false,
    	        text: 'Chart.js Bar Chart - Stacked',
    	      },
    	    },
    	    responsive: true,
    	    interaction: {
    	      intersect: false,
    	    },
    	    scales: {
    	      x: {
    	        stacked: true,
    	        grid: {
    	          display: false,
    	        },
    	      },
    	      y: {
    	        stacked: true,
    	        grid: {
    	          display: true,
    	          drawTicks: true,
    	        },
    	        ticks: {
    	          stepSize: 3,
    	        },
    	      },
    	    },
    	    maxBarThickness: 30,
    	    categorySpacing: 10,
    	  },
    	});

	});
   ////////////////////////////////////////////////////////////////////////////////////////////////
      function drawReportReasonChart() {
        var reportReasonData = google.visualization.arrayToDataTable([
          ['신고 사유', '일별 신고 건수'],
        	<c:forEach items="${reportCount}" var="reportData" varStatus="loop">
          	[ 
	          	 <c:choose>
		             <c:when test ="${reportData.REASON eq '1'}">'게시판 성격에 부적절함'		</c:when>
		             <c:when test ="${reportData.REASON eq'2'}">'욕설/비하'				</c:when>
		             <c:when test ="${reportData.REASON eq'3'}">'음란물/불건전한 만남 및 대화'	</c:when>
		             <c:when test ="${reportData.REASON eq'4'}">'상업적 광고 및 판매'			</c:when>
		             <c:when test ="${reportData.REASON eq'5'}">'유출/사칭/사기'				</c:when>
		             <c:when test ="${reportData.REASON eq'6'}">'낚시/놀람/도배'				</c:when>
		             <c:when test ="${reportData.REASON eq'7'}">'정당/정치인 비하 및 선거운동'	</c:when>
	             </c:choose>
	        	  , ${reportData.COUNT_BY_REASON}
        	] <c:if test="${!loop.last}">, </c:if>
          </c:forEach>
        ]);
        var reportReasonOptions = {
        		chartArea:{
        			top:40,
        			left:40,
        			right:40,
        		},
         		title: {
        			display: false,
         		},
         		 colors: ['#7C83FD','#829DFF', '#96BAFF', '#7DEDFF', '#88FFF7', '#91FFE6','#87FFCE'],
         		legend: {
         		    position: 'bottom', // 범례 위치를 바닥으로 설정
         		    maxLines:4,
         		  }
        };

        var reportReasonChart = new google.visualization.PieChart(document.getElementById('piechart'));

        reportReasonChart.draw(reportReasonData, reportReasonOptions);
      }
    </script>
</html>
