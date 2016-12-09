$(function(){

    cmpfback_init();

    /*================================协办反馈==================================*/
    $('#table_cmpfback').datagrid({
        width : '100%',
        height : 438,
        url:"/cmpfback/list",
        fitColumns : true,
        rownumbers:true,
        nowrap:false,
        scrollbarSize:-1,
        columns : [ [
            {field : 'caseid',align:'center',title : '事件编号',width:100},
            {field : 'contactid',align:'center',title : '客户编号',width:100},
            {field : 'contactName',align:'center',title : '客户姓名',width:100},
            {field : 'orderid', align:'center',title : '订单编号',width:100},
            {field : 'productdsc', align:'center',title : '产品描述',width:100},
            {field : 'ordercrdt',align:'center',title : '订购日期',width:120,formatter:dateFormatter},
            {field : 'probdsc', align:'center',title : '投诉内容',width:180},
            {field : 'yijian',align:'center',title : '部门',width:130},
            {field : 'yijian2',align:'center',title : '业务',width:130},
            {field : 'crusr',align:'center',title : '受理人',width:100},
            {field : 'crdt',align:'center',title : '受理时间',width:120,formatter:dateFormatter},
            {field : 'chuliqk', align:'center',title : '处理情况',width:100},
            {field : 'chuliusr',align:'center',title : '处理人',width:100},
            {field : 'fbdt',align:'center',  title : '反馈日期',width:120,formatter:dateFormatter}
        ] ],
        remoteSort : false,
        singleSelect : true,
        pagination : true,
        pageList : [ 5, 10, 15 ],
        pageSize:15,

        rowStyler: function(index,row){
            if (row.exigency == '0') {
                return 'background-color:#FFC1C1';
            } else if (row.exigency == '1') {
                return 'background-color:#fcffa6';
            }
        },onDblClickRow:function(rowIndex, rowData){
            cmpfback_task_support ();
        }
    });

    var campaignTaskPager = $('#table_cmpfback').datagrid('getPager');
    $(campaignTaskPager).pagination({
        beforePageText : '第',
        afterPageText : '页    共 {pages} 页',
        displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onBeforeRefresh : function() {
            $(this).pagination('loading');
            $(this).pagination('loaded');
        }
    });

    //查询
    $('#btn_cmpfback_query').click(function(){
        var startDate =  $('#cmpfback_startDate').datebox('getValue');
        var endDate =  $('#cmpfback_endDate').datebox('getValue');
        if((new Date(endDate)-new Date(startDate))/86400000>30){
            window.parent.alertWin("警告","查询时间跨度为30天");
            return;
        }
        $('#table_cmpfback').datagrid('reload',{
            startDate :startDate,
            endDate : endDate,
            contactid : $("#cmpfback_customerID").val(),
            crusr : $('#cmpfback_crusr').val(),
            orderid : $('#cmpfback_orderId').val(),
            productdsc : $('#cmpfback_productdsc').val(),
            yijian : $('#cmpfback_yijian').val(),
            result:$("#cmpfback_result").val()
        });
    });

    //清空
    $('#btn_cmpfback_clear').click(function(){
        $('#cmpfbackForm')[0].reset();
        $('#cmpfback_startDate').datebox('setValue', new Date(new Date()-7*86400000).format('yyyy-MM-dd'));
        $('#cmpfback_endDate').datebox('setValue', new Date().format('yyyy-MM-dd'));
    });

});

function dateFormatter(value){
        if(value){
            return new Date(value).format('yyyy-MM-dd');
        }
}
function cmpfback_init(){
    $("#cmpfback_startDate").datebox({
        required: "true",
        missingMessage: "必填项",
        value: new Date(new Date()-7*86400000).format('yyyy-MM-dd')

    });
    $("#cmpfback_endDate").datebox({
        required: "true",
        missingMessage: "必填项",
        value: new Date().format('yyyy-MM-dd')
    });
}

function cmpfback_task_support (){
    var row = $("#table_cmpfback").datagrid("getSelected");
    if (row && row.caseid) {
        eventCmpfbackDialog(row.caseid)

    }else{
        window.parent.alertWin("警告","事件编号为空，请刷新页面!");
    }

}