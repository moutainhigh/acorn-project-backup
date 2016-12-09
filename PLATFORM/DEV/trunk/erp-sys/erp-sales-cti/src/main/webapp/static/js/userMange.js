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
       //// url:'/static/plugin/ttt.json',
        frozenColumns:[[

        ]],
        columns:[[
            {field:'uid',title:'用户',width:60},
            {field:'ip',title:'登录IP',width:60},
            {field:'lastTime',title:'最近登录时间',width:60},
            {field:'server',title:'服务器',width:60},
            {field:'operator',title:'操作',width:50,align:'center', formatter:function(value,row,index){
                return "<a class='link kickOut mr10' title='登出' href=\"javascript:cullingUser('"+ row.uid +"','"+row.server+"')\"></a>"
                    +"<a class='link resetPw mr10' title='重置密码' href=\"javascript:changePwdShow('"+ row.uid +"')\"></a>"
                    +"<a class='link unlock' title='解锁' href=\"javascript:unlock('"+ row.uid +"','"+row.server+"')\"></a>";
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
            $.messager.confirm('系统提示', '<div style="padding:10px 0;">您确定要强制用户'+uid+'登出?</div>', function(r){
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


/**
 * 修改密码
 * @param uid
 */
function changePwd(uid){
//    var content = '<div class="resetPW">' +
//        '<form id="" action="" method="post">' +
//        '<table width="100%">' +
//        '<tbody>' +
//    '<tr>' +
//    '<td style="padding:5px;text-align: right;"><div class="lableWord">新密码</div></td>' +
//    ' <td style="padding:5px;">' +
//    ' <input id="newPw" name="newPw" value="" type="password" maxlength="20" validtype="length[8,20]" class="easyui-validatebox validatebox-text" data-options="required:true">' +
//    '</td>' +
//    '</tr>' +
//    '<tr>' +
//    '<td style="padding:5px;text-align: right;"><div class="lableWord">确认新密码</div></td>' +
//    ' <td style="padding:5px;">' +
//    '  <input id="" name="" value="" type="password" maxlength="20" validtype="equals[\'#newPw\']" class="easyui-validatebox validatebox-text" data-options="required:true">' +
//    '  </td> ' +
//    '</tr> ' +
//        '</tbody> ' +
//        '</table> ' +
//        '</form> ' +
//    '</div>';
    var npwd="12345678";
    var content = '<div class="resetPW">' +
             '确认要把用户['+uid+']'+'的密码重置为:'+npwd;
        '</div>';
    if(uid){
        $.messager.confirm('重置密码', content, function(r){
            if (r){
                $.ajax({
                    type : "post",
                    url : "/security/modifyPwd4Iagent",
                    data : {userId:uid,newPassword:npwd},
                    async : false,
                    dataType:"json",
                    success : function(data){
                        $.messager.alert('系统提示', data.msg);
                    }
                });
            }
        });
    }
}

/**
 * 修改密码
 * @param uid
 */
function changePwd2(){
    var uid=$("#cullingUserId").val();
//    var content = '<div class="resetPW">' +
//        '<form id="" action="" method="post">' +
//        '<table width="100%">' +
//        '<tbody>' +
//    '<tr>' +
//    '<td style="padding:5px;text-align: right;"><div class="lableWord">新密码</div></td>' +
//    ' <td style="padding:5px;">' +
//    ' <input id="newPw" name="newPw" value="" type="password" maxlength="20" validtype="length[8,20]" class="easyui-validatebox validatebox-text" data-options="required:true">' +
//    '</td>' +
//    '</tr>' +
//    '<tr>' +
//    '<td style="padding:5px;text-align: right;"><div class="lableWord">确认新密码</div></td>' +
//    ' <td style="padding:5px;">' +
//    '  <input id="" name="" value="" type="password" maxlength="20" validtype="equals[\'#newPw\']" class="easyui-validatebox validatebox-text" data-options="required:true">' +
//    '  </td> ' +
//    '</tr> ' +
//        '</tbody> ' +
//        '</table> ' +
//        '</form> ' +
//    '</div>';
    var npwd="12345678";
    var content = '<div class="resetPW">' +
        '确认要把用户['+uid+']'+'的密码重置为:'+npwd;
    '</div>';
    if(uid){
        $.messager.confirm('重置密码', content, function(r){
            if (r){
                $.ajax({
                    type : "post",
                    url : "/security/modifyPwd4Iagent",
                    data : {userId:uid,newPassword:npwd},
                    async : false,
                    dataType:"json",
                    success : function(data){
                        $.messager.alert('系统提示', data.msg);
                    }
                });
            }
        });
    }else{
        $.messager.alert('系统提示',"请输入用户Id");
    }
}

/**
 * 修改密码
 * @param uid
 */
function changePwdShow(state){
    if(state==2){//强制重置密码
        var uid=$("#cullingUserId").val();
        if(uid){
            $('#viewUserId').val(uid);
            $('#resetWin').window('open');
        }else{
            $.messager.alert('系统提示',"请输入用户Id");
        }
    }else{   //从列表重置密码
        uid=state;
        if(uid){
            $('#viewUserId').val(uid);
            $('#resetWin').window('open');
        }

    }

}


function submitChangePwd(){


    if($("#modifyPWForm").form('validate')){
        $.ajax({
            type : "post",
            url : "/security/modifyPwd4Iagent",
            data : {userId:$("#viewUserId").val(),
                   newPassword:$("#password2").val()
                   },
            async : false,
            dataType:"json",
            success : function(data){
                $('#resetWin').window('close');
                $.messager.alert('系统提示', data.msg);
            }
        });
    }


}

function clearChangePwd(){
    $('#viewUserId').val("");
    $('#password1').val("");
    $('#password2').val("");

    $('#resetWin').window('close');


}

/**
 * 解锁
 * @param uid
 */
function unlock(uid){
    if(uid){
        $.ajax({
            type : "get",
            url : "/security/releaseLock",
            data : {userId:uid},
            async : false,
            dataType:"json",
            success : function(data){
                $.messager.alert('系统提示',data.msg);

            }
        });
    }

}

/**
 * 解锁
 * @param uid
 */
function unlock2(){
    var uid=$("#cullingUserId").val();
    if(uid){
        $.ajax({
            type : "get",
            url : "/security/releaseLock",
            data : {userId:uid},
            async : false,
            dataType:"json",
            success : function(data){

                $.messager.alert('系统提示',data.msg);

            }
        });
    }else{
        $.messager.alert('系统提示',"请输入用户Id");
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
