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
		url : '/carrier/queryPageList',
		queryParams : {
			'id' : $('#id').val(),
			'name' : $("#name").val(),
			'entityId' : $("#companyId").val(),
			'orderChannel.id' : $("#channelId").val(),
			'areaGroup.id' : $("#areaGroupId").val(),
			'warehouseId' : $("#warehouseId").val(),
			'priority' : $("#priority").val()
		},

		columns : [ [
		{
			field : 'id',
			title : '规则编号',
		}, 
		{
			field : 'name',
			title : '匹配规则名称',
			width : 150
		}, 
		{
			field : 'isActive',
			title : '当前状态',
			formatter : function(value, row, index) {
				if (row.active) {
					return '启用';
				} else if (!row.active) {
					return '停用';
				}
				return 'NONE';
			}
		}, 
		{
			field : 'orderChannel.id',
			title : '渠道编号',
			formatter : function(value, row, index) {
				return row.orderChannel.id;
			}
		}, 
		{
			field : 'orderChannel.channelName',
			title : '渠道名称',
			width : 80,
			formatter : function(value, row, index) {
				return row.orderChannel.channelName;
			}
		}, 
		{
			field : 'priority',
			title : '优先级'
		}, 
		{
			field : 'areaGroup.id',
			title : '地址组编号',
			formatter : function(value, row, index) {
				if(null!=row.areaGroup){
					return row.areaGroup.id;
				}
				return '';
			}
		}, 
		{
			field : 'areaGroup.name',
			title : '地址组名称',
			width : 150,
			formatter : function(value, row, index) {
				if(null!=row.areaGroup){
					return row.areaGroup.name;
				}
				return '';
			}
		}, 
		{
			field : 'warehouseName',
			title : '出库仓库',
			width : 100
		}, 
		{
			field : 'prodCode',
			title : '商品编码',
			width : 50,
		},{
			field : 'minAmount',
			title : '最小金额',
			width : 50,
		},
		{
			field : 'maxAmount',
			title : '最大金额',
			width : 50,
		},
		{
			field : 'entityId',
			title : '承运商'
		}, 
		{
			field : 'mdUser',
			title : '最后编辑人'
		}, 
		{
			field : 'mdDT',
			title : '最后编辑时间',
			width : 130
		} 
		] ],
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		remoteSort : false,
		singleSelect : true,
		pagination : true,
		rownumbers : true
	});
	
	function getSelected(){
		var selected = $('#dataTable').datagrid('getSelected');
		if (selected){
			return selected;
		}
	}

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

	// 查询按钮
	$("#queryBtn").click(function() {
		
		$('#dataTable').datagrid('reload', {
			'id' : $('#id').val(),
			'name' : $("#name").val(),
			'entityId' : $("#companyId").val(),
			'orderChannel.id' : $("#channelId").val(),
			'areaGroup.id' : $("#areaGroupId").val(),
			'warehouseId' : $("#warehouseId").val(),
			'priority' : $("#priority").val(),
			'prodCode' : $("#prodCode").val(),
			'beginMinAmount' : $("#beginMinAmount").val(),
			'endMinAmount' : $("#endMinAmount").val(),
			'beginMaxAmount' : $("#beginMaxAmount").val(),
			'endMaxAmount' : $("#endMaxAmount").val()
		});
	});
	
	//清空按钮
	$('#clearBtn').click(function(){
		$('#id').numberbox('setValue', null);
		$("#name").val('');
		$("#companyId").numberbox('setValue', null);
		$("#channelId").val('');
		$("#areaGroupId").numberbox('setValue', null);
		$("#warehouseId").val('');
		$("#priority").numberbox('setValue', null);
		$("#prodCode").numberbox('setValue', null);
		$("#beginMinAmount").numberbox('setValue', null);
		$("#endMinAmount").numberbox('setValue', null);
		$("#beginMaxAmount").numberbox('setValue', null);
		$("#endMaxAmount").numberbox('setValue', null);
	});

	// 启用按钮
	$("#enableBtn").click(function() {
		var row = getSelected();
		if(undefined !=row && null!=row) {
			$.messager.confirm('确认', '确定启用规则编号为['+row.id+']的记录?', function(result) {
				if(result){
					
					$.ajax({
						type: 'POST',
						url: 'changeStatus',
						data: {'id':row.id, 'active': true},
						success:function(data){
							if(eval(data.success)){
								$('#dataTable').datagrid('reload');
							}else{
//								$.messager.show({
//									title : 'Error',
//									msg : data.message
//								});
								$.messager.alert('错误',data.message,'error');
							}
						},
						error:function(msg){
//							$.messager.show({
//								title : 'Error',
//								msg : msg
//							});
							$.messager.alert('错误',msg,'error');
						}
					});
				}
			});
		}
	});

	//禁用按钮
	$("#disableBtn").click(function() {
		var row = getSelected();
		if(undefined !=row && null!=row) {
			$.messager.confirm('确认', '确定禁用规则编号为['+row.id+']的记录?', function(result) {
				if(result){
					
					$.ajax({
						type: 'POST',
						url: 'changeStatus',
						data: {'id':row.id, 'active': false},
						success:function(data){
							if(eval(data.success)){
								$('#dataTable').datagrid('reload');
							}else{
//								$.messager.show({
//									title : 'Error',
//									msg : data.message
//								});
								$.messager.alert('错误',data.message,'error');
							}
						},
						error:function(msg){
//							$.messager.show({
//								title : 'Error',
//								msg : msg
//							});
							$.messager.alert('错误',msg,'error');
						}
					});
				}
			});
		}
	});

	//新增按钮
	$("#addBtn").click(function() {
		
		$('#d_id').val(null);
		$('#d_name').val(null);
		$('#d_orderChannel').val(null);
		$('#d_areaGroup').val(null);
		$('#d_entityId').numberbox('setValue', null);
		$('#d_warehouseId').val(null);
		$('#d_priority').numberbox('setValue', null);
		$('#d_prodCode').val(null);
		$('#d_minAmount').val(null);
		$('#d_maxAmount').val(null);
		
		$('#persistDialog').window({
			width : 600,
			height : 400,
			iconCls : '',
			//top : ($(window).height() - 300) * 0.5,
			left : ($(window).width() - 500) * 0.5,
			shadow : true,
			modal : true,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			onBeforeClose: function(){
				document.forms['oarForm'].reset();
				$('#message').html('');
			}
		}).window('open');

		$('#persistDialog').window('setTitle', '分配规则新增');
	});
	
	$('#editBtn').click(function(){
		
		var row = getSelected();
		if(undefined==row || null==row || ''==row){
			return;
		}
		
		$('#d_id').val(row.id);
		$('#d_name').val(row.name);
		$('#d_orderChannel').val(row.orderChannel.id);
		if(null!=row.areaGroup){
			$('#d_areaGroup').val(row.areaGroup.id);
		}
		$('#d_entityId').numberbox('setValue', row.entityId);
		$('#d_warehouseId').val(row.warehouseId);
		$('#d_priority').numberbox('setValue', row.priority);
		$('#d_prodCode').val(row.prodCode);
		$('#d_amount').val(row.amount);
		$('#d_minAmount').val(row.minAmount);
		$('#d_maxAmount').val(row.maxAmount);
		
		$('#persistDialog').window({
			width : 600,
			height : 400,
			iconCls : '',
			//top : ($(window).height() - 300) * 0.5,
			left : ($(window).width() - 500) * 0.5,
			shadow : true,
			modal : true,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			onBeforeClose: function(){
				document.forms['oarForm'].reset();
				$('#message').html('');
			}
		}).window('open');

		$('#persistDialog').window('setTitle', '分配规则修复');
	});
	
	
	//保存
	$("#saveBtn").click(function() {
		var _minAmount = $('#d_minAmount').val();
		var _maxAmount = $('#d_maxAmount').val();
		$('#d_minAmount').val(formatFloat(_minAmount, 2));
		$('#d_maxAmount').val(formatFloat(_maxAmount, 2));
		
		
		var _data = $('#oarForm').serialize();
		var _validate = $('#oarForm').form('validate');
		if(!_validate){
			return false;
		}
		var logicCehck = checkRule();
		if(!logicCehck){
			//$('#message').html('规则匹配错误');
			return false;
		}
		$.ajax({
			type: 'POST',
			url: 'persist',
			data: _data,
			success:function(data){
				if(eval(data.success)){
					$('#persistDialog').window('close');
					$('#dataTable').datagrid('reload');
				}else{
					$('#message').html(data.message);
				}
			},
			error:function(msg){
				$('#message').html(msg);
			}
		});
	});
	
	//取消新增
	$("#closeBtn").click(function() {
		document.forms['oarForm'].reset();
		$('#message').html('');
		$('#persistDialog').dialog('close');
	});
	
	/**
	 * 保存时规则校验
	 * 返回是否有效
	 * true: 无效
	 * false:
	 */
	function checkRule(){
		var _areaGroupId = $('#d_areaGroup').val();
		var _prodCode = $('#d_prodCode').val();
		var _minAmount = $('#d_minAmount').val();
		var _maxAmount = $('#d_maxAmount').val();
		
		//如果不存在地址组，则要对商品编码与金额校验
		if(null==_areaGroupId || ''==_areaGroupId){
			if(null != _prodCode && ''!=_prodCode){
				return true;
			}
			
			if(null != _minAmount && ''!=_minAmount && null!=_maxAmount && ''!=_maxAmount){
				if(_minAmount<=_maxAmount){
					return true;
				}else{
					$('#message').html('最大金额不能小于最小金额');
					return false;
				}
			}
			
			$('#message').html('匹配规则有误，请输入[地址组编号]或[商品编码]或[金额范围]');
			return false;
		//如果存在地址组，则不对商品编码和金额校验	
		}else{
			if(isNaN(_areaGroupId)){
				$('#message').html('无效的地址组编号');
				return false;
			}
			return true;
		}
	}
	
	//Number 格式化
	function formatFloat(val, pos){
		if(null==val || ''==val || isNaN(val)){
			return null;
		}
		return Math.round(val*Math.pow(10, pos)) / Math.pow(10, pos);
	}

});
