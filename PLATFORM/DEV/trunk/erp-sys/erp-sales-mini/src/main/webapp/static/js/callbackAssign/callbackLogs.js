/**
 * Created with IntelliJ IDEA.
 * User: gaudi.gao
 * Date: 13-8-13
 * Time: 上午10:45
 * To change this template use File | Settings | File Templates.
 */
$(function(){

    $('#log-calltype').combobox({
        onSelect:function(row){
            switch (row.id){
                case "1":{

                } break;
                case "2":{

                } break;
                case "3":{
                    $("#show_timeLength2").show();
                    $("#show_priority2").hide();
                    $("#log-priority").combobox("setValue", "");
                } break;
                case "4":{

                } break;
                case "5":{
                    $("#show_timeLength2").hide();
                    $("#show_priority2").show();
                    $("#log-callduration").combobox("setValue", "");
                } break;
                default :{

                } break;
            }
        }
    });

    $('#callback-log-grid').datagrid({
        striped: true,
        border: true,
        collapsible:true,
        fitColumns:false,
        scrollbarSize:17,
        url:'/callback/logs',
        columns:[[
            {field:'ck',checkbox:true,width:20},
            {field:'type',title:'分配类型',width:60,align:'center',formatter:function(value){
                switch (value){
                    case "5": return "Callback";
                    case "3": return "通话";
                    default: return value;
                }
            }},
            {field:'batchId',title:'分配批次号',width:80,align:'center'},
            {field:'acdGrp',title:'ACD组',width:60,align:'center'},
            {field:'dnis',title:'被叫号',width:60,align:'center'},
            {field:'ani',title:'主叫号',width:60,align:'center'},
            {field:'usrId',title:'座席工号',width:60,align:'center'},
            {field:'usrName',title:'座席姓名',width:60,align:'center'},
            {field:'usrGrp',title:'工作组',width:60,align:'center'},
            {field:'callDate',title:'呼入时间',width:80,align:'center',
                formatter:function(value) {
                    return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                }
            },
            {field:'firstdt',title:'1次分配时间',width:80,align:'center',
                formatter:function(value) {
                    return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                }
            },
            {field:'firstusrId',title:'1次分配人员',width:60,align:'center'},
            {field:'dbdt',title:'2次分配时间',width:80,align:'center',hidden:true,
                formatter:function(value) {
                    return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                }
            },
            {field:'dbusrId',title:'2次分配人员',width:60,align:'center',hidden:true},
            {field:'mediaprodId',title:'媒体产品',width:60,align:'center'}
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
            window.parent.alertWin('系统提示', "分配历史加载失败!");
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
                priority: $("#log-priority").combobox("getValue"),
                acdGroup: $("#log-acdgroup").val(),
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
            priority: $("#log-priority").combobox("getValue"),
            acdGroup: $("#log-acdgroup").val(),
            dnis:$("#log-dnis").val(),
            agentUser:$("#log-agentuser").val(),
            allocatedNumber:$("#log-allocatedNumber").combobox("getValue"),
            batchId: $("#log-batchid").val()
        };
        document.location.href = '/callback/logs/export?'+$.param(queryParams);
    });
});


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