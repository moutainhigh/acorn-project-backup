
function New_filter(){
    $('#dlg_filter').dialog('setTitle','新增').dialog('open');
}

function Edit_filter(){
    $('#dlg_filter').dialog('setTitle','编辑').dialog('open');
}

function Del_filter(){
    $('#confirm_filter').dialog('open');
}

function initEasyui_filter(){
    $('#dlg_filter').dialog({
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
            handler:function(){$('#dlg_filter').dialog('close') }
        }]
    });
    $('#confirm_filter').dialog({
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
            handler:function(){$('#confirm_filter').dialog('close') }
        }]
    });
}
$(function(){
    //初始化easyui
    initEasyui_filter();

});