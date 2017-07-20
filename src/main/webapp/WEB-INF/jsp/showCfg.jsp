<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Configuration</title>
	<link href="<%=request.getContextPath() %>/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<center>
	<table class="table table-bordered table-hover" style="width:70%">
	   <caption>i_open_diction配置</caption>
	   <thead>
	      <tr>
	        <c:forEach var="cols" items="${result[0]}">
			   <th>${cols}</th>
			</c:forEach>
	      </tr>
	   </thead>
	   <tbody>
	      <c:forEach var="row" items="${result}" begin="1">
		      <tr>
		         <c:forEach var="col" items="${row}">
		           <td>${col}</td>
		         </c:forEach>
		      </tr>
		  </c:forEach>
	   </tbody>
	</table>
</center>
<script src="<%=request.getContextPath() %>/js/jquery-3.1.0.min.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
</body>
</html>