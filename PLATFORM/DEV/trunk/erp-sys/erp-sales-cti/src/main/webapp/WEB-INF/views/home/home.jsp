<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<link href="${ctx}/static/style/home.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/static/js/home/home.js?${rnd}"></script>
    <script type="text/javascript" src="${ctx}/static/js/task/task.js?${rnd}"></script>
</head>
<body>
	<input id="userID" name="userID" value="${userID}" type="hidden" maxlength="20" disabled="disabled" />
	<input id="department" name="department" value="${department}" type="hidden" maxlength="20" disabled="disabled" />

	<div id="home"   >
		<div class="myTasks">
			<table id="table_campaignTask" style="height: 210px" title="我的营销任务"
				data-options="singleSelect:true,fitColumns:true,fit:true" pagination="true">
			</table>
		</div>
		
		<div class="myTasks">
			<table id="table_auditTask" style="height: 210px" title="我的审批任务"
				data-options="singleSelect:true,fitColumns:true,fit:true" pagination="true">
			</table>
		</div>

		<div class="issueOrders">
			<table id="table_myQuestionnaire" style="height: 210px" title="问题订单"
				data-options="singleSelect:true,fitColumns:true,fit:true" pagination="true">
			</table>
		</div>
		<div id="window_campaignTaskCancel"></div>
		<div id="window_processOrderCancelAuditTask" style="display: none;">
			<!--问题单对话框-->
		<div id="div_problematic_reply"  style="display: none;">
            <div class="popWin_wrap">

            <table id="detail" class="editTable">
				<tr>
					<td><label  for="txtOrderId">问题单号</label></td>
					<td><input id="input_problematic_reply_oid" class="readonly ml10"  disabled="disabled" name="input_problematic_reply_oid"></td>
				</tr>
				<tr>
					<td valign="top" class="pt10"><label class="" for="txtRemark">回复内容</label></td>
					<td  class="pt10">
					   <textarea id="textarea_problematic_reply_reason" cols="50" rows="4" class='ml10 fr' style='width:300px' name="textarea_problematic_reply_reason"></textarea>
					</td>
				</tr>
			</table>
        </div>
	</div>
	</div>
        </div>

</body>
</html>