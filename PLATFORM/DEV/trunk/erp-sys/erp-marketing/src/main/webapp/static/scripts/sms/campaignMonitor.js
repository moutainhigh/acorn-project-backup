$(function(){
	
	$('#dataList').datagrid({
		//title:'SMS模板列表',
		title : '',
		iconCls:'icon-save',
		width:'100%',
		height:470,
		nowrap:false,
		striped: true,
		autoRowHeight: false,
		scrollbarSize:0,
		url:'campaignMonitorList',
		idField:'id',
		fitColumns:true,		
		columns:[[
		         {field:'id',title:'id',hidden:true,width:10},
		         {field:'campaignId', title:'营销计划id', width:100},
		         {field:'minuCount', title:'条数/分钟', width:140},
		         {field:'status', title:'状态', width:140,		        	 
						formatter : function(value, rowData, rowIndex) { 
				              if (rowData.status=='0') {  
		                            return  "初始速率";  
		                        }  
		                        if (rowData.status=='1') {  
		                            return  "更改速率成功";  
		                        } 
		                        if (rowData.status=='2') {  
		                            return  "更改速率失败";
		                        } 
		                        if (rowData.status=='3') {  
		                            return  "恢复初始速率成功";
		                        } 
		                        if (rowData.status=='4') {  
		                            return  "恢复初始速率失败";
		                        } 		                 
		                 }
		         },
		         {field:'smsContent', title:'短信内容', width:100, align:'center'},
		         {field:'smsCount', title:'短信总量', width:100, align:'center'},
		         {field:'receiveCount', title:'短信已发送量', width:100, align:'center'},
		         {field:'createTime', title:'创建时间', width:150, align:'center',
		        	 formatter:function(value){
		        		 var _date = new Date(value);
		        		 return _date.format('yyyy-MM-dd hh:mm:ss');
		        	 }
		         },
		         {field:'createUser', title:'创建工号', width:100, align:'center'},
		         {field:'updateTime', title:'更新时间', width:150, align:'center',
		        	 formatter:function(value){
		        		 if (value!=null) {
			        		 var _date = new Date(value);
			        		 return _date.format('yyyy-MM-dd hh:mm:ss'); 
		        		 }
		        	 }
		         },
		         {field:'updateUser', title:'更新工号', width:100, align:'center'}
		]],
		remoteSort : false,
		/*singleSelect : true,*/
		pagination : true,
		rownumbers : false,
		queryParams:{
			startTimes : $('#starttime').val(),
			endTimes : $("#endtime").val()
		},
		onDblClickRow:function(index, row){	
			var _date = new Date(row.createTime);
			if (row.smsCount!=null&&row.smsCount!="") {
				doubleClick(row.campaignId,row.minuCount,row.createUser,_date.format('yyyy-MM-dd hh:mm:ss'),row.id,row.smsContent,row.status,row.smsCount,row.receiveCount);					
			}else{
				alerts("短信批次正在创建中，请稍后再更改速率");
				$('#dataList').datagrid('load', {
					campaignId: $('#campaignId').val(),
					startTimes :$('#starttime').datetimebox('getValue'),
					endTimes : $("#endtime").datetimebox('getValue'),
					createUser :$('#createUser').val()
				});
			}
		}
	});
    
	var p = $('#dataList').datagrid('getPager');
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
	
	//查询
	$('#queryBtn').click(function(){
		$('#dataList').datagrid('load', {
			campaignId: $('#campaignId').val(),
			startTimes :$('#starttime').datetimebox('getValue'),
			endTimes : $("#endtime").datetimebox('getValue'),
			createUser :$('#createUser').val()
		});
	});
	
	//清空
	$('#clearBtn').click(function(){
		$('#campaignId').val('');
	});
});




function doubleClick(id,minuCount,createUser,createTime,ids,smsContent,status,smsCount,receiveCount){
	if(!id){
		id = "";
	}
	 showWindow({  
	        title:'短新速率设置',  
	        href:'openWincampaignChangeInit?campaignId='+id+'&minuCount='+minuCount+'&createUser='+createUser+'&createTime='+createTime+'&id='+ids+'&status='+status+'&smsCount='+smsCount+'&receiveCount='+receiveCount,  
	        width:600, 
	        height:450,
	        maximizable:false,
	        minimizable:false,
	        zIndex:20
	    },"addEditWin");  
}
function closeSaveWin(){
	$('#addEditWin').window('close');
}



/**
 * 时间对象的格式化;
 */
Date.prototype.format = function(format) {
	/*
	 * eg:format="yyyy-MM-dd hh:mm:ss";
	 */
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
		// millisecond
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
						- RegExp.$1.length));
	}

	for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
							? o[k]
							: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};