$(function(){
    var entities = [];
    var orderTypes=[];

    $.post("/shipment/refund/lookup/orderType", {}, function(data){
        if (!data.isError) {
            orderTypes = data;
        }
    });

    //设置表格自适应浏览器高度
    var body = (document.compatMode&&document.compatMode.toLowerCase() == "css1compat")?document.documentElement:document.body;
    //alert(body.clientHeight-$('.currentPage').height()-$('#condition').height()-$('#toolbar').height()-108);
    $('#dgOrderhist').parent().height(body.clientHeight-$('.currentPage').height()-$('#condition').height()-$('#toolbar').height()-108);

    $('#dgOrderhist').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height:'100%',
        striped: true,
        border: true,
        collapsible:true,
        fit: true,
        columns:[[
            {field:'orderId',title:'ID',width:0,editor:'text', hidden: true},
            {field:'prodPrice',title:'产品费用',width:0,editor:'text', hidden: true},
            {field:'rfOutDate',title:'出库日期',width:105,editor:'text'},
            {field:'shipmentId',title:'发运单号',width:110,editor:'text'},
            {field:'mailId',title:'邮件号',width:110,editor:'text'},
            {field:'orderTypeId',title:'订单类型',width:90,editor:'text',formatter:function (value){
                for(var i= 0; i<orderTypes.length; i++){
                    if (orderTypes[i].id == value || (!value && orderTypes[i].id == "0")) return orderTypes[i].dsc;
                }
                return value;
            }},
            {field:'totalPrice',title:'订单金额',width:90,editor:'text'},
            {field:'mailPrice',title:'原始运费',width:90,editor:'text'},
            {field:'name',title:'客户名称',width:90,editor:'text'},
            {field:'phone',title:'电话',width:110,editor:'text'},
            {field:'address',title:'地址',width:200,editor:'text'},
            {field:'refundPrice',title:'半签收后金额',width:90,editor:'text'},
            {field:'remark',title:'remark',width:0,editor:'text', hidden: true}
        ]],
        frozenColumns:[[
            {field:'ck',checkbox:true}
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination:true,
        rownumbers:true,
        queryParams: {
            orderId: $("#txtOrderId").val(),
            contactId: $("#txtContactId").val()
        },
        onLoadSuccess: function(data){
            if(data.err!=null)
            {
                alert(data.err);
            }
        },
        onLoadError: function(){
            alert("加载失败!");
        },
        onUncheck:function(index,data){
            $(this).datagrid("clearSelections");
        },
        onDblClickRow:function(rowIndex, rowData){
            $("#lbCheckIn").click();
        }
    });

    var p = $('#dgOrderhist').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    var mailPrice;
    var orderPrice;
    function cacleRefundTotalShow()
    {
        var total=0.0;
        var rowData= $('#orderdet').datagrid('getRows');
        for(var i=0;i<rowData.length;i++)
        {
            if(rowData[i].checkInPayment!=null&&rowData[i].checkInPayment!="")
                total+=formatFloat(rowData[i].checkInPayment,4);
        }
        var textShow="产品";
        textShow+=total;
        textShow+="+";
        textShow+="运费";
        if(mailPrice!=null)
            textShow+=mailPrice;

        $("#txtRefundTotal").val(textShow);
    }
    var lastIndex=-1;
    $('#orderdet').datagrid({
        title:'',
        iconCls:'icon-edit',

        striped: true,
        border: true,
        collapsible:true,
        fit: true,
        columns:[[
            {field:'id',title:'ID',width:10,editor:'text', hidden: true},
            {field:'prodName',title:'商品名称',width:220},
            {field:'prodQuantity',title:'订购数量',width:40},
            {field:'prodPrice',title:'单价',width:90},
            {field:'prodPayment',title:'合计金额',width:90},
            {field:'checkInQuantity',title:'签收数量',width:40,editor:'numberbox'},
            {field:'checkInPrice',title:'确认价格',width:90,editor:{type:'numberbox',options:{precision:4}}},
            {field:'checkInPayment',title:'确认合计',width:90}
        ]],
        remoteSort:false,
        singleSelect:true,
        //checkOnSelect: true,
        //selectOnCheck: true,
        //pageNumber: 1,
        pagination:true,
        //fitColumns: true,
        //pageSize:10,
        rownumbers:true,
        queryParams: {
            orderId: 0
        },
        onClickCell:function(rowIndex, field1, value){
            if(lastIndex>=0)
            {
                $('#orderdet').datagrid('endEdit', lastIndex);
                lastIndex=-1;
            }
            if(field1=="checkInPrice")
            {
                var opts = $('#orderdet').datagrid('getColumnOption', 'checkInQuantity');
                if(opts)
                {
                    opts.editor=null;
                }
                opts = $('#orderdet').datagrid('getColumnOption', 'checkInPrice');
                if(opts)
                {
                    opts.editor={type:'numberbox',options:{required:true,precision:4}};
                }
                $('#orderdet').datagrid('beginEdit', rowIndex);
                lastIndex=rowIndex;
            }
            else if(field1=="checkInQuantity")
            {
                var opts = $('#orderdet').datagrid('getColumnOption', 'checkInQuantity');
                if(opts)
                {
                    opts.editor={type:'numberbox',options:{required:true}};//'numberbox';
                }
                opts = $('#orderdet').datagrid('getColumnOption', 'checkInPrice');
                if(opts)
                {
                    opts.editor=null;
                }
                $('#orderdet').datagrid('beginEdit', rowIndex);
                lastIndex=rowIndex;
            }
            //    $('#orderdet').datagrid('getEditor', {index:1,field:"checkInPrice"});
            //}
        },
        onBeforeEdit:function(index,row)
        {
            //row.editing = true;
            $('#orderdet').datagrid('refreshRow', index);
        },
        onAfterEdit:function(index,row){
            //row.editing = '';
            row.checkInPayment=row.checkInQuantity*row.checkInPrice;
            $('#orderdet').datagrid('refreshRow', index);
            cacleRefundTotalShow();
        },
        onCancelEdit:function(index,row){
            //row.editing = '';
            $('#orderdet').datagrid('refreshRow', index);
        },
        onLoadSuccess: function(data){
            if(data.err!=null)
            {
                alert(data.err);
            }
        }

    });

    //查询
    $("#btnSearch").click(function () {
        $("#txtOrderId").val($("#txtOrderId").val().replace(/^\s+|\s+$/g, ""));
        $("#txtMailId").val($("#txtMailId").val().replace(/^\s+|\s+$/g, ""));
        var params = {
            orderId: $("#txtOrderId").val(),
            contactId: "",
            mailId:$("#txtMailId").val(),
            phone: "",
            entityId: ""
        };
        if((params.orderId==null||params.orderId=="")
        &&(params.mailId==null||params.mailId==""))
        {
            alert("请输入一个查询条件");
            return;
        }
        else
        {
            $('#dgOrderhist').datagrid('load', params);
        }

    });
    var orderId;
    var selectedRow=null;
    //半拒收登记
    $("#lbCheckIn").click(function () {
        var row = $('#dgOrderhist').datagrid('getSelected');
        if (row){

            selectedRow=row;
            orderId=row.orderId;
            var textShow="产品";
            var textShow1=textShow;
            textShow+=row.prodPrice;
            if(row.refundPrice!=null&&row.refundPrice!="")
                textShow1+=row.refundPrice;
            else
                textShow1+=row.prodPrice;
            if(row.remark!=null&&row.remark!="")
                $('#txtRemark').val(row.remark);
            orderPrice= row.totalPrice;
            textShow+="+";
            textShow+="运费";
            textShow+=row.mailPrice;
            textShow1+="+";
            textShow1+="运费";
            textShow1+=row.mailPrice

            mailPrice=row.mailPrice;

            $("#txtOrgTotal").val(textShow);
            $("#txtRefundTotal").val(textShow1);
            $('#dlg').window({title:'订单半签收登记',
                width:950,
                height:450,
                iconCls:'',
                top:($(window).height() - 450) * 0.5,
                left:($(window).width() - 950) * 0.5,
                shadow: true,
                modal:true,
                closed:true,
                minimizable:false,
                maximizable:false,
                collapsible:false
            })
                .window('open');

            $('#fm').form('load',row);
            var params1 = {
                orderId: orderId
            };
            $('#orderdet').datagrid('load', params1);
        }
        else
        {
            alert("请先选择一条订单记录!");
        }
    });

    function formatFloat(src, pos)
    {
        return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);
    }

    function checkRefund()
    {
        var rowData= $('#orderdet').datagrid('getRows');
        var amount = 0;
        for(var i=0;i<rowData.length;i++)
        {
            if(rowData[i].checkInQuantity==null || rowData[i].checkInPrice==null)
            {
                $.messager.alert("系统提示","请填写签收数量!");
                return false;
            }
            if(rowData[i].checkInQuantity > rowData[i].prodQuantity)
            {
                $.messager.alert("系统提示","签收数量大于订购数量!");
                return false;
            }
            if(rowData[i].checkInQuantity == rowData[i].prodQuantity){
                if(rowData[i].checkInPrice != rowData[i].prodPrice){
                    $.messager.alert("系统提示","订购数等于签收数商品价格不允变动!");
                    return false;
                }
            }
            if(rowData[i].checkInQuantity>0)
            {
            	var checkInPrice = formatFloat(rowData[i].checkInPrice,4);
            	amount += checkInPrice;
            }
        }
        /*if(amount==0){
            $.messager.alert("系统提示","确认价格为零!");
        	return false;
        }*/
        return true;
    }

    $("#lbSave").click(function(){
        if(lastIndex>=0)
        {
            $('#orderdet').datagrid('endEdit', lastIndex);
            lastIndex=-1;
        }
        var valid=checkRefund();
        if(valid==false)
        {
            //alert("签收数量或单价不合法");
            return;
        }
        var remark= $('#txtRemark').val();
        if(remark==null||remark=="")
        {
            alert("备注不能为空");
            return;
        }

        var prodQuantityCount = 0;//订购数
        var checkInQuantityCount = 0;//签收数
        var total=0.0;
        var rowData= $('#orderdet').datagrid('getRows');
        for(var i=0;i<rowData.length;i++)
        {
        	prodQuantityCount += rowData[i].prodQuantity;
        	checkInQuantityCount += Number(rowData[i].checkInQuantity);
            if(rowData[i].checkInPayment!=null&&rowData[i].checkInPayment!="")
                total+=formatFloat(rowData[i].checkInPayment,4);
        }
        if(prodQuantityCount==checkInQuantityCount){
        	$.messager.alert('错误',"签收数与订购数不能完全一致！",'erroe');
        	return;
        }else if(prodQuantityCount<checkInQuantityCount){
        	$.messager.alert('错误',"签收数不能大于订购数！",'erroe');
        	return;
        }
//        var temp1=parseFloat(total)+parseFloat(mailPrice);
//        if(temp1>parseFloat(orderPrice)){
//            alert("半签收金额不能大于订单总金额");
//            return;
//        }
        var rowData= $('#orderdet').datagrid('getRows');
        var refundData= {
            orderId:orderId,
            orderdetRefundDtoSet:rowData,
            remark:remark
        };

        var parms1={
            orderRefundDto:refundData
        }
        //var columns2=JSON.stringify(refundData);
        $.messager.progress({  
            title:'请稍等',  
            msg:'处理中...',
            text:''
        }); 
        $.ajax({
            url:"/shipment/refund/submitRefund",
            type:"POST",
            contentType:"application/json; charset=utf-8",
            data:JSON.stringify(refundData),
            success:function(data){
            	$.messager.progress('close');
                if(data==null || (data!=null && data!="succ")){
                    if(data.err!=null){
                        $.messager.alert('警告',data.err,'warning');
                        return;
                    }else{
                        $.messager.alert('警告',data,'warning');
                        return;
                    }
                }else{
                    $('#dlg').dialog('close');
                    $.messager.show({  
                        title:'提示',  
                        msg:'半签收订单登记成功',  
                        timeout:5000,  
                        showType:'slide'  
                    }); 
                    $("#btnSearch").click();
                }
            }
        });
    });

    $("#lbClose").click(function(){
        $('#dlg').dialog('close');
    });

});
