<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>座席状态管理</title>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link href="${ctx}/static/style/stat.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/style/easyui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/stat.js"></script>
<script type="text/javascript">
	var $_ctx = "${ctx}";
</script>
</head>
<body>
	<div  class="wrap">
        <div class="content">
            <div class="head">座席状态列表</div>
            <div class="grid_body">
                <table id="agentTable"></table>
            </div>
        </div>
	</div>
	<%--<script type="text/javascript" >--%>
        <%--$(function(){--%>
            <%--$('#wrap').width(950);--%>
            <%--if($.browser.msie) {--%>
                <%--$('#content').height(window.screen.availHeight-20);--%>
            <%--}else{--%>
                <%--$('#content').height( window.innerHeight-20);--%>
            <%--}--%>
        <%--})--%>
	<%--</script>--%>
</body>
</html>