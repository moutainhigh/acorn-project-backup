<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.Random" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.joda.time.DateTime" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.chinadrtv.com/acron/tags" prefix="acron" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="now" value="<%=new Date()%>"/>
<c:set var="before7now" value="<%= new Date(new Date().getTime()  - 86400000L * 6) %>"/>
<c:set var="before30now" value="<%= new Date(new Date().getTime() - 86400000L * 29) %>"/>
<fmt:formatDate var="today" value="${now}" pattern="yyyy-MM-dd" />
<fmt:formatDate var="before7Day" value="${before7now}" pattern="yyyy-MM-dd" />
<fmt:formatDate var="before30Day" value="${before30now}" pattern="yyyy-MM-dd" />
<%
Integer rnd = new Random().nextInt();
request.setAttribute("rnd", rnd);
%>