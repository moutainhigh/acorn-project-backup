$(function(){

	$.extend($.fn.datagrid.methods, {   
	    /**
	     * 开打提示功能  
	     * @param {} jq  
	     * @param {} params 提示消息框的样式  
	     * @return {}  
	     */  
	    doCellTip : function(jq, params) {   
	        function showTip(data, td, e) {   
	            if ($(td).text() == "")   
	                return;   
	            data.tooltip.text($(td).text()).css({   
	                        top : (e.pageY + 10) + 'px',   
	                        left : (e.pageX + 20) + 'px',   
	                        'z-index' : $.fn.window.defaults.zIndex,   
	                        display : 'block'   
	                    });   
	        };   
	        return jq.each(function() {   
	            var grid = $(this);   
	            var options = $(this).data('datagrid');   
	            if (!options.tooltip) {   
	                var panel = grid.datagrid('getPanel').panel('panel');   
	                var defaultCls = {   
	                    'border' : '1px solid #333',   
	                    'padding' : '1px',   
	                    'color' : '#333',   
	                    'background' : '#f7f5d1',   
	                    'position' : 'absolute',   
	                    'max-width' : '200px',   
	                    'border-radius' : '4px',   
	                    '-moz-border-radius' : '4px',   
	                    '-webkit-border-radius' : '4px',   
	                    'display' : 'none'   
	                };
	                var tooltip = $("<div id='celltip'></div>").appendTo('body');   
	                tooltip.css($.extend({}, defaultCls, params.cls));   
	                options.tooltip = tooltip;   
	                panel.find('.datagrid-body').each(function() {   
	                    var delegateEle = $(this).find('> div.datagrid-body-inner').length   
	                            ? $(this).find('> div.datagrid-body-inner')[0]   
	                            : this;   
	                    $(delegateEle).undelegate('td', 'mouseover').undelegate(   
	                            'td', 'mouseout').undelegate('td', 'mousemove')   
	                            .delegate('td', {   
	                                'mouseover' : function(e) {   
	                                    if (params.delay) {   
	                                        if (options.tipDelayTime)   
	                                            clearTimeout(options.tipDelayTime);   
	                                        var that = this;   
	                                        options.tipDelayTime = setTimeout(   
	                                                function() {   
	                                                    showTip(options, that, e);   
	                                                }, params.delay);   
	                                    } else {   
	                                        showTip(options, this, e);   
	                                    }   
	  
	                                },   
	                                'mouseout' : function(e) {   
	                                    if (options.tipDelayTime)   
	                                        clearTimeout(options.tipDelayTime);   
	                                    options.tooltip.css({   
	                                                'display' : 'none'   
	                                            });   
	                                },   
	                                'mousemove' : function(e) {   
	                                    var that = this;   
	                                    if (options.tipDelayTime) {   
	                                        clearTimeout(options.tipDelayTime);   
	                                        options.tipDelayTime = setTimeout(   
	                                                function() {   
	                                                    showTip(options, that, e);   
	                                                }, params.delay);   
	                                    } else {   
	                                        showTip(options, that, e);   
	                                    }   
	                                }   
	                            });   
	                });   
	  
	            }   
	  
	        });   
	    },   
	    /**
	     * 关闭消息提示功能  
	     * @param {} jq  
	     * @return {}  
	     */  
	    cancelCellTip : function(jq) {   
	        return jq.each(function() {   
	                    var data = $(this).data('datagrid');   
	                    if (data.tooltip) {   
	                        data.tooltip.remove();   
	                        data.tooltip = null;   
	                        var panel = $(this).datagrid('getPanel').panel('panel');   
	                        panel.find('.datagrid-body').undelegate('td',   
	                                'mouseover').undelegate('td', 'mouseout')   
	                                .undelegate('td', 'mousemove');   
	                    }   
	                    if (data.tipDelayTime) {   
	                        clearTimeout(data.tipDelayTime);   
	                        data.tipDelayTime = null;   
	                    }   
	                });   
	    }   
	});  
	
	initTemplateComboList();
	initTemplateList();
	
	if($('#smsSendHistory').length>0){
		//全部客户
		$('#smsSendHistory').datagrid({
			width : '100%',
			height : 410,
			scrollbarSize : 0,
			nowrap : true,
			striped: true,
			remoteSort : false,
			singleSelect : true,
			pagination : true,
			autoRowHeight : false,
			url : 'querySendHistory',
			queryParams : {'contactId' : $('#contactId').val()},
			columns : [ [ 
				{field : 'createUser', title : '发送坐席', align: 'center', width : 60}, 
				{field : 'contactId', title : '客户编号', align: 'center', width : 80}, 
				{field : 'mobile', align:'center', title : '发送手机', width : 100}, 
				{field : 'smsName', title : '模板名称', align: 'center', width : 160}, 
				{field : 'appContent', title : '短信内容', align: 'center', width : 220}, 
				{field : 'createDate', title : '发送时间', width : 140, align: 'center', formatter : dateFormatter}, 
				{field : 'appResponseCode', align:'center', title : '发送状态', width : 90}, 
				{field : 'op', title : '操作', align: 'center', width : 70, 
					formatter: function(val, row){
						if(row.smsErrorCode == 0){
							return '<a href="javascript:void(0)" onclick=javascript:resendSms(\''+row.uuid
									+'\',\''+row.contactId+'\')>重发</a>';
						}
					}
				} 
			] ],
			onLoadSuccess:function(data){   
			    $('#smsSendHistory').datagrid('doCellTip',{delay:500});   
			}  
		});
		
		var p = $('#smsSendHistory').datagrid('getPager');
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
	}

    
	
	//绑定事件
	binding();
	
	if(undefined != _contactId && null!=_contactId && ''!=_contactId){
		$('#queryContact').trigger('click');
	}

    $('#editTem').click(function(){
        $('#editTemWin').window('open');
    });
    
});

function binding() {
	$('#queryContact').click(function(){
		var contactId = $('#contactId').val();
		//加载历史发送数据
		$('#smsSendHistory').datagrid('reload',{
			'contactId' : contactId
		});
		
		$('#mobile').combobox({
			url : 'queryCustomerMobile?contactId=' + contactId,
			valueField : 'phoneNum',
			textField : 'phoneNumMask'
		});
		
		$('#orderId').combobox({
			url : 'queryOrders?contactId=' + contactId,
			valueField : 'orderId',
			textField : 'orderId'
		});
	});
	
	//发送信息
	$('#sendSms').click(function(){
		if($('#emmaCheck').html() != '') {
			return;
		}
		var _data = new Object();
		_data['customerId'] = $('#contactId').val();
		_data['mobile'] = $('#mobile').combobox('getValue');
		_data['orderId'] = $('#orderId').combobox('getValue');
		_data['smsName'] = $('#smsName').combogrid('getValue');
		_data['smsContent'] = $('#smsContent').html();
		
		sendSms(_data);
	});
	
	$('#queryOrderContact').click(function(){
		var orderId = $('#orderId').combobox('getValue');
		if (orderId) {
			$.ajax({
				type: 'GET',
				async: false,
				url: 'queryContactByOrderId?orderId=' + orderId,
				success: function(data) {
					if (data != '') {
						$('#contactId').val(data);
						$('#contactId').validatebox("validate");
						$('#queryContact').trigger('click');
					}
				}
			});
			$('#orderId').combobox('setValue', orderId);
		}
		
	});
}

function initTemplateComboList() {
	$('#smsName').combogrid({
		required: true,
		url: 'template/list/activated',
		rownumbers: true,
		idField: 'type',
		textField: 'type',
		panelWidth: 400,
		panelHeight: 320,
		required: true,
		editable: false,
		mode: 'remote', 
		toolbar: '#tt4',
		pagination: true,
		columns: [[
			{field:'type',title:'短信主题',width:400}
		]],
		onChange: function(newValue, oldValue) {
			if (!$('#contactId').validatebox('isValid')) {
				showEmmaCheck('客户编号必填');
				$('#smsName').combogrid('clear');
				return;
			}
			
			if (!$('#mobile').combobox('isValid')) {
				showEmmaCheck('手机号码必填');
				$('#smsName').combogrid('clear');
				return;
			}
			
			var req = {};
			req.contactId = $('#contactId').val();
			req.mobile = $('#mobile').combobox('getValue');
			req.orderId = $('#orderId').combobox('getValue');
			req.type = newValue;
			
			$.ajax({
				type: 'POST',
				async: false,
				url: 'merge',
				contentType: "application/json; charset=utf-8",
				dataType: 'json',
				data: JSON.stringify(req),
				success: function(data) {
					if (data.code == '0') {
						showEmmaCheck(data.msg);
						$('#smsName').combogrid('clear');
					} else {
						hideEmmaCheck();
						$('#smsContent').html(data.msg);
					}
				}
			});
			
		}
	});
}

function showEmmaCheck(html) {
	$('#emmaCheck').html('');
	$('#emmaCheck').html(html);
	$('#emmaCheck').show();
}

function hideEmmaCheck() {
	$('#emmaCheck').hide();
	$('#emmaCheck').html('');
}

function initTemplateList() {
	$('#grid_sms').datagrid({
    	url: 'template/list',
        width: '100%',
        height: 180,
        scrollbarSize: 0,
        nowrap: true,
        fitColumns: true,
        pagination: true,
        columns: [ [
            {checkbox: true},
            {field: 'type', title: '类型', width: 80, align: 'center'}, 
            {field: 'desc', title: '内容', width: 180, align: 'center'}, 
            {field: 'status', title: '是否有效', width: 40, align: 'center', 
            	formatter: function(value) {
            		return value == '1' ? '有效' : '无效';
            	}
            }
        ] ],
        remoteSort : false,
        singleSelect : true,
        onDblClickRow: function (rowIndex, row) {
        	$('#emma_type').val(row.type);
        	$('#emma_desc').val(row.desc);
        	$('#emma_status').attr('checked', row.status == '1');
        	doSaveCheck();
        }
    });
}

function saveTempleate() {
	if (!doSaveCheck()) {
		return;
	}
	
	var temp = {};
	temp.type = $('#emma_type').val();
	temp.desc = $('#emma_desc').val();
	temp.status = $('#emma_status').attr("checked") == "checked" ? "1" : "0";
	
	$.ajax({
		type: 'POST',
		async: false,
		url: 'template/save',
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(temp),
		dataType: 'json',
		success: function(data) {
			window.parent.alertWin('系统提示', '保存成功');
			$('#grid_sms').datagrid("reload");
		}
	});
}

function doSaveCheck() {
	if (!$('#emma_type').validatebox("validate")) {
		return false;
	}
	if (!$('#emma_desc').validatebox("validate")) {
		return false;
	}
	return true;
}

function cleanTemplate() {
	$('#emma_type').val('');
	$('#emma_desc').val('');
	$('#emma_status').attr('checked', false);
}

function removeTemplate() {
	var row = $('#grid_sms').datagrid('getSelected');
	if (row) {
		$('#removeConfirm').dialog('open');
		$('#removeConfirmHolder').html('');
		$('#removeConfirmHolder').html(row.type);
	}
}

function doRemoveTemplate() {
	var row = $('#grid_sms').datagrid('getSelected');
	$.ajax({
		type: 'POST',
		async: false,
		url: 'template/remove',
		contentType: "text/html; charset=utf-8",
		data: row.type,
		success: function(data) {
			$('#grid_sms').datagrid("reload");
		}
	});
	$('#removeConfirmHolder').html('');
	$('#removeConfirm').dialog('close');
}

/**
 * 短信重发
 * @param _row
 */
function resendSms(uuid, contactId){
	$.ajax({
		url : ctx+'/sms/resend',
		data : {'uuid': uuid, 'contactId': contactId},
		success : function(_rs){
			if(eval(_rs.success)){
				window.parent.alertWin('发送提醒', '发送提交成功');
				$('#smsSendHistory').datagrid('reload',{
					'contactId' : contactId
				});
			}else{
				window.parent.alertWin('发送提醒', '发送提交失败');
			}
		},
		error :function(){
			window.parent.alertWin('提示', '网络错误或会话超时');
		}
	});
}

/**
 * 短信发送
 * @param row
 */
function sendSms(_data){
	$.ajax({
		url : ctx+'/sms/send',
		data : _data,
		success : function(_rs){
			if(eval(_rs.success)){
				window.parent.alertWin('发送提醒', '发送提交成功');
				
				var _contactId = $('#customerId').val();
				//清空form
				$('#smsName').combobox('clear');
				$('#smsContent').html('');
				$('#mobile').combobox("loadData", []);
				$('#mobile').combobox('clear');
				
				//reload
				$('#contactId').val(_contactId);
				$('#queryContact').trigger('click');
			}else{
				window.parent.alertWin('发送提醒', '发送提交失败');
			}
		},
		error :function(){
			window.parent.alertWin('提示', '网络错误或会话超时');
		}
	});
}

function dateFormatter(val, row){
	if(null!=val){
		return new Date(val).format('yyyy-MM-dd hh:mm:ss');
	}
}

Date.prototype.format = function(format){
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(),    //day
	"h+" : this.getHours(),   //hour
 	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
 	"q+" : Math.floor((this.getMonth()+3)/3),  //quarter
 	"S" : this.getMilliseconds() //millisecond
	};

	if(/(y+)/.test(format)) {
		format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
	}
	
 	for(var k in o){
		if(new RegExp("("+ k +")").test(format)){
 			format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
		}
	}
	return format;
};