<%@ include file="/common/taglibs.jsp"%>
<div class="header">
    <div class="headerint">
        <div class="loginint">
        <a href="<c:url value="/logout"/>" class="current">éåº</a>
            <!--  
            <c:if test="${empty pageContext.request.remoteUser}"> <a href="<c:url value="/login"/>" class="current"><fmt:message key="login.title"/></a> </c:if>
            <c:if test="${not empty pageContext.request.remoteUser}">
                <b><sec:authentication property="principal.username"/></b> |
                <a href="<c:url value="/logout"/>" class="current"><fmt:message key="logout.title"/></a>
            </c:if>
            -->
        </div>
    </div>
</div>