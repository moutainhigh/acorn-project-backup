var queryNoticeParams={};
$(function(){
    $.ajax({
        url: '/notice/queryNoticeType',
        contentType: "application/json",
        success:function(data){
            nameListSelectHtml = '';
            for (var i = 0; i < data.names.length; i++) {
                var name = data.names[i];
                nameListSelectHtml += '<option value=\''+name.dsc+'\'>'+name.dsc+'</option>';
            }
            $("#noteclass").append(nameListSelectHtml);
        }
    });

    $("#gendate_begin").datetimebox('setValue', new Date(new Date()-7*86400000).format('yyyy-MM-dd')+" 00:00:00");
    $("#gendate_end").datetimebox('setValue', new Date().format('yyyy-MM-dd')+" 23:59:59");

    $("#redate_begin").datetimebox('setValue', new Date(new Date()-7*86400000).format('yyyy-MM-dd')+" 00:00:00");
    $("#redate_end").datetimebox('setValue', new Date().format('yyyy-MM-dd')+" 23:59:59");


    $('#msgNotification').datagrid({
        width : '100%',
        height : 180,
        scrollbarSize:0,
        method:'post',
        nowrap:true,
        rownumbers:true,
        fitColumns : false,
        columns : [ [
            {
                field : 'isreplay',
                title : '是否回复',
                align: 'center',
                width: 60,
                formatter:function(val) {
                    if (val == 0) return '否';
                    else return '是';
                }
            }, {
                field : 'gendate',
                align:'center',
                title : '生成时间',
                width: 120,
                formatter:function(val) {
                    if(null!=val){
                        return new Date(val).format('yyyy-MM-dd hh:mm:ss');
                    }
                }
            }, {
                field : 'genseat',
                title : '生成座席',
                width: 100,
                align:'center'
            }, {
                field : 'noteclass',
                title : '通知分类',
                align: 'center',
                width: 100
            },{
                field : 'noteremark',
                title : '通知备注',
                width: 300,
                align: 'center'
            },{
                field : 'redate',
                title : '回复时间',
                width: 120,
                align: 'center',
                formatter:function(val) {
                    if(null!=val){
                        return new Date(val).format('yyyy-MM-dd hh:mm:ss');
                    }
                }
            },{
                field : 'reseat',
                title : '回复坐席',
                width: 100,
                align: 'center'
            },{
                field : 'renote',
                title : '回复内容',
                width: 300,
                align: 'center'
            },{
                field : 'featstr',
                title : '通知单序号',
                align: 'center',
                width: 30
            }
        ] ],
        remoteSort : false,
        singleSelect : true,
        pagination : true,
        onDblClickRow : function(rowIndex, rowData) {
            openReCusnote(rowData);
        }
    });

    $('#requisition').datagrid({
        width : '100%',
        height : 180,
        scrollbarSize:0,
        method:'get',
        nowrap:true,
        rownumbers:true,
        fitColumns : false,
        columns : [ [
            {
                field : 'isreplay',
                title : '是否回复',
                align: 'center',
                width: 60,
                formatter:function(val) {
                    if (val == 0) return '否';
                    else return '是';
                }
            }, {
                field : 'gendate',
                align:'center',
                title : '生成时间',
                width: 120,
                formatter:dateFormatter
            }, {
                field : 'genseat',
                title : '生成座席',
                width: 100,
                align:'center'
            }, {
                field : 'noteclass',
                title : '通知分类',
                align: 'center',
                width: 100
            },{
                field : 'noteremark',
                title : '通知备注',
                width: 300,
                align: 'center'
            },{
                field : 'redate',
                title : '回复时间',
                width: 120,
                align: 'center',
                formatter:dateFormatter
            },{
                field : 'reseat',
                title : '回复坐席',
                width: 100,
                align: 'center'
            },{
                field : 'renote',
                title : '回复内容',
                width: 300,
                align: 'center'
            },{
                field : 'featstr',
                title : '通知单序号',
                align: 'center',
                width: 30
            }
        ] ],
        remoteSort : false,
        singleSelect : true,
        pagination : true,
        onClickRow : function(owIndex, rowData) {
            resetNotice(rowData);
        }
    });
});

function findNotice() {
    if ($("#gendate_begin").datetimebox('getValue') != '' && $("#gendate_end").datetimebox('getValue')!='' && $("#reseat").validatebox('isValid')) {
        queryNoticeParams = {
            'isreplay' : $("#isreplay").val(),
            'noteclass': $("#noteclass").val(),
            'gendate_begin': $("#gendate_begin").datetimebox('getValue'),
            'gendate_end': $("#gendate_end").datetimebox('getValue'),
            'genseat': $("#genseat").val(),
            'redate_begin': $("#redate_begin").datetimebox('getValue'),
            'redate_end' : $("#redate_end").datetimebox('getValue'),
            'reseat' : $("#reseat").val()
        };

        $('#msgNotification').datagrid({url : '/notice/queryNoticeHistForTask',
                                     queryParams : queryNoticeParams});
    }
}

function openReCusnote(rowData) {
    $.ajax({
        url: '/notice/queryInfo/'+rowData.orderid,
        contentType: "application/json",
        success:function(data){
            if (data.error) {
                window.parent.alertWin('系统提示', data.error);
                return;
            }
            $('#customerNDialog_contactName').val(data.contactName);
            $('#customerNDialog_orderId').html(rowData.orderid);
            $('#customerNDialog_featstr').val(rowData.featstr);
            $('#customerNDialog_orderTime').val(dateFormatter(data.orderCrdt));
            $('#customerNDialog_eventId').val(rowData.caseid);
            $('#customerNDialog_notes').val(rowData.noteremark);
            $('#customerNDialog_noticeType').val(rowData.noteclass);
            if (rowData.isreplay==-1) {
                $('#customerNDialog_feedBack').val(rowData.renote);
                $('#customerNDialog_feedBack').attr("disabled", true);
            } else {
                $('#customerNDialog_feedBack').val('');
                $('#customerNDialog_feedBack').attr("disabled", false);
            }
            $('#customerNDialog').window('setTitle','客服通知').window('open');
            $('#requisition').datagrid({url:"/notice/queryNoticeHist/"+rowData.orderid});
        }
    });
}

function cleanNotice() {
    if ($("#customerNDialog_feedBack").attr('disabled')!='disabled') $("#customerNDialog_feedBack").val('');
}

function resetNotice(rowData) {
    $('#customerNDialog_eventId').val(rowData.caseid);
    $('#customerNDialog_featstr').val(rowData.featstr);
    $('#customerNDialog_notes').val(rowData.noteremark);
    $('#customerNDialog_noticeType').val(rowData.noteclass);
    if (rowData.isreplay==-1) {
        $('#customerNDialog_feedBack').val(rowData.renote);
        $('#customerNDialog_feedBack').attr("disabled", true);
    } else {
        $('#customerNDialog_feedBack').val('');
        $('#customerNDialog_feedBack').attr("disabled", false);
    }
}

function updateNotice() {
    $("#saveNoticeButton").hide();
    if ($("#customerNDialog_feedBack").val() == null || $.trim($("#customerNDialog_feedBack").val()) == '') {
        window.parent.alertWin('系统提示', '通知回复信息不能为空。');
        $("#saveNoticeButton").show();
        return;
    }
    if ($("#customerNDialog_feedBack").attr('disabled')=='disabled') {
        window.parent.alertWin('系统提示', '已回复的通知，不需要再次保存。');
        $("#saveNoticeButton").show();
        return;
    }
    $.post('/notice/update',
        {"orderid":$("#customerNDialog_orderId").html(), "featstr": $("#customerNDialog_featstr").val(), "renote": $("#customerNDialog_feedBack").val()},
        function(){
            $("#saveNoticeButton").show();
            $('#customerNDialog').window('close');
            $('#msgNotification').datagrid('reload');
        });
}

function viewOrder() {
    var orderId = $("#customerNDialog_orderId").html();
    var url='myorder/orderDetails/get/'+orderId+'?activable=false';
    window.parent.addTab('myorder_'+orderId,'订单：'+orderId,'false',url);
}

function dateFormatter(val){
    if(null!=val){
        return new Date(val).format('yyyy-MM-dd hh:mm:ss');
    }
}