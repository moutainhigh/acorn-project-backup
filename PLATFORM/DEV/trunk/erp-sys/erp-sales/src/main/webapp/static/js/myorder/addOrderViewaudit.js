$(function() {
	window.isSubmitted = false;

	//无效地址提示
	var addressKey = $("#d_address_check", window.parent.document).val().split(",");
	var isInvalidAddress = false;
	var inputval = $("#span_detail_address").html();// formatAddress($("#address").val());
	for ( var i = 0; i < addressKey.length; i++) {
		if (inputval.indexOf(addressKey[i]) >= 0) {
			isInvalidAddress = true;
			break;
		} else {
			isInvalidAddress = false;
		}
	}
    if(isInvalidAddress) {
    	$("#span_detail_address").css("background-Color","yellow");
    	$("#span_invalid_address_tip").html("红字为修改内容;&nbsp;黄底为无效地址");
    }
	
	$("#a_cancelInstance").bind("click", function() {
		var batchId = $('#input_batchId').val();
		window.parent.destoryTab('task_' + batchId);
	});
	
	var isManager = $("#input_isManager").val();
	var isAllApproved = $("#input_isAllApproved").val();
	
	var auditTip = $("#input_auditTip").val();
	$('#batchId').tooltip({
	    position: 'right',
	    content: '<span style="color:red">'+auditTip+'</span>'
	});

	if (isManager == 'false' || isAllApproved == 'true') {
		$("#a_approveBatch").hide();
		$("#a_approveBatch").unbind("click");
		$("#a_rejectBatch").hide();
		$("#a_rejectBatch").unbind("click");
	}
});

function orderAuditApprove(action) {
	if(isSubmitted) {
		window.parent.alertWin('系统提示',"请求已经提交，请耐心等待。");
		return;
	} else {
		isSubmitted = true;
	}
	var id = $('#input_id').val();
	var batchId = $('#input_batchId').val();
	var crUser = $('#input_crUser').val();
	var source = $('#input_source').val();
	$.ajax({
//	    url: ctx+'/task/updateAuditTaskByBatchId?appliedUser='+crUser+'&batchId='+batchId+"&id="+id+'&busiType=新增订单&action='+action,
	    url: ctx+'/task/updateAuditTaskByBatchId',
	    type:'get',
	    cache:false,
	    dataType:'json',
	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
	    data: {
	    	appliedUser: crUser,
	    	batchId: batchId,
	    	id: id,
	    	busiType: '新增订单',
	    	action: action
	    },
	    success:function(data) {
            window.parent.alertWin('系统提示',data);
            var id = $('#input_batchId').val();
	    	if(source == 0) {
		    	//跳到首页
		        window.parent.document.getElementById('home').click();
		        window.parent.frames['p_home'].refreshAuditGrid(1);
	    	} else if(source == 1) {
		    	//跳到我的任务页
		        window.parent.document.getElementById('mytask').click();
		        window.parent.frames['p_mytask'].switchInnerTab(1);
//		        $('#mytask_tabs',window.parent.frames['p_mytask'].document).tabs('select',0);
		        window.parent.frames['p_mytask'].document.getElementById('btn_auditTask').click();
	    	}
	    	window.parent.destoryTab('task_'+id);
	     },    
	     error : function() {
             window.parent.alertWin('系统提示',"操作失败，请重新再试。");
         }
	});
}

function orderAuditReject() {
	var batchId = $('#input_batchId').val();
	$('#input_reject_batchid').val(batchId);
	$('#div_reject_comment').show();
	$('#div_reject_comment').dialog({
		title:'驳回原因',
	    width: 500,
	    top: 200,
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
				content = $('#textarea_reject_comment').val();
				if(content == "" || content ==null) {
					var tip = '驳回原因必填';
					window.parent.alertWin('系统提示',tip);
/*					$('#textarea_reject_comment').tooltip({
					    position: 'right',
					    content: '<span style="color:red">'+tip+'</span>'
					}).tooltip('show');
//					alert("驳回原因必填");
					$('#textarea_reject_comment').bind("click", function() {
						$('#textarea_reject_comment').tooltip("destroy");
						$('#textarea_reject_comment').unbind("click");
					});*/
					return;
				}
				showRejectConfirm();
			}
			}, {
			text: '取消',
            plain:true,
			handler: function() {
				$('#div_reject_comment').dialog('close');
				$('#textarea_reject_comment').val("");
				$('#div_reject_comment').hide();
			}
			}]
	}
	);
	$('#div_reject_comment').dialog('open');
}

function showRejectConfirm() {
	$('#div_reject_confirm').show();
	$('#div_reject_confirm').dialog({
		title:'确认驳回',
	    width: 300,
	    top: 200,
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
				$('#div_reject_confirm').dialog('close');
				$('#div_reject_confirm').hide();
				addOrderReject();
			}
			}, {
			text: '取消',
            plain:true,
			handler: function() {
				$('#div_reject_confirm').dialog('close');
				$('#div_reject_confirm').hide();
			}
			}]
	}
	);
	$('#div_reject_confirm').dialog('open');
}

function addOrderReject() {
	if(isSubmitted) {
		window.parent.alertWin('系统提示',"请求已经提交，请耐心等待。");
		return;
	} else {
		isSubmitted = true;
	}
	content = $('#textarea_reject_comment').val();
	var id = $('#input_id').val();
	var batchId = $('#input_batchId').val();
	var crUser = $('#input_crUser').val();
	var source = $('#input_source').val();
	$.ajax({
	    url: ctx+'/task/updateAuditTaskByBatchId',
	    type:'get',
	    cache:false,
	    dataType:'json',
	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
	    data: {
	    	appliedUser: crUser,
	    	batchId: batchId,
	    	id: id,
	    	busiType: '新增订单',
	    	action: '2',
	    	comment: content
	    },
		 success:function(data) {
			 	$('#div_reject_comment').hide();
				$('#div_reject_comment').dialog('close');
		        window.parent.alertWin('系统提示',data);
		        var id = $('#input_batchId').val();
		    	if(source == 0) {
			    	//跳到首页
			        window.parent.document.getElementById('home').click();
			        window.parent.frames['p_home'].refreshAuditGrid(1);
		    	} else if(source == 1) {
			    	//跳到我的任务页
			        window.parent.document.getElementById('mytask').click();
			        window.parent.frames['p_mytask'].switchInnerTab(1);
			        window.parent.frames['p_mytask'].document.getElementById('btn_auditTask').click();
		    	}
		    	window.parent.destoryTab('task_'+id);
			},
		     error : function() {
		          alert("异常！");
		          $('#div_reject_comment').hide();
		          $('#textarea_reject_comment').val("");
		          $('#div_reject_comment').dialog('close');
		     }
	});
}