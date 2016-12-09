$(function(){
	$('#smsList').datagrid({
		title:'',
		iconCls:'',
		width:'100%',
		height:435,
		nowrap: false,
		striped: true,
		collapsible:true,
		fitColumns:true,
		url:'detailesList',
		queryParams:{
			batchId: $("#batchId").val(),
			mobile:$("#mobile").val(),
			receiveStatus:$("#receiveStatus").val(),
			feedbackStatus:$("#feedbackStatus").val(),
			startfeedBackStatusTime:$("#startfeedBackStatusTime").datetimebox("getValue"),
			endfeedBackStatusTime:$("#endfeedBackStatusTime").datetimebox("getValue"),
			startreceiveStatusTime:$("#startreceiveStatusTime").datetimebox("getValue"),
			endreceiveStatusTime:$("#endreceiveStatusTime").datetimebox("getValue"),
			campaignId:$("#d_campaignId").val()
		},
		remoteSort: false,
		fitColumns:true,	
		columns : [ [
						{
							field : 'batchId',
							title : '批次编号',
							rowspan:2,
							width : 100
						},
						{
							field : 'mobile',
							title : '用户电话',
							rowspan:2,
							width : 60
						},{
							field : 'content',
							title : '短信内容',
							rowspan:2,
							width : 120
						},{
							field : 'receiveStatus',
							title : '短信发送状态',
							rowspan:2,
							width : 40,
							formatter : function(value, rowData, rowIndex) {  
		                        if (rowData.receiveStatus=='0') {  
		                            return  "发送失败";  
		                        }  
		                        if (rowData.receiveStatus=='10') {  
		                            return  "无结果";  
		                        } 
		                        if (rowData.receiveStatus=='1') {  
		                            return  "发送成功";
		                        } 
			                 }
						},
						{
							field : 'receiveStatusTime',
							title : '短信发送状态时间',
							rowspan:2,
							width : 80
						},
						{
							field : 'feedbackStatus',
							title : '短信回执状态',
							rowspan:2,
							width : 40,
							formatter : function(value, rowData, rowIndex) {  
		                        if (rowData.feedbackStatus=='0') {  
		                            return  "发送失败";  
		                        }  
		                        if (rowData.feedbackStatus=='10') {  
		                            return  "无结果";  
		                        } 
		                        if (rowData.feedbackStatus=='1') {  
		                            return  "发送成功";
		                        } 
			                 }
						},
						{
							field : 'feedBackStatusTime',
							title : '短信回执状态时间',
							rowspan:2,
							width : 80
						},{
							field : 'connectId',
							title : '短信联系人',
							rowspan:2,
							width : 40
						}
						] ],
		pagination:true
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
	
	// 查询按钮
	$("#querySmsAnswerBtn").click(function() {
		$('#smsList').datagrid('load', {
			batchId:$("#batchId").val(),
			mobile:$("#mobile").val(),
			receiveStatus:$("#receiveStatus").val(),
			feedbackStatus:$("#feedbackStatus").val(),
			startfeedBackStatusTime:$("#startfeedBackStatusTime").datetimebox("getValue"),
			endfeedBackStatusTime:$("#endfeedBackStatusTime").datetimebox("getValue"),
			startreceiveStatusTime:$("#startreceiveStatusTime").datetimebox("getValue"),
			endreceiveStatusTime:$("#endreceiveStatusTime").datetimebox("getValue"),
			campaignId:$("#d_campaignId").val()
		});
	});	
});