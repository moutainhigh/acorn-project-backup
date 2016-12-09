$(function() {
	
	$('#dataTable').datagrid({
		title : '',
		iconCls : 'icon-edit',
		width : '100%',
		height : 410,
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,
		fitColumns:true,
		scrollbarSize:0,
		remoteSort : false,
		singleSelect : false,
		pagination : false,
		rownumbers : true,
		url : '/shipment/queryRefundSendList',
		columns : [ [ {
			field : 'entityName',
			title : '承运商',
			width : 150
		}, {
			field : 'mailId',
			title : '邮件号',
			width : 100
		}, {
			field : 'shipmentId',
			title : '发运单号',
			width:100
		}, {
			field : 'warehouseId',
			title : '原始发货仓库',
			width : 150,
			formatter : function(value, row, index){
				return row.warehouseName;
			}
		}, {
			field : 'rejectName',
			title : '拒收理由',
			width: 200
		}, {
			field : 'isComplete',
			title : '货品完整性',
			width: 200
		} 
		] ],
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ]
	});
	
	var p = $('#dataTable').datagrid('getPager');
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
	
	function getSelected(){
		var selected = $('#dataTable').datagrid('getSelected');
		if (selected){
			return selected;
		}
	}
	

	// 查询按钮
	$("#queryBtn").click(function() {
		$('#dataTable').datagrid('reload', {
			'entityId' : $('#entityId').combobox('getValue'),
			'warehouseId' : $('#warehouseId').val()
		});
	});
	
	
	//清空按钮
	$('#clearBtn').click(function(){
		$('#entityId').val();
		$('#warehouseId').val();
	});

	
	//导出
	$('#exportBtn').click(function(){
		var selectedRows = $('#dataTable').datagrid('getSelections');
		if(null==selectedRows || selectedRows.length==0){
			return;
		}
		var ids = '';
		for(var i=0; i<selectedRows.length; i++){
			ids += selectedRows[i].id + ',';
		}
		
		if(ids.length>1){
			ids = ids.substring(0, ids.length-1);
		}
		
		var url = 'exportRefundSendList?ids=' + ids;
		document.location.href = url;
	});
	
	
	//生成退包入库单
	$('#generateBtn').click(function(){
		var selectedRows = $('#dataTable').datagrid('getSelections');
		if(null==selectedRows || selectedRows.length==0){
			return;
		}
		var ids = '';
		for(var i=0; i<selectedRows.length; i++){
			ids += selectedRows[i].id + ',';
		}
		
		if(ids.length>1){
			ids = ids.substring(0, ids.length-1);
		}
		
		var _data = {
			'ids' : ids,
			'warehouseId' : $('#warehouseId').val(),
			'companyId' : $('#entityId').val()
		};
		
		$.ajax({
			url : 'generateRefundList',
			type : 'POST',
			data : _data,
			success:function(rs){
				if(!eval(rs.success)){
					$.messager.alert('确认', rs.message);
				}else{
					$('#dataTable').datagrid('reload');
				}
			},
			error:function(msg){
				$.messager.alert('确认', '网络超时会会话错误');
			}
		});
	});
});
