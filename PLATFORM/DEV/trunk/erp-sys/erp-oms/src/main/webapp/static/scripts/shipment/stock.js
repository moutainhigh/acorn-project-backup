/**
 * 库内变更承运商
 * User: gaodejian
 * Date: 12-12-5
 * Time: 下午1:43
 * To change this template use File | Settings | File Templates.
 */
$(function(){

    var warehouses = [];
    var entities = [];
    var logisticsStatuses = [];
    var accountStatuses = [];

    $.post("/shipment/logisticsStatus/lookup", {}, function(data){
        if (!data.isError) {
            logisticsStatuses = data;
        }
    });

    $.post("/shipment/accountStatus/lookup", {}, function(data){
        if (!data.isError) {
            accountStatuses = data;
        }
    });

    $("#entityId").combobox({
        url:'/shipment/company/lookup/0',
        valueField: 'companyid',
        textField: 'name',
        onLoadSuccess:function(data){
            entities = data;
        }
    });

    $("#warehouseId").combobox({
        url:'/shipment/warehouse/lookup',
        valueField: 'warehouseId',
        textField: 'warehouseName',
        onLoadSuccess:function(data){
            warehouses = data;
        },
        onSelect:function(r){
            $.post("/shipment/company/lookup/"+r.warehouseId,{ }, function(data){
                if (!data.isError) {
                    $("#entityId").combobox("clear");
                    $("#entityId").combobox("loadData", data);
                }
            });
        }
    });

	
   $('#dg').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height:450,
        striped: true,
        border: true,
        collapsible:true,
        fitColumns:true,
        scrollbarSize:0,
        columns:[[
            {field:'id',title:'ID',width:60,editor:'text', hidden: true},
            {field:'orderId',title:'订单号',width:220,editor:'text'},
            {field:'shipmentId',title:'发运单号',width:220,editor:'text'},
            {field:'warehouseId',title:'发货仓库',width:220,editor:'text',formatter:function (value){
                for(var i=0; i<warehouses.length; i++){
                    if (warehouses[i].warehouseId == value) return warehouses[i].warehouseName;
                }
                return value;
            }},
            {field:'entityId',title:'承运商',width:120,editor:'text',formatter:function (value){
                for(var i=0; i<entities.length; i++){
                    if (entities[i].companyid == value) return entities[i].name;
                }
                return value;
            }},
            {field:'logisticsStatusId',title:'运单物流状态',width:120,editor:'text',formatter:function (value){
                for(var i= 0; i<logisticsStatuses.length; i++){
                    if (logisticsStatuses[i].id == value || (!value && logisticsStatuses[i].id == "0")) return logisticsStatuses[i].dsc;
                }
                return value;
            }},
            {field:'logisticsStatusRemark',title:'运单物流状态',width:130,editor:'text', hidden: true},
            {field:'accountStatusId',title:'物流记账状态',width:120,editor:'text',formatter:function (value){
                for(var i=0; i<accountStatuses.length; i++){
                    if (accountStatuses[i].id == value) return accountStatuses[i].dsc;
                }
                return value;
            }},
            {field:'accountStatusRemark',title:'物流记账状态',width:180,editor:'text', hidden: true}
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
            shipmentId: $("#txtShipmentId").val()
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
            $("#lbAlternate").click();
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
        $("#txtOrderId").val($("#txtOrderId").val().replace(/^\s+|\s+$/g, ""));
        $("#txtShipmentId").val($("#txtShipmentId").val().replace(/^\s+|\s+$/g, ""));

        var params = {
            orderId: $("#txtOrderId").val(),
            shipmentId: $("#txtShipmentId").val()
        };
        if(params.orderId && params.orderId!="" ||
            params.shipmentId && params.shipmentId!=""){
            $('#dg').datagrid('load', params);
        }
        else
        {
            alert("请至少输入一个查询条件");
        }
    });
    //发火变更
    $("#lbAlternate").click(function () {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#dlg').window({title:'订单发货单发货变更',
          					width:500,
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

            $('#fm').form('load',row);
            //界面初始化
            $("#lblShipmentId").text(row.shipmentId);
            $("#lblEntityId").text(row.entityId);
            $("#lblWarehouseId").text(row.warehouseId);

            for(var i=0; i<warehouses.length; i++){
                if (warehouses[i].warehouseId == row.warehouseId) {
                    $("#lblWarehouseId").text(warehouses[i].warehouseName);
                    break;
                }
            }
            for(var i=0; i<entities.length; i++){
                if (entities[i].companyid == row.entityId) {
                    $("#lblEntityId").text(entities[i].name);
                    break;
                }
            }

            $.post("/shipment/company/lookup/"+row.warehouseId,{ }, function(data){
                if (!data.isError) {
                    $("#entityId").combobox("loadData", data);
                }
            });
        }
        else
        {
            alert("请先选择一条发运单记录!");
        }
    });

    $("#lbSave").click(function(){
        $('#fm').form('submit',{
            url: "/shipment/stock/alternate",
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if (result.errorMsg){
                    alert(result.errorMsg);
                }
                $('#dlg').dialog('close');      // close the dialog
                $('#dg').datagrid('reload');    // reload the user data
            }
        });
    });

    $("#lbClose").click(function(){
        $('#dlg').dialog('close');
    });

});
