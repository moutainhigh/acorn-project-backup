$(function(){
	
	$('#dataGrid').datagrid({
		title : '',
		loadMsg:'数据加载中请稍后……', 
		fitColumns:true,
		striped:true,
		singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination: true,  
		rownumbers:true,
		collapsible:true,
		height:400,
		pageSize:10,
        pageList:[10,20,30],
        frozenColumns:[[
            {field:'ck',checkbox:true}
        ]],
		columns:[[
		    {field:'paytype',title:'付款方式ID',hidden:true},
		    {field:'orderId',title:'订单编号',width:140,sortable:true},
		    {field:'crdt',title:'订单提交时间',width:120},
		    {field:'dsca',title:'订单状态',width:80},
		    {field:'totalPrice',title:'订单金额',width:80,align:'right'},
		    {field:'contactId',title:'客户编号',width:140},
		    {field:'name',title:'客户姓名',width:120},
		    {field:'dscb',title:'支付方式',width:80},
		    {field:'confirm',title:'是否已付款',width:70},
		]]
	});
	
    $('#payType').combobox({  
        url:'/shipment/orderPaymentConfirm/payTypes',  
        valueField:'payTypeId',  
        textField:'name',
        onLoadSuccess:function(data){
        	$('#payType').combobox('setValue',data[0].payTypeId);
        }
    });  
    
    $('#searchBtn').click(function(){
    	$('#dataGrid').datagrid({
    		url:'/shipment/orderPaymentConfirm/listOrder',
    		queryParams:{
    			orderId : $("#orderId").val(),
    			payUserName : $("#payUserName").val(),
    			payUserTel : $("#payUserTel").val(),
    			payType : $("#payType").combobox('getValue'),
    			orderDateS : $("#orderDateS").datetimebox('getValue'),
    			orderDateE : $("#orderDateE").datetimebox('getValue')
    		}
    	});
    });
    
    $("#payConfirm").click(function () {
        var row = $('#dataGrid').datagrid('getSelected');
        if (row){
        	
        	$('#orderIdStr').val(row.orderId);
        	$('#payTypeStr').val(row.payType);
        	
        	$('#orderIdDis').val(row.orderId);
        	$('#crdtDis').val(row.crdt);
        	$('#dscaDis').val(row.dsca);
        	$('#totalPriceDis').val(row.totalPrice);
        	$('#contactIdDis').val(row.contactId);
        	$('#nameDis').val(row.name);
        	$('#dscbDis').val(row.dscb);
        	$('#confirmDis').val(row.confirm);
        	
        	$('#payNo').val("");
        	$('#payUser').val("");
        	$('#payDate').datetimebox('setValue','');
        	
        	if(row.payType=="13"){
        		$('#payNo').val("DEF");
        		$('#payNo').parents("tr").hide();
        	}else{
        		$('#payNo').val("");
        		$('#payNo').parents("tr").show();
        	}
            $('#dlg').window({title:'订单付款确认',
                width:400,
                height:390,
                iconCls:'',
                top:($(window).height() - 400) * 0.5,
                left:($(window).width() - 400) * 0.5,
                shadow: true,
                modal:true,
                closed:true,
                minimizable:false,
                maximizable:false,
                collapsible:false
            }).window('open');

        }else{
            alert("请先选择一条订单记录!");
        }
    });
    
    $("#lbSave").click(function(){
        $('#fm').form('submit',{
            url: "/shipment/orderPaymentConfirm/confirm",
            onSubmit: function(){
            	return $(this).form('validate');
            },
            success: function(result){
            	if(result!=null && result!='' ){
                    var result = eval('('+result+')');
                    if (result.errorMsg){
//                        $.messager.show({
//                            title: '错误',
//                            msg: result.errorMsg
//                        });
                        $.messager.alert('错误',result.errorMsg,'error');
                    } else {
                        $('#dlg').dialog('close');      	// close the dialog
                        $('#dataGrid').datagrid('reload');	// reload the user data
                    }
            	}else{
                    $('#dlg').dialog('close');      	// close the dialog
                    $('#dataGrid').datagrid('reload');	// reload the user data	
            	}
            }
        });
    });
    
    $("#lbClose").click(function(){
        $('#dlg').dialog('close');
    });
	
});

