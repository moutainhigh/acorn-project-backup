$(function(){
	$('#smsList').datagrid({
		title:'',
		iconCls:'icon-save',
		width:'100%',
		height:400,
		nowrap: false,
		striped: true,
		collapsible:true,
		fitColumns:true,
		scrollbarSize:0,
		url:'smsList',
		queryParams:{
			receiveChannel : $('#receiveChannel').val(),
			startTime : $("#startTime").datetimebox('getValue'),
			endTime : $("#endTime").datetimebox('getValue'),
			mobile : $("#mobile").val(),
			smsChildId:$("#smsChildId").val()
		},
		remoteSort: false,
		fitColumns:true,
		toolbar:[{
			text:'添加黑名单',
			iconCls:'icon-add',
			handler:function(){
				var ids = "";
				var rows = $('#smsList').datagrid('getSelections');
				if (rows.length>0) {
					for(var i=0;i<rows.length;i++){
						ids = ids+rows[i].id+",";
					}
				}else {
					alerts("请选择要添加的数据");
					return false;
				}				
				$.post("insertBlack", {
					"ids" : ids
				}, function(data) {
					alerts('加入黑名单成功');
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
							field : 'mobile',
							title : '客户电话',
							rowspan:2,
							width : 80
						},
						{
							field : 'contactid',
							title : '客户编码',
							rowspan:2,
							width : 80
						},{
							field : 'receiveContent',
							title : '客户回复内容',
							rowspan:2,
							width : 80
						},{
							field : 'receiveChannel',
							title : '短信通道号',
							rowspan:2,
							width : 80
						},{
							field : 'smsChildId',
							title : '短信子码',
							rowspan:2,
							width : 80
						},{
							field : 'receiveTimes',
							title : '短信回复时间',
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
		if ($('#startTime').datetimebox('getValue')>$("#endTime").datetimebox('getValue')) {
			alerts('起始时间不能晚于结束时间');
			return false;
		}
		$('#smsList').datagrid('load', {
			receiveChannel : $('#receiveChannel').val(),
			startTime : $("#startTime").datetimebox('getValue'),
			endTime : $("#endTime").datetimebox('getValue'),
			mobile : $("#mobile").val(),
			smsChildId:$("#smsChildId").val()
		});
	});	
});



