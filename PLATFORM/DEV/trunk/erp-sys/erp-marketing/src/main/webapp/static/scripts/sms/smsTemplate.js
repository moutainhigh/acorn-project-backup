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
		//title:'SMS模板列表',
		title : '',
		iconCls:'icon-save',
		width:'100%',
		height:470,
		nowrap:false,
		striped: true,
		autoRowHeight: false,
		scrollbarSize:0,
		url:'templateList',
		idField:'id',
		fitColumns:true,
		toolbar:[{
			id:'btnadd',
			text:'添加',
			iconCls:'icon-add',
			handler:function(){
				$('#addEditWin').window({
					  title:'新增短信模板',
					  iconCls:'',
					  width:600,
					  minimizable:false,
				      maximizable:false,
				      collapsible:false,
				      modal:true,
				      shadow:false
				      });
				$('#addEditWin').window('open');
			}
		},'-',{
			id:'btndel',
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){
				var selected = $('#dataList').datagrid('getSelected');
				if(null==selected){
					alerts('没有选中任记录');
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
									alerts('删除失败');
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
		        	 alerts(123);
		         }},
		         {field:'code', title:'短信编码', width:100},
		         {field:'name', title:'短信名称', width:140},
		         {field:'content', title:'短信内容', width:420
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
		         {field:'crttime', title:'创建时间', width:150, align:'center',
		        	 formatter:function(value){
		        		 var _date = new Date(value);
		        		 return _date.format('yyyy-MM-dd hh:mm:ss');
		        	 }
		         },
		         {field:'crtuid', title:'创建工号', width:100, align:'center'},
		         {field:'status', title:'当前状态', width:124,
		        	 formatter:function(value, rec){
		        		 if(value==0){
		        			 return "停用";
		        		 }else if(value==1){
		        			 return "可用";
		        		 }
		        	 }
		         },
		         {field:'themeTemplate', title:'短信主题', width:80},
		         {field:'exeDepartment', hidden: true}
		]],
		remoteSort : false,
		/*singleSelect : true,*/
		pagination : true,
		rownumbers : false,
		onDblClickRow:function(index, row){
			$('#addEditWin').find('.id').val(row.id);
			$('#addEditWin').find('.name').val(row.name);
			$('#addEditWin').find('#status').val(row.status);
			$('#addEditWin').find('#code').val(row.code);
			$('#costId').numberbox("setValue",row.cost);
			$('#addEditWin').find('.content').val(row.content);
       		var _date = new Date(row.crttime);
       		_date.format('yyyy-MM-dd hh:mm:ss');
			$('#addEditWin').find('#crttime').val(_date.format('yyyy-MM-dd hh:mm:ss'));
			$('#addEditWin').find('.crtuid').val(row.crtuid);
			$('#addEditWin').find('#themeTemplate').val(row.themeTemplate);
			if (row.exeDepartment !=null && row.exeDepartment !="") {
				$('#addEditWin').find('#exeDepartment').combobox('setValues',row.exeDepartment.split(','));
			}
			loadContentLen();
			$('#addEditWin').window({title:'编辑短信模板',iconCls:'',width:580, minimizable:false, maximizable:false,  collapsible:false,  modal:true});
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
		$('#dataList').datagrid('load', {
			code: $('#code').val(),
			name: $('#name').val()
		});
	});
	
	//清空
	$('#clearBtn').click(function(){
		$('#code').val('');
		$('#name').val('');
	});
	
	var _len = $('.content').val().length;
	$('.content_hover').html('已录入'+_len+'个字');
	
	$(window).keyup(function(event){
		var _len = $('.content').val().length;
		$('.content_hover').html('已录入'+_len+'个字');
	});
});



function saveTemplate(){
	var name = $(".name").val();
	var content = $(".content").val();
	var themeTemplate = $("#themeTemplate").val();
	if (name=="") {
		alerts("短信名称不能为空");
		return false;
	}
	if (content=="") {
		alerts("短信内容不能为空");
		return false;
	}

	 //增加敏感词过滤
	$.post('viewStopWords',{
		"content":content
	},function (data){
		if (data.wordfilter!=null&&data.wordfilter!="undefined") {
			  $.messager.confirm('确认框','模板中有敏感词'+data.wordfilter+'\n请去掉敏感词再保存模板',function(r){   
				     if (r){   
				    		var _data = $('#templateForm').serializeArray();
				    		$.ajax({
				    			type:'POST',
				    			url: 'templateSave',
				    			data:_data,
				    			cache:false,
				    			success:function(data){
				    				if (data.wordfilter!=null&&data.wordfilter!="undefined") {
				    					alerts("模板中有敏感词"+data.wordfilter+"\n请去掉敏感词再保存模板");
				    				}else {
				    					if(eval(data.success)){
				    						alerts('保存成功');
				    						$('#addEditWin').window('close');
				    						$('#dataList').datagrid('reload');
				    					}else{
				    						alerts('保存失败');
				    					}
				    				}	
				    			},
				    			error:function(msg){
				    				alerts(msg);
				    			}
				    		});   
				     }   
				  });  
		}else {		
    		var _data = $('#templateForm').serializeArray();
    		$.ajax({
    			type:'POST',
    			url: 'templateSave',
    			data:_data,
    			cache:false,
    			success:function(data){
    				if (data.wordfilter!=null&&data.wordfilter!="undefined") {
    					alerts("模板中有敏感词"+data.wordfilter+"\n请去掉敏感词再保存模板");
    				}else {
    					if(eval(data.success)){
    						alerts('保存成功');
    						$('#addEditWin').window('close');
    						$('#dataList').datagrid('reload');
    					}else{
    						alerts('保存失败');
    					}
    				}	
    			},
    			error:function(msg){
    				alerts(msg);
    			}
    		}); 
		}
	}
	);	 
}

function closeSaveWin(){
	$('#addEditWin').window('close');
}

function loadContentLen(){
	var _len = $('.content').val().length;
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