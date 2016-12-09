
$(document).ready(function() {

    //查询按钮
    $("#queryOrderBtn").click(function () {
        var rfoutdatStr = $("#rfoutdatStr").datebox('getValue');
        var rfoutdatEnd = $("#rfoutdatEnd").datebox('getValue');
        var buyStr = $("#buyStr").datebox('getValue');
        var buyEnd = $("#buyEnd").datebox('getValue');
        var entityId = $("#entityId").combobox('getValue');
        var orderId = $("#orderId").val();
        var shipmentId = "";

        if(!rfoutdatStr && !rfoutdatEnd && !buyStr && !buyEnd && !entityId && !orderId){
            alert("请输入一个查询条件!");
            return false;
        }
        if(rfoutdatStr && rfoutdatEnd){
            var days = GetDateDiff(rfoutdatStr,rfoutdatEnd);
            if(days > 31){
                alert("出库日期范围超过31天!");
                return false;
            }
        }
        if(buyStr && buyEnd){
            var days = GetDateDiff(buyStr,buyEnd);
            if(days > 31){
                alert("订购日期范围超过31天!");
                return false;
            }
        }
        var inx = orderId.indexOf("V");   //判断是否是运单号
        if(inx > 0){
            shipmentId = orderId;
            orderId = "";
        }
        alert("订单号："+orderId+",运单号："+shipmentId);

        var reportServerUrl = $("#reportServerUrl").val();
        var url = reportServerUrl + "/reportview/frameset/orderDetailsSearchReport.rptdesign?__showPrint=false"
                 + "&foutdatStr=" + rfoutdatStr
                 + "&foutdatEnd=" + rfoutdatEnd
                 + "&buydatStr=" + buyStr
                 + "&buydatEnd=" + buyEnd
                 + "&entityId=" + entityId
                 + "&shipmentId=" + shipmentId
                 + "&orderId=" + orderId;

                 $("#reportrForm").attr("action",url);
                 $("#reportrForm").submit();

    });
    //清空按钮
    $('#clearBtn').click(function(){
        $("#rfoutdatStr").datebox('setValue',"");
        $("#rfoutdatEnd").datebox('setValue',"");
        $("#buyStr").datebox('setValue',"");
        $("#buyEnd").datebox('setValue',"");
        $("#entityId").combobox('setValue',"");
        $("#orderId").val("");
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

