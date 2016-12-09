$(function(){

    $('#creditCards-grid').datagrid({
        title:'',
        width: '100%',
        striped: false,
        border: false,
        collapsible:false,
        fitColumns:true,
        scrollbarSize:-1,
        url : '/checkout/cc/default',
        columns:[[
            {field:'state',title:'审批',width:80,align: "center",formatter: function(value) {
                if(value==0) return '<span class="exaa">待审核</span>';
                else if(value==1) return '<span class="exaa">审核中</span>';
                else if(value==3) return '<span class="exa">未通过</span>';
                else  return '';
            }},
            {field:'contactId',title:'持卡人客户编号',width:120},
            {field:'contactName',title:'持卡人姓名',width:120},
            {field:'bankName',title:'银行',width:120},
            {field:'typeName',title:'证件类型',width:140},
            {field:'cardNumber',title:'证件号',width:150},
            {field:'validDate',title:'有效期',width:120},
            {field:'lastStatus',title:'分期数',width:60,
                editor: {
                    type:'combobox', options: {
                        valueField:'amortisation',
                        textField:'amortisation',
                        data:[],
                        required:false,
                        onSelect:function(){
                             getOrderMessage();
                        }
                    }
                }
            }
        ]],
        remoteSort:false,
        singleSelect:true,
        queryParams: {
            contactId: $("#payment_contactid").val()
        },
        onLoadSuccess: function(data){
            if(parent && frameElement && parent.SetCwinHeight){
                parent.SetCwinHeight(frameElement);
            }
        },
        onLoadError: function(){
            window.parent.alertWin('系统提示',"加载失败!");
        },
        onClickRow:function(rowIndex, row){
            var lastIndex = $(this).attr("lastIndex");
            if(lastIndex){
                $(this).datagrid('endEdit', lastIndex);
            }
            if(["001","002","011","014"].indexOf($(row).attr("type")) > -1){
                return;
            }
            $(this).datagrid('beginEdit', rowIndex);
            var editors = $(this).datagrid('getEditors', rowIndex);
            if(editors.length > 0){
                $(editors[0].target).combobox("clear");
                $(editors[0].target).combobox("loadData", row.amortisations);
                $(editors[0].target).combobox("setValue", row.lastStatus);
                $(editors[0].target).focus();
            }
            $(this).attr("lastIndex", rowIndex);
        }
    });

    $('#selectCards-grid').datagrid({
        width : '90%',
        showHeader: true,
        nowrap : false,
        striped : true,
        border : true,
        collapsible : false,
        fitColumns:true,
        scrollbarSize:17,
        url : '/checkout/cc/all',
        frozenColumns:[[

        ]],
        columns:[[
            {field:'ck',checkbox:true},
            {field:'state',title:'审批',width:68, align: "center", formatter: function(value) {
                if(value==0) return '<span class="exaa">待审核</span>';
                else if(value==1) return '<span class="exaa">审核中</span>';
                else if(value==3) return '<span class="exa">未通过</span>';
                else  return '';
            }},
            {field:'contactId',title:'持卡人客户编号',width:120},
            {field:'contactName',title:'持卡人姓名',width:120},
            {field:'bankName',title:'银行',width:120},
            {field:'typeName',title:'证件类型',width:80},
            {field:'cardNumber',title:'证件号',width:150},
            {field:'validDate',title:'有效期',width:120},
            {field:'lastStatus',title:'分期数',width:60},
            {field:'o',title:'操作',width:50,align:'center', formatter:function(value,row,index){
                return "<a class='link modify ' title='编辑' href='javascript:editCard("+ index +")'></a>";
            }}
        ]],
        remoteSort:false,
        singleSelect:false,
        checkOnSelect: true,
        selectOnCheck: true,
        queryParams:{
            contactId: $("#payment_contactid").val()
        },
        onClickRow:function(rowIndex, row){

        },
        onLoadSuccess: function(data){
            //alert(JSON.stringify(data));
            if(data.total > 0) {
                $('#selectCards-list').show();
                $('#selectCards-grid').datagrid("resize");
                if(parent && frameElement && parent.SetCwinHeight){
                    parent.SetCwinHeight(frameElement);
                }
            }
            else{
                //$('#selectCards-list').hide();
            }
        },
        onLoadError: function(){
            window.parent.alertWin('系统提示',"加载失败!");
        }
    });


    $("#tbTypeOptions").change(function(){

        var type = $(this).val();
        if(type){

            $("#lblType").parents('tr').show();
//            $("#tbType").show();

            $("#lblBank").parents('tr').show();
//            $("#tbBank").show();

            $("#lblCardNumber").parents('tr').show();
//            $("#tbCardNumber").show();

            $("#lblValidDate").parents('tr').show();
//            $("#lblValidDate2").show();

            switch(type){
                case "0":{
                    $("#tbType").find("div > option").unwrap().removeAttr("selected");
                    $("#tbType").find("option").wrap("<div/>");
                    $("#tbType").find("div > option:first").unwrap();
                    $("#tbType").find("div > option[value='014']").unwrap();
                    $("#tbType").find("div > option[value='011']").unwrap();
                    $("#tbType").find("div > option[value='002']").unwrap();
                    $("#tbType").find("div > option[value='001']").unwrap();
                    $('#tbBank').val("");
                    $('#tbBank').closest("tr").find("td[accesskey='bank']").hide();
                } break;
                case "1":{
                    $("#tbType").find("div > option").unwrap().removeAttr("selected");
                    $('#tbBank').closest("tr").find("td[accesskey='bank']").show();
                } break;
                default: {
                    $("#tbType").find("div > option").unwrap().removeAttr("selected");
                } break;
            }
        }
        window.parent.SetCwinHeight(frameElement);
    });

    $('#tbBank').change(function(){
        var bank = $(this).val();
        if(bank){
            var opts =$("#tbType").find( "option[title='"+bank+"']");
            if(opts.length > 0){
                $("#tbType").val($(opts.get(0)).attr("value"));
            }
        } else {
            $('#tbType').val("");
        }
    });

    $("#tbType").change(function(){
        //var type = $(this).val(); fixed
        var type = $("#tbType > option").eq($("#tbType").get(0).selectedIndex).attr("value");
        if(type){
            if(["001","002","011","014"].indexOf(type) > -1){
                $('#tbBank').val("");
            } else {
                var opt =  $(this).find("option[value='"+type+"']");
                if(opt){
                    var bank = $(opt).attr("title");
                    $("#tbBank").find("option[value='"+bank+"']").attr("selected", true);
                    $("#tbBank").val(bank);
                }
            }
        } else {
            $('#tbBank').val("");
        }
    });

    $("#tbValidYear").numberbox({
        formatter: function(value) {
            return value.length > 0 ? (value + "000").substring(0, 4) : value;
        }
    });

    $("#tbValidMonth").numberbox({
        formatter: function(value) {
            return value.length < 2 && value.length > 0 ? '0'+value : value;
        }
    });

    $('#lnkCardConfirm').click(function(){
        var rows = $('#selectCards-grid').datagrid('getSelections');
        if (rows && rows.length > 0){
            //检查收货地址和付款人是否一致
            //加查是否一张信用卡或身份证件
            var ccRow = null;
            var idRow = null;
            var addressContactId = getAddressContactId();
            for(var i = 0; i < rows.length; i++){
                var contactId = $(rows[i]).attr("contactId");
                if(contactId != addressContactId){
                    window.parent.alertWin('系统提示',"信用卡订单付款人与收货人必须一致");
                    return;
                }

                if(["001","002","011","014"].indexOf($(rows[i]).attr("type")) > -1){
                    idRow = rows[i];
                } else {
                    if(ccRow) {
                        window.parent.alertWin('系统提示',"最多只能使用一张信用卡");
                        return;
                    } else {
                        ccRow = rows[i];
                    }
                }
            }

            if(idRow && idRow.state=="1"){
                window.parent.alertWin('系统提示',"不能使用正在审批的身份证件");
                return;
            } else  if(idRow && idRow.state=="3"){
                window.parent.alertWin('系统提示',"不能使用未通过的身份证件");
                return;
            }

            if(ccRow && ccRow.state=="1"){
                window.parent.alertWin('系统提示',"不能使用正在审批的信用卡");
                return;
            } else  if(ccRow && ccRow.state=="3"){
                window.parent.alertWin('系统提示',"不能使用未通过审批的信用卡");
                return;
            }

            $('#creditCards-grid').datagrid("loadData", rows);
            $("#creditCards-grid").datagrid('resize', {height:'auto', width: '100%'})
            $('#selectCards-div').hide();
            $('#id_creditTitle_div').hide();

            getOrderMessage();

        }
        else
        {
            window.parent.alertWin('系统提示',"请先选择一笔证件或卡记录");
        }
        window.parent.SetCwinHeight(frameElement);
    });

    $('#lnkCardNew').click(function(){
        $('#tbType').val("");
        $('#tbBank').val("");
        $('#tbCardNumber').val("");
        $('#tbValidYear').numberbox("setValue", "");
        $('#tbValidMonth').numberbox("setValue", "");
        $('#tbCardId').val("");
        $('#tbTypeOptions').val("");
        $("#tbType").find( "span > option" ).unwrap().removeAttr("selected");

        $("#lblType").parents('tr').hide();
//        $("#tbType").hide();

        $("#lblBank").parents('tr').hide();
//        $("#tbBank").hide();

        $("#lblCardNumber").parents('tr').hide();
//        $("#tbCardNumber").hide();

        $("#lblValidDate").parents('tr').hide();
//        $("#lblValidDate2").hide();

        $("#card-container-div").show();
        window.parent.SetCwinHeight(frameElement);
    });

    $('#lnkCardCancel').click(function(){
        $("#card-container-div").hide();
        window.parent.SetCwinHeight(frameElement);
    });

    $('#lnkCardSave').click(function(){

        var year = $('#tbValidYear').numberbox("getValue");
        var month = $('#tbValidMonth').numberbox("getValue");
        var validDate = '';
        if(year && month) {
            if(month.length < 2){
                month = "0" +month;
            }
            validDate = year + "-" + month
        }
        //信用卡有效期不能为空
        var type = $("#tbType > option").eq($("#tbType").get(0).selectedIndex).attr("value");
        if(!type){
            window.parent.alertWin('系统提示',"证件类型不能为空！");
            $('#tbType').focus();
            return;
        }
        //var type = $('#tbType').val();
        if(["001","002","011","014"].indexOf(type) == -1){
            if(validDate == ''){
                window.parent.alertWin('系统提示',"信用卡有效期不能为空");

                $('#tbValidYear').focus();
                return;
            }
            else if(new Date(validDate+"-01") < new Date()){
                window.parent.alertWin('系统提示',"信用卡有效期已经过期");

                $('#tbValidYear').focus();
                return;
            }
        }

        $.post("/checkout/cc/save",
            {
                contactId : $('#payment_contactid').val(),
                cardType : type,
                cardNumber:$('#tbCardNumber').val(),
                validDate: validDate,
                cardId :$('#tbCardId').val()
            },
            function(result){
                if(result){
                    $('#selectCards-grid').datagrid("load", { contactId:$('#payment_contactid').val() });
                    if(result.indexOf("失败") > -1){
                        window.parent.alertWin('系统提示',result);
                    } else{
                        $("#card-container-div").hide();
                    }
                    if(parent && frameElement && parent.SetCwinHeight){
                        parent.SetCwinHeight(frameElement);
                    }
                }
                window.parent.alertWin('系统提示',result);
            },
            "text"
        );

    });

    $('#lnkAuth').click(function(){
        var rows = $('#creditCards-grid').datagrid('getRows');
        if (rows && rows.length > 0){
            /*
             * 检查一个信用卡，一个身份证件
             * 001=身份证 002=护照 011=军官证 014=台胞证
             * */
            var ccRow = null;
            var idRow = null;
            var lastStatus = 1;
            for(var i = 0; i < rows.length; i++){
                $("#creditCards-grid").datagrid('endEdit', i);

                if(["001","002","011","014"].indexOf($(rows[i]).attr("type")) > -1){
                    idRow = rows[i];
                } else {
                    if(ccRow) {
                        window.parent.alertWin('系统提示',"最多只能使用一张信用卡");
                        return;
                    } else {
                        ccRow = rows[i];
                        lastStatus = $(ccRow).attr("lastStatus");
                    }
                }
            }

            if(ccRow == null || idRow == null){
                window.parent.alertWin('系统提示',"至少必须有一张信用卡与一个身份证件");

                return;
            }

            if(!lastStatus){
                lastStatus = 1;
            }

            var amortisations = $(ccRow).attr("amortisations");
            if(amortisations.length == 0){
                window.parent.alertWin('系统提示',"一张信用卡设置需要设置相应分期数");

                return;
            } else {
                var lprice = 0;
                var uprice = 0;
                var match = false;
                for(var i = 0; i < amortisations.length; i++){
                    if(amortisations[i].amortisation == lastStatus){
                        lprice = amortisations[i].lprice;
                        uprice = amortisations[i].uprice;
                        match = true;
                        break;
                    }
                }
                if(match){
                    var amount = $("#tbPayAmount").val();
                    if(amount < lprice || amount >= uprice){
                        window.parent.alertWin('系统提示',"当前信用卡分期数["+lastStatus+"]的金额范围："+lprice+"-"+uprice);

                        return;
                    }
                } else {
                    window.parent.alertWin('系统提示',"当前信用卡分期数不正确");

                    return;
                }
            }

            $.post("/checkout/cc/auth",
                {
                    orderId : $('#tbOrderId').val(),
                    ccCardId: $(ccRow).attr("cardId"),
                    idCardId: $(idRow).attr("cardId"),
                    extraCode: $("#tbExtraCode").val(),
                    lastStatus: lastStatus
                },
                function(result){
                    if(result){
                        window.parent.alertWin('系统提示', "信用卡索权成功！");
                    }
                    else
                    {
                        window.parent.alertWin('系统提示', "信用卡索权失败！");
                    }
                }
            );
        }
    });

    $('#lnkPay').click(function(){

        var rows = $('#creditCards-grid').datagrid('getRows');
        if (rows && rows.length > 0){
            /*
             * 检查一个信用卡，一个身份证件
             * 001=身份证 002=护照 011=军官证 014=台胞证
             * */
            var ccRow = null;
            var idRow = null;
            var lastStatus = 1;
            for(var i = 0; i < rows.length; i++){
                $("#creditCards-grid").datagrid('endEdit', i);

                if(["001","002","011","014"].indexOf($(rows[i]).attr("type")) > -1){
                    idRow = rows[i];
                } else {
                    if(ccRow) {
                        window.parent.alertWin('系统提示',"最多只能使用一张信用卡");
                        return;
                    } else {
                        ccRow = rows[i];
                        lastStatus = $(ccRow).attr("lastStatus");
                    }
                }
            }

            if(ccRow == null || idRow == null){
                window.parent.alertWin('系统提示', "至少必须有一张信用卡与一个身份证件");

                return;
            }

            if(!lastStatus){
                lastStatus = 1;
            }

            var amortisations = $(ccRow).attr("amortisations");
            if(amortisations.length == 0){
                window.parent.alertWin('系统提示', "一张信用卡设置需要设置相应分期数");

                return;
            } else {
                var lprice = 0;
                var uprice = 0;
                var match = false;
                for(var i = 0; i < amortisations.length; i++){
                    if(amortisations[i].amortisation == lastStatus){
                        lprice = amortisations[i].lprice;
                        uprice = amortisations[i].uprice;
                        match = true;
                        break;
                    }
                }
                if(match){
                    var amount = $("#tbPayAmount").val();
                    if(amount < lprice || amount >= uprice){
                        window.parent.alertWin('系统提示', "当前信用卡分期数["+lastStatus+"]的金额范围："+lprice+"-"+uprice);
                        return;
                    }
                } else {
                    window.parent.alertWin('系统提示', "当前信用卡分期数不正确");

                    return;
                }
            }

            $("#tbExtraCode").val($("#tbExtraCode").val().replace(/^\s+|\s+$/g, ""));

            if($(ccRow).attr("showextracode") == "0"){

            }
            else if(!$("#tbExtraCode").val())
            {
                window.parent.alertWin('系统提示', "请输入信用卡附加码!");
                $("#tbExtraCode").focus();
                return;
            }

            $.post("/checkout/cc/pay",
                {
                    orderId : $('#tbOrderId').val(),
                    ccCardId: $(ccRow).attr("cardId"),
                    idCardId: $(idRow).attr("cardId"),
                    extraCode: $("#tbExtraCode").val(),
                    lastStatus: lastStatus
                },
                function(result){
                    if(result.indexOf("支付成功") > -1){
                        matchDelivery($('#tbOrderId').val(), 2);
                    }else {
                        window.parent.alertWin('系统提示', result);
                    }
                },
                "text"
            );
        }
        else
        {
            window.parent.alertWin('系统提示', "请选择信用卡及其证件记录!");
        }
    });

    $('#lnkCancel').click(function(){
        if(confirm("真的要取消订单吗？")){
            $.post("/checkout/cc/cancel",
                {
                    orderId : $('#tbOrderId').val()
                },
                function(result){
                    if(result){
                        window.parent.alertWin('系统提示', "订单取消成功!");
                    }
                    else
                    {
                        window.parent.alertWin('系统提示', "订单取消失败!");
                    }
                }
            );
        }
    });

    $('#lnkContact').click(function(){
        selectContactAddress2();
    });

    $('#modifyDCBtn').click(function(){
        $('#lnkCardNew').click();
        $('#selectCards-div').toggle();
        $('#id_creditTitle_div').toggle();
        var rows = $('#selectCards-grid').datagrid('getRows');
        if(rows==0)$("#selectCards-grid").datagrid('resize', { width: '100%',height:60});
        $("#selectCards-grid").datagrid('resize', { width: '100%',height:150});
//        $('#selectCards-grid').datagrid("resize");
        $("#card-container-div").hide();
//        if(parent && frameElement && parent.SetCwinHeight){
        parent.SetCwinHeight(frameElement);
//        }
    });

    $('#selectCards-div').hide();
});

function initCreditCard(){
    checkPayerName($("#payment_contactid").val());

    $('#creditCards-grid').datagrid("load", {
        contactId: $("#payment_contactid").val()
    });
    $('#selectCards-grid').datagrid("load", {
        contactId: $("#payment_contactid").val()
    });
    $('#selectCards-div').hide();
}

function editCard(index){
    var row = $('#selectCards-grid').datagrid('getRows')[index];
    if(row){
        if(["001","002","011","014"].indexOf($(row).attr("type")) > -1){
            $("#tbTypeOptions").val(0);
        }else{
            $("#tbTypeOptions").val(1);
        }
        $('#tbTypeOptions').change();

        $('#tbBank').val($(row).attr("bankName"));
        $('#tbBank').change();
        $('#tbCardId').val($(row).attr("cardId"));
        $('#tbType').val($(row).attr("type"));
        $('#tbCardNumber').val($(row).attr("cardNumber"));
        var validDate = $(row).attr("validDate");
        if(validDate){
            $('#tbValidYear').numberbox("setValue", validDate.substring(0, 4));
            $('#tbValidMonth').numberbox("setValue", validDate.substring(5, 7));
        }
        else
        {
            $('#tbValidYear').numberbox("setValue", "");
            $('#tbValidMonth').numberbox("setValue", "");
        }
        $("#card-container-div").show();
    }
}

//选择收货人
function selectContactAddress2(){
    window.parent.queryC(frameElement.id,'getCustomerShow2','shoppingCart');
}
//更新收货人
function  getCustomerShow2(contactId, contactName){
    parent._source ="";

    if(contactId ==$("#shoppingcart_contactId").val()){
        $("#lblModifyContact").html("当前客户");
    }
    $.post( '/cart/check/contactName',
        {contactId:contactId},
        function (data) {
            if (data == 'auditContact') {
                $("#id_payer_errorMessage").html("付款人("+contactName+")正在审批中，无法选用");
                return;
            }
            if (data != 'false') {
                $("#id_payer_errorMessage").html("付款人("+contactName+")姓名正在审批中");
            }else{
                $("#id_payer_errorMessage").html("");

                $('#creditCards-grid').datagrid("load", {
                    contactId: contactId
                });
                $('#selectCards-grid').datagrid("load", {
                    contactId: contactId
                });
                $("#payment_contactid").val(contactId);
                if(contactName){
                    $("#lblModifyContact").html(contactName);
                } else {
                    $("#lblModifyContact").html(contactId);
                }
            }
        }
    );
    $("#card-container-div").hide();



    /*
    $.post("/checkout/cc/change", {
            contactId : contactId
        },
        function(result){
            if(result){
                $('#selectCards-grid').datagrid("loadData", $(result).attr("cards"));
            } else {
                $('#selectCards-grid').datagrid("loadData", []);
            }
        }
    );
    */
}

function checkPayerName(contactId){
    $.post( '/cart/check/contactName',
        {contactId:contactId},
        function (data) {
            if (data == 'auditContact') {
                $("#id_payer_errorMessage").html("付款人正在审批中,无法选用");
                return;
            }
            if (data != 'false') {
                $("#id_payer_errorMessage").html("付款人姓名正在审批中");
            }else{
                $("#id_payer_errorMessage").html("");
            }
        }
    );
}
