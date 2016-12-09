<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/json.min.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="/static/scripts/company/company.js"></script>
    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
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
        }
    </style>
</head>
<body>

<div class="easyui-layout" fit="true" border="false" style="height:500px">
      <div region="north" style="height:80px;">
          <div style="padding: 8px;">
              <table cellspacing="4" cellpadding="4" border="0">
                    <tr>
                        <td><label for="ci">公司编码:</label></td>
                        <td><input id="ci" name="companyId"/></td>
                        <td><label for="cn">公司名称:</label></td>
                        <td><input id="cn" name="companyName"/></td>
                        <td></td>
                    </tr>
                  <tr>
                      <td><label for="ct">公司类型:</label></td>
                      <td><input id="ct" style="width:150px" class="easyui-combobox" name="companyType"/></td>
                      <td><label for="mt">订购方式:</label></td>
                      <td><input id="mt" style="width:150px" class="easyui-combobox" name="mailType" /></td>
                      <td> <button id="btnSearch" type="button" value='查找'>查找</button></td>
                  </tr>
              </table>

          </div>
      </div>
      <div region="center" style="padding:4px">
          <div id="toolbar">
              <a href="#" id="lbNew" class="easyui-linkbutton" iconCls="icon-add" plain="true" style="float:left;">新增</a>
              <a href="#" id="lbEdit" class="easyui-linkbutton" iconCls="icon-edit" plain="true" style="float:left;">编辑</a>
              <a href="#" id="lbDelete" class="easyui-linkbutton" iconCls="icon-remove" plain="true" style="float:left;">删除</a>
              <a href="#" id="lbShow" class="easyui-linkbutton" iconCls="icon-search" plain="true" style="float:left;">查看</a>
              <a href="#" id="lbLine" class="easyui-linkbutton" iconCls="icon-edit" plain="true" style="float:right;">费用</a>
              <a href="#" id="lbLeg" class="easyui-linkbutton" iconCls="icon-edit" plain="true" style="float:right;">路程</a>
              <a href="#" id="lbQuota" class="easyui-linkbutton" iconCls="icon-edit" plain="true" style="float:right;">额度</a>
              <a href="#" id="lbVersion" class="easyui-linkbutton" iconCls="icon-edit" plain="true" style="float:right;">版本</a>
          </div>
          <div style="padding: 3px;height: 350px">
              <table id="dg" title="送货公司" fit="true" cellspacing="0" cellpadding="0" toolbar="#toolbar"></table>
          </div>

          <!--基本信息-->
          <div id="dlg" class="easyui-dialog" style="width:640px;height:500px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
              <div class="ftitle">送货公司</div>
              <form id="fm" method="post" novalidate>
                  <table cellspacing="4" cellpadding="4" border="0" style="width:100%;height:auto">
                      <tr>
                          <td>
                              <div class="fitem">
                                  <label>公司编码:</label>
                                  <input id="inpCompanyId" name="companyId" class="easyui-validatebox" validtype="length[1,5]" invalidMessage="最大长度5位" required="true">
                              </div>
                          </td>
                          <td>
                              <div class="fitem">
                                  <label>NC公司编码:</label>
                                  <input name="nccompanyId" class="easyui-validatebox" validtype="length[1,50]" invalidMessage="最大长度50位" required="true">
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td>
                              <div class="fitem">
                                  <label>公司名称:</label>
                                  <input name="companyName" class="easyui-validatebox"  validtype="length[0,80]" invalidMessage="最大长度80位">
                              </div>
                          </td>
                          <td>
                              <div class="fitem">
                                  <label>公司类型:</label>
                                  <input id="cbxCompanyType" name="companyType" class="easyui-combobox">
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td>
                              <div class="fitem">
                                  <label>有效:</label>
                                  <input id="chkdescription" type="radio" name="description" value="-1" checked>是
                                  <input type="radio" name="description" value="0">否
                              </div>
                          </td>
                          <td>
                              <div class="fitem">
                                  <label>订购类型:</label>
                                  <input id="cbxMailType" name="mailType" class="easyui-combobox">
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td nowrap>
                              <div class="fitem">
                                  <label>打印发票:</label>
                                  <input type="radio" name="bill" value="-1" >是
                                  <input id="chkbill" type="radio" name="bill" value="0" checked="checked">否
                              </div>
                          </td>
                          <td nowrap>
                              <div class="fitem">
                                  <label>投递费:</label>
                                  <input name="postFee" class="easyui-validatebox">
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td nowrap>
                              <div class="fitem">
                                  <label>是否分支物流:</label>
                                  <input type="radio" name="zip" value="-1" >是
                                  <input id="chkzip" type="radio" name="zip" value="0" checked="checked">否
                              </div>
                          </td>
                          <td nowrap>
                              <div class="fitem">
                                  <label>电子邮件:</label>
                                  <input name="email" class="easyui-validatebox" validType="email">
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td nowrap>
                              <div class="fitem">
                                  <label>区号:</label>
                                  <input name="areaCode" class="easyui-validatebox" validtype="length[0,10]" invalidMessage="最大长度10位">
                              </div>
                          </td>
                          <td nowrap>
                              <div class="fitem">
                                  <label>传真:</label>
                                  <input name="fax" class="easyui-validatebox" validtype="length[0,20]" >
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td nowrap>
                              <div class="fitem">
                                  <label>电话:</label>
                                  <input name="phone" class="easyui-validatebox" validtype="length[0,24]" >
                              </div>
                          </td>
                          <td nowrap>
                              <div class="fitem">
                                  <label>分机号码:</label>
                                  <input name="subPhone" class="easyui-validatebox"  validtype="length[0,5]">
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td colspan="2" nowrap>
                              <div class="fitem">
                                  <label>公司地址:</label>
                                  <input name="address" class="easyui-validatebox"  validtype="length[0,120]" invalidMessage="最大长度120位" style="width: 400px">
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td nowrap>
                              <div class="fitem">
                                  <label>公司录入简码:</label>
                                  <input name="shortCode" class="easyui-validatebox" validtype="length[0,6]" invalidMessage="最大长度6位" >
                              </div>
                          </td>
                          <td nowrap>
                              <div class="fitem">
                                  <label>公司所在城市名:</label>
                                  <input name="cityName" class="easyui-validatebox" validtype="length[0,15]" invalidMessage="最大长度15位">
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td nowrap>
                              <div class="fitem">
                                  <label>邮政编号:</label>
                                  <input name="mailCode" class="easyui-validatebox" validtype="length[0,15]" invalidMessage="最大长度15位" >
                              </div>
                          </td>
                          <td nowrap>
                              <div class="fitem">
                                  <label>公司所在城市号:</label>
                                  <input name="spellId" class="easyui-validatebox" validtype="length[0,60]" invalidMessage="最大长度60位">
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td colspan="2">
                              <div class="fitem">
                                  <label>公司备注:</label>
                                  <input name="remark" class="easyui-validatebox" validtype="length[0,200]"  invalidMessage="最大长度200位" style="width: 400px">
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td nowrap>
                              <div class="fitem">
                                  <label>发票包含运费:</label>
                                  <input id="chkbillPostFee" type="radio" name="billPostFee" value="-1" checked="checked">是
                                  <input type="radio" name="billPostFee" value="0">否
                              </div>
                          </td>
                          <td nowrap>
                              <div class="fitem">
                                  <div>打印“不需要”发票:
                                  <input type="radio" name="noUseBill" value="-1" >是
                                  <input id="chknoUseBill" type="radio" name="noUseBill" value="0" checked="checked">否
                                  </div>
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td nowrap>
                              <div class="fitem">
                                  <label>公司对应拒收投递费:</label>
                                  <input name="refPostFee" class="easyui-validatebox" validType="Double">
                              </div>
                          </td>
                          <td nowrap>
                              <div class="fitem">
                                  <label>目标仓库:</label>
                                  <select name="toWarehouse" class="easyui-validatebox">
                                      <option value="" selected="selected"></option>
                                      <option value="华新仓">华新仓</option>
                                      <option value="北京仓">北京仓</option>
                                      <option value="成都仓">成都仓</option>
                                      <option value="广州仓">广州仓</option>
                                      <option value="自备小库">自备小库</option>
                                  </select>
                              </div>
                          </td>
                      </tr>
                  </table>
              </form>
          </div>

          <div id="dlg-buttons">
              <a href="javascript:void(0)" id="lbSave" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
              <a href="javascript:void(0)" id="lbClose" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
          </div>

          <!--基本信息查看-->
          <div id="dlg-view" class="easyui-dialog" style="width:640px;height:500px;padding:10px 20px" closed="true" buttons="#dlg-view-buttons">
              <div class="ftitle">送货公司</div>
                  <table cellspacing="4" cellpadding="4" border="0" style="width:100%;height:auto">
                      <tr>
                          <td>
                              <div class="fitem">
                                  <label>公司编码:</label>
                                  <label id="lblCompanyId"></label>
                              </div>
                          </td>
                          <td>
                              <div class="fitem">
                                  <label>NC公司编码:</label>
                                  <label id="lblNccompanyId"></label>
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td>
                              <div class="fitem">
                                  <label>公司名称:</label>
                                  <label id="lblCompanyName"></label>
                              </div>
                          </td>
                          <td>
                              <div class="fitem">
                                  <label>公司类型:</label>
                                  <label id="lblCompanyType"></label>
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td>
                              <div class="fitem">
                                  <label>有效:</label>
                                  <label id="lblDescription"></label>
                              </div>
                          </td>
                          <td>
                              <div class="fitem">
                                  <label>订购类型:</label>
                                  <label id="lblMailType"></label>
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td nowrap>
                              <div class="fitem">
                                  <label>打印发票:</label>
                                  <label id="lblBill"></label>
                              </div>
                          </td>
                          <td nowrap>
                              <div class="fitem">
                                  <label>投递费:</label>
                                  <label id="lblPostFee"></label>
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td nowrap>
                              <div class="fitem">
                                  <label>是否分支物流:</label>
                                  <label id="lblZip"></label>
                              </div>
                          </td>
                          <td nowrap>
                              <div class="fitem">
                                  <label>电子邮件:</label>
                                  <label id="lblEmail"></label>
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td nowrap>
                              <div class="fitem">
                                  <label>区号:</label>
                                  <label id="lblAreaCode"></label>
                              </div>
                          </td>
                          <td nowrap>
                              <div class="fitem">
                                  <label>传真:</label>
                                  <label id="lblFax"></label>
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td nowrap>
                              <div class="fitem">
                                  <label>电话:</label>
                                  <label id="lblPhone"></label>
                              </div>
                          </td>
                          <td nowrap>
                              <div class="fitem">
                                  <label>分机号码:</label>
                                  <label id="lblSubPhone"></label>
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td colspan="2" nowrap>
                              <div class="fitem">
                                  <label>公司地址:</label>
                                  <label id="lblAddress"></label>
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td nowrap>
                              <div class="fitem">
                                  <label>公司录入简码:</label>
                                  <label id="lblShortCode"></label>
                              </div>
                          </td>
                          <td nowrap>
                              <div class="fitem">
                                  <label>公司所在城市名:</label>
                                  <label id="lblCityName"></label>
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td nowrap>
                              <div class="fitem">
                                  <label>邮政编号:</label>
                                  <label id="lblMailCode"></label>
                              </div>
                          </td>
                          <td nowrap>
                              <div class="fitem">
                                  <label>公司所在城市号:</label>
                                  <label id="lblSpellId"></label>
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td colspan="2">
                              <div class="fitem">
                                  <label>公司备注:</label>
                                  <label id="lblRemark"></label>
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td nowrap>
                              <div class="fitem">
                                  <label>发票包含运费:</label>
                                  <label id="lblBillPostFee"></label>
                              </div>
                          </td>
                          <td nowrap>
                              <div class="fitem">
                                  <div>打印“不需要”发票:
                                      <label id="lblNoUseBill"></label>
                                  </div>
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td nowrap>
                              <div class="fitem">
                                  <div>公司对应拒收投递费:
                                     <label id="lblRefPostFee"></label>
                                  </div>
                              </div>
                          </td>
                          <td nowrap>
                              <div class="fitem">
                                  <label>目标仓库:</label>
                                  <label id="lblToWarehouse"></label>
                              </div>
                          </td>
                      </tr>
                  </table>
              </form>
          </div>
          <div id="dlg-view-buttons">
              <a href="javascript:void(0)" id="lbClose-view" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
          </div>

          <!--费用版本-->
          <div id="toolbar-version">
              <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" style="float:left;" onclick="javascript:$('#dg-version').edatagrid('addRow')">新增</a>
              <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" style="float:left;" onclick="javascript:$('#dg-version').edatagrid('destroyRow')">删除</a>
              <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" style="float:left;" onclick="javascript:$('#dg-version').edatagrid('saveRow');">保存</a>
              <a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" style="float:left;" onclick="javascript:$('#dg-version').edatagrid('cancelRow')">取消</a>
          </div>
          <div id="dlg-version" class="easyui-dialog" style="width:640px;height:500px;padding:10px 20px" closed="true" buttons="#dlg-version-buttons">
              <div class="ftitle">费用版本</div>
              <table id="dg-version" title="费用版本" fit="true" cellspacing="0" cellpadding="0" toolbar="#toolbar-version"></table>
          </div>
          <div id="dlg-version-buttons">
              <a href="javascript:void(0)" id="lbClose-version" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
          </div>

          <!--费用额度-->
          <div id="toolbar-quota">
              <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" style="float:left;" onclick="javascript:$('#dg-quota').edatagrid('addRow')">新增</a>
              <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" style="float:left;" onclick="javascript:$('#dg-quota').edatagrid('destroyRow')">删除</a>
              <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" style="float:left;" onclick="javascript:$('#dg-quota').edatagrid('saveRow');">保存</a>
              <a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" style="float:left;" onclick="javascript:$('#dg-quota').edatagrid('cancelRow')">取消</a>
          </div>
          <div id="dlg-quota" class="easyui-dialog" style="width:640px;height:500px;padding:10px 20px" closed="true" buttons="#dlg-quota-buttons">
              <div class="ftitle">费用额度</div>
              <table id="dg-quota" title="费用额度" fit="true" cellspacing="0" cellpadding="0" toolbar="#toolbar-quota"></table>
          </div>
          <div id="dlg-quota-buttons">
              <a href="javascript:void(0)" id="lbClose-quota" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
          </div>

          <!--费用路程-->
          <div id="toolbar-leg">
              <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" style="float:left;" onclick="javascript:$('#dg-leg').edatagrid('addRow')">新增</a>
              <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" style="float:left;" onclick="javascript:$('#dg-leg').edatagrid('destroyRow')">删除</a>
              <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" style="float:left;" onclick="javascript:$('#dg-leg').edatagrid('saveRow');">保存</a>
              <a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" style="float:left;" onclick="javascript:$('#dg-leg').edatagrid('cancelRow')">取消</a>
          </div>
          <div id="dlg-leg" class="easyui-dialog" style="width:760px;height:500px;padding:10px 20px" closed="true" buttons="#dlg-leg-buttons">
              <div class="ftitle">费用路程</div>
              <table id="dg-leg" title="费用路程" fit="true" cellspacing="0" cellpadding="0" toolbar="#toolbar-leg"></table>
          </div>
          <div id="dlg-leg-buttons">
              <a href="javascript:void(0)" id="lbClose-leg" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
          </div>

          <!--费用明细-->
          <div id="toolbar-line">
              <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" style="float:left;" onclick="javascript:$('#dg-line').edatagrid('addRow')">新增</a>
              <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" style="float:left;" onclick="javascript:$('#dg-line').edatagrid('destroyRow')">删除</a>
              <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" style="float:left;" onclick="javascript:$('#dg-line').edatagrid('saveRow');">保存</a>
              <a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" style="float:left;" onclick="javascript:$('#dg-line').edatagrid('cancelRow')">取消</a>
          </div>
          <div id="dlg-line" class="easyui-dialog" style="width:860px;height:500px;padding:10px 20px" closed="true" buttons="#dlg-line-buttons">
              <div class="ftitle">费用明细</div>
              <table id="dg-line" title="费用明细" fit="true" cellspacing="0" cellpadding="0" toolbar="#toolbar-line"></table>
          </div>

          <div id="dlg-line-buttons">
              <a href="javascript:void(0)" id="lbClose-line" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
          </div>

          <!--送货公司费用编辑窗体-->
          <div id="dlg-line-form" class="easyui-dialog" style="width:760px;height:500px;padding:10px 20px" closed="true" buttons="#dlg-line-form-buttons">
              <form id="fm-line" method="post" novalidate>
                  <table>
                      <tr>
                          <td colspan="3">
                              <div class="fitem">
                                  <label>优先级:</label>
                                  <input name="priority" class="easyui-validatebox" required="true">
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td>
                              <div class="fitem">
                                  <label>版本:</label>
                                  <input name="version" class="easyui-validatebox" required="true">
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td>
                              <div class="fitem">
                                  <label>额度:</label>
                                  <input name="quota" class="easyui-validatebox" required="true">
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td>
                              <div class="fitem">
                                  <label>路程:</label>
                                  <input name="leg" class="easyui-validatebox" required="true">
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td>
                              <div class="fitem">
                                  <label>投递费/首重费:</label>
                                  <input name="basePrice" class="easyui-validatebox" required="true">
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td>
                              <div class="fitem">
                                  <label>提货费/续重费:</label>
                                  <input name="surcharge" class="easyui-validatebox" required="true">
                              </div>
                          </td>
                      </tr>
                      <tr>
                          <td>
                              <div class="fitem">
                                  <label>手续费率:</label>
                                  <input name="baseRate" class="easyui-validatebox" required="true">
                              </div>
                          </td>
                      </tr>
                  </table>
              </form>
          </div>
          <div id="dlg-line-form-buttons">
              <a href="javascript:void(0)" id="lbClose-line-form" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
          </div>
      </div>
  </div>

</body>
</html>
