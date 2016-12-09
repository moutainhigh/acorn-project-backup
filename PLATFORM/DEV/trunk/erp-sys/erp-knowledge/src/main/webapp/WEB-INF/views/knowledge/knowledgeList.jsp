<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type=text/javascript src="${ctx}/static/js/knowledge/knowledgeArticle.js"></script>
<script type=text/javascript src="${ctx}/static/js/common-ui.js"></script> 
<script>
$(function() {    
	$('#knowledgeTable')
			.datagrid(
					{
						title : '',
						iconCls : 'icon-edit',
						width : '100%',
						height : 440,
						nowrap : false,
						striped : true,
						border : true,
                        fit:true,
						fitColumns:true,
						collapsible : false,
						scrollbarSize:0,
					    pagination:true,
						url : '${ctx}/knowledge/knowledgeList',
						queryParams:{
							categoryId : "${categoryId}",
							keyWord:"${keyWord}"
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
							field : 'title',
							title : '标题',
							width : 100
						},
								{
									field : 'productName',
									title : '产品名称',
									width : 200
								},
								{
									field : 'productCode',
									title : '产品编码',
									width : 100
								},
								{
									field : 'shortPinyin',
									title : '产品简码',
									width : 150			
								},								
								{
									field : 'createDate',
									title : '创建时间',
									width : 180,
									formatter : function(val, rec) {										 
						        		 var _sdate = new Date (rec.createDate);
						        		 return _sdate.format('yyyy-MM-dd hh:mm:ss');						   
									}
								},{
									field : 'createUser',
									title : '创建工号',
									width : 80
								},{
									field : 'updateDate',
									title : '最后修改时间',
									width : 180,
									formatter : function(val, rec) {
										if (rec.updateDate!=null) {
											 var _sdate = new Date (rec.updateDate);
							        		 return _sdate.format('yyyy-MM-dd hh:mm:ss');
										}						        		
									}
								},{
									field : 'updateUser',
									title : '修改工号',
									width : 80
								}] ],
						onDblClickRow:function(rowIndex, rowData) {
							view(rowData.id);
						},
						onLoadSuccess:function (data) {
							if (data.total !=0) {
								$("#categoryName").html(eval(data).rows[0].knowledgeCategory.name);
							}
						},
						remoteSort : false,
						singleSelect : true,
						pagination : true,
						rownumbers : true
					});	
	var p = $('#knowledgeTable').datagrid('getPager');
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

function view(id){
	if (id !=""&&id!=null) {
		$("#main-layout")
        .layout('panel','center').panel('refresh','/knowledge/initw?id='+id);	    
};      	
}
</script>
<div class="easyui-panel" fit="true" border="false" style="padding:10px">
<div class="easyui-layout" fit="true">
    <div  data-options="region:'north',border:false">
        <div   class="c_block" style="margin:0">
        <P class="title" style="margin:0" id="categoryName"></P>
            </div>
        </div>
    <div  data-options="region:'center',border:false">
        <table id="knowledgeTable" cellspacing="0" cellpadding="0" data-options=''>

        </table>
    </div>
    </div>

  </div>