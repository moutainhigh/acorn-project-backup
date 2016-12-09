/**
 * 库内变更承运商
 * User: gaodejian
 * Date: 12-12-5
 * Time: 下午1:43
 * To change this template use File | Settings | File Templates.
 */
$(function(){
    $("#tbImpdt1").datetimebox('setValue',currentTime());
    $("#tbImpdt2").datetimebox('setValue',currentTime()+" 23:59:59");
    $("#tbCardtype").combobox({
        url:'/order/cardtype/lookup',
        valueField: 'id',
        textField: 'name'
    });

 
	
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
            {field:'orderid',title:'订单编号',width:220,editor:'text'},
            {field:'orderdetid',title:'订单明细编号',width:220,editor:'text'},
            {field:'prodbankid',title:'商品编号',width:120,editor:'text'},
            {field:'scode',title:'产品简码',width:180,editor:'text'},
            {field:'cardrightnum',title:'索权号',width:120,editor:'text'},
            {field:'prodprice',title:'金额',width:130,editor:'text',align:'right'},
            {field:'impdt',title:'导入日期',width:130,editor:'text'},
            {field:'impusr',title:'导入工号',width:130,editor:'text'}
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
            cardtype: $("#tbCardtype").combobox('getValue'),
            orderid: $("#tbOrderid").val(),
            cardrightnum: $("#tbCardrightnum").val(),
            impdt1: $("#tbImpdt1").datetimebox('getValue'),
            impdt2: $("#tbImpdt2").datetimebox('getValue'),
            confirm:  $("#tbAuth").attr('checked') == 'checked' ? "0" : ""
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

        }
    });

    var p = $('#dg').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    //查询
    $("#btnSearch").click(function () {
        $("#tbOrderid").val($("#tbOrderid").val().replace(/^\s+|\s+$/g, ""));
        $("#tbCardrightnum").val($("#tbCardrightnum").val().replace(/^\s+|\s+$/g, ""));

        var params = {
            cardtype: $("#tbCardtype").combobox('getValue'),
            orderid: $("#tbOrderid").val(),
            cardrightnum: $("#tbCardrightnum").val(),
            impdt1: $("#tbImpdt1").datetimebox('getValue'),
            impdt2: $("#tbImpdt2").datetimebox('getValue'),
            confirm:  $("#tbAuth").attr('checked') == 'checked' ? "0" : ""
        }

        if(params.cardtype && params.cardtype!="" ||
           params.orderid && params.orderid!="" ||
           params.cardrightnum && params.cardrightnum!="" ||
           params.impdt1 && params.impdt1!="" ||
           params.impdt2 && params.impdt2!="" ||
           params.confirm && params.confirm!="") {
           $('#dg').datagrid('load',params);
        }
        else
        {
            alert("请至少输入一个查询条件");
        }
    });
    //变更
    $("#lbImport").click(function () {
        $('#dlg').window({title:'索权文件导入',
            width:500,
            height:100,
            iconCls:'',
            top:($(window).height() - 200) * 0.5,
            left:($(window).width() - 500) * 0.5,
            shadow: true,
            modal:true,
            closed:true,
            minimizable:false,
            maximizable:false,
            collapsible:false
        }).window('open');

    });

    $("#lbCapture").click(function(){
        var rows = $('#dg').datagrid('getSelections');
        if (rows && rows.length > 0){
            var ids = new Array();
            var numIds = new Array();
            for(var i = 0; i < rows.length; i++){
                ids.push(rows[i].orderid);
               // moneys.push($('#dg').datagrid('getChecked')[i].prodprice);
                numIds.push(rows[i].cardrightnum);
            }

            $.post("/order/auth/capture",{ orderid: ids,cardrightnum:numIds },
                function(result){
                    var result = eval('('+result+')');

                    if (result.errorMsg){
                        //$.messager.alert("信息提示",result.errorMsg);
                        messageInfoShow();
                        $("#p").html(result.errorMsg);
                    } else if (result.success){
                      // $.messager.alert("信息提示",tmp);
                        messageInfoShow();
                        $("#p").html(result.success);
                    }
                }
            );
        }
        else
        {
            alert("请先选择一笔信用卡授权记录!");
        }
    });

    $("#lbClose").click(function(){
        $('#dlg').dialog('close');
    });

});

//索权信息提示
function messageInfoShow(){
    $('#messageInfo').window({
        title:'信息提示',
        width:435,
        height:200,
        iconCls:'',
        top:($(window).height() - 200) * 0.5,
        left:($(window).width() - 500) * 0.5,
        shadow: true,
        modal:true,
        closed:true,
        minimizable:false,
        maximizable:false,
        collapsible:false
    })
        .window('open');
}
//初始化时间
function currentTime(){
    var d = new Date(),str = '';
    str += d.getFullYear()+'-';
    str += d.getMonth() + 1+'-';
    str += d.getDate();
    return str;
}
