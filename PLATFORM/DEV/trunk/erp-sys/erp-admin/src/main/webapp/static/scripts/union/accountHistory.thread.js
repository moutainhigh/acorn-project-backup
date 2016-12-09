$(document).ready(function() {
    $(function () {
   $('#listAccountHisotryTable').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: 700,
        height: 350,
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fit: true,
        url:'/union/accountHistory/json',
        //sortName: 'code',
        //sortOrder: 'desc',
        remoteSort:false,
        idField:'id',
        singleSelect:false,
        pagination:true,
        rownumbers:true,
    queryParams: {
    	status: $("#linkSelect").val()
        }
    });

    var p = $('#listAccountHisotryTable').datagrid('getPager');
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

function exportExl(){
    var d1v=document.getElementById("d1").value;
    var d2v=document.getElementById("d2").value;
    window.location.href="/performance/exportExcel.do?startTime="+d1v+"&endTime="+d2v;
}

var exportExcel = function () {
    $.ajax({ 
 	   type: "POST", 
 	   url: "/union/accountHistory/exportExcel", 
 	   data: "status="+$("#linkSelect").val(), 
 	   success: function(o){
 	      } 
 	   });   
};

var queryAuditData = function () {
    $('#listAccountHisotryTable').datagrid('load',{
    	status: $("#linkSelect").val()
    });
};
