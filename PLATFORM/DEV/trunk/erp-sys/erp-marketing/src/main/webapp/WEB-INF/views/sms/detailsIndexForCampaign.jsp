<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>

<style>
#showdiv{
 display:none;  
 position:absolute;
 
 z-index:9999;  
 padding:12px;
} 
#screen{
 display:none;  
 position:absolute;    
 height:100%;  
 width:100%;  
 top:0;  
 left:0;  
 background-color:#000000;  
 z-index:8888;  
}
#move{
 cursor:move;
}

</style>
</head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/static/scripts/sms/smsDetailsForCampaign.js"></script>
<div id="condition">
<input type="hidden" id="d_campaignId" value="${campaignId }">
		<div>
			<label class="w80">短信回执时间:</label>
			<input id="d_startfeedBackStatusTime" readonly="readonly" style="width:150px" name="d_startfeedBackStatusTime"  type="text"  class="easyui-datetimebox " />
			<label class="cen">至:</label>
			<input id="d_endfeedBackStatusTime" readonly="readonly" style="width:150px"  name ="endfeedBackStatusTime"  type="text"   class="easyui-datetimebox"  />
			<label  for="batchId" class="w65">发送批次号:</label> 
			<input id="d_batchId"  type="text" class="w150 input-text"name="batchId"size="10" />
			<label  for="receiveStatus" class="w65">发送状态:</label> 
			<select id="d_receiveStatus" name="receiveStatus" style="width:150px;" >
						<option value="">请选择</option>
						<option value="1">发送成功</option>
						<option value="0">发送失败</option>
			</select> 
		</div>
		<div>
			<label class="w80">发送状态时间:</label>
			<input id="d_startreceiveStatusTime" readonly="readonly" style="width:150px" name="startreceiveStatusTime"  type="text"  class="easyui-datetimebox " />
			<label class="cen">至:</label>
			<input id="d_endreceiveStatusTime" readonly="readonly" style="width:150px"  name ="endreceiveStatusTime"  type="text"   class="easyui-datetimebox"  />
			<label  for="mobile" class="w65" >电话号码:</label> 
			<input id="d_mobile"  type="text"  class="w150 input-text" name="mobile"size="10" />
			<label  for="feedbackStatus" class="w65">回执:</label> 
			<select name="feedbackStatus"  id="d_feedbackStatus" style="width:150px;" >
				<option value="">请选择</option>
				<option value="1">发送成功</option>
				<option value="0">发送失败</option>
			</select>  			
			<a  class="Btn" id="d_querySmsAnswerBtn" >查询</a>			
		</div>
	
	</div>
	<div class="container">
   <table id="smsDetailDataGrid">
   </table>  
</div>   
<script type="text/javascript">

</script>

</body>

</html>
