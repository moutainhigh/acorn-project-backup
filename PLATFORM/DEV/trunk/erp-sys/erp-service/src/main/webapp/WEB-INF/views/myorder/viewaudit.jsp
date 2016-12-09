<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>

        <link href="/static/style/inboundCall.css" rel="stylesheet"
              type="text/css" />
	<script type="text/javascript" src="/static/js/myorder/viewaudit.js?${rnd}"></script>
    <script type="text/javascript">
        document.write("<style type='text/css'>.compareCell{width:"+($(window).width()-166)*0.5+"px}</style>");
    </script>

</head>
<body>
	<input id="input_busiType" type="hidden" value="${auditTaskType}"/>
	<input id="input_id" type="hidden" value="${id}"/>
	<input id="input_batchId" type="hidden" value="${batchId}"/>
	<input id="input_crUser" type="hidden" value="${crUser}"/>
	<input id="input_isManager" type="hidden" value="${isManager}"/>
	<input id="input_isAllApproved" type="hidden" value="${isAllApproved }"/>
	<input id="input_isConfirmAudit" type="hidden" value="${isConfirmAudit }"/>
	<input id="input_source" type="hidden" value="${source }" />
<!-- 商品明细比较 -->
    <div style="padding:10px">
    <h2 class="h2_tabtitle">
        修改申请编号：<span id="batchId">${batchId}</span>
    </h2>
        <table class="changeAuTa" border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr style="background: #e2f2ff;">
                <th scope="col">&nbsp;</th>
                <th scope="col" class="big">修改前</th>
                <th scope="col" class="big red">修改后</th>
            </tr>
            <tr><td class="no_r no_b"></td><td class="no_r no_b"></td><td class="no_r no_b"></td></tr>
            <c:if test="${isConfirmAudit=='0' or (isConfirmAudit=='1' and instMap['3'].status=='2')}">
            <tr>
                <td valign="top" width="90px" >
                    <div class="t_head cart_info">购物车<br>信息</div>
                </td>
                <td valign="top" >
                    <div class="compareCell">
                    <table id="oldCartTable" class="pro" width="100%"  border="1" cellspacing="0" cellpadding="0" >
                        <thead>
                        <tr>
                            <th align="center" >套装信息</th>
                            <th style="min-width: 60px">商品编号</th>
                            <th scope="col" >商品名称</th>
                            <th align="center" >规格</th>
                            <th align="center">价格</th>
                            <th align="center" >数量</th>
                            <th align="center">积分</th>
                            <th align="center" >销售方式</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="detail" items="${order.orderdets}">
                            <c:set var="price" value="${!empty detail.upnum && detail.upnum ne 0 ? detail.uprice : detail.sprice}" scope="page" />
                            <c:set var="num" value="${!empty detail.upnum && detail.upnum ne 0 ? detail.upnum : detail.spnum}" scope="page" />
                            <tr>
                                <td align="center"><div  class="compare_cell" ></div></td>
                                <td><div title="${detail.prodid}" class="compare_cell" >${detail.prodid}</div></td>
                                <td><div title="${detail.prodname}" class="compare_cell" >${detail.prodname}</div></td>
                                <td align="center"><div title="${detail.producttype}" class="compare_cell" >${detail.producttype}</div></td>
                                <td align="right"><div title="¥${price}" class="compare_cell" >¥${price}</div></td>
                                <td align="center"><div title="${num}" class="compare_cell" >${num}</div></td>
                                <td align="center"><div title="${detail.jifen}" class="compare_cell" >${detail.jifen}</div></td>
                                <td align="center">
                                    <div title="<c:forEach var="saleType" items="${saleTypes}"><c:if test="${saleType.id.id == detail.soldwith}">${saleType.dsc}</c:if></c:forEach>" class="compare_cell" >
                                    <%--<c:choose><c:when test="${detail.soldwith eq 1}">直接销售</c:when><c:when test="${detail.soldwith eq 2}">搭销</c:when><c:otherwise>赠品</c:otherwise></c:choose>" class="compare_cell" > --%>
                                    <%--<c:choose>
                                        <c:when test="${detail.soldwith eq 1}">直接销售x</c:when>
                                        <c:when test="${detail.soldwith eq 2}">搭销x</c:when>
                                        <c:otherwise>赠品x</c:otherwise>
                                    </c:choose>     --%>
                                        <c:forEach var="saleType" items="${saleTypes}">
                                            <c:if test="${saleType.id.id == detail.soldwith}">${saleType.dsc}</c:if>
                                        </c:forEach></div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                  </div>
                </td>

                <td class="no_r" valign="top">
                    <div class="compareCell">
                    <c:if test="${!empty instMap['3'].bpmInstID }">
                    <table class="pro"  border="1" cellspacing="0" cellpadding="0" >
                        <thead>
                        <tr>
                            <th align="center" >套装信息</th>
                            <th scope="col">商品编号</th>
                            <th scope="col" >商品名称</th>
                            <th align="center" >规格</th>
                            <th align="center" >价格</th>
                            <th align="center" >数量</th>
                            <th align="center">积分</th>
                            <th align="center" >销售方式</th>
                        </tr>
                        </thead>
                        <c:choose>
                            <c:when test="${empty changeMap}">
                                <c:forEach var="detail" items="${order.orderdets}">
                                    <c:set var="price" value="${!empty detail.upnum && detail.upnum ne 0 ? detail.uprice : detail.sprice}" scope="page" />
                                    <c:set var="num" value="${!empty detail.upnum && detail.upnum ne 0 ? detail.upnum : detail.spnum}" scope="page" />
                                    <tr>
                                        <td align="center"></td>
                                        <td><div title="${detail.prodid}" class="compare_cell" >${detail.prodid}</div></td>
                                        <td><div title="${detail.prodname}" class="compare_cell" >${detail.prodname}</div></td>
                                        <td align="center"><div title="${detail.producttype}" class="compare_cell" >${detail.producttype}</div></td>
                                        <td align="right"><div title="¥${price}" class="compare_cell" >¥${price}</div></td>
                                        <td align="center"><div title="${num}" class="compare_cell" >${num}</div></td>
                                        <td align="center"><div title="${detail.jifen}" class="compare_cell" >${detail.jifen}</div></td>
                                        <td align="center"><div title="<c:forEach var="saleType" items="${saleTypes}"><c:if test="${saleType.id.id == detail.soldwith}">${saleType.dsc}</c:if></c:forEach>" class="compare_cell">
                                        <%--<c:choose><c:when test="${detail.soldwith eq 1}">直接销售</c:when><c:when test="${detail.soldwith eq 2}">搭销</c:when><c:otherwise>赠品</c:otherwise></c:choose>" class="compare_cell">--%>
                                            <c:forEach var="saleType" items="${saleTypes}">
                                                <c:if test="${saleType.id.id == detail.soldwith}">${saleType.dsc}</c:if>
                                            </c:forEach>
                                            <%--<c:choose>
                                                <c:when test="${detail.soldwith eq 1}">直接销售</c:when>
                                                <c:when test="${detail.soldwith eq 2}">搭销</c:when>
                                                <c:otherwise>赠品</c:otherwise>
                                            </c:choose> --%></div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="change" items="${changeMap}">
                                    <c:set var="highlight" value=""/>
                                    <c:set var="hlprodtype" value=""/>
                                    <c:set var="hlprice" value=""/>
                                    <c:set var="hlnum" value=""/>
                                    <c:set var="hljifen" value=""/>
                                    <c:set var="hlsoldwith" value=""/>
                                    <c:set var="changeNum" value="" />
                                    <c:set var="changeNum" value="${!empty change.upnum && change.upnum ne 0 ? change.upnum : change.spnum}" />
                                    <c:set var="changePrice" value="${!empty change.upnum && change.upnum ne 0 ? change.uprice : change.sprice}" />
                                    <c:choose>
                                        <c:when test="${change.modifyFlag eq '-1'}">
                                            <c:set var="highlight" value="style=\"text-decoration: line-through\"" />
                                        </c:when>
                                        <c:when test="${change.modifyFlag eq '1'}">
                                            <c:set var="highlight" value="style=\"color:red\"" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="old" items="${order.orderdets}">
                                                <c:if test="${old.id eq change.id}">
                                                    <c:if test="${old.producttype ne change.producttype}">
                                                        <c:set var="hlprodtype" value="style=\"color:red\"" />
                                                    </c:if>
                                                    <c:set var="oldPrice" value="${!empty old.upnum && old.upnum ne 0 ? old.uprice : old.sprice}" />
                                                    <c:if test="${oldPrice ne changePrice}">
                                                        <c:set var="hlprice" value="style=\"color:red\"" />
                                                    </c:if>
                                                    <c:set var="oldNum" value="${!empty old.upnum && old.upnum ne 0 ? old.upnum : old.spnum}" />

                                                    <c:if test="${oldNum ne changeNum}">
                                                        <c:set var="hlnum" value="style=\"color:red\"" />
                                                    </c:if>
                                                    <c:if test="${!empty old.jifen && old.jifen ne change.jifen}">
                                                        <c:set var="hljifen" value="style=\"color:red\"" />
                                                    </c:if>
                                                    <c:if test="${!empty change.jifen && change.jifen ne old.jifen}">
                                                        <c:set var="hljifen" value="style=\"color:red\"" />
                                                    </c:if>
                                                    <c:if test="${old.soldwith ne change.soldwith}">
                                                        <c:set var="hlsoldwith" value="style=\"color:red\"" />
                                                    </c:if>
                                                </c:if>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>


                                    <tr>
                                        <td><div  class="compare_cell" ></div></td>
                                        <td><div title="${change.prodid}" class="compare_cell" ><span ${highlight}>${change.prodid}</span></div></td>
                                        <td><div title="${change.prodname}" class="compare_cell" ><span ${highlight}>${change.prodname}</span></div></td>
                                        <td  align="center"><div title="${change.producttype}" class="compare_cell" ><span ${highlight}${hlprodtype}>${change.producttype}</span></div></td>
                                        <td><div title="¥${changePrice}" class="compare_cell" ><span ${highlight}${hlprice}>¥${changePrice}</span></div></td>
                                        <td  align="center"><div title="${changeNum}" class="compare_cell" ><span ${highlight}${hlnum}>${changeNum}</span></div></td>
                                        <td  align="center"><div title="${change.jifen}" class="compare_cell" ><span ${highlight}${hljifen}>${change.jifen}</span></div></td>
                                        <td  align="center">
                                            <div title='<c:forEach var="saleType" items="${saleTypes}"><c:if test="${saleType.id.id == change.soldwith}">${saleType.dsc}</c:if></c:forEach><%--<c:choose><c:when test="${change.soldwith eq 1}">直接销售</c:when><c:when test="${change.soldwith eq 2}">搭销</c:when><c:otherwise>赠品</c:otherwise></c:choose>--%>' class="compare_cell">
                                                <c:forEach var="saleType" items="${saleTypes}">
                                                    <c:if test="${saleType.id.id == change.soldwith}"><span ${highlight}${hlsoldwith}>${saleType.dsc}</span></c:if>
                                                </c:forEach>
                                                <%--<c:choose><c:when test="${change.soldwith eq 1}"><span ${highlight}${hlsoldwith}>直接销售</span></c:when><c:when test="${change.soldwith eq 2}"><span ${highlight}${hlsoldwith}>搭销</span></c:when><c:otherwise><span ${highlight}${hlsoldwith}>赠品</span></c:otherwise></c:choose>--%>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </table>
                        <c:choose>
                            <c:when test="${isManager and !empty instMap['3'].bpmInstID and instMap['3'].status=='0'}">
                                <div id='div_order_cartinfo_command' style="display: block;">
                                    <c:choose>
                                        <c:when test="${empty instMap['3'].parentInsId}">
                                            <p class="mb10 mt10">
                                                <label><input type="radio" name="radio_order_cartinfo_command" id="radio_order_cartinfo_command_approve" value="1_<c:out value="${instMap['3'].bpmInstID}"/>"/>批准</label>
                                                <label><input type="radio" name="radio_order_cartinfo_command" id="radio_order_cartinfo_command_reject" value="2_<c:out value="${instMap['3'].bpmInstID}"/>"/>驳回</label>
                                                <input type="text" id="radio_order_cartinfo_command_text" style="display:none; margin-left:20px; width:60%"/>
                                                <input type="text" id="radio_order_cartinfo_inst" style="display:none;" value="${instMap['3'].bpmInstID}"/>
                                            </p>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="mb10 mt10"><b>审批关联</b></p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </c:when>
                            <c:when test="${!isManager and !empty instMap['3'].bpmInstID and instMap['3'].status=='0'}">
                                <div style="display: block;">
                                    <p class="mb10 mt10">
                                    <div> <label color="red">待审批</label></div>
                                    </p>
                                </div>
                            </c:when>
                            <c:when test="${!empty instMap['3'].bpmInstID and instMap['3'].status=='1'}">
                                <div> <label color="red">已审批</label></div>
                            </c:when>
                            <c:when test="${!empty instMap['3'].bpmInstID and instMap['3'].status=='2'}">
                                <div> <label color="red">已驳回</label>
                                    <span style="margin-left:20px;color:red">意见:  ${instMap['3'].approverRemark }</span>
                                </div>
                            </c:when>
                            <c:when test="${!empty instMap['3'].bpmInstID and instMap['3'].status=='4'}">
                                <div> <label color="red">已关闭</label></div>
                            </c:when>
                        </c:choose>
                    </c:if>
                        </div>
                </td>

            </tr>
            </c:if>
            <c:if test="${isConfirmAudit=='0' or (isConfirmAudit=='1' and instMap['5'].status=='2')}">
            <c:if test="${!empty card}">
            <tr>
                <td valign="top" width="90px" class="">
                    <div class="t_head card_info" >信用卡</div>
                </td>
                <td valign="top" class="" >
                    <div class="compareCell">
                    <table class="pro" width="100%" border="1" cellspacing="0" cellpadding="0">
                        <thead>
                        <tr>
                            <th scope="col" width="40px">卡类型</th>
                            <th scope="col">持卡人</th>
                            <th scope="col">卡号</th>
                            <th scope="col">有效期</th>
                            <th scope="col">附加码</th>
                            <th scope="col">分期数</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${card.type}</td>
                            <td>${card.contactId}</td>
                            <td>${card.cardNumber}</td>
                            <td>${card.validDate}</td>
                            <td>${card.extraCode}</td>
                            <td>${order.laststatus}</td>
                        </tr>
                        </tbody>
                    </table>
                        </div>
                </td>
                <td class="no_r  " valign="top">
                    <div class="compareCell">
                    <c:if test="${!empty instMap['5'].bpmInstID }">
                        <table class="pro" width="100%" border="1" cellspacing="0" cellpadding="0">
                            <thead>
                            <tr>
                                <th scope="col" width="40px">卡类型</th>
                                <th scope="col">持卡人</th>
                                <th scope="col">卡号</th>
                                <th scope="col">有效期</th>
                                <th scope="col">附加码</th>
                                <th scope="col">分期数</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>

                                <td><span <c:if test="${!empty cardChange.type && cardChange.type ne card.type}">style="color:red"</c:if>>${!empty cardChange.type ? cardChange.type : card.type}</span></td>
                                <td><span <c:if test="${!empty cardChange.contactId && cardChange.contactId ne card.contactId}">style="color:red"</c:if>>${!empty cardChange.contactId ? cardChange.contactId : card.contactId}</span></td>
                                <td><span <c:if test="${!empty cardChange.cardNumber && cardChange.cardNumber ne card.cardNumber}">style="color:red"</c:if>>${!empty cardChange.cardNumber ? cardChange.cardNumber : card.cardNumber}</span> </td>
                                <td><span <c:if test="${!empty cardChange.validDate && cardChange.validDate ne card.validDate}">style="color:red"</c:if>>${!empty cardChange.validDate ? cardChange.validDate : card.validDate}</span></td>
                                <td><span <c:if test="${!empty cardChange.extraCode && cardChange.extraCode ne card.extraCode}">style="color:red"</c:if>>${!empty cardChange.extraCode ? cardChange.extraCode : card.extraCode}</span></td>
                                <td><span <c:if test="${!empty orderChange.laststatus && orderChange.laststatus ne order.laststatus}">style="color:red"</c:if>>${!empty orderChange.laststatus ? orderChange.laststatus : order.laststatus}</span></td>
                            </tr>
                            </tbody>
                        </table>
                        <c:choose>
                            <c:when test="${isManager and !empty instMap['5'].bpmInstID and instMap['5'].status=='0'}">
                                <div id='div_order_card_command' style="display: block;">
                                    <c:choose>
                                        <c:when test="${empty instMap['5'].parentInsId}">
                                            <p class="mb10 mt10">
                                                <label><input type="radio" name="radio_order_card_command" id="radio_order_card_command_approve" value="1_<c:out value="${instMap['5'].bpmInstID}"/>"/>批准</label>
                                                <label><input type="radio" name="radio_order_card_command" id="radio_order_card_command_reject" value="2_<c:out value="${instMap['5'].bpmInstID}"/>"/>驳回</label>
                                                <input type="text" id="radio_order_card_command_text" style="display:none; margin-left:20px; width:60%"/>
                                                <input type="text" id="radio_order_card_inst" style="display:none;" value="${instMap['5'].bpmInstID}"/>
                                            </p>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="mb10 mt10"><b>审批关联</b></p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </c:when>
                            <c:when test="${!isManager and !empty instMap['5'].bpmInstID and instMap['5'].status=='0'}">
                                <div style="display: block;">
                                    <p class="mb10 mt10">
                                    <div> <label color="red">待审批</label></div>
                                    </p>
                                </div>
                            </c:when>
                            <c:when test="${!empty instMap['5'].bpmInstID and instMap['5'].status=='1'}">
                                <div> <label color="red">已审批</label></div>
                            </c:when>
                            <c:when test="${!empty instMap['5'].bpmInstID and instMap['5'].status=='2'}">
                                <div> <label color="red">已驳回</label>
                                    <span style="margin-left:20px;color:red">意见:  ${instMap['5'].approverRemark }</span>
                                </div>
                            </c:when>
                             <c:when test="${!empty instMap['5'].bpmInstID and instMap['5'].status=='4'}">
                                <div> <label color="red">已关闭</label></div>
                            </c:when>
                        </c:choose>
                    </c:if>
                        </div>
                </td>
            </tr>
            </c:if>
            </c:if>
            <c:if test="${isConfirmAudit=='0' or (isConfirmAudit=='1' and isContactChangedAndRejected)}">
            <tr>
                <td valign="top" width="90px" class="">
                    <div class="t_head addr_info" style="line-height: 40px">收货信息</div>
                </td>
                <td valign="top" class="" >
                    <div class="compareCell">
                    <table class="pro" width="100%" border="1" cellspacing="0" cellpadding="0">
                        <thead>
                        <tr>
                            <th scope="col" width="40px">姓名</th>
                            <th scope="col">收货地址</th>
                            <th scope="col">详细地址</th>
                            <th scope="col">邮编</th>
                            <th scope="col">联系电话</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <c:set var="preAddress" value="${order.address.province.chinese}${order.address.city.cityname}${order.address.county.countyname}${order.address.area.areaname}" />
                            <td><div title="${contact.name}" class="compare_cell" >${contact.name}</div></td>
                            <td><div title="${preAddress}" class="compare_cell" >${preAddress}</div></td>
                            <td><div title="${order.address.addressDesc}" class="compare_cell" >${order.address.addressDesc}</div></td>
                            <td><div title="${!empty order.address.area.zipcode ? order.address.area.zipcode : order.address.county.zipcode}" class="compare_cell" >${!empty order.address.area.zipcode ? order.address.area.zipcode : order.address.county.zipcode}</div></td>
                            <td>  </td>

                        </tr>
                        </tbody>
                    </table>
                        </div>
                </td>
                <td class="no_r  " valign="top">
                    <div class="compareCell" >
                    <c:if test="${isContactChanged}">
                        <%--<table width="100%" class="pro" border="0" cellspacing="0" cellpadding="0" >--%>
                            <%--<tr><td>--%>
                                <table class="pro" width="100%" border="1" cellspacing="0" cellpadding="0">
                                    <thead>
                                    <tr>
                                        <th scope="col" width="40px">姓名</th>
                                        <th scope="col">收货地址</th>
                                        <th scope="col">详细地址</th>
                                        <th scope="col">邮编</th>
                                        <th scope="col">联系电话</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td><div title="${!empty contactChange ? contactChange.name : contact.name}" class="compare_cell" ><span <c:if test="${!empty contactChange && contact.name ne contactChange.name }">style="color:red"</c:if>>${!empty contactChange ? contactChange.name : contact.name}</span></div></td>
                                        <c:if test="${empty addressChange}">
                                            <c:set var="preAddress" value="${order.address.province.chinese}${order.address.city.cityname}${order.address.county.countyname}${order.address.area.areaname}" />
                                            <td><div title="${preAddress}" class="compare_cell" >${preAddress}</div></td>
                                            <td><div title="${order.address.addressDesc}" class="compare_cell" >${order.address.addressDesc}</div></td>
                                            <td><div title="${!empty order.address.area.zipcode ? order.address.area.zipcode : order.address.county.zipcode}" class="compare_cell" >${!empty order.address.area.zipcode ? order.address.area.zipcode : order.address.county.zipcode}</div></td>
                                            <td>
                                                <c:forEach var="phoneChange" items="${phoneChanges}">
                                                    <div title="${phoneChange.phoneNum}" class="compare_cell" >
                                            <div style="color: red;">${phoneChange.phoneNum}</div>
                                                    </div>
                                    </c:forEach>

                                    </td>
                                        </c:if>

                                        <c:if test="${!empty addressChange}">
                                            <c:set var="zipcode" value="${!empty order.address.area.zipcode ? order.address.area.zipcode : order.address.county.zipcode}" />
                                            <c:set var="zipcodeChange" value="${zipcode}" />
                                            <c:if test="${!empty addressChange.county && !empty addressChange.county.zipcode}">
                                                <c:set var="zipcodeChange" value="${addressChange.county.zipcode}" />
                                            </c:if>
                                            <c:if test="${!empty addressChange.area && !empty addressChange.area.zipcode}">
                                                <c:set var="zipcodeChange" value="${addressChange.area.zipcode}" />
                                            </c:if>
                                            <c:set var="provinceChange" value="${!empty addressChange.province ? addressChange.province.chinese : order.address.province.chinese}" />
                                            <c:set var="cityChange" value="${!empty addressChange.city ? addressChange.city.cityname : order.address.city.cityname}" />
                                            <c:set var="countyChange" value="${!empty addressChange.county ? addressChange.county.countyname : order.address.county.countyname}" />
                                            <c:set var="areaChange" value="${!empty addressChange.area ? addressChange.area.areaname : order.address.area.areaname}" />
                                            <c:set var="preAddressChange" value="${provinceChange}${cityChange}${countyChange}${areaChange}" />
                                            <td><div title="${preAddressChange}" class="compare_cell" ><span <c:if test="${preAddress ne preAddressChange}">style="color:red"</c:if>>${preAddressChange}</span></div></td>
                                            <td><div title="${!empty addressChange.addressDesc ? addressChange.addressDesc : order.address.addressDesc}" class="compare_cell" ><span <c:if test="${!empty addressChange.addressDesc && order.address.addressDesc ne addressChange.addressDesc}">style="color:red"</c:if>>${!empty addressChange.addressDesc ? addressChange.addressDesc : order.address.addressDesc}</span></div></td>
                                            <td><div title="${zipcodeChange}" class="compare_cell" ><span <c:if test="${zipcodeChange ne zipcode}">style="color:red"</c:if>>${zipcodeChange}</span></div></td>
                                            <td>
                                                <c:forEach var="phoneChange" items="${phoneChanges}">
                                                    <div title="${phoneChange.phoneNum}" class="compare_cell" >
                                                    <div style="color: red;">${phoneChange.phoneNum}</div>
                                                    </div>
                                                </c:forEach>

                                            </td>
                                        </c:if>
                                    </tr>
                                    </tbody>
                                </table>
                            <%--</td></tr>--%>
                            <%--<c:choose>--%>
                                <%--<c:when test="${!empty phoneChanges}">--%>
                                    <%--&lt;%&ndash;<tr><td>&ndash;%&gt;--%>
                                    <%--<p>--%>
                                        <%--<table class="pro"  border="1" cellspacing="0" cellpadding="0">--%>
                                            <%--<thead>--%>
                                            <%--<tr>--%>
                                                <%--<th scope="col">新增电话</th>--%>
                                            <%--</tr>--%>
                                            <%--</thead>--%>
                                            <%--<c:forEach var="phoneChange" items="${phoneChanges}">--%>
                                                <%--<tr>--%>
                                                    <%--<td><span style="color: red;">${phoneChange.phoneNum}</span></td>--%>
                                                <%--</tr>--%>
                                            <%--</c:forEach>--%>
                                        <%--</table>--%>
                                    <%--</p>--%>
                                    <%--&lt;%&ndash;</td></tr>&ndash;%&gt;--%>
                                <%--</c:when>--%>
                            <%--</c:choose>--%>
                            <%--<tr><td>--%>
                        <p>
                                <c:choose>
                                    <c:when test="${isManager and isContactChanged and contactStatus=='0'}">
                                        <div id='div_order_contact_command' style="display: block;">
                                            <c:choose>
                                                <c:when test="${empty instMap['2'].parentInsId}">
                                                    <p class="mb10 mt10" >
                                                        <label><input type="radio" name="radio_order_contact_command" id="radio_order_contact_command_approve" value="1<c:out value="${contactInsts}"/>"/>批准</label>
                                                        <label><input type="radio" name="radio_order_contact_command" id="radio_order_contact_command_reject" value="2<c:out value="${contactInsts}"/>"/>驳回</label>
                                                        <input type="text" id="radio_order_contact_command_text" style="display:none; margin-left:20px; width:60%"/>
                                                    </p>
                                                </c:when>
                                                <c:otherwise>
                                                    <p class="mb10 mt10"><b>审批关联</b></p>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </c:when>
                                    <c:when test="${!isManager and isContactChanged and contactStatus=='0'}">
                                        <div style="display: block;">
                                            <p class="mb10 mt10">
                                            <div> <label color="red">待审批</label></div>
                                            </p>
                                        </div>
                                    </c:when>
                                    <c:when test="${isContactChanged and contactStatus=='1'}">
                                        <div> <label color="red">已审批</label></div>
                                    </c:when>
                                    <c:when test="${isContactChanged and contactStatus=='2'}">
                                        <div> <label color="red">已驳回</label>
                                            <span style="margin-left:20px;color:red">意见:  ${contactComment }</span>
                                        </div>
                                    </c:when>
                                   <c:when test="${isContactChanged and contactStatus=='4'}">
                                	    <div> <label color="red">已关闭</label></div>
                            	   </c:when>
                                </c:choose>
                        </p>
                            <%--</td></tr>--%>
                        <%--</table>--%>
                    </c:if>
                        </div>
                </td>
            </tr>
            </c:if>
            <%--<script type="text/javascript">--%>
                <%--$('div.compare_cell').each(function(){--%>
                    <%--$(this).width($(this).parent().width()-5*2+2)--%>
                            <%--.css('white-space','nowrap');--%>
                <%--})--%>
            <%--</script>--%>
            <c:if test="${isConfirmAudit=='0' or (isConfirmAudit=='1' and instMap['4'].status=='2')}">
            <tr>
                <td valign="top" width="90px" class="">
                    <div class="t_head int_info" style="line-height: 40px" >订单备注</div>
                </td>
                <td valign="top" class="" >
                    <div class="compareCell">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td valign="top">订单备注：</td>
                            <td><textarea id="o_note" style="width:230px"   rows="3" disabled>${order.note}</textarea></td>
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
                    <c:if test="${!empty instMap['4'].bpmInstID }">
                    <%--<table border="0" cellspacing="0" cellpadding="0">--%>
                        <%--<tr>--%>
                            <%--<td valign="top">订单备注：</td>--%>
                            <%--<td><textarea id="o_note" style="width:330px"   rows="3" disabled>${order.note}</textarea></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td>&nbsp;</td>--%>
                            <%--<td>&nbsp;</td>--%>
                        <%--</tr>--%>
                    <%--</table>--%>
                        <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                <td valign="top">订单备注：</td>
                                <td>
                                <c:set var="noteStyle" value="style=\"width:230px\""/>
                                <c:if test="${!empty orderChange.note && orderChange.note ne order.note}">
                                <c:set var="noteStyle" value="style=\"width:230px;color:red\""/>
                                </c:if>
                                <textarea id="n_note" ${noteStyle}   style="width:230px" rows="3" disabled>${!empty orderChange.note ? orderChange.note : order.note}</textarea>
                                </td>
                                </tr>
                                </table>
                    </c:if>

                    <c:choose>
                        <c:when test="${isManager and !empty instMap['4'].bpmInstID and instMap['4'].status=='0'}">
                            <div id='div_order_remark_command' style="display: block;">
                                <c:choose>
                                    <c:when test="${empty instMap['4'].parentInsId}">
                                        <p class="mb10 mt10">
                                            <label><input type="radio" name="radio_order_remark_command" id="radio_order_remark_command_approve" value="1_<c:out value="${instMap['4'].bpmInstID}"/>"/>批准</label>
                                            <label><input type="radio" name="radio_order_remark_command" id="radio_order_remark_command_reject" value="2_<c:out value="${instMap['4'].bpmInstID}"/>"/>驳回</label>
                                            <input type="text" id="radio_order_remark_command_text" style="display:none; margin-left:20px; width:60%"/>
                                            <input type="text" id="radio_order_remark_inst" style="display:none;" value="${instMap['4'].bpmInstID}"/>
                                        </p>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="mb10 mt10"><b>审批关联</b></p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:when>
                        <c:when test="${!isManager and !empty instMap['4'].bpmInstID and instMap['4'].status=='0'}">
                            <div style="display: block;">
                                <p class="mb10 mt10">
                                <div> <label color="red">待审批</label></div>
                                </p>
                            </div>
                        </c:when>
                        <c:when test="${!empty instMap['4'].bpmInstID and instMap['4'].status=='1'}">
                            <div> <label color="red">已审批</label></div>
                        </c:when>
                        <c:when test="${!empty instMap['4'].bpmInstID and instMap['4'].status=='2'}">
                            <div> <label color="red">已驳回</label>
                                <span style="margin-left:20px;color:red">意见:  ${instMap['4'].approverRemark }</span>
                            </div>
                        </c:when>
                       <c:when test="${!empty instMap['4'].bpmInstID and instMap['4'].status=='4'}">
                           <div> <label color="red">已关闭</label></div>
                       </c:when>
                    </c:choose>
                    </div>
                </td>
            </tr>
            </c:if>
            <c:if test="${isConfirmAudit=='0' or (isConfirmAudit=='1' and (instMap['7'].status=='2' or instMap['11'].status=='2'))}">
            <tr>
                <td valign="top" width="90px" class="">
                    <div class="t_head set_info" style="line-height: 40px">结算</div>
                </td>
                <td valign="top" class="" >
                    <div class="compareCell">
                    <p>
                        货款金额:&nbsp;¥${order.prodprice} + 运费:&nbsp;¥${order.mailprice} -
                        = 应付总额:&nbsp;¥${order.totalprice}
                        (获得积分:&nbsp;${order.jifen})
                    </p>
                        </div>
                </td>
                <td class="no_r  " valign="top">
                    <div class="compareCell">
                    <c:if test="${!empty instMap['7'].bpmInstID or !empty instMap['11'].bpmInstID or (!empty orderChange.prodprice && orderChange.prodprice ne order.prodprice) or (!empty orderChange.jifen && orderChange.jifen ne order.jifen) }">
                        <p>
                            货款金额 :&nbsp;
                            <span <c:if test="${!empty orderChange.prodprice && orderChange.prodprice ne order.prodprice}">style="color:red"</c:if>>¥${!empty orderChange.prodprice ? orderChange.prodprice : order.prodprice}</span>
                            + 运费:&nbsp;
                            <span <c:if test="${!empty orderChange.mailprice && orderChange.mailprice ne order.mailprice}">style="color:red"</c:if>>¥${!empty orderChange.mailprice ? orderChange.mailprice : order.mailprice}</span>
                            = 应付总额:&nbsp;
                            <span <c:if test="${!empty orderChange.totalprice && orderChange.totalprice ne order.totalprice}">style="color:red"</c:if>>¥${!empty orderChange.totalprice ? orderChange.totalprice : order.totalprice}</span>
                            (获得积分:&nbsp;<span <c:if test="${!empty orderChange.jifen && orderChange.jifen ne order.jifen}">style="color:red"</c:if>>${!empty orderChange.jifen ? orderChange.jifen : order.jifen}</span>)
                        </p>
                        <c:choose>
                            <c:when test="${isManager and !empty instMap['7'].bpmInstID and instMap['7'].status=='0'}">
                                <div id='div_order_bill_command' style="display: block;">
                                    <c:choose>
                                        <c:when test="${empty instMap['7'].parentInsId}">
                                            <p class="mb10 mt10">
                                                <label><input type="radio" name="radio_mailprice_command" id="radio_mailprice_command_approve" value="1_<c:out value="${instMap['7'].bpmInstID}"/>"/>批准 </label>
                                                <label><input type="radio" name="radio_mailprice_command" id="radio_mailprice_command_reject" value="2_<c:out value="${instMap['7'].bpmInstID}"/>"/>驳回</label>
                                                <input type="text" id="radio_mailprice_command_text" style="display:none; margin-left:20px; width:60%"/>
                                                <input type="text" id="radio_mailprice_inst" style="display:none;" value="${instMap['7'].bpmInstID}"/>
                                            </p>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="mb10 mt10"><b>审批关联</b></p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </c:when>
                            <c:when test="${isManager and !empty instMap['11'].bpmInstID and instMap['11'].status=='0'}">
                                <div id='div_order_bill_command' style="display: block;">
                                    <c:choose>
                                        <c:when test="${empty instMap['11'].parentInsId}">
                                            <p class="mb10 mt10">
                                                <label><input type="radio" name="radio_mailprice_command" id="radio_mailprice_command_approve" value="1_<c:out value="${instMap['11'].bpmInstID}"/>"/>批准 </label>
                                                <label><input type="radio" name="radio_mailprice_command" id="radio_mailprice_command_reject" value="2_<c:out value="${instMap['11'].bpmInstID}"/>"/>驳回</label>
                                                <input type="text" id="radio_mailprice_command_text" style="display:none; margin-left:20px; width:60%"/>
                                                <input type="text" id="radio_mailprice_inst" style="display:none;" value="${instMap['11'].bpmInstID}"/>
                                            </p>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="mb10 mt10"><b>审批关联</b></p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </c:when>
                            <c:when test="${(!isManager and !empty instMap['7'].bpmInstID and instMap['7'].status=='0') or (!isManager and !empty instMap['11'].bpmInstID and instMap['11'].status=='0')}">
                                <div style="display: block;">
                                    <p class="mb10 mt10">
                                    <div> <label color="red">待审批</label></div>
                                    </p>
                                </div>
                            </c:when>
                            <c:when test="${(!empty instMap['7'].bpmInstID and instMap['7'].status=='1') or (!empty instMap['11'].bpmInstID and instMap['11'].status=='1')}">
                                <div> <label color="red">已审批</label></div>
                            </c:when>
                            <c:when test="${!empty instMap['7'].bpmInstID and instMap['7'].status=='2'}">
                                <div> <label color="red">已驳回</label>
                                    <span style="margin-left:20px;color:red">意见:  ${instMap['7'].approverRemark }</span>
                                </div>
                            </c:when>
                              <c:when test="${!empty instMap['11'].bpmInstID and instMap['11'].status=='2'}">
                                <div> <label color="red">已驳回</label>
                                    <span style="margin-left:20px;color:red">意见:  ${instMap['11'].approverRemark }</span>
                                </div>
                            </c:when>
                            <c:when test="${(!empty instMap['7'].bpmInstID and instMap['7'].status=='4') or (!empty instMap['11'].bpmInstID and instMap['11'].status=='4')}">
                                <div> <label color="red">已关闭</label></div>
                            </c:when>
                        </c:choose>
                    </c:if>
                        </div>
                </td>
            </tr>
            </c:if>
            <c:if test="${isConfirmAudit=='0' or (isConfirmAudit=='1' and instMap['6'].status=='2')}">
            <tr>
                <td valign="top" width="90px" class="">
                    <div class="t_head invoice_info" >发票</div>
                </td>
                <c:set var="oldBill" value="${!empty order.bill ? order.bill : '0'}" />
                <c:set var="changeBill" value="${!empty orderChange.bill ? orderChange.bill : '0'}" />
                <c:set var="oldTitle" value="${!empty order.invoicetitle ? order.invoicetitle : ''}" />
                <c:set var="changeTitle" value="${!empty orderChange.invoicetitle ? orderChange.invoicetitle : ''}" />
                <c:set var="hlbill" value=""/>
                <c:set var="hltitle" value=""/>
                <c:if test="${oldBill ne changeBill}">
                    <c:set var="hlbill" value="style=\"color:red\"" />
                </c:if>
                <c:if test="${oldTitle ne changeTitle}">
                    <c:set var="hltitle" value="style=\"color:red\"" />
                </c:if>
                <td valign="top" class="" >
                    <div class="compareCell">
                    <span><c:choose>
                        <c:when test="${'1' eq order.bill}">
                            需要
                        </c:when>
                        <c:otherwise>
                            不需要
                        </c:otherwise>
                    </c:choose>
                    &nbsp;发票抬头:&nbsp;${order.invoicetitle} </span>
                        </div>
                </td>

                <td class="no_r  " valign="top">
                    <div class="compareCell">
                    <c:if test="${!empty instMap['6'].bpmInstID }">

                    <c:choose>
                        <c:when test="${'1' eq orderChange.bill}">
                            <span ${hlbill}>需要</span>
                        </c:when>
                        <c:otherwise>
                            <span ${hlbill}>不需要</span>
                        </c:otherwise>
                    </c:choose>
                    &nbsp;发票抬头:&nbsp;<span ${hltitle}>${orderChange.invoicetitle} </span>

                    <c:choose>
                        <c:when test="${isManager and !empty instMap['6'].bpmInstID and instMap['6'].status=='0'}">
                            <div id='div_order_invoice_command' style="display: block;">
                                <c:choose>
                                    <c:when test="${empty instMap['6'].parentInsId}">
                                        <p class="mb10 mt10" >
                                            <label><input type="radio" name="radio_order_invoice_command" id="radio_order_invoice_command_approve" value="1_<c:out value="${instMap['6'].bpmInstID}"/>"/>批准</label>
                                            <label><input type="radio" name="radio_order_invoice_command" id="radio_order_invoice_command_reject" value="2_<c:out value="${instMap['6'].bpmInstID}"/>"/>驳回</label>
                                            <input type="text" id="radio_order_invoice_command_text" style="display:none; margin-left:20px; width:60%"/>
                                            <input type="text" id="radio_order_invoice_inst" style="display:none;" value="${instMap['6'].bpmInstID}"/>
                                        </p>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="mb10 mt10"><b>审批关联</b></p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:when>
                        <c:when test="${!isManager and !empty instMap['6'].bpmInstID and instMap['6'].status=='0'}">
                            <div style="display: block;">
                                <p class="mb10 mt10">
                                <div> <label color="red">待审批</label></div>
                                </p>
                            </div>
                        </c:when>
                        <c:when test="${!empty instMap['6'].bpmInstID and instMap['6'].status=='1'}">
                            <div> <label color="red">已审批</label></div>
                        </c:when>
                        <c:when test="${!empty instMap['6'].bpmInstID and instMap['6'].status=='2'}">
                            <div> <label color="red">已驳回</label>
                                <span style="margin-left:20px;color:red">意见:  ${instMap['6'].approverRemark }</span>
                            </div>
                        </c:when>
                       <c:when test="${!empty instMap['6'].bpmInstID and instMap['6'].status=='4'}">
                          <div> <label color="red">已关闭</label></div>
                       </c:when>
                    </c:choose>
                </c:if>
                        </div>
                </td>
            </tr>
            </c:if>
            <c:if test="${isConfirmAudit=='0' or (isConfirmAudit=='1' and instMap['8'].status=='2')}">
            <tr>
                <td valign="top" width="90px" class="">
                    <div class="t_head deliver_info" style="line-height: 40px">配送</div>
                </td>
                <td valign="top" class="" >
                    <div class="compareCell">
                    预计出货仓库:&nbsp;${order.warehouseName}<br/>
                    预计送货公司:&nbsp;${order.entityName}
                        </div>
                </td>

                <td class="no_r  " valign="top">
                    <div class="compareCell">
                    <c:if test="${!empty instMap['8'].bpmInstID }">
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
                    <span ${hlems}>修改说明:${ instMap['8'].remark }</span>
                    <c:choose>
                        <c:when test="${isManager and !empty instMap['8'].bpmInstID and instMap['8'].status=='0'}">
                            <div id='div_order_ems_command' style="display: block;">
                                <c:choose>
                                    <c:when test="${empty instMap['8'].parentInsId}">
                                        <p class="mb10 mt10">
                                            <label><input type="radio" name="radio_order_ems_command" id="radio_order_ems_command_approve" value="1_<c:out value="${instMap['8'].bpmInstID}"/>" />批准</label>
                                            <label><input type="radio" name="radio_order_ems_command" id="radio_order_ems_command_reject" value="2_<c:out value="${instMap['8'].bpmInstID}"/>" />驳回</label>
                                            <input type="text" id="radio_order_ems_command_text" style="display: none; margin-left: 20px; width: 60%" />
                                            <input type="text" id="radio_order_ems_inst" style="display:none;" value="${instMap['8'].bpmInstID}"/>
                                        </p>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="mb10 mt10"><b>审批关联</b></p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:when>
                        <c:when
                                test="${!isManager and !empty instMap['8'].bpmInstID and instMap['8'].status=='0'}">
                            <div style="display: block;">
                                <p class="mb10 mt10">
                                <div>
                                    <label color="red">待审批</label>
                                </div>
                                </p>
                            </div>
                        </c:when>
                        <c:when
                                test="${!empty instMap['8'].bpmInstID and instMap['8'].status=='1'}">
                            <div>
                                <label color="red">已审批</label>
                            </div>
                        </c:when>
                        <c:when
                                test="${!empty instMap['8'].bpmInstID and instMap['8'].status=='2'}">
                            <div>
                                <label color="red">已驳回</label> <span
                                    style="margin-left: 20px; color: red">意见:
                                    ${instMap['8'].approverRemark }</span>
                            </div>
                        </c:when>
                       <c:when test="${!empty instMap['8'].bpmInstID and instMap['8'].status=='4'}">
                            <div> <label color="red">已关闭</label></div>
                       </c:when>
                    </c:choose>
                </c:if>
                        </div>
                </td>
            </tr>
            </c:if>
            <c:if test="${isConfirmAudit=='0' or (isConfirmAudit=='1' and instMap['9'].status=='2')}">
            <tr>
                <td valign="top" width="90px" class="">
                    <div class="t_head order_type" style="line-height: 40px">订单类型</div>
                </td>
                <c:set var="hlordertype" value=""/>
                <c:if test="${order.ordertypeName ne orderChange.ordertypeName}">
                    <c:set var="hlordertype" value="style=\"color:red\"" />
                </c:if>
                <td valign="top" class="" >
                    <div class="compareCell"> <span> ${order.ordertypeName}</span> </div>
                </td>
                <td class="no_r" valign="top">
                    <div class="compareCell">
                    <c:if test="${!empty instMap['9'].bpmInstID }">
                    <span ${hlordertype}>${orderChange.ordertypeName}</span>
                </c:if> </div>
                </td>
            </tr>
             </c:if>
        </table>
    </div>
<%--<div id="cart_tabs" style="height: auto">--%>
<%--<table class="c_table goods_info" width="100%" border="0" cellspacing="5" cellpadding="0">--%>
 		<%--<c:if test="${isConfirmAudit=='0' or (isConfirmAudit=='1' and instMap['3'].status=='2')}">--%>
			<%--<tr>--%>
                <%--<td colspan="2" class="c_title">购物车信息</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
	            <%--<td colspan="2" class="c_context"></td>--%>
            <%--</tr>--%>
             <%--<tr>--%>
                <%--<td class="c_context rBorder" width="50%" valign="top">--%>
                   <%--&lt;%&ndash;<table class="pro" width="100%" border="1" cellspacing="0" cellpadding="0">&ndash;%&gt;--%>
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
                                <%--&lt;%&ndash;<c:set var="price" value="${!empty detail.upnum && detail.upnum ne 0 ? detail.uprice : detail.sprice}" scope="page" />&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<c:set var="num" value="${!empty detail.upnum && detail.upnum ne 0 ? detail.upnum : detail.spnum}" scope="page" />&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<td align="center"></td>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<td>${detail.prodid}</td>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<td>${detail.prodname}</td>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<td align="center">${detail.producttype}</td>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<td align="right">¥${price}</td>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<td align="center">${num}</td>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<td align="center">${detail.jifen}</td>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<td align="center">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<c:choose>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<c:when test="${detail.soldwith eq 1}">直接销售</c:when>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<c:when test="${detail.soldwith eq 2}">搭销</c:when>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<c:otherwise>赠品</c:otherwise>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;</c:choose>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                       <%--&lt;%&ndash;</tbody>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</table>&ndash;%&gt;--%>
                <%--</td>--%>
                <%--<c:if test="${!empty instMap['3'].bpmInstID }">--%>
	                <%--<td class="c_context" width="50%" valign="top">--%>
	                    <%--&lt;%&ndash;<table class="pro" width="100%" border="1" cellspacing="0" cellpadding="0">&ndash;%&gt;--%>
	                            <%--&lt;%&ndash;<thead>&ndash;%&gt;--%>
	                            <%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<th align="center" width="60px">套装信息</th>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<th scope="col">商品编号</th>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<th scope="col" style="min-width: 60px">商品名称</th>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<th align="center" width="40px">规格</th>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<th align="center" width="40px">价格</th>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<th align="center" width="40px">数量</th>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<th align="center" width="40px">积分</th>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<th align="center" width="60px">销售方式</th>&ndash;%&gt;--%>
	                            <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
	                            <%--&lt;%&ndash;</thead>&ndash;%&gt;--%>
	                            <%--&lt;%&ndash;<c:choose>&ndash;%&gt;--%>
	                                <%--&lt;%&ndash;<c:when test="${empty changeMap}">&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;<c:forEach var="detail" items="${order.orderdets}">&ndash;%&gt;--%>
	                                        <%--&lt;%&ndash;<c:set var="price" value="${!empty detail.upnum && detail.upnum ne 0 ? detail.uprice : detail.sprice}" scope="page" />&ndash;%&gt;--%>
	                                        <%--&lt;%&ndash;<c:set var="num" value="${!empty detail.upnum && detail.upnum ne 0 ? detail.upnum : detail.spnum}" scope="page" />&ndash;%&gt;--%>
	                                        <%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<td align="center"></td>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<td>${detail.prodid}</td>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<td>${detail.prodname}</td>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<td align="center">${detail.producttype}</td>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<td align="right">¥${price}</td>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<td align="center">${num}</td>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<td align="center">${detail.jifen}</td>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<td align="center">&ndash;%&gt;--%>
	                                                <%--&lt;%&ndash;<c:choose>&ndash;%&gt;--%>
	                                                    <%--&lt;%&ndash;<c:when test="${detail.soldwith eq 1}">直接销售</c:when>&ndash;%&gt;--%>
	                                                    <%--&lt;%&ndash;<c:when test="${detail.soldwith eq 2}">搭销</c:when>&ndash;%&gt;--%>
	                                                    <%--&lt;%&ndash;<c:otherwise>赠品</c:otherwise>&ndash;%&gt;--%>
	                                                <%--&lt;%&ndash;</c:choose>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
	                                        <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
	                                <%--&lt;%&ndash;</c:when>&ndash;%&gt;--%>
	                                  <%--&lt;%&ndash;<c:otherwise>&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;<c:forEach var="change" items="${changeMap}">&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<c:set var="highlight" value=""/>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<c:set var="hlprodtype" value=""/>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<c:set var="hlprice" value=""/>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<c:set var="hlnum" value=""/>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<c:set var="hljifen" value=""/>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<c:set var="hlsoldwith" value=""/>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<c:set var="changeNum" value="" />&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<c:set var="changeNum" value="${!empty change.upnum && change.upnum ne 0 ? change.upnum : change.spnum}" />&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<c:set var="changePrice" value="${!empty change.upnum && change.upnum ne 0 ? change.uprice : change.sprice}" />&ndash;%&gt;--%>
	                                        <%--&lt;%&ndash;<c:choose>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<c:when test="${change.modifyFlag eq '-1'}">&ndash;%&gt;--%>
	                                                <%--&lt;%&ndash;<c:set var="highlight" value="style=\"text-decoration: line-through\"" />&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;</c:when>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<c:when test="${change.modifyFlag eq '1'}">&ndash;%&gt;--%>
	                                                <%--&lt;%&ndash;<c:set var="highlight" value="style=\"color:red\"" />&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;</c:when>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<c:otherwise>&ndash;%&gt;--%>
	                                                <%--&lt;%&ndash;<c:forEach var="old" items="${order.orderdets}">&ndash;%&gt;--%>
	                                                    <%--&lt;%&ndash;<c:if test="${old.id eq change.id}">&ndash;%&gt;--%>
	                                                        <%--&lt;%&ndash;<c:if test="${old.producttype ne change.producttype}">&ndash;%&gt;--%>
	                                                            <%--&lt;%&ndash;<c:set var="hlprodtype" value="style=\"color:red\"" />&ndash;%&gt;--%>
	                                                        <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
	                                                        <%--&lt;%&ndash;<c:set var="oldPrice" value="${!empty old.upnum && old.upnum ne 0 ? old.uprice : old.sprice}" />&ndash;%&gt;--%>
	                                                        <%--&lt;%&ndash;<c:if test="${oldPrice ne changePrice}">&ndash;%&gt;--%>
	                                                            <%--&lt;%&ndash;<c:set var="hlprice" value="style=\"color:red\"" />&ndash;%&gt;--%>
	                                                        <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
	                                                        <%--&lt;%&ndash;<c:set var="oldNum" value="${!empty old.upnum && old.upnum ne 0 ? old.upnum : old.spnum}" />&ndash;%&gt;--%>
	<%--&lt;%&ndash;&ndash;%&gt;--%>
	                                                        <%--&lt;%&ndash;<c:if test="${oldNum ne changeNum}">&ndash;%&gt;--%>
	                                                            <%--&lt;%&ndash;<c:set var="hlnum" value="style=\"color:red\"" />&ndash;%&gt;--%>
	                                                        <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
	                                                        <%--&lt;%&ndash;<c:if test="${!empty old.jifen && old.jifen ne change.jifen}">&ndash;%&gt;--%>
	                                                            <%--&lt;%&ndash;<c:set var="hljifen" value="style=\"color:red\"" />&ndash;%&gt;--%>
	                                                        <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
	                                                        <%--&lt;%&ndash;<c:if test="${!empty change.jifen && change.jifen ne old.jifen}">&ndash;%&gt;--%>
	                                                            <%--&lt;%&ndash;<c:set var="hljifen" value="style=\"color:red\"" />&ndash;%&gt;--%>
	                                                        <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
	                                                        <%--&lt;%&ndash;<c:if test="${old.soldwith ne change.soldwith}">&ndash;%&gt;--%>
	                                                            <%--&lt;%&ndash;<c:set var="hlsoldwith" value="style=\"color:red\"" />&ndash;%&gt;--%>
	                                                        <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
	                                                    <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
	                                                <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;</c:otherwise>&ndash;%&gt;--%>
	                                        <%--&lt;%&ndash;</c:choose>&ndash;%&gt;--%>
	<%--&lt;%&ndash;&ndash;%&gt;--%>
	<%--&lt;%&ndash;&ndash;%&gt;--%>
	                                        <%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<td></td>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<td><span ${highlight}>${change.prodid}</span></td>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<td><span ${highlight}>${change.prodname}</span></td>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<td><span ${highlight}${hlprodtype}>${change.producttype}</span></td>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<td><span ${highlight}${hlprice}>¥${changePrice}</span></td>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<td><span ${highlight}${hlnum}>${changeNum}</span></td>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<td><span ${highlight}${hljifen}>${change.jifen}</span></td>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;<td align="left">&ndash;%&gt;--%>
	                                                <%--&lt;%&ndash;<c:choose>&ndash;%&gt;--%>
	                                                    <%--&lt;%&ndash;<c:when test="${change.soldwith eq 1}"><span ${highlight}${hlsoldwith}>直接销售</span></c:when>&ndash;%&gt;--%>
	                                                    <%--&lt;%&ndash;<c:when test="${change.soldwith eq 2}"><span ${highlight}${hlsoldwith}>搭销</span></c:when>&ndash;%&gt;--%>
	                                                    <%--&lt;%&ndash;<c:otherwise><span ${highlight}${hlsoldwith}>赠品</span></c:otherwise>&ndash;%&gt;--%>
	                                                <%--&lt;%&ndash;</c:choose>&ndash;%&gt;--%>
	                                            <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
	                                        <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
	                                <%--&lt;%&ndash;</c:otherwise>&ndash;%&gt;--%>
	                            <%--&lt;%&ndash;</c:choose>&ndash;%&gt;--%>
	                        <%--&lt;%&ndash;</table>&ndash;%&gt;--%>
	                    <%--&lt;%&ndash;<c:choose>&ndash;%&gt;--%>
	    				<%--&lt;%&ndash;<c:when test="${isManager and !empty instMap['3'].bpmInstID and instMap['3'].status=='0'}">&ndash;%&gt;--%>
	    					<%--&lt;%&ndash;<div id='div_order_cartinfo_command' style="display: block;">&ndash;%&gt;--%>
		                    	<%--&lt;%&ndash;<p class="mb10 mt10">&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<label><input type="radio" name="radio_order_cartinfo_command" id="radio_order_cartinfo_command_approve" value="1_<c:out value="${instMap['3'].bpmInstID}"/>"/>批准</label>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<label><input type="radio" name="radio_order_cartinfo_command" id="radio_order_cartinfo_command_reject" value="2_<c:out value="${instMap['3'].bpmInstID}"/>"/>驳回</label>&ndash;%&gt;--%>
		                    		<%--&lt;%&ndash;<input type="text" id="radio_order_cartinfo_command_text" style="display:none; margin-left:20px; width:60%"/>&ndash;%&gt;--%>
		                    		<%--&lt;%&ndash;<input type="text" id="radio_order_cartinfo_inst" style="display:none;" value="${instMap['3'].bpmInstID}"/>&ndash;%&gt;--%>
		                    	<%--&lt;%&ndash;</p>&ndash;%&gt;--%>
							<%--&lt;%&ndash;</div>&ndash;%&gt;--%>
						<%--&lt;%&ndash;</c:when>&ndash;%&gt;--%>
						<%--&lt;%&ndash;<c:when test="${!isManager and !empty instMap['3'].bpmInstID and instMap['3'].status=='0'}">&ndash;%&gt;--%>
	    					<%--&lt;%&ndash;<div style="display: block;">&ndash;%&gt;--%>
		                    	<%--&lt;%&ndash;<p class="mb10 mt10">&ndash;%&gt;--%>
		                    		<%--&lt;%&ndash;<div> <label color="red">待审批</label></div>&ndash;%&gt;--%>
		                    	<%--&lt;%&ndash;</p>&ndash;%&gt;--%>
							<%--&lt;%&ndash;</div>&ndash;%&gt;--%>
						<%--&lt;%&ndash;</c:when>&ndash;%&gt;--%>
						<%--&lt;%&ndash;<c:when test="${!empty instMap['3'].bpmInstID and instMap['3'].status=='1'}">&ndash;%&gt;--%>
							<%--&lt;%&ndash;<div> <label color="red">已审批</label></div>&ndash;%&gt;--%>
						<%--&lt;%&ndash;</c:when>&ndash;%&gt;--%>
						<%--&lt;%&ndash;<c:when test="${!empty instMap['3'].bpmInstID and instMap['3'].status=='2'}">&ndash;%&gt;--%>
							<%--&lt;%&ndash;<div> <label color="red">已驳回</label>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<span style="margin-left:20px;color:red">意见:  ${instMap['3'].approverRemark }</span>&ndash;%&gt;--%>
							<%--&lt;%&ndash;</div>&ndash;%&gt;--%>
						<%--&lt;%&ndash;</c:when>&ndash;%&gt;--%>
					<%--&lt;%&ndash;</c:choose>&ndash;%&gt;--%>
	                <%--</td>--%>
				<%--</c:if>--%>
            <%--</tr>--%>
            <%--<tr>--%>
	            <%--<td class="c_context" width="50%" valign="top"></td>--%>
	            <%--<td class="c_context" width="50%" valign="top">--%>

			<%--</td>--%>
         	<%--</tr>--%>
         <%--</c:if>--%>

	<%--<c:if test="${isConfirmAudit=='0' or (isConfirmAudit=='1' and instMap['5'].status=='2')}">--%>
            <%--<c:if test="${!empty card}">--%>
                <%--<tr>--%>
                    <%--<td colspan="2" class="c_title">信用卡</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td colspan="2" class="c_context"></td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td class="c_context rBorder" width="50%" valign="top">--%>
                    <%--<table class="pro" width="100%" border="1" cellspacing="0" cellpadding="0">--%>
                    <%--<thead>--%>
                    <%--<tr>--%>
                        <%--<th scope="col" width="40px">卡类型</th>--%>
                        <%--<th scope="col">持卡人</th>--%>
                        <%--<th scope="col">卡号</th>--%>
                        <%--<th scope="col">有效期</th>--%>
                        <%--<th scope="col">附加码</th>--%>
                        <%--<th scope="col">分期数</th>--%>
                    <%--</tr>--%>
                    <%--</thead>--%>
                    <%--<tbody>--%>
                        <%--<tr>--%>
                            <%--<td>${card.type}</td>--%>
                            <%--<td>${card.contactId}</td>--%>
                            <%--<td>${card.cardNumber}</td>--%>
                            <%--<td>${card.validDate}</td>--%>
                            <%--<td>${card.extraCode}</td>--%>
                            <%--<td>${order.laststatus}</td>--%>
                        <%--</tr>--%>
                    <%--</tbody>--%>
                    <%--</table>--%>
                    <%--</td>--%>

                    <%--<c:if test="${!empty instMap['5'].bpmInstID }">--%>
                        <%--<td class="c_context" width="50%" valign="top">--%>

	                        <%--<table class="pro" width="100%" border="1" cellspacing="0" cellpadding="0">--%>
	                        <%--<thead>--%>
	                        <%--<tr>--%>
	                            <%--<th scope="col" width="40px">卡类型</th>--%>
	                            <%--<th scope="col">持卡人</th>--%>
	                            <%--<th scope="col">卡号</th>--%>
	                            <%--<th scope="col">有效期</th>--%>
	                            <%--<th scope="col">附加码</th>--%>
	                            <%--<th scope="col">分期数</th>--%>
	                        <%--</tr>--%>
	                        <%--</thead>--%>
	                        <%--<tbody>--%>
	                        <%--<tr>--%>

	                                <%--<td><span <c:if test="${!empty cardChange.type && cardChange.type ne card.type}">style="color:red"</c:if>>${!empty cardChange.type ? cardChange.type : card.type}</span></td>--%>
	                            <%--<td><span <c:if test="${!empty cardChange.contactId && cardChange.contactId ne card.contactId}">style="color:red"</c:if>>${!empty cardChange.contactId ? cardChange.contactId : card.contactId}</span></td>--%>
	                            <%--<td><span <c:if test="${!empty cardChange.cardNumber && cardChange.cardNumber ne card.cardNumber}">style="color:red"</c:if>>${!empty cardChange.cardNumber ? cardChange.cardNumber : card.cardNumber}</span> </td>--%>
	                            <%--<td><span <c:if test="${!empty cardChange.validDate && cardChange.validDate ne card.validDate}">style="color:red"</c:if>>${!empty cardChange.validDate ? cardChange.validDate : card.validDate}</span></td>--%>
	                            <%--<td><span <c:if test="${!empty cardChange.extraCode && cardChange.extraCode ne card.extraCode}">style="color:red"</c:if>>${!empty cardChange.extraCode ? cardChange.extraCode : card.extraCode}</span></td>--%>
	                            <%--<td><span <c:if test="${!empty orderChange.laststatus && orderChange.laststatus ne order.laststatus}">style="color:red"</c:if>>${!empty orderChange.laststatus ? orderChange.laststatus : order.laststatus}</span></td>--%>
	                        <%--</tr>--%>
	                        <%--</tbody>--%>
	                        <%--</table>--%>
                        <%--<c:choose>--%>
	    				<%--<c:when test="${isManager and !empty instMap['5'].bpmInstID and instMap['5'].status=='0'}">--%>
	    					<%--<div id='div_order_card_command' style="display: block;">--%>
		                    	<%--<p class="mb10 mt10">--%>
                                    <%--<label><input type="radio" name="radio_order_card_command" id="radio_order_card_command_approve" value="1_<c:out value="${instMap['5'].bpmInstID}"/>"/>批准</label>--%>
                                    <%--<label><input type="radio" name="radio_order_card_command" id="radio_order_card_command_reject" value="2_<c:out value="${instMap['5'].bpmInstID}"/>"/>驳回</label>--%>
		                    		<%--<input type="text" id="radio_order_card_command_text" style="display:none; margin-left:20px; width:60%"/>--%>
		                    		<%--<input type="text" id="radio_order_card_inst" style="display:none;" value="${instMap['5'].bpmInstID}"/>--%>
		                    	<%--</p>--%>
							<%--</div>--%>
						<%--</c:when>--%>
						<%--<c:when test="${!isManager and !empty instMap['5'].bpmInstID and instMap['5'].status=='0'}">--%>
	    					<%--<div style="display: block;">--%>
		                    	<%--<p class="mb10 mt10">--%>
		                    		<%--<div> <label color="red">待审批</label></div>--%>
		                    	<%--</p>--%>
							<%--</div>--%>
						<%--</c:when>--%>
						<%--<c:when test="${!empty instMap['5'].bpmInstID and instMap['5'].status=='1'}">--%>
							<%--<div> <label color="red">已审批</label></div>--%>
						<%--</c:when>--%>
						<%--<c:when test="${!empty instMap['5'].bpmInstID and instMap['5'].status=='2'}">--%>
							<%--<div> <label color="red">已驳回</label>--%>
								<%--<span style="margin-left:20px;color:red">意见:  ${instMap['5'].approverRemark }</span>--%>
							<%--</div>--%>
						<%--</c:when>--%>
					<%--</c:choose>--%>
                        <%--</td>--%>
                    <%--</c:if>--%>

                <%--</tr>--%>
            <%--</c:if>--%>
         <%--<tr>--%>
             <%--<td colspan="2" class="c_context"></td>--%>
         <%--</tr>--%>
       <%--</c:if>--%>




            <%--<c:if test="${isConfirmAudit=='0' or (isConfirmAudit=='1' and isContactChangedAndRejected)}">--%>
             <%--<tr>--%>
                <%--<td colspan="2" class="c_title">收货信息</td>--%>
            <%--</tr>--%>
             <%--<tr>--%>
	            <%--<td colspan="2" class="c_context"></td>--%>
            <%--</tr>--%>
             <%--<tr>--%>
                <%--<td class="c_context rBorder" width="50%" valign="top">--%>
                  <%--<table class="pro" width="100%" border="1" cellspacing="0" cellpadding="0">--%>
                            <%--<thead>--%>
                            <%--<tr>--%>
                                <%--<th scope="col" width="40px">姓名</th>--%>
                                <%--<th scope="col">详细地址</th>--%>
                                <%--<th scope="col">详细地址</th>--%>
                                <%--<th scope="col">邮编</th>--%>
                            <%--</tr>--%>
                            <%--</thead>--%>
                            <%--<tbody>--%>
                            <%--<tr>--%>
                                <%--<c:set var="preAddress" value="${order.address.province.chinese}${order.address.city.cityname}${order.address.county.countyname}${order.address.area.areaname}" />--%>
                                <%--<td>${contact.name}</td>--%>
                                <%--<td>${preAddress}</td>--%>
                                <%--<td>${order.address.addressDesc}</td>--%>
                                <%--<td>${!empty order.address.area.zipcode ? order.address.area.zipcode : order.address.county.zipcode}</td>--%>
                            <%--</tr>--%>
                            <%--</tbody>--%>
                        <%--</table>--%>
                <%--</td>--%>
                <%--<c:if test="${isContactChanged}">--%>
	                <%--<td class="c_context" width="50%" valign="top">--%>
                        <%--<table>--%>
                            <%--<tr><td>--%>
	                    <%--<table class="pro" width="100%" border="1" cellspacing="0" cellpadding="0">--%>
	                            <%--<thead>--%>
	                            <%--<tr>--%>
	                                <%--<th scope="col" width="40px">姓名</th>--%>
	                                <%--<th scope="col">收货地址</th>--%>
	                                <%--<th scope="col">详细地址</th>--%>
	                                <%--<th scope="col">邮编</th>--%>
	                            <%--</tr>--%>
	                            <%--</thead>--%>
	                            <%--<tbody>--%>
	                                <%--<tr>--%>
	                                	<%--<td><span <c:if test="${!empty contactChange && contact.name ne contactChange.name }">style="color:red"</c:if>>${!empty contactChange ? contactChange.name : contact.name}</span></td>--%>
	                                     <%--<c:if test="${empty addressChange}">--%>
	                                    <%--<c:set var="preAddress" value="${order.address.province.chinese}${order.address.city.cityname}${order.address.county.countyname}${order.address.area.areaname}" />--%>
	                                    <%--<td>${preAddress}</td>--%>
	                                    <%--<td>${order.address.addressDesc}</td>--%>
	                                    <%--<td>${!empty order.address.area.zipcode ? order.address.area.zipcode : order.address.county.zipcode}</td>--%>
	                                     <%--</c:if>--%>

                                        <%--<c:if test="${!empty addressChange}">--%>
                                            <%--<c:set var="zipcode" value="${!empty order.address.area.zipcode ? order.address.area.zipcode : order.address.county.zipcode}" />--%>
                                            <%--<c:set var="zipcodeChange" value="${zipcode}" />--%>
                                            <%--<c:if test="${!empty addressChange.county && !empty addressChange.county.zipcode}">--%>
                                                <%--<c:set var="zipcodeChange" value="${addressChange.county.zipcode}" />--%>
                                            <%--</c:if>--%>
                                            <%--<c:if test="${!empty addressChange.area && !empty addressChange.area.zipcode}">--%>
                                                <%--<c:set var="zipcodeChange" value="${addressChange.area.zipcode}" />--%>
                                            <%--</c:if>--%>
	                                        <%--<c:set var="provinceChange" value="${!empty addressChange.province ? addressChange.province.chinese : order.address.province.chinese}" />--%>
                                            <%--<c:set var="cityChange" value="${!empty addressChange.city ? addressChange.city.cityname : order.address.city.cityname}" />--%>
                                            <%--<c:set var="countyChange" value="${!empty addressChange.county ? addressChange.county.countyname : order.address.county.countyname}" />--%>
                                            <%--<c:set var="areaChange" value="${!empty addressChange.area ? addressChange.area.areaname : order.address.area.areaname}" />--%>
                                            <%--<c:set var="preAddressChange" value="${provinceChange}${cityChange}${countyChange}${areaChange}" />--%>
                                            <%--<td><span <c:if test="${preAddress ne preAddressChange}">style="color:red"</c:if>>${preAddressChange}</span></td>--%>
                                            <%--<td><span <c:if test="${!empty addressChange.addressDesc && order.address.addressDesc ne addressChange.addressDesc}">style="color:red"</c:if>>${!empty addressChange.addressDesc ? addressChange.addressDesc : order.address.addressDesc}</span></td>--%>
                                            <%--<td><span <c:if test="${zipcodeChange ne zipcode}">style="color:red"</c:if>>${zipcodeChange}</span></td>--%>
                                        <%--</c:if>--%>
	                                <%--</tr>--%>
	                            <%--</tbody>--%>
                                <%--</table>--%>
                        <%--</td></tr>--%>
								<%--<c:choose>--%>
									<%--<c:when test="${!empty phoneChanges}">--%>
                                        <%--<tr><td>--%>
                                            <%--<table class="pro"  border="1" cellspacing="0" cellpadding="0">--%>
                                            <%--<thead>--%>
                                            <%--<tr>--%>
                                                <%--<th scope="col">新增电话</th>--%>
                                            <%--</tr>--%>
                                            <%--</thead>--%>
										<%--<c:forEach var="phoneChange" items="${phoneChanges}">--%>
											<%--<tr>--%>
												<%--<td><span style="color: red;">${phoneChange.phoneNum}</span></td>--%>
											<%--</tr>--%>
										<%--</c:forEach>--%>
                                        <%--</table></td></tr>--%>
									<%--</c:when>--%>
								<%--</c:choose>--%>
                            <%--<tr><td>--%>
	                        	<%--<c:choose>--%>
				    				<%--<c:when test="${isManager and isContactChanged and contactStatus=='0'}">--%>
				    					<%--<div id='div_order_contact_command' style="display: block;">--%>
					                    	<%--<p class="mb10 mt10" >--%>
                                                <%--<label><input type="radio" name="radio_order_contact_command" id="radio_order_contact_command_approve" value="1<c:out value="${contactInsts}"/>"/>批准</label>--%>
                                                <%--<label><input type="radio" name="radio_order_contact_command" id="radio_order_contact_command_reject" value="2<c:out value="${contactInsts}"/>"/>驳回</label>--%>
					                    		<%--<input type="text" id="radio_order_contact_command_text" style="display:none; margin-left:20px; width:60%"/>--%>
					                    	<%--</p>--%>
										<%--</div>--%>
									<%--</c:when>--%>
									<%--<c:when test="${!isManager and isContactChanged and contactStatus=='0'}">--%>
				    					<%--<div style="display: block;">--%>
					                    	<%--<p class="mb10 mt10">--%>
					                    		<%--<div> <label color="red">待审批</label></div>--%>
					                    	<%--</p>--%>
										<%--</div>--%>
									<%--</c:when>--%>
									<%--<c:when test="${isContactChanged and contactStatus=='1'}">--%>
										<%--<div> <label color="red">已审批</label></div>--%>
									<%--</c:when>--%>
									<%--<c:when test="${isContactChanged and contactStatus=='2'}">--%>
										<%--<div> <label color="red">已驳回</label>--%>
											<%--<span style="margin-left:20px;color:red">意见:  ${contactComment }</span>--%>
										<%--</div>--%>
									<%--</c:when>--%>
								<%--</c:choose>--%>
                            <%--</td></tr>--%>
                        <%--</table>--%>
							<%--</td>--%>
	            <%--</c:if>--%>


             <%--<tr>--%>
	            <%--<td colspan="2" class="c_context"></td>--%>
            <%--</tr>--%>
            <%--</c:if>--%>

            <%--<c:if test="${isConfirmAudit=='0' or (isConfirmAudit=='1' and instMap['4'].status=='2')}">--%>
             <%--<tr>--%>
                <%--<td colspan="2" class="c_title">订单备注</td>--%>
            <%--</tr>--%>
             <%--<tr>--%>
	            <%--<td colspan="2" class="c_context"></td>--%>
            <%--</tr>--%>
             <%--<tr>--%>
                <%--<td class="c_context rBorder" width="50%" valign="top">--%>
                  <%--<table border="0" cellspacing="0" cellpadding="0">--%>
                            <%--<tr>--%>
                                <%--<td valign="top">订单备注：</td>--%>
                                <%--<td><textarea id="o_note" style="width:330px"   rows="3" disabled>${order.note}</textarea></td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td>&nbsp;</td>--%>
                                <%--<td>&nbsp;</td>--%>
                            <%--</tr>--%>
                        <%--</table>--%>
                    <%--<!--     <div>使用积分：--%>
                            <%--<input id="o_ifen" type="text" class="readonly" value="${order.jifen}" disabled />--%>
                        <%--</div>-->--%>
                <%--</td>--%>
                <%--<c:if test="${!empty instMap['4'].bpmInstID }">--%>
	                <%--<td class="c_context" width="50%" valign="top">--%>
	                    <%--<table border="0" cellspacing="0" cellpadding="0">--%>
	                            <%--<tr>--%>
	                                <%--<td valign="top">订单备注：</td>--%>
	                                <%--<td>--%>
                                        <%--<c:set var="noteStyle" value="style=\"width:330px\""/>--%>
                                        <%--<c:if test="${!empty orderChange.note && orderChange.note ne order.note}">--%>
                                            <%--<c:set var="noteStyle" value="style=\"width:330px;color:red\""/>--%>
                                        <%--</c:if>--%>
	                                    <%--<textarea id="n_note" ${noteStyle}  cols="50" rows="3" disabled>${!empty orderChange.note ? orderChange.note : order.note}</textarea>--%>
	                                <%--</td>--%>
	                            <%--</tr>--%>
	                        <%--</table>--%>
	                   <%--<!--     <div>使用积分：--%>
	                            <%--<input id="n_jifen" class="readonly" <c:if test="${!empty orderChange.jifen}">style="color:red"</c:if> type="text" value="${!empty orderChange.jifen ? orderChange.jifen : order.jifen}" disabled />--%>
	                        <%--</div> -->--%>
	                  <%--<c:choose>--%>
	            		<%--<c:when test="${isManager and !empty instMap['4'].bpmInstID and instMap['4'].status=='0'}">--%>
	                    <%--<div id='div_order_remark_command' style="display: block;">--%>
	                    	<%--<p class="mb10 mt10" >--%>
                                <%--<label><input type="radio" name="radio_order_remark_command" id="radio_order_remark_command_approve" value="1_<c:out value="${instMap['4'].bpmInstID}"/>"/>批准</label>--%>
                                <%--<label><input type="radio" name="radio_order_remark_command" id="radio_order_remark_command_reject" value="2_<c:out value="${instMap['4'].bpmInstID}"/>"/>驳回</label>--%>
	                    		<%--<input type="text" id="radio_order_remark_command_text" style="display:none; margin-left:20px; width:60%"/>--%>
	                    		<%--<input type="text" id="radio_order_remark_inst" style="display:none;" value="${instMap['4'].bpmInstID}"/>--%>
	                    	<%--</p>--%>
						<%--</div>--%>
						<%--</c:when>--%>
	            		<%--<c:when test="${!isManager and !empty instMap['4'].bpmInstID and instMap['4'].status=='0'}">--%>
	    					<%--<div style="display: block;">--%>
		                    	<%--<p class="mb10 mt10">--%>
		                    		<%--<div> <label color="red">待审批</label></div>--%>
		                    	<%--</p>--%>
							<%--</div>--%>
						<%--</c:when>--%>
						<%--<c:when test="${!empty instMap['4'].bpmInstID and instMap['4'].status=='1'}">--%>
							<%--<div> <label color="red">已审批</label></div>--%>
						<%--</c:when>--%>
						<%--<c:when test="${!empty instMap['4'].bpmInstID and instMap['4'].status=='2'}">--%>
							<%--<div> <label color="red">已驳回</label>--%>
								<%--<span style="margin-left:20px;color:red">意见:  ${instMap['4'].approverRemark }</span>--%>
							<%--</div>--%>
						<%--</c:when>--%>
	            	<%--</c:choose>--%>

	                <%--</td>--%>
	            <%--</c:if>--%>

            <%--</tr>--%>
           <%--</c:if>--%>
         <%--<tr>--%>
             <%--<td colspan="2" class="c_context"></td>--%>
         <%--</tr>--%>
           <%--<c:if test="${isConfirmAudit=='0' or (isConfirmAudit=='1' and instMap['7'].status=='2')}">--%>
             <%--<tr>--%>
                <%--<td colspan="2" class="c_title">结算</td>--%>
            <%--</tr>--%>
             <%--<tr>--%>
	            <%--<td colspan="2" class="c_context"></td>--%>
            <%--</tr>--%>
             <%--<tr>--%>
                <%--<td class="c_context rBorder" width="50%" valign="top">--%>
                   <%--<p>--%>
                            <%--货款金额:&nbsp;¥${order.prodprice} + 运费:&nbsp;¥${order.mailprice} ---%>
                             <%--= 应付总额:&nbsp;¥${order.totalprice}--%>
                            <%--(获得积分:&nbsp;${order.jifen})--%>
                        <%--</p>--%>
                <%--</td>--%>
                <%--<c:if test="${!empty instMap['7'].bpmInstID or (!empty orderChange.prodprice && orderChange.prodprice ne order.prodprice) or (!empty orderChange.jifen && orderChange.jifen ne order.jifen) }">--%>
	                <%--<td class="c_context" width="50%" valign="top">--%>

	                    <%--<p>--%>
	                            <%--货款金额 :&nbsp;--%>
	                            <%--<span <c:if test="${!empty orderChange.prodprice && orderChange.prodprice ne order.prodprice}">style="color:red"</c:if>>¥${!empty orderChange.prodprice ? orderChange.prodprice : order.prodprice}</span>--%>
	                            <%--+ 运费:&nbsp;--%>
	                            <%--<span <c:if test="${!empty orderChange.mailprice && orderChange.mailprice ne order.mailprice}">style="color:red"</c:if>>¥${!empty orderChange.mailprice ? orderChange.mailprice : order.mailprice}</span>--%>
	                            <%--= 应付总额:&nbsp;--%>
	                            <%--<span <c:if test="${!empty orderChange.totalprice && orderChange.totalprice ne order.totalprice}">style="color:red"</c:if>>¥${!empty orderChange.totalprice ? orderChange.totalprice : order.totalprice}</span>--%>
	                            <%--(获得积分:&nbsp;<span <c:if test="${!empty orderChange.jifen && orderChange.jifen ne order.jifen}">style="color:red"</c:if>>${!empty orderChange.jifen ? orderChange.jifen : order.jifen}</span>)--%>
	                        <%--</p>--%>
	                 <%--<c:choose>--%>
	          			 <%--<c:when test="${isManager and !empty instMap['7'].bpmInstID and instMap['7'].status=='0'}">--%>
	                    <%--<div id='div_order_bill_command' style="display: block;">--%>
	                    	<%--<p class="mb10 mt10">--%>
	                    		<%--<label><input type="radio" name="radio_mailprice_command" id="radio_mailprice_command_approve" value="1_<c:out value="${instMap['7'].bpmInstID}"/>"/>批准 </label>--%>
                                    <%--<label><input type="radio" name="radio_mailprice_command" id="radio_mailprice_command_reject" value="2_<c:out value="${instMap['7'].bpmInstID}"/>"/>驳回</label>--%>
	                    		<%--<input type="text" id="radio_mailprice_command_text" style="display:none; margin-left:20px; width:60%"/>--%>
	                    		<%--<input type="text" id="radio_mailprice_inst" style="display:none;" value="${instMap['7'].bpmInstID}"/>--%>
	                    	<%--</p>--%>
						<%--</div>--%>
						<%--</c:when>--%>
						<%--<c:when test="${!isManager and !empty instMap['7'].bpmInstID and instMap['7'].status=='0'}">--%>
	    					<%--<div style="display: block;">--%>
		                    	<%--<p class="mb10 mt10">--%>
		                    		<%--<div> <label color="red">待审批</label></div>--%>
		                    	<%--</p>--%>
							<%--</div>--%>
						<%--</c:when>--%>
						<%--<c:when test="${!empty instMap['7'].bpmInstID and instMap['7'].status=='1'}">--%>
							<%--<div> <label color="red">已审批</label></div>--%>
						<%--</c:when>--%>
						<%--<c:when test="${!empty instMap['7'].bpmInstID and instMap['7'].status=='2'}">--%>
							<%--<div> <label color="red">已驳回</label>--%>
								<%--<span style="margin-left:20px;color:red">意见:  ${instMap['7'].approverRemark }</span>--%>
							<%--</div>--%>
						<%--</c:when>--%>
					<%--</c:choose>--%>
	                <%--</td>--%>
	             <%--</c:if>--%>

            <%--</tr>--%>
            <%--</c:if>--%>
         <%--<tr>--%>
             <%--<td colspan="2" class="c_context"></td>--%>
         <%--</tr>--%>
         <%--<c:if test="${isConfirmAudit=='0' or (isConfirmAudit=='1' and instMap['6'].status=='2')}">--%>
		    <%--<tr>--%>
                <%--<td colspan="2" class="c_title">发票</td>--%>
            <%--</tr>--%>
             <%--<tr>--%>
	            <%--<td colspan="2" class="c_context"></td>--%>
            <%--</tr>--%>
              <%--<tr>--%>
              <%--<c:set var="oldBill" value="${!empty order.bill ? order.bill : '0'}" />--%>
            <%--<c:set var="changeBill" value="${!empty orderChange.bill ? orderChange.bill : '0'}" />--%>
            <%--<c:set var="oldTitle" value="${!empty order.invoicetitle ? order.invoicetitle : ''}" />--%>
            <%--<c:set var="changeTitle" value="${!empty orderChange.invoicetitle ? orderChange.invoicetitle : ''}" />--%>
            <%--<c:set var="hlbill" value=""/>--%>
            <%--<c:set var="hltitle" value=""/>--%>
            <%--<c:if test="${oldBill ne changeBill}">--%>
                <%--<c:set var="hlbill" value="style=\"color:red\"" />--%>
            <%--</c:if>--%>
            <%--<c:if test="${oldTitle ne changeTitle}">--%>
                  <%--<c:set var="hltitle" value="style=\"color:red\"" />--%>
            <%--</c:if>--%>
                <%--<td class="c_context rBorder" width="50%" valign="top">--%>
                  <%--<span><c:choose>--%>
                <%--<c:when test="${'1' eq order.bill}">--%>
                    <%--需要--%>
                <%--</c:when>--%>
                    <%--<c:otherwise>--%>
                        <%--不需要--%>
                    <%--</c:otherwise>--%>
                    <%--</c:choose>--%>
                    <%--&nbsp;发票抬头:&nbsp;${order.invoicetitle} </span>--%>
                 <%--</td>--%>
                 <%--<c:if test="${!empty instMap['6'].bpmInstID }">--%>
                <%--<td class="c_context" width="50%" valign="top">--%>
                      <%--<c:choose>--%>
                     <%--<c:when test="${'1' eq orderChange.bill}">--%>
                         <%--<span ${hlbill}>需要</span>--%>
                     <%--</c:when>--%>
                     <%--<c:otherwise>--%>
                         <%--<span ${hlbill}>不需要</span>--%>
                     <%--</c:otherwise>--%>
                 <%--</c:choose>--%>
                    <%--&nbsp;发票抬头:&nbsp;<span ${hltitle}>${orderChange.invoicetitle} </span>--%>

                    <%--<c:choose>--%>
	            		<%--<c:when test="${isManager and !empty instMap['6'].bpmInstID and instMap['6'].status=='0'}">--%>
	                    <%--<div id='div_order_invoice_command' style="display: block;">--%>
	                    	<%--<p class="mb10 mt10" >--%>
                                <%--<label><input type="radio" name="radio_order_invoice_command" id="radio_order_invoice_command_approve" value="1_<c:out value="${instMap['6'].bpmInstID}"/>"/>批准</label>--%>
                                <%--<label><input type="radio" name="radio_order_invoice_command" id="radio_order_invoice_command_reject" value="2_<c:out value="${instMap['6'].bpmInstID}"/>"/>驳回</label>--%>
	                    		<%--<input type="text" id="radio_order_invoice_command_text" style="display:none; margin-left:20px; width:60%"/>--%>
	                    		<%--<input type="text" id="radio_order_invoice_inst" style="display:none;" value="${instMap['6'].bpmInstID}"/>--%>
	                    	<%--</p>--%>
						<%--</div>--%>
						<%--</c:when>--%>
	            		<%--<c:when test="${!isManager and !empty instMap['6'].bpmInstID and instMap['6'].status=='0'}">--%>
	    					<%--<div style="display: block;">--%>
		                    	<%--<p class="mb10 mt10">--%>
		                    		<%--<div> <label color="red">待审批</label></div>--%>
		                    	<%--</p>--%>
							<%--</div>--%>
						<%--</c:when>--%>
						<%--<c:when test="${!empty instMap['6'].bpmInstID and instMap['6'].status=='1'}">--%>
							<%--<div> <label color="red">已审批</label></div>--%>
						<%--</c:when>--%>
						<%--<c:when test="${!empty instMap['6'].bpmInstID and instMap['6'].status=='2'}">--%>
							<%--<div> <label color="red">已驳回</label>--%>
								<%--<span style="margin-left:20px;color:red">意见:  ${instMap['6'].approverRemark }</span>--%>
							<%--</div>--%>
						<%--</c:when>--%>
	            	<%--</c:choose>--%>
                <%--</td>--%>
                <%--</c:if>--%>
            <%--</tr>--%>
       <%--</c:if>--%>
            <%--<tr>--%>
            <%--<td class="c_context" width="50%" valign="top"></td>--%>
            <%--<td class="c_context" width="50%" valign="top">--%>

		<%--</td>--%>
		<%--</tr>--%>
		<%--<c:if test="${isConfirmAudit=='0' or (isConfirmAudit=='1' and instMap['8'].status=='2')}">--%>
		<%--<tr>--%>
                 <%--<td colspan="2" class="c_title">配送</td>--%>
            <%--</tr>--%>
             <%--<tr>--%>
	            <%--<td colspan="2" class="c_context"></td>--%>
            <%--</tr>--%>
              <%--<tr>--%>

                <%--<td class="c_context rBorder" width="50%" valign="top">--%>
                    <%--预计出货仓库:&nbsp;${order.warehouseName}<br/>--%>
                  <%--预计送货公司:&nbsp;${order.entityName}--%>
                <%--</td>--%>
                <%--<c:if test="${!empty instMap['8'].bpmInstID }">--%>
	                <%--<td class="c_context" width="50%" valign="top">--%>
	                    <%--<c:set var="hlems" value=""/>--%>
	                    <%--<c:set var="hlcheck" value=""/>--%>
	                    <%--<c:if test="${order.reqEMS ne orderChange.isreqems}">--%>
	                        <%--<c:set var="hlems" value="style=\"color:red\"" />--%>
	                    <%--</c:if>--%>
	                    <%--<c:if test="${orderChange.isreqems eq 'Y'}">--%>
	                        <%--<c:set var="hlcheck" value="checked=\"checked\"" />--%>
	                    <%--</c:if>--%>
	                    <%--<label class="fl"><input class="fl" name="phoneType" type="radio" disabled="disabled" maxlength="20" value="4" ${hlcheck}/>--%>
	                  <%--<span ${hlems}>指定ems配送</span></label><br/>--%>
	                    <%--<span ${hlems}>修改说明${ instMap['8'].remark }</span>--%>
	                    <%--<c:choose>--%>
							<%--<c:when--%>
								<%--test="${isManager and !empty instMap['8'].bpmInstID and instMap['8'].status=='0'}">--%>
								<%--<div id='div_order_ems_command' style="display: block;">--%>
									<%--<p class="mb10 mt10">--%>
                                        <%--<label><input type="radio" name="radio_order_ems_command" id="radio_order_ems_command_approve" value="1_<c:out value="${instMap['8'].bpmInstID}"/>" />批准</label>--%>
                                        <%--<label><input type="radio" name="radio_order_ems_command" id="radio_order_ems_command_reject" value="2_<c:out value="${instMap['8'].bpmInstID}"/>" />驳回</label>--%>
										<%--<input type="text" id="radio_order_ems_command_text" style="display: none; margin-left: 20px; width: 60%" />--%>
										<%--<input type="text" id="radio_order_ems_inst" style="display:none;" value="${instMap['8'].bpmInstID}"/>--%>
									<%--</p>--%>
								<%--</div>--%>
							<%--</c:when>--%>
							<%--<c:when--%>
								<%--test="${!isManager and !empty instMap['8'].bpmInstID and instMap['8'].status=='0'}">--%>
								<%--<div style="display: block;">--%>
									<%--<p class="mb10 mt10">--%>
									<%--<div>--%>
										<%--<label color="red">待审批</label>--%>
									<%--</div>--%>
									<%--</p>--%>
								<%--</div>--%>
							<%--</c:when>--%>
							<%--<c:when--%>
								<%--test="${!empty instMap['8'].bpmInstID and instMap['8'].status=='1'}">--%>
								<%--<div>--%>
									<%--<label color="red">已审批</label>--%>
								<%--</div>--%>
							<%--</c:when>--%>
							<%--<c:when--%>
								<%--test="${!empty instMap['8'].bpmInstID and instMap['8'].status=='2'}">--%>
								<%--<div>--%>
									<%--<label color="red">已驳回</label> <span--%>
										<%--style="margin-left: 20px; color: red">意见:--%>
										<%--${instMap['8'].approverRemark }</span>--%>
								<%--</div>--%>
							<%--</c:when>--%>
						<%--</c:choose>--%>
	                <%--</td>--%>
	             <%--</c:if>--%>
			<%--</tr>--%>
            <%--</c:if>--%>
         <%--<tr>--%>
             <%--<td colspan="2" class="c_context"></td>--%>
         <%--</tr>--%>
            <%--<c:if test="${isConfirmAudit=='0' or (isConfirmAudit=='1' and instMap['9'].status=='2')}">--%>
            <%--<tr>--%>
                 <%--<td colspan="2" class="c_title">订单类型</td>--%>
            <%--</tr>--%>
             <%--<tr>--%>
	            <%--<td colspan="2" class="c_context"></td>--%>
            <%--</tr>--%>
              <%--<tr>--%>
             <%--<c:set var="hlordertype" value=""/>--%>
            <%--<c:if test="${order.ordertypeName ne orderChange.ordertypeName}">--%>
                <%--<c:set var="hlordertype" value="style=\"color:red\"" />--%>
            <%--</c:if>--%>
                <%--<td class="c_context rBorder" width="50%" valign="top">--%>
                             <%--<span> ${order.ordertypeName}</span>--%>
                <%--</td>--%>
                <%--<c:if test="${!empty instMap['9'].bpmInstID }">--%>
	                <%--<td class="c_context" width="50%" valign="top">--%>
	                         <%--<span ${hlordertype}>${orderChange.ordertypeName}</span>--%>
	                <%--</td>--%>
	            <%--</c:if>--%>
            <%--</tr>--%>
            <%--</c:if>--%>
         <%--<tr>--%>
             <%--<td colspan="2" class="c_context"></td>--%>
         <%--</tr>--%>
<%--</table>--%>

<%--</div>--%>
        <p class="mb10 mt10" align="center">
	        <a id="a_submitInstance" class="window_btn" href="javascript:void(0)" onclick="orderAuditSubmit()">确定</a>
	        <a id="a_cancelInstance" class="window_btn ml10" href="javascript:void(0)">关闭</a>
	        <a id="a_agreeRejection" class="window_btn" href="javascript:void(0)">完成</a>
	        <a id="a_disAgreeRejection" class="window_btn ml10" href="javascript:void(0)">修改订单</a>
        </p>

</body>
</html>