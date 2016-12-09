function _handler(){
    $('#inboundtabs,#contactTab,#cart_tabs').tabs('resize',{width:'100%'});
    $('#phoneTable,#addressTable,#cardTable,#orderHistory').datagrid('resize',{width:'100%'});
    var tab = $('#inboundtabs').tabs('getSelected');
    var index = $('#inboundtabs').tabs('getTabIndex',tab);
    if(index==3){
        $('#id_cases').datagrid('resize',{width:'100%'});
    }else if(index==4){
        $('#lead_interation_history,#message_history').datagrid('resize',{width:'100%'});
    }
}

function compare(){
    var options ={title:'修改确认'};
    window.parent.popWin(frameElement.id,'compare',options);
}

function formatAddress(str){
   //全角转半角
    var result="";
    for (var i = 0; i < str.length; i++)
    {
        if (str.charCodeAt(i)==12288)
        {
           // result+= String.fromCharCode(str.charCodeAt(i)-12256);
            continue;
        }
        if (str.charCodeAt(i)==32)
        {
            // result+= String.fromCharCode(str.charCodeAt(i)-12256);
            continue;
        }
        if (str.charCodeAt(i)>65280 && str.charCodeAt(i)<65375)
            result+= String.fromCharCode(str.charCodeAt(i)-65248);
        else result+= String.fromCharCode(str.charCodeAt(i));
    }
   return result;
}

$(document).ready(function(){
    $("#show_address_check").hide();
    var addressKey = $("#d_address_check", window.parent.document).val().split(",");
    var addressextKey = $("#d_address_check_ext", window.parent.document).val().split(",");
    var isInvalidAddress = false;
    $("#address").keyup(function(){
        var inputval=formatAddress($("#address").val());
        console.info("inbound.js:"+inputval);
        for (var i = 0; i < addressKey.length; i++)
        {
          if(inputval.indexOf(addressKey[i])>0){
              isInvalidAddress =true;
              break;
          }else{
              isInvalidAddress =false;
          }
        }

       //且不包含

        for (var i = 0; i < addressextKey.length; i++)
        {
            if(inputval.indexOf(addressextKey[i])>=0){
                isInvalidAddress =false;
                break;
            }
        }

        if(isInvalidAddress){
            $("#show_address_check").show();
        }else{
            $("#show_address_check").hide();
        }
    });
    // parent.createRecommendTask(parent.phone.dnsi,parent.phone.ani,$("#customerId").val(), $("#customerType").val());
    $.extend($.fn.datagrid.methods, {
        doRowTip: function (jq, params) {
            function showTip(data, tr, e,colName,func) {
                var selFilter=">td[field='"+colName+"']";
                var offset=$(tr).offset();
                var rowIndex=$(tr).attr('datagrid-row-index');
                console.debug(rowIndex);

                if(rowIndex!=null&&data.data!=null&&data.data.rows!=null) {
                    var colData=data.data.rows[rowIndex][colName];
                    if(colData!=null) {
                        if(func!=null) {
                            var name=func(colData);
                            data.tooltip.html(name).css({
                                top: (offset.top + 10) + 'px',
                                left: (offset.left + 20) + 'px',
                                'max-width': '2000px',
                                'z-index': $.fn.window.defaults.zIndex,
                                display: 'block'
                            });
                        }
                        else {
                            data.tooltip.text(colData).css({
                                top: (offset.top + 10) + 'px',
                                left: (offset.left + 20) + 'px',
                                'max-width': '2000px',
                                'z-index': $.fn.window.defaults.zIndex,
                                display: 'block'
                            });
                        }
                    }
                }
            };
            return jq.each(function () {
                var grid = $(this);
                var options = $(this).data('datagrid');
                if (!options.tooltip) {
                    var panel = grid.datagrid('getPanel').panel('panel');
                    var defaultCls = {
                        'border': 'none',
                        'padding': '5px',
                        'color': '#333',
                        'position': 'absolute',
                        'border-radius': '4px',
                        '-moz-border-radius': '4px',
                        '-webkit-border-radius': '4px',
                        'display': 'none'
                    }
                    var tooltip = $('<div id="celltip" class="goods_info"></div>').appendTo('body');
                    tooltip.css($.extend({}, defaultCls, params.cls));
                    options.tooltip = tooltip;
                    panel.find('.datagrid-body').each(function () {
                        var delegateEle = $(this).find('> div.datagrid-body-inner').length ? $(this).find('> div.datagrid-body-inner')[0] : this;
                        $(delegateEle).undelegate('tr', 'mouseover').undelegate('tr', 'mouseout').undelegate('tr', 'mousemove').delegate('tr', {
                            'mouseover': function (e) {
                                if (params.delay) {
                                    if (options.tipDelayTime)
                                        clearTimeout(options.tipDelayTime);
                                    var that = this;
                                    options.tipDelayTime = setTimeout(function () {
                                        showTip(options, that, e, params.col,params.func);
                                    }, params.delay);
                                }
                                else {
                                    showTip(options, this, e, params.col,params.func);
                                }
                            },
                            'mouseout': function (e) {
                                if (options.tipDelayTime)
                                    clearTimeout(options.tipDelayTime);
                                options.tooltip.css({
                                    'display': 'none'
                                });
                            },
                            'mousemove': function (e) {
                                var that = this;
                                if (options.tipDelayTime)
                                    clearTimeout(options.tipDelayTime);
                                options.tipDelayTime = setTimeout(function () {
                                    showTip(options, that, e, params.col,params.func);
                                }, params.delay);
                            }
                        });
                    });
                }
            });
        },
        cancelRowTip: function (jq) {
            return jq.each(function () {
                var data = $(this).data('datagrid');
                if (data.tooltip) {
                    data.tooltip.remove();
                    data.tooltip = null;
                    var panel = $(this).datagrid('getPanel').panel('panel');
                    panel.find('.datagrid-body').undelegate('tr', 'mouseover').undelegate('tr', 'mouseout').undelegate('tr', 'mousemove')
                }
                if (data.tipDelayTime) {
                    clearTimeout(data.tipDelayTime);
                    data.tipDelayTime = null;
                }
            });
        }
    });

    $('#inboundtabs').tabs({
        border:false,
        onSelect:function(title){
            if(title=="客户扩展信息"){
//                $.get("/customer/mycustomer/ext/init", function(result){});
            }
            if(title =='客户服务历史'){
                contactCases();
                initComplainTable();
            }
            if(title =='客户联系历史'){
                contactContactHist();
            }
            if(title=="客户购物车"){
                if($("#customerId").val()){
                    showShoppingCartPage();
                }else{
                    $("#id_page_shoppingCart").hide();
                    $("#id_page_shoppingCart_message").show();
                }
            }
            window.parent.SetCwinHeight(frameElement);
        }
    })

    if ($("#customerId").val() =="") {
        $("#token").hide();
    } else if ($("#customerType").val() =="2") {
        $("#addCoustomerInfoBut").hide();
        $("#clearCoustomerInfoBut").hide();
    }

    if ($("#customerType").val() =="1") {
        $("#clearCoustomerInfoBut").hide();
    }

    //新建客户
    $('#addCoustomerBut').bind('click',function(){
        addCoustomer();
    });
    //新建客户基本信息

    $('#addCoustomerInfoBut').click(function(){
        $(this).hide();
        var addressRows=$('#addressTable').datagrid('getRows');
        if ($("#customerId").val() == "") {
            addCoustomerInfo();
        } else if ($("#customerType").val() == "2" && addressRows=="") {
            submitEditNameAndSex();
        } else if ($("#customerType").val() == "2" && addressRows!="") {
            addAddressToUpgrade();
        } else {
            compareNameAndSex();
        }
    });
    //清空客户基本信息
    $('#clearCoustomerInfoBut').bind('click',function(){
        clearCoustomerInfo();
    });

    //展开添加电话信息
    $('#displayCoustomerPhoneBut').bind('click',function(){
        displayCoustomerPhone();
    });

    $("#phonetypid").bind('click',function(){
        changefPhone();
    });

    //添加电话信息
    $('#addCoustomerPhoneBut').bind('click',function(){
        $(this).hide();
        addCoustomerPhone();
    });

    //清空客户电话信息
    $('#cancleCoustomerPhoneBut').bind('click',function(){
        cancleCoustomerPhone();
    });


    //展开收货地址
    $('#displayCoustomerAddressBut').bind('click',function(){
        displayCoustomerAddress();
    });

    //展开卡信息
    $('#addCardIcon').bind('click',function(){
        $('#addCardTable').show();
    });

    //新建常用收货信息
    $('#addCoustomerAddressBut').bind('click',function(){
        $("#show_address_check").hide();
        $(this).hide();
        if ($('#addressRowIndex').val()!='' && $('#addressId').val()!='') {
            addCoustomerAddress();
        } else if ($('#addressRowIndex').val()=='' && $('#addressId').val()=='') {
            addCoustomerAddress();
        } else {
            saveEditAddress();
        }
    });

    $('#saveEditCard').bind('click',function(){
        if ($('#addCardId').val()!='') {
            saveEditCard();
        }
    });

    $('#cancelEditCard').bind('click',function(){
        cancelEditCard();
    });

    //清空地址信息
    $('#cancleCoustomerAddressBut').bind('click',function(){
        cancleCoustomerAddress();
    });

    //新建扩展信息
    $('#addCoustomerExtBut').bind('click',function(){
        addCoustomerExt();
    });
    //取消扩展信息
    $('#cancleCoustomerExtBut').bind('click',function(){
        if ($("#customerType").val() == "1") {
            $.get("/contact/getContactInfo/" + $("#customerId").val(), function (data) {
                $("#birthday").datebox("setValue", data.customer.birthdayStr);
                $("#marriage").combobox('setValue',data.customer.marriage);
                $("#income").combobox('setValue',data.customer.income);
                $("#occupationStatus").combobox('setValue',data.customer.occupationStatus);
                $("#education").combobox('setValue',data.customer.education);
                if (data.customer.car=='-1') $('#isCar').attr("checked", true); else $('#isCar').attr("checked", false);
                if (data.customer.car!='-1') $('#carmoney2').attr("disabled", true); else $('#carmoney2').attr("disabled", false);
                $("#carmoney2").val(data.customer.carmoney2);
                if (data.customer.children=='-1') $('#isChild').attr("checked", true); else $('#isChild').attr("checked", false);
                if (data.customer.children!='-1') $('#childrenage').datebox({disabled:true}); else $('#childrenage').datebox({disabled:false});
                $("#childrenage").datebox('setValue',data.customer.childrenageStr);
                if (data.customer.hasElder=='-1') $('#isParent').attr("checked", true); else $('#isParent').attr("checked", false);
                if (data.customer.hasElder!='-1') $('#elderBirthday').datebox({disabled:true}); else $('#elderBirthday').datebox({disabled:false});
                $("#elderBirthday").datebox('setValue',data.customer.elderBirthdayStr);
            });
        }
    });

    $('#phoneTable').datagrid({
        title:'',
        method:'get',
        width: '100%',
        height: 120,
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        singleSelect:true,
        scrollbarSize:-1,
        onBeforeLoad:function(){
            if ($("#customerId").val()==null || $("#customerId").val()=='') return false;
        },
        onLoadSuccess:function() {
            if ($("#customerState").val()=='' || $("#customerState").val()=='2') displayCoustomerPhone();
        },
        url:'/contact/phoneList/'+$("#customerType").val()+"/"+$("#customerId").val(),
        columns:[[
            {field:'state',width:30,align:'center',title:'审核',formatter:function(value){
                if(value==0) return '<span class="exaa">待审核</span>';
                if(value==1) return '<span class="exaa">审核中</span>';
                if(value==3) return '<span class="exa">未通过</span>';
                else return '';
            }},
            {field:'phn2',title:'客户电话',width:100,align:'center',
                formatter:function(value,rowData,rowIndex){
                    if ($('#customerState').val() == '0' || $('#customerState').val() == '1' || $('#customerState').val() == '3' ||
                        rowData.state == '0' || rowData.state == '1' || rowData.state == '3') {
                        if (rowData.phonetypid && rowData.phonetypid != 4) {
                            var phone = "";
                            phone += (rowData.phn1 ? rowData.phn1+"-":"");
                            phone += value;
                            phone += (rowData.phn3 ? "-"+rowData.phn3:"");
                            phone += rowData.belongAddress;
                            phone += ' <a class="customerQuery s_btn" href="javascript:void(0);" onclick="findByPhone('+rowIndex+')"></a>';
                            return phone;
                        } else {
                            return value+rowData.belongAddress+' <a class="customerQuery s_btn" href="javascript:void(0);" onclick="findByPhone('+rowIndex+')"></a>';
                        }
                    }
                    if (rowData.phonetypid && rowData.phonetypid != 4) {
                        var phone = "";
                        phone += (rowData.phn1 ? rowData.phn1+"-":"");
                        phone += value;
                        phone += (rowData.phn3 ? "-"+rowData.phn3:"");
                        phone += rowData.belongAddress;
                        return '<span class="tel"><a class="phoneTablePhn2_'+rowIndex+' link pl5 pr5" style="margin:0 5px" href="javascript:void(0);" onclick="showOutPhoneType('+rowIndex+')">'+phone+'</a>'+' <a class="customerQuery s_btn" href="javascript:void(0);" onclick="findByPhone('+rowIndex+')"></a>'+'</span>';
                    } else {
                        return '<span class="tel"><a class="phoneTablePhn2_'+rowIndex+' link pl5 pr5" style="margin:0 5px" href="javascript:void(0);" onclick="showOutPhoneType('+rowIndex+')">'+value+rowData.belongAddress+'</a>'+'<a class="customerQuery s_btn" href="javascript:void(0);" onclick="findByPhone('+rowIndex+')"></a>'+'</span>';
                    }
                }},
            {field:'lastCallDateStr',title:'最近接通时间',width:100},
            {field:'callCount',title:'接通次数',width:40,align:'center'},
            {field:'prmphn',title:'默认',width:30,align:'center',
                formatter:function(value, rowData, rowIndex){
                    if ($('#customerState').val() == '0' || $('#customerState').val() == '1' || $('#customerState').val() == '3' ||
                        rowData.state == '0' || rowData.state == '1' || rowData.state == '3') {
                        return rowData.prmphn=='Y' ? '<input type="checkbox" checked disabled/>':'<input type="checkbox" disabled/>';
                    }
                    return value=='Y' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" onclick="setPrimePhone('+rowIndex+')" />';
                }},
            {field:'backupPhn',title:'备选',width:30,align:'center',
                formatter:function(value, rowData, rowIndex){
                    if ($('#customerState').val() == '0' || $('#customerState').val() == '1' || $('#customerState').val() == '3' ||
                        rowData.state == '0' || rowData.state == '1' || rowData.state == '3') {
                        return rowData.prmphn=='B' ? '<input type="checkbox" checked disabled/>':'<input type="checkbox" disabled/>';
                    }
                    return rowData.prmphn=='B' ? '<input type="checkbox" onclick="editBackupPhone('+rowIndex+',this)" checked />':'<input type="checkbox" onclick="editBackupPhone('+rowIndex+',this)" />';
                }},
            {field:'phoneOperate',title:'操作',align:'center',width:100,hidden:true,
                formatter:function(value, rowData, rowIndex){
                    return  '<a class="modify" title="修改" href="javascript:void(0);" onclick="editPhone('+rowIndex+')"></a>  '+ ' <a class="del ml10" href="javascript:void(0);" onclick="deletePhone('+rowIndex+')"></a>';
                }
            },
            {field:'phonetypid',title:'电话类型',width:100,hidden:true}
        ]]
    });

    $('#addressTable').datagrid({
        title:'',
        method:'get',
        width: '100%',
        height: 150,
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        singleSelect:true,
        scrollbarSize:-1,
        onBeforeLoad:function(){
            if ($("#customerId").val()==null || $("#customerId").val()=='' || $("#customerType").val()=='2') return false;
        },
        onLoadSuccess:function() {
            if ($("#customerState").val()=='' || $("#customerState").val()=='2') displayCoustomerAddress();
        },
        url:'/contact/addressList/'+$("#customerType").val()+"/"+$("#customerId").val(),
        columns:[[
            {field:'auditState',width:26,align:'center',title:'审核',sortable:false,formatter:function(value){
                if(value==0) return '<span class="exaa">待审核</span>';
                if(value==1) return '<span class="exaa">审核中</span>';
                if(value==3) return '<span class="exa">未通过</span>';
                else return '';
            }},
            {field:'receiveAddress',title:'收货地址',width:120},
            {field:'address',title:'详细地址',width:30,resizable:false,
                formatter:function(value) {
                    return '<div onselectstart="return false;"><marquee direction="left" scrollamount="3">'+value+'</marquee></div>';
                }},
            {field:'zip',title:'邮编',width:30,align:'center'},
            {field:'isdefault',title:'默认',width:30,align:'center',
                formatter:function(value, rowData, rowIndex){
                    if ($('#customerState').val() == '0' || $('#customerState').val() == '1' || $('#customerState').val() == '3' ||
                        rowData.auditState == '0' || rowData.auditState == '1' || rowData.auditState == '3') {
                        return value=='-1' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" disabled/>';
                    }
                    return value=='-1' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" onclick="setMainAddress('+rowIndex+')" />';
                }},
            {field:'addressEdit',title:'操作',align:'center',width:50,
                formatter:function(value, rowData, rowIndex){
                    if ($('#customerState').val() == '0' || $('#customerState').val() == '1' || $('#customerState').val() == '3' ||
                        rowData.auditState == '0' || rowData.auditState == '1' || rowData.auditState == '3') {
                        return '';
                    }
                    if ($("#customerId").val() == "") {
                        return  '<a class="modify" href="javascript:void(0);" onclick="editAddressNoId('+rowIndex+')"></a>'+' <a class="del" href="javascript:void(0);" onclick="deleteAddress('+rowIndex+')"></a>';
                    }else{
                        var html = '<a class="modify" href="javascript:void(0);" onclick="editAddress('+rowIndex+')"></a>';
                        if ($("#agentType").val().toLocaleLowerCase() == 'in' && rowData.addressid!=null) {
                            html += '&nbsp;&nbsp;&nbsp;<a class="modify" href="javascript:void(0);" onclick="onlyEditFourthAddress('+rowIndex+');">四</a>';
                        }
                        return  html;
                    }
                }},
            {field:'addrtypid',title:'地址类型',width:100,hidden:true}
        ]]
    });

    if ($("#customerId").val()==null || $("#customerId").val()=='') {
        displayCoustomerAddress();
        displayCoustomerPhone();
    }
    if ($("#customerType").val()=='2') {
        displayCoustomerAddress();
        hideInsureExt();
    }
    else
    {
        $('#insureInfoTab').datagrid({
            title:'',
            method:'get',
            width: '100%',
            height: 120,
            nowrap: false,
            striped: true,
            border: true,
            collapsible:false,
            fitColumns:true,
            singleSelect:true,
            scrollbarSize:-1,
            onBeforeLoad:function(){
                return false;
            },
            columns:[[
                {field:'prodId',title:'商品编号',width:20},
                {field:'prodName',title:'商品名称',width:30}
            ]]
        });


//        if(insureData!=null)
//            $('#insureInfoTab').datagrid("loadData",insureData);

        $('#insureInfoCanTab').datagrid({
            title:'',
            method:'get',
            width: '100%',
            height: 120,
            nowrap: false,
            striped: true,
            border: true,
            collapsible:false,
            fitColumns:true,
            singleSelect:true,
            scrollbarSize:-1,
            onBeforeLoad:function(){
                return false;
            },
            columns:[[
                {field:'prodId',title:'商品编号',width:20},
                {field:'prodName',title:'商品名称',width:30}
            ]]
        });


//        if(insureToData!=null)
//            $('#insureInfoCanTab').datagrid("loadData",insureToData);

    }

    $('#cardTable').datagrid({
        title:'',
        method:'get',
        width: '100%',
        height: 120,
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        singleSelect:true,
        scrollbarSize:-1,
        onBeforeLoad:function(){
            if ($("#customerId").val()==null || $("#customerId").val()=='' || $("#customerType").val()=='2') return false;
        },
        url:'/contact/cardList/'+$("#customerType").val()+"/"+$("#customerId").val(),
        columns:[[
            {field:'state',width:7,align:'center',title:'审核',formatter:function(value){
                if(value==0) return '<span class="exaa">待审核</span>';
                if(value==1) return '<span class="exaa">审核中</span>';
                if(value==3) return '<span class="exa">未通过</span>';
                else return '';
            }},
            {field:'cardtypeName',title:'信用卡类型',width:20},
            {field:'cardNumber',title:'卡号',width:30},
            {field:'validDate',title:'有效期',width:20}
        ]]
    });



    $("#birthday").datebox({
        formatter:function(date){
            return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
        },
        onChange:function() {
            $("#formAddCustomerExt").data("changed",true);
        }
    });
    $("#birthday").datebox("setValue", $("#birthdayHidden").val());
    $("#formAddCustomerExt").data("changed",false);

    $("#childrenage").datebox({
        formatter:function(date){
            return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
        },
        onChange:function() {
            $("#formAddCustomerExt").data("changed",true);
        }
    });
    $("#childrenage").datebox("setValue", $("#childrenageHidden").val());
    $("#formAddCustomerExt").data("changed",false);

    $("#elderBirthday").datebox({
        formatter:function(date){
            return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
        },
        onChange:function() {
            $("#formAddCustomerExt").data("changed",true);
        }
    });
    $("#elderBirthday").datebox("setValue", $("#elderBirthdayHidden").val());
    $("#formAddCustomerExt").data("changed",false);

    initCustomerData();

    $("#formAddCustomerExt").change(function(){
        $("#formAddCustomerExt").data("changed",true);
    });

    $("#compareEditPhoneAndAddress").click(function(){
        $(this).hide();
        var addressRows=$('#addressTable').datagrid('getRows');
        if ($("#customerId").val() == "") {
            addCoustomerInfo();
            return;
        } else if ($("#customerType").val() == "2" && addressRows=="") {
            submitEditNameAndSex();
            return;
        } else if ($("#customerType").val() == "2" && addressRows!="") {
            addAddressToUpgrade();
            return;
        }
        $.post("/contact/comparePhoneAndAddress",{
            customerId:$("#customerId").val(),
            customerType:$("#customerType").val(),
            phones:JSON.stringify($("#phoneTable").datagrid("getRows")),
            addresss:JSON.stringify($("#addressTable").datagrid("getRows"))
        },function(data){
            if (data.error) {
                window.parent.alertWin('系统提示', data.error);
            } else {
                var options ={title:'客户电话地址信息修改确认',height:395};
                window.parent.popWin(frameElement.id,'comparePhoneAndAddress'+$("#initCustomerId").val(),options);

                $(getPopWinNode("comparePhoneAndAddress", "oldPhoneTable"), window.parent.document).datagrid({
                    width: 325,
                    height: 120,
                    nowrap: false,
                    striped: true,
                    border: true,
                    collapsible:false,
                    fitColumns:true,
                    columns:[[
                        {field:'phn2',title:'客户电话',width:50,
                            formatter:function(value,rowData,rowIndex){
                                if (rowData.phonetypid && rowData.phonetypid != 4) {
                                    var phone = "";
                                    phone += (rowData.phn1 ? rowData.phn1+"-":"");
                                    phone += value;
                                    phone += (rowData.phn3 ? "-"+rowData.phn3:"");
                                    return phone;
                                } else {
                                    return value;
                                }
                            }},
                        {field:'prmphn',title:'默认',width:20,
                            formatter:function(value){
                                return value=='Y' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" disabled />';
                            }},
                        {field:'backupPhn',title:'备选',width:20,
                            formatter:function(value, rowData, rowIndex){
                                return rowData.prmphn=='B' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" disabled />';
                            }}
                    ]]
                });

                $(getPopWinNode("comparePhoneAndAddress", "oldPhoneTable"), window.parent.document).datagrid('loadData', data.oldPhones);

                $(getPopWinNode("comparePhoneAndAddress", "newPhoneTable"), window.parent.document).datagrid({
                    width:325,
                    height: 120,
                    nowrap: false,
                    striped: true,
                    border: true,
                    collapsible:false,
                    fitColumns:true,
                    scrollbarSize:0,
                    columns:[[
                        {field:'phn2',title:'客户电话',width:50,
                            formatter:function(value,rowData,rowIndex){
                                if (rowData.phonetypid && rowData.phonetypid != 4) {
                                    var phone = "";
                                    phone += (rowData.phn1 ? rowData.phn1+"-":"");
                                    phone += value;
                                    phone += (rowData.phn3 ? "-"+rowData.phn3:"");
                                    return phone;
                                } else {
                                    return value;
                                }
                            }},
                        {field:'prmphn',title:'默认',width:20,
                            formatter:function(value){
                                return value=='Y' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" disabled />';
                            }},
                        {field:'backupPhn',title:'备选',width:20,
                            formatter:function(value, rowData, rowIndex){
                                return rowData.prmphn=='B' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" disabled />';
                            }}
                    ]]
                });

                var phoneEditIndexs = eval('['+data.phoneEditIndexs+']');
                $(getPopWinNode("comparePhoneAndAddress", "newPhoneTable"), window.parent.document).datagrid({
                    rowStyler:function(rowIndex){
                        if($.inArray(rowIndex, phoneEditIndexs)>-1) return 'color:red;';
                    }
                });
                $(getPopWinNode("comparePhoneAndAddress", "newPhoneTable"), window.parent.document).datagrid('loadData', data.newPhones);

                $(getPopWinNode("comparePhoneAndAddress", "oldAddressTable"), window.parent.document).datagrid({
                    width: 325,
                    height: 120,
                    striped: true,
                    border: true,
                    collapsible:false,
                    fitColumns:true,
                    columns:[[
                        {field:'receiveAddress',title:'收货地址',width:20},
                        {field:'address',title:'详细地址',width:10,resizable:false,
                            formatter:function(value) {
                                return '<div onselectstart="return false;"><marquee direction="left" scrollamount="3">'+value+'</marquee></div>';
                            }},
                        {field:'zip',title:'邮编',width:15},
                        {field:'isdefault',title:'默认',width:10,
                            formatter:function(value){
                                return value=='-1' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" disabled />';
                            }}
                    ]]
                });
                $(getPopWinNode("comparePhoneAndAddress", "oldAddressTable"), window.parent.document).datagrid('loadData', data.oldAddresss);

                var addAddressIndexs = eval('['+data.addressEditIndexs.addAddressIndexs+']');
                var editAddressIndexs = eval('['+data.addressEditIndexs.editAddressIndexs+']');
                $(getPopWinNode("comparePhoneAndAddress", "newAddressTable"), window.parent.document).datagrid({
                    width: 325,
                    height: 120,
                    striped: true,
                    border: true,
                    collapsible:false,
                    fitColumns:true,
                    rowStyler:function(rowIndex){
                        if($.inArray(rowIndex, addAddressIndexs)>-1) return 'color:red;';
                    },
                    columns:[[
                        {field:'receiveAddress',title:'收货地址',width:20,
                            styler:function(value, rowData, rowIndex) {
                                if($.inArray(rowIndex, editAddressIndexs)>-1) {
                                    var editAddressIndexClumns = eval('['+data.editAddressIndexColumns[rowIndex]+']');
                                    if ($.inArray(1, editAddressIndexClumns)>-1) return 'color:red;';
                                }
                            }},
                        {field:'address',title:'详细地址',width:10,resizable:false,
                            formatter:function(value) {
                                return '<div onselectstart="return false;"><marquee direction="left" scrollamount="3">'+value+'</marquee></div>';
                            },
                            styler:function(value, rowData, rowIndex) {
                                if($.inArray(rowIndex, editAddressIndexs)>-1) {
                                    var editAddressIndexClumns = eval('['+data.editAddressIndexColumns[rowIndex]+']');
                                    if ($.inArray(2, editAddressIndexClumns)>-1) return 'color:red;';
                                }
                            }},
                        {field:'zip',title:'邮编',width:15,
                            styler:function(value, rowData, rowIndex) {
                                if($.inArray(rowIndex, editAddressIndexs)>-1) {
                                    var editAddressIndexClumns = eval('['+data.editAddressIndexColumns[rowIndex]+']');
                                    if ($.inArray(3, editAddressIndexClumns)>-1) return 'color:red;';
                                }
                            }},
                        {field:'isdefault',title:'默认',width:10,
                            formatter:function(value){
                                return value=='-1' ? '<input type="checkbox" checked disabled />':'<input type="checkbox" disabled />';
                            }}
                    ]]
                });

                $(getPopWinNode("comparePhoneAndAddress", "newAddressTable"), window.parent.document).datagrid('loadData', data.newAddresss);
            }
            $("#compareEditPhoneAndAddress").show();
        });
    });

    $("#cancelEdit").click(function(){
        if ($("#customerId").val() == "") {
            $('#name').val('');
            $('input[name="sex"]')[0].checked=true;
            $("#phoneTable").datagrid('loadData',{total:0,rows:[]});
            $("#addressTable").datagrid('loadData',{total:0,rows:[]});
        } else if ($("#customerType").val() == "2") {
            $.get("/contact/getPotentialContactInfo/" + $("#customerId").val(), function (data) {
                $('#name').val(data.customer.name);
                data.customer.sex == 2 ? $('input[name="sex"]')[1].checked=true : $('input[name="sex"]')[0].checked=true;
            });
            $("#phoneTable").datagrid('reload');
            $("#addressTable").datagrid('reload');
        } else {
            $("#phoneTable").datagrid('reload');
            $("#addressTable").datagrid('reload');
        }
    });

    //页面初始化隐藏不现实的内容
    hiddenDocumentElement();

    //客户订单历史
    $('#orderHistory').datagrid({
        view: detailview,
        detailFormatter:function(index,row){
            return '<div style="padding:2px"><table id="ddv-' + index + '"></table></div>';
        },
        onExpandRow: function(index,row){
            $('#ddv-'+index).datagrid({
                fitColumns:true,
                singleSelect:true,
                scrollbarSize:-1,
                loadMsg:'',
                height:'auto',
                columns:[[
                    {field:'prodId',title:'商品编号',width:100,align:'center'},
                    {field:'prodName',title:'商品名称',width:200,align:'center'},
                    {field:'typeName',title:'规格',width:100,align:'center'},
                    {field:'price',title:'价格',width:100,align:'right',formatter:function(value){
                        return formatPrice(value);
                    }
                    },
                    {field:'quantity',title:'数量',width:100,align:'center'},
                    {field:'soldwith',title:'销售方式',width:100,align:'center'}
                ]],
                onResize:function(){
//                    $('#orderHistory').datagrid('fixDetailRowHeight',index);
                },
                onLoadSuccess:function(){
                    setTimeout(function(){
                        $('#orderHistory').datagrid('fixDetailRowHeight',index);
                    },200);
                }
            });
            $('#ddv-'+index).datagrid("loadData",{"total":row['contactOrderDetailFrontDtos'].length,"rows":row['contactOrderDetailFrontDtos']});
//            $('#dg').datagrid('fixDetailRowHeight',index);
        },
        width : '100%',
        height : 410,
        fitColumns : true,
        scrollbarSize:-1,
        url : '/myorder/myorder/query_contact',
        queryParams : {
            'contactId' : $('#customerId').val()
        },
        rowStyler: function(index,row)
        {

            var rowStyle='';
            switch(row.status)
            {
                case "0": //cancel
                case "10":
                    rowStyle = 'background-color:#cecece;text-decoration: line-through;'; break;
                case "8": //物流取消
                    rowStyle = 'background-color:#A2BADD;text-decoration: line-through;'; break;
                case "1": //new
                    rowStyle='background-color:#FFC0C0';break;
                case "5": //finish
                    rowStyle='background-color:#C0FFC0';break;

                default:rowStyle='';break;

            }
            return rowStyle;
        },
        columns : [ [
            {
                field : 'orderid',
                title : '订单编号',
                align:'center',
                width : 80 ,
                formatter:function (value,row,index){
                    var canedit=0;
                    if(row.right.indexOf("MODIFY")!=-1||row.right.indexOf("ALL")!=-1)
                    {
                        canedit=1;
                    }
                    var token='<a class="link" href="javascript:void(0);" onclick="javascript:viewOrder('+'\'myOrderlist\','+index+','+row.orderid+','+canedit+')" > ';
                    token+=value;
                    token+='</a>';

                    return token;
                }

            }, {
                field : 'crdt',
                title : '订单生成时间',
                width : 120
            },{
                field : 'paytype',
                title : '支付类型',
                align:'center',
                width : 80,
                formatter : orderPaytypeFormatter
            }, {
                field : 'status',
                title : '订单状态',
                align:'center',
                width : 80,
                formatter : orderStatusFormatter
            }, {
                field : 'totalprice',
                title : '订单金额',
                width : 80,
                align:'right',
                formatter:function(value){
                    return formatPrice(value);
                }
            }, {
                field : 'crusr',
                title : '生成坐席',
                align:'center',
                width : 80
            }, {
                field : 'grpName',
                title : '坐席组',
                align:'center',
                width : 140
            }, {
                field : 'mailid',
                title : '包裹单号',
                align:'center',
                width : 120
            },
            {
                field : 'fbdt',
                title : '反馈日期',
                width : 150,
                align:'center'
            },
            {
                field : 'logisticsStatusId',
                title : '物流状态',
                align:'center',
                width : 150,
                formatter: logisticStatusFormatter
            },
            {
                field : 'contactOrderDetailFrontDtos',
                title : '商品明细',
                hidden:true,
                formatter:function (value,row,index){
                    return '<a>'+row.id+'</a>';
                }
            }
        ] ],
        remoteSort : false,
        singleSelect : true,
        pagination : true,
        onLoadSuccess: function(data){
            if(data.err!=null)
            {
                window.parent.alertWin('系统提示', data.err);
            }
        },
        onLoadError: function(){
            window.parent.alertWin('系统提示', "加载失败");
        }
    });

    $("#pointsDetail").click(function() {
        window.parent.addTab("pointDetail_"+$('#customerId').val(),$('#name').val()+"-积分详情",false,'/integral/integral?name='+encodeURI(encodeURI($('#name').val()))+"&customerId="+$('#customerId').val());
    });

    $("#newCustomerHiddenTd4").click(function() {
        $("#inboundtabs").tabs('select', 1);
    });

    $("#taskCount").click(function() {
        window.parent.addTab("myTask_"+$('#customerId').val(),$('#name').val()+"-待办任务",false,'/task/myCampaignTask?contactId='+$('#customerId').val());
    });

    //分配历史
    $('#assignHistory').datagrid({
    	width : '100%',
		height : 200,
		fitColumns : true,
		remoteSort : false,
		singleSelect : true,
		pagination : false,
		url : ctx+'/contact/queryAssignHistory',
		queryParams : {'initLoad' : false},
		onBeforeLoad : function(param){
			if(param.initLoad){
				return true;
			}
			return false;
		},
		onLoadSuccess:function(data){
		    $('#assignHistory').datagrid('doCellTip',{delay:500});
		},
		columns:[[{
				field : 'agentId',
				title : '当前分配座席',
				align : 'center',
				width : 100
			},{
				field : 'agentGrp',
				title : '座席组',
				align : 'center',
				width : 100
			},{
				field : 'sourceType',
				title : '分配方式',
				align : 'center',
				width : 100,
				formatter : function(value, row){
					var _label = '';
					if(value == 1){
						_label = '自助取数';
					}else if(value == 3){
						_label = '数据推送';
					}
					return _label;
				}
			},{
				field : 'createDate',
				title : '分配时间',
				align : 'center',
				width : 100,
				formatter : dateFormatter
			},{
				field : 'endDate',
				title : '到期时间',
				align : 'center',
				width : 100,
				formatter : dateFormatter
			}
		]]
    });

    $("#cancleEditFourthAddressBut").click(function() {
        $("#editFourthAddressTab").hide();
        $("#addAddressDiv").show();
    });

    $("#editFourthAddressBut").click(function () {
        var addresss = $("#addressTable").datagrid("getRows");
        var address = addresss[$("#addressRowIndexForFourth").val()];
        $.post('/contact/updateAddressArea/',
            {'addressId' : address.addressid, "areaId" : $("#areaIdForEditFourth").combobox('getValue')},
            function (data) {
                $.ajax({
                    url: '/contact/getReceiveAddress/' + $("#provinceForEditFourth").combobox('getValue') + "/" + $("#cityIdForEditFourth").combobox('getValue') + "/" + $("#countyIdForEditFourth").combobox('getValue') + "/" + $("#areaIdForEditFourth").combobox('getValue'),
                    type: 'GET',
                    dataType: 'json',
                    success: function (data) {
                        if (data == 'error') {
                            window.parent.alertWin('系统提示', '四级地址选择错误。');
                            return;
                        }
                        $("#addressTable").datagrid("updateRow", {index: $("#addressRowIndexForFourth").val(),
                            row: {areaid: $("#areaIdForEditFourth").combobox('getValue'),
                                receiveAddress: data}
                        });
                    }});
                $("#editFourthAddressTab").hide();
                $("#addAddressDiv").show();
            });
    });

    console.info("begin add click func");
    parent.addTabClickCallback($('#customerId').val(),'showCallback');
    console.info("end add click func");
    try{
        $('#name').focus();
        $('#phn1,#phn2,#phn3,#phn4').keydown(function(event) {
            if (event.keyCode == 13)  $('#addCoustomerPhoneBut').click();
        });
        $('#formAddCustomerAddress').keypress(function(event) {
            if (event.keyCode == 13)  $('#addCoustomerAddressBut').click();
        });
    }catch(err){
        console.error(err);
    }


    //新建扩展信息
    $('#contact_insure_save').bind('click',function(){
        addContactInsure();
    });

    $('#refuse').bind('click',function(){
        changeIsrefuse();
    });

    if($("#page_refresh").val()){
        $("#inboundtabs").tabs("select",parseInt($("#page_refresh").val()));
    }




    $('#contact_ext').tabs({
        onSelect:function(title){
            if(title=="保险客户基本信息"){
                 console.info("-----------------------1");
                $.get("/contact/getInsureList",{contactid:$("#customerId").val()},function(result){
                    console.info("-----------------------2");
                    if(result.product){
                        $('#insureInfoTab').datagrid("loadData",result.product);
                    }else{
                        $('#insureInfoTab').datagrid("loadData",[]);
                    }
                    if(result.productTo){
                        $('#insureInfoCanTab').datagrid("loadData",result.productTo);
                    }else{
                        $('#insureInfoCanTab').datagrid("loadData",[]);
                    }
                });


            }

        }
    });
});

function queryAssignHistory(){
	$('#gg').window('open');
	$('#assignHistory').datagrid('reload', {
		'initLoad' : true,
		'contactId' : $('#showCustomerId').html()
	});
}

function contactCases(){
    $("#id_cases").datagrid({
        width:'100%',
        height:410,
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        scrollbarSize:-1,
        url:'/contact/queryCase/'+$('#customerId').val(),
        columns:[[
            {title:'事件类型',field:'casetypeName',width:80},
            {title:'事件生成时间',field:'crdt',width:160},
            {title:'生成座席',field:'crusr',width:80},
            {title:'解决方式',field:'delivered',width:80},
            {title:'问题描述',field:'probdsc',width:150},
            {title:'处理描述',field:'external',width:150},
            {title:'是否协办',field:'cmpfBack',width:80}
        ]],
        remoteSort:false,
        singleSelect:false,
        pagination:true
    });
}

function initComplainTable(){
    $("#complainTable").datagrid({
        width:'100%',
        height:410,
        nowrap: false,
        method: 'GET',
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        scrollbarSize:-1,
        url:'/complain/complainList/'+$('#customerId').val(),
        columns:[[
            {title:'客户姓名',field:'contactName',width:30},
            {title:'投诉内容',field:'content',width:97},
            {title:'投诉时间',field:'crtm',width:73,
                formatter:dateFormatter},
            {title:'创建坐席',field:'crusr',width:30},
            {title:'订单编号',field:'orderId',width:40},
            {title:'产品',field:'prod',width:40},
            {title:'优先级',field:'priovity',width:30,
                formatter:priorityFormatter},
            {title:'状态',field:'status',width:30,
                formatter:complainStatusFormatter},
            {title:'事件ID',field:'caseId',width:25},
            {title:'处理人',field:'opusr',width:22}
        ]],
        remoteSort:false,
        singleSelect:false,
        pagination:true
    });
}

function contactContactHist(){
    $("#lead_interation_history").datagrid({
        width:'100%',
        url:'/contact/getLeadIntegration/'+ $('#customerId').val(),
        nowrap: false,
        striped: true,
        height:330,
        border: true,
        collapsible:false,
        fitColumns:true,
        scrollbarSize:-1,
        columns:[[
            {title:'开始时间',field:'createDate',width:80},
            {title:'结束时间',field:'endDate',width:80},
            {title:'创建人',field:'createUser',width:80},
            {title:'备注',field:'note',width:80},
            {title:'客户编号',field:'contactId',width:80}
        ]],
        remoteSort:false,
        singleSelect:false,
        pagination:true
    });
    $("#message_history").datagrid({
        width:'100%',
        height:330,
        url:'/contact/getMessageHistory/'+ $('#customerId').val(),
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        scrollbarSize:-1,
        columns:[[
            {title:'类型',field:'smsType',width:80},
            {title:'手机号码',field:'mobile',width:80},
            {title:'内容',field:'content',width:80},
            {title:'时间',field:'createTime',width:80},
            {title:'状态',field:'receiveStatusDes',width:80}
        ]],
        remoteSort:false,
        singleSelect:false,
        pagination:true
    });
}

var hiddenDocumentElement =function(){
    if($("#customerType").val() !="1"){
        $("#displayCard").css("display","none");
        $("#newCustomerHiddenTr").hide();
        $("#newCustomerHiddenTd1").hide();
        $("#newCustomerHiddenTd2").hide();
        $("#newCustomerHiddenTd3").hide();
        $("#newCustomerHiddenTd4").hide();
        $('#phoneTable').datagrid('showColumn',"phoneOperate");
    }
    //隐藏切换联系人按钮。
    if(parent.showccBut){
        $("#cc_change").show();
    }else{
        $("#cc_change").hide();
    }
}

var initCustomerData = function () {
    $("#marriage").combobox('setValue',$("#marriageHidden").val());
    $("#income").combobox('setValue',$("#incomeHidden").val());
    $("#occupationStatus").combobox('setValue',$("#occupationStatusHidden").val());
    $("#education").combobox('setValue',$("#educationHidden").val());
};
var checkSubmitFlg = true;
//新建客户
var addCoustomer = function(name,sex,phones,addresses){
    if (checkSubmitFlg == true){
        checkSubmitFlg = false;
        //客户姓名,性别,电话,地址
        $.post("/customer/mycustomer/add/formal",{
            customerId:$("#customerId").val(),
            name:$("#name").val(),
            sex:$('input[name="sex"]:checked').val(),
            phones:phones,
            addresses:addresses,
            source:parent._source
        },function(data){
            var potentialContactTabId = $("#customerId").val();
            $("#customerId").val(data.customerId);
            $("#showCustomerId").html(data.customerId);
            $("#customerType").val(data.customerType);
            if(data.result){
                if(typeof(parent._source) == "undefined")
                {
                    parent.gotoInfoCustomer(data.customerId,customerId.customerType,$("#name").val(), !window.parent.$("#"+window.parent.currentTabObj.tabId).hasClass("rell"));
                }else if(parent._source=="shoppingCart"){
                    //添加客户后回到购物车
                    parent.subCallback(parent._callBackframeid,parent._callbackMethod,data.customerId);

                }else if(parent._source=="editOrder"){
                	parent.subCallback(parent._callBackframeid,parent._callbackMethod,data.customerId);
                }else{
                    parent.gotoInfoCustomer(data.customerId,customerId.customerType,$("#name").val(), !window.parent.$("#"+window.parent.currentTabObj.tabId).hasClass("rell"));
                }
                if(potentialContactTabId == null||potentialContactTabId == "") parent.destoryTab('newCustomer');
                else parent.destoryTab(potentialContactTabId);
            }else{
                window.parent.alertWin('系统提示', data.msg);
            }
            checkSubmitFlg = true;
            $("#compareEditPhoneAndAddress").show();
        }).error(function() { 	checkSubmitFlg = true;$("#compareEditPhoneAndAddress").show(); });
    } else $("#compareEditPhoneAndAddress").show();
}

//新增客户基本信息
var addCoustomerInfo = function () {
    if ($("#formAddCustomerInfo").form('validate')) {
        //客户电话信息
        var phoneRows = $('#phoneTable').datagrid('getRows');
        //客户地址信息
        var addressRows = $('#addressTable').datagrid('getRows');
        if (phoneRows == "") {
            window.parent.alertWin('提示', "请添加客户电话");
            $("#compareEditPhoneAndAddress").show();
        } else if (addressRows == "") {
            window.parent.alertWin('提示', "请添加客户地址");
            $("#compareEditPhoneAndAddress").show();
        } else {
            addCoustomer($("#name").val(), $('input[name="sex"]:checked').val(), JSON.stringify(phoneRows), JSON.stringify(addressRows));
        }
    } else $("#compareEditPhoneAndAddress").show();
}

//潜客增加地址升级为正式客户
var addAddressToUpgrade = function(){
    if ($("#name").val()==null || $("#name").val().trim()=='') {
        window.parent.alertWin('系统提示', "升级为正式客户，姓名为必填项。");
        $("#compareEditPhoneAndAddress").show();
        return;
    }
    if ($("#formAddCustomerInfo").form('validate')) {
        //客户地址信息
        var addressRows = $('#addressTable').datagrid('getRows');
        addCoustomer($("#name").val(), $('input[name="sex"]:checked').val(), null, JSON.stringify(addressRows));
    } else $("#compareEditPhoneAndAddress").show();
}
//同步(防止购物车未初始化就添加产品)显示购物车，并初始化购物车相关数据
function showShoppingCartPage(){
    if($("#shoppingcart_id").val()){
        $("#id_page_shoppingCart").show();
        $("#id_page_shoppingCart_message").hide();
        initShoppingCart();
    }else{
        var shoppingCartCreateUrl  ="/cart/shoppingCartCreate";
        if($("#order_id").val()) {
            shoppingCartCreateUrl = shoppingCartCreateUrl+"/ORDER/"+$("#customerId").val();
        }else{
            shoppingCartCreateUrl = shoppingCartCreateUrl+"/CART/"+$("#customerId").val();
        }
        $.ajax({url:shoppingCartCreateUrl,
            async:false,type:'POST',dataType:'JSON',
            success:function(data){
                if(data.shoppingCartId){
                    $("#id_page_shoppingCart").show();
                    $("#id_page_shoppingCart_message").hide();
                    $("#shoppingcart_id").attr("value",data.shoppingCartId);
                    $("#shoppingcart_contactId").attr("value",data.shoppingCartContactId);
                    $("#cart_cartType").attr("value",data.cart_cartType);
                    $("#approveManager").attr("value",data.approveManager);
                    //信用卡付款
                    $("#payment_contactid").attr("value",data.shoppingCartContactId);
                    if(data.cardTypes !=null){
                        for(var index in data.cardTypes){
                                var cartType = data.cardTypes[index];
                                if(cartType.bankName){
                                    $("#tbType").append('<option title="'+cartType.bankName+'" value="' + cartType.cardtypeid  + '">' + cartType.bankName+'--'+cartType.cardname+ '</option>');
                                }else{
                                    $("#tbType").append('<option value="' + cartType.cardtypeid  + '">' + cartType.cardname + '</option>');

                                }
                        }
                    }
                    if(data.cardBanks !=null){
                        for(var index in data.cardBanks){
                            $("#tbBank").append('<option value="' + data.cardBanks[index] + '">' + data.cardBanks[index]  + '</option>');
                        }
                    }
                    initShoppingCart();
                }
            }});
    }
}


//比对客户姓名 性别
var compareNameAndSex = function(){
    if($("#formAddCustomerInfo").form('validate')){
        $.post("/contact/compareNameAndSex",{
            name:$("#name").val(),
            sex:$('input:radio[name="sex"]:checked').val(),
            customerId:$("#customerId").val(),
            customerType:$("#customerType").val()
        },function(data){
            $('#addCoustomerInfoBut').show();
            if (data.error) {
                window.parent.alertWin('系统提示', data.error);
            } else {
                $(getPopWinNode("compareNameAndSex", "oldName")).val(data.oldName);
                $(getPopWinNode("compareNameAndSex", "newName")).val(data.newName);
                data.nameEdit ? $(getPopWinNode("compareNameAndSex", "newNameTR")).css('color','red') : $(getPopWinNode("compareNameAndSex", "newNameTR")).css('color','black');
                data.oldSex == 2 ? $(getPopWinNode("compareNameAndSex", "oldSexWomen"))[0].checked=true : $(getPopWinNode("compareNameAndSex", "oldSexMen"))[0].checked=true;
                data.newSex == 2 ? $(getPopWinNode("compareNameAndSex", "newSexWomen"))[0].checked=true : $(getPopWinNode("compareNameAndSex", "newSexMen"))[0].checked=true;
                data.newSex == 2 ? $(getPopWinNode("compareNameAndSex", "newSexMen"))[0].checked=false : $(getPopWinNode("compareNameAndSex", "newSexWomen"))[0].checked=false;
                data.sexEdit ? $(getPopWinNode("compareNameAndSex", "newSexTR")).css('color','red') : $(getPopWinNode("compareNameAndSex", "newSexTR")).css('color','black');
                var options ={title:'客户基本信息修改确认'};
                window.parent.popWin(frameElement.id,'compareNameAndSex'+$("#initCustomerId").val(),options);
            }
        });
    } else $('#addCoustomerInfoBut').show();
}

//确认修改客户姓名 性别
var submitEditNameAndSex = function(){
    if($("#formAddCustomerInfo").form('validate')){
        $.post("/contact/submitEditNameAndSex",{
            batchId:$("#input_batchId").val(),
            name:$("#name").val(),
            sex:$('input:radio[name="sex"]:checked').val(),
            customerId:$("#customerId").val(),
            customerType:$("#customerType").val()
        },function(data){
            window.parent.alertWin('系统提示', data.msg);
            $("#compareEditPhoneAndAddress").show();
        });
    } else $("#compareEditPhoneAndAddress").show();
}

var clearCoustomerInfo =function(){
    if($("#customerId").val().trim() == ""){
        $("#formAddCustomerInfo").form('clear');
    }else{
        window.parent.alertWin('系统提示', "客户信息已保存！");
    }
}

$.extend($.fn.validatebox.methods, {
    remove: function(jq, newposition){
        return jq.each(function(){
            $(this).removeClass("validatebox-text validatebox-invalid").unbind('focus').unbind('blur');
        });
    },
    reduce: function(jq, newposition){
        return jq.each(function(){
            var opt = $(this).data().validatebox.options;
            $(this).addClass("validatebox-text").validatebox(opt);
        });
    }
});

var displayCoustomerPhone= function(){
    initPhone();
    var phones = $("#phoneTable").datagrid("getRows");
    if (phones.length==0) {
        $("#isDefault").attr("checked",true);
        $("#isDefault").attr("disabled",true);
        $('#isDefaultPhoneTR').show();
    } else {
        $("#isDefault").attr("checked",false);
        $('#isDefaultPhoneTR').hide();
    }
    $('#phn1').validatebox("remove");
    $('#phn2').validatebox("remove");
    $('#phn4').validatebox("reduce");
    $("#AddPhoneTab").css("display","block");
    window.parent.SetCwinHeight(frameElement);
}

var initPhone=function(){
    $('#phn1').val('');
    $('#phn2').val('');
    $('#phn3').val('');
    $('#phn4').val('');
    $('#editRowIndex').val('');
    $("#potentialPhoneId").val('');
    $("#phonetypid").attr("checked",false);
    changefPhone();
    var phones = $("#phoneTable").datagrid("getRows");
    if (phones.length==0) {
        $("#isDefault").attr("checked",true);
        $("#isDefault").attr("disabled",true);
        $('#isDefaultPhoneTR').show();
    } else {
        $("#isDefault").attr("checked",false);
        $('#isDefaultPhoneTR').hide();
    }
}
var changefPhone = function(){
    var test = $("#phonetypid").attr("checked")?'1':'4';
    if(test=='1'){
        $('#phn1').validatebox("reduce");
        $('#phn2').validatebox("reduce");
        $('#phn4').validatebox("remove");
        $("#fphone1").hide();
        $("#fphone2").show();
        if($("#editRowIndex").val() != ''){
            var rows = $('#phoneTable').datagrid('getRows');
            var rowData = rows[$("#editRowIndex").val()];
            if(rowData.phonetypid=="1"){
                $('#phn2').val(rowData.phn2);
            }else{
                $('#phn2').val('');
            }
        }
    }else if(test=='4'){
        $('#phn1').validatebox("remove");
        $('#phn2').validatebox("remove");
        $('#phn4').validatebox("reduce");
        $("#fphone1").show();
        $("#fphone2").hide();
        if($("#editRowIndex").val() != ''){
            var rows = $('#phoneTable').datagrid('getRows');
            var rowData = rows[$("#editRowIndex").val()];
            if(rowData.phonetypid=="4"){
                $('#phn2').val(rowData.phn2);
            }else{
                $('#phn2').val('');
            }
        }
    }
}

var addCoustomerPhone = function () {
    if ($("#formAddCustomerPhone").form('validate')) {
//        if ($("#phonetypid").attr("checked")) {
//            executeAddCusomerPhone();
//        } else {
            var phoneType = ($("#phonetypid").attr("checked")?1:4);
            var phn1 = $("#phn1").val();
            var phn2 = $("#phn2").val();
            var phn3 = $("#phn3").val();
            var fixPhone = (phn1 == null || phn1=='' ? "" : (phn1+"-"))+phn2+(phn3 == null||phn3=='' ? "" : ("-"+phn3));
            var fixPhoneParam = phn1 + "-" + phn2 + "-" + phn3;

            var phones = $("#phoneTable").datagrid("getRows");
            var phoneNum = $("#phn4").val();

            var checkParam = (phoneType == 1 ? fixPhoneParam : phoneNum);
            var showParam = (phoneType == 1 ? fixPhone : phoneNum);

            for (var i = 0; i < phones.length; i++) {
                if (phoneType == 1 && phones[i].phn1 == phn1 && phones[i].phn2 == phn2 && phones[i].phn3 == phn3) {
                    window.parent.alertWin('系统提示', '添加了重复电话号码。');
                    $("#addCoustomerPhoneBut").show();
                    return;
                }
                if (phoneType == 4 && phones[i].phn2 == phoneNum) {
                    window.parent.alertWin('系统提示', '添加了重复电话号码。');
                    $("#addCoustomerPhoneBut").show();
                    return;
                }
            }
            var customerId = $("#customerId").val()==null || $("#customerId").val()=='' ? 'newCustomer':$("#customerId").val();
            $.get("/contact/checkDuplicatePhone/" + customerId + "/" + phoneType + "/" + checkParam, function (data) {
                if (data.code == 0) {
                    window.parent.alertWin('系统提示', showParam+'已关联本客户ID！');
                    $("#addCoustomerPhoneBut").show();
                } else if (data.code == 1) {
                    executeAddCusomerPhone();
                } else if (data.code == 2) {
                    $(getPopWinNode("phoneBindOneContact", "popWinPhoneNum")).html(showParam);
                    $(getPopWinNode("phoneBindOneContact", "popWinContactId")).html(data.bindContactId);
                    if (customerId == 'newCustomer') {
                        $(getPopWinNode("phoneBindOneContact", "popWinPhonesMsg")).html('。');
                        $(getPopWinNode("phoneBindOneContact", "popWinButtons")).hide();
                    } else {
                        $(getPopWinNode("phoneBindOneContact", "popWinPhonesMsg")).html('，是否新增？');
                        $(getPopWinNode("phoneBindOneContact", "popWinButtons")).show();
                    }
                    var options = {title: '客户新增电话'};
                    window.parent.popWin(frameElement.id, 'phoneBindOneContact' + $("#initCustomerId").val(), options);
                    $("#addCoustomerPhoneBut").show();
                } else {
                    $(getPopWinNode("phoneBindMultiContact", "popWinPhoneNum")).html(showParam);
                    if (customerId == 'newCustomer') {
                        $(getPopWinNode("phoneBindMultiContact", "popWinMultiPhonesMsg")).html('已关联多个客户ID，请选择绑定客户。');
                        $(getPopWinNode("phoneBindMultiContact", "popWinButtons")).hide();
                    } else {
                        $(getPopWinNode("phoneBindMultiContact", "popWinMultiPhonesMsg")).html('已关联多个客户ID，是否新增？');
                        $(getPopWinNode("phoneBindMultiContact", "popWinButtons")).show();
                    }
                    $(getPopWinNode("phoneBindMultiContact", "multiContactTable")).datagrid({
                        title:'',
                        iconCls:'icon-edit',
                        height: 200,
                        nowrap: false,
                        striped: true,
                        border: true,
                        collapsible:false,
                        fitColumns:true,
                        scrollbarSize:-1,
                        url:'/customer/contact/phone/find',
                        idField:'contactid',
                        sortName: 'crdt',
                        sortOrder: 'desc',
                        columns:[[
                            {field:'customerId',title:'客户编号',align:'center',width:100},
                            {field:'name',title:'客户名称',align:'center',width:80},
                            {field:'level',title:'会员等级',align:'center',width:80},
                            {field:'address',title:'客户地址',width:200,formatter:function(value, rowData, rowIndex){

                                return "<div unselectable='on' onselectstart='return false;' style='-moz-user-select:none;'><marquee scrollamount='3'>"+value+"</marquee></div>";

                            }},
                            {field:'crusr',title:'创建人',align:'center',width:80},
                            {field:'crdt',title:'创建时间',width:140},
                            {field:'addressid',title:'地址编号',width:100,hidden:true}
                        ]],
                        remoteSort:false,
                        singleSelect:false,
                        pagination:true,
                        queryParams: {
                            phone:checkParam,
                            phoneType:phoneType
                        },
                        onDblClickRow:function(index,row){
                            if(row){
                                window.parent.addTab(row.customerId,"客户详情",false,'/contact/1/'+row.customerId);
                                window.parent.closeWin('phoneBindMultiContact'+$("#initCustomerId").val());
                            }
                        }
                    });
                    $(getPopWinNode("phoneBindMultiContact", "multiContactTable")).datagrid('load');
                    var p = $(getPopWinNode("phoneBindMultiContact", "multiContactTable")).datagrid('getPager');
                    $(p).pagination({
                        pageSize: 10,
                        pageList: [5,10,15],
                        beforePageText: '第',
                        afterPageText: '页    共 {pages} 页',
                        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
                        onBeforeRefresh:function(){
                            $(this).pagination('loading');
                            $(this).pagination('loaded');
                        }
                    });
                    $('#phoneBindMultiContact' + $("#initCustomerId").val()).window('open');
                    $("#addCoustomerPhoneBut").show();
                }
            });
//        }
    } else $("#addCoustomerPhoneBut").show();
}

var executeAddCusomerPhone = function () {
    if ($("#customerId").val() == "") {
        if ($("#editRowIndex").val() != "") {
            $('#phoneTable').datagrid('updateRow', {
                index: $("#editRowIndex").val(),
                row: {
                    customerId: $("#customerId").val(),
                    customerType: $("#customerType").val(),
                    phn1: $("#phonetypid").attr("checked") ? $("#phn1").val() : "",
                    phn2: $("#phonetypid").attr("checked") ? $("#phn2").val() : $("#phn4").val(),
                    phn3: $("#phonetypid").attr("checked") ? $("#phn3").val() : "",
                    phonetypid: $("#phonetypid").attr("checked") ? '1' : '4',
                    prmphn: $("#isDefault").attr("checked") ? 'Y' : 'N'
                }
            });
            $("#editRowIndex").val("");
        } else {
            $("#editRowIndex").val("");
            $("#phoneTable").datagrid("appendRow",
                {customerId: $("#customerId").val(),
                    customerType: $("#customerType").val(),
                    phn1: $("#phonetypid").attr("checked") ? $("#phn1").val() : "",
                    phn2: $("#phonetypid").attr("checked") ? $("#phn2").val() : $("#phn4").val(),
                    phn3: $("#phonetypid").attr("checked") ? $("#phn3").val() : "",
                    phonetypid: $("#phonetypid").attr("checked") ? '1' : '4',
                    belongAddress: '',
                    prmphn: $("#isDefault").attr("checked") ? 'Y' : 'N'});
        }
        initPhone();
        $("#addCoustomerPhoneBut").show();
        window.parent.SetCwinHeight(frameElement);
    } else {
        if ($("#customerType").val() == '1') {
            $("#phoneTable").datagrid("appendRow",
                {customerId: $("#customerId").val(),
                    customerType: $("#customerType").val(),
                    phn1: $("#phonetypid").attr("checked") ? $("#phn1").val() : "",
                    phn2: $("#phonetypid").attr("checked") ? $("#phn2").val() : $("#phn4").val(),
                    phn3: $("#phonetypid").attr("checked") ? $("#phn3").val() : "",
                    phonetypid: $("#phonetypid").attr("checked") ? '1' : '4',
                    belongAddress: '',
                    prmphn: $("#isDefault").attr("checked") ? 'Y' : 'N'});
            initPhone();
            $("#addCoustomerPhoneBut").show();
            return;
        }
        $.post("/customer/mycustomer/phone/add",
            {customerId: $("#customerId").val(),
                customerType: $("#customerType").val(),
                phn1: $("#phn1").val(),
                phn2: $("#phn2").val(),
                phn3: $("#phn3").val(),
                phn4: $("#phn4").val(),
                isDefault: $("#isDefault").attr("checked"),
                phonetypid: $("#phonetypid").attr("checked"),
                potentialPhoneId: $("#potentialPhoneId").val()
            },
            function (data) {
                if (data.result) {
                    $("#phoneTable").datagrid({url: "/contact/phoneList/" + $("#customerType").val() + "/" + $("#customerId").val()});
                    $("#phoneTable").datagrid('reload');
                } else {
                    window.parent.alertWin('系统提示', data.msg);
                }
                $("#potentialPhoneId").val('');
                $("#addCoustomerPhoneBut").show();
            });
        $("#potentialPhoneId").val('');
        initPhone();
//        $("#AddPhoneTab").css("display", "none");
        window.parent.SetCwinHeight(frameElement);
    }
}

var clickPopWinContactId = function () {
    var contactId = $(getPopWinNode("phoneBindOneContact", "popWinContactId")).html();
    window.parent.addTab("p_"+$('#initCustomerId').val(),"客户详情",false,'/contact/1/'+contactId);
    window.parent.closeWin('phoneBindOneContact'+$("#initCustomerId").val());
}

var clickCancelPhoneBind = function () {
    window.parent.closeWin('phoneBindOneContact'+$("#initCustomerId").val());
}

var clickSubmitPhoneBind = function () {
    executeAddCusomerPhone();
    window.parent.closeWin('phoneBindOneContact'+$("#initCustomerId").val());
}

var clickCancelPhoneMultiBind = function () {
    $('#phoneBindMultiContact'+$("#initCustomerId").val()).window('close');
}

var clickSubmitPhoneMultiBind = function () {
    executeAddCusomerPhone();
    $('#phoneBindMultiContact'+$("#initCustomerId").val()).window('close');
}


var editPhone=function(rowIndex){
    displayCoustomerPhone();
    var rows = $('#phoneTable').datagrid('getRows');
    var rowData = rows[rowIndex];
    $("#potentialPhoneId").val(rowData.customerPhoneId);
    $("#phn1").val(rowData.phn1);
    $("#phn2").val(rowData.phn2);
    $("#phn3").val(rowData.phn3);
    $("#phn4").val(rowData.phn2);
    if(rowData.prmphn =="Y"){
        $("#isDefault").attr("checked",true);
    }else{
        $("#isDefault").attr("checked",false);
    }
    if(rowData.phonetypid=="4"){
        $('#phn1').validatebox("remove");
        $('#phn2').validatebox("remove");
        $('#phn4').validatebox("reduce");
        $("#fphone1").show();
        $("#fphone2").hide();
        if($("#editRowIndex").val() != ''){
            if(rowData.phonetypid=="4"){
                $('#phn2').val(rowData.phn2);
            }else{
                $('#phn2').val('');
            }
        }
    }else{
        $("#phonetypid").attr("checked",true);
        $('#phn1').validatebox("reduce");
        $('#phn2').validatebox("reduce");
        $('#phn4').validatebox("remove");
        $("#fphone1").hide();
        $("#fphone2").show();
        if($("#editRowIndex").val() != ''){
            if(rowData.phonetypid=="1"){
                $('#phn2').val(rowData.phn2);
            }else{
                $('#phn2').val('');
            }
        }
    }
    if($("#customerId").val()==""){
        $("#editRowIndex").val(rowIndex);
        if(rowData.prmphn =="Y"){
            $("#isDefaultPhoneTR").show();
        }else{
            $("#isDefaultPhoneTR").hidden();
        }
    }
    changefPhone();
}

var findByPhone=function(rowIndex){
    var rows = $('#phoneTable').datagrid('getRows');
    var rowData = rows[rowIndex];
    var phoneNum = (rowData.noEncryptionPhone == null || rowData.noEncryptionPhone == '') ? rowData.phn2 : rowData.noEncryptionPhone;
    parent.queryCustomer('','','queryByPhone',phoneNum,true);
    parent.findCustomer();
}

var deletePhone=function(rowIndex){
    var rows = $('#phoneTable').datagrid('getRows');
    var rowData = rows[rowIndex];
    if(rowData.prmphn =="Y") {
        window.parent.alertWin('系统提示', '主电话不能被删除');
        return;
    }
    if($("#customerId").val()==""){
        var rows = $('#phoneTable').datagrid('getRows');
        var rowData = rows[rowIndex];
        if(rowData.prmphn=="Y"){
            window.parent.alertWin('系统提示', "默认电话不可以删除");
        }else{
            $("#phoneTable").datagrid('deleteRow',rowIndex);
        }
    }else{
        $.post("/customer/mycustomer/phone/delete",{
            customerId : $("#customerId").val(),
            customerType : $("#customerType").val(),
            potentialPhoneId:rowData.customerPhoneId
        },function(data){
            $("#phoneTable").datagrid({url : "/contact/phoneList/"+ $("#customerType").val()+ "/"+ $("#customerId").val()});
            $("#phoneTable").datagrid('reload');
            if(! data.result){
                window.parent.alertWin('系统提示', data.msg);
            }
        });
    }
    initPhone();
//    $("#AddPhoneTab").css("display", "none");
}

var cancleCoustomerPhone = function(){
    $("#editRowIndex").val('');
    $("#formAddCustomerPhone")[0].reset();
    initPhone();
//    $("#AddPhoneTab").hide();
    window.parent.SetCwinHeight(frameElement);
}


var addCoustomerAddress = function(){
    if($("#formAddCustomerAddress").form('validate')){
        if ($("#customerId").val() == "") {
            if($("#editAddressRowIndex").val() != ""){
                $.ajax({
                    url: '/contact/getReceiveAddress/'+$("#province").combobox('getValue')+"/"+$("#cityId").combobox('getValue')+"/"+$("#countyId").combobox('getValue')+"/"+$("#areaId").combobox('getValue'),
                    type: 'GET',
                    dataType: 'json',
                    success: function(data) {
                        $("#addCoustomerAddressBut").show();
                        if (data == 'error') {
                            window.parent.alertWin('系统提示', '四级地址选择错误。');
                            return;
                        }
                        $("#addressTable").datagrid("updateRow",{index:$("#editAddressRowIndex").val(),
                            row:{state:$("#province").combobox('getValue'),
                                cityId:$("#cityId").combobox('getValue'),
                                countyId:$("#countyId").combobox('getValue'),
                                areaid:$("#areaId").combobox('getValue'),
                                address:$("#address").val(),
                                zip:$("#zip").val(),
                                receiveAddress:data,
                                isdefault:$("#isDefaultAddress").attr("checked")?'-1':'0'}
                        });
                        $("#editAddressRowIndex").val('');
                        cancleCoustomerAddress();
                    }});
            }else{
                $("#editAddressRowIndex").val('');
                $.ajax({
                    url: '/contact/getReceiveAddress/'+$("#province").combobox('getValue')+"/"+$("#cityId").combobox('getValue')+"/"+$("#countyId").combobox('getValue')+"/"+$("#areaId").combobox('getValue'),
                    type: 'GET',
                    dataType: 'json',
                    success: function(data) {
                        $("#addCoustomerAddressBut").show();
                        if (data == 'error') {
                            window.parent.alertWin('系统提示', '四级地址选择错误。');
                            return;
                        }
                        $("#addressTable").datagrid("appendRow",
                            {contactid:$("#customerId").val(),
                                state:$("#province").combobox('getValue'),
                                cityId:$("#cityId").combobox('getValue'),
                                countyId:$("#countyId").combobox('getValue'),
                                areaid:$("#areaId").combobox('getValue'),
                                address:$("#address").val(),
                                addrtypid:"2",
                                zip:$("#zip").val(),
                                receiveAddress:data,
                                isdefault:$("#isDefaultAddress").attr("checked")?'-1':'0'});
                        cancleCoustomerAddress();
                    }
                });
            }
//            $("#AddAdderssTab").hide();
        } else {
            $.ajax({
                url: '/contact/getReceiveAddress/'+$("#province").combobox('getValue')+"/"+$("#cityId").combobox('getValue')+"/"+$("#countyId").combobox('getValue')+"/"+$("#areaId").combobox('getValue'),
                type: 'GET',
                dataType: 'json',
                success: function(data) {
                    $("#addCoustomerAddressBut").show();
                    if (data == 'error') {
                        window.parent.alertWin('系统提示', '四级地址选择错误。');
                        return;
                    }
                    $("#addressTable").datagrid("appendRow",
                        {contactid:$("#customerId").val(),
                            state:$("#province").combobox('getValue'),
                            cityId:$("#cityId").combobox('getValue'),
                            countyId:$("#countyId").combobox('getValue'),
                            areaid:$("#areaId").combobox('getValue'),
                            address:$("#address").val(),
                            zip:$("#zip").val(),
                            receiveAddress:data,
                            isdefault:$("#isDefaultAddress").attr("checked")?'-1':'0'});
                    cancleCoustomerAddress();
                }
            });
//            $("#AddAdderssTab").hide();
        }
    } else $("#addCoustomerAddressBut").show();
}

var displayCoustomerAddress = function(){
    //清空数据
  //  afterInitAddress('');
    cancleCoustomerAddress();
    var addresss = $("#addressTable").datagrid("getRows");
    if (addresss.length==0) {
        $("#isDefaultAddress").attr("checked", true);
        $("#isDefaultAddress").attr("disabled",true);
        $("#isDefaultAddressTR").show();
    } else {
        $("#isDefaultAddress").attr("checked",false);
        $("#isDefaultAddress").attr("disabled",false);
        $("#isDefaultAddressTR").hide();
    }
    $("#address").show();
    $("#AddAdderssTab").css("display","block");
    window.parent.SetCwinHeight(frameElement);
}


var cancleCoustomerAddress = function(){
    if ($("#h_provid").val()){

        addressInit('',$("#h_provid").val(), $("#h_cityid").val(), "", "");
    }
    else{
        cancleAddressItem();
    }
    $("#formAddCustomerAddress")[0].reset();
    //清空下拉框内容
    $("#editAddressRowIndex").val('');
    $("#addressRowIndex").val('');
    $("#addressId").val('');
//    $("#AddAdderssTab").hide();
    window.parent.SetCwinHeight(frameElement);
    var addresss = $("#addressTable").datagrid("getRows");
    if (addresss.length==0) {
        $("#isDefaultAddress").attr("checked", true);
        $("#isDefaultAddress").attr("disabled",true);
        $("#isDefaultAddressTR").show();
    } else {
        $("#isDefaultAddress").attr("checked",false);
        $("#isDefaultAddress").attr("disabled",false);
        $("#isDefaultAddressTR").hide();
    }
}



var addCoustomerExt = function(){
    var carv,childv,parentv;
    if(typeof($("#isCar").attr("checked")) == "undefined"){
        carv=0;
    }else{
        carv='-1';
    }


    if(typeof($("#isChild").attr("checked")) == "undefined"){
        childv=0;
    }else{
        childv='-1';
    }

    if(typeof($("#isParent").attr("checked")) == "undefined"){
        parentv=0;
    }else{
        parentv='-1';
    }

    if ($("#email").val()!=null && $("#email").val()!='' && /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test($("#email").val()) == false) {
        $("#emailError").show();
        return;
    } else $("#emailError").hide();

    if($("#formAddCustomerExt").form('validate')){
        $.post("/customer/mycustomer/ext/add",{
            customerId:$("#customerId").val(),
            customerType:$("#customerType").val(),
            email:$("#email").val(),
            weixin:$("#weixin").val(),
            birthday:$("#birthday").datebox('getValue'),
            marriage:$("#marriage").combobox('getValue'),
            income:$("#income").combobox('getValue'),
            occupation:$("#occupationStatus").combobox('getValue'),
            education:$("#education").combobox('getValue'),
            isCar:carv,
            carValue:$("#carmoney2").val(),
            isChild:childv,
            childAge:$("#childrenage").datebox('getValue'),
            isParent:parentv,
            parentAge:$("#elderBirthday").datebox('getValue')
        },function(data){
            window.parent.alertWin('系统提示', data.result);
        });
    }
}

var clearCoustomerExt =function(){
    $("#formAddCustomerExt").form('clear');
}

var editAddress = function (rowIndex) {
    var rows = $('#addressTable').datagrid('getRows');
    var rowData = rows[rowIndex];
//    if (rowData.addressid!=null) {
//        $.get("/contact/haveExWarehouseOrder/"+rowData.addressid,function(data){
//            if (data) {
//                window.parent.alertWin('系统提示', "该地址关联有未出库订单，暂时不能修改。");
//                return;
//            }
//            else initAddressForm(rowData, rowIndex);
//        });
//    } else
        initAddressForm(rowData, rowIndex);
};

var onlyEditFourthAddress = function(rowIndex) {
    var rows = $('#addressTable').datagrid('getRows');
    var rowData = rows[rowIndex];
    if (rowData.addressid!=null) {
        $.get("/contact/haveExWarehouseOrder/"+rowData.addressid,function(data){
            if (data) {
                window.parent.alertWin('系统提示', "该地址关联有未出库订单，暂时不能修改。");
                return;
            }
            else {
                $("#editFourthAddressTab").show();
                $("#addAddressDiv").hide();
                $("#addressRowIndexForFourth").val(rowIndex);
                addressInitForEditFourth(rowData.state, rowData.cityId, rowData.countyId, rowData.areaid);
            }
        });
    }
}

var initAddressForm = function (rowData, rowIndex) {
    $("#AddAdderssTab").show();
    $("#address").val('');
    $("#address").show();
    addressInit('',rowData.state, rowData.cityId, rowData.countyId, rowData.areaid);
    $("#address").val();
    $("#zip").val(rowData.zip);
    $("#addressId").val(rowData.addressid);
    $("#addressRowIndex").val(rowIndex);
//    if (rowData.isdefault == '-1') {
//        $("#isDefaultAddress")[0].checked=true;
//    } else {
//        $("#isDefaultAddress")[0].checked=false;
//    }
    $("#isDefaultAddressTR").hide();
    window.parent.SetCwinHeight(frameElement);
};

var editAddressNoId = function (rowIndex) {
    $("#AddAdderssTab").show();
    $("#address").val('');
    $("#address").show();
    $("#editAddressRowIndex").val(rowIndex);
    var rows = $('#addressTable').datagrid('getRows');
    var rowData = rows[rowIndex];
    addressInit('',rowData.state, rowData.cityId, rowData.countyId, rowData.areaid);
    $("#address").val();
    $("#zip").val(rowData.zip);
    $("#addressId").val(rowData.addressid);
    if (rowData.isdefault == '-1') {
        $("#isDefaultAddress")[0].checked=true;
        $("#isDefaultAddress").attr("disabled",true);
        $("#isDefaultAddressTR").show();
    } else {
        $("#isDefaultAddressTR").hide();
        $("#isDefaultAddress")[0].checked=false;
        $("#isDefaultAddress").attr("disabled",false);
    }
};

var deleteAddress=function(rowIndex){
    var rows = $('#addressTable').datagrid('getRows');
    var rowData = rows[rowIndex];
    if (rowData.isdefault == '-1') {
        window.parent.alertWin('系统提示', '主地址不能被删除');
        return;
    }
    if($("#customerId").val()==''){
        var rows = $('#addressTable').datagrid('getRows');
        var rowData = rows[rowIndex];
        if(rowData.isdefault=="-1"){
            window.parent.alertWin('系统提示', "默认地址不可以删除");
        }else{
            $("#addressTable").datagrid('deleteRow',rowIndex);
        }
    }
}
var saveEditAddress = function() {
    if(!$("#formAddCustomerAddress").form('validate')) {
        $("#addCoustomerAddressBut").show();
        return;
    }
    $.ajax({
        url: '/contact/getReceiveAddress/'+$("#province").combobox('getValue')+"/"+$("#cityId").combobox('getValue')+"/"+$("#countyId").combobox('getValue')+"/"+$("#areaId").combobox('getValue'),
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            $("#addCoustomerAddressBut").show();
            if (data == 'error') {
                window.parent.alertWin('系统提示', '四级地址选择错误。');
                return;
            }
            $("#addressTable").datagrid("updateRow",{index:$("#addressRowIndex").val(),
                row:{state:$("#province").combobox('getValue'),
                    cityId:$("#cityId").combobox('getValue'),
                    countyId:$("#countyId").combobox('getValue'),
                    areaid:$("#areaId").combobox('getValue'),
                    address:$("#address").val(),
                    zip:$("#zip").val(),
                    receiveAddress:data,
                    isdefault:$("#isDefaultAddress").attr("checked")?'-1':'0'}
            });
            $("#addressId").val('');
            $("#addressRowIndex").val('');
            cancleCoustomerAddress();
        }});
//    $("#AddAdderssTab").hide();
    window.parent.SetCwinHeight(frameElement);
};

var editCard = function (rowIndex) {
    $("#addCardTable").show();
    var rows = $('#cardTable').datagrid('getRows');
    var rowData = rows[rowIndex];
    $("#addCardType").val(rowData.type);
    $("#addCardNumber").val(rowData.cardNumber);
    $("#addCardValidateDate").val(rowData.validDate);
    $("#addCardRowIndex").val(rowIndex);
    $("#addCardId").val(rowData.cardId);
};

var saveEditCard = function() {
    $("#cardTable").datagrid("updateRow",{index:$("#addCardRowIndex").val(),
        row:{type:$("#addCardType").val(),
            cardNumber:$("#addCardNumber").val(),
            validDate:$("#addCardValidateDate").val()
        }});
    $("#addCardId").val('');
    $("#addCardRowIndex").val('');
    $("#addCardTable").hide();
    window.parent.SetCwinHeight(frameElement);
};

var cancelEditCard = function() {
    $("#addCardId").val('');
    $("#addCardRowIndex").val('');
    $("#addCardTable").hide();
    window.parent.SetCwinHeight(frameElement);
};

//确认修改客户电话 地址
var submitEditPhoneAndAddress = function(){
    if($("#formAddCustomerInfo").form('validate')){
        $.post("/contact/submitEditPhoneAndAddress",{
            batchId:$("#input_batchId").val(),
            customerId:$("#customerId").val(),
            customerType:$("#customerType").val(),
            phones:JSON.stringify($("#phoneTable").datagrid("getRows")),
            addresss:JSON.stringify($("#addressTable").datagrid("getRows"))
        },function(data){
            window.parent.alertWin('系统提示', data.msg);
        });
    }
}

var clickIsCar = function(){
    var vcar= $("#isCar").attr("checked")?"1":"0";
    if(vcar=="0"){
        $("#carmoney2").val("");
        $("#carmoney2").attr("disabled","disabled");//再改成disabled
    }else{
        $("#carmoney2").removeAttr("disabled");//要变成Enable，JQuery只能这么写
    }
}

var clickIsChild = function(){
    var vchild= $("#isChild").attr("checked")?"1":"0";
    if(vchild=="0"){
        $('#childrenage').datebox('setValue', '');
        $('#childrenage').datebox('disable');
    }else{
        $('#childrenage').datebox('enable');
    }
}

var clickIsParent = function(){
    var vparent= $("#isParent").attr("checked")?"1":"0";
    if(vparent=="0"){
        $('#elderBirthday').datebox('setValue', '');
        $('#elderBirthday').datebox('disable');
    }else{
        $('#elderBirthday').datebox('enable');
    }
}

var setPrimePhone = function (rowIndex) {
    var rows = $('#phoneTable').datagrid('getRows');
    var rowData = rows[rowIndex];
    if ($("#customerId").val() == "") {
        resetDefaultPhone(rows, rowIndex);
    }else{
        if(rowData.customerPhoneId==null) {
            resetDefaultPhone(rows, rowIndex);
            return;
        }
        $.post("/contact/setPrimePhone",{
            customerId:$("#customerId").val(),
            customerType:$("#customerType").val(),
            phoneId:rowData.customerPhoneId
        },function(data){
            $('#phoneTable').datagrid('reload');
        });
    }
};

var resetDefaultPhone = function(rows, rowIndex) {
    for (var i = 0; i < rows.length; i ++) {
        $("#phoneTable").datagrid("updateRow",{index:i,
            row:{prmphn:'N'}});
    }
    $("#phoneTable").datagrid("updateRow",{index:rowIndex,
        row:{prmphn:'Y'}});
}

var editBackupPhone = function (rowIndex, node) {
    var rows = $('#phoneTable').datagrid('getRows');
    var rowData = rows[rowIndex];
    if (rowData.prmphn=='Y') {
        $(node).attr("checked",false);
        window.parent.alertWin('系统提示', "主电话不能同时成为备选电话。");
        return;
    }
    if ($("#customerId").val() == "") {
        resetBackupPhone(rows, rowIndex);
    }else{
        if(rowData.customerPhoneId==null) {
            resetBackupPhone(rows, rowIndex);
            return;
        }
        $.post("/contact/setBackupPhone",{
            customerId:$("#customerId").val(),
            customerType:$("#customerType").val(),
            phoneId:rowData.customerPhoneId,
            isBackup:rows[rowIndex].prmphn!='B'
        },function(data){
            $('#phoneTable').datagrid('reload');
        });
    }
};

var resetBackupPhone = function(rows, rowIndex) {
    if (rows[rowIndex].prmphn=='B') {
        $("#phoneTable").datagrid("updateRow",{index:rowIndex,
            row:{prmphn:'N'}});
        return;
    }
    var backupPhones = new Array();
    for (var i = 0; i < rows.length; i ++) {
        if (rows[i].prmphn=='B')  backupPhones.push(i);
    }
    if (backupPhones.length>1) {
        $("#phoneTable").datagrid("updateRow",{index:backupPhones[0],
            row:{prmphn:'N'}});
    }
    $("#phoneTable").datagrid("updateRow",{index:rowIndex,
        row:{prmphn:'B'}});
}

var setMainAddress = function (rowIndex) {
    var rows = $('#addressTable').datagrid('getRows');
    var rowData = rows[rowIndex];
    if(rowData.addressid==null) {
        resetMainAddress(rows, rowIndex);
        return;
    }
    $.post("/contact/setMainAddress",{
        customerId:$("#customerId").val(),
        addressId:rowData.addressid
    },function(data){
        $('#addressTable').datagrid('reload');
    });
}

var resetMainAddress = function(rows, rowIndex) {
    for (var i = 0; i < rows.length; i ++) {
        $("#addressTable").datagrid("updateRow",{index:i,
            row:{isdefault:'0'}});
    }
    $("#addressTable").datagrid("updateRow",{index:rowIndex,
        row:{isdefault:'-1'}});
}

function clickCancelEditNameAndSex() {
    window.parent.closeWin('compareNameAndSex'+$("#initCustomerId").val());
}

function clickSubmitEditNameAndSex() {
    window.parent.closeWin('compareNameAndSex'+$("#initCustomerId").val());
    submitEditNameAndSex();
}

function clickCancelEditPhoneAndAddress() {
    window.parent.closeWin('comparePhoneAndAddress'+$("#initCustomerId").val());
}

function clickSubmitEditPhoneAndAddress() {
    window.parent.closeWin('comparePhoneAndAddress'+$("#initCustomerId").val());
    submitEditPhoneAndAddress();
}

function getPopWinNode(popWinId, nodeClass) {
    return "#" + popWinId + $('#initCustomerId').val() + " ." + nodeClass;
}

function orderStatusFormatter(val, row){
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

function orderPaytypeFormatter(val, row){
    for(var i=0;i<orderPayTypeList.length;i++)
    {
        if(orderPayTypeList[i].id.id==val)
            return orderPayTypeList[i].dsc;
    }
    return val;
}

function logisticStatusFormatter(val, row){
    var _label = '';
    if(val==0){
        _label = '正常';
    }else if(val==1){
        _label = '已取消';
    }else if(val==2){
        _label = '已出货';
    }else if(val==3){
        _label = '';
    }else if(val==4){
        _label = '';
    }else if(val==5){
        _label = '客户签收';
    }else if(val==6){
        _label = '拒收未入库';
    }else if(val==7){
        _label = '问题单';
    }else if(val==8){
        _label = '结算完成';
    }else if(val==9){
        _label = '拒收入库';
    }else if(val==10){
        _label = '配送异常';
    }else if(val==11){
        _label = '总站收件';
    }else if(val==12){
        _label = '派送点收件';
    }else if(val==13){
        _label = '派件';
    }else if(val==14){
        _label = '部分签收';
    }else if(val==15){
        _label = '丢失';
    }else if(val==16){
        _label = '退回';
    }else if(val==17){
        _label = '转站';
    }else if(val==18){
        _label = '二次配送';
    }else if(val==19){
        _label = '三次配送';
    }
    return _label;
}

function dateFormatter(val, row){
    if(null!=val){
        return new Date(val).format('yyyy-MM-dd hh:mm:ss');
    }
}

function priorityFormatter(val){
    var _label = '';
    if(val==1){
        _label = '紧急';
    }else if(val==2){
        _label = '一般';
    }else if(val==3){
        _label = '可以等待';
    }else if(val==4){
        _label = '特别紧急';
    }
    return _label;
}

function complainStatusFormatter(val) {
    if (val == 1) return "已受理";
    else return "未受理";
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

function formatPrice(price) {
    if(price==null||price=='')
        return '0.00';
    var prc=price;
    return prc.toFixed(2);
}

function hideElement(node) {
    $(node).css("visibility","hidden");
}

function hideElementAndNext(node) {
    $(node).css("visibility","hidden");
    $($(node).next()).css("visibility","hidden");
}

function hideElementAndPrev(node) {
    $(node).css("visibility","hidden");
    $($(node).prev()).css("visibility","hidden");
}

/**
 * phone:外呼电话号码
 * type: 1. 本地;2.长途
 * instId:任务Id
 *
 */

var callPhone= function(phone,type,instId){
    if (!checkStatusToCall()) return;
    if ($("#analoglines", window.parent.document).val()=='false') {
        $('#outphone_loading').fadeIn();
        $('#outphone_loading div:first').css('line-height',$('#outphone_loading div:first').height()+'px');
    }
    $("#instanceId").val('');
    parent.outphone(phone,type,instId,$("#customerId").val(),$("#customerType").val(),0, frameElement.id);
}

function loadingFadeOut() {
    $('#outphone_loading').fadeOut();
}

function dateFormatter(val){
    if(null!=val){
        return new Date(val).format('yyyy-MM-dd hh:mm:ss');
    }
}

function checkStatusToCall() {
    if ($("#callback", window.parent.document).val()==1) {
        window.parent.alertWin('系统提示', '系统处于callback模式，不支持外呼。');
        return false;
    }

    // -1 摘机 1 就绪可外呼  2 离席 状态才能外拨  -- 0 就绪状态下不再支持外呼
    if (parent.phone.status==-1||parent.phone.status==1||parent.phone.status==2) {
        return true;
    }
    window.parent.alertWin('系统提示', "系统处在不能外拨的状态，请将软电话状态切换至[就绪(可外呼)]。");
    return false;
}

var problematicOrderCallPhone = function(phone,type) {
    var campaignDto = '{"id":' + $("#h_campaignId").val() + '}';
    ajaxToCreateTaskAndCallPhone(phone, campaignDto, type);
}

var createTaskAndCallPhone = function (phone, rowIndex, type) {
    //判断是否选择了任务 ， 新建任务 获取返回数据，直接外拨
    var campaignDto = $(".campaignTip" + type + "_" + rowIndex).val();
    if (null == campaignDto || '' == campaignDto) {
        return;
    }
    ajaxToCreateTaskAndCallPhone(phone, campaignDto, type);
};

var ajaxToCreateTaskAndCallPhone = function(phone, campaignDto,type) {
    var customerId = $("#customerId").val();
    $.ajax({
        url: '/contact/createTask',
        contentType: "application/json",
        data: {"campaignDtoStr":campaignDto, "contactId": customerId, "dnsi":phone},
        success:function(data){
            callPhone(phone,type,data.campaignTask.instID);
        }
    });
}

function viewOrder(listName, index, orderId,canedit) {
    var url='myorder/orderDetails/get/'+orderId+'?activable=false';
    window.parent.addTab('myorder_'+orderId,'订单：'+orderId,'false',url);
}

function refreshOrderHistory() {
    $('#orderHistory').datagrid('reload');
    bRefreshOrderHistory=true;
}

var bRefreshOrderHistory=false;
function showCallback() {
    if(bRefreshOrderHistory==true) {
        $('#orderHistory').datagrid('reload');
        bRefreshOrderHistory=false;
    }
}

function showOutPhoneType(rowIndex) {
    if (!checkStatusToCall()) return;
    var rows = $('#phoneTable').datagrid('getRows');
    var rowData = rows[rowIndex];
        if (rowData.customerPhoneId==null||rowData.customerPhoneId=='') return;
    var value = rowData.noEncryptionPhone;

    if ($("#instanceId").val()) {
        $.ajax({
            url: '/task/isTaskFinished',
            async: false,
            contentType: "application/json",
            data: {"instId":$("#instanceId").val()},
            success:function(data){
                if (data) $("#instanceId").val('');
            }
        });
    }

    if ($("#instanceId").val()) {
        $('.phoneTablePhn2_' + rowIndex).tooltip({
            hideEvent:"none",
            showEvent:"click",
            content: '<div class="textC"><div>'+$('.phoneTablePhn2_' + rowIndex).text()+'</div><a class="callback" href="javascript:void(0);" ondblclick="javascript:void(0);" onclick="hideElementAndNext(this);callPhone(\'' + value + '\', 1,\''+$("#instanceId").val()+'\')">本地</a>&nbsp;<a class="callback" href="javascript:void(0);" ondblclick="javascript:void(0);" onclick="hideElementAndPrev(this);callPhone(\'' + value + '\', 2,\''+$("#instanceId").val()+'\')">长途</a></div>',
            onShow: function (jq,e) {
                var t = $(this);
                t.tooltip('tip').css({
                    backgroundColor: '#fff',
                    borderColor: '#236FBD',
                    borderRadius:'5px',
                    padding:'3px',
                    minWidth: '130px',
                    color:'#236FBD',
                    boxShadow:'0 0 3px rgba(0,0,0,0.5)' ,
                    left: t.offset().left+ t.width()/2-63 ,
                    top:  t.offset().top-6
                }).unbind().focus().bind('blur', function () {
                        t.tooltip('hide');
                    });
            }
        });
        $('.phoneTablePhn2_' + rowIndex).tooltip("show").tooltip('tip').find('.tooltip-arrow,.tooltip-arrow-outer').hide();
    } else if ($("#h_campaignId").val()) {
        // 问题单
        $('.phoneTablePhn2_' + rowIndex).tooltip({
            hideEvent:"none",
            showEvent:"click",
            content: '<div class="textC"><div>'+$('.phoneTablePhn2_' + rowIndex).text()+'</div><a class="callback" href="javascript:void(0);" ondblclick="javascript:void(0);" onclick="hideElementAndNext(this);problematicOrderCallPhone(\'' + value + '\', 1)">本地</a>&nbsp;<a class="callback" href="javascript:void(0);" ondblclick="javascript:void(0);" onclick="hideElementAndPrev(this);problematicOrderCallPhone(\'' + value + '\', 2)">长途</a></div>',
            onShow: function (jq,e) {
                var t = $(this);
                t.tooltip('tip').css({
                    backgroundColor: '#fff',
                    borderColor: '#236FBD',
                    borderRadius:'5px',
                    padding:'3px',
                    minWidth: '130px',
                    color:'#236FBD',
                    boxShadow:'0 0 3px rgba(0,0,0,0.5)' ,
                    left: t.offset().left+ t.width()/2-63 ,
                    top:  t.offset().top-6
                }).unbind().focus().bind('blur', function () {
                        t.tooltip('hide');
                    });
            }
        });
        $('.phoneTablePhn2_' + rowIndex).tooltip("show").tooltip('tip').find('.tooltip-arrow,.tooltip-arrow-outer').hide();
    } else {
        //没有指定的instID
        // 1. 根据坐席ID，客户ID查找任务
        // a. 如果有唯一任务，直接外拨
        // b. 如果无任务，根据坐席ID、客户ID判断是否有新建任务权限
        // b1: 无权限，提示不能外拨；b2: 有权限，找出任务列表，选择一条，直接新建，外拨
        //c: 如果多条任务，弹出列表，坐席选择一条，直接外拨
        $.ajax({
            url: '/contact/queryContactTasks/'+$("#customerType").val()+"/"+$("#customerId").val(),
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                if (data.code == '0') {
                    window.parent.alertWin('系统提示', data.msg);
                } else if(data.code == '1') {
                    //弹出新建任务列表
                    $('.phoneTablePhn2_' + rowIndex).tooltip({
                        hideEvent:"none",
                        showEvent:"click",
                        content: '<div class="textC"><div>'+$('.phoneTablePhn2_' + rowIndex).text()+'</div><a class="callback phoneTip1_'+rowIndex+'">本地</a>&nbsp;<a class="callback phoneTip2_'+rowIndex+'">长途</a></div>',
                        onShow: function (jq,e) {
                            var t = $(this);
                            t.tooltip('tip').css({
                                backgroundColor: '#fff',
                                borderColor: '#236FBD',
                                borderRadius:'5px',
                                padding:'3px',
                                minWidth: '130px',
                                color:'#236FBD',
                                boxShadow:'0 0 3px rgba(0,0,0,0.5)' ,
                                left: t.offset().left+ t.width()/2-63 ,
                                top:  t.offset().top-6
                            }).unbind().focus().bind('blur', function () {
                                if ((!t.attr('isTip1Show') || t.attr('isTip1Show')=='false') && (!t.attr('isTip2Show')||t.attr('isTip2Show')=='false')) {
                                    t.tooltip('hide');
                                }
                        });
                        }
                    });
                    $('.phoneTablePhn2_' + rowIndex).tooltip("show").tooltip('tip').find('.tooltip-arrow,.tooltip-arrow-outer').hide();
                    $(".campaignTip1_" + rowIndex).each(function(){
                        this.parentNode.removeChild(this);
                    });
                    var localCampaignListSelectHtml = '<div class="textC mb5"><p><font style="color:#fff;">任务：</font><select class="campaignTip1_'+rowIndex+'" style="width:150px;">';
                    localCampaignListSelectHtml += '<option value="">--请选择--</option>';
                    for (var i = 0; i < data.campaignList.length; i++) {
                        var campaign = data.campaignList[i];
                        localCampaignListSelectHtml += '<option value=\''+campaign.json+'\'>'+campaign.name+'</option>';
                    }
                    localCampaignListSelectHtml += '</select></p><br><br><a class="dialBtn " href="javascript:void(0);" ondblclick="javascript:void(0);" onclick="hideElement(this);createTaskAndCallPhone(\''+value+'\','+rowIndex+', 1)">确定</a></div>';
                    $('.phoneTip1_' + rowIndex).tooltip({
                        showEvent: 'click',
                        hideEvent:'none',
                        content: localCampaignListSelectHtml,
                        onShow: function () {
                            var t = $(this);
                            $('.phoneTablePhn2_' + rowIndex).attr('isTip1Show', true);
                            t.tooltip('tip').css({
                                backgroundColor: '#3892D3',
                                borderColor: '#3892D3',
                                borderRadius:'5px',
                                padding:'3px',
                                boxShadow:'0 0 2px #999'

                            }).unbind().focus().bind('blur', function () {
                                    setTimeout(function() {
                                        if ($("select:focus").attr('class') != 'campaignTip1_'+rowIndex) {
                                            t.tooltip('hide');
                                            $('.phoneTablePhn2_' + rowIndex).attr('isTip1Show', false);
                                            if(!$('.phoneTablePhn2_' + rowIndex).attr('isTip2Show') || $('.phoneTablePhn2_' + rowIndex).attr('isTip2Show') == 'false') {
                                                $('.phoneTablePhn2_' + rowIndex).tooltip('hide');
                                            }
                                        } else {
                                            $(".campaignTip1_"+rowIndex).bind('blur', function() {
                                                t.tooltip('hide');
                                                $('.phoneTablePhn2_' + rowIndex).attr('isTip1Show', false);
                                                setTimeout(function() {
                                                    if(!$('.phoneTablePhn2_' + rowIndex).attr('isTip2Show') || $('.phoneTablePhn2_' + rowIndex).attr('isTip2Show') == 'false') {
                                                        $('.phoneTablePhn2_' + rowIndex).tooltip('hide');
                                                    }
                                                }, 500);
                                            });
                                        }
                                    }, 500);
                                });
                        }
                    });

                    $(".campaignTip2_" + rowIndex).each(function(){
                        this.parentNode.removeChild(this);
                    });
                    var remoteCampaignListSelectHtml = '<div class="textC mb5"><p><font style="color:#fff;">任务：</font><select class="campaignTip2_'+rowIndex+'" style="width:150px;">';
                    remoteCampaignListSelectHtml += '<option value="">--请选择--</option>';
                    for (var i = 0; i < data.campaignList.length; i++) {
                        var campaign = data.campaignList[i];
                        remoteCampaignListSelectHtml += '<option value=\''+campaign.json+'\'>'+campaign.name+'</option>';
                    }
                    remoteCampaignListSelectHtml += '</select></p><br><br><a class="dialBtn" href="javascript:void(0);" ondblclick="javascript:void(0);" onclick="hideElement(this);createTaskAndCallPhone(\''+value+'\','+rowIndex+', 2)">确定</a></div>';
                    $('.phoneTip2_' + rowIndex).tooltip({
                        showEvent: 'click',
                        hideEvent:'none',
                        content: remoteCampaignListSelectHtml,
                        onShow: function () {
                            var t = $(this);
                            $('.phoneTablePhn2_' + rowIndex).attr('isTip2Show', true);
                            t.tooltip('tip').css({
                                backgroundColor: '#3892D3',
                                borderColor: '#3892D3',
                                borderRadius:'5px',
                                padding:'3px'
                            }).unbind().focus().bind('blur', function () {
                                    setTimeout(function() {
                                        if ($("select:focus").attr('class') != 'campaignTip2_'+rowIndex) {
                                            t.tooltip('hide');
                                            $('.phoneTablePhn2_' + rowIndex).attr('isTip2Show', false);
                                            if(!$('.phoneTablePhn2_' + rowIndex).attr('isTip1Show') || $('.phoneTablePhn2_' + rowIndex).attr('isTip1Show')=='false') {
                                                $('.phoneTablePhn2_' + rowIndex).tooltip('hide');
                                            }
                                        } else {
                                            $(".campaignTip2_"+rowIndex).bind('blur', function() {
                                                t.tooltip('hide');
                                                $('.phoneTablePhn2_' + rowIndex).attr('isTip2Show', false);
                                                setTimeout(function() {
                                                    if(!$('.phoneTablePhn2_' + rowIndex).attr('isTip1Show') || $('.phoneTablePhn2_' + rowIndex).attr('isTip1Show') == 'false') {
                                                        $('.phoneTablePhn2_' + rowIndex).tooltip('hide');
                                                    }
                                                }, 500);
                                            });
                                        }
                                    }, 500);
                                });
                        }
                    });
                } else if (data.code == '2') {
                    //只有一个任务，直接外拨
                    $('.phoneTablePhn2_' + rowIndex).tooltip({
                        hideEvent:"none",
                        showEvent: 'click',
                        content: '<div class="textC"><div>'+$('.phoneTablePhn2_' + rowIndex).text()+'</div><a class="callback" href="javascript:void(0);" ondblclick="javascript:void(0);" onclick="hideElementAndNext(this);callPhone(\'' + value + '\', 1,\''+data.campaignTask.instID+'\')">本地</a>&nbsp;<a class="callback" href="javascript:void(0);" ondblclick="javascript:void(0);" onclick="hideElementAndPrev(this);callPhone(\'' + value + '\', 2,\''+data.campaignTask.instID+'\')">长途</a></div>',
                        onShow: function (jq,e) {
                            var t = $(this);
                            t.tooltip('tip').css({
                                backgroundColor: '#fff',
                                borderColor: '#236FBD',
                                borderRadius:'5px',
                                padding:'3px',
                                minWidth: '130px',
                                color:'#236FBD',
                                boxShadow:'0 0 3px rgba(0,0,0,0.5)' ,
                                left: t.offset().left+ t.width()/2-63 ,
                                top:  t.offset().top-6
                            }).unbind().focus().bind('blur', function () {
                                    t.tooltip('hide');
                                });
                        }
                    });
                    $('.phoneTablePhn2_' + rowIndex).tooltip("show").tooltip('tip').find('.tooltip-arrow,.tooltip-arrow-outer').hide();
                } else{
                    //弹出多个任务列表
                    $('.phoneTablePhn2_' + rowIndex).tooltip({
                        hideEvent:"none",
                        showEvent:"click",
                        content: '<div class="textC"><div>'+$('.phoneTablePhn2_' + rowIndex).text()+'</div><a href="javascript:void(0)" class="callback phoneTip1_'+rowIndex+'">本地</a>&nbsp;<a href="javascript:void(0)" class="callback phoneTip2_'+rowIndex+'">长途</a></div>',
                        onShow: function (jq,e) {
                            var t = $(this);
                            t.tooltip('tip').css({
                                backgroundColor: '#fff',
                                borderColor: '#236FBD',
                                borderRadius:'5px',
                                padding:'3px',
                                minWidth: '130px',
                                color:'#236FBD',
                                boxShadow:'0 0 3px rgba(0,0,0,0.5)' ,
                                left: t.offset().left+ t.width()/2-63,
                                top:  t.offset().top-6
                            }).unbind().focus().bind('blur',function () {
                                if ((!t.attr('isTip1Show') || t.attr('isTip1Show')=='false') && (!t.attr('isTip2Show')||t.attr('isTip2Show')=='false')) {
                                    t.tooltip('hide');
                                }
                            });
                        }
                    });
                    $('.phoneTablePhn2_' + rowIndex).tooltip("show").tooltip('tip').find('.tooltip-arrow,.tooltip-arrow-outer').hide();

                    var height = data.myCampaignList.length > 5 ? 160 : (22+data.myCampaignList.length*24);

                    var localMyCampaignTipTableHtml = '<div style="height:'+height+'px;overflow:auto;white-space:nowrap;"><table width="100%" class="tooltip_2" >';
                    localMyCampaignTipTableHtml += '<tr><th >任务编号</th><th>任务名称</th><th align="center">任务类型</th><th>任务生成时间</th></tr>';
                    for (var i = 0; i < data.myCampaignList.length; i++) {
                        var campaign = data.myCampaignList[i];
                        localMyCampaignTipTableHtml += '<tr><td><a class="link" href="javascript:void(0);" ondblclick="javascript:void(0);" onclick="hideElement(this);callPhone(\''+value+'\',1,\''+campaign.instID+'\')">'+campaign.instID+'</td><td>'+campaign.caName+'</td><td align="center">'+campaign.taskType+'</td><td>'+dateFormatter(campaign.ltCreateDate)+'</td></tr>';
                    }
                    localMyCampaignTipTableHtml+='</table></div>';
                    $('.phoneTip1_' + rowIndex).tooltip({
                        hideEvent:'none',
                        showEvent: 'click',
                        content: localMyCampaignTipTableHtml,
                        onShow: function () {
                            var t = $(this);
                            $('.phoneTablePhn2_' + rowIndex).attr('isTip1Show', true);
                            t.tooltip('tip').css({
                                backgroundColor: '#3892D3',
                                borderColor: '#3892D3',
                                borderRadius:'5px',
                                padding:'3px',
                                width:'361px',
                                left: t.offset().left+ t.width()/2-170
                            }).unbind().focus().bind('blur',function () {
                                    t.tooltip('hide');
                                    $('.phoneTablePhn2_' + rowIndex).attr('isTip1Show', false);
                                    setTimeout(function() {
                                        if(!$('.phoneTablePhn2_' + rowIndex).attr('isTip2Show') || $('.phoneTablePhn2_' + rowIndex).attr('isTip2Show')=='false') {
                                            $('.phoneTablePhn2_' + rowIndex).tooltip('hide');
                                        }
                                    },500);
                                });
                        }
                    });

                    var remoteMyCampaignTipTableHtml = '<div style="height:'+height+'px;overflow:auto;white-space:nowrap;"><table width="100%" class="tooltip_2">';
                    remoteMyCampaignTipTableHtml += '<tr><th>任务编号</th><th>任务名称</th><th align="center">任务类型</th><th>任务生成时间</th></tr>';
                    for (var i = 0; i < data.myCampaignList.length; i++) {
                        var campaign = data.myCampaignList[i];
                        remoteMyCampaignTipTableHtml += '<tr><td><a class="link" href="javascript:void(0);" ondblclick="javascript:void(0);" onclick="hideElement(this);callPhone(\''+value+'\',2,\''+campaign.instID+'\')">'+campaign.instID+'</td><td>'+campaign.caName+'</td><td align="center">'+campaign.taskType+'</td><td>'+dateFormatter(campaign.ltCreateDate)+'</td></tr>';
                    }
                    remoteMyCampaignTipTableHtml+='</table>';
                    $('.phoneTip2_' + rowIndex).tooltip({
                        hideEvent:'none',
                        showEvent: 'click',
                        content: remoteMyCampaignTipTableHtml,
                        onShow: function () {
                            var t = $(this);
                            $('.phoneTablePhn2_' + rowIndex).attr('isTip2Show', true);
                            t.tooltip('tip').css({
                                backgroundColor: '#3892D3',
                                borderColor: '#3892D3',
                                borderRadius:'5px',
                                padding:'3px',
                                width:'361px'
                            }).unbind().focus().bind('blur',function () {
                                    t.tooltip('hide');
                                    $('.phoneTablePhn2_' + rowIndex).attr('isTip2Show', false);
                                    setTimeout(function() {
                                        if(!$('.phoneTablePhn2_' + rowIndex).attr('isTip1Show') || $('.phoneTablePhn2_' + rowIndex).attr('isTip1Show')=='false') {
                                            $('.phoneTablePhn2_' + rowIndex).tooltip('hide');
                                        }
                                    },500);
                                });
                        }
                    });
                }
            }});
    }
}

function addContactInsure()
{
    var isok,status,parentv;
    if(typeof($("#contact_isok").attr("checked")) == "undefined"){
        isok=0;
    }else{
        isok='-1';
    }
    if(typeof($("#contact_insure").attr("checked")) == "undefined"){
        status=0;
    }else{
        status='-1';
    }

    //判断电话状态,通话中,挂断,后处理可以进行赠险
    if(isok == status && isok=='-1'){
        if(parent.phone.status ==4 || parent.phone.status == 5 || parent.phone.status == 6 || parent.phone.status == 7  ){

        }else{
            if($("#hasinsure").val()==0){
                if($("#v_insureAge").val()=="-1"){
                    $("#contact_isok").attr("checked",true);
                }else{
                    $("#contact_isok").attr("checked",false);
                }
                if($("#v_insureStatus").val()=="-1"){
                    $("#contact_insure").attr("checked",true);
                }else{
                    $("#contact_insure").attr("checked",false);
                }

                window.parent.alertWin('系统提示', "请在会话状态下添加保险");
                return ;
            }
        }
    }

    $.post("/contact/saveInsure",{
        contactid:$("#customerId").val(),
        isok:isok,
        dsc:$("#contact_insure_note").val(),
        status:status,
        refuse:$("#refuse").attr("checked")?"1":"0",
        callId:parent.phone.cticonnid

    },function(data){
        if(data!="ok"){
            window.parent.alertWin('系统提示', data);
        }
        else{
            window.parent.msgSplash('客户保险','保存成功',2);
        }

        $.get("/contact/getInsureList",{contactid:$("#customerId").val()},function(result){
            if(result.product){
                $('#insureInfoTab').datagrid("loadData",result.product);
            }else{
                $('#insureInfoTab').datagrid("loadData",[]);
            }
            if(result.productTo){
                $('#insureInfoCanTab').datagrid("loadData",result.productTo);
            }else{
                $('#insureInfoCanTab').datagrid("loadData",[]);
            }
        });


    });
}
/**
 * 修改拒绝接受免险
 *
 */

function changeIsrefuse(){
    $.get("/contact/getInsure",{contactid:$("#customerId").val()},function(result){
        if(result=="1"){
            window.parent.alertWin('系统提示', "该用户已选择,拒绝接受免险");
            $("#refuse").attr("checked",true);
            return;
        }
    });
}

function hideInsureExt()
{
    var ctab = $('#contact_ext').tabs('getTab', '保险客户基本信息').panel('options').tab;
    ctab.hide();
//    var ctab = $('#contact_ext').tabs('getTab', '赠险情况').panel('options').tab;
//    ctab.hide();
}



