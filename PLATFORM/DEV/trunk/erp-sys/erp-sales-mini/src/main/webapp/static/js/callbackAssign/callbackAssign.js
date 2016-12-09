//表格输入是否有效
var isValidTable = true;
var lastIndex;
var lastRow;

$(function(){

    var now = new Date();
    $('#incomingLowDate').datetimebox('setValue', now.getFullYear()+"-"+(now.getMonth() + 1)+"-"+(now.getDay() - 2)+" 00:00:00");
    $('#incomingHighDate').datetimebox('setValue', now.getFullYear()+"-"+(now.getMonth() + 1)+"-"+now.getDay()+" "+now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds());

    $('#assignType').combobox({
        onSelect:function(record){
            if(record.id ==1){

            }else if(record.id ==2){

            }else if(record.id ==3){
                $("#show_timeLength").css("display","block");
                $("#show_priority").css("display","none");
            }else if(record.id ==4){

            }else if(record.id ==5){
                inintCallback();
            }else if(title =="虚拟进线"){

            }
            $('#workgroupT').datagrid("reload",{'callType':record.id});
        }
    });

    $('#workgroupT').datagrid({
        url:'/traffic/getValidGroup',
        height : 200,
        fitColumns : true,
        remoteSort : false,
        singleSelect : false,
        checkOnSelect: false,
        selectOnCheck: true,
        queryParams : {
            'callType' : $('#assignType').combobox('getValue')
        },
        columns:[[
            {field:'ck',checkbox:true,width:20},
            {field:'grpid',title:'坐席组ID',width:100,align:'center'},
            {field:'grpname',title:'坐席组名称',width:100,align:'center'},
            {field:'allotNum',title:'分配数量',width:100,align:'center', editor:{type:'numberbox',options:{min: 0}}},
            {field:'overplusNum',title:'剩余数量',width:100,align:'center'}
        ]],
        onClickCell: function(index,field,value){
            //alert(121);
//            $(this).datagrid('beginEdit', index);
//            var ed = $(this).datagrid('getEditor', {index:index,field:field});
//            $(ed.target).focus();
            if (editIndex != index){
                if (endEditing()){
                    $(this).datagrid('selectRow', index)
                        .datagrid('beginEdit', index);
                    editIndex = index;
                } else {
                    $(this).datagrid('selectRow', editIndex);
                }
            }
            
            $('#assignOperate').hide();
        },
        onAfterEdit:function(rowIndex, rowData, changes){
            checkAll = $('#workgroupT').datagrid('getChecked');
            for(var i=0 ; i<checkAll.length ; i++){
                if(rowIndex == $('#workgroupT').datagrid('getRowIndex',checkAll[i])){
                    checkDateforedit(rowIndex, rowData, changes);
                }
            }
        },
        onBeforeEdit:function(rowIndex, rowData){
            checkAll = $('#workgroupT').datagrid('getChecked');
            for(var i=0 ; i<checkAll.length ; i++){

                if(rowIndex == $('#workgroupT').datagrid('getRowIndex',checkAll[i])){
            tonBeforeEdit(rowIndex, rowData);
                }
            }
        },
        onCheck:function(rowIndex,rowData){
            redoRowData(rowIndex);
        },onUncheck:function(rowIndex,rowData){
            cleanRowData(rowIndex);
        }
//        ,onCheckAll:function(){
//            $('#workgroupT').datagrid("reload");
//
//        }

    });

    $('#agentTb').datagrid({
        url:'/callbackAssign/queryAvaliableAgent',
        height : 200,
		fitColumns : true,
		remoteSort : false,
		singleSelect : false,
        checkOnSelect: false,
        selectOnCheck: true,
		pagination : false,
        queryParams : {
            'lowDate' : $('#g_beginDate').datetimebox('getValue'),
            'highDate' : $('#g_endDate').datetimebox('getValue'),
            'level' : $('#g_level').combobox('getValue'),
            'agentId' : $('#g_agentId').val(),
            'groups' : null,
            'cType' : $('#assignType').combobox('getValue')
        },
        columns:[[
            {field:'ck',checkbox:true,width:20},
            {field:'userWorkGrp',title:'工作组',width:100,align:'center'},
            {field:'userId',title:'座席工号',width:80,align:'center'},
            {field:'userName',title:'座席姓名',width:60,align:'center'},
            {field:'levelId',title:'座席等级(00:00-08:00)',width:150,align:'center'},
            {field:'levelId2',title:'座席等级(08:00-18:00)',width:150,align:'center'},
            {field:'levelId3',title:'座席等级(18:00-24:00)',width:150,align:'center'},
            {field:'unitcost4',title:'已分配/已执行',width:80,align:'center', 
            	formatter : function(val, row){
            		var rs = '';
            		if(null != row){
            			rs = row.intradayAllocatedCount + "/" + row.intradayExecutedCount;
            		}
            		return rs;
            }},
            {field:'assignCount',title:'分配数量',width:60,align:'center', editor:{type:'numberbox',options:{min: 0}}}
        ]],
        onClickCell: function(index, row){
        	if (lastIndex != index){
				$(this).datagrid('endEdit', lastIndex);
				$(this).datagrid('beginEdit', index);
			}
			lastIndex = index;

	    	$('#assignOperate').hide();
        },
        onCheck : function(rowIndex, rowData){
        	calcAverageCount();
        	$('#assignOperate').hide();
        },
        onUncheck : function(rowIndex, rowData){
        	calcAverageCount();
        	clearRowData(rowIndex, false);
        	$('#assignOperate').hide();
        },
        onCheckAll : function(rowIndex, rowData){
        	calcAverageCount();
        	$('#assignOperate').hide();
        },
        onUncheckAll : function(rowData){
        	calcAverageCount();
        	clearRowData(rowData, true);
        	$('#assignOperate').hide();
        }
    });

    $("#v_selectGroup").bind("keyup",function(){
        $('#workgroupT').datagrid('load',{
            groupName: $("#v_selectGroup").val()
        });
    });


    $('#g_todayRange').click(function(){
    	if($(this).is(':checked')){
    		$('#g_beginDate').datetimebox('setValue', curDate + ' 00:00:00');
    		$('#g_endDate').datetimebox('setValue', curDate + ' 23:59:59');
    	}
    });
    
    
    //平均分配到坐席组
    $('#g_isAverage').click(calcAverageCount);
    
});

function queryAvaliableForm_clear(){
    $("#assignType").combobox("setValue",'0');
    $("#timeLength").combobox("setValue","");
    $("#allocatedNumbers").combobox("setValue",'0');
    $("#v_priority").combobox("setValue","");
    $('#incomingLowDate').datetimebox('setValue','');
    $('#incomingHighDate').datetimebox('setValue', '');
    $("#acdGroup").val("");
    $("#dnis").val("");

}

//初始化callback话务分配
var inintCallback= function(){
    console.info("=============================");
//隐藏通话时长
    $("#show_timeLength").css("display","none");
//显示优先级
    $("#show_priority").css("display","block");
    //findCallback();



};



var editIndex = undefined;
function endEditing(){
    if (editIndex == undefined){return true;}
    if ($('#workgroupT').datagrid('validateRow', editIndex)){
        $('#workgroupT').datagrid('endEdit', editIndex);
        //editIndex=初始化
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

var checkAll = [];

var vallotGroup = function(){
    if(typeof($("#v_allotGroup").attr("checked")) == "undefined"){
        $('#workgroupT').datagrid('reload');
    }else{
        checkAll = $('#workgroupT').datagrid('getChecked');
        if(checkAll.length == 0){
            $("#v_allotGroup").attr("checked",false);
            window.parent.alertWin("系统提示","请选择坐席组");
        }else{
            equalDivision(parseInt($("#v_allotNum").html()),checkAll.length);
        }

    }
};



/**
 *
 * @param totalNum ,可分配数量
 * @param num，被分配数量
 */
var equalDivision = function(totalNum,num){
    var complementNum =  totalNum % num; //余数
    var wholeNum= parseInt(totalNum/num);//整数
    if(totalNum <= num){
        for(var i=0 ; i<totalNum ; i++){
            salesUpdateRow($('#workgroupT').datagrid('getRowIndex',checkAll[i]),1);
        }
    }else{

        for(var i=0 ; i<num ; i++){
            if(i <= complementNum-1){
                salesUpdateRow($('#workgroupT').datagrid('getRowIndex',checkAll[i]),wholeNum+1);
            }else{
                salesUpdateRow($('#workgroupT').datagrid('getRowIndex',checkAll[i]),wholeNum);
            }
        };
    }
    console.info("++++++++++++++218");
    //设置可分配数量为0
    $("#v_overplusNum").html("0");

};



//更新row
var salesUpdateRow=function(index,value){
    $('#workgroupT').datagrid('updateRow',{
        index: index,
        row: {allotNum:value,overplusNum:value}
    });
};

//清除当前行的修改{

var cleanRowData=function(rowIndx){
    $('#workgroupT').datagrid('updateRow',{
        index: rowIndx,
        row: {allotNum:'',overplusNum:''}
    });

    console.info("===uncheck");
    redoRowData(rowIndx);
}


//重算

var redoRowData=function(rowIndx){
    checkAll=[];
    $('#workgroupT').datagrid('acceptChanges');
    checkAll = $('#workgroupT').datagrid('getChecked');


    //可分配数量
    var assign_data = parseInt($("#v_allotNum").html());
    //已分配数量
    var assigned_data = 0;
    if(checkAll.length>0){
        for(var i=0 ; i<checkAll.length ; i++){
            assigned_data+= isNaN(parseInt(checkAll[i].allotNum))?0:parseInt(checkAll[i].allotNum);
            if(rowIndx == $('#workgroupT').datagrid('getRowIndex',checkAll[i])){
                salesUpdateRow(rowIndx,isNaN(parseInt(checkAll[i].allotNum))?'':parseInt(checkAll[i].allotNum));
            }
        }

       console.info("assign_data:"+assign_data);
        console.info("assigned_data:"+assigned_data);


    }
    if(assign_data>assigned_data){
        $('#v_overplusNum').html(isNaN(assign_data-assigned_data)?0:assign_data-assigned_data);
    }

}



///**

// *分配到坐席组
// */
//var allotToGroup=function(){
//
//    if(checkAll.length==0){
//        $("#v_allotGroup").attr("checked",false);
//        window.parent.alertWin("系统提示","请选择坐席组");
//    }else{
//        //组装数据
//        var groupids ="",groupNums="";
//        $.each(checkAll, function(i,val){
//            if (typeof(val.allotNum) != "undefined")
//            {
//                groupids+=val.grpid+",";
//                groupNums+=val.allotNum+",";
//            }
//        });
//
//        $.post("/traffic/allotToGroup",{groupids:groupids,groupNames:groupNums},function(result){
//
//        });
//
//
//    }
//}




var findCallback=function(){
    $('#workgroupT').datagrid('reload');
    $('#queryAvaliableQty').attr('disabled',true);
    $.post("/traffic/findCallback",{
        priority:$("#v_priority").combobox("getValue"), //通话时长
        sdt:$("#incomingLowDate").datetimebox('getValue'),//开始时间
        edt:$("#incomingHighDate").datetimebox('getValue'),    //结束时间
        acd:$("#acdGroup").val(),    //ACD组
        dnis:$("#dnis").val(),    //被叫号码
        allocatedNumbers:$("#allocatedNumbers").combobox("getValue")    //数据状态
    },function(date){

        if(date.result){
            console.info("++++++++++++++275"+date.count);
            $('#v_allotNum').html(date.count);
            $('#v_overplusNum').html(date.count);
        }else{
            $('#v_allotNum').html("");
            $('#v_overplusNum').html("");
        }
        $('#queryAvaliableQty').removeAttr('disabled');
    });

};

var tempValue=0;
var tonBeforeEdit= function(rowIndex, rowData){
    var exit= $.inArray(rowData,checkAll);
    //如果选择的行没有check则不允许编辑
    if(exit == -1){
        rowData.editing = true;
        $('#workgroupT').datagrid('refreshRow', rowIndex);
    }else{
        tempValue= parseInt(rowData.allotNum);
    }

};

//校验分配到组的数据
var checkDateforedit=function(rowIndex, rowData, changes){

    $('#workgroupT').datagrid('acceptChanges');
    checkAll = $('#workgroupT').datagrid('getChecked');

    //可分配数据
    var assign_data = parseInt($("#v_allotNum").html());
    //修改后的已分配数据
    var assigned_data = 0;

    for(var i=0 ; i<checkAll.length ; i++){
        console.info("checkAll:"+checkAll[i]);
        if(rowIndex != $('#workgroupT').datagrid('getRowIndex',checkAll[i])){
            assigned_data+=  parseInt(checkAll[i].allotNum);
        }
    }
    assigned_data+=parseInt(rowData.allotNum);

    if(assigned_data>assign_data){
        cleanRowData(rowIndex);
    }else if(checkAll.length>0){
        salesUpdateRow(rowIndex,changes.allotNum);
        console.info("++++++++++++++318"+(assign_data));
        console.info("++++++++++++++318"+assigned_data);
        //修改剩余数量
        $('#v_overplusNum').html(isNaN(assign_data-assigned_data)?0:assign_data-assigned_data);
    }

    //

};


//分配到坐席
var allotCallbackToPerson =function(users){
    console.info('分配到坐席：'+users);

    var rows = $('#agentTb').datagrid('getChecked');


    $.post("/traffic/allotCallbackToPerson",{
        priority:$("#v_priority").combobox("getValue"), //通话时长
        sdt:$("#incomingLowDate").datetimebox('getValue'),//开始时间
        edt:$("#incomingHighDate").datetimebox('getValue'),    //结束时间
        acd:$("#acdGroup").val(),    //ACD组
        dnis:$("#dnis").val(),    //被叫号码
        allocatedNumbers:$("#allocatedNumbers").combobox("getValue"),    //数据状态
        users:users

    },function(data){

        parent.alertWin("系统提示",data.msg);

    });

};








/*===================================	话务分配	===================================*/


//平均计算分配数量
function calcAverageCount(){
	var isAverage = $('#g_isAverage').is(':checked');
	if(!isAverage){
		return;
	}
    calcAverageCount2();
    /*
	var groupRows = $('#workgroupT').datagrid('getChecked');
	var agentRows = $('#agentTb').datagrid('getChecked');
	for(var i=0; i<groupRows.length; i++){
		var groupRow = groupRows[i];
		var groupQty = Number(isNaN(groupRow.allotNum)==true ? 0 : groupRow.allotNum);
		//每人每个
		var each = Math.floor(groupQty / agentRows.length);
		//剩下的
		var left = Number(groupQty % agentRows.length);
		for(var j=0; j<agentRows.length; j++){
			if(agentRows[j].userWorkGrp == groupRow.grpid){
				var rowIndex = $('#agentTb').datagrid('getRowIndex', agentRows[j]);
				callback.updateAgentCell(rowIndex, each);
			}
		}
		
		for(var k=0; k<agentRows.length; k++){
			if(agentRows[k].userWorkGrp == groupRow.grpid && left > 0){
				var rowIndex = $('#agentTb').datagrid('getRowIndex', agentRows[k]);
				callback.updateAgentCell(rowIndex, Number(agentRows[k].assignCount) + 1);
				left = Number(left) - 1 ;
			}
		}
	}
	*/
}

function calcAverageCount2(){
    var groupRows = $('#workgroupT').datagrid('getChecked');
    var agentRows = $('#agentTb').datagrid('getChecked');
    if(groupRows.length > 0 && agentRows.length > 0){
        for(var i = 0; i < agentRows.length; i++){
            var rowIndex = $('#agentTb').datagrid('getRowIndex',agentRows[i]);
            if(rowIndex > -1){
                callback.updateAgentCell(rowIndex, 0);
            }
        }
        groupDistribute(groupRows, agentRows);
    }
}

function groupDistribute(groupRows, agentRows){
    for(var i = 0; i < groupRows.length; i++){
        userDistribute(groupRows[i], agentRows);
    }
}

function userDistribute(groupRow, agentRows){
    var allotNum = $(groupRow).attr("allotNum");
    if($.isNumeric(allotNum)){
       var srcGrp = $(groupRow).attr("grpid");
       var newNum = allotNum;
       while(newNum > 0){
           for(var index = 0; index < agentRows.length; index++){
               var agentRow = agentRows[index];
               var destGrp =  $(agentRow).attr("userWorkGrp");
               if(srcGrp == destGrp) {
                   var assignCount =  $(agentRow).attr("assignCount");
                   if(!$.isNumeric(assignCount)) assignCount = 0;
                   var rowIndex = $('#agentTb').datagrid('getRowIndex',agentRow);
                   if(rowIndex > -1){
                       callback.updateAgentCell(rowIndex, assignCount + 1);
                       newNum--;
                       //分配完毕
                       if(newNum == 0) return;
                   }
               }
           }
           //没有同组坐席
           if(newNum == allotNum) return;
       }
    }
}


//清空分配数据
function clearRowData(rowIndex, isAll){
	if(isAll){
		for(var i=0; i<rowIndex.length; i++){
			callback.updateAgentCell(i, 0);
		}
	}else{
		callback.updateAgentCell(rowIndex, 0);	
	}
}


var callback = {};

var URL_QUERY_LEAD_INTERACTION_QTY = ctx + '/callbackAssign/queryAvaliableQty';
var URL_QUERY_CALLBACK_QTY = '';

var URL_AGENT_ASSIGN = ctx + '/callbackAssign/assignToAgent';
var URL_CALLBACK_ASSIGN = '';

//查询可分配数量
callback.queryAvaliableQty = function(){
    var now = new Date();
    var crdt = new Date(now.getFullYear()+"-"+(now.getMonth() + 1)+"-"+(now.getDay() - 2)+" 00:00:00");
    var lowDate = new Date($('#incomingLowDate').datetimebox('getValue'));
    if(lowDate < crdt) {
        if(parent){
            parent.alertWin('错误', '不能查询超过三天的数据！');
        } else {
            alert("不能查询超过三天的数据");
        }
        return;
    }

	var _assignType = $('#assignType').combobox('getValue');
	//接通
	if(_assignType == 3){
		this.queryLeadInteractionQty();
	}else if(_assignType == 5){
        findCallback();
	}

};

//查询分配类型为‘接通’的分配数量
callback.queryLeadInteractionQty = function(){
	
	var data = this.prepareParam(3);
	
	this.queryQtyByParam(data, URL_QUERY_LEAD_INTERACTION_QTY);
	
console.log('data: ', data);
};

//处理表单数据
callback.prepareParam = function (type){
	var data = $('#queryAvaliableForm').serialize();
	if(type == 3){
		var timeLength = $('#timeLength').combobox('getValue');
		var lowLength = -1;
		var highLength = -1;
		if('A' == timeLength){
			lowLength = 0;
			highLength = 20;
		}else if('B' == timeLength){
			lowLength = 20;
			highLength = 180;
		}else if('C' == timeLength){
			lowLength = 180;
			highLength = -1;
		}
		
		data += '&lowCallDuration=' + lowLength;
		data += '&highCallDuration=' + highLength;
	}

	return data;
};

callback.queryQtyByParam = function(_data, _url){
	$.ajax({
		url : _url,
		type : 'POST',
		data : _data,
        beforeSend:function(){
            $('#queryAvaliableQty').attr('disabled',true);
        },
		success : function(rs){
			if(null != rs){
				var obj = eval(rs);
				var qty = obj.qty;
				$('#v_allotNum').html(qty);
                console.info("++++++++++++++476");
				$('#v_overplusNum').html(qty);
			}else{
				$('#v_allotNum').html(0);
				$('#v_overplusNum').html(0);
			}
            $('#queryAvaliableQty').removeAttr('disabled');
		}
	});
};

//分配到坐席
callback.assignToAgent = function(){
    var _assignType = $('#assignType').combobox('getValue');
    var isAverage = $('#isAverage').is(':checked');
    var rows = $('#agentTb').datagrid('getChecked');
    
    if(rows.length==0){
        window.parent.alertWin('错误', '请选择要分配的坐席！');
        return;
    }
    
    var _isValid = true;
    var assignArr = new Array();
    for(var i=0; i<rows.length; i++){
        var _obj = {};
        var _assignCount = 0;
        if(null != rows[i].assignCount && undefined != rows[i].assignCount && '' != rows[i].assignCount){
            _assignCount = Number(rows[i].assignCount);
        }
        if(0==_assignCount){
            _isValid = false;
            break;
        }
        _obj['assignCount'] = _assignCount;
        _obj['userGroup'] = rows[i].userWorkGrp;
        _obj['userId'] = rows[i].userId;
        assignArr.push(_obj);
    }

    if(!_isValid){
        window.parent.alertWin('错误', '坐席['+rows[i].userId+']分配数量不能为0！');
        return;
    }

    //接通
    if(_assignType == 3){
       
        var obj = {};
        obj['agentUsersStr'] = JSON.stringify(assignArr);
        var data = this.prepareParam(3, data);
        data += '&' + $.param(obj);
        data += '&averageAssign=' + isAverage;

        this.assignOperate(data, URL_AGENT_ASSIGN);
    }else if(_assignType == 5){
        console.info('分配到坐席：_assignType=5');
        allotCallbackToPerson(JSON.stringify(assignArr));
    }
};

callback.assignOperate = function(_data, _url){
	$.ajax({
		url : _url,
		type : 'POST',
		data : _data,
		beforeSend : showLoading,
		complete : hideLoading,
		success : function(rs){
			if(eval(rs.success)){
				window.parent.alertWin('提示', '分配成功<br>批次号：' + rs.batchId);

				//重新加载分配数量
				callback.queryAvaliableQty();
				
				//重新加载组信息
				$('#workgroupT').datagrid('reload');
				
				//重新加载坐席
				callback.queryAvaliableAgent();
			}else{
				window.parent.alertWin('提示', '分配失败');
			}
		}
	});
};


callback.queryAvaliableAgent = function(){
	var rows = $('#workgroupT').datagrid('getChecked');
	var groups = '';
	for(var i=0; i<rows.length; i++){
		groups += rows[i].grpid;
		if(i!=rows.length-1){
			groups += ',';
		}
	}
	
//console.log('groups: ', groups);
	
	$('#agentTb').datagrid('reload', {
		'lowDate' : $('#g_beginDate').datetimebox('getValue'),
		'highDate' : $('#g_endDate').datetimebox('getValue'),
		'level' : $('#g_level').combobox('getValue'),
		'agentId' : $('#g_agentId').val(),
		'groups' : groups,
        'cType' : $('#assignType').combobox('getValue')
		
	});
};

callback.updateAgentCell=function(index,value){
    $('#agentTb').datagrid('updateRow',{
        index: index,
        row: {assignCount:value}
    });
};


callback.validateAssignment = function(){
	
	$('#agentTb').datagrid('endEdit', lastIndex);
	$('#workgroupT').datagrid('endEdit', editIndex);
    lastIndex = undefined;
    editIndex = undefined;
    $('#workgroupT').datagrid('acceptChanges');
    $('#agentTb').datagrid('acceptChanges');
	
	var groupRows = $('#workgroupT').datagrid('getChecked');
	var agentRows = $('#agentTb').datagrid('getChecked');
	
    if(agentRows.length==0){
        window.parent.alertWin('错误', '请选择要分配的坐席！');
        return;
    }
    
    //触发平均分配事件
    calcAverageCount();
	
	var valid = true;
	var lastGroup = '';
	for(var i=0; i<groupRows.length; i++){
		
		var agentTotalQty = 0;
		var groupTotalQty = 0;
		
		var _gr = groupRows[i];
		groupTotalQty = Number(isNaN(_gr.allotNum)==true? 0 : _gr.allotNum);
		if(groupTotalQty==0){
			window.parent.alertWin('错误', '坐席组['+_gr.grpid+']可分配数量不足！');
			return;
		}
		lastGroup = groupRows[i].grpid;
		for(var j=0; j<agentRows.length; j++){
			var _group = agentRows[j].userWorkGrp;
			if(_gr.grpid == _group){
				var _val = agentRows[j].assignCount;
				if(null!= _val && undefined != _val && ''!=_val){
					agentTotalQty += Number(isNaN(agentRows[j].assignCount)==true ? 0 : agentRows[j].assignCount);
				}
			}
		}
		
		if(agentTotalQty>groupTotalQty){
			valid = false;
			break;
		}else{
			if(agentTotalQty<=groupTotalQty){
				var diff = Number(groupTotalQty) - Number(agentTotalQty);
				for(var i=0; i<groupRows.length; i++){
					var __gr = groupRows[i];
					if(__gr.grpid == _gr.grpid){
						var grpRowIndex = $('#workgroupT').datagrid('getRowIndex', __gr);
						$('#workgroupT').datagrid('updateRow',{
					        index: grpRowIndex,
					        row: {overplusNum:diff}
					    });
					}
				}
			}
		}
	}
	
	var allEmpty = true;
	for(var j=0; j<agentRows.length; j++){
		var _val = agentRows[j].assignCount;
		if(null!= _val && undefined!=_val && ''!=_val && _val!=0){
			allEmpty &= false;
		}
	}
	
	if(allEmpty){
		$('#assignOperate').hide();
		return false;
	}
	
	if(!valid){
		$('#assignOperate').hide();
		window.parent.alertWin('错误', '坐席分配总数大于工作组['+lastGroup+']的数量！');
		return false;
	}
	
	$('#assignOperate').show();

	return true;
};


function showLoading(){
	$('#loading').fadeIn();
    $('#loading div:first').css('line-height',$('#loading div:first').height()+'px');
}

function hideLoading(){
	$('#loading').fadeOut();
}

function ajaxFileUpload() {
//        $("#loading").ajaxStart(function() {
//            $(this).show();
//        });
//        $("#loading").ajaxComplete(function(){
//            $(this).hide();
//        });
        
    $.ajaxFileUpload({
        url: '/callbackAssign/import', 
        secureuri: false,
        fileElementId: 'fileImport',
        dataType: 'json',
        success: function (data, status) {
        	if (data.code) {
        		window.parent.alertWin("上传成功", data.count + "条记录保存进数据库");
        	} else {
           		window.parent.alertWin("系统提示", data.msg);
        	}
        }
    })
    
    return false;
}