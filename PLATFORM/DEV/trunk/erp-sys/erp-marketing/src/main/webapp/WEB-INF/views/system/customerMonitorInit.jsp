<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<script type="text/javascript">

$(function() {
    
	$('#customerTable')
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
						url : '${ctx}/system/customerMonitorList',
						queryParams:{
							groupCode : $('#groupCodeId').val(),
							batchCode:$("#batchCode").val()	
						},
						columns : [ [
								{
									field : 'batchCode',
									title : '客户群批次',
									width : 100
								},
								{
									field : 'groupCode',
									title : '客户群编号',
									width : 100
								},
								{
									field : 'groupName',
									title : '名称',
									width : 150
								},
								{
									field : 'status',
									title : '状态',
									width : 100,
									formatter : function(val, rec) {
										if (rec.status == '1') {
											return "初始状态";
										} 
										if (rec.status == '2') {
											return "记录回收数据";
										} 
										if (rec.status == '3') {
											return "生成数据";
										} 
										if (rec.status == '4') {
											return "删除重复数据";
										} 
										if (rec.status == '5') {
											return "客户群创建成功";
										}
										if (rec.status == '6') {
											return "客户群创建失败出现异常";
										} 
									}
								},{
									field : 'sqlCount',
									title : '生成客户数量',
									width : 60				
								},{
									field : 'costTimes',
									title : '花费时间',
									width : 160
								}] ],
						onDblClickRow:function(rowIndex, rowData) {
							editGroup(rowData.groupCode);
						},
						remoteSort : false,
						pagination : true,
						rownumbers : true
					});

	
	var p = $('#customerTable').datagrid('getPager');
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
		$('#customerTable').datagrid('load', {
			groupCode : $('#groupCode').val(),
			batchCode : $("#batchCode").val()
		});
	});

	
	// 保存按钮
	$("#saveOrderBtn").click(function() {
		saveOderList();
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

	<div id="condition">
		<div>
			<label  for="groupCode">客户群编码:</label> 
			<span>
			<input id="groupCode" class="input-text w150"   name="groupCode"size="10" type="text"/> 
			</span>
			
			<label  for="batchCode">客户群批次:</label> 
			<span>
			<input id="batchCode"type="text"   class="input-text w150"   name="batchCode"size="10" /> 
			</span>
			</span>
			<a  class="Btn ml20" id="queryOrderBtn" data-options="iconCls:'icon-search'">检&nbsp;&nbsp;索</a>	
			<a  class="Btn ml20" id="clearBtn" data-options="iconCls:'icon-search'">清&nbsp;&nbsp;空</a>		
		</div>

		
	</div>
	
	<div style="margin:0 10px 0">

		<table id="customerTable" cellspacing="0" cellpadding="0" data-options=''>

		</table>
	</div>
	

<!-- 客户群批次详情 start 
<div id="batchHisDiv" style="display:none;margin:0 10px">

	<div style="margin-top:10px;font-size: 14px;line-height:1.5em;">
    	 	<label  for="lastMdyTimeView">客户群描述：</label> 
    	 	<span id="groupDescSpan"></span>
	</div>
<div style="float: left; width: 100%;">


		<table id="batchHisTable" cellspacing="0" cellpadding="0" data-options='' style="display:none;">

		</table>
</div>
</div>

<div id="commonMyPopWindow" class="easyui-window" 
closed="true" 
modal="true" 
shadow="false" 
minimizable="false" 
cache="false" 
maximizable="false" 
collapsible="false" 
resizable="false" 
style="margin: 0px;padding: 0px;overflow: auto;">
</div> 
<div id="datasourceMyPopWindow" class="easyui-window" closed="true" modal="true" shadow="false" minimizable="false" cache="false" maximizable="false" collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div> 

<div id="batchPopWindow" class="easyui-window" data-options="title:'批次数据回收'"  closed="true" modal="true" shadow="false" minimizable="false" cache="false" maximizable="false" collapsible="false" resizable="false"  style="width:500px;height:200px;margin: 0px;padding: 0px;overflow: auto;">
		 <div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<input type="hidden" value="" id="batchCodeHide">
				<label>批次数据量:</label><span id="batchCount">-</span>
				<label>可回收数量:</label><span id="useableCount">-</span>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;">
				<span id="msgInfo"></span>
				<a class="easyui-linkbutton" id="btn_ok" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="batchRecycle()">确定回收</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="closeBatchWin()">取消</a>
			</div>
		</div> 
	</div>

-->
</body>
</html>
