function _handler(){
       $('#mytask_tabs').tabs('resize',{width:'100%'});
       $('#table_campaignTask,#table_auditTask,#table_jobRelationex').datagrid('resize',{width:'100%'});
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




//弹出窗口
function showWindow(options,winPop){
    if (options && options.width && options.height){
        options.top=($(window).height() - options.height) * 0.5;
        options.left=($(window).width() - options.width) * 0.5;
    }
    if(winPop){
        winPop="#"+winPop;
        jQuery(winPop).window(options);
        jQuery(winPop).window('open');
    }else{
        jQuery("#MyPopWindow").window(options);
        $('#MyPopWindow').window('open');
    }
}
$(function(){

    mytask_init();

    /*================================营销任务==================================*/
    $('#table_campaignTask').datagrid({
        width : '100%',
        height : 438,
        fitColumns : true,
        rownumbers:true,
        nowrap:false,
//        fit:true,
        url : 'queryCampaignTask',
        scrollbarSize:-1,
        queryParams : {
            'xxx':'xxx'
        },
        onBeforeLoad:function(data)
        {
            if(data==null||data.xxx!=null)
                return false;
        },
        columns : [ [
            {
                field : 'instID',
                title : '任务编号',
                formatter: function(val, row) {
                    if(row.conid==null) {
                        var url = '<a class="link" href="javascript:void(0)" id=\''+val+'\' onclick="showOutPhone(\''+val+'\')">'+val+'</a>';
                        return url;
                    } else {
                        return val;
                    }
                }
            },
            {
                field : 'caID',
                title : '营销编号'
            },
            {
                field : 'caName',
                title : '营销名称'
            },
            {
                field : 'taskType',
                align:'center',
                title : '任务类型'
            },
            {
                field : 'sourceTypeDsc',
                align:'center',
                title : '任务来源'
            },
            {
                field : 'conid',
                title : '客户编号',
                align:'center',
                formatter: function(val, row){
                    if(val != null) {
                        var id = val;
                        var url;
                        var paras='';
                        if(row.ltStatus=='初始化' || row.ltStatus=='开始') {
                            paras+='?instId='+row.instID;
                        }
                        if(row.isPotential==0) {
                            url = ctx+'/contact/1/'+row.conid;
                        } else {
                            url = ctx+'/contact/2/'+row.conid;
                        }
                        url+=paras;
                        url = '<a class="link" href="javascript:void(0)" id=\''+id+'\' onclick="execTask(\''+id.valueOf()+'\', \'我的客户\', \''+url+'\','+row.caID+','+row.instID+')">'+val+'</a>';
                        return url;
                    }
                }
            },
            {
                field : 'conName',
                align:'center',
                title : '客户名称'
            },
            {
                field : 'ltCreateDate',
                title : '任务生成时间',
                formatter : dateFormatter,
                width:130
            },{
                field : 'endDate',
                title : '任务到期时间',
                formatter : dateFormatter,
                width:130
            },
            {
                field : 'appointDate',
                title : '预约联系时间',
                formatter : dateFormatter,
                width:130
            },
            {
                field : 'ltStatus',
                align:'center',
                title : '状态'
            },
            {
                field : 'remark',
                width:100,
                title : '备注'
            },
            {
                field : 'ops',
                title : '操作',
                width:100,
                align:'center',
                formatter: function(value,row){
                    if((row.sourceType == 0 || row.sourceType==2) &&(row.ltStatus=='初始化' || row.ltStatus=='开始')) {
                        url = '<a href="javascript:void(0)" class="del" id=\''+row.instID+'\' onclick="cancelTask(\''+row.instID+'\')"></a>';
                        return url;
                    } else {
                        return;
                    }
                }
            }
        ] ],
        remoteSort : false,
        singleSelect : true,
        pagination : true,
        pageList : [ 5, 10, 15 ],
        pageSize:15,

        rowStyler: function(index,row){
            if (row.exigency == '0') {
                return 'background-color:#FFC1C1';
            } else if (row.exigency == '1') {
                return 'background-color:#fcffa6';
            }
        }
    });

    var campaignTaskPager = $('#table_campaignTask').datagrid('getPager');
    $(campaignTaskPager).pagination({
        beforePageText : '第',
        afterPageText : '页    共 {pages} 页',
        displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onBeforeRefresh : function() {
            $(this).pagination('loading');
            $(this).pagination('loaded');
        }
    });

    //查询
    $('#btn_campaignTask').click(function(){
        if(!$("#campaignTaskForm").form("validate"))
        {
            console.debug("invalidate data!!!");
            return;
        }
        var startTime = getTimeByDateStr($('#startDate').datebox('getValue'));
        var endTime = getTimeByDateStr($('#endDate').datebox('getValue'));
        if(endTime - startTime > 15*86400000) {
            window.parent.alertWin('系统提示', "查询时间间隔不能超过15天");
            return;
        }
        $('#table_campaignTask').datagrid('reload',{
            'startDate' : $('#startDate').datebox('getValue'),
            'endDate' : $('#endDate').datebox('getValue'),
            'customerID' : $("#customerID").val(),
            'customerName' : $('#customerName').val(),
            'campaignName' : $('#campaignName').val(),
            'userID' : $('#userID').val(),
            'taskType' : $('#campaignTaskTypes').combobox('getValue'),
            'taskSourceType' : $('#campaignTaskSourceTypes').combobox('getValue'),
            'status' : $('#campaignTaskStatus').combobox('getValue')
        });
    });

    //清空
    $('#btn_campaignClear').click(function(){
        $('#campaignTaskForm')[0].reset();
        $('#campaignTaskStatus').combobox('setValue','');
        $('#campaignTaskTypes').combobox('setValue','');
        $('#startDate').datebox('setValue', new Date(new Date()-15*86400000).format('yyyy-MM-dd'));
        $('#endDate').datebox('setValue', new Date().format('yyyy-MM-dd'));
    });

	/*================================投诉查询=================================*/
	$('#complaintsQuery').datagrid({
		width : '100%',
		height : 438,
		fitColumns : true,
        rownumbers:true,
        nowrap:false,
        scrollbarSize:-1,
		columns : [ [
 		{
			field : 'instID',
			title : '客户姓名'
		},
		{
			field : 'caID',
			title : '投诉内容'
		},
		{
			field : 'caName',
			title : '投诉日期'
		},
		{
			field : 'taskType',
            align:'center',
			title : '投诉时间'
		},
		{
			field : 'sourceTypeDsc',
            align:'center',
			title : '创建人'
		},
		{
			field : 'conid',
			title : '订单编号'
		},
		{
			field : 'conName',
            align:'center',
			title : '产品'
		},
		{
			field : 'ltCreateDate',
			title : '优先级',
			width:130
		},{
			field : 'endDate',
			title : '状态',
			width:130
		},
		{
			field : 'appointDate',
			title : '事件ID',
			width:130
		},
		{
			field : 'ltStatus',
            align:'center',
			title : '处理人'
		}
		] ],
		remoteSort : false,
		singleSelect : true,
		pagination : true,
        pageList : [ 5, 10, 15 ],
        pageSize:15
	});

	var data = $('#auditTaskStatus').combobox('getData');
    if (data.length > 0) {
        $('#auditTaskStatus').combobox('select', data[1].value);
    } 
	$('#table_auditTask').datagrid({
		width : '100%',
		height : 438,
		fitColumns:true,
        rownumbers:true,
        scrollbarSize:-1,
		url : 'queryAuditTask',
		queryParams : {
			'startDate' : $('#startDate4Audit').datebox('getValue'),
			'endDate' : $('#endDate4Audit').datebox('getValue'),
			'contactID' : $("#contactID4Audit").val(),
			'appliedUserID' : $("#crUser4Audit").val(),
			'orderCreatedUserID' : $("#orderCreatedUserID4Audit").val(),
			'department' : $("#department").val(),
			'orderID' : $("#orderID4Audit").val(),
			'taskType' : $('#auditTaskTypes').combobox('getValue'),
			'taskStatus' : $('#auditTaskStatus').combobox('getValue')
		},
		columns : [ [
		{
			field : 'batchID',
			title : '修改批次',
			width:60
		}, 
		{
			field : 'busiType',
			title : '类型',
            align:'center',
			width:60
		}, 
		{
			field : 'crDate',
			title : '申请时间',
			formatter : dateFormatter,
			width:130
		}
		,
		{
			field : 'cID',
			title : '客户编号',
			width:80
		},
		{
			field : 'cName',
			title : '客户姓名',
            align:'center',
			width:60
		}, 
		{
			field : 'orderID',
			title : '订单编号',
			width:80
		},
		{
			field : 'status',
			title : '状态',
            align:'center',
			width:60
		}, 
		{
			field : 'crUser',
			title : '申请座席',
            align:'center',
			width:60
		},
		{
			field : 'orCrUsrID',
			title : '订单创建座席',
			width:80
		},
		{
			field : 'orCrUsrGrpName',
			title : '创建座席组',
            align:'center',
			width:80,
			formatter: function(value,row){
				content = value;
				if(value != null) {
					content = '<span title="'+value+'" >'+value+'</span>';
				}
				return content;
			}
		},
		{
			field : 'mdUser',
			title : '操作',
			width:60,
			align:'center',
			formatter: function(value,row){
				var id = 'myaudittask_'+row.batchID;
//				var url = ctx+'/task/processAuditTask?batchId='+row.batchID+'&auditTaskType='+row.busiType;
				var contentId;
				if(row.busiType == '修改客户' || row.busiType == '新增客户') {
					contentId = row.cID;
				} else if(row.busiType == '修改订单' || row.busiType == '取消订单' || row.busiType == '新增订单') {
					contentId = row.orderID;
				} 
				url = '<a class="dispose" title="处理" id=\''+value+'\' onclick="processAuditTask(\''+row.batchID+'\',\''+row.busiType+'\',\''+row.crUser+'\',\''+contentId+'\',\'1\')"></a>';
//				url = '<input type="button" id=\'btn_'+row.batchID+'\' value=\'处理\' onclick="window.parent.addTab(\''+id.valueOf()+'\', \'处理审批任务\', \'true\', \''+url+'\')"/>';
				return url;
			}
		}
		] ],
		remoteSort : false,
		singleSelect : true,
		pagination : true,
        pageList : [ 5, 10, 15 ],
        pageSize:15
	});

	var auditTaskPager = $('#table_auditTask').datagrid('getPager');
	$(auditTaskPager).pagination({
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function() {
			$(this).pagination('loading');
			$(this).pagination('loaded');
		}
	});
	
	//查询
	$('#btn_auditTask').click(function(){
        if(!$("#auditTaskForm").form("validate"))
        {
            console.debug("invalidate data!!!");
            return;
        }
        var startTime = getTimeByDateStr($('#startDate4Audit').datebox('getValue'));
        var endTime = getTimeByDateStr($('#endDate4Audit').datebox('getValue'));
        if(endTime - startTime > 15*86400000) {
        	window.parent.alertWin('系统提示', "查询时间间隔不能超过15天");
        	return;
        }
		$('#table_auditTask').datagrid('reload',{
			'startDate' : $('#startDate4Audit').datebox('getValue'),
			'endDate' : $('#endDate4Audit').datebox('getValue'),
			'contactID' : $("#contactID4Audit").val(),
			'appliedUserID' : $("#crUser4Audit").val(),
			'orderCreatedUserID' : $("#orderCreatedUserID4Audit").val(),
			'department' : $("#department").val(),
			'orderID' : $("#orderID4Audit").val(),
			'taskType' : $('#auditTaskTypes').combobox('getValue'),
			'taskStatus' : $('#auditTaskStatus').combobox('getValue')
		});
	});
	
	//清空
	$('#btn_auditClear').click(function(){
		$('#auditTaskForm')[0].reset();
		$('#startDate4Audit').datebox('setValue', new Date(new Date()-15*86400000).format('yyyy-MM-dd'));
		$('#endDate4Audit').datebox('setValue', new Date().format('yyyy-MM-dd'));
		$('#auditTaskTypes').combobox('setValue','');
		$('#auditTaskStatus').combobox('setValue','');
	});

    /*==========================事件回访==============================*/

    $('#btn_eventReturn_query').click(function(){
        $('#eventReturn').datagrid("load",{
            startDate: $("#eventReturn_startDate").datebox("getValue") + " 00:00:00",
            endDate: $("#eventReturn_endDate").datebox("getValue") + " 23:59:59"
        });
    });

    $('#eventReturn').datagrid({
        width : '100%',
        height:438,
        fitColumns:true,
        rownumbers:true,
        nowrap:false,
        url:'/caseRecall/queryList',
        scrollbarSize:-1,
        queryParams : {
            startDate: $("#eventReturn_startDate").datebox("getValue") + " 00:00:00",
            endDate: $("#eventReturn_endDate").datebox("getValue") + " 23:59:59"
        },
        onBeforeLoad: function(p){
            var bcrdt = new Date(p.startDate.replace(/-/g,"/"));
            var ecrdt = new Date(p.endDate.replace(/-/g,"/"));
            var day = (ecrdt.getTime()-bcrdt.getTime())/(1000*60*60*24);
            if(day > 30){
                window.parent.alertWin("警告","查询时间范围不能超过30天!");
                return false;
            }
            return true;
        },
        onDblClickRow:function(index, row){
            showWindow({
                title:'设置回访',
                href:'/caseRecall/openwindow?contactid='+row.contactid+'&orderid='+row.orderid+"&caseid="+row.caseid+'&recid='+row.recid,
                width: 700,
                onLoad: function(){
                    //$('#resourceForm').form('clear');
                }
            },"revisitDialog");
            var url = "/contact/1/"+row.contactid+"?provid=&cityid=";
            var tabName = row.usrname+"-详细信息";
            var multiTab = false;
            window.parent.addTab(row.contactid,tabName,multiTab,url,"",true);
        },
        columns : [ [
            {field:'contactid',hidden:true},
            {field:'contactid',hidden:true},
            {
                width:100,
                align:'center',
                field : 'usrname',
                title : '客户姓名'
            },
            {
                width:100,
                align:'center',
                field : 'recalltype',
                title : '回访类型',
                formatter : function(value, rowData, rowIndex) {
                    if (rowData.recalltype=='1') {
                        return  "退货回访";
                    }
                    if (rowData.recalltype=='2') {
                        return  "拒收回访";
                    }
                    if (rowData.recalltype=='3') {
                        return  "换货补发回访";
                    }
                    if (rowData.recalltype=='4') {
                        return  "其他";
                    }
                    if (rowData.recalltype=='5') {
                        return  "延时回访";
                    }
                    if (rowData.recalltype=='6') {
                        return  "待处理回访";
                    }
                    if (rowData.recalltype=='7') {
                        return  "效果回访";
                    }
                    if (rowData.recalltype=='8') {
                        return  "汇款回访";
                    }
                    if (rowData.recalltype=='9') {
                        return  "使用回访";
                    }
                    if (rowData.recalltype=='10') {
                        return  "经销商回访";
                    }
                    if (rowData.recalltype=='11') {
                        return  "教材回访";
                    }
                    if (rowData.recalltype=='12') {
                        return  "信件回访";
                    }
                }
            },
            {
                width:100,
                align:'center',
                field : 'status',
                title : '状态',
                formatter : function(value, rowData, rowIndex) {
                    if (rowData.status=='1') {
                        return  "未分配";
                    }
                    if (rowData.status=='2') {
                        return  "已分配未完成";
                    }
                    if (rowData.status=='3') {
                        return  "完成";
                    }
                }
            },
            {
                width:100,
                field : 'dsc',
                align:'center',
                title : '备注'
            },
            {
                width:100,
                field : 'orderid',
                align:'center',
                title : '订单编号'
            },
            {
                width:100,
                align:'center',
                field : 'caseid',
                title : '事件编号'
            },
            {
                width:100,
                field : 'usrid',
                align:'center',
                title : '座席编号'
            },
            {
                width:100,
                align:'center',
                field : 'crdt',
                title : '创建日期',
                formatter:function(value){
                    if(value){
                        var _date = new Date(value);
                        return _date.format('yyyy-MM-dd ');
                    } else {
                        return "";
                    }
                }
            },
            {
                width:100,
                align:'center',
                field : 'recalldt',
                title : '回访日期',
                formatter:function(value){
                    if(value){
                        var _date = new Date(value);
                        return _date.format('yyyy-MM-dd ');
                    } else {
                        return "";
                    }
                }
            }
        ] ],
        remoteSort : false,
        singleSelect : true,
        pagination : true,
        pageList : [ 5, 10, 15 ],
        pageSize:15
    });
	/*==========================主管处理==============================*/
    if(parent && parent.case){
        parent.case.register(function(){
            $('#chargeDeal').datagrid("reload");
        });
    }

    $('#btn_chargeDeal_query').click(function(){
        $('#chargeDeal').datagrid("load",{
            startDate: $("#chargeDeal_startDate").datebox("getValue") + " 00:00:00",
            endDate: $("#chargeDeal_endDate").datebox("getValue") + " 23:59:59"
        });
    });

    $('#chargeDeal').datagrid({
        width : '100%',
        height:438,
        fitColumns:true,
        rownumbers:true,
        nowrap:false,
        scrollbarSize:-1,
        url: "/case/group/load",
        queryParams : {
            startDate: $("#chargeDeal_startDate").datebox("getValue") + " 00:00:00",
            endDate: $("#chargeDeal_endDate").datebox("getValue") + " 23:59:59"
        },
        onBeforeLoad: function(p){
            var bcrdt = new Date(p.startDate.replace(/-/g,"/"));
            var ecrdt = new Date(p.endDate.replace(/-/g,"/"));
            var day = (ecrdt.getTime()-bcrdt.getTime())/(1000*60*60*24);
            if(day > 7){
                window.parent.alertWin("警告","查询时间范围不能超过7天!");
                return false;
            }
            return true;
        },
        columns : [ [
            {
                width:100,
                align:'center',
                field : 'caseid',
                title : '事件编号',
                formatter:function(val,row,index){
                    return "<a href=\"#\" onclick=\"chargeDealEventTab('"+row.caseid+"','"+(row.contactid?row.contactid:"")+"','"+row.contactname+"');\">"+val+"</a>";
                }
            },
            {
                width:100,
                align:'center',
                field : 'contactid',
                title : '客户编号'
            },
            {
                width:100,
                align:'center',
                field : 'contactname',
                title : '客户姓名'
            },
            {
                width:100,
                field : 'orderid',
                align:'center',
                title : '订单编号'
            },
            {
                width:100,
                field : 'orderdate',
                align:'center',
                title : '订购日期'
            },
            {
                width:100,
                align:'center',
                field : 'scode',
                title : '产品'
            },
            {
                width:120,
                field : 'probdsc',
                align:'center',
                title : '问题描述'
            },
            {
                width:120,
                align:'center',
                field : 'reqdsc',
                title : '处理描述'
            },
            {
                width:100,
                align:'center',
                field : 'crusr',
                title : '受理人'
            },
            {
                width:100,
                align:'center',
                field : 'crdt',
                title : '受理时间'
            },
            {
                width:100,
                align:'center',
                field : 'external',
                title : '处理情况'
            },
            {
                width:100,
                align:'center',
                field : 'chargeusr',
                title : '处理人'
            },
            {
                width:100,
                align:'center',
                field : 'chargedt',
                title : '处理时间'
            },
            {
                width:100,
                align:'center',
                field : 'dsc2',
                title : '状态'
            }
        ] ],
        remoteSort : false,
        singleSelect : true,
        pagination : true,
        pageSize: 15,
        pageList : [ 15, 30, 45, 60, 75, 90 ] ,
        onDblClickRow: function(rowIndex, rowData){
            $.post("/case/group/status", { caseId: rowData.caseid }, function(row){
                if(row.success) {
                    chargeDealEventTab(rowData.caseid,rowData.contactid, rowData.contactname);
                } else {
                    window.parent.alertWin("警告",row.errorMsg);
                }
            });
        }
    });

    /*==========================退款信息==============================
    $('#refundInfo').datagrid({
        width : '100%',
        height : 438,
        fitColumns : true,
        rownumbers:true,
        nowrap:false,
        scrollbarSize:-1,
        columns : [ [
            {
                field : 'instID',
                align:'center',
                title : '退款分类'
            },
            {
                field : 'caID',
                align:'center',
                title : '客户编号'
            },
            {
                field : 'caName',
                align:'center',
                title : '客户姓名'
            },
            {
                field : 'taskType',
                align:'center',
                title : '持卡人姓名'
            },
            {
                field : 'sourceTypeDsc',
                align:'center',
                title : '开户行/退款地址'
            },
            {
                field : 'conid',
                align:'center',
                title : '银行卡号/邮政编码'
            },
            {
                field : 'conName',
                align:'center',
                title : '电话号码'
            },
            {
                field : 'ltCreateDate',
                align:'center',
                title : '退款金额',
                width:130
            },{
                field : 'endDate',
                align:'center',
                title : '订单号码',
                width:130
            },
            {
                field : 'appointDate',
                align:'center',
                title : '创建日期',
                width:130
            },
            {
                field : 'ltStatus',
                align:'center',
                title : '创建人'
            },
            {
                field : 'remark',
                align:'center',
                width:100,
                title : '修改日期'
            },
            {
                field : 'ops',
                align:'center',
                title : '修改人',
                width:100
            }
        ] ],
        remoteSort : false,
        singleSelect : true,
        pagination : true,
//        toolbar:[{
//            text:'刷新',
//            iconCls:'icon-reload',
//            handler:function(){$('#table_campaignTask').datagrid('reload')}
//        },'-',{
//            text:'<input type="text"/>'
//        },{
//            text:'按订单号查询',
//            handler:function(){}
//        },'-',{
//            text:'保存并发送短信',
//            handler:function(){}
//        },'-',{
//            text:'取消退款',
//            handler:function(){}
//        }
//        ],
        pageList : [ 5, 10, 15 ],
        pageSize:15
    });
     */
     /*==========================消息通知==============================*/

    /*==========================客服任务==============================*/
    $('#serviceTasks').datagrid({
        width : '100%',
        height:438,
        fitColumns:true,
        rownumbers:true,
        scrollbarSize:-1,
        url:'/callbackTask/queryServiceTasks',
        queryParams:{validOrder:true,rdbusrid:currentUserId},
        columns : [ [
            {field:'taskId',title:'任务编号',width:100,align:'center'},
            {
                width:100,
                align:'center',
                field : 'taskType',
                title : '任务类型',
                formatter:function(value){
                    console.debug("tasktype:"+value);
                    for(var i= 0; i<taskTaskTypes.length; i++){
                        if (taskTaskTypes[i].id.id == value)
                            return taskTaskTypes[i].dsc;
                    }
                    return value;
                }
            },
            {
                width:100,
                align:'center',
                field : 'dbdt',
                title : '分配时间'
            },
            {
                width:100,
                align:'center',
                field : 'dbusrid',
                title : '分配座席'
            },
            {
                width:100,
                field : 'flag',
                align:'center',
                title : '处理状态',
                formatter:function(value){
                    if(value=='1')
                        return '未处理';
                    else if(value=='2')
                        return '处理中';
                    else
                        return value;
                }
            },
            {
                width:100,
                field : 'crdt',
                align:'center',
                title : '生成时间'
            },
            {
                width:100,
                align:'center',
                field : 'orderCrUsr',
                title : '订单座席'
            },
            {
                width:100,
                field :'contactId',
                align:'center',
                title : '客户编号'
            },
            {
                width:100,
                align:'center',
                field : 'contactName',
                title : '客户名称'
            },
            {
                width:100,
                align:'center',
                field : 'note',
                title : '备注'
            }
        ] ],
        onDblClickRow:function(index,row){
            if(row.taskType=='5')
            {
                //
                var checkCallbackObj={taskId:row.taskId}
                $.post("/callbackTask/checkTaskOrder", checkCallbackObj, function(data) {
                    if (data.succ=="succ") {
                        showWindow({
                            title:'客服外呼任务',
                            href:'/callbackTask/addressConfirm/open?sourceType=1&taskId='+row.taskId,
                            width: 500
                        },"addressConfirmDialog");
                    }else {
                        window.parent.alertWin('系统提示', data.msg);
                        $('#serviceTasks').datagrid('reload');
                    }
                }, 'json');
            }
        },
        remoteSort : false,
        singleSelect : true,
        pagination : true,
        pageList : [ 5, 10, 15 ],
        pageSize:15
    });
	function mytask_init() {

        init_complaintsSet();

        $("#startDate").datebox({
            required: "true",
            missingMessage: "必填项",
            formatter: function (date) {
                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                var d = date.getDate();
                return y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);
            },
            validType:'querydatevalidator'
        });
        $("#endDate").datebox({
            required: "true",
            missingMessage: "必填项",
            formatter: function (date) {
                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                var d = date.getDate();
                return y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);
            },
            validType:'querydatevalidator'
        });

        $('#mytask_tabs').tabs({
            onSelect:function(title){
                if(title == "投诉处理"){
                    init_complaintsSet();
                }
            }
        });
		$("#startDate4Audit").datebox({
			required: "true",
			missingMessage: "必填项",
			formatter: function (date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);
			}
		});
		$("#endDate4Audit").datebox({
			required: "true",
			missingMessage: "必填项",
			formatter: function (date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);
			}
		});

        $('#startDate').datebox('setValue', new Date(new Date()-15*86400000).format('yyyy-MM-dd'));
        $('#endDate').datebox('setValue', new Date().format('yyyy-MM-dd'));

		$('#startDate4Audit').datebox('setValue', new Date(new Date()-15*86400000).format('yyyy-MM-dd'));
		$('#endDate4Audit').datebox('setValue', new Date().format('yyyy-MM-dd'));


	}

	// 每10分钟刷新一次
	setInterval("reloadCampaignTask()", 1000 * 60 * 10);

    /*==========================任务分配历史==============================*/
   /* $('#callback-log-grid').datagrid({
        width : '100%',
        height : 400,
        fitColumns:true,
        pagination:true,
        rownumbers:true,
        pageSize: 10,
        pageList: [5,10,15],
        columns : [ [
            {
                field : 'campaignId',
                title : '营销计划',
                align:'center',
                width:180
            },
            {
                field : 'batchCode',
                title : '客户群批次号',
                align:'center',
                width:180
            },
            {
                field : 'assignTime',
                title : '分配时间',
                align:'center',
                width:180,
                formatter:function(value) {
                    return new Date(value).format('yyyy-MM-dd hh:mm:ss');
                }
            }
            ,
            {
                field : 'status',
                title : '分配状态',
                align:'center',
                width:180
            }
        ]],
        remoteSort : false,
        singleSelect : true,
        rownumbers : false,
        onLoadSuccess:function(data) {
            $('#log_allotNum').html(data.total);
        }
    });

    var logPager = $('#callback-log-grid').datagrid('getPager');
    $(logPager).pagination({
//        pageSize: 10,
//        pageList: [5,10,15],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onBeforeRefresh:function(){
            $(this).pagination('loading');
            $(this).pagination('loaded');
        }
    });

    $('#lnk-callback-log-submit').click(function(){
        $('#callback-log-grid').datagrid({
            url : 'findContactAssignHist',
            queryParams: {
                campaignId:$('#log-campaign').combobox('getValue'),
                batchCode:$('#log-batchCode').val(),
                status:$('#log-status').combobox('getValue'),
                assignStartTime:$('#log-startdate').datetimebox('getValue'),
                assignEndTime:$('#log-enddate').datetimebox('getValue')
            }
        });
        $('#d_log-campaign').val($('#log-campaign').combobox('getValue'));
        $('#d_log-batchCode').val($('#log-batchCode').val());
        $('#d_log-status').val($('#log-status').combobox('getValue'));
        $('#d_log-startdate').val($('#log-startdate').datetimebox('getValue'));
        $('#d_log-enddate').val($('#log-enddate').datetimebox('getValue'));
    });

    $('#lnk-callback-log-reset').click(function(){
        $('#log-campaign').combobox('setValue', ''),
        $('#log-batchCode').val(''),
        $('#log-status').combobox('setValue',''),
        $('#log-startdate').datetimebox('setValue',''),
        $('#log-enddate').datetimebox('setValue','')
    });

    $('#lnk-export-log').click(function(){
        $('#downloadForm').submit();
    });*/
});

function reloadCampaignTask() {
	$("#table_campaignTask").datagrid("reload");
}

function switchInnerTab(index) {
	$('#mytask_tabs').tabs('select',index);
}

function chargeDealEventTab(caseid,contactid,contactname){
    if(contactid){
        window.parent.addTab('E'+contactid, contactname + "-事件管理", false, "/case/index?module=supervisor&caseId="+caseid+"&contactId="+contactid+"&contactName="+ encodeURIComponent(contactname),"",true);
    } else {
        window.parent.alertWin("警告","客户编号不能为空!");
    }
}

function assignHist(jobId) {
	$.ajax({
		//url : ctx + "/task/assignHist4PerfTest?jobId=" + jobId,
		url : ctx + "/task/assignHist?jobId=" + jobId,
		success : function(data) {

			if(data.isSuc) {
				if(data.tip != null && data.tip != "") {
					$('#p_assign_tip').text(data.tip);
				}
				$('#span_contactStartDate').text(new Date(data.startDate).format('yyyy-MM-dd hh:mm:ss'));
				$('#span_contactEndDate').text(new Date(data.endDate).format('yyyy-MM-dd hh:mm:ss'));
				$('#a_taskId').text(data.instId);
				$('#span_contactId').text(data.contactId);
				$('#window_campaignTasktip').show();
				$('#window_campaignTasktip').window({
					title:'取数',
					top:200,
//                    width: 500,
//                    height: 200,
					shadow:false,
					collapsible:false,
					minimizable:false,
					maximizable:false,
					resizable:false,
					closed:true,
					modal:true,
                    draggable:false,
					onOpen : function() {
						if(data.instId != null && data.instId!='') {
							$('#a_taskId').bind("click",function(){
								$('#window_campaignTasktip').hide();
								$('#window_campaignTasktip').window('close');
								var id = data.contactId;

								var url;
								var paras='?instId='+data.instId;
								if(data.isPotential=='0') {
									url = ctx+'/contact/1/'+data.contactId;
								} else {
									url = ctx+'/contact/2/'+data.contactId;
								}
								url+=paras;
								execTask(data.contactId,'我的客户',url,data.campaignId,data.instId);
							});
						}
					},
					onBeforeClose: function(){
						$('#p_assign_tip').text("取到一条数据");
						$('#window_campaignTasktip').hide();
						$('#a_taskId').unbind("click");
					}
				});
				$('#window_campaignTasktip').window('open');
				$('#table_jobRelationex').datagrid('reload', {});
			} else {
				if(data.tip != null && data.tip != "") {
                    window.parent.alertWin('系统提示',data.tip);
				} else {
                    window.parent.alertWin('系统提示','取数失败');
				}
			}

		},
		error : function() {
            window.parent.alertWin('系统提示','取数失败');
			$('#window_campaignTasktip').window('close');
		}
	});
}

/**
 *
 * @param sysmessage see com.chinadrtv.erp.sales.dto.SysMessageDto
 */
function queryFromSysmessage(sysmessage)
{
    $("#mytask_tabs").tabs("select","修改审批");
    if(sysmessage.orgSourceType=='2')//cancel order
    {
        $("#auditTaskTypes").combobox('setValue','2');
    }
    else if(sysmessage.orgSourceType=='3') //modify contact
    {
        $("#auditTaskTypes").combobox('setValue','1');
    }
    else if(sysmessage.orgSourceType=='4') //modify contact
    {
        $("#auditTaskTypes").combobox('setValue','0');
    }
    else if(sysmessage.orgSourceType=='8') //add contact
    {
        $("#auditTaskTypes").combobox('setValue','4');
    }
    else if(sysmessage.orgSourceType=='10') //add order
    {
        $("#auditTaskTypes").combobox('setValue','3');
    }

    //主管不需要
    //$("#crUser4Audit").val(sysmessage.receiverId);
    $("#btn_auditTask").click();
}


function dateFormatter(val){
    if(null!=val){
        return new Date(val).format('yyyy-MM-dd hh:mm:ss');
    }
}

function dateFormatternoTime(val){
    if(null!=val){
        return new Date(val).format('yyyy-MM-dd');
    }
}

function timeFormatter(val){
    if(null!=val){
        return new Date(val).format('hh:mm:ss');
    }
}



function priorityFormatter(val){
    var _label = '';
    if(val==1){
        _label = '紧急';
    }else if(val==2){
        _label = '一般';
    }else if(val==3){
        _label = '可以等待';
    }else if(val==4){
        _label = '特别紧急';
    }
    return _label;
}

function complainStatusFormatter(val) {
    if (val == 1) return "已受理";
    else return "未受理";
}

function getTimeByDateStr(dateStr){  
    var year = parseInt(dateStr.substring(0,4));  
    var month = parseInt(dateStr.substring(5,7),10)-1;  
    var day = parseInt(dateStr.substring(8,10),10);  
    return new Date(year, month, day).getTime();  
}  

function init_complaintsSet(){
    var stdt=  new Date().setDate(new Date().getDate()-6);
    //初始化查询字段
    $("#complaintsSet_status").combobox('setValue','0');
    $("#complaintsSet_startDate").datebox('setValue', new Date(stdt).format('yyyy-MM-dd'));
    $("#complaintsSet_endDate").datebox('setValue', new Date().format('yyyy-MM-dd'));
    //初始化complaintSet
    $("#complaintsSet").datagrid({
        width:'100%',
        height:410,
        nowrap: false,
        method: 'POST',
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        scrollbarSize:-1,
        url:'/complain/complainAll',
        columns:[[
            {title:'客户姓名',field:'contactName',width:30},
            {title:'投诉内容',field:'content',width:97},

            {title:'投诉日期',field:'crdt',width:30,
                formatter:dateFormatternoTime},
            {title:'投诉时间',field:'crtm',width:30,
                formatter:timeFormatter},
            {title:'创建坐席',field:'crusr',width:30},
            {title:'订单编号',field:'orderId',width:40},
            {title:'产品',field:'prod',width:40},
            {title:'优先级',field:'priovity',width:30,
                formatter:priorityFormatter},
            {title:'状态',field:'status',width:30,
                formatter:complainStatusFormatter},
            {title:'事件ID',field:'caseId',width:25},
            {title:'处理人',field:'opusr',width:22},

            {title:'客户编号',field:'contactId',hidden:true}
        ]],
        remoteSort:false,
        singleSelect:false,
        pagination:true,
        queryParams: {
            orderId:$("#complaintsSet_orderId").val(),
            customerName:$("#complaintsSet_name").val(),
            status:$("#complaintsSet_status").combobox('getValue'),
            priority:$("#complaintsSet_priority").combobox('getValue'),
            sdt:$("#complaintsSet_startDate").datebox('getValue'),
            edt:$("#complaintsSet_endDate").datebox('getValue')

        },
        onDblClickRow: function(rowIndex, rowData){
            //弹出添加事件页面.
           // alert(rowData);
            var v_crtm =new Date(rowData.crtm).format('hh:mm:ss');
            var v_crdt = new Date(rowData.crdt).format('yyyy-MM-dd');
           if(rowData.status == 1){
               window.parent.alertWin('系统提示', "此条数据已处理!");
           }else{
               console.info("crdt:"+v_crdt);
               console.info("crdt:"+v_crtm);
                $.ajax({
                   type:'post',//可选get
                   url:'/complain/process',//这里是接收数据的PHP程序
                   data: "contactId="+rowData.contactId+"&crdt="+v_crdt+"&crtm="+v_crtm,
                   dataType:'text',//服务器返回的数据类型 可选XML ,Json jsonp script html text等
                   async: false,
                   success:function(date){
                       date =eval('('+date+')');
                       if(date.result){
                           window.parent.alertWin('系统提示',date.msg);
                       }else{
                           //window.parent.gotoInfoCustomer(rowData.contactId,"1",rowData.contactName,true);
                           window.parent.addTab('E'+rowData.contactId,'事件管理', false, "/case/index?module=toCase&callback="+ encodeURIComponent("/complain/callback?contactId="+rowData.contactId+"&crdt="+v_crdt+"&crtm="+v_crtm)+"&contactId="+rowData.contactId+"&tocaseCrdt="+v_crdt+"&tocaseCrtm="+v_crtm,"",true);
                       }

                   },
                   error:function(){

                   }
               });
           }

        }

    });




}


//查询
function complaintsSet_query(){
    var vsdt = new Date($("#complaintsSet_startDate").datebox('getValue'));
    var vedt = new Date($("#complaintsSet_endDate").datebox('getValue'));
    var v2dt =(vedt-vsdt)/(24*60*60*1000);
    if( 0 <= v2dt && v2dt <= 6 ){
        $("#complaintsSet").datagrid('load', {
            orderId:$("#complaintsSet_orderId").val(),
            customerName:$("#complaintsSet_name").val(),
            status:$("#complaintsSet_status").combobox('getValue'),
            priority:$("#complaintsSet_priority").combobox('getValue'),
            sdt:$("#complaintsSet_startDate").datebox('getValue'),
            edt:$("#complaintsSet_endDate").datebox('getValue')

        });
    }else{
        window.parent.alertWin('系统提示',"时间范围不超过7天");
    }
}

function complaintsSet_clear(){
    $("#complaintsSet_orderId").val("");
    $("#complaintsSet_name").val("");
    $("#complaintsSet_status").combobox('setValue','');
    $("#complaintsSet_priority").combobox('setValue','');
    $("#complaintsSet_startDate").datebox('setValue','');
    $("#complaintsSet_endDate").datebox('setValue','');
}

$.extend($.fn.validatebox.defaults.rules,{
    querydatevalidator:{
        validator: function(value)
        {
            var date = $.fn.datebox.defaults.parser(value);
            var s = $.fn.datebox.defaults.formatter(date);
            return s==value;
        },message: '日期无效'
    }
});