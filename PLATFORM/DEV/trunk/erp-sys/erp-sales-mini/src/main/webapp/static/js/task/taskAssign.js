/*
 * @(#)taskAssign.js 1.0 2013年8月28日下午3:54:04
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 * 
 * SAMBA数据推送到坐席
 */

var lastIndex;

$(function(){
	
	$('#agentTb').datagrid({
		width : '100%',
		height : 180,
		fitColumns : true,
		remoteSort : false,
		singleSelect : false,
        checkOnSelect: false,
        selectOnCheck: true,
		pagination : false,
        scrollbarSize:-1,
		url : ctx+'/task/queryAgent',
		queryParams : {'initLoad' : false},
		onBeforeLoad : function(param){
			if(param.initLoad){
				return true;
			}
			return false;
		},
		columns : [ [ 
		{
			field:'ck',
			checkbox:true,
			width:20
		},{
			field : 'agentId',
			title : '坐席',
			align : 'center',
			width : 100
		},{
			field : 'userWorkGrp',
			title : '坐席组',
			align : 'center',
			width : 100
		},{
			field : 'assignQty',
			title : '分配到坐席数量',
			align : 'center',
			width : 100,
			editor: {type:'numberbox', options:{min: 0}}
		},{
			field : 'obtainQty',
			title : '历史已分配数量',
			align : 'center',
			width : 100
		},{
			field : 'obtainUnDialQty',
			title : '当前未拨打数量',
			align : 'center',
			width : 100
		}
		] ],
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
	
	 //平均分配模式
    $('#averageMode').click(calcAverageCount);
    
    //计算校验
    $('#assignValidate').click(validateAssignment);
    
    //分配操作
    $('#assignOperate').click(assignOperate);
});


/**
 * 查询坐席组
 */
function queryGroup(){
	var groupRows = $('#workgroupT').datagrid('getChecked');
	var groups = '';
	for(var i=0; i<groupRows.length; i++){
		groups += groupRows[i].grpid;
		if(i!=groupRows.length-1){
			groups += ',';
		}
	}
			
	$('#agentTb').datagrid('reload',{
		'initLoad' : true,
		'groups' : groups
	});
}


//平均计算分配数量
function calcAverageCount(){
	var averageMode = $('#averageMode').is(':checked');
	if(!averageMode){
		return;
	}
	
	var groupRows = $('#workgroupT').datagrid('getChecked');
	var agentRows = $('#agentTb').datagrid('getChecked');
	for(var i=0; i<groupRows.length; i++){
		var groupRow = groupRows[i];
		var groupQty = Number(isNaN(groupRow.overplusNum)==true ? 0 : groupRow.overplusNum);
		//每人每个
		var each = Math.floor(groupQty / agentRows.length);
		//剩下的
		var left = Number(groupQty % agentRows.length);
		for(var j=0; j<agentRows.length; j++){
			if(agentRows[j].userWorkGrp == groupRow.grpid){
				var rowIndex = $('#agentTb').datagrid('getRowIndex', agentRows[j]);
				updateAgentCell(rowIndex, each);
			}
		}
		
		for(var k=0; k<agentRows.length; k++){
			if(agentRows[k].userWorkGrp == groupRow.grpid && left > 0){
				var rowIndex = $('#agentTb').datagrid('getRowIndex', agentRows[k]);
				updateAgentCell(rowIndex, Number(agentRows[k].assignQty) + 1);
				left = Number(left) - 1 ;
			}
		}
	}
}

//清空分配数据
function clearRowData(rowIndex, isAll){
	if(isAll){
		for(var i=0; i<rowIndex.length; i++){
			updateAgentCell(i, 0);
		}
	}else{
		updateAgentCell(rowIndex, 0);	
	}
}

function updateAgentCell(index,value){
    $('#agentTb').datagrid('updateRow',{
        index: index,
        row: {assignQty:value}
    });
}


//分配到坐席进行校验
function validateAssignment(){
	
	$('#agentTb').datagrid('endEdit', lastIndex);
    lastIndex = undefined;
    $('#workgroupT').datagrid('acceptChanges');
    $('#agentTb').datagrid('acceptChanges');
    
	var groupRows = $('#workgroupT').datagrid('getChecked');
	var agentRows = $('#agentTb').datagrid('getChecked');
	
    if(agentRows.length==0){
        window.parent.alertWin('错误', '请选择要分配的坐席！');
        return;
    }
	
	var valid = true;
	var lastGroup = '';
	for(var i=0; i<groupRows.length; i++){
		
		var agentTotalQty = 0;
		var groupTotalQty = 0;
		
		var _gr = groupRows[i];
		groupTotalQty = Number(isNaN(_gr.obtainQty)==true ? 0 : _gr.obtainQty);
		if(groupTotalQty==0){
			window.parent.alertWin('错误', '坐席组['+_gr.grpid+']可分配数量不足！');
			return;
		}
		lastGroup = groupRows[i].grpid;
		for(var j=0; j<agentRows.length; j++){
			var _group = agentRows[j].userWorkGrp;
			if(_gr.grpid == _group){
				var _val = agentRows[j].assignQty;
				if(null!= _val && undefined != _val && ''!=_val){
					agentTotalQty += Number(agentRows[j].assignQty);
				}else{
					window.parent.alertWin('错误', '分配到坐席['+agentRows[j].agentId+']数量不能为空！');
					return;
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
						//组剩余数量
						var grpRowIndex = $('#workgroupT').datagrid('getRowIndex', __gr);
						$('#workgroupT').datagrid('updateRow',{
					        index: grpRowIndex,
					        row: {overplusNum:diff}
					    });
						//组已分配数量
						$('#workgroupT').datagrid('updateRow',{
					        index: grpRowIndex,
					        row: {obtainQty4use:Number(agentTotalQty)}
					    });
					}
				}
			}
		}
	}
	
	var allEmpty = true;
	for(var j=0; j<agentRows.length; j++){
		var _val = agentRows[j].assignQty;
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
}


//分配到坐席
function assignOperate(){
	
	//var averageMode = $('#averageMode').is(':checked');
	var groupRows = $('#workgroupT').datagrid('getChecked');
    var rows = $('#agentTb').datagrid('getChecked');
    if(groupRows.length==0){
    	window.parent.alertWin('错误', '请选择要分配的坐席组！');
        return;
    }
    
    if(rows.length==0){
        window.parent.alertWin('错误', '请选择要分配的坐席！');
        return;
    }
    
    var _isValid = true;
    var assignArr = new Array();
    for(var i=0; i<rows.length; i++){
        var _obj = {};
        var _assignCount = 0;
        if(null != rows[i].assignQty && undefined != rows[i].assignQty && '' != rows[i].assignQty){
            _assignCount = Number(rows[i].assignQty);
        }
        if(0==_assignCount){
            _isValid = false;
            break;
        }
        _obj['assignCount'] = _assignCount;
        _obj['userGroup'] = rows[i].userWorkGrp;
        _obj['userId'] = rows[i].agentId;
        assignArr.push(_obj);
    }

    if(!_isValid){
        window.parent.alertWin('错误', '坐席['+rows[i].agentId+']分配数量不能为0！');
        return;
    }
    //获取所选的组
	var groups = '';
	for(var i=0; i<groupRows.length; i++){
		groups += groupRows[i].grpid;
		if(i!=groupRows.length-1){
			groups += ',';
		}
	}

    var obj = {};
    obj['agentUsersStr'] = JSON.stringify(assignArr);
    obj['agentGroupList'] = groups;
    obj['assignStrategy'] = $('input[name="agentAssignStrategy"]:checked').val();
    var data = $('#queryAvaliableForm').serialize();
    data += '&' + $.param(obj);
	
	$.ajax({
		url : ctx + '/task/assignToAgent',
		type : 'POST',
		data : data,
		beforeSend : showLoading,
		complete : hideLoading,
		success : function(rs){
			if(eval(rs.success)){
				window.parent.alertWin('提示', '分配成功');

				//查询剩余数量
				findAvaliableQty();
				
				//重新加载组信息
				$('#workgroupT').datagrid('reload');
				//将分配过的组选中
				var allGroups = $('#workgroupT').datagrid('getRows');
				for(var i=0; i<allGroups.length; i++){
					for(var j=0; j<groupRows.length; j++){
						if(allGroups[i].grpid == groupRows[j].grpid){
							$('#workgroupT').datagrid('checkRow', i);
							break;
						}
					}
				}
				
				//重新加载坐席信息
				$('#agentTb').datagrid('reload',{
					'initLoad' : true,
					'groups' : groups
				});
				
				$('#assignOperate').hide();
			}else{
				window.parent.alertWin('提示', '分配失败');
			}
		},
		error:function() {
			window.parent.alertWin('提示', '分配失败，网络异常');
		}
	});
}


function showLoading(){
	$('#loading').fadeIn();
    $('#loading div:first').css('line-height',$('#loading div:first').height()+'px');
}

function hideLoading(){
	$('#loading').fadeOut();
}