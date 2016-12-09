<%@include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
<link href="${ctx}/static/style/orderDetails.css?<acron:md5 path="/static/style/orderDetails.css"></acron:md5>" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/style/loading.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/myorder/orderDetails.js?<acron:md5 path="/static/js/myorder/orderDetails.js"></acron:md5>"></script>
<script type="text/javascript" src="${ctx}/static/js/loading-min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/datagrid-detailview.js"></script>
<script type="text/javascript" src="${ctx}/static/js/idcard-validation.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/myorder/reminder.js?${rnd}"></script>
<script type="text/javascript" src="${ctx}/static/js/myorder/problematicorder.js?${rnd}"></script>
<script type="text/javascript">
var orderId = "${order.orderid}";
var payType = "${order.paytype}";
var cartId = "${cartId}";
var ctx = "${ctx}";
var prepath = "${ctx}/myorder/orderDetails";
var batchId = "${batchId}";
var confirm = "${order.confirm}";
var totalPrice = "${order.totalprice}";
var orderContactId = "${contact.contactid}";
var orderContactName = "${contact.name}";
var getContactId = "${order.getcontactid}"; // 联系人
var getContactName = "${getContactName}";
var payContactId = "${order.paycontactid}";
var payContactName = "${payContactName}";
var addressId = "${order.address.addressId}";
var isDetChanged = "${empty isDetChanged ? 'false' : isDetChanged}";
</script>
</head>
<body>
<div id="loading_mask">
	<div style=" margin: 15px">
		<div class="baseInfo">
			<table border="0" cellspacing="0" cellpadding="0" width="90%" style="margin: 0 auto">
				<tr>
					<td width="15%">客户姓名</td>
					<td width="17%">${contact.name}</td>
					<td width="15%">客户性别</td>
					<td width="15%"><c:if test="${contact.sex == '1'}">男</c:if><c:if test="${contact.sex == '2'}">女</c:if></td>
					<td width="15%">客户编号</td>
					<td width="23%"><a class="link" onclick="showContact('${contact.contactid}')">${contact.contactid}</a></td>
				</tr>
				<tr>
					<td width="15%">会员等级</td>
					<td width="17%">${contact.levelName}</td>
					<td width="15%">可用积分</td>
					<td width="15%">${contact.jifen}</td>
					<td width="15%">绑定座席(组)</td>
					<td width="23%">${contact.bindingGroup}</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="cart_tabs" style="height: auto">
		<div class="goods_info">
			<h3 class="goods_info_title">
				<c:if test="${not empty order.checkResult && order.checkResult == 0}"><span class="exaa abs">待审核</span></c:if>
				<c:if test="${not empty order.checkResult && order.checkResult == 1}"><span class="exaa abs">审核中</span></c:if>
				<c:if test="${not empty order.checkResult && order.checkResult == 3}"><span class="exa abs">未通过</span></c:if>
				订单信息
				<div id="id_order_op_div" class="fr">
					<!-- <a onclick="problematicOrder('${orderId}')" class="problematicorder "><span></span>问题单</a> -->
					<c:if test="${couldReApply}">
						<a onclick="showReminder()" class="reminder "><span></span>催送货</a>
					</c:if>
					<a onclick="showOrderProcess('${order.orderid}')" class="history "><span></span>历史处理记录</a>
					<c:if test="${editable}">
						<a id="id_order_edit_button" class="modify_order " onclick="enableButton()"><span></span>修改订单</a>
					</c:if>
					<c:if test="${deletable}">
						<a id="id_order_cancel_button" class="candel_order " onclick="cancelOrder()"><span></span>取消订单</a>
					</c:if>
                    <c:if test="${consultable}">
                        <a id="id_order_cancel_button" class="consult " onclick="consultOrder()"><span></span>转咨询</a>
                    </c:if>
				</div>
			</h3>
			<div class="order_info_body">
				<h3>基本信息</h3>
				<table class="order_baseinfo" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>订单编号</td>
						<td>${order.orderid}</td>
						<td>订单生成时间</td>
						<td><fmt:formatDate value="${order.crdt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>订单生成座席</td>
						<td>${order.crusr}</td>
					</tr>
					<tr>
						<td>订单状态</td>
						<td>${order.checkResult == 3 ? '作废' : order.orderStatusName}</td>
						<td>呼入媒体</td>
						<td>${order.media}</td>
						<td>父订单号</td>
						<td>${order.parentid}</td>
					</tr>
					<tr>
						<td>订单配送状态</td>
						<td>${order.customizeStatusName}</td>
						<td>订单类型</td>
						<td>${order.orderTypeName}</td>
						<td>面单号</td>
						<td>${order.mailid}</td>
					</tr>
				</table>
				
				<h3 class="mt10">商品明细
					<c:if test="${editable && approvaled}">
						<a id="id_modify_product_a" class="buttonEdit" <c:if test="${!activable}">style="display: none"</c:if>
							href="${ctx}/contact/1/${order.contactid}?orderId=${order.orderid}&batchId=${batchId}" title="修改"></a>
					</c:if>
				</h3>
				<table id="id_product_details_table" class="pro" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th scope="col" width="100">商品编号</th>
						<th scope="col" width="250">商品名称</th>
						<th scope="col" width="100">规格</th>
						<th scope="col" width="80">价格</th>
						<th scope="col" width="80">数量</th>
						<th scope="col" width="80">使用积分</th>
						<th scope="col" width="80">销售方式</th>
					</tr>
					<c:set var="productTotalPrice" value="0" />
					<c:set var="productTotalNum" value="0" />
					<c:forEach var="detail" items="${!empty editDetails ? editDetails : order.orderDetails}">
						<c:set var="price" value="${!empty detail.upnum && detail.upnum != 0 ? detail.uprice : detail.sprice}" />
						<c:set var="num" value="${!empty detail.upnum && detail.upnum != 0 ? detail.upnum : detail.spnum}" />
						<c:set var="productTotalPrice" value="${productTotalPrice + price * num}" />
						<c:set var="productTotalNum" value="${productTotalNum + num}" />
						<tr>
							<td>${detail.prodid}</td>
							<td>${detail.prodname}</td>
							<td>${detail.productTypeName}</td>
							<td>¥&nbsp;<fmt:formatNumber value="${price}" pattern="0.00"/></td>
							<td>${num}</td>
							<td>${detail.jifen}</td>
							<td>${detail.soldwith eq 1 ? '直接销售' : (detail.soldwith eq 2 ? '搭销' : '赠品')}</td>
						</tr>
					</c:forEach>
				</table>
				<div class="cart_price">
					总共&nbsp;&nbsp;<span id="producttotalnum">${productTotalNum}</span>件商品，
					合计&nbsp;&nbsp;<span>¥</span>&nbsp;<span id="producttotalprice"><fmt:formatNumber value="${productTotalPrice}" pattern="0.00"/></span>元
				</div>
				
				<!-- 信用卡 -->
				<div class="way">支付方式&nbsp;&nbsp;&nbsp;<input id="select_paytype" style="width: 90px;" disabled /></div>
				<c:if test="${order.paytype == 2}">
					<div>
						<h3>信用卡信息
							<c:if test="${order.confirm != -1 && editable && approvaled}">
								<a id="id_modify_credit_a" class="buttonEdit" <c:if test="${!activable}">style="display: none"</c:if> 
									onclick="toggleCredits('${order.paycontactid}')" title="修改"></a>
							</c:if>
						</h3>
						<div>索权状态&nbsp;&nbsp;&nbsp;<span>${order.confirm == -1 ? '已索权' : '未索权'}</span></div>
						<input id="input_select_credit_index" type="hidden" value="" />
						<table id="id_credit_table" class="easyui-datagrid"></table> 
						<div id="id_credit_list_div" class="easyui-panel list_div" 
							data-options="closable:true,collapsible:true,minimizable:true,maximizable:true,closed:true">
                            <div id="" class="mt10 clearfix" >
                                <span class=" ccgrid_t"> 信用卡列表 &nbsp;</span>
                                <span id="credit_contactName" style="font-weight: bold"></span>&nbsp;（<span id="credit_contactId" style="font-weight: bold"></span>）
                            </div>
                            <div class="selectCards">
								<table id="id_credit_list_table" class="easyui-datagrid"></table>
                                <div id="id_credit_button_div" class="orderInfo_btns" style="display: none">
                                    <a class="add" href="javascript:void(0)" onclick="addCredit()">新增</a>
                                    <a href="javascript:void(0)" onclick="selectContact('2')">选择付款人</a>
                                </div>
                                <input id="input_card_id" type="hidden" />
                                <table id="id_credit_create_table" border="0" class="newCD" cellspacing="0" cellpadding="0" 
                                	style="display: none;width:100%;background: #fafafa;">
                                    <tr>
                                        <td style="width:70px"><div class="phoneNums-title">类别:</div></td>
                                        <td><input id="input_category" class="easyui-combobox" data-options="width:100"/></td>
                                    </tr>
                                    <tr><td style="width:70px"></td><td></td></tr>
                                    <tr><td style="width:70px"></td><td></td></tr>
                                    <tr><td style="width:70px"></td><td></td></tr>
                                    <tr><td style="width:70px"></td><td></td></tr>
                                    <tr><td style="width:70px"></td><td></td></tr>
                                    <tr><td style="width:70px"></td><td></td></tr>
                                    <tr>
                                        <td style="width:70px"></td>
                                        <td >
                                            <a class="phoneNums_btns" onclick="javascript:doSaveCredit()">保存</a>
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <a class="phoneNums_btns" onclick="javascript:doCancelCredit()">取消</a>
                                        </td>
                                    </tr>
                                </table>
                            </div>
						</div>

					</div>
				</c:if>
				<br />

				<!-- 收货人信息 -->
				<div id="all_address_info">
					<h3>收货人信息
						<c:if test="${order.confirm != -1 && editable && approvaled}">
							<a id="id_modify_receipt_a" class="buttonEdit" onclick="toggleReceipts('${order.getcontactid}')"
								<c:if test="${!activable}">style="display: none"</c:if> title="修改"></a>
						</c:if>
						<span style="color: #ff0000;float: right">预计配送时间 
                           	<span id="id_delivey_days"><c:if test="${not empty order.delivery.userDef1}">${order.delivery.userDef1}天</c:if></span>
                        </span>
					</h3>
					<table id="id_address_show_table" class="easyui-datagrid" style=""></table>

					<div id="id_address_list_div" class="easyui-panel list_div" style=""
						data-options="closable:true,collapsible:true,minimizable:true,maximizable:true,closed:true">
                        <div id="id_addressTitle_div" class="mt10 clearfix" >
                            <span class=" ccgrid_t"> 收货人信息列表 &nbsp;
                            	<span id="receipt_contactName" style="font-weight: bold"></span>&nbsp;（<span id="receipt_contactId" style="font-weight: bold"></span>） 
                            </span>
                        </div>
                        <div class="selectCards">
						<table id="id_address_list_table" class="easyui-datagrid"></table>

                        <c:if test="${order.paytype != 11}">
                            <div id="id_receipt_button_div" class="orderInfo_btns" style="display: none">
                                <a class="add" href="javascript:void(0)" onclick="addReceipt()">新增</a>
                                <a href="javascript:void(0)" onclick="selectContact('1')">选择收货人</a>
                            </div>
                        </c:if>
                        <input id="input_select_index" type="hidden" value="" />
                        <table id="id_address_table" class="newCD" border="0" cellspacing="0" cellpadding="0" style="display: none;width:100%;background: #fafafa;">
                            <tr>
                                <td width="80"><div class="phoneNums-title" style="text-indent: 10px">收货地址</div></td>
                                <td>
                                    <div style="margin-left: 28px">
                                        <jsp:include page="/common/common_address.jsp"></jsp:include>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td width="100"></td>
                                <td>
                                    <div style="margin-left: 28px">
                                        <input id="input_addressDesc" type="text" size="80" class="easyui-validatebox" plaintext="" data-options="required:true" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td width="80">
                                    <div class="phoneNums-title"  style="text-indent: 10px">邮政编码&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                </td>
                                <td>
                                    <div style="margin-left: 28px">
                                        <input id="input_zip" type="text" size="8" class="easyui-validatebox" data-options="required:true,validType:'validZip'" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td width="80" valign="top"><div class="phoneNums-title"  style="text-indent: 10px">收货电话<a onclick="doAddPhone(this)" class="addBtn"></a></div></td>
                                <td>
                                    <div id="div_phonelist_exist">
                                        <ul id="ul_phonelist_exist" class="phoneNums-content"></ul>
                                    </div>
                                    <ul id="ul_phonelist_added" class="phoneNums-content"></ul>
                                </td>
                            </tr>
                            <tr id="id_addAddressButton">
                                <td width="100"></td>
                                <td style="padding-left:35px"><a class="phoneNums_btns" onclick="saveReceipt()">保存</a>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <a class="phoneNums_btns" onclick="cancelAddReceipt()">取消</a>
                                </td>
                            </tr>
                        </table>
                        </div>
					</div>
				</div>
				<br />
				
				<h3>发票信息 
					<c:if test="${editable && approvaled}">
						<a id="id_modify_invoice_a" class="buttonEdit" <c:if test="${!activable}">style="display: none"</c:if> 
							onclick="enable('#invoicetitle');" title="修改"></a>
					</c:if>
				</h3>
				<table width="60%">
					<tr>
						<td>发票抬头&nbsp;&nbsp;&nbsp;<input id="invoicetitle" type="text" disabled value="${order.invoicetitle}" /></td>
						<td>发票内容&nbsp;&nbsp;&nbsp;<input type="text" disabled value="产品明细" /></td>
					</tr>
				</table>
				<br />
				
				<!-- 备注积分 -->
				<h3>备注 
					<c:if test="${editable && approvaled}">
						<a id="id_modify_note_a" class="buttonEdit" <c:if test="${!activable}">style="display: none"</c:if> 
							onclick="enable('#note');enable('#jifen');" title="修改"></a>
					</c:if>
				</h3>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td valign="top">订单备注&nbsp;&nbsp;</td>
						<td><textarea id="note" cols="50" rows="3" maxlength="30" disabled>${order.note}</textarea></td>
						<td valign="top"><i id="id_cart_note_info">&nbsp;注：订单备注中前30个<br>&nbsp;字会打印在配送面单上</i></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td align="right">已输入<span id="notecount"></span>个字</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<br />
				
				<!-- 结算 -->
				<h3>
					结算 
					<c:if test="${editable && approvaled}">
						<a id="id_modify_settle_a" class="buttonEdit" <c:if test="${!activable}">style="display: none"</c:if> 
							onclick="enable('#jifen');enable('#mailprice');" title="修改"></a>
					</c:if>
				</h3>
				<p>当前共使用积分&nbsp;&nbsp;&nbsp;<span id="jifen"></span></p>
				<p>
					货款金额&nbsp;&nbsp;¥&nbsp;<span><fmt:formatNumber value="${productTotalPrice}" pattern="0.00"/></span> + 
					运费&nbsp;&nbsp;¥&nbsp;<input id="mailprice" type="text" size="5" class="easyui-numberbox" value="${order.mailprice}" 
						data-options="required:true,min:0,precision:2,
							formatter:function(value){
								if (isNaN(parseFloat($('#mailprice').val()))) return;
								return parseFloat($('#mailprice').val());
							}" disabled /> = 
					应付总额&nbsp;&nbsp;¥&nbsp;<span id="totalprice"></span> 
					(获得积分&nbsp;&nbsp;<span id="getjifen"></span>)
				</p>
				<br />
				
				<!-- 配送信息 -->
				<h3>配送
					<c:if test="${order.paytype == '1' && editable && approvaled}">
						<a id="id_modify_shipment_a" class="buttonEdit" <c:if test="${!activable}">style="display: none"</c:if> 
							onclick="enable('#ems');enable('#remark')" title="修改"></a>
					</c:if>
				</h3>
				<table width="80%">
					<tr>
						<td width="33%">预计出货仓库&nbsp;&nbsp;&nbsp;${order.delivery.warehouseName}</td>
						<td width="33%">预计送货公司&nbsp;&nbsp;&nbsp;${order.delivery.companyName}</td>
						<td width="34%">预计配送时间&nbsp;&nbsp;&nbsp;<c:if test="${not empty order.delivery.userDef1}">${order.delivery.userDef1}天</c:if></td>
					</tr>
					<tr>
						<td colspan="3">
							<input id="ems" type="checkbox" disabled <c:if test="${order.reqEMS == 'Y'}">checked</c:if>>指定EMS配送</input>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input id="remark" type="text" size="70" disabled />
						</td>
					</tr>
				</table>
				<br />
				
				<c:if test="${editable && approvaled}">
					<div id="id_main_button_div" <c:if test="${!activable}">style="display: none"</c:if>>
						<p class="mt10">
							<a id="id_commit_a" class="phoneNums_btns" style="width: 100px" onclick="showConfirmWindow()">提交</a>
						</p>
						<div id="activity_pane" style="float: left;"></div>
					</div>
				</c:if>
			</div>
		</div>
	</div>

    <div id="id_dialog_content_div"></div>
    
    <jsp:include page="orderProcess.jsp">
    	<jsp:param name="orderId" value="${order.orderid}" />
    </jsp:include>
    
	<c:if test="${editable && approvaled}">
		<jsp:include page="confirm.jsp"></jsp:include>
	</c:if>
	
	<div id="reminder" class="easyui-window"  data-options="closed:true,draggable:false">
		<form id="reminderApplyForm" class="form-tabs">
			<input type="hidden" name="orderid" value="${order.orderid }"/>
			<input type="hidden" id="frameId" name="frameId" value=""/>
			<input type="hidden" id="reminderSource" name="reminderSource" value="DETAIL"/>
			<table border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td><div style="width:65px">催送货等级</div></td>
					<td>
						<select id="class_apply" name="class_">
							<option value="">--请选择--</option>
							<option value="1">普通</option>
							<option value="2">加急</option>
							<option value="3">紧急</option>
						</select>
	                    <span id="message" class="fr" style="color: red;">&nbsp;</span>
					</td>
				</tr>
				<tr class="pt10">
					<td valign='top'class="pt10" >提交原因</td>
					<td class="pt10"><textarea id="applicationreason_apply" name="applicationreason" rows="5" cols="38" maxlength="200"></textarea></td>
				</tr>
			</table>
		</form>
        <p class="winBtnsBar textC">
        	<a class="window_btn mr10" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="subCallback($('#frameId').val(), 'submitReminder')">确定</a>
        	<a class="window_btn" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="closeWin('taskDialog')">取消</a>
        </p>
    </div>
    
    <div id="id_single_repeat_mobile_window" class="easyui-window" title="客户新增电话" style="width: 400px; height: 130px; padding: 10px;"
    	data-options="top:600,modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true">
	    <div class="c_content">新增的电话已关联客户ID
	        <a class="detailItem ml10" onclick="openContact()"><span id="repeat_mobile_contact_id"></span></a>，是否新增？
	        <p class="mb10 mt10" align="center">
	        	<a class="window_btn" onclick="doSaveReceipt();$('#id_single_repeat_mobile_window').window('close');">确定</a>
	        	<a class="window_btn ml10" onclick="$('#id_single_repeat_mobile_window').window('close')">取消</a>
	        </p>
	    </div>
	</div>
	<div id="id_multi_repeat_mobile_window" class="easyui-window" title="客户新增电话" style="width:700px;"
		data-options="top:100,modal:true,shadow:true,draggable:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true">
	    <div class="c_content">新增的电话已关联多个客户ID，是否新增？
	        <p class="mb10 mt10" align="center">
	        	<a class="window_btn" onclick="doSaveReceipt();$('#id_multi_repeat_mobile_window').window('close');">确定</a>
	        	<a class="window_btn ml10" onclick="$('#id_multi_repeat_mobile_window').window('close')">取消</a>
	        </p>
	    </div>
    	<table id="id_multi_repeat_mobile_table"></table>
	</div>    
    
	<div id="window_problematicOrderMarking" style="display: none;"> </div>
	<div id="dialog_consult_order">
		<table style="height: 30px; margin-left: 10px">
			<tr>
				<td><span id="span_consult_order_content"></span></td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>