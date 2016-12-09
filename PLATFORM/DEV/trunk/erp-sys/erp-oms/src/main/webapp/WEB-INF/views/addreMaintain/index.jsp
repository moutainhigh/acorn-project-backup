<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="/static/scripts/ajaxfileupload.js"></script>
<script type="text/javascript" src="/static/scripts/addreMaintain/address.js"></script>
<link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
      <!--地址组维护-->
	<div id="condition">
		<div>
			<label  for="areagroupId" class="w100"  >地址组ID:</label>
            <input id="areagroupId"  type="text"  name="areagroupId"size="20"class="easyui-numberbox" data-options="required:false" />
            <label  for="areagroupName" class="ml10 w80">地址组名称:</label>
            <input id="areagroupName"  type="text"  name="areagroupName"size="20" />
		</div>
        <div>
            <label  for="priority" class="w100">优先级别:</label>
            <input id="priority"  type="text"  name="priority"class="easyui-numberbox" data-options="required:false" />
            <label for="entityId" class="ml10 w80">承运商:</label>
            <select name="entityId" id="entityId" class="easyui-combobox" style="width: 150px">
                <option value="">===请选择===</option>
                <c:forEach items="${companyList }" var="company">
                    <option value="${company.companyid }">${company.name }</option>
                </c:forEach>
            </select>
        </div>
        <div>
            <label  for="channelId" class="w100">渠道ID:</label>
            <select name="channelId" id="channelId" style="width: 150px">
                <option value="">===请选择===</option>
                <c:forEach items="${channelList }" var="channel">
                    <option value="${channel.id }">${channel.channelName }</option>
                </c:forEach>
            </select>
            <span>
               <input type="button" id="queryBtn" value="查询" class="ml10 Btn" name="">
            </span>
        </div>
	</div>
<div id="toolbar">
    <a href="#" id="lbAdd" class="easyui-linkbutton l-btn l-btn-plain" iconCls="icon-add" plain="true">新增</a>
    <a plain="true" iconcls="icon-redo" class="easyui-linkbutton l-btn l-btn-plain" id="btncut" href="javascript:exportOderList();">导出明细</a>
    <a  plain="true" iconcls="icon-back" class="easyui-linkbutton l-btn l-btn-plain" id="btnExcelUpload" >导入明细</a>
    <a href="#" id="lbOn" class="easyui-linkbutton l-btn l-btn-plain" iconCls="icon-remove" plain="true">启用</a>
    <a href="#" id="lbOff" class="easyui-linkbutton l-btn l-btn-plain" iconCls="icon-remove" plain="true">停用</a>
</div>
	<div class="container">

		<table id="addressTable" cellspacing="0" cellpadding="0" data-options="">

    </table>
	</div>

      <!--新增基本信息-->
      <div id="dlg" class="easyui-dialog" style="padding:10px 20px" closed="true" buttons="#dlg-buttons">
          <form id="fm" name="formsubmitf" method="post" novalidate>
              <input type=hidden name='mypretime' value='0'>
              <table cellspacing="4" cellpadding="4" border="0" align="center" style="height:auto">
                  <tr>
                      <td>
                          <div class="fitem" style="margin-top: 5px">
                              <label>地址组名称：</label>
                              <input id="apName" name="apName" size="27" class="easyui-validatebox" data-options="required:true">
                          </div>
                      </td>
                  </tr>
                  <tr>
                      <td>
                          <div class="fitem" style="margin-top: 5px">
                              <label>地址组描述：</label>
                              <textarea name="apDesc" id="apDesc" cols="30" rows="5" class="easyui-validatebox" data-options="required:true"></textarea>
                          </div>
                      </td>
                  </tr>
                  <tr>
                      <td>
                          <div class="fitem" style="margin:5px ">
                              <label>请选择渠道：</label>
                              <select name="cid" id="cid" class="easyui-validatebox" data-options="required:true">
                                  <option value="">=======请选择=======</option>
                                  <c:forEach items="${channelList }" var="channel">
                                      <option value="${channel.id }">${channel.channelName }</option>
                                  </c:forEach>
                              </select>
                          </div>
                      </td>
                  </tr>
              </table>
          </form>
      </div>

      <div id="dlg-buttons">
          <%--<a href="#" id="lbSave" class="easyui-linkbutton" iconCls="icon-ok">保存</a>--%>
          <a href="#" id="lbSave" class="easyui-linkbutton" data-options="iconCls:'icon-save'" iconCls="icon-ok">保存</a>
          <a href="#" id="lbClose" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
      </div>
      <!--文件上传-->
      <div id="uploadFileDiv" class="easyui-window" data-options="closed:true,modal:false,title:'导入'" style="width:300px;height:100px;">
          <!---->
          <input id="fileToUpload" type="file" name="fileToUpload" class="input">
          <button class="button" id="buttonUpload" onclick="return ajaxFileUpload();">上传</button>
          <br/>
          <img id="loading" src="/static/images/loading.gif" style="display:none;">
      </div>
      <!--显示地址组明细-->
      <div id="showAddressList" class="easyui-dialog" style="padding:5px 5px" closed="true">

          <table id="areaGroupDetailList" cellspacing="0" cellpadding="0">

          </table>
      </div>
      <!--导出明细-->
      <form action="/addreMaintain/download" method="post" target="downFrame" id="downloadForm">
          <input type="hidden" id="d_areId" name="areId" value=""/>
      </form>
      <!--下载隐藏frame-->
      <iframe style="display:none" id="downFrame" name="downFrame" src=""></iframe>
</body>
</html>
