<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/static/scripts/report/rebates.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="/common/reportSupport.jsp" />
<%--返款报表--%>
<div id="condition">
    <form id="reportrForm" method='POST' target='reportIframe'>
        <div>
            <label  for="startDate" class="mr100">打款日期:</label>
            <input id="startDate" data-options="editable:false" value="${startDate }" class="easyui-datebox" style="width: 150px" /> <label for="">至:</label>
            <input id="endDate" data-options="editable:false" type="text"  value="${endDate }" class="easyui-datebox" size="50" style="width: 150px"/>
       		<input id="r_username" name="r_username" type="password" value="${r_username}" style="display: none;">
    		<input id="r_password" name="r_password" type="password" value="${r_password}" style="display: none;">
            <label  for="startDate" class="ml10 w80"></label>
            <span >
				<input type="button" id="queryOrderBtn" value="查询" class="ml10 Btn" name="">
                <input type="button" id="clearBtn" value="清空" class="ml10 Btn" name="">
	    	</span>
        </div>

    </form>
</div>
<%--发包清单报表--%>
<div class="container report-view">
    <input id="reportServerUrl" type="hidden" value="${reportServerUrl}">
    <iframe id="reportIframe" name="reportIframe" width="100%" height="480"></iframe>
</div>
</body>
</html>
