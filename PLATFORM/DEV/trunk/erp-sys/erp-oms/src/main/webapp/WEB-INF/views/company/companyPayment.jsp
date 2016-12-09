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
	.main_tb td{
		border: 1px solid #00FF00;
	}
	</style>
	<script type="text/javascript" src="/static/scripts/json2.js"></script>
	<script type="text/javascript" src="/static/scripts/company/companyPayment.js?${rnd}"></script>
</head>

<body>
      
	<div id="condition">
		<table class="td_padding2" width="100%" border="1">
			<tr>
				<td>水单号:</td>
				<td><input name="paymentCode" id="paymentCode" /></td>
				<td>登记结算时间:</td>
				<td>
					<input name="beginPaymentDate" id="beginPaymentDate" class="easyui-datetimebox datebox-f combo-f" />到
					<input name="endPaymentDate" id="endPaymentDate" class="easyui-datetimebox datebox-f combo-f" />
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td><label>承运商: </label></td>
				<td>
					<select id="companyContractId">
						<option value="">------请选择------</option>
						<c:forEach items="${companyContractList }" var="cc">
							<option value="${cc.id }">${cc.name }</option>
						</c:forEach>
					</select>
				</td>
				<td><label>核销状态: </label></td>
				<td>
					<select id="isSettled" >
						<option value="">------请选择------</option>
						<option value="0">未核销</option>
						<option value="1">核销中</option>
						<option value="2">全部核销</option>
					</select>
				</td>
				<td>
					<input type="button" id="queryBtn" class="ml10 Btn" value="查询">
					<input type="button" id="clearBtn" class="ml10 Btn" value="清空">
					<input type="button" id="exportBtn" class="ml10 Btn" value="导出">
				</td>
			</tr>
		</table>
	</div>
	
	<div id="toolbar">
	    <a href="#" id="addBtn" class="easyui-linkbutton l-btn l-btn-plain" iconCls="icon-add" plain="true">新增</a>
	    <a href="#" id="editBtn" class="easyui-linkbutton l-btn l-btn-plain" iconCls="icon-remove" plain="true">编辑</a>
	    <a href="#" id="deleteBtn" class="easyui-linkbutton l-btn l-btn-plain" iconCls="icon-remove" plain="true">删除</a>
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
