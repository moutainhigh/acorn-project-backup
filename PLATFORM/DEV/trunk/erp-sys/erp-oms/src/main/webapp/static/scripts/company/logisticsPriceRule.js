$(function(){

    var contracts = [];
    $('#fmContract').combobox({
        data: contracts,
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function(data){
            contracts = data;
        }
    });

    var companies = [];
    $("#cbCompany").combobox({
        data: companies,
        valueField: 'companyid',
        textField: 'name',
        onLoadSuccess: function(data){
            companies = data;
        }
    });

    var warehouses = [];
    $("#cbWarehouse").combobox({
        url:'/company/logisticsFeeRule/warehouses',
        valueField: 'warehouseId',
        textField: 'warehouseName',
        onLoadSuccess: function(data){
            warehouses = data;

            $("#fmWarehouse").combobox({
                data: data,
                valueField: 'warehouseId',
                textField: 'warehouseName',
                onSelect: function(r){
                    $.post("/company/logisticsFeeRule/contracts/"+r.warehouseId,{ }, function(data2){
                        var value = $("#fmContract").combobox("getValue");
                        $("#fmContract").combobox("clear");
                        $("#fmContract").combobox("loadData", data2);
                        $("#fmContract").combobox("select", value);
                    });
                }
            });
        },
        onSelect:function(r){
            $.post("/company/logisticsFeeRule/companies/"+r.warehouseId,{ }, function(data){
                $("#cbCompany").combobox("clear");
                $("#cbCompany").combobox("loadData", data);
            });
        }
    });

    var statuses = [{id: 1, name: '启用'},{id: 0, name: '禁用'}];
    $("#fmStatus").combobox({
        data: statuses,
        valueField: 'id',
        textField: 'name'
    });

    $('#dg').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height: 200,
        striped: true,
        border: true,
        collapsible:true,
        fitColumns:true,
        scrollbarSize:0,
        url:'/company/logisticsFeeRule/load',
        saveUrl: '/company/logisticsFeeRule/save',
        updateUrl: '/company/logisticsFeeRule/update',
        destroyUrl: '/company/logisticsFeeRule/delete',
        idField:'id',
        columns:[[
            {field:'contract',title:'承运商合同',width:100,editor:'text',
                formatter: function(obj) {
                    return obj != null ? obj.name : "";
                }},
            {field:'version',title:'运费公式版本',width:100,editor:'text'},
            {field:'status',title:'目前状态',width:120,editor:'text',
                formatter: function(value) {
                    for(var i=0; i<statuses.length; i++){
                        if (statuses[i].id == value) return statuses[i].name;
                    }
                    return value;
                }},
            {field:'beginDate',title:'合同起始日',width:120,editor:'text'},
            {field:'endDate',title:'合同终止日',width:120,editor:'text'},
            {field:'createDate',title:'创建日期',width:120,editor:'text'}
        ]],
        frozenColumns:[[
            {field:'ck',checkbox:true}
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: false,
        pagination:true,
        rownumbers:true,
        queryParams: {
            companyId: $("#cbCompany").combobox('getValue'),
            warehouseId: $("#cbWarehouse").combobox('getValue'),
            freightMethod: 1  //按价格
        },
        onLoadSuccess: function(data){
            //alert(JSON.stringify(data));
        },
        onLoadError: function(){
           alert("运费规则加载失败!");
        },
        onUncheck:function(index,data){
            $(this).datagrid("clearSelections");
        },
        onSelect:function(rowIndex, row){
            $('#dg-price').datagrid('load',{
                ruleId: row.id
            });
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

    $("#btnSearch").click(function () {

        $('#dg').datagrid('load',{
            companyId: $("#cbCompany").combobox('getValue'),
            warehouseId: $("#cbWarehouse").combobox('getValue'),
            freightMethod: 1  //按价格
        });
    });

    /* toolbar */
    $("#lbNew").click(function(){
        $('#fm').form('clear');
        $('#fm').attr('url', "/company/logisticsFeeRule/add");
        $('#fmStatus').combobox({ disabled: true});
        $('#dlg').dialog('open').dialog('setTitle','添加合同计费规则');
    });

    $("#lbEdit").click(function(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#lbSave').show();
            $('#fmStatus').combobox({ disabled: false});
            $('#fm').form('load',row);
            $('#fm').attr('url', "/company/logisticsFeeRule/save");

            //下拉属性特殊处理
            var contract =row["contract"];
            if(contract){
                $('#fmWarehouse').combobox("select",contract.warehouse.warehouseId);
                $('#fmContract').combobox("setValue",contract.id);
            }
            else
            {
                $('#fmWarehouse').combobox("setValue","");
                $('#fmContract').combobox("setValue","");
            }

            $('#dlg').dialog('open').dialog('setTitle','编辑合同计费规则');
        }
        else
        {
            alert("请先选择合同计费规则!");
        }
    });

    $("#lbDelete").click(function(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('确认','要删除当前记录吗? ',function(r){
                if (r){
                    $.post('/company/logisticsFeeRule/delete',{id:row.id},function(result){
                        if (result.success){
                            var rowIndex = $("#dg").datagrid("getRowIndex",row);
                            if(rowIndex > -1){
                                $("#dg").datagrid("deleteRow",rowIndex);
                            }
                            $('#dg').datagrid('reload');    // reload the user data
                        } else {
//                            $.messager.show({   // show error message
//                                title: 'Error',
//                                msg: result.errorMsg
//                            });
                            $.messager.alert('错误',result.errorMsg,'error');
                        }
                    },'json');
                }
            });
        }
        else
        {
            alert("请先选择合同计费规则!");
        }
    });
    $("#lbSave").click(function(){

        $('#fm').form('submit',{
            url: $("#fm").attr("url"),
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if (result.errorMsg){
//                    $.messager.show({
//                        title: '错误',
//                        msg: result.errorMsg
//                    });
                    $.messager.alert('错误',result.errorMsg,'error');
                } else {
                    $('#dlg').dialog('close');      // close the dialog
                    $('#dg').datagrid('reload');    // reload the user data
                }
            }
        });
    });

    $("#lbClose").click(function(){
        $('#dlg').dialog('close');
    });

});

