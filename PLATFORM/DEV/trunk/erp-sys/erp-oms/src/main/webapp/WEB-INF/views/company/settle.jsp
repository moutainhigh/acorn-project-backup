<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/json.min.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="/static/scripts/company/payment.js"></script>
    <script type="text/javascript" src="/static/scripts/company/settlement.js"></script>
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

<div region="center" >
    <div class="condition" border="false">
        <table cellpadding="2" cellspacing="2">
            <tr>
                <td><label class="ml10" for="tbPaymentCode">水单号：</label></td>
                <td><input id="tbPaymentCode" type="text" class="inputStyle" name="paymentCode" style="width:130px"></td>
                <td><label class="ml10" for="tbPaymentStart">打款时间开始：</label></td>
                <td><input id="tbPaymentStart" type="text" class="inputStyle easyui-datebox" name="paymentStart" style="width:130px"></td>
                <td><label class="ml10" for="tbPaymentEnd">打款时间结束：</label></td>
                <td><input id="tbPaymentEnd" class="inputStyle easyui-datebox" style="width:130px" name="paymentEnd"></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td><label class="ml10" for="tbCompanyId">承运商：</label></td>
                <td><input id="tbCompanyId" class="easyui-combobox" style="width:130px" name="companyId" size="8"></td>
                <td>
                    <input id="tbIsSettled" type="checkbox" class="floatStyle" name="verified">
                    <label class="" for="tbIsSettled" >显示已核销的水单</label>
                </td>
                <td colspan="4">
                    <button class="Btn ml10" type="button" id="btnPaymentSearch">查找</button>
                </td>
            </tr>
        </table>
    </div>

    <div class="container" style="height: 200px">
        <table id="dg" title="付款单"
               toobar="#condition"
               url="${paymentUrl}"
               cellspacing="0" cellpadding="0" data-options="" ></table>
    </div>

    <div class="condition" style="margin-top: 8px">
        <table cellpadding="2" cellspacing="2">
            <tr>
                <td>所选水单可核销金额：</td>
                <td width="80px"><span id="lblPaymentTotal">0.0</span></td>
                <td>所选发运单累计结算金额：</td>
                <td width="80px"><span id="lblSettlementTotal">0.0</span></td>
                <td>差异金额：</td>
                <td width="80px"><span id="lblBalanceTotal">0.0</span></td>
                <td>&nbsp;</td>
                <td><button class="Btn" type="button" id="btnConfirm" style="padding-left: 1px">拍平确认</button></td>
                <td>&nbsp;</td>
            </tr>
         </table>
    </div>

    <div class="condition" border="false">
        <table cellpadding="2" cellspacing="2">
            <tr>
                <td><label class="ml10" for="tbShipmentId">运单号：</label></td>
                <td><input id="tbShipmentId" type="text" class="inputStyle" name="ahipmentId" style="width:130px"></td>
                <td><label class="ml10" for="tbFbStart">结账反馈开始：</label></td>
                <td><input id="tbFbStart" type="text" class="inputStyle easyui-datebox" name="fbstart" style="width:130px"></td>
                <td><label class="ml10" for="tbFbEnd">结账反馈结束：</label></td>
                <td><input id="tbFbEnd" class="inputStyle easyui-datebox" style="width:130px" name="fbend"></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td><label class="ml10" for="tbOrderId">订单号：</label></td>
                <td><input id="tbOrderId" type="text" class="inputStyle" name="orderId" style="width:130px"></td>
                <td><label class="ml10" for="tbRfStart">RF出库开始：</label></td>
                <td><input id="tbRfStart" class="inputStyle easyui-datebox" style="width:130px" name="RfStart" size="8"></td>
                <td><label class="ml10" for="tbRfEnd">RF出库结束：</label></td>
                <td><input id="tbRfEnd" class="inputStyle easyui-datebox" style="width:130px" name="rfEnd" size="8"></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td><label class="ml10" for="tbCompanyId2">承运商：</label></td>
                <td><input id="tbCompanyId2" class="easyui-combobox" style="width:130px" name="companyId" size="8"></td>
                <td>
                    <input id="tbIsSettled2" type="checkbox" class="floatStyle" name="verified">
                    <label class="" for="tbIsSettled" >显示已核销的结算单</label>
                </td>
                <td>
                    <button class="Btn ml10" type="button" id="btnSettlementSearch">查找</button>
                    <button class="Btn" type="button" id="btnCancel" style="padding: 5px;display: none">拍平取消</button>
                </td>
                <td colspan="3">&nbsp;</td>
            </tr>
        </table>
    </div>

    <div class="container" style="height: 200px">
        <table id="dg2" title="结算单"
               url="${settlementUrl}"
               cellspacing="0" cellpadding="0" data-options="" ></table>
    </div>
</div>


</body>
</html>
