<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<html>
<head>
    <script type="text/javascript" src="/static/scripts/json.min.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="/static/scripts/card/auth.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        #fm{
            margin:0;

        }
        #fm td{margin:auto;padding:5px;}
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
        .editTable td{padding:2px;}
    </style>
</head>
<body>

<div id="condition" region="north" border="false" >
    
        <table class="editTable" c>

            <tr>
                <td><label class="ml10" for="tbOrderid">订单号：</label></td>
                <td><input id="tbOrderid" class="inputStyle"  type="text" name="orderid" size="8"></td>
                <td><label class="ml10" for="tbCardrightnum">索权号：</label></td>
                <td><input id="tbCardrightnum" class="inputStyle" type="text" name="cardrightnum" size="8"></td>
                <td colspan="3">&nbsp;</td>
            </tr>
            <tr>
                <td><label class="ml10" for="tbImpdt1">导入开始：</label></td>
                <td><input id="tbImpdt1" class="easyui-datetimebox datebox-f combo-f" style="width:156px" name="impdt1" size="8"></td>
                <td style="text-align: center">导入结束：</td>
                <td><input id="tbImpdt2" class="easyui-datetimebox datebox-f combo-f" style="width:156px" name="impdt2" size="8"></td>
                <td colspan="3">&nbsp;</td>

            </tr>
            <tr>
                <td><label class="ml10" for="tbCardtype">信用卡：</label></td>
                <td><input id="tbCardtype" class="inputStyle easyui-combobox" name="cardtype" style="width:130px" size="8"></td>
                <td>&nbsp;</td>
                <td>
                    <input id="tbAuth" type="checkbox" class="floatStyle" name="verified">
                    <label class="" for="tbAuth" >只显示未索权</label>
                </td>
                <td colspan="3"><button class="Btn ml10" value="查找" type="button" id="btnSearch">查找</button></td>
            </tr>
        </table>
    
</div>
<div region="center" >
    <div id="toolbar">
        <a href="#" id="lbImport" class="easyui-linkbutton button" iconCls="icon-edit" plain="true" style="float:left;">导入</a>
        <a href="#" id="lbCapture" class="easyui-linkbutton button" iconCls="icon-edit" plain="true" style="float:left;">索权</a>
    </div>
    <div class="container">
        <table id="dg" title="订单发货单" 
               url="${url}"
               saveUrl="${saveUrl}"
               updateUrl="${updateUrl}"
               destroyUrl="${destroyUrl}"
               cellspacing="0" cellpadding="0" data-options=""  ></table>
    </div>
</div>

<div id="dlg" class="easyui-dialog" style="padding:10px 20px" closed="true" buttons="#dlg-buttons">
  <fieldset>
      <table style="width: 100%" cellpadding="4" cellspacing="4">
          <tr>
              <td>导入文件：</td>
              <td>
                  <form method="post" enctype="multipart/form-data" action="${postUrl}" style="display: inline">
                      <table>
                          <tr>
                              <td>
                                  <input type="file" id="uploadfile" name="uploadfile" size="30">
                              </td>
                              <td valign="bottom">
                                  <button type="submit" class="ml10 Btn">&nbsp;上   传&nbsp;</button>
                              </td>
                          </tr>
                          <tr>
                              <td colspan="2">
                                  <span style="float: left;"> 注：请按照模板导入文件.<a href="/static/files/cardClaims.xls" style="text-wrap: none;text-decoration:underline;">下载模板</a> </span>
                              </td>
                          </tr>
                      </table>
                  </form>
              </td>
          </tr>
      </table>

  </fieldset>
</div>
<div id ="messageInfo">
    <div id="p" class="easyui-panel" style="width:400px;padding:1px;">
    </div>
</div>
</body>
</html>
