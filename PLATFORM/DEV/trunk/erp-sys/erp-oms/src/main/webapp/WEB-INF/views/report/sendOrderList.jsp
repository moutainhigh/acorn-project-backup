<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/ajaxfileupload.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
    <link href="/static/style/report/sendOrderList.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="/static/scripts/report/sendOrderList.js"></script>

</head>
<body>
<!--发包清单查询-->
<div id="condition">
    <div>
        <label  for="sId" class="w100">发包交接单号:</label>
        <input id="sId"  type="text"   name="sId" size="20" />
        <label  for="startDate" class="ml10 w80">发包出库日期:</label>
        <input id="startDate" data-options="editable:false" value="${startDate }" class="easyui-datebox" /> <label for="">至:</label>
        <input id="endDate" data-options="editable:false" type="text"  value="${endDate }" class="easyui-datebox" size="50" />
    </div>

    <div>
        <label for="cName" class="w100">承运商:</label>
        <select name="cName" id="cName" style="width:150px">
            <option value="">=======请选择=======</option>
            <c:forEach items="${companyList }" var="company">
                <option value="${company.name }">${company.name }</option>
            </c:forEach>
        </select>
        <label  for="warehouse" class="ml10 w80">仓库:</label>
        <input id="warehouse"  type="text"    name="warehouse"size="20" />
			<span >
			<input type="button" id="searchBtn" value="查询" class="ml10 Btn" name="">
    	 	</span>
    </div>

</div>
<%--发包清单报表condition  container--%>
<div class="condition iframe" >
    <iframe id="report" id="ifm" name="ifm" width = "100%" height="400"
            src="http://localhost:9080/report/frameset?__report=reports/RSendInventory.rptdesign&__format=HTML&__showtitle=false">
    </iframe>
</div>
</body>
</html>
