<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/json.min.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="/static/scripts/shipment/orderPaymentConfirm.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
		.fitem label {
		    display: inline-block;
		    padding: 5px 0 0;
		    width: 120px;
		}
		
		.fitem {
		    margin-bottom: 5px;
		}
    </style>
</head>
<body>
	<!--订单付款确认-->
	<div id="condition">
		<div>
			<label for="orderId" class="ml10 w80">订单编号:</label> 
			<input id="orderId" type="text" name="orderId" size="20" /> 
			<label for="payUserName" class="mr10">&nbsp;&nbsp;&nbsp;订购人姓名:</label> 
			<input id="payUserName" type="text" name="payUserName" size="20" /> 
			<label for="payUserTel" class="mr10">&nbsp;&nbsp;&nbsp;订购人电话:</label> 
			<input id="payUserTel" type="text" name="payUserTel" size="20" />
		</div>

		<div>
			<label for="payType" class="ml10 w80">付款方式:</label> 
			<input id="payType" class="easyui-combobox" name="payType"> 
			<label for="orderDateS" class="ml10 w80">订购日期:</label> 
			<input id="orderDateS" data-options="editable:false" value="${startDate }" class="easyui-datebox" /> 
			<label for="">至</label> 
			<input id="orderDateE" data-options="editable:false" value="${endDate }" class="easyui-datebox" /> 
			<span>
				<input type="button" id="searchBtn" value="查询" class="ml10 Btn" name=""> 
			</span>
		</div>
	</div>

	<div>
		<div id="toolbar">
			<a href="#" id="payConfirm" class="easyui-linkbutton button" iconCls="icon-edit" plain="true">付款确认</a>
		</div>
		<div class="container">
			<table id="dataGrid" title="订单列表" cellspacing="0" cellpadding="0" data-options=""></table>
		</div>
	</div>
		
	<div id="dlg" class="easyui-dialog" style="padding:10px 20px" closed="true" buttons="#dlg-buttons">
	    <fieldset>
	        <form id="fm" method="post">
	        	<input id="orderIdStr" name="orderIdStr" type="hidden" >
	        	<input id="payTypeStr" name="payTypeStr" type="hidden" >
	            <table cellspacing="4" cellpadding="4" border="0" style="width:100%;height:auto">
	                <tr>
	                    <td>
	                        <div class="fitem"><label>订单编号：</label><input id="orderIdDis" disabled="true" style="width:200px;" class="easyui-validatebox"/></div>
	                    </td>
	                </tr>
	                <tr>
	                    <td>
	                        <div class="fitem"><label>订单提交时间:</label><input id="crdtDis" disabled="true" style="width:200px;" class="easyui-validatebox"></div>
	                    </td>
	                </tr>
	                <tr>
	                    <td>
	                        <div class="fitem"><label>订单状态:</label><input id="dscaDis" disabled="true" style="width:200px;" class="easyui-validatebox"></div>
	                    </td>
	                </tr>
	                <tr>
	                    <td>
	                        <div class="fitem"><label>订单金额：</label><input id="totalPriceDis" disabled="true" style="width:200px;" class="easyui-validatebox"></div>
	                    </td>
	                </tr>
	                <tr>
	                    <td>
	                        <div class="fitem"><label>客户编号：</label><input id="contactIdDis" disabled="true" style="width:200px;" class="easyui-validatebox"></div>
	                    </td>
	                </tr>
	                <tr>
	                    <td>
	                        <div class="fitem"><label>客户姓名：</label><input id="nameDis" disabled="true" style="width:200px;" class="easyui-validatebox"></div>
	                    </td>
	                </tr>
	                <tr>
	                    <td>
	                        <div class="fitem"><label>支付方式：</label><input id="dscbDis" disabled="true" style="width:200px;" class="easyui-validatebox"></div>
	                    </td>
	                </tr>
	                <tr>
	                    <td>
	                        <div class="fitem"><label>是否已付款：</label><input id="confirmDis" disabled="true" style="width:200px;" class="easyui-validatebox"></div>
	                    </td>
	                </tr>
	                <tr>
	                    <td>
	                        <div class="fitem"><label>付款单号：</label><input id="payNo" name="payNo" style="width:200px;" class="easyui-validatebox" required="true"></div>
	                    </td>
	                </tr>
	                <tr>
	                    <td>
	                        <div class="fitem"><label>付款人：</label><input id="payUser" name="payUser" style="width:200px;" class="easyui-validatebox" required="true"></div>
	                    </td>
	                </tr>
	                <tr>
	                    <td>
	                        <div class="fitem"><label>付款日期：</label><input id="payDate" name="payDate" style="width:200px;" class="easyui-datetimebox" required="true"></div>
	                    </td>
	                </tr>
	            </table>
	        </form>
	    </fieldset>
	    <div id="dlg-buttons">
			<a href="javascript:void(0)" id="lbSave" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
			<a href="javascript:void(0)" id="lbClose" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
	</div>



</body>
</html>
