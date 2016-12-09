
function New_menu(){
    $('#dlg_menu').dialog('setTitle','新增').dialog('open');
}

function Edit_menu(){
    $('#dlg_menu').dialog('setTitle','编辑').dialog('open');
}

function Del_menu(){
    $('#confirm_menu').dialog('open');
}

function initEasyui_menu(){
    $('#dlg_menu').dialog({
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
            handler:function(){$('#dlg_menu').dialog('close') }
        }]
    });
    $('#confirm_menu').dialog({
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
            handler:function(){$('#confirm_menu').dialog('close') }
        }]
    });
}
$(function(){
    //初始化easyui
    initEasyui_menu();

});