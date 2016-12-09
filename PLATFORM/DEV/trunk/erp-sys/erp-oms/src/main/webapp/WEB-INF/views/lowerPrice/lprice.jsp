<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/json.min.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="/static/scripts/lowerPrice/lPrice.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%--商品折扣--%>
<div id="condition">
    <div>
        <label  for="shipmentId" class="w100">订单号(发运单):</label>
        <input id="shipmentId"  type="text"   name="shipmentId" size="20" />
    </div>

    <div>
        <label  for="mailId" class="w100">邮件号:</label>
        <input id="mailId"  type="text"    name="mailId"size="20" />
			<span >
			<input type="button" id="queryOrderBtn" value="查询" class="ml10 Btn" name="">
    	 	</span>
    </div>

</div>
<div id="toolbar">
    <a href="#" plain="true" iconcls="icon-edit" class="easyui-linkbutton l-btn l-btn-plain" id="btnEdit" >编辑订单折扣金额</a>
</div>
<!--运费列表-->
<div class="container" >
    <table id="shipmentHeaderTable" cellspacing="0" cellpadding="0" ></table>
</div>
<%--订单详细信息--%>
<div id="dlg" class="easyui-dialog" style="padding-right: 20px" closed="true" buttons="#dlg-buttons">
    <table id="orderdetVim" cellspacing="0" cellpadding="0"></table>
</div>
<div id="dlg-buttons">
    <div style="padding-left: 15px;padding-bottom: 10px">
        <form id="fm" method="post" novalidate>
            <table cellspacing="4" cellpadding="4" border="0" align="center" style="height:auto">
                <tr>
                    <td>
                        <div class="fitem" style="margin-top: 10px">
                            <label>全渠道订单最低商品总金额：</label>
                            <input type="hidden" id="orderId" name="orderId" value=""/>
                            <input type="hidden" id="totalPrice" name="totalPrice" value=""/>
                            <label id="lowerPrice"></label>
                        </div>
                    </td>
                    <td style="padding-left: 20px">
                        <div class="fitem" style="margin-top: 10px">
                            <label>订单金额调整：</label>
                            <input id="editPrice" name="editPrice" size="15" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',required:true">
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <a href="#" id="lbSave" class="easyui-linkbutton" data-options="iconCls:'icon-save'" iconCls="icon-ok">保存</a>
    <a href="#" id="lbClose" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
</div>
</body>
</html>
