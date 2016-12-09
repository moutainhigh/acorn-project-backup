//function _handler(){
//       $('#mytask_tabs').tabs('resize',{width:'100%'});
//       $('#table_campaignTask,#table_auditTask,#table_jobRelationex').datagrid('resize',{width:'100%'});
//}
$(function(){

	init();
	//营销任务
	$('#table_campaignTask').datagrid({
		width : '100%',
		height : 410,
		fitColumns : true,
//        fit:true,
		url : 'queryUnstartedCampaignTask',
		queryParams : {
			'startDate' : $('#startDate').datebox('getValue'),
			'endDate' : $('#endDate').datebox('getValue'),
			'customerID' : $("#customerID").val(),
			'customerName' : $('#customerName').val(),
			'campaignName' : $('#campaignName').val(),
			'userID' : $('#userID').val(),
			'taskType' : $('#campaignTaskTypes').combobox('getValue'),
			'status' : $('#campaignTaskStatus').combobox('getValue')
		},
		columns : [ [
		{
			field : 'instID',
			title : '任务编号',
		}, 
		{
			field : 'caID',
			title : '营销编号',
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
			field : 'conid',
			title : '客户编号',
            align:'center',
			formatter: function(val, row){
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
		pagination : true
	});

	var p = $('#table_campaignTask').datagrid('getPager');
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
	
	//查询
	$('#btn_campaignTask').click(function(){
        var startTime = getTimeByDateStr($('#startDate').datebox('getValue'));
        var endTime = getTimeByDateStr($('#endDate').datebox('getValue'));
        if(endTime - startTime > 15*86400000) {
        	window.parent.alertWin('系统提示', "查询时间间隔不能超过15天");
        	return;
        }
		$('#table_campaignTask').datagrid('reload',{
			'startDate' : $('#startDate').datebox('getValue'),
			'endDate' : $('#endDate').datebox('getValue'),
			'customerID' : $("#customerID").val(),
			'customerName' : $('#customerName').val(),
			'campaignName' : $('#campaignName').val(),
			'userID' : $('#userID').val(),
			'taskType' : $('#campaignTaskTypes').combobox('getValue'),
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
	
	function dateFormatter(val, row){
		if(null!=val){
			return new Date(val).format('yyyy-MM-dd hh:mm:ss');
		}
	}
	
	function getTimeByDateStr(dateStr){  
	    var year = parseInt(dateStr.substring(0,4));  
	    var month = parseInt(dateStr.substring(5,7),10)-1;  
	    var day = parseInt(dateStr.substring(8,10),10);  
	    return new Date(year, month, day).getTime();  
	}  
	
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
	
});
