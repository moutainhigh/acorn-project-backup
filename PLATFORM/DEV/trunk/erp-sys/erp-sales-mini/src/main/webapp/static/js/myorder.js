function _handler(){
    $('#myorder_tabs').tabs('resize',{width:'100%'});
    $('#myOrderlist,#myOrderlist_pay,#myOrderlist_audit,#myProblemOrderlist,#reminderDataGrid').datagrid('resize',{width:'100%'});
}

var busiType=null;
var parmMyOrder={xxx:0};
var parmMyOrderPay={xxx:0};//{orderId:'0'};
var parmMyOrderAudit={xxx:0};

function setCrusrIds(usrid)
{
    $('#tbCrusr,#tbCrusr_pay,#tbCrusr_audit,#agentId,#input_problem_tbCrusr').val(usrid);
}

var b_order_pay_address_init=false;
// JavaScript Document
var bShowCondition=0;

$(function(){
    if(isManager==false)
    {
        $('#tbCrusr,#tbCrusr_pay,#tbCrusr_audit,#agentId,#input_problem_tbCrusr').attr('disabled','disabled');
    }

    var totalOrderCount=-1;
    var totalOrderPayCount=-1;
    var totalOrderAuditCount=-1;

    
    showMoreCondition(bShowCondition);
    var conditionHeight=0;
    $('#tbShowMoreCondition').click(function(){
        if(bShowCondition==0)
        {
            bShowCondition=1;
        }
        else
        {
            bShowCondition=0;
        }

        showMoreCondition(bShowCondition);
    });

    $('#tbCrusr,#tbCrusr_pay,#tbCrusr_audit,#agentId,#input_problem_tbCrusr').blur(function(e){
       var usrid=$(this).val();
       setCrusrIds(usrid);
    });

    /*$('#myorder_tabs').tabs({
        onSelect:function (title,index)
        {
           if(index==1)
           {
               if(b_order_pay_address_init==false)
               {
                $('#pay_order_address').load("/common/address_no_validation.jsp?instId=002",function()
                {
                    b_order_pay_address_init=true;
                });
               }
           }
        }
    });*/

/*    $('.easyui-datebox').datebox({
        formatter: function(date){
            return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
        },
        parser: function(date)
        {
        	if(undefined!=date && null!=date && ''!=date){
        		return new Date(Date.parse(date.replace(/-/g,"/")));
        	}
        	return null;
        }
    });*/

    $("#tbStatus").combobox({
        data:orderStatuses,
        valueField: 'id',
        textField: 'dsc',
        onLoadSuccess:function(data){
            $("#status").combobox({
                data: data,
                valueField: 'id',
                textField: 'dsc'
            });
        },
        onChange:function(newValue,oldValue)
        {
            if(newValue=="1"||newValue=="2")
            {
                $("#tbCheckResult").combobox("enable");
                //$("#tbCheckResult").combobox("setValue",2);
            }
            else
            {
                if(newValue=="10")
                    $("#tbCheckResult").combobox("setValue",3);
                else
                    $("#tbCheckResult").combobox("setValue",2);

                var validStatus=false;
                var index=0;
                for(index=0;index<orderStatuses.length;index++)
                {
                    if(newValue==orderStatuses[index].id)
                    {
                        validStatus=true;
                        break;
                    }
                }
                if(validStatus==true)
                    $("#tbCheckResult").combobox("disable");
                else
                    $("#tbCheckResult").combobox("enable");
            }
            
            //跟单按钮
            $('#myOrderallot').hide();
        }
    });

    $("#tbCheckResult").combobox({
        data:itemDataList,
        valueField: 'id',
        textField: 'val'
        /*onLoadSuccess:function(data){
            $("#status").combobox({
                data: data,
                valueField: 'id',
                textField: 'dsc'
            });
        }*/
    });

    $("#tbCheckResult").combobox("setValue",2);

    $("#tbPaytype").combobox({
        data:payTypes,
        valueField: 'id',
        textField: 'dsc',
        onLoadSuccess:function(data){
            //只显示费cod的
            var notCodPayType=[];
            var index=0;

            $("#paytype").combobox({
                data: data,
                valueField: 'id',
                textField: 'dsc'
            });
            $("#tbPaytype_audit").combobox({
                data: data,
                valueField: 'id',
                textField: 'dsc'
            });  //tbpaytype_pay

            if(payTypes==null)
            {
                window.parent.alertWin('系统提示', "paytype is null");
                return;
            }

            var NCODPaytypes = $('#NCODPaytypes_hidden').val();
            if(NCODPaytypes!=null&&NCODPaytypes!="")
            {
                var strs=NCODPaytypes.split(',');
                for(var i=0;i<payTypes.length;i++)
                {
                    for(var j=0;j<strs.length;j++)
                    {
                        if(payTypes[i].id==strs[j])
                        {
                            notCodPayType[index]=payTypes[i];
                            index++;
                            continue;
                        }

                    }
                }
            }

            $("#tbPaytype_pay").combobox({
                data: notCodPayType,
                valueField: 'id',
                textField: 'dsc'
            });
        }
    });

    $("#tbStatus_audit").combobox({
        data:auditStatuses,
        valueField: 'id',
        textField: 'dsc',
        onLoadSuccess:function(data){
            $("#status_audit").combobox({
                data: data,
                valueField: 'id',
                textField: 'dsc'
            });
        }
    });

    $("#tbPaytype_result").combobox({
        data:resultStatusList,
        valueField: 'id',
        textField: 'val'/*,
        onLoadSuccess:function(data){
            $("#status_audit").combobox({
                data: data,
                valueField: 'id',
                textField: 'dsc'
            });
        }*/
    });
    
    $("#d_group").combobox({
        valueField : 'id',
        textField : 'name',
        onSelect : function(param) {
            var url = '/common/getSeatByGroup?groupId=' + param.id;
            $('#d_seat').combobox('clear').combobox('reload', url);
        }
    });
    $("#d_seat").combobox({
        valueField : 'userId',
        textField : 'userId',
        required:true
    });

    var orderlistInit=false;
    $('#myOrderlist').datagrid({
        title:'',
        iconCls:'icon-edit',
        width : '100%',
        height : 410,
        url : '/myorder/myorder/query',
//        fitColumns : true,
        striped: true,
        border: true,
        collapsible:true,
        scrollbarSize:-1,
        //fit: true,
        rowStyler: function(index,row)
        {

            var rowStyle='';
            switch(row.status)
            {
                case "0": //cancel
                case "10":
                    rowStyle = 'background-color:#cecece;text-decoration: line-through;'; break;
                case "8": //物流取消
                    rowStyle = 'background-color:#A2BADD;text-decoration: line-through;'; break;
                case "1": //new
                    rowStyle='background-color:#FFC0C0';break;
                case "5": //finish
                    rowStyle='background-color:#C0FFC0';break;
                default:rowStyle='';break;

            }
            return rowStyle;
        },
        columns:[[
            {field:'id',title:'ID',width:0,editor:'text', hidden: true},
            {field:'ck', width:20, checkbox: true},
            {field:'orderid',title:'订单编号',align:'center',width:80,editor:'text',formatter:function (value,row,index){
                var canedit=0;
                if(row.right.indexOf("MODIFY")!=-1||row.right.indexOf("ALL")!=-1)
                {
                    canedit=1;
                }
                var token='<a class="link" href="javascript:void(0);" onclick="javascript:viewOrder('+'\'myOrderlist\','+index+','+row.orderid+','+canedit+')" > ';
                token+=value;
                token+='</a>';

                return token;
            }},
            {field:'crdt',title:'订单生成时间',width:140,editor:'text'},
            {field:'status',title:'订单状态',align:'center',width:80,editor:'text',formatter:function (value){
                for(var i= 0; i<orderStatuses.length; i++){
                    if (orderStatuses[i].id == value || (!value && orderStatuses[i].id == "0")) return orderStatuses[i].dsc;
                }
                return value;
            }},
            {field:'totalprice',title:'订单金额',width:80,align:'right',editor:'text', formatter:function(value){
                return formatPrice(value);
            }},
            {field:'crusr',title:'座席工号',align:'center',width:80,editor:'text'},
            {field:'contactid',title:'客户编号',width:80,editor:'text'},
            {field:'contactname',title:'客户名称',align:'center',width:80,editor:'text'},
            {field:'paytype',title:'支付方式',width:80,editor:'text',formatter:function (value){
                for(var i= 0; i<payTypes.length; i++){
                    if (payTypes[i].id == value || (!value && payTypes[i].id == "0")) return payTypes[i].dsc;
                }
                return value;
            }},
            {field:'rfoutdat',title:'出库时间',width:120,editor:'text',align:'center'},
            {field:'mailid',title:'包裹单号',width:80,editor:'text',align:'center'},
            {field:'assignUser',title:'跟单分配人',width:80,align:'center'},
            {field:'assignTime',title:'分配时间',width:120,align:'center'},
            {field:'trackUsr',title:'跟单人',width:120,align:'center'},
            {field:'right',title:'操作',align:'center',width:120,formatter: function(val, row,index){
//                var cancelEnableToken='<a href="javascript:void(0);" onclick="javascript:cancelOrder('+'\'myOrderlist\','+index+','+row.orderid+','+row.contactid+')" title="取消订单" class="del"></a>';
//                var cancelDisableToken='<a href="javascript:void(0);"  class="del_dis" disabled="true" title="取消订单"></a>';
//                var modifyEnableToken= '<a href="javascript:void(0);" onclick="javascript:modifyOrder('+'\'myOrderlist\','+index+','+row.orderid+')" title="修改" class="modify"></a>';
//                var modifyDisableToken='<a href="javascript:void(0);"  class="modify_dis" disabled="true" title="修改"></a>';
//
//                var consultEnableToken= '<a href="javascript:void(0);" onclick="javascript:consultOrder('+'\'myOrderlist\','+index+','+row.orderid+','+row.contactid+')" title="转咨询" class="cancel"></a>';
//                var consultDisableToken='<a href="javascript:void(0);"  class="cancel_dis" disabled="true" title="转咨询"></a>';
//
//                var noteEnableToken= '<a href="javascript:void(0);" onclick="javascript:noteOrder('+'\'myOrderlist\','+index+','+row.orderid+')" title="跟单备注" class="addnote"></a>';
//                var noteDisableToken='<a href="javascript:void(0);"  class="addnote_dis" disabled="true" title="跟单备注"></a>';
//
//                var tableToken='';
//                if(val.indexOf("ALL")!=-1)
//                {
//                    //tableToken+=viewEnableToken;
//                    tableToken+=modifyEnableToken;
//                    tableToken+=cancelEnableToken;
//                    tableToken+=consultEnableToken;
//                }
//                else
//                {
//                    if(val.indexOf("MODIFY")!=-1)
//                    {
//                        tableToken+=modifyEnableToken;
//                    }
//                    else
//                    {
//                        tableToken+=modifyDisableToken;
//                    }
//                    if(val.indexOf("DELETE")!=-1)
//                    {
//                        tableToken+=cancelEnableToken;
//                    }
//                    else
//                    {
//                        tableToken+=cancelDisableToken;
//                    }
//                    if(val.indexOf("CONSULT")!=-1)
//                    {
//                        tableToken+=consultEnableToken;
//                    }
//                    else
//                    {
//                        tableToken+=consultDisableToken;
//                    }
//                }
//                tableToken+="";
//
//                //催送货权限
//                if(eval(row.couldReApply)) {
//                	tableToken += '<a href="javascript:void(0);" onclick="javascript:showReminder('+row.orderid+')"  class="deliveryBtn" title="催送货"></a>';
//                }else {
//                	tableToken += '<a href="javascript:void(0);" class="deliveryBtn_dis" title="催送货"></a>';
//                }
//                tableToken+=noteEnableToken;
//
//                //跟单相关权限
//                trackUser = row.trackUsr;
//                if(null != trackUser && ''!=trackUser &&  trackUser!= currentUsrId) {
//                	tableToken = tableToken.replace(/onclick=["][^"]*["]/gi, "");
//
//                	var _buttons = $.parseHTML(tableToken);
//                	for(var i=0; i<_buttons.length; i++) {
//
//                		var _className = $(_buttons[i]).attr('class');
//                        if($(_buttons[i]).hasClass('mr10')){
//                            _className.replace(" mr10","");
//                        }
//                		if(null == _className.match(/^\w+_dis$/)) {
//
//                			var disClassName = _className + '_dis mr10';
//                			tableToken = tableToken.replace(_className, disClassName);
//                		}
//
//                	}
//                }  else{
//                    var _buttons = $.parseHTML(tableToken);
//                    for(var i=0; i<_buttons.length; i++) {
//                        var disClassName = _className + ' mr10';
//                        tableToken = tableToken.replace(_className, disClassName);
//                    }
//                }
//
//                return tableToken;
                var _buttonWrap = $('<div></div>');
                var _cancelButton = $('<a href="javascript:void(0);" title="取消订单" class="del mr10"></a>');
                var _modifyButton =  $('<a href="javascript:void(0);" title="修改" class="modify mr10"></a>');
                var _consultButton =  $('<a href="javascript:void(0);"  title="转咨询" class="cancel mr10"></a>');
                var _noteButton = $('<a href="javascript:void(0);" title="跟单备注" class="addnote"></a>');
                var _deliveryButton = $('<a href="javascript:void(0);" title="催送货"  class="deliveryBtn mr10" ></a>');
                var  _cancelonclick = function(){cancelOrder('myOrderlist'+index,row.orderid,row.contactid)};
                var  _modifyonclick = function(){
                    modifyOrder('myOrderlist'+index,row.orderid)
                };
                var  _consultonclick = function(){consultOrder('myOrderlist'+index,row.orderid,row.contactid)};
                var  _noteonclick = function(){noteOrder('myOrderlist'+index,row.orderid)};
                var  _deliveryonclick = function(){showReminder(row.orderid)};
               if(val.indexOf("ALL")!=-1){
                    _buttonWrap.append(_cancelButton).append(_modifyButton).append(_consultButton).append(_deliveryButton).append(_noteButton);
                }else{
                   if(val.indexOf("MODIFY")!=-1)
                   {

                       _modifyButton.removeClass('modify_dis').addClass('modify');
                       _modifyButton.attr('onclick','modifyOrder('+'\'myOrderlist\','+index+','+row.orderid+')');
                       _buttonWrap.append(_modifyButton);
                   }else {
                       _modifyButton.removeClass('modify').addClass('modify_dis');
                       _modifyButton.removeAttr('onclick');
                   }
                   
                   if(val.indexOf("DELETE")!=-1){
                       _cancelButton.removeClass('del_dis').addClass('del');
                       _cancelButton.attr('onclick','cancelOrder('+'\'myOrderlist\','+index+','+row.orderid+','+row.contactid+')');
                       _buttonWrap.append(_cancelButton);
                   }else {
                       _cancelButton.removeClass('del').addClass('del_dis');
                       _cancelButton.removeAttr('onclick');
                       _buttonWrap.append(_cancelButton);
                   }
                   
                   if(val.indexOf("CONSULT")!=-1)
                   {
                       _consultButton.removeClass('cancel_dis').addClass('cancel');
                       _consultButton.attr('onclick','consultOrder('+'\'myOrderlist\','+index+','+row.orderid+','+row.contactid+')');
                       _buttonWrap.append(_consultButton);

                   }else {
                       _consultButton.removeClass('cancel').addClass('cancel_dis');
                       _consultButton.removeAttr('onclick');
                       _buttonWrap.append(_consultButton);
                   }
               }
               
                if(eval(row.couldReApply)) {
                    _deliveryButton.removeClass('deliveryBtn_dis').addClass('deliveryBtn');
                    _deliveryButton.attr('onclick','showReminder('+row.orderid+')');
                    _buttonWrap.append(_deliveryButton);

                } else{
                    _deliveryButton.removeClass('deliveryBtn').addClass('deliveryBtn_dis');
                    _deliveryButton.removeAttr('onclick');
                    _buttonWrap.append(_deliveryButton);
                }
                _noteButton.attr('onclick','noteOrder('+'\'myOrderlist\','+index+','+row.orderid+')');
                _buttonWrap.append(_noteButton);
                
                //跟单相关权限
                trackUser = row.trackUsr;
                if(null != trackUser && ''!=trackUser &&  trackUser!= currentUsrId) {

                    _cancelButton.removeClass('del').addClass('del_dis');
                    _modifyButton.removeClass('modify').addClass('modify_dis');
                    _consultButton.removeClass('cancel').addClass('cancel_dis');
                    _deliveryButton.removeClass('deliveryBtn').addClass('deliveryBtn_dis');
                    _noteButton.removeClass('addnote').addClass('addnote_dis');

                    _cancelButton.removeAttr('onclick');
                    _modifyButton.removeAttr('onclick');
                    _consultButton.removeAttr('onclick');
                    _noteButton.removeAttr('onclick');
                    _deliveryButton.removeAttr('onclick');


                    _buttonWrap.append(_noteButton);
                }
                return _buttonWrap.html();

            }},
            {field:'trackRemark',title:'跟单备注',editor:'text',formatter:function (value,row){
                if(value!=null)
                {
                    var token='<a class="link" href="javascript:void(0);" onclick="javascript:viewTrack('+row.orderid+')" > ';
                    token+=value;
                    token+='</a>';
                    return token;
                }
                else
                {
                    return value;
                }
            }
            }
        ]],
        remoteSort:false,
        singleSelect:false,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination:true,
        queryParams: parmMyOrder,
        onBeforeLoad:function(data)
        {
            if(data==null||data.xxx!=null)
                return false;
        },
        onLoadSuccess: function(data){
            if(data.err!=null)
            {
                window.parent.alertWin('系统提示', data.err);
            }
            if(data.total!=null)
            {
                totalOrderCount=data.total;
            }
            $('.disable-linkbutton').linkbutton({disable:'true', plain:true});
            $('.enable-linkbutton').linkbutton({color:'blue', plain:true});
        },
        onLoadError: function(){
            window.parent.alertWin('系统提示', "加载失败!");
        },
        onUncheck:function(index,data){
            $(this).datagrid("clearSelections");
        }

    });

    var p = $('#myOrderlist').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    $('#myOrdersearch').click(function(){
        if(isManager==true)
        {
            if(!$("#tbCrusr").validatebox("isValid"))
            {
                return;
            }
        }

        totalOrderCount=-1;
        if(bShowCondition==1)
        {
            parmMyOrder={
                'orderId' : getNotEmptyString($("#orderid").val().replace(/^\s+|\s+$/g, "")),
                'provinceId': getNotEmptyString($("#province001").combobox('getValue')),
                'cityId': getNotEmptyString($("#cityId001").combobox('getValue')),
                'countyId': getNotEmptyString($("#countyId001").combobox('getValue')),
                'areaId': getNotEmptyString($("#areaId001").combobox('getValue')),
                'status': getNotEmptyString($("#tbStatus").combobox('getValue')),
                'mailId' : getNotEmptyString($("#tbMailid").val().replace(/^\s+|\s+$/g, "")),
                'contactId' : getNotEmptyString($("#tbContactid").val().replace(/^\s+|\s+$/g, "")),
                'contactName' : getNotEmptyString($("#tbContactname").val().replace(/^\s+|\s+$/g, "")),
                'crUsr' : getNotEmptyString($("#tbCrusr").val().replace(/^\s+|\s+$/g, "")),
                'beginCrdt' : $("#tbBeginCrdt").datebox('getValue'),
                'endCrdt' : $("#tbEndCrdt").datebox('getValue'),
                'getContactName' : getNotEmptyString($("#tbGetcontactname").val().replace(/^\s+|\s+$/g, "")),
                'beginOutdt' : getNotEmptyString($("#tbBeginOutdt").val().replace(/^\s+|\s+$/g, "")),
                'endOutdt' : getNotEmptyString($("#tbEndOutdt").val().replace(/^\s+|\s+$/g, "")),
                'parentOrderId' : getNotEmptyString($("#tbParentid").val().replace(/^\s+|\s+$/g, "")),
                'getContactPhone' : getNotEmptyString($("#tbPhone").val().replace(/^\s+|\s+$/g, "")),
                'payType' : getNotEmptyString($("#tbPaytype").combobox('getValue')),
                'prodId' : getNotEmptyString($("#tbProdname").val().replace(/^\s+|\s+$/g, "")),
                'checkResult': getNotEmptyString($("#tbCheckResult").combobox('getValue')),
                'countRows':totalOrderCount
            };
        }else
        {
            parmMyOrder={
                'orderId' : getNotEmptyString($("#orderid").val().replace(/^\s+|\s+$/g, "")),
                'status': getNotEmptyString($("#tbStatus").combobox('getValue')),
                'mailId' : getNotEmptyString($("#tbMailid").val().replace(/^\s+|\s+$/g, "")),
                'contactId' : getNotEmptyString($("#tbContactid").val().replace(/^\s+|\s+$/g, "")),
                'contactName' : getNotEmptyString($("#tbContactname").val().replace(/^\s+|\s+$/g, "")),
                'crUsr' : getNotEmptyString($("#tbCrusr").val().replace(/^\s+|\s+$/g, "")),
                'beginCrdt' : $("#tbBeginCrdt").datebox('getValue'),
                'endCrdt' : $("#tbEndCrdt").datebox('getValue'),
                'checkResult': getNotEmptyString($("#tbCheckResult").combobox('getValue')),
                'countRows':totalOrderCount
            };
        }
        if(orderlistInit==false)
        {
            $('#myOrderlist').datagrid({url:'/myorder/myorder/query'});
            orderlistInit=true;
        }
        $('#myOrderlist').datagrid('load',parmMyOrder);
        
        //跟单按钮
        if(isManager) {
        	var _orderStatus = $('#tbStatus').combobox('getValue');
        	var _agentId = getNotEmptyString($('#tbCrusr').val());
        	//订购和分拣状态的订单
        	if(('1' == _orderStatus || '2' == _orderStatus) && null != _agentId) {
        		$('#myOrderallot').show();
        		
        		//加载跟单对应坐席组
        		 $('#d_dept').combobox({
        		        url : '/common/getAllDept',
        		        valueField : 'id',
        		        textField : 'name',
        		        onSelect : function(param) {
        		            var url = '/common/getGroupByDept?deptId=' + param.id;
        		            $('#d_group').combobox('clear').combobox('reload', url);
        		        }
        		    });
        	}else{
        		$('#myOrderallot').hide();
        	}
        }
    });

    $('#myOrderreset').click(function(){
        var currentUsrId=$('#tbCrusr').val();
        $('#myOrderForm').form('clear');
        $('#tbCrusr').val(currentUsrId);
        $("#tbCheckResult").combobox("enable");
    });
    
    $('#myOrderallot').click(function(){
    	var checkedRows = $('#myOrderlist').datagrid('getChecked');
    	if(checkedRows.length > 0) {
    		$('#op_list').window('open');
    	}else{
    		window.parent.alertWin('错误', '请选择要分配的订单');
    	}
        
    });

    $('#myOrderreset_pay').click(function(){
        var currentUsrId=$('#tbCrusr_pay').val();
        $('#myOrderForm_pay').form('clear');
        $('#tbCrusr_pay').val(currentUsrId);
    });

    $('#myOrderreset_audit').click(function(){
        var currentUsrId=$('#tbCrusr_audit').val();
        $('#myOrderForm_audit').form('clear');
        $('#tbCrusr_audit').val(currentUsrId);
    });

    $('#orderCancel_confirm').click(function(){
        var orderId=$("#txtOrderId").val();
        var remark=$("#txtRemark").val();

        $.ajax({
            url:"/myorder/myorder/cancel",
            contentType:"application/json; charset=utf-8",
            data: {"orderId":orderId,"remark":encodeURI(remark)},
            success:function(data){
                if(data!=null)
                {
                    var contactId=null;
                    if(data.succ!=null&&data.succ=='1')
                    {
                        $('#dlgCancel').dialog('close');
                        if(data.audit!=null&&data.audit=='0')
                        {
                            var rowIndex=$('#orderCancel_index_hidden').val();
                            contactId=$('#orderCancel_contact_hidden').val();


                            $('#myOrderlist').datagrid('reload',parmMyOrder);
                            $('#myOrderlist_audit').datagrid('reload',parmMyOrderAudit);
                        }
                    }
                    window.parent.alertWin('系统提示', data.msg);
                    if(contactId!=null)
                    {
                        window.parent.subContactCallback(contactId, 'refreshOrderHistory');
                    }
                }
                else
                {
                    $('#dlgCancel').dialog('close');
                }
            }
        });
    });

    $('#orderCancel_cancel').click(function(){
        $('#dlgCancel').window('close');
    });


//    $('#orderConsult_confirm').click(function(){
//        var orderId=$("#txtOrderId_Consult").val();
//
//        $.ajax({
//            url:"/myorder/myorder/consult",
//            contentType:"application/json; charset=utf-8",
//            data: {"orderId":orderId},
//            success:function(data){
//                if(data!=null)
//                {
//                    var contactId=null;
//                    if(data.succ!=null&&data.succ=='1')
//                    {
//                        $('#dlgConsult').dialog('close');
//                        contactId=$('#orderCancel_contact_hidden').val();
//
//
//                        $('#myOrderlist').datagrid('reload',parmMyOrder);
//                        $('#myOrderlist_audit').datagrid('reload',parmMyOrderAudit);
//                    }
//                    window.parent.alertWin('系统提示', data.msg);
//                    if(contactId!=null)
//                    {
//                        window.parent.subContactCallback(contactId, 'refreshOrderHistory');
//                    }
//                }
//                else
//                {
//                    $('#dlgConsult').dialog('close');
//                }
//            }
//        });
//    });
//
//    $('#orderConsult_cancel').click(function(){
//        $('#dlgConsult').window('close');
//    });



    //待付款订单查询相关
    var paylistInit=false;
    $('#myOrderlist_pay').datagrid({
        width : '100%',
        height : 390,
        fitColumns : true,
        //url : '/myorder/myorder/query_pay',
        columns:[[
            {field:'id',title:'ID',width:0,editor:'text', hidden: true},
            {field:'orderid',title:'订单编号',align:'center',width:80,editor:'text',formatter:function (value,row,index){
                var canedit=0;
                if(row.right.indexOf("MODIFY")!=-1||row.right.indexOf("ALL")!=-1)
                {
                    canedit=1;
                }
                var token='<a class="link" href="javascript:void(0);" onclick="javascript:viewOrder('+'\'myOrderlist_pay\','+index+','+row.orderid+','+canedit+')" > ';

                token+=value;
                token+='</a>';

                return token;
            }},
            {field:'crdt',title:'订单生成时间',width:140,editor:'text'},
            {field:'status',title:'订单状态',align:'center',width:80,editor:'text',formatter:function (value){
                for(var i= 0; i<orderStatuses.length; i++){
                    if (orderStatuses[i].id == value || (!value && orderStatuses[i].id == "0")) return orderStatuses[i].dsc;
                }
                return value;
            }},
            {field:'totalprice',title:'订单金额',width:80,align:'right',editor:'text', formatter:function(value){
                return formatPrice(value);
            }},
            {field:'crusr',title:'座席工号',align:'center',width:80,editor:'text'},
            {field:'contactid',title:'客户编号',align:'center',width:80,editor:'text'},
            {field:'contactname',title:'客户名称',align:'center',width:80,editor:'text'},
            {field:'paytype',title:'支付方式',align:'center',width:80,editor:'text',formatter:function (value){
                for(var i= 0; i<payTypes.length; i++){
                    if (payTypes[i].id == value || (!value && payTypes[i].id == "0")) return payTypes[i].dsc;
                }
                return value;
            }},
            {field:'cardtype',title:'卡类型',align:'center',width:80,editor:'text'},
            {field:'confirm',title:'支付结果',align:'center',width:80,editor:'text',formatter:function (value){
                 if(value=="-1")
                 {
                     return resultStatusList[1].val;
                 }
                 else
                 {
                     return resultStatusList[0].val;
                 }
            }},
            {field:'right',title:'操作',align:'center',width:80,editor:'text',formatter: function(val, row,index){
                var cancelEnableToken='<a href="javascript:void(0);" onclick="javascript:cancelOrder('+'\'myOrderlist_pay\','+index+','+row.orderid+','+row.contactid+')" title="取消订单" class="del"></a>';
                var cancelDisableToken='<a href="javascript:void(0);"  class="del_dis" disabled="true" title="取消订单"></a>';
                var modifyEnableToken= '<a href="javascript:void(0);" onclick="javascript:modifyOrder('+'\'myOrderlist_pay\','+index+','+row.orderid+')" class="modify mr10" title="修改"></a>';
                var modifyDisableToken='<a href="javascript:void(0);"  class="modify_dis mr10" disabled="true"  title="修改"></a>';

                var claimEnableToken= '<a href="javascript:void(0);" onclick="javascript:claimOrder('+'\'myOrderlist_pay\','+index+','+row.orderid+')" class="cr_card mr10" title="索权"></a>';
                var claimDisableToken='<a href="javascript:void(0);"  class="cr_card_dis mr10" disabled="true"  title="索权"></a>';
                var tableToken='';
                if(val.indexOf("ALL")!=-1)
                {
                    //tableToken+=viewEnableToken;
                    tableToken+=modifyEnableToken;
                    if(isClaimEnable(row.bankName))
                        tableToken+=claimEnableToken;
                    else
                        tableToken+=claimDisableToken;
                    tableToken+=cancelEnableToken;
                }
                else
                {
                    if(val.indexOf("MODIFY")!=-1)
                    {
                        tableToken+=modifyEnableToken;
                        if(row.confirm!='-1' && isClaimEnable(row.bankName))
                            tableToken+=claimEnableToken;
                        else
                            tableToken+=claimDisableToken;
                    }
                    else
                    {
                        tableToken+=modifyDisableToken;
                        tableToken+=claimDisableToken;
                    }
                    if(val.indexOf("DELETE")!=-1)
                    {
                        tableToken+=cancelEnableToken;
                    }
                    else
                    {
                        tableToken+=cancelDisableToken;
                    }
                }
                tableToken+="";

                return tableToken;

            }}
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination:true,
        queryParams: parmMyOrderPay,
        onBeforeLoad:function(data)
        {
          if(data==null||data.xxx!=null)
            return false;
        },
        onLoadSuccess: function(data){
            if(data.err!=null)
            {
                window.parent.alertWin('系统提示', data.err);
            }
            if(data.total!=null)
            {
                totalOrderPayCount=data.total;
            }
            $('.disable-linkbutton2').linkbutton({disable:'true', plain:true});
            $('.enable-linkbutton2').linkbutton({color:'blue', plain:true});
        },
        onLoadError: function(){
            window.parent.alertWin('系统提示', '加载失败!');
        },
        onUncheck:function(index,data){
            $(this).datagrid("clearSelections");
        }

    });

    var pPay = $('#myOrderlist_pay').datagrid('getPager');
    $(pPay).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    $('#myOrdersearch_pay').click(function(){
        if(isManager==true)
        {
            if(!$("#tbCrusr_pay").validatebox("isValid"))
            {
                return;
            }
        }

        totalOrderPayCount=-1;
        parmMyOrderPay={
            'orderId' : getNotEmptyString($("#orderid_pay").val().replace(/^\s+|\s+$/g, "")),
            'provinceId': getNotEmptyString($("#province002").combobox('getValue')),
            'cityId': getNotEmptyString($("#cityId002").combobox('getValue')),
            'countyId': getNotEmptyString($("#countyId002").combobox('getValue')),
            'areaId': getNotEmptyString($("#areaId002").combobox('getValue')),
            'contactId' : getNotEmptyString($("#tbContactid_pay").val().replace(/^\s+|\s+$/g, "")),
            'contactName' : getNotEmptyString($("#tbContactname_pay").val().replace(/^\s+|\s+$/g, "")),
            'crUsr' : getNotEmptyString($("#tbCrusr_pay").val().replace(/^\s+|\s+$/g, "")),
            'beginCrdt' : $("#tbBeginCrdt_pay").datebox('getValue'),
            'endCrdt' : $("#tbEndCrdt_pay").datebox('getValue'),
            'getContactName' : getNotEmptyString($("#tbGetcontactname_pay").val().replace(/^\s+|\s+$/g, "")),
            'getContactPhone' : getNotEmptyString($("#tbPhone_pay").val().replace(/^\s+|\s+$/g, "")),
            'payType' : getNotEmptyString($("#tbPaytype_pay").combobox('getValue')),
            'prodId' : getNotEmptyString($("#tbProdname_pay").val().replace(/^\s+|\s+$/g, "")),
            'confirm' : getNotEmptyString($("#tbPaytype_result").combobox('getValue')),
            'countRows':totalOrderPayCount
        };
        if(paylistInit==false)
        {
            $('#myOrderlist_pay').datagrid({url:'/myorder/myorder/query_pay'});
            paylistInit=true;
        }
        $('#myOrderlist_pay').datagrid('load',parmMyOrderPay);
    });




    //待审批订单查询相关
    var auditlistInit=false;
    $('#myOrderlist_audit').datagrid({
        width : '100%',
        height : 410,
        fitColumns : true,
        //url : '/myorder/myorder/query_audit',
        columns:[[
            {field:'batchid',title:'工作流批号',width:80,editor:'text',formatter:function (value,row,index){
                var token='<a class="link" href=# onclick="javascript:confirmAudit('+index+','+row.orderid+','+ row.batchid+',\''+row.audittype+'\',0)" > ';
                token+=value;
                token+='</a>';

                return token;
            }},
            {field:'orderid',title:'订单编号',width:80,editor:'text'},
            {field:'crdt',title:'订单生成时间',width:80,editor:'text'},
            {field:'status',title:'订单状态',width:80,editor:'text',formatter:function (value){
                for(var i= 0; i<orderStatuses.length; i++){
                    if (orderStatuses[i].id == value || (!value && orderStatuses[i].id == "0")) return orderStatuses[i].dsc;
                }
                return value;
            }},
            {field:'totalprice',title:'订单金额',width:80, align:'right', editor:'text', formatter:function(value){
                return formatPrice(value);
            }},
            {field:'crusr',title:'座席工号',width:80,editor:'text'},
            {field:'contactid',title:'客户编号',width:80,editor:'text'},
            {field:'contactname',title:'客户名称',width:80,editor:'text'},
            {field:'paytype',title:'支付方式',width:80,editor:'text',formatter:function (value){
                for(var i= 0; i<payTypes.length; i++){
                    if (payTypes[i].id == value || (!value && payTypes[i].id == "0")) return payTypes[i].dsc;
                }
                return value;
            }},
            {field:'auditdt',title:'提交时间',width:80,editor:'text'},
            {field:'audittype',title:'审核类型',width:80,editor:'text'},
            {field:'auditstatus',title:'工作流状态',width:80,editor:'text',formatter:function (value){
                for(var i= 0; i<auditStatuses.length; i++){
                    if (auditStatuses[i].id == value || (!value && auditStatuses[i].id == "0")) return auditStatuses[i].dsc;
                }
                return value;
            }},
            {field:'right',title:'处理',width:80,editor:'text',align:'center',formatter: function(val, row,index){
                var cancelEnableToken='<a href="javascript:void(0);" onclick="javascript:cancelAudit('+index+','+row.orderid+','+ row.batchid +')" class="del"></a>';
                var confirmEnableToken= '<a href="javascript:void(0);" onclick="javascript:confirmAudit('+index+','+row.orderid+','+ row.batchid+',\''+row.audittype+'\',1)" class="link easyui-linkbutton">确认</a>';

                var tableToken='';
                if(val.indexOf("ALL")!=-1)
                {
                    tableToken+=cancelEnableToken;
                    if(row.audittype != '新增订单' && row.audittype != '新增客户' && row.audittype != '取消订单') {
                    	tableToken+=confirmEnableToken;
                    }
                }
                else
                {
                    if(val.indexOf("DELETE")!=-1)
                    {
                        tableToken+=cancelEnableToken;
                    }

                    if(val.indexOf("CONFIRM")!=-1 && (row.audittype != '新增订单' && row.audittype != '新增客户' && row.audittype != '取消订单'))
                    {
                        tableToken+=confirmEnableToken;
                    }
                }
                tableToken+="";

                return tableToken;

            }}
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination:true,
        queryParams: parmMyOrderAudit,
        onBeforeLoad:function(data)
        {
          if(data==null||data.xxx!=null)
            return false;
        },
        onLoadSuccess: function(data){
            if(data.err!=null)
            {
                window.parent.alertWin('系统提示', data.err);
            }
            if(data.total!=null)
            {
                totalOrderAuditCount=data.total;
            }
        },
        onLoadError: function(){
            window.parent.alertWin('系统提示', "加载失败!");
        },
        onUncheck:function(index,data){
            $(this).datagrid("clearSelections");
        }
    });

    var pAudit = $('#myOrderlist_audit').datagrid('getPager');
    $(pAudit).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });


    $('#myOrdersearch_audit').click(function(){
        if(isManager==true)
        {
            if(!$("#tbCrusr_audit").validatebox("isValid"))
            {
                return;
            }
        }

        totalOrderAuditCount=-1;
        parmMyOrderAudit={
            'orderId' : getNotEmptyString($("#orderid_audit").val().replace(/^\s+|\s+$/g, "")),
            'contactId' : getNotEmptyString($("#tbContactid_audit").val().replace(/^\s+|\s+$/g, "")),
            'contactName' : getNotEmptyString($("#tbContactname_audit").val().replace(/^\s+|\s+$/g, "")),
            'crUsr' : getNotEmptyString($("#tbCrusr_audit").val().replace(/^\s+|\s+$/g, "")),
            'beginCrdt' : $("#tbBeginCrdt_audit").datebox('getValue'),
            'endCrdt' : $("#tbEndCrdt_audit").datebox('getValue'),
            'payType' : getNotEmptyString($("#tbPaytype_audit").combobox('getValue')),
            'prodId' : getNotEmptyString($("#tbProdname_audit").val().replace(/^\s+|\s+$/g, "")),
            'auditStatus': $("#tbStatus_audit").combobox('getValue'),
            'busiType':busiType,
            'countRows':totalOrderAuditCount
        };
        if(auditlistInit==false)
        {
            $('#myOrderlist_audit').datagrid({url:'/myorder/myorder/query_audit'});
            auditlistInit=true;
        }
        $('#myOrderlist_audit').datagrid('load',parmMyOrderAudit);
    });

//    $('#orderAuditCancel_confirm').click(function(){
//        var batchId=$("#txtAuditBatchId").val();
//        var remark=$("#txtAuditRemark").val();
//
//        $.ajax({
//            url:"/myorder/myorder/cancel_audit",
//            contentType:"application/json; charset=utf-8",
//            data: {"batchId":batchId,"remark":encodeURI(remark)},
//            success:function(data){
//                if(data!=null)
//                {
//                    var showInfo=data.substr(1);
//                    var flag=data.substr(0,1);
//                    window.parent.alertWin('系统提示', showInfo);
//                    if(flag=='1')
//                    {
//                        $('#dlgAuditCancel').dialog('close');
//                        $('#myOrderlist_audit').datagrid('reload',parmMyOrderAudit);
//                    }
//                }
//                else
//                {
//                    $('#dlgAuditCancel').dialog('close');
//                }
//            }
//        });
//    });
//
//    $('#orderAuditCancel_cancel').click(function(){
//        $('#dlgAuditCancel').window('close');
//    });


    //索权相关
    $('#orderClaim_confirm').click(function(){
        var orderId=$("#txtClaimOrderId").val();

        $.ajax({
            url:"/myorder/myorder/grant/"+orderId,
            async : false,
            success:function(data){
                if(data!=null)
                {
                    window.parent.alertWin('系统提示', data.msg);
                    if(data.st=='succ')
                    {
                        $('#dlgClaim').dialog('close');
                    }
                }
                else
                {
                    $('#dlgClaim').dialog('close');
                }
                $('#myOrderlist_pay').datagrid('reload',parmMyOrderPay);
            }
        });
    });

    $('#orderClaim_cancel').click(function(){
        $('#dlgClaim').window('close');
    });

    $('#pay_order_address').load("/common/address_no_validation.jsp?instId=002");
})

function viewOrder(listName, index, orderId,canedit)
{
    var url='myorder/orderDetails/get/'+orderId+'?activable=false';
    window.parent.addTab('myorder_'+orderId,'订单：'+orderId,'false',url);
}
function modifyOrder(listName, index, orderId)
{
	if (isOrderInBmp(orderId)) {
		return;
	} else {
		doModifyOrder(orderId);
	}
}
function doModifyOrder(orderId) {
	if (orderId == undefined) {
		orderId = $("#id_audit_order").html();
		closeOpenAuditWindow();
	}
	window.parent.addTab('myorder_'+orderId,'订单：'+orderId,'false','myorder/orderDetails/get/'+orderId+'?activable=true');
}

function isOrderInBmp(orderId) {
	var isInBmp = false;
	$.ajax({
		type: 'POST',
		async: false,
		url: 'orderDetails/check/' + orderId + '/approving',
		dataType: 'json',
		success: function(data) {
			if (data.approve == 'false') {
				isInBmp = true;
				$("#id_audit_batch").html(data.batchId);
				$("#id_audit_order").html(orderId);
				$("#id_input_bmp_type").val(data.bpmType);
				$("#div_open_audit").dialog("open");
			}
		}
	});
	return isInBmp;
}
function openAudit() {
	var batchId = $("#id_audit_batch").html();
	var orderId = $("#id_audit_order").html();
	var bpmType = $("#id_input_bmp_type").val();
	var url = 'task/processAuditTaskAjax?batchId=' + batchId + '&auditTaskType=' + bpmType + '&isConfirmAudit=0&id=' + orderId;
	window.parent.addTab('task_' + batchId, '我的订单', 'false', url);
	closeOpenAuditWindow();
}

function closeOpenAuditWindow() {
	$("#div_open_audit").dialog("close");
	$("#id_audit_batch").html("");
	$("#id_audit_order").html("");
	$("#id_input_bmp_type").val("");
}

function consultOrder(listName, index, orderId, contactId)
{
    var text='订单：'+orderId+' 确定要转咨询吗？';

    $("#txtOrderId_Consult").val(orderId);
    $('#consult_show_text').html(text);
    $('#orderCancel_contact_hidden').val(contactId);
    $('#dlgConsult').dialog({title:'订单转咨询',
        width:300,
//        height:200,
        top:200,
        iconCls:'',
        shadow: true,
        modal:true,
        closed:true,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        draggable:false,
        buttons: [{
            text: '确定',
            plain:true,
            handler: function() {
                var orderId=$("#txtOrderId_Consult").val();

                $.ajax({
                    url:"/myorder/myorder/consult",
                    contentType:"application/json; charset=utf-8",
                    data: {"orderId":orderId},
                    success:function(data){
                        if(data!=null)
                        {
                            var contactId=null;
                            if(data.succ!=null&&data.succ=='1')
                            {
                                $('#dlgConsult').dialog('close');
                                contactId=$('#orderCancel_contact_hidden').val();


                                $('#myOrderlist').datagrid('reload',parmMyOrder);
                                $('#myOrderlist_audit').datagrid('reload',parmMyOrderAudit);
                            }
                            window.parent.alertWin('系统提示', data.msg);
                            if(contactId!=null)
                            {
                                window.parent.subContactCallback(contactId, 'refreshOrderHistory');
                            }
                        }
                        else
                        {
                            $('#dlgConsult').dialog('close');
                        }
                    }
                });
            }
        }, {
            text: '取消',
            plain:true,
            handler: function() {
                $('#dlgConsult').dialog('close');
            }
        }]
    })
        .window('open');
}

function noteOrder(listName, index, orderId)
{
    $("#txtOrderId_Note").val(orderId);
    $("#txtAppend_note").val('');
    $('#dlgNote').dialog({title:'订单跟单备注',
        width:350,
        top:200,
        iconCls:'',
        shadow: true,
        modal:true,
        closed:true,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        draggable:false,
        buttons: [{
            text: '确定',
            plain:true,
            handler: function() {
                var orderId=$("#txtOrderId_Note").val();
                var newNote=$("#txtAppend_note").val();
                $.ajax({
                    url:"/myorder/myorder/note",
                    contentType:"application/json; charset=utf-8",
                    data: {"orderId":orderId, "newNote": newNote},
                    success:function(data){
                        if(data!=null)
                        {
                            if(data.succ!=null&&data.succ=='1')
                            {
                                $('#dlgNote').dialog('close');

                                $('#myOrderlist').datagrid('reload',parmMyOrder);
                            }
                            else
                            {
                                window.parent.alertWin('系统提示', data.msg);
                            }
                            //window.parent.alertWin('系统提示', data.msg);
                        }
                        else
                        {
                            $('#dlgConsult').dialog('close');
                        }
                    }
                });
            }
        }, {
            text: '取消',
            plain:true,
            handler: function() {
                $('#dlgNote').dialog('close');
            }
        }]
    })
        .window('open');
}

function claimOrder(listName, index, orderId)
{
    //check
    var pass=true;
    $.ajax({
        url:"/myorder/myorder/grantCheck/"+orderId,
        async : false,
        success:function(data){
            if(data!=null)
            {
                if(data.result=="1")
                {
                    pass=false;
                    window.parent.alertWin('系统提示', "订单正在审批中，请稍后索权。");
                    return;
                }
            }
        }
    });

    if(pass==false)
        return;
    $('#txtClaimOrderId').val(orderId);
    $('#dlgClaim').window({title:'订单在线索权',
        width:400,
        height:140,
        top:200,
        iconCls:'',
        shadow: true,
        modal:true,
        closed:true,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        draggable:false
    })
        .window('open');
}

function cancelOrder(listName, index, orderId, contactId)
{
    $('#txtOrderId').val(orderId);
    $('#txtRemark').val(null);
    $('#orderCancel_contact_hidden').val(contactId);
    $('#dlgCancel').dialog({title:'取消订单',
        width:400,
//        height:200,
        top:200,
        iconCls:'',
        shadow: true,
        modal:true,
        closed:true,
        minimizable:false,
        maximizable:false,
        collapsible:false ,
        draggable:false ,
        buttons: [{
            text: '确定',
            plain:true,
            handler: function() {
                var orderId=$("#txtOrderId").val();
                var remark=$("#txtRemark").val();

                $.ajax({
                    url:"/myorder/myorder/cancel",
                    contentType:"application/json; charset=utf-8",
                    data: {"orderId":orderId,"remark":encodeURI(remark)},
                    success:function(data){
                        if(data!=null)
                        {
                            var contactId=null;
                            if(data.succ!=null&&data.succ=='1')
                            {
                                $('#dlgCancel').dialog('close');
                                if(data.audit!=null&&data.audit=='0')
                                {
                                    var rowIndex=$('#orderCancel_index_hidden').val();
                                    contactId=$('#orderCancel_contact_hidden').val();


                                    $('#myOrderlist').datagrid('reload',parmMyOrder);
                                    $('#myOrderlist_audit').datagrid('reload',parmMyOrderAudit);
                                }
                            }
                            window.parent.alertWin('系统提示', data.msg);
                            if(contactId!=null)
                            {
                                window.parent.subContactCallback(contactId, 'refreshOrderHistory');
                            }
                        }
                        else
                        {
                            $('#dlgCancel').dialog('close');
                        }
                    }
                });
            }
        }, {
            text: '取消',
            plain:true,
            handler: function() {
                $('#dlgCancel').window('close');
            }
        }]
    })
        .window('open');
}

function cancelAudit(index, orderId, batchId)
{
    $('#txtAuditBatchId').val(batchId);
    $('#txtAuditRemark').val(null);
    $('#dlgAuditCancel').dialog({
        title:'取消审批',
        width:350,
//        height:200,
        iconCls:'',
        top: 200,
//        top:($(window).height() - 450) * 0.5,
//        left:($(window).width() - 950) * 0.5,
        shadow: true,
        modal:true,
        closed:true,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        draggable:false,
        buttons: [{
            text: '确定',
            plain:true,
            handler: function() {
                var batchId=$("#txtAuditBatchId").val();
                var remark=$("#txtAuditRemark").val();

                $.ajax({
                    url:"/myorder/myorder/cancel_audit",
                    contentType:"application/json; charset=utf-8",
                    data: {"batchId":batchId,"remark":encodeURI(remark)},
                    success:function(data){
                        if(data!=null)
                        {
                            var showInfo=data.substr(1);
                            var flag=data.substr(0,1);
                            window.parent.alertWin('系统提示', showInfo);
                            if(flag=='1')
                            {
                                $('#dlgAuditCancel').dialog('close');
                                $('#myOrderlist_audit').datagrid('reload',parmMyOrderAudit);
                            }
                        }
                        else
                        {
                            $('#dlgAuditCancel').dialog('close');
                        }
                    }
                });
            }
        }, {
            text: '取消',
            plain:true,
            handler: function() {
                $('#dlgAuditCancel').dialog('close');
            }
        }]
    })
        .window('open');
}

function confirmAudit(index, orderId, batchId,auditTaskType,isConfirmAudit)
{
    window.parent.addTab('task_'+batchId,'我的订单','false','task/processAuditTaskAjax?batchId='+batchId+'&auditTaskType='+auditTaskType+'&isConfirmAudit='+isConfirmAudit+'&id='+orderId);
}

function timeFormatter(date)
{
    return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
}
function timeParser(date)
{
    return new Date(Date.parse(date.replace(/-/g,"/")));
}

var b_order_address_init=false;
function showMoreCondition(bShowCondition)
{
    if(bShowCondition==0)
    {
        $('#morConditons1').css({display:'none'});
        $('#morConditons2').css({display:'none'});
        $('#morConditons3').css({display:'none'});
        $('#morConditons4').css({display:'none'});
        $('.u').removeClass('underline');
    }
    else
    {
        if(b_order_address_init==false)
        {
            $('#order_address').load("/common/address_no_validation.jsp?instId=001",function()
            {
                b_order_address_init=true;
                $('#morConditons1').css({display:''});
                $('#morConditons2').css({display:''});
                $('#morConditons3').css({display:''});
                $('#morConditons4').css({display:''});
                $('.u').addClass('underline');
            }
            );

        }
        else
        {
        $('#morConditons1').css({display:''});
        $('#morConditons2').css({display:''});
        $('#morConditons3').css({display:''});
        $('#morConditons4').css({display:''});
        $('.u').addClass('underline');
        }
    }
    setTimeout(function(){
        window.parent.SetCwinHeight(frameElement);
    },200);
}

function formatPrice(price)
{
    if(price==null||price=='')
        return '0.00';
    var prc=price;
    return prc.toFixed(2);

}

var bNeedRefresh=false;
function refreshOrderHistory()
{
    bNeedRefresh=true;
}

function refreshMyorderlist()
{
    if(bNeedRefresh==true)
    {
        $('#myOrderlist').datagrid('reload');
        $('#myOrderlist_audit').datagrid('reload');
        bNeedRefresh=false;
    }
}

function justest(data)
{
    $("#orderid_pay").val(data.orgSourceType);
    $("#myorder_tabs").tabs("select",1);
}

function isClaimEnable(cardtype)
{
    if(cardtype==null)
        return false;
    var strIndex=cardtype.indexOf('招行');
    if(strIndex>=0)
    {
        return true;
    }
    return false;
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

function viewTrack(orderId)
{
    showOrderProcess(orderId, '跟单查询');
}

/**
 *
 * @param sysmessage see com.chinadrtv.erp.sales.dto.SysMessageDto
 */
function queryFromSysmessage(sysmessage)
{
    if(sysmessage.orgSourceType=='5'||sysmessage.orgSourceType=='6'||sysmessage.orgSourceType=='11')
    {
        $("#myorder_tabs").tabs("select",3);
        var beginDate=$("#tbBeginCrdt_audit").datebox('getValue');
        var endDate=$("#tbEndCrdt_audit").datebox('getValue');
        $('#myOrderreset_audit').click();
        $("#tbCrusr_audit").val(sysmessage.receiverId);
        $("#tbStatus_audit").combobox('setValue','2');
        if(sysmessage.orgSourceType=='5')  //cancel_order reject
        {
            busiType='2';
        }
        else if(sysmessage.orgSourceType=='6') //modify_order reject
        {
            busiType='1';
        }
        else if(sysmessage.orgSourceType=='11') //add_order reject
        {
            busiType='1';
        }

        $("#tbBeginCrdt_audit").datebox('setValue',beginDate);
        $("#tbEndCrdt_audit").datebox('setValue',endDate);

        $('#myOrdersearch_audit').click();
        busiType=null;
    }
    else if(sysmessage.orgSourceType=='0')
    {
        //问题单
        //保留日期
        $("#myorder_tabs").tabs("select",2);
        var beginDate=$("#input_problem_tbBeginCrdt").datebox('getValue');
        var endDate=$("#input_problem_tbEndCrdt").datebox('getValue');

        $('#a_myProblemOrderreset').click();

        $('#input_problem_tbBeginOutdt').datebox('setValue','');
        $('#input_problem_tbEndOutdt').datebox('setValue','');
        $("#input_problem_tbBeginCrdt").datebox('setValue',beginDate);
        $("#input_problem_tbEndCrdt").datebox('setValue',endDate);
        $("#input_problem_tbCrusr").val(sysmessage.receiverId);


        $('#a_myProblemOrdersearch').click();
    }
    else if(sysmessage.orgSourceType=='1')
    {
        //催送货
        $("#myorder_tabs").tabs("select",4);
        $('#clearReminder').click();
        //日期从消息中来
        //r_beginCrDate r_endCrDate
        $('#r_beginCrDate').datebox('setValue',new Date(new Date()-90*86400000).format('yyyy-MM-dd'));

        $('#r_endCrDate').datebox('setValue', new Date().format('yyyy-MM-dd'));
        $('#agentId').val(sysmessage.receiverId);
        $('#r_status').combobox('setValue','1');
        $('#queryReminder').click();
    }

}





/**
 * 跟单
 */
function followOrder() {
	var trackUser = $('#d_seat').combobox('getValue');
	var checkedRows = $('#myOrderlist').datagrid('getChecked');
	
	if(null == trackUser || '' == trackUser || checkedRows.length == 0){
		return;
	}
	
	var orderIds = '';
	for(var i=0; i<checkedRows.length; i++) {
		orderIds += checkedRows[i].orderid;
		if(i != (checkedRows.length-1)) {
			orderIds += ',';
		}
	}

	var ownerUser = $('#tbCrusr').val();
	
	$.ajax({
		type : 'POST',
		url : '/myorder/myorder/assignToAgent',
		data : {'orderIds': orderIds, 'trackUser': trackUser, 'ownerUser': ownerUser},
		success : function(rs) {
			if(eval(rs.success)) {
				closeFollowWin();
				$('#myOrdersearch').trigger('click');
				window.parent.alertWin('提示', '共分配 ['+rs.totalCount+'] 条跟单任务到坐席 ['+trackUser+'] ');
			}else{
				window.parent.alertWin('提示', '分配跟单任务失败');
				console.error('分配跟单任务失败: ' + rs.message);
			}
		},
		error : function(msg){
			window.parent.alertWin('提示', '分配跟单任务失败');
		}
	});
}
/**
 * 关闭跟单弹出窗口
 */
function closeFollowWin() {
	$('#op_list').window('close');
	$('#d_dept').combobox('setValue', null);
	$('#d_group').combobox('setValue', null);
	$('#d_seat').combobox('setValue', null);
}


function getNotEmptyString(str){
    if(str==null||str=="")
        return null;
    else
        return str;
}