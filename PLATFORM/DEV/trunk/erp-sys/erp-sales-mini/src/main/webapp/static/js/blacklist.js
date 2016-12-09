$(document).ready(function () {
    $('#blackPhoneTable').datagrid({
        view: detailview,
        detailFormatter: function (index, row) {
            return '<table style="" id="ddv-' + index + '"></table>';
        },
        onExpandRow: function (index, row) {
            $('#ddv-' + index).datagrid({
                fitColumns: true,
                singleSelect: true,
                scrollbarSize: -1,
                loadMsg: '',
                columns: [
                    [
                        {field: 'potentialContactId', title: '客户编号', width: 100, align: 'center',
                            formatter:function(val, rowData) {
                                if(val == null) return rowData.contactId;
                                else return val;
                        }},
                        {field: 'createUser', title: '坐席工号', width: 200, align: 'center'},
                        {field: 'createDate', title: '加黑申请时间', width: 100, align: 'center',formatter: dateFormatter},
                        {field: 'createUserGrp', title: '坐席组', width: 100, align: 'center'}
                    ]
                ],
                onResize: function () {
//                    $('#blackPhoneTable').datagrid('fixDetailRowHeight', index);
                },
                onLoadSuccess: function () {
                    setTimeout(function () {
                        $('#blackPhoneTable').datagrid('fixDetailRowHeight', index);
                    }, 200);
                }
            });
//            $('blackPhoneTable').datagrid('fixDetailRowHeight', index);
            $('#ddv-' + index).datagrid("loadData", {"total": row['blackLists'].length, "rows": row['blackLists']});
        },
        width: '100%',
        height: 410,
        scrollbarSize: -1,
        fitColumns: true,
        columns: [
            [
                {
                    field: 'phoneNum',
                    title: '电话',
                    align: 'center',
                    width: 60
                },
                {
                    field: 'addTimes',
                    title: '加黑申请次数',
                    align: 'center',
                    width: 40
                },
                {
                    field: 'approveManager',
                    title: '操作人',
                    align: 'center',
                    width: 40
                },
                {
                    field: 'approveDate',
                    title: '操作时间',
                    align: 'center',
                    width: 120,
                    formatter: dateFormatter
                },
                {
                    field: 'status',
                    title: '状态',
                    align: 'center',
                    width: 80,
                    formatter: orderStatusFormatter
                },
                {
                    field: 'action',
                    title: '操作',
                    width: 80,
                    align: 'center',
                    formatter:function(value, rowData) {
                        if (rowData.status == 1) return '' +
                            '<div class="orderInfo_btns"><a class="add" href="javascript:void(0)" onclick="toggleBlackPhone('+rowData.status+', '+rowData.blackPhoneId+')" >加黑</a></div>';
                        if (rowData.status == 2) return '' +
                            '<div class="orderInfo_btns"><a class="add" href="javascript:void(0)" onclick="toggleBlackPhone(' + rowData.status + ', ' + rowData.blackPhoneId + ')" >去黑</a></div>';
                    }
                }
            ]
        ],
        remoteSort: false,
        singleSelect: true,
        pagination: true,
        onLoadSuccess: function (data) {
            if (data.err != null) {
                window.parent.alertWin('系统提示', data.err);
            }
        },
        onLoadError: function () {
            window.parent.alertWin('系统提示', "加载失败");
        }
    });

    $("#messagesearch").bind('click', function() {
            $('#blackPhoneTable').datagrid({
                url:'/blackList/query',
                queryParams: {
                    'phoneNum': $.trim($('#phoneNum').val()),
                    'addTimes': $.trim($('#addTimes').val()),
                    'status': $('#status').val()
                }});
    });

    $("#messagereset").bind('click', function() {
        $('#phoneNum').val('');
        $('#addTimes').val('');
        $('#status').val('');
    });
});

function orderStatusFormatter(val){
    var _label = '';
    if(val==1){
        _label = '未加黑';
    }else if(val==2){
        _label = '<span style="color:red;">已加黑</span>';
    }else if(val==3){
        _label = '已去黑';
    }
    return _label;
}

function dateFormatter(val){
    if(null!=val){
        return new Date(val).format('yyyy-MM-dd hh:mm:ss');
    }
}

Date.prototype.format = function(format){
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(),    //day
        "h+" : this.getHours(),   //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
        "S" : this.getMilliseconds() //millisecond
    };

    if(/(y+)/.test(format)) {
        format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }

    for(var k in o){
        if(new RegExp("("+ k +")").test(format)){
            format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
        }
    }
    return format;
};

function toggleBlackPhone(status, blackPhoneId) {
    if (status == 1) {
        $.ajax({
            url: '/blackList/addToBlackPhone/'+blackPhoneId,
            contentType: "application/json",
            success:function(data){
                if(data) $('#blackPhoneTable').datagrid('reload');
                else window.parent.alertWin('系统提示', "加入黑名单失败");
            }
        });
    } else {
        $.ajax({
            url: '/blackList/removeFromBlackPhone/'+blackPhoneId,
            contentType: "application/json",
            success:function(data){
                if(data) $('#blackPhoneTable').datagrid('reload');
                else window.parent.alertWin('系统提示', "移除黑名单失败");
            }
        });
    }
}