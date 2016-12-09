<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>

<html>
<head>
    <%@ include file="/common/meta.jsp" %>
    <title><decorator:title/> Marketing</title>
    <meta http-equiv="X-UA-Compatible" content="IE=9" />
	<meta name="context_path" content="${pageContext.request.contextPath}" />
	<script type="text/javascript">
		var ctx = '${ctx}';
	</script>
    <script type="text/javascript" src="${ctx}/static/scripts/jquery/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="${ctx}/static/scripts/jquery.jclock.js"></script>
    <script type="text/javascript" src="${ctx}/static/scripts/jquery/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/scripts/jquery/locale/easyui-lang-zh_CN.js"></script>
        <script type="text/javascript" src="${ctx}/static/scripts/common-ui.js"></script>
    <script type="text/javascript">
 	    $.ajaxSetup({
	    	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	    	complete : function(xhr, textStatus) {
	    	    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	    	    var curWwwPath=window.document.location.href;
	    	    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	    	    var pathName=window.document.location.pathname;
	    	    var pos=curWwwPath.indexOf(pathName);
	    	    //获取主机地址，如： http://localhost:8083
	    	    var localhostPaht=curWwwPath.substring(0,pos);
	    	    //获取带"/"的项目名，如：/uimcardprj
	    	    //var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	    	    var projectName = "<%=request.getContextPath()%>";
	    		//session timeout
	    		if (xhr.status == 911) {
	    			window.location = localhostPaht+projectName;//返回应用首页
	    			return;
	    		}
	    	}
	    }); 
    </script>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/scripts/jquery/themes/icon.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/scripts/jquery/themes/default/easyui.css">

    <link href="${ctx}/static/style/layout.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/static/style/style.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${ctx}/static/style/ms_edit.css"/>
     <link href="${ctx}/static/style/marketing_layout.css" rel="stylesheet" type="text/css" />
   
    <decorator:head/>
</head>
<body>
<div id="wrap">
<!--关部-->
<div id="header">
  <div class="h_light"></div>
 <img alt="" src="${ctx}/static/images/marketing-logo.png" width=331 height=60>
	<span  id="serverTime" class="_date"></span>
    <!--
    <div class="joinus mr50 fr"><span class="f12 fb"></span><input name="" type="button" value="退出" class="but bordernone tc "/></div>
    -->
    <a target="_self" href="${ctx }/logout" class="outbutton">注销</a>
    <span class="comp">橡果科技有限公司</span>
    <span class="user"><%=com.chinadrtv.erp.user.util.SecurityHelper.getLoginUser().getUserId() %></span>
</div>

<!--导航
<ul id="sddm">
	
</ul>
-->
<!--页面主体-->
<div id="maincontent" >
<div id="sidebar">
  <jsp:include page="/common/left.jsp" />
</div>
<div id="content" class="clearfix">
<div id="handlebar" class="handlebar"><img alt="" src="../static/images/handleV.png" width="3" height="39"></div>
<div class="currentPage"><span class="l"></span> <span class="p"></span></div>
<decorator:body/> 
</div>
</div>
<!--版权-->
<!--
<div id="copyright" class="f12"> 橡果国际IT平台开发组支持
</div>-->
</div>
</body>
</html>
