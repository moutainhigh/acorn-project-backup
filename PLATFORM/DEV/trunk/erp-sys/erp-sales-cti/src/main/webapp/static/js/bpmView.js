$(function(){
	window.isSubmitted = false;
    $('#oldPhoneTable').datagrid({
        width:($(window).width()-166)*0.5,
        height: 150,
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        scrollbarSize:0,
        columns:[[
            {field:'phn2',title:'客户电话',width:150,
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
            {field:'prmphn',title:'默认',width:50,align:'center',
                formatter:function(value){
                    return value=='Y' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" disabled />';
                }},
            {field:'backupPhn',title:'备选',width:50, align:'center',
                formatter:function(value, rowData, rowIndex){
                    return rowData.prmphn=='B' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" disabled />';
                }}
        ]]
    });

    $('#newPhoneTable').datagrid({
        width: ($(window).width()-166)*0.5,
        height: 150,
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        scrollbarSize:0,
        columns:[[
            {field:'phn2',title:'客户电话',width:150,
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
            {field:'prmphn',title:'默认',width:50,align:'center',
                formatter:function(value,rowData,rowIndex){
                	if(rowData.instId != null && "" != rowData.instId) {
                		return value=='Y' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" disabled />';
                	} else {
                		return "";
                	}
                }},
            {field:'backupPhn',title:'备选',width:50,align:'center',
                formatter:function(value, rowData, rowIndex){
                    return rowData.prmphn=='B' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" disabled />';
                }}
        ]]
    });

    $('#newAddressTable').datagrid({
        width:($(window).width()-166)*0.5,
        height: 150,
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        scrollbarSize:0,
        columns:[[
            {field:'receiveAddress',title:'收货地址',width:30,
            	formatter:function(value,row,index) {
            		return '<div onselectstart="return false;">'+value+'</div>';
/*            	if(row.instId != null && "" != row.instId) {
            		return '<div onselectstart="return false;">'+value+'</div>';
            	} else {
            		return "";
            	}*/
            }},
            {field:'address',title:'详细地址',width:10,resizable:false,
                formatter:function(value,row,index) {
                	return '<div onselectstart="return false;"><marquee direction="left" scrollamount="3">'+value+'</marquee></div>';
/*                	if(row.instId != null && "" != row.instId) {
                		return '<div onselectstart="return false;"><marquee direction="left" scrollamount="3">'+value+'</marquee></div>';
                	} else {
                		return "";
                	}*/
                }},
            {field:'zip',title:'邮编',width:15, align:'center',
                	formatter:function(value,row,index) {
                	return '<div onselectstart="return false;">'+value+'</marquee></div>';
    /*        	if(row.instId != null && "" != row.instId) {
            		
            	} else {
            		return "";
            	}*/
             }},
            {field:'isdefault',title:'默认',width:10,align:'center',
                formatter:function(value,row,index){
                	return value=='-1' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" disabled />';
/*                	if(row.instId != null && "" != row.instId) {
                		return value=='-1' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" disabled />';
                	}*/
                }}
        ]]
    });

    $('#oldAddressTable').datagrid({
        width: ($(window).width()-166)*0.5,
        height: 150,
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        scrollbarSize:0,
        columns:[[
            {field:'receiveAddress',title:'收货地址',width:30},
            {field:'address',title:'详细地址',width:10,resizable:false,
                formatter:function(value) {
                    return '<div onselectstart="return false;"><marquee direction="left" scrollamount="3">'+value+'</marquee></div>';
                }},
            {field:'zip',title:'邮编',width:15,align:'center'},
            {field:'isdefault',title:'默认',width:10,align:'center',
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
        	var isAllApproved =true;
        	var isManager=$("#input_isManager").val();
        	var isConfirmAudit = $("#input_isConfirmAudit").val();
        	var isBaseChanged = true;
        	if(isConfirmAudit=='0' || (isConfirmAudit=='1' && data.isBaseRejected=='1')) {
	        	if(data.baseInfoInstId == null || "" == data.baseInfoInstId) {
	        		$("td[name^='td_contact_base_info']").hide();
	        		isBaseChanged = false;
	        	} else {
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
		    		} else if(isBaseChanged&&data.baseInfoStatus == '4') {
		        		$("#div_contact_baseinfo_command").html('<div> <span style="color:red">已关闭</span></div>');
		    		} 
		    		$("#div_contact_baseinfo_command").show();
	        	}
	    		
	            $("#oldName").val(data.oldName);
	            $("#newName").val(data.newName);
	            data.nameEdit ? $("#newNameTR").addClass("red") : $("#newNameTR").removeClass("red");
	            data.oldSex == 2 ? $("#oldSexWomen")[0].checked=true : $("#oldSexMen")[0].checked=true;
	            data.newSex == 2 ? $("#newSexWomen")[0].checked=true : $("#newSexMen")[0].checked=true;
	            data.sexEdit ? $("#newSexTR").addClass("red") : $("#newSexTR").removeClass("red");
        	} else {
        		$("tr[name^='tr_base_']").each(function(){
        			$(this).hide();
        		});
        	}
            //控制客户电话信息模块
        	if(isConfirmAudit=='0' || (isConfirmAudit=='1' && data.isPhoneRejected=='1')) {
	            $("#oldPhoneTable").datagrid('loadData', data.oldPhones);
	            
	            var isPhoneChanged=true;
	            var phoneStatus;
	            for(i = 0; i < data.newPhones.length; i++) {
	            	if(isNotBlank(data.newPhones[i].instId)) {
	            		phoneInstArr.push(data.newPhones[i].instId);
	            		phoneStatus = data.newPhones[i].status;
	            	}
	            }
	        	if(phoneInstArr==null || phoneInstArr.length ==0) {
	        		$("td[name^='td_new_phone_info']").hide();
	        		isPhoneChanged = false;
	        	} else {
	        			var phoneAddIndexs = eval('['+data.phoneAddIndexs+']');
		                $("#newPhoneTable").datagrid({
		                    rowStyler:function(rowIndex){
		                        if($.inArray(rowIndex, phoneAddIndexs)>-1) {
	//	                        	$("#newPhoneTable_"+rowIndex).show();
		                            return 'color:red;';
		                        }
		                    }
		                });
	                	$("#newPhoneTable").datagrid('loadData', data.newPhones);
		        		if(isManager=='true'&&isPhoneChanged &&phoneStatus =='0') {
		        		$("#div_contact_phone_command").html('<p class="mb10 mt10" align="left">'+
		        										  '<input type="radio" name="radio_contact_phone_command" value="1" id="radio_contact_phone_approve"/>批准' +
		        										  '<input type="radio" name="radio_contact_phone_command" value="2" id="radio_contact_phone_reject"/>驳回' +
		        										  '<input type="text" id="radio_contact_phone_command_text" style="display:none; margin-left:20px; width:60%"/>'+
		        										  '</p>');
			        		$("#radio_contact_phone_reject").click(function() {
			        			$("#radio_contact_phone_command_text").show();
			        		});
			        		$("#radio_contact_phone_approve").click(function() {
			        			$("#radio_contact_phone_command_text").hide();
			        		});
			        		isAllApproved = false;
			        	} else if(isManager=='false'&&isPhoneChanged&& phoneStatus =='0') {
			        		$("#div_contact_phone_command").html('<div> <span style="color:red">待审批</span></div>');
			    		} else if(isPhoneChanged &&phoneStatus == '1') {
			        		$("#div_contact_phone_command").html('<div> <span style="color:red">已审批</span></div>');
			    		} else if(isPhoneChanged &&phoneStatus == '2') {
			    			$("#div_contact_phone_command").html('<div> <span style="color:red">已驳回</span>'+
			    					'<span style="margin-left:20px;color:red">审批意见:  '+data.phoneComment+'</span>'+
			    					'</div>');
			    		} else if(isPhoneChanged &&phoneStatus == '4') {
			        		$("#div_contact_phone_command").html('<div> <span style="color:red">已关闭</span></div>');
			    		} 
			        	$("#div_contact_phone_command").show();
	        		}
        	} else {
        		$("tr[name^='tr_phone_']").each(function(){
        			$(this).hide();
        		});
        	}
        	//控制客户地址信息模块
        	if(isConfirmAudit=='0' || (isConfirmAudit=='1' && data.isAddressRejected=='1')) {
	            $("#oldAddressTable").datagrid('loadData', data.oldAddresss);
	            
	            var isAddressChanged=true;
	            var status;
	            for(i = 0; i < data.newAddresss.length; i++) {
	            	if(isNotBlank(data.newAddresss[i].instId)) {
	            		addressInstArr.push(data.newAddresss[i].instId);
	            		status = data.newAddresss[i].status;
	            	}
	            }
	        	if(addressInstArr==null || addressInstArr.length ==0) {
	        		$("td[name^='td_new_address_info']").hide();
	        		isAddressChanged = false;
	        	} else {
		                var addressEditIndexs = eval('['+data.addressEditIndexs+']');
		                $("#newAddressTable").datagrid({
		                    rowStyler:function(rowIndex){
		                        if($.inArray(rowIndex, addressEditIndexs)>-1) {
	//	                        	 $("#newAddressTable_"+rowIndex).show();
		                            return 'color:red;';
		                        }
		                    }
		                });
		                $("#newAddressTable").datagrid('loadData', data.newAddresss);
		        		if(isManager=='true'&&isAddressChanged && status =='0') {
		        		$("#div_contact_new_address_command").html('<p class="mb10 mt10" align="left">' +
		        												   '<input type="radio" name="radio_contact_address_command" value="1" id="radio_contact_address_approve"/>批准' +
		        												   '<input type="radio" name="radio_contact_address_command" value="2" id="radio_contact_address_reject"/>驳回' +
		        												   '<input type="text" id="radio_contact_address_command_text" style="display:none; margin-left:20px; width:60%"/>'+
		        												   '</p>');
		        		$("#radio_contact_address_reject").click(function() {
		        			$("#radio_contact_address_command_text").show();
		        		});
		        		$("#radio_contact_address_approve").click(function() {
		        			$("#radio_contact_address_command_text").hide();
		        		});
		        		isAllApproved = false;
		        	} else if(isManager=='false'&&isAddressChanged&& status =='0') {
		        		$("#div_contact_new_address_command").html('<div> <span style="color:red">待审批</span></div>');
		    		} else if(isAddressChanged &&status == '1') {
		        		$("#div_contact_new_address_command").html('<div> <span style="color:red">已审批</span></div>');
		    		} else if(isAddressChanged &&status == '2') {
		    			$("#div_contact_new_address_command").html('<div> <span style="color:red">已驳回</span>'+
		    					'<span style="margin-left:20px;color:red">审批意见:  '+data.addressComment+'</span>'+
		    					'</div>');
		    		} else if(isAddressChanged &&status == '4') {
		        		$("#div_contact_new_address_command").html('<div> <span style="color:red">已关闭</span></div>');
		    		}
		        	$("#div_contact_new_address_command").show();
	        	}
        	} else {
        		$("tr[name^='tr_address_']").each(function(){
        			$(this).hide();
        		});
        	}
        	
        	if (isConfirmAudit == '0') {
        		$("#a_agreeRejection").hide();
        		$("#a_agreeRejection").unbind("click");
        		$("#a_disAgreeRejection").hide();
        		$("#a_disAgreeRejection").unbind("click");
        		if (isManager == 'false' || isAllApproved) {
        			$("#a_submitInstance").hide();
        			$("#a_submitInstance").unbind("click");
        		}
        	} else if (isConfirmAudit == '1') {
        		$("#a_submitInstance").hide();
        		$("#a_submitInstance").unbind("click");
        		$("#a_cancelInstance").hide();
        		$("#a_cancelInstance").unbind("click");
        	}
        }
    });
	    $("#a_submitInstance").bind("click",function() {
			var arrjson = new Array();
			var isAllCommentHad = true;
	    	$("input[name^='radio_']").each(function(){
	    		if($(this).attr("checked")) {
	    			var busiType='修改客户';
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
                    window.parent.alertWin('系统提示', data);
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
                     window.parent.alertWin('系统提示', "操作失败，请重新再试");
			     }  
			});
	    });
    $("#a_cancelInstance").bind("click",function() {
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
    });
    
    $("#a_agreeRejection").bind("click", function() {
    	if(isSubmitted) {
    		window.parent.alertWin('系统提示',"请求已经提交，请耐心等待。");
    		return;
    	} else {
    		isSubmitted = true;
    	}
    	$(this).attr("disabled","disabled");
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
                 window.parent.alertWin('系统提示',"操作失败，请重新再试");
		     }
			});
	});
	
	$("#a_disAgreeRejection").bind("click", function() {
		$(this).attr("disabled","disabled");
		var id = $('#input_id').val();
		var batchId = $('#input_batchId').val();
		var url = ctx+'/contact/1/'+id+'?batchId='+batchId;
		window.parent.addTab(id,'我的客户','false',url);
	});
    

    function isNotBlank(value) {
    	if(value!=null && value!="") {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    
});