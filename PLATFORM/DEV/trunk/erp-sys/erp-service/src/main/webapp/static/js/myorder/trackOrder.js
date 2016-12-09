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
	
	if($('#fo_status').length > 0) {
		$("#fo_status").combobox({
	        data:orderStatuses,
	        valueField: 'id',
	        textField: 'dsc'
	    });
	}
	
	
	if($('#trackOrderDataGrid').length > 0) {
		
		$('#trackOrderDataGrid').datagrid({
			width : '100%',
			height : 410,
			fitColumns : false,
			remoteSort : false,
			singleSelect : true,
			pagination : true,
			url : ctx+'/myorder/trackorder/queryPageList',
			queryParams : {'initLoad' : false},
			onBeforeLoad : function(param){
				if(param.initLoad){
					return true;
				}
				return false;
			},
			columns : [ [ 
			{
				field : 'orderId',
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
				field : 'orderStatus',
				align : 'center',
				title : '订单状态',
				width : 100
			},{
				field : 'crdt',
				title : '订单生成时间',
				align : 'center',
				width : 100,
				formatter : dateFormatter
			}, {
				field : 'totalprice',
				align : 'right',
				title : '订单金额',
				width : 100,
				formatter:function(value){
	                return formatPrice(value);
	            }
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
				field : 'rfoutdat',
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
				field : 'assignUser',
				title : '分配人',
				align : 'center',
				width : 100
			},{
				field : 'assignTime',
				title : '分配时间',
				align : 'center',
				width : 100,
				formatter : dateFormatter
			},{
				field : 'trackUser',
				title : '跟单人',
				align : 'center',
				width : 100
			},{field:'trackRemark',title:'跟单备注',editor:'text',formatter:function (value,row){
	            if(value!=null) {
	                var token='<a class="link" href="javascript:void(0);" onclick="javascript:viewTrack('+row.orderId+')" > ';
	                token+=value;
	                token+='</a>';
	                return token;
	            } else {
	                return value;
	            }
	        }}
			] ],
			onLoadSuccess:function(data){   
			    $('#trackOrderDataGrid').datagrid('doCellTip',{delay:500});
			} 
		});
		
		var p = $('#trackOrderDataGrid').datagrid('getPager');
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
	
	
	$('#fo_query').click(function() {
		var valid = $('#fo_form').form('validate');
		
		var beginDate = $('#fo_beginDate').datebox('getValue');
		var endDate = $('#fo_endDate').datebox('getValue');
		
		var diff = Date.parse(endDate) - Date.parse(beginDate);
		
		if(diff/1000/3600/24/31>=3){
			window.parent.alertWin('错误', '订单生成时间跨度不能超过3个月');
			$('#trackOrderDataGrid').datagrid('loadData', {'total':0, 'rows':[]});
			return;
		}
		
		if(!valid){
			$('#trackOrderDataGrid').datagrid('loadData', {'total':0, 'rows':[]});
			return;
		}

		$('#trackOrderDataGrid').datagrid('reload',{
			'orderId' : $('#fo_orderId').val(),
			'orderStatus' : $('#fo_status').combobox('getValue'),
			'mailId' : $('#fo_mailId').val(),
			'contactId' : $('#fo_contactId').val(),
			'contactName' : $('#fo_contactName').val(),
			'crusr' : $('#fo_crusr').val(),
			'beginDate' : $('#fo_beginDate').datebox('getValue'),
			'endDate' : $('#fo_endDate').datebox('getValue'),
			'trackUser' : $('#fo_queryTrackUser').val(),
			'initLoad' : true
		});
	});
	
	$('#fo_reset').click(function() {
		$('#fo_orderId').val('');
		$('#fo_status').combobox('setValue', null);
		$('#fo_mailId').val('');
		$('#fo_contactId').val('');
		$('#fo_contactName').val('');
		$('#fo_crusr').val('');
		//$('#fo_beginDate').datebox('getValue');
		//$('#fo_endDate').datebox('getValue');
		$('#fo_queryTrackUser').val('');
	});
});

function selectAgent(opt) {
	console.log('selectAgent event');
	//var _val = $(opt).val();
	var v = [];
    var s = '';
    $("input[name='tonodes']:checkbox").each(function () {
        if ($(this).attr("checked")) {
            v.push($(this).val());
            if (s != '') {
                s += ',';
            }
            s += $(this).next('span').text();
        }
    });
    $('#fo_crusr_select').combo('setValues', v).combo('setText', s);
}

function formatPrice(price) {
    if(price==null||price=='')
        return '0.00';
    var prc=price;
    return prc.toFixed(2);
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