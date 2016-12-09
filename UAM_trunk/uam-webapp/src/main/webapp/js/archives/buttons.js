
function New_buttons(){
    $('#dlg_buttons').dialog('setTitle','新增').dialog('open');
}

function Edit_buttons(){
    $('#dlg_buttons').dialog('setTitle','编辑').dialog('open');
}

function Del_buttons(){
    $('#confirm_buttons').dialog('open');
}

function initEasyui_buttons(){
    $('#dlg_buttons').dialog({
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
            handler:function(){$('#dlg_buttons').dialog('close') }
        }]
    });
    $('#confirm_buttons').dialog({
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
            handler:function(){$('#confirm_buttons').dialog('close') }
        }]
    });
}
$(function(){
    //初始化easyui
    initEasyui_buttons();

});