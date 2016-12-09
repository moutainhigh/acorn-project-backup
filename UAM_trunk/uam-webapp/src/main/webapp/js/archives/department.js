$(function () {
    //初始化easyui
    initEasyui_department();
    //初始化角色表格
    $('#grid_department').datagrid({
        url: 'department/list', 
        title: '部门维护',
        toolbar: '#tb_department',
        filtColumn: 'true',                
        singleSelect: 'true',
        fit: true,
        fitColumns: true,
        columns: [
            [
                {field: 'id', checkbox: true},
                {field: 'name', title: '部门名称', width: 100, align: 'center'},
                {field: 'test', title: '区号', width: 100, align: 'center'},                
                {field: 'test', title: '编码', width: 100, align: 'center'},
                {field: 'test', title: '来源', width: 100, align: 'center'}
            ]
        ]
    });
   
    $('#id_department_new_name02').combobox({
        url:'/site/listuam',
        method:'GET',
        valueField:'id',
        textField:'name',
        panelHeight:'auto'
    });
    $('#id_department_new_name03').combobox({
        url:'/site/listuam',
        method:'GET',
        valueField:'id',
        textField:'name',
        panelHeight:'auto'
    });
});

function new_department() {
    $('#id_dlg_new_department').dialog('setTitle', '新增').dialog('open');
}

function edit_department() {
    var row = $('#grid_department').datagrid('getSelected');
    if (row) {
        $("#id_department_edit_id").val(row.id);
        $("#id_department_edit_name").val(row.name);
        $("#id_department_edit_description").val(row.description);
        if(row.site && row.site['id']){
            $("#id_department_edit_site_name").combobox('setValue',row.site['id']);
        } else{
            $("#id_department_edit_site_name").combobox('setValue','uam');
        }
        $('#id_dlg_edit_department').dialog('setTitle', '新增').dialog('open');
    } else {
        alert("请选择修改列");
    }
}

function del_department() {
    var row = $('#grid_department').datagrid('getSelected');
    if (row) {
        $('#confirm_department').dialog('open');
    } else {
        alert("请选择删除角色");
    }
}
function add_department() {
	$('#id_dlg_add_department').dialog('setTitle', '增加角色').dialog('open');
}
function remove_department() {
	$('#id_dlg_remove_department').dialog('setTitle', '删除角色').dialog('open');
}
function initEasyui_department() {
    //新增
    $('#id_dlg_new_department').dialog({
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
                    if (!$("#id_department_new_name").val()) {
                        alert("请输入角色名称");
                        return;
                    }

                    $('#id_dlg_new_department').dialog('close');
                    $.post("department/add", {
                            siteName:$("#id_department_new_site_name").combobox('getText'),
                            name: $("#id_department_new_name").val(), description: $("#id_department_new_description").val()},
                        function (data) {
                            if (data == 'success') {
                                $('#grid_department').datagrid("reload");
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
                    $('#id_dlg_new_department').dialog('close')
                }
            }
        ]
    });
    $('#id_dlg_add_department').dialog({
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
                    if (!$("#id_department_add_name").val()) {
                        alert("请输入角色名称");
                        return;
                    }

                    $('#id_dlg_add_department').dialog('close');
                    $.post("department/add", {
                            siteName:$("#id_department_add_site_name").combobox('getText'),
                            name: $("#id_department_add_name").val(), description: $("#id_department_add_description").val()},
                        function (data) {
                            if (data == 'success') {
                                $('#grid_department').datagrid("reload");
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
                    $('#id_dlg_add_department').dialog('close')
                }
            }
        ]
    });
    $('#id_dlg_remove_department').dialog({
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
                    if (!$("#id_department_remove_name").val()) {
                        alert("请输入角色名称");
                        return;
                    }

                    $('#id_dlg_remove_department').dialog('close');
                    $.post("department/add", {
                            siteName:$("#id_department_remove_site_name").combobox('getText'),
                            name: $("#id_department_remove_name").val(), description: $("#id_department_remove_description").val()},
                        function (data) {
                            if (data == 'success') {
                                $('#grid_department').datagrid("reload");
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
                    $('#id_dlg_remove_department').dialog('close')
                }
            }
        ]
    });
    $('#id_dlg_edit_department').dialog({
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
                    if (!$("#id_department_edit_name").val()) {
                        alert("请输入角色新名称");
                        return;
                    }
                    $('#id_dlg_edit_department').dialog('close');
                    $.post("department/update",
                        {id: $("#id_department_edit_id").val(),siteName:$("#id_department_edit_site_name").combobox('getText'),
                            name: $("#id_department_edit_name").val(), description: $("#id_department_edit_description").val()},
                        function (data) {
                            if (data == 'success') {
                                $('#grid_department').datagrid("reload");
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
                    $('#id_dlg_edit_department').dialog('close')
                }
            }
        ]
    });
    $('#confirm_department').dialog({
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
                    $('#confirm_department').dialog('close');
                    var row = $('#grid_department').datagrid('getSelected');
                    if (row) {
                       var  rowId = row.id;
                        $.get("/department/delete/"+rowId,
                            function (data) {
                                if (data == 'success') {
                                    $('#grid_department').datagrid("reload");
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
                    $('#confirm_department').dialog('close')
                }
            }
        ]
    });
}

