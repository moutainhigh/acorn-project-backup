var CONTEXT_PATH = jQuery('meta[name="context_path"]').attr('content') || '';
$(function(){

	$('#smsDetailDataGrid').datagrid({
		title:'',
		iconCls:'',
		width:'100%',
		height:435,
		nowrap: false,
		striped: true,
		collapsible:true,
		fitColumns:true,
		url:CONTEXT_PATH+'/sms/detailesListForCampaign',
		queryParams:{
			batchId: $("#d_batchId").val(),
			mobile:$("#d_mobile").val(),
			receiveStatus:$("#d_receiveStatus").val(),
			feedbackStatus:$("#d_feedbackStatus").val(),
			//startfeedBackStatusTime:$("#d_startfeedBackStatusTime").datetimebox("getValue"),
			//endfeedBackStatusTime:$("#d_endfeedBackStatusTime").datetimebox("getValue"),
			//startreceiveStatusTime:$("#d_startreceiveStatusTime").datetimebox("getValue"),
			//endreceiveStatusTime:$("#d_endreceiveStatusTime").datetimebox("getValue"),
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
	var p = $('#smsDetailDataGrid').datagrid('getPager');
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
	$("#d_querySmsAnswerBtn").click(function() {
		$('#smsDetailDataGrid').datagrid('load', {
			batchId: $("#d_batchId").val(),
			mobile:$("#d_mobile").val(),
			receiveStatus:$("#d_receiveStatus").val(),
			feedbackStatus:$("#d_feedbackStatus").val(),
			startfeedBackStatusTime:$("#d_startfeedBackStatusTime").datetimebox("getValue"),
			endfeedBackStatusTime:$("#d_endfeedBackStatusTime").datetimebox("getValue"),
			startreceiveStatusTime:$("#d_startreceiveStatusTime").datetimebox("getValue"),
			endreceiveStatusTime:$("#d_endreceiveStatusTime").datetimebox("getValue"),
			campaignId:$("#d_campaignId").val()
		});
	});	
});