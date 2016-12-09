<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<script type="text/javascript">

$(function() {
    
	$('#quartzTable')
			.datagrid(
					{
						title : '',
						iconCls : 'icon-edit',
						width : '100%',
						height : 440,
						nowrap : false,
						striped : true,
						border : true,
						fitColumns:true,
						collapsible : false,
						scrollbarSize:0,
						url : '${ctx}/system/quartzList',
						queryParams:{
						},
						columns : [ [
								{
									field : 'triggerName',
									title : '任务名称',
									width : 100
								},
								{
									field : 'triggerGroup',
									title : '任务组别',
									width : 100,
									formatter : function(val, rec) {
										if (rec.triggerGroup == 'JOB_GROUP_CUSTOMER_GROUP') {
											return "客户群定时任务";
										} 
										else if (rec.triggerGroup == 'JOB_GROUP_SMS_SEND') {
											return "短信发送定时任务";
										} 
										else if (rec.triggerGroup == 'JOB_GROUP_CAMPAIGN') {
											return "营销计划定时任务";
										} 
										else {
											return "默认系统定时任务";
										}
									}
								},
								{
									field : 'nextFireTime',
									title : '下次执行时间',
									width : 150
								},
								{
									field : 'prevFireTime',
									title : '上次执行时间',
									width : 100
								},{
									field : 'priority',
									title : '优先级',
									width : 60				
								},{
									field : 'triggerState',
									title : '运行状态',
									width : 160
								},{
									field : 'startTime',
									title : '开始时间',
									width : 160
								},{
									field : 'endTime',
									title : '结束时间',
									width : 160
								}
								] ],

						remoteSort : false,
						pagination : true,
						rownumbers : true
					});

	
	var p = $('#quartzTable').datagrid('getPager');
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
	$("#queryOrderBtn").click(function() {
		$('#quartzTable').datagrid('load', {
			groupCode : $('#groupCode').val(),
			batchCode : $("#batchCode").val()
		});
	});

	
	
	//清空
	$('#clearBtn').click(function(){
		$("#condition :input").each(function(){
		      ($(this).val(''));     
		     });  	
	});
	
});



</script>
</head>
<body>
	
	<div style="margin:0 10px 0">

		<table id="quartzTable" cellspacing="0" cellpadding="0" data-options=''>

		</table>
	</div>
	

</body>
</html>
