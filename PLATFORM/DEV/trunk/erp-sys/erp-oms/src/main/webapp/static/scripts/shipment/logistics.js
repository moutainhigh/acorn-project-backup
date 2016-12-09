/**
 * 物流变更承运商
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

    $("#tbCardtype").combobox({
        url:'/shipment/cardtype/lookup',
        valueField: 'id',
        textField: 'name'
    });

    $("#tbEntityId").combobox({
        url:'/shipment/company/lookup/0',
        valueField: 'companyid',
        textField: 'name',
        onLoadSuccess:function(data){
            entities = data;

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
            warehouses = data;
            //alert(JSON.stringify(data));

            $("#warehouseId").combobox({
                data: data,
                valueField: 'warehouseId',
                textField: 'warehouseName',
                onSelect:function(r){
                    $.post("/shipment/company/lookup/"+r.warehouseId,{ }, function(data){
                        if (!data.isError) {
                            $("#entityId").combobox("clear");
                            $("#entityId").combobox("loadData", data);
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

    $("#tbCountyId").combobox({
        data:[],
        valueField: 'countyid',
        textField: 'countyname'
    });

    $("#tbCityId").combobox({
        data:[],
        valueField: 'cityid',
        textField: 'cityname',
        onSelect:function(r){
            $.post("/order/county/lookup",{ cityId:r.cityid }, function(data){
                $("#tbCountyId").combobox("clear");
                $("#tbCountyId").combobox("loadData", data);
            });
        }
    });

    $("#tbProvinceId").combobox({
        url:'/order/province/lookup',
        valueField: 'provinceid',
        textField: 'chinese',
        onSelect:function(r){
            $.post("/order/city/lookup",{ provinceId:r.provinceid }, function(data){
                if (!data.isError) {
                    $("#tbCityId").combobox("clear");
                    $("#tbCityId").combobox("loadData", data);
                }
            });
        }
    });

    $("#tblogisticsStatusId").combobox({
        url:'/shipment/logisticsStatus/lookup',
        valueField: 'id',
        textField: 'dsc',
        onLoadSuccess:function(data){
            logisticsStatuses = data;
        }
    });
    $("#tbAccountStatusId").combobox({
        url:'/shipment/accountStatus/lookup',
        valueField: 'id',
        textField: 'dsc',
        onLoadSuccess:function(data){
            accountStatuses = data;
        }
    });

    $("#tbPaytypeId").combobox({
        url:"/shipment/paytype/lookup",
        valueField: 'id',
        textField: 'dsc'
    });

    $("#tbBuytypeId").combobox({
        url:"/shipment/buytype/lookup",
        valueField: 'id',
        textField: 'dsc'
    });

 
	
    $('#dg').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height:350,
        striped: true,
        border: true,
        collapsible:true,
        fitColumns:false,
        columns:[[
            {field:'id',title:'ID',width:60,editor:'text', hidden: true},
            {field:'orderId',title:'订单号',width:90,editor:'text'},
            {field:'shipmentId',title:'发运单号',width:110,editor:'text'},
            {field:'address',title:'发运地址',width:100,editor:'text'},
            {field:'totalPrice',title:'合计金额',width:80,editor:'text'},
            {field:'warehouseId',title:'发货仓库',width:80,editor:'text',formatter:function (value){
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
            {field:'logisticsStatusId',title:'运单物流状态',width:100,editor:'text',formatter:function (value){
                for(var i= 0; i<logisticsStatuses.length; i++){
                    if (logisticsStatuses[i].id == value || (!value && logisticsStatuses[i].id == "0")) return logisticsStatuses[i].dsc;
                }
                return value;
            }},

            {field:'status',title:'物流记账状态',width:100,editor:'text',formatter:function (value){
                for(var i=0; i<accountStatuses.length; i++){
                    if (accountStatuses[i].id == value) return accountStatuses[i].dsc;
                }
                return value;
            }},
            {field:'confirmdt',title:'锁权日期',width:100},
            {field:'prodName',title:'产品备注',width:200,editor:'text'}

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
            orderId: $("#tbOrderId").val(),
            shipmentId: $("#tbShipmentId").val(),
            provinceId: $("#tbProvinceId").combobox('getValue'),
            cityId: $("#tbCityId").combobox('getValue'),
            countyId: $("#tbCountyId").combobox('getValue'),
            accountStatusId: $("#tbAccountStatusId").combobox('getValue'),
            warehouseId: $("#tbWarehouseId").combobox('getValue'),
            entityId: $("#tbEntityId").combobox('getValue'),
            paytypeId: $("#tbPaytypeId").combobox('getValue'),
            minAmount: $("#tbMinAmount").val(),
            maxAmount: $("#tbMaxAmount").val(),
            capture: $("#tbCapture").attr('checked') == 'checked',
            productId: $("#tbProductId").val(),
            //创建时间、交际时间段
            crdtStart:$("#tbCrdtStart").datetimebox('getValue'),
            crdtEnd:$("#tbCrdtEnd").datetimebox('getValue'),
            senddtStart:$("#tbSenddtStart").datetimebox('getValue'),
            senddtEnd:$("#tbSenddtEnd").datetimebox('getValue'),
            cardtype:$("#tbCardtype").combobox('getValue')


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
        pageSize: 500,
        pageList: [500,300,100],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    //查询
    $("#btnSearch").click(function () {
        $("#tbOrderId").val($("#tbOrderId").val().replace(/^\s+|\s+$/g, ""));
        $("#tbShipmentId").val($("#tbShipmentId").val().replace(/^\s+|\s+$/g, ""));
        var crdtStart = $("#tbCrdtStart").datetimebox('getValue');
        var crdtEnd = $("#tbCrdtEnd").datetimebox('getValue');
        if(crdtStart && crdtEnd){
            var days = GetDateDiff(crdtStart,crdtEnd);
            if(days > 60){
                alert("时间段范围过大,请选择时间范围2个月内!");
                return false;
            }
        }else{
            alert("创建时间段为必选,且时间范围在2个月内!");
            return false;
        }

        var params = {
            orderId: $("#tbOrderId").val(),
            shipmentId: $("#tbShipmentId").val(),
            provinceId: $("#tbProvinceId").combobox('getValue'),
            cityId: $("#tbCityId").combobox('getValue'),
            countyId: $("#tbCountyId").combobox('getValue'),
            accountStatusId: $("#tbAccountStatusId").combobox('getValue'),
            warehouseId: $("#tbWarehouseId").combobox('getValue'),
            entityId: $("#tbEntityId").combobox('getValue'),
            paytypeId: $("#tbPaytypeId").combobox('getValue'),
            minAmount: $("#tbMinAmount").val(),
            maxAmount: $("#tbMaxAmount").val(),
            capture: $("#tbCapture").attr('checked') == 'checked',
            productId: $("#tbProductId").val(),
            //创建时间、交际时间段
            crdtStart:$("#tbCrdtStart").datetimebox('getValue'),
            crdtEnd:$("#tbCrdtEnd").datetimebox('getValue'),
            senddtStart:$("#tbSenddtStart").datetimebox('getValue'),
            senddtEnd:$("#tbSenddtEnd").datetimebox('getValue'),
            cardtype:$("#tbCardtype").combobox('getValue')
        }

        if(params.orderId && params.orderId!="" ||
            params.shipmentId && params.shipmentId!="" ||
            params.provinceId && params.provinceId!="" ||
            params.cityId && params.cityId!="" ||
            params.accountStatusId && params.accountStatusId!="" ||
            params.warehouseId && params.warehouseId!="" ||
            params.entityId && params.entityId!="" ||
            params.paytypeId && params.paytypeId!="" ||
            params.minAmount && params.minAmount!="" ||
            params.maxAmount && params.maxAmount!="" ||
            params.countyId && params.countyId!="" ||
            params.productId && params.productId!="" ||
            params.crdtStart && params.crdtStart!="" ||
            params.crdtEnd && params.crdtEnd!="" ||
            params.senddtStart && params.senddtStart!="" ||
            params.senddtEnd && params.senddtEnd!="" ||
            params.cardtype && params.cardtype!=""
            ) {
            $('#dg').datagrid('load',params);
        }
        else
        {
            alert("请至少输入一个查询条件");
        }

    });
    //时间间隔
    function GetDateDiff(startDate,endDate)
    {
        var startTime = new Date(Date.parse(startDate.replace(/-/g,   "/"))).getTime();

        var endTime = new Date(Date.parse(endDate.replace(/-/g,   "/"))).getTime();

        var dates = Math.abs((startTime - endTime))/(1000*60*60*24);

        return  dates;
    }
    //发货变更
    $("#lbAlternate").click(function () {
        var rows = $('#dg').datagrid('getSelections');
        var ids = [];
        for(var i=0;i<rows.length;i++){
            ids.push(rows[i].id);
            if(rows[0].warehouseId != rows[i].warehouseId || rows[0].entityId != rows[i].entityId){
                alert("批量操作需保证出库仓和承运商一致!")
                return false;
            }
        }

        var row =  $('#dg').datagrid('getSelected');
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
            $("#ids").val(ids.join(","));
            //界面初始化
           // $("#lblShipmentId").text(row.shipmentId);
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
            url: "/shipment/logistics/alternate",
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
