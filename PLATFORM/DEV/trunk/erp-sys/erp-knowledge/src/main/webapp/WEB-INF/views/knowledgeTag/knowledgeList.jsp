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
						url : '${ctx}/knowledgeTag/list',
						queryParams:{
							name:"${name}"
						},
						idField : 'id',
						sortName : 'id',
						sortOrder : 'desc',
						toolbar:'#tagButtons',
                        checkOnSelect:false,
                        selectOnCheck:false,
						columns : [ [{
							field : 'ck',
                            checkbox:true
						},{
							field : 'id',
							title : 'ID',
							width : 60
						},
								{
									field : 'name',
									title : '标签名称',
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

var url;
function createTag(id){
    $('#newTag').dialog('refresh','/knowledgeTag/view?id='+id ).window('open');
    $('#fm').form('clear');
    url = 'knowledgeTag/saves';
}

function editTag(){
    var row = $('#knowledgeTable').datagrid('getSelected');
    if (row){
        $('#newTag').dialog('refresh','/knowledgeTag/view?id='+row.id ).window('open');
        $('#fm').form('clear');
        url = 'knowledgeTag/saves';
    }
}
function saveTag(){
    $('#fm').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
                $('#newTag').dialog('close');        // close the dialog
                $('#knowledgeTable').datagrid('reload');    // reload the user data
            }
        }
    });
}
function destroyTag(){
    var ids = "";
	var rows = $('#knowledgeTable').datagrid('getChecked');
	if (rows.length>0) {
		for(var i=0;i<rows.length;i++){
			ids = ids+rows[i].id+",";
		}
	}else {
		alerts("请选择要删除的数据");
		return ;
	}				
        $.messager.confirm('提示','确定删除这个标签吗？',function(r){
            if (r){
                $.post('knowledgeTag/delete',{ids:ids},function(result){
                    if (result.success){
                        $('#dg').datagrid('reload');    // reload the user data
                    } else {
                        $.messager.show({    // show error message
                            title: 'Error',
                            msg: result.result
                        });
                    }
                },'json');
                $('#knowledgeTable').datagrid('reload');
            }
        });
}
</script>
<div class="easyui-panel" fit="true" border="false" style="padding:10px">
    <div class="easyui-layout" fit="true">
        <div  data-options="region:'north',border:false">
            <div   class="c_block nui-btn" style="margin:0">
            <P class="title" style="margin:0" id="">知识点标签<span class="nui-btn-text fr" id="nui-component2-text" onclick="toHome()"><a class="icon-back"></a>&nbsp;返回</span></P>
                </div>
            </div>
        <div  data-options="region:'center',border:false">
            <table id="knowledgeTable" cellspacing="0" cellpadding="0" data-options=''></table>
        </div>
    </div>
</div>
<div id="newTag" class="easyui-dialog" data-options="title:'修改标签',
            width:400,
            height:200,
            closed:true,
            bodyCls:{padding:10},
            minimizable:false,
            maximizable:false,
            collapsible:false,
            draggable:false,
            modal:true,
            buttons:'#dialogButtons'" style="padding:10px;"></div>
<div id="dialogButtons">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:saveTag()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#newTag').dialog('close')">关闭</a>
</div>
<div id="tagButtons">
    <div>
        <input class="easyui-searchbox" data-options="prompt:'标签名字',searcher:doSearch"
               style="width:300px;height:30px;">
    </div>
    <span >
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="createTag('')">新增标签</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editTag()">编辑标签</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyTag()">删除标签</a>
    </span>
</div>
