<!--
http://union.yihaodian.com/passport/viewBankInfo.do
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="/common/taglibs.jsp"%>

<!-- 
<div class="admin_logout">
    <c:if test="${not empty pageContext.request.remoteUser}">
        <span class="admintil"><sec:authentication property="principal.username"/></span> | <a href="<c:url value='/logout'/>">Logout</a>
    </c:if>
    <span class="admin_tip">&#9670 </span>
</div>
 -->
<div class="lft_nav">
        <h1>主页</h1>
        <ul>
            <li><a href="<c:url value='/index'/>">首页</a></li>
        </ul>
        <sec:authorize access="hasAnyRole('ADMIN','GROUPADMIN')">
         <h1>客户管理</h1>
        <ul>
            <li><a href="<c:url value='/customer/init'/>">客户群管理</a></li>
        </ul>
        </sec:authorize>
        <h1>营销管理</h1>
        <ul>
            <li><a href="<c:url value='/campaign/init'/>">营销计划管理</a></li>
        </ul>


		<h1>渠道管理</h1>
        <ul>
            <li><a href="<c:url value='/sms/templateIndex'/>">SMS模版管理</a></li>
            <li><a href="<c:url value='/discourse/discourseIndex'/>">呼叫中心话术维护</a></li>
        </ul>
        
		<h1>营销监控</h1>
        <ul>
            <li><a href="<c:url value='/sms/campaignMonitorInit'/>">短信速率监控</a></li>
            <li><a href="<c:url value='/sms/sendList'/>">短信发送管理</a></li>
            <li><a href="<c:url value='/sms/index'/>">短信回复列表</a></li>
           <!--  <li><a href="<c:url value='/sms/detailsIndex'/>">短信发送详情列表</a></li>-->
            <li><a href="<c:url value='/recommend/init'/>">产品推荐配置</a></li>
        </ul>
         
        <h1>优惠券管理</h1>
        <ul>
            <li><a href="<c:url value='/coupon/init'/>">优惠券配置管理</a></li>
            <li><a href="<c:url value='/coupon/couponCrInit'/>">优惠券详情</a></li>
        </ul>
         <sec:authorize access="hasAnyRole('ADMIN','GROUPADMIN')">
	        <h1>系统监控</h1>
	        <ul>
	            <li><a href="<c:url value='/system/customerMonitorInit'/>">客户群监控</a></li>
	            <li><a href="<c:url value='/system/quartzInit'/>">定时任务监控</a></li>
	        </ul>
         </sec:authorize>
</div>