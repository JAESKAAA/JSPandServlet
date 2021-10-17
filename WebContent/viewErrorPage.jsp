<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage ="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에러 페이지출력</title>
</head>
<body>

요청하신 처리 과정에서 에러가 발생하였습니다.<br>
빠른 시간내에 처리하도록 하겠습니다.<br>
<p>
에러타입 : <%=exception.getClass().getName() %><br>
에러 메시지: <b><%=exception.getMessage() %></b><br>
</body>
</html>