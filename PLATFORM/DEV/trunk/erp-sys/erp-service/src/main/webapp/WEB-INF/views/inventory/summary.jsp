<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<link href="${ctx}/static/style/inventory.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/inventory/inventory.js"></script>
</head>
<body>
	<div id="home" >
		<div class="myTasks">
            <table id="table-favorites" style="height: 220px" title="我的关注">
            </table>
		</div>

		<div class="myTasks mt10">
			<table id="table-hots" style="height: 220px" title="热点商品">
			</table>
            注：当前热点商品非实时统计数据
		</div>

        <div id="dlg-intransit" >
            <table id="table-intransit" title="库存在途列表" cellspacing="0" cellpadding="0">
            </table>
        </div>
	</div>
</body>
</html>