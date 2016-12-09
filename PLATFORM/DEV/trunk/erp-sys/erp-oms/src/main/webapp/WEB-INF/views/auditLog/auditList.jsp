<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/auditLog/auditLog.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="condition">
    <div>
           <!--  
			<label class="labelTitle" for="appName">系统名称:</label> 
			<input  id="appName" name="appName" class="inputStyle" size="8">
			--> 
			 <label class="labelTitle" for="funcName">功能名称:</label>
		<input id="funcName" name="funcName" type="text" class="inputStyle"  >
		 <label class="labelTitle ml10" for="funcName" style="width:auto">网上单号:</label>
		<input id="treadid" name="treadid" type="text" class="inputStyle"  >
			</div>
				<div>
			
			<label class="labelTitle" for="beginDate">开始时间:</label>
			<input  id="beginDate"  class="easyui-datebox datebox-f combo-f"  style="width:130px"   >
			<label class="ml10" for="endDate">结束时间:</label>
			<input type="text" id="endDate" style="width:130px" class="easyui-datebox datebox-f combo-f"  >
			<button class="Btn ml10" value="查找" name="queryAuditLogBtn" type="button" id="queryAuditLogBtn" onclick="queryAuditData()"> 查找</button>
				</div>
      
    
</div>

 
<div id="toolbar">
<!-- 
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#listAuditLogTable').edatagrid('addRow')">新增</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#listAuditLogTable').edatagrid('destroyRow')">删除</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#listAuditLogTable').edatagrid('saveRow')">保存</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#listAuditLogTable').edatagrid('cancelRow')">取消</a>
-->
</div>

<div class="container">

<table id="listAuditLogTable" cellspacing="0" cellpadding="0" data-options=""  >

</table>
 </div>
</body>
</html>
