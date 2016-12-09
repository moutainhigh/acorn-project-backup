function _handler(){
    $('#table_campaignTask,#table_auditTask,#table_myQuestionnaire').datagrid('resize',{width:'100%'});
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
$(function(){
	//营销任务
	$('#table_campaignTask').datagrid({
		width : '100%',
		fitColumns : true,
        scrollbarSize:-1,
//        fit:true,
		url : '/task/queryCampaignTask',
		queryParams : {
			'startDate' : new Date(new Date()-90*86400000).format('yyyy-MM-dd'),
			'endDate' : new Date().format('yyyy-MM-dd'),
			'userID' : $('#userID').val()
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
            width:50,
            align:'center',
			title : '营销编号'
		},
		{
			field : 'caName',
            width:100,
			title : '营销名称'
		},
		{
			field : 'taskType',
            width:50,
            align:'center',
			title : '任务类型'
		},
		{
			field : 'sourceTypeDsc',
            align:'center',
            width:50,
			title : '任务来源'
		}, 
		{
			field : 'conid',
			title : '客户编号',
            width:80,
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
            width:50,
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
            width:40,
			title : '状态'
		},
		{
			field : 'remark',
            width:40,
			title : '备注'
		},
		{
			field : 'ops',
			title : '操作',
			width:60,
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
		rowStyler: function(index,row){
			if (row.exigency == '0') {
				return 'background-color:#FFC1C1';
			} else if (row.exigency == '1') {
				return 'background-color:#fcffa6';
			}
		}
	});

	var campaignTaskPortalPager = $('#table_campaignTask').datagrid('getPager');
	$(campaignTaskPortalPager).pagination({
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

	/*=========================================================================*/
	
	//审批任务
	$('#table_auditTask').datagrid({
		width : '100%',
		fitcolumns:true,
        scrollbarSize:-1,
		url : '/task/queryAuditTask',
		queryParams : {
			'startDate' : new Date(new Date()-90*86400000).format('yyyy-MM-dd'),
			'endDate' : new Date().format('yyyy-MM-dd'),
			'department' : $("#department").val(),
			'taskStatuses' : '0,5'
		},
		columns : [ [
		{
			field : 'batchID',
			title : '修改批次',
			width:80
		}, 
		{
			field : 'busiType',
			title : '类型',
			width:80
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
			width:80
		}, 
		{
			field : 'orderID',
			title : '订单编号',
			width:80
		},
		{
			field : 'status',
			title : '状态',
			width:80
		}, 
		{
			field : 'crUser',
			title : '申请座席',
			width:80
		},
		{
			field : 'orCrUsrID',
			title : '订单创建座席',
			width:80
		},
		{
			field : 'orCrUsrGrpName',
			title : '创建座席组',
			width:80
		},
		{
			field : 'mdUser',
			title : '操作',
			width:80,
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
				url = '<a  class="dispose" title="处理" id="+value+" onclick="processAuditTask(\''+row.batchID+'\',\''+row.busiType+'\',\''+row.crUser+'\',\''+contentId+'\',\'0\')"></a>';
//				url = '<input type="button" id=\'btn_'+row.batchID+'\' value=\'处理\' onclick="window.parent.addTab(\''+id.valueOf()+'\', \'处理审批任务\', \'true\', \''+url+'\')"/>';
				return url;
			}
		}
		] ],
		remoteSort : false,
		singleSelect : true,
		pagination : true,
		rownumbers : false
	});
	
	var auditTaskPortalPager = $('#table_auditTask').datagrid('getPager');
	$(auditTaskPortalPager).pagination({
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

	/*===========================问题单============================*/
	//营销任务
	$('#table_myQuestionnaire').datagrid({
		width : '100%',
		height : 410,
		fitColumns : true,
		remoteSort : false,
		singleSelect : true,
		pagination : true,
        scrollbarSize:-1,
		url : ctx+'/problematicOrder/query',
		queryParams : {
			'beginCrDate' : new Date(new Date()-90*86400000).format('yyyy-MM-dd'),
			'endCrDate' : new Date().format('yyyy-MM-dd'),
			'crusr' : $('#userID').val()
		},
		columns : [ [ 
		{
			field : 'orderid',
			title : '订单编号',
			align : 'center',
			width : 100
		}, {
			field : 'crdate',
			title : '订单生成时间',
			align : 'center',
			width : 150,
			formatter : dateFormatter
		}, {
			field : 'statusDsc',
			title : '订单状态',
			align : 'center',
			width : 100
		}, {
			field : 'totalprice',
			align : 'center',
			title : '订单金额',
			align:'right',
			width : 100,
			formatter:function(value){
                return formatPrice(value);
            },
		}, {
			field : 'crusr',
			title : '坐席工号',
			align : 'center',
			width : 100
		}, {
			field : 'contactId',
			align : 'center',
			title : '客户编号',
			width : 100,
			formatter: function(val, row){
				var id = val;
				
				var url;
				var paras='';
				if(row.campaignId!=null && row.campaignId!='') {
					paras+='?campaignId='+row.campaignId;
				}
				url = ctx+'/contact/1/'+row.contactId;
				url+=paras;
				url = '<a class="link" href="javascript:void(0)" id=\''+id+'\' onclick="window.parent.addTab(\''+id.valueOf()+'\', \'我的客户\', \'false\', \''+url+'\')">'+val+'</a>';
				return url;
			}
		}, {
			field : 'contactName',
			title : '客户姓名',
			align : 'center',
			width : 100
		}, {
			field : 'sentDate',
			title : '出库时间',
			align : 'center',
			width : 100,
			formatter : dateFormatter
		}, {
			field : 'problemDate',
			title : '问题时间',
			align : 'center',
			width : 100,
			formatter : dateFormatter
		}, {
			field : 'problemUser',
			title : '提交座席',
			align : 'center',
			width : 100
		},{
			field : 'problemTypeDsc',
			title : '问题类型',
			align : 'center',
			width : 100
		},{
			field : 'problemDsc',
			title : '问题描述',
			align : 'center',
			width : 100
		},{
			field : 'problemStatusDsc',
			title : '问题状态',
			align : 'center',
			width : 100
		},
		{
			field : 'mdUser',
			title : '操作',
			width:60,
			align:'center',
			formatter: function(value,row){
				url = '<a class="dispose" title="处理" id=\''+value+'\' onclick="replyProblematicOrder(\''+row.problemId+'\',\''+row.orderid+'\')"></a>';
				return url;
			}
		}
		] ]
	});

	var p = $('#table_myQuestionnaire').datagrid('getPager');
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
	
	/*====================================================*/
	
	function dateFormatter(val, row){
		if(null!=val){
			return new Date(val).format('yyyy-MM-dd hh:mm:ss');
		}
	}
	
	// 每10分钟刷新一次
	setInterval("reloadCampaignTask()", 1000 * 60 * 10);
});

function reloadCampaignTask() {
	$("#table_campaignTask").datagrid("reload");
}



function replyProblematicOrder(problemId, orderId) {
	$('#input_problematic_reply_oid').val(orderId);
	$('#div_problematic_reply').show();
	$('#div_problematic_reply').dialog({
		title:'问题单回复',
	    width: 400,
	    top: 400,
//	    height: 200,
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
				replyContent = $('#textarea_problematic_reply_reason').val();
				$.ajax({
					url: ctx+"/problematicOrder/replyProblematicOrder?replyContent="+replyContent+"&problemId="+problemId,
					 success:function(data) {
						 	$('#div_problematic_reply').hide();
						 	$('#textarea_problematic_reply_reason').val("");
							 $('#div_problematic_reply').dialog('close');
						},
					     error : function() {
					          alert("异常！");
					          $('#div_problematic_reply').hide();
					          $('#textarea_problematic_reply_reason').val("");
					          $('#div_problematic_reply').dialog('close');
					     }
					});
			}
			}, {
			text: '取消',
            plain:true,
			handler: function() {
				$('#div_problematic_reply').dialog('close');
				$('#textarea_problematic_reply_reason').val("");
				$('#div_problematic_reply').hide();
			}
			}]
	}
	);
	$('#div_problematic_reply').dialog('open');
}

function formatPrice(price)
{
    if(price==null||price=='')
        return '';
    var prc=price;
    return prc.toFixed(2);

}
