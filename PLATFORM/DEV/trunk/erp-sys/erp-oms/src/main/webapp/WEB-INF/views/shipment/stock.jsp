<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/json.min.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="/static/scripts/shipment/stock.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        #fm{
            margin:0;
           
        }
         #fm td{margin:auto;padding:8px;}
        .ftitle{
            font-size:14px;
            font-weight:bold;
            color:#666;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
            padding:5px 0 0 0;
        }
    </style>
</head>
<body>

    <div id="condition" region="north" border="false" >
        <div>
            <table class="editTable">
                <tr>
                    <td><label class="ml10" for="txtOrderId">订单号：</label></td>
                    <td><input id="txtOrderId" class="inputStyle" name="orderId" size="8"></td>
                    <td><label class="ml10" for="txtShipmentId">发运单号：</label></td>
                    <td><input id="txtShipmentId" class="inputStyle" name="shipmentId" size="8"></td>
                    <td><button class="Btn ml10" value="查找" type="button" id="btnSearch">查找</button></td>
                </tr>
            </table>
        </div>
    </div>
    <div region="center" >
        <div id="toolbar">
            <a href="#" id="lbAlternate" class="easyui-linkbutton button" iconCls="icon-edit" plain="true" style="float:left;">发货变更</a>
        </div> 
        <div  class="container">
            <table id="dg" title="订单发货单" 
                   url="${url}"
                   saveUrl="${saveUrl}"
                   updateUrl="${updateUrl}"
                   destroyUrl="${destroyUrl}"
                   cellspacing="0" cellpadding="0"data-options="" ></table>
        </div>
    </div>

<!--基本信息-->
<div id="dlg" class="easyui-dialog" style="padding:10px 20px" closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post" novalidate>
        <input id="id" name="id" type="hidden">
        <table cellspacing="4" cellpadding="4" border="0" align="center" style="height:auto;">
            <tr>
                <td align="left" colspan="2">
                    <div class="fitem">
                        <label style="padding:0">发运单号：</label>
                        <span id="lblShipmentId" ></span>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="left">
                    <div class="fitem">
                        <label style="padding:0">出库仓：</label>
                        <span id="lblWarehouseId"></span>
                    </div>
                </td>
                <td align="left">
                    <div class="fitem">
                        <label style="padding:0">承运商：</label>
                        <span id="lblEntityId"></span>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="left">
                    <div class="fitem">
                        <label>新出库仓：</label>
                        <input id="warehouseId" name="warehouseId" class="easyui-combobox" style="display:inline-block">
                    </div>
                </td>
                <td align="left">
                    <div class="fitem">
                        <label>新承运商：</label>
                        <input id="entityId" name="entityId" class="easyui-combobox">
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:void(0)" id="lbSave" class="easyui-linkbutton" iconCls="icon-ok">变更</a>
    <a href="javascript:void(0)" id="lbClose" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
</div>
</body>
</html>
