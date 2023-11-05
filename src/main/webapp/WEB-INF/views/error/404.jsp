<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>404 오류</title>
	<style>
		div{text-align:center}
	</style>
</head>
<body>
	<div>
	${exception} <br>
	<img src="${pageContext.request.contextPath}/resources/image/error/404.png" width="300px"><br>
		요청하신 <b>${url}</b> 이 존재하지 않습니다.
	</div>
</body>
</html>