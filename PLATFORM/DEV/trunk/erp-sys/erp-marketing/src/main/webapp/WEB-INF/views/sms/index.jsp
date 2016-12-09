<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/sms/smsAnswer.js"></script>
</head>
<body>
<div id="condition">
		<div>
			<label class="w65">发送时间:</label>
			<input id="startTime" readonly="readonly"  name="startTime" type="text" value="${startTimes}" class="easyui-datetimebox" style="width:150px"/>
			<label class="w65 cen">至:</label>
			<input id="endTime" readonly="readonly"  type="text"  name="endTime" value="${endTimes}"  class="easyui-datetimebox" size="50" />
			<label  for="receiveChannel" class="w65">发送通道:</label> 
			<input id="receiveChannel"  class="w150 input-text" type="text"  name="receiveChannel"size="10" /> 
		</div>
		<div>
			<label  for="mobile" class="w65">手机号码:</label> 
			<input id="mobile"  type="text" class="w150 input-text"  name="mobile"size="10" /> 
			<label  for="smsChildId" class="w65 cen">短信子码:</label> 
			<input id="smsChildId"  type="text" class="w150 input-text"  name="smsChildId"size="10" /> 	
			<a  class="Btn" id="querySmsAnswerBtn">查询</a>		
		</div>
		
	</div>
	<div style="margin:0 10px" >
   <table id="smsList" data-options=''>
   </table>    
   </div>
</body>
</html>
