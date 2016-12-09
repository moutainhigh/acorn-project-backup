$(function(){
    $("#queryContact").click(function() {
        window.parent.queryC(frameElement.id,'updateContactInfo','createComplain');
    });
    $("#submitComplain").click(function() {
        submitComplain();
    });
    $("#cancelComplain").click(function() {
        cancelComplain();
    });
    if (window.parent.openedContact.length==1) {
        $.ajax({
            url: '/complain/getContactInfo/' + window.parent.openedContact[0],
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                updateContactInfo(data.contactid,data.name);
            }});
    }

    $("#priority").combobox('setValue', 2);
});

function updateContactInfo(contactId, contactName) {
    $("#contactId").val(contactId);
    $("#name").val(contactName);

    $.ajax({
        url: '/myorder/myorder/query_contact',
        type: 'POST',
        dataType: 'json',
        data : {
            'contactId' : contactId,
            'rows': 50
        },
        success: function (data) {
            $("#orderId").empty();
            $("#orderId").append('<option value="">-请选择-</option>');
            $.each(data.rows,function(index,b){
                var html = '<option value="'+ b.orderid+'">'+b.orderid + ' | ' + b.crdt+ ' | ' + orderStatusFormatter(b.status) +'</option>';
                $("#orderId").append(html);
            })
        }});
}

function orderStatusFormatter(val){
    var _label = '';
    if(val==0){
        _label = '取消';
    }else if(val==1){
        _label = '订购';
    }else if(val==2){
        _label = '分拣';
    }else if(val==5){
        _label = '完成';
    }else if(val==6){
        _label = '拒收';
    }else if(val==7){
        _label = '压单';
    }else if(val==8){
        _label = '物流取消';
    }else if(val==10){
        _label = '作废';
    }
    return _label;
}

function submitComplain() {
    if ($("#contactId").val()==null || $("#contactId").val()=='') {
        window.parent.alertWin('系统提示', '投诉单必须绑定指定客户。');
        return;
    }

    $.ajax({
        url: '/complain/submitComplain',
        type: 'POST',
        dataType: 'json',
        data : {
            'contactId' : $("#contactId").val(),
            'contactName' : $("#name").val(),
            'orderId' : $("#orderId").val(),
            'prod' : $("#product").val(),
            'phone' : $("#phone").val(),
            'priovity' : $("#priority").combobox('getValue'),
            'content' : $("#content").val()
        },
        success: function (data) {
            if (data) {
                window.parent.alertWin('系统提示', '生成投诉单成功。');
                cancelComplain();
            }
            else window.parent.alertWin('系统提示', '生成投诉单出错，请联系管理员。');
        }});
}

function cancelComplain() {
    $("#contactId").val('');
    $("#name").val('');
    $("#orderId").val('');
    $("#product").val('');
    $("#phone").val('');
    $("#priority").combobox('setValue', 2);
    $("#content").val('');
}