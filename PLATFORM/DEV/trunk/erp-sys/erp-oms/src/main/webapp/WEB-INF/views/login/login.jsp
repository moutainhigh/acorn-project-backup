<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>OMS-登录</title>
<link href="/static/style/ms_layout.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/static/scripts/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="/static/scripts/tracker_top.js"></script>
<script type="text/javascript" src="/static/scripts/login.js"></script>
</head>

<body>
<div class="warp">
	<div class="g_block"></div>
    <div>
    	<div class="flt dl_block"><span></span></div>
    	<div class="flr dr_block"><div class="do"><h1></h1>
        <div class="do_b">
            <form method="post" id="loginForm" action="<c:url value='/j_security_check'/>" onsubmit="return validateForm(this)">
              <p class="error">
               <c:if test="${param.error != null}">
                
                    <img width="15" height="15" src="<c:url value="/static/images/er_bg.png"/>" alt="密码错误" class="icon"/>
              			  无效的用户名或密码！
                        <%--${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}--%>
                
               
               
                 </c:if>
                  </p>
                <p><label  for="j_username">用户名:&nbsp;</label></p>
                <p>
                <input type="text" class="w_120" name="j_username" id="j_username" value="${userName }" tabindex="1"/></p>
                <p style="margin-top:10px;"><label  for="j_password" >密&nbsp;&nbsp;&nbsp;码:&nbsp;</label> 
                </p>
                <p>
                <input type="password" class="w_120" name="j_password" id="j_password" value="${pwd }" tabindex="2"/></p>
                    <p class="txt_c rid" >
                    <c:if test="${appConfig['rememberMeEnabled']}">
                    <label>
                    <input type="checkbox" name="_spring_security_remember_me" id="rememberMe" tabindex="3"/>记住用户名
                    </label>
                    </c:if>
                    </p>
                <input class="l_Btn"  value="" type="submit" />
                </form>
           </div>
           <div class="do_f"></div>
           </div>
           
           </div>
        
    </div>

</div>
</body>
</html>
