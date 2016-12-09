<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/static/scripts/freight/avoidFreight.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!--免运费-->
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
    <a href="#" plain="true" iconcls="icon-edit" class="easyui-linkbutton l-btn l-btn-plain" id="btnEdit" >编辑运费</a>
</div>
<!--运费列表-->
<div class="container" >

    <table id="shipmentHeaderTable" cellspacing="0" cellpadding="0"data-options="" ></table>
</div>
 <!--编辑运费-->
<!--新增基本信息-->
<div id="dlg" class="easyui-dialog" style="padding:10px 20px" closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post" novalidate>
        <table cellspacing="4" cellpadding="4" border="0" align="center" style="height:auto">
            <tr>
                <td>
                    <div class="fitem" style="margin-top: 10px">
                        <label>原始运费：</label>
                        <input type="hidden" id="Id" name="Id" value=""/>
                        <label id="newFreight"></label>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="fitem" style="margin-top: 10px">
                        <label>调整后运费：</label>
                        <input id="afterFreight" name="afterFreight" size="15" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',required:true">
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="#" id="lbSave" class="easyui-linkbutton" data-options="iconCls:'icon-save'" iconCls="icon-ok">保存</a>
    <a href="#" id="lbClose" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
</div>
</body>
</html>
