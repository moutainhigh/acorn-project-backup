<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>登录页</title>
</head>
<jsp:include page="../common/base.jsp" />

<body>
<% 

String error = request.getParameter("error");
if("1".equals(error)){
	%>
		<span>用户名或者密码错误！</span>
	<%
}
%>

	<form name="f" action="j_spring_security_check" method="POST">
		<table>
			<tr>
				<td>用户名:</td>
				<td><input type='text' name='username' value="" /></td>
			</tr>
			<tr>
				<td>密&nbsp;&nbsp;码:</td>
				<td><input type='password' name='password' value=""></td>
			</tr>
			<tr>
				<td colspan='2'><input value="登录" type="submit"></td>
			</tr>
		</table>
	</form>

</body>
</html>
