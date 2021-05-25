<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>메인페이지</h2>
	<h3>${sessionScope.loginName}</h3>
	<button onclick="location.href='joinform'">회원가입</button>
	<button onclick="location.href='loginform'">로그인</button>
</body>
</html>