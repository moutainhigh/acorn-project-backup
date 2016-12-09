
$(document).ready(function() {
   $("#startDate").datetimebox('setValue',currentTime());
   $("#endDate").datetimebox('setValue',currentTime()+" 23:59:59");
    //查询按钮
    $("#queryBtn").click(function () {
        var startDate = $("#startDate").datetimebox('getValue');
        var endDate = $("#endDate").datetimebox('getValue');
        var warehouseId = $("#warehouseId").val();

       // alert("仓库="+warehouseId+"开始时间="+startDate+"结束时间="+endDate)
        if(startDate && endDate){
            //时间不为空
            var days = GetDateDiff(startDate,endDate);
            if(days > 90){
                alert("查询时间范围超过90天,请核实!");
            }else{
                var reportServerUrl = $("#reportServerUrl").val();
                var url = reportServerUrl + "/reportview/frameset/OutBoundPerformance.rptdesign?__showPrint=false"
                    + "&STARTDATE=" + startDate
                    + "&ENDDATE=" + endDate
                    + "&WAREHOUSE=" + warehouseId;
                
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
        $("#entityId").val("");
        $("#warehouseId").val("");
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
