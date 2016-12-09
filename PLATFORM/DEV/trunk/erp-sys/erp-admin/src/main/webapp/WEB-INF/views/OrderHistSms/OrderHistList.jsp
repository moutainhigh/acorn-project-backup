
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page import="com.chinadrtv.erp.user.util.SecurityHelper" %>
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/orderHistLog/orderHistLog.js"></script>
     <script type="text/javascript" src="/static/scripts/orderHistLog/orderHistSmsLog.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
   <script type="text/javascript" src="/static/scripts/orderHistLog/Js/jquery.easyui.min.js"></script>
   <!-- <script src="/static/scripts/orderHistLog/Js/jquery-1.3.2.js" type="text/javascript"></script>
  <script src="/static/scripts/orderHistLog/Js/jquery.bgiframe.min.js" type="text/javascript"></script>
 <script src="/static/scripts/orderHistLog/Js/jquery.multiSelect.js" type="text/javascript"></script>
   <link rel="stylesheet" type="text/css" href="/static/scripts/orderHistLog/Css/jquery.multiSelect.css">-->
    <link rel="stylesheet" type="text/css" href="/static/scripts/orderHistLog/Css/demo.css">
    <link rel="stylesheet" type="text/css" href="/static/scripts/orderHistLog/Css/orderType.css">
    <script type="text/javascript">

        $(document).ready( function() {
            // Options displayed in comma-separated list
           // $("#v_ordertype").multiSelect({ oneOrMoreSelected: '*' });
        });
        function one(){
            //alert($("#control_7").html());
        }

    </script>
</head>
<body>
<div class="easyui-layout" fit="true" border="false" style="height:600px;">
        <div region="north" style="height:160px">
           <h2 style="text-align: center;background-color: #ebf2ff;">短信配置</h2>
            <input type="hidden" id="LoginName" value="<%= SecurityHelper.getLoginUser().getUserId() %>"/>
            <table id="selTable" cellspacing="1" cellpadding="1" border="0" style="width:100%;">
                <tr style="border:8px solid white;">
                    <td><label for="smstype"> 短信类型:</label></td>
                       <td><select id="smstype" name="smstype"style="width:180px" >
                            <option value="1" selected> 订购短信
                            <option value="2">验证码短信
                            <option value="3">出库短信
                        </select></td>
                    <td><label for="ordertypeId">订单类型:</label></td>
                       <td> <select id="ordertypeId" name="ordertypeId" multiple="multiple" style="width:180px">
                        </select></td>
                </tr>
                <tr style="border:8px solid white;">
                    <td><label for="paytype">付款方式: </label> </td>
                    <td><select id="paytype" style="width:180px" name="paytype" multiple="multiple"></select></td>
                    <td><label for="smsName">短信名称:</label></td>
                    <td> <input id="smsName" name="smsName" size="25" type="text" style="width:180px" maxlength="25"/></td>
                </tr>
                <tr style="border:8px solid white" >
                    <td><label for="beginDate">开始时间:</label></td>
                       <td> <input id="beginDate" class="easyui-datebox" data-options="formatter:myformatter" style="width:180px"/></td>
                       <td><label for="endDate">结束时间:</label></td>
                      <td><input id="endDate" type="text" class="easyui-datebox"  data-options="formatter:myformatter"   style="width:180px"/></td>
                </tr>
            </table>
            <div > <!--id="toolbar"-->
                <a href="javascript:openNew();" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="">新增</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="DeleteRow()">删除</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="UpdateRow()">修改</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="RefereshOHistData()">刷新</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="queryOHistData()">查找</a>
            </div>
    </div>

        <div region="center" style="height:430px" style="padding:4px">
            <div style="padding: 3px;height:400px;width:1500px">
                <table id="listOHistLogTable"cellspacing="0" cellpadding="0" toolbar="#toolbar"></table>
            </div>
            <div id="dlg" class="easyui-dialog" style="width:600px;height:420px;text-align:center;" closed="true" buttons="#dlg-buttons">
                     <table cellpadding="5" cellspacing="5">
                    <tr style="border:8px solid white;"><td> <input type="hidden" id="v_id"/><label for="v_smstype">短信类型</label>&nbsp;</td>
                  <td><select id="v_smstype" style="width:150px" >
                <option value="1" selected> 订购短信
                <option value="2">验证码短信
                <option value="3">出库短信
               </select></td>
             <td><label for="v_paytype">付款方式</label>&nbsp;</td>
            <td><select id="v_paytype" style="width:150px" name="v_paytype" multiple="multiple"></select></td></tr>
        <tr style="border:8px solid white;">
            <td> <label for="v_ordertype">订单类型</label>&nbsp;</td>
            <td><select id="v_ordertype" style="width:150px" name="v_ordertype" multiple="multiple"></select></td>
            <td><label for="shopName">网店名称</label>&nbsp;</td>
            <td> <select id="shopName" style="width:150px" name="shopName" style="WIDTH:150px"></select></td></tr>
        <tr style="border:8px solid white;">
            <td> <label for="v_setProId">快递公司</label>&nbsp;</td>
            <td> <select id="v_setProId" style="width:150px"class="easyui-combobox" name="v_setProId" style="WIDTH:150px"></select></td>
            <td><label for="v_sendDelyTime">延迟时间</label>&nbsp;</td>
            <td><input id="v_sendDelyTime" name="v_sendDelyTime" class="easyui-numberbox" style="width:150px" type="text"  maxlength="19"/><h6 style="color: red">(小时)</h6></td></tr>
        <tr style="border:8px solid white;">
            <td> <label for="v_smsName">短信名称</label>&nbsp;</td>
            <td colspan=2> <input id="v_smsName" name="v_smsName" class="easyui-validatebox" size="30" type="text"  maxlength="30"/></td>
            <td></td></tr>
        <tr style="border:8px solid white;">
            <td><button id="butpar"onclick="yulan()">添加参数</button></td>
            <td></td>
            <td></td>
            <td></td></tr>
        <tr style="border:8px solid white;"><td colspan=4><textarea id="v_smscontent" name="v_smscontent" rows="10" cols="70" style="max-width:500px;max-height:270px"></textarea></td></tr>
        <tr style="border:8px solid white;"><td colspan="4" style="text-align: right;"> <a href="#" id="saveSms" class="easyui-linkbutton" iconCls="icon-ok"plain="true" onclick="SmstypeSave()">保存</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-cancel"plain="true" onclick="closeinsert()">取消</a></td></tr>
    </table>
    </div>
 </div>
</div>
</body>
</html>
