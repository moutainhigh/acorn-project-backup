<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<script type="text/javascript">
$(function() {
	$('#couponConfigTable')
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
						url : '${ctx}/coupon/list',
						queryParams:{
							couponConfigName : $("#couponConfigName").val(),
							couponType:$("#couponType").val()
						},
						idField : 'id',
						sortName : 'id',
						sortOrder : 'desc',
						columns : [ [{
							field : 'id',
							title : 'ID',
							checkbox:true,
							width : 40
						},{
							field : 'couponConfigName',
							title : '优惠券配置名称',
							width : 80
						},
								{
									field : 'couponType',
									title : '优惠券类型',
									width : 90,
									formatter : function(val, rec) {
										if (rec.couponType == '1') {
											return "目录册";
										} else if (rec.couponType == '2')
											return "保险";
										}	
								},			
								{
									field : 'smsSendType',
									title : '短信发送类型',
									width : 80,
									formatter : function(val, rec) {
										if (rec.smsSendType == '1') {
											return "单条";
										} else 
										{											
											return "批量";
										}
									}
								}
								,{
									field : 'stardt',
									title : '有效时间',
									width : 180,
									formatter : function(val, rec) {
						        		 return rec.stardt+"<br/>"+rec.enddt;
									}
								},{
									field : 'crdt',
									title : '创建时间',
									width : 160,
									formatter : function(val, rec) {
						        		 var _sdate = new Date (rec.crdt);
						        		 return _sdate.format('yyyy-MM-dd hh:mm:ss');
									}
								},{
									field : 'crusr',
									title : '创建工号',
									width : 80
								},{
									field : 'mddt',
									title : '最后修改时间',
									width : 160,
									formatter : function(val, rec) {										
						        		 var _sdate = new Date (rec.mddt);
						        		 if (rec.mddt == null || rec.mddt =="" ) {
						        			 return ;
						        		 }
						        		 return _sdate.format('yyyy-MM-dd hh:mm:ss');
									}
								},{
									field : 'mdusr',
									title : '修改工号',
									width : 80
								}] ],
						toolbar : [ {
							id : 'btncut',
							text : '添加',
							iconCls : 'icon-add',
							handler : function() {
								editCouponConfig();
							}
						},{
							id : 'btnview',
							text : '删除',
							iconCls : 'icon-search',
							handler : function() {
								deletes();
							}
						} 
						],
						onDblClickRow:function(rowIndex, rowData) {
							editCouponConfig(rowData.id);
						},
						remoteSort : false,
						singleSelect : true,
						pagination : true,
						rownumbers : true
					});	
	var p = $('#couponConfigTable').datagrid('getPager');
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
		$('#couponConfigTable').datagrid('load', {
			couponConfigName : $("#couponConfigName").val(),
			couponType:$("#couponType").val()
		});
	});	
	// 保存按钮
	$("#saveOrderBtn").click(function() {
		saveOderList();
	});	
});

function deletes () {
	var selected = $('#couponConfigTable').datagrid('getSelected');
	if(null==selected){
		alerts('没有选中任记录');
	}else{
		if(confirm('确定要删除选中的模板？')){
			var ids = [];
			var rows = $('#couponConfigTable').datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].id);
			}
			ids.join(',');	
			$.ajax({
				type:'POST',
				url:'deleteCouponConfig',
				data:{'id':ids.toString()},
				success:function(data){
					if(!eval(data.result)){
						alerts('删除失败');
					}
					$('#couponConfigTable').datagrid('reload');
				}
			});
		}
	}
	
	}

function editCouponConfig(id){
	if(!id){
		id = "";
	}
	 showWindow({  
	        title:'优惠券',  
	        href:'${ctx }/coupon/openWinEditCouponConfig?id='+id,  
	        width:640,  
	        height:420,  
	        onLoad: function(){  
	            //$('#resourceForm').form('clear');  
	        }  
	          
	    },"commonMyPopWindow");  
}

</script>
</head>
<body>
	<div id="condition">
		<div class="">
			<label  for="couponConfigName">优惠券配置名称:</label> 
			<span>
			<input id="couponConfigName"type="text"   class="input-text w150"   name="couponConfigName"size="10" /> 
			</span>
			<label  for="couponType">优惠券类型:</label> 
			<span>
			<input id="couponType" type="text"  class="input-text w150"  name="couponType"size="10" /> 
			</span>
			<a  class="Btn ml20" id="queryOrderBtn" data-options="iconCls:'icon-search'">检&nbsp;&nbsp;索</a>
		</div>
		
	</div>
	
	<div style="margin:0 10px 0">

		<table id="couponConfigTable" cellspacing="0" cellpadding="0" data-options=''>

		</table>
	</div>

<!-- 弹出窗口 -->
<div id="commonMyPopWindow" class="easyui-window" closed="true" modal="true" shadow="false" minimizable="false" cache="false" maximizable="false" collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div> 

</body>
</html>
