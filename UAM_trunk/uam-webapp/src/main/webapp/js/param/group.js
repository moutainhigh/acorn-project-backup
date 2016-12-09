
function New_item3_Pa(){
    $('#dlg_item3_pa').dialog('setTitle','新增').dialog('open');      
}

function Edit_item3_Pa(){
	var row = $('#grid_item3_pa').datagrid('getSelected');
	if (row != null) {
		$('#groupId').val(row.id);
		$('#groupName').val(row.groupName);
		$('#descrition').val(row.descrition);
		$('#dlg_item3_pa').dialog('setTitle','编辑').dialog('open');
	}
}

function Del_item3_Pa(){
	var row = $('#grid_item3_pa').datagrid('getSelected');
	if (row != null) {
		$('#confirm_item3_pa').dialog('open');
	}
}

function initEasyui_item3_Pa(){
    $('#dlg_item3_pa').dialog({
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
            	// TODO validatebox cannot use
//            	alert($('#groupName').validatebox('isValid'));
//            	if (!$('#groupName').validatebox('isValid')) {
//            		return;
//            	}
            	
            	var data = {};
            	if ($('#groupId').val()) {
            		data.id = $('#groupId').val();
            	}
            	data.groupName = $('#groupName').val();
            	data.descrition = $('#descrition').val();
            	
            	$.ajax({
        			url: 'param/group/save',
        			type: 'POST',
        			async: false,
        			contentType: "application/json; charset=utf-8",
        			data: JSON.stringify(data),
        			dataType: 'json',
        			success: function(data) {
        				if (data.res == '1') {
        					alert('操作成功');
        					$('#dlg_item3_pa').dialog('close');
        					$('#grid_item3_pa').datagrid('reload');
        				} else {
        					alert(data.msg);
        				}
        			}
        		});
            }
        },{
            text:'取消',
            plain:true,
            iconCls:'icon-cancel',
            handler:function(){
            	$('#dlg_item3_pa').dialog('close');
            }
        }]
    });
    $('#confirm_item3_pa').dialog({
        title:'提示',
        width: 400,
        closed: true,
        cache: false,
        modal: true,
        buttons:[{
            text:'确定',
            iconCls:'icon-ok',
            plain:true,
            handler:function(){
            	var row = $('#grid_item3_pa').datagrid('getSelected');
            	$.ajax({
        			url: 'param/group/remove/' + row.id,
        			async: false,
        			dataType: 'text',
        			success: function(data) {
        				if (data == 'success') {
        					alert('删除成功');
        					$('#confirm_item3_pa').dialog('close');
        					$('#grid_item3_pa').datagrid('reload');
        				}
        			}
        		});
            }
        },{
            text:'取消',
            plain:true,
            iconCls:'icon-cancel',
            handler:function(){
            	$('#confirm_item3_pa').dialog('close');
            }
        }]
    });
}
$(function(){
    //初始化easyui
    initEasyui_item3_Pa();    
});