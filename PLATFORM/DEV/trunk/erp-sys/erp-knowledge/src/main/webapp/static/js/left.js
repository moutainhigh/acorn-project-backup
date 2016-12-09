// 是否在编辑模式
var $_isEditing = false;
// 页面用的新文件id
var $_newFileId = null;
//
var $_defaultFileNamePrefix = "File_";

(function(){
    //构建树形导航
    $("#tt").tree({
        animate: true,
        lines: true,
        method: 'get',
        url: $_ctx + '/category/tree',
        formatter: formatter,
        onClick: onClick,
        onDblClick: onDblClick,
        onContextMenu: onContextMenu,
    	onAfterEdit: onAfterEdit
    });
    $(document).keyup(function(e) {
        if($("#tt").tree('getSelected')){
            switch (e.which) {
                case 46: alert('detele');
                    break;
                case 174: alert('F2');
                    break;
            }
        }

    })
})();

/*******************************************************************************/
/***************				事件开始					************************/
/*******************************************************************************/

function formatter(node) {
	$(this).bind('contextmenu', function() { return false; });
    return node.text;
}

function onClick(node) {
	// 无论是否可编辑，首页下的链接都是跳转
	if (node.id == -1001) {
        toHome(0);
    } else if (node.id == -1002) {
    	toHome(1);
    } else if (node.id == -1003) {
    	toHome(2);
    }
    else if (node.id == -1004) {
    	toHome(4);
    }
    else if (node.id == -1005) {
    	toHome(5);
    }
}

function onDblClick(node) {
	if (isHomeLink(node.id)) {
        return;
    }
    
    if (node.attributes.isDir) {
		if (node.attributes.type != 0) {
			ajaxGetArticlesByCategory(node);
			articleList(node.id);
		}
    } else {
    	convert(node.id);
    }
}

function onContextMenu(e, node) {
	if (!$_isEditing) { // 不可编辑下不显示右键菜单
		return false;
	}
	if (typeof node.attributes == 'undefined') {
        return false;
    }
    
    if (!node.attributes.addable) { // 右键菜单中是否有增加项
        disableAdd();
    } else {
    	enableAdd();
    }
    if (node.attributes.editable) { // 右键菜单中是否有删除项
    	enableEdit();
    	enableRemove();
    } else {
    	disableEdit();
    	disableRemove();
    }
    
    if (node.attributes.isDir) {
    	var target = $('#appendFile')[0];
		$('#mm').menu('setText', { 
			target: target, 
			text: node.attributes.type == 1 ? '知识话术' : '常见问答' 
		});
		$('#mm').menu('setIcon', { 
			target: target, 
			iconCls: node.attributes.type == 1 ? 'icon-file' : 'icon-file' 
		});
    }
    
    e.preventDefault();
    $(this).tree('select', node.target);
    $('#mm').menu('show', { left: e.pageX, top: e.pageY });
}

function onAfterEdit(node) {
	if (node.attributes.isDir) {
		ajaxUpdateFolder(node);
	}
}

/*******************************************************************************/
/***************				事件结束					************************/
/*******************************************************************************/


/*******************************************************************************/
/***************				右键菜单开始  			************************/
/*******************************************************************************/

// 右键菜单中收缩子节点
function collapse() {
	var node = $('#tt').tree('getSelected');
	$('#tt').tree('collapse', node.target);
}

// 右键菜单中展开子节点
function expand() {
	var node = $('#tt').tree('getSelected');
	$('#tt').tree('expand', node.target);
}

// 右键菜单中添加文件
function appendFile() {
	if ($_newFileId != null) {
		var existNode = $('#tt').tree('find', $_newFileId);
		$('#tt').tree('remove', existNode.target);
	}
	
    var newId = $_defaultFileNamePrefix + new Date().getTime();
    $_newFileId = newId;
	
    var selectedNode = $('#tt').tree('getSelected');
    var text = selectedNode.attributes.type == 1 ? '新建知识话术' : '新建常见问答';
    var icon = selectedNode.attributes.type == 1 ? 'icon_file' : 'icon_file';
    $('#tt').tree('append', {
        parent: selectedNode.target,
        data: [{
            id: newId,
            text: text,
            iconCls: icon,
            attributes:{
            	isDir: false,
                addable: false,
                editable: true
            }
        }]
    });
    
    var newFile = $('#tt').tree('find', newId);
    $('#tt').tree('select', newFile.target);
    newArticle(selectedNode.id, selectedNode.attributes.type);
}

// 右键菜单中添加文件夹
function appendFolder() {
    var node = $('#tt').tree('getSelected');
    var newNode = $('#tt').tree('find', ajaxAddFolder(node));
    $('#tt').tree('select', newNode.target);
    $('#tt').tree('beginEdit', newNode.target);
}

function editit() {
	var node = $('#tt').tree('getSelected');
	if (node.attributes.isDir) {
		$('#tt').tree('beginEdit', node.target);
	} else {
		convert(node.id);
	}
}

// 确认删除的提示框
function confirmRemove() {
	var node = $('#tt').tree('getSelected');
	if (node.attributes.isDir) {
		var children = $('#tt').tree('getChildren', node.target);
		if (children != undefined && children.length != 0) {
            $.messager.alert('提示',
                '<p style="font-size:14px;height:30px;line-height: 30px;color: #666">不能删除含有子目录或者知识话术的父目录!<p>');
		}
		$.messager.confirm('提示',
            '<p style="font-size:14px;height:30px;line-height: 30px;color: #666">确定删除该目录?<p>',
            function(r){
	            if (r){
					removeit();
	            }
	        });
	} else {
		var str = node.attributes.type == 1 ? "知识话术" : "常见问答";
        $.messager.confirm('提示',
            '<p style="font-size:14px;height:30px;line-height: 30px;color: #666">是否删除该' + str + '?<p>',
            function(r){
                if (r){
					removeit();
                }
            });
	}
}

// 右键菜单中删除节点
function removeit() {
	var node = $('#tt').tree('getSelected');
	if (node.attributes.isDir) {
		ajaxRemoveFolder(node);
	} else {
		if (node.id.toString().indexOf($_defaultFileNamePrefix) != -1) {
			$('#tt').tree('remove', node.target);
			$_newFileId = null;
			toHome();
		} else {
			articleList(node.attributes.parentId);
			ajaxRemoveArticle(node);
		}
	}
}

// 右键菜单中禁用添加
function disableAdd() {
	var itemEl = $('#addBtns')[0]; // the menu item element
	$('#mm').menu('disableItem', itemEl);
}

// 右键菜单中启用添加
function enableAdd() {
	var itemEl = $('#addBtns')[0]; // the menu item element
	$('#mm').menu('enableItem', itemEl);
}

// 右键菜单中禁用删除
function disableRemove() {
	var ele = $('#removeBtn')[0];
	$('#mm').menu('disableItem', ele);
}

// 右键菜单中启用删除
function enableRemove() {
	var ele = $('#removeBtn')[0];
	$('#mm').menu('enableItem', ele);
}

// 右键菜单中禁用编辑
function disableEdit() {
	var ele = $('#editBtn')[0];
	$('#mm').menu('disableItem', ele);
}

// 右键菜单中启用编辑
function enableEdit() {
	var ele = $('#editBtn')[0];
	$('#mm').menu('enableItem', ele);
}

/*******************************************************************************/
/***************				右键菜单结束  			************************/
/*******************************************************************************/

// 设置当前的编辑状态
function setEditing(isEditing) {
	$_isEditing = isEditing;
}

// 判断是否是首页链接
function isHomeLink(id) {
	return id == -1001 || id == -1002 || id == -1003|| id == -1004|| id == -1005;
}

// 获取当前目录下的最后位置,用来往最后添加目录
function findPosition(selectedNode) {
	var position = -1;
	var children = $('#tt').tree('getChildren', selectedNode.target);
	$.each(children, function(){
		if (selectedNode.id == this.attributes.parentId) {
			position = this.attributes.position;
		}
	});
	return position;
}

// 后台增加目录
function ajaxAddFolder(selectedNode) {
	var data = {};
    data.name = '新建目录';
    data.parentId = selectedNode.id;
    data.isEdit = 'Y';
    data.isAdd = 'Y';
    data.position = findPosition(selectedNode) + 1;
    data.articleType = selectedNode.attributes.type;
    
    var newFolderId;
    $.ajax({
		type: 'POST',
		async: false,
		url: $_ctx + '/category/add',
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(res) {
			newFolderId = res.id;
			$('#tt').tree('append', { parent: selectedNode.target, data: [res] });
		}
	});
	
	return newFolderId;
}

// 后台更新目录名
function ajaxUpdateFolder(selectedNode) {
	$.ajax({
		type: 'POST',
		async: false,
		url: $_ctx + '/category/update/' + selectedNode.id,
		contentType: 'application/text; charset=utf-8',
		dataType: 'json',
		data: selectedNode.text,
		success: function(res) {
			$('#tt').tree('update', { target: selectedNode.target, text: selectedNode.text });
		}
	});
}

// 后台删除目录
function ajaxRemoveFolder(node) {
	$.ajax({
		type: 'POST',
		async: false,
		url: $_ctx + '/category/remove/' + node.id,
		dataType: 'text',
		data: node.text,
		success: function(res) {
			if (res == 'true') {
				$('#tt').tree('remove', node.target);
			} else {
                $.messager.alert('提示',
                    '<p style="height:30px;line-height: 30px;text-align:center;color: #666">不能删除含有子目录或者知识话术的父目录!<p>')
			}
		}
	});
}

function ajaxRemoveArticle(node) {
	$.ajax({
		type: 'POST',
		async: false,
		url: $_ctx + '/knowledge/deleteknowledge',
		data: { id: node.id,type:node.attributes.parentId },
		dataType: 'json',
		success: function(data) {
			if (data.result == true) {
				$('#tt').tree('remove', node.target);
			}
		}
	});
}

// 获取文件节点
function ajaxGetArticlesByCategory(node) {
	$.ajax({
		type: 'GET',
		url: $_ctx + '/category/articles/' + node.id,
		contentType: 'application/text; charset=utf-8',
		dataType: 'json',
		success: function(data) {
			var children = $('#tt').tree('getChildren', node.target);
			$.each(children, function() {
				if (!this.attributes.isDir && this.attributes.parentId == node.id) {
					$('#tt').tree('remove', this.target);
				}
			})
			$('#tt').tree('append', { parent: node.target, data: data }); 
		}
	});
}

// 保存文件后的回调方法
function saveFileCallback(id) {
	if ($_newFileId != null) {
		var newNode = $('#tt').tree('find', $_newFileId);
		$.ajax({
			type: 'GET',
			url: $_ctx + '/category/article/' + id,
			dataType: 'json',
			success: function(data) {
				$('#tt').tree('update', {
					target: newNode.target,
					id: data.id,
					text: data.text,
					iconCls: data.iconCls,
					state: data.state,
					attributes: data.attributes
				});
			}
		});
		$_newFileId = null;
	}
}

// 取消文件后的回调方法
function cancelFileCallback() {
	if ($_newFileId != null) {
		var existNode = $('#tt').tree('find', $_newFileId);
		$('#tt').tree('remove', existNode.target);
		$_newFileId = null;
	}
}