<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <link href="/static/style/inboundCall.css" rel="stylesheet"
          type="text/css" />
<script type="text/javascript" src="/static/js/myorder/addOrderViewaudit.js?${rnd}"></script>
    <script type="text/javascript">
        document.write("<style type='text/css'>.compareCell{width:"+($(window).width()-166)*0.5+"px}</style>");
    </script>
</head>
<body>
	<input id="input_busiType" type="hidden" value="${auditTaskType}" />
	<input id="input_id" type="hidden" value="${id}" />
	<input id="input_batchId" type="hidden" value="${batchId}" />
	<input id="input_crUser" type="hidden" value="${crUser}" />
	<input id="input_isManager" type="hidden" value="${isManager}" />
	<input id="input_isAllApproved" type="hidden" value="${isAllApproved }" />
	<input id="input_isConfirmAudit" type="hidden" value="${isConfirmAudit }" />
	<input id="input_auditTip" type="hidden" value="${auditTip }" />
	<input id="input_source" type="hidden" value="${source }" />
	<!-- 商品明细比较 -->
    <div style="padding:10px">
    <h2 class="h2_tabtitle">
    <!-- <span class ="red">(审批提示：) </span>-->
        修改申请编号：<span id="batchId">${batchId}</span>
    </h2>
    <table class="changeAuTa" border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr style="background: #e2f2ff;">
        <th scope="col">&nbsp;</th>
        <th scope="col" class="big">新增前</th>
        <th scope="col" class="big red">新增后</th>
    </tr>
    <tr><td class="no_r no_b"></td><td class="no_r no_b"></td><td class="no_r no_b"></td></tr>
    <!-- 订购人黑名单审批 -->
    <c:if test="${!empty instMap['10'].bpmInstID }">
            <tr>
                <td valign="top" width="90px" class="">
                    <div class="t_head card_info" >订购人</div>
                </td>
                <td valign="top" class="" style="opacity: 0.5" >
                </td>
                <td class="no_r  " valign="top">
                    <div class="compareCell red">
                    	${subscriber.name }
                    </div>
                </td>
            </tr>
    </c:if>
    
    <!-- 购物车0元审批 -->
        <tr>
            <td valign="top" width="90px" >
                <div class="t_head cart_info">购物车<br>信息</div>
            </td>
            <td valign="top" style="opacity: 0.5">
                <div class="compareCell">
                    <table class="pro" width="100%" border="1" cellspacing="0"
                           cellpadding="0">
                        <thead>
                        <tr>
                            <th align="center">套装信息</th>
                            <th>商品编号</th>
                            <th scope="col" >商品名称</th>
                            <th align="center" >规格</th>
                            <th align="center" >价格</th>
                            <th align="center" >数量</th>
                            <th align="center" >积分</th>
                            <th align="center" >销售方式</th>
                        </tr>
                        </thead>
                        <tbody>

                            <tr>

                                <td colspan="8" align="center"><div  class="compare_cell"  style="font-size: 20px;color: #999">&nbsp;</div></td>

                            </tr>
                        </tbody>
                    </table>

                </div>
            </td>

            <td class="no_r" valign="top">
                <div class="compareCell">
                    <table class="pro" width="100%" border="1" cellspacing="0"
                           cellpadding="0">
                        <thead>
                        <tr>
                            <th align="center">套装信息</th>
                            <th>商品编号</th>
                            <th scope="col" >商品名称</th>
                            <th align="center" >规格</th>
                            <th align="center" >价格</th>
                            <th align="center" >数量</th>
                            <th align="center" >积分</th>
                            <th align="center" >销售方式</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="detail" items="${order.orderdets}">
                            <c:set var="price"
                                   value="${!empty detail.upnum && detail.upnum ne 0 ? detail.uprice : detail.sprice}"
                                   scope="page" />
                            <c:set var="num"
                                   value="${!empty detail.upnum && detail.upnum ne 0 ? detail.upnum : detail.spnum}"
                                   scope="page" />
                            <tr <c:if test="${!empty productValidationMap[detail.prodid] }">class="red"</c:if>>
                                <td align="center"></td>
                                <td><div title="${detail.prodid}" class="compare_cell" >${detail.prodid}</div></td>
                                <td><div title="${detail.prodname}" class="compare_cell" >${detail.prodname}</div></td>
                                <td align="center"><div title="${detail.producttype}" class="compare_cell" >${detail.producttype}</div></td>
                                <td ><div title="￥${price}" class="compare_cell" >￥${price}</div></td>
                                <td align="center"><div title="${num}" class="compare_cell" >${num}</div></td>
                                <td align="center"><div title="${detail.jifen}" class="compare_cell" >${detail.jifen}</div></td>
                                <td align="center"><div title="<c:choose><c:when test="${detail.soldwith eq 1}">直接销售</c:when><c:when test="${detail.soldwith eq 2}">搭销</c:when><c:otherwise>赠品</c:otherwise></c:choose>" class="compare_cell" >
                                    <c:choose>
                                    <c:when test="${detail.soldwith eq 1}">直接销售</c:when>
                                    <c:when test="${detail.soldwith eq 2}">搭销</c:when>
                                    <c:otherwise>赠品</c:otherwise>
                                </c:choose></div></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </td>

        </tr>
    <c:if test="${!empty card}">
            <tr>
                <td valign="top" width="90px" class="">
                    <div class="t_head card_info" >信用卡</div>
                </td>
                <td valign="top" class="" style="opacity: 0.5" >
                    <div class="compareCell">
                            <table class="pro" width="100%" border="1" cellspacing="0"
                                   cellpadding="0">
                                <thead>
                                <tr>
                                    <th scope="col" >卡类型</th>
                                    <th scope="col">持卡人</th>
                                    <th scope="col">卡号</th>
                                    <th scope="col">有效期</th>
                                    <th scope="col">附加码</th>
                                    <th scope="col">分期数</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td colspan="6"><div  class="compare_cell" >&nbsp;</div></td>
                                </tr>
                                </tbody>
                            </table>

                    </div>
                </td>
                <td class="no_r  " valign="top">
                    <div class="compareCell">
                        <c:if test="${!empty instMap['5'].bpmInstID }">
                            <table class="pro" width="100%" border="1" cellspacing="0"
                                   cellpadding="0">
                                <thead>
                                <tr>
                                    <th scope="col" >卡类型</th>
                                    <th scope="col">持卡人</th>
                                    <th scope="col">卡号</th>
                                    <th scope="col">有效期</th>
                                    <th scope="col">附加码</th>
                                    <th scope="col">分期数</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td><div title="￥${card.type}" class="compare_cell" >${card.type}</div></td>
                                    <td><div title="￥${card.contactId}" class="compare_cell" >${card.contactId}</div></td>
                                    <td><div title="￥${card.cardNumber}" class="compare_cell" >${card.cardNumber}</div></td>
                                    <td><div title="￥${card.validDate}" class="compare_cell" >${card.validDate}</div></td>
                                    <td><div title="￥${card.extraCode}" class="compare_cell" >${card.extraCode}</div></td>
                                    <td><div title="￥${order.laststatus}" class="compare_cell" >${order.laststatus}</div></td>
                                </tr>
                                </tbody>
                            </table>
                        </c:if>
                    </div>
                </td>
            </tr>
    </c:if>
        <tr>
            <td valign="top" width="90px" class="">
                <div class="t_head addr_info" style="line-height: 40px">收货信息</div>
            </td>
            <td valign="top" class="" style="opacity: 0.5">
                <div class="compareCell">
	           			<table class="pro" width="100%" border="1" cellspacing="0"
	                          cellpadding="0">
	                       <thead>
	                       <tr>
	                           <th scope="col"align="center">姓名</th>
	                           <th scope="col">收货地址</th>
	                           <th scope="col">详细地址</th>
	                           <th scope="col" align="center">邮编</th>
	                           <th scope="col">联系电话</th>
	                       </tr>
	                       </thead>
	                       <tbody>
	                       <tr>
	                           <td align="center" colspan="5"><div  class="compare_cell" >&nbsp;</div></td>
	                       </tr>
	                       </tbody>
	                   </table>
                    
                </div>
            </td>
            <td class="no_r  " valign="top">
                <div class="compareCell" >
	                	<table class="pro" width="100%" border="1" cellspacing="0"
	                          cellpadding="0">
	                       <thead>
	                       <tr>
	                           <th scope="col"align="center">姓名</th>
	                           <th scope="col">收货地址</th>
	                           <th scope="col">详细地址</th>
	                           <th scope="col" align="center">邮编</th>
	                           <th scope="col">联系电话</th>
	                       </tr>
	                       </thead>
	                       <tbody>
	                       <tr <c:if test="${isContactChanged }">class="red"</c:if>>
	                           <c:set var="preAddress"
	                                  value="${order.address.province.chinese}${order.address.city.cityname}${order.address.county.countyname}${order.address.area.areaname}" />
	                           <td align="center"><div title="${!empty contactChange.name ? contactChange.name : contact.name}" class="compare_cell" >${!empty contactChange.name ? contactChange.name : contact.name}</div></td>
	                           <td><div title="${preAddress}" class="compare_cell" >${preAddress}</div></td>
	                           <td><div title="${order.address.addressDesc}" class="compare_cell" >${order.address.addressDesc}</div></td>
	                           <td align="center"><div title="${!empty order.address.area.zipcode ?order.address.area.zipcode : order.address.county.zipcode}" class="compare_cell" >${!empty order.address.area.zipcode ?
	                                   order.address.area.zipcode : order.address.county.zipcode}</div></td>
	                           <td><div class="compare_cell" >
	                           	<c:forEach var="phoneChange" items="${phoneChanges}">${phoneChange.phoneNum}</br></c:forEach>
	                           </div></td>
	                       </tr>
	                       </tbody>
	                   </table>
                    
                </div>
            </td>
        </tr>
    <%--<script type="text/javascript">--%>
        <%--$('div.compare_cell').each(function(){--%>
            <%--$(this).width($(this).parent().width()-parseInt($(this).parent().css('padding'))*2+2).css('white-space','nowrap');--%>
        <%--});--%>

    <%--</script>--%>
        <tr>
            <td valign="top" width="90px" class="">
                <div class="t_head int_info" style="line-height: 40px" >订单备注</div>
            </td>
            <td valign="top" class="" style="opacity: 0.5">
                <div class="compareCell">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td valign="top">订单备注：</td>
                            <td><textarea  style="width: 230px" rows="3"
                                          disabled></textarea></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                    </table>
                </div>
            </td>
            <td class="no_r  " valign="top">
                <div class="compareCell">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td valign="top">订单备注：</td>
                            <td><textarea id="o_note" style="width: 230px" rows="3"
                                          disabled>${order.note}</textarea></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <td valign="top" width="90px" class="">
                <div class="t_head set_info" style="line-height: 40px">结算</div>
            </td>
            <td valign="top" class="" style="opacity: 0.5" >
                <div class="compareCell">
	                <c:choose>
								<c:when test="${!empty instMap['7'].bpmInstID}">
				                    <p>货款金额:&nbsp;￥${order.prodprice} +
				                        运费:&nbsp;￥${order.mailprice}  = 应付总额:&nbsp;￥${order.totalprice}
				                        (获得积分:&nbsp;${order.jifen})</p>
								</c:when>
								<c:otherwise>
					                    <p>货款金额:&nbsp; &nbsp;&nbsp;+
					                        运费:&nbsp; &nbsp;&nbsp; = 应付总额:&nbsp;&nbsp;&nbsp;
					                        (获得积分:&nbsp;&nbsp;&nbsp;)</p>
								</c:otherwise>
					</c:choose>

                </div>
            </td>
            <td class="no_r  " valign="top">
                <div class="compareCell">
	             <c:choose>
						<c:when test="${!empty instMap['7'].bpmInstID}">
	                        <p>
	                            货款金额 :&nbsp;
	                            <span <c:if test="${!empty orderChange.prodprice && orderChange.prodprice ne order.prodprice}">style="color:red"</c:if>>¥${!empty orderChange.prodprice ? orderChange.prodprice : order.prodprice}</span>
	                            + 运费:&nbsp;
	                            <span <c:if test="${!empty orderChange.mailprice && orderChange.mailprice ne order.mailprice}">style="color:red"</c:if>>¥${!empty orderChange.mailprice ? orderChange.mailprice : order.mailprice}</span>
	                            = 应付总额:&nbsp;
	                            <span <c:if test="${!empty orderChange.totalprice && orderChange.totalprice ne order.totalprice}">style="color:red"</c:if>>¥${!empty orderChange.totalprice ? orderChange.totalprice : order.totalprice}</span>
	                            (获得积分:&nbsp;<span <c:if test="${!empty orderChange.jifen && orderChange.jifen ne order.jifen}">style="color:red"</c:if>>${!empty orderChange.jifen ? orderChange.jifen : order.jifen}</span>)
	                        </p>
						</c:when>
						<c:otherwise>
			                    <p>货款金额:&nbsp;￥${order.prodprice} +
			                        运费:&nbsp;￥${order.mailprice}  = 应付总额:&nbsp;￥${order.totalprice}
			                        (获得积分:&nbsp;${order.jifen})</p>
						</c:otherwise>
				</c:choose>

                </div>
            </td>
        </tr>
        <tr>
            <td valign="top" width="90px" class="">
                <div class="t_head invoice_info" >发票</div>
            </td>
            <td valign="top" class=""  style="opacity: 0.5">
                <div class="compareCell">
                    发票抬头:
                </div>
            </td>

            <td class="no_r  " valign="top">
                <div class="compareCell">
                    <c:set var="oldBill" value="${!empty order.bill ? order.bill : '0'}" />
                    <c:set var="changeBill"
                           value="${!empty orderChange.bill ? orderChange.bill : '0'}" />
                    <c:set var="oldTitle"
                           value="${!empty order.invoicetitle ? order.invoicetitle : ''}" />
                    <c:set var="changeTitle"
                           value="${!empty orderChange.invoicetitle ? orderChange.invoicetitle : ''}" />
                    <c:set var="hlbill" value="" />
                    <c:set var="hltitle" value="" />
                    <c:if test="${oldBill ne changeBill}">
                        <c:set var="hlbill" value="style=\"color:red\"" />
                    </c:if>
                    <c:if test="${oldTitle ne changeTitle}">
                        <c:set var="hltitle" value="style=\"color:red\"" />
                    </c:if>

                   <span><c:choose>
                        <c:when test="${'1' eq order.bill}">
                            需要
                        </c:when>
                        <c:otherwise>
                            不需要
                        </c:otherwise>
                    </c:choose> &nbsp;发票抬头:&nbsp;${order.invoicetitle} </span>
                </div>
            </td>
        </tr>
        <tr>
            <td valign="top" width="90px" class="">
                <div class="t_head deliver_info" style="line-height: 40px">配送</div>
            </td>
            <td valign="top" class="" style="opacity: 0.5">
					<div class="compareCell">
						<c:choose>
							<c:when test="${!empty instMap['8'].bpmInstID}">
							                    预计出货仓库:&nbsp;${order.warehouseName} <br />
							                    预计送货公司:&nbsp;${order.entityName}
							</c:when>
							<c:otherwise>
								预计出货仓库:&nbsp;<br /> 预计送货公司:&nbsp;
							</c:otherwise>
						</c:choose>

					</div>
				</td>

            <td class="no_r  " valign="top">
                <div class="compareCell">
	                <c:choose>
						<c:when test="${!empty instMap['8'].bpmInstID}">
						    <c:set var="hlems" value=""/>
		                    <c:set var="hlcheck" value=""/>
		                    <c:if test="${order.reqEMS ne orderChange.isreqems}">
		                        <c:set var="hlems" value="style=\"color:red\"" />
		                    </c:if>
		                    <c:if test="${orderChange.isreqems eq 'Y'}">
		                        <c:set var="hlcheck" value="checked=\"checked\"" />
		                    </c:if>
		                    <label class="fl"><input class="fl" name="phoneType" type="radio" disabled="disabled" maxlength="20" value="4" ${hlcheck}/>
		                        <span ${hlems}>指定ems配送</span></label><br/>
		                    <span ${hlems}>修改说明${ instMap['8'].remark }</span>
						</c:when>
						<c:otherwise>
							                    预计出货仓库:&nbsp;${order.warehouseName} <br />
                    			      预计送货公司:&nbsp;${order.entityName}
						</c:otherwise>
					</c:choose>
                </div>
            </td>
        </tr>

        <tr>
            <td valign="top" width="90px" class="">
                <div class="t_head order_type" style="line-height: 40px">订单类型</div>
            </td>
            <td valign="top" class="" style="opacity: 0.5" >
                <div class="compareCell">

                </div>
            </td>
            <td class="no_r" valign="top">
                <div class="compareCell">
                    <c:set var="hlordertype" value="" />
                    <c:if test="${order.ordertypeName ne orderChange.ordertypeName}">
                        <c:set var="hlordertype" value="style=\"color:red\"" />
                    </c:if>
                   <span>${order.ordertypeName}</span>
                </div>
            </td>
        </tr>

    </table>
    </div>

	<%--<div id="cart_tabs" style="height: auto">--%>
		<%--<table class="c_table goods_info" width="100%" border="0"--%>
			<%--cellspacing="5" cellpadding="0">--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_title">购物车信息</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_context"></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td class="c_context rBorder" width="50%" valign="top"></td>--%>
				<%--<td class="c_context" width="50%" valign="top">--%>
					<%--&lt;%&ndash;<table class="pro" width="100%" border="1" cellspacing="0"&ndash;%&gt;--%>
						<%--&lt;%&ndash;cellpadding="0">&ndash;%&gt;--%>
						<%--&lt;%&ndash;<thead>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<th align="center" width="60px">套装信息</th>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<th style="min-width: 60px">商品编号</th>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<th scope="col" style="min-width: 60px">商品名称</th>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<th align="center" width="40px">规格</th>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<th align="center" width="40px">价格</th>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<th align="center" width="40px">数量</th>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<th align="center" width="40px">积分</th>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<th align="center" width="60px">销售方式</th>&ndash;%&gt;--%>
							<%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
						<%--&lt;%&ndash;</thead>&ndash;%&gt;--%>
						<%--&lt;%&ndash;<tbody>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<c:forEach var="detail" items="${order.orderdets}">&ndash;%&gt;--%>
								<%--&lt;%&ndash;<c:set var="price"&ndash;%&gt;--%>
									<%--&lt;%&ndash;value="${!empty detail.upnum && detail.upnum ne 0 ? detail.uprice : detail.sprice}"&ndash;%&gt;--%>
									<%--&lt;%&ndash;scope="page" />&ndash;%&gt;--%>
								<%--&lt;%&ndash;<c:set var="num"&ndash;%&gt;--%>
									<%--&lt;%&ndash;value="${!empty detail.upnum && detail.upnum ne 0 ? detail.upnum : detail.spnum}"&ndash;%&gt;--%>
									<%--&lt;%&ndash;scope="page" />&ndash;%&gt;--%>
								<%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
									<%--&lt;%&ndash;<td align="center"></td>&ndash;%&gt;--%>
									<%--&lt;%&ndash;<td>${detail.prodid}</td>&ndash;%&gt;--%>
									<%--&lt;%&ndash;<td>${detail.prodname}</td>&ndash;%&gt;--%>
									<%--&lt;%&ndash;<td align="center">${detail.producttype}</td>&ndash;%&gt;--%>
									<%--&lt;%&ndash;<td align="right">?${price}</td>&ndash;%&gt;--%>
									<%--&lt;%&ndash;<td align="center">${num}</td>&ndash;%&gt;--%>
									<%--&lt;%&ndash;<td align="center">${detail.jifen}</td>&ndash;%&gt;--%>
									<%--&lt;%&ndash;<td align="center"><c:choose>&ndash;%&gt;--%>
											<%--&lt;%&ndash;<c:when test="${detail.soldwith eq 1}">直接销售</c:when>&ndash;%&gt;--%>
											<%--&lt;%&ndash;<c:when test="${detail.soldwith eq 2}">搭销</c:when>&ndash;%&gt;--%>
											<%--&lt;%&ndash;<c:otherwise>赠品</c:otherwise>&ndash;%&gt;--%>
										<%--&lt;%&ndash;</c:choose></td>&ndash;%&gt;--%>
								<%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
							<%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
						<%--&lt;%&ndash;</tbody>&ndash;%&gt;--%>
					<%--&lt;%&ndash;</table>&ndash;%&gt;--%>
				<%--</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td class="c_context" width="50%" valign="top"></td>--%>
				<%--<td class="c_context" width="50%" valign="top"></td>--%>
			<%--</tr>--%>

			<%--<c:if test="${!empty card}">--%>
				<%--<tr>--%>
					<%--<td colspan="2" class="c_title">信用卡</td>--%>
				<%--</tr>--%>
				<%--<tr>--%>
					<%--<td colspan="2" class="c_context"></td>--%>
				<%--</tr>--%>
				<%--<tr>--%>
					<%--<td class="c_context rBorder" width="50%" valign="top"></td>--%>

					<%--<c:if test="${!empty instMap['5'].bpmInstID }">--%>
						<%--<td class="c_context" width="50%" valign="top">--%>

							<%--&lt;%&ndash;<table class="pro" width="100%" border="1" cellspacing="0"&ndash;%&gt;--%>
								<%--&lt;%&ndash;cellpadding="0">&ndash;%&gt;--%>
								<%--&lt;%&ndash;<thead>&ndash;%&gt;--%>
									<%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
										<%--&lt;%&ndash;<th scope="col" width="40px">卡类型</th>&ndash;%&gt;--%>
										<%--&lt;%&ndash;<th scope="col">持卡人</th>&ndash;%&gt;--%>
										<%--&lt;%&ndash;<th scope="col">卡号</th>&ndash;%&gt;--%>
										<%--&lt;%&ndash;<th scope="col">有效期</th>&ndash;%&gt;--%>
										<%--&lt;%&ndash;<th scope="col">附加码</th>&ndash;%&gt;--%>
										<%--&lt;%&ndash;<th scope="col">分期数</th>&ndash;%&gt;--%>
									<%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
								<%--&lt;%&ndash;</thead>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<tbody>&ndash;%&gt;--%>
									<%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
										<%--&lt;%&ndash;<td>${card.type}</td>&ndash;%&gt;--%>
										<%--&lt;%&ndash;<td>${card.contactId}</td>&ndash;%&gt;--%>
										<%--&lt;%&ndash;<td>${card.cardNumber}</td>&ndash;%&gt;--%>
										<%--&lt;%&ndash;<td>${card.validDate}</td>&ndash;%&gt;--%>
										<%--&lt;%&ndash;<td>${card.extraCode}</td>&ndash;%&gt;--%>
										<%--&lt;%&ndash;<td>${order.laststatus}</td>&ndash;%&gt;--%>
									<%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
								<%--&lt;%&ndash;</tbody>&ndash;%&gt;--%>
							<%--&lt;%&ndash;</table>&ndash;%&gt;--%>
						<%--</td>--%>
					<%--</c:if>--%>

				<%--</tr>--%>
			<%--</c:if>--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_context"></td>--%>
			<%--</tr>--%>


			<%--<!-- 收货信息 -->--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_title">收货信息</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_context"></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td class="c_context rBorder" width="50%" valign="top"></td>--%>
				<%--<td class="c_context" width="50%" valign="top">--%>
					<%--&lt;%&ndash;<table class="pro" width="100%" border="1" cellspacing="0"&ndash;%&gt;--%>
						<%--&lt;%&ndash;cellpadding="0">&ndash;%&gt;--%>
						<%--&lt;%&ndash;<thead>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<th scope="col" width="40px">姓名</th>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<th scope="col">详细地址</th>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<th scope="col">详细地址</th>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<th scope="col">邮编</th>&ndash;%&gt;--%>
							<%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
						<%--&lt;%&ndash;</thead>&ndash;%&gt;--%>
						<%--&lt;%&ndash;<tbody>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<c:set var="preAddress"&ndash;%&gt;--%>
									<%--&lt;%&ndash;value="${order.address.province.chinese}${order.address.city.cityname}${order.address.county.countyname}${order.address.area.areaname}" />&ndash;%&gt;--%>
								<%--&lt;%&ndash;<td>${contact.name}</td>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<td>${preAddress}</td>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<td>${order.address.addressDesc}</td>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<td>${!empty order.address.area.zipcode ?&ndash;%&gt;--%>
									<%--&lt;%&ndash;order.address.area.zipcode : order.address.county.zipcode}</td>&ndash;%&gt;--%>
							<%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
						<%--&lt;%&ndash;</tbody>&ndash;%&gt;--%>
					<%--&lt;%&ndash;</table>&ndash;%&gt;--%>
				<%--</td>--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_context"></td>--%>
			<%--</tr>--%>

			<%--<!-- 订单备注 -->--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_title">订单备注</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_context"></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td class="c_context rBorder" width="50%" valign="top">--%>
					<%--<!--     <div>使用积分：--%>
                            <%--<input id="o_ifen" type="text" class="readonly" value="${order.jifen}" disabled />--%>
                        <%--</div>-->--%>
				<%--</td>--%>
				<%--<td class="c_context" width="50%" valign="top">--%>
					<%--&lt;%&ndash;<table border="0" cellspacing="0" cellpadding="0">&ndash;%&gt;--%>
						<%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<td valign="top">订单备注：</td>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<td><textarea id="o_note" style="width: 330px" rows="3"&ndash;%&gt;--%>
									<%--&lt;%&ndash;disabled>${order.note}</textarea></td>&ndash;%&gt;--%>
						<%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
						<%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<td>&nbsp;</td>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<td>&nbsp;</td>&ndash;%&gt;--%>
						<%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
					<%--&lt;%&ndash;</table> <!--     <div>使用积分：&ndash;%&gt;--%>
	                            <%--&lt;%&ndash;<input id="n_jifen" class="readonly" <c:if test="${!empty orderChange.jifen}">style="color:red"</c:if> type="text" value="${!empty orderChange.jifen ? orderChange.jifen : order.jifen}" disabled />&ndash;%&gt;--%>
	                        <%--&lt;%&ndash;</div> -->&ndash;%&gt;--%>
				<%--</td>--%>

			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_context"></td>--%>
			<%--</tr>--%>
			<%--<!-- 订单金额  -->--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_title">结算</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_context"></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td class="c_context rBorder" width="50%" valign="top"></td>--%>
				<%--<td class="c_context" width="50%" valign="top">--%>
					<%--&lt;%&ndash;<p>货款金额:&nbsp;?${order.prodprice} +&ndash;%&gt;--%>
						<%--&lt;%&ndash;运费:&nbsp;?${order.mailprice} - = 应付总额:&nbsp;?${order.totalprice}&ndash;%&gt;--%>
						<%--&lt;%&ndash;(获得积分:&nbsp;${order.jifen})</p>&ndash;%&gt;--%>
				<%--</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_context"></td>--%>
			<%--</tr>--%>

			<%--<!-- 发票  -->--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_title">发票</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_context"></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--&lt;%&ndash;<c:set var="oldBill" value="${!empty order.bill ? order.bill : '0'}" />&ndash;%&gt;--%>
				<%--&lt;%&ndash;<c:set var="changeBill"&ndash;%&gt;--%>
					<%--&lt;%&ndash;value="${!empty orderChange.bill ? orderChange.bill : '0'}" />&ndash;%&gt;--%>
				<%--&lt;%&ndash;<c:set var="oldTitle"&ndash;%&gt;--%>
					<%--&lt;%&ndash;value="${!empty order.invoicetitle ? order.invoicetitle : ''}" />&ndash;%&gt;--%>
				<%--&lt;%&ndash;<c:set var="changeTitle"&ndash;%&gt;--%>
					<%--&lt;%&ndash;value="${!empty orderChange.invoicetitle ? orderChange.invoicetitle : ''}" />&ndash;%&gt;--%>
				<%--&lt;%&ndash;<c:set var="hlbill" value="" />&ndash;%&gt;--%>
				<%--&lt;%&ndash;<c:set var="hltitle" value="" />&ndash;%&gt;--%>
				<%--&lt;%&ndash;<c:if test="${oldBill ne changeBill}">&ndash;%&gt;--%>
					<%--&lt;%&ndash;<c:set var="hlbill" value="style=\"color:red\"" />&ndash;%&gt;--%>
				<%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
				<%--&lt;%&ndash;<c:if test="${oldTitle ne changeTitle}">&ndash;%&gt;--%>
					<%--&lt;%&ndash;<c:set var="hltitle" value="style=\"color:red\"" />&ndash;%&gt;--%>
				<%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
				<%--<td class="c_context rBorder" width="50%" valign="top"></td>--%>
				<%--<td class="c_context" width="50%" valign="top">--%>
                    <%--&lt;%&ndash;<span><c:choose>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<c:when test="${'1' eq order.bill}">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;需要&ndash;%&gt;--%>
                <%--&lt;%&ndash;</c:when>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<c:otherwise>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;不需要&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</c:otherwise>&ndash;%&gt;--%>
						<%--&lt;%&ndash;</c:choose> &nbsp;发票抬头:&nbsp;${order.invoicetitle} </span>&ndash;%&gt;--%>
                <%--</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td class="c_context" width="50%" valign="top"></td>--%>
				<%--<td class="c_context" width="50%" valign="top"></td>--%>
			<%--</tr>--%>

			<%--<!-- 配送 -->--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_title">配送</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_context"></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>

				<%--<td class="c_context rBorder" width="50%" valign="top"></td>--%>
				<%--<td colspan="2" class="c_context">--%>
					<%--&lt;%&ndash;预计出货仓库:&nbsp;${order.warehouseName} <br />&ndash;%&gt;--%>
					<%--&lt;%&ndash;预计送货公司:&nbsp;${order.entityName}&ndash;%&gt;--%>
				<%--</td>--%>
			<%--</tr>--%>
			<%--<!-- 订单类型 -->--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_title">订单类型</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_context"></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<c:set var="hlordertype" value="" />--%>
				<%--<c:if test="${order.ordertypeName ne orderChange.ordertypeName}">--%>
					<%--<c:set var="hlordertype" value="style=\"color:red\"" />--%>
				<%--</c:if>--%>
				<%--<td class="c_context rBorder" width="50%" valign="top"></td>--%>
				<%--<td class="c_context" width="50%" valign="top"><span>--%>
						<%--&lt;%&ndash;${order.ordertypeName}&ndash;%&gt;--%>
                <%--</span></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td colspan="2" class="c_context"></td>--%>
			<%--</tr>--%>
		<%--</table>--%>

	<%--</div>--%>
	<p class="mb10 mt10" align="center">
		<a id="a_approveBatch" class="window_btn" href="javascript:void(0)"
			onclick="orderAuditApprove('1')">批准</a>
		<a id="a_rejectBatch" class="window_btn ml10" href="javascript:void(0)"
			onclick="orderAuditReject()">驳回</a>
		<a id="a_cancelInstance"
			class="window_btn ml10" href="javascript:void(0)">关闭</a>
	</p>

	<!-- 驳回对话框 -->
	<div id="div_reject_comment" style="display: none;">
			<div class="popWin_wrap"><table id="detail" class="editTable">
				<tr>
					<td valign="top"><label class="ml10">批次号</label></td>
					<td>
                        <input id="input_reject_batchid"  class="readonly ml10 mb10"  disabled="disabled" name="input_reject_batchid">
                    </td>
				</tr>
				<tr>
					<td valign="top"><label class="ml10">驳回原因</label></td>
					<td><textarea id="textarea_reject_comment" cols="50" rows="4" class='ml10 fr' name="textarea_reject_comment"></textarea></td>
				</tr>
			</table>
            </div>
	</div>
	
		<!-- 驳回确认对话框 -->
	<div id="div_reject_confirm" style="display: none;">
			<div class="popWin_wrap">
				<label>驳回会作废订单，确认驳回?</label>
            </div>
	</div>

</body>
</html>