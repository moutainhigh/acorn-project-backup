$(document).ready(function() {
    //查询按钮
    $("#queryBtn").click(function () {
        var planNum = $("#planNum").val();
        var warehouse = $("#warehouseId").val();

        //alert("plannum="+planNum+"warehouse="+warehouse)
        if(planNum && warehouse){
            var reportServerUrl = $("#reportServerUrl").val();
            var url = reportServerUrl + "/reportview/frameset/InventoryDifference_O_Papers.rptdesign?__showPrint=false"
                + "&PLAN_NUM=" + planNum
                + "&WAREHOUSE=" + warehouse;

            $("#reportrForm").attr("action",url);
            $("#reportrForm").submit();
        }else{
            alert("请输入查询条件!");
        }

    });
    //清空按钮
    $('#clearBtn').click(function(){
        $("#planNum").val("");
        $("#warehouseId").val("");
    });
});

