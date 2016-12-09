<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<script type="text/javascript">

$(function() {
    
	$('#campaignTable')
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
						url : '${ctx}/campaign/list',
						queryParams:{
							type : $('#campaignTypeId').combobox("getValue"),
							startDate : $("#startDateId").val(),
							endDate : $("#endDateId").val(),
							name : $("#campaignNameId").val(),
							user:$("#userId").val()
						},
						idField : 'id',
						sortName : 'id',
						sortOrder : 'desc',
						columns : [ [{
							field : 'department',
							title : '',
							width : 40,
							formatter:function(val,rec){
								if(rec.status == '1'||rec.status =='5'||rec.campaignType.id==2){
									return "";
								}else if(rec.status == '3'){
									return "<a href=\"javascript:goOnCampaign('"+rec.id+"','"+rec.name+"')\" title=\"继续\"><img width=20 height=20 src=\"${ctx}/static/scripts/jquery/themes/icons/continue.png\"></a>";;
								}
								return "<a href=\"javascript:stopCampaign('"+rec.id+"','"+rec.name+"')\" title=\"停止\"><img width=20 height=20 src=\"${ctx}/static/scripts/jquery/themes/icons/stop.png\"></a>";
							}
						},{
							field : 'id',
							title : 'ID',
							width : 60
						},
								{
									field : 'name',
									title : '计划名称',
									width : 200
								},
								{
									field : 'owner',
									title : '负责人',
									width : 90
								},
								{
									field : 'audience',
									title : '客户群',
									width : 150,
									formatter : function(val, rec) {
										return rec.group.groupName;
									}
								},
								//{
								//	field : 'budgetedCost',
								//	title : '预算成本',
								//	width : 80
								//},
								{
									field : 'campaignTypeId',
									title : '营销任务',
									width : 130,
									formatter : function(val, rec) {
										return rec.campaignType.name;
									}
								},
								{
									field : 'status',
									title : '状态',
									width : 60,
									formatter : function(val, rec) {
										if (rec.status == '0') {
											return "正常";
										} else if (rec.status == '1') {
											return "完成";
										} else if (rec.status == '2') {
											return "调度中";
										} else if (rec.status == '3') {
											return "停止";
										}
									}
								},{
									field : 'startDate',
									title : '有效时间',
									width : 180,
									formatter : function(val, rec) {
										return rec.startDate+"<br/>"+rec.endDate;
	
									}
								},{
									field : 'createDate',
									title : '创建时间',
									width : 180
								},{
									field : 'createUser',
									title : '创建工号',
									width : 80
								},{
									field : 'updateDate',
									title : '最后修改时间',
									width : 180
								},{
									field : 'updateUser',
									title : '修改工号',
									width : 80
								}] ],
						toolbar : [ {
							id : 'btncut',
							text : '添加',
							iconCls : 'icon-add',
							handler : function() {
								editCampaign();
							}
						},{
							id : 'btnview',
							text : '查看',
							iconCls : 'icon-search',
							handler : function() {
								viewCampaignBatch();
							}
						}
						],
						onDblClickRow:function(rowIndex, rowData) {
							editCampaign(rowData.id);
						},
						remoteSort : false,
						singleSelect : true,
						pagination : true,
						rownumbers : true
					});

	//<a href=\"javascript:recoverData('"+rec.groupCode+"')\"  title=\"数据收回\"><img width=20 height=20 src=\"${ctx}/static/images/reserve.png\"></a>&nbsp;&nbsp;
	
	var p = $('#campaignTable').datagrid('getPager');
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
		$('#campaignTable').datagrid('load', {
			type : $('#campaignTypeId').combobox("getValue"),
			startDate : $("#startDateId").datetimebox("getValue"),
			endDate : $("#endDateId").datetimebox("getValue"),
			name : $("#campaignNameId").val(),
			user:$("#userId").val()
		});
	});

	
	// 保存按钮
	$("#saveOrderBtn").click(function() {
		saveOderList();
	});
	
});

function editCampaign(id){
	if(!id){
		id = "";
	}
	 showWindow({  
	        title:'营销计划设置向导',  
	        href:'${ctx }/campaign/openWinEditCampaign?id='+id,  
	        //href:'${ctx }/campaign/openWinEditCampaign?id=1050', 		
	        width:640,  
	        height:420,  
	        onLoad: function(){  
	            //$('#resourceForm').form('clear');  
	        }  
	          
	    },"commonMyPopWindow");  
}

function stopCampaign(id,cname){
	$.messager.confirm('提示', '确认停止[ '+cname+' ]?', function(r){
		if (r){
			$.ajax({
				type:'POST',
				url:'${ctx }/campaign/stopCampaign',
				data:{'campaignId':id},
				success:function(data){
					alerts(data.result);
					$('#campaignTable').datagrid("reload");
				}
			});
		}
	});
}

function goOnCampaign(id,cname){
	$.messager.confirm('提示', '确认继续执行[ '+cname+' ]?', function(r){
		if (r){
			$.ajax({
				type:'POST',
				url:'${ctx }/campaign/goOnCampaign',
				data:{'campaignId':id},
				success:function(data){
					alerts(data.result);
					$('#campaignTable').datagrid("reload");
				}
			});
		}
	});
}



function editNextPage(id,url,returnUrl){
	if(!id){
		id = "";
	}
	 showWindow({  
	        title:'营销计划设置向导',  
	        href:'${ctx }'+url+"?id="+id+"&returnUrl="+returnUrl,  
	        width:640,  
	        height:420,  
	        onLoad: function(){  
	            //$('#resourceForm').form('clear');  
	        }  
	          
	    },"commonMyPopWindow");  
}

function editPrevPage(url){
	 showWindow({  
	        title:'营销计划设置向导',  
	        href:url,  
	        width:640,  
	        height:420,  
	        onLoad: function(){  
	            //$('#resourceForm').form('clear');  
	        }  
	          
	    },"commonMyPopWindow");  
}

function viewCampaignBatch(){
	var row = $('#campaignTable').datagrid('getSelected');
	if (row){
		showWindow({  
	        title:'营销计划批次信息',  
	        href:'${ctx}/campaign/openWinCampaignView?campaignId='+row.id,  
	        width:840,  
	        height:520,  
	        onLoad: function(){  
	        }  
	          
	    },"commonMyPopWindow");  
		
	}else{
		alerts("请选择一条营销计划");
	}
}

function viewCouponBatch(){
	var row = $('#campaignTable').datagrid('getSelected');
	if (row){
		showWindow({  
	        title:'营销coupon批次信息',  
	        href:'${ctx}/campaign/openWinCouponView?campaignId='+row.id,  
	        width:840,  
	        height:520,  
	        onLoad: function(){  
	        }  
	          
	    },"commonMyPopWindow");  
		
	}else{
		alerts("请选择一条营销计划");
	}
}







function viewDetailList(campaignId){
		showWindow({  
	        title:'营销计划短信发送',  
	        href:'${ctx}/sms/openWinDetailsIndex?campaignId='+campaignId,  
	        width:1040,  
	        height:520,  
	        onLoad: function(){  
	        }  
	          
	    },"smsDetailList");  
		
}
function changeSetItem(){
    if ($("#campaignTypeId").val()=='2') {
        $("#setItem").hide();
    }else  if ($("#campaignTypeId").val()=='1'){
    	$("#setItem").show();
    }
}
</script>
</head>
<body>

	<div id="condition">
		<div>
			<label  for="campaignTypeId">计划类型:</label> 
			<span>
			<input id="campaignTypeId"  class="easyui-combobox"  style="width:150px;"
						name="campaignTypeId"
						data-options="
								url:'${ctx }/campaignType/getCampaignTypes',
								valueField:'id',
								textField:'name',
								panelHeight:'auto',
								multiple:false
						">
						
			</span>
			
			<label  class="labelTitle" for="startDateId">创建时间:</label>
			<span>
			<input class="easyui-datetimebox"   readonly="readonly" id="startDateId" name="startDateId" style="width:150px">
			至
			<input class="easyui-datetimebox"  readonly="readonly" id="endDateId" name="endDateId"   style="width:140px">
			</span>
		</div>

		<div class="">
			<label  for="campaignNameId">计划名称:</label> 
			<span>
			<input id="campaignNameId"type="text"   class="input-text w150"   name="campaignNameId"size="10" /> 
			</span>
			<label  for="userId">负责人:</label> 
			<span>
			<input id="userId" type="text"  class="input-text w150"  name="userId"size="10" /> 
			</span>
			<a  class="Btn ml20" id="queryOrderBtn" data-options="iconCls:'icon-search'">检&nbsp;&nbsp;索</a>
		</div>
		
	</div>
	
	<div style="margin:0 10px 0">

		<table id="campaignTable" cellspacing="0" cellpadding="0" data-options=''>

		</table>
	</div>

<!-- 弹出窗口 -->
<div id="commonMyPopWindow" class="easyui-window" closed="true" modal="true" shadow="false" minimizable="false" cache="false" maximizable="false" collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div> 

</body>
</html>
