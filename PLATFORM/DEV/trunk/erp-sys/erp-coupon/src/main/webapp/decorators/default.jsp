<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>

<html>
<head>
    <%@ include file="/common/meta.jsp" %>
    <title><decorator:title/> Marketing</title>
	<meta name="context_path" content="${pageContext.request.contextPath}" />
	<script type="text/javascript">
		var ctx = '${ctx}';
	</script>
    <script type="text/javascript" src="${ctx}/static/scripts/jquery/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="${ctx}/static/scripts/common-ui.js"></script>
    <script type="text/javascript" src="${ctx}/static/scripts/jquery/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/scripts/jquery/locale/easyui-lang-zh_CN.js"></script>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/scripts/jquery/themes/icon.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/scripts/jquery/themes/default/easyui.css">

    <link href="${ctx}/static/style/layout.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/static/style/style.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${ctx}/static/style/ms_edit.css"/>
     <link href="${ctx}/static/style/marketing_layout.css" rel="stylesheet" type="text/css" />
   
    <decorator:head/>
</head>
<body>

<!--关部-->
<div id="header">
	<div class="title fl" style="text-shadow:#D86318 1px 1px 0;font-size: 24px;color: white;font-weight: bold;">橡果国际CRM系统</div>
    <!--
    <div class="joinus mr50 fr"><span class="f12 fb"></span><input name="" type="button" value="退出" class="but bordernone tc "/></div>
    -->
</div>

<!--导航
<ul id="sddm">
	
</ul>
-->
<!--页面主体-->
<div id="maincontent">
<div id="sidebar">
  <jsp:include page="/common/left.jsp" />
</div>
<div id="content">
<decorator:body/> 
</div>
</div>
<!--版权-->

<div id="copyright" class="f12"> 橡果国际IT平台开发组支持
</div>

</body>
</html>
