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
<script type="text/javascript" src="${ctx}/static/scripts/sms/smsSendListByCampaign.js"></script>
<input type="hidden" id="campaignId" value="${campaignId }">
	<div style="margin:0 10px;text-align: center;">
   <table id="smsList" data-options=''>
   </table>  <br/>
   <a class="easyui-linkbutton"
				data-options="iconCls:'icon-cancel'"
				href="javascript:viewDetailList(${campaignId })">查看短信详情</a>
   <a class="easyui-linkbutton"
				data-options="iconCls:'icon-cancel'"
				href="javascript:closeWindow('commonMyPopWindow')">关闭</a>
   </div>
   <div id="smsDetailList" class="easyui-window" closed="true" modal="true"  cache="false"  collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>     
   <div id="smsDetail" class="easyui-window" closed="true" modal="true"  cache="false"  collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>     
   
   <div id="smsSla" class="easyui-window" closed="true" modal="true"  cache="false"  collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>     
</body>

</html>
