$(document).ready(function() {
    $(function () {
        //  $('#beginDate').datepicker({dateFormat:"yy-mm-dd"});
      //$('#endDate').datepicker({dateFormat:"yy-mm-dd"});
    $('#listAuditLogTable').edatagrid({
        title:'',
        iconCls:'icon-edit',
        width: 700,
        height: 400,
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fit: true,
        url:'/admin/queryAuditLog',
        saveUrl: '/admin/saveAuditLog',
        updateUrl: '/admin/saveAuditLog',
        destroyUrl: '/admin/delAuditLog',
        //sortName: 'code',
        //sortOrder: 'desc',
        columns:[[
            {field:'id',title:'ID',width:60},
            {field:'appName',title:'系统名称',width:100,editor:'text'},
            {field:'funcName',title:'功能名称',width:100,editor:'text'},
            {field:'userId',title:'用户',width:60,editor:'text'},
            {field:'logDate',title:'日期',width:100,editor:'text'},
            {field:'logValue',title:'操作',width:100,editor:'text'}
        ]],
        remoteSort:false,
        idField:'id',
        singleSelect:false,
        pagination:true,
        rownumbers:true,
        frozenColumns:[[
        {field:'ck',checkbox:true}
    ]],
    queryParams: {
        appName: $("#appName").val(),
        funcName: $("#funcName").val(),
        startDate: $("#beginDate").datebox('getValue'),
        endDate: $("#endDate").datebox('getValue')
        }
    });

    var p = $('#listAuditLogTable').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    /*onBeforeRefresh:function(){
        $(this).pagination('loading');
        alert('before refresh');
        $(this).pagination('loaded');
        }*/
    });

    });
});


var queryAuditData = function () {
    $('#listAuditLogTable').datagrid('load',{
        appName: $("#appName").val(),
        funcName: $("#funcName").val(),
        beginDate: $("#beginDate").datebox('getValue'),
        endDate: $("#endDate").datebox('getValue')
    });
};
