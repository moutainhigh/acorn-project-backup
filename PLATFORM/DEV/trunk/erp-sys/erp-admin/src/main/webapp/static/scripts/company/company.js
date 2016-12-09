$(function(){

    $('#dg').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height: '100%',
        striped: true,
        border: true,
        collapsible:true,
        fit: true,
        url:'/admin/company/load',
        saveUrl: '/admin/company/save',
        updateUrl: '/admin/company/update',
        destroyUrl: '/admin/company/delete',
        idField:'companyId',
        columns:[[
            {field:'companyId',title:'公司编号',width:60,editor:'text'},
            {field:'nccompanyId',title:'NC公司编号',width:60,editor:'text'},
            {field:'companyName',title:'公司名称',width:100,editor:'text'},
            {field:'companyType',title:'公司类型',width:100,editor:'text',
                formatter: function(obj) {
                return obj != null ? obj.name : "";
            }
            },
            {field:'description',title:'有效',width:100,editor:'text',
                formatter: function(val) {
                    return val=="-1" ? "是" : "否";
                }
            },
            {field:'mailType',title:'订购类型',width:100,editor:'text',
                formatter: function(obj) {
                    return obj != null ? obj.name : "";
                }
            },
            {field:'bill',title:'打印发票',width:100,editor:'text',
                formatter: function(val) {
                    return val=="-1" ? "是" : "否";
                }
            },
            {field:'postFee',title:'投递费',width:100,editor:'text'},
            {field:'zip',title:'分支物流',width:100,editor:'text',
                formatter: function(val) {
                    return val=="-1" ? "是" : "否";
                }
            },
            {field:'billPostFee',title:'发票含运费',width:100,editor:'text',
                formatter: function(val) {
                    return val=="-1" ? "是" : "否";
                }
            }
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
            companyId: $("#ci").val(),
            companyName: $("#cn").val(),
            companyType: $("#ct").combobox('getValue'),
            mailType: $("#mt").combobox('getValue')
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
            $("#lbShow").click();
        }
    });

    var p = $('#dg').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
        /*onBeforeRefresh:function(){
         $(this).pagination('loading');
         alert('before refresh');
         $(this).pagination('loaded');
         }*/
    });

    $("#btnSearch").click(function () {
        $("#ci").val($("#ci").val().replace(/^\s+|\s+$/g, ""));
        $("#cn").val($("#cn").val().replace(/^\s+|\s+$/g, ""));
        $("#dg").datagrid("uncheckAll");
        $("#dg").datagrid("unselectAll");
        $('#dg').datagrid('load',{
            companyId: $("#ci").val(),
            companyName: $("#cn").val(),
            companyType: $("#ct").combobox('getValue'),
            mailType: $("#mt").combobox('getValue')
        });
    });

    $("#ct").combobox({
        url:'/admin/companyType/lookup',
        valueField: 'id',
        textField: 'name'
    });

    $("#mt").combobox({
        url:'/admin/mailType/lookup',
        valueField: 'id',
        textField: 'name'
    });

    $("#cbxCompanyType").combobox({
        url:'/admin/companyType/lookup',
        valueField: 'id',
        textField: 'name'
    });

    $("#cbxMailType").combobox({
        url:'/admin/mailType/lookup',
        valueField: 'id',
        textField: 'name'
    });

    /* toolbar */
    $("#lbNew").click(function(){
        $('#dlg').dialog('open').dialog('setTitle','添加送货公司');
        $('#fm').form('clear');
        $('#fm').attr('url', "/admin/company/add");
        //设置默认值
        $('#inpCompanyId').attr("readonly", false);
        $('#chkbill').attr("checked", true);
        $('#chkdescription').attr("checked", true);
        $('#chkzip').attr("checked", true);
        $('#chkbillPostFee').attr("checked", true);
        $('#chknoUseBill').attr("checked", true);
    });

    $("#lbEdit").click(function(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#dlg').dialog('open').dialog('setTitle','编辑送货公司');
            $('#fm').form('load',row);
            $('#fm').attr('url', "/admin/company/save");

            //下拉属性特殊处理
            $('#inpCompanyId').attr("readonly", true);
            var companyType =row["companyType"];
            if(companyType){
                $('#cbxCompanyType').combobox("setValue",companyType.id);
            }
            else
            {
                $('#cbxCompanyType').combobox("setValue","");
            }
            var mailType =row["mailType"];
            if(mailType){
                $('#cbxMailType').combobox("setValue",mailType.id);
            }
            else
            {
                $('#cbxMailType').combobox("setValue","");
            }
        }
        else
        {
            alert("请先选择送货公司!");
        }
    });

    $("#lbDelete").click(function(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('确认','要删除当前记录吗? ',function(r){
                if (r){
                    $.post('/admin/company/delete',{companyId:row.companyId},function(result){
                        if (result.success){
                            var rowIndex = $("#dg").datagrid("getRowIndex",row);
                            if(rowIndex > -1){
                                $("#dg").datagrid("deleteRow",rowIndex);
                            }
                            $('#dg').datagrid('reload');    // reload the user data
                        } else {
                            $.messager.show({   // show error message
                                title: 'Error',
                                msg: result.errorMsg
                            });
                        }
                    },'json');
                }
            });
        }
        else
        {
            alert("请先选择送货公司!");
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
                    $.messager.show({
                        title: '错误',
                        msg: result.errorMsg
                    });
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

    //费用版本设置
    $('#dg-version').edatagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height: '100%',
        striped: true,
        border: true,
        collapsible:true,
        fit: true,
        url:'/admin/freightVersion/load',
        saveUrl: '/admin/freightVersion/save',
        updateUrl: '/admin/freightVersion/update',
        destroyUrl: '/admin/freightVersion/delete',
        idField:'id',
        columns:[[
            {field:'id',title:'标示',width:60,hidden: true},
            {field:'company',title:'公司',width:60,hidden: true},
            {field:'name',title:'名称',width:100,editor:"text"},
            {field:'startDate',title:'开始日期',width:100,editor:'datebox'},
            {field:'endDate',title:'结束日期',width:100,editor:'datebox'},
            {field:'active',title:'有效',width:100,editor:{type:'checkbox',options:{on:true,off:false}},
                formatter:function(value) { return value ? "是":"否" }
            }
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: false,
        pagination:true,
        rownumbers:true,
        frozenColumns:[[
            {field:'ck',checkbox:true}
        ]],
        queryParams: {
            companyId:""
        },
        destroyMsg:{
            norecord:{
                title:'警告',
                msg:'请先选择一条记录。'
            },
            confirm:{
                title:'确认',
                msg:'您真的要删除吗？'
            }
        },
        destroyConfirmTitle: '确认',
        destroyConfirmMsg: '您真的要删除吗？',
        onLoadSuccess: function(data){
            //alert(JSON.stringify(data));
        },
        onLoadError: function(){
            //alert("当前费用版本加载失败!");
        },
        onBeforeSave: function(row, index){
            var pRow = $('#dg').edatagrid('getSelected');
            if (pRow){
                vRow = $('#dg-version').edatagrid('getSelected');
                if(vRow){
                    vRow.company = pRow.companyId;
                }
            }
        },
        onSave: function(index, row){
            $('#dg-version').edatagrid('reload');
        },
        onEdit:function(index, row){
            var e = $('#dg-version').datagrid('getEditor', {
                index: index,
                field: "active"
            });
            $(e.target).parent().find("input:checkbox:first").attr("checked", row.active);
        }
    });

    $("#lbVersion").click(function(){
        var row = $('#dg').edatagrid('getSelected');
        if (row){
            $('#dg-version').edatagrid("load", { companyId: row.companyId });
            $('#dlg-version').dialog('open').dialog('setTitle','费用版本('+row.companyName+')');
        } else {
            alert("请先选择送货公司!");
        }
    });

    $("#lbClose-version").click(function(){
        $('#dlg-version').dialog('close');
    });

    //费用版本设置
    $('#dg-quota').edatagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height: '100%',
        striped: true,
        border: true,
        collapsible:true,
        fit: true,
        url:'/admin/freightQuota/load',
        saveUrl: '/admin/freightQuota/save',
        updateUrl: '/admin/freightQuota/update',
        destroyUrl: '/admin/freightQuota/delete',
        idField:'id',
        columns:[[
            {field:'id',title:'标示',width:60,hidden: true},
            {field:'company',title:'公司',width:60,hidden: true},
            {field:'name',title:'名称',width:100,editor:"text"},
            {field:'minAmount',title:'最小金额',width:100,editor:'numberbox'},
            {field:'maxAmount',title:'最大金额',width:100,editor:'numberbox'},
            {field:'active',title:'有效',width:100,editor:{type:'checkbox',options:{on:true,off:false}},formatter:function(value) { return value ? "是":"否" }}
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: false,
        pagination:true,
        rownumbers:true,
        frozenColumns:[[
            {field:'ck',checkbox:true}
        ]],
        queryParams: {
            companyId:""
        },
        destroyMsg:{
            norecord:{
                title:'警告',
                msg:'请先选择一条记录。'
            },
            confirm:{
                title:'确认',
                msg:'您真的要删除吗？'
            }
        },
        destroyConfirmTitle: '确认',
        destroyConfirmMsg: '您真的要删除吗？',
        onLoadSuccess: function(data){
            //alert(JSON.stringify(data));

        },
        onLoadError: function(){
            alert("当前费用额度加载失败!");
        },
        onBeforeSave: function(row, index){
            var pRow = $('#dg').edatagrid('getSelected');
            if (pRow){
                vRow = $('#dg-quota').edatagrid('getSelected');
                if(vRow){
                    vRow.company = pRow.companyId;
                }
            }
        },
        onSave: function(index, row){
            $('#dg-quota').edatagrid('reload');
        },
        onEdit:function(index, row){
            var e = $('#dg-quota').datagrid('getEditor', {
                index: index,
                field: "active"
            });
            $(e.target).parent().find("input:checkbox:first").attr("checked", row.active);
        }
    });

    $("#lbQuota").click(function(){
        var row = $('#dg').edatagrid('getSelected');
        if (row){
            $('#dg-quota').edatagrid("load", { companyId: row.companyId });
            $('#dlg-quota').dialog('open').dialog('setTitle','费用额度('+row.companyName+')');
        } else {
            alert("请先选择送货公司!");
        }
    });

    $("#lbClose-quota").click(function(){
        $('#dlg-quota').dialog('close');
    });

    //费用路程
    var targets = [
        {id:'01',name:'省会城市'},
        {id:'02',name:'地级城市'},
        {id:'03',name:'县级城市'}
    ];
    var provinces=[];

    $("#lbLeg").click(function(){
        var row = $('#dg').edatagrid('getSelected');
        if (row){
            $('#dg-leg').edatagrid("load", { companyId: row.companyId });
            $('#dlg-leg').dialog('open').dialog('setTitle','费用路程('+row.companyName+')');

            $.post("/admin/province/lookup",{}, function(data){
                if (!data.isError) {
                    provinces = data;
                }
            });
        } else {
            alert("请先选择送货公司!");
        }
    });

    $("#lbClose-leg").click(function(){
        $('#dlg-leg').dialog('close');
    });

    $('#dg-leg').edatagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height: '100%',
        striped: true,
        border: true,
        collapsible:true,
        fit: true,
        url:'/admin/freightLeg/load',
        saveUrl: '/admin/freightLeg/save',
        updateUrl: '/admin/freightLeg/update',
        destroyUrl: '/admin/freightLeg/delete',
        idField:'id',
        columns:[[
            {field:'id',title:'标示',width:60,hidden: true},
            {field:'company',title:'公司',width:60,hidden: true},
            {field:'name',title:'名称',width:100,editor:"text"},
            {field:'departProvince',title:'收寄省份',width:100,editor:{
                    type:'combobox',
                    options:{
                        valueField:'provinceId',
                        textField:'chinese',
                        data:provinces
                    }
                },
                formatter: function(obj) {
                    return obj != null ? obj.chinese : "";
                }
            },
            {field:'departCity',title:'收寄城市',width:100,editor:{
                    type:'combobox',
                    options:{
                        valueField:'cityId',
                        textField:'cityName',
                        data:[]
                    }
                },
                formatter: function(obj) {
                    return obj != null ? obj.cityName : "";
                }
            },
            {field:'arrivalProvince',title:'送达省份',width:100,editor:{
                type:'combobox',
                options:{
                    valueField:'provinceId',
                    textField:'chinese',
                    data:provinces
                }
               },
               formatter: function(obj) {
                   return obj != null ? obj.chinese : "";
               }
            },
            {field:'arrivalType',title:'送达类型',width:100,editor:{
                    type:'combobox',
                    options:{
                        valueField:'id',
                        textField:'name',
                        data:targets
                    }
                },formatter:function (value){
                    for(var i=0; i<targets.length; i++){
                        if (targets[i].id == value) return targets[i].name;
                    }
                    return value;
                }
            },
            {field:'arrivalCity',title:'送达城市',width:100,editor:{
                    type:'combobox',
                    options:{
                        valueField:'cityId',
                        textField:'cityName',
                        data:[]
                    }
                },
                formatter: function(obj) {
                    return obj != null ? obj.cityName : "";
                }
            },
            {field:'firstWeight',title:'首重(克)',width:100,editor:'numberbox'},
            {field:'additionalWeight',title:'续重(克)',width:100,editor:'numberbox'},
            {field:'active',title:'有效',width:100,editor:{type:'checkbox',options:{on:true,off:false}},formatter:function(value) { return value ? "是":"否" }}
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: false,
        pagination:true,
        rownumbers:true,
        frozenColumns:[[
            {field:'ck',checkbox:true}
        ]],
        queryParams: {
            companyId:""
        },
        destroyMsg:{
            norecord:{
                title:'警告',
                msg:'请先选择一条记录。'
            },
            confirm:{
                title:'确认',
                msg:'您真的要删除吗？'
            }
        },
        destroyConfirmTitle: '确认',
        destroyConfirmMsg: '您真的要删除吗？',
        onBeforeSave: function(row, index){
            var pRow = $('#dg').edatagrid('getSelected');
            if (pRow){
                vRow = $('#dg-leg').edatagrid('getSelected');
                if(vRow){
                    vRow.company = pRow.companyId;
                }
            }
        },
        onSave: function(index, row){
            $('#dg-leg').edatagrid('reload');
        },
        onAdd:function(index, row){
            //收寄城市邦定
            var departProvince = $('#dg-leg').datagrid('getEditor', {
                index: index,
                field: "departProvince"
            });
            var departCity = $('#dg-leg').datagrid('getEditor', {
                index: index,
                field: "departCity"
            });

            $(departProvince.target).combobox({
                data: provinces,
                valueField:'provinceId',
                textField:'chinese',
                onSelect:function(record){
                    $.post("/admin/city/lookup",{ provinceId: record.provinceId }, function(data){
                        if (!data.isError) {

                            $(departCity.target).combobox({
                                data: data,
                                valueField:'cityId',
                                textField:'cityName'
                            });

                            $(departCity.target).combobox("setValue", "");
                            $(departCity.target).combobox("resize", $(departCity.target).parent().width());
                        }
                    });
                }
            });
            if(row.departProvince){
                $(departProvince.target).combobox("setValue", row.departProvince.provinceId);

                $.post("/admin/city/lookup",{ provinceId: row.departProvince.provinceId }, function(data){
                    if (!data.isError) {

                        $(departCity.target).combobox({
                            data: data,
                            valueField:'cityId',
                            textField:'cityName'
                        });
                        $(departCity.target).combobox("resize", $(departCity.target).parent().width());
                    }
                });
            }
            $(departProvince.target).combobox("resize", $(departProvince.target).parent().width());

            //送达城市邦定
            var arrivalProvince = $('#dg-leg').datagrid('getEditor', {
                index: index,
                field: "arrivalProvince"
            });

            var arrivalCity = $('#dg-leg').datagrid('getEditor', {
                index: index,
                field: "arrivalCity"
            });

            $(arrivalProvince.target).combobox({
                data: provinces,
                valueField:'provinceId',
                textField:'chinese',
                onSelect:function(record){
                    $.post("/admin/city/lookup",{ provinceId: record.provinceId }, function(data){
                        if (!data.isError) {
                            $(arrivalCity.target).combobox({
                                data: data,
                                valueField:'cityId',
                                textField:'cityName'
                            });

                            $(arrivalCity.target).combobox("setValue", "");
                            $(arrivalCity.target).combobox("resize", $(arrivalCity.target).parent().width());
                        }
                    });
                }
            });
            if(row.arrivalProvince){
                $(arrivalProvince.target).combobox("setValue", row.arrivalProvince.provinceId);
                $.post("/admin/city/lookup",{ provinceId: row.arrivalProvince.provinceId }, function(data){
                    if (!data.isError) {
                        $(arrivalCity.target).combobox({
                            data: data,
                            valueField:'cityId',
                            textField:'cityName'
                        });
                        $(arrivalCity.target).combobox("resize", $(arrivalCity.target).parent().width());
                    }
                });
            }
            $(arrivalProvince.target).combobox("resize", $(arrivalProvince.target).parent().width());
        },
        onEdit:function(index, row){
            //设置Checkbox状态
            var e = $('#dg-leg').datagrid('getEditor', {
                index: index,
                field: "active"
            });
            $(e.target).parent().find("input:checkbox:first").attr("checked", row.active);

            //收寄城市邦定
            var departProvince = $('#dg-leg').datagrid('getEditor', {
                index: index,
                field: "departProvince"
            });
            var departCity = $('#dg-leg').datagrid('getEditor', {
                index: index,
                field: "departCity"
            });

            $(departProvince.target).combobox({
                data: provinces,
                valueField:'provinceId',
                textField:'chinese',
                onSelect:function(record){
                    $.post("/admin/city/lookup",{ provinceId: record.provinceId }, function(data){
                        if (!data.isError) {

                            $(departCity.target).combobox({
                                data: data,
                                valueField:'cityId',
                                textField:'cityName'
                            });

                            $(departCity.target).combobox("setValue", "");
                            $(departCity.target).combobox("resize", $(departCity.target).parent().width());
                        }
                    });
                }
            });
            if(row.departProvince){
                $(departProvince.target).combobox("setValue", row.departProvince.provinceId);

                $.post("/admin/city/lookup",{ provinceId: row.departProvince.provinceId }, function(data){
                    if (!data.isError) {

                        $(departCity.target).combobox({
                            data: data,
                            valueField:'cityId',
                            textField:'cityName'
                        });
                        if(row.departCity)
                        {
                            $(departCity.target).combobox("setValue", row.departCity.cityId);
                            $(departCity.target).combobox("resize", $(departCity.target).parent().width());
                        }
                    }
                });
            }
            $(departProvince.target).combobox("resize", $(departProvince.target).parent().width());

            //送达城市邦定
            var arrivalProvince = $('#dg-leg').datagrid('getEditor', {
                index: index,
                field: "arrivalProvince"
            });

            var arrivalCity = $('#dg-leg').datagrid('getEditor', {
                index: index,
                field: "arrivalCity"
            });

            $(arrivalProvince.target).combobox({
                data: provinces,
                valueField:'provinceId',
                textField:'chinese',
                onSelect:function(record){
                    $.post("/admin/city/lookup",{ provinceId: record.provinceId }, function(data){
                        if (!data.isError) {
                            $(arrivalCity.target).combobox({
                                data: data,
                                valueField:'cityId',
                                textField:'cityName'
                            });

                            $(arrivalCity.target).combobox("setValue", "");
                            $(arrivalCity.target).combobox("resize", $(arrivalCity.target).parent().width());
                        }
                    });
                }
            });
            if(row.arrivalProvince){
                $(arrivalProvince.target).combobox("setValue", row.arrivalProvince.provinceId);
                $.post("/admin/city/lookup",{ provinceId: row.arrivalProvince.provinceId }, function(data){
                    if (!data.isError) {
                        $(arrivalCity.target).combobox({
                            data: data,
                            valueField:'cityId',
                            textField:'cityName'
                        });

                        if(row.arrivalCity){
                            $(arrivalCity.target).combobox("setValue", row.arrivalCity.cityId);
                            $(arrivalCity.target).combobox("resize", $(arrivalCity.target).parent().width());
                        }
                    }
                });
            }
            $(arrivalProvince.target).combobox("resize", $(arrivalProvince.target).parent().width());


        }
    });

    //费用明细
    var versions = [];
    var quotas = [];
    var legs = [];

    $("#lbLine").click(function(){
        var row = $('#dg').edatagrid('getSelected');
        if (row){
            $('#dg-line').edatagrid("load", { companyId: row.companyId });
            $('#dlg-line').dialog('open').dialog('setTitle','费用配置('+row.companyName+')');
            //获取相关绑定数据
            $.post("/admin/freightVersion/lookup", { companyId: row.companyId }, function (data) {
                if (!data.isError) {
                    versions = data;
                }
            }, "json");
            $.post("/admin/freightQuota/lookup", { companyId: row.companyId }, function (data) {
                if (!data.isError) {
                    quotas = data;
                }
            }, "json");
            $.post("/admin/freightLeg/lookup", { companyId: row.companyId }, function (data) {
                if (!data.isError) {
                    legs = data;
                }
            }, "json");
        } else {
            alert("请先选择送货公司!");
        }
    });

    $("#lbClose-line").click(function(){
        $('#dlg-line').dialog('close');
    });

    var legFormatter = function(obj) {
        if(obj){
            if(obj.departCity && obj.arrivalCity){
                return  obj.departCity.cityName +"～"+ obj.arrivalCity.cityName;
            }
            else if(obj.departCity && obj.arrivalType){
                var text;
                for(var i = 0; i < targets.length; i++)
                {
                    if(targets[i].id == obj.arrivalType)
                    {
                        text = targets[i].name;
                        break;
                    }

                }
                return  obj.departCity.cityName +"～"+  text;
            }
            return obj.name;
        }
        return "";
    }

    $('#dg-line').edatagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height: '100%',
        striped: true,
        border: true,
        collapsible:true,
        fit: true,
        url:'/admin/freightLine/load',
        saveUrl: '/admin/freightLine/save',
        updateUrl: '/admin/freightLine/update',
        destroyUrl: '/admin/freightLine/delete',
        idField:'id',
        columns:[[
            {field:'id',title:'标示',width:60,hidden: true},
            {field:'company',title:'公司',width:60,hidden: true},
            {field:'priority',title:'优先级',width:60,editor:{type:'numberbox',options:{ required:true }}},
            {field:'version',title:'版本',width:160,editor:'combobox',
                formatter: function(obj) {
                    return obj != null ? obj.startDate + "～" + obj.endDate : "";
                }
            },
            {field:'quota',title:'额度',width:120,editor:{type:'combobox',options:{valueField:'id',textField:'name',data:quotas}},
                formatter: function(obj) {
                    return obj != null ? obj.minAmount +"～"+ obj.maxAmount:"";
                }
            },
            {field:'leg',title:'路程',width:180,editor:{type:'combobox',options:{valueField:'id',textField:'name',data:legs}},
                formatter: legFormatter
            },
            {field:'basePrice',title:'投递费/首重费',width:80,editor:{type:'numberbox',options:{precision:2}}},
            {field:'surcharge',title:'提货费/续重费',width:80,editor:{type:'numberbox',options:{precision:2}}},
            {field:'baseRate',title:'手续费率',width:80,editor:{type:'numberbox',options:{precision:2}},
                formatter: function(obj) {
                    return isNaN(obj) ? "" : (100 * obj) + "%";
                }
            }
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: false,
        pagination:true,
        rownumbers:true,
        frozenColumns:[[
            {field:'ck',checkbox:true}
        ]],
        queryParams: {
            companyId:""
        },
        destroyMsg:{
            norecord:{
                title:'警告',
                msg:'请先选择一条记录。'
            },
            confirm:{
                title:'确认',
                msg:'您真的要删除吗？'
            }
        },
        destroyConfirmTitle: '确认',
        destroyConfirmMsg: '您真的要删除吗？',
        onBeforeSave: function(index, row){
            var pRow = $('#dg').edatagrid('getSelected');
            if (pRow){
                vRow = $('#dg-line').edatagrid('getSelected');
                if(vRow){
                    vRow.company = pRow.companyId;
                }
            }
        },
        onSave: function(index, row){
            $('#dg-line').edatagrid('reload');
        },
        onAdd:function(index, row){
            var e = $('#dg-line').datagrid('getEditor', {
                index: index,
                field: "version"
            });

            $(e.target).combobox({
                data: versions,
                valueField:'id',
                textField:'name',
                formatter:function(row){
                    return row.startDate +"～"+ row.endDate;
                }
            });
            if(row.version){
                $(e.target).combobox("setValue", row.version.id);
                $(e.target).combobox("resize", $(e.target).parent().width());
            }

            var e = $('#dg-line').datagrid('getEditor', {
                index: index,
                field: "quota"
            });
            $(e.target).combobox({
                data: quotas,
                valueField:'id',
                textField:'name',
                formatter:function(row){
                    if(row) {
                      return row.minAmount +"～"+ row.maxAmount;
                    }
                    return "";
                }
            });

            if(row.quota){
                $(e.target).combobox("setValue", row.quota.id);
                $(e.target).combobox("resize", $(e.target).parent().width());
            }

            var e = $('#dg-line').datagrid('getEditor', {
                index: index,
                field: "leg"
            });

            $(e.target).combobox({
                data: legs,
                valueField:'id',
                textField:'name',
                formatter: legFormatter
            });

            if(row.leg){
                $(e.target).combobox("setValue", row.leg.id);
                $(e.target).combobox("resize", $(e.target).parent().width());
            }
        },
        onEdit:function(index, row){
            var e = $('#dg-line').datagrid('getEditor', {
                index: index,
                field: "version"
            });

            $(e.target).combobox({
                data: versions,
                valueField:'id',
                textField:'name',
                formatter:function(row){
                    return row.startDate +"～"+ row.endDate;
                }
            });
            if(row.version){
                $(e.target).combobox("setValue", row.version.id);
                $(e.target).combobox("resize", $(e.target).parent().width()-1);
            }

            var e = $('#dg-line').datagrid('getEditor', {
                index: index,
                field: "quota"
            });
            $(e.target).combobox({
                data: quotas,
                valueField:'id',
                textField:'name',
                formatter:function(row){
                    return row.minAmount +"～"+ row.maxAmount;
                }
            });

            if(row.quota){
                $(e.target).combobox("setValue", row.quota.id);
                $(e.target).combobox("resize", $(e.target).parent().width()-1);
            }

            var e = $('#dg-line').datagrid('getEditor', {
                index: index,
                field: "leg"
            });

            $(e.target).combobox({
                data: legs,
                valueField:'id',
                textField:'name',
                formatter: legFormatter
            });

            if(row.leg){
                $(e.target).combobox("setValue", row.leg.id);
                $(e.target).combobox("resize", $(e.target).parent().width()-1);
            }
        }
    });

    //送货公司查看
    $("#lbShow").click(function(){
        var row = $('#dg').edatagrid('getSelected');
        if (row){
            $('#dlg-view').dialog('open').dialog('setTitle','浏览送货公司');
            //设置默认值
            $('#lblCompanyId').text(row.companyId);
            $('#lblNccompanyId').text(row.nccompanyId);
            $('#lblCompanyName').text(row.companyName);
            if(row.companyType){
                $('#lblCompanyType').text(row.companyType.name);
            }
            $('#lblDescription').text(row.description == "-1" ? "是":"否");
            if(row.mailType){
                $('#lblMailType').text(row.mailType.name);
            }
            $('#lblBill').text(row.bill == "-1" ? "是" : "否");
            $('#lblPostFee').text(row.postFee);
            $('#lblZip').text(row.zip == "-1" ? "是":"否");
            $('#lblEmail').text(row.email);
            $('#lblAreaCode').text(row.areaCode);
            $('#lblFax').text(row.fax);
            $('#lblPhone').text(row.phone);
            $('#lblSubPhone').text(row.subPhone);
            $('#lblAddress').text(row.address);
            $('#lblShortCode').text(row.shortCode);
            $('#lblCityName').text(row.cityName);
            $('#lblMailCode').text(row.mailCode);
            $('#lblSpellId').text(row.spellId);
            $('#lblRemark').text(row.remark);
            $('#lblBillPostFee').text(row.billPostFee=="-1"? "是":"否");
            $('#lblNoUseBill').text(row.noUseBill=="-1"? "是":"否");
            $('#lblRefPostFee').text(row.refPostFee);
            $('#lblToWarehouse').text(row.toWarehouse);
        } else {
            alert("请先选择送货公司!");
        }
    });
    //关闭窗口
    $("#lbClose-view").click(function(){
        $('#dlg-view').dialog('close');
    });

});

