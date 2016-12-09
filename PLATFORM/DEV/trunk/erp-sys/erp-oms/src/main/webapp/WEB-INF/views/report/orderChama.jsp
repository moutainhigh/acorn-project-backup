<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/static/scripts/report/orderChama.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="/common/reportSupport.jsp" />
<!--茶马订单报表-->
<div id="condition">
    <form id="reportrForm" method='POST' target='reportIframe'>
        <div>
            <label  for="startDate" class="w100">导入开始时间:</label>
            <input id="startDate" data-options="editable:false" value="${startDate }" class="easyui-datetimebox" style="width: 156px" /> <label for="">至:</label>
            <input id="endDate" data-options="editable:false" type="text"  value="${endDate }" class="easyui-datetimebox" size="50" style="width: 156px"/>
       		<input id="r_username" name="r_username" type="password" value="${r_username}" style="display: none;">
    		<input id="r_password" name="r_password" type="password" value="${r_password}" style="display: none;">
        </div>
        <div>
            <label for="chamOrderID" class="w100">茶马订单号:</label>
            <input id="chamOrderID"  type="text"  name="chamOrderID"size="20" />
            <label for="taobaoOrderID" class="ml10 w80">淘宝订单号:</label>
            <input id="taobaoOrderID"  type="text"  name="taobaoOrderID"size="20" />
        </div>
        <div>
            <label for="acornOrderID" class="w100">橡果订单号:</label>
            <input id="acornOrderID"  type="text"  name="acornOrderID"size="20" />
            <label for="orderStatus" class="ml10 w80">订单状态:</label>
            <select name="orderStatus" id="orderStatus" style="width:150px">
                <option value="">===请选择=======</option>
                <option value="1" style="color:#00CCFF">订购</option>
                <option value="2" style="color:#CC3300">分拣</option>
                <option value="3" style="color:#33FF00">退货</option>
                <option value="4" style="color:#009966">换货</option>
                <option value="5" style="color:#FF9966">完成</option>
                <option value="6" style="color:#666699">拒收</option>
                <option value="7" style="color:#009966">压单</option>
                <option value="8" style="color:#FF9966">物流取消</option>
                <option value="9" style="color:#666699">装车</option>
                <option value="0" style="color:#33FF00">取消</option>
            </select>
            <label  for="orderStatus" class="w100"></label>
				<span >
				<input type="button" id="queryOrderBtn" value="查询" class="ml10 Btn" name="">
                <input type="button" id="clearBtn" value="清空" class="ml10 Btn" name="">
	    	 	</span>
        </div>
    </form>
</div>
<%--茶马订单报表--%>
<div class="container report-view">
    <input id="reportServerUrl" type="hidden" value="${reportServerUrl}">
    <iframe id="reportIframe" name="reportIframe" width="100%" height="480"></iframe>
</div>
</body>
</html>
