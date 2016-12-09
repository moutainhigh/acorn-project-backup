$(function() {
	
	$(".datebox :text").attr("readonly","readonly");
	
	$('#d_companyContractId').combobox({
        onSelect:function(data){
        	console.log('data: ', data);
            $('#d_companyAccountCode').combobox({
            	//data : data.value,
            	url: '/company/queryAccountListByContract?id='+ data.value,
        		valueField : 'accountCode',
        		textField : 'accountCode'
            }).combobox('clear');
        }    
	});
	 
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
		singleSelect : true,
		pagination : true,
		rownumbers : true,
		url : '/company/queryPaymentList',
		columns : [ [ {
			field : 'paymentCode',
			title : '水单号',
			width : 150
		}, {
			field : 'itemDesc',
			title : '承运商',
			width : 150,
			formatter : function(value, row, index){
				return row.companyContract.name;
			}
		}, {
			field : 'amount',
			title : '水单金额'
		}, {
			field : 'paymentDate',
			title : '打款时间',
			width : 150
		}, {
			field : 'isSettled',
			title : '状态',
			width: 100,
			formatter : function(value, row, index){
				if(row.isSettled==0){
					return '未审核';
				}else if(row.isSettled==1){
					return '核销中';
				}else if(row.isSettled==2){
					return '全部核销';
				}
			}
		}, {
			field : 'companyAccountCode',
			title : '打款账号',
			width: 200
		}, {
			field : 'cpompanyAccountName',
			title : '户名',
			width: 200
		}, {
			field : 'createUser',
			title : '录入人员',
			width: 100
		}, {
			field : 'createDate',
			title : '录入时间',
			width : 150
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
			'paymentCode' : $('#paymentCode').val(),
			'beginPaymentDate' : $('#beginPaymentDate').datebox('getValue'),
			'endPaymentDate' : $("#endPaymentDate").datebox('getValue'),
			'companyContract.id' : $("#companyContractId").val(),
			'isSettled' : $('#isSettled').val(),
			'initLoad' : true
		});
	});
	
	
	//清空按钮
	$('#clearBtn').click(function(){
		$('#paymentCode').val('');
		$('#beginPaymentDate').datebox('setValue', null);
		$("#endPaymentDate").datebox('setValue', null);
		$("#companyContractId").val('');
		$('#isSettled').val('');
	});

	//新增按钮
	$("#addBtn").click(function() {
		
		$('#d_id').val(null);
		$('#d_paymentCode').val(null);
		$('#d_companyContractId').val(null);
		$('#d_amount').numberbox('setValue', null);
		$('#d_companyAccountCode').val(null);
		$('#d_cpompanyAccountName').val(null);
		$('#d_paymentDate').datebox('setValue', null);
		$('#d_settledAmount').val(null);
		$('#d_isSettled').val(null);
		$('#d_createUser').val(null);
		$('#d_createDate').val(null);
		
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
				document.forms['dataForm'].reset();
				$('#message').html('');
			}
		}).window('open');

		$('#persistDialog').window('setTitle', '核销付款单录入');
	});
	
	
	//编辑
	$('#editBtn').click(function(){
		var row = getSelected();
		if(undefined==row || null==row || ''==row){
			return;
		}
		
		if(row.isSettled==1){
			$.messager.alert('确认', '当前记录状态为核销中，不能编辑');
			return;
		}else if(row.isSettled==2){
			$.messager.alert('确认', '当前记录状态为全部核销，不能编辑');
			return;
		}
		
		var contractId = row.companyContract.id;
		
		$('#d_id').val(row.id);
		$('#d_paymentCode').val(row.paymentCode);
		$('#d_amount').numberbox('setValue', row.amount);
		$('#d_cpompanyAccountName').val(row.cpompanyAccountName);
		$('#d_paymentDate').datebox('setValue', row.paymentDate);
		$('#d_settledAmount').val(row.settledAmount);
		$('#d_isSettled').val(row.isSettled);
		$('#d_createUser').val(row.createUser);
		$('#d_createDate').val(row.createDate);
		//触发承运商变更事件，级联打款账号
		$('#d_companyContractId').combobox('setValue', contractId);
		
		$('#d_companyAccountCode').combobox({
			url: '/company/queryAccountListByContract?id='+ contractId,
			valueField : 'accountCode',
			textField : 'accountCode'
		}).combobox('clear');
		$('#d_companyAccountCode').combobox('setValue', row.companyAccountCode);
		
		
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
				document.forms['dataForm'].reset();
				$('#message').html('');
			}
		}).window('open');

		$('#persistDialog').window('setTitle', '核销付款单修改');
	});
	
	
	//保存
	$("#saveBtn").click(function() {
		var _data = $('#dataForm').serialize();
		var _validate = $('#dataForm').form('validate');
		if(!_validate){
			return false;
		}
		
		$.ajax({
			type: 'POST',
			url: 'persist',
			data: _data,
			success:function(rs){
				if(eval(rs.success)){
					$('#persistDialog').window('close');
					$('#dataTable').datagrid('reload');
				}else{
					$('#message').html(rs.message);
				}
			},
			error:function(msg){
				$('#message').html(msg);
			}
		});
	});
	
	
	//删除
	$('#deleteBtn').click(function(){
		var row = getSelected();
		if(undefined==row || null==row || ''==row){
			return;
		}
		
		if(row.isSettled==1){
			$.messager.alert('确认', '当前记录状态为核销中，不能删除');
			return;
		}else if(row.isSettled==2){
			$.messager.alert('确认', '当前记录状态为全部核销，不能删除');
			return;
		}
		
		$.messager.confirm('删除确认', '确认删除选中的记录', function(r){
			if(r){
				$.ajax({
					type: 'POST',
					url: 'delete',
					data: {'id': row.id},
					success:function(data){
						if(eval(data.success)){
							$('#persistDialog').window('close');
							$('#dataTable').datagrid('reload');
						}else{
							$.messager.alert(data.message);
						}
					},
					error:function(msg){
						$.messager.alter(msg);
					}
				});
			}
		});
	});
	
	$('#exportBtn').click(function(){
		var _data = {
				'paymentCode' : $('#paymentCode').val(),
				'beginPaymentDate' : $('#beginPaymentDate').val(),
				'endPaymentDate' : $("#endPaymentDate").val(),
				'companyContract.id' : $("#companyContractId").val(),
				'isSettled' : $('#isSettled').val()
		};
		
		document.location.href = 'export?'+$.param(_data);
	});
	
	
	//取消新增
	$("#closeBtn").click(function() {
		document.forms['dataForm'].reset();
		$('#message').html('');
		$('#persistDialog').dialog('close');
	});
	
	/*
	//级联查询账号列表
	$('#d_companyContractId').change(function(){

		var accountCode = document.getElementById('d_companyAccountCode');
		
		var contractId = $('#d_companyContractId').val();
		if(null==contractId || ''==contractId){
			accountCode.options.length=0;
			return;
		}
		
		$.ajax({
			url: '/company/queryAccountListByContract',
			type: 'POST',
			data:{'id': contractId},
			success:function(rs){
				var srList = rs.accountList;
				
				accountCode.options.length=1;
				if(srList.length>0){	
					for(var i=0; i<srList.length; i++){
						var _obj = srList[i];
						var _opt = document.createElement("OPTION");
						_opt.value = _obj.accountCode;
						_opt.text = _obj.accountName;
						accountCode.options.add(_opt);
					}
				}
			},
			error:function(msg){
				$.messager.alert("提示", "会话超时或网络错误");
			}
		});
	});
	*/
});
