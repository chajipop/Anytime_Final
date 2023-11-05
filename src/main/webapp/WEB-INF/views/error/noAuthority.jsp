<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv='refresh' content='5;url=<c:out value="${redirectUrl}"/>'> <!-- 5초 후에 지정된 URL로 리다이렉트 -->
<meta charset="UTF-8">
<title>권한 없음</title>

<script type="text/javascript">
window.onload = function() {
    var count = 5;
    var countdown = document.getElementById("countdown");

    setInterval(function() {
        count--;
        countdown.innerHTML = "리스트로 이동합니다. 현재 남은 시간: " + count + "초";
        if (count == 0) {
            window.location.href = '<c:out value="${redirectUrl}"/>';
        }
    }, 1000);
}
</script>

</head>
<body>
	<h1>권한이 없는 게시물입니다.</h1>
	<h2 id="countdown">리스트로 이동합니다.</h2>
</body>
</html>

