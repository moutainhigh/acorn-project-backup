<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<html>
<head>
    <%@ include file="/common/meta.jsp" %>
    <title><decorator:title/> 前置订单处理</title>
	<script type="text/javascript">
		var ctx = '${ctx}';
	</script>
    <script type="text/javascript" src="${ctx}/static/scripts/jquery/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="${ctx}/static/scripts/common-ui.js"></script>
    <script type="text/javascript" src="${ctx}/static/scripts/jquery/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/scripts/jquery/locale/easyui-lang-zh_CN.js"></script>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/scripts/jquery/themes/icon.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/scripts/jquery/themes/default/easyui.css">

    <link rel="stylesheet"  href="${ctx}/static/style/reset.css">
    <link href="${ctx}/static/style/ms_layout.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${ctx}/static/style/ms_edit.css"/>
    <decorator:head/>
</head>
<body>
<!-- 顶部菜单 -->
<div id="wrap" style="min-width: 1024px;">
<div class="top_nav"><div class="w top_navc">
    
    <img alt="" src="/static/images/oms-logo.png" width="223" height="60">
	<span class="_date"></span>

    <a target="_self" href="/logout" class="outbutton">注销</a>
    <span class="comp">橡果科技有限公司</span>
    <span class="user"><c:out value="${sessionScope.omsusername}"></c:out></span>

</div></div>
<div id="main" class="w">
    <!-- logo -->
    <!--<div class="logoc"><a href="#" target="_self"></a></div>-->
    <!-- content -->
    
    	
        <div class="grid_260">
       	    <div class="tree">
           	     <!-- <div class="group"></div> -->
            	 <jsp:include page="/common/left.jsp" />
        	</div>
        	
        </div>
        
        <div class="grid_700 clearfix">
        <div id="handlebar" class="handlebar"><img alt="" src="/static/images/handleV.png" width="3" height="39"></div>
         <div class="currentPage"><span class="l"></span><span class="p"></span></div>
            <div class="rig_con"><decorator:body/>  </div>
        </div>
    

</div>
</div>
</body>
</html>
