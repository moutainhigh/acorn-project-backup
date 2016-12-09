$(function(){

    $('#dg-price').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height: 210,
        striped: true,
        border: true,
        collapsible:true,
        fitColumns:true,
        scrollbarSize:0,
        url:'/company/logisticsPriceRuleDetail/load',
        saveUrl: '/company/logisticsPriceRuleDetail/save',
        updateUrl: '/company/logisticsPriceRuleDetail/update',
        destroyUrl: '/company/logisticsPriceRuleDetail/delete',
        idField:'id',
        columns:[[
            {field:'amountFloor',title:'金额范围最小(>=)',width:120,editor:'text'},
            {field:'amountCeil',title:'金额范围最大(<)',width:120,editor:'text'},
            {field:'succeedFeeAmount',title:'成功费用',width:120,editor:'text'},
            {field:'succeedFeeRatio',title:'成功费率',width:120,editor:'text'},
            {field:'refusedFeeAmount',title:'拒收费用',width:120,editor:'text'},
            {field:'refusedFeeRatio',title:'拒收费率',width:120,editor:'text'},
            {field:'virtualFeeAmount',title:'虚拟成功费用',width:120,editor:'text'}
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
        onLoadSuccess: function(data){
            //alert(JSON.stringify(data));
        },
        onLoadError: function(){
            alert("运费金额规则加载失败!");
        },
        onUncheck:function(index,data){
            $(this).datagrid("clearSelections");
        },
        onSelect:function(rowIndex, rowData){
            $("#lbShow").click();
        }
    });

    var p = $('#dg-price').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    /* toolbar */
    $("#lbNew-price").click(function(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#fm-price').form('clear');
            $('#fm-price').attr('url', "/company/logisticsPriceRuleDetail/add");
            $('#fmRule-price').val(row.id);
            $('#dlg-price').dialog('open').dialog('setTitle','添加合同计费规则价格明细');
        } else {
           alert("请先选择一条运费规则价格明细");
        }
    });

    $("#lbEdit-price").click(function(){
        var row = $('#dg-price').datagrid('getSelected');
        if (row){
            $('#lbSave').show();
            $('#fm-price').form('load',row);
            $('#fm-price').attr('url', "/company/logisticsPriceRuleDetail/save");

            $('#fm-price').find('input').each(function(){
                var name = $(this).attr("name");
                if(name != "createDate" && name != "updateDate") {
                   $(this).removeAttr("disabled");
                }
            });
            //下拉属性特殊处理
            var contract =row["contract"];
            if(contract){
                $('#fm-priceWarehouse').combobox("select",contract.warehouse.warehouseId);
                $('#fm-priceContract').combobox("setValue",contract.id);
            }
            else
            {
                $('#fm-priceWarehouse').combobox("setValue","");
                $('#fm-priceContract').combobox("setValue","");
            }

            $('#dlg-price').dialog('open').dialog('setTitle','编辑合同计费规则价格明细');
        }
        else
        {
            alert("请先选择合同计费规则价格明细!");
        }
    });

    $("#lbDelete-price").click(function(){
        var row = $('#dg-price').datagrid('getSelected');
        if (row){
            $.messager.confirm('确认','要删除当前记录吗? ',function(r){
                if (r){
                    $.post('/company/logisticsPriceRuleDetail/delete',{ id:row.id},function(result){
                        if (result.success){
                            var rowIndex = $("#dg-price").datagrid("getRowIndex",row);
                            if(rowIndex > -1){
                                $("#dg-price").datagrid("deleteRow",rowIndex);
                            }
                            $('#dg-price').datagrid('reload');    // reload the user data
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
            alert("请先选择合同计费规则价格明细!");
        }
    });
    $("#lbSave-price").click(function(){

        $('#fm-price').form('submit',{
            url: $("#fm-price").attr("url"),
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
                    $('#dlg-price').dialog('close');      // close the dialog
                    $('#dg-price').datagrid('reload');    // reload the user data
                }
            }
        });
    });

    $("#lbClose-price").click(function(){
        $('#dlg-price').dialog('close');
    });

});

