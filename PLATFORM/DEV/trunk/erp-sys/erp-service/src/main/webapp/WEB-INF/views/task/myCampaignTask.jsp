<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<link href="${ctx}/static/style/myOrders.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/task/myCampaignTask.js?${rnd}"></script>
<script type="text/javascript" src="${ctx}/static/js/task/task.js?${rnd}"></script>

</head>
<body>

	<div id="mytask_tabs" class="easyui-tabs" style="height: auto">
		<div title="任务列表">
			<form id="campaignTaskForm" class="tabs-form" action="" method="post">

				<div class="baseInfo" style="margin: 0">
					<table class="" border="0" cellspacing="0" cellpadding="0"
						width="100%">
						<tr>
							<td>任务名称</td>
							<td><input class="easyui-validatebox" id="campaignName"
								name="campaignName" type="text" value="" /></td>

							<td>任务类型</td>
							<td><select id="campaignTaskTypes" name="campaignTaskTypes"
								style="width: 134px" class="easyui-combobox">
									<option value="">--请选择--</option>
									<c:forEach items="${campaignTaskTypes}" var="item"
										varStatus="indexs">
										<option value="${indexs.index}">${item}</option>
									</c:forEach>
							</select></td>
							<td>任务状态</td>
							<td><select id="campaignTaskStatus"
								name="campaignTaskStatus" style="width: 134px"
								class="easyui-combobox">
									<option value="">--请选择--</option>
									<c:forEach items="${campaignTaskStatus }" var="l"
										varStatus="indexs">
										<option value="${indexs.index}">${l }</option>
									</c:forEach>
							</select></td>

						</tr>
						<tr>
							<td>客户编号</td>
							<td><input class="easyui-validatebox" id="customerID"
								name="customerID" type="text" value="${customerID}"/></td>
							<td>客户姓名</td>
							<td><input class="easyui-validatebox" id="customerName"
								name="customerName" type="text" value="" /></td>
							<td>座席工号</td>
							<td><input class="readonly" id="userID" name="userID"
								value="${userID}" type="text" disabled="disabled" /></td>

						</tr>
						<tr>
							<td>生成时间</td>
							<td><input size="10" type="text" id="startDate"
								style="width: 95px" required="required" />&nbsp;~&nbsp; <input
								size="10" type="text" id="endDate" style="width: 95px"
								required="required" /></td>
							<td></td>
							<td></td>
							<td colspan="2">
								<div class="submitBtns" style="text-align: left">
									<a href="javascript:void(0)" id="btn_campaignTask">查找</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="javascript:void(0)" id="btn_campaignClear">清空</a>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</form>
			<div class="receiptInfo tabs-form">
				<table id="table_campaignTask" cellspacing="0" cellpadding="0" ></table>
			</div>
		</div>
		</div>
		<div id="window_campaignTaskCancel"></div>
		</body>
</html>