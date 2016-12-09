<!--
http://union.yihaodian.com/passport/viewBankInfo.do
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>


<div class="admin_logout">
    <c:if test="${not empty pageContext.request.remoteUser}">
        <span class="admintil"><sec:authentication property="principal.username"/></span> | <a href="<c:url value='/logout'/>">Logout</a>
    </c:if>
    <span class="admin_tip">&#9670 </span>
</div>

<div class="lft_nav">
        <h1>配置管理</h1>
        <ul>

            <sec:authorize ifAllGranted="OMSPromotion">
            <li><a href="<c:url value='/admin/promotions'/>">促销管理</a></li>
            </sec:authorize>
            <!--
            <li><a href="<c:url value='/admin/auditLogs'/>">操作日志</a></li>
            <li><a href="<c:url value='/admin/company'/>">送货公司</a></li>
            <li><a href="<c:url value='/admin/OrderTypeSmsSendLogs'/>">短信配置</a></li>
            <li><a href="<c:url value='/admin/promotions'/>">目录册</a></li>
            -->
        </ul>

</div>