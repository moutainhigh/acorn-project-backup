<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><decorator:title default="默认title" /></title>
	<%@ include file="/static/common/meta.jsp" %>
	<%@ include file="/static/common/resources.html" %>
	<script type="text/javascript">
		var ctx = '${ctx}';
	</script>
	<decorator:head />
</head>

<body>
	<!-- 从被装饰页面获取body标签内容 -->
	<decorator:body />
</body>
</html>
