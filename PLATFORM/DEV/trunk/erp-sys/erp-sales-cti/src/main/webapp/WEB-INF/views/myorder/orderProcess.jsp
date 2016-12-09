<%@include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<script type="text/javascript">
function showOrderProcess(orderId, title) {
	$("#${empty param.orderId ? '' : param.orderId}_logistics_table").datagrid({  
	    url: '${ctx}/myorder/orderDetails/logistics?orderId=' + orderId,
	    singleSelect: true,
	    fitColumns: true,
        fit: true,
	    scrollbarSize: -1,
        nowrap: false,
	    height: 245,
	    width: 450,
	    columns: [[  
	        {field: 'opDate', title: '时间', width: 100},
	        {field: 'opDsc', title: '描述', width: 200},
	        {field: 'opUser', title: '操作人', width: 50, align: 'center'},
	        {field: 'mailId', title: '包裹号', width: 50, align: 'center'},
	        {field: 'company', title: '承运商', width: 50, align: 'center'}
	    ]]
	});
	
	$("#${empty param.orderId ? '' : param.orderId}_track_table").datagrid({  
	    url: '${ctx}/myorder/orderDetails/track?orderId=' + orderId,
	    singleSelect: true,
	    fitColumns: true,
        fit: true,
	    scrollbarSize: -1,
        nowrap: false,
	    height: 245,
	    width: 450,
	    columns: [[  
	        {field: 'createDate', title: '创建时间', width: 100},
	        {field: 'remark', title: '跟单备注', width: 200},
	        {field: 'createUser', title: '创建座席编号', width: 75, align: 'center'},
	        {field: 'agentName', title: '创建座席名', width: 75, align: 'center'}
	    ]]
	});
	
	$("#id_order_process_${empty param.orderId ? '' : param.orderId}").window('open');
	if (title) {
		$('#tabs').tabs('select', title);
	}
}
</script>

<div id="id_order_process_${empty param.orderId ? '' : param.orderId}" class="easyui-window" title="订单处理记录查询" 
	data-options="top:70,width:700,height:400,modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,closed:true,resizable:false,draggable:false">
	<div id="tabs" class="easyui-tabs" fit="true" style="height: auto">
		<div title="物流查询">
			<table id="${empty param.orderId ? '' : param.orderId}_logistics_table"></table>
		</div>
		<div title="跟单查询">
			<table id="${empty param.orderId ? '' : param.orderId}_track_table"></table>
		</div>
	</div>
</div>
