$(function () {
    //初始化easyui
    initEasyui_role();

    //初始化角色表格
    $('#grid_role').datagrid({
        url: 'role/list', 
        title: '角色维护',
        toolbar: '#tb_role',
        filtColumn: 'true',        
        singleSelect: 'true',
        fit: true,
        fitColumns: true,
        columns: [
            [
                {field: 'id', checkbox: true},
                {field: 'name', title: '角色名', width: 100, align: 'center'},
                {field: 'description', title: '描述', width: 100, align: 'center'},
                {field: 'site', title: '系统名', width: 100, align: 'center',formatter:function(value,row,index){
                    if(row.site){
                        return row.site.name;
                    }else{
                        return value;
                    }
                }}
            ]
        ]
    });

    $('#id_role_new_site_name').combobox({
        url:'/site/listuam',
        method:'GET',
        valueField:'id',
        textField:'name',
        panelHeight:'auto'
    });

    $('#id_role_edit_site_name').combobox({
        url:'/site/listuam',
        method:'GET',
        valueField:'id',
        textField:'name',
        panelHeight:'auto'
    });

});

function new_role() {
    $('#id_dlg_new_role').dialog('setTitle', '新增').dialog('open');
}

function edit_role() {
    var row = $('#grid_role').datagrid('getSelected');
    if (row) {
        $("#id_role_edit_id").val(row.id);
        $("#id_role_edit_name").val(row.name);
        $("#id_role_edit_description").val(row.description);
        if(row.site && row.site['id']){
            $("#id_role_edit_site_name").combobox('setValue',row.site['id']);
        } else{
            $("#id_role_edit_site_name").combobox('setValue','uam');
        }
        $('#id_dlg_edit_role').dialog('setTitle', '新增').dialog('open');
    } else {
        alert("请选择修改列");
    }

}

function del_role() {
    var row = $('#grid_role').datagrid('getSelected');
    if (row) {
        $('#confirm_role').dialog('open');
    } else {
        alert("请选择删除角色");
    }
}

function initEasyui_role() {
    //新增
    $('#id_dlg_new_role').dialog({
        width: 400,
        closed: true,
        cache: false,
        modal: true,
        buttons: [
            {
                text: '保存',
                iconCls: 'icon-save',
                plain: true,
                handler: function () {
                    if (!$("#id_role_new_name").val()) {
                        alert("请输入角色名称");
                        return;
                    }

                    $('#id_dlg_new_role').dialog('close');
                    $.post("role/add", {
                            siteName:$("#id_role_new_site_name").combobox('getText'),
                            name: $("#id_role_new_name").val(), description: $("#id_role_new_description").val()},
                        function (data) {
                            if (data == 'success') {
                                $('#grid_role').datagrid("reload");
                            } else {
                                alert(data);
                            }
                        }
                    );
                }
            },
            {
                text: '取消',
                plain: true,
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#id_dlg_new_role').dialog('close')
                }
            }
        ]
    });

    $('#id_dlg_edit_role').dialog({
        width: 400,
        closed: true,
        cache: false,
        modal: true,
        buttons: [
            {
                text: '保存',
                iconCls: 'icon-save',
                plain: true,
                handler: function () {
                    if (!$("#id_role_edit_name").val()) {
                        alert("请输入角色新名称");
                        return;
                    }
                    $('#id_dlg_edit_role').dialog('close');
                    $.post("role/update",
                        {id: $("#id_role_edit_id").val(),siteName:$("#id_role_edit_site_name").combobox('getText'),
                            name: $("#id_role_edit_name").val(), description: $("#id_role_edit_description").val()},
                        function (data) {
                            if (data == 'success') {
                                $('#grid_role').datagrid("reload");
                            } else {
                                alert(data);
                            }
                        }
                    );

                }
            },
            {
                text: '取消',
                plain: true,
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#id_dlg_edit_role').dialog('close')
                }
            }
        ]
    });
    $('#confirm_role').dialog({
        title: '提示',
        width: 400,
        closed: true,
        cache: false,
        modal: true,
        buttons: [
            {
                text: '确定',
                iconCls: 'icon-ok',
                plain: true,
                handler: function () {
                    $('#confirm_role').dialog('close');
                    var row = $('#grid_role').datagrid('getSelected');
                    if (row) {
                       var  rowId = row.id;
                        $.get("/role/delete/"+rowId,
                            function (data) {
                                if (data == 'success') {
                                    $('#grid_role').datagrid("reload");
                                } else {
                                    alert(data);
                                }
                            }
                        );
                    }
                }
            },
            {
                text: '取消',
                plain: true,
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#confirm_role').dialog('close')
                }
            }
        ]
    });   
}

