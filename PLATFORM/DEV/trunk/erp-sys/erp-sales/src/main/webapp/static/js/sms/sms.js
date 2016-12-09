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
			queryParams : {
				'contactId' : $('#customerId').val()
			},
			columns : [ [ 
			{
				field : 'createUser',
				title : '发送坐席',
				align: 'center',
				width : 50
			}, {
				field : 'contactId',
				title : '客户编号',
				align: 'center',
				width : 80
			}, {
				field : 'mobile',
	            align:'center',
				title : '发送手机',
				width : 100
			}, {
				field : 'orderIds',
				title : '订单号',
				align:'center',
				width : 100
			}, {
				field : 'smsName',
				title : '模板名称',
				align: 'center',
				width : 120
			}, {
				field : 'appContent',
				title : '短信内容',
				align: 'center',
				width : 150
			}, {
				field : 'createDate',
				title : '发送时间',
				width : 100,
				formatter : dateFormatter
			}, {
				field : 'appResponseCode',
	            align:'center',
				title : '发送状态',
				width : 100
			}, {
				field : 'op',
				title : '操作',
				align: 'center',
				width : 100,
				formatter: function(val, row){
					var _val = '';
					if(row.smsErrorCode==0){
						_val = '<a href="javascript:void(0)" onclick=javascript:resendSms(\''+row.uuid
								+'\',\''+row.contactId+'\',\''+row.orderIds+'\')>重发</a>';
					}
					return _val;
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
	
	
	//查询联系人
	$('#queryContact').click(function(){
		//加载历史发送数据
		$('#smsSendHistory').datagrid('reload',{
			'contactId' : $('#customerId').val()
		});
		
		var contactId = $('#customerId').val();
		$('#mobile').combobox({
			url : ctx+'/sms/queryCustomerMobile?contactId='+contactId,
			valueField : 'phoneNum',
			textField : 'phoneNumMask'
		});
	});
	
	//发送信息
	$('#sendSms').click(function(){
		var isValid = $('#smsForm').form('validate');
		if(!isValid){
			return;
		}
		var _data = $('#smsForm').serialize();
		var _smsName =  $('#smsName').combobox('getText');
		_data = updateURIParameter(_data, 'smsName', _smsName);
		
		sendSms(_data);
	});
	
	if(undefined != _contactId && null!=_contactId && ''!=_contactId){
		$('#queryContact').trigger('click');
	}
});

/**
 * 购物车短信发送
 * @param _row			发送数据
 * @param showMessage	是否显示发送提示
 */
function sendSmsViaOrder(_row, showMessage){
	var _obj = eval('('+_row+')');

	var _data = new Object();
	_data['mobile'] = _obj.mobile;
	_data['smsContent'] = _obj.appContent;
	_data['customerId'] = _obj.contactId;
	_data['smsName'] = _obj.smsName;
	_data['orderId'] = _obj.orderIds;
	_data['leadId'] = _obj.leadId;
	
	$.ajax({
		url : ctx+'/sms/send',
        data : _data,
        async : false,
		success : function(_rs){
			if(showMessage){
				if(eval(_rs.success)){
					window.parent.alertWin('发送提醒', '发送提交成功');
				}else{
					window.parent.alertWin('发送提醒', '发送提交失败:'+_rs.message);
				}	
			}
		},
		error :function(msg){
//console.log('msg: ', msg);
			window.parent.alertWin('提示', '网络错误或会话超时');
		}
	});
}

/**
 * 短信重发
 * @param _row
 */
function resendSms(uuid, contactId, orderIds){
	var leadId = $('#leadId').val();
	
	$.ajax({
		url : ctx+'/sms/resend',
		data : {'uuid': uuid, 'leadId':leadId, 'orderId': orderIds},
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
				$('#smsContent').val('');
				$('#mobile').combobox("loadData", []);
				$('#mobile').combobox('clear');
				
				//reload
				$('#customerId').val(_contactId);
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


//替换URL中的参数值
function updateURIParameter(url, param, paramVal){
    var newAdditionalURL = "";
    var tempArray = url.split("?");
    //var baseURL = tempArray[0];
    var additionalURL = tempArray[0];
    var temp = "";
    if (additionalURL) {
        tempArray = additionalURL.split("&");
        for (i=0; i<tempArray.length; i++){
            if(tempArray[i].split('=')[0] != param){
                newAdditionalURL += temp + tempArray[i];
                temp = "&";
            }
        }
    }

    var rows_txt = temp + "" + param + "=" + paramVal;
    return  newAdditionalURL + rows_txt;
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