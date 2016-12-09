/**
 * Created with IntelliJ IDEA.
 * User: guadi.gao
 * Date: 14-4-24
 * Time: 下午1:25
 * To change this template use File | Settings | File Templates.
 */

var caseCategory = new CaseCategory();
var caseAction = new CaseAction();

$(function(){

    $("#cbCaseType").combobox({
        url:'/case/type/lookup',
        valueField: 'casetypeid',
        textField: 'dsc',
        onSelect:function(r){
            caseCategory.list1($("#cbCaseType").get(0), r.category);
        }
    });

    $("#cbCaseState").combobox({
        url:'/case/state/lookup',
        valueField: 'casestatid',
        textField: 'dsc'
    });

    $("#cbSolution").combobox({
        url:'/case/solution/lookup',
        valueField: 'tmpId',
        textField: 'dsc'
    });

    $("#tbProcessTime").combobox({
        url:'/case/processtime/lookup',
        valueField: 'tmpId',
        textField: 'dsc'
    });

    $('#tbScode').combogrid({
        panelWidth: 500,
        singleSelect : false,
        multiple:true,
        fitColumns: true,
        idField: 'scode',
        textField: 'scode',
        method: 'post',
        url:'/case/product/lookup',
        queryParams : {
            key : undefined,
            scodes:undefined
        },
        columns: [[
            {field:'ck',checkbox:true,resize:false},
            {field:'scode',title:'产品简码',width:80},
            {field:'prodid',title:'产品编号',width:80},
            {field:'prodname',title:'产品名称',width:120}
        ]],
        toolbar:'#prod_query',
        onBeforeLoad: function(p){
            return !p.key ? false : true;
        },
        onLoadSuccess: function(data){
            //alert(JSON.stringify(data));
        }
    });

    $('#tbProdQuery').searchbox({
        searcher:function(value){
            var grid = $('#tbScode').combogrid('grid');
            if(grid){
                var scodes = $('#tbScode').combogrid('getValues');
                grid.datagrid("load", { key: value, scodes: scodes});
            }
        }
    });

    $('#btnSave').click(function() {
        var validate = $('#fmCase').form('validate');
        if(validate && caseCategory.validate()){
            var url = $("#fmCase").attr("url") +"?"+ $("#fmCase").attr("query");
            var data = $('#fmCase').serialize();
            //alert(JSON.stringify(data));
            $.ajax({
                type: 'POST',
                url: url,
                data: data,
                beforeSend: function(){
                    $.messager.progress({ title:'提示', msg:'请稍等', text:'处理中...' });
                },
                complete: function(){
                    $.messager.progress('close');
                },
                success:function(result){
                    if(result.success){
                        //alert(JSON.stringify(result));
                        if(result.caseid){
                            $("#tbCaseId").val(result.caseid);
                            $("#fmCase").attr("url", "/case/save");
                            $('#dgCase').datagrid("reload");
                        }
                        if(window.parent){
                            if(window.parent.case){
                                window.parent.case.execute(result.caseid);
                            }
                            window.parent.alertWin("提示","客户事件保存成功!");
                        }
                    }else{
                        alert(result.errorMsg);
                    }
                },
                error:function(msg){

                }
            });
        }
    });

    $("#btnNew").click(function(){
        caseAction.create();
    });

    $('#btnClear').click(function(){
        caseAction.clear();
    });

    $('#dgCase').datagrid({
        width : '100%',
        height : 410,
        scrollbarSize:0,
        striped: true,
        border: true,
        collapsible:true,
        fitColumns:false,
        idField: "caseid",
        url: "/case/contact/load",
        columns : [ [
            { field:'ck',checkbox:true,resize:false },
            { field : 'caseid', title : '事件编号', align: 'center' },
            { field : 'contactid', title : '客户编号', align: 'center' },
            { field : 'orderid', align:'center', title : '订单编号' },
            { field : 'dsc1', title : '事件类型', width: 130, align:'center' },
            { field : 'scode', title : '产品', align: 'center' },
            { field : 'dsc2', title : '状态', align: 'center' },
            { field : 'probdsc', title : '问题描述' },
            { field : 'reqdsc', align:'center', title : '客户要求' },
            { field : 'dsc3', title : '解决方案', align:'center', width : 130 },
            { field : 'external', title : '处理描述', align: 'center' },
            { field : 'crdt', title : '联系时间', align: 'center' },
            { field : 'crusr', title : '座席代表ID', width: 100,  align: 'center' } ,
            { field : 'mdusr', title : '修改人', width: 100, align: 'center'  } ,
            { field : 'mddt', title : '修改日期', width: 100, align: 'center' } ,
            { field : 'reason', title : '投诉原因', width: 100, align: 'center' } ,
            { field : 'iscmpfback', title : '是否协办', width: 100, align: 'center' },
            { field : '_a', title : '操作', width: 260, align: 'center', hidden:true, formatter:function(val,row,index){
                       return '<a href="javascript:void(0)" class="icon-remove  pa40" onclick="caseAction.delete('+index+')" >删除</a>' +
                           '<a href="javascript:void(0)" class="icon-edit  pa40" onclick="caseAction.edit('+index+')" >编辑 </a>' +
                           '<a href="javascript:void(0)" class="icon-reload  pa40" onclick="cmpfback_support()" >协办 </a>' +
                           '<a href="javascript:void(0)" class="icon-back  pa40" onclick="repeatedV()" >回访 </a>' +
                           '<a href="javascript:void(0)" class="icon-undo pa40" onclick="editRufendsms()">退款管理</a>';
            } }
        ] ],
        toolbar:[{
            text:'删除',
            iconCls:'icon-remove',
            handler:function(){
                caseAction.delete(-1);
            }
        },'-',{
            text:'编辑',
            iconCls:'icon-edit',
            handler:function(){
                caseAction.edit(-1);
            }
        },'-',{
            text:'协办',
            iconCls:'icon-reload',
            handler:function(){
                cmpfback_support();
            }
        },'-',{
            text:'回访',
            iconCls:'icon-back',
            handler:function(){
                repeatedV();
                //$('#revisitDialog').window('setTitle','设置回访').window('open');
            }
        },'-',{
            text:'退款管理',
            iconCls:'icon-undo',
            handler:function(){
                var row = $("#dgCase").datagrid("getSelected");
                if (row) {
                    editRufendsms(row.contactid,row.orderid);
                }
            }
        }
        /*
        ,'-',{
            text:'客服通知',
            iconCls:'icon-tip',
            handler:function(){
                openCreateNewCusnote();
            }
        }
        */
        ],
        queryParams : {
           contactId : $("#tbContactId").val()
        },
        remoteSort : false,
        singleSelect : true,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination : true,
        onBeforeLoad:function(param){
            if(!param.contactId) {
                window.parent.alertWin("警告","找不到联系人!");
                return false;
            }
            else return true;
        },
        onLoadSuccess: function(data){
            //alert(JSON.stringify(data));
        },
        onLoadError: function(){
            window.parent.alertWin("警告","客户历史事件加载失败!");
        },
        onClickRow: function(rowIndex, rowData){
            caseAction.edit(rowIndex);
        }
    });

    var p = $('#dgCase').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    caseAction.init();

    if(!record_deleted){
        var toolbar =  $('#dgCase-container .datagrid-toolbar').find('td:first');
        if(toolbar){
            toolbar.remove();
        }
    }
});

function CaseAction(){
    var $action = this;
    this.init = function(){
        var caseId = $("#tbCaseId").val();
        if(caseId){
            $.post("/case/retrieve", { caseId: caseId }, function(row){
                $action.bind(row, "/case/save");
            });
        } else {
            var callbackUrl = $("#fmCase").attr("callback");
            if(callbackUrl){
                $.post(decodeURIComponent(callbackUrl),{ }, function(row){
                    $action.bind(row, "/case/add");
                    var caseType = $("#cbCaseType").combobox("getValue");
                    if(caseType) {
                        $("#cbCaseType").combobox("select", caseType);
                    }
                });
            }
        }


    },
    this.create = function(){
        var contactId = $("#tbContactId").val();
        try {
            $('#fmCase').form('clear');
            $('#fmCase').attr('url', "/case/add");
            $("#lstCaseDet1").empty();
            $("#lstCaseDet2").empty();
            $("#lstCaseDet3").empty();
        } finally{
            $("#tbContactId").val(contactId);
        }
    }
    this.bind = function(row, url){
        var contactId = $("#tbContactId").val();
        try {
            $('#fmCase').form('clear');
            $('#fmCase').form('load',row);
            $('#fmCase').attr('url', url);
        } finally{
            $("#tbContactId").val(contactId);
        }
        //产品
        if(row.scode){
            $('#tbScode').attr("originalValues", row.scode);
            $('#tbScode').combogrid('setValues', row.scode.split(","));
        }
        //分类

        if(row.category){
            if(row.caseid){
                $.post("/case/detail/lookup",{ caseId: row.caseid }, function(dets){
                    var detail1, detail2, detail3;
                    $.each(dets, function(){
                        if(!detail1) {
                            detail1 = this;
                        } else if(!detail2){
                            detail2 = this;
                        } else if(!detail3){
                            detail3 = this;
                        }
                    });
                    caseCategory.check(row.category, detail1, detail2, detail3);
                });
            }  else {
                caseCategory.check(row.category);
            }
        }
    }
    this.edit = function(index){
        if(index > -1){
            $('#dgCase').datagrid('selectRow', index);
        }
        var row = $('#dgCase').datagrid('getSelected');
        if (row){
            $action.bind(row, "/case/save");
        }
    }
    this.delete = function(index) {
        if(index > -1){
            $('#dgCase').datagrid('selectRow', index);
        }
        var row = $('#dgCase').datagrid('getSelected');
        if (row){
            window.parent.msgConfirm('询问', '要删除当前记录吗? ', function(r){
                if(r) {
                    $.post('/case/delete',{caseid:row.caseid},function(r2){
                        if(r2.success){
                            $('#dgCase').datagrid("reload");
                            var caseId = $("#tbCaseId").val();
                            if(caseId == row.caseid) $action.create();
                            if(window.parent){
                                if(window.parent.case){
                                    window.parent.case.execute(row.caseid);
                                }
                                window.parent.alertWin("提示","成功删除记录!");
                            }
                        } else {
                            window.parent.alertWin("错误",r2.errorMsg);
                        }
                    },'json');
                }
            });

        } else {
            window.parent.alertWin("提示","请先选择一笔客户时间!");
        }
    }
    this.clear = function(){
        var contactId = $("#tbContactId").val();
        var caseId = $("#tbCaseId").val();
        try {
            $('#fmCase').form("clear");
            $("#lstCaseDet1").empty();
            $("#lstCaseDet2").empty();
            $("#lstCaseDet3").empty();
        } finally{
            $("#tbContactId").val(contactId);
            $("#tbCaseId").val(caseId);
        }
    }
    return this;
}

function CaseCategory(){
    var $category = this;
    this.validate = function(){
        var result =
            ($("#lstCaseDet1 input").size() == 0 || $("#lstCaseDet1 input:checked").size() > 0) &&
            ($("#lstCaseDet2 input").size() == 0 || $("#lstCaseDet2 input:checked").size() > 0) &&
            ($("#lstCaseDet3 input").size() == 0 || $("#lstCaseDet3 input:checked").size() > 0);
        if(!result){
            window.parent.alertWin("提示","必须勾选事件类型各列分类!");
        }
        return result;
    }
    this.check = function(category, detail1, detail2, detail3){

        $category.list1($("#cbCaseType").get(), category, function(r1){
            if(detail1) {
                var input1 = $("#lstCaseDet1 input:checkbox[title='"+detail1.item+"']");
                $(input1).attr("checked", "checked");
                $category.list2(input1, input1.attr("accesskey"), function(r2){
                    if(detail2){
                        var input2 = $("#lstCaseDet2 input:checkbox[title='"+detail2.item+"']");
                        $(input2).attr("checked", "checked");
                        $category.list3(input2, input2.attr("accesskey"), function(r3){
                            if(detail3){
                                var input3 = $("#lstCaseDet3 input:checkbox[title='"+detail3.item+"']");
                                $(input3).attr("checked", "checked");
                            }
                        });
                    }
                });

            }
        });
    }
    this.list1 = function (input, category, callback){
        $.post("/case/category/lookup",{ category: category }, function(rows){
            $("#lstCaseDet1").empty();
            $("#lstCaseDet2").empty();
            $("#lstCaseDet3").empty();
            for(var i = 0; i < rows.length; i++){
                $("<li><label><input type=\"checkbox\" name='categoryId' accesskey='"+rows[i].linkId + "' title='"+rows[i].item+"' value=\""+ rows[i].categoryId +"\" onchange='caseCategory.list2(this,\""+rows[i].linkId+"\")'>"+rows[i].item+"</label></li>").appendTo("#lstCaseDet1");
            }
            if(callback) callback(rows);
        });
    }
    this.list2 = function (input, category, callback){
        if($(input).attr("checked")){
            $.post("/case/category/lookup",{ category: category }, function(rows){
                $("#lstCaseDet2").empty();
                $("#lstCaseDet3").empty();
                for(var i = 0; i < rows.length; i++){
                    $("<li title=\""+category+"\"><label><input type=\"checkbox\" name='categoryId' accesskey='"+rows[i].linkId + "' title='"+rows[i].item+"' value=\""+ rows[i].categoryId +"\" onchange='caseCategory.list3(this,\""+rows[i].linkId+"\")'>"+rows[i].item+"</label></li>").appendTo("#lstCaseDet2");
                }
                if(callback) callback(rows);
            });
            $("#lstCaseDet1 input:checked").removeAttr("checked");
            $(input).attr("checked", "checked")
        } else {
            //$("#lstCaseDet2 li[title=\""+category+"\"]").remove();
            $("#lstCaseDet2").empty();
            $("#lstCaseDet3").empty();
        }
    }
    this.list3 = function (input, category, callback){
        if($(input).attr("checked")){
            $.post("/case/category/lookup",{ category: category }, function(rows){
                $("#lstCaseDet3").empty();
                for(var i = 0; i < rows.length; i++){
                    $("<li title=\""+category+"\"><label><input type=\"checkbox\" name='categoryId' accesskey='"+rows[i].linkId + "' title='"+rows[i].item+"' value=\""+ rows[i].categoryId +"\" onchange='caseCategory.list4(this,\""+rows[i].linkId+"\")'>"+rows[i].item+"</label></li>").appendTo("#lstCaseDet3");
                }
                if(callback) callback(rows);
            });
            $("#lstCaseDet2 input:checked").removeAttr("checked");
            $(input).attr("checked", "checked");
        } else {
            //$("#lstCaseDet3 li[title=\""+category+"\"]").remove();
            $("#lstCaseDet3").empty();
        }
    }
    this.list4 = function (input, category, callback){
        if($(input).attr("checked")){
            $("#lstCaseDet3 input:checked").removeAttr("checked");
            $(input).attr("checked", "checked");
        }
    }
    return this;
}

function showContact(contactid){
    var url = "/contact/1/"+contactid+"?provid=&cityid=";
    var contactname = $("#lblContactName").text();
    if(!contactname) {
        contactname = contactid;
    }
    window.parent.addTab(contactid, contactname+"-详细信息", false, url, "", true);
}

//弹出窗口
function showWindow(options,winPop){
    if (options && options.width && options.height){
        options.top=($(window).height() - options.height) * 0.5;
        options.left=($(window).width() - options.width) * 0.5;
    }
    if(winPop){
        winPop="#"+winPop;
        jQuery(winPop).window(options);
        jQuery(winPop).window('open');
    }else{
        jQuery("#MyPopWindow").window(options);
        $('#MyPopWindow').window('open');
    }
}




function editRufendsms(){
    var row = $('#dgCase').datagrid('getSelected');
    if(row.orderid==null||row.orderid=='')
    {
        window.parent.alertWin('系统提示', '未关联订单，不能进行退款处理');
        return;
    }
    showWindow({
        title:'退款管理',
        href:'/refundsms/openwindow?contactid='+row.contactid+'&orderid='+row.orderid+"&types=0",
        width: 500,
        onLoad: function(){
            //$('#resourceForm').form('clear');
        }
    },"drawbackDialog");
}

//退款管理
function Refund (){

}
//回访
function  repeatedV(){
    var row = $('#dgCase').datagrid('getSelected');
    if (row && row.caseid) {
        showWindow({
            title:'设置回访',
            href:'/caseRecall/openwindow?contactid='+row.contactid+'&orderid='+row.orderid+"&caseid="+row.caseid,
            width: 700,
            onLoad: function(){
                //$('#resourceForm').form('clear');
            }
        },"revisitDialog");
    }else{
        window.parent.alertWin("警告","事件编号为空，请刷新页面!");
    }
}
//协办
function cmpfback_support (){
    var row = $("#dgCase").datagrid("getSelected");
    if (row && row.caseid) {
        eventCmpfbackDialog(row.caseid)
    }else{
        window.parent.alertWin("警告","事件编号为空，请刷新页面!");
    }

}
//客服通知
function customerNoti (){
    $('#customerNDialog').window('setTitle','客服通知').window('open');
}

function openCreateNewCusnote() {
    if ($("#tbOrderId").val() == null || $.trim($("#tbOrderId").val()) == '') {
        window.parent.alertWin('系统提示', '订单编号为空时不能生成客服通知。');
        return;
    }
    $.ajax({
        url: '/notice/queryInfo/'+$("#tbOrderId").val(),
        contentType: "application/json",
        success:function(data){
            if (data.error) {
                window.parent.alertWin('系统提示', data.error);
                return;
            }
            $('#customerNDialog_contactName').val(data.contactName);
            $('#customerNDialog_orderId').val($("#tbOrderId").val());
            $('#customerNDialog_orderTime').val(dateFormatter(data.orderCrdt));
            $('#customerNDialog_eventId').val($("#tbCaseId").val());
            $('#customerNDialog_notes').val('');
            $('#customerNDialog').window('setTitle','客服通知').window('open');
            $('#requisition').datagrid({url:"/notice/queryNoticeHist/"+$("#tbOrderId").val()});
        }
    });
}