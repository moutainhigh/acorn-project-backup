$(function(){
    //初始化easyui
    initEasyui_userParam_Pa();

    $('#id_userParam_table').datagrid({
        url: '/user/list',
        title: '用户维护',
        toolbar: '#id_userParam_linkbutton',
        singleSelect: 'true',
        pagination:true,
        pageSize:20,
        columns: [
            [
                {field: 'id', checkbox: true},
                {field: 'name', title: '用户名', width: 100, align: 'center'},
                {field: 'userGroup', title: '用户组', width: 200, align: 'center',formatter: function(value,row,index){
                    if (value.name)
                        return value.name;
                }},
                {field: 'department', title: '部门', width: 200, align: 'center',formatter: function(value,row,index){
                        if(row.userGroup && row.userGroup['department'] )
                        return row.userGroup['department']['name'];
                }},
                {field: 'roles', title: '角色名', width: 300, align: 'center',formatter:function(value,row,index){
                    var roleNames = '';
                    for(var index in value)
                        roleNames += value[index]['name'] +",";
                    if(roleNames){
                        roleNames = roleNames.substring(0,roleNames.length-1);
                    }
                    return roleNames;
                }},
                {field: 'userTitle', title: '职务', width: 100, align: 'center'},
                {field: 'workGroup', title: '工作组', width: 100, align: 'center'}
            ]
        ]
    });

    $('#id_edit_userParam_roleName').combobox({
        url:'/role/list',
        method:'GET',
        valueField:'id',
        textField:'name',
        multiple:true,
        panelHeight:'auto'
    });

});



function edit_userParam_Pa(){
    var row = $('#id_userParam_table').datagrid("getSelected");
    if(row){
        $("#id_edit_userParam_id").val(row.id);
        $("#id_edit_userParam_name").val(row.name);
        $("#id_edit_userParam_roleName").combobox('clear');
        var idArray=new Array();
        for(var index in row.roles){
            var roleId = row.roles[index]['id'];
            idArray[index] = roleId;
        }
        $("#id_edit_userParam_roleName").combobox('setValues', idArray);

        $("#id_edit_userParam_userTitle").val(row.userTitle);
        $("#id_edit_userParam_workGroup").val(row.workGroup);

        $('#id_dlg_userParam_pa').dialog('setTitle','编辑').dialog('open');
    }else{
        alert("请选择编辑列");
    }
}

function del_userParam_Pa(){
    var row = $('#id_userParam_table').datagrid("getSelected");
    if(row){
        $("#id_delete_userParam_id").val(row.id);
        $('#confirm_userParam_pa').dialog('open');
    }else{
        alert("请选择删除列");
    }
}

function initEasyui_userParam_Pa(){
    $('#id_dlg_userParam_pa').dialog({
        title:' ',
        width: 400,
        closed: true,
        cache: false,
        modal: true,
        buttons:[{
            text:'保存',
            iconCls:'icon-save',
            plain:true,
            handler:function(){
                $('#id_dlg_userParam_pa').dialog('close');
                $.post("user/update",
                    {   id: $("#id_edit_userParam_id").val(),
                        userTitle: $("#id_edit_userParam_userTitle").val() ,
                        workGroup: $("#id_edit_userParam_workGroup").val(),
                        roleIdArray:$("#id_edit_userParam_roleName").combobox('getValues')
                    },
                    function (data) {
                        if (data == 'success') {
                            $('#id_userParam_table').datagrid("reload");
                        } else {
                            alert(data);
                        }
                    }
                );
            }
        },{
            text:'取消',
            plain:true,
            iconCls:'icon-cancel',
            handler:function(){$('#id_dlg_userParam_pa').dialog('close') }
        }]
    });
    $('#confirm_userParam_pa').dialog({
        title:'提示',
        width: 400,
        closed: true,
        cache: false,
        modal: true,
        buttons:[{
            text:'确定',
            iconCls:'icon-ok',
            plain:true,
            handler:function(){
                $('#confirm_userParam_pa').dialog('close');

                $.post("user/delete",
                    {   id: $("#id_delete_userParam_id").val()},
                    function (data) {
                        if (data == 'success') {
                            $('#id_userParam_table').datagrid("reload");
                        } else {
                            alert(data);
                        }
                    }
                );
            }
        },{
            text:'取消',
            plain:true,
            iconCls:'icon-cancel',
            handler:function(){$('#confirm_userParam_pa').dialog('close') }
        }]
    });
}
