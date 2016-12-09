<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
    <link href="/static/style/cc.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="/static/js/cart/cc.js"></script>
    <script type="text/javascript" src="/static/js/cart/cart.js"></script>
</head>
<body>



	<div style="padding: 20px">
		<div class="baseInfo">
			
                <form id="formAddCustomerInfo" action="/customer/mycustomer/add"
					method="post">
					<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
                            <td >客户编号</td>

                            <td ><input id="tbContactId" type="text" class="readonly" value="${contactId}" id="u97"></td>

                            <td>客户姓名</td>

                            <td ><input id="tbContactName" type="text" class="readonly" value="${contactName}" id="u95"></td>

							<td>订单编号</td>

							<td ><input id="tbOrderId" type="text" disabled="" class="readonly" value="${orderId}"></td>
						</tr>
                        <tr>
                            <td>付款人客户编号</td>

                            <td ><input id="tbPayerId" type="text" class="readonly" value="${contactId}"></td>

                            <td>付款人姓名</td>

                            <td ><input id="tbPayerName" type="text" class="readonly" value="${contactName}"></td>
							<td>订单金额</td>
							<td><input id="tbPayAmount" type="text" disabled="disabled" class="readonly" value="${totalPrice}">&nbsp;元</td>
                           
                        </tr>

					</table>
				</form>
			
		</div>
        <div class="receiptInfo">


            <div class="receiptInfo-title">收货信息</div>
            <table id="addresses-grid"  style="height: 150px" data-options="fitColumns:true">
                <thead>
                <tr>
                    <th data-options="field:'contactName',width:80">收货人</th>
                    <th data-options="field:'prefixAddress',width:80">收货地址</th>
                    <th data-options="field:'addressDesc',width:40">详细地址</th>
                    <th data-options="field:'zip',width:40">邮编</th>
                    <th data-options="field:'phone',width:100">联系电话</th>
                </tr>
                </thead>
                <c:if test="${not empty shippingAddresses }">
                    <tbody>
                    <c:forEach items="${shippingAddresses}" var="address">
                        <tr>
                            <td>${address.contactName}</td>
                            <td>${address.prefixAddress}</td>
                            <td><div onselectstart="return false;"><marquee direction="left" scrollamount="3">${address.addressDesc}</marquee></div></td>
                            <td>${address.zip}</td>
                            <td>${address.phone}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </c:if>
            </table>
        </div>

		<div class="creditCards">
			<div class="creditCards-title">
				信用卡信息&nbsp;&nbsp;&nbsp;&nbsp;<a id="modifyDCBtn" class="modifyDCBtn" href="javascript:void(0);"><span>【修改】</span></a>
			</div>
            <table id="creditCards-grid" cellspacing="0" cellpadding="0" data-options=""></table>
		</div>


		<div class="mt10 clearfix">
            <table class="fr" cellpadding="2" cellspacing="2">
                <tr>
                    <td>
                        <label>录入信用卡附加码</label> &nbsp;
                        <input id="tbExtraCode" type="text" maxlength="255" name="extraCode">
                    </td>
                    <td>
                        <div class="submitBtns">
                            <a id="lnkAuth" href="javascript:void(0)" class="ml10" style="padding:2px 5px; display: none">在线索权</a>
                        </div>
                    </td>
                </tr>
            </table>
		</div>


        <div id="selectCards-div" class="selectCards">
            <div id="selectCards-list">
                <div class="list_div">
                    <table id="selectCards-grid" cellspacing="0" cellpadding="0"></table>
                </div>
            </div>
            <div class="orderInfo_btns"   >
                <a id="lnkCardConfirm" href="javascript:void(0)">确认</a>
                <a  id="lnkCardNew" class="add" href="javascript:void(0)" onclick="addContactAddress()"><span></span>新增</a>
                <a id="lnkContact" href="javascript:void(0)">选择付款人</a>
            </div>
            <table class="newCD" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><div class="phoneNums-title">类别</div></td>
                    <td>
                        <select class="u52 u52_text_sketch" id="tbTypeOptions" style="width:150px">
                            <option selected="selected"></option>
                            <option title="身份卡" value="0">身份卡</option>
                            <option title="银行卡" value="1">信用卡</option>
                        </select>
                    </td>
                    <td><div class="phoneNums-title">证件类型</div></td>
                    <td>
                        <select class="u52 u52_text_sketch" id="tbType" style="width:150px">
                            <option selected="selected"></option>
                            <c:if test="${not empty cardTypes }">
                                <c:forEach items="${cardTypes}" var="cardType">
                                    <option title="${cardType.bankName}" value="${cardType.cardtypeid}">
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
                    </td>
                    <td style="width:50px" accesskey="bank">银行</td>
                    <td accesskey="bank">
                        <select class="u59 u59_text_sketch" id="tbBank" style="width:150px">
                            <option value="" selected="selected"></option>
                            <c:if test="${not empty cardBanks }">
                                <c:forEach items="${cardBanks}" var="bank">
                                    <option value="${bank}">${bank}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        证件号
                    </td>
                    <td>
                        <input id="tbCardNumber" type="text" class="u62 u62_text_sketch" >
                    </td>
                	<td>有效期</td>
                    <td colspan="3">
                        <input id="tbValidYear" class="easyui-numberbox" name="validYear" type="text" size="11" maxlength="4" />-
                        <input id="tbValidMonth" class="easyui-numberbox" max="12" min="1" name="validMonth"
                               type="text" size="7" maxlength="2" />
                    </td>
                </tr>
                <tr >
                	<td></td>
                    <td colspan="5">
                        <input id="tbCardId" name="cardId" type="hidden" />
                        <a id="lnkCardSave" class="phoneNums_btns" href="javascript:void(0);">保存</a>
                        <a id="lnkCardCancel" class="phoneNums_btns" href="javascript:void(0);">取消</a>
                    </td>
                </tr>
            </table>
        </div>

		<div style="margin-top: 20px" class="submitBtns">
			<a id="lnkPay" href="javascript:void(0);">确认支付</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a id="lnkCancel" href="javascript:void(0);">取消订单</a>
		</div>


	</div>


	<div id="subOrder_COD" title="送货确认" class="easyui-window popWin"  data-options="modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true,draggable:false" >
	    <input id="orderId" type="hidden"/>
	    <input id="cart_delivery_frameId" type="hidden"/>
	    <input id="tbPaytype" type="hidden" value="2"/>
	    <div class="popWin_wrap">
	        <table ><tbody>
	        <tr>
	            <td id="message" colspan="2" style="color:red;"><td>
	        </tr>
	        <tr>
	            <td>预计出货仓库</td>
	            <td><input id="warehoueName" readonly="readonly" class="readonly" type="text"  width="100%" /></td>
	
	        </tr>
	        <tr><td>预计送货公司</td><td><input id="entityName" readonly="readonly" class="readonly" type="text"  width="100%" /></td></tr>
	        <tr><td>预计配送时间</td><td><input id="deliveryDays"  readonly="readonly" class="readonly" type="text"  width="100%" /></td></tr>
	        <tr>
	            <td><label><input id="isEms" name="isEms" type="checkbox"/>指定EMS送货</label></td>
	            <td ><input id="emsRemark"  size="50"  class="readonly" type="text"  width="100%"  value=""/> </td>
	        </tr>
	        <tr><td colspan="2" align="center">
		        <a class="window_btn" onclick="subCallback($('#cart_delivery_frameId').val(),'confirmUpdate')" href="javascript:void(0)">确定</a>
		        <a class="window_btn ml10" onclick="subCallback($('#cart_delivery_frameId').val(), 'cancelDelivery')" href="javascript:void(0)">取消</a>
	        </td></tr>
	        </tbody></table></div>
	</div>
</body>

</html>