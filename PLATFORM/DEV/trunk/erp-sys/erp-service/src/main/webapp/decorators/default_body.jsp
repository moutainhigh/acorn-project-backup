<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<html>
<head>
<%@ include file="/common/meta.jsp"%>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>

<link href="${ctx}/static/style/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/style/easyui.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/style/themes/icon.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}/static/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/browser.js"></script>
<script type="text/javascript" src="${ctx}/static/js/json.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/easyui-lang-zh_CN.js"></script>
<!--
<link type="text/css" rel="stylesheet" href="${ctx}/static/style/themes/icon.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/static/style/themes/default/easyui.css">
-->

<decorator:head />
</head>
<body style="background-color: transparent">
	<decorator:body />
</body>
</html>
