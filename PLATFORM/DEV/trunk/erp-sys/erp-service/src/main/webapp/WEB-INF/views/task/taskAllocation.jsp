<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<link href="${ctx}/static/style/myOrders.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/style/callbackAssign.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
	var isDepartmentManager = '${isDepartmentManager}';
</script>
<script type="text/javascript" src="${ctx}/static/js/task/taskAllocation.js"></script>
</head>
<body>
<form id="allocationTaskForm" class="tabs-form" action="" method="post">

    <div class="baseInfo" style="margin: 0">
        <table class="" border="0" cellspacing="0" cellpadding="0"
               width="100%">
            <tr>
                <td>生成时间</td>
                <td ><input size="10" type="text" id="crStartDate" style="width: 150px"/>&nbsp;~&nbsp;
                     <input size="10" type="text" id="crEndDate" style="width: 150px"/> 
                </td>
                <td>订单座席</td>
                <td><input class="easyui-validatebox" id="orderCrUsr" name="" type="text" value=""/></td>
                <td>处理状态</td>
                <td>
                 	<select id="taskStatus" name="taskStatus" style="width: 134px" class="easyui-combobox" data-options="required:'true',missingMessage:'必填项'">
							<c:forEach items="${taskStatus }" var="l" varStatus="indexs">
								<option value="${indexs.index}">${l }</option>
							</c:forEach>
					</select>
                </td>
            </tr>

            <tr>
                <td>分配时间</td>
                <td ><input size="10" type="text" id="alloStartDate" style="width: 150px"/>&nbsp;~&nbsp; 
                     <input size="10" type="text" id="alloEndDate" style="width: 150px"/> </td>
                <td>被分配座席</td>
                <td><input class="easyui-validatebox" id="rdbusrid" name="" type="text" value=""/></td>
                <td>任务类型</td>
                <td>
                	<select id="taskType" name="taskType" style="width: 134px" class="easyui-combobox">
							<option value="">--请选择--</option>
							<c:forEach items="${taskType }" var="l" varStatus="indexs">
								<option value="${l.id.id}">${l.dsc }</option>
							</c:forEach>
					</select>
				</td>
            </tr>
            <tr><td></td><td></td><td></td><td></td><td colspan="2">
                <div class="submitBtns" style="text-align: left">
                    <a href="javascript:void(0)" id="btn_callbackTask">查询任务</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="javascript:void(0)" id="btn_callbackTaskClear">清空</a>
                </div>
            </td></tr>
        </table>
    </div>
</form>
<div class="receiptInfo tabs-form withtool">
    <table id="table_callbackTask" cellspacing="0" cellpadding="0"> </table>
</div>

<div class="tabs-form">
<%--<div class="baseInfo" style="margin: 0">--%>
    <p class="" style="margin-bottom: 10px">座席工号&nbsp;<input id="input_agentId" type="text"/>  <span class="submitBtns" style="text-align: left">
    <a href="javascript:void(0)" id="btn_queryAgent">查询</a>&nbsp;
    <a href="javascript:void(0)" id="btn_addAgent">添加</a>&nbsp;
    <a href="javascript:void(0)" id="btn_removeAgent">删除</a>
     &nbsp;&nbsp;&nbsp;&nbsp;
     <span id="label_userinfo" style="color:red"></span>
   <a href="javascript:void(0)" class="fr" style="height: 17px;line-height: 17px" id="btn_task_dist">任务分配</a>
</span>  </p>
<%--</div>--%>
    <table id="table_agentList"  cellspacing="0" cellpadding="0">
    </table>
</div>

</body>

</html>