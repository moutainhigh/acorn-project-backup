var parmInvoiceQuery={xxx:0};
var totalInvoiceCount=-1;
$(function() {
    var orderlistInit=false;
    $('#invoiceSubmit_table').datagrid({
        title:'',
        iconCls:'icon-edit',
        width : '100%',
        height : 410,
        url : '/invoice/query',
//        fitColumns : true,
        striped: true,
        border: true,
        collapsible:true,
        scrollbarSize:-1,
        columns:[[
            {field:'tuid',title:'ID',width:0,editor:'text', hidden: true},
            {field:'orderid',title:'订单编号',align:'center',width:60,editor:'text',formatter:function (value,row,index){
                var token='<a class="link" href="javascript:void(0);" onclick="javascript:viewOrder('+'\'myOrderlist\','+index+','+row.orderid+',0 )" > ';
                token+=value;
                token+='</a>';

                return token;
            }},
            {field:'status',title:'处理状态',align:'center',width:60,editor:'text',formatter:function (value){
                if(value==2)
                {
                    return "确定";
                }
                else if(value==3)
                {
                    return "取消";
                }
                else
                    return "申请";
            }},
            {field:'title',title:'抬头',width:60,editor:'text'},
            {field:'mailtype',title:'订购方式',width:60,editor:'text'},
            {field:'freetype',title:'发票打印类型',width:80,editor:'text'},
            {field:'baonum',title:'寄出包裹号',width:80,editor:'text'},
            {field:'prodname',title:'产品名称',align:'center',width:80,editor:'text'},
            {field:'prodscode',title:'产品简码',width:80,editor:'text'},
            {field:'prodprice',title:'产品价格',width:80,align:'right',editor:'text', formatter:function(value){
                return formatPrice(value);
            }},
            {field:'mailprice',title:'运费',width:80,align:'right',editor:'text', formatter:function(value){
                return formatPrice(value);
            }},
            {field:'ordprice',title:'订单总价',width:80,align:'right',editor:'text', formatter:function(value){
                return formatPrice(value);
            }},
            {field:'address',title:'联系地址',align:'center',width:180,editor:'text'},
            {field:'comments',title:'备注',align:'center',width:180,editor:'text'},
            {field:'rdsc',title:'退回原因',align:'center',width:80,editor:'text'},
            {field:'applicant',title:'发票申请人',align:'center',width:80,editor:'text'},
            {field:'createdat',title:'申请日期',align:'center',width:120,editor:'text'},
            {field:'modifyby',title:'处理人',align:'center',width:80,editor:'text'},
            {field:'modifydat',title:'处理日期',align:'center',width:120,editor:'text'}
        ]],
        remoteSort:false,
        singleSelect:false,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination:true,
        queryParams: parmInvoiceQuery,
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
                totalInvoiceCount=data.total;
            }
            $('.disable-linkbutton').linkbutton({disable:'true', plain:true});
            $('.enable-linkbutton').linkbutton({color:'blue', plain:true});
        },
        onLoadError: function(){
            window.parent.alertWin('系统提示', "加载失败!");
        }

    });

    var p = $('#invoiceSubmit_table').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    $('#invoice_query').click(function(){
        if(!$("#invoice_form").form("validate"))
        {
            console.debug("invalidate data!!!");
            return;
        }

        totalInvoiceCount=-1;
        parmInvoiceQuery={
                'orderId' : getNotEmptyString($("#orderid_invoice").val().replace(/^\s+|\s+$/g, "")),
                'beginDate' : $("#tbBeginCrdt_invoice").datebox('getValue'),
                'endDate' : $("#tbEndCrdt_invoice").datebox('getValue'),
                'status': getNotEmptyString($("#tbStatus_invoice").combobox('getValue')),
                'countRows':totalInvoiceCount
            };
        $('#invoiceSubmit_table').datagrid('load',parmInvoiceQuery);
    });

    $('#invoice_export').click(function(){
        var queryParams={
            'orderId' : getNotEmptyString($("#orderid_invoice").val().replace(/^\s+|\s+$/g, "")),
            'beginDate' : $("#tbBeginCrdt_invoice").datebox('getValue'),
            'endDate' : $("#tbEndCrdt_invoice").datebox('getValue'),
            'countRows':totalInvoiceCount
        };

        document.location.href = '/invoice/export?'+$.param(queryParams);
    });
})