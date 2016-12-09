<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<script type="text/javascript">

$(function() {
    
	$('#recommendTable')
			.datagrid(
					{
						title : '',
						iconCls : 'icon-edit',
						width : '100%',
						height : 520,
						nowrap : false,
						striped : true,
						border : true,
						fitColumns:true,
						collapsible : false,
						scrollbarSize:0,
						//fit : true,
						url : '${ctx}/recommend/list',
						idField : 'id',
						sortName : 'id',
						sortOrder : 'desc',
						columns : [ [
								{
									field : 'groupid',
									title : '客户群ID',
									width : 111
								},
								{
									field : 'groupName',
									title : '客户群名称',
									width : 120,
									formatter:function(val,rec){
										return rec.group.groupName;
									}
								},
								{
									field : 'product1',
									title : '推荐产品一',
									width : 120,
									formatter:function(val,rec){
										if(rec.prod1){
											return rec.prod1.prodname;
										}else{
											return "";
										}
										
									}
								},
								{
									field : 'product2',
									title : '推荐产品二',
									width : 120,
									formatter:function(val,rec){
										if(rec.prod2){
											return rec.prod2.prodname;
										}else{
											return "";
										}
									}
								},{
									field : 'product3',
									title : '推荐产品三',
									width : 120,
									formatter:function(val,rec){
										if(rec.prod3){
											return rec.prod3.prodname;
										}else{
											return "";
										}
									}
								},{
									field : 'valid_start',
									title : '起始时间',
									width : 90
								},{
									field : 'valid_end',
									title : '结束时间',
									width : 90
								},{
									field : 'crt_user',
									title : '创建工号',
									width : 80
								},{
									field : 'crt_date',
									title : '创建时间',
									width : 90
								},{
									field : 'up_user',
									title : '修改工号',
									width : 80
								},{
									field : 'up_date',
									title : '修改时间',
									width : 90
								}

								] ],
		
						toolbar : [ {
							id : 'btncut',
							text : '添加',
							iconCls : 'icon-add',
							handler : function() {
								editRecommend();
							}
						} ],
						onDblClickRow:function(rowIndex, rowData) {
							editRecommend(rowData.id);
						},
						remoteSort : false,
						singleSelect : true,
						pagination : true,
						rownumbers : true
					});

	//<a href=\"javascript:recoverData('"+rec.groupCode+"')\"  title=\"数据收回\"><img width=20 height=20 src=\"${ctx}/static/images/reserve.png\"></a>&nbsp;&nbsp;
	
	var p = $('#recommendTable').datagrid('getPager');
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

	
});

function editRecommend(id){
	if(!id){
		id = "";
	}
	 showWindow({  
	        title:'产品推荐规则配置',  
	        href:'${ctx }/recommend/openWinEditRecommend?id='+id,  
	        width:640,  
	        height:280,  
	        onLoad: function(){  
	            //$('#resourceForm').form('clear');  
	        }  
	          
	    },"commonMyPopWindow");  
}



</script>
</head>
<body>

	
	<div style="margin:0 10px">

		<table id="recommendTable" cellspacing="0" cellpadding="0" data-options=''>

		</table>
	</div>
	

<!-- 弹出窗口 -->
<div id="commonMyPopWindow" class="easyui-window" closed="true" modal="true" shadow="false" minimizable="false" cache="false" maximizable="false" collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div> 

</body>
</html>
