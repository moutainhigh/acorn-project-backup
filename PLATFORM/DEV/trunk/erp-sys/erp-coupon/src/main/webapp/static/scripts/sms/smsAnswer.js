$(function(){
	$('#smsList').datagrid({
		title:'短息回复列表',
		iconCls:'icon-save',
		width:700,
		height:350,
		nowrap: false,
		striped: true,
		collapsible:true,
		fit:true,
		url:'smsList',
		queryParams:{
			smsChanelNum : $('#smsChanelNum').val(),
			startDate : $("#startDate").val(),
			endDate : $("#endDate").val(),
			userGroup : $("#userGroup").val(),
			smsName:$("#smsName").val(),
			sendUserNum:$("#sendUserNum").val()
		},
		remoteSort: false,
		fitColumns:true,
		toolbar:[{
			text:'添加黑名单',
			iconCls:'icon-add',
			handler:function(){
				var ids = "";
				var rows = $('#smsList').datagrid('getSelections');
				for(var i=0;i<rows.length;i++){
					ids = ids+rows[i].id+",";
				}
				$.post("insertBlack", {
					"ids" : ids
				}, function(data) {
					alert('加入黑名单成功');
					$('#smsList').datagrid('reload');
				});
			}
		}],		
		columns : [ [
		             	{field:'ck',checkbox:true},
						{
							field : 'id',
							title : '短信编号',
							rowspan:2,
							width : 80
						},{
							field : 'userPhoneNum',
							title : '用户电话',
							rowspan:2,
							width : 80
						},{
							field : 'userAnswerContent',
							title : '用户回复内容',
							rowspan:2,
							width : 80
						},{
							field : 'smsChanelNum',
							title : '短信通道号',
							rowspan:2,
							width : 80
						},{
							field : 'userGroup',
							title : '用户组',
							rowspan:2,
							width : 80
						},{
							field : 'smsName',
							title : '短信名称',
							rowspan:2,
							width : 80
						},{
							field : 'sendUserNum',
							title : '客服工号',
							rowspan:2,
							width : 80
						}
						] ],
		pagination:true
	});
	var p = $('#smsList').datagrid('getPager');
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
	$("#querySmsAnswerBtn").click(function() {
		if ($('#startDate').datetimebox('getValue')>$("#endDate").datetimebox('getValue')) {
			alert('起始时间不能晚于结束时间');
			return false;
		}
		$('#smsList').datagrid('reload', {
			smsChanelNum : $('#smsChanelNum').val(),
			startDate :$('#startDate').datetimebox('getValue'),
			endDate : $("#endDate").datetimebox('getValue'),
			userGroup : $("#userGroup").val(),
			smsName:$("#smsName").val(),
			sendUserNum:$("#sendUserNum").val()
		});
	});	
});



