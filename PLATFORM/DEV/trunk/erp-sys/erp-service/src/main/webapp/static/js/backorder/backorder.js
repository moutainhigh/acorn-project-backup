var parmBackorderQuery={xxx:0};
var totalBackorderCount=-1;
$(function() {
    $('#omsbackorder_table').datagrid({
        title:'',
        iconCls:'icon-edit',
        width : '100%',
        height : 410,
        url : '/myorder/backorder/query',
//        fitColumns : true,
        striped: true,
        border: true,
        collapsible:true,
        scrollbarSize:-1,
        columns:[[
            {field:'orderid',title:'订单编号',align:'center',width:60,editor:'text',formatter:function (value,row,index){
                var token='<a class="link" href="javascript:void(0);" onclick="javascript:viewOrder('+'\'myOrderlist\','+index+','+row.orderid+',0 )" > ';
                token+=value;
                token+='</a>';

                return token;
            }},
            {field:'crdt',title:'订单生成时间',width:140,editor:'text'},
            {field:'status',title:'订单状态',align:'center',width:80,editor:'text',formatter:function (value){
                for(var i= 0; i<orderStatuses.length; i++){
                    if (orderStatuses[i].id == value || (!value && orderStatuses[i].id == "0")) return orderStatuses[i].dsc;
                }
                return value;
            }},
            {field:'reason',title:'压单原因',width:220,editor:'text'},
            {field:'iscan',title:'需坐席处理',width:80,editor:'text'},
            {field:'scmreason',title:'原因',width:60,editor:'text'},
            {field:'prod',title:'需处理商品',width:80,editor:'text'},
            {field:'expired',title:'最晚处理时间',width:140,editor:'text'},
            {field:'crusr',title:'创建人',width:80,editor:'text'}
        ]],
        remoteSort:false,
        singleSelect:false,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination:true,
        queryParams: parmBackorderQuery,
        onBeforeLoad:function(data)
        {
            if(data==null||data.xxx!=null)
                return false;
        },
        onLoadSuccess: function(data){
            if(data.err!=null)
            {
                window.parent.alertWin('系统提示', data.err);
            }
            if(data.total!=null)
            {
                totalBackorderCount=data.total;
            }
            $('.disable-linkbutton').linkbutton({disable:'true', plain:true});
            $('.enable-linkbutton').linkbutton({color:'blue', plain:true});
            $('#omsbackorder_query').removeClass('l-btn-disabled');
            $('#omsbackorder_query').one("click", function(){loadBackorders()});
        },
        onLoadError: function(){
            window.parent.alertWin('系统提示', "加载失败!");
            $('#omsbackorder_query').removeClass('l-btn-disabled');
            $('#omsbackorder_query').one("click",function(){loadBackorders()});
        }

    });

    /*var p = $('#omsbackorder_table').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });*/

    $('#omsbackorder_query').one("click",function(){
        loadBackorders();
    });

});

function loadBackorders()
{
    totalBackorderCount=-1;
    $('#omsbackorder_query').addClass('l-btn-disabled');
    parmBackorderQuery={
        'orderId' : getNotEmptyString($("#omsbackorder_orderid").val().replace(/^\s+|\s+$/g, "")),
        'countRows':totalBackorderCount
    };
    $('#omsbackorder_table').datagrid('load',parmBackorderQuery);
}