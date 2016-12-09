function _handler(){
       $('#table_callbackTask').datagrid('resize',{width:'100%'});
}

$(function(){
	init();
    $('.dial').hover(function(){
          $(this).find('.marquee_hover').show();
    },function(){
        $(this).find('.marquee_hover').hide();
    });
	$('#table_callbackTask').datagrid({
		width : '100%',
		height : 438,
		fitColumns : true,
		idField : "taskId",
//        rownumbers:true,
        nowrap:false,
        scrollbarSize:-1,
        url : '/callbackTask/query',
        rownumbers : false,
        singleSelect : false,
        checkOnSelect: true,
        selectOnCheck : true,
		queryParams : {
			'crStartDate' : $('#crStartDate').datebox('getValue'),
			'crEndDate' : $('#crEndDate').datebox('getValue'),
			'alloStartDate' : $('#alloStartDate').datebox('getValue'),
			'alloEndDate' : $('#alloEndDate').datebox('getValue'),
			'taskStatus' : $('#taskStatus').combobox('getValue')
		},
		columns : [ [
		{
			field:'taskId',
			checkbox:true,
			width:20
		},
 		{
			field : 'taskTypeDsc',
			width:70,
			title : '任务类型'
		}, 
		{
			field : 'contactId',
			title : '客户编号',
			formatter: function(val, row){
				if(val != null) {
					var id = val;
					var url;
					var paras='';
					url = ctx+'/contact/1/'+row.contactId;
					url+=paras;
					//window.parent.addTab(id, name, false, url);
					url = '<a class="link" href="javascript:void(0)" id=\''+id+'\' onclick="window.parent.addTab(\''+id.valueOf()+'\', \'我的客户\','+false+',\''+url+'\')">'+val+'</a>';
					return url;
				}
			}
		}, 
		{
			field : 'contactName',
			title : '客户姓名'
		}, 
		{
			field : 'orderCrUsr',
			title : '订单座席'
		}, 
		{
			field : 'crdt',
			width:100,
			formatter : dateFormatter,
			title : '创建时间'
		}, 
		{
			field : 'dbdt',
			width:100,
			formatter : dateFormatter,
			title : '分配时间'
		}, 
		{
			field : 'rdbusrid',
			title : '被分配座席'
		},
		{
			field : 'flagDsc',
            align:'center',
			title : '处理状态'
		},
		{
			field : 'note',
			title : '备注',
			width:130
		}
		] ],
		remoteSort : false,
		pagination : true,
        pageList : [ 5, 10, 15 ],
        pageSize:15
	});
	
	var callbackTaskPager = $('#table_callbackTask').datagrid('getPager');
	$(callbackTaskPager).pagination({
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function() {
			$(this).pagination('loading');
			$(this).pagination('loaded');
		}
	});
	
	//查询
	$('#btn_callbackTask').click(function(){
        if(!$("#allocationTaskForm").form("validate"))
        {
        	window.parent.alertWin('系统提示', "请检查表单项");
            console.debug("invalidate data!!!");
            return;
        }
		var crStartTime = getTimeByDateStr($('#crStartDate').datebox('getValue'));
        var crEndTime = getTimeByDateStr($('#crEndDate').datebox('getValue'));
        if(crEndTime - crStartTime > 15*86400000) {
        	window.parent.alertWin('系统提示', "生成时间查询间隔不能超过15天");
        	return;
        }
        
		var alloStartTime = getTimeByDateStr($('#alloStartDate').datebox('getValue'));
        var alloEndTime = getTimeByDateStr($('#alloEndDate').datebox('getValue'));
        if(alloEndTime - alloStartTime > 15*86400000) {
        	window.parent.alertWin('系统提示', "分配时间查询间隔不能超过15天");
        	return;
        }
        var  alloStatus = $('#taskStatus').combobox('getValue');
        if(alloStatus != 0 && alloStatus != 1) {
        	$('#table_callbackTask').datagrid('hideColumn','taskId');
        } else {
        	$('#table_callbackTask').datagrid('showColumn','taskId');
        }
		$('#table_callbackTask').datagrid('reload',{
			'crStartDate' : $('#crStartDate').datebox('getValue'),
			'crEndDate' : $('#crEndDate').datebox('getValue'),
			'alloStartDate' : $('#alloStartDate').datebox('getValue'),
			'alloEndDate' : $('#alloEndDate').datebox('getValue'),
			'rdbusrid' : $("#rdbusrid").val(),
			'orderCrUsr' : $("#orderCrUsr").val(),
			'taskType' : $('#taskType').combobox('getValue'),
			'taskStatus' : $('#taskStatus').combobox('getValue')
		});
	});
	
	//清空
	$('#btn_callbackTaskClear').click(function(){
		$('#allocationTaskForm')[0].reset();
		$('#taskStatus').combobox('setValue','');
		$('#taskType').combobox('setValue','');
		$('#crStartDate').datebox('setValue', new Date(new Date()-15*86400000).format('yyyy-MM-dd'));
		$('#crEndDate').datebox('setValue', new Date().format('yyyy-MM-dd'));
		$('#alloStartDate').datebox('setValue', new Date(new Date()-15*86400000).format('yyyy-MM-dd'));
		$('#alloEndDate').datebox('setValue', new Date().format('yyyy-MM-dd'));
	});
	
	function dateFormatter(val, row){
		if(null!=val){
			return new Date(val).format('yyyy-MM-dd hh:mm:ss');
		}
	}
	
	function getTimeByDateStr(dateStr){  
	    var year = parseInt(dateStr.substring(0,4));  
	    var month = parseInt(dateStr.substring(5,7),10)-1;  
	    var day = parseInt(dateStr.substring(8,10),10);  
	    return new Date(year, month, day).getTime();  
	}  
	
	function init() {
		$("#crStartDate").datebox({
			required: "true",
			missingMessage: "必填项",
			formatter: function (date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);
			}
		});
		$("#crEndDate").datebox({
			required: "true",
			missingMessage: "必填项",
			formatter: function (date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);
			}
		});
		$("#alloStartDate").datebox({
			//required: "false",
			//missingMessage: "必填项",
/*			formatter: function (date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);
			}*/
		});
		$("#alloEndDate").datebox({
			//required: "false",
			//missingMessage: "必填项",
/*			formatter: function (date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);
			}*/
		});
		$('#crStartDate').datebox('setValue', new Date(new Date()-15*86400000).format('yyyy-MM-dd'));
		$('#crEndDate').datebox('setValue', new Date().format('yyyy-MM-dd'));
		//$('#alloStartDate').datebox('setValue', new Date(new Date()-15*86400000).format('yyyy-MM-dd'));
		//$('#alloEndDate').datebox('setValue', new Date().format('yyyy-MM-dd'));
		document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];     
             if(e && e.keyCode==13){
            	 if(document.activeElement.id=='input_agentId') {
                     $('#btn_addAgent').click();
            	 }

            }
        }; 
	}
	
	
	$('#table_agentList').datagrid({
		width : '100%',
		height : 438,
		fitColumns : true,
        rownumbers:true,
        nowrap:false,
        scrollbarSize:-1,
	columns : [ [
 		{
			field : 'workGrp',
			title : '工作组',
			width:130
		}, 
		{
			field : 'agentId',
			title : '座席工号',
			width:130
		}, 
		{
			field : 'name',
			title : '姓名',
			width:130
			
		}
		] ],
		remoteSort : false,
		pagination : true,
        pageList : [ 5, 10, 15 ],
        pageSize:15
	});
	var agentId, agentName, agentWorkGrp;
	//查询座席
	$('#btn_queryAgent').click(function(){
		agentId = $("#input_agentId").val();
		agentName="";
		agentWorkGrp="";
		if(agentId != "" && agentId != null) {
			$.get(
				"/callbackTask/queryAgentName",
				{agentId : agentId},
				function(data) {
					if(data.name != null && data.name != "") {
						agentName = data.name;
					} else if(data.displayName != null && data.displayName != "") {
						agentName = data.displayName;
					}
					agentWorkGrp = data.workGrp;
					if(agentName != null && agentName != "") {
						$("#label_userinfo").html(agentId+"【 "+agentName+" 】");
					} else {
						$("#label_userinfo").html(agentId+" 不存在 ");
					}
				}
			);
		}
	});
	
	//添加座席
	$('#btn_addAgent').click(function(){
		agentId = $("#input_agentId").val();
		agentName="";
		agentWorkGrp="";
		if(agentId != null && agentId != "") {
			var userDatas = $('#table_agentList').datagrid('getData');
			for(var i = 0; i < userDatas.total; i++) {
				if(userDatas.rows[i].agentId == agentId)
					return;
			}

			if(agentId != "" && agentId != null) {
				$.get(
					"/callbackTask/queryAgentName",
					{agentId : agentId},
					function(data) {
						if(data.name != null && data.name != "") {
							agentName = data.name;
						} else if(data.displayName != null && data.displayName != "") {
							agentName = data.displayName;
						}
						agentWorkGrp = data.workGrp;
						if(agentName != null && agentName != "") {
							$("#label_userinfo").html(agentId+"【 "+agentName+" 】");
							$('#table_agentList').datagrid('appendRow', {
								workGrp: agentWorkGrp,
								agentId: agentId,
								name: agentName
							}
						);
						} else {
							$("#label_userinfo").html(agentId+" 不存在 ");
						}
					}
				);
			}

		}
	});
	
	//删除座席
	$('#btn_removeAgent').click(function(){
		agentId = $("#input_agentId").val();
		if(agentId != null && agentId != "") {
			var userDatas = $('#table_agentList').datagrid('getData');
			var indexDel = -1;
			for(var i = 0; i < userDatas.total; i++) {
				if(userDatas.rows[i].agentId == agentId) {
					indexDel=i;
					break;
				}
			}
			if(indexDel > -1) {
				$('#table_agentList').datagrid('deleteRow', indexDel
			);
			}
		}
	});
	
	//分配任务
	$('#btn_task_dist').click(function(){
		//获取选中的任务ID
		var allTaskIdsString="", dbUserIdsString="";
		
		var allTasks = $('#table_callbackTask').datagrid('getSelections');
		for(var i = 0; i < allTasks.length; i++) {
			var taskId = allTasks[i].taskId;
			var flag = allTasks[i].flag;
			if(taskId != null && taskId !="" && (flag=="0" || flag=="1")) {
				if(i != allTasks.length -1)
					allTaskIdsString+=(taskId+",");
				else 
					allTaskIdsString+=(taskId);
			}
		}

		//获取所有人的ID
		var dbUserIds = $('#table_agentList').datagrid('getData');
		for(var i = 0; i < dbUserIds.total; i++) {
			var dbUserId = dbUserIds.rows[i].agentId;
			if(dbUserId != null && dbUserId !="") {
				if(i != dbUserIds.total -1)
					dbUserIdsString+=(dbUserId+",");
				else 
					dbUserIdsString+=(dbUserId);
			}
		}
		
		var distData = {
		    	"allTaskIds" : allTaskIdsString,
		    	"dbUserIds" : dbUserIdsString
		};
		var distData = JSON.stringify(distData);
		$.ajax({
		    url: '/callbackTask/distCallbackTask',
		    data: distData,
		    type:'post',
		    cache:false,
		    dataType:'json',
		    contentType: "application/json; charset=utf-8",
		    success:function(data) {
	            window.parent.alertWin('系统提示',data);
		     },    
		     error : function() {
	             window.parent.alertWin('系统提示',"操作失败，请重新再试。");

	         }
		});
	});
	
});

Date.prototype.format = function(format){
	var o = {
	"M+" : this.getMonth()+1, // month
	"d+" : this.getDate(),    // day
	"h+" : this.getHours(),   // hour
 	"m+" : this.getMinutes(), // minute
	"s+" : this.getSeconds(), // second
 	"q+" : Math.floor((this.getMonth()+3)/3),  // quarter
 	"S" : this.getMilliseconds() // millisecond
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
