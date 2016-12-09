$(function () {
    //初始化easyui    
	initEasyui_group();
	
    $('#item1_datagrid').datagrid({
        title:'组维护',
        toolbar:'#tb_item1_pe',
        fitColumn:true,
        singleSelect:true,
        fit:true,        
        //url:'datagrid_data.json',
        columns:[[
            {field:'a',title:'组名称',width:100,align:'center'},
            {field:'b',title:'区号',width:100,align:'center'},
            {field:'c',title:'组类型',width:100,align:'center'},
            {field:'d',title:'来源',width:100,align:'center'},
            {field:'e',title:'所属部门',width:100,align:'center'}            
        ]]
    });    

    $('#id_group_new_site_name').combobox({
        url:'/site/listuam',
        method:'GET',
        valueField:'id',
        textField:'name',
        panelHeight:'auto'
    });
    $('#id_group_new_kind').combobox({
        url:'',
        method:'GET',
        valueField:'',
        textField:'',
        panelHeight:'auto'
    });
    $('#id_group_new_group').combobox({
        url:'/site/listuam',
        method:'GET',
        valueField:'',
        textField:'',
        panelHeight:'auto'
    });
    $('#id_group_new_name02').combobox({
        url:'/site/listuam',
        method:'GET',
        valueField:'id',
        textField:'name',
        panelHeight:'auto'
    });
    $('#id_group_new_name03').combobox({
        url:'/site/listuam',
        method:'GET',
        valueField:'id',
        textField:'name',
        panelHeight:'auto'
    });
});
function new_group() {
    $('#id_dlg_new_group').dialog('setTitle', '新增').dialog('open');
}

function edit_group() {
    var row = $('#grid_group').datagrid('getSelected');
    if (row) {
        $("#id_group_edit_id").val(row.id);
        $("#id_group_edit_name").val(row.name);
        $("#id_group_edit_description").val(row.description);
        if(row.site && row.site['id']){
            $("#id_group_edit_site_name").combobox('setValue',row.site['id']);
        } else{
            $("#id_group_edit_site_name").combobox('setValue','uam');
        }
        $('#id_dlg_edit_group').dialog('setTitle', '新增').dialog('open');
    } else {
        alert("请选择修改列");
    }

}

function del_group() {
    var row = $('#grid_group').datagrid('getSelected');
    if (row) {
        $('#confirm_group').dialog('open');
    } else {
        alert("请选择删除角色");
    }
}
function add_group() {
	$('#id_dlg_add_group').dialog('setTitle', '增加角色').dialog('open');
}
function remove_group() {
	$('#id_dlg_remove_group').dialog('setTitle', '删除角色').dialog('open');
}
function initEasyui_group() {
    //新增
    $('#id_dlg_new_group').dialog({
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
                    if (!$("#id_group_new_name").val()) {
                        alert("请输入角色名称");
                        return;
                    }

                    $('#id_dlg_new_group').dialog('close');
                    $.post("group/add", {
                            siteName:$("#id_group_new_site_name").combobox('getText'),
                            name: $("#id_group_new_name").val(), description: $("#id_group_new_description").val()},
                        function (data) {
                            if (data == 'success') {
                                $('#grid_group').datagrid("reload");
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
                    $('#id_dlg_new_group').dialog('close')
                }
            }
        ]
    });
    $('#id_dlg_add_group').dialog({
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
                    if (!$("#id_group_add_name").val()) {
                        alert("请输入角色名称");
                        return;
                    }

                    $('#id_dlg_add_group').dialog('close');
                    $.post("group/add", {
                            siteName:$("#id_group_add_site_name").combobox('getText'),
                            name: $("#id_group_add_name").val(), description: $("#id_group_add_description").val()},
                        function (data) {
                            if (data == 'success') {
                                $('#grid_group').datagrid("reload");
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
                    $('#id_dlg_add_group').dialog('close')
                }
            }
        ]
    });
    $('#id_dlg_remove_group').dialog({
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
                    if (!$("#id_group_remove_name").val()) {
                        alert("请输入角色名称");
                        return;
                    }

                    $('#id_dlg_remove_group').dialog('close');
                    $.post("group/add", {
                            siteName:$("#id_group_remove_site_name").combobox('getText'),
                            name: $("#id_group_remove_name").val(), description: $("#id_group_remove_description").val()},
                        function (data) {
                            if (data == 'success') {
                                $('#grid_group').datagrid("reload");
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
                    $('#id_dlg_remove_group').dialog('close')
                }
            }
        ]
    });
    $('#id_dlg_edit_group').dialog({
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
                    if (!$("#id_group_edit_name").val()) {
                        alert("请输入角色新名称");
                        return;
                    }
                    $('#id_dlg_edit_group').dialog('close');
                    $.post("group/update",
                        {id: $("#id_group_edit_id").val(),siteName:$("#id_group_edit_site_name").combobox('getText'),
                            name: $("#id_group_edit_name").val(), description: $("#id_group_edit_description").val()},
                        function (data) {
                            if (data == 'success') {
                                $('#grid_group').datagrid("reload");
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
                    $('#id_dlg_edit_group').dialog('close')
                }
            }
        ]
    });
    $('#confirm_group').dialog({
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
                    $('#confirm_group').dialog('close');
                    var row = $('#grid_group').datagrid('getSelected');
                    if (row) {
                       var  rowId = row.id;
                        $.get("/group/delete/"+rowId,
                            function (data) {
                                if (data == 'success') {
                                    $('#grid_group').datagrid("reload");
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
                    $('#confirm_group').dialog('close')
                }
            }
        ]
    });
}


