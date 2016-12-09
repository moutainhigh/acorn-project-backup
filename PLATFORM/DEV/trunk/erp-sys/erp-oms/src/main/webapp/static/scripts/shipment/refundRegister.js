$(function() {
	$('#dataTable').datagrid({
		title : '',
		iconCls : 'icon-edit',
		width : 700,
		height : 400,
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,
		fit : true,
		remoteSort : false,
		singleSelect : true,
		pagination : false,
		rownumbers : true,
		idField : 'id',
		url : '/shipment/queryRefundList',
		columns : [ [
		{
			field : 'itemId',
			title : '产品编码',
		}, 
		{
			field : 'itemDesc',
			title : '产品名称',
			width : 200
		}, 
		{
			field : 'agentQty',
			title : '拒收数量'
		}, 
		{
			field : 'unitPrice',
			title : '单价'
		}, 
		{
			field : 'warehouseQty',
			title : '实际退货数量',
			width: 100,
			editor:{
				type:'numberbox',
				options:{
					required:true
				}
			}
		}
		] ],
		
		/*frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],*/
		
		onLoadSuccess : function(_data) {
			if(undefined != _data && null!=_data && undefined!=_data.shipmentRefund){
				$('#shipmentRefund').val(JSON.stringify(_data.shipmentRefund));
				$('#rs_shipmentId').html(_data.shipmentRefund.shipmentId);
			}else{
				$('#rs_shipmentId').html('');
			}
		},
		onClickCell : function(rowIndex, rowData) {
			$(this).datagrid('beginEdit', rowIndex);
		},
	});
	
	var p = $('#dataTable').datagrid('getPager');
	$(p).pagination({
		pageSize : 10,
		pageList : [ 5, 10, 15 ],
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function() {
		}
	});
	
	function getSelected(){
		var selected = $('#dataTable').datagrid('getSelected');
		if (selected){
			return selected;
		}
	}
	
    $('#entity').combobox({
    	onSelect : function(rec){
    		refresh(rec.value);
    	}
	});
    
    function refresh(entityId){
		var _val = entityId;
		if(null==_val || ""==_val){
			//清空操作
			$('#allCheckCount').val(0);
			$('#selfCheckCount').val(0);
			return;
		}
		
		$.ajax({
			url: '/shipment/queryRegisterCount',
			type: 'POST',
			data:{'entityId':_val},
			success:function(rs){
				$('#allCheckCount').html(rs.allCheckCount);
				$('#selfCheckCount').html(rs.selfCheckCount);
				var srList = rs.srList;
				
				var history = document.getElementById('history');
				history.options.length=0;
				if(srList.length>0){	
					for(var i=0; i<srList.length; i++){
						var _obj = srList[i];
						var _opt = document.createElement("OPTION");
						_opt.value = _obj.shipmentId;
						_opt.text = _obj.mailId + '   ' + _obj.shipmentId + '   ' + _obj.hisLabel;
						history.options.add(_opt);
					}
					
					$('#dataTable').datagrid('reload');
				}else{
					$('#mailId').val('');
					$('#shipmentId').val('');
					$('#rs_shipmentId').html('');
					$('#rejectCode').val('');
					$('#dataTable').datagrid('loadData',{total:0,rows:[]});
					$('#shipmentRefund').val(null);
				}
				
			},
			error:function(msg){
				$.messager.alert("提示", "会话超时或网络错误");
			}
		});   	
    }

	// 查询按钮
	$("#queryBtn").click(function() {
		$('#dataTable').attr('url', '/shipment/queryRefundList');
		$('#dataTable').datagrid('reload', {
			'mailId' : $('#mailId').val(),
			'shipmentId' : $("#shipmentId").val(),
			'entityId' : $("#entity").combobox('getValue')
		});
	});
	
	//清空按钮
	$('#clearBtn').click(function(){
		$('#mailId').val('');
		$("#shipmentId").val('');
		$('#shipmentRefund').val(null);
	});

	//新增按钮
	$("#saveBtn").click(function() {
		$('#dataTable').datagrid('acceptChanges');
	
		var rejectCode = $('#rejectCode').val();
		var shipmentRefund = $('#shipmentRefund').val();
		if(null==shipmentRefund || ''==shipmentRefund){
			$.messager.alert("提示", "请选中一条记录");
			return;
		}
		if(null==rejectCode || ''==rejectCode){
			$.messager.alert('提示', '请选择拒收原因');
			return;
		}
		
		var _data = $('#dataTable').datagrid('getRows');
		for(var i=0; i<_data.length; i++){
			if(null==_data[i].warehouseQty || ''==_data[i].warehouseQty){
				//$.messager.alert('提示', '请输入实际退货数量');
				//return;
			}else{
				if(_data[i].warehouseQty > _data[i].agentQty){
					$.messager.alert('提示', '实退数量不能大于拒收数量');
					return;
				}
			}
		}
		
		$.ajax({
			type:'POST',
			url:'/shipment/saveRefundList',
			data:{'jsonList': JSON.stringify(_data), 'rejectCode':rejectCode, 'shipmentRefund':shipmentRefund},
			success:function(rs){
				if(eval(rs.success)){
					$('#dataTable').datagrid('acceptChanges');
//					$('#entity').trigger('onSelect');
//					$('#entity').combobox('onSelect');
					refresh($("#entity").combobox('getValue'));
					$('#dataTable').datagrid('reload');
				}else{
					$.messager.alert("提示", rs.message);
				}
			},
			error:function(msg){
				$.messager.alert("提示", "会话超时或网络错误");
			}
		});
			 
	});

	//删除退包
	$('#deleteBtn').click(function(){
		var shipmentRefund = $('#shipmentRefund').val();
		if(null==shipmentRefund || ''==shipmentRefund){
			$.messager.alert("提示", "请选中一条退包记录");
			return;
		}
		var _shipmentId = $('#rs_shipmentId').html();
		
		$.messager.confirm('删除退包确认', '确定要删除运单号为['+_shipmentId+']的退包数据？', function(rs){
			if(!rs){
				return;
			}
			$.ajax({
				type:'POST',
				url:'/shipment/deleteRefund',
				data:{'shipmentRefund':shipmentRefund},
				success:function(rs){
					if(eval(rs.success)){
						$("#queryBtn").trigger('click');
//						$('#entity').trigger('onSelect');
//						$('#entity').combobox('onSelect');
						refresh($("#entity").combobox('getValue'));
					}else{
						$.messager.alert("提示", rs.message);
					}
				},
				error:function(msg){
					$.messager.alert("提示", "会话超时或网络错误");
				}
			});
		});
	});
	
	$('#history').click(function(){
		var _val = $(this).val();
		$('#mailId').val('');
		$('#shipmentId').val(_val);
		$('#queryBtn').trigger('click');
	});
});
