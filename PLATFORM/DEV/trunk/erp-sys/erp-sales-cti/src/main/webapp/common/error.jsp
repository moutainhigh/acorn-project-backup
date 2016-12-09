<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.chinadrtv.erp.exception.ExceptionConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
    <style type="text/css">
        .errorWrap{text-align: center;margin: 100px 0;}
        .errorTitle{padding:5px 0;margin:0;font-family: 'microsoft yahei'; color:#666;border-bottom: 1px #8DCCA1 solid;}
        .errorBody{font-family: 'microsoft yahei';font-size: 12px;color:#666;}
    </style>
</head>
<body>
<div class="errorWrap"><img src="/static/images/error.png"/></div>
<p class="errorTitle">错误信息</p>
<div class="errorBody">
<%=exception.getMessage() %> <br><br>
<%
Logger logger = Logger.getLogger(ExceptionConstant.class);
logger.error("捕获到JSP页面异常", exception);
exception.printStackTrace();
%>
</div>
</body>
</html>