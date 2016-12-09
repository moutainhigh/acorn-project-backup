
$(function () {
    //  $('#startDate').datepicker({dateFormat:"yy-mm-dd"});
//  $('#endDate').datepicker({dateFormat:"yy-mm-dd"});
$('#listCouponTable').datagrid({
    title:'',
    iconCls:'icon-edit',
    width: 700,
    height: 'auto',
    nowrap: false,
    striped: true,
    border: true,
    collapsible:false,
    fit: true,
    url:'/coupons/query',
    //sortName: 'code',
    //sortOrder: 'desc',
    remoteSort:false,
    idField:'couponId',
    singleSelect:false,
    pagination:true,
    rownumbers:true,
    frozenColumns:[[
    {field:'ck',checkbox:true}
]],
queryParams: {
    couponCode: $("#qCouponCode").val(),
    couponName: $("#qCouponName").val()
    }
});

var p = $('#listCouponTable').datagrid('getPager');
$(p).pagination({
    pageSize: 10,
    pageList: [5,10,15],
    beforePageText: '',
    afterPageText: 'page    total {pages}page',
displayMsg: 'current {from} - {to} records total {total} records'
/*onBeforeRefresh:function(){
    $(this).pagination('loading');
    alert('before refresh');
    $(this).pagination('loaded');
    }*/
});
var tabView = new YAHOO.widget.TabView('product_desc');
});
