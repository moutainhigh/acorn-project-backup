<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<script>
var campaignTypes = '${campaign.campaignTypeId}';
var types = '789'
if (campaignTypes ==5) {
	types = '456'
}
$(function(){
	var CONTEXT_PATH = "${ctx}";
	var lastTemplateId =  $('#tmpTemplateId').val();;
	 $.extend($.fn.datagrid.defaults.editors, {    
	        timespinner: {    
	            init: function(container, options){    
	                var input = $('<input type="text">').appendTo(container);    
	                return input.timespinner(options);    
	            },    
	            destroy: function(target){    
	                $(target).timespinner('destroy');    
	            },    
	            getValue: function(target){    
	                return $(target).timespinner('getValue');    
	            },    
	            setValue: function(target, value){    
	                $(target).timespinner('setValue',value);    
	            },    
	            resize: function(target, width){    
	                $(target).timespinner('resize',width);    
	            }    
	        }    
	    }); 
	$('#template').combogrid({
		panelWidth:200,
		idField:'id',
		textField:'name',
		url:CONTEXT_PATH+'/sms/smslist?',
		queryParams :{
				themeTemplate:types
		},
		panelWidth:450,
		panelHeight:320,
		pagination:true,
		rownumbers:true,
		required:true,
		editable:false,
		 mode: 'remote', 
		columns:[[
			{field:'id',title:'短信编号',width:220},
			{field:'name',title:'短信内容',width:200},
		]],
		 onSelect:function(index,row){
			 $("#templatesContent").attr("value",row.content);
			 $("#templateSize")[0].innerHTML="短信字数"+row.content.length;
			 if(row.id!=lastTemplateId){
				 lastTemplateId = row.id;
				 $('#varGrid').datagrid({
					 url:CONTEXT_PATH+'/sms/getStaticVar',
						queryParams:{
							content : row.content
						},
						onLoadSuccess:function(data){
							$('#varGrid').datagrid('acceptChanges');
							for(var i=0;i<data.rows.length;i++){
								$('#varGrid').datagrid('beginEdit', i);
							}
						}
				 });
			 }
			
		}
	});


	var lastIndex;
	$('#timeList').datagrid({
		width:400,
		height:120,
		striped : true,
		border : true,
		collapsible : false,
		singleSelect :true,
		toolbar:[{
			text:'新增',
			iconCls:'icon-add',
			handler:function(){
				$('#timeList').datagrid('endEdit', lastIndex);
					$('#timeList').datagrid('appendRow',{
						starttime:'',
						endtime:'',
						maxsend:''
					});
					var lastIndex = $('#timeList').datagrid('getRows').length-1;
					$('#timeList').datagrid('beginEdit', lastIndex);
				}
		},'-',{
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){
				var row = $('#timeList').datagrid('getSelected');
				if (row){
					var index = $('#timeList').datagrid('getRowIndex', row);
					$('#timeList').datagrid('deleteRow', index);
				}
			}
		},'-',{
			text:'接受',
			iconCls:'icon-save',
			handler:function(){
				$('#timeList').datagrid('acceptChanges');
				calculateRow();
			}
		}],
		columns : [ [
						{
							field : 'starttime',
							title : '开始时间',
							rowspan:1,
							width : 100,
							editor:'timespinner'
						},{
							field : 'endtime',
							title : '结束时间',
							rowspan:1,
							width : 100,
							editor:'timespinner'
							
						},{
							field : 'maxsend',
							title : '最大发送量',
							rowspan:1,
							width : 100,
							editor:'numberbox'
						},{
							field : 'minuteSend',
							title : '每分钟发送量',
							rowspan:1,
							width : 100
						}]],
						onClickRow:function(rowIndex){
							if (lastIndex != rowIndex){
								$('#timeList').datagrid('endEdit', lastIndex);
								$('#timeList').datagrid('beginEdit', rowIndex);
							}
							lastIndex = rowIndex;
						}
	});

	
	$('#varGrid').datagrid({
		width:250,
		height:80,
		striped : true,
		border : true,
		collapsible : false,
		singleSelect :true,
		columns : [ [
						{
							field : 'name',
							title : '变量',
							width : 100
						},{
							field : 'value',
							title : '取值',
							width : 150,
							editor:{type:'validatebox',options:{
								required:true
							}}
						}]],
						onClickRow:function(rowIndex){
								$('#varGrid').datagrid('beginEdit', rowIndex);
						}
	});
	
	
});

function initDataGrid(){
	var tmpMaxsendId = $("#tmpMaxsendId").val();
	var tmpStarttimeId = $("#tmpStarttimeId").val();
	var tmpEndtimeId = $("#tmpEndtimeId").val();
	var tmpVarNamesId = $("#tmpVarNamesId").val();
	var tmpVarValuesId = $("#tmpVarValuesId").val();
	if(tmpMaxsendId!=""){
		
		var maxsendIdArray = tmpMaxsendId.split(",");
		var starttimeIdArray = tmpStarttimeId.split(",");
		var endtimeIdArray = tmpEndtimeId.split(",");
		for(var i=0;i<maxsendIdArray.length;i++){
			if(maxsendIdArray[i]!="")
			$('#timeList').datagrid('appendRow',{
				starttime:starttimeIdArray[i],
				endtime:endtimeIdArray[i],
				maxsend:maxsendIdArray[i]
			});
		}
		
		$('#timeList').datagrid('acceptChanges');
		
		calculateRow();
	}
	
	if(tmpVarNamesId!=""){
		
		var varNamesIdArray = tmpVarNamesId.split(",");
		var varValuesIdArray = tmpVarValuesId.split(",");
		for(var i=0;i<varNamesIdArray.length;i++){
			if(varNamesIdArray[i]!=""){
				$('#varGrid').datagrid('appendRow',{
					name:varNamesIdArray[i],
					value:varValuesIdArray[i]
				});
				$('#varGrid').datagrid('beginEdit', i);
			}
		}
		
	}
	
	//$('#template').val($('#tmpTemplateId').val());
	$('#template').combogrid('setValue',$('#tmpTemplateId').val());
	$('#template').combogrid('setText',$('#tmpTemplateTitle').val());
}
/** 
 * 时间对象的格式化; 
 */  
Date.prototype.format = function(format) {  
    /* 
     * eg:format="YYYY-MM-dd hh:mm:ss"; 
     */  
    var o = {  
        "M+" :this.getMonth() + 1, // month  
        "d+" :this.getDate(), // day  
        "h+" :this.getHours(), // hour  
        "m+" :this.getMinutes(), // minute  
        "s+" :this.getSeconds(), // second  
        "q+" :Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S" :this.getMilliseconds()  
    // millisecond  
    }  
  
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "")  
                .substr(4 - RegExp.$1.length));  
    }  
  
    for ( var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]  
                    : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
}  
function sumb () {
	$('#timeList').datagrid('acceptChanges');
	var validForm = $('#ff').form('validate');
	if(!validForm){
		return false;
	}
	var rows = $("#timeList").datagrid("getRows");
	var varGridRows = $("#varGrid").datagrid("getRows");
	var varNames = "";
	var varValues = "";
	var maxsend = "";
	var timescope = "";
	var starttime = "";
	var endtime = "";
	//var ts = $('#template').combogrid('grid').datagrid('getSelected');
	var template = $('#templatesContent').val();
	var priority = $("#priority").val();
	var isreply = "Y";
	var realtime = "Y";
	var allowChannel = "Y";
	var blackListFilter = "off";
	var mainNum = "off";
	var checkBoxs = "off";
	
	
	var stime = $("#stime").datebox("getValue"); 
	var etime = $("#etime").datebox("getValue"); 
	var myDate = new Date();
	var times =  myDate.format("yyyy-MM-dd hh:mm:ss");
	if (etime<times) {
		alerts('结束时间不能晚于当前时间');
		 return false;
	}
	if (stime>etime) {
		alerts('起始时间不能晚于结束时间');
		return false;
	}
	/**
	if (stime<times) {
		alerts('起始时间不能晚于当前时间');
		return false;
	}
	if ($("#isreply").attr("checked")=="checked") {
		isreply = "Y";
	}else {
		isreply = "N";
	}
	if ($("#realtime").attr("checked")=="checked") {
		realtime = "Y";
	}else {
		realtime = "N";
	}
	if ($("#allowChannel").attr("checked")=="checked") {
		allowChannel = "Y";
	}else {
		allowChannel = "N";
	}**/
	if ($("#blackListFilter").attr("checked")=="checked") {
		blackListFilter = "on";
	}
	if ($("#mainNum").attr("checked")=="checked") {
		mainNum = "on";
	}
	
	for(var i=0;i<rows.length;i++)
	{	
		
		
		var starttimes = stime.split('-');
		var endtimes = etime.split('-');
		var starttimeTemp =  starttimes[2].substring(2);
		var endtimesTemp =   endtimes[2].substring(2);
		var istart=parseInt(starttimeTemp.split(":")[0]) * 100 + parseInt(starttimeTemp.split(":")[1]);
		var mstart=parseInt(starttime.split(":")[0]) * 100 + parseInt(starttime.split(":")[1]);
		var iendt=parseInt(endtimesTemp.split(":")[0]) * 100 + parseInt(endtimesTemp.split(":")[1]);
		var mendt=parseInt(endtime.split(":")[0]) * 100 + parseInt(endtime.split(":")[1]);
		/**
		 if(mstart<istart)
	     {
			 alerts("区段开始时间不能早于开始时间");
			 return false;
	     }
		 if(iendt<mstart)
	     {
			 alerts("区段开始时间不能晚于结束时间");
			 return false;
	     }
		 if(mendt<mstart)
	     {
			 alerts("区段结束时间不能晚于区段开始时间");
			 return false;
	     }
		 if(mendt>iendt)
	     {
			 alerts("区段结束时间不能晚于结束时间");
			 return false;
	     }**/
		 if(mendt<istart)
	     {
			 alerts("区段结束时间不能早于开始时间");
			 return false;
	     }
	     
		 
		maxsend = maxsend+rows[i].maxsend+",";
		if (maxsend==null||maxsend == '') {
			alerts("发送量不能为空");
			return false;
		}
		if (maxsend == '0') {
			alerts("发送量不能为0");
			return false;
		}		
		starttime = starttime+rows[i].starttime+",";
		endtime = endtime+rows[i].endtime+",";
	}
	
	
	
	for(var i=0;i<varGridRows.length;i++)
	{
		varNames = varNames+varGridRows[i].name+",";
		var ed = $('#varGrid').datagrid('getEditor', {index:i,field:'value'});
		varValues = varValues+$(ed.target).val()+",";
	}
	$.post(CONTEXT_PATH+"/campaign/saveSmsSet", {
		"maxsend" : maxsend,
		"starttime":starttime,
		"endtime":endtime,
		"template":template,
		"priority":priority,			
		"stime":stime,
		"etime":etime,
		"isreply":isreply,
		"realtime":realtime,
		"allowChannel":allowChannel,
		"blackListFilter":blackListFilter,
		"mainNum":mainNum,
		"varNames":varNames,
		"varValues":varValues,
		"campaignId":$("#smsCampaignId").val(),
		"returnUrl":$("#returnUrl").val(),
		"templateTitle":$('#template').combogrid('getText'),
		"templateId":$('#template').combogrid('getValue')
	},function(data) {
		editNextPage(data.campaignId,data.next,data.returnUrl);
	});	
}

function changeTemplate(){
	var template = $('#template').combogrid('grid').datagrid('getSelected');
	if (template==null||template==""){
		$("#templatesContent").attr("value","");
		$("#templateSize")[0].innerHTML="";
	}else {
		$.post("templates", {
			"template":template
		},function(data) {
			$("#templatesContent").attr("value",data.content);
			$("#templateSize")[0].innerHTML="短信字数"+data.size;
		});	
	}

}

function smsStops(){
	if(confirm('确定要停止吗？')){
		var batchId = $("#batchIds").val();
		if ($("#receiveCount").val()==$("#smsCount").val()) {
			alerts("已全都发送完毕，无法停止");
			return false;
		}
		$.ajax({
			type:'POST',
			url:'stopSms',
			data:{'batchId':batchId},
			success:function(data){
				alerts(data.result);
				$('#smsStop').attr('disabled', 'disabled');
				$('#smsDetail').window('close');
				$('#smsList').datagrid('reload');
			}
		});
	}
}

initDataGrid();

function calculateMinu(starttime,endtime,maxSend){
	if(starttime==""||endtime==""||maxSend==""){
		return "";
	}
	var mstart=parseInt(starttime.split(":")[0],10) * 60 + parseInt(starttime.split(":")[1],10);
	var mendt=parseInt(endtime.split(":")[0],10) * 60 + parseInt(endtime.split(":")[1],10);
	var minuteValue = mendt-mstart;
	if(minuteValue>0){
		return parseInt(maxSend,10)/minuteValue;
	}
	return "";
}

function calculateRow(){
	var rows = $("#timeList").datagrid("getRows");
	var mSendInt = "";
	if(rows.length>0){
		for(var i=0;i<rows.length;i++){
			mSendInt = calculateMinu(rows[i].starttime,rows[i].endtime,rows[i].maxsend);
			$('#timeList').datagrid('updateRow',{
				index: i,
				row: {
					minuteSend: Math.round(mSendInt)
				}
			});
		}
	}
}

function getCounts () {
	$("#messages").show();
	$("#counts")[0].innerHTML="";
	var mainNumVar = $("#mainNum").is(":checked")?"on":"off";
	var blackListFilterVar = $("#blackListFilter").is(":checked")?"on":"off";
	$.ajax({
		type:'POST',
		url:'${ctx}/sms/countsForCampaign',
		data:{'campaignId':$("#smsCampaignId").val(),
			'mainNum':mainNumVar,
			'blackListFilter':blackListFilterVar},
		success:function(data){
			$("#messages").hide();
			$("#counts").show();	
			$("#counts")[0].innerHTML="短信条数"+data.PHONES;
		}
	});
	

}

</script>
	   	 <form id="ff" method="post" >
	   	 <input type="hidden" id="smsCampaignId" name="campaignId" value="${campaignId }">
	   	 <input type="hidden" id="returnUrl" name="returnUrl" value="${returnUrl }">
	   	 <input type="hidden" id="tmpMaxsendId" value="${smsSet['maxsend']}">
	   	 <input type="hidden" id="tmpStarttimeId" value="${smsSet['starttime']}">
	   	 <input type="hidden" id="tmpEndtimeId" value="${smsSet['endtime']}">
	   	 <input type="hidden" id="tmpVarNamesId" value="${smsSet['varNames']}">
	   	 <input type="hidden" id="tmpVarValuesId" value="${smsSet['varValues']}">
	   	 <input type="hidden" id="tmpTemplateId" value="${smsSet['templateId']}">
	   	 <input type="hidden" id="tmpTemplateTitle" value="${smsSet['templateTitle']}">
	   	 <ul class="tr" >
		   	 <li>
		   	 <ul  class="td">
		   	 <li class="w65"><label for="templates">短信模板:</label></li>
		   	 <li> 	
		   	 <select id="template" name="template" style="width:250px;"></select><span id="templateSize" ></span>
		   	 <input type="button" class="" onclick="getCounts()" value="查询数量"/><span class="mt5 ml10" style="color:red;"><span id="counts" style="display:none"></span><img id="messages" src="../static/images/loading.gif" style="display:none;position:absolute;left:50%;top:50%"/></span>
			</li>
			<li>
			<ul  class="td">
	            <li class="w65"></li>
	            <li>
	            <textarea rows="2" cols="50" class="sm-tem" disabled="disabled" id="templatesContent" >${smsSet['template'] }</textarea>
	            </li>
	            <li>
	            <table id="varGrid">
				</table>
	            </li>
	            </ul>
			
			</li>
		   	 </ul> </li>
		   	  <li> <ul  class="td">
	            <li class="w65"><label for="email">优先级:</label>
	            </li>
	            <li>
	           <select  id = "priority" name="priority" required="true" class="select deptid ml10" >
						<option value="1" <c:if test="${smsSet['priority']=='1' }"> selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${smsSet['priority']=='2' }"> selected="selected"</c:if>>2</option>
						<option value="3" <c:if test="${smsSet['priority']=='3' }"> selected="selected"</c:if>>3</option>
						<option value="4" <c:if test="${smsSet['priority']=='4' }"> selected="selected"</c:if>>4</option>
						<option value="5"  <c:if test="${smsSet['priority']=='5' }"> selected="selected"</c:if><c:if test="${smsSet['priority']==null }"> selected="selected"</c:if>>5</option>
						<option value="6" <c:if test="${smsSet['priority']=='6' }"> selected="selected"</c:if>>6</option>
						<option value="7" <c:if test="${smsSet['priority']=='7' }"> selected="selected"</c:if>>7</option>
				</select>
	            </li>
	             <li>
	             <!--   
				<p><label ><input  id="isreply"  type="checkbox" name="isreply" <c:if test="${smsSet['isreply']==null || smsSet['isreply']=='Y'}">  checked="checked"</c:if>/>接收回复内容</label>
		        
		      </p>
	            <p><label ><input  id="realtime"  type="checkbox" name="realtime"  <c:if test="${smsSet['realtime']==null || smsSet['realtime']=='Y'}">  checked="checked"</c:if>/>及时反馈客户回执</label>
		        </p>
	            <p><label > <input  id="allowChannel"  type="checkbox" name="allowChannel"  <c:if test="${smsSet['allowChannel']==null || smsSet['allowChannel']=='Y'}">  checked="checked"</c:if>/>是否允许转通道</label>
		       </p>
	            </li>
	                -->
	            <li>
	            <p><label><input  id="blackListFilter"  type="checkbox" name="blackListFilter" <c:if test="${smsSet['blackListFilter']==null || smsSet['blackListFilter']=='on'}">  checked="checked"</c:if>/>排除短信黑名单</label></p>
	            <p><label  class="v-top" > <input  id="mainNum"  type="checkbox" name="mainNum" <c:if test="${smsSet['mainNum']=='on'}">  checked="checked"</c:if> />多个号码时，只发一个号码，默认主号码</label></p>
	            </li>	            
		   	  </ul> 
		   	  </li>
		   	   <li><ul class="td">
		   	   			<li class="w100"> <label for="message">发送时间:</label><br>
		   	   			<span style="font-size:10px;color:green">（该时间为下发平台时间，非真正短信发送时间。如遇平台堵塞，真正发送时间会超过定义中的最晚时间）</span>
		   	   			</li>
		   	   			<li><input id="stime" readonly="readonly" value="<c:if test="${smsSet['stime']!=null}">${smsSet['stime']}</c:if><c:if test="${smsSet['stime']==null}">${startDate}</c:if>" type="text" style="width: 150px" required="true" class="easyui-datetimebox" name="stime" />~<input id="etime"  value="<c:if test="${smsSet['etime']!=null}">${smsSet['etime']}</c:if><c:if test="${smsSet['etime']==null}">${endDate}</c:if>" required="true" readonly="readonly" type="text" style="width: 150px" class="easyui-datetimebox" name="etime"/><br><div style="margin-top:10px"><table  id="timeList">
		   	   </table></div>
		   	   
		   	   
		   	   
		   	   </li>
		   	   		<li>
		   	   	   		<ul class="td">
		   	   	   			<li>
		   	   	   			<a href="javascript:editPrevPage('${ctx}/campaign/openWinEditCampaign?id=${campaignId }')" class="easyui-linkbutton" data-options="iconCls:'icon-prev'" onclick="">上一步</a>
		   	   	   			</li>
		   	   	   			<li>
		   	   	   			 <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-next'" onclick="sumb()">确定,下一步</a>
		   	   	   			</li>
		   	   	   			<li>
		   	   	   			<a href="javascript:closeWindow('commonMyPopWindow')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
		   	   	   			</li>
		   	   	   		</ul>
		   	   	   		</li>
		   	   	   </ul>
		   	   </li>		   	
	   	 </ul>
	    </form>
