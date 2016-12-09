var CONTEXT_PATH = jQuery('meta[name="context_path"]').attr('content') || '';

var popupStatus = 0;
   var  $place = function(selector){//目标居中,目标应为绝对定位
	    var $t=( $("#addSendMessage").height()-$(selector).height())/2;
	    var $l=( $("#addSendMessage").width()-$(selector).width())/2;    
    $(selector).css({"left":$l+"px","top":$t+"px"});
  }  
$(function(){
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
	
	$('#smsList').datagrid({
		title:'',
		iconCls:'icon-save',
		width:'100%',
		height:435,
		autoRowHeight: false,
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,
		fitColumns:true,
		url:CONTEXT_PATH+'/sms/smsSendListByCampaign',
		queryParams:{
			campaignId : $('#campaignId').val()
		},
		remoteSort: false,
		fitColumns:true,
		toolbar : [ {
			id : 'btnview',
			text : '停止',
			iconCls : 'icon-cancel',
			handler : function() {
				var row = $('#smsList').datagrid('getSelected');
				if (row==null) {
					alerts("请选择要停止的批次");
				}else {
					if (row.receiveCount == row.recordcount) {
						alerts("已发送完毕无法停止");
					}else {
						if (row.sendStatus=="2") {
							smsStops2(row.batchId);
						}else {
							alerts("只有在创建成功后才能停止短信发送"); 
						}
					}					
				}				
			}
		} ],		
		columns : [ [
						{	
							field : 'batchId',
							title : '发送批次号',
							rowspan:1,
							width : 170
						},{
							field : 'createTime',
							title : '创建时间',
							rowspan:1,
							width : 80
						},{
							field : 'groupCode',
							title : '客户群编号',
							rowspan:1,
							width : 80
						},{
							field : 'groupName',
							title : '客户群名称',
							rowspan:1,
							width : 80
						},{
							field : 'smsName',
							title : '短信名称',
							rowspan:1,
							width : 80
						},{
							field : 'creator',
							title : '发送用户',
							rowspan:1,
							width : 80
						},
						{
							field : 'smsstopStatus',
							rowspan:0,
							hidden:true
						},
				         {field:'receiveCount',title:'receiveCount',hidden:true},
				         {field:'recordcount',title:'recordcount',hidden:true},
						{
							field : 'sendStatus',
							title : '任务状态',
							rowspan:1,
							width : 80,
							formatter : function(value, rowData, rowIndex) {  
		                        if (rowData.sendStatus=='0') {  
		                            return  "创建失败";  
		                        }  
		                        if (rowData.sendStatus=='1') {  
		                            return  "正在创建";  
		                        } 
		                        if (rowData.sendStatus=='2') {  
		                            return  "创建成功";
		                        } 
		                        if (rowData.sendStatus=='3') {  
		                            return  "任务已停止"; 
		                        } 
		                        if (rowData.sendStatus=='4') {  
		                            return  "任务停止失败"; 
		                        }
		                        if (rowData.sendStatus=='5') {  
		                            return  "上传ftp失败"; 
		                        } 
		                        if (rowData.sendStatus=='6') {  
		                            return  "连接大汉三通失败"; 
		                        }
		                        if (rowData.sendStatus=='7') {  
		                            return  "短信已发送结束无法停止"; 
		                        } 
		                        if (rowData.sendStatus=='8') {  
		                            return  "短信还未发送到大汉平台，无法停止"; 
		                        } 
			                 }
						}
						]],
						pagination:true,
						onDblClickRow:function(index, row){							
							doubleClick(row.batchId);							
						}
	});
	var p = $('#smsList').datagrid('getPager');
	$(p).pagination({
		pageSize : 10,
		pageList : [ 5, 10, 15 ],
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function() {
			$(this).pagination('loading');
			$(this).pagination('loaded');
		}
	});
	
	
	
});



function doubleClick(id){
	if(!id){
		id = "";
	}
	 showWindow({  
	        title:'短信监控',  
	        href:CONTEXT_PATH+'/sms/openWinReport?batchid='+id,  
	        width:600, 
	        height:450,
	        maximizable:false,
	        minimizable:false,
	        zIndex:20
	    },"smsSla");  
}

var  varGrid= $("#varGrid").combogrid('grid');
alerts(varGrid); 

function smsStops(){
	if(confirm('确定要停止吗？')){
		var batchId = $("#batchIds").val();
		if ($("#receiveCount").val()==$("#smsCount").val()) {
			alerts("已全都发送完毕，无法停止");
			return ;
		}
		$('#smsStop').attr('disabled', 'disabled');
		$.ajax({
			type:'POST',
			url:CONTEXT_PATH+'/sms/stopSms',
			data:{'batchId':batchId},
			success:function(data){
				$('#smsDetail').window('close');
				$('#smsList').datagrid('reload');
				alerts(data.result);
			}
		});
	}
}


function smsStops2(batchId){
	if(confirm('确定要停止吗？')){
		$.ajax({
			type:'POST',
			url:CONTEXT_PATH+'/sms/stopSms',
			data:{'batchId':batchId},
			success:function(data){
				$('#smsList').datagrid('reload');
				alerts(data.result);
			}
		});
	}
}
