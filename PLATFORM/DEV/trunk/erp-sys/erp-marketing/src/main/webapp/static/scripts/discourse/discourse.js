$(function(){
	$('#discourseList').datagrid({
		title:'',
		iconCls:'icon-save',
		width:'100%',
		height:435,
		nowrap: false,
		striped: true,
		collapsible:true,
		fitColumns:true,
		url:'discourseList',
		queryParams:{
			status: $("#status").val(),
			discourseName:$("#discourseName").val(),
			startTime:$("#startTime").datetimebox("getValue"),
			endTime:$("#endTime").datetimebox("getValue"),
			productCode:$("#productCode").val()	
		},
		remoteSort: false,
		fitColumns:true,
		toolbar:[{
			text:'新增',
			iconCls:'icon-add',
			handler:function(){
				editRecommend();
		}},'-',{
			id:'btndel',
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){
				var ids = "";
				var rows = $('#discourseList').datagrid('getSelections');
				if (rows.length>0) {
					for(var i=0;i<rows.length;i++){
						ids = ids+rows[i].id+",";
					}
				}else {
					alerts('请选择要删除的数据');
					return false;
				}				
				$.post("deleteDiscourse", {
					"id" : ids
				}, function(data) {
					alerts('设置无效成功');
					$('#discourseList').datagrid('reload');
				});			
			}

		}],
		onDblClickRow:function(rowIndex, rowData) {
			editRecommend(rowData.id);
		},
		columns : [ [
		             	{field:'ck',checkbox:true},
						{
							field : 'discourseName',
							title : '话术名称',
							rowspan:2,
							width : 80
						},
						{
							field : 'createTime',
							title : '创建时间',
							rowspan:2,
							width : 80
						},{
							field : 'creator',
							title : '创建者',
							rowspan:2,
							width : 80
						},
						{
							field : 'productCode',
							title : '产品编码',
							rowspan:2,
							width : 80
						},
						{
							field : 'productName',
							title : '产品名称',
							rowspan:2,
							width : 80
						},
						{
							field : 'department',
							title : '部门',
							rowspan:2,
							width : 80
						}
						] ],
						
		pagination:true
	});
	var p = $('#discourseList').datagrid('getPager');
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
	$("#queryBtn").click(function() {
		$('#discourseList').datagrid('load', {
			status: $("#status").val(),
			discourseName:$("#discourseName").val(),
			startTime:$("#startTime").datetimebox("getValue"),
			endTime:$("#endTime").datetimebox("getValue"),
			productCode:$("#productCode").val()			
		});
	});	
});
function onsub() {
	var validForm = $('#myform').form('validate');
	if(!validForm){
		return false;
	}
	discourseNames =  $('#discourseNames').val();
	if (discourseNames=="") {
		alerts("话术名称不能为空");
		return ;
	}
	var content = 	UE.getEditor('myEditor').getContent();
	var s = $('#product').combogrid('grid').datagrid('getSelected');
	var productCode = s.prodid;
	var productName = s.prodname;
	var discourseNames = $('#discourseNames').val();
	var hiddenDiscourse =  $('#hiddenDiscourse').val();
	var id = $("#id").val();
	var department = $('#department').combobox('getText');
	var departmentCode = $('#department').combobox('getValue');
	$.post('queryByCode', {
		'productCode' : productCode									
	},function(datas) {
	if (datas!=null&&datas!='') {
		if (datas.flag=='该产品已存在话术'&&hiddenDiscourse!=productCode) {											
				alerts(datas.flag);
				return false;
			}else {				
				$.post("save", {
					"productCode" : productCode,
					"productName":productName,
					"discourseHtmlContent":content,
					"discourseName":discourseNames,
					"id":id,
					"department":department,
					"departmentCode":departmentCode
				},function(data) {
					if (data.success=="该产品话术已存在") {
						alerts(data.success);
						s.clear();
						return false;
					}else {
						alerts(data.success);
						closeWindow('commonMyPopWindow');
						$('#discourseList').datagrid('reload');
					}
				},'json');	
			}
	}		
	},'json');


}  

function editRecommend(id){
	if(!id){
		id = "";
	}
	 showWindow({  
	        title:'新增产品话术',  
	        href:'openWinEditDiscourse?id='+id,  
	        width:800,  
	        height:500,
	        maximizable:false,
	        minimizable:false,
	        zIndex:10
	    },"commonMyPopWindow");  
}