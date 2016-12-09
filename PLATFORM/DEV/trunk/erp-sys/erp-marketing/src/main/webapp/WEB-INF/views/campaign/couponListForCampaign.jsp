<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>

<style>
#showdiv{
 display:none;  
 position:absolute;
 
 z-index:9999;  
 padding:12px;
} 
#screen{
 display:none;  
 position:absolute;    
 height:100%;  
 width:100%;  
 top:0;  
 left:0;  
 background-color:#000000;  
 z-index:8888;  
}
#move{
 cursor:move;
}

</style>

</head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<div class="container">
   <table id="couponCrDataGrid">
   </table>  
</div>   
<script type="text/javascript">
$(function() {
    
	$('#couponCrDataGrid')
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
						url : '${ctx}/campaign/couponCrList',
						queryParams:{
							campaingId:$("#d_campaignId").val()
						},
						idField : 'id',
						sortName : 'id',
						sortOrder : 'desc',
						columns : [ [{
							field : 'id',
							title : 'ID',
							width : 60
						},
								{
									field : 'couponId',
									title : '优惠券编码',
									width : 200
								},
								{
									field : 'phone',
									title : '电话号码',
									width : 90
								},
								{
									field : 'contactId',
									title : '客户id',
									width : 150,
								},
								{
									field : 'status',
									title : '状态',
									width : 60,
									formatter : function(val, rec) {
										if (rec.status == '0') {
											return "正常";
										} else if (rec.status == '1') {
											return "已使用";
										} 
									}
								},{
									field : 'enddt',
									title : '有效时间',
									width : 180,
						        	formatter:function(value, rec){
						        		 var _edate = new Date(rec.enddt);
						        		 var _sdate = new Date (rec.startdt);
						        		 return _sdate.format('yyyy-MM-dd hh:mm:ss')+"<br/>"+_edate.format('yyyy-MM-dd hh:mm:ss');
						        	}
								},{
									field : 'crdt',
									title : '创建时间',
									width : 180,
						        	formatter:function(value){
						        		 var _date = new Date(value);
						        		 return _date.format('yyyy-MM-dd hh:mm:ss');
						        	}
								},] ],
						remoteSort : false,
						singleSelect : true,
						pagination : true,
						rownumbers : true
					});	
	var p = $('#couponCrDataGrid').datagrid('getPager');
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
</script>
<input  type="hidden" id='d_campaignId' value="${campaignId}" />
</body>

</html>
