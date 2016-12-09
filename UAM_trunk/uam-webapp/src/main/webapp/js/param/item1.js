
function New_item1_Pa(){
    $('#dlg_item1_pa').dialog('setTitle','新增').dialog('open');
}

function Edit_item1_Pa(){
    $('#dlg_item1_pa').dialog('setTitle','编辑').dialog('open');
}

function Del_item1_Pa(){
    $('#confirm_item1_pa').dialog('open');
}
function Im_item1_Pa(){
    $('#im_item1_pa').dialog('open');
}

function initEasyui_item1_Pa(){
    $('#dlg_item1_pa').dialog({
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
    $('#confirm_item1_pa').dialog({
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
    $('#im_item1_pa').dialog({
        title:'导入文件',
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
            handler:function(){$('#im_item1_pa').dialog('close') }
        }]
    });
}
$(function(){
    //初始化easyui
    initEasyui_item1_Pa();

});