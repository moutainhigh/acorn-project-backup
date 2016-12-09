$(document).ready(function() {
    $(function () {
        $('#listLinkTable').edatagrid({
            title:'',
            iconCls:'icon-edit',
            width: 500,
            height: 'auto',
            nowrap: false,
            striped: true,
            border: true,
            collapsible:false,
            fit: true,
            url:'/admin/queryLinks',
            saveUrl: '/admin/saveLink',
            updateUrl: '/admin/saveLink',
            destroyUrl: '/admin/delLink',
            //sortName: 'code',
            //sortOrder: 'desc',
            columns:[[
                {field:'id',title:'分类ID',width:60},
                {field:'name',title:'名称',width:100,editor:'text'},
                {field:'isActive',title:'激活',width:50,align:'center',
                    editor:{
                        type:'checkbox',
                        options:{
                            on: 'True',
                            off: 'False'
                        }
                    }
                }
            ]],
            remoteSort:false,
            idField:'id',
            singleSelect:false,
            pagination:true,
            rownumbers:true,
            frozenColumns:[[

            ]],
            queryParams: {
                linkName: $("#linkName").val()
            }
        });

        var p = $('#listLinkTable').datagrid('getPager');
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


var queryOrderData = function () {
    $('#listLinkTable').datagrid('load',{
        linkName: $("#linkName").val()
    });
};

