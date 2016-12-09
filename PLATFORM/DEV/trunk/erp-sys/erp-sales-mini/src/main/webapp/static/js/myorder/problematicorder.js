var problematic_address_init=false;
$(function() {
	var isHidden = true;
	$("tr[name^='tr_problem_morConditons']").each(function(){
		$(this).hide();
	});
	
	$('#a_problem_tbShowMoreCondition').click(function(){
        if(isHidden == true && problematic_address_init==false)
        {
            $('#problematic_order_address').load("/common/address_no_validation.jsp?instId=_problematic_order",function()
            {
                $("tr[name^='tr_problem_morConditons']").each(function(){
                    if(!isHidden) {
                        $(this).hide();
                        $("#tr_my_problem_order_last").removeClass('underline');
                    } else {
                        $(this).show();
                        $("#tr_my_problem_order_last").addClass('underline');
                    }
                });
                problematic_address_init=true;
                if(isHidden) {
                    isHidden = false;
                } else {
                    isHidden = true;
                }
            });

        }
        else
        {
            $("tr[name^='tr_problem_morConditons']").each(function(){
                if(!isHidden) {
                    $(this).hide();
                    $("#tr_my_problem_order_last").removeClass('underline');
                } else {
                    $(this).show();
                    $("#tr_my_problem_order_last").addClass('underline');
                }
            });

            if(isHidden) {
                isHidden = false;
            } else {
                isHidden = true;
            }
        }
        setTimeout(function(){
            window.parent.SetCwinHeight(frameElement);
        },200);
	});
	
	if ($("#input_problem_order_tbStatus").length > 0) {
	    $("#input_problem_order_tbStatus").combobox({
	        data:orderStatuses,
	        valueField: 'id',
	        textField: 'dsc',
	        onLoadSuccess:function(data){
	            $("#input_problem_order_status").combobox({
	                data: data,
	                valueField: 'id',
	                textField: 'dsc'
	            });
	        }
	    });
	}
    
	if ($("#input_problem_process_tbStatus").length > 0) {
	    $("#input_problem_process_tbStatus").combobox({
	        data:problemStatuses,
	        valueField: 'id',
	        textField: 'dsc',
	        onLoadSuccess:function(data){
	            $("#input_problem_process_status").combobox({
	                data: data,
	                valueField: 'id',
	                textField: 'dsc'
	            });
	        }
	    });
	}
    
	if ($("#input_problem_tbPaytype").length > 0) {
	    $("#input_problem_tbPaytype").combobox({
	        data:payTypes,
	        valueField: 'id',
	        textField: 'dsc',
	        onLoadSuccess:function(data){
	            $("#input_problem_paytype").combobox({
	                data: data,
	                valueField: 'id',
	                textField: 'dsc'
	            });
	        }
	    });
	}
    /*	var data = $('#input_problem_process_tbStatus').combobox('getData');
    if (data.length > 0) {
        $('#input_problem_process_tbStatus').combobox('select',data[0].id);
    } */
	if ($('#myProblemOrderlist').length > 0) {
		$('#myProblemOrderlist').datagrid({
			width : '100%',
			height : 410,
			fitColumns : true,
			remoteSort : false,
			singleSelect : true,
	        nowrap:false,
			pagination : true,
			url : ctx+'/problematicOrder/query',
			queryParams : {
				'crusr' : $('#input_problem_tbCrusr').val(),
				'beginCrDate' : $('#input_problem_tbBeginCrdt').datebox('getValue'),
				'endCrDate' : $('#input_problem_tbEndCrdt').datebox('getValue'),
				'processStatus' : $('#input_problem_process_tbStatus').combobox('getValue')
			},
			columns : [ [ 
			{
				field : 'orderid',
				title : '订单编号',
				align : 'center',
				width : 100,
				formatter:function (value,row,index){
					var canedit = false;
					 var url='<a class="link" href="javascript:void(0);" onclick="javascript:viewOrder('+'\'myOrderlist\','+index+','+row.orderid+','+canedit+')" >'+value+'</a>';
					 return url;
				}
			}, {
				field : 'crdate',
				title : '订单生成时间',
				align : 'center',
				width : 150,
				formatter : dateFormatter
			}, {
				field : 'statusDsc',
				title : '订单状态',
				align : 'center',
				width : 100
			}, {
				field : 'totalprice',
				align : 'center',
				title : '订单金额',
				align:'right',
				width : 100,
				formatter:function(value){
	                return formatPrice(value);
	            },
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
					var id = val;
					
					var url;
					var paras='';
					if(row.campaignId!=null && row.campaignId!='') {
						paras+='?campaignId='+row.campaignId;
					}
					url = ctx+'/contact/1/'+row.contactId;
					url+=paras;
					url = '<a class="link" href="javascript:void(0)" id=\''+id+'\' onclick="window.parent.addTab(\''+id.valueOf()+'\', \'我的客户\', \'false\', \''+url+'\')">'+val+'</a>';
					return url;
				}
			}, {
				field : 'contactName',
				title : '客户姓名',
				align : 'center',
				width : 100
			}, {
				field : 'sentDate',
				title : '出库时间',
				align : 'center',
				width : 100,
				formatter : dateFormatter
			}, {
				field : 'problemDate',
				title : '问题时间',
				align : 'center',
				width : 100,
				formatter : dateFormatter
			}, {
				field : 'problemUser',
				title : '提交座席',
				align : 'center',
				width : 100
			},{
				field : 'problemTypeDsc',
				title : '问题类型',
				align : 'center',
				width : 100
			},{
				field : 'problemDsc',
				title : '问题描述',
				align : 'center',
				width : 100
			},{
				field : 'problemStatusDsc',
				title : '问题状态',
				align : 'center',
				width : 100
			},
			{
				field : 'mdUser',
				title : '操作',
				width:60,
				align:'center',
				formatter: function(value,row){
					url = "";
					if(row.problemStatus != '3') {
						url = '<a class="dispose" title="处理" id=\''+value+'\' onclick="replyProblematicOrder(\''+row.problemId+'\',\''+row.orderid+'\',\''+row.problemStatus+'\')"></a>';
					}
					return url;
				}
			}
			] ],
			onLoadSuccess:function(data){   
	//		    $('#reminderDataGrid').datagrid('doCellTip',{delay:500});   
			} 
		});
	}
	
	if ($('#myProblemOrderlist').length > 0) {
		var p = $('#myProblemOrderlist').datagrid('getPager');
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
	
	//查询
	if ($('#a_myProblemOrdersearch').length > 0) {
		$('#a_myProblemOrdersearch').click(function(){
	//		$('#myProblemOrderlist').datagrid({url : ctx+'/problematicOrder/query'});
			if(checkMember($('#input_problem_tbCrusr').val())) {
				reloadProblematicGrid();
			} else {
				$('#myProblemOrderlist').datagrid('loadData',{total:0,rows:[]});
			}
		});
	}
	
	//清空
	if ($('#a_myProblemOrderreset').length > 0) {
		$('#a_myProblemOrderreset').click(function(){
			$('#myProblemOrderForm')[0].reset();
			$('#input_problem_tbBeginCrdt').datebox('setValue', new Date().format('yyyy-MM-dd'));
			$('#input_problem_tbEndCrdt').datebox('setValue', new Date().format('yyyy-MM-dd'));
			$('#input_problem_tbBeginOutdt').datebox('setValue', new Date().format('yyyy-MM-dd'));
			$('#input_problem_tbEndOutdt').datebox('setValue', new Date().format('yyyy-MM-dd'));
			$('#input_problem_order_tbStatus').combobox('setValue','');
			$('#input_problem_process_tbStatus').combobox('setValue','');
			$('#input_problem_tbPaytype').combobox('setValue','');
		});
	}
	
});

function reloadProblematicGrid() {
	$('#myProblemOrderlist').datagrid('reload',{
		//			url : ctx+'/problematicOrder/query',
					'orderid' : $('#input_problem_orderid').val(),
					'orderStatus' : $('#input_problem_order_tbStatus').combobox('getValue'),
					'mailId' : $('#input_problem_tbMailid').val(),
					'contactId' : $('#input_problem_tbContactid').val(),
					'contactName' : $('#input_problem_tbContactname').val(),
					'processStatus' : $('#input_problem_process_tbStatus').combobox('getValue'),
					'beginCrDate' : $('#input_problem_tbBeginCrdt').datebox('getValue'),
					'endCrDate' : $('#input_problem_tbEndCrdt').datebox('getValue'),
					'crusr' : $('#input_problem_tbCrusr').val(),
					//optional
					'provinceid' : getNotEmptyComboboxValue($('#province_problematic_order')),
					'cityid' : getNotEmptyComboboxValue($('#cityId_problematic_order')),
					'countyid' : getNotEmptyComboboxValue($('#countyId_problematic_order')),
					'areaid' : getNotEmptyComboboxValue($('#areaId_problematic_order')),
					'receiverName' : $('#input_problem_tbGetcontactname').val(),
					'receiverPhone' : $('#input_problem_tbPhone').val(),
					'parentOrderId' : $('#input_problem_tbParentid').val(),
					'paytype' : $('#input_problem_tbPaytype').combobox('getValue'),
					'itemCode' : $('#input_problem_tbProdname').val(),
					'beginRfoutdt' : $('#input_problem_tbBeginOutdt').datebox('getValue'),
					'endRfoutdt' : $('#input_problem_tbEndOutdt').datebox('getValue')
				});
}

function replyProblematicOrder(problemId, orderId, problemStatus) {
	$('#input_problematic_reply_oid').val(orderId);
	if(problemStatus == '2') {
	    $.get("/problematicOrder/queryContent?problemId="+problemId, 
	    		function(result){
	    	$('#textarea_problematic_reply_reason').val(result);
	    });
	} else {
		$('#textarea_problematic_reply_reason').val("");
	}
	$('#div_problematic_reply').show();
	$('#div_problematic_reply').dialog({
		title:'问题单回复',
	    width: 500,
	    top: 200,
//	    height: 200,
		shadow:false,
		collapsible:false,
		minimizable:false,
		maximizable:false,
		resizable:true,
		closed:true,
		modal:true,
        draggable:false,
		buttons: [{
			text: '提交',
            plain:true,
			handler: function() {
				replyContent = $('#textarea_problematic_reply_reason').val();
				$.ajax({
					url: ctx+"/problematicOrder/replyProblematicOrder?replyContent="+replyContent+"&problemId="+problemId,
					 success:function(data) {
						 	$('#div_problematic_reply').hide();
//						 	$('#textarea_problematic_reply_reason').val("");
							 $('#div_problematic_reply').dialog('close');
							 reloadProblematicGrid();
						},
					     error : function() {
					          alert("异常！");
					          $('#div_problematic_reply').hide();
					          $('#textarea_problematic_reply_reason').val("");
					          $('#div_problematic_reply').dialog('close');
					          reloadProblematicGrid();
					     }
					});
			}
			}, {
			text: '取消',
            plain:true,
			handler: function() {
				$('#div_problematic_reply').dialog('close');
				$('#textarea_problematic_reply_reason').val("");
				$('#div_problematic_reply').hide();
			}
			}]
	}
	);
	$('#div_problematic_reply').dialog('open');
}

function problematicOrder(orderId) {
	url = ctx + "/problematicOrder/problematicOrderMarking?orderId=" + orderId;
	$.ajax({
		url : url+"&ajax=true",
		success : function(data) {
			$('#window_problematicOrderMarking').html(data);
			$('#window_problematicOrderMarking').show();
			$('#window_problematicOrderMarking').window({
				title : '问题单',
				width : 400,
//				height : 200,
				iconCls : '',
				top : 150,
//				left : ($(window).width() - 400) * 0.5,
				shadow : true,
				modal : true,
				closed : true,
				minimizable : false,
				maximizable : false,
				collapsible : false,
                draggable:false,
				onBeforeOpen : function() {
					$('#close').click(function() {
						$('#window_problematicOrderMarking').html("");
							$(this).hide();
							$('#window_problematicOrderMarking').window('close');
						}
					);
				},
				onBeforeClose : function() {
					$('#window_problematicOrderMarking').hide();
				}
			});
			$('#window_problematicOrderMarking').window('open');

		},
		error : function() {
			alert("异常！");
			$('#window_problematicOrderMarking').window('close');
		}
	});
}




function markProblematicOrder(orderIdElement, problematicOrderTypeElement, problematicOrderDscElement) {
	var orderId = $('#'+orderIdElement).val();
	var problematicOrderDsc = $('#'+problematicOrderDscElement).val();
	var problematicOrderType = $('#'+problematicOrderTypeElement).val();
	url = ctx + "/problematicOrder/markProblematicOrder?orderId=" + orderId+"&problemDsc="+problematicOrderDsc+"&problemType="+problematicOrderType;
	$.ajax({
		url : url+"&ajax=true",
		success : function(data) {
			$('#window_problematicOrderMarking').window('close');
		},
		error : function() {
			alert("异常！");
			$('#window_problematicOrderMarking').window('close');
		}
	});
}

function checkMember(usrId)
{
    var bChecked=false;
    //校验用户
    $.ajax({
        type : "post",
        url : "/myorder/myorder/checkGrpMember",
        data : "usrId=" + usrId,
        async : false,
        dataType:"json",
        success : function(data){
            bChecked= data;
        }
    });

    return bChecked;
}

function formatPrice(price)
{
    if(price==null||price=='')
        return '0.00';
    var prc=price;
    return prc.toFixed(2);
}

function getNotEmptyComboboxValue(element)
{
	
	if(element != null && element.length > 0) {
		var str = element.combobox('getValue');
	    if(str==null||str=="")
	        return null;
	    else
	        return str;
	} else {
		return null;
	}
}