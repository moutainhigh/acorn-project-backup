<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body>
     <form id="ff" method="post" >
	   	 <ul class="tr" >
		   	 <li>
		   	 <ul  class="td">
		   	      <li class="w100"><lable>客户群编号</lable></li>
		   	      <li >	
		   	            
		   	      	<span class="inputSpan"><input type="text" disabled="disabled"  value="${smsSend.groupCode}"/>
		   	      </span>
		   	     
		   	        
					 
				  </li>	
				  <li class="w100"> <lable>客户群名称</lable>  </li>	     
				  <li><span class="inputSpan">
		   	      	<input type="text"  value="${smsSend.groupName}" disabled="disabled" />
				  </span> 
				    
					 
		   	 </ul>
			   	 <ul class="td">
			   	 	<li class="w100"></li>
			   	 	<li ><label  class="v-top" ><input  id="blackListFilters"   disabled="disabled" type="checkbox" name="blackListFilter" />排除短信黑名单</label>
				 		 <label  class="v-top" > <input  id="mainNums"  disabled="disabled" type="checkbox" name="mainNum"  />多个号码时，只发一个号码，默认主号码</label></li>
				 </ul>
		   	 </li>
		   	 <li>
		   	 <ul  class="td">
		   	 <li class="w100"><label for="templates">短信模板:</label></li>
		   	 <li> 	
		   	 <select id="template" name="template" style="width:250px;" value="${smsTemplate.name}">
		   	 	<option value="${smsTemplate.name }">${smsTemplate.name }</option>
		   	 </select>	  		   	
				<br>
				<textarea rows="2" cols="60" class="sm-tem" disabled="disabled" id="templatesContent" >${smsTemplate.content}</textarea>
				<div id="templateSize" ></div>
				</li>
		   	 </ul> </li>
		   	  <li> <ul  class="td">
	            <li class="w100"><label for="email">优先级:</label><select  id = "prioritys"name="priority" required="true" class="select deptid ml10" >
						<option value="${smsSend.priority }">${smsSend.priority }</option>
				</select>
	            </li>
	            <!--  
	             <li>
				<p><label ><input  id="isreplys"   disabled="disabled" type="checkbox" name="isreply" />接收回复内容</label>	<label ><input  id="realtimes"  disabled="disabled" type="checkbox" name="realtime"  />及时反馈客户回执</label><label > <input  id="allowChannels" disabled="disabled"  type="checkbox" name="allowChannel" />是否允许转通道</label>	        
		      </p>
	          	
	            </li>	
	             -->            
	           <li>
	           <ul class="td">
			   	 	<li class="w100"></li>
			   	 	<li>
			   	 	<table id="varGrid" class="datagrid-body sendList" cellpadding="0"  cellspacing="0">
				<thead>
				<tr class="datagrid-header"> 
					<td>
						<lable>变量名称</lable>
						
						</td>
						<td>
						<lable>变量内容</lable>
						
					</td>
				</tr>
				<c:forEach items="${varList}" var="var">
				
				<tr> 
					<td>
						${var.var_name }
						</td>
						<td>
						${var.var_value }
					</td>
				</tr>
				</c:forEach>
				</thead>
				</table>
				
				 		 </li>
				 </ul>	            
	            	
	            </li>
		   	  </ul> 
		   	  </li>
		   	   <li><ul class="td">
		   	   			<li class="w100"> <label for="message">发送时间:</label><br>
		   	   			<span style="font-size:10px;color:green"></span>
		   	   			</li>
		   	   			<li><input id="stime" readonly="readonly" type="text"  value="${startTime}" disabled="disabled"  required="true" class="easyui-datetimebox" name="stime" />~<input id="etime"  required="true" readonly="readonly" type="text"  disabled="disabled"  value = "${endTime}"class="easyui-datetimebox" name="etime"/><br><div style="margin-top:10px">
		   	   			<table  id="timeList" class="datagrid-body sendList" cellpadding="0"  cellspacing="0">
		   	   			<tr class="datagrid-header"> 
							<td>
								<lable>发送时间段</lable>
								
								</td>
								<td>
								<lable>发送数量</lable>
								
							</td>
						</tr>
		   	   			<c:forEach items="${timeScopeList}" var="timescope">
		   	   			
							<tr> 							
								<td>
								${timescope.time }
								</td>
								<td>
								${timescope.iops }
								</td>
							</tr>
							</c:forEach>
		   	   			</table>
		   	   			</div>
		   	   			</li>		   	   	   		
		   	   	   </ul>
		   	   </li>		   			   	 
	   	 </ul>
	    </form>
<script>
<!-- 
if ('${smsSend.isreply}'=='Y') {
	$("#isreplys").attr("checked","checked");
}
if ('${smsSend.realtime}'=='Y') {
	$("#realtimes").attr("checked","checked");
}
if ('${smsSend.allowChannel}'=='Y') {
	$("#allowChannels").attr("checked","checked");
}
-->

if ('${smsSend.mainNum}'=='on') {
	$("#mainNums").attr("checked","checked");
}
if ('${smsSend.blackListFilter}'=='on') {
	$("#blackListFilters").attr("checked","checked")
}
</script>
</body>
</html>