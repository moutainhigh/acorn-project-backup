<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>

    <script type="text/javascript" src="/static/scripts/json.min.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="/static/scripts/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/static/scripts/report/paymentCheck.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
    <link href="/static/style/report/sendOrderList.css" rel="stylesheet" type="text/css" />

</head>
<body>
<!--应收应付款核对查询-->
<div id="condition">
    <div>
        <label for="sId" class="ml10 w80">发运单号:</label>
        <input id="sId"  type="text"   name="sId" size="20" />
        <label for="mailID" class="mr10">&nbsp;&nbsp;&nbsp;邮件号:</label>
        <input id="mailID"  type="text"   name="mailID"size="20" />

    </div>

    <div>
        <label  for="startDate" class="ml10 w80">结账反馈日期:</label>
        <input id="startDate" data-options="editable:false" value="${startDate }" class="easyui-datebox" /> <label for="">至:</label>
        <input id="endDate" data-options="editable:false" type="text"  value="${endDate }" class="easyui-datebox" size="50" />
        <span>
        	<input type="checkbox" id="accounted" name="checkbox2" value="checkbox"> 已结订单
              <input type="checkbox" id="exclude" name="checkbox1" value="checkbox"> 排除应收款差异
        </span>
    </div>
    <div>
        <label for="tbWarehouseId" class="ml10 w80">仓库:</label>
        <input id="tbWarehouseId" class="inputStyle easyui-combobox"style="width:130px" name="warehouseId">
        <label for="tbEntityId" class="ml10 w80">承运商:</label>
        <input id="tbEntityId" class="inputStyle easyui-combobox" style="width:130px" name="entityId">
        <label  for="ck" class="ml10 w80"></label>
		<span >
			<input type="button" id="searchBtn" value="查询" class="ml10 Btn" name="">
            <input type="button" id="createIDBtn" value="生成结算" class="ml10 Btn" name="">
            <input type="button" id="exportBtn" value="结果导出" class="ml10 Btn" name="">
    	</span>

    </div>

</div>

<div region="center" >
    <div id="toolbar">

    </div>
    <div class="container">
        <table id="dg" title="订单发货单" url="${url}" cellspacing="0" cellpadding="0" data-options=""  ></table>
    </div>
</div>

</body>
</html>
