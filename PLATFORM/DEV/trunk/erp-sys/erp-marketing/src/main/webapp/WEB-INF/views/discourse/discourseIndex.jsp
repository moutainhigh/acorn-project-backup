<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/static/scripts/discourse/discourse.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/ueditor/editor_all.js">
</script>

<script type=text/javascript src="${ctx}/static/ueditor/editor_config.js"></script> 
<LINK rel=stylesheet href="${ctx}/static/ueditor/themes/default/css/ueditor.css"/>
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
<div id="condition">
		<div>
			<label class="w65">创建时间:</label>
			<input id="startTime" readonly="readonly" style="width:150px" name="startTime"  type="text"  class="easyui-datetimebox " />
			<label class="w65 cen">至:</label>
			<input id="endTime" readonly="readonly" style="width:150px"  name ="endTime"  type="text"   class="easyui-datetimebox"  />
			<label  for="discourseName" class="w65">话术名称:</label> 
			<input id="discourseName"  type="text" class="w150 input-text"name="discourseName"size="10" /> 		
			<label  for="productCode" class="w65 cen" >产品编码:</label> 
			<input id="productCode"  type="text" class="w150 input-text"name="productCode"size="10" /> 				
			<a  class="Btn" id="queryBtn" >查询</a>			
		</div>		
	</div>
	<div class="container">
   <table id="discourseList">
   </table>
   </div>
      <div id="commonMyPopWindow" class="easyui-window" closed="true" modal="true"  cache="false"  collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div> 
   
</body>

</html>
