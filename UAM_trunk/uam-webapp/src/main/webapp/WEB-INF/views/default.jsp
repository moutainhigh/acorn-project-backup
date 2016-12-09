<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UAM</title>

<link rel="stylesheet" href="<c:url value="/css/global.css" />" type="text/css">
<link rel="stylesheet" href="<c:url value="/js/easyui/themes/gray/easyui.css" />" type="text/css">
<link rel="stylesheet" href="<c:url value="/js/easyui/themes/icon.css" />" type="text/css">

</head>
<body>
<div class="U_Wrap">
    <div class="easyui-layout" style="height:800px;">
        <div data-options="region:'north'" border="false" style="height:80px">
           <div class="U-hd"> <img src="<c:url value="/images/logo.png" />">  </div>
        </div>
        <div data-options="region:'center',title:'',iconCls:'icon-ok'" border="false" >
            <div id="tab"  fit="true"   border="false"  data-options="tools:'#tab-tools'">
                <div title="首页" href="home" style="padding:10px">

                </div>
                <div title="档案维护" selected="true" href="archives"  style="padding:10px 0">

                </div>
                <div title="权限维护" href="permissions" style="padding:10px 0">

                </div>
                <div title="参数配置维护" href="param" style="padding:10px 0">

                </div>
            </div>
        </div>
</div>
    <div id="tab-tools">
        <a href="j_spring_security_logout" class="easyui-linkbutton" data-options="plain:true,iconCls:''" >退出</a>
    </div>

<script type="text/javascript" src="<c:url value="/js/jquery.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/json.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/easyui/jquery.easyui.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/easyui/easyui-lang-zh_CN.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/common.js" />"></script>
</body>
</html>