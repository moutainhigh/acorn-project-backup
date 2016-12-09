/**
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 13-10-24
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */

$(document).ready(function(){
    $('#loginUsers-grid').datagrid({
        width : '90%',
        height : '540',
        showHeader: true,
        nowrap : false,
        striped : true,
        border : true,
        collapsible : false,
        fitColumns:true,
        scrollbarSize:-1,
       // url : '/getAllUsers',
        url : '/getAU4',
        frozenColumns:[[

        ]],
        columns:[[
            {field:'uid',title:'用户',width:60},
            {field:'ip',title:'登录IP',width:60},
            {field:'lastTime',title:'最近登录时间',width:60},
            {field:'server',title:'服务器',width:60},
            {field:'operator',title:'操作',width:50,align:'center', formatter:function(value,row,index){
                return "<a class='link stop' title='登出' href=\"javascript:cullingUser('"+ row.uid +"','"+row.server+"')\"></a>";
            }}
        ]],
        remoteSort:false,
        singleSelect:false,
        checkOnSelect: false,
        selectOnCheck: false,
        pagination:true,
        pageSize: 10,
        loadFilter:pagerFilter,
        queryParams:{
            uid:$("#uid").val(),
            service:$("#server").combobox("getValue"),
            ip: $("#ip").val()
        },
        onLoadSuccess: function(data){
            //alert(JSON.stringify(data));
        },
        onLoadError: function(){
            window.parent.alertWin('系统提示',"加载用户Session信息失败!");
        }
    });

    //初始化下拉列表

    $('#server').combobox({
        url:'/getAU3',
        valueField:'server',
        textField:'server'
    });

});

function cullingUser(uid,server){
    var fnbg = $(parent.document).find("#fn-bg");
    if(fnbg) {
       if(uid != fnbg.html().trim().split("&")[0]) {
            $.messager.confirm('系统提示', '您确定要强制用户'+uid+'登出?', function(r){
               if (r){
//             if(confirm('您确定要强制用户'+uid+'登出?')){
                   $.ajax({
                       type : "get",
                       url : "/cullingUserFromServer",
                       data : {name:uid,server:server},
                       async : false,
                       dataType:"json",
                       success : function(){
                           findInfo();

                       }
                   });
               }
           });
       }
        else{
           $.messager.alert('系统提示', '不能强制自己登出！');
       }
    }
}

function pagerFilter(data){
    if (typeof data.length == 'number' && typeof data.splice == 'function'){ // is array
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage:function(pageNum, pageSize){
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh',{
                pageNumber:pageNum,
                pageSize:pageSize
            });
            dg.datagrid('loadData',data);
        }
    });
    if (!data.originalRows){
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}

function cullingSingleUser(){
    var fnbg = $(parent.document).find("#fn-bg");
    var user_v = $("#cullingUserId").val();
    if(user_v==""){

        $.messager.alert('系统提示', '用户Id不能为空！');
        return false;
    }

    if(user_v != fnbg.html().trim().split("&")[0]) {
        $.messager.confirm('系统提示', '您确定要强制用户'+user_v+'登出?', function(r){
            if (r){
//             if(confirm('您确定要强制用户'+uid+'登出?')){
                $.ajax({
                    type : "get",
                    url : "/cullingUserAll",
                    data : "name=" + user_v,
                    async : false,
                    dataType:"json",
                    success : function(data){
                        if(typeof(data)=="undefined" ){
                            $.messager.alert('系统提示', '强制退出失败！');
                        }else{
                            $("#cullingUserId").val("");
                            $.messager.alert('系统提示', '强制退出成功！');

                        }
                        findInfo();
                    }
                });
            }
        });
    }
    else{
        $.messager.alert('系统提示', '不能强制自己登出！');
    }
}


function findInfo(){

        $('#loginUsers-grid').datagrid('load',{
            uid:$("#uid").val(),
            service:$("#server").combobox("getValue"),
            ip: $("#ip").val()
        });

}
