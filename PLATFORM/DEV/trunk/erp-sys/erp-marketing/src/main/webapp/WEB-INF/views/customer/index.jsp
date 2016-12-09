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
						url : '${ctx}/customer/list',
						queryParams:{
							groupCode : $('#groupCodeId').val(),
							startDate : $("#startDateId").val(),
							endDate : $("#endDateId").val(),
							groupName : $("#groupNameid").val(),
							user:$("#userId").val(),
							status:$("#status").val(),
							jobId:$("#jobId").val()	
						},
						columns : [ [
						             {field:'ck',checkbox:true},
								{
									field : 'groupCode',
									title : '编号',
									width : 100
								},
								{
									field : 'groupName',
									title : '名称',
									width : 150
								},
								{
									field : 'executeType',
									title : '周期',
									width : 60,
									formatter : function(val, rec) {
										if (rec.executeCycel == '5') {
											return "定时";
										} else if (rec.executeCycel == '0') {
											return "一次";
										}else{
											return "循环";
										}
									}
								},
								{
									field : 'status',
									title : '状态',
									width : 60,
									formatter : function(val, rec) {
										if (rec.status == 'Y') {
											return "可用";
										} else {
											return "停用";
										}
									}
								},{
									field : 'jobId',
									title : '业务编号',
									width : 60				
								},{
									field : 'crtDate',
									title : '创建时间',
									width : 160
								},{
									field : 'crtUser',
									title : '创建工号',
									width : 100
								},{
									field : 'upDate',
									title : '最后修改时间',
									width : 160
								},{
									field : 'upUser',
									title : '修改工号',
									width : 100
								},{
									field : 'ext1',
									title : '操作',
									width : 162,
									formatter:function(val,rec){
										return "<a href=\"javascript:editGroup('"+rec.groupCode+"')\" title=\"查看\"><img width=20 height=20 src=\"${ctx}/static/images/edit.png\"></a>&nbsp;&nbsp;<a href=\"javascript:viewBatchHis('"+rec.groupCode+"')\"  title=\"历史批次\"><img width=20 height=20 src=\"${ctx}/static/images/history.png\"></a>";
									}
								}] ],
						toolbar : [ {
							id : 'btncut',
							text : '添加',
							iconCls : 'icon-add',
							handler : function() {
								editGroup();
							}
						},"-",{
							id : 'btncount',
							text : '查询可用总量',
							iconCls : 'icon-add',
							handler : function() {
								queryCount();
							}
							
						} ],
						onDblClickRow:function(rowIndex, rowData) {
							editGroup(rowData.groupCode);
						},
						remoteSort : false,
						pagination : true,
						rownumbers : true
					});

	//<a href=\"javascript:recoverData('"+rec.groupCode+"')\"  title=\"数据收回\"><img width=20 height=20 src=\"${ctx}/static/images/reserve.png\"></a>&nbsp;&nbsp;
	
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
			groupCode : $('#groupCodeId').val(),
			startDate : $("#startDateId").datetimebox("getValue"),
			endDate : $("#endDateId").datetimebox("getValue"),
			groupName : $("#groupNameId").val(),
			user:$("#userId").val(),
			status:$("#status").val(),
			jobId:$("#jobId").val()	
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

function editGroup(groupCode){
	if(!groupCode){
		groupCode = "";
	}
	 showWindow({  
	        title:'客户群管理',  
	        href:'${ctx }/customer/openWinEditGroup?groupCode='+groupCode,  
	        width:640,  
	        height:460,  
	        onLoad: function(){  
	            //$('#resourceForm').form('clear');  
	        }  
	          
	    },"commonMyPopWindow");  
}


function recoverData(groupCode){
	 showWindow({  
	        title:'数据收回确认',  
	        href:'${ctx }/customer/openWinRecoverDataSource?groupCode='+groupCode,  
	        width:440,  
	        height:200,  	
	        onLoad: function(){  
	            //$('#resourceForm').form('clear');  
	        }  
	          
	    },"commonMyPopWindow");  
}


function editDataSource(groupCode,flag){
	var url = '${ctx }/customer/openWinEditDataSource?groupCode='+groupCode;
		url = url + "&flag="+flag;
	 showWindow2({
	        title:'数据分配',  
	        href:url,  
	        width:800,  
	        height:530,
	        zIndex:-20	          
	    },"datasourceMyPopWindow");
	 $("#datasourceMyPopWindow").parent(".window").addClass("important");
}

function viewBatchHis(groupCode){
	$("#batchHisDiv").show();
		//批次grid
		$('#batchHisTable')
		.datagrid(
				{
					title : '批次历史',
					iconCls : '',
					//width : '100%',
					//height : 200,
					nowrap : false,
					striped : true,
					border : true,
					fitColumns:true,
					collapsible : false,
					showFooter:true,
					scrollbarSize:0,
					url : '${ctx}/customer/viewBatchHis',
					queryParams:{
						groupCode : groupCode
					},
					idField : 'orderid',
					columns : [ [
							{
								field : 'type',
								title : '操作类型',
								width : 80,
								formatter : function(val, rec) {
									if (rec.type == '1') {
										return "生成";
									} else if (rec.type == '2') {
										return "消耗";
									}else{
										return val;
									}
								}
							},
							{
								field : 'batchCode',
								title : '批次号',
								width : 100
							},
							{
								field : 'genDate',
								title : '时间',
								width : 50
							},
							{
								field : 'recoveryCount',
								title : '回收数据量',
								width : 50
							},
							{
								field : 'count',
								title : '生成数据量',
								width : 50
							},
							{
								field : 'usedCount',
								title : '已使用数据量',
								width : 50
							},{
								field : 'unusedCount',
								title : '可使用数据量',
								width : 50
							},{
								field : 'ext1',
								title : '操作',
								width : 162,
								formatter:function(val,rec){
									if(rec.batchCode!=null){
										return "<a href=\"javascript:viewBatchStatus('"+rec.batchCode+"','"+rec.batchCode+"')\" title=\"回收\"><img width=20 height=20 src=\"${ctx}/static/images/edit.png\"></a>";
									}
									
								}	
							}] ],
					onLoadSuccess:function(){
						$("#groupDescSpan").html($('#batchHisTable').datagrid('getData').groupDesc);
					},
					remoteSort : false,
					pagination : true,
					rownumbers : true
				});

		
	var p = $('#batchHisTable').datagrid('getPager');
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
	
	$('#customerTable').datagrid("resize");
	//setTimeout(function(){alerts($('#content').height());},300);
	setTimeout(function(){$('#handlebar').height($('#content').height())},500);
}

function  queryCount() {
	var ids = "";
	var rows = $('#customerTable').datagrid('getSelections');
	if (rows.length>0) {
		for(var i=0;i<rows.length;i++){
			ids = ids+rows[i].groupCode+",";
		}
	}else {
		alerts("请选择要查询的数据");
		return false;
	}
	$.post("queryCount", {
		"ids" : ids
	}, function(data) {
		alerts('可用数据'+data.count);
	});
	
}

function changeSetItem(){
	    if ($("#ext1").attr("checked")) {
	        $("#setItem").hide();
	    }else{
	    	$("#setItem").show();
	    }
}

function viewBatchStatus(groupCode,batchCode){
	
	$("#batchCodeHide").val(batchCode);
	$("#msgInfo").html("");
	$("#batchCount").html("-");
	$("#useableCount").html("-");
	$("#btn_ok").show();
	$.post("${ctx}/customer/viewBatchStatus", {
		"groupCode" : groupCode,
		"batchCode":batchCode
	}, function(data) {
		if(data){
			$("#batchCount").html(data.count);
			$("#useableCount").html(data.usable);
			
			if(data.usable==0){
				$("#btn_ok").hide();
			}
			$("#batchPopWindow").window("open");
		}
	});
}

function closeBatchWin(){
	$("#batchPopWindow").window("close");
}

function batchRecycle(){
	var batchCode = $("#batchCodeHide").val();
	$.post("${ctx}/customer/batchRecycle", {
		"batchCode":batchCode
	}, function(data) {
		if(data){
			var msg = "数据回收成功";
			if(data.result==-1){
				msg = data.msg;
			}else{
				viewBatchStatus($("#batchCodeHide").val(),$("#batchCodeHide").val());
			}
			$("#msgInfo").html(msg);
		}
	});
}
</script>
</head>
<body>

	<div id="condition">
		<div>
			<label  for="groupCodeId">客户群编码:</label> 
			<span>
			<input id="groupCodeId" class="input-text w150"   name="groupCodeId"size="10" type="text"/> 
			</span>
			
			<label  class="labelTitle" for="startDateId">创建时间:</label>
			<span>
			<input class="easyui-datetimebox"   readonly="readonly" id="startDateId" name="startDateId" style="width:150px">
			至
			<input class="easyui-datetimebox"  readonly="readonly" id="endDateId" name="endDateId"   style="width:140px">
			</span>
		</div>

		<div class="">
			<label  for="groupNameId">客户群名称:</label> 
			<span>
			<input id="groupNameId"type="text"   class="input-text w150"   name="groupNameId"size="10" /> 
			</span>
			<label  for="userId">创建工号:</label> 
			<span>
			<input id="userId" type="text"  class="input-text w150"  name="userId"size="10" /> 
			</span>
			<label  for="busiCode">业务编号:</label> 
			<span>
				<select id="jobId"  name="jobId">
				  	<option value="">请选择</option>
				  	<c:forEach items="${list}" var="list"> 
						<option value="${list.jobId}">${list.jobId}</option>
					</c:forEach>	
				</select>
			</span>
		</div>
		<div>
		<label  for="status">状态:</label> 
			<span>
				<select id="status"  name="status">
				  	<option value="">请选择</option>
					<option value="Y">可用</option>
					<option value="N">停用</option>	
				</select>
			</span>
			<a  class="Btn ml20" id="queryOrderBtn" data-options="iconCls:'icon-search'">检&nbsp;&nbsp;索</a>	
			<a  class="Btn ml20" id="clearBtn" data-options="iconCls:'icon-search'">清&nbsp;&nbsp;空</a>		
		</div>
		
	</div>
	
	<div style="margin:0 10px 0">

		<table id="customerTable" cellspacing="0" cellpadding="0" data-options=''>

		</table>
	</div>
	

<!-- 客户群批次详情 start -->
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
<!-- 客户群批次详情 end -->
<!-- 弹出窗口 -->
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


</body>
</html>
