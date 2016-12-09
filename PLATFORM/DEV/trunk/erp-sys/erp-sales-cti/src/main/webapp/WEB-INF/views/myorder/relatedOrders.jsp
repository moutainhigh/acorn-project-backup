<%@include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<script type="text/javascript">
function showRelatedOrders(orders) {
	window.parent.popConfirm(frameElement.id, 'id_related_${param.id}', {title: '关联单提示', height: 380});
	$("#id_related_table_${param.id}", window.parent.document).datagrid({
		width: '90%',
        height: 300,
        data: orders,
        singleSelect: false,
        fitColumns: true,
        columns: [[
            {field: 'orderId', title: '订单号', width: 80, 
            	formatter: function(value) {
            		return '<a class="link" onclick="openOrder(\'' + value + '\', \'${param.id}\')">' + value + '</a>';
            	}
            },
            {field: 'createTime', title: '创建时间', width: 150},
            {field: 'status', title: '订单状态', width: 100},
            {field: 'productPrice', title: '商品总价', width: 80},
            {field: 'mailPrice', title: '运费', width: 80},
            {field: 'totalPrice', title: '订单金额', width: 80},
            {field: 'crusr', title: '生成座席', width: 100},
            {field: 'grpid', title: '座席组', width: 100}
        ]],
        view: detailview,
        detailFormatter: function(index, row){
            return '<div style="padding:2px"><table id="orderdet_table_' + index + '"></table></div>';
        },
        onExpandRow: function(index, row){
            $('#orderdet_table_' + index, window.parent.document).datagrid({
                url: '${ctx}/myorder/orderDetails/get/details/' + row.orderId,
                fitColumns: true,
                singleSelect: true,
                height: 'auto',
                columns: [[
                    {field: 'prodid', title: '商品编号', width: 80},
                    {field: 'prodname', title: '商品名称', width: 150},
                    {field: 'productTypeName', title: '规格', width: 80},
                    {field: 'price', title: '价格', width: 70},
                    {field: 'num', title: '数量', width: 50},
                    {field: 'jifen', title: '积分', width: 50},
                    {field: 'soldwith', title: '销售方式', width: 70}
                ]],
                onResize: function() {
                    $("#id_related_table_${param.id}", window.parent.document).datagrid('fixDetailRowHeight', index);
                },
                onLoadSuccess:function(){
                    setTimeout(function(){
                        $("#id_related_table_${param.id}", window.parent.document).datagrid('fixDetailRowHeight', index);
                    },0);
                }
            });
            $("#id_related_table_${param.id}", window.parent.document).datagrid('fixDetailRowHeight',index);
        }
	});
}
</script>
<div id="id_related_${param.id}" class="easyui-window" title="关联单提示" style="width: 700px; height: 240px;"
	data-options="modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true,draggable:false">
	<table id="id_related_table_${param.id}"></table>
	<p class="mt10 textC"><a class="phoneNums_btns" style="width: 100px;" onclick="relatedCallback('${param.id}', '${param.callbackMethod}')">关闭</a></p>
</div>