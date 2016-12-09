$(function(){
	window.isSubmitted = false;
    $('#oldPhoneTable').datagrid({
        width:($(window).width()-166)*0.5,
//        height: 120,
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        scrollbarSize:0,
        columns:[[
            {field:'phn2',title:'客户电话',width:40,
                formatter:function(value,rowData,rowIndex){
                    if (rowData.phonetypid && rowData.phonetypid != 4) {
                        var phone = "";
                        phone += (rowData.phn1 ? rowData.phn1+"-":"");
                        phone += value;
                        phone += (rowData.phn3 ? "-"+rowData.phn3:"");
                        return phone;
                    } else {
                        return value;
                    }
                }},
            {field:'prmphn',title:'默认',width:20,align:'center',
                formatter:function(value){
                    return value=='Y' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" disabled />';
                }},
            {field:'backupPhn',title:'备选',width:20,align:'center',
                formatter:function(value, rowData, rowIndex){
                    return rowData.prmphn=='B' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" disabled />';
                }}
        ]]
    });

    $('#oldAddressTable').datagrid({
        width: ($(window).width()-166)*0.5,
//        height: 120,
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        scrollbarSize:0,
        columns:[[
            {field:'receiveAddress',title:'收货地址',width:50},
            {field:'address',title:'详细地址',width:40,resizable:false,
                formatter:function(value) {
                    return '<div onselectstart="return false;"><marquee direction="left" scrollamount="3">'+value+'</marquee></div>';
                }},
            {field:'zip',title:'邮编',width:30 ,align:'center'},
            {field:'isdefault',title:'默认',width:20, align:'center',
                formatter:function(value){
                    return value=='-1' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" disabled />';
                }}
        ]]
    });
    
    var phoneInstArr = new Array();
    var addressInstArr = new Array();
    var baseInfoInstId;
    
    $.ajax({
        url: '/contact/updateBpm/get/'+$("#batchId").text(),
        type: 'GET',
        dataType: 'json',
        success: function(data) {
        	//控制客户基本信息模块
        	var isAllProcessed =true;
/*        	var isConfirmAudit = $("#input_isConfirmAudit").val();
        	var isBaseChanged = true;
			baseInfoInstId = data.baseInfoInstId;		
			var baseInfoStatus = data.baseInfoStatus;
			
    		if(isManager=='true'&&isBaseChanged && data.baseInfoStatus =='0') {
        		$("#div_contact_baseinfo_command").html('<p class="mb10 mt10" align="left">'+
        										  '<input type="radio" name="radio_contact_baseinfo_command" value="1" id="radio_contact_baseinfo_approve"/>批准'+
        										  '<input type="radio" name="radio_contact_baseinfo_command" value="2" id="radio_contact_baseinfo_reject"/>驳回'+
        										  '<input type="text" id="radio_contact_baseinfo_command_text" style="display:none; margin-left:20px; width:60%"/>'+
        										  '</p>');
        		$("#radio_contact_baseinfo_reject").click(function() {
        			$("#radio_contact_baseinfo_command_text").show();
        		});
        		$("#radio_contact_baseinfo_approve").click(function() {
        			$("#radio_contact_baseinfo_command_text").hide();
        		});
        		isAllApproved = false;
        	} else if(isManager=='false'&&isBaseChanged&& data.baseInfoStatus =='0') {
        		$("#div_contact_baseinfo_command").html('<div> <span style="color:red">待审批</span></div>');
    		} else if(isBaseChanged&&data.baseInfoStatus == '1') {
        		$("#div_contact_baseinfo_command").html('<div> <span style="color:red">已审批</span></div>');
    		} else if(isBaseChanged&&data.baseInfoStatus == '2') {
    			$("#div_contact_baseinfo_command").html('<div> <span style="color:red">已驳回</span>'+
    													'<span style="margin-left:20px;color:red">意见:  '+data.basecomment+'</span>'+
    													'</div>');
    		}
    		$("#div_contact_baseinfo_command").show();*/
        	if(data.baseInfoStatus =='0') {
        		isAllProcessed = false;
        	}
    		
            $("#oldName").val(data.oldName);
            data.oldSex == 2 ? $("#oldSexWomen")[0].checked=true : $("#oldSexMen")[0].checked=true;
            //控制客户电话信息模块
	        $("#oldPhoneTable").datagrid('loadData', data.oldPhones);
        	//控制客户地址信息模块
	        $("#oldAddressTable").datagrid('loadData', data.oldAddresss);
	        
	    	var isManager=$("#input_isManager").val();
	    	if (isManager == 'false' || isAllProcessed) {
	    		$("#a_approveBatch").hide();
	    		$("#a_approveBatch").unbind("click");
	    		$("#a_rejectBatch").hide();
	    		$("#a_rejectBatch").unbind("click");
	    	}
        }
    });
	    /*$("#a_submitInstance").bind("click",function() {
			var arrjson = new Array();
			var isAllCommentHad = true;
	    	$("input[name^='radio_']").each(function(){
	    		if($(this).attr("checked")) {
	    			var busiType='新增客户';
	    			var appliedUser = $('#input_crUser').val();
	    			var id = $('#input_id').val();
	    			var comment, status, instId;
	    			comment=$("#"+this.name+"_text").val();
					status=this.value;
	    			if(status=='2' && (comment==null || comment=="")) {
	    				isAllCommentHad =false;
	    			}
	    			if(this.name=='radio_contact_address_command') {
	    				for(i=0; i<addressInstArr.length; i++) {
	    					var instId = addressInstArr[i];
	    					arrjson.push({appliedUser: appliedUser, id: id, comment: comment, instId: instId, busiType: busiType, status: status}); 
	    				}
	    			} else if(this.name=='radio_contact_phone_command') {
	    				for(i=0; i<phoneInstArr.length; i++) {
	    					var instId = phoneInstArr[i];
	    					arrjson.push({appliedUser: appliedUser, id: id, comment: comment, instId: instId, busiType: busiType, status: status});
	    				}
	    			} else {
	    				var instId = baseInfoInstId;
	    				arrjson.push({appliedUser: appliedUser, id: id, comment: comment, instId: instId, busiType: busiType, status: status});
	    			}		
	    		}
	
	    		});
	    	if(!isAllCommentHad) {
                window.parent.alertWin('系统提示', "驳回信息必须填写");
				return;
	    	}
	    	if(arrjson.length==0) {
                window.parent.alertWin('系统提示', '没有流程可审批');
	    		return;
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
                    window.parent.alertWin('系统提示', data);
			    	var id = $('#input_batchId').val();
			    	window.parent.destoryTab('task_'+id);
			     },    
			     error : function() {
                     window.parent.alertWin('系统提示', "操作失败，请重新再试");
			     }  
			});
	    });*/
    
    $("#a_cancelInstance").bind("click",function() {
    	var id = $('#input_batchId').val();
    	window.parent.destoryTab('task_'+id);
    });

    function isNotBlank(value) {
    	if(value!=null && value!="") {
    		return true;
    	} else {
    		return false;
    	}
    }

});

function contactAuditApprove(action) {
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
	    url: ctx+'/task/updateAuditTaskByBatchId',
	    type:'get',
	    cache:false,
	    dataType:'json',
	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
	    data: {
	    	appliedUser: crUser,
	    	batchId: batchId,
	    	id: id,
	    	busiType: '新增客户',
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
		        window.parent.frames['p_mytask'].document.getElementById('btn_auditTask').click();
	    	}
	    	window.parent.destoryTab('task_'+id);
	     },    
	     error : function() {
             window.parent.alertWin('系统提示',"操作失败，请重新再试。");

         }
	});
}

function contactAuditReject() {
	var batchId = $('#input_batchId').val();
	$('#input_reject_contact_batchid').val(batchId);
	$('#div_reject_contact_comment').show();
	$('#div_reject_contact_comment').dialog({
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
				content = $('#textarea_reject_contact_comment').val();
				if(content == "" || content ==null) {
					var tip = '驳回原因必填';
					window.parent.alertWin('系统提示',tip);
					return;
				}
				addContactReject();
			}
			}, {
			text: '取消',
            plain:true,
			handler: function() {
				$('#div_reject_contact_comment').dialog('close');
				$('#textarea_reject_contact_comment').val("");
				$('#div_reject_contact_comment').hide();
			}
			}]
	}
	);
	$('#div_reject_contact_comment').dialog('open');
}

function addContactReject() {
	if(isSubmitted) {
		window.parent.alertWin('系统提示',"请求已经提交，请耐心等待。");
		return;
	} else {
		isSubmitted = true;
	}
	var content = $('#textarea_reject_contact_comment').val();
	var id = $('#input_id').val();
	var batchId = $('#input_batchId').val();
	var crUser = $('#input_crUser').val();
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
	    	busiType: '新增客户',
	    	action: '2',
	    	comment: content
	    },
/*	    beforeSend : function() {
	    	showLoading();
	    },
	    complete : function() {
	    	hideLoading();
	    },*/
		 success:function(data) {
			 	$('#div_reject_contact_comment').hide();
				$('#div_reject_contact_comment').dialog('close');
		        window.parent.alertWin('系统提示',data);
		        var id = $('#input_batchId').val();
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
		        window.parent.destoryTab('task_'+id);
			},
		     error : function() {
		    	 window.parent.alertWin('系统提示', '异常');
		          $('#div_reject_contact_comment').hide();
		          $('#textarea_reject_contact_comment').val("");
		          $('#div_reject_contact_comment').dialog('close');
		     }
			
		
	});
}

function showLoading(){
	$('#loading').fadeIn();
    $('#loading div:first').css('line-height',$('#loading div:first').height()+'px');
}

function hideLoading(){
	$('#loading').fadeOut();
}