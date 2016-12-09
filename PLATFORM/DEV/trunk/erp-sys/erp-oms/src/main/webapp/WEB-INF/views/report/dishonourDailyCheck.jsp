<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/static/scripts/report/dishonourDailyCheck.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="/common/reportSupport.jsp" />
<%--拒收退包日报核对表--%>
<div id="condition">
    <form id="reportrForm" method='POST' target='reportIframe'>
        <div>
            <label  for="startDate" class="ml10 w80">退包入库日期:</label>
            <input id="startDate" data-options="editable:false" value="${startDate }" class="easyui-datetimebox" style="width: 156px" /> <label for="">至:</label>
            <input id="endDate" data-options="editable:false" type="text"  value="${endDate }" class="easyui-datetimebox" style="width: 156px" size="50" />
        	<input id="r_username" name="r_username" type="password" value="${r_username}" style="display: none;">
    		<input id="r_password" name="r_password" type="password" value="${r_password}" style="display: none;">
        </div>

        <div>
            <label for="entityId" class="ml10 w80">承运商:</label>
            <select name="entityId" id="entityId" class="easyui-combobox" style="width:156px">
                <option value="">==请选择输入==</option>
                <c:forEach items="${companyList }" var="company">
                    <option value="${company.companyid }">${company.name }</option>
                </c:forEach>
            </select>
            <label  for="warehouseId" class="mr10">&nbsp;仓库:</label>
            <select name="warehouseId" id="warehouseId" style="width:156px">
                <option value="">===请选择==</option>
                <c:forEach items="${warehouseList }" var="warehouse">
                    <option value="${warehouse.warehouseId }">${warehouse.warehouseName }</option>
                </c:forEach>
            </select>
            <label  for="warehouseId" class="ml10 w80"></label>
			<span >
			<input type="button" id="searchBtn" value="查询" class="ml10 Btn" name="">
            <input type="button" id="clearBtn" value="清空" class="ml10 Btn" name="">
    	 	</span>
        </div>
    </form>
</div>
<%--拒收退包日报核对表--%>
<div class="container report-view">
    <input id="reportServerUrl" type="hidden" value="${reportServerUrl}">
    <iframe id="reportIframe" name="reportIframe" width="100%" height="480"></iframe>
</div>
</body>
</html>
