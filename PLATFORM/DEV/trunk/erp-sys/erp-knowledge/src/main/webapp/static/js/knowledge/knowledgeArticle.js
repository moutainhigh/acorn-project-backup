function saveArticle(){
	if ($('#searchTab').val()!=null||$('#searchTab').val()!=undefined) {
		var searchTab = $('#searchTab').tabs('getSelected'); 
		tempId = searchTab.panel('options').tab[0].id;
	}
	var type=$('#type'+tempId).val();
	var flag =  $('#flag'+tempId).val();
	if (flag=='true') {
		alerts("该产品知识点已存在");
		return ;
	}		
	var rows_current =  $('#tagTable'+tempId).datagrid('getRows');
    var   currentId   = new Array();
    for(var j=0;j<rows_current.length;j++){
      currentId[j] = rows_current[j]["id"];
    }
	var relationshipIdss = currentId.toString();
	if (type!="2"){
		var validForm = $('#myform'+tempId).form('validate');
		if(!validForm){
			return ;
		}
		var s = $('#product'+tempId).combogrid('grid').datagrid('getSelected');
		var s1 = $('#product'+tempId).combogrid('grid').datagrid('getSelected');
		
		if (s!=null) {
			var productCode = s.prodid;
			var productName = s.prodname;
			var scode = s.scode;
		}else {
			var productCode =  $('#productCode'+tempId).val();
			var productName = $('#productName'+tempId).val();
			var scode = $('#shortPinyin'+tempId).val();
		}								
	}
	var categoryId = $('#categoryId'+tempId).val();
	var pp = $('#maintabs'+tempId).tabs('getSelected');
	var index = $('#maintabs'+tempId).tabs('getTabIndex',pp);
	if (index==0) {	
		var contentHtml = "";
		var content ="";
		if (!$('#myEditor'+tempId).val()==undefined) {
			contentHtml = UE.getEditor('myEditor'+tempId).getAllHtml();
			content = UE.getEditor('myEditor'+tempId).getContent();
		}
		var discouseHtml = UE.getEditor('myEditor2'+tempId).getAllHtml();
		var discouse = UE.getEditor('myEditor2'+tempId).getContent();		
		var title =  $('#title'+tempId).val();
		var id =  $('#id'+tempId).val();
		$.post("knowledge/save", {
			"productCode" : productCode,
			"productName":productName,
			"shortPinyin":scode,
			"discouse":discouse,
			"content":content,
			"discourseHtml":discouseHtml,
			"contentHtml":contentHtml,		
			"categoryId":categoryId,
			"type":type,
			"title":title,
			"id":id,
			"relationshipIds":relationshipIdss
		},function(data) {			
			saveFileCallback(data.id);
			$('#id').val(data.id);
			$('#id_attachment_td').show();
			alerts(data.result);
		},'json');	
	}
	if (index==1) {
		var contentHtml = UE.getEditor('myEditor'+tempId).getAllHtml();
		var discouseHtml = UE.getEditor('myEditor2'+tempId).getAllHtml();
		var content = UE.getEditor('myEditor'+tempId).getContent();
		var discouse = UE.getEditor('myEditor2'+tempId).getContent();
		var title =  $('#title'+tempId).val();
		var id =  $('#id'+tempId).val();
		$.post("knowledge/save", {
			"productCode" : productCode,
			"productName":productName,
			"shortPinyin":scode,
			"discouse":discouse,
			"content":content,
			"discourseHtml":discouseHtml,
			"contentHtml":contentHtml,
			"categoryId":categoryId,
			"type":type,
			"title":title,
			"id":id,
			"relationshipIds":relationshipIdss
		},function(data) {
			
			alerts(data.result);		
		},'json');	
	}
	if (type!="2") {
		$('#product'+tempId).combogrid('disable');
	}
	if ($("#isExit"+tempId).val()==null) {
		$("#isExit"+tempId).attr("value","true");
		saveFileCallback(data.id);
	}

}


function saveTags(){	
	if ($('#searchTab').val()!=null||$('#searchTab').val()!=undefined) {
		var searchTab = $('#searchTab').tabs('getSelected'); 
		tempId = searchTab.panel('options').tab[0].id;
	}
	
   var rows_checked =  $('#knowledgeTable').datagrid('getChecked');
    var rows_current =  $('#tagTable'+tempId).datagrid('getRows');
         for(var i=0;i<rows_checked.length;i++){
             var  isEx = false;
             for(var j=0;j<rows_current.length;j++){
               currentId  =  rows_current[j]["id"] ;
                 if(rows_checked[i]["id"]==currentId){
                     isEx = true;
                 }
             }
             if(!isEx){
                $('#tagTable'+tempId).datagrid('appendRow',rows_checked[i]);
             }
         }       
}
function removeTag(){
    var rows_checked =  $('#tagTable'+tempId).datagrid('getChecked');
    for(var i = rows_checked.length-1;i>-1;i--){
        var index_checked = $('#tagTable'+tempId).datagrid('getRowIndex',rows_checked[i]);
        $('#tagTable'+tempId).datagrid('deleteRow',index_checked);

    }

}


//查询按钮
$("#queryProductBtn").click(function() {
	var g = $('#product'+tempId).combogrid('grid');	// get datagrid object
	var r = g.datagrid('load', {
		productName : $('#searchText1'+tempId).val()
	});
});

function addFavorite () {
	var categoryId = $('#categoryId'+tempId).val();
	var type = $('#type'+tempId).val();
	var id = $('#id'+tempId).val();
	$.post("favorite/addFavotite", {
		"categoryId":categoryId,
		"type":type,
		"articleId":id
	},function(data) {
		if (data.result=="1") {
			$('#addfav'+tempId).hide();
			$('#removefav'+tempId).show();		
			alerts("添加成功");	
		}
		if (data.result=="2") {
			$('#addfav'+tempId).hide();
			$('#removefav'+tempId).show();		
			alerts("不可重复添加");	
		}
		if (data.result=="0") {
			alerts("添加异常");		
		}
	},'json');
}
function  removeFavorite () {
	var id = $('#id'+tempId).val();
	$.post("favorite/removeFavotite", {
		"articleId":id
	},function(data) {
		if (data.result=="1") {
			$('#addfav'+tempId).show();
			$('#removefav'+tempId).hide();		
			alerts("取消成功");	
		}
		if (data.result=="0") {
			alerts("取消异常");		
		}
	},'json');
	
}


/*************** 附件相关 ***************/
function showDownloads() {
	$('#attachmentsTable'+tempId).datagrid({
		url: '/attachment/' + tag + '/list',
		singleSelect: true,
		fitColumns: true,
        scrollbarSize: -1,
        fit:true,
		columns: [[
			{checkbox: true},
			{field: 'name', title: '附件名', width: 150, formatter: formatFileName},
			{field: 'fileSize', title: '附件大小', width: 60, align: 'right', formatter: formatFileSize},
			{field: 'downloadCount', title: '下载次数', align: 'right',  width: 60},
			{field: 'url', title: '下载', align: 'center', width: 60, formatter: formatUrl}
		]],
		toolbar: [
			{iconCls: 'icon-add', handler: handleAdd},
			{iconCls: 'icon-remove', handler: handleDel}
		]
	});
	
	$('#attachmentsWindow'+tempId).window('open');
}

function handleAdd() {
	$('#uploadAttachment'+tempId).click();
}

function handleDel() {
	var rows = $('#attachmentsTable'+tempId).datagrid('getChecked');
	if (rows.length == 0) {
		return;
	}
	var html = '<p style="font-size:14px;height:30px;line-height: 30px;color: #666">确定删除?<p>';
	$.messager.defaults = { ok: "确定", cancel: "取消"};
    $.messager.confirm('提示', html, function(r){if(r)remove(rows[0].id)});
}

function formatUrl(value, row, index) {
	return '<a  id="'+index+'_download'+tempId+'" href="/attachment/download/' + row.id + '">下载</a>';
}

function formatFileName(value, row, index) {
	return value+'&nbsp;<img id="'+index+'_ok'+tempId+' " class="fr" title="完成" src="/static/style/easyui/themes/icons/ok.png"/>';
}
function formatFileSize(value, row, index) {
	var num = value / 1024 / 1024;
	return (Math.floor(num * 100) / 100) + "MB";
}

function remove(id) {
	$.ajax({
		type: 'POST',
		async: false,
		url: '/attachment/remove/' + id,
		dataType: 'text',
		success: function(data) {
			if (data == 'true') {
				$('#attachmentsTable'+tempId).datagrid('reload');
				count($('#id'+tempId).val());
			}
		}
	});
}

function count(artId) {
	$.ajax({
		type: 'POST',
		async: false,
		url: '/attachment/' + artId + '/count',
		dataType: 'text',
		success: function(res) {
			$("#attachmentCount"+tempId).html(res);
		}
	});
}

function upload() {
	
	
    var _pathArray =  $('#uploadAttachment'+tempId).val().split("\\");
    
    alert(_pathArray);
    var _filename = _pathArray[_pathArray.length-1];
    var newRow = {
        name: _filename,
        fileSize: 0,
        downloadCount: '0',
        url: ''
    };
    alert(_filename);

    $('#attachmentsTable'+tempId).datagrid('appendRow', newRow);
    var index = $('#attachmentsTable'+tempId).datagrid('getRowIndex', newRow);
    alert(index);
    $('#'+index+'_ok'+tempId).attr('src','/static/style/easyui/images/loading.gif').attr('title','上传中…');
    alert($('#'+index+'_ok'+tempId));
    $('#'+index+'_download'+tempId).removeAttr('href').addClass('disalbed');
    
    $.ajaxFileUpload({
        url: '/attachment/upload',
        secureuri: false,
        fileElementId: 'uploadAttachment'+tempId,
        data: {articleId: $('#id'+tempId).val()},
        dataType: 'json',
        success: function (data, status) {
        	if (data.id != undefined && data.id != '') {
        		$('#attachmentsTable'+tempId).datagrid('updateRow', { index: index, row: data });
        		$('#'+index+'_ok'+tempId).attr('src','/static/style/easyui/themes/icons/ok.png').attr('title','完成');
    			$('#'+index+'_download'+tempId).attr('href', '/attachment/download/' + data.id);
    			count($('#id'+tempId).val());
        	} else {
        		alert("上传失败");
        	}
        },
        error: function() {
        }
    })
    
    return false;
}


