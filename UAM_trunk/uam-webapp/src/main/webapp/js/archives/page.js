
function New_page(){
    $('#dlg_role').dialog('setTitle','新增').dialog('open');
}

function Edit_page(){
    $('#dlg_role').dialog('setTitle','编辑').dialog('open');
}

function Del_page(){
    $('#confirm_role').dialog('open');
}

function initEasyui_page(){
    $('#dlg_page').dialog({
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
            handler:function(){$('#dlg_role').dialog('close') }
        }]
    });
    $('#confirm_page').dialog({
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
            handler:function(){$('#confirm_role').dialog('close') }
        }]
    });
}
$(function(){
    //初始化easyui
    initEasyui_page();

});