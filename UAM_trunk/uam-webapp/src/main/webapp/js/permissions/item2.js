
function Edit_item2_Pe(){
    $('#dlg_item2_pe').dialog('setTitle','编辑').dialog('open');
}

function initEasyui_item2_Pe(){
    $('#dlg_item2_pe').dialog({
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
            handler:function(){$('#dlg_item2_pe').dialog('close') }
        }]
    });
    $('#tag_pitem2').tabs({
        border:false,
        onSelect:function(title){
//            alert(title+' is selected');
        }
    });
}
$(function(){
    //初始化easyui
    initEasyui_item2_Pe();

});