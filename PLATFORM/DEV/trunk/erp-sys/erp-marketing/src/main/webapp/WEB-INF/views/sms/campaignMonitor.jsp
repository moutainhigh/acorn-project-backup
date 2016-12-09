<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style type="text/css">
<!--
.none{
	display: none;
}
.select{
	width: 150px;
}
.spacing_tb td{
	padding: 3px;
	font-size:12px;
}
-->
</style>

<div id="condition">
	<span>
		<label>营销计划id： </label>
		<input type="text" name="campaignId" class="input-text w150" id="campaignId" />
		<label class="w65">发送时间:</label>
		<input id="starttime" readonly="readonly" style="width:150px" name="starttime" id="starttime"  type="text" value="${startTimes}" class="easyui-datetimebox " />
		<label class="w65 cen">至:</label>
		<input id="endtime" readonly="readonly" style="width:150px"  name ="endtime" id="endtime" type="text" value="${endTimes}"  class="easyui-datetimebox"  />		
	</span>
	
	<span class="btnBar" style="text-align: left;margin-left:20px;">
	    <a  class="Btn" id="queryBtn" >检索</a> 
		<a  class="Btn" id="clearBtn" > &nbsp;&nbsp;清空&nbsp;&nbsp;  </a> 	
	</span>
</div>

<div style="margin:0 10px">
	<div id="dataList"  cellspacing="0" cellpadding="0" data-options=''></div>
</div>


   <div id="addEditWin" class="easyui-window" closed="true" modal="true"  cache="false"  collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>     
   </div> 



<script type="text/javascript" src="${ctx}/static/scripts/sms/campaignMonitor.js"></script>


