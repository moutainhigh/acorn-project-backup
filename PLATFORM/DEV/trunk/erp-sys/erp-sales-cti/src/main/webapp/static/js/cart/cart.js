$(function () {
    $(".remove_phone_input").live('click', function () {
        $(this).parent().parent().remove();
    });
    $("#pay_type_window").panel({'onClose': function () {
        window.location.href = '/contact/1/' + $("#shoppingcart_contactId").val() + "?selectedTab=1"
    }
    });
    $("#zip001").keyup(function() {
        inputNumberOnly(this);
    });

    $(".numberOnly").live("keyup",function(){
        inputNumberOnly(this);
    }) ;

    $("#cart_address_desc_check").hide();
    var addressKey = $("#d_address_check", window.parent.document).val().split(",");
    console.info("inbound.js:"+addressKey);
    var isInvalidAddress = false;
    $("#id_address_desc").keyup(function(){
        var inputval=formatAddress($("#id_address_desc").val());
        console.info("inbound.js:"+inputval);
        for (var i = 0; i < addressKey.length; i++)
        {
            if(inputval.indexOf(addressKey[i])>=0){
                isInvalidAddress =true;
                break;
            }else{
                isInvalidAddress =false;
            }
        }
        if(isInvalidAddress){
            $("#cart_address_desc_check").show();
        }else{
            $("#cart_address_desc_check").hide();
        }
    });


});

function inputNumberOnly(element) {
    var regx = /\D/g;
    $(element).val($(element).val().replace(regx, ''));
}

function initShoppingCart() {
    $('#cart_tabs').tabs({
        border: false,
        onSelect: function (title) {
            if (title == "购物车") {
                $(this).find('li.tabs-selected a').removeClass('tabs_cart_disabled').addClass('tabs_cart');
                $(this).find('li:not(.tabs-selected) a').removeClass('tabs_info').addClass('tabs_info_disabled');
            } else {
                $(this).find('li.tabs-selected a').removeClass('tabs_info_disabled').addClass('tabs_info');
                $(this).find('li:not(.tabs-selected) a').removeClass('tabs_cart').addClass('tabs_cart_disabled');
            }
        }
    });
    $('#cart_tabs').tabs('disableTab', 1);

    $("#id_address_show_table").datagrid({
        title:'',
        width: '100%',
        height: 59,
        nowrap: true,
        border: true,
        collapsible:false,
        scrollbarSize:-1,
        url: '/cart/getAddressList/' + $("#shoppingcart_contactId").val() + '/single',
        fitColumns: true,
        columns: [
            [
                {field: 'addressid', hidden: true},
                {field: 'contactid', hidden: true},
                {field: 'auditState', title: '审核', width: 80,formatter:function(value){
                    if(value==0) return '<span class="exaa">待审核</span>';
                    else if(value==1) return '<span class="exaa">审核中</span>';
                    else if(value==3) return '<span class="exa">未通过</span>';
                    else  return '';
                }},
                {field: 'contactName', title: '姓名', width: 80},
                {field: 'receiveAddress', title: '收货地址', width: 150},
                {field: 'addressDesc', title: '详细地址', width: 40, resizable: false, formatter: function (value) {
                    return '<div onselectstart="return false;"><marquee direction="left" scrollamount="3">' + value + '</marquee></div>';
                }},
                {field: 'zip', title: '邮编',width: 80} ,
                {field: 'phone', title: '联系电话', width: 300}
            ]
        ],
        onLoadSuccess: function () {
            getOrderMessage();
        }
    });

    $("#id_address_list_table").datagrid({
        width: '100%',
        height: 95,
        url: '/cart/getAddressList/' + $("#shoppingcart_contactId").val() + '/list',
        fitColumns: true,
        scrollbarSize:-1,
        singleSelect:true,
        columns: [
            [
                {field: 'addressid', hidden: true},
                {field: 'state', hidden: true},
                {field: 'cityId', hidden: true},
                {field: 'countyId', hidden: true},
                {field: 'areaid', hidden: true},
                {field: 'phoneStrList', hidden: true},
                {field: 'contactid', hidden: true},
                {field: 'addrtypid', hidden: true},
                {field: 'auditState', title: '审核',width: 80,formatter:function(value){
                    if(value==0) return '<span class="exaa">待审核</span>';
                    else if(value==1) return '<span class="exaa">审核中</span>';
                    else if(value==3) return '<span class="exa">未通过</span>';
                    else  return '';
                }},
                {field: 'contactName', title: '姓名', width: 80},
                {field: 'receiveAddress', title: '收货地址',  width: 150},
                {field: 'addressDesc', title: '详细地址',width: 40, resizable: false, formatter: function (value) {
                    return '<div onselectstart="return false;"><marquee direction="left" scrollamount="3">' + value + '</marquee></div>';
                }},
                {field: 'zip', title: '邮编',width: 80} ,
                {field: 'phone',title: '联系电话',  width: 180},
                {field: 'isdefault', width: 60, formatter: function (value, row, index) {
                    if (value != '-1' ) {
                        return '<a   onclick="updateContactMainAddress(' + row.contactid+','+row.addressid+','+row.auditState + ')"> 设为默认</a>';
                    }
                }} ,
                {field: 'modify', width: 60, formatter: function (value, row, index) {
                    return '<a  onclick="updateContactAddress(' + index + ')" >修改</a>';
                }}
            ]
        ],
        onClickCell: function (rowIndex, field, value) {
            if (field != 'isdefault' && field != 'modify') {
                var row = $('#id_address_list_table').datagrid('getRows')[rowIndex];
                if(row.auditState==1 || row.auditState==3){
                    window.parent.alertWin('系统提示', "审核中或未通过的地址无法下单");
                    return;
                }
                if(!row.state || !row.cityId || !row.countyId  || !row.areaid ){
                    window.parent.alertWin('系统提示', "收货地址不完整，请修改补全");
                    return;
                }
                $("#id_address_show_table").datagrid({url: "/cart/getAddressByAddressId/" + row.contactid+ "/" + row.addressid});

                toggleAddressList(false);
            }
        }
    });

    $("#id_self_take_table").datagrid({
        url: "/cart/getTakeAddressList/" + $("#shoppingcart_contactId").val(),
        width: '100%',
        singleSelect: true,
        fitColumns: true,
        columns: [
            [
                {field: 'ck', checkbox: true},
                {field: 'type', width: 100, title: '类型'},
                {field: 'contactid', width: 100, title: '客户编号'},
                {field: 'contactName', width: 100, title: '收货人'},
                {field: 'receiveAddress', width: 180, title: '收货地址'},
                {field: 'address', width: 200, title: '详细地址'},
                {field: 'zip', width: 100, title: '邮编'},
                {field: 'phone', width: 100, title: '联系电话'}
            ]
        ],
        onSelect: function (rowIndex, rowData) {
            getOrderMessage();
        }});

    calculateShoppingCartPrice();
    initOrderType();
    initLeadId();
    checkContactName();
    initCreditCard();
    insertValidate();
}
function insertValidate(){
    $("#id_insure_message").html("");
    $.get( '/cart/insureProductValidate/'+$("#shoppingcart_id").val(),
        function(data){
            if(data){
                $("#id_insure_message").html(data);
            }
        }
    );
}

function checkContactName(){
    $.post( '/cart/check/contactName',
        {contactId:$("#shoppingcart_contactId").val()},
        function (data) {
            if (data == 'auditContact') {
                $("#id_address_errorMessage").html("收货人正在审批中,无法选用");
                return;
            }
            if (data != 'false') {
                $("#id_address_errorMessage").html("收货人姓名正在审批中");
            }else{
                $("#id_address_errorMessage").html("");
            }
        }
    );
}

//获取订单类型
function initOrderType() {
    var phone = window.parent.phone;
    var getOrderTypeUrl = "/cart/getOrderType";
    if (phone) {
        getOrderTypeUrl += "?d=" + new Date()
        if (phone.customerId == $("#shoppingcart_contactId").val()) {
            if (phone.dnsi) {
                getOrderTypeUrl += "&dnsi=" + phone.dnsi;
            }
            if (phone.instId) {
                getOrderTypeUrl += "&instId=" + phone.instId;
            }
        }
    }

    $.get(getOrderTypeUrl,
        function (data) {
            $("#id_orderTypeMessage").html("");
            if (data.message != null) {
                $("#id_orderTypeMessage").html( data.message);
            } else {
                $("#id_order_type").html("");
                var grpOrderTypeList = eval("(" + data.result + ")");
                for (var index in grpOrderTypeList) {
                    var grpOrderType = grpOrderTypeList[index];
                    if (grpOrderType.default) {
                        $("#id_order_type").append('<option selected="selected" value="' + grpOrderType.orderType + '">' + grpOrderType.orderTypeName + '</option>');
                    } else {
                        $("#id_order_type").append('<option value="' + grpOrderType.orderType + '">' + grpOrderType.orderTypeName + '</option>');
                    }
                }
            }
        }
    );
}

//获取leadid
function initLeadId() {
    var getLeadIdUrl = '/cart/queryMarketingTask/' + $("#shoppingcart_contactId").val();
    if (window.parent.phone && window.parent.phone.instId) {
        getLeadIdUrl += "?instId=" + window.parent.phone.instId;
    }
    $.post(getLeadIdUrl,
        function (data) {
            $("#leadId").val(data);
        }
    );
}

//计算购物车产品数量及价格
function calculateShoppingCartPrice() {
    $.get('/cart/getSalePrice/' + $("#shoppingcart_id").val(),
        function (data) {
            if (data != null && data.result.length != 0 && data.result != "[]") {
                $("#id_shoppingCartProductPrice").html("¥" + data.result);
                $("#id_scp_price").html(data.result);
            }
        }
    );

    $.get('/cart/getProductQuantity/' + $("#shoppingcart_id").val(),
        function (data) {
            if (data != null && data.result.length != 0 && data.result != "[]") {
                $("#id_shoppingCartProductQuantity").html(data.result);
            }
        }
    );

    $.get('/cart/getScmPromotions/' + $("#shoppingcart_id").val(),
        function (data) {
            //删除所有促销产品
            $("#id_scmPromotonTable").find("tr").each(function (trindex, tritem) {
                if (trindex > 1) {
                    $(tritem).remove();
                }
            });
            if (data != null && data.result.length != 0 && data.result != "[]") {
                var scmPromotions = eval("(" + data.result + ")");
                //重新加载促销产品
                for (var i = 0; i < scmPromotions.length; i++) {
                    var scmPromotion = scmPromotions[i];
                    var html = getScmPromotionHtml(scmPromotion);
                    $("#id_scmPromotonTable").append(html);
                }
                window.parent.SetCwinHeight(frameElement);
            }
        }
    );

    if(window.parent.phone.insure){
        $.get('/cart/getInsurePromotions/' + $("#shoppingcart_id").val(),
            function (data) {
                //删除所有促销产品
                $("#id_insurePromptTable").find("tr").each(function (trindex, tritem) {
                    if (trindex > 1) {
                        $(tritem).remove();
                    }
                });
                if (data != null && data.result.length != 0 && data.result != "[]") {
                    var productDtos = eval("(" + data.result + ")");
                    //重新加载促销产品
                    for (var i = 0; i < productDtos.length; i++) {
                        var productDto = productDtos[i];
                        var html = getInsurePromotionHtml(productDto);
                        $("#id_insurePromptTable").append(html);
                    }
                    window.parent.SetCwinHeight(frameElement);
                }
            }
        );
    }


};

//获取地址id
function getAddressId() {
    var addressId = '';
    if ($("#tbPaytype").val() == 11) {
        var row = $('#id_self_take_table').datagrid('getSelected');
        if (row) {
            addressId = row.addressid;
        }
    } else {
        var rows = $('#id_address_show_table').datagrid('getRows');
        if (rows) {
            var row = rows[0];
            if (row) {
                addressId = row.addressid;
            }
        }
    }
    return addressId;
}

//获取address表中contactid
function getAddressContactId() {
    var contactId = '';
    var rows = $('#id_address_show_table').datagrid('getRows');
    if (rows) {
        var row = rows[0];
        if (row) {
            contactId = row.contactid;
        }
    }

    if(!contactId){
        contactId = $("#shoppingcart_contactId").val();
    }
    return contactId;
}

function getAddressAudltState(){
    var audltState = -1;
    if ($("#tbPaytype").val() != 11) {
        var rows = $('#id_address_show_table').datagrid('getRows');
        if (rows) {
            var row = rows[0];
            if (row) {
                audltState = row.auditState;
            }
        }
    }
    return audltState;
}


//获取运费 获取配送时效
function getOrderMessage() {
    var addressid = getAddressId();
    $("#id_deliveyDays_Message").html("");
    if (addressid) {
        $.post('/cart/getOrderMessage'  ,
            {shoppingCartId:$("#shoppingcart_id").val(),invoicetitle: $("#invoicetitle").val(), addressId: getAddressId(),
                ordertype: $("#id_order_type").val(), paytype: $("#tbPaytype").val(), laststatus: getCreditLastStatus() },
            function (data) {
                if (data.mailPrice !=null) {
                    $("#order_mail_price").attr("value", data.mailPrice);
                    $("#defaultMailPrice").attr("value", data.mailPrice);
                    calculatePayPrice();
                }
                if(data.deliveryDays){
                    $("#id_deliveyDays_Message").html("预计配送时间:"+data.deliveryDays+"天");
                }
            }
        );
    }
}

//获取运费 获取配送时效
function getSynchOrderMessage() {
    var addressid = getAddressId();
    $("#id_deliveyDays_Message").html("");
    if (addressid) {
        $.ajax({
            url:'/cart/getOrderMessage'  ,
            type: 'POST',
            async:false,
            dataType: 'JSON',
            data:{shoppingCartId:$("#shoppingcart_id").val(),invoicetitle: $("#invoicetitle").val(), addressId: getAddressId(),
                ordertype: $("#id_order_type").val(), paytype: $("#tbPaytype").val(), laststatus: getCreditLastStatus() },
            success:function(data){
                if (data.mailPrice !=null) {
                    $("#order_mail_price").attr("value", data.mailPrice);
                    $("#defaultMailPrice").attr("value", data.mailPrice);
                    calculatePayPrice();
                }
                if(data.deliveryDays){
                    $("#id_deliveyDays_Message").html("预计配送时间:"+data.deliveryDays+"天");
                }
            }

        });
    }
}

//计算总价
function calculatePayPrice() {
    var scpPrice = parseFloat($("#id_scp_price").html());
    var mailPrice = parseFloat($("#order_mail_price").val());
    var totalPrice = scpPrice + mailPrice;
    if (totalPrice < 0) {
        totalPrice = 0;
    }

    $("#tbPayAmount").val(totalPrice.toFixed(2));
    $("#id_pay_price").html(totalPrice.toFixed(2));
    $("#id_conpoint_get").html((scpPrice / 100).toFixed(2));

    $("#id_mailPriceChange_Message").html("");
    if ($("#mailPricePermission").val() == 'false') {
        var defaultMailPrice = $("#defaultMailPrice").val();
        var orderMailPrice = $("#order_mail_price").val();
        if (defaultMailPrice != orderMailPrice) {
            $("#id_mailPriceChange_Message").html("修改运费，需要主管审批之后订单才能放行");
        }
    }
    parent.SetCwinHeight(frameElement);
}

//全选或反选商品
function checkShoppingCartProduct(input){
    $.get("/cart/shoppingCartProductSelectChange/"+ $("#shoppingcart_id").val()+"/"+input.checked,
        function (data) {
            if(data =='success'){
                $("#id_shoppingCartTable").find("input[name='isSelected']").each(function(index,item){
                    $(this).attr("checked", input.checked);
                }) ;
                calculateShoppingCartPrice();
            } else{
                reloadAllShoppingCartProducts();
                calculateShoppingCartPrice();
            }
        }
    );
}

//删除购物车产品
function deleteShoppingCartProduct(productId) {
    $.get('/cart/deleteProduct/' + $("#shoppingcart_id").val() + '/' + productId,
        function (data) {
            if (data.message != null) {
                window.parent.alertWin('系统提示', data.message);
                reloadAllShoppingCartProducts();
                calculateShoppingCartPrice();
            } else {
                //页面产品删除
                $('#scp_' + productId).remove();
                //级联删除
                if (data != null && data.result.length != 0 && data.result != "[]") {
                    var shoppingcartProducts = eval("(" + data.result + ")");
                    var deleteProducts = '';
                    for (var i = 0; i < shoppingcartProducts.length; i++) {
                        var shoppingcartProduct = shoppingcartProducts[i];
                        deleteProducts += shoppingcartProduct['productName'] + ',';
                        $('#scp_' + shoppingcartProduct['id']).remove();
                    }
                    window.parent.alertWin('系统提示', deleteProducts + '不满足促销规则被删除');
                }
                calculateShoppingCartPrice();
            }
        }
    );
}

//添加促销产品
function addScmPromotion(pluid, prodname, slprc, giftSource) {
    var prodtype = '';
    if ($("#scm_" + pluid).find("select") && $("#scm_" + pluid).find("select").val()) {
        prodtype = $("#scm_" + pluid).find("select").val();
    }
    $.post('/cart/addScmPromotion',
        {shoppingCartId:$("#shoppingcart_id").val() ,skuCode: pluid, productName: prodname, productType: prodtype, salePrice: slprc,giftSource: giftSource},
        function (data) {
            if (data.message != null) {
                window.parent.alertWin('系统提示', data.message);
                reloadAllShoppingCartProducts();
                calculateShoppingCartPrice();
            } else {
                //购物车列表增加
                var shoppingCartProductDto = eval("(" + data.result + ")");
                //判断购物车中是否已经存在，如果存在改变数量
                var product = $("#id_shoppingCartTable").find("#scp_" + shoppingCartProductDto.id);
                if (product.length > 0) {
                    var count = parseInt(product.find(".productQuantity").find("input").val()) + parseInt(shoppingCartProductDto.productQuantity);
                    product.find(".productQuantity").find("input").attr("value", count);
                    calculateDetailPrice(shoppingCartProductDto.id);
                } else {
                    var html = getShoppingCartHtml(shoppingCartProductDto);
                    $("#id_shoppingCartTable").append(html);
                }
            }
            calculateShoppingCartPrice();
        }
    );
}

function addInsurePromotion(prodid,unitprice){
    var prodtype = '';
    if ($("#insure_" + prodid).find("select") && $("#insure_" + prodid).find("select").val()) {
        prodtype = $("#insure_" + prodid).find("select").val();
    }

    addProduct(prodid,prodtype,unitprice);
}
//添加库存产品
function addProduct(ncCode, ncFreeName,salePrice) {
    if($("#inboundtabs").tabs('getSelected').panel('options').title !="客户购物车"){
        $("#inboundtabs").tabs("select",5);
    }
    if($("#cart_tabs").tabs('getSelected').panel('options').title !="购物车"){
        $("#cart_tabs").tabs("select", 0);
    }

    ncFreeName = ncFreeName.replace(/\+/g, "%2B").replace(/\&/g, "%26");
    $.post('/cart/addShoppingCartProduct' ,
        {ncCode: ncCode, ncFreeName: ncFreeName,salePrice:salePrice, shoppingCartId:$("#shoppingcart_id").val()},
        function (data) {
            if (data.message != null) {
                window.parent.alertWin('系统提示', data.message);
                reloadAllShoppingCartProducts();
                calculateShoppingCartPrice();
            } else if(data.reload=="true"){
                reloadAllShoppingCartProducts();
                calculateShoppingCartPrice();
            }else {
                //购物车列表增加
                var shoppingCartProductDto = eval("(" + data.result + ")");
                //判断购物车中是否已经存在，如果存在改变数量
                var product = $("#id_shoppingCartTable").find("#scp_" + shoppingCartProductDto.id);
                if (product.length > 0) {
                    var count = parseInt(product.find(".productQuantity").find("input").val()) + parseInt(shoppingCartProductDto.productQuantity);
                    product.find(".productQuantity").find("input").attr("value", count);
                    calculateDetailPrice(shoppingCartProductDto.id);
                } else {
                    var html = getShoppingCartHtml(shoppingCartProductDto);
                    $("#id_shoppingCartTable").append(html);
                }
            }
            calculateShoppingCartPrice();
        }
    );
}

function reloadAllShoppingCartProducts(){
    $.get('/cart/shoppingCartReload/'+$("#shoppingcart_id").val(),function(data){
        $("#id_shoppingCartTable").html("");
        var shoppingCartProductDtos = eval(data.result);
        for(var index in shoppingCartProductDtos){
            var html = getShoppingCartHtml(shoppingCartProductDtos[index]);
            $("#id_shoppingCartTable").append(html);
        }
    }) ;
}

//促销产品HTML
function getScmPromotionHtml(scmPromotion) {
    var html = '<tr id =scm_' + scmPromotion.pluid + ' >';
    html += '<td>' + scmPromotion.docname + '</td>';
    html += '<td>' + scmPromotion.pluid + '</td>';
    html += '<td>' + scmPromotion.prodname + '</td>';
    html += '<td>'
    if (scmPromotion.productTypes && scmPromotion.productTypes != '') {
        html += '<select>'
        for (var index in scmPromotion.productTypes) {
            var productType = scmPromotion.productTypes[index];
            html += '<option value="' + productType.ncfreename + '">' + productType.ncfreename + '</option>';
        }
        html += '</select>'
    }
    html += '</td>';
    html += '<td>¥' + scmPromotion.slprc + '</td>';
    html += '<td align="center">';
    html += '<a class="cart_addBtn" href="javascript:void(0)" onclick="addScmPromotion(\'' + scmPromotion.pluid + '\',\'' + scmPromotion.prodname + '\',\'' + scmPromotion.slprc + '\',\'' + scmPromotion.docname + '\')"  ></a>';
    html += '</td>';
    html += '</tr>';
    return html;
}

//保险产品HTML
function getInsurePromotionHtml(productDto) {
    var html = '<tr id =insure_' + productDto.prodid + ' >';
    html += '<td>' + productDto.script + '</td>';
    html += '<td>' + productDto.prodid + '</td>';
    html += '<td>' + productDto.prodname + '</td>';
    html += '<td>'
    if (productDto.productTypes && productDto.productTypes != '') {
        html += '<select>'
        for (var index in productDto.productTypes) {
            var productType = productDto.productTypes[index];
            html += '<option value="' + productType.ncfreename + '">' + productType.ncfreename + '</option>';
        }
        html += '</select>'
    }
    html += '</td>';
    html += '<td>¥' + productDto.unitprice + '</td>';
    html += '<td align="center">';
    html += '<a class="cart_addBtn" href="javascript:void(0)" onclick="addInsurePromotion(\'' + productDto.prodid +  '\',\'' + productDto.unitprice + '\')"  ></a>';
    html += '</td>';
    html += '</tr>';
    return html;
}

//添加购物车产品HTML
function getShoppingCartHtml(shoppingCartProductDto) {
    var html = '<tr id=scp_' + shoppingCartProductDto.id + ' >';
    html += '<td width="5%" class="isSelected" ><input name="isSelected" type="checkbox" checked="checked" onchange="updateShoppingCartProduct(' + shoppingCartProductDto.id + ',\'isSelected=\'+this.checked)"  /></td>';
    html += '<td width="10%">' + shoppingCartProductDto.skuCode + '</td>';
    html += '<td width="15%">' + shoppingCartProductDto.productName + '</td>';
    html += '<td width="15%" class="productType">';
    if (shoppingCartProductDto.productType) {
        html += '<select name="select" onchange="updateShoppingCartProduct(' + shoppingCartProductDto.id + ',\'productType=\'+this.value)" >';
        var productTypes = shoppingCartProductDto.productTypes;
        for (var x in productTypes) {
            if (shoppingCartProductDto.productType == productTypes[x].ncfreename) {
                html += '<option selected="selected"  value ="' + productTypes[x].ncfreename + '">' + productTypes[x].ncfreename + '</option>';
            } else {
                html += '<option  value ="' + productTypes[x].ncfreename + '">' + productTypes[x].ncfreename + '</option>';
            }
        }
        html += '</select>';
    }
    html += '</td>';
    html += '<td  width="10%" class="salePrice" >¥&nbsp;<input type="text" onchange="updateShoppingCartProduct(' + shoppingCartProductDto.id + ',\'salePrice=\'+this.value)"  style="width: 40px" value="' + shoppingCartProductDto.salePrice + '" ';
    if (shoppingCartProductDto.isScmGift) {
        html += 'readonly="readonly"';
    }
    html += ' /></td>';
    html += '<td  width="10%" class="productQuantity" ><input type="text" onchange="updateShoppingCartProduct(' + shoppingCartProductDto.id + ',\'productQuantity=\'+this.value)"  style="width: 30px" value="' + shoppingCartProductDto.productQuantity + '" ';
    if (shoppingCartProductDto.isScmGift && shoppingCartProductDto.salePrice == 0) {
        html += 'readonly="readonly"';
    }
    html += ' /></td>';
    html += '<td  width="10%" class="point" ><input type="text"  onchange="updateShoppingCartProduct(' + shoppingCartProductDto.id + ',\'point=\'+this.value)" style="width: 30px" value="' + shoppingCartProductDto.point + '"  /></td>'
    html += '<td  width="10%" class="detailPrice" ><span>¥&nbsp;' + shoppingCartProductDto.detailPrice + '</span></td>'
    html += '<td   width="10%" ><select name="select4" ';
    if (shoppingCartProductDto.isScmGift) {
        html += 'disabled="disabled"';
    }
    html += 'onchange="updateShoppingCartProduct(' + shoppingCartProductDto.id + ',\'isGift=\'+this.value)">'
    if (shoppingCartProductDto.isGift == 3) {
        html += '<option value="1">直接销售</option><option value="2">搭销</option><option selected="selected" value="3">赠品</option></select></td>';
    } else if (shoppingCartProductDto.isGift == 1) {
        html += '<option selected="selected" value="1">直接销售</option><option value="2">搭销</option><option value="3">赠品</option></select></td>';
    } else {
        html += '<option value="1">直接销售</option><option selected="selected" value="2">搭销</option><option value="3">赠品</option></select></td>';
    }
    html += '<td  width="5%" align="center"><a class="cart_delBtn" href="javascript:void(0);" onclick="deleteShoppingCartProduct(' + shoppingCartProductDto.id + ')" ></a></td>';
    html += ' </tr>';
    return html;
}

//验证用户输入积分
function validatePoint(id, property) {
    var allPoint = 0;
    var usedPoint = 0;
    if ($("#allPoint").val()) {
        allPoint = parseFloat($("#allPoint").val());
    }
    $("#id_shoppingCartTable").find(".point").each(function (index, item) {
        if ($(this).find("input").val()) {
            usedPoint += parseFloat($(this).find("input").val());
        }
    });
    var salePrice = parseFloat($("#scp_" + id).find(".salePrice").find("input").val());
    var productQuantity = parseFloat($("#scp_" + id).find(".productQuantity").find("input").val());
    if (usedPoint > allPoint) {
        window.parent.alertWin('系统提示', "使用积分超过剩余可用积分");
        var inputPoint = parseFloat($("#scp_" + id).find(".point").find("input").val());
        var leftPoint = allPoint + inputPoint - usedPoint;
        $("#scp_" + id).find(".point").find("input").attr("value", leftPoint);
        property = 'point=' + leftPoint;
        if (salePrice * productQuantity < leftPoint) {
            property = 'point=' + salePrice * productQuantity;
        }
    } else {
        if (salePrice * productQuantity < usedPoint) {
            window.parent.alertWin('系统提示', "使用积分超过产品价格");
            property = 'point=' + salePrice * productQuantity;
        }
    }
    return property;
}

//计算结算金额
function calculateDetailPrice(scpId) {
    var point = $("#scp_" + scpId).find(".point").find("input").val();
    if (point) {
        point = parseFloat(point);
    }
    var salePrice = parseFloat($("#scp_" + scpId).find(".salePrice").find("input").val());
    var productQuantity = parseFloat($("#scp_" + scpId).find(".productQuantity").find("input").val());
    if (salePrice * productQuantity < point) {
        window.parent.alertWin('系统提示', "使用积分大于产品价格");
        $("#scp_" + scpId).find(".point").find("input").val(salePrice * productQuantity);
        point = salePrice * productQuantity;
    }
    var detailPrice = ((salePrice - (point / productQuantity).toFixed(2)) * productQuantity).toFixed(2);
    $("#scp_" + scpId).find(".detailPrice").find("span").html('¥&nbsp;' + detailPrice);
}
//标记值，当更新购物车和去结算同时触发时，保证正确更新时才能触发去结算
var validateSkip =true;

//更新购物车信息，id:购物车产品id，property：购物车属性xxxx=value
function updateShoppingCartProduct(shoppingCartProductId, property) {
    validateSkip=true;
    property = validatePoint(shoppingCartProductId, property);
    property = property.replace(/\+/g, "%2B").replace(/\&/g, "%26");
    //后台添加
    $.ajax({url: '/cart/updateShoppingCartProduct',
        data: "id=" + shoppingCartProductId + "&" + property,
        type: 'POST',async:false,dataType: 'JSON',
        error: function (data) {
            validateSkip=false;
            resetShoppingCartProduct(shoppingCartProductId);
        },
        success: function (data) {
            if (data.message) {
                validateSkip=false;
                window.parent.alertWin('系统提示', data.message);
                resetShoppingCartProduct(shoppingCartProductId);
            }else if(data.reload=="true"){
                reloadAllShoppingCartProducts();
                calculateShoppingCartPrice();
            }else {
                //级联删除
                if (data != null && data.result != "[]" && data.result.length != 0) {
                    validateSkip=false;
                    var shoppingcartProducts = eval("(" + data.result + ")");
                    var deleteProducts = '';
                    for (var i = 0; i < shoppingcartProducts.length; i++) {
                        var shoppingCartProduct = shoppingcartProducts[i];
                        deleteProducts += shoppingCartProduct['productName'] + ',';
                        $('#scp_' + shoppingCartProduct['id']).remove();

                        $("#id_shoppingCartTable").find("tr").each(function (trindex, tritem) {
                            if ($(tritem).text().indexOf(shoppingCartProduct.skuCode) > -1  &&  shoppingCartProduct.isGift ==$(tritem).find("select[name='select4']").val()) {
                                var count = parseInt($(tritem).find(".productQuantity").find("input").val()) + parseInt(shoppingCartProduct.productQuantity);
                                $(tritem).find(".productQuantity").find("input").attr("value", count);
                                calculateDetailPrice(tritem.id.replace("scp_", ""));
                            }
                        });

                    }
                    window.parent.alertWin('系统提示', deleteProducts + '不满足规则被删除');
                }
                resetShoppingCartProduct(shoppingCartProductId);
                calculateShoppingCartPrice();
            }

        }
    });
}

//更新失败还原数据
function resetShoppingCartProduct(shoppingCartProductId) {
    $.get('/cart/getShoppingCartProduct/'+ shoppingCartProductId,
        function (data) {
            if (data != null && data.length != 0 && data != "[]") {
                var shoppingcartProduct = eval(data);
                $("#scp_" + shoppingcartProduct.id).find(".productType").find("select").val(shoppingcartProduct.productType);
                $("#scp_" + shoppingcartProduct.id).find(".salePrice").find("input").val(shoppingcartProduct.salePrice);
                $("#scp_" + shoppingcartProduct.id).find(".productQuantity").find("input").val(shoppingcartProduct.productQuantity);
                $("#scp_" + shoppingcartProduct.id).find(".point").find("input").val(shoppingcartProduct.point);

                calculateDetailPrice(shoppingcartProduct.id);
            }
        });
}

//[修改]
function toggleAddressList(reload) {
    //afterInitAddress('001');
    cancelEditAddressAndPhone(reload);
    $('#id_addressTitle_div').toggle();

//    $('#id_address_list_div').toggle();
    $('#id_address_list_div').parent().toggle();
    $("#id_address_list_table").datagrid('resize', {height: 120, width: '100%'})

    $("#id_addressButton_div").toggle();

    if ($("#customerType").val() != '1') {
        $("#id_select_contact_address").hide();
    } else {
        $("#id_select_contact_address").show();
    }
    window.parent.SetCwinHeight(frameElement);
}

//取消修改地址
function cancelEditAddressAndPhone(reload) {
    if (reload) {
        cleanAddressItem('001');
    }

    $("#id_address_phone_edit_table").hide();
    $("#id_address_phone_edit_table").find("input").each(function (index, item) {
        $(this).attr("value", "");
        $(this).attr("disabled", false);
    });
    $("#oldContactPhonediv").html('');
    $("#newContactPhonediv").html('');
    window.parent.SetCwinHeight(frameElement);
}

//设置默认地址
function updateContactMainAddress(contactId,addressId,auditState) {
    if(auditState==0){
        window.parent.alertWin('系统提示', "待审核的地址无法设为默认地址");
        return;
    }else if(auditState==1) {
        window.parent.alertWin('系统提示', "审核中的地址无法设为默认地址");
        return;
    }else if(auditState==3){
        window.parent.alertWin('系统提示', "未通过的地址无法设为默认地址");
        return;
    }
    $.get('/cart/updateContactMainAddress/' + contactId+ '/' +addressId,
        function (data) {
            if (data.message != null) {
                window.parent.alertWin('系统提示', data.message);
            } else {
                $("#id_address_list_table").datagrid({url: '/cart/getAddressList/' +  contactId + '/list'});
            }
        }
    );

}

//添加收货人手机HTML
function contactPhoneHtmlAdd() {
    var html = '';
    html += '<ul class="newAddressClass" style="margin:5px 0"><li><input type="hidden" name="phoneId" />';
    html += '<span  name="fphone1" style="display: inline-block;width:220px"><input type="text" name="phn4" size="20"  disabled=disabled   /></span>';
    html += '<span  name="fphone2" style="display:none;width:220px">';
    html += '<input  type="text"  name="phn1" size="5"  disabled=disabled  />-<input type="text"  name="phn2" size="10"  disabled=disabled  />-';
    html += '<input type="text"  name="phn3" size="8" disabled=disabled  /></span>';
    html += '<input name="phoneType" disabled=disabled  type="checkbox" onchange="changePhoneType(this)" />固话' ;
    html += '<input name="prmphn"  type="checkbox" style="margin:0 0 0 20px" onchange="updateMainPhone(this)" />默认电话';
    html += '<input name="backphn"  type="checkbox" style="margin:0 0 0 20px" onchange="changeBackPhone(this)" />备选电话' ;
    html +='<span style="margin:0 0 0 20px" name="auditState" ></span>'
    html +=    '</li></ul>';
    $("#oldContactPhonediv").append(html);
    var height = $("#oldContactPhonediv").height();
    if (height > 85) {
        $("#oldContactPhonediv").height(85);
        $("#oldContactPhonediv").css({
            "overflow-y": "auto",
            "overflow-x": "hidden"
        });
    }
}

//添加收货人手机HTML
function newContactPhoneAdd() {
    //潜客请升级为正式客户再添加电话号码
    if ($("#customerType").val() != '1') {
        window.parent.alertWin('系统提示', '潜客无法新增电话号码，请录入潜客地址并保存');
        return;
    }
    var html = '<ul class="newAddressClass" style="margin:5px 0 5px -16px"><li><a class="remove_phone_input removeBtn"  ></a><input type="hidden" name="phoneId" />';
    html += '<span  name="fphone1" style="display: inline-block;width:220px"><input class="numberOnly" type="text" name="phn4" size="20"  /></span>';
    html += '<span  name="fphone2" style="display:none;width:220px">';
    html += '<input class="numberOnly" type="text"  name="phn1" size="5"  />-<input class="numberOnly" type="text"  name="phn2" size="10"  />-';
    html += '<input class="numberOnly" type="text"  name="phn3" size="8" /></span>';
    html += '<input name="phoneType"   type="checkbox" onchange="changePhoneType(this)" />固话';
    html += '<input name="prmphn"   type="checkbox" style="margin:0 0 0 20px" onchange="updateMainPhone(this)" />默认电话';
    html += '<input name="backphn"   type="checkbox" style="margin:0 0 0 20px" onchange="changeBackPhone(this)" />备选电话';
    html +='<span style="margin:0 0 0 20px"></span>';
    html+= '</li></ul>';
    $("#newContactPhonediv").append(html);
    $(".newAddressClass").find("input[name='phn4']").validatebox({required:true,validType:'mobile'});
    $(".newAddressClass").find("input[name='phn1']").validatebox({validType: 'telephoneAreaCode'});
    $(".newAddressClass").find("input[name='phn2']").validatebox({required:true,validType:'telephone'});
    $(".newAddressClass").find("input[name='phn3']").validatebox({validType: 'telephoneExt'});
    if($("#approveManager").val() != 'true'){
        $("#newContactPhonediv").find(".newAddressClass").find("input[name='prmphn']").attr("disabled","disabled");
        $("#newContactPhonediv").find(".newAddressClass").find("input[name='backphn']").attr("disabled","disabled");
    }
}

function updateMainPhone(input) {
    if (!$(input).parent("li").find("input[name='phn4']").val() && !$(input).parent("li").find("input[name='phn2']").val()) {
        $(input).attr("checked", false);
        return;
    }
    var phoneId = $(input).parent("li").find("input[name='phoneId']").val();
    if(phoneId){
        var contactId = getAddressContactId();
        var  mainPhoneUrl ='/cart/updateContactMainPhone/'+contactId+"/"+phoneId+"/"+$("#customerType").val();
        var unSetBackupPhoneUrl ='/cart/unsetBackupPhone/'+phoneId+"/"+$("#customerType").val();
        $.ajax({url:mainPhoneUrl, type: 'GET', dataType: 'text',
            success: function (data) {
                if (data == 'false') {
                    window.parent.alertWin('系统提示', "更新客户主电话失败");
                    $(input).attr("checked", false);
                }else{
                    updateMainPhoneSubsequent(input)
                }
            }});
        if($(input).parent("li").find("input[name='backphn']").attr("checked") =='checked'){
            $.ajax({url: unSetBackupPhoneUrl,type: 'GET',dataType: 'text',
                success:function(data){
                    if (data == 'false') {
                        window.parent.alertWin('系统提示', "更新客户备选失败");
                    }
                }
            });
        }
    }else{
        updateMainPhoneSubsequent(input)
    }
}

function updateMainPhoneSubsequent(input){
    $("#addressPhone").find("input[name='prmphn']").each(function (index, item) {
        $(this).attr("checked", false);
    });
    $("#addressPhone").find("input[name='backphn']").each(function (index, item) {
        $(this).attr("disabled", false);
    });
    $(input).attr("checked", "checked");
    $(input).parent("li").find("input[name='backphn']").attr("checked",false);
    $(input).parent("li").find("input[name='backphn']").attr("disabled","disabled");
}

function unsetBackupPhone(phoneId,input){
    $.ajax({url: '/cart/unsetBackupPhone/'+phoneId+"/"+$("#customerType").val(),
        type: 'GET',dataType: 'text',
        success:function(data){
            if (data == 'false') {
                $(input).attr("checked", "checked");
                window.parent.alertWin('系统提示', "更新客户备选失败");
            }
        }
    });
}
function setBackupPhone(contactId,phoneId,input){
    $.ajax({url: '/cart/setBackupPhone/'+contactId+"/"+phoneId+"/"+$("#customerType").val(),
        type: 'GET',dataType: 'text',
        success:function(data){
            if (data == 'false') {
                window.parent.alertWin('系统提示', "更新客户备选失败");
            }else{
                $(input).attr("checked", "checked");
            }
        }
    });
}

function changeBackPhone(input){
    var  phone_Id = $(input).parent("li").find("input[name='phoneId']").val();
    if(!input.checked){
        if(phone_Id){
            unsetBackupPhone(phone_Id,input);
        }
        return;
    }
    if (!$(input).parent("li").find("input[name='phn4']").val() && !$(input).parent("li").find("input[name='phn2']").val()) {
        $(input).attr("checked", false);
        return;
    }

    $(input).attr("checked",false);
    var count =0
    $("#addressPhone").find("input[name='backphn']").each(function (index, item) {
        if (this.checked) {
            count++;
        }
    });
    var add = true;
    if(count >1 ){
        var unSelect =  $("#addressPhone").find("input[name='backphn'][checked='checked']").eq(0);
        var old_phone_id =unSelect.parent("li").find("input[name='phoneId']").val();
        if(old_phone_id){
            $.ajax({url: '/cart/unsetBackupPhone/'+old_phone_id+"/"+$("#customerType").val(),
                type: 'GET',dataType: 'text',async:false,
                success:function(data){
                    if (data == 'false') {
                        window.parent.alertWin('系统提示', "更新客户备选失败");
                        add = false;
                    }else{
                        unSelect.attr("checked", false);
                    }
                }
            });
        }else{
            unSelect.attr("checked", false);
        }
    }
    if(add){
        if(phone_Id){
            var contactId = getAddressContactId();
            setBackupPhone(contactId,phone_Id,input);
        } else{
            $(input).attr("checked", "checked");
        }
    }

}

function changePhoneType(input) {
    if (input.checked) {
        $(input).parent("li").find("span").eq(0).css("display", "none");
        $(input).parent("li").find("span").eq(1).css("display", "inline-block");
    } else {
        $(input).parent("li").find("span").eq(0).css("display", "inline-block");
        $(input).parent("li").find("span").eq(1).css("display", "none");
    }
}

//修改地址信息
function updateContactAddress(rowIndex) {
    var row = $('#id_address_list_table').datagrid('getRows')[rowIndex];

    cancelEditAddressAndPhone(false);
    potentialContactEdit(row.contactid);
    contactPhoneAdd(row.contactid);
    $("#id_address_phone_edit_table").show();
    $("#id_addressId").attr("value", row.addressid);
    $("#id_isdefault").attr("value", row.isdefault);
    $("#id_addressContactId").attr("value", row.contactid);
    $("#id_addrtypid").attr("value", row.addrtypid);
    $("#zip001").attr("value", row.zip);

    var addressDesc = row.addressDesc;
    if(addressDesc && addressDesc.length<=12){
        $("#id_address_desc").attr("value", addressDesc);
        $("#id_address_desc_hide").attr("value", addressDesc);
    }else{
        var temp = addressDesc.substr(0,12);
        for(var i=0;i<addressDesc.length-12;i++){
            temp +="*";
        }
        $("#id_address_desc").attr("value", temp);
        $("#id_address_desc_hide").attr("value", temp);
    }

    addressInit('001', row.state, row.cityId, row.countyId, row.areaid);

}

//潜客地址处理
function potentialContactEdit(contactId) {
    if ('1' == $("#customerType").val()) {
        $("#id_customer_code_tr").hide();
        $("#id_customer_name_tr").hide();

    } else {
        if ($("#h_provid").val()) {
            addressInit('001', $("#h_provid").val(), $("#h_cityid").val(), null, null);
        }
        $("#id_customer_code_tr").show();
        $("#id_customer_name_tr").show();
        $("#id_customer_name_input").attr("value", $("#name").val());
        $("#id_customer_code_td").html(contactId);
        $('input[name="cartGender"]').eq(0).val(1);
        $('input[name="cartGender"]').eq(1).val(2);
        if ('1' == $('input[name="sex"]:checked').val()) {
            $('input[name="cartGender"][value="1"]').attr("checked", "checked");
        } else {
            $('input[name="cartGender"][value="2"]').attr("checked", "checked");
        }
    }
}
//验证地址和手机
function    validateAddressPhoneBaseInfo() {
    if ($("#customerType").val() != '1') {
        if (!$("#id_customer_name_input").val()) {
            window.parent.alertWin('系统提示', "请填写客户姓名");
            return;
        }
    }
    if (!$('#id_address_desc').val() || !$('#province001').combobox('getValue') || !$('#cityId001').combobox('getValue')
        || !$('#countyId001').combobox('getValue') || !$('#areaId001').combobox('getValue')) {
        window.parent.alertWin('系统提示', "请填写完整的收货地址");
        return;
    }
    if (!$("#zip001").val()) {
        window.parent.alertWin('系统提示', "请填写邮政编码");
        return;
    }
    if ($("#zip001").val().length !=6) {
        window.parent.alertWin('系统提示', "请填写正确的邮政编码");
        return;
    }
    var count = 0;
    var phoneValidate = true;
    var mainPhoneValidate = true;
    $("#id_address_phone_edit_table").find("input[name='phoneType']").each(function (index, item) {
        if (this.checked) {
            if ($(this).parent("li").find("input[name='phn2']").val()) {
                count += 1;
                if ($(this).parent("li").find("input[name='phn2']").val().indexOf("*") == -1) {
                    if ($(this).parent("li").find("input[name='phn2']").val().length > 8 || $(this).parent("li").find("input[name='phn2']").val() < 7) {
                        phoneValidate = false;
                    }
                }
            }else{
                if ($(this).parent("li").find("input[name='prmphn']").attr("checked") =="checked") {
                    mainPhoneValidate = false;
                }
            }
        } else {
            if ($(this).parent("li").find("input[name='phn4']").val()) {
                count += 1;
                if ($(this).parent("li").find("input[name='phn4']").val().indexOf("*") == -1) {
                    var re = /^1[3|4|5|8|7]\d{9}$/
                    var phone = $(this).parent("li").find("input[name='phn4']").val();
                    if (!re.test(phone)) {
                        phoneValidate = false;
                    }
                }
            }else{
                if ($(this).parent("li").find("input[name='prmphn']").attr("checked") =="checked") {
                    mainPhoneValidate = false;
                }
            }
        }
    });
    if(!mainPhoneValidate){
        window.parent.alertWin('系统提示', "电话为空时不能选择为主要电话");
        return;
    }
    if (!phoneValidate) {
        window.parent.alertWin('系统提示', "输入电话号码错误");
        return;
    }
    if (count == 0) {
        window.parent.alertWin('系统提示', "没有新增电话");
        return;
    }
    if (count == 1) {
        $("#cart_phone_frameId").val(frameElement.id);
        window.parent.popWin(frameElement.id,'cart_onePhone_confirm',{title: '手机号码确认'});
        return;
    }
    validateAddressPhoneContactType();
}

function saveOnePhoneConfirm(){
    window.parent.closeWin('cart_onePhone_confirm');
    validateAddressPhoneContactType();
}
function closeOnePhoneConfirm(){
    window.parent.closeWin('cart_onePhone_confirm');
}

//保存地址
function validateAddressPhoneContactType(){
    if ($("#customerType").val() == '1') {
        validateAddressPhoneContactOrder();
    } else {
        updateCustomer();
    }
    window.parent.SetCwinHeight(frameElement);
}
//验证是否有未出库订单
function validateAddressPhoneContactOrder() {
    if($('#id_addressId').val()){
        $.post('/cart/validateContactAddressAndPhone',
            { addressid: $('#id_addressId').val() },
            function (data) {
                if (data != null && data.length != 0 && data != "[]") {
                    var orders = {};
                    orders.total = data.length;
                    orders.rows = data;
                    showRelatedOrders(orders);
                } else {
                    validatePhoneRepeat(null);
                }
            }
        );
    }else{
        validatePhoneRepeat(null);
    }

}

function openOrder(orderId){
    var url='myorder/orderDetails/get/'+orderId+'?activable=false';
    window.parent.addTab('myorder_'+orderId,'订单：'+orderId,'false',url);
}


function validatePhoneRepeat(windowId){
    if(windowId){
        window.parent.closeWin('id_related_' + windowId);
    }
    var phoneStr = getPhoneStr();
    $.post("/cart/checkRepeatPhone",{contactId:getAddressContactId(),phoneStr:phoneStr},
        function (data) {
            if (data.result == 1) {
                window.parent.alertWin('系统提示', '已关联本客户ID！');
            } else if (data.result == 2) {
                $("#cart_popWinContactId").html(data.data);
                $("#cart_phoneBind_frameId").val(frameElement.id);
                window.parent.popWin(frameElement.id,'cart_phoneBindOne',{title: '客户新增电话'});
            } else if(data.result ==3) {
                $('#cart_phoneBindMulti_frameId').val(frameElement.id);
                window.parent.popWin(frameElement.id,'cart_phoneBindMulti',{title: '客户新增电话'});
                $("#cart_multiContactTable",window.parent.document).datagrid({
                    height: 200,
                    fitColumns:true,
                    url:'/cart/findContactByPhone',
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
                    pagination:true,
                    queryParams: {
                        phoneStr:phoneStr
                    },
                    onDblClickRow:function(index,row){
                        if(row){
                            window.parent.addTab(row.customerId,"客户详情",false,'/contact/1/'+row.customerId);
                            window.parent.closeWin('cart_phoneBindMulti');
                        }
                    }
                });

            } else{
                saveOrUpdateContactAddress();
            }
        });
}

function openCurrentContact(){
    window.parent.addTab($("#cart_popWinContactId").html(),"客户详情",false,'/contact/1/'+$("#cart_popWinContactId").html());
    window.parent.closeWin('cart_phoneBindOne');
}
function savePhoneBindOne(){
    window.parent.closeWin('cart_phoneBindOne');
    saveOrUpdateContactAddress();
}
function closePhoneBindOne(){
    window.parent.closeWin('cart_phoneBindOne');
    cancelEditAddressAndPhone(false);
}
function savePhoneBindMulti(){
    window.parent.closeWin('cart_phoneBindMulti');
    saveOrUpdateContactAddress();
}
function closePhoneBindMulti(){
    window.parent.closeWin('cart_phoneBindMulti');
    cancelEditAddressAndPhone(false);
}

//保存或更新地址        $("#id_addressContactId").val()
function saveOrUpdateContactAddress() {
    var contactId = getAddressContactId()
    $.post('/cart/saveOrUpdateContactAddressAndPhone' ,
        {address: getCusAddress(), zip: $("#zip001").val(), state: $('#province001').combobox('getValue'),
            cityId: $('#cityId001').combobox('getValue'), countyId: $('#countyId001').combobox('getValue'), areaid: $('#areaId001').combobox('getValue'),
            addressid: $('#id_addressId').val(), phoneStr: getPhoneStr(), isdefault: $("#id_isdefault").val(),
            contactid: contactId, addrtypid: $("#id_addrtypid").val(),modify:getCusAddressModify() },
        function (data) {
            if (data.message != null && data.message.length != 0) {
                window.parent.alertWin('系统提示', data.message);
                $("#id_address_show_table").datagrid({url: '/cart/getAddressList/' + contactId+ '/single'});
                $("#id_address_list_table").datagrid({url: '/cart/getAddressList/' + contactId + '/list'});
            } else {
                $("#id_address_list_table").datagrid({url: '/cart/getAddressList/' + contactId + '/list'});
                $("#id_address_list_table").datagrid('resize', {height: 120, width: '100%'})

                $("#id_address_show_table").datagrid({url: "/cart/getAddressByAddressId/" + contactId + "/" + data.result});

                toggleAddressList(false);
            }
        }
    );
}

//获取客户地址
function getCusAddress(){
    if($("#id_address_desc_hide").val() && $("#id_address_desc_hide").val() ==$('#id_address_desc').val() ){
        return     '';
    }
    return $('#id_address_desc').val();
}
//判断客户地址是否修改
function getCusAddressModify(){
    if($("#id_address_desc_hide").val() && $("#id_address_desc_hide").val() ==$('#id_address_desc').val()   ) {
        return false;
    }
    return true;
}

//潜客升级
function updateCustomer() {
    var potentialContactCode = $("#customerId").val();
    $.post("/customer/mycustomer/address/add", {
        customerType: $("#customerType").val(),
        customerId: $("#customerId").val(),
        province: $("#province001").combobox('getValue'),
        cityId: $("#cityId001").combobox('getValue'),
        countyId: $("#countyId001").combobox('getValue'),
        areaId: $("#areaId001").combobox('getValue'),
        address: $("#id_address_desc").val(),
        zip: $("#zip001").val(),
        isDefault: '-1',
        phoneStr: getPhoneStr(),
        name: getChangeName(),
        sex: $('input[name="cartGender"]:checked').val()
    }, function (data) {
        $("#customerId").val(data.customerId);
        $("#customerType").val(data.customerType);
        $("#payment_contactid").val(data.customerId);
        if (data.result && data.result != '添加成功') {
            window.parent.alertWin('系统提示', data.result);
        }
        $("#shoppingcart_contactId").attr("value", data.customerId);
        $("#id_address_show_table").datagrid({url: '/cart/getAddressList/' + data.customerId + '/single'});
        $("#id_address_list_table").datagrid({url: '/cart/getAddressList/' + data.customerId + '/list'});

        toggleAddressList(false);

        //移到后台升级
        // updateShoppingCart(potentialContactCode, data.customerId);
        $("#id_customer_code_td").html(data.customerId);
    });
}

function getChangeName() {
    if ($("#id_customer_name_input").val()) {
        return  $("#id_customer_name_input").val()
    }
    return $("#name").val();
}

//潜客升级为正式客户，需要修改购物车中联系人
function updateShoppingCart(potentialContactCode, contactCode) {
    $.get("/cart/updateShoppingCartContact/" + potentialContactCode + "/" + contactCode,
        function (data) {
            if (data != null && data != '') {
                window.parent.alertWin('系统提示', data);
            }
        });
}

//选择其他收货人
function selectContactAddress() {
    window.parent.queryC(frameElement.id, 'getCustomerShow', 'shoppingCart');
}
//更新收货人
function getCustomerShow(customerId,contactName) {
    parent._source =""
    $.post( '/cart/check/contactName',
        {contactId:customerId},
        function (data) {
            if (data == 'auditContact') {
                $("#id_address_errorMessage").html("收货人正在审批中,无法选用");
                return;
            }
            if (data != 'false') {
                $("#id_address_errorMessage").html("收货人姓名正在审批中");
            }else{
                $("#id_address_errorMessage").html("");
            }

            if(customerId ==$("#shoppingcart_contactId").val()){
                $("#id_addressContact_span").html("当前客户");
            }else{
                if(contactName){
                    $("#id_addressContact_span").html(contactName);
                }else{
                    $("#id_addressContact_span").html("客户编号:"+customerId);
                }

            }
            $("#id_address_show_table").datagrid({url: "/cart/getAddressList/"+customerId+"/single"});
            $("#id_address_list_table").datagrid({url: "/cart/getAddressList/"+customerId+"/list"});
            $("#id_address_phone_edit_table").hide();
            window.parent.SetCwinHeight(frameElement);
        }
    );
}

//新增收货信息
function addContactAddress() {
    if ('1' == $("#customerType").val()) {
        cancelEditAddressAndPhone(true);
    } else {
        cancelEditAddressAndPhone(false);
    }
    $("#id_address_phone_edit_table").show();

    potentialContactEdit($("#customerId").val());
    contactPhoneAdd($("#customerId").val());

}

//新增或修改，手机号码连带出来
function contactPhoneAdd(contactId) {
    $.get('/cart/getPhones/' + contactId+"/"+$("#customerType").val(),
        function (data) {
            if (data.message != null) {
                window.parent.alertWin('系统提示', data.message);
            } else {
                if (data != null && data.result.length != 0 && data.result != "[]") {
                    var phones = eval(data.result);
                    for (var i = 0; i < phones.length; i++) {
                        var phone = phones[i];
                        contactPhoneHtmlAdd();
                        var addressPhone = $(".newAddressClass").eq(i);
                        setContactPhoneValue(addressPhone, phone);
                    }
                }
            }
            window.parent.SetCwinHeight(frameElement);
        }
    );
}

function setContactPhoneValue(addressPhone, phone) {
    addressPhone.find("input[name='phoneId']").val(phone.phoneid);
    if (phone.phonetypid == '4' || phone.phn2.length==11) {
        addressPhone.find("span").eq(0).css("display", "inline-block");
        addressPhone.find("span").eq(1).css("display", "none");
        if(phone.phoneMask){
            addressPhone.find("input[name='phn4']").val(phone.phoneMask);
        }else{
            addressPhone.find("input[name='phn4']").val(phone.phn2);
        }
    } else {
        addressPhone.find("span").eq(0).css("display", "none");
        addressPhone.find("span").eq(1).css("display", "inline-block");
        addressPhone.find("input[name='phn1']").val(phone.phn1);
        if(phone.phoneMask){
            addressPhone.find("input[name='phn2']").val(phone.phoneMask);
        }else{
            addressPhone.find("input[name='phn2']").val(phone.phn2);
        }
        addressPhone.find("input[name='phn3']").val(phone.phn3);
        addressPhone.find("input[name='phoneType']").attr("checked", "checked");
    }
    if(phone.prmphn=='Y'){
        addressPhone.find("input[name='prmphn']").attr("checked", "checked");
        addressPhone.find("input[name='backphn']").attr("disabled", "disabled");
    }else if(phone.prmphn=='B'){
        addressPhone.find("input[name='backphn']").attr("checked", "checked");
    }
    if(phone.state==0)  {
        addressPhone.find("span[name='auditState']").html("待审核");
        addressPhone.find("span[name='auditState']").addClass("exaa");
    }else if(phone.state==1){
        addressPhone.find("span[name='auditState']").html("审核中");
        addressPhone.find("span[name='auditState']").addClass("exaa");
    }else if(phone.state==3){
        addressPhone.find("span[name='auditState']").html("未通过");
        addressPhone.find("span[name='auditState']").addClass("exa");
    }else{
        addressPhone.find("span[name='auditState']").html("");
        addressPhone.find("span[name='auditState']").removeClass("exa");
        addressPhone.find("span[name='auditState']").removeClass("exaa");
    }
}
//获取电话号码
function getPhoneStr() {
    var phones = new Array();
    $("#newContactPhonediv").find("input[name='phoneType']").each(function (index, item) {
        var phone = {};
        phone['phoneid'] = $(this).parent("li").find("input[name='phoneId']").val();
        if (this.checked) {
            phone['phn1'] = $(this).parent("li").find("input[name='phn1']").val();
            phone['phn2'] = $(this).parent("li").find("input[name='phn2']").val();
            phone['phn3'] = $(this).parent("li").find("input[name='phn3']").val();
            phone['phonetypid'] = 1;
        } else {
            phone['phn2'] = $(this).parent("li").find("input[name='phn4']").val();
            phone['phonetypid'] = 4;
        }
        phone['contactid'] = getAddressContactId();
        if($(this).parent("li").find("input[name='prmphn']").attr("checked") =='checked'){
            phone['prmphn'] = 'Y';
        }else if($(this).parent("li").find("input[name='backphn']").attr("checked") =='checked'){
            phone['prmphn'] = 'B';
        }else{
            phone['prmphn'] = 'N';

        }
        phones.push(phone);
    });
    return JSON.stringify(phones);
}

//切换支付方式
function changePayType(value) {
    $("#order_mail_price").attr("disabled", false);
    if (value == 2) {
        if ($("#customerType").val() != '1') {
            window.parent.alertWin('系统提示', '潜客无法选择信用卡，请录入潜客地址');
            $("#tbPaytype").val(1);
            return;
        }

        if ($("#mailPricePermission").val() =='false') {
            $("#order_mail_price").attr("disabled", "disabled");
        }
        $("#id_shoppingCart_creditCards").show();
//        $("#selectCards-grid").datagrid('resize', {height:150, width: '100%'});
        var rows = $('#creditCards-grid').datagrid('getRows');
        if(rows==0)$("#creditCards-grid").datagrid('resize', { width: '100%',height:60});
        $("#creditCards-grid").datagrid('resize', { width: '100%'});
//        $("#creditCards-grid").datagrid('open');
        parent.SetCwinHeight(frameElement);

    }else{
        $("#id_shoppingCart_creditCards").hide();
    }

    if (value == 11) {
        if ($("#customerType").val() != '1') {
            window.parent.alertWin('系统提示', '潜客无法选择上门自提，请录入潜客地址');
            $("#tbPaytype").val(1);
            return;
        }
        $("#all_address_info").hide();
        $("#id_self_take_div").show();
        $("#id_self_take_table").datagrid({url: "/cart/getTakeAddressList/" + $("#shoppingcart_contactId").val()});
        $("#id_self_take_table").datagrid('resize', {height: 110, width: '100%'})
    } else {
        $("#all_address_info").show();
        $("#id_self_take_div").hide();
        $("#id_address_show_table").datagrid('resize', {height: 60, width: '100%'})
    }
    getOrderMessage();
    $('#isEms').removeAttr('checked');
    $('#emsRemark').val('');
    window.parent.SetCwinHeight(frameElement);
}

//获取短信模板并保存到界面
var smsTemplateList = {};
function getSmsTemple(theme) {
    $.ajax({
        url: '/cart/getSmsTemple',data: {'themeTemplate': theme},type: 'POST',async: false,
        success: function (data) {
            if (data.message != null) {
                window.parent.alertWin('系统提示', data.message);
                window.location.href = '/contact/1/' + $("#shoppingcart_contactId").val() + "?selectedTab=1";
            } else {
                $("#id_message_select").html("");
                $("#id_smsInfo").html("");
                smsTemplateList = eval("(" + data.result + ")");
                for (var i = 0; i < smsTemplateList.length; i++) {
                    var smsTemplate = smsTemplateList[i];
                    if ($("#tbPaytype").val() == 11) {
                        var row = $('#id_self_take_table').datagrid('getSelected');
                        var receiveAddress = row.receiveAddress;
                        if (receiveAddress.indexOf('北京') != -1 && smsTemplate.name.indexOf('北京') != -1) {
                            $("#id_message_select").append('<option selected="selected" value="' + smsTemplate.name + '">' + smsTemplate.name + '</option>');
                            $("#id_message_content").html(smsTemplate.content.replaceAll('\n', '<br>'));
                        } else if (receiveAddress.indexOf('北京') == -1 && smsTemplate.name.indexOf('北京') == -1) {
                            $("#id_message_select").append('<option selected="selected" value="' + smsTemplate.name + '">' + smsTemplate.name + '</option>');
                            $("#id_message_content").html(smsTemplate.content.replaceAll('\n', '<br>'));
                        }
                    } else {
                        if (i == 0) {
                            $("#id_message_select").append('<option selected="selected" value="' + smsTemplate.name + '">' + smsTemplate.name + '</option>');
                            $("#id_message_content").html(smsTemplate.content.replaceAll('\n', '<br>'));
                        } else {
                            $("#id_message_select").append('<option value="' + smsTemplate.name + '">' + smsTemplate.name + '</option>');
                        }
                    }
                }
                $("#pay_type_window").window('open');
                getCellPhones();
            }
        },
        error: function () {
            window.parent.alertWin('系统提示', "获取短信模版失败");
            window.location.href = '/contact/1/' + $("#shoppingcart_contactId").val() + "?selectedTab=1";
        }
    });
}

//修改短信模板
function changeSelectSmsTemple(value) {
    for (var i = 0; i < smsTemplateList.length; i++) {
        var smsTemplate = smsTemplateList[i];
        if (smsTemplate.name == value) {
            $("#id_message_content").html(smsTemplate.content.replaceAll('\n', '<br>'));
        }
    }
}

//获取订购人手机列表
function getCellPhones() {
    $.get('/cart/getCellPhones/' + $("#shoppingcart_contactId").val(),
        function (data) {
            if (data.message != null) {
                window.parent.alertWin('系统提示', data.message);
            } else {
                if (data != null && data.result.length != 0 && data.result != "[]")
                    var phones = eval("(" + data.result + ")");
                $("#id_message_phone_select").html("");
                for (var index in phones) {
                    var phone = phones[index];
                    $("#id_message_phone_select").append('<option value="' + phone['phone'] + '" >' + phone['phoneStr'] + '</option>');
                }
            }
        }
    );
}

//发送短信
function sendMessage() {
    $("#pay_type_window").window('close');
    for (var i = 0; i < smsTemplateList.length; i++) {
        var smsTemplate = smsTemplateList[i];
        if (smsTemplate.name == $("#id_message_select").val()) {
            var showMessage = false;
            if ($("#send_message_check").attr("checked") == "checked") {
                showMessage = true;
            }
            var phone = new Object();
            phone['mobile'] = $("#id_message_phone_select").val();
            phone['appContent'] = smsTemplate.content;
            phone['contactId'] = $("#shoppingcart_contactId").val();
            phone['smsName'] = smsTemplate.themeTemplate;
            phone['orderIds'] = $("#newCreate_orderId").val();
            phone['leadId'] = $("#leadId").val();
            var phoneStr = JSON.stringify(phone);
            sendSmsViaOrder(phoneStr, showMessage);
        }
    }
}

String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
};

//备注处理
function showAlreadyInputWord() {
    if ($("#id_cart_note").val()) {
        if ($("#id_cart_note").val().length > 30) {
            $("#id_input_words").css("color", "red");
        } else {
            $("#id_input_words").css("color", "black");
        }
        $("#id_input_words").html($("#id_cart_note").val().length);
    } else {
        $("#id_input_words").html(0);
    }

}

//去结算
function toSettleAccount() {
    if(!validateSkip){
        validateSkip = true;
        return;
    }
    if (!$("#leadId").val()) {
        window.parent.alertWin('系统提示', "坐席无法针对该客户下单");
        return;
    }
    $.get('/cart/validateShoppingCart/' + $("#shoppingcart_id").val(),
        function (data) {
            if (data != null) {
                window.parent.alertWin('系统提示', data);
            } else {
                $("#cart_tabs").tabs("select", 1);
                window.parent.SetCwinHeight(frameElement);
            }
        }
    );
    $.get('/cart/getMailPricePermission',
        function (data) {
            $("#mailPricePermission").val(data);
        }
    );

    getSynchOrderMessage();
}

//返回订单详情页
function returnToOrder() {
    $.get('/cart/validateShoppingCart/' + $("#shoppingcart_id").val(),
        function (data) {
            if (data != null) {
                window.parent.alertWin('系统提示', data);
            } else {
                var batchId=$("#input_batchId").val();
                window.location.href = '/myorder/orderDetails/get/' + $("#shoppingcart_order_id").val() + '?cartId=' + $("#shoppingcart_id").val() + '&activable=true&batchId='+batchId;
            }
        }
    );
}

//取消购物车
function cancelShoppingCart() {
    $.get('/cart/deleteShoppingCart/' + $("#shoppingcart_contactId").val() + '/ORDER',
        function (data) {
            if (data != null) {
                window.parent.alertWin('系统提示', data);
            } else {
                var batchId=$("#input_batchId").val();
                window.location.href = '/myorder/orderDetails/get/' + $("#shoppingcart_order_id").val() + '?activable=true&batchId='+batchId;
            }
        }
    );
}

//返回购物车
function toShoppingCart() {
    $("#cart_tabs").tabs("select", 0);
}

//验证订单
function validateCreateOrder() {
    var  productCheck = false;
    $.ajax({
        url:'/cart/validateShoppingCart/' + $("#shoppingcart_id").val(),
        type: 'GET',
        dataType: 'JSON',
        async:false,
        success: function (data) {
            if (data != null) {
                window.parent.alertWin('系统提示', data);
            } else{
                productCheck = true;
            }
        }});
    if(!productCheck){
        return;
    }


    if (!$("#leadId").val()) {
        window.parent.alertWin('系统提示', "坐席无法针对该客户下单");
        return false;
    }

    var addressId = getAddressId();
    if (!addressId) {
        window.parent.alertWin('系统提示', "请录入收件人地址信息");
        return false;
    }

    var addressAudltState = getAddressAudltState();
    if(1 ==addressAudltState || 3 ==addressAudltState){
        window.parent.alertWin('系统提示', "审核中或未通过的地址无法下单");
        return false;
    }
    return true;
}

//非信用卡支付返回null
function getCreditLastStatus(){
    if ($("#tbPaytype").val() != '2') {
        return;
    }
    var lastStatus = 0; //没有分期数传0
    var rows = $('#creditCards-grid').datagrid('getRows');
    if (rows && rows.length > 0)
    {
        for(var i = 0; i < rows.length; i++){
            $("#creditCards-grid").datagrid('endEdit', i);
            if(["001","002","011","014"].indexOf($(rows[i]).attr("type")) > -1){
                continue;
            } else {
                lastStatus = $(rows[i]).attr("lastStatus");
                break;
            }
        }
    }
    return lastStatus;
}

function validCreditCards() {
    var ccRow = null;
    var idRow = null;
    var lastStatus = 1;
    var rows = $('#creditCards-grid').datagrid('getRows');
    if (!rows || rows.length < 1){
        window.parent.alertWin('系统提示', "请选择信用卡及其证件记录!");
        return [];
    }
    for(var i = 0; i < rows.length; i++){
        $("#creditCards-grid").datagrid('endEdit', i);
        if(["001","002","011","014"].indexOf($(rows[i]).attr("type")) > -1){
            idRow = rows[i];
        } else {
            if(ccRow) {
                window.parent.alertWin('系统提示',"最多只能使用一张信用卡");
                return [];
            } else {
                ccRow = rows[i];
                lastStatus = $(ccRow).attr("lastStatus");
            }
        }
    }
    if(ccRow == null || idRow == null){
        window.parent.alertWin('系统提示', "至少必须有一张信用卡与一个身份证件");
        return [];
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

    if(!lastStatus){
        lastStatus = 1;
    }
    var amortisations = $(ccRow).attr("amortisations");
    if(amortisations.length == 0){
        window.parent.alertWin('系统提示', "一张信用卡设置需要设置相应分期数");
        return [];
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
                return [];
            }
        } else {
            window.parent.alertWin('系统提示', "当前信用卡分期数不正确");
            return [];
        }
    }

    if($(ccRow).attr("showextracode") == "0"){
        return [ccRow, idRow, lastStatus];
    }else if(!$(ccRow).attr("extraCode")) {
        window.parent.alertWin('系统提示', "请输入信用卡附加码!");
        return [];
    }

    //检查收货地址和付款人是否一致
    if(rows.length > 0){
        var contactId = $(rows[0]).attr("contactId");
        if(contactId != getAddressContactId()){
            window.parent.alertWin('系统提示',"信用卡订单付款人与收货人必须一致");
            return [];
        }
    }
    return [ccRow, idRow, lastStatus];
}

//创建订单
function createOrder() {
    var validateResult = validateCreateOrder();
    if (!validateResult) {
        return;
    }
    var attrs ={};
    if( $("#tbPaytype").val()=='2'){
        attrs = validCreditCards();
        if(attrs.length == 0){
            return;
        }
    }

    if ($("#btn_submit_info").val() != 'true') {
        return;
    }
    $("#btn_submit_info").val('false');
    $.post('/cart/createOrder',
        {   shoppingCartId: $("#shoppingcart_id").val(),
            invoicetitle: $("#invoicetitle").val(), addressId: getAddressId(), ordertype: $("#id_order_type").val(),
            paytype: $("#tbPaytype").val(), mailprice: $("#defaultMailPrice").val(),
            freight: $("#order_mail_price").val(), note: $("#id_cart_note").val(),
            leadId: $("#leadId").val(),
            cardnumber:getCreditId(attrs),
            laststatus:getLastStatus(attrs),
            cardtype:getCreditCardType(attrs),
            extraCode: $("#tbExtraCode").val()
        },
        function (data) {
            $("#btn_submit_info").val('true');
            if (data.message != null) {
                window.parent.alertWin('系统提示', data.message);
            } else {
                afterCreateOrder(data.result);
                $("#newCreate_orderId").val(data.result);
                //判断是否信用卡支付
                var paytype = $("#tbPaytype").val();
                switch (paytype) {
                    case "2": {
                        //audit :true 表示不需要审批
                        if('true' == data.audit){
                            payOrder(data.result,attrs[0],attrs[1],attrs[2]);
                        }else{
                            creditCardMatchDelivery(data.result,paytype,attrs[0].bankName);
                        }
                    }
                        break;
                    default:{
                        var message = getdeliverMessage(paytype,data.audit);
                        matchDelivery(data.result, paytype,message);
                    }
                        break;
                }
            }
        }
    );
}
function creditCardMatchDelivery(orderId, paytype,bankName){
    $.post('/checkout/cc/canOnlinePay',{bankName: bankName },
        function (data) {
            if(data){
                matchDelivery(orderId, paytype,'订单提交成功，等待主管审批；审批通过后请至"我的订单-待付款订单"进行在线索权');
            }else{
                matchDelivery(orderId, paytype,"订单提交成功，等待主管审批通过及信用卡线下索权");
            }
        }
    );
}

function  getdeliverMessage(paytype,audit){
    switch (paytype) {
        case "1":{
            if('true' == audit)        return "订单提交成功，等待仓库发货，货到付款";
            else  return "订单提交成功，等待主管审批通过后仓库发货，货到付款";
        }
        case "12":{
            if('true' == audit)        return "订单提交成功，等待客户邮局汇款";
            else  return "订单提交成功，等待主管审批通过及客户邮局汇款";
        }
        case "13":{
            if('true' == audit)        return "订单提交成功，等待客户银行转账";
            else  return "订单提交成功，等待主管审批通过及客户银行转账";
        }
        case "11":{
            if('true' == audit)        return "订单提交成功，等待客户上门自提";
            else  return "订单提交成功，等待主管审批通过后客户上门自提";
        }
        /* case "2":{
         if('true' == audit)        return "订单提交成功，等待银行与客户确认支付信息";
         else  return "订单提交成功，等待主管审批通过后，银行与客户确认支付信息";
         }*/
        default: {
            return "";
        }
    }
}

function getCreditId(attrs){
    if(attrs.length > 0){
        return  $(attrs[0]).attr("cardId");
    }
    return '';
}

function getCreditCardType(attrs){
    if(attrs.length > 0){
        return  $(attrs[0]).attr("typeName");
    }
    return '';
}

function getLastStatus(attrs){
    if(attrs.length > 1){
        return attrs[2];
    }
    return '';
}

function afterCreateOrder(orderId){
    $.post("/cart/afterCreateOrder",
        { orderId : orderId,
            shoppingCartId : $("#shoppingcart_id").val() ,
            leadId: $("#leadId").val()
        },
        function(result){
            //window.parent.alertWin('系统提示', result);
        }
    );
}

function payOrder(orderId,ccRow,idRow,lastStatus){
    $.post("/checkout/cc/pay",
        {
            orderId : orderId,
            ccCardId: $(ccRow).attr("cardId"),
            idCardId: $(idRow).attr("cardId"),
            extraCode: $("#tbExtraCode").val(),
            lastStatus: lastStatus
        },
        function(result){
            if(result.indexOf("支付成功") > -1){
                matchDelivery($('#tbOrderId').val(), 2,"订单提交成功，等待信用卡线下索权");
            }else if(result.indexOf("索权成功") > -1){
                matchDelivery($('#tbOrderId').val(), 2,"订单提交成功，在线索权成功");
            } else {
                matchDelivery($('#tbOrderId').val(), 2,"订单提交成功，在线索权失败，原因:"+result);
            }
        },
        "text"
    );
}

//清空购物车页面
function cleanShoppingCart() {
    $("#id_shoppingCartTable").find("tr").each(function (trindex, tritem) {
        if ($(tritem).find(".isSelected").find("input").attr("checked")) {
            $(tritem).remove();
        }
    });
    $("#id_scmPromotonTable").find("tr").each(function (trindex, tritem) {
        if (trindex > 1) {
            $(tritem).remove();
        }
    });
    $("#tbPaytype").val("1");
    $("#all_address_info").show();
    $("#id_self_take_div").hide();
    $("#id_cart_note").attr("value", "");

}

/*================================================================================*/


/**
 * 匹配仓库承运商信息
 */
function matchDelivery(_orderId, _payType,message) {
    //清空显示
    $('#message').html('');
    $('#warehoueName').val('');
    $('#entityName').val('');
    $('#deliveryDays').val('');

    $('#orderId').val(_orderId);
    //只有货到付款才能指定EMS
    if (_payType == 1) {
        $('#isEms').attr('disabled', false);
        $('#emsRemark').attr('disabled', false);
    } else {
        $('#isEms').attr('disabled', true);
        $('#emsRemark').attr('disabled', true);
    }

    $.ajax({
        url: ctx + '/delivery/matchDelivery',
        type: 'POST',
        data: {'orderId': _orderId},
        success: function (_data) {
            if (eval(_data.success)) {
                var warehouseName = _data.warehouseName;
                var entityName = _data.entityName;
                var deliveryDays = _data.deliveryDays;

                $('#warehoueName').val(warehouseName);
                $('#entityName').val(entityName);
                $('#deliveryDays').val(deliveryDays);

                if(message){
                    $('#message').html(message);
                }
            } else {
                if(message){
                    $('#message').html(_data.message+"<br>"+message);
                }else{
                    $('#message').html(_data.message);
                }
            }

            $('#cart_delivery_frameId').val(frameElement.id);

            window.parent.popWin(frameElement.id, 'subOrder_COD', {
                title: '订单支付及配送信息',
                iconCls: '',
                cache: false,
                shadow: false,
                modal: true,
                closed: true,
                closable: false,
                minimizable: false,
                maximizable: false,
                collapsible: false
            });

            var isAuditing = eval(_data.isAuditing);
            console.log('isAuditing: ', isAuditing);
            if(isAuditing){
                $('#isEms').attr('disabled', true);
                window.parent.document.getElementById('isEms').disabled = true;
            }
        },
        error: function (msg) {
            window.parent.alertWin('提示', '会话超时或网络错误');
        }
    });

}

/**
 * 取消送货确认窗口
 */
function cancelDelivery() {
    window.parent.closeWin('subOrder_COD');
    var _payType = $('#tbPaytype').val();

    if (_payType == 11) {
        getSmsTemple('上门自提');
    } else if (_payType == 12) {
        getSmsTemple('邮局汇款');
    } else if (_payType == 13) {
        getSmsTemple('银行转账');
    } else {
        window.location.href = '/contact/1/' + $("#shoppingcart_contactId").val() + "?selectedTab=1";
    }
}

/**
 * 确认要EMS送货
 * @returns {Boolean}
 */
function confirmUpdate() {
    var _payType = $('#tbPaytype').val();
    var _orderId = $('#orderId').val();
    var _isEms = window.parent.document.getElementById('isEms').checked == true ? true : false;
    var _remark = window.parent.document.getElementById('emsRemark').value;

    if (_isEms && (null == _remark || '' == _remark)) {
        window.parent.document.getElementById('message').innerHTML = '申请EMS送货理由不能为空';
        return false;
    } else {
        window.parent.document.getElementById('message').innerHTML = '';
    }

    //如果是货到付款
    if (null != _payType && undefined != _payType && _payType == 1 && _isEms) {
        $.ajax({
            url: ctx + '/delivery/assignEms',
            type: 'POST',
            data: {'orderId': _orderId, 'remark': _remark},
            async: true,
            success: function (_data) {
                if (eval(_data.success)) {
                    window.parent.alertWin('提示', '指定EMS送货成功');
                    cancelDelivery();
                } else {
                    window.parent.alertWin('提示', _data.message);
                    cancelDelivery();
                }
            },
            error: function (msg) {
                window.parent.alertWin('提示', '会话超时或网络错误');
            }
        });
    } else {
        cancelDelivery();
    }
}