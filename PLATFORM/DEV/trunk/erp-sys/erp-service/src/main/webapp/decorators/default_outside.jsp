<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<html>
<head>
    <%@ include file="/common/meta.jsp" %>
    <title>第三方<decorator:title/></title>

    <script type="text/javascript" src="/static/scripts/jquery/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/locale/easyui-lang-zh_CN.js"></script>
    <link type="text/css" rel="stylesheet" href="/static/scripts/jquery/themes/icon.css"/>
    <link type="text/css" rel="stylesheet" href="/static/scripts/jquery/themes/default/easyui.css">

    <link rel="stylesheet"  href="/static/style/reset.css">
    <link href="/static/style/ms_layout.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="/static/style/ms_edit.css"/>
    <decorator:head/>
</head>
<body>
<!-- 顶部菜单 -->
<div class="top_nav"><div class="w top_navc">
    <a href="#" target="_self"></a>

</div></div>
<div class="w">
    <!-- logo -->
    <div class="logoc"><a href="#" target="_self"></a></div>
    <!-- content -->
    <div class=" mt10 clearfix">
        <div class="grid_260">
        </div>
        <div class="grid_700">
            <div class="rig_con"><decorator:body/>  </div>
        </div>
    </div>

</div>

</body>
</html>
