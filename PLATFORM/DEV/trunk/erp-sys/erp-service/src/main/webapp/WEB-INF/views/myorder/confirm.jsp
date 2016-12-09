<%@include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<c:set var="oldJifen" value="0" scope="page" />

<div id="id_edit_comfirm_div_${order.orderid}" class="easyui-window" title="修改确认" style="overflow: auto;"
	data-options="modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true,draggable:false">
	<!-- 商品明细比较 -->
	<div style="padding: 10px">
		<table class="changeAuTa" border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<th scope="col">&nbsp;</th>
				<th scope="col" class="big">修改前</th>
				<th scope="col" class="big red">修改后</th>
			</tr>
			<tr>
				<td width="90px" valign="top"><div class="t_head cart_info">购物车<br>信息</div></td>
				<td valign="top">
					<table class="pro" width="404px" border="1" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th scope="col"><div style="width: 62px">商品编号</div></th>
								<th scope="col"><div style="width: 62px">商品名称</div></th>
								<th scope="col"><div style="width: 33px">规格</div></th>
								<th scope="col"><div style="width: 33px">价格</div></th>
								<th scope="col" align="center"><div style="width: 33px">数量</div></th>
								<th scope="col"  align="center"><div style="width: 33px">积分</div></th>
								<th scope="col"><div style="width: 53px">销售方式</div></th>
							</tr>
						</thead>
						<c:forEach var="detail" items="${order.orderDetails}">
							<c:set var="oldJifen" value="${oldJifen + detail.jifen}" />
							<c:set var="price" value="${!empty detail.upnum && detail.upnum ne 0 ? detail.uprice : detail.sprice}" />
							<c:set var="num" value="${!empty detail.upnum && detail.upnum ne 0 ? detail.upnum : detail.spnum}" />
							<tr>
								<td><div style="width: 62px">${detail.prodid}</div></td>
								<td><div style="width: 62px">${detail.prodname}</div></td>
								<td><div style="width: 33px">${detail.productTypeName}</div></td>
								<td><div style="width: 33px">¥<fmt:formatNumber value="${price}" pattern="0.00" /></div></td>
								<td  align="center"><div style="width: 33px">${num}</div></td>
								<td  align="center"><div style="width: 33px">${detail.jifen}</div></td>
								<td><div style="width: 53px">
                                    <c:forEach var="saleType" items="${saleTypes}">
                                        <c:if test="${saleType.id.id == detail.soldwith}">${saleType.dsc}</c:if>
                                    </c:forEach>
								<%--${detail.soldwith eq 1 ? '直接销售' : (detail.soldwith eq 2 ? '搭销' : '赠品')}--%></div></td>
							</tr>
						</c:forEach>
					</table>
				</td>
				<td class="no_r" valign="top">
					<table class="pro" width="404px" border="1" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th scope="col"><div style="width: 62px">商品编号</div></th>
								<th scope="col"><div style="width: 62px">商品名称</div></th>
								<th scope="col"><div style="width: 33px">规格</div></th>
								<th scope="col"><div style="width: 33px">价格</div></th>
								<th scope="col"  align="center"><div style="width: 33px">数量</div></th>
								<th scope="col"  align="center"><div style="width: 33px">积分</div></th>
								<th scope="col"><div style="width: 53px">销售方式</div></th>
							</tr>
						</thead>
						<c:set var="highlight" value="style=\"color:red\"" />
						<c:set var="remove" value="style=\"text-decoration: line-through\"" />
						<c:choose>
							<c:when test="${!empty detailsMap}">
								<c:forEach var="detmap" items="${detailsMap}">
									<c:choose>
										<c:when test="${detmap.value eq 'modify'}">
											<c:set var="price"><fmt:formatNumber value="${!empty detmap.key.upnum && detmap.key.upnum ne 0 ? detmap.key.uprice : detmap.key.sprice}" pattern="0.00" /></c:set>
											<c:set var="num" value="${!empty detmap.key.upnum && detmap.key.upnum ne 0 ? detmap.key.upnum : detmap.key.spnum}" />
											<c:forEach var="olddet" items="${order.orderDetails}">
												<c:if test="${olddet.orderdetid eq detmap.key.orderdetid}">
													<c:set var="oldprice"><fmt:formatNumber value="${!empty olddet.upnum && olddet.upnum ne 0 ? olddet.uprice : olddet.sprice}" pattern="0.00" /></c:set>
													<c:if test="${oldprice ne price}"><c:set var="hlprice" value="${highlight}" /></c:if>
													<c:set var="oldnum" value="${!empty olddet.upnum && olddet.upnum ne 0 ? olddet.upnum : olddet.spnum}" />
													<c:if test="${oldnum ne num}"><c:set var="hlnum" value="${highlight}" /></c:if>
													<c:if test="${olddet.soldwith ne detmap.key.soldwith}"><c:set var="hlsoldwith" value="${highlight}" /></c:if>
													<c:if test="${olddet.jifen ne detmap.key.jifen}"><c:set var="hljifen" value="${highlight}" /></c:if>
												</c:if>
											</c:forEach>
											<tr>
												<td><div style="width: 62px"><span>${detmap.key.prodid}</span></div></td>
												<td><div style="width: 62px"><span>${detmap.key.prodname}</span></div></td>
												<td><div style="width: 33px"><span>${detmap.key.productTypeName}</span></div></td>
												<td><div style="width: 33px"><span ${hlprice}>¥${price}</span></div></td>
												<td><div style="width: 33px"><span ${hlnum}>${num}</span></div></td>
												<td><div style="width: 33px"><span ${hljifen}>${detmap.key.jifen}</span></div></td>
												<td><div style="width: 53px"><span ${hlsoldwith}>${detmap.key.soldwith eq 1 ? '直接销售' : (detmap.key.soldwith eq 2 ? '搭销' : '赠品')}</span></div></td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:if test="${detmap.value eq 'create'}"><c:set var="style" value="${highlight}" /></c:if>
											<c:if test="${detmap.value eq 'remove'}"><c:set var="style" value="${remove}" /></c:if>
											<c:if test="${detmap.value eq 'none'}"><c:set var="style" value="" /></c:if>
											<c:set var="price" value="${!empty detmap.key.upnum && detmap.key.upnum ne 0 ? detmap.key.uprice : detmap.key.sprice}" />
											<c:set var="num" value="${!empty detmap.key.upnum && detmap.key.upnum ne 0 ? detmap.key.upnum : detmap.key.spnum}" />
											<tr>
												<td><span ${style}>${detmap.key.prodid}</span></td>
												<td><span ${style}>${detmap.key.prodname}</span></td>
												<td><span ${style}>${detmap.key.productTypeName}</span></td>
												<td><span ${style}>¥<fmt:formatNumber value="${price}" pattern="0.00" /></span></td>
												<td  align="center"><span ${style}>${num}</span></td>
												<td  align="center"><span ${style}>${detmap.key.jifen}</span></td>
												<td><span ${style}>
												<c:forEach var="saleType" items="${saleTypes}">
                                                    <c:if test="${saleType.id.id == detmap.key.soldwith}">${saleType.dsc}</c:if>
                                                </c:forEach>
                                                <%--${detmap.key.soldwith eq 1 ? '直接销售' : (detmap.key.soldwith eq 2 ? '搭销' : '赠品')}--%></span></td>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach items="${order.orderDetails}">
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td align="left">&nbsp;</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</table>
				</td>
			</tr>
			<tr>
				<td valign="top"><div class="t_head card_info">信用卡<br>信息</div></td>
				<td valign="top"><table class="pro" width="404px" border="1" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th scope="col" width="42px">持卡人</th>
								<th scope="col">卡类型</th>
								<th scope="col">卡号</th>
								<th scope="col">有效期</th>
								<th scope="col">分期数</th>
								<th scope="col">附加码</th>
							</tr>
						</thead>
						<tr>
							<td><span id="o_cardholder">${!empty cardMap ? cardMap['contactName'] : ''}</span></td>
							<td><span id="o_cardtype">${!empty cardMap ? cardMap['cardType'] : ''}</span></td>
							<td><span id="o_cardnum">${!empty cardMap ? cardMap['cardNumber'] : ''}</span></td>
							<td><span id="o_validdate">${!empty cardMap ? cardMap['validity'] : ''}</span></td>
							<td><span id="o_amortisation">${!empty cardMap ? cardMap['amortisation'] : ''}</span></td>
							<td><span id="o_extracode">${!empty cardMap ? cardMap['extraCode'] : ''}</span></td>
						</tr>
					</table>
				</td>
				<td class="no_r" valign="top">
					<table id="id_det_compare_table1" class="pro" width="404px" border="1" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th scope="col" width="42px">持卡人</th>
								<th scope="col">卡类型</th>
								<th scope="col">卡号</th>
								<th scope="col">有效期</th>
								<th scope="col">分期数</th>
								<th scope="col">附加码</th>
							</tr>
						</thead>
						<tr>
							<td><span id="n_cardholder"></span></td>
							<td><span id="n_cardtype"></span></td>
							<td><span id="n_cardnum"></span></td>
							<td><span id="n_validdate"></span></td>
							<td><span id="n_amortisation"></span></td>
							<td><span id="n_extracode"></span></td>
						</tr>
					</table>
				</td>
			</tr>
			<c:choose>
				<c:when test="${address.state =='01'||address.state =='02'||address.state =='14'||address.state=='29'}"><c:set var="preAddress" value="" /></c:when>
				<c:when test="${address.state =='25'}"><c:set var="preAddress" value="${address.provinceName}壮族自治区" /></c:when>
				<c:when test="${address.state =='09'}"><c:set var="preAddress" value="${address.provinceName}回族自治区" /></c:when>
				<c:when test="${address.state =='13'}"><c:set var="preAddress" value="${address.provinceName}维吾尔自治区" /></c:when>
				<c:when test="${address.state =='30'||address.state =='05'}"><c:set var="preAddress" value="${address.provinceName}自治区" /></c:when>
				<c:otherwise><c:set var="preAddress" value="${address.provinceName}省"/></c:otherwise>
			</c:choose>
			<c:set var="preAddress" value="${preAddress}${address.cityName}" />
			<c:if test="${address.countyName!='其他区'&&address.countyName!='其他'}"><c:set var="preAddress" value="${preAddress}${address.countyName}" /></c:if>
			<c:if test="${address.areaName!='其他'&&address.areaName!='城区'}"><c:set var="preAddress" value="${preAddress}${address.areaName}" /></c:if>
			<tr>
				<td valign="top"><div class="t_head addr_info">收货<br>信息</div></td>
				<td valign="top">
					<table class="pro" width="404px" border="1" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th scope="col" width="42px">姓名</th>
								<th scope="col">收货地址</th>
								<th scope="col">详细地址</th>
								<th scope="col" width="53px">邮编</th>
								<th scope="col">联系电话</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><span id="o_contactName"><c:if test="${order.paytype == 11}">${contact.name}</c:if><c:if test="${order.paytype != 11}">${address.contactName}</c:if></span></td>
								<td><span id="o_preAddress">${preAddress}</span></td>
								<td><span id="o_addressDesc">${address.addressDesc}</span></td>
								<td><span id="o_zipcode">${address.zip}</span></td>
								<td><span id="o_phoneString"> <c:forEach var="ph" items="${phoneArray}"><p>${ph}</p></c:forEach></span></td>
							</tr>
						</tbody>
					</table>
				</td>
				<td class="no_r" valign="top">
					<table class="pro" width="404px" border="1" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th scope="col" width="42px">姓名</th>
								<th scope="col">收货地址</th>
								<th scope="col">详细地址</th>
								<th scope="col" width="53px">邮编</th>
								<th scope="col">联系电话</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><span id="n_contactName"></span></td>
								<td><span id="n_preAddress"></span></td>
								<td><span id="n_addressDesc"></span></td>
								<td><span id="n_zipcode"></span></td>
								<td><span id="n_phoneString"></span></td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td valign="top"><div class="t_head int_info">订单备注<br>及积分</div></td>
				<td valign="top">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td valign="top">发票抬头：</td>
							<td><span id="o_invoicetitle">${order.invoicetitle}</span></td>
						</tr>
						<tr>
							<td valign="top">订单备注：</td>
							<td><span id="o_note">${order.note}</span></td>
						</tr>
						<tr>
							<td>使用积分：</td>
							<td><span id="o_jifen">${oldJifen}</span></td>
						</tr>
					</table>
				</td>
				<td class="no_r" valign="top">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td valign="top">发票抬头：</td>
							<td><span id="n_invoicetitle"></span></td>
						</tr>
						<tr>
							<td valign="top">订单备注：</td>
							<td><span id="n_note"></span></td>
						</tr>
						<tr>
							<td>使用积分：</td>
							<td><span id="n_jifen"></span></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td valign="top"><div class="t_head set_info" style="line-height: 40px;">结算</div></td>
				<td valign="top">
					<table>
						<tr>
							<td width="130">货款金额:&nbsp;¥&nbsp; <span id="o_producttotalprice"><fmt:formatNumber value="${order.prodprice}" pattern="0.00" /></span></td>
							<td width="20">+</td>
							<td width="100">运费:&nbsp;¥&nbsp;<span id="o_mailprice">${order.mailprice}</span></td>
							<td width="20">=</td>
							<td width="120">应付总额:&nbsp;¥&nbsp; <span id="o_totalprice"><fmt:formatNumber value="${order.totalprice}" pattern="0.00" /></span>
							</td>
						</tr>
						<tr>
							<td colspan="5">(获得积分:&nbsp;&nbsp;<span id="o_getjifen"></span>)
							</td>
						</tr>
					</table>
				</td>
				<td class="no_r" valign="top">
					<table>
						<tr>
							<td width="130">货款金额 :&nbsp;¥&nbsp;<span id="n_producttotalprice"></span></td>
							<td width="20">+</td>
							<td width="100">运费:&nbsp;¥&nbsp;<span id="n_mailprice"></span></td>
							<td width="20">=</td>
							<td width="120">应付总额:&nbsp;¥&nbsp;<span id="n_totalprice"></span></td>
						</tr>
						<tr>
							<td colspan="5">(获得积分:&nbsp;&nbsp;<span id="n_getjifen"></span>)
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="no_b" valign="top"><div class="t_head deliver_info" style="line-height: 45px;">配送</div></td>
				<td class="no_b" valign="top">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="65">指定EMS:</td>
							<td><span id="o_ems">${!empty order.reqEMS && order.reqEMS == 'Y' ? '是' : '否'}</span></td>
						</tr>
						<tr>
							<td>备注:</td>
							<td><span id="o_remark"></span></td>
						</tr>
					</table>
				</td>
				<td class="no_r no_b" valign="top">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="65">指定EMS:</td>
							<td><span id="n_ems"></span></td>
						</tr>
						<tr>
							<td>备注:</td>
							<td><span id="n_remark"></span></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<p class=" textC winBtnsBar"> &nbsp;&nbsp;&nbsp;&nbsp; <a id="id_confirm_a" class="window_btn" onclick="callRelatedConfirm('${order.orderid}');">确认</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a class="window_btn" onclick="closeWin('id_edit_comfirm_div_${order.orderid}')">取消</a>
	</p>
</div>
<jsp:include page="relatedOrders.jsp">
	<jsp:param name="id" value="${order.orderid}"></jsp:param>
	<jsp:param name="callbackMethod" value="commitEdit" />
</jsp:include>