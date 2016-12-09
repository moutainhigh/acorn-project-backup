<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/json.min.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="/static/scripts/shipment/omsRequestCarrier.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
</head>
<body>

	<!--请求EMS发货-->
	<div id="condition">
		<div>
			<label for="timeS" class="ml10 w80">提交时间:</label>
			<input id="timeS" data-options="editable:false" value="${timeS }" class="easyui-datebox" />
			<label for="">至</label> 
			<input id="timeE" data-options="editable:false" value="${timeE }" class="easyui-datebox" /> 			
			<label for="isEms" class="ml10">请求EMS</label><input id="isEms" type="checkbox"/>
			<span>
				<input type="button" id="searchBtn" value="查询" class="ml10 Btn">
			</span>
		</div>
	</div>

	<div>
		<div id="toolbar">
			<a href="#" id="agree" class="easyui-linkbutton button" iconCls="icon-ok" plain="true">同意</a>
			<a href="#" id="refuse" class="easyui-linkbutton button" iconCls="icon-remove" plain="true">拒绝</a>
		</div>
		<div class="container">
			<table id="dataGrid" title="请求列表" cellspacing="0" cellpadding="0" data-options=""></table>
		</div>
	</div>

</body>
</html>
