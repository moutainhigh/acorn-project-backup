//表格输入是否有效
var isValidTable = true;
var lastIndex;
var lastRow;
var omniNum = 0;
//var acdData = [];

$(function(){
    var ivrData = [];
    var nonData = [];

    $('#assignType').combobox({
        onSelect:function(record){

            //acd group
            /*
            if(record.id ==1){ //IVR
                if(ivrData.length > 0){
                    resetAcdGroupss(ivrData);
                } else {
                    $.post("/acdgroup/lookup",{ ivr: true}, function(data){
                        ivrData = data;
                        resetAcdGroupss(ivrData);
                    });
                }
            } else {
                if(nonData.length > 0){
                    resetAcdGroupss(nonData);
                } else {
                    $.post("/acdgroup/lookup",{ ivr: false}, function(data){
                        nonData = data;
                        resetAcdGroupss(nonData);
                    });
                }
            }
            */
            $('#v_allotNum').html("0");
            $('#v_overplusNum').html("0");
            
            $('#agentPanel').show();
        	$('#distrBtn').show();
        	$('#assignToDept').hide();
        	$('#totalAssignQtyBtn').show();
        	
        	var _obData = [{'id':'', 'text':'=====请选择====='},{'id':'A', 'text':'A [0~20s]'}, {'id':'B', 'text':'B (20-180)'}, {'id':'C', 'text':'C [180,∞)'}];
        	$("#timeLength").combobox('clear').combobox('loadData', _obData);
        	
        	$('#deptAvailableQtyLable').hide();
        	$('#deptAvailableQty').html('0');

            if(record.id ==1){ //IVR
                $('#distrBtn').hide();
                $('#v_allotGroup').parent().hide();
                $("#show_timeLength").css("display","none");
                $("#show_priority").css("display","none");
                $('#workgroupT').datagrid("hideColumn", "grpqty");
                $('#workgroupT').datagrid("hideColumn", "allotNum");
                $('#workgroupT').datagrid("hideColumn", "overplusNum");

            }else if(record.id ==2){
                $('#distrBtn').hide();
                $('#v_allotGroup').parent().hide();
                $("#show_timeLength").css("display","none");
                $("#show_priority").css("display","none");
                $('#workgroupT').datagrid("hideColumn", "grpqty");
                $('#workgroupT').datagrid("hideColumn", "allotNum");
                $('#workgroupT').datagrid("hideColumn", "overplusNum");
            }else if(record.id ==3){
                $('#distrBtn').show();
                $('#v_allotGroup').parent().show();
                $("#show_timeLength").css("display","block");
                $("#show_priority").css("display","none");
                $('#workgroupT').datagrid("showColumn", "grpqty");
                $('#workgroupT').datagrid("showColumn", "allotNum");
                $('#workgroupT').datagrid("hideColumn", "overplusNum");
                $('#totalAssignQtyBtn').hide();
                
                if(deptAssignRight) {
                	$('#deptAvailableQtyLable').show();
                	//$('#assignToDept').show();
                }else{
                	$('#assignToDept').hide();
                }
                
                //如果是经理权限则隐藏分配到坐席datagrid
                if(isDepartmentManager) {
                	//$('#agentPanel').hide();
                	$('#distrBtn').show();
                	$('#workgroupT').datagrid('resize');
                }else{
                	$('#agentPanel').show();
                	$('#distrBtn').hide();
                }
                
                //如果有分配到坐席的权限，则显示分配坐席的datagrid
                if(isSupervisor) {
                	$('#agentPanel').show();
                }else{
                	$('#agentPanel').hide();
                }
                
                var _assignType = $('#assignType').combobox('getValue');
                if('3'==_assignType) {
                	//var ibData = [{'id':'Snatch-In', 'text':'Snatch-In'},{'id':'A', 'text':'A [0~20s]'}, {'id':'B', 'text':'B (20-180)'}, {'id':'C', 'text':'C [180,∞)'}];
                    var ibData = [{'id':'A', 'text':'A [0~20s]'}, {'id':'B', 'text':'B (20-180)'}, {'id':'C', 'text':'C [180,∞)'}];
                	$("#timeLength").combobox('clear').combobox('loadData', ibData);
                }
            }else if(record.id ==4){
                $('#distrBtn').hide();
                $('#v_allotGroup').parent().hide();
                $('#workgroupT').datagrid("hideColumn", "grpqty");
                $('#workgroupT').datagrid("hideColumn", "allotNum");
                $('#workgroupT').datagrid("hideColumn", "overplusNum");
            }else if(record.id ==5){
                $('#distrBtn').hide();
                $('#v_allotGroup').parent().hide();
                $('#workgroupT').datagrid("hideColumn", "grpqty");
                $('#workgroupT').datagrid("hideColumn", "allotNum");
                $('#workgroupT').datagrid("hideColumn", "overplusNum");
                inintCallback();
            }else if(record.id == 11) {
                //IVR-井星
                $('#distrBtn').hide();
                $('#v_allotGroup').parent().hide();
                $('#workgroupT').datagrid("hideColumn", "grpqty");
                $('#workgroupT').datagrid("hideColumn", "allotNum");
                $('#workgroupT').datagrid("hideColumn", "overplusNum");
                $("#show_timeLength").css("display","none");
                $("#show_priority").css("display","none");
            }else if(record.id == 12) {
               //放弃-井星
                $('#distrBtn').hide();
                $('#v_allotGroup').parent().hide();
                $('#workgroupT').datagrid("hideColumn", "grpqty");
                $('#workgroupT').datagrid("hideColumn", "allotNum");
                $('#workgroupT').datagrid("hideColumn", "overplusNum");
                $("#show_timeLength").css("display","none");
                $("#show_priority").css("display","none");
            }else if(record.id == 13) {
                //接通-井星
                $('#distrBtn').hide();
                $('#v_allotGroup').parent().hide();
                $('#workgroupT').datagrid("hideColumn", "grpqty");
                $('#workgroupT').datagrid("hideColumn", "allotNum");
                $('#workgroupT').datagrid("hideColumn", "overplusNum");

                $("#show_timeLength").css("display","inline-block");
                $("#show_priority").css("display","none");
            }else if(record.id == 15) {
                //callback-接通
                $('#distrBtn').hide();
                $('#v_allotGroup').parent().hide();
                $('#workgroupT').datagrid("hideColumn", "grpqty");
                $('#workgroupT').datagrid("hideColumn", "allotNum");
                $('#workgroupT').datagrid("hideColumn", "overplusNum");
                $("#show_timeLength").css("display","none");
                $("#show_priority").css("display","block");
            }

            $('#workgroupT').datagrid("reload",{'callType':record.id});
        }
    });
    
    $('#g_level').combobox({
    	multiple:true,
        onSelect:function(rec){
            var opts = $(this).combobox('options');
            var checkbox = $('#_'+rec[opts.valueField]+'_g_level');
            checkbox.attr('checked',true);
        },
        onUnselect:function(rec){
            var opts = $(this).combobox('options');
            var checkbox = $('#_'+rec[opts.valueField]+'_g_level');
            checkbox.removeAttr('checked');
        },
        formatter: function(row){
            var opts = $(this).combobox('options');
            return '<input id="_'+ row[opts.valueField] +'_g_level" class="v_bottom" type="checkbox"/>'+row[opts.textField];
        }
    });
    $('#g_level').combobox("clear");

    $('#workgroupT').datagrid({
        url:'/traffic/getValidGroup',
        height : 150,
        fitColumns : true,
        scrollbarSize:-1,
//        remoteSort : false,
//        singleSelect : false,
        checkOnSelect: false,
        selectOnCheck: false,
        queryParams : {
            'callType' : $('#assignType').combobox('getValue')
        },
        rownumbers:true,
        remoteSort:false,
        columns:[[
            {field:'ck',checkbox:true,width:20},
            {field:'grpid',title:'坐席组ID',width:180,align:'center',fixed: true},
            {field:'deptname',title:'部门名称',width:200,align:'center',fixed: true},
            {field:'grpname',title:'坐席组名称',width:220,align:'center',fixed: true},
            {field:'grpqty',title:'可分配数',width:220,align:'center',fixed: true, hidden: true},
            {field:'allotNum',title:'分配数量',width:220,align:'center',fixed: true, hidden: true,
                editor:{type:'numberbox',options:{min: 0}}

                },
            {field:'overplusNum',title:'剩余数量',width:220,align:'center',fixed: true, hidden: true }
        ]],
        onClickCell: function(index,field,value){
            if(index==0){
                $(this).datagrid('endEdit', editIndex);
                $(this).datagrid('beginEdit', index);
            }
            if (editIndex != index){
                $(this).datagrid('endEdit', editIndex);
                $(this).datagrid('beginEdit', index);
            }else{
                $(this).datagrid('beginEdit', index);
            }
            editIndex = index;
            var editors = $(this).datagrid('getEditors', index);
            editors[0].target.bind('blur',function () {
                $(this).hide();
                $('#workgroupT').datagrid('updateRow',{
                    index: index,
                    row: {
                        allotNum: editors[0].target.val()
                    }
                });
            }).focus();
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
            cleanRowData(rowIndex, rowData);
        }
//        ,onCheckAll:function(){                        x
//            $('#workgroupT').datagrid("reload");
//
//        }

    });

    $('#agentTb').datagrid({
        url:'/callbackAssign/queryAvaliableAgent',
        height : 400,
		fitColumns : true,
//        scrollbarSize:-1,
		remoteSort : false,
        multiSort: true,
//		singleSelect : true,
        checkOnSelect: false,
        selectOnCheck: false,
		pagination : false,
        rownumbers:true,
        queryParams : {
//            'lowDate' : $('#g_beginDate').datetimebox('getValue'),
//            'highDate' : $('#g_endDate').datetimebox('getValue'),
//            'level' : $('#g_level').combobox('getValue'),
//            'agentId' : $('#g_agentId').val(),
//            'groups' : null,
//            'cType' : $('#assignType').combobox('getValue')
        	'initLoad' : false
        },
        onBeforeLoad : function(param){
			if(param.initLoad){
				return true;
			}
			return false;
		},
        columns:[[
            {field:'ck',checkbox:true,width:20},
            {field:'userWorkGrpName',sortable:true,title:'工作组',width:90,align:'center'},
            {field:'userId',sortable:true,title:'座席工号',width:40,align:'center'},
            {field:'userName',sortable:true,title:'座席姓名',width:40,align:'center'},
            {field:'levelId',sortable:true,title:'座席等级(00:00-08:00)',width:80,align:'center'},
            {field:'levelId2',sortable:true,title:'座席等级(08:00-18:00)',width:80,align:'center'},
            {field:'levelId3',sortable:true,title:'座席等级(18:00-24:00)',width:80,align:'center'},
            {field:'unitcost4',sortable:true,title:'已分配/已执行',width:80,align:'center',
            	formatter : function(val, row){
            		var rs = '';
            		if(null != row){
            			rs = row.intradayAllocatedCount + "/" + row.intradayExecutedCount;
            		}
            		return rs;
            }},
            {field:'assignCount',sortable:true,title:'分配数量',width:110,align:'center', editor:{type:'numberbox',options:{min: 0}}}
        ]],
        onClickCell: function(index, row){
            if(index==0){
                $(this).datagrid('endEdit', editIndex);
                $(this).datagrid('beginEdit', index);
            }
        	if (lastIndex != index){
				$(this).datagrid('endEdit', lastIndex);
				$(this).datagrid('beginEdit', index);
			}else{
                $(this).datagrid('beginEdit', index);
            }
			lastIndex = index;
            var editors_tb = $(this).datagrid('getEditors', index);
            editors_tb[0].target.bind('blur',function () {
                $(this).hide();
                $('#agentTb').datagrid('updateRow',{
                    index: index,
                    row: {
                        assignCount: editors_tb[0].target.val()
                    }
                });
            }).focus();
	    	$('#assignOperate').hide();
        },
        onCheck : function(rowIndex, rowData){
        	calcAverageCount();
        	$('#assignOperate').hide();
             if(rowData){
                omniNum++;
                $('#omniNum').text(omniNum);
            }
        },
        onUncheck : function(rowIndex, rowData){
        	calcAverageCount();
        	clearRowData(rowIndex, false);
        	$('#assignOperate').hide();
            omniNum--;
            $('#omniNum').text(omniNum);
        },
        onSortColumn:function(){
            omniNum = 0;
            $('#omniNum').text(omniNum);
        },
        onCheckAll : function(rowIndex, rowData){
        	calcAverageCount();
        	$('#assignOperate').hide();
        },
        onUncheckAll : function(rowData){
        	calcAverageCount();
        	clearRowData(rowData, true);
        	$('#assignOperate').hide();
            omniNum = 0;
        }
    }).datagrid('enableEx').datagrid('enableNumEx');

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

    $('#g_allRange').click(function(){
        if($(this).is(':checked')){
            $('#g_beginDate').datetimebox('setValue', '');
            $('#g_endDate').datetimebox('setValue', '');
        }
    });


    $("#tbTotalNum").change(function(){
        $("#assignOperate").hide();

        /*
        if($.isNumeric($("#v_allotNum").html()) &&
           $.isNumeric($("#tbTotalNum").val())){
            $("#v_overplusNum").html(parseFloat($("#v_allotNum").html()) - parseFloat($("#tbTotalNum").val()));
        } else {
            $("#v_overplusNum").html($("#v_allotNum").html());
        }
        */
    });

    
    //平均分配到坐席组
    $('#g_isAverage').click(calcAverageCount);


    $('#v_priority').combobox({
        multiple:true,
        onSelect:function(rec){
            var opts = $(this).combobox('options');
            var checkbox = $('#_'+rec[opts.valueField]+'_v_priority');
            checkbox.attr('checked',true);
        },
        onUnselect:function(rec){
            var opts = $(this).combobox('options');
            var checkbox = $('#_'+rec[opts.valueField]+'_v_priority');
            checkbox.removeAttr('checked');
        },
        formatter: function(row){
            var opts = $(this).combobox('options');
            return '<input id="_'+ row[opts.valueField] +'_v_priority" class="v_bottom" type="checkbox"/>'+row[opts.textField];
        }
    });
    $('#v_priority').combobox("clear");
    $('#log-priority').combobox({
        multiple:true,
        onSelect:function(rec){
            var opts = $(this).combobox('options');
            var checkbox = $('#_'+rec[opts.valueField]+'_log_priority');
            checkbox.attr('checked',true);
        },
        onUnselect:function(rec){
            var opts = $(this).combobox('options');
            var checkbox = $('#_'+rec[opts.valueField]+'_log_priority');
            checkbox.removeAttr('checked');
        },
        formatter: function(row){
            var opts = $(this).combobox('options');
            return '<input id="_'+ row[opts.valueField] +'_log_priority" class="v_bottom" type="checkbox"/>'+row[opts.textField];
        }
    });
    $('#log_priority').combobox("clear");


    $('#acdGroup').combobox({
        url: '/acdinfo/lookup',
        valueField:'acd',
        textField:'acd2',
        multiple:true,
        onSelect:function(rec){
            var opts = $(this).combobox('options');
            var checkbox = $('#_'+rec[opts.valueField]+'_acdgroup');
            checkbox.attr('checked',true);
        },
        onUnselect:function(rec){
            var opts = $(this).combobox('options');
            var checkbox = $('#_'+rec[opts.valueField]+'_acdgroup');
            checkbox.removeAttr('checked');
        },
        formatter: function(row){
            var opts = $(this).combobox('options');
            var mpn =  row.mediaProdName; //findMediaProdName(row[opts.textField]);
            return '<div style="white-space: nowrap;text-overflow: ellipsis;overflow: hidden;" title="'+ (mpn?mpn:"") +'"><input id="_'+ row[opts.valueField] +'_acdgroup" class="v_bottom" type="checkbox"/>'+row[opts.textField] +(mpn ? " ("+mpn+") ":"")+"</div>";
        },
        onShowPanel:function(){
                $(this).combo('panel').width(248).parent().width(250);
        }
    });




//    $.post("/acdinfo/lookup",function(data){
//        acdData = data;
//    });

    var now = new Date();
//    $('#incomingLowDate').datetimebox('setValue', now.getFullYear()+"-"+(now.getMonth() + 1)+"-"+(now.getDay() - 2)+" 00:00:00");
//    $('#incomingHighDate').datetimebox('setValue',
//        now.getFullYear()+"-"+(now.getMonth() + 1)+"-"+now.getDay()+" "+
//        now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds());
    $('#tabs').tabs({
        tabPosition:'left',
        fit:true
    }).tabs('resize');
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

var resetAcdGroupss = function(data) {
    $("#acdGroup").combobox({
        data: data,
        valueField:'acdId',
        textField:'acdId'
    });
    $("#acdGroup").combobox("clear");
}
//
//var findMediaProdName = function(acd){
//    for(var i = 0; i< acdData.length; i++){
//        if(acdData[i].acd == acd){
//            return acdData[i].mediaProdName;
//        }
//    }
//}

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
//            window.parent.alertWin("系统提示","请选择坐席组");
            $.messager.alert("系统提示","请选择坐席组")

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

var cleanRowData=function(rowIndx, rowData){
    $('#workgroupT').datagrid('updateRow',{
        index: rowIndx,
        row: {allotNum:'',overplusNum:''}
    });

    redoRowData(rowIndx);
    
    callback.clearAgentList(rowData.grpid);
};


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
        if($.isNumeric($("#tbTotalNum").val())){
            assigned_data += parseFloat($("#tbTotalNum").val());
        } else {
            for(var i=0 ; i<checkAll.length ; i++){
                assigned_data+= isNaN(parseInt(checkAll[i].allotNum))?0:parseInt(checkAll[i].allotNum);
                if(rowIndx == $('#workgroupT').datagrid('getRowIndex',checkAll[i])){
                    salesUpdateRow(rowIndx,isNaN(parseInt(checkAll[i].allotNum))?'':parseInt(checkAll[i].allotNum));
                }
            }
        }
        console.info("assign_data:"+assign_data);
        console.info("assigned_data:"+assigned_data);
    }
    if(assign_data>assigned_data)$('#v_overplusNum').html(isNaN(assign_data-assigned_data)?0:assign_data-assigned_data);

}

function findCallAbandonCount(){
    $('#queryAvaliableQty').attr('disabled',true);
    $.post("/call/abandon/count",{
        callType: 2,
        priority:$("#v_priority").combobox("getValues").join(','), //通话时长
        startDate:$("#incomingLowDate").datetimebox('getValue'),//开始时间
        endDate:$("#incomingHighDate").datetimebox('getValue'),    //结束时间
        acdGroup:$("#acdGroup").combobox("getValues").join(','),    //ACD组
        dnis:$("#dnis").val(),    //被叫号码
        allocatedNumbers:$("#allocatedNumbers").combobox("getValue")    //数据状态
    },function(data){
        if(data){
            $('#v_allotNum').html(data.total);
            $('#v_overplusNum').html(data.total);
        }else{
            $('#v_allotNum').html("");
            $('#v_overplusNum').html("");
        }
        $('#queryAvaliableQty').removeAttr('disabled');
    });
}

function findCallDetailCount(){
    $('#queryAvaliableQty').attr('disabled',true);
    $.post("/call/details/count",{
        callType: 1,
        priority:$("#v_priority").combobox("getValues").join(','), //通话时长
        startDate:$("#incomingLowDate").datetimebox('getValue'),//开始时间
        endDate:$("#incomingHighDate").datetimebox('getValue'),    //结束时间
        acdGroup:$("#acdGroup").combobox("getValues").join(','),    //ACD组
        dnis:$("#dnis").val(),    //被叫号码
        allocatedNumbers:$("#allocatedNumbers").combobox("getValue")    //数据状态
    },function(data){
        if(data){
            $('#v_allotNum').html(data.total);
            $('#v_overplusNum').html(data.total);
        }else{
            $('#v_allotNum').html("");
            $('#v_overplusNum').html("");
        }
        $('#queryAvaliableQty').removeAttr('disabled');
    });
}

function wilcomfindIvrCount(){
    $('#queryAvaliableQty').attr('disabled',true);
    $.post("/wilcom/ivr/count",{
        callType: 11,
        priority:$("#v_priority").combobox("getValues").join(','), //通话时长
        startDate:$("#incomingLowDate").datetimebox('getValue'),//开始时间
        endDate:$("#incomingHighDate").datetimebox('getValue'),    //结束时间
        acdGroup:$("#acdGroup").combobox("getValues").join(','),    //ACD组
        dnis:$("#dnis").val(),    //被叫号码
        allocatedNumbers:$("#allocatedNumbers").combobox("getValue")    //数据状态
    },function(data){
        if(data){
            $('#v_allotNum').html(data.total);
            $('#v_overplusNum').html(data.total);
        }else{
            $('#v_allotNum').html("");
            $('#v_overplusNum').html("");
        }
        $('#queryAvaliableQty').removeAttr('disabled');
    });
}

function wilcomfindAbandonCount(){
    $('#queryAvaliableQty').attr('disabled',true);
    $.post("/wilcom/abandon/count",{
        callType: 12,
        priority:$("#v_priority").combobox("getValues").join(','), //通话时长
        startDate:$("#incomingLowDate").datetimebox('getValue'),//开始时间
        endDate:$("#incomingHighDate").datetimebox('getValue'),    //结束时间
        acdGroup:$("#acdGroup").combobox("getValues").join(','),    //ACD组
        dnis:$("#dnis").val(),    //被叫号码
        allocatedNumbers:$("#allocatedNumbers").combobox("getValue")    //数据状态
    },function(data){
        if(data){
            $('#v_allotNum').html(data.total);
            $('#v_overplusNum').html(data.total);
        }else{
            $('#v_allotNum').html("");
            $('#v_overplusNum').html("");
        }
        $('#queryAvaliableQty').removeAttr('disabled');
    });
}

function wilcomfindCallbackCount(){
    $('#queryAvaliableQty').attr('disabled',true);
    $.post("/wilcom/callback/count",{
        callType: 15,
        priority:$("#v_priority").combobox("getValues").join(','), //通话时长
        startDate:$("#incomingLowDate").datetimebox('getValue'),//开始时间
        endDate:$("#incomingHighDate").datetimebox('getValue'),    //结束时间
        acdGroup:$("#acdGroup").combobox("getValues").join(','),    //ACD组
        dnis:$("#dnis").val(),    //被叫号码
        allocatedNumbers:$("#allocatedNumbers").combobox("getValue")    //数据状态
    },function(data){
        if(data){
            $('#v_allotNum').html(data.total);
            $('#v_overplusNum').html(data.total);
        }else{
            $('#v_allotNum').html("");
            $('#v_overplusNum').html("");
        }
        $('#queryAvaliableQty').removeAttr('disabled');
    });
}

var findCallbackCount=function(){
    $('#workgroupT').datagrid('reload');
    $('#queryAvaliableQty').attr('disabled',true);
    $.post("/traffic/findCallback",{
        priority:$("#v_priority").combobox("getValues").join(','), //通话时长
        sdt:$("#incomingLowDate").datetimebox('getValue'),//开始时间
        edt:$("#incomingHighDate").datetimebox('getValue'),    //结束时间
        acd:$("#acdGroup").combobox("getValues").join(','),    //ACD组
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
        cleanRowData(rowIndex,tempValue);
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
var allotCallbackToPerson =function(calltype, users, index){
    //window.parent.printLog('分配到坐席：'+users);

    var rows = $('#agentTb').datagrid('getChecked');

    $.post("/traffic/allotCallbackToPerson",{
        callType: calltype,
        priority:$("#v_priority").combobox("getValues").join(','), //通话时长
        sdt:$("#incomingLowDate").datetimebox('getValue'),//开始时间
        edt:$("#incomingHighDate").datetimebox('getValue'),    //结束时间
        acd:$("#acdGroup").combobox("getValues").join(','),    //ACD组
        dnis:$("#dnis").val(),    //被叫号码
        allocatedNumbers:$("#allocatedNumbers").combobox("getValue"),    //数据状态
        users:JSON.stringify([users[index]])
    },function(data){
        if(++index < users.length){
            allotCallbackToPerson(calltype, users, index);
        } else {
            if(data.result) callback.assignReload();
            $.messager.alert("系统提示",data.msg);
        }
    });

};


//分配到坐席
var allotDataToPerson =function(calltype, url, users, index, len){
    len = Math.min(len, users.length-index);

    var rows = $('#agentTb').datagrid('getChecked');
    $.post(url,{
        callType: calltype,
        priority:$("#v_priority").combobox("getValues").join(','), //通话时长
        startDate:$("#incomingLowDate").datetimebox('getValue'),//开始时间
        endDate:$("#incomingHighDate").datetimebox('getValue'),    //结束时间
        acdGroup:$("#acdGroup").combobox("getValues").join(','),    //ACD组
        dnis:$("#dnis").val(),    //被叫号码
        allocatedNumbers:$("#allocatedNumbers").combobox("getValue"),    //数据状态
        users:JSON.stringify(users.slice(index, index + len))
    },function(data){
        index += len;
        if(index < users.length){
            if(users.length > 0){
                $("#loader-msg").html("("+parseInt(index * 100 / users.length) + "%)");
            } else {
                $("#loader-msg").html("");
            }
            allotDataToPerson(calltype, url, users, index, len);
        } else {
            $("#loader-msg").html("");
            if(data.result) callback.assignReload();
            $.messager.alert("系统提示",data.msg);
        }
    });
};


//分配到坐席
var allotIvrToPerson =function(users){
    var rows = $('#agentTb').datagrid('getChecked');
    $.post("/call/details/assign",{
        callType: 1,
        priority:$("#v_priority").combobox("getValues").join(','), //通话时长
        startDate:$("#incomingLowDate").datetimebox('getValue'),//开始时间
        endDate:$("#incomingHighDate").datetimebox('getValue'),    //结束时间
        acdGroup:$("#acdGroup").combobox("getValues").join(','),    //ACD组
        dnis:$("#dnis").val(),    //被叫号码
        allocatedNumbers:$("#allocatedNumbers").combobox("getValue"),    //数据状态
        users:users
    },function(data){
        if(data.result){
            callback.assignReload();
        }
        $.messager.alert("系统提示",data.msg);
    });
};

var allotAbandonToPerson =function(users){
    var rows = $('#agentTb').datagrid('getChecked');
    $.post("/call/abandon/assign",{
        callType: 2,
        priority:$("#v_priority").combobox("getValues").join(','), //通话时长
        startDate:$("#incomingLowDate").datetimebox('getValue'),//开始时间
        endDate:$("#incomingHighDate").datetimebox('getValue'),    //结束时间
        acdGroup:$("#acdGroup").combobox("getValues").join(','),    //ACD组
        dnis:$("#dnis").val(),    //被叫号码
        allocatedNumbers:$("#allocatedNumbers").combobox("getValue"),    //数据状态
        users:users
    },function(data){
        if(data.result){
            callback.assignReload();
        }
        $.messager.alert("系统提示",data.msg);
    });
};

//分配到坐席-井星数据
var allotWilcomIvrToPerson =function(users){
    var rows = $('#agentTb').datagrid('getChecked');
    $.post("/wilcom/ivr/assign",{
        callType: 11,
        priority:$("#v_priority").combobox("getValues").join(','), //通话时长
        startDate:$("#incomingLowDate").datetimebox('getValue'),//开始时间
        endDate:$("#incomingHighDate").datetimebox('getValue'),    //结束时间
        acdGroup:$("#acdGroup").combobox("getValues").join(','),    //ACD组
        dnis:$("#dnis").val(),    //被叫号码
        allocatedNumbers:$("#allocatedNumbers").combobox("getValue"),    //数据状态
        users:users
    },function(data){
        if(data.result){
            callback.assignReload();
        }
        $.messager.alert("系统提示",data.msg);
    });

};

//分配到坐席-井星数据
var allotWilcomAbandonToPerson =function(users){
    var rows = $('#agentTb').datagrid('getChecked');
    $.post("/wilcom/abandon/assign",{
        callType: 12,
        priority:$("#v_priority").combobox("getValues").join(','), //通话时长
        startDate:$("#incomingLowDate").datetimebox('getValue'),//开始时间
        endDate:$("#incomingHighDate").datetimebox('getValue'),    //结束时间
        acdGroup:$("#acdGroup").combobox("getValues").join(','),    //ACD组
        dnis:$("#dnis").val(),    //被叫号码
        allocatedNumbers:$("#allocatedNumbers").combobox("getValue"),    //数据状态
        users:users
    },function(data){
        if(data.result){
            callback.assignReload();
        }
        $.messager.alert("系统提示",data.msg);
    });
};

//分配到坐席-井星数据
var allotWilcomCallbackToPerson =function(users){
    var rows = $('#agentTb').datagrid('getChecked');
    $.post("/wilcom/callback/assign",{
        callType: 15,
        priority:$("#v_priority").combobox("getValues").join(','), //通话时长
        startDate:$("#incomingLowDate").datetimebox('getValue'),//开始时间
        endDate:$("#incomingHighDate").datetimebox('getValue'),    //结束时间
        acdGroup:$("#acdGroup").combobox("getValues").join(','),    //ACD组
        dnis:$("#dnis").val(),    //被叫号码
        allocatedNumbers:$("#allocatedNumbers").combobox("getValue"),    //数据状态
        users:users
    },function(data){
        if(data.result){
            callback.assignReload();
        }
        $.messager.alert("系统提示",data.msg);
    });
};



/*===================================	话务分配	===================================*/


//平均计算分配数量
function calcAverageCount(){
    var blockCheckable = $('#agentTb').attr("blockCheckable");
    if(blockCheckable && blockCheckable == '1') return;

	var isAverage = $('#g_isAverage').is(':checked');
	if(!isAverage){
		return;
	}

    var assignType = $('#assignType').combobox('getValue');
    if(assignType != 3){ //接通
        calcAverageCount3();
    } else {
        calcAverageCount2();
    }

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
        //init
        for(var i = 0; i < agentRows.length; i++){
            $(agentRows[i]).attr("assignCount", 0);
        }
        groupDistribute(groupRows, agentRows);
        //refresh row
        for(var i = 0; i < agentRows.length; i++){
            var rowIndex = $('#agentTb').datagrid('getRowIndex',agentRows[i]);
            if(rowIndex > -1){
                $('#agentTb').datagrid("refreshRow", rowIndex);
            }
        }
    }
}

function groupDistribute(groupRows, agentRows){
    for(var i = 0; i < groupRows.length; i++){
        userDistribute(groupRows[i], agentRows);
    }
}

function userDistribute(groupRow, agentRows){
    var allotNum = $(groupRow).attr("grpqty");
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
                   $(agentRow).attr("assignCount", assignCount + 1);
                   newNum--;
                   //分配完毕
                   if(newNum == 0) return;
               }
           }
           //没有同组坐席
           if(newNum == allotNum) return;
       }
    }
}

function calcAverageCount3(){

    var assignNum = parseInt($("#v_allotNum").html());
    var totalNum = $("#tbTotalNum").val();
    if(totalNum < 1 || totalNum > assignNum){
        $.messager.alert("系统提示","当前分配数量["+totalNum+"]不能超过可分配数量["+assignNum+"]");
        return;
    }

    var agentRows = $('#agentTb').datagrid('getChecked');
    if(agentRows.length > 0){
        //init
        for(var i = 0; i < agentRows.length; i++){
            $(agentRows[i]).attr("assignCount", 0);
        }
        userGroupDistribute(totalNum, agentRows);
        //refresh row
        for(var i = 0; i < agentRows.length; i++){
            var rowIndex = $('#agentTb').datagrid('getRowIndex',agentRows[i]);
            if(rowIndex > -1){
                $('#agentTb').datagrid("refreshRow", rowIndex);
            }
        }
    }
}

function userGroupDistribute(allotNum, agentRows){
    var newNum = allotNum;
    for(var index = 0; index < agentRows.length; index++){
        var agentRow = agentRows[index];
        var assignCount =  $(agentRow).attr("assignCount");
        if(!$.isNumeric(assignCount)) assignCount = 0;
        $(agentRow).attr("assignCount", assignCount + 1);
        newNum--;
        if(newNum == 0) break;
    }
    if(newNum > 0 && newNum < allotNum){
        userGroupDistribute(newNum, agentRows);
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
var URL_QUERY_IVR_CONNECTED_QTY = ctx + '/callbackAssign/ivr/connected/queryAvaliableQty';

var URL_GROUP_ASSIGN = ctx + '/callbackAssign/assignToGroup';
var URL_AGENT_ASSIGN = ctx + '/callbackAssign/assignToAgent';
var URL_IVR_CONNECTED_AGENT_ASSIGN = ctx + '/callbackAssign/ivr/connected/assignToAgent';

//查询可分配数量
callback.queryAvaliableQty = function(){
	
	var _valid = $('#queryAvaliableForm').form('validate');
	if(!_valid) {
		return false;
	}
	
	var lowDate = $('#incomingLowDate').datetimebox('getValue');
	var highDate = $('#incomingHighDate').datetimebox('getValue');
    if(highDate > lowDate) {
    	var diff = Date.parse(highDate) - Date.parse(lowDate);
        if((diff/1000/3600/24) > 3){
        	$.messager.alert('错误', '呼入开始时间跨度不能超过3天！');
        	return;
        }
    }else{
    	$.messager.alert('错误', '呼入开始时间不能大于呼入结束时间！');
    	return;
    }

	var _assignType = $('#assignType').combobox('getValue');
    if(_assignType == 1){ //IVR
        findCallDetailCount();
    }
    else if(_assignType == 2){ //放
        findCallAbandonCount();
    }
	else if(_assignType == 3){ //接通
		this.queryLeadInteractionQty();
	}else if(_assignType == 5){
        findCallbackCount();
    }
    else if(_assignType == 11){
        wilcomfindIvrCount();
    } else if(_assignType == 12){
        wilcomfindAbandonCount();
    }else if(_assignType == 13) {
    	findIvrConnectedQty();
    }
    else if(_assignType == 15) {
        wilcomfindCallbackCount();
    }
};

//查询分配类型为‘接通’的分配数量
callback.queryLeadInteractionQty = function(){
	
	var data = this.prepareParam(3);
	
	//查询组的可分配数量
	this.queryQtyByParam(data, URL_QUERY_LEAD_INTERACTION_QTY);
	
	ivrType = $('#timeLength').combobox('getValue');
	_data = callback.prepareParam(3);
	_data += '&ivrType='+ivrType;
	
	if(null==ivrType || ''==ivrType) {
		return;
	}
	
	//查询部门的可分配数量
	$.ajax({
        //global: false,
		url : ctx + '/callbackAssign/queryDeptAvaliableQty',
		type : 'POST',
		data : _data,
        beforeSend:function(){
            $('#queryAvaliableQty').attr('disabled',true);
        },
        complete: function() {
        	 $('#queryAvaliableQty').removeAttr('disabled');
        },
		success : function(rs){
			var qty = Number(rs.qty);
			$('#deptAvailableQty').html(qty);
			if(qty > 0) {
				if(deptAssignRight) {
					$('#assignToDept').show();	
				}
			}else{
				$('#assignToDept').hide();
			}
		},
		error: function() {
			$.messager.alert('错误', '网络超时或会话错误');
			console.error('网络超时或会话错误');
		}
	});
};

//处理表单数据
callback.prepareParam = function (type){
	var data = $('#queryAvaliableForm').serialize();
	if(type == 3 || type == 13){
		var timeLength = $('#timeLength').combobox('getValue');
		var lowLength = -1;
		var highLength = -1;
		var callType = '';
		if('A' == timeLength){
			lowLength = 0;
			highLength = 20;
		}else if('B' == timeLength){
			lowLength = 20;
			highLength = 180;
		}else if('C' == timeLength){
			lowLength = 180;
			highLength = -1;
		}else if('Snatch-In' == timeLength && type == 3) {
			lowLength = -1;
			highLength = -1;
			callType = 'Snatch In';
		}
		
		data += '&lowCallDuration=' + lowLength;
		data += '&highCallDuration=' + highLength;
		if(type == 3) {
			data += '&callType=' + callType;	
		}
	}

	return data;
};

callback.queryQtyByParam = function(_data, _url){
	$.ajax({
        //global: false,
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
    var win = parent != null ? parent : window;
    var _assignType = $('#assignType').combobox('getValue');
    var isAverage = $('#isAverage').is(':checked');
    var rows = $('#agentTb').datagrid('getChecked');
    
    if(rows.length==0){
        $.messager.alert('错误', '请选择要分配的坐席！') ;
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
        $.messager.alert('错误', '坐席['+rows[i].userId+']分配数量不能为0！') ;
        return;

    }
    if(_assignType == 1){  //IVR
        //allotIvrToPerson(JSON.stringify(assignArr));
        allotDataToPerson(1, "/call/details/assign", assignArr, 0, 10);
    }
    else if(_assignType == 2){  //放弃
        //allotAbandonToPerson(JSON.stringify(assignArr));
        allotDataToPerson(2, "/call/abandon/assign", assignArr, 0, 10);
    }
    else if(_assignType == 3){  //接通
       
        var obj = {};
        obj['agentUsersStr'] = JSON.stringify(assignArr);
        var data = this.prepareParam(3, data);
        data += '&' + $.param(obj);
        data += '&averageAssign=' + isAverage;

        this.assignOperate(data, URL_AGENT_ASSIGN);
    }else if(_assignType == 5){
        //win.printLog('分配到坐席：_assignType=5');
        //allotCallbackToPerson(JSON.stringify(assignArr));
        allotCallbackToPerson(5, assignArr, 0);
    }
    else if(_assignType == 11){  //IVR-井星
        allotWilcomIvrToPerson(JSON.stringify(assignArr));
    }
    else if(_assignType == 12){  //放弃-井星
        allotWilcomAbandonToPerson(JSON.stringify(assignArr));
    }
    else if(_assignType == 15){  //Callback-井星
        allotWilcomCallbackToPerson(JSON.stringify(assignArr));
    }
    else if(_assignType == 13) {
    	var data = this.prepareParam(3, data, assignArr);
    	callback.assignToAgentConnIvr(data, url);
    	/*
    	var obj = {};
        obj['agentUsersStr'] = JSON.stringify(assignArr);
        var data = this.prepareParam(3, data);
        data += '&' + $.param(obj);

        this.assignOperate(data, URL_IVR_CONNECTED_AGENT_ASSIGN);
        */	
    }
};

callback.assignOperate = function(_data, _url){
	$.ajax({
        global: false,
		url : _url,
		type : 'POST',
		data : _data,
		beforeSend : showLoading,
		complete : hideLoading,
		success : function(rs){
			if(eval(rs.success)){
				var msg = '分配成功<br>';
				
				if(undefined != rs.batchId && null != rs.batchId) {
					msg += '批次号：' + rs.batchId + '<br>';
				}
				if(undefined != rs.assinedCount && null != rs.assinedCount) {
					msg += '去重后共分配' + rs.assinedCount + '条数据';
				}
                $.messager.alert('提示', msg);
                callback.assignReload();
			}else{
				$.messager.alert('提示', '分配失败：' + rs.message);
				console.error('分配失败：' + rs.message);
                $.messager.alert('提示', '分配失败') ;
			}
		}
	});
};

callback.assignReload = function(){
    //重新加载分配数量
    callback.queryAvaliableQty();

    //重新加载坐席
    callback.queryAvaliableAgent();
    
    //重新加载组信息
    $('#workgroupT').datagrid('reload');
}

callback.queryAvaliableAgent = function(){
	var rows = $('#workgroupT').datagrid('getChecked');
	var groups = '';
	for(var i=0; i<rows.length; i++){
		groups += rows[i].grpid;
		if(i!=rows.length-1){
			groups += ',';
		}
	}

	
	$('#agentTb').datagrid('reload', {
		'lowDate' : $('#g_beginDate').datetimebox('getValue'),
		'highDate' : $('#g_endDate').datetimebox('getValue'),
		'level' : $('#g_level').combobox('getValues').join(','),
		'agentId' : $('#g_agentId').val(),
		'groups' : groups,
        'cType' : $('#assignType').combobox('getValue'),
        'initLoad' : true
	});
    //重置选择座席数量
    omniNum = 0;
    $('#omniNum').text(omniNum);
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
        $.messager.alert('错误', '请选择要分配的坐席！') ;
        return;
    }



    //触发平均分配事件
    calcAverageCount();

    var valid = true;

    var assignType = $('#assignType').combobox('getValue');
    if(assignType != 3){ //接通
        var agentRows = $('#agentTb').datagrid('getChecked');
        if(agentRows.length > 0){
            var allotNum = parseInt($("#v_allotNum").html());
            var assignNum = parseInt($("#tbTotalNum").val());
            if(assignNum > allotNum){
                $.messager.alert("系统提示","不能超过可分配数量［"+allotNum+"］!","error", function(){
                    $("#tbTotalNum").focus();
                });
                return false;
            }

            var totalNum = 0;
            for(var i = 0; i < agentRows.length; i++){
                var rowIndex = $('#agentTb').datagrid('getRowIndex',agentRows[i]);
                if(rowIndex > -1){
                    var assignCount =  $(agentRows[i]).attr("assignCount");
                    if(!$.isNumeric(assignCount)) assignCount = 0;
                    totalNum += parseInt(assignCount);
                }
            }

            if(!$.isNumeric($("#tbTotalNum").val())){
                $.messager.alert("系统提示","总分配数量必须是数字!","error", function(){
                    $("#tbTotalNum").focus();
                });
                return false;
            }


            if(totalNum < 1){
                $('#assignOperate').hide();
                $.messager.alert("系统提示","当前坐席还没有分配!");
                return false;
            }
            else if(totalNum > assignNum){
                $('#assignOperate').hide();
                $.messager.alert("系统提示","当前坐席分配总数量["+totalNum+"]不能超过可分配数量["+assignNum+"]");
                return false;
            }
            else $('#assignOperate').show();
        } else {
            $('#assignOperate').hide();
            $.messager.alert("系统提示","还没有选择任何坐席!");
        }
    }
    else{
        var lastGroup = '';
        for(var i=0; i<groupRows.length; i++){

            var agentTotalQty = 0;
            var groupTotalQty = 0;

            var _gr = groupRows[i];
            groupTotalQty = Number(isNaN(_gr.grpqty)==true? 0 : _gr.grpqty);
            if(groupTotalQty==0){
                $.messager.alert('错误', '坐席组['+_gr.grpid+']可分配数量不足！') ;
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
                    for(var k=0; k<groupRows.length; k++){
                        var __gr = groupRows[k];
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
        }else{
        	$('#assignOperate').show();
        }


        if(!valid){
            $('#assignOperate').hide();
            $.messager.alert('错误', '坐席分配总数大于工作组['+lastGroup+']的数量！') ;

            return false;
        }
    }





	return true;
};

/**
 * 查询井星接通类型可分配数量
 */
function findIvrConnectedQty() {
	var data = callback.prepareParam(13);

	callback.queryQtyByParam(data, URL_QUERY_IVR_CONNECTED_QTY);
}


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
        global: false,
        url: '/callbackAssign/import', 
        secureuri: false,
        fileElementId: 'fileImport',
        dataType: 'json',
        success: function (data, status) {
        	if (data.code) {
//        		window.parent.alertWin("上传成功", data.count + "条记录保存进数据库");
                $.messager.alert("上传成功", data.count + "条记录保存进数据库") ;
        	} else {
//           		window.parent.alertWin("系统提示", data.msg);
                $.messager.alert("系统提示", data.msg) ;
        	}
        }
    })
    
    return false;
}






//@ sourceURL=callbackAssign.js