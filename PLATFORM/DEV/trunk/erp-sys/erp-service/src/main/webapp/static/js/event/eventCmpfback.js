function eventCmpfbackDialog(caseId){
    $('#cmpfback_event_cate1').combobox({
        url:'/cmpfback/cmpCate1'  ,
        valueField:'cate1',
        textField:'cate1',
        onSelect: function(param){
            $('#cmpfback_event_cate2').combobox('clear');
            var url = '/cmpfback/cmpCate2/'+param.cate1;
            $('#cmpfback_event_cate2').combobox('reload', url);
        }
    });

    $('#cmpfback_event_cate2').combobox({
        valueField:'cate2',
        textField:'cate2'
    });

    $.ajax({url:'/cmpfback/case/'+caseId  ,
        type: 'GET',
        async:false,
        dataType: 'JSON',
        success:function(data){
            var cmpfbackEventDto = eval(data);
            if(cmpfbackEventDto.resultMessage){
                window.parent.alertWin("警告",cmpfbackEventDto.resultMessage);
                return;
            }else{
                $('#cmpfback_event_dialog').window('setTitle','投诉处理').window('open');
                $("#cmpfback_event_tabs").tabs("select", 0);
            }

            $("#cmpfback_event_id").val(caseId);
            $("#cmpfback_event_contactId").val(cmpfbackEventDto.contactid);
            $("#cmpfback_event_contactName").val(cmpfbackEventDto.contactName);

            $("#cmpfback_event_address").val(cmpfbackEventDto.address);
            $("#cmpfback_event_phone").val(cmpfbackEventDto.phone);
            $("#cmpfback_event_phoneMask").val(cmpfbackEventDto.phoneMask);

            $("#cmpfback_event_zip").val(cmpfbackEventDto.zip);

            $("#cmpfback_event_orderid").val(cmpfbackEventDto.orderid);
            $("#cmpfback_event_productdsc").val(cmpfbackEventDto.productdsc);
            if (cmpfbackEventDto.ordercrdt) {
                $("#cmpfback_event_ordercrdt").datetimebox('setValue', new Date(cmpfbackEventDto.ordercrdt).format("yyyy-MM-dd hh:mm:ss"));
            }

            $("#cmpfback_event_probdsc").val(cmpfbackEventDto.probdsc);


            $("#cmpfback_event_chuliqk").val(cmpfbackEventDto.chuliqk);

            $("#cmpfback_event_crusr").val(cmpfbackEventDto.crusr);
            $("#cmpfback_event_crdt_hide").val(new Date(cmpfbackEventDto.crdt).format("yyyy-MM-dd hh:mm:ss"));
            $("#cmpfback_event_crdt").datebox('setValue', new Date(cmpfbackEventDto.crdt).format("yyyy-MM-dd"));

            $("#cmpfback_event_chuliusr").val(cmpfbackEventDto.chuliusr);
            $("#cmpfback_event_chulidt_hide").val(new Date(cmpfbackEventDto.chulidt).format("yyyy-MM-dd hh:mm:ss"));
            $("#cmpfback_event_chulidt").datebox('setValue', new Date(cmpfbackEventDto.chulidt).format("yyyy-MM-dd"));

            $("#cmpfback_event_fbusr").val(cmpfbackEventDto.fbusr);
            $("#cmpfback_event_fbdt_hide").val(new Date(cmpfbackEventDto.fbdt).format("yyyy-MM-dd hh:mm:ss"));
            $("#cmpfback_event_fbdt").datebox('setValue', new Date(cmpfbackEventDto.fbdt).format("yyyy-MM-dd"));

            $("#cmpfback_event_feedbackdsc").val(cmpfbackEventDto.feedbackdsc);
            $("input[type='radio'][name='cmpfback_event_result'][value='" + cmpfbackEventDto.result + "']").attr("checked", "checked");
            $("#cmpfback_event_reason").val(cmpfbackEventDto.reason);

            //协办部门【相关部门处理情况】                       【原因】【结果】
            //非协办部门【投诉内容】【处理意见】【反馈】【原因】【结果】
            if (cmpfbackEventDto.cmpfbackDepartment) {
                $("#cmpfback_event_probdsc").attr("disabled", true);
                setCmpfbackYijian(cmpfbackEventDto.yijian, cmpfbackEventDto.yijian2, true);
                $("#cmpfback_event_chuliqk").attr("disabled", false);
                $("#cmpfback_event_feedbackdsc").attr("disabled", true);
            } else {
                $("#cmpfback_event_probdsc").attr("disabled", false);
                //客服主管
                if (cmpfbackEventDto.complainManager) {
                    $("#cmpfback_event_chuliqk").attr("disabled", false);
                } else {
                    $("#cmpfback_event_chuliqk").attr("disabled", true);
                }
                setCmpfbackYijian(cmpfbackEventDto.yijian, cmpfbackEventDto.yijian2, false);
                $("#cmpfback_event_feedbackdsc").attr("disabled", false);

            }
        }
    });
}

function setCmpfbackYijian(yijian, yijian2, valiable) {
    $('#cmpfback_event_cate1').combobox('clear');
    $('#cmpfback_event_cate2').combobox('clear');
    $('#cmpfback_event_cate1').combobox({
        disabled: valiable
    });
    $('#cmpfback_event_cate2').combobox({
        disabled: valiable
    });
    if (yijian) {
        $('#cmpfback_event_cate1').combobox({
            value: yijian
        });
        var url = '/cmpfback/cmpCate2/' + yijian;
        $('#cmpfback_event_cate2').combobox('reload', url);
        if (yijian2) {
            $('#cmpfback_event_cate2').combobox({
                value: yijian2
            });
        }
    }
}

function cmpfback_event_next(){
    $("#cmpfback_event_tabs").tabs("select", 1);
}

function cmpfback_event_closeWin(){
    $('#cmpfback_event_dialog').window('close');
}

function cmpfback_event_save(){
    $.ajax({url:'/cmpfback/save' ,
        type: 'POST',
        async:false,
        dataType: 'JSON',
        data:{
            caseid:$("#cmpfback_event_id").val(),
            contactid:$("#cmpfback_event_contactId").val(),
            orderid:$("#cmpfback_event_orderid").val(),
            productdsc:$("#cmpfback_event_productdsc").val(),
            ordercrdt:$("#cmpfback_event_ordercrdt").datetimebox('getValue'),
            probdsc:$("#cmpfback_event_probdsc").val(),
            yijian:$("#cmpfback_event_cate1").combobox("getValue"),
            crusr:$("#cmpfback_event_crusr").val(),
            crdt:$("#cmpfback_event_crdt_hide").val(),
            chuliqk:$("#cmpfback_event_chuliqk").val(),
            chuliusr:$("#cmpfback_event_chuliusr").val(),
            chulidt:$("#cmpfback_event_chulidt_hide").val(),
            feedbackdsc:$("#cmpfback_event_feedbackdsc").val(),
            fbdt:$("#cmpfback_event_fbdt_hide").val(),
            fbusr:$("#cmpfback_event_fbusr").val(),
            result:getCmpfbackResult,
            reason:$("#cmpfback_event_reason").val(),
            yijian2:$("#cmpfback_event_cate2").combobox("getValue"),
            phone:$("#cmpfback_event_phone").val()


        },
        success:function(data){
            if(data=='success'){
                cmpfback_event_closeWin();
            }else{
                window.parent.alertWin("警告",data);
            }
        }
    });
}

function cmpfback_event_print(){
    $("#cmpfback_print_contactName").html( $("#cmpfback_event_contactName").val());
    $("#cmpfback_print_ordercrdt").html($("#cmpfback_event_ordercrdt").datetimebox('getValue'));
    $("#cmpfback_print_orderid").html($("#cmpfback_event_orderid").val());

    $("#cmpfback_print_date").html((new Date()).format("yyyy-MM-dd"));
    $("#cmpfback_print_productdsc").html($("#cmpfback_event_productdsc").val());

    $("#cmpfback_print_phoneNum").html($("#cmpfback_event_phone").val());
    $("#cmpfback_print_probdsc").html($("#cmpfback_event_probdsc").val());
    $("#cmpfback_print_cate").html($("#cmpfback_event_cate1").combobox("getValue")+$("#cmpfback_event_cate2").combobox("getValue"));
    $("#cmpfback_print_crusr").html($("#cmpfback_event_crusr").val());
    $("#cmpfback_print_chuliqk").html($("#cmpfback_event_chuliqk").val());
    $("#cmpfback_print_chuliusr").html($("#cmpfback_event_chuliusr").val());

    var myWindow=window.open('_blank','协办打印','','');
    myWindow.document.write($("#cmpfback_print").html());
    myWindow.document.close();
    myWindow.focus();
    myWindow.print();


}

function getCmpfbackResult(){
   var result = $("input[type='radio'][name='cmpfback_event_result']:checked").val();
    if(result){
        return result;
    }
    return "0";
}