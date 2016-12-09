<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>话务分配系统-登录</title>

<link href="${ctx}/static/style/global.css" rel="stylesheet" type="text/css" />
<style type="text/css">
a {
	color: #0b7aae;
}

input.primary,.button,.btnPrimary {
	background: #3198d8;
	border: 1px solid #4176ba;
}

.button,.button,.btnPrimary,.btnPrimary {
	border: 1px solid, #1c78b1(0.11, 0.471, 0.694);
	background: #2e8ec9;
	background: -moz-linear-gradient(bottom, #2789c7 0%, #3f9fd9 100%);
	background: -webkit-gradient(linear, left bottom, left top, color-stop(0%, #2789c7),
		color-stop(100%, #3f9fd9));
	background: -webkit-linear-gradient(bottom, #2789c7 0%, #3f9fd9 100%);
	background: -o-linear-gradient(bottom, #2789c7 0%, #3f9fd9 100%);
	background: -ms-linear-gradient(bottom, #2789c7 0%, #3f9fd9 100%);
	background: linear-gradient(bottom, #2789c7 0%, #3f9fd9 100%) bottom(0.153,
		0.537, 0.78) to top(0.247, 0.624, 0.851) filter: progid:DXImageTransform.Microsoft.gradient(
		 startColorstr='#3f9fd9', endColorstr='#2789c7', GradientType=0);
}

.button:hover,.button:focus,.btnPrimary:hover,.btnPrimary:focus {
	background: #278ac7( 0.153, 0.541, 0.78)
}

body {
	background-color: #145b7d;
}

#footer,#footer a {
	color: #ffffff;
}

#content {
	background-color: #ffffff;
}

#content {
	border: 1px solid #CCCCCC;
	border-radius: 3px;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
	padding: 10px;
}

#left_side {
	float: left;
	width: 320px;
}

#right_side {
	text-align: right;
	vertical-align: top;
}
</style>

<script type="text/javascript" src="${ctx}/static/js/jquery.min.js"></script>
<script type="text/javascript">
	function checkCaps(b) {
	    var a = 0, c = !1, a = document.all ? b.keyCode : b.which, c = b.shiftKey;
	    b = document.getElementById("pwcaps");
	    var d = 65 <= a && 90 >= a, a = 97 <= a && 122 >= a;
	    if (d && !c || a && c)b.style.display = "block"; else if (a && !c || d && c)b.style.display = "none"
	};
</script>
</head>

<body>

	<div id="login">
		<div id="login_wrapper">
			<div id="left_side">
				<div id="login_component">
					<div id="logo_wrapper">
						<a href="#"><img id="logo" class="logo_repository"
							src="static/images/logo_telephoneTraffic.png"
							border="0" /></a>
					</div>
					<div id="loginwidget">
						<form name="login" method="post" autocomplete="off"
							novalidate="novalidate"
							action="<c:url value='/j_security_check'/>" target="_top">
							<fieldset style="display: none">

								<input type="hidden" name="un" value="" /> <input type="hidden"
									name="width" value="" /> <input type="hidden" name="height"
									value="" /> <input type="hidden" name="hasRememberUn"
									value="true" /> <input type="hidden" name="startURL" value="" />
								<input type="hidden" name="loginURL" value="" /> <input
									type="hidden" name="loginType" value="" /> <input
									type="hidden" name="useSecure" value="true" /> <input
									type="hidden" name="local" value="" /> <input type="hidden"
									name="lt" value="standard" /> <input type="hidden" name="qs"
									value="" /> <input type="hidden" name="locale" value="cn" />
								<input type="hidden" name="oauth_token" value="" /> <input
									type="hidden" name="oauth_callback" value="" /> <input
									type="hidden" name="login" value="" /> <input type="hidden"
									name="serverid" value="" /> <input type="hidden"
									name="display" value="page" />
							</fieldset>
							<div class="loginError">
								<c:if test="${sessionScope.USER_LOGIN_EXCEPTION_MSG != null}">
                            ${sessionScope.USER_LOGIN_EXCEPTION_MSG}
                            </c:if>
							</div>
							<div class="loginbox_container"
								onClick="document.login.j_username.focus();">
								<div
									onclick="document.getElementById('j_username').value = '';this.style.display='none';document.login.un.focus();"
									id="clrUsr" class="clrField">&nbsp;</div>
								<div id="usrn">
									<div>
										<label for="j_username" class="zen-assistiveText">用户名</label>
										<input id="j_username" placeholder="用户名" name="j_username"
											type="text" value="${userName}" tabindex="1" class="input"
											onkeyup="if(this.value != ''){document.getElementById('clrUsr').style.display='block';}else{document.getElementById('clrUsr').style.display='none';}" />
									</div>
								</div>
							</div>
							<div class="loginbox_container"
								onclick="document.login.j_password.focus();">
								<div
									onclick="document.getElementById('j_password').value = '';this.style.display='none';document.login.j_password.focus();"
									id="clrPw" class="clrField">&nbsp;</div>
								<div id="pswd">
									<div>
										<label for="j_password" class="zen-assistiveText">密码</label> <input
											type="password" placeholder="密码" name="j_password"
											id="j_password" value="${pwd }" tabindex="2" class="input"
											onkeypress="checkCaps(event)" autocomplete="off"
											onkeyup="if(this.value != ''){document.getElementById('clrPw').style.display='block';}else{document.getElementById('clrPw').style.display='none';} " />
									</div>
								</div>
							</div>
							<div id="pwcaps" class="loginbox_container" style="display: none">
								<img id="pwcapsicon" alt="打开了 Caps Lock！" width="16" height="16" />
								打开了 Caps Lock！
							</div>
							<div class="loginbox_container">
								<%--<input  type="submit" value="登&nbsp;录" name="Login" id="Login"--%>
								<button class="button" id="Login" name="Login">
									<span class="label">登&nbsp;&nbsp;&nbsp;录</span>
								</button>
							</div>
							<div class="loginbox_container">
								<div id="rem" class="wrapper_remember">
									<input type="checkbox" id="rememberUn" name="rememberUn">
										&nbsp;<label for="rememberUn">记住用户名</label>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div id="right_side">
				<div id="marketing"><img src="/static/images/distri-logo.jpg"></div>
			</div>
		</div>
		<div id="footer"></div>
	</div>
</body>
</html>
