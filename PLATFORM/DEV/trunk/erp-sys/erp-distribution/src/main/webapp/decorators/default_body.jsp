<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ include file="/common/taglibs.jsp"%>

<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/common/meta.jsp" %>
    <link href="${ctx}/static/style/main.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
        var ctx = '${ctx}';
    </script>
    <script type="text/javascript" src="${ctx}/static/scripts/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.countup.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/common.js"></script>
    <decorator:head/>
</head>
<body>
<div id="header">
    <div id="header-inner">
        <table cellpadding="0" cellspacing="0" style="width:100%;">
            <tbody><tr>
                <td rowspan="2" style="width:20px;">
                </td>
                <td style="height:52px;">
                    <div style="color:#fff;font-size:22px;font-weight:bold;">
                        <%--<a href="/index.php" style="color:#fff;font-size:22px;font-weight:bold;text-decoration:none">知识库</a>--%>
                        <span style="color:#fff;font-size:22px;font-weight:bold;text-decoration:none">知识库后台管理</span>
                    </div>
                    <div style="color:#fff">
                        <%--<a href="/index.php" style="color:#fff;text-decoration:none">repository helps you build your order easily!</a>--%>
                        <span  style="color:#fff;text-decoration:none">repository helps you build your order easily!</span>
                    </div>
                </td>
                <td style="padding-right:5px;text-align:right;vertical-align:bottom;">
                    <div id="topmenu">

                    </div>
                </td>
            </tr>
            </tbody></table>
    </div>

</div>
<div id="mainwrap">
    <div id="content">

        <link href="${ctx}/static/style/easyui/easyui.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="/static/style/themes/icon.css">
        <style type="text/css">
        </style>
        <style type="text/css">
            *{
                font-size:12px;
            }
            #header a{
                font-size:14px;
            }
            #mainwrap{
                margin:0;
            }
            #content{
                width:100%;
                padding:0;
                border:0;
            }
        </style>
        <script type="text/javascript" src="${ctx}/static/js/jquery-1.8.3.js"></script>
        <script type="text/javascript" src="${ctx}/static/js/jquery.easyui.min.js"></script>

        <script>
            $(function(){
                $(window).resize(function(){
                    $('#main-layout').layout('resize');
                });
            });
        </script>


        <div id="main-layout" class="easyui-layout" fit="true" style="height:550px" >
            <div region="west" title="" split="true" style="width:300px" data-options="border:false">
                <jsp:include page="/common/manage/left.jsp" />
            </div>

            <div region="south" split="true" title="" style="height:20px;" data-options="border:false" >
                <div id="footer">
                    <div>状态条</div>
                </div>
            </div>
            <div region="center" split="true" title=""  style="padding:5px" data-options="border:false">
                <%--<decorator:body />--%>
                    <jsp:include page="/common/manage/center.jsp" />
            </div>
        </div></div>
</div>

<%--<div id="footer">--%>
<%--<div>状态条</div>--%>
<%--</div>--%>

</body>

</html>
