function cancelTask(instId) {
	$('#window_campaignTaskCancel').html("<div class=\"popWin_wrap\"><table ><tbody><tr><td valign=\"top\">取消原因</td>" +
			"<td><textarea id=\"textarea_cancelRemark\" cols=\"50\" rows=\"4\" class='ml10 fr' style='width:300px'></textarea></td></tr></tbody></table></div>"
			);
	var startDate=null, endDate=null, customerID=null, customerName=null, campaignName=null, userID=null,taskType=null, status=null;
	if($('#startDate').length > 0) {
		startDate=$('#startDate').datebox('getValue');
	} else {
		startDate=new Date(new Date()-90*86400000).format('yyyy-MM-dd');
	}
	if($('#endDate').length > 0) {
		endDate=$('#endDate').datebox('getValue');
	} else {
		endDate=new Date().format('yyyy-MM-dd');
	}
	if($('#customerID').length > 0) {
		customerID=$("#customerID").val();
	}
	if($('#customerName').length > 0) {
		customerName=$("#customerName").val();
	}
	if($('#campaignName').length > 0) {
		campaignName=$("#campaignName").val();
	}
	if($('#userID').length > 0) {
		userID=$("#userID").val();
	}
	if($('#campaignTaskTypes').length > 0) {
		taskType=$("#campaignTaskTypes").combobox('getValue');
	}
	if($('#campaignTaskStatus').length > 0) {
		status=$("#campaignTaskStatus").combobox('getValue');
	}


	// "<td><a class=\"window_btn\" href=\"#\">查找</a>"
	$('#window_campaignTaskCancel').dialog({
		title:'取消任务',
	    width: 400,
	    top: 200,
// height: 200,
		shadow:false,
		collapsible:false,
		minimizable:false,
		maximizable:false,
		resizable:true,
		closed:true,
		modal:true,
        draggable:false,
		buttons: [{
			text: '提交',
            plain:true,
			handler: function() {
				remark = $('#textarea_cancelRemark').val();
				$.ajax({
					url: ctx+"/task/cacelCampaignTask?remark="+remark+"&instId="+instId,
					 success:function(data) {
							$('#table_campaignTask').datagrid('reload',{
								'startDate' : startDate,
								'endDate' : endDate,
								'customerID' : customerID,
								'customerName' : customerName,
								'campaignName' : campaignName,
								'userID' : userID,
								'taskType' : taskType,
								'status' : status
							});
							 $('#window_campaignTaskCancel').dialog('close');
						},
					     error : function() {
                             window.parent.alertWin('系统提示','异常！');
                             $('#window_campaignTaskCancel').dialog('close');
					     }
					});
			}
			}, {
			text: '取消',
            plain:true,
			handler: function() {
				$('#window_campaignTaskCancel').dialog('close');
			}
			}]
	}
	);
	$('#window_campaignTaskCancel').dialog('open');
}

function execTask(id, name, url, campaignId, instId) {
	window.parent.outgoingCall(campaignId, instId);
	window.parent.addTab(id, name, false, url);	
}

function processAuditTask(batchId, busiType, crUser, id, source) {
	url = ctx + "/task/processAuditTaskAjax?batchId=" + batchId
			+ "&auditTaskType=" + busiType + "&crUser=" + crUser + "&id=" + id+"&source="+source;
	if ('取消订单' == busiType) {
		$.ajax({
			url : url+"&ajax=true",
			success : function(data) {

				$('#window_processOrderCancelAuditTask').html(data);
				$('#window_processOrderCancelAuditTask').show();
				$('#window_processOrderCancelAuditTask').window({
					title : '取消订单审批',
// width : 280,
// height : 200,
					iconCls : '',
					top : 200,
					left : ($(window).width() - 400) * 0.5,
					shadow : true,
					modal : true,
					closed : true,
					minimizable : false,
					maximizable : false,
					collapsible : false,
                    draggable:false,
					onBeforeOpen : function() {
						$('#close').click(function() {
							$('#window_processOrderCancelAuditTask').html("");
								$(this).hide();
								$('#window_processOrderCancelAuditTask').window('close');
							}
						);
					},
					onBeforeClose : function() {
						$('#window_processOrderCancelAuditTask').hide();
					}
				});
				$('#window_processOrderCancelAuditTask').window('open');

			},
			error : function() {
                window.parent.alertWin('系统提示','异常！');

                $('#window_processOrderCancelAuditTask').window('close');
			}
		});
	} else if ('修改客户' == busiType){
		window.parent.addTab('task_'+batchId, '修改客户审批', 'false', url);
	} else if ('修改订单' == busiType) {
		$.ajax({
			url : ctx + "/task/orderExist?id=" + id,
			success : function(data) {
				if(data) {
					window.parent.addTab('task_'+batchId, '修改订单审批', 'false', url);
				} else {
					 window.parent.alertWin('系统提示',"订单已被取消或转咨询，流程将关闭");
						$.ajax({
//							url : ctx + "/myorder/myorder/cancel_audit?batchId=" + batchId+"&remark=订单取消或转咨询，流程取消"
							url : ctx + "/task/cancelOrderRelatedProcess?id=" + id+"&remark=订单取消或转咨询，流程取消"
						});
				}
			}
		});

	} else  if('新增订单' == busiType) {
		$.ajax({
			url : ctx + "/task/orderExist?id=" + id,
			success : function(data) {
				if(data) {
					window.parent.addTab('task_'+batchId, '新增订单审批', 'false', url);
				} else {
					 window.parent.alertWin('系统提示',"订单已被取消或转咨询，流程将/已关闭");
						$.ajax({
							url : ctx + "/myorder/myorder/cancel_audit?batchId=" + batchId+"&remark=订单取消或转咨询，流程取消"
						});
				}
			}
		});

	} else  if('新增客户' == busiType) {
		window.parent.addTab('task_'+batchId, '新增客户审批', 'false', url);
	} 
}

function approve(instId_id, comment_id) {
	var comment = $('#'+comment_id).val();
	var instId =  $('#'+instId_id).val();
	var status = "1";
	var busiType = $('#input_busiType').val();
	var id = $('#input_id').val();
	var crUser = $('#input_crUser').val();
	var arrjson = new Array();
	arrjson.push({appliedUser: crUser, id: id, comment: comment, instId: instId, busiType: busiType, status: status});
	var data = JSON.stringify(arrjson);
	$.ajax({
	    url: ctx+'/task/updateAuditTaskBatch',
	    data: data,
	    type:'post',
	    cache:false,
	    dataType:'json',
	    contentType: "application/json; charset=utf-8",
	    success:function(data) {
	    	$('#window_processOrderCancelAuditTask').window('close');
	        window.parent.alertWin('系统提示',data);
	        refreshAuditGrid(1);
	    },
	    error:function(data) {
	    	$('#window_processOrderCancelAuditTask').window('close');
	        window.parent.alertWin(data);
	    }
	});



    $('#table_auditTask').datagrid('reload', {});
}

function reject(instId_id, comment_id) {
	var comment = $('#'+comment_id).val();
	var instId =  $('#'+instId_id).val();
	var status = "2";
	var busiType = $('#input_busiType').val();
	var id = $('#input_id').val();
	var crUser = $('#input_crUser').val();
	var arrjson = new Array();
	arrjson.push({appliedUser: crUser, id: id, comment: comment, instId: instId, busiType: busiType, status: status});
	var data = JSON.stringify(arrjson);
	$.ajax({
	    url: ctx+'/task/updateAuditTaskBatch',
	    data: data,
	    type:'post',
	    cache:false,
	    dataType:'json',
	    contentType: "application/json; charset=utf-8",
	    success:function(data) {
	    	$('#window_processOrderCancelAuditTask').window('close');
	        window.parent.alertWin('系统提示',data);
	    	refreshAuditGrid(1);
	    },
	    error:function(data) {
	    	$('#window_processOrderCancelAuditTask').window('close');
	        window.parent.alertWin(data);
	    }
	});


    $('#table_auditTask').datagrid('reload', {});
}

function refreshAuditGrid(source) {
	if(source==1) {
		//首页
	    $('#table_auditTask').datagrid('reload',{
			'startDate' : new Date(new Date()-90*86400000).format('yyyy-MM-dd'),
			'endDate' : new Date().format('yyyy-MM-dd'),
			'department' : $("#department").val(),
			'taskStatuses' : '0,5'
	    });
	} else if(source ==2) {
		//我的任务页
	    $('#table_auditTask').datagrid('reload',{
			'startDate' : $('#startDate4Audit').datebox('getValue'),
			'endDate' : $('#endDate4Audit').datebox('getValue'),
			'contactID' : $("#contactID4Audit").val(),
			'appliedUserID' : $("#crUser4Audit").val(),
			'orderCreatedUserID' : $("#orderCreatedUserID4Audit").val(),
			'department' : $("#department").val(),
			'orderID' : $("#orderID4Audit").val(),
			'taskType' : $('#auditTaskTypes').combobox('getValue'),
			'taskStatus' : $('#auditTaskStatus').combobox('getValue')
	    });
	}
}

function refreshCampaignGrid(source) {
	if(source == 1) {
		//首页
		 $('#table_campaignTask').datagrid('reload',{
				'startDate' : new Date(new Date()-90*86400000).format('yyyy-MM-dd'),
				'endDate' : new Date().format('yyyy-MM-dd'),
				'userID' : $('#userID').val()
		    });

	} else if(source == 2) {
		//我的任务页
		 $('#table_campaignTask').datagrid('reload',{
				'startDate' : $('#startDate').datebox('getValue'),
				'endDate' : $('#endDate').datebox('getValue'),
				'customerID' : $("#customerID").val(),
				'customerName' : $('#customerName').val(),
				'campaignName' : $('#campaignName').val(),
				'userID' : $('#userID').val(),
				'taskType' : $('#campaignTaskTypes').combobox('getValue'),
				'taskSourceType' : $('#campaignTaskSourceTypes').combobox('getValue'),
				'status' : $('#campaignTaskStatus').combobox('getValue')
		    });
	}
}

function showOutPhone(taskId) {
    $.post("/task/queryPhone",{
    	instId:taskId
    },function(data){
    	window.parent.outPhone2(data, taskId);
    	
    });
}

Date.prototype.format = function(format){
	var o = {
	"M+" : this.getMonth()+1, // month
	"d+" : this.getDate(),    // day
	"h+" : this.getHours(),   // hour
 	"m+" : this.getMinutes(), // minute
	"s+" : this.getSeconds(), // second
 	"q+" : Math.floor((this.getMonth()+3)/3),  // quarter
 	"S" : this.getMilliseconds() // millisecond
	};

	if(/(y+)/.test(format)) {
		format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
	}

 	for(var k in o){
		if(new RegExp("("+ k +")").test(format)){
 			format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
		}
	}
	return format;
};