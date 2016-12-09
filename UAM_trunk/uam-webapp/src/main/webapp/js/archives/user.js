$(function () {
	initEasyui_user();
    //初始化easyui    
    $('#item1_datagrid').datagrid({
        title:'用户配置',
        toolbar:'#tb_item1_pe',
        fitColumn:true,
        singleSelect:true,
        fit:true,
        border: false,
        //url:'datagrid_data.json',
        columns:[[
              {field:'a',title:'用户名',width:100,align:'center'},
              {field:'b',title:'工号',width:100,align:'center'},
              {field:'c',title:'所属组',width:100,align:'center'},
              {field:'d',title:'所属部门',width:100,align:'center'},
              {field:'e',title:'来源',width:100,align:'center'},
              {field:'f',title:'角色',width:100,align:'center'}
        ]]
    });    
    //新增弹出框中[用户名]的select
    $('#id_user_new_site_name').combobox({
    	url:'',
        method:'GET',
        valueField:'id',
        textField:'name',
        panelHeight:'auto'
    });
  //新增弹出框中[所属组]的select
    $('#id_user_new_site_group').combobox({
    	url:'',
        method:'GET',
        valueField:'id',
        textField:'name',
        panelHeight:'auto'
    });
    //新增弹出框中[角色]的select
    $('#id_user_new_site_role').combobox({
    	url:'',
        method:'GET',
        valueField:'id',
        textField:'name',
        panelHeight:'auto'
    });
    $('#id_user_edit_site_name').combobox({
    	url:'',
        method:'GET',
        valueField:'id',
        textField:'name',
        panelHeight:'auto'
    });   
});

function new_user() {	
    $('#id_dlg_new_user').dialog('setTitle', '新增').dialog('open');
}

function edit_user() {
    var row = $('#grid_user').datagrid('getSelected');
    if (row) {
        $("#id_user_edit_id").val(row.id);
        $("#id_user_edit_name").val(row.name);
        $("#id_user_edit_description").val(row.description);
        if(row.site && row.site['id']){
            $("#id_user_edit_site_name").combobox('setValue',row.site['id']);
        } else{
            $("#id_user_edit_site_name").combobox('setValue','uam');
        }
        $('#id_dlg_edit_user').dialog('setTitle', '新增').dialog('open');
    } else {
        alert("请选择修改列");
    }

}

function del_user() {
    var row = $('#grid_user').datagrid('getSelected');
    if (row) {
        $('#confirm_user').dialog('open');
    } else {
        alert("请选择删除角色");
    }
}
function initEasyui_user() {
    //新增
    $('#id_dlg_new_user').dialog({
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
                    if (!$("#id_user_new_name").val()) {
                        alert("请输入角色名称");
                        return;
                    }

                    $('#id_dlg_new_user').dialog('close');
                    $.post("user/add", {
                            siteName:$("#id_user_new_site_name").combobox('getText'),
                            name: $("#id_user_new_name").val(), description: $("#id_user_new_description").val()},
                        function (data) {
                            if (data == 'success') {
                                $('#grid_user').datagrid("reload");
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
                    $('#id_dlg_new_user').dialog('close')
                }
            }
        ]
    });
    $('#id_dlg_add_user').dialog({
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
                    if (!$("#id_user_add_name").val()) {
                        alert("请输入角色名称");
                        return;
                    }

                    $('#id_dlg_add_user').dialog('close');
                    $.post("user/add", {
                            siteName:$("#id_user_add_site_name").combobox('getText'),
                            name: $("#id_user_add_name").val(), description: $("#id_user_add_description").val()},
                        function (data) {
                            if (data == 'success') {
                                $('#grid_user').datagrid("reload");
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
                    $('#id_dlg_add_user').dialog('close')
                }
            }
        ]
    });
    $('#id_dlg_remove_user').dialog({
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
                    if (!$("#id_user_remove_name").val()) {
                        alert("请输入角色名称");
                        return;
                    }

                    $('#id_dlg_remove_user').dialog('close');
                    $.post("user/add", {
                            siteName:$("#id_user_remove_site_name").combobox('getText'),
                            name: $("#id_user_remove_name").val(), description: $("#id_user_remove_description").val()},
                        function (data) {
                            if (data == 'success') {
                                $('#grid_user').datagrid("reload");
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
                    $('#id_dlg_remove_user').dialog('close')
                }
            }
        ]
    });
    $('#id_dlg_edit_user').dialog({
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
                    if (!$("#id_user_edit_name").val()) {
                        alert("请输入角色新名称");
                        return;
                    }
                    $('#id_dlg_edit_user').dialog('close');
                    $.post("user/update",
                        {id: $("#id_user_edit_id").val(),siteName:$("#id_user_edit_site_name").combobox('getText'),
                            name: $("#id_user_edit_name").val(), description: $("#id_user_edit_description").val()},
                        function (data) {
                            if (data == 'success') {
                                $('#grid_user').datagrid("reload");
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
                    $('#id_dlg_edit_user').dialog('close')
                }
            }
        ]
    });
    $('#confirm_user').dialog({
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
                    $('#confirm_user').dialog('close');
                    var row = $('#grid_user').datagrid('getSelected');
                    if (row) {
                       var  rowId = row.id;
                        $.get("/user/delete/"+rowId,
                            function (data) {
                                if (data == 'success') {
                                    $('#grid_user').datagrid("reload");
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
                    $('#confirm_user').dialog('close')
                }
            }
        ]
    });
}


