<!--
http://union.yihaodian.com/passport/viewBankInfo.do
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<ul id="leftPage">
<%--<li><a id="newCustomer" class="newCustomer leftMenu" name="/inbound/inbound"  multiTab="false" tab="">新建客户</a></li>--%>
<li><a class="customerQuery" href="javascript:queryC()">查询客户</a></li>
<%--<li><a class="delivery" href="javascript:openReminder()">催送货</a></li>--%>
<%--<li><a class="repository" href="${knowledgeUrl}" target="_blank">知识库</a></li>--%>
<%--<li><a class="note" href="javascript:openSms()" >短信</a></li>--%>
<%--<li><a id="inventory" class="inventory leftMenu" name="/inventory/summary"  multiTab="false" tab="">库存查看</a></li>--%>
<%--<li><a class="message" href="javascript:openMessage()">消息管理</a></li>--%>
<%--<li><a id="m_callback" class=" leftMenu" name="/callback/callback"  multiTab="false">CallBack</a></li>--%>
<%--<li><a id="callbackAssign" class="phoneBook" href="${distributionUrl}" target="_blank">话务分配管理</a></li>--%>
<li><a id="complain" class="complain leftMenu" name="/complain/complain"  multiTab="false" href="javascript:void(0)" >生成投诉</a></li>

 <%--<sec:authorize ifAllGranted="CTI_SESSION_MANAGER">--%>
<%--<li><a id="userManagement" class="orderTaker leftMenu" name="/userManagement/userManagement"  multiTab="false">在线用户管理</a></li>--%>

   <%--</sec:authorize>--%>
</ul>