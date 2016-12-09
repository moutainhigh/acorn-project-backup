/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-5
 * Time: 下午1:43
 * To change this template use File | Settings | File Templates.
 */

Date.prototype.format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

$(function(){

    var statuses = [
        {id:'0',name:'未处理'},
        {id:'1',name:'自动取消'},
        {id:'2',name:'坐席取消'}
    ];

    
    $('#dg').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height:400,
        striped: true,
        border: true,
        collapsible:true,
        fitColumns:true,
        scrollbarSize:0,
        columns:[[
            {field:'id',title:'序列',width:160,editor:'text',hidden:true},
            {field:'orderid',title:'订单编号',width:160,editor:'text'},
            {field:'prodId',title:'产品编号',width:200,editor:'text'},
            {field:'prodName',title:'产品名称',width:150,editor:'text'},
            {field:'spec',title:'产品规格',width:60,editor:'text'},
            {field:'reason',title:'压单原因',width:150,editor:'text'},
            {field:'createdTime',title:'导入时间',width:140,editor:'datebox',
                formatter: function(value) {
                    if(value != null){
                        return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                    }
                    return value;
                }
            },
            {field:'expired',title:'到期时间',width:140,editor:'datebox',
                formatter: function(value) {
                    if(value != null){
                        return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                    }
                    return value;
                }
            },
            {field:'status',title:'处理状态',width:100,editor:'text',
                formatter: function(value) {
                    for(var i=0; i<statuses.length; i++){
                        if (statuses[i].id == value) return statuses[i].name;
                    }
                    return value;
                },
                sortable:true
            }
        ]],
        frozenColumns:[[
            {field:'ck',checkbox:true}
        ]],
        remoteSort:false,
        singleSelect:false,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination:true,
        rownumbers:true,
        queryParams: {
            orderId: $("#oi").val(),
            productId: $("#pi").val(),
            status: $("#st").val(),
            startDate: $("#sd").datetimebox('getValue'),
            endDate: $("#ed").datetimebox('getValue')
        },
        onLoadSuccess: function(data){
            //alert(JSON.stringify(data));
        },
        onLoadError: function(){
            alert("加载失败!");
        },
        onUncheck:function(index,data){
            $(this).datagrid("clearSelections");
        },
        onDblClickRow:function(rowIndex, rowData){
            //$("#lbShow").click();
        }
    });

    var p = $('#dg').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
        /*onBeforeRefresh:function(){
         $(this).pagination('loading');
         alert('before refresh');
         $(this).pagination('loaded');
         }*/
    });

    $("#btnSearch").click(function () {

        $("#oi").val($("#oi").val().replace(/^\s+|\s+$/g, ""));
        $("#pi").val($("#pi").val().replace(/^\s+|\s+$/g, ""));

        $('#dg').datagrid('load',{
            orderId: $("#oi").val(),
            productId: $("#pi").val(),
            status: $("#st").val(),
            startDate: $("#sd").datetimebox('getValue'),
            endDate: $("#ed").datetimebox('getValue')
        });
    });

    $("#lbDelete").click(function(){
        var rows = $('#dg').datagrid('getSelections');
        if (rows && rows.length > 0){
            $.messager.confirm('确认','当前选中的'+rows.length+'条记录，真的要删除吗? ',function(r){
                if (r){
                    for(var i = 0; i< rows.length; i++){
                        var row = rows[i];
                        $.post('/order/backorder/delete',{ id:row.id },function(result){
                            if (result.success){
                                var rowIndex = $("#dg").datagrid("getRowIndex",row);
                                if(rowIndex > -1){
                                    $("#dg").datagrid("deleteRow",rowIndex);
                                }
                                $('#dg').datagrid('reload');    // reload the user data
                            } else {
//                                $.messager.show({   // show error message
//                                    title: 'Error',
//                                    msg: result.errorMsg
//                                });
                                $.messager.alert('错误',result.errorMsg,'error');
                            }
                        },'json');
                    }
                }
            });
        }
        else
        {
            alert("请先选择一条压单数据!");
        }
    });

    $("#lbDefer").click(function(){
        var rows = $('#dg').datagrid('getSelections');
        if (rows && rows.length > 0){
            $.messager.prompt('提示，当前选中的'+rows.length+'条记录','请输入延迟天数',function(r){
                if(!r || isNaN(r)) {
                    $.messager.alert("错误", "延迟天数必须是一个数字!");
                }else {
                    for(var i = 0; i< rows.length; i++){
                        var row = rows[i];
                        $.post('/order/backorder/defer',{ id:row.id, days: r },function(result){
                            if (result.success){
                                $('#dg').datagrid('reload');    // reload the user data
                            } else {
//                                $.messager.show({   // show error message
//                                    title: 'Error',
//                                    msg: result.errorMsg
//                                });
                                $.messager.alert('错误',result.errorMsg,'error');
                            }
                        },'json');
                    }
                }
            });
        }
        else
        {
            alert("请先选择一条压单数据!");
        }
    });

    var d1 = new Date();
    $('#sd').datetimebox('setValue', d1.getFullYear()+'-'+(d1.getMonth()+1)+'-'+d1.getDate()+' 00:00:00');
    $('#ed').datetimebox('setValue', d1.getFullYear()+'-'+(d1.getMonth()+1)+'-'+d1.getDate()+' 23:59:59');
});
