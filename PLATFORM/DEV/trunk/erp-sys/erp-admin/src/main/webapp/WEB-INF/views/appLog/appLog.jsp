<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Google Phone Gallery</title>

    <script type="text/javascript" src="/static/scripts/appLog/controllers.js"></script>
</head>
<body>

<div style="padding: 1px;">
    <!--
        <label for="appName">系统名称:</label>
        <input id="appName" name="appName" size="10"/>
        -->
    <label for="funcName">功能名称:</label>
    <input id="funcName" name="funcName" ng-model="funcName" size="10"/>
    <label for="beginDate">开始时间:</label>
    <input id="beginDate"  ng-model="beginDate"  class="easyui-datebox"/>
    <label for="endDate">结束时间:</label></th>
    <input id="endDate" ng-model="endDate" type="text" class="easyui-datebox" size="50"/>

    <input type="button" name="queryAuditLogBtn" value='查找'
           id="queryAuditLogBtn" onclick="queryAuditData()"/>

</div>

<div style="padding: 3px;height: 350px">

    <table id="listAppLogTable" cellspacing="0" cellpadding="0" >

    </table>

</body>
</html>