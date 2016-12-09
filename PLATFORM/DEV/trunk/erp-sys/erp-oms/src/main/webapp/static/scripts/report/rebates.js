
$(document).ready(function() {

    //查询按钮
    $("#queryOrderBtn").click(function () {
        var startDate = $("#startDate").datebox('getValue');
        var endDate = $("#endDate").datebox('getValue');

        if(startDate && endDate){
            //时间不为空
            var days = GetDateDiff(startDate,endDate);
            if(days > 31){
                alert("查询时间范围超过31天,请核实!");
            }else{
                //alert("查询时间段："+startDate+"至"+endDate);
                var reportServerUrl = $("#reportServerUrl").val();
                var url = reportServerUrl + "/reportview/frameset/rebatesReport.rptdesign?__showPrint=false"
                    + "&strDate=" + startDate
                    + "&endDate=" + endDate;
                
                $("#reportrForm").attr("action",url);
                $("#reportrForm").submit();
            }
        }else{
            alert("查询时间段为必选!");
        }

    });
    //清空按钮
    $('#clearBtn').click(function(){
        $("#startDate").datebox('setValue',"");
        $("#endDate").datebox('setValue',"");
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

