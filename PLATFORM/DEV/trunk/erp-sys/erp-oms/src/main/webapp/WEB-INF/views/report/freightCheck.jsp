<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/static/scripts/report/freightCheck.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="/common/reportSupport.jsp" />
<%--运费对账报表--%>
<div id="condition">
    <form id="reportrForm" method='POST' target='reportIframe'>
        <div>
            <label><input for="state" type="radio" name="state" value="0" checked="checked"/>按交际日期</label>
            <label><input for="state" type="radio" name="state" value="1" />按出库时间&nbsp;&nbsp;</label>
            <input id="settleStrDate" data-options="editable:false" value="${settleStrDate }" class="easyui-datetimebox" style="width: 156px" /> <label for="">至:</label>
            <input id="settleEndDate" data-options="editable:false" type="text"  value="${settleEndDate }" class="easyui-datetimebox" size="50" style="width: 156px"/>
       		<input id="r_username" name="r_username" type="password" value="${r_username}" style="display: none;">
    		<input id="r_password" name="r_password" type="password" value="${r_password}" style="display: none;">
        </div>
        <div>
            <label for="entityId" class="ml10 w80">送货公司:</label>
            <select name="entityId" id="entityId" class="easyui-combobox" style="width: 156px">
                <option value="">==请选择或输入==</option>
                <c:forEach items="${companyList }" var="company">
                    <option value="${company.companyid }">${company.name }</option>
                </c:forEach>
            </select>
            <label for="orderStatus">订单状态:</label>
            <select name="orderStatus" id="orderStatus">
                <option value="">===请选择===</option>
                <option value="5">完成</option>
                <option value="6">拒收</option>
            </select>
				<span >
				<input type="button" id="queryOrderBtn" value="查询" class="ml10 Btn" name="">
                <input type="button" id="clearBtn" value="清空" class="ml10 Btn" name="">
	    	 	</span>
        </div>
    </form>
</div>

<div class="container report-view">
    <input id="reportServerUrl" type="hidden" value="${reportServerUrl}">
    <iframe id="reportIframe" name="reportIframe" width="100%" height="480"></iframe>
</div>
</body>
</html>
