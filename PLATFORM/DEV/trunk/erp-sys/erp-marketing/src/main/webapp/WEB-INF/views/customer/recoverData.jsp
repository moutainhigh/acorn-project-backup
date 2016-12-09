<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>

</head>
<body>

<!-- 数据收回 start -->

		<div class="easyui-layout baseLayout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<form action="" id="customerGroupForm" method="post">
				<!-- window content -->
				<div>
					该客户群被活跃中的“XXX”，“YYY”营销活动调用，是否强制收回本客户群的所有数据
				</div>
</form>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:saveGroup()" >停止并强制收回</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:saveGroup()" >停止，不进行强制收回</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:closeWindow('commonMyPopWindow')" >取消</a>
			</div>
		</div>
<!-- 数据收回end -->
</form>

<script type="text/javascript">

function saveGroup(){
	var r = $('#customerGroupForm').form('validate');
	if(!r) {
		return false;
	}
	$.post("${ctx}/customer/saveGroup",$("#customerGroupForm").serializeArray(),function(data){
		closeWindow('commonMyPopWindow');
		$('#oderhistTable')
		.datagrid("reload");
	},'json');
}


</script>
</body>
</html>
