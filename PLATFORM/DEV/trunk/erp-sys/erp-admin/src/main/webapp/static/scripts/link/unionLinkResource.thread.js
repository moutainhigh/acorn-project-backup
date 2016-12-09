$(document).ready(function() {
    $(function () {
     $('#listUnionLinkResourceTable').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: 700,
        height: 350,
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fit: true,
        url:'/union/queryUnionLinkResource',
        remoteSort:false,
        idField:'id',
        singleSelect:false,
        pagination:true,
        rownumbers:true,
       queryParams: {
    	status:''
        }
    });

    var p = $('#listUnionLinkResourceTable').datagrid('getPager');
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


var queryAuditData = function () {
    $('#listUnionLinkResourceTable').datagrid('load',{
    	status: ''
    });
};
