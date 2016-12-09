<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head> 
		<title>MyEclipse Sample Report Access Webpage</title>    
	</head>
  
	<body>
		<br />
		<table border="1">
			<c:forEach items="${ reports }" var="report" varStatus="status">
			<tr>
				<td>${ status.index+1 }</td>
				<td>${ report }</td>
			</tr>
			</c:forEach>

		</table>
 	</body>
 	
</html>