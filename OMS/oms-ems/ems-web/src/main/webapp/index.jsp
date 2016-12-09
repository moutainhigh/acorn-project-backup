
<%@page import="org.slf4j.Logger"%>
<%@page import="org.slf4j.LoggerFactory"%><html>
<body>
	<h2>Hello PAFF!</h2>
	<a href="<%=request.getContextPath() %>/hello">Hello!</a>
	<% 
	//test
	Logger logger = LoggerFactory.getLogger("cip.biz");
	logger.info("bizInfo***bizInfo***bizInfo***bizInfo***bizInfo***bizInfo***bizInfo***bizInfo***bizInfo***bizInfo***bizInfo***bizInfo***bizInfo***bizInfo***bizInfo***bizInfo***bizInfo***bizInfo***bizInfo***");
	logger.error("error***error***error***error***error***error***error***error***error***error***");
%>
</body>
</html>
