<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파라미터를 얻어 보자</title>
</head>
<body>

<form action="/chap02/viewParameter.jsp" method="post">
이름: <input type="text" name="name" size="10"><br>
주소: <input type="text" address="address" size="30"><br>
좋아하는 동물: 
<input type="checkbox" name="pet" value="dog"> 강아지
<input type="checkbox" name="pet" value="cat"> 고양이
<input type="checkbox" name="pet" value="pig"> 돼지
<br>
<input type="submit" value="전송">
</form>
</body>
</html>