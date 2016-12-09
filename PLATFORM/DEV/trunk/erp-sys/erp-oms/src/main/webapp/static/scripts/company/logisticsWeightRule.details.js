$(function(){

    var counties=[];
    $("#fmDestinationCounty").combobox({
        url:'/company/logisticsWeightRuleDetail/counties',
        valueField: 'countyid',
        textField: 'countyname',
        onLoadSuccess: function(data){
            if(counties.length == 0){
                counties = data;
            }
        }
    });

    var cities=[];
    $("#fmDestinationCity").combobox({
        url:'/company/logisticsWeightRuleDetail/cities',
        valueField: 'cityid',
        textField: 'cityname',
        onLoadSuccess: function(data){
            if(cities.length == 0){
                cities = data;
            }
        },
        onSelect: function(row){
            $.post('/company/logisticsWeightRuleDetail/counties', { cityId: row.cityid }, function(data){
                var value = $("#fmDestinationCounty").combobox("getValue");
                $("#fmDestinationCounty").combobox("clear");
                $("#fmDestinationCounty").combobox("loadData", data);
                $("#fmDestinationCounty").combobox("select", value);
            });
        }
    });

    var provinces=[];
    $("#fmDestinationProvince").combobox({
        url:'/company/logisticsWeightRuleDetail/provinces',
        valueField: 'provinceid',
        textField: 'chinese',
        onLoadSuccess: function(data){
            provinces = data;
        },
        onSelect: function(row){
            $.post('/company/logisticsWeightRuleDetail/cities', { provinceId: row.provinceid }, function(data){
                var value = $("#fmDestinationCity").combobox("getValue");
                $("#fmDestinationCity").combobox("clear");
                $("#fmDestinationCity").combobox("loadData", data);
                $("#fmDestinationCity").combobox("select", value);
            });
        }
    });

    $('#dg-weight').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height: 210,
        striped: true,
        border: true,
        collapsible:true,
        fitColumns:true,
        scrollbarSize:0,
        url:'/company/logisticsWeightRuleDetail/load',
        saveUrl: '/company/logisticsWeightRuleDetail/save',
        updateUrl: '/company/logisticsWeightRuleDetail/update',
        destroyUrl: '/company/logisticsWeightRuleDetail/delete',
        idField:'id',
        columns:[[
            {field:'warehouse',title:'发货仓库',width:120,editor:'text',
                formatter: function(obj) {
                    return obj != null ? obj.warehouseName : "";
             }},
            {field:'destinationProvince',title:'到达省份',width:80,editor:'text', formatter: function(value) {
                for(var i=0; i<provinces.length; i++){
                    if (provinces[i].provinceid == value) return provinces[i].chinese;
                }
                return value;
            }},
            {field:'destinationCity',title:'城市',width:80,editor:'text', formatter: function(value) {
                for(var i=0; i<cities.length; i++){
                    if (cities[i].cityid == value) return cities[i].cityname;
                }
                return value;
            }},
            {field:'destinationCounty',title:'区/县',width:80,editor:'text', formatter: function(value) {
                for(var i=0; i<counties.length; i++){
                    if (counties[i].countyid == value) return counties[i].countyname;
                }
                return value;
            }},
            {field:'weightFloor',title:'首重条件1（KG）',width:80,editor:'text'},
            {field:'weightCeil',title:'首重条件2（KG）',width:80,editor:'text'},
            {field:'weightInitial',title:'首重（KG）',width:80,editor:'text'},
            {field:'weightInitialFee',title:'首重费用',width:80,editor:'text'},
            {field:'weightIncrease',title:'续重（KG）',width:80,editor:'text'},
            {field:'weightIncreaseFee',title:'单位续重费用',width:100,editor:'text'},
            {field:'succeedFeeAmount',title:'成功服务费',width:100,editor:'text'},
            {field:'roundType',title:'取整方式',width:80,editor:'text',
                formatter: function(value) {
                    if('1' == value){
                        return '向上取整';
                    } else if('0' == value || null == value){
                        return '正常';
                    }
                }
            },
            {field:'refusedFeeAmount',title:'拒收服务费',width:100,editor:'text'},
            {field:'refusedIncludePostFee',title:'拒收是否包含邮费',width:80,editor:'text',
                formatter: function(value) {
                    if('1' == value){
                        return '是';
                    }else if('0' == value || null == value){
                        return '否';
                    }
                }
            },
            {field:'succeedFeeRatio',title:'成功费率',width:100,editor:'text'},
            {field:'refusedFeeRatio',title:'拒收费率',width:100,editor:'text'},
            {field:'sideLength',title:'边长（cm）',width:100,editor:'text'},
            {field:'volumeFeeAmount',title:'每立方体积费用',width:100,editor:'text'}
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

    var p = $('#dg-weight').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    /* toolbar */
    $("#lbNew-weight").click(function(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#fm-weight').form('clear');
            $('#fm-weight').attr('url', "/company/logisticsWeightRuleDetail/add");
            $('#fmRule-weight').val(row.id);
            $('#dlg-weight').dialog('open').dialog('setTitle','添加合同计费规则价格明细');
        } else {
           alert("请先选择一条运费规则价格明细");
        }
    });

    $("#lbEdit-weight").click(function(){
        var row = $('#dg-weight').datagrid('getSelected');
        if (row){
            $('#lbSave').show();
            $('#fm-weight').form('load',row);
            $('#fm-weight').attr('url', "/company/logisticsWeightRuleDetail/save");

            $('#fm-weight').find('input').each(function(){
                var name = $(this).attr("name");
                if(name != "createDate" && name != "updateDate") {
                   $(this).removeAttr("disabled");
                }
            });
            //下拉属性特殊处理
            if(row.destinationProvince){
                $('#fmDestinationProvince').combobox("select",row.destinationProvince);
            }

            $('#dlg-weight').dialog('open').dialog('setTitle','编辑合同计费规则价格明细');
        }
        else
        {
            alert("请先选择合同计费规则价格明细!");
        }
    });

    $("#lbDelete-weight").click(function(){
        var row = $('#dg-weight').datagrid('getSelected');
        if (row){
            $.messager.confirm('确认','要删除当前记录吗? ',function(r){
                if (r){
                    $.post('/company/logisticsWeightRuleDetail/delete',{ id:row.id},function(result){
                        if (result.success){
                            var rowIndex = $("#dg-weight").datagrid("getRowIndex",row);
                            if(rowIndex > -1){
                                $("#dg-weight").datagrid("deleteRow",rowIndex);
                            }
                            $('#dg-weight').datagrid('reload');    // reload the user data
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
    $("#lbSave-weight").click(function(){

        $('#fm-weight').form('submit',{
            url: $("#fm-weight").attr("url"),
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
                    $('#dlg-weight').dialog('close');      // close the dialog
                    $('#dg-weight').datagrid('reload');    // reload the user data
                }
            }
        });
    });

    $("#lbClose-weight").click(function(){
        $('#dlg-weight').dialog('close');
    });

});

