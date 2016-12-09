<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
<link href="/static/style/orderDetails.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/static/js/myorder/orderDetails.js?${rnd}"></script>
</head>
<body>
	<!-- 商品明细比较 -->
	<div id="cart_tabs" style="height: auto">
		<div class="goods_info">
			<h3 class="goods_info_title">购物车信息</h3>
		    <div class="c_content">
		        <table class="c_table" width="100%" border="0" cellspacing="5" cellpadding="0">
		            <tr>
		                <td class="c_context rBorder" width="50%" valign="top">
		                	<table class="pro" width="100%" border="1" cellspacing="0" cellpadding="0">
		                		<thead>
		                			<tr>
		                				<th scope="col">套装信息</th>
										<th scope="col">商品编号</th>
										<th scope="col">商品名称</th>
										<th scope="col">规格</th>
										<th scope="col">价格</th>
										<th scope="col">数量</th>
										<th scope="col" align="left">销售方式</th>
									</tr>
		                		</thead>
		                		<c:forEach var="detail" items="${oldOrder.orderdets}">
									<tr>
										<td></td>
										<td>${detail.prodid}</td>
										<td>${detail.prodname}</td>
										<td>${detail.producttype}</td>
										<td>¥${!empty detail.upnum && detail.upnum != 0 ? detail.uprice : detail.sprice}</td>
										<td>${!empty detail.upnum && detail.upnum != 0 ? detail.upnum : detail.spnum}</td>
										<td align="left">
											<c:choose>
												<c:when test="${detail.soldwith == 1}">直接销售</c:when>
												<c:when test="${detail.soldwith == 2}">搭销</c:when>
												<c:otherwise>赠品</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
		                	</table>
		                </td>
		                <td class="c_context" width="50%" valign="top">
		                	<table class="pro" width="100%" border="1" cellspacing="0" cellpadding="0">
		                		<thead>
		                			<tr>
		                				<th scope="col">套装信息</th>
										<th scope="col">商品编号</th>
										<th scope="col">商品名称</th>
										<th scope="col">规格</th>
										<th scope="col">价格</th>
										<th scope="col">数量</th>
										<th scope="col" align="left">销售方式</th>
									</tr>
		                		</thead>
		                		<c:forEach var="detail" items="${newOrder.orderdets}">
									<tr>
										<td></td>
										<td>${detail.prodid}</td>
										<td>${detail.prodname}</td>
										<td>${detail.producttype}</td>
										<td>¥${!empty detail.upnum && detail.upnum != 0 ? detail.uprice : detail.sprice}</td>
										<td>${!empty detail.upnum && detail.upnum != 0 ? detail.upnum : detail.spnum}</td>
										<td align="left">
											<c:choose>
												<c:when test="${detail.soldwith == 1}">直接销售</c:when>
												<c:when test="${detail.soldwith == 2}">搭销</c:when>
												<c:otherwise>赠品</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
		                	</table>
		                </td>
		            </tr>
		        </table>
		    </div>
	    </div>
	</div>
	
	<!-- 信用卡比较 -->
	<div id="cart_tabs" style="height: auto">
		<div class="goods_info">
			<h3 class="goods_info_title">信用卡信息</h3>
		    <div class="c_content">
		        <table class="c_table" width="100%" border="0" cellspacing="5" cellpadding="0">
		            <tr>
		                <td class="c_context rBorder" width="50%" valign="top">
		                                          信用卡左边
		                </td>
		                <td class="c_context" width="50%" valign="top">
		                                          信用卡右边
		                </td>
		            </tr>
		        </table>
		    </div>
	    </div>
	</div>
	
	<!-- 收货人地址及电话比较 -->
	<div id="cart_tabs" style="height: auto">
		<div class="goods_info">
			<h3 class="goods_info_title">收货信息</h3>
		    <div class="c_content">
		        <table class="c_table" width="100%" border="0" cellspacing="5" cellpadding="0">
		            <tr>
		                <td class="c_context rBorder" width="50%" valign="top">
		                	<table class="pro" width="100%" border="1" cellspacing="0" cellpadding="0">
		                		<thead>
		                			<tr>
		                				<th scope="col">姓名</th>
										<th scope="col">收货地址</th>
										<th scope="col">收货地址</th>
										<th scope="col">邮编</th>
										<th scope="col">联系电话</th>
									</tr>
		                		</thead>
		                		<tbody>
		                			<tr>
		                				<td>${oldContactName}</td>
		                				<td>${oldOrder.address.province.chinese}${oldOrder.address.city.cityname}${oldOrder.address.county.countyname}${oldOrder.address.area.areaname}</td>
		                				<td>${oldOrder.address.addressDesc}</td>
		                				<td>${oldAddress.zip}</td>
		                				<td>${pPhoneString}</td>
		                			</tr>
		                		</tbody>
		                	</table>
		                </td>
		                <td class="c_context" width="50%" valign="top">
		                	<table class="pro" width="100%" border="1" cellspacing="0" cellpadding="0">
		                		<thead>
		                			<tr>
		                				<th scope="col">姓名</th>
										<th scope="col">收货地址</th>
										<th scope="col">收货地址</th>
										<th scope="col">邮编</th>
										<th scope="col">联系电话</th>
									</tr>
		                		</thead>
		                		<tbody>
		                			<tr>
		                				<td>${empty newContactName ? oldContactName : newContactName}</td>
		                				<td>${orderInfo.address.provinceName}${orderInfo.address.cityName}${orderInfo.address.countyName}${orderInfo.address.areaName}</td>
		                				<td>${orderInfo.address.addressDesc}</td>
		                				<td>${orderInfo.address.zipcode}</td>
		                				<td></td>
		                			</tr>
		                		</tbody>
		                	</table>
		                </td>
		            </tr>
		        </table>
		    </div>
	    </div>
	</div>
	
	<!-- 备注及积分比较 -->
	<div id="cart_tabs" style="height: auto">
		<div class="goods_info">
			<h3 class="goods_info_title">订单备注及积分</h3>
		    <div class="c_content">
		        <table class="c_table" width="100%" border="0" cellspacing="5" cellpadding="0">
		            <tr>
		                <td class="c_context rBorder" width="50%" valign="top">
		                    <table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td valign="top">订单备注：</td>
									<td><textarea id="o_note" cols="50" rows="3" disabled>${oldOrder.note}</textarea></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
							</table>
							<div>使用积分：
								<input id="o_ifen" type="text" value="${oldOrder.jifen}" disabled />
							</div>
		                </td>
		                <td class="c_context" width="50%" valign="top">
		                    <table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td valign="top">订单备注：</td>
									<td><textarea id="n_note" cols="50" rows="3" disabled>${orderInfo.order.note}</textarea></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
							</table>
							<div>使用积分：
								<input id="n_jifen" type="text" value="${orderInfo.order.jifen}" disabled />
							</div>
		                </td>
		            </tr>
		        </table>
		    </div>
	    </div>
	</div>
	
	<!-- 结算比较 -->
	<div id="cart_tabs" style="height: auto">
		<div class="goods_info">
			<h3 class="goods_info_title">结算</h3>
		    <div class="c_content">
		        <table class="c_table" width="100%" border="0" cellspacing="5" cellpadding="0">
		            <tr>
		                <td class="c_context rBorder" width="50%" valign="top">
		                    <p>货款金额 ￥${oldOrder.prodprice} + 运费: 
								<input id="o_mailprice" type="text" value="${oldOrder.mailprice}" disabled />
								- 使用积分: ${oldOrder.jifen} = 应付总额：￥${oldOrder.totalprice} (获得积分：)
							</p>
		                </td>
		                <td class="c_context" width="50%" valign="top">
		                    <p>货款金额 ￥${newOrder.prodprice} + 运费: 
								<input id="n_mailprice" type="text" value="${orderInfo.order.mailPrice}" disabled />
								- 使用积分: ${newOrder.jifen} = 应付总额：￥${newOrder.totalprice} (获得积分：)
							</p>
		                </td>
		            </tr>
		        </table>
		    </div>
	    </div>
	</div>
	
	<!-- 配送信息 -->
	<div id="cart_tabs" style="height: auto">
		<div class="goods_info">
			<h3 class="goods_info_title">配送</h3>
		    <div class="c_content">
		        <table class="c_table" width="100%" border="0" cellspacing="5" cellpadding="0">
		            <tr>
		                <td class="c_context rBorder" width="50%" valign="top">
		                                          配送左边
		                </td>
		                <td class="c_context" width="50%" valign="top">
		                                          配送右边
		                </td>
		            </tr>
		        </table>
		    </div>
	    </div>
	</div>
	
	<p class="mt10">
		<a class="phoneNums_btns" style="width: 100px;" onclick="$('#form').submit()">提交</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="phoneNums_btns" style="width: 100px;" href="javascript:void(0)">取消</a>
	</p>
	
	<form id="form" action="../edit/${oldOrder.orderid}/confirm" method="get">
		<input id="f_cartId" type="hidden" name="cart.cartId" value="${orderInfo.cart.cartId}" />
		<input id="f_note" type="hidden" name="order.note" value="${orderInfo.order.note}" />
		<input id="f_jifen" type="hidden" name="order.jifen" value="${orderInfo.order.jifen}" />
		<input id="f_mailprice" type="hidden" name="order.mailPrice" value="${orderInfo.order.mailPrice}" />
		<input id="f_contactId" type="hidden" name="address.contactId" value="${orderInfo.address.contactId}" />
		<input id="f_addressId" type="hidden" name="address.addressId" value="${orderInfo.address.addressId}" />
		<input id="f_addressDesc" type="hidden" name="address.addressDesc" value="${orderInfo.address.addressDesc}" />
		<input id="f_provinceId" type="hidden" name="address.provinceId" value="${orderInfo.address.provinceId}" />
		<input id="f_provinceName" type="hidden" name="address.provinceName" value="${orderInfo.address.provinceName}" />
		<input id="f_cityId" type="hidden" name="address.cityId" value="${orderInfo.address.cityId}" />
		<input id="f_cityName" type="hidden" name="address.cityName" value="${orderInfo.address.cityName}" />
		<input id="f_countyId" type="hidden" name="address.countyId" value="${orderInfo.address.countyId}" />
		<input id="f_countyName" type="hidden" name="address.countyName" value="${orderInfo.address.countyName}" />
		<input id="f_areaId" type="hidden" name="address.areaId" value="${orderInfo.address.areaId}" />
		<input id="f_areaName" type="hidden" name="address.areaName" value="${orderInfo.address.areaName}" />
		<input id="f_zipcode" type="hidden" name="address.zipcode" value="${orderInfo.address.zipcode}" />
	</form>
</body>
</html>