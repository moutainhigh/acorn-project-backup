/**
 * Created with IntelliJ IDEA.
 * User: haoleitao, youngphy.fei
 * Date: 13-8-28
 * Time: 上午11:08
 * To change this template use File | Settings | File Templates.
 */
$(function(){
    initDocument();
    $("#v_selectGroup").bind("keyup",function(){
        $('#workgroupT').datagrid('load',{
        	jobId: $("#jobNum").combobox('getValue'),
        	groupcode: $("#v_selectGroup").val()
        });

    });
});


/**
 * 页面初始化
 */
var jobNum, campaignId, customerBatch;
var initDocument=function(){	

    $("#jobNum").combobox({
        url:'/task/allocate/getJobNum',
        valueField:'jobId',
        textField:'jobId',
        onSelect : function(param) {
            jobNum=param.jobId;
            $('#workgroupT').datagrid("reload",{'jobId':param.jobId});
            $("#campaignId").combobox({
            	url:'/task/allocate/getAllCampaign?jobId='+param.jobId,
                valueField:'id',
                textField:'name',
                onSelect : function(param) {
                	campaignId=param.id;
//                    $('#workgroupT').datagrid("reload",{'jobId':jobNum,'campaignId':campaignId});
                    $("#customerBatch").combobox({
                        url:'/task/allocate/customerBatchByGroupCode?groupCode=' + param.audience,
                        valueField:'batchCode',
                        textField:'batchCode',
                        onSelect : function(param) {
                        	customerBatch = param.batchCode;
                        	$('#workgroupT').datagrid("reload",{'jobId':jobNum,'campaignId':campaignId,'customerBatch':customerBatch});
                        }
                    });

                }
            });


            $('#customerBatch').combobox("setValue", '');

        }
    });
    
    //清空
    $('#a_alloClear').click(function(){
    	$('#campaignId').combobox('setValue','');
    	$('#jobNum').combobox('setValue','');
    	$('#customerBatch').combobox('setValue','');
    });

    $('#workgroupT').datagrid({
        url:'/task/allocate/queryGroupList',
        height : 157,
        fitColumns : true,
        remoteSort : false,
        singleSelect : false,
        checkOnSelect: false,
        selectOnCheck: true,
        scrollbarSize:-1,
        columns:[[
            {field:'ck',checkbox:true,width:20},
            {field:'grpid',title:'坐席组ID',width:100,align:'center'},
            {field:'grpname',title:'坐席组名称',width:100,align:'center'},
            {field:'allotNum',title:'分配数量',width:100,align:'center',editor:'numberbox'},
            {
            	field:'overplusNum',
            	title:'剩余数量',
            	width:100,
            	align:'center',
            	formatter: function(val, row){
            		if(val == null || val=="") {
            			return '0';
            		}else {
            			return val;
            		}
            	}
            },
            {
    			field : 'obtainQty4use',
    			title : '已分配',
    			align : 'center',
    			width : 100
    		},
            {
    			field : 'obtainQty',
    			title : '总数量',
    			align : 'center',
    			width : 100,
    			hidden: true
    		}
        ]],
        onClickCell: function(index,field,value){
//            $(this).datagrid('beginEdit', index);
//            var ed = $(this).datagrid('getEditor', {index:index,field:field});
//            $(ed.target).focus();
        	if(eval(isDepartmentManager)) {
//        		$(this).datagrid('selectRow', editIndex);
//	            if (editIndex != index){
	                if (endEditing()){
	                    $(this).datagrid('selectRow', index)
	                        .datagrid('beginEdit', index);
	                    editIndex = index;
	                } else {
	                    $(this).datagrid('selectRow', editIndex);
	                }
//	            }
        	} else {
        		$(this).datagrid('selectRow', index);
        	}
        },
        onAfterEdit:function(rowIndex, rowData, changes){
        	if(eval(isDepartmentManager)) {
        		checkDateforedit(rowIndex, rowData, changes);
        	}
        },
        onBeforeEdit:function(rowIndex, rowData){
        	 if(eval(isDepartmentManager)) {
        		 tonBeforeEdit(rowIndex, rowData);
        	 }
        },
        onCheck : function(rowIndex, rowData){
        	if($('#v_allotGroup').attr("checked")) {
        		vallotGroup();
        	}
//        	 $(this).datagrid('beginEdit', rowIndex);
        	queryGroup();
        },
        onUncheck : function(rowIndex, rowData){
        	if($('#v_allotGroup').attr("checked")) {
        		vallotGroup();
        		salesUpdateRow(rowData,$('#workgroupT').datagrid('getRowIndex',rowData),0);
        	}
//        	$(this).datagrid('endEdit', rowIndex);
        	queryGroup();
        },
        onCheckAll : function(rowIndex, rowData){
        	queryGroup();
        },
        onUncheckAll : function(rowData){
        	queryGroup();
        },
        onLoadSuccess : function(rowData){
            if(eval(isDepartmentManager)) {
            	$('#workgroupT').datagrid("hideColumn","obtainQty4use");
            } else {
            	$('#div_a_departmentManager').hide();
            	$('#workgroupT').datagrid("hideColumn","allotNum");
            }
            //刷新座席列表
            queryGroup();
        }
    });
}

//获取可分配数据
var findAvaliableQty= function(){
    $("#allotToGroupBut").show();
    if($("#queryAvaliableForm").form('validate')){

    	//刷新主管列表
        $('#workgroupT').datagrid("reload",{'jobId':jobNum,'campaignId':campaignId,'customerBatch':customerBatch});

        $.post("/task/allocate/findAssigenableNum",{
            campaignId:$("#campaignId").combobox("getValue"),
            jobNum:$("#jobNum").combobox('getValue'),
            customerBatch:$("#customerBatch").combobox('getValue'),
            dataState:$("#dataState").combobox('getValue')
        },function(data){
            if(data.result){
                $('#v_allotNum').html(data.count);
//                $('#v_overplusNum').html(data.count);
            }else{
                $('#v_allotNum').html("");
//                $('#v_overplusNum').html("");
            }

        });
    }

}

var tempValue=0;
var tonBeforeEdit= function(rowIndex, rowData){
	checkAll = $('#workgroupT').datagrid('getChecked');
    var exit= $.inArray(rowData,checkAll);
    //如果选择的行没有check则不允许编辑
    if(exit == -1){
//        rowData.editing = false;
        $('#workgroupT').datagrid('refreshRow', rowIndex);

    }else{
        tempValue= parseInt(rowData.allotNum);
    }

}

//校验分配到组的数据
var checkDateforedit=function(rowIndex, rowData, changes){
//	 $('#workgroupT').datagrid('endEdit', rowIndex);
    //可分配数据
	/*    var assign_data = parseInt($("#v_allotNum").html());
    //修改后的已分配数据
    var assigned_data = 0;
    for(var i=0 ; i<checkAll.length ; i++){
        if(rowIndex != $('#workgroupT').datagrid('getRowIndex',checkAll[i])){
            assigned_data+=  parseInt(checkAll[i].allotNum);
        }
    }

    assigned_data+=parseInt(rowData.allotNum);

    if(assigned_data>assign_data){
        salesUpdateRow(rowData, rowIndex,tempValue);
    }else{
        salesUpdateRow(rowData, rowIndex,changes.allotNum);

        //修改剩余数量
        $('#v_overplusNum').html((assign_data-isNaN(assigned_data)?0:assigned_data));
    }*/
	salesUpdateRow(rowData, rowIndex,rowData.allotNum);
    //

}


var editIndex = undefined;
function endEditing(){
    if (editIndex == undefined){return true}
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
        if(checkAll.length==0){
            $("#v_allotGroup").attr("checked",false);
            window.parent.alertWin("系统提示","请选择坐席组");

        }else{
            equalDivision(parseInt($("#v_allotNum").html()),checkAll.length);
        }

    }
}

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
            salesUpdateRow(checkAll[i],$('#workgroupT').datagrid('getRowIndex',checkAll[i]),1);
        }
    }else{

        for(var i=0 ; i<num ; i++){
            if(i <= complementNum-1){
                salesUpdateRow(checkAll[i],$('#workgroupT').datagrid('getRowIndex',checkAll[i]),wholeNum+1);
                
            }else{
                salesUpdateRow(checkAll[i],$('#workgroupT').datagrid('getRowIndex',checkAll[i]),wholeNum);
            }
        };
    }
    //设置可分配数量为0
//    $("#v_overplusNum").html("0");

}



//更新row
var salesUpdateRow=function(rowData,index,value){
	$('#workgroupT').datagrid('updateRow',{
        index: index,
        row: {allotNum:value}
    });
	var remaining;
	if(!isNaN(rowData.obtainQty)) {
		if(!isNaN(rowData.allotNum)) {
			if(value != "") {
				remaining = parseInt(rowData.obtainQty);//+parseInt(value);
			} else {
				remaining = parseInt(rowData.obtainQty);
			}
		} else {
			remaining = value;
		}
	} else {
		remaining = value;
	}
/*	$('#workgroupT').datagrid('updateRow',{
        index: index,
        row: {overplusNum:remaining}
    });*/
/*    $('#workgroupT').datagrid('updateRow',{
        index: index,
        row: {allotNum:value,overplusNum:value}
    });*/
}




/**
*分配到坐席组
*/
var allotToGroup=function(){
    $("#v_msg").html("");
    $("#allotToGroupBut").hide();
    checkAll=[];
	//assignStrategy
    var is_v_allotGroup= $("#v_allotGroup").attr("checked") ? true : false;
    $('#workgroupT').datagrid('acceptChanges');
    checkAll = $('#workgroupT').datagrid('getChecked');
 
    if(checkAll.length==0 || parseInt($("#v_allotNum").html())==0 ){
        $("#v_allotGroup").attr("checked",false);
       if(parseInt($("#v_allotNum").html())==0){
           window.parent.alertWin("系统提示","没有找到可分配数据");
           $("#allotToGroupBut").show();
           return;
       }else{
           window.parent.alertWin("系统提示","请选择坐席组");
           $("#allotToGroupBut").show();
           return;
       }
    }else{
        //组装数据
        var groupids ="",groupNums="";
        var total= 0;
        $.each(checkAll, function(i,val){
            if (typeof(val.allotNum) != "undefined")
            {
                groupids+=val.grpid+",";
                groupNums+=val.allotNum+",";
                total+=parseInt(val.allotNum);
            }
        });
        var assign_data = parseInt($("#v_allotNum").html());
        if(total > assign_data) {
        	window.parent.alertWin("系统提示","分配数量超标");
            $("#allotToGroupBut").show();
        	return;
        }
        var alloStrategy = $("input[name='assignStrategy']:checked").val();
/*        $.ajax({
        	  type: "POST",
        	  url: "/task/allocate/allotToGroup",
        	  data: {
                  campaignId:$("#campaignId").combobox("getValue"),
                  jobNum:$("#jobNum").combobox('getValue'),
                  customerBatch:$("#customerBatch").combobox('getValue'),
                  dataState:$("#dataState").combobox('getValue'),
                  groupIds:groupids,
                  groupNums:groupNums,
                  alloStrategy:alloStrategy,
        	  },
              success : function(data) {
              	findAvaliableQty();
              }
        	});*/
        $.post("/task/allocate/allotToGroup",{
            campaignId:$("#campaignId").combobox("getValue"),
            jobNum:$("#jobNum").combobox('getValue'),
            customerBatch:$("#customerBatch").combobox('getValue'),
            dataState:$("#dataState").combobox('getValue'),
            groupIds:groupids,
            groupNums:groupNums,
            alloStrategy:alloStrategy
        },
         function(result){
             checkAll=[];
             $("#v_msg").html(result.msg);
             $("#allotToGroupBut").show();
        	findAvaliableQty();
        });


    }
}