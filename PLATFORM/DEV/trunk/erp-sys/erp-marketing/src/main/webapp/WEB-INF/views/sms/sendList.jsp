<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/static/scripts/sms/smsSendMessage.js"></script>
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
			<label class="w65">发送时间:</label>
			<input id="starttime" readonly="readonly" style="width:150px" name=starttime  type="text" value="${startTimes}" class="easyui-datetimebox " />
			<label class="w65 cen">至:</label>
			<input id="endtime" readonly="readonly" style="width:150px"  name ="endtime"  type="text" value="${endTimes}"  class="easyui-datetimebox"  />
			<label  for="batchuId" class="w65">发送批次号:</label> 
			<input id="batchId"  type="text" class="w150 input-text"name="batchId"size="10" /> 
		</div>
		<div>
			<label  for="userGroup" class="w65">客户群编号:</label> 
			<input id="userGroup"  type="text"  class="w150 input-text" name="userGroup"size="10" /> 
			<label  for="smsName" class="w65 cen" >短信名称:</label> 
			<input id="smsName"  type="text"  class="w150 input-text" name="smsName"size="10" /> 
			<label  for="creator" class="w65">发送用户:</label> 
			<input id="creator"  type="text"  class="w150 input-text" name="creator"size="10" /> 
			<a  class="Btn" id="querySmsAnswerBtn" >查询</a>			
		</div>
	
	</div>
	<div style="margin:0 10px">
   <table id="smsList" data-options=''>
   </table>  
   <div id="smsDetail" class="easyui-window" closed="true" modal="true"  cache="false"  collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>     
   </div> 
   <div id="smsSla" class="easyui-window" closed="true" modal="true"  cache="false"  collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>     
   </div>   
</div>   
 <div id="addSendMessage" style="padding:5px;display:none;">
   <div id="screen"><!--该DIV用于锁屏--></div>
	<div id="showdiv"> 
	 <div id="move"><img id="message" src="../static/images/loading.gif" style="position:absolute;left:50%;top:50%"/></div><br />      
	</div>
	   	 <form id="ff" method="post" >
	   	 <ul class="tr" >
		   	 <li>
		   	 <ul  class="td">
		   	      <li class="w100"><label class="v-top"  for="name">客户群:</label></li>
		   	      <li >	   	      
		   	      		<span class="inputSpan">
						<input class="easyui-combogrid" 
						id="customers"
						name="customers"
						width="200"
						data-options="
								url:'${ctx }/sms/grouplist',
								idField:'groupCode',
								textField:'groupName',
								panelWidth:450,
								panelHeight:320,
								pagination:true,
								rownumbers:true,
								required:true,
								editable:false,
								 mode: 'remote', 
								 toolbar:'#tt4',
								columns:[[
									{field:'groupCode',title:'客户群编号',width:220},
									{field:'groupName',title:'客户群名称',width:200}
								]]
						">
					</span>
					 <label  class="v-top" ><input  id="blackListFilter"  type="checkbox" name="blackListFilter" checked="checked"/>排除短信黑名单</label>
			 		 <label  class="v-top" > <input  id="mainNum"  type="checkbox" name="mainNum"  />多个号码时，只发一个号码，默认主号码</label>
					 <br>
					 <input type="button" class="" onclick="getCounts()" value="查询数量"/><span class="mt5 ml10" style="color:red;"><span id="counts" style="display:none"></span><img id="messages" src="../static/images/loading.gif" style="display:none;position:absolute;left:50%;top:50%"/></span>					 		   	 					 
				  </li>		   	     
		   	 </ul>
		   	 </li>
		   	 <li>
		   	 <ul  class="td">
		   	 <li class="w100"><label for="templates">短信模板:</label></li>
		   	 <li> 	
		   	 <select id="template" name="template" style="width:250px;"></select>	  		   	
				<br>
				<textarea rows="2" cols="60" class="sm-tem" disabled="disabled" id="templatesContent" ></textarea>
				<div id="templateSize" ></div>
				</li>
		   	 </ul> </li>
		   	  <li> <ul  class="td">
	            <li class="pdl30"><label for="email">优先级:</label><select  id = "priority"name="priority" required="true" class="select deptid ml10" >
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5" selected="selected">5</option>
						<option value="6">6</option>
						<!--   
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						-->
				</select>
	            </li>
	            <!--  
	             <li>
				<p><label ><input  id="isreply"  type="checkbox" name="isreply" checked="checked"/>接收回复内容</label>
		        
		      </p>
	            <p><label ><input  id="realtime"  type="checkbox" name="realtime"  checked="checked"/>及时反馈客户回执</label>
		        </p>
	            <p><label > <input  id="allowChannel"  type="checkbox" name="allowChannel" checked="checked"/>是否允许转通道</label>
		       </p>
	            </li>
	             -->	            
	           <li>	            
	            <table id="varGrid" class="easyui-datagrid" style="width:300px;height:90px"
					data-options="singleSelect:true" title="">
				<thead>
					<tr>
						<th data-options="field:'name',width:100">变量</th>
						<th data-options="field:'value',width:150,editor:{type:'validatebox',options:{
								required:true
							}}">取值</th>
					</tr>
				</thead>
				</table>	
	            </li>
		   	  </ul> 
		   	  </li>
		   	   <li><ul class="td">
		   	   			<li class="w100"> <label for="message">发送时间:</label><br>
		   	   			<span style="font-size:10px;color:green">（该时间为下发平台时间，非真正短信发送时间。如遇平台堵塞，真正发送时间会超过定义中的最晚时间）</span>
		   	   			</li>
		   	   			<li><input id="stime" readonly="readonly" type="text"  required="true" class="easyui-datetimebox" name="stime" />~<input id="etime"  required="true" readonly="readonly" type="text"  class="easyui-datetimebox" name="etime"/><br><div style="margin-top:10px"><table  id="timeList">
		   	   </table></div></li>
		   	   	   		
		   	   	   </ul>
		   	   </li>		   	
		   	  <li class="mt10" style="text-align: center;"> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="sumb()">确定执行</a>
		   	 </li>
	   	 </ul>
	    </form>
   </div>
</body>

</html>
