<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ include file="/common/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/taglibs.jsp" %>
    <script type="text/javascript">
        var ctx = '${ctx}';
    </script>
    <link href="${ctx}/static/style/main.css" rel="stylesheet" type="text/css"/> 
    
    <decorator:head/>
</head>
<body>
<div id="header">
    <div id="header-inner">
        <table cellpadding="0" cellspacing="0" style="width:100%;">
            <tbody>
            <tr>
                <td rowspan="2" style="width:20px;">
                </td>
                <td style="height:52px;">
                    <div style="color:#fff;font-size:22px;font-weight:bold;">
                        <span style="color:#fff;font-size:22px;font-weight:bold;text-decoration:none">知识库<span style="font-size: 11px">(版本号：<spring:message code="systemTemps"/>)</span></span>
                    </div>
                    <div style="color:#fff">
                        <span style="color:#fff;text-decoration:none">repository helps you build your order easily!</span>
                    </div>
                </td>
                <td style="padding-right:5px;text-align:right;vertical-align:bottom;">
                    <a class="logout" id="logout" href="javascript:logout();">退出</a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

</div>
<div id="mainwrap">
    <div id="mainContent" >

        <link href="${ctx}/static/style/easyui/easyui.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="/static/style/easyui/themes/icon.css">
        <style type="text/css">
        </style>
        <style type="text/css">

            #header a {
                font-size: 14px;
            }

            #mainwrap {
                margin: 0;
            }

        </style>
        <script type="text/javascript" src="${ctx}/static/js/jquery.min.js"></script>
        <script type="text/javascript" src="${ctx}/static/js/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="${ctx}/static/js/easyui-lang-zh_CN.js"></script>

        <script>
            $(function () {
                $(window).resize(function () {
                    $('#main-layout').layout('resize');
                });
//                $('#main-layout').layout('collapse','east');
            });
        </script>


        <div id="main-layout" class="easyui-layout" fit="true"  style="height:550px;" >
            <div region="west" title="" data-options="tools:'#west-tools',collapsible:false,border:false" split="true" style="width:220px;">
                <div id="left-panel" class="easyui-panel" fit="true" border="false" title="知识及话术" tools="#west-tools" style="padding:5px" >

                        <jsp:include page="/common/left.jsp"/>

                    </div>

            </div>

            <%--<div style="width:300px;" data-options="region:'east',split:true,border:false">--%>
                <%--<jsp:include page="/common/right.jsp"/>--%>
            <%--</div>--%>
            <div style="height:20px;" data-options="region:'south',border:false" >
                <jsp:include page="/common/footer.jsp"/>
            </div>
            <div region="center" href="/index" split="true" border="false" title=""  >

            </div>
        </div>
    </div>
</div>
<div id="west-tools">
	<sec:authorize access="hasAnyRole('INBOUND_MANAGER,OUTBOUND_MANAGER,COMPLAIN_MANAGER')"> 
	    <a id="categoryOkBtn" href="javascript:void(0)" style="display: none;" class="icon-ok" onclick="finishEditing()"></a>
	    <a id="categoryEditBtn" href="javascript:void(0)" class="icon-edit" onclick="startEditing()"></a>
    </sec:authorize>
</div>
<input id="isH" type="hidden" value="1"/>
<script type="text/javascript" src="${ctx}/static/js/common.js"></script>
</body>

</html>
