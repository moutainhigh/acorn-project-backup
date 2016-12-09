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
<title>Marketing-登录</title>
<link href="static/style/ms_layout.css" rel="stylesheet" type="text/css" />

</head>

<body>
<div id="wrap" class="wrap">
	<div id="do_b" class="do_b">
		<div class="head clearfix">
		<span><img width="55" height="56" src="static/images/logo.png"/></span>
		<span style="margin-left:10px">
			<div class="en_name" >CRM-Marketing Management System</div>
			<div class="zh_name">客户关系管理-营销管理系统</div></span>
		</div>
		<div class="f_body">
			<form method="post" id="loginForm" action="<c:url value='/j_security_check'/>" onsubmit="saveUsername(this);return validateForm(this)">
              
                
                <p class="clearfix">
                <label style="width:80px"  for="j_username">用户名:&nbsp;</label>
                <input type="text" class="w80 l_text" name="j_username" id="j_username" value="${userName }" tabindex="1"/></p>
               
                <p class="clearfix" style="padding-bottom: 0;">
                <label style="width:80px"  for="j_password" >密&nbsp;&nbsp;码:&nbsp;</label> 
                <input type="password" class="w80 l_text" name="j_password" id="j_password" value="${pwd }" tabindex="2"/></p>
                    <p class="txt_c rid clearfix" >
                	
                	<span class="error">
                	<c:if test="${param.error != null}">
                
                    <img src="<c:url value="/static/images/iconWarning.gif"/>"
                         alt="密码错误" class="icon"/>
                无效的用户名或密码。
                        <%--${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}--%>
                
               
               
                 </c:if>
                	</span>
                	
                    </p>
                    <p style="padding:0" class="rid clearfix"><span>
                	  <!--<c:if test="${appConfig['rememberMeEnabled']}">-->
                    <label class="reme">
                    <input type="checkbox" name="_spring_security_remember_me" id="rememberMe" tabindex="3" class="flt" />记住用户名
                    </label>
					 <!--   </c:if>-->
                	</span>  <input class="l_Btn"  value="" type="submit" /></p>
                
                
                </form>
		</div>
	</div>
  </div>
<script type="text/javascript" src="static/scripts/login.js"></script>
</body>
</html>
