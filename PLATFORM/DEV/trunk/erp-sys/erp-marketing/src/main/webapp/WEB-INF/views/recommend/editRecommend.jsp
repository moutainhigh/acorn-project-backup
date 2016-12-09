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
				
				<c:if test="${recommend.id!=null }">
				<input type="hidden" name="id" value="${recommend.id}">
				</c:if>
				
				<div>
					<label>客户群组:</label> 
					 
						<span class="inputSpan">
						<input class="easyui-combogrid" style="width:150px;"
						id="groupid"
						name="groupid"
						width="200"
						value="${recommend.groupid}"
						data-options="
								url:'${ctx }/sms/grouplist',
								idField:'groupCode',
								textField:'groupName',
								panelWidth:450,
								panelHeight:320,
								pagination:true,
								queryParams: {
									groupCode: '${recommend.groupid}'
								},
								rownumbers:true,
								required:true,
								editable:false,
								 mode: 'remote', 
								 toolbar:'#tt4',
								columns:[[
									{field:'groupCode',title:'客户群编号',width:220},
									{field:'groupName',title:'客户群名称',width:200}
								]],
								onLoadSuccess:function(data){
									$('#searchText4').val('${recommend.groupid}')
								},onChange:function(data){
									$.post('queryGroupCode', {
											'groupCode' : data									
										},function(datas) {
										if (datas!=null&&datas!='') {
											if ('该客户群已推荐产品'==datas.result&&'${recommend.groupid}'!=data) {											
													alerts(datas.result);
												}
										}											
										},'json');
								}
						">
					</span>
				</div>
				
				<div>
					<label>流程任务:</label> 
					<span class="inputSpan">
					<select id="process_defid" class="easyui-combobox" style="width:150px;" value="${recommend.process_defid}" name="process_defid" style="" data-options="editable:false,required:true,value:'${recommend.process_defid}'">
						<option value=""></option>
						<option value="agent_task" <c:if test="${recommend.process_defid=='agent_task' }">selected</c:if>>获取客户任务</option>
					</select>
					</span>
					<label>状态:</label> 
					<span class="inputSpan">
					<select id="status" class="easyui-combobox" style="width:150px;" value="${recommend.status}" name="status" style="" data-options="editable:false">
						<option value="1"  <c:if test="${recommend.status=='1' }">selected</c:if>>有效</option>
						<option value="0" <c:if test="${recommend.status=='0' }">selected</c:if>>无效</option>
						<option value="-1"<c:if test="${recommend.status=='-1' }">selected</c:if>>删除</option>
					</select>
					</span>
				</div>
				
				<div>
					<label>推荐产品一:</label> 
					 <span class="inputSpan">
						<input class="easyui-combogrid" style="width:150px;"
						id="product1"
						name="product1"
						width="200"
						value="${recommend.product1}"
						data-options="
								url:'${ctx }/dict/getProduct',
								idField:'prodid',
								textField:'prodname',
								panelWidth:450,
								panelHeight:320,
								pagination:true,
								queryParams: {
									productName: '${recommend.prod1.prodname}',
									productName: '${recommend.prod1.prodid}'
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
									$('#searchText1').val('${recommend.prod1.prodname}')
								}
						">
					</span>
					<label>推荐产品二:</label> 
					 <span class="inputSpan">
						<input class="easyui-combogrid" style="width:150px;"
						id="product2"
						name="product2"
						width="200"
						value="${recommend.product2}"
						data-options="
								url:'${ctx }/dict/getProduct',
								idField:'prodid',
								textField:'prodname',
								panelWidth:450,
								panelHeight:320,
								pagination:true,
								queryParams: {
									productName: '${recommend.prod2.prodname}',
									productName: '${recommend.prod2.prodid}'
								},
								rownumbers:true,
								required:true,
								editable:false,
								toolbar:'#tt2',
								columns:[[
									{field:'prodid',title:'产品ID',width:220},
									{field:'prodname',title:'产品名称',width:200}
								]],
								onLoadSuccess:function(data){
									$('#searchText2').val('${recommend.prod2.prodname}')
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
						value="${recommend.product3}"
						data-options="
								url:'${ctx }/dict/getProduct',
								idField:'prodid',
								textField:'prodname',
								panelWidth:450,
								panelHeight:320,
								pagination:true,
								queryParams: {
									productName: '${recommend.prod3.prodname}',
									productName: '${recommend.prod3.prodid}'
								},
								rownumbers:true,
								required:true,
								editable:false,
								toolbar:'#tt3',
								columns:[[
									{field:'prodid',title:'产品ID',width:220},
									{field:'prodname',title:'产品名称',width:200}
								]],
								onLoadSuccess:function(data){
									$('#searchText3').val('${recommend.prod3.prodname}')
								}
						">
					</span>
				</div>
				<div>
					<label>有效时间:</label> 
					 <span class="pd5">
					<input  class="easyui-datetimebox" id="starttime" readonly="readonly" type="text"  style="width:150px" name="valid_start" value="${recommend.valid_start}" data-options="editable:false,required:true">
					至<input class="easyui-datetimebox" id="endtime" readonly="readonly" type="text"  style="width:150px" name="valid_end" value="${recommend.valid_end}" data-options="editable:false,required:true">
					</span>
					
				</div>
</form>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:saveRecommend()" >保存</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:closeWindow('commonMyPopWindow')" >取消</a>
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
<div id="tt4">
		<input type="text" id="searchText4"/><a href="#" id="queryProductBtn4" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'"></a>
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
	var starttime = $("#starttime").datebox("getValue");
	var endtime = $("#endtime").datebox("getValue");
	if (endtime<starttime) {
		alerts("结束时间不能早于开始时间");
		return;		
	}
	var data = $('#groupid').combogrid('grid').datagrid('getSelected');
	$.post('queryGroupCode', {
		'groupCode' : data.groupCode									
	},function(datas) {
	if (datas!=null&&datas!='') {
		if ('该客户群已推荐产品'==datas.result&&'${recommend.groupid}'!=data.groupCode) {											
				alerts(datas.result);
				return ;
			}else {				
				$.post("${ctx}/recommend/saveRecommend",$("#recommendForm").serializeArray(),function(data){
					closeWindow('commonMyPopWindow');
					$('#recommendTable').datagrid("reload");
				},'json');
			}
	}		
	},'json');
	
}

function changeCycleType(type){
	var fV = type;
	hideAllFieldSet();
	if(fV=='5'){
		$("#timeStrFieldSet").show();
	}else if(fV=='1'){
		$("#dayFieldSet").show();
	}else if(fV=='3'){
		$("#monthFieldSet").show();
	}else if(fV=='4'){
		$("#yearFieldSet").show();
	}else if(fV=='2'){
		$("#weekFieldSet").show();
	}else if(fV=='6'){
		$("#hourFieldSet").show();
	}
	
	if(fV!='0' && fV != '5'){
		$("#timeRangeFieldSet").show();
	}
}
var hideAllFieldSet = function (){
	$("#timeStrFieldSet").hide();
	$("#timeRangeFieldSet").hide();
	$("#dayFieldSet").hide();
	$("#monthFieldSet").hide();
	$("#yearFieldSet").hide();
	$("#weekFieldSet").hide();
	$("#hourFieldSet").hide();
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
//查询按钮
$("#queryProductBtn4").click(function() {
	var g = $('#groupid').combogrid('grid');	// get datagrid object
	var r = g.datagrid('reload', {
		groupCode : $('#searchText4').val()
	});
});
</script>
</body>
</html>
