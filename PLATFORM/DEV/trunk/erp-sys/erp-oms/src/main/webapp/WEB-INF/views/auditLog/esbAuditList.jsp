<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/auditLog/esbAuditLog.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
</head>
<body>

     <div id="v_company" style="display:none" >${company_json}</div>
     <div id="condition">
     <div>
			<label class="labelTitle" style="width:auto" for="esbName">ESB名称:</label> 
			<input id="esbName" name="esbName" type="text" class="inputStyle" size="8"> 
			<label class="labelTitle ml10" style="width:auto" for="errorCode">错误代码:</label>
		    <input id="errorCode" name="errorCode" type="text" class="inputStyle"  size="20">
		    <label class="labelTitle ml10" style="width:auto" for="companyid">承运商:</label>
		 	<select name="companyid" id="companyid"  class="inputStyle"   >
				<option value="">======请选择======</option>  
 				<c:forEach var="n" items="${company_req}">
			    <option value="${n.companyid}">${n.name}</option>
			    </c:forEach>
		    </select>
	 </div>
	 			<div>
			
			<label class="labelTitle" style="width:auto">产生日期:</label><label for="beginDate" style="line-height:22px">从:</label>
			<input  id="beginDate" class="easyui-datebox datebox-f combo-f" style="width:130px" >
			<label for="endDate" style="line-height:22px">到:</label>
			<input type="text" id="endDate" class="easyui-datebox datebox-f combo-f" style="width:130px"  >
			<button class="Btn ml10" value="查找" name="queryAuditLogBtn" type="button" id="queryAuditLogBtn" onclick="queryAuditData()"> 查找</button>
				</div>
      
    
</div>

 
<div id="toolbar">
<!-- 
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#listAuditLogTable').edatagrid('addRow')">新增</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#listAuditLogTable').edatagrid('destroyRow')">删除</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#listAuditLogTable').edatagrid('saveRow')">保存</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#listAuditLogTable').edatagrid('cancelRow')">取消</a>
-->
</div>

<div class="container">

<table id="listEsbAuditLogTable" cellspacing="0" cellpadding="0" data-options="">

</table>
 </div>
</body>
</html>
