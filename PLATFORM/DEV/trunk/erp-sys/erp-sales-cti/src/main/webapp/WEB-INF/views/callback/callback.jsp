<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
    <link href="/static/style/plugins/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="/static/js/datagrid-detailview.js"></script>
    <script type="text/javascript" src="/static/js/callback/callback.js"></script>
    <script type="text/javascript" src="/static/js/plugins/jquery.autocomplete.js"></script>
</head>
<body>


<div style="padding:10px 20px ;">
    <input type="hidden" id="c_leadId" name="c_leadId"/>
    <input type="hidden" name="c_leadInterId" id="c_leadInterId"/>
    <input type="hidden" name="c_instId" id="c_instId"/>
    <input type="hidden" name="c_acdgroup" id="c_acdgroup"/>
    <input type="hidden" name="c_ani" id="c_ani"/>
    <input type="hidden" name="c_dnsi" id="c_dnsi"/>
    <input type="hidden" name="c_phn1" id="c_phn1"/>
    <input type="hidden" name="c_phn2" id="c_phn2"/>
    <input type="hidden" name="c_phn3" id="c_phn3"/>
    <input type="hidden" name="c_time_length" id="c_time_length"/>

    <label id="callbackHookMsg" style="color: #ff4500"></label>
    <form id="formCallback" action="/common/callBack" method="post" onkeypress="if(event.keyCode==13||event.which==13){return false;}">
    <table style="margin:10px auto;width:70%;">
        <input type="hidden" name="customerType" id="customerType" value="${customer.customerType}">
        <tr>
            <td style="padding:5px;">客户姓名</td>
            <td style="padding:5px;">
                <input name="name" id="name" type="text" value="${customer.name}"/>
                <a class="customerQuery s_btn ml10" onclick="javascript:callback_findcustomer();"></a></td>
            <td style="padding:5px;">咨询产品</td>
            <td style="padding:5px;">
                <ol>
                    <input name="spellcode" id="tbSpellcode" type="text" class="easyui-combobox" />
                </ol>
            </td>
        </tr>
        <tr>
            <td style="padding:5px;">客户编号</td>
            <td style="padding:5px;"><input name="customerId" id="customerId" type="text"
                                            value="${customer.customerId}"  class="easyui-validatebox" data-options="required:true" /></td>
            <td style="padding:5px;">回呼时间</td>
            <td style="padding:5px;"><input class="easyui-datetimebox" name="callbackDt" id="callbackDt" type="text" value=""/></td>
        </tr>
        <tr style="height: 23px;">
            <td style="padding:5px;" valign="top">客户性别</td>
            <td style="padding:5px;" valign="top">
                <label><input name="sex" type="radio" value="1" <c:if test="${customer.sex == 1}">checked="checked"</c:if> />男</label>
                <label class="ml10"><input name="sex" type="radio" value="2"  <c:if test="${customer.sex == 2}">checked="checked"</c:if> />女</label></td>
            <td style="padding:5px;" valign="top">回呼备注</td>
            <td style="padding:5px;width:35%" rowspan="2"><textarea name="callbackRemark" id="callbackRemark" rows="5" cols="30"></textarea></td>
        </tr>
        <tr>
            <td style="padding:5px;" valign="top">优先级别</td>
            <td style="padding:5px;" valign="top">
            <select class="easyui-combobox" name="callbackPriority" id="callbackPriority">
                <OPTION selected value="1">非常重要</OPTION>
                <OPTION value="2">重要</OPTION>
                <OPTION value="3">一般</OPTION>
                <OPTION value="4">售后</OPTION>
                <OPTION value="5">骚扰</OPTION>
            </select>
            </td>
            <td></td>
        </tr>

    </table>
    </form>
    <p class="winBtnsBar textC mb10" style="background-color:white;margin-left: -20px;">
        <a class="window_btn" id="show_callback_quit" style="margin-right: 50px" href="javascript:void(0)" onclick="javascript:qiutCallback();">重置</a>
        <a class="window_btn" id="show_callback_save" style="margin-right: 50px" href="javascript:void(0)" onclick="javascript:savaCallBack();">保存</a>
        <a class="window_btn" href="javascript:void(0)" onclick="javascript:clearCallbackForm2();">清空</a>
        </p>
    <h3 class="sift" style="box-shadow: none;border-color: silver">

            <label style="display:inline-block;">当前客户订单历史</label>

    </h3>
    <table id="orderHistory"></table>
</div>

<script type="text/javascript">
    var orderTypeList=eval(${orderTypes});
    var orderPayTypeList=eval(${payTypes});
    var orderStatusList=eval(${orderStatuses});
    var orderLogisticsList=eval(${logisticsTypes});

</script>

</body>

</html>