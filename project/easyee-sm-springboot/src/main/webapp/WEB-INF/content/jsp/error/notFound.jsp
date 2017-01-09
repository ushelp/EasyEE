<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
if (request != null && ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))
		|| "XMLHttpRequest".equalsIgnoreCase(request.getParameter("xRequestedWith")))) {
	// AJax请求处理
	// 没有权限，返回错误信息，无须存入session和request
	response.setCharacterEncoding("UTF-8");
	response.setContentType("text/json;charset=utf-8");
	out.println("{\"msg\":\"请求不存在！\",\"statusCode\":\"404\"}");
	out.flush();
	out.close();
	return;
}%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>请求不存在</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <h1 align="center">404，Page Not Found!</h1>
    <p align="center">
    <a href=""  style="color: #FF8400">返回</a>
    </p>
  </body>
</html>
