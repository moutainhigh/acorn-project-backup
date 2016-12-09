$(function() {
	window.isSubmitted = false;
	
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
    
	$("#radio_order_cartinfo_command_reject").click(function() {
		$("#radio_order_cartinfo_command_text").show();
	});
	$("#radio_order_cartinfo_command_approve").click(function() {
		$("#radio_order_cartinfo_command_text").hide();
	});
	
	$("#radio_order_card_command_reject").click(function() {
		$("#radio_order_card_command_text").show();
	});
	$("#radio_order_card_command_approve").click(function() {
		$("#radio_order_card_command_text").hide();
	});
	
	$("#radio_order_contact_command_reject").click(function() {
		$("#radio_order_contact_command_text").show();
	});
	$("#radio_order_contact_command_approve").click(function() {
		$("#radio_order_contact_command_text").hide();
	});

	$("#radio_order_remark_command_reject").click(function() {
		$("#radio_order_remark_command_text").show();
	});
	$("#radio_order_remark_command_approve").click(function() {
		$("#radio_order_remark_command_text").hide();
	});

	$("#radio_mailprice_command_reject").click(function() {
		$("#radio_mailprice_command_text").show();
	});
	$("#radio_mailprice_command_approve").click(function() {
		$("#radio_mailprice_command_text").hide();
	});
	
	$("#radio_order_invoice_command_reject").click(function() {
		$("#radio_order_invoice_command_text").show();
	});
	$("#radio_order_invoice_command_approve").click(function() {
		$("#radio_order_invoice_command_text").hide();
	});
	$("#radio_order_ems_command_reject").click(function() {
		$("#radio_order_ems_command_text").show();
	});
	$("#radio_order_ems_command_approve").click(function() {
		$("#radio_order_ems_command_text").hide();
	});

	$("#a_cancelInstance").bind("click", function() {
        var source = $('#input_source').val();
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
		var batchId = $('#input_batchId').val();
		window.parent.destoryTab('task_' + batchId);
	});

	$("#a_agreeRejection").bind("click", function() {
		if(isSubmitted) {
			window.parent.alertWin('系统提示',"请求已经提交，请耐心等待。");
			return;
		} else {
			isSubmitted = true;
		}
		var batchId = $('#input_batchId').val();
		$.ajax({
		    url: ctx+'/task/closeAuditTask?batchId='+batchId,
		    cache:false,
		    success:function(data) {
                window.parent.alertWin('系统提示',data);
		        var source = $('#input_source').val();
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
		    	window.parent.destoryTab('task_'+batchId);
		     },
		     error : function() {
                 window.parent.alertWin('系统提示',"操作失败，请重新再试。");

             }
			});
	});
	
	$("#a_disAgreeRejection").bind("click", function() {
		var id = $('#input_id').val();
		var batchId = $('#input_batchId').val();
		var url = ctx+'/myorder/orderDetails/get/'+id+'?batchId='+batchId+'&edit=true';
		window.parent.addTab('myorder_'+id,'订单：'+id,'false',url);
/*		$.ajax({
		    url: ctx+'/myorder/orderDetails/get/'+id+'?batchId='+batchId+'&edit=true',
		    cache:false,
		    success:function(data) {
		    	window.parent.destoryTab('task_'+batchId);
		     },
		     error : function() {
		     }
			});*/
	});
	
	var isManager = $("#input_isManager").val();
	var isAllApproved = $("#input_isAllApproved").val();
	var isConfirmAudit = $("#input_isConfirmAudit").val();
	if (isConfirmAudit == '0') {
		$("#a_agreeRejection").hide();
		$("#a_agreeRejection").unbind("click");
		$("#a_disAgreeRejection").hide();
		$("#a_disAgreeRejection").unbind("click");
		if (isManager == 'false' || isAllApproved == 'true') {
			$("#a_submitInstance").hide();
			$("#a_submitInstance").unbind("click");
		}
	} else if (isConfirmAudit == '1') {
		$("#a_submitInstance").hide();
		$("#a_submitInstance").unbind("click");
		$("#a_cancelInstance").hide();
		$("#a_cancelInstance").unbind("click");
	}

});
function orderAuditSubmit() {
	var arrjson = new Array();
	var isAllCommentHad=true;;
	$("input[name^='radio_']").each(
					function() {
						if ($(this).attr("checked")) {
							var s = this.value.split("_");
							var crUser = $('#input_crUser').val();
							var id = $('#input_id').val();
							var comment=$('#'+this.name+'_text').val();
			    			if(s[0]=='2' && (comment==null || comment=="")) {
			    				isAllCommentHad =false;
			    			}
							for(i=1; i<s.length; i++) {
								arrjson.push({appliedUser: crUser, id: id, comment: comment, instId: s[i], busiType: '修改订单', status: s[0]}); 
							}
						}

					});
	if(!isAllCommentHad) {
        window.parent.alertWin('系统提示',"驳回信息必须填写");
		return;
	}
	if(arrjson.length==0) {
        window.parent.alertWin('系统提示', '没有流程可审批');
		return;
	}
	if(isSubmitted) {
		window.parent.alertWin('系统提示',"请求已经提交，请耐心等待。");
		return;
	} else {
		isSubmitted = true;
	}
	var data = JSON.stringify(arrjson);
	$.ajax({
	    url: ctx+'/task/updateAuditTaskBatch',
	    data: data,
	    type:'post',
	    cache:false,
	    dataType:'json',
	    contentType: "application/json; charset=utf-8",
	    success:function(data) {
            window.parent.alertWin('系统提示',data);
	        var source = $('#input_source').val();
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
            var id = $('#input_batchId').val();
	    	window.parent.destoryTab('task_'+id);
	     },    
	     error : function() {
             window.parent.alertWin('系统提示',"操作失败，请重新再试。");

         }
	});
}