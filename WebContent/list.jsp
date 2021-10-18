<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = mini.service.GetMessageListService %>
    
   <%
   	String pageNumberStr = request.getParameter("page");
   int pageNumber = 1;
   if(pageNumberStr != null){
	   pageNumber = Integer.parseInt(pageNumberStr);
   }
   
   GetMessageListService messageListService = GetMessageListService.getInstance();
   %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>