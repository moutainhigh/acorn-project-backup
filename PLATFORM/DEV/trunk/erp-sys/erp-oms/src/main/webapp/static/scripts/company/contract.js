$(function(){
    var warehouses = [];
    $("#cbWarehouse").combobox({
        url:'/company/warehouse/lookup',
        valueField: 'warehouseId',
        textField: 'warehouseName',
        onLoadSuccess: function(data){
            $("#fmWarehouse").combobox({
                data: data,
                valueField: 'warehouseId',
                textField: 'warehouseName'
            });
            warehouses = data;
        }
    });

    $("#cbChannel").combobox({
        url:'/company/channel/lookup',
        valueField: 'id',
        textField: 'channelName',
        onLoadSuccess: function(data){
            $("#fmChannel").combobox({
                data: data,
                valueField: 'id',
                textField: 'channelName'
            });
        }
    });
    $("#cbDistributionRegion").combobox({
        url:'/company/distributionRegion/lookup',
        valueField: 'id',
        textField: 'dsc',
        onLoadSuccess: function(data){
            $("#fmDistributionRegion").combobox({
                data: data,
                valueField: 'id',
                textField: 'dsc'
            });
        }
    });

    var companyTypes = [];
    $("#fmCompanyType").combobox({
        url:'/company/companyType/lookup',
        valueField: 'id',
        textField: 'dsc',
        onLoadSuccess: function(data){
            companyTypes = data;
        }
    });

    $("#fmSuretyType").combobox({
        url:'/company/suretyType/lookup',
        valueField: 'id',
        textField: 'dsc'
    });


    var statuses = [{id: 1, name: '启用'},{id: 0, name: '禁用'}];
    $("#fmStatus").combobox({
        data: statuses,
        valueField: 'id',
        textField: 'name'
    });

    var freightMethods = [{id: 1, name: '按金额'},{id: 2, name: '按重量'}];
    $("#fmFreightMethod").combobox({
        data: freightMethods,
        valueField: 'id',
        textField: 'name'
    });

    $("#fmIsManual").combobox({
        data: [{id: true, name: '是'},{id: false, name: '否'}],
        valueField: 'id',
        textField: 'name'
    });

    $('#dg').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height: 300,
        striped: true,
        border: true,
        collapsible:true,
        fitColumns:true,
        scrollbarSize:0,
        url:'/company/contract/load',
        saveUrl: '/company/contract/save',
        updateUrl: '/company/contract/update',
        destroyUrl: '/company/contract/delete',
        idField:'id',
        columns:[[
            {field:'nccompanyId',title:'承运商NC编码',width:100,editor:'text'},
            {field:'nccompanyName',title:'承运商NC名称',width:100,editor:'text'},
            {field:'id',title:'承运商二级部门ID',width:120,editor:'text'},
            {field:'name',title:'承运商名称',width:120,editor:'text'},
            {field:'companyType',title:'承运商类型',width:120,editor:'text',
                formatter: function(value) {
                    for(var i=0; i<companyTypes.length; i++){
                        if (companyTypes[i].id == value) return companyTypes[i].dsc;
                    }
                    return value;
            }},
            {field:'warehouse',title:'默认出库仓库',width:120,editor:'text',
                formatter: function(obj) {
                    return obj != null ? obj.warehouseName : "";
                }
            },
            {field:'status',title:'当前状态',width:100,editor:'text',
                formatter: function(value) {
                    for(var i=0; i<statuses.length; i++){
                        if (statuses[i].id == value) return statuses[i].name;
                    }
                    return value;
                }},
            {field:'createDate',title:'创建日期',width:100,editor:'text'},
            {field:'beginDate',title:'合同开始',width:100,editor:'text'},
            {field:'endDate',title:'合同结束',width:100,editor:'text'}
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
            companyId: $("#tbCompanyId").val(),
            nccompanyId: $("#tbNccompanyId").val(),
            warehouseId: $("#cbWarehouse").combobox('getValue'),
            channelId: $("#cbChannel").combobox('getValue'),
            startDate: $("#tbStartDate").datebox('getValue'),
            endDate: $("#tbEndDate").datebox('getValue'),
            status: $("#tbStatus").attr('checked') == 'checked',
            quYuId:$("#cbDistributionRegion").combobox('getValue')
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
        onSelect:function(rowIndex, rowData){
            $("#lbShow").click();
        }
    });
    
    
    
    var p = $('#dg').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [10,50,100],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    $("#btnSearch").click(function () {
        $("#tbCompanyId").val($("#tbCompanyId").val().replace(/^\s+|\s+$/g, ""));
        $("#tbNccompanyId").val($("#tbNccompanyId").val().replace(/^\s+|\s+$/g, ""));
        $('#dg').datagrid('load',{
            companyId: $("#tbCompanyId").val(),
            nccompanyId: $("#tbNccompanyId").val(),
            warehouseId: $("#cbWarehouse").combobox('getValue'),
            channelId: $("#cbChannel").combobox('getValue'),
            startDate: $("#tbStartDate").datebox('getValue'),
            endDate: $("#tbEndDate").datebox('getValue'),
            status: $("#tbStatus").attr('checked') == 'checked',
            quYuId:$("#cbDistributionRegion").combobox('getValue')
        });
    });

    /* toolbar */
    $("#lbNew").click(function(){
        $('#dlg').dialog('open').dialog('setTitle','添加送货承运商合同');
        $('#fm').form('clear');
        $('#fm').attr('url', "/company/contract/add");
    });

    $("#lbEdit").click(function(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#fm').form('clear');
            $('#fm').form('load',row);
            $('#fm').attr('url', "/company/contract/save");

            $('#fm').find('.easyui-validatebox').each(function(){
                $(this).removeAttr("disabled");
            });
            $('#fm').find('.easyui-combobox').each(function(){
                $(this).combobox({disabled: false});
            });
            $('#fm').find('.easyui-datebox').each(function(){
                $(this).datebox({disabled: false});
            });
            $('#fm').find('.easyui-numberbox').each(function(){
                $(this).numberbox({disabled: false});
            });
            //fmActualRiskFactor
            $('#fmActualRiskFactor').numberbox('disable');
            $('#fmDeliverySuccessRate').numberbox('disable');
            //下拉属性特殊处理
            if(row.warehouse){
                $('#fmWarehouse').combobox("select",row.warehouse.warehouseId);
            }
            else
            {
                $('#fmWarehouse').combobox("setValue","");
            }
            if(row.channel){
                $('#fmChannel').combobox("select",row.channel.id);
            }
            else
            {
                $('#fmChannel').combobox("setValue","");
            }

            if(row.beginDate){
                $('#fmBeginDate').datebox("setValue",row.beginDate);
            }
            if(row.endDate){
                $('#fmEndDate').datebox("setValue",row.endDate);
            }

            if(row.companyType){
                $('#fmCompanyType').combobox("select",row.companyType);
            }
            else
            {
                $('#fmCompanyType').combobox("setValue","");
            }

            if(row.suretyType){
                $('#fmSuretyType').combobox("select",row.suretyType);
            } else{
                $('#fmSuretyType').combobox("select","");
            }

            if(row.distributionRegion){
                $('#fmDistributionRegion').combobox("select",row.distributionRegion);
            } else{
                $('#fmDistributionRegion').combobox("select","");
            }

            if(row.freightMethod){
                $('#fmFreightMethod').combobox("select",row.freightMethod);
            }
            else
            {
                $('#fmFreightMethod').combobox("setValue","");
            }
            if(row.status){
                $('#fmStatus').combobox("select",row.status);
            }
            if(row.isManual){
                $('#fmIsManual').combobox("select",row.isManual);
            }

            $('#lbSave').show();
            $("#lbShow").show();
            $("#lbEdit").hide();
            //$('#dlg').dialog('open').dialog('setTitle','编辑送货承运商合同');
        }
        else
        {
            alert("请先选择送货承运商合同!");
        }
    });

    //送货公司查看
    $("#lbShow").click(function(){
        var row = $('#dg').edatagrid('getSelected');
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#fm').form('clear');
            $('#fm').form('load',row);
            $('#fm').attr('url', "#");
            $('#fm').find('.easyui-validatebox').each(function(){
                $(this).attr("disabled", "disabled");
            });
            $('#fm').find('.easyui-combobox').each(function(){
                $(this).combobox({disabled: true});
            });
            $('#fm').find('.easyui-datebox').each(function(){
                $(this).datebox({disabled: true});
            });
            $('#fm').find('.easyui-numberbox').each(function(){
                $(this).numberbox({disabled: true});
            });
            //下拉属性特殊处理
            if(row.warehouse){
                $('#fmWarehouse').combobox("select",row.warehouse.warehouseId);
            }
            else
            {
                $('#fmWarehouse').combobox("setValue","");
            }
            //渠道
            if(row.channel){
                $('#fmChannel').combobox("select",row.channel.id);
            }
            else
            {
                $('#fmChannel').combobox("setValue","");
            }

            if(row.companyType){
                $('#fmCompanyType').combobox("select",row.companyType);
            }
            else
            {
                $('#fmCompanyType').combobox("setValue","");
            }

            if(row.suretyType){
                $('#fmSuretyType').combobox("select",row.suretyType);
            } else{
                $('#fmSuretyType').combobox("select","");
            }

            if(row.distributionRegion){
                $('#fmDistributionRegion').combobox("select",row.distributionRegion);
            } else{
                $('#fmDistributionRegion').combobox("select","");
            }
            if(row.freightMethod){
                $('#fmFreightMethod').combobox("select",row.freightMethod);
            }
            else
            {
                $('#fmFreightMethod').combobox("setValue","");
            }
            if(row.beginDate){
                $('#fmBeginDate').datebox("setValue",row.beginDate);
            }
            if(row.endDate){
                $('#fmEndDate').datebox("setValue",row.endDate);
            }
            if(row.status){
                $('#fmStatus').combobox("select",row.status);
            }
            if(row.isManual){
                $('#fmIsManual').combobox("select",row.isManual);
            }
            $('#lbSave').hide();
            $("#lbShow").hide();
            $("#lbEdit").show();
            //$('#dlg').dialog('open').dialog('setTitle','编辑送货承运商合同');
        } else {
            alert("请先选择送货公司!");
        }
    });

    $("#lbDelete").click(function(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('确认','要删除当前记录吗? ',function(r){
                if (r){
                    $.post('/company/contract/delete',{carrierId:row.carrierId},function(result){
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
            alert("请先选择送货承运商!");
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
                    $("#lbShow").click();
                }
            }
        });
    });

    $("#lbClose").click(function(){
        $('#dlg').dialog('close');
    });

    $("#lbClose-view").click(function(){
        $('#dlg-view').dialog('close');
    });
    //重置table宽度
    resizeTable();
    $('#tt').tabs('resize');
});

