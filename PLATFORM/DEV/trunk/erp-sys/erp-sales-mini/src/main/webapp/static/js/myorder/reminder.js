var reminder_address_init=false;
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
	
	if($('#r_orderStatus').length > 0) {
		$('#r_orderStatus').combobox({
	        data:orderStatuses,
	        valueField: 'id',
	        textField: 'dsc',
	    });	
	}
	
	if ($('#reminderDataGrid').length>0) {
		$('#reminderDataGrid').datagrid({
			width : '100%',
			height : 410,
			fitColumns : false,
			remoteSort : false,
			singleSelect : true,
			pagination : true,
			url : ctx+'/reminder/queryPageList',
			queryParams : {'initLoad' : false},
			onBeforeLoad : function(param){
				if(param.initLoad){
					return true;
				}
				return false;
			},
			columns : [ [ 
			{
				field : 'orderid',
				title : '订单编号',
				align : 'center',
				width : 100,
				formatter: function(val, row){
					var id = 'mycustomer_'+val;
					var url = ctx + '/myorder/orderDetails/get/'+val+'?activable=false';
					url = '<a class="link" href="javascript:void(0);" id=\''+id+'\' onclick="window.parent.addTab(\'myorder_'+val+'\', \'订单：'+val+'\', \'false\', \''+url+'\',\'showCallback\')">'+val+'</a>';
					return url;
				}
			}, {
				field : 'crdt',
				title : '订单生成时间',
				align : 'center',
				width : 100,
				formatter : dateFormatter
			}, {
				field : 'totalprice',
				align : 'center',
				title : '订单金额',
				width : 100
			}, {
				field : 'crusr',
				title : '坐席工号',
				align : 'center',
				width : 100
			}, {
				field : 'contactId',
				align : 'center',
				title : '客户编号',
				width : 100,
				formatter: function(val, row){
					var id = 'reminder_'+val;
					var url = ctx+'/contact/1/'+row.contactId;
					url = '<a class="link" href="javascript:void(0);" id=\''+id+'\' onclick="window.parent.addTab(\''+val+'\', \'查看客户\', \'false\', \''+url+'\',\'showCallback\')">'+val+'</a>';
					return url;
				}
			}, {
				field : 'contactName',
				title : '客户姓名',
				align : 'center',
				width : 100
			}, {
				field : 'rfoutdt',
				title : '出库时间',
				align : 'center',
				width : 100,
				formatter : dateFormatter
			},{
				field : 'mailId',
				title : '包裹单号',
				align : 'center',
				width : 100
			},{
				field : 'apppsn',
				title : '提交坐席',
				align : 'center',
				width : 100
			},{
				field : 'class_',
				title : '等级',
				align : 'center',
				width : 100,
				formatter : function(value, row){
					var _lable = '';
					if('1' == value){
						_label = '普通'; 
					}else if('2' == value){
						_label = '加急'; 
					}else if('3' == value){
						_label = '紧急'; 
					}
					return _label;
				}
			},{
				field : 'applicationreason',
				title : '原因',
				align : 'center',
				width : 100
			},{
				field : 'appdate',
				title : '提交时间',
				align : 'center',
				width : 100,
				formatter : dateFormatter
			},{
				field : 'chkpsn',
				title : '确认人员',
				align : 'center',
				width : 100
			},{
				field : 'chkdate',
				title : '确认日期',
				align : 'center',
				width : 100,
				formatter : dateFormatter
			},{
				field : 'chkreason',
				title : '确认说明',
				align : 'center',
				width : 100
			},{
				field : 'endpsn',
				title : '完成人员',
				align : 'center',
				width : 100
			},{
				field : 'enddate',
				title : '完成日期',
				align : 'center',
				width : 100,
				formatter : dateFormatter
			},{
				field : 'appdsc',
				title : '完成说明',
				align : 'center',
				width : 100
			},{
				field : 'xx',
				title : '状态',
				align : 'center',
				width : 100,
				formatter: function(val, row){
					var html = '';
					if(row.appStatus==1){
						html = '待处理';
					}else if(row.appStatus==2){
						html = '处理中';
					}else if(row.appStatus==3){
						html = '已回复';
					}
					return html;
				}
			}  
			] ],
			onLoadSuccess:function(data){   
			    $('#reminderDataGrid').datagrid('doCellTip',{delay:500});
			} 
		});
		
		var p = $('#reminderDataGrid').datagrid('getPager');
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
		$('#queryReminder').click(function(){
			var valid = $('#reminderForm').form('validate');
			if(!valid){
				$('#reminderDataGrid').datagrid('loadData', {'total':0, 'rows':[]});
				return;
			}
			
			$('#reminderDataGrid').datagrid('reload',{
				'orderid' : $('#r_orderId').val(),
				'orderStatus' : $('#r_orderStatus').combobox('getValue'),
				'mailId' : $('#r_mailId').val(),
				'contactId' : $('#r_contactId').val(),
				'contactName' : $('#r_contactName').val(),
				'appStatus' : $('#r_status').combobox('getValue'),
				'beginCrDate' : $('#r_beginCrDate').datebox('getValue'),
				'endCrDate' : $('#r_endCrDate').datebox('getValue'),
				'provinceid' : ($('#province_r').length>0) ? $('#province_r').combobox('getValue'):null,
				'cityid' : ($('#cityId_r').length>0) ? $('#cityId_r').combobox('getValue') : null,
				'countyid' : ($('#countyId_r').length>0) ? $('#countyId_r').combobox('getValue'):null,
				'areaid' : ($('#areaId_r').length>0) ? $('#areaId_r').combobox('getValue') : null,
				'receiverName' : $('#r_receiverName').val(),
				'receiverPhone' : $('#r_receiverPhone').val(),
				'parentOrderId' : $('#r_parentOrderId').val(),
				'paytype' : $('#r_paytype').combobox('getValue'),
				'itemCode' : $('#r_itemCode').val(),
				'beginRfoutdt' : $('#r_beginRfoutdt').datebox('getValue'),
				'endRfoutdt' : $('#r_endRfoutdt').datebox('getValue'),
				'agentId' : $('#agentId').val(),
				'initLoad' : true
			});
		});
		
		//清空
		$('#clearReminder').click(function(){
			$('#r_orderId').val('');
			$('#r_orderStatus').combobox('setValue', null);
			$('#r_mailId').val('');
			$('#r_contactId').val('');
			$('#r_contactName').val('');
			$('#r_status').combobox('setValue', null);
			//$('#r_beginCrDate').datebox('setValue', startDate);
			//$('#r_endCrDate').datebox('setValue', null);
			$('#province_r').combobox('setValue', null);
			$('#cityId_r').combobox('setValue', null);
			$('#countyId_r').combobox('setValue', null);
			$('#areaId_r').combobox('setValue', null);
			$('#r_receiverName').val('');
			$('#r_receiverPhone').val('');
			$('#r_parentOrderId').val('');
			$('#r_paytype').combobox('setValue', null);
			$('#r_itemCode').val('');
			$('#r_beginRfoutdt').datebox('setValue', null);
			$('#r_endRfoutdt').datebox('setValue', null);
		});
		
		$('#showMoreCondition').click(function(){
			var visible = $('.reminer_hidden').is(':hidden');	
			if(visible){
                if(reminder_address_init==false)
                {
                    $('#remider_order_address').load("/common/address_no_validation.jsp?instId=_r",function()
                    {
                        $('.reminer_hidden').show();
                        $('#reminder_line').addClass('underline');
                        reminder_address_init=true;
                    });
                }
                else
                {
                    $('.reminer_hidden').show();
                    $('#reminder_line').addClass('underline');
                }
			}else{
				$('.reminer_hidden').hide();
				$('#reminder_line').removeClass('underline');
			}
            setTimeout(function(){
                window.parent.SetCwinHeight(frameElement);
            },200);
		});
	}
});


/**
 * 打开催送货窗口
 */
function showReminder(orderId){
	$('#frameId').val(frameElement.id);
	
	if(null!=orderId && ''!=orderId){
		$('#sr_orderId').val(orderId);
	}
	
	window.parent.popWin(frameElement.id, 'reminder', {
		title : '订单催送货',
		width : 400,
//		height : 250,
		iconCls : '',
		shadow : true,
		modal : true,
		closed : true,
        modal:true,
		minimizable : false,
		maximizable : false,
		collapsible : false ,
        draggable:false
	});
}

/**
 * 提交催送货
 */
function submitReminder(){
	var priority = window.parent.document.getElementById('class_apply').value;
	var reason = window.parent.document.getElementById('applicationreason_apply').value;
	if(null==priority || ''==priority){
		window.parent.document.getElementById('message').innerHTML = '请选择催送货等级';
		return false;
	}
	if(null==reason || ''==reason){
		window.parent.document.getElementById('message').innerHTML = '请输入提交原因';
		return false;
	}
	window.parent.document.getElementById('message').innerHTML = '';
	
	var form = window.parent.document.forms['reminderApplyForm'];
	var _data = $(form).serialize();
	var source = window.parent.document.getElementById('reminderSource').value;
	
	$.ajax({
		type : 'POST',
		url : ctx + '/reminder/apply',
		data : _data,
		success : function(_rs){
			if(eval(_rs.success)){
				window.parent.closeWin('reminder');
				if(eval(_rs.isCreator)){
					var frameId = frameElement.id;
					var tabId = frameId.substring(2, frameId.length);
					window.parent.alertWin('提示', '申请成功');
					//订单详情页的后续处理
					if('DETAIL' == source){
						window.parent.document.getElementById('myorder').click();
						window.parent.frames['p_myorder'].document.getElementById('myOrdersearch').click();
						window.parent.destoryTab(tabId);
					//全部订单页的后续处理	
					}else if('HEADER' == source){
						$('#myOrdersearch').trigger('click');
					}
					
				}else{
					window.parent.alertWin('提示', '申请成功');
					//刷新页面
					location = location;
				}
			}else{
				window.parent.document.getElementById('message').innerHTML = _rs.message;
			}
		},
		error: function(){
			window.parent.alertWin('提示', '会话超时或网络错误');
		}
	});
}

/**
 * 查看催送货回复
 * @param orderId
 */
function viewResult(orderId){
	
	$.ajax({
		type : 'POST',
		url : ctx + '/reminder/queryReplay',
		data : {'orderId': orderId},
		success : function (_rs){
			if(eval(_rs.success)){
				var oua = _rs.oua;
				$('#apppsn').val(oua.apppsn);
				var levelLabel = '';
				
				if(1==oua.class_){
					levelLabel = '普通';
				}else if(2==oua.class_){
					levelLabel = '加急';
				}else if(3==oua.class_){
					levelLabel = '紧急';
				}
				$('#class_').val(levelLabel);
				$('#applicationreason').text(oua.applicationreason);
				$('#chkpsn').val(oua.chkpsn);
				$('#chkreason').text(oua.chkreason);
				
				window.parent.popWin(frameElement.id, 'reminderReplay', {
					title : '订单催送货回复',
					width : 400,
					height : 250,
					iconCls : '',
					shadow : true,
					modal : true,
					closed : true,
			        modal:true,
					minimizable : false,
					maximizable : false,
					collapsible : false ,
                    draggable:false
				});
			}else{
				window.parent.alertWin('提示', _rs.message);
			}
		},
		error : function(){
			window.parent.alertWin('提示', '会话超时或网络错误');
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