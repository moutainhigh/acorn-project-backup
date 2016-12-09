function _handler(){
       $('#tabs').tabs('resize',{width:'100%'});
       $('#AllCustomerDT,#auditCustomerDt,#auditAddCustomerDt').datagrid('resize',{width:'100%'});
}




$(function(){
	//非Inbound
	if(!isINB) {

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
        })
	}

    $.ajax({
        url: '/notice/queryNoticeType',
        contentType: "application/json",
        success:function(data){
            nameListSelectHtml = '';
            for (var i = 0; i < data.names.length; i++) {
                var name = data.names[i];
                nameListSelectHtml += '<option value=\''+name.dsc+'\'>'+name.dsc+'</option>';
            }
            $("#customerNDialog_noticeType").append(nameListSelectHtml);
        }
    });
});

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

function dateFormatter(val){
    if(null!=val){
        return new Date(val).format('yyyy-MM-dd hh:mm:ss');
    }
}



function saveNotice() {
    $("#saveNoticeButton").hide();
    if ($("#customerNDialog_notes").val() == null || $.trim($("#customerNDialog_notes").val()) == '') {
        window.parent.alertWin('系统提示', '通知内容"备注"信息不能为空。');
        $("#saveNoticeButton").show();
        return;
    }
    if ($("#customerNDialog_newNotice").val() == 'no') {
        window.parent.alertWin('系统提示', '如需新增通知，请先"清空"信息。');
        $("#saveNoticeButton").show();
        return;
    }
    $.post('/notice/save',
        {"orderid":$("#customerNDialog_orderId").val(), "caseid": $("#customerNDialog_eventId").val(), "noteclass": $("#customerNDialog_noticeType").val(), "noteremark": $("#customerNDialog_notes").val()},
        function(){
            $("#saveNoticeButton").show();
            $('#customerNDialog').window('close');
        });
}

function cleanNotice() {
    $('#customerNDialog_noticeType').attr("disabled",false);
    $('#customerNDialog_notes').attr("disabled",false);
    $('#customerNDialog_eventId').val($("#tbCaseId").val());
    $('#customerNDialog_notes').val('');
    $('#customerNDialog_newNotice').val('');
    $('#customerNDialog_feedBack').val('');
}

function resetNotice(rowData) {
    $('#customerNDialog_noticeType').attr("disabled",true);
    $('#customerNDialog_notes').attr("disabled",true);
    $('#customerNDialog_eventId').val(rowData.caseid);
    $('#customerNDialog_notes').val(rowData.noteremark);
    $('#customerNDialog_noticeType').val(rowData.noteclass);
    $('#customerNDialog_feedBack').val(rowData.renote);
    $('#customerNDialog_newNotice').val('no');
}


