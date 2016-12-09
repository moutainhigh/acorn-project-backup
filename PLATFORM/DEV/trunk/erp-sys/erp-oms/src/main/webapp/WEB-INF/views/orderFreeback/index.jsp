<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript"
	src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
<script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.parser.js">
</script>
<script type="text/javascript" src="/static/scripts/pretread/freebask.js"></script>
	 <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="condition">
	<div>
	
			<label><input for="state" type="radio" name="state" value="0" checked="checked"/>导入日期</label>
			
		
			<label><input for="state" type="radio" name="state" value="1" />交寄日期</label>
		
		&nbsp;&nbsp;
		<label for="beginDate">从:</label>
		<input id="beginDate" class="easyui-datetimebox" style="width:180" /> <label for="endDate">至:</label>
		<input id="endDate" type="text" class="easyui-datetimebox" style="width:180" />
		&nbsp;&nbsp;
        <input id="cbRemark" type="checkbox" name="remark"/>
        <label for="cbRemark" >未结算</label>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" name="findData" class="Btn" value='检索' id="findDate" onclick="queryData()" />
		</div>
         <div>
		<label class="labelTitle" for="settleType" style="width:60px;">结账类型:</label>
		
		<select id="settleType"  class="easyui-combobox" name="settleType" value="3" >
		<option value="0">==请选择==</option>
		<option value="1">EMS结账</option>
		<option value="2">特能结账</option>
		<option value="3">送货结账</option>
		<option value="4">四川EMS结账</option>
		<option value="5">信用卡结账</option>
		<option value="6">江西EMS结账</option>
		</select>
			 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  <input type="button" name="importData" class="Btn" value='导入数据' id="importData" />
		</div>
		<div>
          <label class="labelTitle" for="companyid" style="width:60px;">送货公司:</label> 
			<select name="companyid" id="companyid" class="easyui-combobox" >
				<option value="">==请选择==</option>  
		    </select>
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    	 <input type="button" name="settle" class="Btn" value='结账' id="settle" onclick="settle()" />

    	 <label>${msg}</label>
		</div>
	</div>
	<div id="toolbar">
		
		
	</div>
	<div class="container" style=" height: 380px">
		<table id="dataTable" name="dataTable" cellspacing="0" cellpadding="0" data-options="">
		</table>
	</div>
	
<!--  	<div style="padding: 3px; height: 350px">
		<table id="dataTable2" name="dataTable2" cellspacing="0" cellpadding="0">
		</table>
	</div>-->

	 <div id="dd" title="上传数据" class="easyui-dialog" modal="true"  style="width:600px;height:200px;" closed="true">
	 <br/><br/>
	 
	  <form method="post" name="fm" id="fm" enctype="multipart/form-data" buttons="#dlg-line-form-buttons"  novalidate >
	  <ul style='float:center'>
	 	 <input id="v_settlType" name ="v_settlType" type="hidden" value="${settleType}" size="50" />
	    <input id="v_companyid" name ="v_companyid" type="hidden" size="50" />
	  <label name="uploadfile" required="true" >导入数据</label>
	   <input type="file" id="uploadfile" name="uploadfile" iconCls="icon-export" size="58" class="easyui-validatebox"   
    required="true">
	   <button id="upload" >&nbsp;上传&nbsp;</button>
	   </ul>
	   	
	    </form>
	 
	  </div>  
	  	  <div id="w" modal="true" class="easyui-dialog" title="系统提示" style="width:400px;height:500px;padding:10px"
			data-options="
				iconCls: 'icon-save',
				closable:false
			" closed="true">
		       <div id="w_content" name="w_content"> </div>
		       </br>
		       </br>
		       <a href="#" class="easyui-linkbutton" id="w_ok" name="w_ok" onclick="javascript:$('#w').dialog('close')" data-options="iconCls:'icon-ok'">Ok</a>
		       
	</div>
</body>
</html>
