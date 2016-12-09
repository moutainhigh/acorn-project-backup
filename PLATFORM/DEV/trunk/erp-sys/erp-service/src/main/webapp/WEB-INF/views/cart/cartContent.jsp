<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<p ><div id="parentInfo"><label>父订单号：</label>&nbsp;<span id="parentInfo_id">12343534534</span>&nbsp;<a href="javascript:void(0);" tabindex="6" name="name_parentInfo_clear" id="parentInfo_clear" class="phoneNums_btns small_btns">清空</a></div></p>
<div id="cart_tabs" class="easyui-tabs" style=" height:auto" >
<div title="购物车">
    <input type="hidden" id="shoppingcart_id" value="${shoppingCartDto.id}">
    <input type="hidden" id="shoppingcart_contactId" value="${shoppingCartDto.contactId}">
    <input type="hidden" id="shoppingcart_order_id" value="${orderId}">
    <input type="hidden" id="allPoint" value="${allPoint}">
    <input type="hidden" id="defaultMailPrice" >
    <input type="hidden" id="mailPricePermission" >
    <input type="hidden" id="cart_cartType" value="${cart_cartType}" >
    <input type="hidden" id="leadId" >
    <input type="hidden" id="newCreate_orderId" >
    <input type="hidden" id="payment_contactid" value="${shoppingCartDto.contactId}">
    <input type="hidden" id="approveManager" value="${approveManager}">
    <input type="hidden" id="input_batchId" value="${batchId}">
    <input type="hidden" id="tbPayAmount">
    <input type="hidden" id="parentOrderId" value="${parentOrderId}">
    <input type="hidden" id="createOrderRight" value="${createOrderRight}">

    <div class="goods_info">
        <h3 class="goods_info_title">商品信息</h3>
        <div class="goods_info_body">
            <div class="pro_head">
                <table class="pro" link="pro" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <th style="width:5%;"><input type="checkbox" onclick="checkShoppingCartProduct(this)"  /></th>
                        <th style="width:10%;">商品编号</th>
                        <th style="width:15%;">商品名称</th>
                        <th style="width:15%;">商品规格</th>
                        <th style="width:10%;">商品价格</th>
                        <th style="width:10%;">数量</th>
                        <th style="width:10%;">使用积分</th>
                        <th style="width:10%;">结算金额</th>
                        <th style="width:10%;">销售方式</th>
                        <th style="width:5%;">操作</th>
                    </tr>
                </table>
            </div>
            <div class="pro_wrap">
                <table  id="id_shoppingCartTable" link="pro" class="pro_body" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <c:if test="${not empty shoppingCartDto && not empty shoppingCartDto.shoppingCartProductDtos }">
                        <c:forEach items="${shoppingCartDto.shoppingCartProductDtos}" var="scp">
                            <tr  id ="scp_${scp.id}">
                                <td width='5%' class="isSelected" ><input name="isSelected" type="checkbox" onchange="updateShoppingCartProduct('${scp.id }','isSelected='+this.checked)"   <c:if test="${scp.isSelected }"> checked="checked" </c:if> /></td>
                                <td width='10%'>${scp.skuCode }</td>
                                <td width='15%'>${scp.productName}</td>
                                <td width='15%' class="productType">
                                    <c:if test="${not empty scp.productType}">
                                        <select style="width:77px" name="select" onchange="updateShoppingCartProduct('${scp.id }','productType='+this.value)"  >
                                            <c:forEach items="${scp.productTypes}" var="proType">
                                                <option  value ="${proType.ncfreename}" <c:if test="${scp.productType ==proType.ncfreename }">selected="selected"</c:if> >${proType.ncfreename}</option>
                                            </c:forEach>
                                        </select></c:if>
                                </td>
                                <td width='10%' class="salePrice" >¥&nbsp;<input type="text" style="width: 40px" value="${scp.salePrice}" onchange="updateShoppingCartProduct('${scp.id }','salePrice='+this.value)"   <c:if test="${scp.isScmGift }">readonly="readonly"</c:if> /></td>
                                <td width='10%' class="productQuantity" ><input type="text" style="width: 30px" value="${scp.productQuantity}" onchange="updateShoppingCartProduct('${scp.id }','productQuantity='+this.value)"  <c:if test="${scp.isScmGift &&  scp.salePrice.unscaledValue() ==0 }">readonly="readonly"</c:if> /></td>
                                <td width='10%' class="point" ><input type="text" style="width: 30px" value="${scp.point}" onchange="updateShoppingCartProduct('${scp.id }','point='+this.value)"   /></td>
                                <td width='10%' class="detailPrice" ><span>¥&nbsp;${scp.detailPrice}</span></td>
                                <td width='10%' >
                                    <select style="width:77px" name="select4" onchange="updateShoppingCartProduct('${scp.id }','isGift='+this.value)"  <c:if test="${scp.isScmGift }">disabled="disabled"</c:if> >
                                        <c:if test="${!empty parentOrderId}">
                                        <c:forEach var="saleType" items="${orderSaleTypes}">
                                            <option value="${saleType.id.id}"  <c:if test="${scp.isGift ==saleType.id.id}">selected="selected"</c:if>  >${saleType.dsc}</option>
                                        </c:forEach>
                                        </c:if>
                                        <c:if test="${empty parentOrderId}">
                                            <option value="1"  <c:if test="${scp.isGift ==1 }">selected="selected"</c:if>  >直接销售</option>
                                            <option value="2"  <c:if test="${scp.isGift ==2 }">selected="selected"</c:if>  >搭销</option>
                                            <option value="3"  <c:if test="${scp.isGift ==3 }">selected="selected"</c:if>  >赠品</option>
                                            <option value="4"  <c:if test="${scp.isGift !=1 && scp.isGift !=2 && scp.isGift !=3}">selected="selected"</c:if>  ></option>
                                        </select>
                                        </c:if>
                                </td>
                                <td width='5%' align="center"><div style="width:50px" class="table-cell"><a class="cart_delBtn" href="javascript:void(0);" onclick="deleteShoppingCartProduct('${scp.id}')" ></a></div></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </table>
            </div>
            <table id="id_scmPromotonTable" class="gift mt10" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <th colspan="7" scope="col"><div>请从以下商品中选择赠品</div></th>
                </tr>
                <tr>
                    <td>规则</td>
                    <td>商品编号</td>
                    <td>商品名称</td>
                    <td>规格</td>
                    <td>商品价格</td>
                    <td align="center">操作</td>
                </tr>
            </table>
            <table id="id_insurePromptTable" class="gift mt10" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <th colspan="7" scope="col"><div>请从以下商品中选择保险<span  id="id_insure_message" style="color: red"></span></div></th>
                </tr>
                <tr>
                    <td>规则</td>
                    <td>商品编号</td>
                    <td>商品名称</td>
                    <td>规格</td>
                    <td>商品价格</td>
                    <td align="center">操作</td>
                </tr>
            </table>
            <div class="cart_price"> 总共：<span id="id_shoppingCartProductQuantity" >${shoppingCartPorductQuantity}</span>&nbsp;件商品，合计：<span id="id_shoppingCartProductPrice">¥${shoppingCartSalePrice}</span>&nbsp;元</div>
            <p align="right">
                <c:if test="${not empty orderId}">
                    <a class="phoneNums_btns fs16" style="width:100px;height:30px;line-height:30px;" href="javascript:void(0)" onclick="returnToOrder()"><span>提交订单</span></a>
                    <a class="phoneNums_btns fs16" style="width:100px;height:30px;line-height:30px;" href="javascript:void(0)" onclick="cancelShoppingCart()"><span>取消</span></a>
                </c:if>
                <c:if test="${empty orderId}">
                    <a class="phoneNums_btns fs16" style="width:100px;height:30px;line-height:30px;" href="javascript:void(0)" onclick="toSettleAccount()"><span>去结算</span></a>
                </c:if>
            </p>
        </div>
    </div>
</div>
<div title="完善订单信息">
    <div class="order_info">
        <h3 class="goods_info_title">订单信息</h3>
        <div class="order_info_body">
            <h3 class="underline">支付信息</h3>
            <div class="way" ><span>支付方式</span>
                <select id="tbPaytype" onchange="changePayType(this.value)">
                    <c:if test="${empty parentOrderId}">
                        <option value="1" selected >货到付款</option>
                        <option value="2" >信用卡</option>
                        <option value="12">邮局汇款</option>
                        <option value="13">银行转账</option>
                        <option value="11">上门自提</option>
                    </c:if>
                    <c:if test="${!empty parentOrderId}">
                        <option value="1" >货到付款</option>
                        <option value="2" >信用卡</option>
                        <option value="12" selected>邮局汇款</option>
                        <option value="13">银行转账</option>
                        <option value="11">上门自提</option>
                    </c:if>

                </select>
            </div>
            <div id="id_self_take_div" class="hide">
                <table  id="id_self_take_table"  > </table></div>
            <div id="all_address_info">
                <h3 style="display: inline">收货人信息<a id="id_show_address" class="buttonEdit" href="javaScript:void(0)" onclick="toggleAddressList(true)" title="修改"></a></h3>
                <span id="id_address_errorMessage" style="color: #ff0000"></span>
                <span id="id_deliveyDays_Message" style="color: #ff0000;float: right"></span>
                <table id="id_address_show_table" > </table>
                <div id="id_addressTitle_div" class="mt10 clearfix" style="display: none">
                    <span class=" ccgrid_t"> 收货人信息列表 [<span id="id_addressContact_span" style="font-weight: bold" class="">当前客户</span>] </span>
                </div>
                <div class="selectCards hide" >
                <div id="id_address_list_div" class=" list_div" >
                    <table  id="id_address_list_table"></table>
                </div>
                <div class="orderInfo_btns"  id="id_addressButton_div" style="display: none">
                    <a class="add" href="javascript:void(0)" onclick="addContactAddress()"><span></span>新增收货信息</a>
                    <a  id="id_select_contact_address"   href="javascript:void(0)" onclick="selectContactAddress()">选择收货人</a></div>
                <table border="0" cellspacing="0" cellpadding="0" class="newCD" style="width:100%;display: none;background: #fafafa;"id="id_address_phone_edit_table">
                    <tr id="id_customer_code_tr">
                        <td><div class="phoneNums-title">客户编号</div></td>
                        <td id="id_customer_code_td"></td>
                    </tr>
                    <tr  id="id_customer_name_tr">
                        <td><div class="phoneNums-title">客户姓名</div></td>
                        <td><input  id="id_customer_name_input" type="text" class="easyui-validatebox" data-options="required:true" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            客户性别<label><input name="cartGender" type="radio" value="1" checked="checked">男</label>&nbsp;&nbsp;&nbsp;&nbsp;
                            <label><input name="cartGender" type="radio" value="2">女</label></td>
                    </tr>
                    <tr>
                        <td style="width:70px"><div class="phoneNums-title" style="text-align: left;text-indent: 10px" >收货地址</div></td>
                        <td>
                            <div class="phoneNums-title"  style="width:auto;text-align: left">
                            <input id="id_addressId" type="hidden">
                            <input id="id_isdefault" type="hidden">
                            <input id="id_addressContactId" type="hidden">
                            <input id="id_addrtypid" type="hidden">
                            <jsp:include page="/common/address.jsp">
                                <jsp:param value="001" name="instId"/>
                                <jsp:param value="true" name="iszip" />
                            </jsp:include>
                                </div>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><div  class="phoneNums-title" style="width:auto"><input id="id_address_desc_hide" type="hidden" /><input id="id_address_desc" type="text" size="80" class="easyui-validatebox" data-options="required:true"   /></div></td>
                    </tr>
                    <tr>
                        <td><div class="phoneNums-title" style="text-align: left;text-indent: 10px">邮政编码</div></td>
                        <td><div class="phoneNums-title"><input id ="zip001"  type="text" size="8" maxlength="6" class="easyui-validatebox fl" data-options="required:true"  /></div></td>
                    </tr>
                    <tr>
                        <td><div  class="phoneNums-title" style="vertical-align: top;text-align: left;text-indent: 10px">收货电话<a href="javascript:void(0)" onclick="newContactPhoneAdd()" class="addBtn"><span></span></a></div></td>
                        <td id="addressPhone">
                            <div  class="phoneNums-title" style="width:auto;text-align: left">
                            <div id="oldContactPhonediv" style="width:520px"></div>
                            <div id="newContactPhonediv"></div>
                                </div>
                        </td>
                    </tr>
                    <%--<tr id="id_addAddressButton"><td></td><td valign="top"><a href="javascript:void(0)" onclick="newContactPhoneAdd()" class="addBtn"><span></span></a></td>--%>
                        <%--<td></td></tr>--%>
                    <tr><td></td>
                        <td><div  class="phoneNums-title" style="width:auto">
                            <a class="phoneNums_btns"  onclick="validateAddressPhoneBaseInfo()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="phoneNums_btns" href="javascrip:void(0)" onclick="cancelEditAddressAndPhone()">取消</a>
                        </div></td>
                    </tr>
                </table>
                </div>
            </div>
            <div id="id_shoppingCart_creditCards"  style="display: none" >
                <div class="creditCards">
                    <h3>信用卡信息<a id="modifyDCBtn" class="buttonEdit" href="javascript:void(0);" title="修改"></a></h3>
                    <table id="creditCards-grid" cellspacing="0" cellpadding="0" data-options=""></table>
                    <div class="grid-footer" id="creditCardMobile" style="display: none">  <label>信用卡绑定手机号码</label> &nbsp;
                        <input id="id_creditCardMobile_hidden" type="hidden" >
                        <input id="id_creditCardMobile" type="text" maxlength="255" >&nbsp;<span class="red">*</span>
                        <input id="tbExtraCode" type="hidden" maxlength="255" name="extraCode"></div>
                </div>
                <div id="id_creditTitle_div" class="mt10 clearfix" style="display: none">
                     <span class=" ccgrid_t"> 信用卡列表 [<span id="lblModifyContact" style="font-weight: bold"  class="">当前客户</span>] </span>
                     <span id="id_payer_errorMessage" style="color: #ff0000"></span>
                </div>
                <div id="selectCards-div" class="selectCards">
                    <div id="selectCards-list">
                        <div class="list_div " style="margin: 0">
                            <table id="selectCards-grid" cellspacing="0" cellpadding="0"></table>
                        </div>
                    </div>
                    <div class="orderInfo_btns"   >
                        <a id="lnkCardConfirm" href="javascript:void(0)">确认</a>
                        <a id="lnkCardNew" class="add" href="javascript:void(0)">新增卡信息</a>
                        <a id="lnkContact" href="javascript:void(0)">选择付款人</a>
                    </div>
                    <div id="card-container-div" class="card-container-div" style="display: none">
                       <table class="newCD" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><div class="phoneNums-title">类别</div></td>
                            <td>
                                <div class="phoneNums-title"><select class="u52 u52_text_sketch" id="tbTypeOptions" style="width:150px">
                                    <option selected="selected" value="">===请选择===</option>
                                    <option title="身份卡" value="0">身份卡</option>
                                    <option title="银行卡" value="1">信用卡</option>
                                </select> </div>
                            </td>
                            </tr>
                           <tr style="display: none">


                            <td><div id="lblType" class="phoneNums-title">证件类型</div></td>
                            <td>
                                <div  class="phoneNums-title" style="width:auto">
                                <select class="u52 u52_text_sketch" id="tbType" style="width:150px">
                                    <option selected="selected" value="">===请选择===</option>
                                    <c:if test="${not empty cardTypes }">
                                        <c:forEach items="${cardTypes}" var="cardType">
                                            <option title="${cardType.bankName}" value="${cardType.cardtypeid}" extra="${cardType.showextracode}">
                                                <c:choose>
                                                    <c:when test="${not empty cardType.bankName }">
                                                        ${cardType.bankName}-${cardType.cardname}
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${cardType.cardname}
                                                    </c:otherwise>
                                                </c:choose>
                                            </option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                                    </div>
                            </td>
                           </tr>
                           <tr style="display: none">
                            <td style="width:50px" accesskey="bank"><div id="lblBank" class="phoneNums-title">银行</div></td>
                            <td accesskey="bank">
                                <div  class="phoneNums-title" style="width:auto">
                                <select class="u59 u59_text_sketch" id="tbBank" style="width:150px">
                                    <option value="" selected="selected"></option>
                                    <c:if test="${not empty cardBanks }">
                                        <c:forEach items="${cardBanks}" var="bank">
                                            <option value="${bank}">${bank}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                                    </div>
                            </td>
                        </tr>
                        <tr  style="display: none">
                            <td><div id="lblCardNumber" class="phoneNums-title">证件号</div></td>
                            <td><div  class="phoneNums-title" style="width:auto"><input id="tbCardNumber" type="text" class="u62 u62_text_sketch easyui-validatebox" data-options="required:true" regular="['(\\d+', '用户名格式不正确!']" size="22"></div></td>
                        </tr>
                           <tr  style="display: none">
                               <td><div id="lblExtraCode" class="phoneNums-title">附加码</div></td>
                               <td><div  class="phoneNums-title" style="width:auto"><input id="tbExtraCode2" type="text" class="u62 u62_text_sketch easyui-validatebox" data-options="required:false" size="22"></div></td>
                           </tr>
                        <tr  style="display: none">
                            <td><div id="lblValidDate" class="phoneNums-title">有效期</div></td>
                            <td colspan="3">
                                <div  class="phoneNums-title" style="width:auto">
                                <div id="lblValidDate2">
                                    <input id="tbValidYear" class="easyui-numberbox" name="validYear" type="text" size="11" maxlength="4" />-
                                    <input id="tbValidMonth" class="easyui-numberbox" name="validMonth" min="1" max="12" type="text" size="7" maxlength="2" />
                                </div>
                                    </div>
                            </td>
                        </tr>
                        <tr >
                            <td></td>
                            <td colspan="5">
                                <div class="phoneNums-title" style="width:auto">
                                <input id="tbCardId" name="cardId" type="hidden" />
                                <a id="lnkCardSave" class="phoneNums_btns" href="javascript:void(0);">保存</a>
                                <a id="lnkCardCancel" class="phoneNums_btns" href="javascript:void(0);">取消</a>
                                    </div>
                            </td>
                        </tr>
                    </table>
                    </div>
                </div>

                <div style="margin-top: 20px;display: none" class="submitBtns">
                    <a id="lnkPay" href="javascript:void(0);">确认支付</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <a id="lnkCancel" href="javascript:void(0);">取消订单</a>
                </div>

            </div>
            <h3>发票信息</h3>
            <p ><label>发票抬头&nbsp;<input id="invoicetitle" type="text"  value="个人" maxlength="255" /></label>&nbsp;
                <label>发票内容&nbsp;<input  type="text" disabled value="产品明细" maxlength="255" /></label></p>
            <h3>其他</h3>
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td valign="top">订单备注：</td>
                    <td><textarea id="id_cart_note" cols="50" rows="3" maxlength="30"   onkeyup="showAlreadyInputWord()"  onkeydown="showAlreadyInputWord()" ></textarea></td>
                    <td valign="top">&nbsp;<i id="id_cart_note_info">注：订单备注中前30个<br>字会打印在配送面单上</i></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">已输入 <span id="id_input_words">0</span> 字</td>
                    <td>&nbsp;</td>
                </tr>
            </table>
            <h3>结算</h3>
            <p>贷款金额 <span id="id_scp_price"  class="att"></span>  +   运费&nbsp;
                <input id="order_mail_price" onchange="calculatePayPrice()"  style="width:50px" type="text"  value="0" maxlength="255" /> = 应付总额：
                <span id="id_pay_price"  class="att"></span> &nbsp;&nbsp;&nbsp;(&nbsp;获得积分：<span id="id_conpoint_get"  class="att"></span>&nbsp;)
                <span id="id_mailPriceChange_Message" style="color: red"></span>
            </p>
            <p>订单类型&nbsp;<select  id="id_order_type"></select><span id="id_orderTypeMessage" style="color: #ff0000;padding-left: 5px"></span></p>
            <p class="mt10"><a id="btnOrderSubmit" class="phoneNums_btns" href="javaScript:void(0)" onclick="createOrder()" style="width:100px;">提交订单</a>
                &nbsp;&nbsp;&nbsp;&nbsp;<a class="phoneNums_btns" style="width:100px;" href="javaScript:void(0)" onclick="toShoppingCart()">取消</a></p>
             <input type="hidden" id="btn_submit_info" value="true">
        </div>

    </div>
</div>
</div>

<div id="cart_onePhone_confirm" class="easyui-window"  data-options="top:50,modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true" style="width:400px;padding:10px;">
    <input id="cart_phone_frameId" type="hidden"/>
    <div class="c_content">
        <span class="popWinPhoneNum">只有1个联系电话号码，是否继续</span>
            <p class="mb10 mt10" align="center"><a class="window_btn" href="javascript:void(0);"  onclick="subCallback($('#cart_phone_frameId').val(),'saveOnePhoneConfirm')">确定</a>
            <a class="window_btn ml10" href="javascript:void(0);" onclick="subCallback($('#cart_phone_frameId').val(),'closeOnePhoneConfirm')">取消</a></p>
    </div>
</div>

<div id="subOrder_COD" title="送货确认" class="easyui-window popWin"  data-options="modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true,draggable:false" >
    <input id="orderId" type="hidden"/>
    <input id="cart_delivery_frameId" type="hidden"/>
    <div class="popWin_wrap">
        <table ><tbody>
        <tr>
            <td id="message" colspan="2" style="color:red;white-space:normal "><td>
        </tr>
        <tr>
            <td>预计出货仓库</td>
            <td><input id="warehoueName" readonly="readonly" class="readonly" type="text"  width="100%" /></td>

        </tr>
        <tr><td>预计送货公司</td><td><input id="entityName" readonly="readonly" class="readonly" type="text"  width="100%" /></td></tr>
        <tr><td>预计配送时间</td><td><input id="deliveryDays"  readonly="readonly" class="readonly" type="text"  width="100%"/></td></tr>
        <tr>
            <td><label><input id="isEms" name="isEms" type="checkbox"/>指定EMS送货</label></td>
            <td ><input id="emsRemark"  size="50"  class="readonly" type="text"  width="100%"  value=""/> </td>
        </tr>
        <tr><td colspan="2" align="center">
	        <a class="window_btn" onclick="subCallback($('#cart_delivery_frameId').val(),'confirmUpdate')" href="javascript:void(0)">确定</a>
	        <%-- <a class="window_btn ml10" onclick="subCallback($('#frameId').val(), 'cancelDelivery')" href="javascript:void(0)">取消</a> --%>
        </td></tr>
        </tbody></table></div>
</div>

<div id="pay_type_window" class="easyui-window popWin" title="短信发送"  data-options="top:490,modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true,draggable:false" >
    <div class="popWin_wrap" style="width:480px;" >
        <table border="0" cellspacing="0" cellpadding="0" >
            <tr>
                <td>客户编号</td>
                <td><input  readonly="readonly" class="readonly" type="text"  width="100%"  value="${customer.customerId}"/></td>
                <td> <div class="orderInfo_btns" style="display: none"><a href="javascript:void(0)" class="add">查询客户</a><a href="javascript:void(0)">查询订单</a></div></td>
            </tr>
            <tr>
                <td>发送手机</td>
                <td><select class="u13 u13_text_sketch"  id="id_message_phone_select"> </select></td>
                <td></td>
            </tr>
            <tr>
                <td>短信主题</td>
                <td colspan="2"><select class="u9 u9_text_sketch" id="id_message_select" onchange="changeSelectSmsTemple(this.value)"></select></td>
            </tr>
            <tr>
                <td style="vertical-align: top">短信内容
                </td>
                <td><div  class = "pay_type_window"  style="width: 292px;" id="id_message_content"></div></td>

                <td style="vertical-align: top">
                    <div class="mb10"><a style="width: 100px;height: 50px;vertical-align: middle;line-height: 50px;"
                            href="javascript:void(0)" onclick="sendMessage()" class="phoneNums_btns" id="u2">发送</a>
                    </div>
                    <p style="text-align:left;"><label class="fl"><input class="fl" type="checkbox" checked="checked" value="checkbox"
                                                              style="" id="send_message_check">
                        <span class="">提示发送状态</span></label></p></td>
            </tr>
        </table>
    </div>
</div>

<div id="cart_phoneBindOne" class="easyui-window" title="客户新增电话" data-options="top:50,modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true" style="width:400px;padding:10px;">
    <input id="cart_phoneBind_frameId" type="hidden"/>
    <div class="c_content">
        <span class="popWinPhoneNum"></span>已关联客户ID<a class="detailItem ml10" href="javascript:void(0);" onclick="subCallback($('#cart_phoneBind_frameId').val(),'openCurrentContact')" ><span id="cart_popWinContactId"></span></a>，是否新增？
        <p class="mb10 mt10" align="center"><a class="window_btn" href="javascript:void(0);"  onclick="subCallback($('#cart_phoneBind_frameId').val(),'savePhoneBindOne')">确定</a>
            <a class="window_btn ml10" href="javascript:void(0);" onclick="subCallback($('#cart_phoneBind_frameId').val(),'closePhoneBindOne')">取消</a></p>
    </div>
</div>

<div id="cart_phoneBindMulti" class="easyui-window" title="客户新增电话" data-options="modal:true,shadow:true,draggable:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true,top:100" style="width:700px;">
    <input id="cart_phoneBindMulti_frameId" type="hidden"/>
    <div class="c_content">
        <span class="popWinPhoneNum"></span>已关联多个客户ID，是否新增？
        <p class="mb10 mt10" align="center"><a class="window_btn" href="javascript:void(0);" onclick="subCallback($('#cart_phoneBindMulti_frameId').val(),'savePhoneBindMulti')" >确定</a>
            <a class="window_btn ml10" href="javascript:void(0);" onclick="subCallback($('#cart_phoneBindMulti_frameId').val(),'closePhoneBindMulti')" >取消</a></p>
    </div>
    <table id="cart_multiContactTable"></table>
</div>

<jsp:include page="../myorder/relatedOrders.jsp">
    <jsp:param name="id" value="shoppingCart"></jsp:param>
    <jsp:param name="callbackMethod" value="validatePhoneRepeat" />
</jsp:include>