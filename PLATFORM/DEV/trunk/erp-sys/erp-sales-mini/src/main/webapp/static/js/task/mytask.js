function _handler(){
       $('#mytask_tabs').tabs('resize',{width:'100%'});
       $('#table_campaignTask,#table_auditTask,#table_jobRelationex').datagrid('resize',{width:'100%'});
}
$(function(){

	init();
	/*================================营销任务==================================*/
	$('#table_campaignTask').datagrid({
		width : '100%',
		height : 438,
		fitColumns : true,
        rownumbers:true,
        nowrap:false,
//        fit:true,
		url : 'queryCampaignTask',
        scrollbarSize:-1,
		queryParams : {
			'startDate' : $('#startDate').datebox('getValue'),
			'endDate' : $('#endDate').datebox('getValue'),
			'customerID' : $("#customerID").val(),
			'customerName' : $('#customerName').val(),
			'campaignName' : $('#campaignName').val(),
			'userID' : $('#userID').val(),
			'taskType' : $('#campaignTaskTypes').combobox('getValue'),
			'taskSourceType' : $('#campaignTaskSourceTypes').combobox('getValue'),
			'status' : $('#campaignTaskStatus').combobox('getValue')
		},
		columns : [ [
 		{
			field : 'instID',
			title : '任务编号',
			formatter: function(val, row) {
				if(row.conid==null) {
					var url = '<a class="link" href="javascript:void(0)" id=\''+val+'\' onclick="showOutPhone(\''+val+'\')">'+val+'</a>';
					return url;
				} else {
					return val;
				}
			}
		}, 
		{
			field : 'caID',
			title : '营销编号'
		}, 
		{
			field : 'caName',
			title : '营销名称'
		}, 
		{
			field : 'taskType',
            align:'center',
			title : '任务类型'
		}, 
		{
			field : 'sourceTypeDsc',
            align:'center',
			title : '任务来源'
		}, 
		{
			field : 'conid',
			title : '客户编号',
            align:'center',
			formatter: function(val, row){
				if(val != null) {
					var id = val;
					var url;
					var paras='';
					if(row.ltStatus=='初始化' || row.ltStatus=='开始') {
						paras+='?instId='+row.instID;
					}
					if(row.isPotential==0) {
						url = ctx+'/contact/1/'+row.conid;
					} else {
						url = ctx+'/contact/2/'+row.conid;
					}
					url+=paras;
					url = '<a class="link" href="javascript:void(0)" id=\''+id+'\' onclick="execTask(\''+id.valueOf()+'\', \'我的客户\', \''+url+'\','+row.caID+','+row.instID+')">'+val+'</a>';
					return url;
				}
			}
		},
		{
			field : 'conName',
            align:'center',
			title : '客户名称'
		}, 
		{
			field : 'ltCreateDate',
			title : '任务生成时间',
			formatter : dateFormatter,
			width:130
		},{
			field : 'endDate',
			title : '任务到期时间',
			formatter : dateFormatter,
			width:130
		},
		{
			field : 'appointDate',
			title : '预约联系时间',
			formatter : dateFormatter,
			width:130
		},
		{
			field : 'ltStatus',
            align:'center',
			title : '状态'
		}, 
		{
			field : 'remark',
            width:100,
			title : '备注'
		},
		{
			field : 'ops',
			title : '操作',
			width:100,
			align:'center',
			formatter: function(value,row){
				if((row.sourceType == 0 || row.sourceType==2) &&(row.ltStatus=='初始化' || row.ltStatus=='开始')) {
					url = '<a href="javascript:void(0)" class="del" id=\''+row.instID+'\' onclick="cancelTask(\''+row.instID+'\')"></a>';
					return url;
				} else {
					return;
				}
			}
		}
		] ],
		remoteSort : false,
		singleSelect : true,
		pagination : true,
        pageList : [ 5, 10, 15 ],
        pageSize:15,

        rowStyler: function(index,row){
			if (row.exigency == '0') {
				return 'background-color:#FFC1C1';
			} else if (row.exigency == '1') {
				return 'background-color:#fcffa6';
			}
		}
	});

	var campaignTaskPager = $('#table_campaignTask').datagrid('getPager');
	$(campaignTaskPager).pagination({
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function() {
			$(this).pagination('loading');
			$(this).pagination('loaded');
		}
	});
	
	//查询
	$('#btn_campaignTask').click(function(){
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
	});
	
	//清空
	$('#btn_campaignClear').click(function(){
		$('#campaignTaskForm')[0].reset();
		$('#campaignTaskStatus').combobox('setValue','');
		$('#campaignTaskTypes').combobox('setValue','');
		$('#startDate').datebox('setValue', new Date(new Date()-90*86400000).format('yyyy-MM-dd'));
		$('#endDate').datebox('setValue', new Date().format('yyyy-MM-dd'));
	});
	
	
	/*================================审批任务==================================*/
	var data = $('#auditTaskStatus').combobox('getData');
    if (data.length > 0) {
        $('#auditTaskStatus').combobox('select', data[1].value);
    } 
	$('#table_auditTask').datagrid({
		width : '100%',
		height : 438,
		fitColumns:true,
        rownumbers:true,
        scrollbarSize:-1,
		url : 'queryAuditTask',
		queryParams : {
			'startDate' : $('#startDate4Audit').datebox('getValue'),
			'endDate' : $('#endDate4Audit').datebox('getValue'),
			'contactID' : $("#contactID4Audit").val(),
			'appliedUserID' : $("#crUser4Audit").val(),
			'orderCreatedUserID' : $("#orderCreatedUserID4Audit").val(),
			'department' : $("#department").val(),
			'orderID' : $("#orderID4Audit").val(),
			'taskType' : $('#auditTaskTypes').combobox('getValue'),
			'taskStatus' : $('#auditTaskStatus').combobox('getValue')
		},
		columns : [ [
		{
			field : 'batchID',
			title : '修改批次',
			width:60
		}, 
		{
			field : 'busiType',
			title : '类型',
            align:'center',
			width:60
		}, 
		{
			field : 'crDate',
			title : '申请时间',
			formatter : dateFormatter,
			width:130
		}
		,
		{
			field : 'cID',
			title : '客户编号',
			width:80
		},
		{
			field : 'cName',
			title : '客户姓名',
            align:'center',
			width:60
		}, 
		{
			field : 'orderID',
			title : '订单编号',
			width:80
		},
		{
			field : 'status',
			title : '状态',
            align:'center',
			width:60
		}, 
		{
			field : 'crUser',
			title : '申请座席',
            align:'center',
			width:60
		},
		{
			field : 'orCrUsrID',
			title : '订单创建座席',
			width:80
		},
		{
			field : 'orCrUsrGrpName',
			title : '创建座席组',
            align:'center',
			width:80
		},
		{
			field : 'mdUser',
			title : '操作',
			width:60,
			align:'center',
			formatter: function(value,row){
				var id = 'myaudittask_'+row.batchID;
//				var url = ctx+'/task/processAuditTask?batchId='+row.batchID+'&auditTaskType='+row.busiType;
				var contentId;
				if(row.busiType == '修改客户' || row.busiType == '新增客户') {
					contentId = row.cID;
				} else if(row.busiType == '修改订单' || row.busiType == '取消订单' || row.busiType == '新增订单') {
					contentId = row.orderID;
				} 
				url = '<a class="dispose" title="处理" id=\''+value+'\' onclick="processAuditTask(\''+row.batchID+'\',\''+row.busiType+'\',\''+row.crUser+'\',\''+contentId+'\',\'1\')"></a>';
//				url = '<input type="button" id=\'btn_'+row.batchID+'\' value=\'处理\' onclick="window.parent.addTab(\''+id.valueOf()+'\', \'处理审批任务\', \'true\', \''+url+'\')"/>';
				return url;
			}
		}
		] ],
		remoteSort : false,
		singleSelect : true,
		pagination : true,
        pageList : [ 5, 10, 15 ],
        pageSize:15
	});

	var auditTaskPager = $('#table_auditTask').datagrid('getPager');
	$(auditTaskPager).pagination({
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function() {
			$(this).pagination('loading');
			$(this).pagination('loaded');
		}
	});
	
	//查询
	$('#btn_auditTask').click(function(){
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
	});
	
	//清空
	$('#btn_auditClear').click(function(){
		$('#auditTaskForm')[0].reset();
		$('#startDate4Audit').datebox('setValue', new Date(new Date()-90*86400000).format('yyyy-MM-dd'));
		$('#endDate4Audit').datebox('setValue', new Date().format('yyyy-MM-dd'));
		$('#auditTaskTypes').combobox('setValue','');
		$('#auditTaskStatus').combobox('setValue','');
	});
	
	function dateFormatter(val, row){
		if(null!=val){
			return new Date(val).format('yyyy-MM-dd hh:mm:ss');
		}
	}
	
	/*==========================自助取数==============================*/
	$('#table_jobRelationex').datagrid({
		width : '100%',
		fitColumns:true,
        scrollbarSize:-1,
		url : 'queryjobRelationex',
		columns : [ [
		{
			field : 'jobId',
			title : '数据队列',
            align:'center',
			width:180
		}, 
		{
			field : 'currentNum',
			title : '当前数量',
            align:'center',
			width:180
		}, 
		{
			field : 'top',
			title : '可取数量',
            align:'center',
			width:180,
			formatter: function(value,row){
				if(row.top==null) {
					value='∞';
				}
				return value;
			}
		}
		,
		{
			field : 'usedNum',
			title : '当日已取数量',
            align:'center',
			width:180
		},
		{
			field : 'mdUser',
			title : '操作',
            align:'center',
			width:180,
			formatter: function(value,row){
//				url = '<a href="javascript:void(0)" id=\''+value+'\' onclick="cancelTask(\''+value+'\')">取消</a>';
				if(row.top==null || (row.top != 0 && row.top-row.usedNum > 0)) {
					url = '<input type="button" id=\'btn_'+row.jobId+'\' value=\'取数\' onclick="assignHist(\''+row.jobId+'\')"/>';
				} else {
					url ='无数可取';
				}
				return url;
			}
		}
		] ],
		remoteSort:false,
		singleSelect:true
//		pagination : false,
//		rownumbers : false
	});
	
	function init() {
		$("#startDate").datebox({
			required: "true",
			missingMessage: "必填项",
			formatter: function (date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);
			}
		});
		$("#endDate").datebox({
			required: "true",
			missingMessage: "必填项",
			formatter: function (date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);
			}
		});
		$("#startDate4Audit").datebox({
			required: "true",
			missingMessage: "必填项",
			formatter: function (date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);
			}
		});
		$("#endDate4Audit").datebox({
			required: "true",
			missingMessage: "必填项",
			formatter: function (date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);
			}
		});
		$('#startDate').datebox('setValue', new Date(new Date()-90*86400000).format('yyyy-MM-dd'));
		$('#endDate').datebox('setValue', new Date().format('yyyy-MM-dd'));
		$('#startDate4Audit').datebox('setValue', new Date(new Date()-90*86400000).format('yyyy-MM-dd'));
		$('#endDate4Audit').datebox('setValue', new Date().format('yyyy-MM-dd'));
	}
	
	// 每10分钟刷新一次
	setInterval("reloadCampaignTask()", 1000 * 60 * 10);

    /*==========================任务分配历史==============================*/
   /* $('#callback-log-grid').datagrid({
        width : '100%',
        height : 400,
        fitColumns:true,
        pagination:true,
        rownumbers:true,
        pageSize: 10,
        pageList: [5,10,15],
        columns : [ [
            {
                field : 'campaignId',
                title : '营销计划',
                align:'center',
                width:180
            },
            {
                field : 'batchCode',
                title : '客户群批次号',
                align:'center',
                width:180
            },
            {
                field : 'assignTime',
                title : '分配时间',
                align:'center',
                width:180,
                formatter:function(value) {
                    return new Date(value).format('yyyy-MM-dd hh:mm:ss');
                }
            }
            ,
            {
                field : 'status',
                title : '分配状态',
                align:'center',
                width:180
            }
        ]],
        remoteSort : false,
        singleSelect : true,
        rownumbers : false,
        onLoadSuccess:function(data) {
            $('#log_allotNum').html(data.total);
        }
    });

    var logPager = $('#callback-log-grid').datagrid('getPager');
    $(logPager).pagination({
//        pageSize: 10,
//        pageList: [5,10,15],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onBeforeRefresh:function(){
            $(this).pagination('loading');
            $(this).pagination('loaded');
        }
    });

    $('#lnk-callback-log-submit').click(function(){
        $('#callback-log-grid').datagrid({
            url : 'findContactAssignHist',
            queryParams: {
                campaignId:$('#log-campaign').combobox('getValue'),
                batchCode:$('#log-batchCode').val(),
                status:$('#log-status').combobox('getValue'),
                assignStartTime:$('#log-startdate').datetimebox('getValue'),
                assignEndTime:$('#log-enddate').datetimebox('getValue')
            }
        });
        $('#d_log-campaign').val($('#log-campaign').combobox('getValue'));
        $('#d_log-batchCode').val($('#log-batchCode').val());
        $('#d_log-status').val($('#log-status').combobox('getValue'));
        $('#d_log-startdate').val($('#log-startdate').datetimebox('getValue'));
        $('#d_log-enddate').val($('#log-enddate').datetimebox('getValue'));
    });

    $('#lnk-callback-log-reset').click(function(){
        $('#log-campaign').combobox('setValue', ''),
        $('#log-batchCode').val(''),
        $('#log-status').combobox('setValue',''),
        $('#log-startdate').datetimebox('setValue',''),
        $('#log-enddate').datetimebox('setValue','')
    });

    $('#lnk-export-log').click(function(){
        $('#downloadForm').submit();
    });*/
});

function reloadCampaignTask() {
	$("#table_campaignTask").datagrid("reload");
}

function switchInnerTab(index) {
	$('#mytask_tabs').tabs('select',index);
}

function assignHist(jobId) {
	$.ajax({
		//url : ctx + "/task/assignHist4PerfTest?jobId=" + jobId,
		url : ctx + "/task/assignHist?jobId=" + jobId,
		success : function(data) {
			
			if(data.isSuc) {
				if(data.tip != null && data.tip != "") {
					$('#p_assign_tip').text(data.tip);
				}
				$('#span_contactStartDate').text(new Date(data.startDate).format('yyyy-MM-dd hh:mm:ss'));
				$('#span_contactEndDate').text(new Date(data.endDate).format('yyyy-MM-dd hh:mm:ss'));
				$('#a_taskId').text(data.instId);
				$('#span_contactId').text(data.contactId);
				$('#window_campaignTasktip').show();
				$('#window_campaignTasktip').window({
					title:'取数',
					top:200,
//                    width: 500,
//                    height: 200,
					shadow:false,
					collapsible:false,
					minimizable:false,
					maximizable:false,
					resizable:false,
					closed:true,
					modal:true,
                    draggable:false,
					onOpen : function() {
						if(data.instId != null && data.instId!='') {
							$('#a_taskId').bind("click",function(){
								$('#window_campaignTasktip').hide();
								$('#window_campaignTasktip').window('close');
								var id = data.contactId;
								
								var url;
								var paras='?instId='+data.instId;
								if(data.isPotential=='0') {
									url = ctx+'/contact/1/'+data.contactId;
								} else {
									url = ctx+'/contact/2/'+data.contactId;
								}
								url+=paras;
								execTask(data.contactId,'我的客户',url,data.campaignId,data.instId);
							});
						}
					},
					onBeforeClose: function(){
						$('#p_assign_tip').text("取到一条数据");
						$('#window_campaignTasktip').hide();
						$('#a_taskId').unbind("click");
					}
				});
				$('#window_campaignTasktip').window('open');
				$('#table_jobRelationex').datagrid('reload', {});
			} else {
				if(data.tip != null && data.tip != "") {
                    window.parent.alertWin('系统提示',data.tip);
				} else {
                    window.parent.alertWin('系统提示','取数失败');
				}
			}

		},
		error : function() {
            window.parent.alertWin('系统提示','取数失败');
			$('#window_campaignTasktip').window('close');
		}
	});
}

/**
 *
 * @param sysmessage see com.chinadrtv.erp.sales.dto.SysMessageDto
 */
function queryFromSysmessage(sysmessage)
{
    $("#mytask_tabs").tabs("select",1);
    if(sysmessage.orgSourceType=='2')//cancel order
    {
        $("#auditTaskTypes").combobox('setValue','2');
    }
    else if(sysmessage.orgSourceType=='3') //modify contact
    {
        $("#auditTaskTypes").combobox('setValue','1');
    }
    else if(sysmessage.orgSourceType=='4') //modify contact
    {
        $("#auditTaskTypes").combobox('setValue','0');
    }
    else if(sysmessage.orgSourceType=='8') //add contact
    {
        $("#auditTaskTypes").combobox('setValue','4');
    }
    else if(sysmessage.orgSourceType=='10') //add order
    {
        $("#auditTaskTypes").combobox('setValue','3');
    }

    //主管不需要
    //$("#crUser4Audit").val(sysmessage.receiverId);
    $("#btn_auditTask").click();
}

