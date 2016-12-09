<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Sales-登录</title>


    <link href="${ctx}/static/style/login.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/static/style/easyui.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/static/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/login.js"></script>
    <script type="text/javascript">
    if (window != top) top.location.href = location.href;


        $(function() {

            $('#j_username').bind({
                blur: function() {
                    var _this = $(this);
                    if (_this.val() != '') {
                        $.getJSON("${ctx}/cti/getCtiInfo/"+_this.val(), function(json){;
                            $('#j_cti_server').val(json.areaCode);
                        });

                    }
                }
            });
        })

    </script>
</head>

<body>
<div id="login">
    <div id="login_wrapper">
        <div id="right_side">
            <div id="login_component">
                <div id="logo_wrapper"><img id="logo" class="logo_repository"
                                                        src="/static/images/logo_sales.png"
                                                         border="0"/>
                </div>
                <div id="loginwidget">
                    <form name="login" method="post" autocomplete="off" novalidate="novalidate" onsubmit="beforeSubmit('${ctx}');return validateForm(this)" action="${ctx}/do_login"
                          target="_top" >
                        <div id="loginError" class="loginError">
                            <c:if test="${sessionScope.USER_LOGIN_EXCEPTION_MSG != null}">
                                ${sessionScope.USER_LOGIN_EXCEPTION_MSG}
                            </c:if>
                        </div>
                        <div class="loginbox_container">

                               <span>
                                       <select id="j_cti_server" class="easyui-combobox" name="j_cti_server" style="width:270px;">
                                           <option value="000">选择CTI服务器</option>
                                           <option value="010">北京</option>
                                           <option value="021">上海</option>
                                           <option value="0510">无锡</option>
                                       </select>

                               </span>
                        </div>

                        <div class="loginbox_container">
                                   <div onclick="document.getElementById('j_phoneno').value = '';this.style.display='none';"
                                        id="clrphoneno" class="clrField">&nbsp;</div>
                            <div id="phoneno">
                                <div ><label for="j_phoneno" class="zen-assistiveText">分机号</label>
                                    <input id="j_phoneno"  placeholder="分机号" name="j_phoneno" type="text" value="" tabindex="0" class="input"
                                           onkeyup="if(this.value != ''){document.getElementById('clrphoneno').style.display='block';}else{document.getElementById('clrphoneno').style.display='none';}"/>
                                </div>
                            </div>
                        </div>

                        <div class="loginbox_container" onClick="document.login.j_username.focus();">
                            <div onclick="document.getElementById('j_username').value = '';this.style.display='none';"
                                 id="clrUsr" class="clrField">&nbsp;</div>
                            <div id="usrn">
                                <div><label for="j_username" class="zen-assistiveText">用户名</label>
                                    <input id="j_username" placeholder="用户名" name="j_username" type="text" value="${userName}" tabindex="1" class="input"
                                           onkeyup="if(this.value != ''){document.getElementById('clrUsr').style.display='block';}else{document.getElementById('clrUsr').style.display='none';}"/>

                                    <%--<input type="email" placeholder="用户名"--%>
                                    <%--value="" class="input"--%>
                                    <%--name="username" id="username"--%>
                                    <%--onkeyup="if(this.value != ''){document.getElementById('clrUsr').style.display='block';}else{document.getElementById('clrUsr').style.display='none';}"/>--%>
                                </div>
                            </div>
                        </div>


                        <div class="loginbox_container" onclick="document.login.j_password.focus();">
                            <div onclick="document.getElementById('j_password').value = '';this.style.display='none';document.login.j_password.focus();"
                                 id="clrPw" class="clrField">&nbsp;</div>
                            <div id="pswd">
                                <div><label for="j_password" class="zen-assistiveText">密码</label>
                                    <input type="password" placeholder="密码" name="j_password" id="j_password" value="${pwd }" tabindex="2"  class="input"
                                           onkeypress="checkCaps(event)"
                                           autocomplete="off"
                                           onkeyup="if(this.value != ''){document.getElementById('clrPw').style.display='block';}else{document.getElementById('clrPw').style.display='none';} "/>

                                    <%--<input type="password" placeholder="密码"--%>
                                    <%--class="input" id="password"--%>
                                    <%--name="pw"--%>
                                    <%--onkeypress="checkCaps(event)"--%>
                                    <%--autocomplete="off"--%>
                                    <%--onkeyup="if(this.value != ''){document.getElementById('clrPw').style.display='block';}else{document.getElementById('clrPw').style.display='none';} "/>--%>
                                </div>
                            </div>
                        </div>
                        <div id="pwcaps" class="loginbox_container" style="display:none"><img id="pwcapsicon" alt="打开了 Caps Lock！"
                                                                                              width="16" height="16"/> 打开了 Caps Lock！
                        </div>
                        <div class="loginbox_container">
                            <%--<input  type="submit" value="登&nbsp;录" name="Login" id="Login"--%>
                            <button class="button" id="Login" name="Login"><span class="label">登&nbsp;&nbsp;&nbsp;录</span></button>
                        </div>
                        <div class="loginbox_container">
                            <div id="rem" class="wrapper_remember"><input type="checkbox" checked="checked" id="softphone" name="softphone">
                                &nbsp;<label for="softphone">加载软电话</label></div>
                        </div>
                        <%--<div class="loginbox_container" id="forgot"><span><a--%>
                        <%--href="/secur/forgotpassword.jsp?locale=cn">忘记了密码？</a></span>&nbsp;|&nbsp;<span class="wrapper_signup"><a--%>
                        <%--id="signup_link"--%>
                        <%--href="">免费注册。</a></span></div>--%>
                    </form>
                    <script type="text/javascript">loader();</script>
                </div>
            </div>
        </div>
        <div id="left_side">
            <div id="marketing" >
                  <img src="/static/images/login_sales.jpg"/>

            </div>
        </div>
    </div>
    <div id="footer">Copyright © 2013 chinadrtv.com, inc. 公司版权所有。保留所有权利。</div>
</div>
</body>
</html>
