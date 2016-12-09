/**
 * Created with IntelliJ IDEA.
 * User: gaudi.gao
 * Date: 13-8-13
 * Time: 上午10:45
 * To change this template use File | Settings | File Templates.
 */
$(function(){

    //var ivrData = [];
    //var nonData = [];

    $('#log-calltype').combobox({
        onSelect:function(row){
            //acd group
            /*
            if(row.id =="1"){ //IVR
                if(ivrData.length > 0){
                    resetLogAcdGroups(ivrData);
                } else {
                    $.post("/acdgroup/lookup",{ ivr: true}, function(data){
                        ivrData = data;
                        resetLogAcdGroups(ivrData);
                    });
                }
            } else {
                if(nonData.length > 0){
                    resetLogAcdGroups(nonData);
                } else {
                    $.post("/acdgroup/lookup",{ ivr: false}, function(data){
                        nonData = data;
                        resetLogAcdGroups(nonData);
                    });
                }
            }
            */
            switch (row.id){
                case "1":{
                    $("#show_timeLength2").hide();
                    $("#show_priority2").hide();
                } break;
                case "2":{
                    $("#show_timeLength2").hide();
                    $("#show_priority2").hide();
                } break;
                case "3":{
                    $("#show_timeLength2").show();
                    $("#show_priority2").hide();
                    $("#log-priority").combobox("setValue", "");
                } break;
                case "5":{
                    $("#show_timeLength2").hide();
                    $("#show_priority2").show();
                    $("#log-callduration").combobox("setValue", "");
                } break;
                case "11":{
                    $("#show_timeLength2").hide();
                    $("#show_priority2").hide();
                } break;
                case "12":{
                    $("#show_timeLength2").hide();
                    $("#show_priority2").hide();
                } break;
                case "13":{
                    $("#show_timeLength2").show();
                    $("#show_priority2").hide();
                    $("#log-priority").combobox("setValue", "");
                } break;
                case "15":{
                    $("#show_timeLength2").hide();
                    $("#show_priority2").show();
                    $("#log-callduration").combobox("setValue", "");
                } break;
                default :{

                } break;
            }
        }
    });

    $('#log-acdGroup').combobox({
        url: '/acdinfo/lookup',
        valueField:'acd',
        textField:'acd2',
        multiple:true,
        onSelect:function(rec){
            var opts = $(this).combobox('options');
            var checkbox = $('#_'+rec[opts.valueField]+'_log_acdGroup');
            checkbox.attr('checked',true);
        },
        onUnselect:function(rec){
            var opts = $(this).combobox('options');
            var checkbox = $('#_'+rec[opts.valueField]+'_log_acdGroup');
            checkbox.removeAttr('checked');
        },
        formatter: function(row){
            var opts = $(this).combobox('options');
            var mpn =  row.mediaProdName; //findMediaProdName(row[opts.textField]);
            return '<div title="'+ (mpn?mpn:"") +'"><input id="_'+ row[opts.valueField] +'_log_acdGroup" class="v_bottom" type="checkbox"/>'+row[opts.textField] +(mpn ? " ("+mpn+") ":"")+"</div>";
        },
        onShowPanel:function(){
            $(this).combo('panel').width(248).parent().width(250);
        }
    });

    //$('#log-acdGroup').combobox("clear");

    $('#callback-log-grid').datagrid({
        striped: true,
        border: true,
        collapsible:true,
        fitColumns:false,
        scrollbarSize:17,
        url:'/callback/logs',
        columns:[[
            {field:'ck',checkbox:true,width:20},
            {field:'type',title:'分配类型',width:80,align:'center',formatter:function(value){
                switch (value){
                    case "1": return "VM";
                    case "2": return "放弃";
                    case "3": return "通话";
                    case "5": return "SNATCH IN";
                    case "11": return "VM(WILCOM)";
                    case "12": return "放弃(WILCOM)";
                    case "13": return "通话(WILCOM)";
                    case "15": return "SNATCH IN(WILCOM)";
                    default: return value;
                }
            }},
            {field:'dnis',title:'被叫号',width:80,align:'center'},
            {field:'ani',title:'主叫号',width:80,align:'center'},
            {field:'usrId',title:'座席工号',width:80,align:'center'},
            {field:'usrName',title:'座席姓名',width:80,align:'center'},
            {field:'usrGrp',title:'工作组',width:80,align:'center',hidden:true},
            {field:'usrGrpName',title:'工作组',width:80,align:'center'},
            {field:'callDate',title:'呼入时间',width:80,align:'center',
                formatter:function(value) {
                    return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                }
            },
            {field:'opusr',title:'1次分配人员工号',width:80,align:'center'},
            {field:'opusrName',title:'1次分配人员姓名',width:80,align:'center'},
            {field:'firstdt',title:'1次分配时间',width:120,align:'center',
                formatter:function(value) {
                    return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                }
            },
            {field:'firstusrId',title:'1次被分配坐席工号',width:80,align:'center'},
            {field:'firstusrName',title:'1次被分配坐席名称',width:80,align:'center'},
            {field:'firstusrGrpName',title:'1次被分配坐席工作组',width:80,align:'center'},
            {field:'dbdt',title:'2次分配时间',width:120,align:'center',hidden:true,
                formatter:function(value) {
                    return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                }
            },
            {field:'dbusrId',title:'2次分配人员',width:80,align:'center',hidden:true},
            {field:'acdGroup',title:'ACD组',width:80,align:'center'},
            {field:'mediaprodId',title:'媒体产品',width:80,align:'center'},
            {field:'batchId',title:'分配批次号',width:140,align:'center'}
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination : true,
        onBeforeLoad:function(){
            $(this).datagrid('rejectChanges');
        },
        onLoadSuccess: function(data){
            //alert(JSON.stringify(data));
            if(data){
                $("#log_allotNum").html(data.total);
            }
        },
        onLoadError: function(){
            $.messager.alert('错误', '分配历史加载失败！') ;
        }
    });

    var p = $('#callback-log-grid').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    $("#lnk-callback-log-submit").click(function(){
        var queryParams = {
                callType: $("#log-calltype").combobox("getValue"),
                callDuration: $("#log-callduration").combobox("getValue"),
                startDate: $("#log-startdate").datetimebox("getValue"),
                endDate: $("#log-enddate").datetimebox("getValue"),
                priority:$("#log-priority").combobox("getValues").join(','),
                acdGroup:$("#log-acdGroup").combobox("getValues").join(','),    //ACD组
                dnis:$("#log-dnis").val(),
                agentUser:$("#log-agentuser").val(),
                allocatedNumber:$("#log-allocatedNumber").combobox("getValue"),
                batchId: $("#log-batchid").val(),
                page: 1
        };
        $('#callback-log-grid').datagrid("load", queryParams);
    });

    $("#lnk-callback-log-reset").click(function(){
        $("#log-calltype").combobox("clear"),
        $("#log-callduration").combobox("clear"),
        $("#log-startdate").datetimebox("clear"),
        $("#log-enddate").datetimebox("clear"),
        $("#log-priority").combobox("clear"),
        $("#log-acdgroup").val(""),
        $("#log-dnis").val(""),
        $("#log-agentuser").val("");
        $("#log-allocatedNumber").combobox("clear");
        $("#log-batchid").val("");
    });

    $("#lnk-export-log").click(function(){
        var queryParams = {
            callType: $("#log-calltype").combobox("getValue"),
            callDuration: $("#log-callduration").combobox("getValue"),
            startDate: $("#log-startdate").datetimebox("getValue"),
            endDate: $("#log-enddate").datetimebox("getValue"),
            priority: $("#log-priority").combobox("getValues").join(','),
            acdGroup: $("#log-acdGroup").combobox("getValues").join(','),
            dnis:$("#log-dnis").val(),
            agentUser:$("#log-agentuser").val(),
            allocatedNumber:$("#log-allocatedNumber").combobox("getValue"),
            batchId: $("#log-batchid").val()
        };
        document.location.href = '/callback/logs/export?'+$.param(queryParams);
    });
});

//var resetLogAcdGroups = function(data) {
//    $("#log-acdGroup").combobox({
//        data: data,
//        valueField:'acdId',
//        textField:'acdId'
//    });
//    $("#log-acdGroup").combobox("setValue", "");
//}


Date.prototype.format = function (format) {
    if (!format) {
        format = "yyyy-MM-dd hh:mm:ss";
    }
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds() // millisecond
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};