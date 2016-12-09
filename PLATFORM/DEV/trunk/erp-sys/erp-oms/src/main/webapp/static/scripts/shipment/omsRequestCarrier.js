$(function(){
	
    $('#dataGrid').datagrid({
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
            {field:'instId',title:'ID',editor:'text',hidden:true},
            {field:'orderId',title:'订单号',editor:'text',width:80},
            {field:'orderCrDate',title:'订购时间',editor:'text',width:100},
            {field:'allAddress',title:'详细地址',editor:'text'},
            {field:'defaultWarehouse',title:'默认仓库',editor:'text',width:60},
            {field:'defaultEntityName',title:'默认送货公司',editor:'text',width:120},
            {field:'designedWarehouseId',title:'designedWarehouseId',hidden:true},
            {field:'designedWarehouse',title:'指派仓库',editor:'text',width:60},
            {field:'designedEntityId',title:'designedEntityId',hidden:true},
            {field:'designedEntityName',title:'指派送货公司',editor:'text',width:120},
            {field:'comment',title:'请求原因',editor:'text',width:120}
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
        queryParams: {},
        onCheck: function(rowIndex, rowData){
//        	if($("#accounted").is(":checked")){
//        		$('#dg').datagrid("uncheckRow",rowIndex);
//        	}
        },
        onLoadSuccess: function(data){
            if(data && data.status && data.status!=1){
            	$.messager.alert('错误',data.msg,'error');
            }
        },
        onLoadError: function(data){
            $.messager.alert('提示',"加载失败!",'alert');
        },
        onDblClickRow:function(rowIndex, rowData){}
    });
    
    //查询
    $('#searchBtn').click(function(){
    	
        var params = {
        		timeS : $("#timeS").datetimebox('getValue'),
        		timeE : $("#timeE").datetimebox('getValue'),
        		isEms : $("#isEms").attr('checked') == 'checked'
        };

        if(!params.timeS || !params.timeE){
        	$.messager.alert('提示',"时间范围！",'alert');
        	return;
        }
    	
    	$('#dataGrid').datagrid({
    		url : '/shipment/omsRequestCarrier/list',
    		queryParams : params
    	});
    	
    });
    
    //同意
    $("#agree").click(function(){
    	approval(1);
    });
    
    //拒绝
    $("#refuse").click(function(){
    	approval(0);
    });
    
    //审批
    function approval(op) {
        var rows = $('#dataGrid').datagrid('getSelections');
        if (rows && rows.length > 0){
            var ids = "";
            var orderIds = "";
            var designedEntityIds = "";
            var designedWarehouseIds = "";
            for(var i = 0; i < rows.length; i++){
            	ids += rows[i].instId + ",";
            	orderIds += rows[i].orderId + ",";
            	designedEntityIds += rows[i].designedEntityId + ",";
            	designedWarehouseIds += rows[i].designedWarehouseId + ",";
            }
            $.messager.progress({  
                title:'请稍等',  
                msg:'处理中...',
                text:''
            }); 
            $.post("/shipment/omsRequestCarrier/approval",{ ids:ids, orderIds:orderIds, designedEntityIds:designedEntityIds, designedWarehouseIds:designedWarehouseIds, op:op},
                function(data){
            		$.messager.progress('close');
	                if(data && data.status!=1){
	                	var msg = "";
	                	var obj = eval(data);
	                	if(obj){
	                		for (var i=0; i<obj.detail.length; i++){
	                			msg += "订单号：" + obj.detail[i].orderId + ",错误信息：" + obj.detail[i].msg + "。";
	                		}
	                	}
	                	$.messager.alert('错误',msg,'error');
	                }else{
	                	$.messager.show({  
	                        title:'信息',  
	                        msg:data.msg,  
	                        timeout:5000,  
	                        showType:'slide'  
	                    });
	                }
                    $("#searchBtn").click();
                }
            );
        }else{
        	$.messager.alert('提示',"您尚未选择任何记录!",'alert');
        }
	}
	
});

