$(function(){
    $('#refundInfo').datagrid({
        width : '100%',
        height:438,
        fitColumns:true,
        rownumbers:true,
        url:'',
        scrollbarSize:-1,
        onDblClickRow:function(index, row){
            editRufendsms();
        },
//        queryParams:{
//            refundtype :""
//        },
        columns : [ [
            {field:'ck',checkbox:true,resize:false},
            {field:'id',hidden:true},
            {
                width:100,
                align:'center',
                field : 'orderid',
                title : '订单编号'
            },
            {
                width:100,
                align:'center',
                field : 'refundtype',
                title : '退款分类',
                formatter : function(value, rowData, rowIndex) {
                    if (rowData.refundtype=='1') {
                        return  "邮政退款";
                    }
                    else if (rowData.refundtype=='2'||rowData.refundtype=='') {
                        return  "网银退款";
                    }
                    else if (rowData.refundtype=='3') {
                        return  "信用卡退款";
                    }
                    else if (rowData.refundtype=='4') {
                        return  "支付宝退款";
                    }
                }
            },
            {
                width:100,
                field : 'usrname',
                align:'center',
                title : '客户姓名'
            },
            {
                width:100,
                field : 'bankname',
                align:'center',
                title : '开户行/退款地址'
            },
            {
                width:100,
                align:'center',
                field : 'bankcardnum',
                title : '银行卡号/邮政编码',
                formatter:function(value, rowData){
                    if (rowData.refundtype=='2') {
                        return  rowData.bankcardnumMap;
                    }
                    else
                    {
                        return value;
                    }
                }
            },
            {
                width:100,
                field : 'holdername',
                align:'center',
                title : '持卡人'
            },
            {
                width:100,
                field : 'phone',
                align:'center',
                title : '电话'
            },
            {
                width:100,
                field : 'money',
                align:'right',
                title : '退款金额',
                formatter:function(value){
                    return refundFormatPrice(value);
                }
            },
            {
                width:100,
                field : 'crusr',
                align:'center',
                title : '创建者'
            },
            {
                width:100,
                field : 'crdt',
                align:'center',
                title : '创建时间',
                formatter:function(value){
                    var _date = new Date(value);
                    return _date.format('yyyy-MM-dd ');
                }
            },
            {
                width:100,
                field : 'mdusr',
                align:'center',
                title : '更改人'
            },
            {
                width:100,
                align:'center',
                field : 'mddt',
                title : '更改时间',
                formatter:function(value){
                    if(value==""||value == null) {
                        return "";
                    } else {
                        var _date = new Date(value);
                        return _date.format('yyyy-MM-dd ');
                    }

                }
            }
        ] ],
        remoteSort : false,
        singleSelect : true,
        pagination : true,
        pageList : [ 5, 10, 15 ],
        pageSize:15
    });

    // 查询按钮
    $("#searchRefund").click(function() {
        if(!$("#refundsmsForm").form("validate"))
        {
            console.debug("refundsmsForm invalidate data!!!");
            return;
        }

        if ($("#orderids").val()!="") {
            $('#refundInfo').datagrid( {
                url:'/refundsms/queryList',
                queryParams:{
                    refundtype :"",
                    orderid :  $("#orderids").val()
                }
                }
            );
        } else {
            $('#refundInfo').datagrid( {
                    url:'/refundsms/queryList',
                    queryParams:{
                        refundtype :$("#refundTypes").combobox('getValue'),
                        orderid :  $("#orderids").val(),
                        startDate:$("#refund_startDate").datebox("getValue"),
                        endDate:$("#refund_endDate").datebox("getValue")
                    }
                }
            );
        }

    });





});

function sendSms() {
    var row = $("#refundInfo").datagrid("getSelected");
    var refund =  $("#refundTypes").combobox('getValue');
    if(row) {
        if (refund!="1"&&refund!="2") {
            window.parent.alertWin('系统提示',"该类型不能发送短信");
            return ;
        } else {
            $.post("/refundsms/sendSms", {
                "id":row.id,
                "orderid":row.orderid,
                "contactid":row.contactid,
                "recalltype":row.recalltype
            }, function(data) {
                if (data.succ=="succ") {
                    window.parent.alertWin('系统提示', "发送成功");
                }else {
                    window.parent.alertWin('系统提示', data.msg);
                }
                $('#refundInfo').datagrid("load");
            }, 'json');

        }
    }

}
function cancleRefund () {
    var row = $("#refundInfo").datagrid("getSelected");
    if(row) {
        $.post("/refundsms/cancel", {
            "id":row.id
        }, function(data) {
            if (data.succ=="succ") {
                window.parent.alertWin('系统提示', "取消成功");
            }else {
                window.parent.alertWin('系统提示', data.msg);
            }
            $('#refundInfo').datagrid("load");
        }, 'json');
    }
}


/**
 * 时间对象的格式化;
 */
Date.prototype.format = function(format) {
    /*
     * eg:format="yyyy-MM-dd hh:mm:ss";
     */
    var o = {
        "M+" : this.getMonth() + 1, // month
        "d+" : this.getDate(), // day
        "h+" : this.getHours(), // hour
        "m+" : this.getMinutes(), // minute
        "s+" : this.getSeconds(), // second
        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
        "S" : this.getMilliseconds()
        // millisecond
    };

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
            - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

//弹出窗口
function showWindow(options,winPop){
    if (options && options.width && options.height){
        options.top=($(window).height() - options.height) * 0.5;
        options.left=($(window).width() - options.width) * 0.5;
    }
    if(winPop){
        winPop="#"+winPop;
        jQuery(winPop).window(options);
        jQuery(winPop).window('open');
    }else{
        jQuery("#MyPopWindow").window(options);
        $('#MyPopWindow').window('open');
    }
}




function editRufendsms(){
    var row = $('#refundInfo').datagrid('getSelected');
    showWindow({
        title:'退款管理',
        href:'/refundsms/openwindow?contactid='+row.contactid+'&orderid='+row.orderid,
        width: 500,
        onLoad: function(){

        }
    },"drawbackDialog");

}

function refundFormatPrice(price)
{
    if(price==null||price=='')
        return '0.00';
    var prc=price;
    return prc.toFixed(2);

}

$.extend($.fn.validatebox.defaults.rules,{
    refundquerydatevalidator:{
        validator: function(value)
        {
            var date = $.fn.datebox.defaults.parser(value);
            var s = $.fn.datebox.defaults.formatter(date);
            return s==value;
        },message: '日期无效'
    }
});





