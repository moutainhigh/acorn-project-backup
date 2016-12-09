<!--
http://union.yihaodian.com/passport/viewBankInfo.do
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
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
            <li><a href="<c:url value='/order/fileUpload'/>">首页</a></li>
        </ul>
        
         <h1>客户管理</h1>
        <ul>
            <li><a href="<c:url value='/customer/init'/>">客户群管理</a></li>
        </ul>

		<h1>渠道管理</h1>
        <ul>
            <li><a href="<c:url value='/sms/templateIndex'/>">SMS模版管理</a></li>
        </ul>
        
		<h1>营销执行管理</h1>
        <ul>
            <li><a href="<c:url value='/sms/sendList'/>">短信发送管理</a></li>
            <li><a href="<c:url value='/sms/index'/>">短信回复列表</a></li>
        </ul>
</div>