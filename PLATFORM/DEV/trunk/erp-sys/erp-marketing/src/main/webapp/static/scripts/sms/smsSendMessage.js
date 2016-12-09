 var popupStatus = 0;
   var  $place = function(selector){//目标居中,目标应为绝对定位
	    var $t=( $("#addSendMessage").height()-$(selector).height())/2;
	    var $l=( $("#addSendMessage").width()-$(selector).width())/2;    
    $(selector).css({"left":$l+"px","top":$t+"px"});
  }  
$(function(){
 $.extend($.fn.datagrid.defaults.editors, {    
        timespinner: {    
            init: function(container, options){    
                var input = $('<input type="text">').appendTo(container);    
                return input.timespinner(options);    
            },    
            destroy: function(target){    
                $(target).timespinner('destroy');    
            },    
            getValue: function(target){    
                return $(target).timespinner('getValue');    
            },    
            setValue: function(target, value){    
                $(target).timespinner('setValue',value);    
            },    
            resize: function(target, width){    
                $(target).timespinner('resize',width);    
            }    
        }    
    }); 
	$('#checkBoxs').click(function () {
		if ($(this).attr("checked")) {
			$('#show').show();
		}else {
			$('#show').hide();
		}
	});
	$('#template').combogrid({
		panelWidth:200,
		idField:'id',
		textField:'name',
		url:'smslist',
		panelWidth:450,
		panelHeight:320,
		pagination:true,
		rownumbers:true,
		required:true,
		editable:false,
		 mode: 'remote', 
		columns:[[
			{field:'id',title:'短信编号',width:220},
			{field:'name',title:'短信内容',width:200}
		]],
		 onSelect:function(index,row){
			 $("#templatesContent").attr("value",row.content);
			 $("#templateSize")[0].innerHTML="短信字数"+row.content.length;
			 
			 $('#varGrid').datagrid({
				 url:'getStaticVar',
					queryParams:{
						content : row.content
					},
					onClickRow:function(rowIndex){
							$('#varGrid').datagrid('beginEdit', rowIndex);
					},onLoadSuccess:function(data){
						for(var i=0;i<data.rows.length;i++){
							$('#varGrid').datagrid('beginEdit', i);														
						}
					}
			 });
		}
	});
	
	$('#smsList').datagrid({
		title:'',
		iconCls:'icon-save',
		width:'100%',
		height:435,
		autoRowHeight: false,
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,
		fitColumns:true,
		url:'smsSendList',
		queryParams:{
			batchId : $('#batchId').val(),
			starttime : $("#starttime").val(),
			endtime : $("#endtime").val(),
			groupCode : $("#groupCode").val(),
			smsName:$("#smsName").val(),
			groupName:$("#groupName").val(),
			creator:$("#creator").val()			
		},
		remoteSort: false,
		fitColumns:true,		
		columns : [ [
						{	
							field : 'batchId',
							title : '发送批次号',
							rowspan:1,
							width : 100
						},{
							field : 'createTime',
							title : '创建时间',
							rowspan:1,
							width : 80
						},{
							field : 'groupCode',
							title : '客户群编号',
							rowspan:1,
							width : 80
						},{
							field : 'groupName',
							title : '客户群名称',
							rowspan:1,
							width : 80
						},{
							field : 'smsName',
							title : '短信名称',
							rowspan:1,
							width : 80
						},{
							field : 'creator',
							title : '发送用户',
							rowspan:1,
							width : 80
						},
						{
							field : 'smsstopStatus',
							rowspan:0,
							hidden:true
						},
						{
							field : 'sendStatus',
							title : '任务状态',
							rowspan:1,
							width : 80,
							formatter : function(value, rowData, rowIndex) {  
					              if (rowData.sendStatus=='0') {  
			                            return  "创建失败";  
			                        }  
			                        if (rowData.sendStatus=='1') {  
			                            return  "正在创建";  
			                        } 
			                        if (rowData.sendStatus=='2') {  
			                            return  "创建成功";
			                        } 
			                        if (rowData.sendStatus=='3') {  
			                            return  "任务已停止"; 
			                        } 
			                        if (rowData.sendStatus=='4') {  
			                            return  "上传ftp失败"; 
			                        }
			                        if (rowData.sendStatus=='5') {  
			                            return  "任务停止失败"; 
			                        } 
			                        if (rowData.sendStatus=='6') {  
			                            return  "连接大汉三通失败"; 
			                        }
			                        if (rowData.sendStatus=='7') {  
			                            return  "短信已发送结束无法停止"; 
			                        } 
			                        if (rowData.sendStatus=='8') {  
			                            return  "短信还未发送到大汉平台，无法停止"; 
			                        } 
			                 }
						}
						]],
						pagination:true,
						onDblClickRow:function(index, row){							
							doubleClick(row.batchId);							
						}
	});
	var p = $('#smsList').datagrid('getPager');
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
	
	// 查询按钮
	$("#querySmsAnswerBtn").click(function() {
		if ($('#starttime').datetimebox('getValue')>$("#endtime").datetimebox('getValue')) {
			alerts('起始时间不能晚于结束时间');
			return false;
		}
		$('#smsList').datagrid('load', {
			batchId : $('#batchId').val(),
			starttime :$('#starttime').datetimebox('getValue'),
			endtime : $("#endtime").datetimebox('getValue'),
			groupCode : $("#groupCode").val(),
			smsName:$("#smsName").val(),
			groupName:$("#groupName").val(),
			creator:$("#creator").val()
		});
	});
	var lastIndex;
	$('#timeList').datagrid({
		width:520,
		height:120,
		striped : true,
		border : true,
		collapsible : false,
		singleSelect :true,
		toolbar:[{
			text:'新增',
			iconCls:'icon-add',
			handler:function(){
				$('#timeList').datagrid('endEdit', lastIndex);
					$('#timeList').datagrid('appendRow',{
						starttime:'',
						endtime:'',
						maxsend:''
					});
					var lastIndex = $('#timeList').datagrid('getRows').length-1;
					$('#timeList').datagrid('beginEdit', lastIndex);
				}
		},'-',{
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){
				var row = $('#timeList').datagrid('getSelected');
				if (row){
					var index = $('#timeList').datagrid('getRowIndex', row);
					$('#timeList').datagrid('deleteRow', index);
				}
			}
		},'-',{
			text:'接受',
			iconCls:'icon-save',
			handler:function(){
				$('#timeList').datagrid('acceptChanges');
			}
		}],
		columns : [ [
						{
							field : 'starttime',
							title : '开始时间',
							rowspan:1,
							width : 133,
							editor:'timespinner'
						},{
							field : 'endtime',
							title : '结束时间',
							rowspan:1,
							width : 133,
							editor:'timespinner'
							
						},{
							field : 'maxsend',
							title : '最大发送量',
							rowspan:1,
							width : 133,
							editor:'numberbox'
						}]],
						onClickRow:function(rowIndex){
							if (lastIndex != rowIndex){
								$('#timeList').datagrid('endEdit', lastIndex);
								$('#timeList').datagrid('beginEdit', rowIndex);
							}
							lastIndex = rowIndex;
						}
	});
	
});



function sumb () {
	
	var validForm = $('#ff').form('validate');
	if(!validForm){
		return false;
	}
	
	var rows = $("#timeList").datagrid("getRows");
	var varGridRows = $("#varGrid").datagrid("getRows");
	var varNames = "";
	var varValues = "";
	var maxsend = "";
	var timescope = "";
	var starttime = "";
	var endtime = "";
	var s = $('#customers').combogrid('grid').datagrid('getSelected');
	var ts = $('#template').combogrid('grid').datagrid('getSelected');
	var template = $('#templatesContent').val()+"="+ts.id+"="+ts.name+"="+ts.dynamicTemplate;
	var customers = s.groupCode+"="+s.groupName;
	var priority = $("#priority").val();
	var isreply = "Y";
	var realtime = "Y";
	var allowChannel = "Y";
	var blackListFilter = "off";
	var mainNum = "off";
	var checkBoxs = "off";
	var cmcc = $("#cmcc").val();
	var cucc = $("#cucc").val();
	var cha = $("#cha").val();
	var stime = $("#stime").datebox("getValue"); 
	var etime = $("#etime").datebox("getValue"); 
	var myDate = new Date();
	var times =  myDate.format("yyyy-MM-dd hh:mm:ss");
	if (etime<times) {
		alerts('结束时间不能晚于当前时间');
		 return false;
	}
	if (stime>etime) {
		alerts('起始时间不能晚于结束时间');
		return false;
	}
	/**
	if ($("#isreply").attr("checked")=="checked") {
		isreply = "Y";
	}else {
		isreply = "N";
	}
	if ($("#realtime").attr("checked")=="checked") {
		realtime = "Y";
	}else {
		realtime = "N";
	}
	if ($("#allowChannel").attr("checked")=="checked") {
		allowChannel = "Y";
	}else {
		allowChannel = "N";
	}**/
	if ($("#blackListFilter").attr("checked")=="checked") {
		blackListFilter = "on";
	}
	if ($("#mainNum").attr("checked")=="checked") {
		mainNum = "on";
	}
	if ($("#checkBoxs").attr("checked")=="checked") {
		checkBoxs = "on";
	}
	for(var i=0;i<rows.length;i++)
	{	
		var starttimes = stime.split('-');
		var endtimes = etime.split('-');
		var starttimeTemp =  starttimes[2].substring(2);
		var endtimesTemp =   endtimes[2].substring(2);
		var istart=parseInt(starttimeTemp.split(":")[0]) * 100 + parseInt(starttimeTemp.split(":")[1]);
		var mstart=parseInt(starttime.split(":")[0]) * 100 + parseInt(starttime.split(":")[1]);
		var iendt=parseInt(endtimesTemp.split(":")[0]) * 100 + parseInt(endtimesTemp.split(":")[1]);
		var mendt=parseInt(endtime.split(":")[0]) * 100 + parseInt(endtime.split(":")[1]);
		/***
		 * 
		 
		 if(mstart<istart)
	     {
			 alerts("区段开始时间不能早于开始时间");
			 return false;
	     }
		 if(iendt<mstart)
	     {
			 alerts("区段开始时间不能晚于结束时间");
			 return false;
	     }
	     	 if(mendt>iendt)
	     {
			 alerts("区段结束时间不能晚于结束时间");
			 return false;
	     }
		 if(mendt<istart)
	     {
			 alerts("区段结束时间不能早于开始时间");
			 return false;
	     }
	     */
		 if(mendt<mstart)
	     {
			 alerts("区段结束时间不能晚于区段开始时间");
			 return false;
	     }
		maxsend = maxsend+rows[i].maxsend+",";
		if (maxsend==null||maxsend == '') {
			alerts("发送量不能为空");
			return false;
		}
		starttime = starttime+rows[i].starttime+",";
		endtime = endtime+rows[i].endtime+",";
	}
	$('#timeList').datagrid('acceptChanges');

	var reg = /^\d{1,10}$/g;
	var temp="";
	for(var i=0;i<varGridRows.length;i++)
	{
		varNames = varNames+varGridRows[i].name+",";
		var ed = $('#varGrid').datagrid('getEditor', {index:i,field:'value'});
		temp = $(ed.target).val();
		if (varGridRows[i].name=="{400}") {
			if (!reg.exec(temp)||temp.length!=10){
				alerts("只能填写10位数字");
				return ;
			}			
		}
		varValues = varValues+$(ed.target).val()+",";
	}
    $place ($("#showdiv"));    
    $(window).resize(function(){$place ($("showdiv"));}) //调整浏览器大小    
     loadPopup();  
	$.post("save", {
		"maxsend" : maxsend,
		"starttime":starttime,
		"endtime":endtime,
		"customers":customers,
		"template":template,
		"priority":priority,			
		"cmcc":cmcc,
		"cucc":cucc,
		"cha":cha,
		"stime":stime,
		"etime":etime,
		"isreply":isreply,
		"realtime":realtime,
		"allowChannel":allowChannel,
		"blackListFilter":blackListFilter,
		"mainNum":mainNum,
		"checkBoxs":checkBoxs,
		"varNames":varNames,
		"varValues":varValues
	},function(data) {
		//仅在开启标志popupStatus为1的情况下去除     
	    if(popupStatus==1){
	     $("#screen").hide();
	     $("#showdiv").hide();
	    }  
		alerts(data.result);
		$("#templateSize")[0].innerHTML="";
		$('#ff').form('clear');
		$('#addSendMessage').window('close');
		$('#smsList').datagrid('reload');
	});	
}


function getCounts () {
	$("#messages").show();
	$("#counts")[0].innerHTML="";
	$.post("counts", $("#ff").serializeArray(),function(data) {
		$("#messages").hide();
		$("#counts").show();	
		$("#counts")[0].innerHTML="短信条数"+data.PHONES;
	});	

}
function changeTemplate(){
	var template = $('#template').combogrid('grid').datagrid('getSelected');
	if (template==null||template==""){
		$("#templatesContent").attr("value","");
		$("#templateSize")[0].innerHTML="";
	}else {
		$.post("templates", {
			"template":template
		},function(data) {
			$("#templatesContent").attr("value",data.content);
			$("#templateSize")[0].innerHTML="短信字数"+data.size;
		});	
	}

}
function loadPopup(){    
   //仅在开启标志popupStatus为0的情况下加载 
   if(popupStatus==0){
    $("#screen").css({
     "opacity": "0.5"
    });     
    $("#screen").fadeIn("slow");

    $("#showdiv").fadeIn("slow");   

     $("body").css({"overflow":"hidden"});

    popupStatus= 1;    
   }
} 
function doubleClick(id){
	if(!id){
		id = "";
	}
	 showWindow({  
	        title:'短信监控',  
	        href:'openWinReport?batchid='+id,  
	        width:600, 
	        height:450,
	        maximizable:false,
	        minimizable:false,
	        zIndex:20
	    },"smsSla");  
}

var  varGrid= $("#varGrid").combogrid('grid');
alerts(varGrid); 

function smsStops(){
	if(confirm('确定要停止吗？')){
		var batchId = $("#batchIds").val();
		if ($("#receiveCount").val()==$("#smsCount").val()) {
			alerts("已全都发送完毕，无法停止");
			return ;
		}
		$.ajax({
			type:'POST',
			url:'stopSms',
			data:{'batchId':batchId},
			success:function(data){
				alerts(data.result);
				$('#smsStop').attr('disabled', 'disabled');
				$('#smsDetail').window('close');
				$('#smsList').datagrid('reload');
			}
		});
	}
}
