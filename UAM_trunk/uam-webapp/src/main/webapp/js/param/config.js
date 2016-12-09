
function New_item4_Pa(){
    $('#dlg_item4_pa').dialog('setTitle','新增配置项').dialog('open');
}

function Edit_item4_Pa(){
	var row = $('#grid_item4').datagrid('getSelected');
	if (row != null) {
		$('#id_config_id').val(row.configId);
		$('#id_select_gruop').combobox('setValue', row.groupId);
		$('#id_select_gruop').combobox('setText', row.groupName);
		$('#id_config_name').val(row.configName);
		$('#id_config_name').validatebox('validate');
		$('#dlg_item4_pa').dialog('setTitle','编辑配置项').dialog('open');
	}
}

function Del_item4_Pa(){
	var row = $('#grid_item4').datagrid('getSelected');
	if (row != null) {
		$('#confirm_item4_pa').dialog('open');
	}
}

function initEasyui_item4_Pa(){
    $('#dlg_item4_pa').dialog({
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
            	if (!$('#id_select_gruop').combobox('isValid')
            			|| !$('#id_config_name').validatebox('isValid')) {
            		return;
            	}
            	
            	var data = {};
            	if ($('#id_config_id').val()) {
            		data.configId = $('#id_config_id').val();
            	}
            	data.groupId = $('#id_select_gruop').combobox('getValue');
            	data.configName = $('#id_config_name').val();
            	
            	$.ajax({
        			url: 'param/config/save',
        			type: 'POST',
        			async: false,
        			contentType: "application/json; charset=utf-8",
        			data: JSON.stringify(data),
        			dataType: 'json',
        			success: function(data) {
        				if (data.res == '1') {
        					alert('操作成功');
        					$('#dlg_item4_pa').dialog('close');
        					$('#grid_item4').datagrid('reload');
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
            	$('#dlg_item4_pa').dialog('close');
            }
        }]
    });
    $('#confirm_item4_pa').dialog({
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
            	var row = $('#grid_item4').datagrid('getSelected');
            	$.ajax({
        			url: 'param/config/remove/' + row.configId,
        			async: false,
        			dataType: 'text',
        			success: function(data) {
        				if (data == 'success') {
        					alert('删除成功');
        					$('#confirm_item4_pa').dialog('close');
        					$('#grid_item4').datagrid('reload');
        				}
        			}
        		});
            }
        },{
            text:'取消',
            plain:true,
            iconCls:'icon-cancel',
            handler:function(){
            	$('#confirm_item4_pa').dialog('close');
            }
        }]
    });
    
}
$(function(){
    //初始化easyui
    initEasyui_item4_Pa();

});