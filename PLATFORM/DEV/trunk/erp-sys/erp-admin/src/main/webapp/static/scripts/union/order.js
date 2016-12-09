$(document).ready(function() {
    $(function () {
        $('#dateAdded_start').datepicker({dateFormat:"yy-mm-dd"});
          $('#dateAdded_end').datepicker({dateFormat:"yy-mm-dd"});
        $('#listOrderTable').datagrid({
            title:'',
            iconCls:'icon-edit',
            width: 700,
            height: 'auto',
            nowrap: false,
            striped: true,
            border: true,
            collapsible:false,
            fit: true,
            url:'/union/queryOrder',
            //sortName: 'code',
            //sortOrder: 'desc',
            remoteSort:false,
            idField:'orderid',
            singleSelect:false,
            pagination:true,
            rownumbers:true,
            frozenColumns:[[

            ]],
            queryParams: {
                beginDate: $("#dateAdded_start").val(),
                endDate: $("#dateAdded_end").val(),
                orderId: $("#orderId").val(),
                unionName: $("#unionName").val()
            }
        });

        var p = $('#listOrderTable').datagrid('getPager');
        $(p).pagination({
            pageSize: 10,
            pageList: [5,10,15],
            beforePageText: '',
            afterPageText: 'page    total {pages}page',
            displayMsg: 'current {from} - {to} records total {total} records'
            /*onBeforeRefresh:function(){
             $(this).pagination('loading');
             alert('before refresh');
             $(this).pagination('loaded');
             }*/
        });

    });
});


var queryOrderData = function () {
    $('#listOrderTable').datagrid('load',{
    	 beginDate: $("#dateAdded_start").val(),
         endDate: $("#dateAdded_end").val(),
        orderId: $("#orderId").val(),
        unionName: $("#unionName").val()
    });
};


function exportExl(){
    var d1v=document.getElementById("d1").value;
    var d2v=document.getElementById("d2").value;
    window.location.href="/performance/exportExcel.do?startTime="+d1v+"&endTime="+d2v;
}


function CompareDate(d1,d2){
    return ((new Date(d1.replace(/-/g,"\/"))) <=(new Date(d2.replace(/-/g,"\/"))));
}

function doSearch(){
    var d1=jQuery("#d1").val();
    var d2=jQuery("#d2").val();
    if(CompareDate(d1,d2)){
        jQuery("#sf").submit();
    }else{
        alert("开始日期必须小于或等于结束日期");
    }
}