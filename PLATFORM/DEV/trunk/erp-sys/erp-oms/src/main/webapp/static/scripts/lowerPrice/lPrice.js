$(function() {
    $('#shipmentHeaderTable') .datagrid(
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
            url:"/lower/price/load",
            /*onBeforeLoad:function(){
                var orderId = $("#shipmentId").val();
                var mailId = $("#mailId").val();
                return !(!orderId && !mailId);
            },*/
            queryParams:{
                shipmentId:$("#shipmentId").val(),
                mailId:$("#mailId").val()
            },
            columns : [ [
                {
                    field : 'orderId',
                    title : '订单ID',
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
                    title : '订单商品总金额',
                    width : 100
                },
                {
                    field : 'mailPrice',
                    title : '订单运费',
                    width : 100
                },
                {
                    field : 'lowerPrice',
                    title : '全渠道订单最低商品总金额',
                    width : 120
                },
                {
                    field : 'alertAmount',
                    title : '订单商品折扣后金额',
                    width : 100
                },
                {
                    field : 'name',
                    title : '客户名称',
                    width : 100
                },
                {
                    field : 'addrdesc',
                    title : '地址',
                    width : 150
                }
            ] ],
            frozenColumns:[[
                {field:'ck',checkbox:true }
            ]],
            remoteSort : false,
            singleSelect : true,
            pagination : true,
            rownumbers : true,
            onLoadError: function(){
                alert("加载失败!");
            }
        });

    var p = $('#shipmentHeaderTable').datagrid('getPager');
    $(p).pagination({
        pageSize : 10,
        pageList : [ 5, 10, 15 ],
        beforePageText : '第',
        afterPageText : '页    共 {pages} 页',
        displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
    });

    // 查询按钮
    $("#queryOrderBtn").click(function() {
        var orderId = $("#shipmentId").val();
        var mailId = $("#mailId").val();
        if(!orderId && !mailId)
        {
            $.messager.alert("系统提示","请至少输入一个查询条件!");
            return false;
        }
        $('#shipmentHeaderTable').datagrid('load',{
            shipmentId:$("#shipmentId").val(),
            mailId:$("#mailId").val()
        });
    });
    //编辑运费
    $("#btnEdit").click(function(){
        var row = $('#shipmentHeaderTable').datagrid('getSelected');
        if(row)
        {
            var p1 = row.totalPrice; //原订单金额
            var p2 = row.lowerPrice; //最低订单金额
            var p3 = row.alertAmount;//折扣后订单金额
            //alert("orderId="+row.orderId+",订单金额 = "+p1+",最低金额="+p2);
            if(parseFloat(p2) >= parseFloat(p1)){
                $.messager.alert("系统提示","订单金额已经是最低价,不可编辑!");
                return false;
            }
            if(parseFloat(p3) > 0){
                $.messager.alert("系统提示","此订单已做过折扣!");
                return false;
            }

            $('#dlg').window({
                title:'订单明细',
                width:600,
                height:400,
                iconCls:'',
                top:($(window).height() - 300) * 0.5,
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
           // $('#fm').form('load',row);
            $("#orderId").val(row.orderId);
            $("#totalPrice").val(row.totalPrice);
            $("#lowerPrice").text(row.lowerPrice);
            $('#editPrice').numberbox('setValue', "");

            $('#orderdetVim')
                .datagrid(
                {
                    title : '',
                    iconCls : 'icon-edit',
                    width:'100%',
                    height :100,
                    nowrap : true,
                    striped : true,
                    border : true,
                    collapsible : false,
                    fitColumns:false,
                    fit : true,
                    scrollbarSize:0,
                    url : '/lower/price/orderDetVim',
                    queryParams:{
                        orderId:row.orderId
                    } ,
                    columns : [ [
                        {
                            field : 'orderId',
                            title : '订单号',
                            width : 80
                        },
                        {
                            field : 'prodid',
                            title : '产品ID',
                            width : 80
                        },
                        {
                            field : 'prodName',
                            title : '产品名称',
                            width : 95
                        },
                        {
                            field : 'lPrice',
                            title : '产品最低价',
                            width : 80,
                            styler:function(){
                                return 'background-color:#ffee00;color:red;';
                            }

                        },
                        {
                            field : 'sPrice',
                            title : '产品均价',
                            width : 60,
                            editor:{type:'numberbox',options:{precision:2}}
                        },
                        {
                            field : 'uPrice',
                            title : '产品订单总价',
                            width : 80,
                            editor:{type:'numberbox',options:{precision:2}}
                        },
                        {
                            field : 'upNum',
                            title : '产品订购数',
                            width : 80
                        }

                    ] ],
                    remoteSort : false,
                    singleSelect : true,
                    pagination : false,
                    rownumbers : false
                });
        }
        else
        {
            $.messager.alert("系统提示","请选择一条数据!");
        }
    });
    //保持编辑运费按钮
    $("#lbSave").click(function(){

        $('#fm').form('submit',{
            url: "/lower/price/save",
            onSubmit: function(){
            	var b = $(this).form('validate');
                var t = $("#lowerPrice").text();  //订单最低价
                var t2 = $("#editPrice").val();  //调整价格
                var p3 = $("#totalPrice").val(); //原订单金额
               // alert("调整金额="+t2+",最低金额="+t+"p3="+p3)
                if(parseFloat(t2) <= parseFloat(t)){
                    $.messager.alert("系统提示","调整金额小于全渠道订单最低商品总金额!");
                    return false;
                }
                if(parseFloat(t2) > parseFloat(p3)){
                    $.messager.alert("系统提示","调整金额大于原始订单金额!")
                    return false;
                }
                if(parseFloat(t2) <= 0){
                    $.messager.alert("系统提示","请输入调整金额!");
                    return false;
                }
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
                    msg:'操作成功!',
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


