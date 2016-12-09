<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/json.min.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="/static/scripts/shipment/refund.js"></script>
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
    <form:form id="refundForm" action="refund">
        <table class="editTable">
            <tr>
                <td><label class="ml10" for="txtOrderId">订单号（发运单）：</label></td>
                <td><input id="txtOrderId" class="inputStyle" name="orderId" size="8"></td>
                <!--<td><label class="ml10" for="txtContactId">顾客名称：</label></td>
                <td><input id="txtContactId" class="inputStyle" name="contactId" size="8"></td>-->
            </tr>
            <tr>
                <td><label class="ml10" for="txtMailId">邮件号：</label></td>
                <td><input id="txtMailId" class="inputStyle" name="mailId" size="8"></td>
                <td>&nbsp;</td>
                <td><button class="Btn ml10" value="查找" type="button" id="btnSearch">查找</button></td>
            </tr>
            <!--<tr>
                <td><label class="ml10" for="txtMailId">邮件号：</label></td>
                <td><input id="txtMailId" class="inputStyle" name="mailId" size="8"></td>
                <td><label class="ml10" for="txtPhone">顾客手机：</label></td>
                <td><input id="txtPhone" class="inputStyle" name="phone" size="8"></td>
            </tr>
            <tr>
                <td><label class="ml10" for="tbEntityId">承运商：</label></td>
                <td><input id="tbEntityId" class="inputStyle easyui-combobox" style="width:130px" name="entityId"></td>
                <td>&nbsp;</td>
                <td><button class="Btn ml10" value="查找" type="button" id="btnSearch">查找</button></td>
            </tr> -->
        </table>
    </form:form>
</div>
<div region="center" >
    <div id="toolbar">
        <a href="#" id="lbCheckIn" class="easyui-linkbutton button" iconCls="icon-edit" plain="true" style="float:left;">半签收登记</a>
    </div>
    <div >
        <table id="dgOrderhist" title="订单" fit="true"
               url="${url}"
               saveUrl="${saveUrl}"
               updateUrl="${updateUrl}"
               destroyUrl="${destroyUrl}"
               cellspacing="0" cellpadding="0" ></table>
    </div>
</div>

<!--基本信息-->
<div id="dlg" class="easyui-dialog" style="padding:10px 20px" closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post" novalidate>
        <div >
            <table id="orderdet" title="订单明细" fit="true"
                   url="/shipment/refund/loadOrderdet"
                   saveUrl="#"
                   updateUrl="#"
                   destroyUrl="#"
                   cellspacing="0" cellpadding="0" ></table>
        </div>

        <table id="detail" class="editTable" >
            <tr>
                <td>
                    <table class="editTable">
                        <tr>
                            <td><label class="ml10" for="txtRemark">半签收备注：</label> </td>
                            <td><input id="txtRemark" class="inputStyle" name="remark" size="80"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table class="editTable">
                        <tr>
                            <td><label class="ml10" for="txtOrgTotal">原始订单金额：</label></td>
                            <td><input id="txtOrgTotal" class="inputStyle" name="orgTotal" readonly="true" size="20"></td>
                            <td><label class="ml10" for="txtRefundTotal">半签收应收金额：</label></td>
                            <td><input id="txtRefundTotal" class="inputStyle" name="refundTotal" readonly="true" size="20"></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:void(0)" id="lbSave" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" id="lbClose" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
</div>
</body>
</html>
