$(function () {
    //初始化easyui
    initEasyui_sys();

    //初始化角色表格
    $('#grid_sys').datagrid({
        url: 'site/list',
        title: '站点维护',
        toolbar: '#tb_sys',
        filtColumn: 'true',
        border: false,
        singleSelect: 'true',
        fit: true,
        fitColumns: true,
        columns: [
            [
                {field: 'id', checkbox: true},
                {field: 'name', title: '名称', width: 100},
                {field: 'serviceId', title: '访问路径', width: 120},
                {field: 'description', title: '描述', width: 150},
                {field: 'enabled', title: '是否启用', width: 50},
                {field: 'ssoEnabled', title: '单点登录', width: 50},
                {field: 'anonymousAccess', title: '匿名访问', width: 50},
                {field: 'allowedToProxy', title: '允许代理', width: 50},
                {field: 'createDate', title: '创建时间', width: 100},
                {field: 'updateDate', title: '修改时间', width: 100}
            ]
        ]
    });

});

function new_sys() {
    $('#id_dlg_new_sys').dialog('setTitle', '新增').dialog('open');
}

function edit_sys() {
    var row = $('#grid_sys').datagrid('getSelected');
    if (row) {
        $("#id_edit_site_id").val(row.id);
        $("#id_edit_site_name").val(row.name);
        $("#id_edit_site_serviceId").val(row.serviceId);
        $("#id_edit_site_description").val(row.description);
        $("#id_edit_site_enabled").attr("checked", row.enabled);
        $("#id_edit_site_ssoEnabled").attr("checked", row.ssoEnabled);
        $("#id_edit_site_anonymousAccess").attr("checked", row.anonymousAccess);
        $("#id_edit_site_allowedToProxy").attr("checked", row.allowedToProxy);

        $('#id_dlg_edit_sys').dialog('setTitle', '编辑').dialog('open');
    } else {
        alert("请选择修改列");
    }

}

function Del_sys() {
    var row = $('#grid_sys').datagrid('getSelected');
    if (row) {
        $('#confirm_sys').dialog('open');
    } else {
        alert("请选择删除角色");
    }


}

function initEasyui_sys() {
    //新增
    $('#id_dlg_new_sys').dialog({
        title: '新增',
        width: 400,
        closed: true,
        cache: false,
        modal: true,
        buttons: [
            {
                text: '保存',
                iconCls: 'icon-save',
                plain: true,
                handler: function () {
                    if (!$("#id_new_site_name").val()) {
                        alert("请输入站点名称");
                        return;
                    }
                    $('#id_dlg_new_sys').dialog('close');
                    var data = {
                		name: $("#id_new_site_name").val(),
                		serviceId: $("#id_new_site_serviceId").val(),
                		description: $("#id_new_site_description").val(),
                		ssoEnabled: $("#id_new_site_ssoEnabled").is(":checked"),
                		anonymousAccess: $("#id_new_site_anonymousAccess").is(":checked"),
                		allowedToProxy: $("#id_new_site_allowedToProxy").is(":checked")
                    };
                    $.ajax({
                		type: 'POST',
                		async: false,
                		url: "site/add",
                		data: data,
                		success: function (data) {
                            if (data == 'success') {
                                $('#grid_sys').datagrid("reload");
                            } else {
                                alert(data);
                            }
                        }
                	});
                }
            },
            {
                text: '取消',
                plain: true,
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#id_dlg_new_sys').dialog('close');
                }
            }
        ]
    });

    $('#id_dlg_edit_sys').dialog({
        title: ' ',
        width: 400,
        closed: true,
        cache: false,
        modal: true,
        buttons: [
            {
                text: '保存',
                iconCls: 'icon-save',
                plain: true,
                handler: function () {
                    if (!$("#id_edit_site_name").val()) {
                        alert("请输入角色新名称");
                        return;
                    }
                    $('#id_dlg_edit_sys').dialog('close');
                    $.post("site/update", 
                    	{
                    		id: $("#id_edit_site_id").val(),
                    		name: $("#id_edit_site_name").val(), 
                    		serviceId: $("#id_edit_site_serviceId").val(),
                    		description: $("#id_edit_site_description").val(),
                    		enabled: $("#id_edit_site_enabled").is(":checked"),
                    		ssoEnabled: $("#id_edit_site_ssoEnabled").is(":checked"),
                    		anonymousAccess: $("#id_edit_site_anonymousAccess").is(":checked"),
                    		allowedToProxy: $("#id_edit_site_allowedToProxy").is(":checked")
                    	},
                        function (data) {
                            if (data == 'success') {
                                $('#grid_sys').datagrid("reload");
                            } else {
                                alert(data);
                            }
                        }
                    );

                }
            },
            {
                text: '取消',
                plain: true,
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#id_dlg_edit_sys').dialog('close');
                }
            }
        ]
    });
	    $('#confirm_sys').dialog({
		title : '提示',
		width : 400,
		closed : true,
		cache : false,
		modal : true,
		buttons : [ {
			text : '确定',
			iconCls : 'icon-ok',
			plain : true,
			handler : function() {
				$('#confirm_sys').dialog('close');
				var row = $('#grid_sys').datagrid('getSelected');
				if (row) {
					$.get("site/delete/" + row.id, function(data) {
						if (data == 'success') {
							$('#grid_sys').datagrid("reload");
						} else {
							alert(data);
						}
					});
				}
			}
		}, {
			text : '取消',
			plain : true,
			iconCls : 'icon-cancel',
			handler : function() {
				$('#confirm_sys').dialog('close');
			}
		} ]
	});
}

