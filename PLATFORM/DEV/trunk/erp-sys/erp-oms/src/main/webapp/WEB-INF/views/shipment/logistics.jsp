<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/json.min.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="/static/scripts/shipment/logistics.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        #fm{
            margin:0;

        }
        #fm td{padding:5px;text-align:left;}
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
            padding:5px 0 0;
        }
    </style>
</head>
<body>

<div id="condition" region="north" border="false">
        <table cellpadding="2" cellspacing="2">
            <tr>
                <td><label class="ml10" for="tbOrderId">订单号：</label></td>
                <td><input id="tbOrderId" type="text" class="inputStyle" name="orderId" size="8"></td>
                <td><label class="ml10" for="tbShipmentId">发运单号：</label></td>
                <td><input id="tbShipmentId" type="text" class="inputStyle" name="shipmentId" size="8"></td>
                <td><label class="ml10" for="tbProductId">产品编码：</label></td>
                <td><input id="tbProductId" class="inputStyle easyui-validatebox" style="width:130px" name="productId" size="8"></td>
                <%--<td><label class="ml10" for="tblogisticsStatusId">物流状态：</label></td>--%>
                <td style="display: none;"><input id="tblogisticsStatusId" class="inputStyle easyui-combobox" style="width:130px" name="logisticsStatusId"></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td><label class="ml10" for="tbAccountStatusId">记账状态：</label></td>
                <td><input id="tbAccountStatusId" class="inputStyle easyui-combobox" style="width:130px" name="accountStatusId"></td>
                <td><label class="ml10" for="tbWarehouseId">仓库：</label></td>
                <td><input id="tbWarehouseId" class="inputStyle easyui-combobox"style="width:130px" name="warehouseId"></td>
                <td><label class="ml10" for="tbEntityId">承运商：</label></td>
                <td><input id="tbEntityId" class="inputStyle easyui-combobox" style="width:130px" name="entityId"></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td><label class="ml10" for="tbOrderId">省：</label></td>
                <td><input id="tbProvinceId" class="inputStyle easyui-combobox" style="width:130px" name="provinceId" size="8"></td>
                <td><label class="ml10" for="tbCityId">市：</label></td>
                <td><input id="tbCityId" class="inputStyle easyui-combobox" style="width:130px" name="cityId" size="8"></td>
                <td><label class="ml10" for="tbCountyId">区/县：</label></td>
                <td><input id="tbCountyId" class="inputStyle easyui-combobox" style="width:130px" name="countyId" size="8"></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td><label class="ml10" for="tbPaytypeId">付款方式：</label></td>
                <td><input id="tbPaytypeId" class="inputStyle easyui-combobox" style="width:130px" name="paytypeId" size="8"></td>
                <%--<td><label class="ml10" for="tbBuytypeId">购买方式：</label></td>
                <td><input id="tbBuytypeId" class="inputStyle easyui-combobox" style="width:130px" name="buytypeId" size="8"></td>--%>
                <td><label class="ml10" for="tbCardtype">信用卡：</label></td>
                <td><input id="tbCardtype" class="inputStyle easyui-combobox" style="width:130px" name="cardtype"></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td><label class="ml10" for="tbCrdtStart">创建日期：</label></td>
                <td colspan="3">
                    <input id="tbCrdtStart" data-options="editable:false" value="${tbCrdtStart }" class="easyui-datetimebox" style="width: 156px" /> <label for="">至:</label>
                    <input id="tbCrdtEnd" data-options="editable:false" type="text"  value="${tbCrdtEnd }" class="easyui-datetimebox" size="50" style="width: 156px"/>
                </td>

            </tr>
            <tr>
                <td><label class="ml10" for="tbSenddtStart">交际日期：</label></td>
                <td colspan="3">
                    <input id="tbSenddtStart" data-options="editable:false" value="${tbSenddtStart }" class="easyui-datetimebox" style="width: 156px" /> <label for="">至:</label>
                    <input id="tbSenddtEnd" data-options="editable:false" type="text"  value="${tbSenddtEnd }" class="easyui-datetimebox" size="50" style="width: 156px"/>
                </td>
            </tr>
            <tr>
                <td><label class="ml10" for="tbMinAmount">金额：</label></td>
                <td clospan="3"><input id="tbMinAmount" type="text" class="" name="minAmount" size="8"><label class="" for="tbMaxAmount">~</label><input id="tbMaxAmount" type="text" class="" name="maxAmount" size="8"></td>
                <!--<td><label class="ml10" for="tbMaxAmount">~</label></td>
                <td><input id="tbMaxAmount" class="inputStyle" name="maxAmount" size="8"></td>  -->
                <td colspan="2" style="padding-left:10px;">
                    <input id="tbCapture" type="checkbox" class="floatStyle" name="capture">
                    <label class="" for="tbCapture" >是否已确认付款（索权标记）</label>
                </td>
                <td ><button class="Btn ml10" type="button" id="btnSearch">查找</button></td>
            </tr>
        </table>
    
</div>
<div region="center" >
    <div id="toolbar">
        <a href="#" id="lbAlternate" class="easyui-linkbutton button" iconCls="icon-edit" plain="true" style="float:left;">发货变更</a>
    </div>
    <div class="container">
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
        <input id="ids" name="ids" type="hidden">
        <input id="orderId" name="orderId" type="hidden">
        <table cellspacing="4" cellpadding="4" border="0" align="center" style="height:auto">
            <%--<tr>
                <td colspan="2">
                    <div class="fitem">
                        <label>发运单号：</label>
                        <label id="lblShipmentId"></label>
                    </div>
                </td>
            </tr>--%>
            <tr>
                <td>
                    <div class="fitem">
                        <label>出库仓：</label>
                        <label id="lblWarehouseId"></label>
                    </div>
                </td>
                <td>
                    <div class="fitem">
                        <label>承运商：</label>
                        <label id="lblEntityId"></label>
                    </div>
                </td>
            </tr>
            <tr>
                <td nowrap>
                    <div class="fitem">
                        <label>新出库仓：</label>
                        <input id="warehouseId" name="warehouseId" class="easyui-combobox">
                    </div>
                </td>
                <td nowrap>
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
