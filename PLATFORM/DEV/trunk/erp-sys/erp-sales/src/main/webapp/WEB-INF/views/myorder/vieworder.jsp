<%@include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <link href="${ctx}/static/style/orderDetails.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/static/js/myorder/orderDetails.js?${rnd}"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            init("${ctx}");
            initAddressTable("${order.address.addressId}");
            if ("${order.paytype}" == "2") {
                showCreditTable("${order.orderid}");
            }
            $("#o_getjifen").html(parseFloat("${order.totalprice}") / 100);
        });
    </script>
</head>
<body>
<div style=" padding: 15px">
    <div class="baseInfo">
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td>客户姓名</td>
                <td width="150"><input type="text" value="${contact.name}" disabled="disabled" /></td>
                <td>客户性别</td>
                <td>
                    <label><input type="radio" <c:if test="${contact.sex == '1'}">checked</c:if> disabled />男</label>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <label><input type="radio" <c:if test="${contact.sex == '2'}">checked</c:if> disabled />女</label>
                </td>
                <td>客户编号</td>
                <td><input type="text" value="${contact.contactid}" disabled /></td>
            </tr>
            <tr>
                <td>会员等级</td>
                <td><input type="text" value="${level}" disabled /></td>
                <td>可用积分</td>
                <td><input type="text" value="${contact.jifen}" disabled /></td>
                <td>绑定座席(组)</td>
                <td><input type="text" value="${order.grpid}" disabled /></td>
            </tr>
        </table>
    </div>
</div>
<div id="cart_tabs" style="height: auto">
<div class="goods_info">
<h3 class="goods_info_title">订单信息
    <div class="fr">
        <a onclick="showHistroy('./${order.orderid}/history/${order.contactid}')" class="history">历史处理记录</a>
    </div>
</h3>
<div class="order_info_body">
<h3>基本信息</h3>
<table class="order_baseinfo" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>订单编号</td>
        <td><input type="text" value="${order.orderid}" disabled />&nbsp;</td>
        <td>订单生成时间</td>
        <td><input type="text" value="${order.crdt}" disabled /></td>
        <td>订单生成座席</td>
        <td><input type="text" value="${order.grpid}" disabled /></td>
    </tr>
    <tr>
        <td>订单状态<br /></td>
        <td><input type="text" value="${order.status}" disabled /></td>
        <td>呼入媒体</td>
        <td><input type="text" value="${order.media}" disabled /></td>
        <td>父订单号<br /></td>
        <td><input type="text" value="${order.parentid}" disabled /></td>
    </tr>
    <tr>
        <td>订单配送状态<br /></td>
        <td><input type="text" value="" disabled /></td>
        <td>订单类型</td>
        <td><input type="text" value="${order.ordertype}" disabled /></td>
        <td>面单号</td>
        <td><input type="text" value="${order.mailid}" disabled /></td>
    </tr>
</table>

<h3 class="mt10">订单明细</h3>
<table class="pro" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <th scope="col">套装信息</th>
        <th scope="col">商品编号</th>
        <th scope="col" align="center" width="62px">商品图片</th>
        <th scope="col">商品名称</th>
        <th scope="col">规格</th>
        <th scope="col">价格</th>
        <th scope="col">数量</th>
        <th scope="col" align="left">销售方式</th>
    </tr>
    <c:set var="productTotalPrice" value="0" scope="page" />
    <c:set var="productTotalNum" value="0" scope="page" />
    <c:forEach var="detail" items="${!empty editDetails ? editDetails : order.orderdets}" varStatus="status">
        <c:set var="price" value="${!empty detail.upnum && detail.upnum != 0 ? detail.uprice : detail.sprice}" scope="page" />
        <c:set var="num" value="${!empty detail.upnum && detail.upnum != 0 ? detail.upnum : detail.spnum}" scope="page" />
        <c:set var="productTotalPrice" value="${productTotalPrice + price * num}" scope="page" />
        <c:set var="productTotalNum" value="${productTotalNum + num}" scope="page" />
        <tr>
            <td></td>
            <td>${detail.prodid}</td>
            <td><img src="images/cart/1.png" width="62" height="62" alt="商品图片" /></td>
            <td>${detail.prodname}</td>
            <td>
                <input id="producttype_${status.index}" class="easyui-combobox" disabled
                       data-options="valueField: 'recid', textField: 'dsc', url: '${ctx}/myorder/orderDetails/product/${detail.prodid}/type/${detail.producttype}',
									onLoadSuccess:function(){$('#producttype_${status.index}').combobox('setValue', ${detail.producttype});}" />
            </td>
            <td>¥${price}</td>
            <td>${num}</td>
            <td align="left">
                <select class="easyui-combobox" disabled>
                    <option value="1" <c:if test="${detail.soldwith == 1}">selected</c:if> >直接销售</option>
                    <option value="2" <c:if test="${detail.soldwith == 2}">selected</c:if> >搭销</option>
                    <option value="3" <c:if test="${detail.soldwith == 3}">selected</c:if> >赠品</option>
                </select>
            </td>
        </tr>
    </c:forEach>
</table>
<div class="cart_price">
    总共:&nbsp;&nbsp;<span id="producttotalnum">${productTotalNum}</span>件商品，
    合计:&nbsp;&nbsp;<span>¥</span><span id="producttotalprice">${productTotalPrice}</span>元
</div>

<!-- 信用卡 -->
<div class="way">
    <select class="easyui-combobox" disabled>
        <option <c:if test="${order.paytype == 1}">selected</c:if> value="1">货到付款</option>
        <option <c:if test="${order.paytype == 2}">selected</c:if> value="2">信用卡</option>
        <option <c:if test="${order.paytype == 3}">selected</c:if> value="3">邮局汇款</option>
        <option <c:if test="${order.paytype == 4}">selected</c:if> value="4">银行转账</option>
        <option <c:if test="${order.paytype == 5}">selected</c:if> value="5">上门自取</option>
    </select>
</div>
<div id="id_credit_div" class="easyui-panel" style="height: 110px"
     data-options="closable:true,collapsible:true,minimizable:true,maximizable:true,closed:true">
    <h3>信用卡信息
        <c:if test="${order.confirm != 1}">
        </c:if>
    </h3>
    <table id="id_credit_table" class="easyui-datagrid"></table>
    <div id="id_credit_list_div" class="easyui-panel" style="height: 110px"
         data-options="closable:true,collapsible:true,minimizable:true,maximizable:true,closed:true">
        <table id="id_credit_list_table" class="easyui-datagrid"></table>
    </div>
    <div style="padding-top: 10px; padding-left: 30px">
        索权状态:&nbsp;&nbsp;&nbsp;<span>${order.confirm == 1 ? '成功' : (order.confirm == -1 ? '拒绝' : '未索权') }</span>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        索权时间:&nbsp;&nbsp;&nbsp;<span></span>
    </div>
</div>
<br />

<!-- 收货人信息 -->
<div id="all_address_info">
    <h3>收货人信息
    </h3>
    <table id="id_address_show_table" class="easyui-datagrid"></table>
    <div id="id_address_list_div" class="easyui-panel" style="height: 110px"
         data-options="closable:true,collapsible:true,minimizable:true,maximizable:true,closed:true">
        <table id="id_address_list_table" class="easyui-datagrid"></table>
    </div>
    <div class="orderInfo_btns" id="id_addressButton_div" style="display: none">
        <a class="add" href="javascript:void(0)" onclick="addReceipt()">新增收货信息</a>
        <a href="javascript:void(0)" onclick="selectContactAddress()">选择收货人</a>
    </div>
    <input id="input_select_index" type="hidden" />
    <table id="id_address_table" border="0" cellspacing="0" cellpadding="0" style="display: none">
        <tr>
            <td><div class="phoneNums-title">收货地址</div></td>
            <td></td>
            <td>
                <input id="input_province" /> &nbsp;
                <input id="input_city" />&nbsp;
                <input id="input_county" />&nbsp;
                <input id="input_area" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td><input id="input_addressDesc" type="text" size="80" class="easyui-validatebox" data-options="required:true" /></td>
        </tr>
        <tr>
            <td>
                <div class="phoneNums-title">邮政编码&nbsp;&nbsp;&nbsp;&nbsp;</div>
            </td>
            <td></td>
            <td><input id="input_zip" type="text" size="8" class="easyui-validatebox" data-options="required:true" />
            </td>
        </tr>
        <tr>
            <td valign="top"><div class="phoneNums-title">收货电话</div></td>
            <td width="20px" valign="top">
                <div class="phoneNums-title">
                    <a onclick="addPhone()" class="addBtn"><span></span></a>
                </div>
            </td>
            <td id="addressPhone1">
                <ul id="ul_phonelist" class="phoneNums-content"></ul>
            </td>
        </tr>
        <tr id="id_addAddressButton">
            <td></td>
            <td></td>
            <td><a class="phoneNums_btns" onclick="doSaveReceipt()">保存</a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a class="phoneNums_btns" onclick="cancelReceipt()">取消</a>
            </td>
        </tr>
    </table>
</div>
<br />

<!-- 发票信息 -->
<h3>发票信息 </h3>
<p><label>发票抬头<input type="text" value="${order.invoicetitle}" maxlength="255" disabled /></label></p>
<br />

<!-- 备注积分 -->
<h3>备注</h3>
<table border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">订单备注：</td>
        <td><textarea id="note" cols="50" rows="3" disabled>${order.note}</textarea></td>
    </tr>
</table>
<div>使用积分:&nbsp;&nbsp;
    <input id="jifen" type="text" class="easyui-validatebox" value="${empty order.jifen ? 0 : order.jifen}" size="8" disabled
           onchange="$('#jf').html($('#jifen').val());$('#jf1').html($('#jifen').val())"/>
    (抵扣:&nbsp;&nbsp;¥<span id="jf">${empty order.jifen ? 0 : order.jifen}</span>元)&nbsp;当前可用积分:&nbsp;&nbsp;${contact.jifen}
</div>
<br />

<!-- 结算 -->
<h3>结算</h3>
<p>
    货款金额 :&nbsp;&nbsp;¥${productTotalPrice} + 运费:&nbsp;&nbsp;¥
    <input id="mailprice" type="text" size="5" class="easyui-numberbox" value="${order.mailprice}" data-options="min:0,precision:0" disabled />
    - 使用积分:&nbsp;&nbsp;<span id="jf1">${empty order.jifen ? 0 : order.jifen}</span> = 应付总额:&nbsp;&nbsp;¥
    <span id="totalprice"></span> (获得积分:&nbsp;&nbsp;<span id="getjifen"></span>)
</p>
<br />

<!-- 配送信息 -->
<h3>配送</h3>
<p>
    预计出货仓库 <input type="text" value="" disabled />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    预计送货公司 <input type="text" value="" disabled />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    预计配送时间 <input type="text" value="" disabled />
</p>
<p>
    <input type="checkbox"  value="" disabled>指定EMS配送</input>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input id="remark" disabled="true" type="text" size="80" />
</p>
</div>
</div>
</div>

<div id="id_prociess_history_div" class="easyui-window" title="历史处理记录" style="width: 700px; height: 300px; padding: 10px; top: 120px"
     data-options="modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,closed:true,resizable:false,draggable:false">
    <table id="id_process_history_table"></table>
</div>

</body>
</html>