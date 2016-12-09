$(function(){
    //初始化easyui
    initEasyui_menuG();

    //初始化菜单组
    $('#grid_menuG').datagrid({
        url: 'menuGroup/list',
        title: '角色维护',
        toolbar: '#tb_menuG',
        filtColumn: 'true',
        border: false,
        singleSelect: 'true',
        fit: true,
        fitColumns: true,
        columns: [
            [
                {field: 'id', checkbox: true},
                {field: 'name', title: '角色名', width: 100, align: 'center'},
                {field: 'description', title: '描述', width: 100, align: 'center'}
            ]
        ]
    });
});

function new_menuG(){
    $('#id_dlg_new_menuG').dialog('setTitle','新增').dialog('open');
}

function edit_menuG(){
    $('#id_dlg_eidt_menuG').dialog('setTitle','编辑').dialog('open');
}

function del_menuG(){
    $('#confirm_menuG').dialog('open');
}

function initEasyui_menuG(){
    $('#id_dlg_new_menuG').dialog({
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
                $('#id_dlg_new_menuG').dialog('close');

            }
        },{
            text:'取消',
            plain:true,
            iconCls:'icon-cancel',
            handler:function(){$('#id_dlg_new_menuG').dialog('close') }
        }]
    });

    $('#id_dlg_edit_menuG').dialog({
        title:' ',
        width: 400,
        closed: true,
        cache: false,
        modal: true,
        buttons:[{
            text:'保存',
            iconCls:'icon-save',
            plain:true,
            handler:function(){}
        },{
            text:'取消',
            plain:true,
            iconCls:'icon-cancel',
            handler:function(){$('#id_dlg_new_menuG').dialog('close') }
        }]
    });

    $('#confirm_menuG').dialog({
        title:'提示',
        width: 400,
        closed: true,
        cache: false,
        modal: true,
        buttons:[{
            text:'确定',
            iconCls:'icon-ok',
            plain:true,
            handler:function(){}
        },{
            text:'取消',
            plain:true,
            iconCls:'icon-cancel',
            handler:function(){$('#confirm_menuG').dialog('close') }
        }]
    });
}
