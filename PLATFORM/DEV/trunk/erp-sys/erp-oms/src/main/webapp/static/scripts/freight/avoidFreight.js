$(function() {

    $('#shipmentHeaderTable')
        .datagrid(
        {
            title : '',
            iconCls : 'icon-edit',
            width :'100%',
            height : 400,
            nowrap : false,
            striped : true,
            border : true,
            collapsible : false,
            fitColumns:true,
            scrollbarSize:0,
           // url : '/freight/list',
            queryParams:{
                shipmentId:$("#shipmentId").val(),
                mailId:$("#mailId").val()
            },

            columns : [ [
                {
                    field : 'id',
                    title : 'ID',
                    width : 100,
                    hidden:true
                },
                {
                    field : 'senddt',
                    title : '出库日期',
                    width : 120
                },
                {
                    field : 'shipmentId',
                    title : '发运单号',
                    width : 130
                },
                {
                    field : 'mailId',
                    title : '邮件号',
                    width : 135
                },
                {
                    field : 'orderType',
                    title : '订单类型',
                    width : 100
                },
                {
                    field : 'totalPrice',
                    title : '订单金额',
                    width : 90
                },
                {
                    field : 'mailPrice',
                    title : '原始运费',
                    width : 90
                },
                {
                    field : 'cutFreight',
                    title : '减免后的运费',
                    width : 90
                },
                {
                    field : 'name',
                    title : '客户名称',
                    width : 80
                },
                {
                    field : 'addrdesc',
                    title : '地址',
                    width : 210
                }
            ] ],
            frozenColumns:[[
                {field:'ck',checkbox:true }
            ]],
            remoteSort : false,
            singleSelect : true,
            pagination : true,
            rownumbers : true
        });

    var p = $('#shipmentHeaderTable').datagrid('getPager');
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
        var orderId = $("#shipmentId").val();
        var mailId = $("#mailId").val();
        if(!orderId && !mailId)
        {
            alert("请至少输入一个查询条件!");
            return false;
        }
        $('#shipmentHeaderTable').datagrid({url:"/freight/list",queryParams:{
            shipmentId:$("#shipmentId").val(),
            mailId:$("#mailId").val()
        }});
    });
    //编辑运费
    $("#btnEdit").click(function(){
        var row = $('#shipmentHeaderTable').datagrid('getSelected');
        if(row)
        {
           //alert("编辑id: "+row.id);
            $('#dlg').window({
                title:'编辑运费',
                width:260,
                height:160,
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
            //初始化界面 原始运费
            $('#fm').form('load',row);
            $("#Id").val(row.id);
            $("#newFreight").text(row.mailPrice);
        }
        else
        {
            alert("请选择一条数据!")
        }
    });
    //保持编辑运费按钮
    $("#lbSave").click(function(){

        $('#fm').form('submit',{
            url: "save",
            onSubmit: function(){
            	var b = $(this).form('validate');
            	if(b){
                    $.messager.progress({  
                        title:'请稍等',  
                        msg:'处理中...',
                        text:''
                    }); 
            	}
                return b;
            },
            success: function(result){
                var result = eval('('+result+')');
                if (result.errorMsg){
                    $.messager.alert('警告','操作失败：'+result.errorMsg,'warning');
                }
                $('#dlg').dialog('close');      // close the dialog
                $('#afterFreight').numberbox('setValue',"");
                $('#shipmentHeaderTable').datagrid('reload');    // reload the user data
                $.messager.progress('close');
                $.messager.show({  
                    title:'提示',  
                    msg:'免运费修改成功',  
                    timeout:5000,  
                    showType:'slide'  
                }); 
            }

        });

    });
    //取消编辑运费
    $("#lbClose").click(function(){
        $('#dlg').dialog('close');
    });

});


