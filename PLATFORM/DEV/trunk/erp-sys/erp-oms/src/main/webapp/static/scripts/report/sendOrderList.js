$(function(){

        //查询按钮     河南城通送货
        $("#searchBtn").click(function() {
            var sId = $("#sId").val();     //发包交接单号
            var strDate = $("#startDate").datebox("getValue");   //开始时间
            var endDate = $("#endDate").datebox("getValue");     //结束时间
            var cName = $("#cName").val();       //承运商
            var warehouse = $("#warehouse").val();        //仓库
            alert('sid:'+sId+'sdate:'+strDate+'edate:'+endDate+'warehouse:'+warehouse+'承运商:'+name);
            $("#report").attr("src", "http://localhost:9080/report/frameset?__report=reports/" +
                "RSendInventory.rptdesign&__format=HTML&__showtitle=false&sID="+sId+"" +
                "&strDate="+strDate+"&endDate="+endDate+"" +
                "&warehouse="+escape(encodeURI(warehouse))+"&cName="+escape(encodeURI(cName)));
        });

 });