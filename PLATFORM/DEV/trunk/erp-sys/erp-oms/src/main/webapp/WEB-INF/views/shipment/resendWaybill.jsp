<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
	.tb_padding td{
		padding:1px;
	}
	</style>

	<script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
	<script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.parser.js"></script>
</head>
<body>

<div id="condition">
	
	<!-- <img id="loading" src="/static/images/loading.gif" style="display:none;"> -->
	
	<form:form id="waybillForm" action="resendWaybill" commandName="shipmentSenderDto">
		<form:hidden path="id" cssClass="id"/>
	
		<table width="80%" class="tb_padding" >
			<tr>
				<td align="right">订单号:</td>
				<td><input type="text" name="orderid" id="orderid"/></td>
				<%--
				<td align="right">邮件编号:</td>
				<td><input type="text" name="mailid" id="mailid" /></td>
				 --%>
				<td colspan="2">
					<input type="button" class="ml10 Btn" onclick="" id="query" value="查询">
					<c:if test="${null!=shipmentSenderDto.orderId}">
						<input type="button" class="ml10 Btn" id="resender" value="重发"/>
					</c:if>
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td colspan="5" id="message" style="color:red;">${message} </td>
			</tr>
			<tr>
				<td colspan="6">&nbsp;</td>
			</tr>
			<tr>
				<td align="right"><label>订单号:</label></td>
				<td><form:input path="orderId" cssClass="orderId"/></td>
				<td align="right"><label>订单类型:</label></td>
				<td><form:input path="orderTypeName" cssClass="orderTypeName"/></td>
				<td align="right"><label>坐席人员:</label></td>
				<td><form:input path="workerName" cssClass="workerName"/></td>
			</tr>
			<tr>
				<td align="right"><label>客户:</label></td>
				<td><form:input path="contactName" cssClass="contactName"/></td>
				<td align="right"><label>送货公司:</label></td>
				<td>
					<form:input path="companyName" cssClass="companyName"/>
				</td>
				<td align="right"><label>订购方式:</label></td>
				<td><form:input path="mailTypeName" cssClass="mailTypeName"/></td>
			</tr>
			<tr>
				<td align="right"><label>订单状态:</label></td>
				<td><form:input path="orderStatusName" cssClass="orderStatusName"/></td>
				<td align="right"><label>出库状态:</label></td>
				<td><form:input path="customerStatusName" cssClass="customerStatusName"/></td>
				<td align="right"><label>付款方式:</label></td>
				<td><form:input path="payTypeName" cssClass="payTypeName"/></td>
			</tr>
			<tr>
				<td align="right"><label>订购时间:</label></td>
				<td><form:input path="orderDate" cssClass="orderDate"/></td>
				<td align="right"><label>出库时间:</label></td>
				<td><form:input path="outDate" cssClass="outDate"/></td>
				<td align="right"><label>交寄时间:</label></td>
				<td><form:input path="sendDate" cssClass="sendDate"/></td>
			</tr>
			<tr>
				<td align="right"><label>新送货公司:</label></td>
				<td colspan="5">
					<form:select path="newCompanyId" cssClass="easyui-validatebox" data-options="required:true">
						<form:option value="">--请选择--</form:option>
						<form:options items="${companyList}" itemValue="companyid" itemLabel="name"/>
					</form:select>
				</td>
			</tr>
		</table>
	</form:form>	
	<%--
	<div id="shipmentList"></div>
	
	<div id="addEditWin" style="width:700px;height:400px;padding:5px;">

		<form:form id="waybillForm" action="resendWaybill" commandName="shipmentSenderDto">
			<form:hidden path="id" cssClass="id"/>
			<form:hidden path="orderType" cssClass="orderType"/>
			<form:hidden path="workerId" cssClass="workerId"/>
			<form:hidden path="contactId" cssClass="contactId"/>
			<form:hidden path="mailType" cssClass="mailType"/>
			<form:hidden path="orderStatus" cssClass="orderStatus"/>
			<form:hidden path="customerStatus" cssClass="customerStatus"/>
			<form:hidden path="payType" cssClass="payType"/>
			
			<table width="100%" class="spacing_tb">
				<tr>
					<td align="right"><label>订单号:</label></td>
					<td><form:input path="orderId" cssClass="orderId"/></td>
					<td align="right"><label>订单类型:</label></td>
					<td><form:input path="orderTypeName" cssClass="orderTypeName"/></td>
				</tr>
				<tr>
					<td align="right"><label>客户:</label></td>
					<td><form:input path="contactName" cssClass="contactName"/></td>
					<td align="right"><label>送货公司:</label></td>
					<td>
						<select name="entityId" class="entityId">
							<option>--请选择--</option>
						</select>
						
					</td>
				</tr>
				<tr>
					<td align="right"><label>订单状态:</label></td>
					<td><form:input path="orderStatusName" cssClass="orderStatusName"/></td>
					<td align="right"><label>出库状态:</label></td>
					<td><form:input path="customerStatusName" cssClass="customerStatusName"/></td>
				</tr>
				<tr>
					<td align="right"><label>订购时间:</label></td>
					<td><form:input path="orderDate" cssClass="orderDate"/></td>
					<td align="right"><label>出库时间:</label></td>
					<td><form:input path="outDate" cssClass="outDate"/></td>
				</tr>
				<tr>
					<td align="right"><label>坐席人员:</label></td>
					<td><form:input path="workerName" cssClass="workerName"/></td>
					<td align="right"><label>订购方式:</label></td>
					<td><form:input path="mailTypeName" cssClass="mailTypeName"/></td>
				</tr>
				<tr>
					<td align="right"><label>付款方式:</label></td>
					<td><form:input path="payTypeName" cssClass="payTypeName"/></td>
					<td align="right"><label>交寄时间:</label></td>
					<td><form:input path="sendDate" cssClass="sendDate"/></td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<a href="#" class="easyui-linkbutton l-btn" onclick="" id="resender">重发</a>
					</td>
				</tr>
			</table>
		</form:form>
	</div>
	 --%>
	</div>
<script type="text/javascript" src="${ctx}/static/scripts/shipment/resendWaybill.js?'${rnd}'"></script>
</body>
</html>
