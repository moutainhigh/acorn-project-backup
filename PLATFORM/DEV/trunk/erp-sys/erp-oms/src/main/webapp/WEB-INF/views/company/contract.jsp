<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/json.min.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="/static/scripts/company/contract.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        #fm{
            margin:0;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:145px;
            padding:5px 0 0;
        }
    </style>
</head>
<body>

<div id="condition">
    <table cellpadding="2" cellspacing="2">
        <tr>
            <td><label for="tbNccompanyId">NC承运商编码：</label></td>
            <td><input id="tbNccompanyId" type="text" name="nccompanyId"/></td>
            <td><label for="tbCompanyId">承运商二级部门编码：</label></td>
            <td><input id="tbCompanyId" type="text" name="id"/></td>
            <td></td>
        </tr>
        <tr>
            <td><label for="cbWarehouse">仓库：</label></td>
            <td><input id="cbWarehouse" style="width:150px" type="text" class="easyui-combobox" name="warehouseId"/></td>
            <td><label for="cbChannel">渠道：</label></td>
            <td><input id="cbChannel" style="width:150px" class="easyui-combobox" name="channelId" /></td>
            <td></td>
        </tr>
        <tr>
            <td><label for="tbStartDate">合同开始：</label></td>
            <td><input id="tbStartDate" style="width:150px" class="easyui-datebox datebox-f combo-f" name="startDate"/></td>
            <td><label for="tbEndDate">合同结束：</label></td>
            <td><input id="tbEndDate" style="width:150px" class="easyui-datebox datebox-f combo-f" name="endDate" /></td>
            <td></td>
        </tr>
        <tr>
            <td><label for="cbDistributionRegion">配送区域：</label></td>
            <td><input id="cbDistributionRegion" style="width:150px" class="easyui-combobox" name="quYuId" /></td>
            <td>
                <input id="tbStatus" type="checkbox" class="floatStyle" name="status" checked="true">
                <label class="" for="tbStatus" >状态是否有效</label>
            </td>
            <td><button id="btnSearch" type="button" value='查找'>&nbsp;&nbsp;查找</button></td>
            <td></td>
        </tr>
    </table>
</div>
<div>
    <div id="toolbar">
        <a href="#" id="lbNew" class="easyui-linkbutton button" iconCls="icon-add" plain="true" style="float:left;display: none">新增</a>
        <a href="#" id="lbEdit" class="easyui-linkbutton button" iconCls="icon-edit" plain="true" style="float:left">启用编辑</a>
        <a href="#" id="lbDelete" class="easyui-linkbutton button" iconCls="icon-remove" plain="true" style="float:left;display: none">删除</a>
        <a href="#" id="lbShow" class="easyui-linkbutton button" iconCls="icon-search" plain="true" style="float:left;display: none">禁用编辑</a>
    </div>
    <div class="container">
        <table id="dg" title="送货公司" cellspacing="0" cellpadding="0" data-options=""></table>
    </div>
</div>
<!--基本信息-->
<div style="height:350px;margin-top:7px">
<div id="tt" class="easyui-tabs" fit="true" >
   <div title="承运商合同维护" style="padding:10px 20px; width:auto;">
       <form id="fm" method="post" novalidate >
           <table cellspacing="4" cellpadding="4" border="0" style="width:100%;height:auto">
               <tr>
                   <td>
                       <div class="fitem">
                           <label>承运商ID:</label>
                           <input id="companyId" name="id" style="width:150px;" class="easyui-validatebox" disabled="true" required="true">
                       </div>
                   </td>
                   <td>
                       <div class="fitem">
                           <label>承运商名称:</label>
                           <input id=fmName name="name" style="width:150px;" class="easyui-validatebox" disabled="true" >
                       </div>
                   </td>
               </tr>
               <tr>
                   <td>
                       <div class="fitem">
                           <label>合同起始:</label>
                           <input id="fmBeginDate" name="beginDate" style="width:150px;" disabled="true" class="easyui-datebox">
                       </div>
                   </td>
                   <td>
                       <div class="fitem">
                           <label>合同中止：</label>
                           <input id="fmEndDate" name="endDate" style="width:150px;" disabled="true" class="easyui-datebox">
                       </div>
                   </td>
               </tr>
               <tr>
                   <td>
                       <div class="fitem">
                           <label>当前状态：</label>
                           <input id="fmStatus" name="status" style="width:150px;" disabled="true" class="easyui-combobox">
                       </div>
                   </td>
                   <td>
                       <div class="fitem">
                           <label>是否手工挑单：</label>
                           <input id="fmIsManual" name="isManual" style="width:150px;" disabled="true" class="easyui-combobox">
                       </div>
                   </td>
               </tr>
               <tr>
                   <td>
                       <div class="fitem">
                           <label>渠道：</label>
                           <input id="fmChannel" name="channel" style="width:150px;" disabled="true" class="easyui-combobox">
                       </div>
                   </td>
                   <td>
                       <div class="fitem">
                           <label>默认发货仓库：</label>
                           <input id="fmWarehouse" name="warehouse" style="width:150px;" disabled="true" class="easyui-combobox">
                       </div>
                   </td>

               </tr>
               <tr>
                   <td>
                       <div class="fitem">
                           <label>承运商类型：</label>
                           <input id="fmCompanyType" name="companyType" style="width:150px;" disabled="true" class="easyui-combobox">
                       </div>
                   </td>
                   <td>
                       <div class="fitem">
                           <label>运费计算方式：</label>
                           <input id="fmFreightMethod" name="freightMethod" style="width:150px;" disabled="true" class="easyui-combobox">
                       </div>
                   </td>
               </tr>

               <tr>
                   <td nowrap>
                       <div class="fitem">
                           <label>担保形式：</label>
                           <input id="fmSuretyType" name="suretyType" style="width:150px;" disabled="true" class="easyui-combobox">
                       </div>
                   </td>
                   <td nowrap>
                       <div class="fitem">
                           <label>保证金：</label>
                           <input name="performanceBondAmount" style="width:150px;" disabled="true" class="easyui-numberbox" maxlength="19" precision="2" min="0">
                       </div>
                   </td>
               </tr>
               <tr>
                   <td nowrap>
                       <div class="fitem">
                           <label>配送能力（最大金额）：</label>
                           <input name="amountCapacity" style="width:150px;" disabled="true" class="easyui-numberbox" maxlength="19" precision="2" min="0" >
                       </div>
                   </td>
                   <td nowrap>
                       <div class="fitem">
                           <label>配送能力（最大单量）：</label>
                           <input name="carryCapacity" style="width:150px;" disabled="true" class="easyui-numberbox" maxlength="19" precision="0" min="0" >
                       </div>
                   </td>
               </tr>

               <tr>
                  <td nowrap>
                        <div class="fitem">
                            <label>风险控制系数：</label>
                            <input name="creditMarginAmount" style="width:150px;" disabled="true" class="easyui-numberbox" maxlength="19" precision="2" min="0" >
                        </div>
                   </td>
                      <td nowrap>
                          <div class="fitem">
                              <label>配比系数：</label>
                              <input name="matchingRatio" style="width:150px;" disabled="true" class="easyui-numberbox" maxlength="19" precision="0" min="1" max="10">
                              （1-10分）
                          </div>
                      </td>
               </tr>
               <tr>
                   <td nowrap>
                       <div class="fitem">
                           <label>实际风险系数(BI)：</label>
                           <input id = "fmActualRiskFactor" name="actualRiskFactor" style="width:150px;background-color:#dcdcdc; border: none" disabled="true" class="easyui-numberbox" maxlength="19" precision="2">
                       </div>
                   </td>
                   <td nowrap>
                       <div class="fitem">
                           <label>送货成功率(BI)：</label>
                           <input id ="fmDeliverySuccessRate" name="deliverySuccessRate" style="width:150px; background-color: #dcdcdc; border: none" disabled="true" class="easyui-numberbox" maxlength="19" precision="2">
                       </div>
                   </td>
               </tr>
               <tr>
                   <td nowrap>
                       <div class="fitem">
                           <label>平均每日发包金额：</label>
                           <input name="dailyAmount" style="width:150px;" disabled="true" class="easyui-numberbox" maxlength="19" precision="2" min="0">
                       </div>
                   </td>
                   <td nowrap>
                       <div class="fitem">
                           <label>配送区域：</label>
                           <input id="fmDistributionRegion" name="distributionRegion" style="width:150px;" disabled="true" class="easyui-combobox">
                       </div>
                   </td>
               </tr>
               <tr>
                   <td nowrap>
                       <div class="fitem">
                           <label>发包清单模板编号：</label>
                           <input name="packTempLateId" style="width:150px;" disabled="true" class="easyui-numberbox" maxlength="19" precision="0" min="1" max="9">
                           （1-9）
                       </div>
                   </td>
                   <td nowrap>
                   </td>
               </tr>
               <tr>
                   <td colspan="2">
                       <div class="fitem">
                           <label>承运商负责人邮件：</label>
                           <input name="informEmail" disabled="true" class="easyui-validatebox" validtype="length[0,500]"  style="width: 500px">
                       </div>
                   </td>
               </tr>
               <tr>
                   <td colspan="2">
                       <div class="fitem">
                           <label>承运商运营邮件：</label>
                           <input name="optsEmail" disabled="true" class="easyui-validatebox"  validtype="length[0,500]" invalidMessage="最大长度120位" style="width: 500px">
                       </div>
                   </td>
               </tr>
               <tr>
                   <td colspan="2">
                       <div class="fitem">
                           <label>橡果负责人邮件：</label>
                           <input name="acornInformEmail" disabled="true" class="easyui-validatebox" validtype="length[0,500]" invalidMessage="最大长度6位" style="width: 500px" >
                       </div>
                   </td>
               </tr>
               <tr>
                   <td colspan="2">
                       <div class="fitem">
                           <label>橡果运营邮件列表：</label>
                           <input name="acornOptsEmail" disabled="true" class="easyui-validatebox" style="width: 500px" >
                       </div>
                   </td>
               </tr>

               <tr>
                   <td colspan="2">
                       <div class="fitem">
                           <label>保证额度超标邮件列表：</label>
                           <input name="riskEMAIL" disabled="true" class="easyui-validatebox" style="width: 500px" >
                       </div>
                   </td>
               </tr>
               <tr>
                   <td colspan="2">
                       <div class="fitem">
                           <label>保证额度超标可选列表：</label>
                           <input name="riskOptsEmail" disabled="true" class="easyui-validatebox" style="width: 500px" >
                       </div>
                   </td>
               </tr>
               <tr>
                   <td colspan="2" style="text-align: center"><a href="javascript:void(0)" id="lbSave" class="easyui-linkbutton" iconCls="icon-ok" style="display: none">保存</a></td>
               </tr>
           </table>
       </form>
   </div>

</div>
</div>
<!--
<div id="dlg" class="easyui-dialog" style="width:640px;height:500px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
    <div>承运商合同维护</div>

</div>

<div id="dlg-buttons">
    <a href="javascript:void(0)" id="lbSave" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" id="lbClose" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
</div>

<div id="dlg-view" class="easyui-dialog" style="width:640px;height:500px;padding:10px 20px" closed="true" buttons="#dlg-view-buttons">
    <div class="ftitle">送货公司</div>

</div>
<div id="dlg-view-buttons">
    <a href="javascript:void(0)" id="lbClose-view" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
</div>
-->

</body>

</html>
