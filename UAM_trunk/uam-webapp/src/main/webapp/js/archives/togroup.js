
function New_togroup(){
    $('#dlg_togroup').dialog('setTitle','新增').dialog('open');
}

function Edit_togroup(){
    $('#dlg_togroup').dialog('setTitle','编辑').dialog('open');
}

function Del_togroup(){
    $('#confirm_togroup').dialog('open');
}

function initEasyui_togroup(){
    $('#dlg_togroup').dialog({
        title:' ',
        width: 1000,
        height:510,
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
            handler:function(){$('#dlg_togroup').dialog('close') }
        }]
    });
    $('#confirm_togroup').dialog({
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
            handler:function(){$('#confirm_togroup').dialog('close') }
        }]
    });

    $('#dlg_togroup_layout').layout();

    $('#current_group').datagrid({
//        url:'datagrid_data.json',
        columns:[[
            {field:'a',title:'菜单组名称',width:100,align:'center'}
        ]]
    });
    $('#all_group').datagrid({
//        url:'datagrid_data.json',
        columns:[[
            {field:'e',title:'菜单组名称',width:100,align:'center'}
        ]]
    });
    $('#undo').linkbutton();
    $('#redo').linkbutton();
}

function saveTags(){
//    if ($('#searchTab').val()!=null||$('#searchTab').val()!=undefined) {
//        var searchTab = $('#searchTab').tabs('getSelected');
//        tempId = searchTab.panel('options').tab[0].id;
//    }

    var rows_checked =  $('#all_group').datagrid('getChecked');
    var rows_current =  $('#current_group').datagrid('getRows');
    for(var i=0;i<rows_checked.length;i++){
        var  isEx = false;
        for(var j=0;j<rows_current.length;j++){
            currentId  =  rows_current[j]["id"] ;
            if(rows_checked[i]["id"]==currentId){
                isEx = true;
            }
        }
        if(!isEx){
            $('#current_group').datagrid('appendRow',rows_checked[i]);
        }
    }
}
function removeTag(){
    var rows_checked =  $('#current_group').datagrid('getChecked');
    for(var i = rows_checked.length-1;i>-1;i--){
        var index_checked = $('#current_group').datagrid('getRowIndex',rows_checked[i]);
        $('#current_group').datagrid('deleteRow',index_checked);

    }

}
$(function(){
    //初始化easyui
    initEasyui_togroup();

});