<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
	.tb_padding td{
		padding:1px;
	}
	.td_padding2 td{
		padding:3px;
	}
	</style>
	<script type="text/javascript" src="/static/scripts/carrier/orderAssignRule.js?${rnd}"></script>
</head>

<body>
      
	<div id="condition">
		<table width="100%" class="tb_padding">
			<tr>
				<td><label class="mr10">分配规则编号:</label></td>
				<td><input name="" id="id" type="text" class="easyui-numberbox" data-options="required:false"></td>
				<td><label class="mr10">分配规则简称:</label></td>
				<td><input name="" id="name"type="text" ></td>
				<td><label class="mr10">承运商编号:</label></td>
				<td><input name="" id="companyId" type="text" class="easyui-numberbox" data-options="required:false"></td>
				<td><label class="mr10">出库仓库:</label></td>
				<td>
					<select id="warehouseId" style="width:145px;">
						<option value="">------请选择------</option>
						<c:forEach items="${warehouseList}" var="wh">
							<option value="${wh.warehouseId}">${wh.warehouseName }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><label class="mr10">渠道:</label></td>
				<td>
					<select  id="channelId" style="width:145px;">
						<option value="">------请选择------</option>
						<c:forEach items="${channelList }" var="channel">
							<option value="${channel.id }">${channel.channelName }</option>
						</c:forEach>
					</select>
				</td>
				<td><label class="mr10">地址组编号:</label></td>
				<td><input name="" id="areaGroupId" type="text" class="easyui-numberbox" data-options="required:false"></td>
				<td>最小金额范围:</td>
				<td>
					<input name="" id=beginMinAmount type="text" class="easyui-numberbox" data-options="required:false" style="width:66px;"> - 
					<input name="" id="endMinAmount" type="text" class="easyui-numberbox" data-options="required:false" style="width:66px;">
				</td>
				<td>最大金额范围:</td>
				<td>
					<input name="" id=beginMaxAmount type="text" class="easyui-numberbox" data-options="required:false" style="width:66px;"> - 
					<input name="" id="endMaxAmount" type="text" class="easyui-numberbox" data-options="required:false" style="width:66px;">
				</td>
			</tr>
			<tr>
				<td><label class="mr10">优先级:</label></td>
				<td><input name="" id="priority" type="text" class="easyui-numberbox" data-options="required:false"></td>
				<td>商品编码:</td>
				<td><input name="" id="prodCode" type="text" class="easyui-numberbox" data-options="required:false"></td>
				<td colspan="4">
					<input type="button" id="queryBtn" value="查询" class="ml10 Btn">
					<input type="button" id="clearBtn" value="清空" class="ml10 Btn">
				</td>
			</tr>
		</table>
	</div>
	
	<div id="toolbar">
	    <a href="#" id="addBtn" class="easyui-linkbutton l-btn l-btn-plain" iconCls="icon-add" plain="true">新增</a>
	    <a href="#" id="editBtn" class="easyui-linkbutton l-btn l-btn-plain" iconCls="icon-remove" plain="true">编辑</a>
	    <a href="#" id="enableBtn" class="easyui-linkbutton l-btn l-btn-plain" iconCls="icon-remove" plain="true">启用</a>
	    <a href="#" id="disableBtn" class="easyui-linkbutton l-btn l-btn-plain" iconCls="icon-remove" plain="true">停用</a>
	</div>
	
	<div  class="container">

		<table id="dataTable" cellspacing="0" cellpadding="0" data-options=""></table>
		
	</div>
    
    <%-- 添加修改 窗口 --%>
     <div id="persistDialog" class="easyui-dialog" style="padding:10px 20px" closed="true" buttons="#dlg-buttons">
		
		<form id="oarForm" action="persist" method="post" >
			<input type="hidden" name="id" id="d_id" value="">
			<table  border="0" align="center" class="td_padding2">
				<tr>
					<td colspan="2" id="message" style="color: red;">&nbsp;</td>
				</tr>
				<tr>
					<td><label>分配规则简称：</label></td>
					<td><input name="name" id="d_name" class="easyui-validatebox" data-options="required:true" size="27"></td>
				</tr>
				<tr>
					<td><label>渠道编号：</label></td>
					<td>
						<select name="orderChannel.id" id="d_orderChannel"  class="easyui-validatebox" data-options="required:true">
							<option value="">------请选择------</option>
							<c:forEach items="${channelList }" var="channel">
								<option value="${channel.id }">${channel.channelName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><label>地址组编号：</label></td>
					<td>
						<input name="areaGroup.id" id="d_areaGroup"  size="27">
					</td>
				</tr>
				<tr>
					<td><label>承运商编号：</label></td>
					<td><input name="entityId" id="d_entityId" class="easyui-numberbox" data-options="required:true" size="27"></td>
				</tr>
				<tr>
					<td><label>出库仓编号：</label></td>
					<td>
						<select name="warehouseId" id="d_warehouseId"  class="easyui-validatebox" data-options="required:true">
							<option value="">------请选择------</option>
							<c:forEach items="${warehouseList}" var="wh">
								<option value="${wh.warehouseId}">${wh.warehouseName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><label>优先级：</label></td>
					<td><input name="priority" id="d_priority" size="12" class="easyui-numberbox" data-options="required:true" style="width:200px"></td>
				</tr>
				<tr class="">
					<td><label>商品编码：</label></td>
					<td><input name="prodCode" size="12" id="d_prodCode" style="width:200px"></td>
				</tr>
				<tr class="amount_input">
					<td><label>金额：</label></td>
					<td>
						<input name="minAmount" size="20" id="d_minAmount" style="width:95px"> - 
						<input name="maxAmount" size="20" id="d_maxAmount" style="width:95px">
					</td>
				</tr>
			</table>
		</form>
		
		<div id="dlg-buttons">
		  <a href="#" id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save'" iconCls="icon-ok">保存</a>
		  <a href="#" id="closeBtn" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>     
     </div>
    
</body>
</html>
