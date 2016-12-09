$(function(){
	
	$.extend($.fn.validatebox.defaults.rules, {     
		maxLength: {     
			validator: function(value, param){     
				return param[0] >= value.length;     
			},     
			message: '最大允许输入{0}位字符.'    
		}     
	});
	
	$('#addEditWin').window({
		/*title:'新增短信模板', */
        iconCls:'icon-save',
        closed:true,
        resizable:false,
        minimizable:false,
        maximizable:false,
        onBeforeClose:function(){
        	$('#templateForm')[0].reset();
        	$('.id').val('');
        	$('.crtuid').val('');
        	$('.crttime').val('');
        	$('#costId').numberbox("setValue", null);
        	$('.content_hover').html('已录入0个字');
        }
	});
	
	$('#dataList').datagrid({
		title:'SMS模板列表',
		iconCls:'icon-save',
		nowrap:true,
		striped: true,
		autoRowHeight: false,
		width:700,
		height:350,
		url:'templateList',
		idField:'id',
		fit:true,
		toolbar:[{
			id:'btnadd',
			text:'添加',
			iconCls:'icon-add',
			handler:function(){
				$('#addEditWin').window('setTitle', '新增短信模板');
				$('#addEditWin').window('open');
			}
		},'-',{
			id:'btndel',
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){
				var selected = $('#dataList').datagrid('getSelected');
				if(null==selected){
					alert('没有选中任记录');
				}else{
					if(confirm('确定要删除选中的模板？')){
						//var _id = selected.id;
						var ids = [];
						var rows = $('#dataList').datagrid('getSelections');
						for(var i=0;i<rows.length;i++){
							ids.push(rows[i].id);
						}
						ids.join(',');
						
						$.ajax({
							type:'POST',
							url:'templateDelete',
							data:{'ids':ids.toString()},
							success:function(data){
								if(!eval(data.success)){
									alert('删除失败');
								}
								$('#dataList').datagrid('reload');
							}
						});
					}
				}
				console.log('selected.id', selected.id);
			}
		}],
		columns:[[
		         {field:'ck',checkbox:true, handler:function(){
		        	 alert(123);
		         }},
		         {field:'code', title:'短信编码', width:100},
		         {field:'name', title:'短信名称', width:150},
		         {field:'content', title:'短信内容', width:300
		        	 /*,
		        	 formatter:function(value, rec){
		        		 if(value.length>23){
		        			 return value.substring(0, 23) + '...';
		        		 }else{
		        			 return value;
		        		 }
		        		 console.log(value.length);
		        	 }*/
		         },
		         {field:'crttime', title:'创建时间', width:130, align:'center',
		        	 formatter:function(value){
		        		 var _date = new Date(value);
		        		 return _date.format('yyyy-MM-dd hh:mm:ss');
		        	 }
		         },
		         {field:'crtuid', title:'创建工号', width:70, align:'center'},
		         {field:'status', title:'当前状态', width:100,
		        	 formatter:function(value, rec){
		        		 if(value==0){
		        			 return "不可用";
		        		 }else if(value==1){
		        			 return "可用";
		        		 }
		        	 }
		         }
		]],
		remoteSort : false,
		/*singleSelect : true,*/
		pagination : true,
		rownumbers : false,
		onDblClickRow:function(index, row){
			$('#addEditWin').find('.id').val(row.id);
			$('#addEditWin').find('.name').val(row.name);
			$('#addEditWin').find('.status').val(row.status);
			$('#addEditWin').find('.code').val(row.code);
			$('#addEditWin').find('.deptid').val(row.deptid);
			$('#costId').numberbox("setValue",row.cost);
			$('#addEditWin').find('.content').val(row.content);
			$('#addEditWin').find('.crttime').val(row.crttime);
			$('#addEditWin').find('.crtuid').val(row.crtuid);
			
			loadContentLen();
			$('#addEditWin').window('setTitle', '编辑短信模板');
			$('#addEditWin').window('open');
		}
	});
	
	var p = $('#dataList').datagrid('getPager');
	$(p).pagination({
		pageSize : 10,
		pageList : [ 5, 10, 15 ],
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function() {
			$(this).pagination('loading');
			$(this).pagination('loaded');
		}
	});
	
	//查询
	$('#queryBtn').click(function(){
		$('#dataList').datagrid('reload', {
			code: $('#code').val(),
			name: $('#name').val()
		});
	});
	
	//清空
	$('#clearBtn').click(function(){
		$('#code').val('');
		$('#name').val('');
	});
	
	var _len = $('#content').val().length;
	$('.content_hover').html('已录入'+_len+'个字');
	
	$(window).keyup(function(event){
		var _len = $('#content').val().length;
		$('.content_hover').html('已录入'+_len+'个字');
	});
});



function saveTemplate(){
	var _data = $('#templateForm').serialize();
	
	var validForm = $('#templateForm').form('validate');
	if(!validForm){
		return false;
	}
	
	$.ajax({
		type:'POST',
		url: 'templateSave',
		data:_data,
		cache:false,
		success:function(data){
			if(eval(data.success)){
				$('#addEditWin').window('close');
				$('#dataList').datagrid('reload');
			}else{
				alert('保存失败');
			}
		},
		error:function(msg){
			alert(msg);
		}
	});
	 
}

function closeSaveWin(){
	$('#addEditWin').window('close');
}

function loadContentLen(){
	var _len = $('#content').val().length;
	$('.content_hover').html('已录入'+_len+'个字');
}


/**
 * 时间对象的格式化;
 */
Date.prototype.format = function(format) {
	/*
	 * eg:format="yyyy-MM-dd hh:mm:ss";
	 */
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
		// millisecond
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
						- RegExp.$1.length));
	}

	for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
							? o[k]
							: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};