var CONTEXT_PATH = jQuery('meta[name="context_path"]').attr('content') || '';
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
		url:CONTEXT_PATH+'/sms/smslist',
		panelWidth:450,
		panelHeight:320,
		pagination:true,
		rownumbers:true,
		required:true,
		editable:false,
		 mode: 'remote', 
		columns:[[
			{field:'id',title:'短信编号',width:220},
			{field:'name',title:'短信内容',width:200},
		]],
		 onSelect:function(index,row){
			 $("#templatesContent").attr("value",row.content);
			 $("#templateSize")[0].innerHTML="短信字数"+row.content.length;
			 
			 $('#varGrid').datagrid({
				 url:CONTEXT_PATH+'/sms/getStaticVar',
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


	var lastIndex;
	$('#timeList').datagrid({
		width:320,
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
							width : 100,
							editor:'timespinner'
						},{
							field : 'endtime',
							title : '结束时间',
							rowspan:1,
							width : 100,
							editor:'timespinner'
							
						},{
							field : 'maxsend',
							title : '最大发送量',
							rowspan:1,
							width : 100,
							editor:'text'
						}]],
						onClickRow:function(rowIndex){
							if (lastIndex != rowIndex){
								$('#timeList').datagrid('endEdit', lastIndex);
								$('#timeList').datagrid('beginEdit', rowIndex);
							}
							lastIndex = rowIndex;
						}
	});
	
	initDataGrid();
});

function initDataGrid(){
	var tmpMaxsendId = $("#tmpMaxsendId").val();
	var tmpStarttimeId = $("#tmpStarttimeId").val();
	var tmpEndtimeId = $("#tmpEndtimeId").val();
	var tmpVarNamesId = $("#tmpVarNamesId").val();
	var tmpVarValuesId = $("#tmpVarValuesId").val();
	
	if(tmpMaxsendId!=""){
		var maxsendIdArray = tmpMaxsendId.split(",");
		var starttimeIdArray = tmpStarttimeId.split(",");
		var endtimeIdArray = tmpEndtimeId.split(",");
		for(var i=0;i<maxsendIdArray.length;i++){
			if(maxsendIdArray[i]!="")
			$('#timeList').datagrid('appendRow',{
				starttime:starttimeIdArray[i],
				endtime:endtimeIdArray[i],
				maxsend:maxsendIdArray[i]
			});
		}
		
		$('#timeList').datagrid('acceptChanges');
	}
	
	if(tmpVarNamesId!=""){
		var varNamesIdArray = tmpVarNamesId.split(",");
		var varValuesIdArray = tmpVarValuesId.split(",");
		for(var i=0;i<varNamesIdArray.length;i++){
			if(varNamesIdArray[i]!=""){
				alerts(varNamesIdArray[i]);
				$('#varGrid').datagrid('appendRow',{
					name:varNamesIdArray[i],
					value:varValuesIdArray[i]
				});
				$('#varGrid').datagrid('beginEdit', i);
			}
		}
		
	}
	
}
/** 
 * 时间对象的格式化; 
 */  
Date.prototype.format = function(format) {  
    /* 
     * eg:format="YYYY-MM-dd hh:mm:ss"; 
     */  
    var o = {  
        "M+" :this.getMonth() + 1, // month  
        "d+" :this.getDate(), // day  
        "h+" :this.getHours(), // hour  
        "m+" :this.getMinutes(), // minute  
        "s+" :this.getSeconds(), // second  
        "q+" :Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S" :this.getMilliseconds()  
    // millisecond  
    }  
  
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "")  
                .substr(4 - RegExp.$1.length));  
    }  
  
    for ( var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]  
                    : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
}  
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
	var ts = $('#template').combogrid('grid').datagrid('getSelected');
	var template = $('#templatesContent').val()+"="+ts.id+"="+ts.name+"=";
	var priority = $("#priority").val();
	var isreply = "off";
	var realtime = "off";
	var allowChannel = "off";
	var blackListFilter = "off";
	var mainNum = "off";
	var checkBoxs = "off";
	
	
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
	if (stime<times) {
		alerts('起始时间不能晚于当前时间');
		return false;
	}
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
	}
	if ($("#blackListFilter").attr("checked")=="checked") {
		blackListFilter = "on";
	}
	if ($("#mainNum").attr("checked")=="checked") {
		mainNum = "on";
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
		 if(mendt<mstart)
	     {
			 alerts("区段结束时间不能晚于区段开始时间");
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
		maxsend = maxsend+rows[i].maxsend+",";
		if (maxsend==null||maxsend == '') {
			alerts("发送量不能为空");
			return false;
		}
		starttime = starttime+rows[i].starttime+",";
		endtime = endtime+rows[i].endtime+",";
	}
	
	for(var i=0;i<varGridRows.length;i++)
	{
		varNames = varNames+varGridRows[i].name+",";
		var ed = $('#varGrid').datagrid('getEditor', {index:i,field:'value'});
		varValues = varValues+$(ed.target).val()+",";
	}
	$.post(CONTEXT_PATH+"/campaign/saveSmsSet", {
		"maxsend" : maxsend,
		"starttime":starttime,
		"endtime":endtime,
		"template":template,
		"priority":priority,			
		"stime":stime,
		"etime":etime,
		"isreply":isreply,
		"realtime":realtime,
		"allowChannel":allowChannel,
		"blackListFilter":blackListFilter,
		"mainNum":mainNum,
		"varNames":varNames,
		"varValues":varValues,
		"campaignId":$("#smsCampaignId").val()
	},function(data) {
		alerts(data.result);
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

function smsStops(){
	if(confirm('确定要停止吗？')){
		var batchId = $("#batchIds").val();
		if ($("#receiveCount").val()==$("#smsCount").val()) {
			alerts("已全都发送完毕，无法停止");
			return false;
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
