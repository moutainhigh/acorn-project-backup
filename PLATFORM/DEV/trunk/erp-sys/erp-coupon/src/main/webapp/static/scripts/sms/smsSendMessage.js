$(function(){
	$('#checkBoxs').click(function () {
		if ($(this).attr("checked")) {
			$('#show').show();
		}else {
			$('#show').hide();
		}
	});
	$('#addSendMessage').window({
        iconCls:'icon-save',
        closed:true,
        resizable:false,
        minimizable:false,
        maximizable:false,
       
	});
	$('#smsList').datagrid({
		title:'短息发送列表',
		iconCls:'icon-save',
		width:900,
		height:800,
		autoRowHeight: false,
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,
		fit : true,
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
			text:'新增',
			iconCls:'icon-add',
			handler:function(){
				$('#addSendMessage').window('setTitle', '新增发送短信');
				$('#addSendMessage').window('open');
			}
		},'-',{
			text:'结束',
			iconCls:'icon-add',
			handler:function(){
				alert('结束成功');
		}}],		
		columns : [ [
						{	
							field : 'id',
							title : '发送批次号',
							rowspan:1,
							width : 80
						},{
							field : 'userPhoneNum',
							title : '发送时间',
							rowspan:1,
							width : 80
						},{
							field : 'userAnswerContent',
							title : '客户群编号',
							rowspan:1,
							width : 80
						},{
							field : 'smsChanelNum',
							title : '客户群名称',
							rowspan:1,
							width : 80
						},{
							field : 'userGroup',
							title : '客户数量',
							rowspan:1,
							width : 80
						},{
							field : 'smsName',
							title : '短信编号',
							rowspan:1,
							width : 80
						},{
							field : 'sendUserNum',
							title : '短信名称',
							rowspan:1,
							width : 80
						},{
							field : 'sendNums',
							title : '发送工号',
							rowspan:1,
							width : 80
						},{
							field : 'sendChanel',
							title : '发送通道',
							rowspan:1,
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



