<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/auditLog/auditLog.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
</head>
<body>

<div class="hd">操作日志</div>
<div style="padding: 1px;">
<!--  
    <label for="appName">系统名称:</label>
    <input id="appName" name="appName" size="10"/>
    -->
    <label for="funcName">功能名称:</label>
    <input id="funcName" name="funcName" size="10"/>
    <label for="beginDate">开始时间:</label>
    <input id="beginDate" class="easyui-datebox"/>
    <label for="endDate">结束时间:</label></th>
    <input id="endDate" type="text" class="easyui-datebox" size="50"/>

    <input type="button" name="queryAuditLogBtn" value='查找'
           id="queryAuditLogBtn" onclick="queryAuditData()"/>

</div>
<div id="toolbar">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#listAuditLogTable').edatagrid('addRow')">新增</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#listAuditLogTable').edatagrid('destroyRow')">删除</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#listAuditLogTable').edatagrid('saveRow')">保存</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#listAuditLogTable').edatagrid('cancelRow')">取消</a>
</div>
<div style="padding: 3px;height: 350px">

<table id="listAuditLogTable" cellspacing="0" cellpadding="0" >

</table>
 </div>
</body>
</html>
