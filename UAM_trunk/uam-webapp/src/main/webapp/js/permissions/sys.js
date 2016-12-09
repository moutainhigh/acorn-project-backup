
function Edit_sys_Pe(){
    $('#dlg_sys_pe').dialog('setTitle','编辑').dialog('open');
}

function initEasyui_sys_Pe(){
    $('#dlg_sys_pe').dialog({
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
            handler:function(){$('#dlg_sys_pe').dialog('close') }
        }]
    });
    $('#tag_psys').tabs({
        border:true,
        onSelect:function(title){
//            alert(title+' is selected');
        }
    });
}
$(function(){
    //初始化easyui
    initEasyui_sys_Pe();

});