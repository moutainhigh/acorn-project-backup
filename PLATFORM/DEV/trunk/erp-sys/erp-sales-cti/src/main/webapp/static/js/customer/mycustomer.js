function _handler(){
       $('#tabs').tabs('resize',{width:'100%'});
       $('#AllCustomerDT,#auditCustomerDt,#auditAddCustomerDt').datagrid('resize',{width:'100%'});
}

$(function(){
	
	//非Inbound
	if(!isINB) {
		//全部客户
		$('#AllCustomerDT').datagrid({
			width : '100%',
			height : 410,
			scrollbarSize:0,
			nowrap:false,
			fitColumns : false,
			url : ctx + '/customer/queryAllCustomer',
			queryParams : {'initLoad' : false},
			onBeforeLoad : function(param){
				if(param.initLoad){
					showLoading();
					return true;
				}
				return false;
			},
			onLoadSuccess: function() {
				hideLoading();
			},
			onLoadError: function() {
				hideLoading();
			},
			columns : [ [ 
			{
				field : 'contactid',
				title : '客户编号',
				align: 'center',
				width: 100,
				formatter: function(val, row){
					var id = 'mycustomer_'+val;
					var url = ctx+'/contact/1/'+row.contactid;
					url = '<a class="link" href="javascript:void(0);" id=\''+id+'\' onclick="window.parent.addTab(\''+val+'\', \'我的客户\', \'false\', \''+url+'\',\'showCallback\')">'+val+'</a>';
					return url;
				}
			}, {
				field : 'name',
				title : '客户姓名',
				align: 'center',
				width: 100
			}, {
				field : 'crusr',
	            align:'center',
				title : '创建人',
				width: 80
			}, {
				field : 'crdt',
				title : '创建时间',
				width: 130,
				align:'center',
				formatter : dateFormatter
			}, {
				field : 'customerFrom',
				title : '客户来源',
				align: 'center',
				width: 80,
				formatter: function(val){
					var _label = '';
					if(val==1){
						_label = '老客户';
					}else if(val==2){
						_label = '自取客户';
					}else if(val==3){
						_label = '成单客户';
					}
					return _label;
				}
			}, {
				field : 'customerOwner',
				title : '客户归属',
				align: 'center',
				width: 100,
				formatter: function(val){
					var _lable = '';
					if(val==1){
						_lable = '专属';
					}else if(val==2){
						_lable = '锁定';
					}else if(val==3){
						_lable = '可用';
					}
					return _lable;
				}
			}, {
				field : 'memberlevelLabel',
				title : '会员等级',
				width: 100	
			}, {
				field : 'membertypeLabel',
	            align:'center',
				title : '会员类型',
				width: 100
			}, {
				field : 'taskQty',
				title : '待办任务数量',
				align: 'center',
				width: 100,
				formatter: function(val, row){
					var id = 'mytask'+val;
					var url = ctx+'/task/myCampaignTask?contactId='+row.contactid;
					url = '<a class="link" href="javascript:void(0)" onclick="window.parent.addTab(\''+id+'\', \'待办任务\', \'false\', \''+url+'\')">'+val+'</a>';
					return url;
				}
			},{
				field : 'bindBeginDate',
				title : '绑定开始日期',
				align:'center',
				formatter : dateFormatter,
				width : 130
			},{
				field : 'bindEndDate',
				title : '绑定结束日期',
				align:'center',
				formatter : dateFormatter,
				width : 130
			},{
				field : 'totalOrderCount',
				title : '累计订单数',
				align:'center',
				width : 90
			},{
				field : 'totalFinishCount',
				title : '完成订单数',
				align:'center',
				width : 90
			},{
				field : 'op',
				title : '操作',
				width: 100,
				align: 'center',
				formatter: function(val, row){
					var _val = '';
					if(row.customerOwner==1 || row.customerOwner==3){
						_val = '<a href="javascript:void(0)" class="createTask" onclick="javascript:createTask('+row.contactid+')" value="'+row.contactid+'">自建任务</a>';
					}
					return _val;
				}
			} 
			] ],
			remoteSort : false,
			singleSelect : true,
			pagination : true
		});
		
		var p = $('#AllCustomerDT').datagrid('getPager');
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
	}
	
	
	function getSelected(){
		var selected = $('#AllCustomerDT').datagrid('getSelected');
		if (selected){
			return selected;
		}
	}
	
	
	//客户类型下拉框
	$('#customerFrom').combobox({
		onSelect:function(r){
			var _val = r.value;
			var _label = '';
			
			if(_val==1){
				_label = '';
				$('.datepart').hide();
				$('#AllCustomerDT').datagrid('showColumn', 'bindBeginDate');
				$('#AllCustomerDT').datagrid('showColumn', 'bindEndDate');
				$('#AllCustomerDT').datagrid('showColumn', 'totalOrderCount');
				$('#AllCustomerDT').datagrid('showColumn', 'totalFinishCount');
				
			}else if(_val==2){
				_label = '取数时间';
				var endDateStr = $('#endDate').datebox('getValue');
				if(endDateStr.length>0){
					var endDate = new Date(Date.parse(endDateStr));
					endDate.setDate(endDate.getDate()-90);
					$('#beginDate').datebox('setValue', dateFormatter(endDate));
				}
				
				$('.datepart').show();
				$('#AllCustomerDT').datagrid('hideColumn', 'bindBeginDate');
				$('#AllCustomerDT').datagrid('hideColumn', 'bindEndDate');
				$('#AllCustomerDT').datagrid('hideColumn', 'totalOrderCount');
				$('#AllCustomerDT').datagrid('hideColumn', 'totalFinishCount');
				
			}else if(_val==3){
				_label = '成单时间';
				var endDateStr = $('#endDate').datebox('getValue');
				if(endDateStr.length>0){
					var endDate = new Date(Date.parse(endDateStr));
					endDate.setDate(endDate.getDate()-7);
					$('#beginDate').datebox('setValue', dateFormatter(endDate));
				}
				
				$('.datepart').show();
				$('#AllCustomerDT').datagrid('hideColumn', 'bindBeginDate');
				$('#AllCustomerDT').datagrid('hideColumn', 'bindEndDate');
				$('#AllCustomerDT').datagrid('hideColumn', 'totalOrderCount');
				$('#AllCustomerDT').datagrid('hideColumn', 'totalFinishCount');
			}
			
			$('#AllCustomerDT').datagrid('loadData', {'total' : 0, 'rows' : []});
			$('#dateLabel').html(_label);
		}
	});
	
	//查询
	$('#allCustomerQuery').click(function(){
		
		var validate = $('#allCustomerForm').form('validate');
		if(!validate){
			return false;
		}
		
		var customerFrom = $('#customerFrom').combobox("getValue");
		var beginDate = $('#beginDate').datebox('getValue');
		var endDate = $('#endDate').datebox('getValue');
		
		var diff = Date.parse(endDate) - Date.parse(beginDate);
		
		if(customerFrom==2){
			if(diff/1000/3600/24/31>=3){
				window.parent.alertWin('错误', '取数时间跨度不能超过3个月');
				return;
			}
		//历史成单一个星期	
		}else if(customerFrom==3){
			if(diff/1000/3600/24>7){
				window.parent.alertWin('错误', '成单时间跨度不能超过7天');
				return;
			}
		}
		
		$('#AllCustomerDT').datagrid('reload',{
			'beginDate' : $('#beginDate').datebox('getValue'),
			'endDate' : $('#endDate').datebox('getValue'),
			'contactid' : $("#contactid").numberbox('getValue'),
			'name' : $('#name').val(),
			'phoneType' : $('input[name="phoneType"]:checked').val(),
			'phoneNo' : $('#phoneNo').val(),
			//'resultCode' : $('#resultCode').combobox("getValue"),
			'membertype' : $('#customerType').combobox("getValue"),
			'memberlevel' : $('#memberLevel').combobox("getValue"),
			'customerFrom' : $('#customerFrom').combobox("getValue"),
			'agentId' : $('#agentId').val(),
			'initLoad' : true
		});
	});
	
	//清空
	$('#allCustomerRest').click(function(){
		var beginDate = $('#beginDate').datebox('getValue');
		var endDate = $('#endDate').datebox('getValue');
		$('#allCustomerForm')[0].reset();
		$('#beginDate').datebox('setValue', beginDate);
		$('#endDate').datebox('setValue', endDate);
	});
	
	/*=========================================================================*/
	
	//修改客户申请
    $('#auditCustomerDt').datagrid({
        width : '100%',
        height : 410,
        fitColumns : true,
        scrollbarSize:0,
        url : ctx+'/customer/queryAuditCustomer',
        queryParams : {'initLoad' : false},
        onBeforeLoad : function(param){
            if(param.initLoad){
                return true;
            }
            return false;
        },
        columns : [ [
            {
                field : 'batchID',
                title : '批次编号',
                align: 'center',
                width : 100,
                formatter: function(val, row){
                    var id = 'task_'+val;
                    url='task/processAuditTaskAjax?batchId='+row.batchID+'&auditTaskType=修改客户&id='+row.cID;
                    url = '<a class="link" href="javascript:void(0);" onclick="window.parent.addTab(\'task_'+row.batchID+'\', \'我的客户\', \'false\', \''+url+'\')">'+val+'</a>';
                    return url;
                }
            }, {
                field : 'cID',
                title : '客户编号',
                align: 'center',
                width : 150,
                formatter: function(val, row){
                    var id = 'mycustomer'+val;
                    var url = ctx + '/contact/1/'+val;
                    url = '<a class="link" href="javascript:void(0);" onclick="window.parent.addTab(\''+val+'\', \'查看客户\', \'false\', \''+url+'\',\'showCallback\')">'+val+'</a>';
                    return url;
                }
            }, {
                field : 'cName',
                align: 'center',
                title : '客户姓名',
                width : 100
            }, {
                align: 'center',
                field : 'crUser',
                title : '创建人',
                width : 100
            }, {
                width : 300,
                align: 'center',
                field : 'crDate',
                title : '提交时间',
                formatter : dateFormatter
            }, {
                align: 'center',
                field : 'status',
                title : '申请状态',
                width : 100
            }, {
                align: 'center',
                field : 'xx',
                title : '处理',
                width : 100,
                formatter: function(val, row){
                    var url;
                    if(row.status=='已拒绝' && row.busiType != '新增客户' && row.busiType != '新增订单') {
                        url='task/processAuditTaskAjax?batchId='+row.batchID+'&auditTaskType=修改客户&isConfirmAudit=1&id='+row.cID;
                        url= '<a href="javascript:void(0);" onclick="window.parent.addTab(\'task_'+row.batchID+'\', \'我的客户\', \'false\', \''+url+'\')" class="link">确认</a>';
                    }
                    return url;
                }
            }
        ] ],
        remoteSort : false,
        singleSelect : true,
        pagination : true
    });
    
	var p = $('#auditCustomerDt').datagrid('getPager');
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
    
    
    if(!isINB) {
    	//新增客户申请
        $('#auditAddCustomerDt').datagrid({
            width : '100%',
            height : 410,
            fitColumns : true,
            scrollbarSize:0,
            url : ctx+'/customer/queryAuditCustomer',
            queryParams : {'initLoad' : false},
            onBeforeLoad : function(param){
                if(param.initLoad){
                    return true;
                }
                return false;
            },
            columns : [ [
                {
                    field : 'batchID',
                    title : '批次编号',
                    align: 'center',
                    width : 100,
                    formatter: function(val, row){
                        var id = 'task_'+val;
                        url='task/processAuditTaskAjax?batchId='+row.batchID+'&auditTaskType=新增客户&id='+row.cID;
                        url = '<a class="link" href="javascript:void(0);" onclick="window.parent.addTab(\'task_'+row.batchID+'\', \'我的客户\', \'false\', \''+url+'\')">'+val+'</a>';
                        return url;
                    }
                }, {
                    field : 'cID',
                    title : '客户编号',
                    align: 'center',
                    width : 150,
                    formatter: function(val, row){
                        var id = 'mycustomer'+val;
                        var url = ctx + '/contact/1/'+val;
                        url = '<a class="link" href="javascript:void(0);" onclick="window.parent.addTab(\''+val+'\', \'查看客户\', \'false\', \''+url+'\',\'showCallback\')">'+val+'</a>';
                        return url;
                    }
                }, {
                    field : 'cName',
                    align: 'center',
                    title : '客户姓名',
                    width : 100
                }, {
                    align: 'center',
                    field : 'crUser',
                    title : '创建人',
                    width : 100
                }, {
                    width : 300,
                    align: 'center',
                    field : 'crDate',
                    title : '提交时间',
                    formatter : dateFormatter
                }, {
                    align: 'center',
                    field : 'status',
                    title : '申请状态',
                    width : 100
                }, {
                    align: 'center',
                    field : 'xx',
                    title : '处理',
                    width : 100,
                    formatter: function(val, row){
                        var url;
                        /* if(row.status=='已拒绝' && row.busiType != '新增客户' && row.busiType != '新增订单') {
                            url='task/processAuditTaskAjax?batchId='+row.batchID+'&auditTaskType=修改客户&isConfirmAudit=0&id='+row.cID;
                            url= '<a href="javascript:void(0);" onclick="window.parent.addTab(\'task_'+row.batchID+'\', \'我的客户\', \'false\', \''+url+'\')" class="link">确认</a>';
                        }*/
                        return url;
                    }
                }
            ] ],
            remoteSort : false,
            singleSelect : true,
            pagination : true
        });
        
        var p = $('#auditAddCustomerDt').datagrid('getPager');
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
    }
    
	
	function getSelected(){
		var selected = $('#auditCustomerDt').datagrid('getSelected');
		if (selected){
			return selected;
		}
	}
   
	//查询修改客户申请
	$('#customerAuditQuery').click(function(){
		
		var validate = $('#customerAuditForm').form('validate');
		if(!validate){
			return false;
		}
		
		$('#auditCustomerDt').datagrid('reload',{
			'contactid': $('#contactid_a').numberbox('getValue'),
			'beginDate': $('#beginFlowDate').datebox('getValue'),
			'endDate': $('#endFlowDate').datebox('getValue'),
			'name' : $('#contactName_a').val(),
			'agentId' : $('#agentId_a').val(),
			'instStatus' : $('#instStatus').combobox('getValue'),
            'auditTaskType':'CONTACTCHANGE',
			'initLoad' : true
		});
	});

    //查询新增客户申请
    $('#customerAddAuditQuery').click(function(){
    	
    	var validate = $('#customerAddAuditForm').form('validate');
		if(!validate){
			return false;
		}
		
        $('#auditAddCustomerDt').datagrid('reload',{
            'contactid': $('#contactid_a_add').numberbox('getValue'),
            'beginDate': $('#beginFlowDate_add').datebox('getValue'),
            'endDate': $('#endFlowDate_add').datebox('getValue'),
            'name' : $('#contactName_a_add').val(),
            'agentId' : $('#agentId_a_add').val(),
            'instStatus' : $('#instStatus_add').combobox('getValue'),
            'auditTaskType':'CONTACTADD',
            'initLoad' : true
        });
    });
	
	//清空
	$('#customerAuditReset').click(function(){
		$('#customerAuditForm')[0].reset();
	});

    //清空
    $('#customerAddAuditReset').click(function(){
        $('#customerAddAuditForm')[0].reset();
    });
	
	$('#cancelTask').click(function(){
		$('#taskDialog').window('close');
	});

});

function createTask(customerId){
	
	$('#customerId_hidden').val(customerId);
	$('#message').html('&nbsp;');
	
	window.parent.popWin(frameElement.id, 'taskDialog', {
		title : '创建任务',
		width : 400,
		height : 135,
		iconCls : '',
		shadow : true,
		modal : true,
		closed : true,
        modal:true,
		minimizable : false,
		maximizable : false,
		collapsible : false,
        draggable:false,
		onBeforeClose: function(){
			$('#customerId_hidden').val('');
		}
	});

}

function closeWin(){
	$('#taskDialog').dialog('close');
}

function ajaxCreateTask(){
	var contactId = $('#customerId_hidden').val();
	var campaignDto = window.parent.document.getElementById('campaignId').value;
	
	if(null==campaignDto || ''==campaignDto){
		window.parent.document.getElementById('message').innerHTML = '请选择任务';
		return false;
	}
	
	$.ajax({
		url: ctx+'/customer/createTask',
		contentType: "application/json",
		data: {"campaignDtoStr":campaignDto, "contactId": contactId},
		success:function(data){
			if(eval(data.success)){
				window.parent.closeWin('taskDialog');
				$('#AllCustomerDT').datagrid('reload');
			}else{
				window.parent.document.getElementById('message').innerHTML = '新增失败:'+data.message;
			}
		},
		error:function(msg){
			window.parent.document.getElementById('message').innerHTML = '会话超时或网络错误';
		}
	});
}

function dateFormatter(val, row){
	if(null!=val){
		return new Date(val).format('yyyy-MM-dd hh:mm:ss');
	}
}

Date.prototype.format = function(format){
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(),    //day
	"h+" : this.getHours(),   //hour
 	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
 	"q+" : Math.floor((this.getMonth()+3)/3),  //quarter
 	"S" : this.getMilliseconds() //millisecond
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

/**
 *
 * @param sysmessage see com.chinadrtv.erp.sales.dto.SysMessageDto
 */
function queryFromSysmessage(sysmessage) {
    if (sysmessage.orgSourceType == 7) {
        $("#tabs").tabs("select",1); //instStatus
        $('#auditCustomerDt').datagrid('load',{
            'contactid': $('#contactid_a').val(),
            'beginDate': $('#beginFlowDate').datebox('getValue'),
            'endDate': $('#endFlowDate').datebox('getValue'),
            'instStatus':'2',
            'auditTaskType':'CONTACTCHANGE',
            'initLoad' : true
        });
    } else if (sysmessage.orgSourceType == 9) {
        $("#tabs").tabs("select",2); //instStatus
        $('#auditAddCustomerDt').datagrid('load',{
            'contactid': $('#contactid_a_add').val(),
            'beginDate': $('#beginFlowDate_add').datebox('getValue'),
            'endDate': $('#endFlowDate_add').datebox('getValue'),
            'instStatus':'2',
            'auditTaskType':'CONTACTADD',
            'initLoad' : true
        });
    }
}


function showLoading(){
	$('#loading').fadeIn();
    $('#loading div:first').css('line-height','500px');
}

function hideLoading(){
	$('#loading').fadeOut();
}
