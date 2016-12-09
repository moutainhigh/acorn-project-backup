<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="/static/scripts/ajaxfileupload.js"></script>
<script type="text/javascript" src="/static/scripts/order/orderhist.js"></script>
<link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
      <div class="hd">手工挑单</div>
	<div id="condition">
		<div>
			<label  class="labelTitle" for="startDate">订购日期:</label>
			<input id="startDate" readonly="readonly" class="easyui-datebox" /> <label for="">至:</label>
			<input id="endDate" readonly="readonly" type="text" class="easyui-datebox" size="50" />
			<label  for="orderId">订单编号:</label> 
			<input id="orderId"    name="tradeId"size="10" /> 
		</div>

		<div>
			<label for="status">是否挑单:</label>
			<select name="status" id="status">
				<option value="1">未挑单</option>
				<option value="2">已挑单</option>
			</select>
			<label for="ware">挑单仓库:</label>
			<select name="ware" id="wareHouseId">
				<option value="1">华新仓</option>
				<option value="2">北京仓</option>
			</select>
			<label for="entityId">承运商:</label>
			<select name="entityId" id="entityId">
				<c:forEach items="${companyList }" var="company">
					<option value="${company.companyid }">${company.name }</option>
				</c:forEach>
			</select>
			
			
		</div>
		<div class="btnBar">
    	 	<a  class="easyui-linkbutton" id="queryOrderBtn" data-options="iconCls:'icon-search'">检索</a>
    	 	<a  class="easyui-linkbutton" id="saveOrderBtn" data-options="iconCls:'icon-save'">保存</a>
		</div>
	</div>

	<div style="padding: 3px; height: 350px">

		<table id="oderhistTable" cellspacing="0" cellpadding="0">

		</table>
	</div>

<form action="/orderhist/download" method="post" target="downFrame" id="downloadForm">
<input type="hidden" id="d_orderId" name="orderId" value=""/>
<input type="hidden" id="d_startDate" name="startDate" value=""/>
<input type="hidden" id="d_endDate" name="endDate" value=""/>
<input type="hidden" id="d_status" name="status" value=""/>
<input type="hidden" id="d_wareHouse" name="wareHouse" value=""/>
<input type="hidden" id="d_entityId" name="entityId" value=""/>
</form>
	<!--下载隐藏frame-->
	<iframe style="display:none" id="downFrame" name="downFrame" src=""></iframe>
	<!-- 上传文件 -->
	<div id="uploadFileDiv" class="easyui-window" data-options="closed:true,modal:false,title:'导入'" style="width:300px;height:100px;">
		<!---->
		  <input id="fileToUpload" type="file" name="fileToUpload" class="input">
		  <button class="button" id="buttonUpload" onclick="return ajaxFileUpload();">上传</button>
		  <br/>
		  <img id="loading" src="/static/images/loading.gif" style="display:none;">
	</div>
</body>
</html>
