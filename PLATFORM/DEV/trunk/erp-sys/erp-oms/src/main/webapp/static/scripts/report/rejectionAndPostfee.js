
$(document).ready(function() {

    //查询按钮
    $("#queryOrderBtn").click(function () {
        var strDate = $("#strDate").datebox('getValue');
        var endDate = $("#endDate").datebox('getValue');
        var entityId = $("#entityId").combobox('getValue');
        var provinceId = $("#provinceId").combobox('getValue');

        //alert("结算日期时间段："+strDate+"至"+endDate+",送货公司="+entityId+",省份="+provinceId);
        if(strDate && endDate){
            //时间不为空
            var days = GetDateDiff(strDate,endDate);
            if(days > 31){
                alert("查询时间范围超过31天,请核实!");
            }else{
                var reportServerUrl = $("#reportServerUrl").val();
                var url = reportServerUrl + "/reportview/frameset/rejectionAndPostfee.rptdesign?__showPrint=false"
                    + "&reconcilDateStr=" + strDate
                    + "&reconcilDateEnd=" + endDate
                    + "&entityId=" + entityId
                    + "&provinceId=" + provinceId;
                
                $("#reportrForm").attr("action",url);
                $("#reportrForm").submit();
            }
        }else{
            alert("查询时间段为必选!");
        }

    });
    //清空按钮
    $('#clearBtn').click(function(){
        $("#strDate").datebox('setValue',"");
        $("#endDate").datebox('setValue',"");
        $("#entityId").combobox('setValue',"");
        $("#provinceId").combobox('setValue',"");
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

