<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body>
     <form name="myform" method="post" id="myform">
      <ul class="tr" style="margin:5px 0">
   	 <li>
   	 <ul  class="td">
   	 <li>
   	      <label>话术名称： </label>
   	      <input type="text text-input"  id="discourseNames"    value="${discourse.discourseName }" class="w150 input-text" />    	  	        
	</li>
		<input type="hidden" id="id" value="${discourse.id}"/>
	<li>
   	 <label>关联产品:</label> 
					 <span class="inputSpan">
						<input class="easyui-combogrid" style="width:150px;"
						id="product"
						name="product"
						width="200"
						value="${discourse.productCode}";
						data-options="
								url:'${ctx }/dict/getProduct',
								idField:'prodid',
								textField:'prodname',
								valueField:'prodid',
								panelWidth:450,
								panelHeight:320,
								pagination:true,							
								rownumbers:true,
								queryParams: {
									productName: '${discourse.productName}'
								},
								required:true,
								editable:false,
								toolbar:'#tt',
								columns:[[
									{field:'prodid',title:'产品ID',width:220},
									{field:'prodname',title:'产品名称',width:200}
								]],
								onLoadSuccess:function(data){
									$('#searchText').val('${discourse.productName}')
								},
								onChange:function(data){
									$.post('queryByCode', {
											'productCode' : data									
										},function(datas) {
										if (datas!=null&&datas!='') {
											if (datas.flag=='该产品已存在话术'&&'${discourse.productCode}'!=data) {											
													alerts(datas.flag);
												}
										}
											
										},'json');
								}								
						">
					</span>
					
	</li>
	<li>
	<label>部门:</label> 
	<input type="hidden" name="departmentCode"	id="departmentCode" value="${discourse.departmentCode}"/>					 	
	<input class="easyui-combobox"  style="width:150px;"
						id = "department"
						value="${discourse.department}"
						data-options="
								url:'${ctx}/dict/getDepartment',
								idField:'id',
								textField:'dsc',
								valueField:'tmpId',
								multiple:false,
								required:true,
								ONSELECT:function(){
										$('#departmentCode').attr('value',$('department').combobox('getValue'));									
								}
						">
	</li>
	<li ><a  class="Btn" onclick="onsub()" >保存</a></li>
	 </ul>
   	 </li>
   	 </ul>
   	 <textarea name="myEditor" id="myEditor">${discourse.discourseHtmlContent}</textarea>
   	 <input type="hidden" id="hiddenDiscourse" name="hiddenDiscourse" value="${discourse.productCode}"/>
</form> 
<div id="tt">
		<input type="text" id="searchText"/><a href="#" id="queryProductBtn" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'"></a>
</div>
<script>
var editor = new UE.ui.Editor();
editor.render('myEditor');
//查询按钮
$("#queryProductBtn").click(function() {
	var g = $('#product').combogrid('grid');	// get datagrid object
	var r = g.datagrid('reload', {
		productName : $('#searchText').val()
	});
});
</script>

</body>
</html>