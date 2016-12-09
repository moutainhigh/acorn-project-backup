$(function() {
	$('#oderhistTable').datagrid({
						title : '',
						iconCls : 'icon-edit',
						width :'100%',
						height : 400,
						nowrap : true,
						striped : true,
						border : true,
						collapsible : false,
					    fitColumns:true,
					    scrollbarSize:0,
						//url : '/orderhist/list',
						queryParams:{
							orderId : $('#orderId').val(),
							startDate : $("#startDate").datebox("getValue"),
							endDate : $("#endDate").datebox("getValue"),
							status : $("#status").val(),
							wareHouse:$("#wareHouseId").val(),
							entityId:$("#entityId").val(),
							queryType:$("#queryType").val()
						},
						idField : 'orderid',
						sortName : 'tradeId',
						sortOrder : 'desc',
						columns : [ [
								{
									field : 'id',
									title : 'ID',
									width : 100
								},
								{
									field : 'orderId',
									title : '运单编号',
									width : 180
								},
								{
									field : 'province',
									title : '省',
									width : 85
								},
								{
									field : 'city',
									title : '市',
									width : 85
								},
								{
									field : 'county',
									title : '县',
									width : 85
								},
								{
									field : 'area',
									title : '街道乡镇',
									width : 170
								},
								{
									field : 'address',
									title : '详细地址',
									width : 300
								},
								{
									field : 'status',
									title : '是否可送',
									width : 100,
									formatter : function(val, rec) {
										if (rec.status == '1') {
											return "<select title='"
													+ rec.orderId
													+ "' id='"
													+ rec.id
													+ "' name='acting'><option value=''></option><option value='1'>可送</option><option value='2'>不可送</option></select>";
										} else {
											return;
										}
									}
								} ] ],
//						toolbar : [
//						           {
//							id : 'btncut',
//							text : '导出',
//							iconCls : 'icon-redo',
//							handler : function() {
//								exportOderList();
//							}
//						}, '-', {
//							id : 'btnsave',
//							text : '导入',
//							iconCls : 'icon-back',
//							handler : function() {
//
//								$('#uploadFileDiv').window('open');
//							}
//						} 
//						],
						remoteSort : false,
						singleSelect : false,
						pagination : true,
						rownumbers : true,
						onLoadSuccess:function(data){
							if(data.status==-1){
								$.messager.alert('提醒','查询的日期间隔需小于或等于10天'); 
							}
						}
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

	// 查询按钮
	$("#queryOrderBtn").click(function() {
		$('#oderhistTable').datagrid({url:"/orderhist/list",queryParams:{
			orderId : $('#orderId').val(),
			startDate : $("#startDate").datebox("getValue"),
			endDate : $("#endDate").datebox("getValue"),
			status : $("#status").val(),
			wareHouse:$("#wareHouseId").val(),
			entityId:$("#entityId").val(),
			queryType:$("#queryType").val()
		}});
	});

	// 保存按钮
	$("#saveOrderBtn").click(function() {
		saveOderList();
	});
	
	
	// 保存按钮
	$("#btnsave").click(function() {
		$('#uploadFileDiv').window('open');
		$('#fileToUpload').val("");
		
		return;
	});

});

function saveOderList() {
	var idStr = "";
	var orderStr = "";
	var statusStr = "";
	var i = 0;
	$("[name='acting']").each(function(idx, obj) {
		if (obj.value != "") {
			if (i != 0) {
				idStr += ",";
				orderStr += ",";
				statusStr += ",";
			}
			idStr += obj.id;
			orderStr += obj.title;
			statusStr += obj.value;
			i++;
		}

	});

	var queryParam = $('#oderhistTable').datagrid('options').queryParams;
	if (orderStr != "") {
		$.post("/orderhist/saveOrder", {
			"idList" : idStr,
			"orderList" : orderStr,
			"statusList" : statusStr,
			"companyId":queryParam.entityId
		}, function(data) {
			$('#oderhistTable').datagrid('reload');
		});
	}
}

function exportOderList() {

	var queryParam = $('#oderhistTable').datagrid('options').queryParams;

	if (queryParam.orderId)
		$('#d_orderId').val(queryParam.orderId);
	if (queryParam.startDate)
		$("#d_startDate").val(queryParam.startDate);
	if (queryParam.endDate)
		$("#d_endDate").val(queryParam.endDate);
	if (queryParam.status)
		$("#d_status").val(queryParam.status);
	if (queryParam.wareHouse)
		$("#d_wareHouse").val(queryParam.wareHouse);
	if (queryParam.entityId)
		$("#d_entityId").val(queryParam.entityId);
	if (queryParam.queryType)
		$("#d_queryType").val(queryParam.queryType);
	$("#downloadForm").submit();
}

function ajaxFileUpload()
{
 

$("#loading")
 .ajaxStart(function(){
  $(this).show();
 })
 .ajaxComplete(function(){
  $(this).hide();
 });


//是否显示图片，估计与浏览器的类型有关吧

 $.ajaxFileUpload
 (
  {
   url:'/orderhist/upload?companyId='+$("#entityId").val(),
   secureuri:false,
   fileElementId:'fileToUpload',
   dataType: 'json',
   beforeSend:function()
   {
    $("#loading").show(); //该图用来显示上传图片ing
   },
   complete:function()
   {
    $("#loading").hide();//隐藏该图
   },    
   success: function (data, status)
   {
    if(typeof(data.error) != 'undefined')
    {
     if(data.error != '')
     {
      alert(data.error);
     }else
     {
      alert(data.msg);
     }
    }
   },
   error: function (data, status, e)
   {
    alert(e);
   }
  }
 );
 
 return false;

}