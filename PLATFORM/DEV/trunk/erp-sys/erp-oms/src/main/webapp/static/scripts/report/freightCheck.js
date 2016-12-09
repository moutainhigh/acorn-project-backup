
$(document).ready(function() {
    $("#settleStrDate").datetimebox('setValue',currentTime());
    $("#settleEndDate").datetimebox('setValue',currentTime()+" 23:59:59");
    //查询按钮
    $("#queryOrderBtn").click(function () {
        var strDate = $("#settleStrDate").datetimebox('getValue');
        var endDate = $("#settleEndDate").datetimebox('getValue');
        var entityId = $("#entityId").combobox('getValue');
        var orderStatus = $("#orderStatus").val();
        var state = $("input[name='state']:radio:checked").val();

        var settleStrDate = '';
        var settleEndDate = '';
        var outStrDate = '';
        var outEndDate = '';
        if(state == 0){
            settleStrDate = strDate;
            settleEndDate = endDate;
        }else{
            outStrDate = strDate;
            outEndDate = endDate;
        }

        var reportServerUrl = $("#reportServerUrl").val();
        var url = reportServerUrl + "/reportview/frameset/freightCheckReport.rptdesign?__showPrint=false"
                    + "&settleStrDate=" + settleStrDate
                    + "&settleEndDate=" + settleEndDate
                    + "&outStrDate=" + outStrDate
                    + "&outEndDate=" + outEndDate
                    + "&orderStatus=" +orderStatus
                    + "&entityId=" + entityId;

        $("#reportrForm").attr("action",url);
        $("#reportrForm").submit();

    });
    //清空按钮
    $('#clearBtn').click(function(){
        $("#entityId").combobox('setValue',"");
        $("#orderStatus").val("");
    });
});

//时间间隔
function GetDateDiff(startDate,endDate)
{
    var startTime = new Date(Date.parse(startDate.replace(/-/g,   "/"))).getTime();

    var endTime = new Date(Date.parse(endDate.replace(/-/g,   "/"))).getTime();

    var dates = Math.abs((startTime - endTime))/(1000*60*60*24);

    return  dates;
}
//初始化时间
function currentTime(){
    var d = new Date(),str = '';
    str += d.getFullYear()+'-';
    str += d.getMonth() + 1+'-';
    str += d.getDate();
    return str;
}
