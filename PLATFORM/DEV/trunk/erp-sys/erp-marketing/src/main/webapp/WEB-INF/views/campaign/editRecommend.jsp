<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>

</head>
<body>

<!-- 数据分配 start -->

	   	 
		<div class="easyui-layout baseLayout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<form action="" id="recommendForm" method="post">
				<!-- window content -->
				<input type="hidden" id="campaignId" name="campaignId" value="${campaignId }">
				<input type="hidden" id="returnUrl" name="returnUrl" value="${returnUrl }">
				<div>
					<label>推荐产品一:</label> 
					 <span class="inputSpan">
						<input class="easyui-combogrid" style="width:150px;"
						id="product1"
						name="product1"
						width="200"
						value="${leadSet['product1']}"
						data-options="
								url:'${ctx }/dict/getProduct',
								idField:'prodid',
								textField:'prodname',
								panelWidth:450,
								panelHeight:320,
								pagination:true,
								queryParams: {
									productName: '${leadSet['product1']}'
								},
								rownumbers:true,
								required:true,
								editable:false,
								toolbar:'#tt',
								columns:[[
									{field:'prodid',title:'产品ID',width:220},
									{field:'prodname',title:'产品名称',width:200}
								]],
								onLoadSuccess:function(data){
									$('#searchText1').val('${leadSet['product1']}')
								}
						">
					</span>
					<label>推荐产品二:</label> 
					 <span class="inputSpan">
						<input class="easyui-combogrid" style="width:150px;"
						id="product2"
						name="product2"
						width="200"
						value="${leadSet['product2']}"
						data-options="
								url:'${ctx }/dict/getProduct',
								idField:'prodid',
								textField:'prodname',
								panelWidth:450,
								panelHeight:320,
								pagination:true,
								queryParams: {
									productName: '${leadSet['product2']}'
								},
								rownumbers:true,
								editable:false,
								toolbar:'#tt2',
								columns:[[
									{field:'prodid',title:'产品ID',width:220},
									{field:'prodname',title:'产品名称',width:200}
								]],
								onLoadSuccess:function(data){
									$('#searchText2').val('${leadSet['product2']}')
								}
						">
					</span>
				</div>
				<div>
					<label>推荐产品三:</label> 
					<span class="inputSpan">
						<input class="easyui-combogrid" style="width:150px;"
						id="product3"
						name="product3"
						width="200"
						value="${leadSet['product3']}"
						data-options="
								url:'${ctx }/dict/getProduct',
								idField:'prodid',
								textField:'prodname',
								panelWidth:450,
								panelHeight:320,
								pagination:true,
								queryParams: {
									productName: '${leadSet['product3']}'
								},
								rownumbers:true,
								editable:false,
								toolbar:'#tt3',
								columns:[[
									{field:'prodid',title:'产品ID',width:220},
									{field:'prodname',title:'产品名称',width:200}
								]],
								onLoadSuccess:function(data){
									$('#searchText3').val('${leadSet['product3']}')
								}
						">
					</span>
				</div>

</form>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;">
				
		   	   	   			<a href="javascript:editPrevPage('${ctx}/campaign/openWinEditCampaign?id=${campaignId }')" class="easyui-linkbutton" data-options="iconCls:'icon-prev'" onclick="">上一步</a>
		   	   	   			 <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-next'" onclick="saveRecommend()">确定,下一步</a>
		   	   	   			<a href="javascript:closeWindow('commonMyPopWindow')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
			</div>
		</div>
<!-- 数据分配 end -->
</form>
<div id="tt">
		<input type="text" id="searchText1"/><a href="#" id="queryProductBtn" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'"></a>
</div>
<div id="tt2">
		<input type="text" id="searchText2"/><a href="#" id="queryProductBtn2" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'"></a>
</div>
<div id="tt3">
		<input type="text" id="searchText3"/><a href="#" id="queryProductBtn3" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'"></a>
</div>
<script type="text/javascript">

function saveRecommend(){
	var r = $('#recommendForm').form('validate');
	if(!r) {
		return;
	}
	var product1 = $('#product1').combogrid('grid').datagrid('getSelected');
	var product2 = $('#product2').combogrid('grid').datagrid('getSelected');
	var product3 = $('#product3').combogrid('grid').datagrid('getSelected');
	if (product1!=null&&product2!=null&&product3!=null) {
		if ((product1.prodname==product2.prodname)||(product1.prodname==product3.prodname)||(product3.prodname==product2.prodname)) {
			alerts("不能推荐同一款产品");
			return;
		}
	}
	
	$.post("${ctx}/campaign/saveLeadSet",$("#recommendForm").serializeArray(),function(data){
		if(data.isSchedule == "1"){
			editNextPage(data.campaignId,data.next,data.returnUrl);
		}else{
			closeWindow('commonMyPopWindow'); 
			$('#campaignTable').datagrid("reload");
		}
		
	},'json');
	
}


//查询按钮
$("#queryProductBtn").click(function() {
	var g = $('#product1').combogrid('grid');	// get datagrid object
	var r = g.datagrid('reload', {
		productName : $('#searchText1').val()
	});
});
//查询按钮
$("#queryProductBtn2").click(function() {
	var g = $('#product2').combogrid('grid');	// get datagrid object
	var r = g.datagrid('reload', {
		productName : $('#searchText2').val()
	});
});
//查询按钮
$("#queryProductBtn3").click(function() {
	var g = $('#product3').combogrid('grid');	// get datagrid object
	var r = g.datagrid('reload', {
		productName : $('#searchText3').val()
	});
});

</script>
</body>
</html>
