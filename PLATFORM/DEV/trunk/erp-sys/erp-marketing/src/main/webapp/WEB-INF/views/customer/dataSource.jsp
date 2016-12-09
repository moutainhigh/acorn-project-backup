<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>

</head>
<body>

<!-- 数据分配 start -->

		<div class="easyui-layout  baseLayout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				
				<div>
					<label>客户群编码:</label> 
					<span class="inputSpan">
					${group.groupCode }
					</span>
					<label>客户群名称:</label> 
					<span class="inputSpan">
					${group.groupName }
					</span>
				</div>
				<div>
		    	 	<label  for="lastMdyTimeView">描述：</label> 
		    	 	<span>${group.remark }</span>
				</div>
				<div  style="width:100%;height:380px;">
					
					<div id="tt" class="easyui-tabs" data-options="tools:'#tab-tools',fit:true">
						<div title="BI标签" data-options="tools:'#p-tools'" style="padding:10px;">
						</div>
						<div title="SQL" data-options="closable:false,cache:false" style="padding:5px;text-align: left;overflow: auto">
							<div id="sqlEditDiv">
							<form action="" id="sqlSourceForm" method="post">
								<!-- window content -->
								<c:if test="${group.sqlSource!=null }">
								<input type="hidden" name="id" value="${group.sqlSource.id}">
								</c:if>
								<input type="hidden" name="groupCode" value="${group.groupCode }">
									<div class=tab-toolbar>
								<c:if test="${flag!='1' }"> 	
								<a href="javascript:viewSqlDataSource('${group.groupCode }')" class="i-btn icon-back" data-options="iconCls:'icon-back'">查看历史语句</a>|
								</c:if>
							<a href="javascript:saveSqlDataSource('${group.groupCode }')" class="i-btn icon-save" data-options="iconCls:'icon-save'">保存</a>|
							<a href="javascript:viewSqlCount()" class="i-btn icon-search" data-options="iconCls:'icon-search'">预估数据量</a>
							<span id="tipInfo" style="color: red"></span>
							</div>
							
							<textarea rows="12" id="sqlContentEdit" name="sqlContent" cols="91">${group.sqlSource.sqlContent }</textarea>
							</form>
							</div>
							<div id="sqlHisGridDiv" style="display:none;height: 100%;width: 100%">
							<div class="tab-toolbar"><a href="javascript:changeView();" class="i-btn icon-back" data-options="iconCls:'icon-back'">隐藏历史语句</a></div>
							<table id="sqlHisGrid" cellspacing="0" cellpadding="0">
							</table>
							</div>
						</div>
						<div title="其他数据源" data-options="iconCls:'icon-reload',closable:false" style="padding:10px;">
							<table id="test" class="easyui-datagrid" data-options="fit:true">
								<thead>
									<tr>
										<th data-options="field:'f1',width:60">field1</th>
										<th data-options="field:'f2',width:60">field2</th>
										<th data-options="field:'f3',width:60">field3</th>
									</tr>
								</thead>
							</table>
						</div>
						
					</div>
				</div>
				

			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:closeWindow('datasourceMyPopWindow')">取消</a>
			</div>
		</div>
		<input type="hidden" id="sqls" value= "${group.sqlSource.sqlContent }"/>
<!-- 数据分配 end -->
</form>

<script type="text/javascript">
var sql = $("#ext2").val();
var sqls =   $("#sqls").val();
if (sql!=null&&sql!="") {
	$("#sqlContentEdit").attr("value",sql);
}
if (sqls!=null&&sqls!='') {
	$("#ext2").attr("value",sqls);
}
function saveSqlDataSource(groupCode){
	if($("#sqlContentEdit").val()==""){
		$("#tipInfo").html("SQL语句内容不能为空");
		return;
	}
	var flag = '${flag}'
	if (flag=='0') {
		$("#ext2").attr("value",$("#sqlContentEdit").val());
		$.post("${ctx}/customer/saveSQLDataSource",$("#sqlSourceForm").serializeArray(),function(data){
			if(data.result=="-1"){
				$("#tipInfo").html("错误:请检查SQL语句或联系管理人员");
			}else if (data.result=="0") {
				$("#tipInfo").html("格式不正确，请调整至 SELECT incontactid AS contactid,'' AS contactinfo,	'' AS statisinfofrom ... ");	
			}else{
				$("#ext2").attr("value",$("#sqlContentEdit").val());
				$("#tipInfo").html("保存成功");
			}
		},'json');
	}else {				
		$.post("${ctx}/customer/viewSQLCount",{sqlContent:$("#sqlContentEdit").val()},function(data){
			if(data.result=="-1"){
				$("#tipInfo").html("错误:请检查SQL语句或联系管理人员");
			}else if (data.result=="0") {
				$("#tipInfo").html("格式不正确，请调整至 SELECT incontactid AS contactid,'' AS contactinfo,	'' AS statisinfofrom ... ");	
			}else{
				$("#ext2").attr("value",$("#sqlContentEdit").val());
				$("#tipInfo").html("保存成功");
			}
		},'json');		
		//$("#tipInfo").html("保存成功");
		//alerts("保存成功");
	}
}

function viewSqlCount(){
	if($("#sqlContentEdit").val()==""){
		$("#tipInfo").html("SQL语句内容不能为空");
		return;
	}
	$.post("${ctx}/customer/viewSQLCount",{sqlContent:$("#sqlContentEdit").val()},function(data){
		if(data.result=="-1"){
			$("#tipInfo").html("错误:请检查SQL语句或联系管理人员");
		} else if (data.result=="0") {
			$("#tipInfo").html("格式不正确，请调整至 SELECT incontactid AS contactid,'' AS contactinfo,	'' AS statisinfofrom ... ");	
		}
		 else{
			$("#tipInfo").html("数据量约: "+data.result+" 行");
		}
	},'json');
	
}
function changeView(){
	$("#sqlHisGridDiv").hide();
	$("#sqlEditDiv").show();
	
}

var isLoaded = false;
function viewSqlDataSource(groupCode){
	$("#sqlEditDiv").hide();
	$("#sqlHisGridDiv").show();
	if(!isLoaded){
	$('#sqlHisGrid').datagrid(
			{
				title : '',
				nowrap : false,
				width : '100%',
				height : 300,
				striped : true,
				border : true,
				collapsible : false,
				url : '${ctx}/customer/viewSQLDataSourceHis',
				queryParams:{
					groupCode : groupCode
				},
				idField : 'id',
				sortName : 'id',
				sortOrder : 'desc',
				columns : [ [
						{
							field : 'crtDate',
							title : '时间',
							width : 150
						},
						{
							field : 'sqlContent',
							title : '语句',
							width : 500
						}] ],

				remoteSort : false,
				singleSelect : true,
				pagination : true,
				rownumbers : true
			});
		
		var p = $('#oderhistTable').datagrid('getPager');
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
		
		isLoaded = true;
}
}


</script>
</body>
</html>
