$(function () {

	var fieldFormatter = function(value,row,index){
		if(value!= undefined && value!=null){
			value = value.toFixed(2);
		}
    	return value;
	};
	
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
        pageSize:100,
        pageList:[100,300,500],
        columns:[[
            {field:'id',title:'ID',width:100,editor:'text',hidden:true},
            {field:'fbdt',title:'结账反馈日期',width:140,editor:'text'},
            {field:'shipmentId',title:'发运单号',width:120,editor:'text'},
            {field:'mailId',title:'邮件号',width:100,editor:'text'},
            {field:'a',title:'原始应收款（A）',width:80,editor:'text',align:'right',formatter: fieldFormatter},
            {field:'b',title:'已登记的免运费(B)',width:80,editor:'text',align:'right',formatter: fieldFormatter},
            {field:'c',title:'已登记的半拒收收金额(C)',width:80,editor:'text',align:'right',formatter: fieldFormatter},
            {field:'d',title:'实际应收款D=（A-B-C）',width:80,editor:'text',align:'right',formatter: fieldFormatter},
            {field:'e',title:'基准投递费（E）',width:80,editor:'text',align:'right',formatter: fieldFormatter},
            {field:'f',title:'成功/拒收服务费(F)',width:80,editor:'text',align:'right',formatter: fieldFormatter},
            {field:'j',title:'代收手续费(J)',width:80,editor:'text',align:'right',formatter: fieldFormatter},
            {field:'k',title:'总投递费(K)',width:80,editor:'text',align:'right',formatter: fieldFormatter},
            {field:'g',title:'反馈导入应收款(G)',width:80,editor:'text',align:'right',formatter: fieldFormatter},
            {field:'h',title:'反馈的投递费(H)',width:80,editor:'text',align:'right',formatter: fieldFormatter},
            {field:'i',title:'应收款差异I=(D-G)',width:80,editor:'text',align:'right',formatter: fieldFormatter}
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
        	shipmentId: $("#sId").val(),
        	mailId: $("#mailID").val(),
        	entityId: $("#tbEntityId").combobox('getValue'),
        	warehouseId: $("#tbWarehouseId").combobox('getValue'),
        	fbDtStrS: $("#startDate").datetimebox('getValue'),
        	fbDtStrE: $("#endDate").datetimebox('getValue'),
        	exclude: $("#exclude").attr('checked') == 'checked',
        	accounted: $("#accounted").attr('checked') == 'checked'
        },
        onCheck: function(rowIndex, rowData){
//        	if($("#accounted").is(":checked")){
//        		$('#dg').datagrid("uncheckRow",rowIndex);
//        	}
        },
        onLoadSuccess: function(data){
            //alert(JSON.stringify(data));
        },
        onLoadError: function(){
            $.messager.alert('提示',"加载失败!",'alert');
        },
        onDblClickRow:function(rowIndex, rowData){

        }
    });
    
    $("#tbEntityId").combobox({
        url:'/shipment/company/lookup/0',
        valueField: 'companyid',
        textField: 'name',
        onLoadSuccess:function(data){
            $("#entityId").combobox({
                data: data,
                valueField: 'companyid',
                textField: 'name'
            });
        }
    });

    $("#tbWarehouseId").combobox({
        url:'/shipment/warehouse/lookup',
        valueField: 'warehouseId',
        textField: 'warehouseName',
        onLoadSuccess:function(data){
            $("#warehouseId").combobox({
                data: data,
                valueField: 'warehouseId',
                textField: 'warehouseName',
                onSelect:function(r){
                    $.post("/shipment/company/lookup/"+r.warehouseId,{ }, function(data){
                        if (!data.isError) {
                            $("#tbEntityId").combobox("clear");
                            $("#tbEntityId").combobox("loadData", data);
                        }
                    });
                }
            });
        },
        onSelect:function(r){
            $.post("/shipment/company/lookup/"+r.warehouseId,{ }, function(data){
                if (!data.isError) {
                    $("#tbEntityId").combobox("clear");
                    $("#tbEntityId").combobox("loadData", data);
                }
            });
        }
    });

    //查询
    $("#searchBtn").click(function () {
    	
        var params = {
            shipmentId: $("#sId").val(),
            mailId: $("#mailID").val(),
            entityId: $("#tbEntityId").combobox('getValue'),
            warehouseId: $("#tbWarehouseId").combobox('getValue'),
            fbDtStrS: $("#startDate").datetimebox('getValue'),
            fbDtStrE: $("#endDate").datetimebox('getValue'),
        	exclude: $("#exclude").attr('checked') == 'checked',
        	accounted: $("#accounted").attr('checked') == 'checked'
        };

        if(!params.shipmentId && !params.mailId){
            if(!params.fbDtStrS || !params.fbDtStrE){
            	$.messager.alert('提示',"请选择结账反馈日期！",'alert');
            	return;
            }
            
            if(!params.entityId){
            	$.messager.alert('提示',"承运商必填！",'alert');
            	return;
            }
        }
        $('#dg').datagrid('load',params);
        
    });
    
	$('#exportBtn').click(function(){
		
        var params = {
                shipmentId: $("#sId").val(),
                mailId: $("#mailID").val(),
                entityId: $("#tbEntityId").combobox('getValue'),
                warehouseId: $("#tbWarehouseId").combobox('getValue'),
                fbDtStrS: $("#startDate").datetimebox('getValue'),
                fbDtStrE: $("#endDate").datetimebox('getValue'),
            	exclude: $("#exclude").attr('checked') == 'checked',
            	accounted: $("#accounted").attr('checked') == 'checked'
            };
		

        
        var str = "";
        
		if(params.shipmentId && params.shipmentId!=""){
			str += "shipmentId=" + params.shipmentId + "&";
		}
		if(params.accountNo && params.accountNo!=""){
			str += "accountNo=" + params.accountNo + "&";
		}
		if(params.mailId && params.mailId!=""){
			str += "mailId=" + params.mailId + "&";
		}
		if(params.entityId && params.entityId!=""){
			str += "entityId=" + params.entityId + "&";
		}
		if(params.warehouseId && params.warehouseId!=""){
			str += "warehouseId=" + params.warehouseId + "&";
		}
		if(params.fbDtStrS && params.fbDtStrS!=""){
			str += "fbDtStrS=" + params.fbDtStrS + "&";
		}
		if(params.fbDtStrE && params.fbDtStrE!=""){
			str += "fbDtStrE=" + params.fbDtStrE + "&";
		}
		
		str += "exclude=" + params.exclude + "&";
		str += "accounted=" + params.accounted + "&";
		
		document.location.href = 'export?'+str;
	});
	
	
    $("#createIDBtn").click(function(){
        var rows = $('#dg').datagrid('getSelections');
        if (rows && rows.length > 0){

            var ids = new Array();
            var shipmentIds = new Array();
            for(var i = 0; i < rows.length; i++){
                ids.push(rows[i].id);
                shipmentIds.push(rows[i].shipmentId);
            }
            $.messager.progress({  
                title:'请稍等',  
                msg:'结算中...',
                text:''
            }); 
            $.post("/report/payment/createId",{ ids : ids, shipmentIds:shipmentIds },
                function(result){
            		$.messager.progress('close');
	                var msg = eval('('+result+')');
	                $.messager.alert('提示',msg.message,'info');
                    $("#searchBtn").click();
                }
            );
        }else{
        	$.messager.alert('提示',"您尚未选择任何记录!",'alert');
        }
    });
    
    $("#accounted").click(function(e){
        var params = {
                shipmentId: $("#sId").val(),
                mailId: $("#mailID").val(),
                entityId: $("#tbEntityId").combobox('getValue'),
                warehouseId: $("#tbWarehouseId").combobox('getValue'),
                fbDtStrS: $("#startDate").datetimebox('getValue'),
                fbDtStrE: $("#endDate").datetimebox('getValue'),
            	exclude: $("#exclude").attr('checked') == 'checked',
            	accounted: $("#accounted").attr('checked') == 'checked'
            };
        if(!params.shipmentId && !params.mailId){
            if(!params.fbDtStrS || !params.fbDtStrE){
            	$.messager.alert('提示',"请选择结账反馈日期！",'alert');
            	e.preventDefault(); 
            	return;
            }
            
            if(!params.entityId){
            	$.messager.alert('提示',"承运商必填！",'alert');
            	e.preventDefault(); 
            	return;
            }
        }
    	var checked = $(this).is(':checked');
    	if(checked){
    		$("#createIDBtn").css("display","none");
    	}else{
    		$("#createIDBtn").removeAttr("style");
    	}
    	$("#searchBtn").click();
    });
    
});