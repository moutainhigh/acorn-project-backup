<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="/static/scripts/ajaxfileupload.js"></script>
<script type="text/javascript" src="/static/scripts/order/orderhistNew.js"></script>
<link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
      <!--  <div class="hd">手工挑单</div>-->
	<div id="condition">
		<div>
			<label  for="startDate" class="mr10">订购日期:</label>
			<input id="startDate" data-options="editable:false" value="${startDate }" class="easyui-datebox" /> <label for="">至:</label>
			<input id="endDate" data-options="editable:false" type="text"  value="${endDate }" class="easyui-datebox" size="50" />
			<label  for="orderId" class="w100">订单编号:</label> 
			<input id="orderId"    name="tradeId"size="10" /> 
			<label for="status" class="mr10">挑单类型:</label>
			<select name="queryType"  id="queryType">
				<option value="shipment">按运单</option>
			</select>
		</div>

		<div>
			<label for="status" class="mr10">是否挑单:</label>
			<select name="status"  id="status">
				<option value="1">未挑单</option>
				<option value="2">已挑单</option>
			</select>
			<label for="ware" class="ml10 mr10">挑单仓库:</label>
			<select name="ware" id="wareHouseId">
				<c:forEach items="${warehouseList }" var="warehouse">
					<option value="${warehouse.warehouseCode }">${warehouse.warehouseName }</option>
				</c:forEach>
			</select>
			<label for="entityId" class="ml10 mr10">承运商:</label>
			<select name="entityId" id="entityId">
				<c:forEach items="${companyList }" var="company">
					<option value="${company.companyid }">${company.name }</option>
				</c:forEach>
			</select>
			<span >
			<input type="button" id="queryOrderBtn" value="检索" class="ml10 Btn" name="">
			<input type="button" id="saveOrderBtn" value="保存" class="ml10 Btn" name="">
    	 	</span>
			
		</div>
		
	</div>
<div id="toolbar">
    <a plain="true" iconcls="icon-redo" class="easyui-linkbutton l-btn l-btn-plain" id="btncut" href="javascript:exportOderList();">导出</a>
    <a  plain="true" iconcls="icon-back" class="easyui-linkbutton l-btn l-btn-plain" id="btnsave" >导入</a>
</div>
	<div class="container">

		<table id="oderhistTable" cellspacing="0" cellpadding="0" data-options="">

		</table>
	</div>

<form action="/orderhistNew/download" method="post" target="downFrame" id="downloadForm">
<input type="hidden" id="d_orderId" name="orderId" value=""/>
<input type="hidden" id="d_startDate" name="startDate" value=""/>
<input type="hidden" id="d_endDate" name="endDate" value=""/>
<input type="hidden" id="d_status" name="status" value=""/>
<input type="hidden" id="d_wareHouse" name="wareHouse" value=""/>
<input type="hidden" id="d_entityId" name="entityId" value=""/>
<input type="hidden" id="d_queryType" name="queryType" value=""/>
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
