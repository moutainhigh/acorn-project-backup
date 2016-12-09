<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/static/scripts/report/rejectionAndPostfee.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="/common/reportSupport.jsp" />
<!--半拒收免运费报表-->
<div id="condition">
    <form id="reportrForm" method='POST' target='reportIframe'>
        <div>
            <label  for="strDate" class="ml10 w80">结算日期:</label>
            <input id="strDate" data-options="editable:false" value="${strDate }" class="easyui-datebox" style="width: 156px" /> <label for="">至:</label>
            <input id="endDate" data-options="editable:false" type="text"  value="${endDate }" class="easyui-datebox" size="50" style="width: 156px"/>
            <input id="r_username" name="r_username" type="password" value="${r_username}" style="display: none;">
            <input id="r_password" name="r_password" type="password" value="${r_password}" style="display: none;">
        </div>
        <div>
            <label for="entityId" class="ml10 w80">送货公司:</label>
            <select name="entityId" id="entityId" class="easyui-combobox" style="width:156px">
                <option value="">==请选择==</option>
                <c:forEach items="${companyList }" var="company">
                    <option value="${company.companyid }">${company.name }</option>
                </c:forEach>
            </select>
            <label for="provinceId" class="mr10">省份:</label>
            <select name="provinceId" id="provinceId" class="easyui-combobox" style="width:132px">
                <option value="">==请选择==</option>
                <c:forEach items="${provinceList }" var="province">
                    <option value="${province.provinceid }">${province.chinese }</option>
                </c:forEach>
            </select>
            <label  for="provinceId" class="w100"></label>
				<span >
				<input type="button" id="queryOrderBtn" value="查询" class="ml10 Btn" name="">
                <input type="button" id="clearBtn" value="清空" class="ml10 Btn" name="">
	    	 	</span>
        </div>
    </form>
</div>
<%--加载报表--%>
<div class="container report-view">
    <input id="reportServerUrl" type="hidden" value="${reportServerUrl}">
    <iframe id="reportIframe" name="reportIframe" width="100%" height="480"></iframe>
</div>
</body>
</html>
