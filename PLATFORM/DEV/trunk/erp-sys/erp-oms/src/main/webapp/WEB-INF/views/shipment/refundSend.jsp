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
		padding:5px;
	}
	.main_tb td{
		border: 1px solid #00FF00;
	}
	</style>
	<script type="text/javascript" src="/static/scripts/json2.js"></script>
	<script type="text/javascript" src="/static/scripts/shipment/refundSend.js?${rnd}"></script>
</head>

<body>
      
	<div id="condition">
		<table class="td_padding2"  border="">
			<tr>
				<td><label>承运商: </label></td>
				<td>
					<select id="entityId" class="easyui-combobox" style="width: 150px">
						<c:forEach items="${compnayList }" var="cc">
							<option value="${cc.companyid }">${cc.name }</option>
						</c:forEach>
					</select>
				</td>
				<td><label>退包仓库: </label></td>
				<td>
					<select id="warehouseId">
						<c:forEach items="${warehouseList }" var="cc">
							<option value="${cc.warehouseId }">${cc.warehouseName }</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<input type="button" id="queryBtn" class="ml10 Btn" value="查询">
				</td>
			</tr>
		</table>
	</div>
	
	<div id="toolbar">
	    <a href="#" id="exportBtn" class="easyui-linkbutton l-btn l-btn-plain" iconCls="icon-add" plain="true">导出退包整理单</a>
	    <a href="#" id="generateBtn" class="easyui-linkbutton l-btn l-btn-plain" iconCls="icon-add" plain="true">生成退包入库单</a>
	</div>
	
	<div class="container">
		<table id="dataTable" cellspacing="0" cellpadding="0" data-options=""></table>
	</div>
    
    <%-- 添加修改 窗口 --%>
     <div id="persistDialog" class="easyui-dialog" style="padding:10px 20px" closed="true" buttons="#dlg-buttons">
		
		<form id="dataForm" action="persist" method="post" >
			<input type="hidden" name="id" id="d_id" value="">
			<input type="hidden" name="settledAmount" id="d_settledAmount" value="">
			<input type="hidden" name="isSettled" id="d_isSettled" value="">
			<input type="hidden" name="createUser" id="d_createUser" value="">
			<input type="hidden" name="createDate" id="d_createDate" value="">
			
			<table  border="0" align="center" class="td_padding2">
				<tr>
					<td colspan="2" id="message" style="color: red;">&nbsp;</td>
				</tr>
				<tr>
					<td><label>水单号：</label></td>
					<td><input name="paymentCode" id="d_paymentCode" class="easyui-validatebox" data-options="required:true" size="27"></td>
				</tr>
				<tr>
					<td><label>承运商：</label></td>
					<td>
						<select name="companyContract.id" id="d_companyContractId"  class="easyui-combobox" data-options="required:true" style="width:200px;">
							<option value="">------请选择------</option>
							<c:forEach items="${companyContractList }" var="cc">
								<option value="${cc.id }">${cc.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><label>水单金额：</label></td>
					<td>
						<input name="amount" id="d_amount" class="easyui-numberbox" precision="2" data-options="required:true" style="width:200px;">
					</td>
				</tr>
				<tr>
					<td><label>打款账号：</label></td>
					<td>
						<select name="companyAccountCode" id="d_companyAccountCode" class="easyui-combobox" data-options="required:true" style="width:200px;">
							<option value="">------请选择------</option>
						</select>
					</td>
				</tr>
				<tr>
					<td><label>户名：</label></td>
					<td><input name="cpompanyAccountName" id="d_cpompanyAccountName" class="easyui-validatebox" data-options="required:true" style="width:200px;"></td>
				</tr>
				<tr>
					<td><label>打款时间：</label></td>
					<td><input name="paymentDate" id="d_paymentDate" class="easyui-datetimebox datebox-f combo-f" data-options="required:true" style="width:200px;"></td>
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
