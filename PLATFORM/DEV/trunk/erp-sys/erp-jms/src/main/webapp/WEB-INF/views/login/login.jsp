<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<title>OMS-登录</title>
 <link href="/static/style/ms_layout.css" rel="stylesheet" type="text/css" />
</head>

<body>

    <div style="margin-top: 160px">
        <h1>OMS系统登录</h1>
        <div style="padding: 20px">
            <form method="post" id="loginForm" action="<c:url value='/j_security_check'/>" onsubmit="saveUsername(this);return validateForm(this)">
                <p><c:if test="${param.error != null}">
                <li class="error">
                    <img src="<c:url value="/static/images/iconWarning.gif"/>"
                         alt="密码错误" class="icon"/>
                无效的用户名或密码。
                        <%--${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}--%>
                </li>
                </c:if>
                </p>
                <p><label  for="j_username">用户名:&nbsp;</label><input type="text" class="w_120" name="j_username" id="j_username" value="${userName }" tabindex="1"/><span class="F_red"> *</span></p>
                <p><label  for="j_password" >密&nbsp;&nbsp;&nbsp;码:&nbsp;</label> <input type="password" class="w_120" name="j_password" id="j_password" value="${pwd }" tabindex="2"/><span class="F_red"> *</span></p>
                <c:if test="${appConfig['rememberMeEnabled']}">
                    <p class="txt_c" > <input type="checkbox" name="_spring_security_remember_me" id="rememberMe" tabindex="3"/>

                    </p>
                </c:if>
                <input  value="登录" type="submit" />
            </form>
           </div>
    </div>


</body>
</html>
