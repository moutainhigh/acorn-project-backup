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
    var currentUserId='${userID}';
    var taskTaskTypes=eval(${taskTaskTypes});
</script>
<script type="text/javascript" src="${ctx}/static/js/task/mytask.js"></script>
<script type="text/javascript" src="${ctx}/static/js/task/task.js"></script>
<script type="text/javascript" src="${ctx}/static/js/task/cmpfback.js?${rnd}"></script>
    <script type="text/javascript" src="${ctx}/static/js/event/eventCmpfback.js?${rnd}"></script>
<script type="text/javascript" src="${ctx}/static/js/task/allocateTask.js"></script>
<script type="text/javascript" src="${ctx}/static/js/task/taskAssign.js"></script>
<script type="text/javascript" src="${ctx}/static/js/task/mytask_cusnote.js?${rnd}"></script>
<script type="text/javascript" src="${ctx}/static/js/task/mytask_refundsms.js"></script>
 <%--<script type="text/javascript" src="${ctx}/static/js/common.js"></script>--%>
<script type="text/javascript" src="${ctx}/static/js/jquery.ajaxQueue.min.js"></script>
</head>
<body>
	<div id="mytask_tabs" class="easyui-tabs" style="height: auto">
        <div title="投诉处理">
            <form id="complaintsSetForm" class="tabs-form" action="" method="post">

                <div class="baseInfo" style="margin: 0">
                    <table class="" border="0" cellspacing="0" cellpadding="0"
                           width="100%">

                        <tr>
                            <td>订单编号</td>
                            <td><input class="easyui-validatebox" id="complaintsSet_orderId" type="text" value=""/></td>
                            <td>客户姓名</td>
                            <td>
                                <input class="easyui-validatebox" id="complaintsSet_name" type="text" value=""/>

                            </td>

                            <td>状态</td>
                            <td>
                                <%--<input class="easyui-combobox" id="complaintsSet_status"/>--%>
                                <input class="easyui-combobox" id="complaintsSet_status" data-options="
		valueField: 'value',
		textField: 'label',
		data: [{label: '请选择',value: ''},
		        {label: '未受理',value: '0'},
		        {label: '已受理',value: '1'}]" />
                            </td>

                        </tr>
                        <tr>
                            <td>优先级</td>
                            <td><input class="easyui-combobox" id="complaintsSet_priority" editable=false  data-options="
		valueField: 'value',
		textField: 'label',
		data: [{label: '请选择',value: ''},
		       {label: '紧急',value: '1'},
		       {label: '一般',value: '2'},
		       {label: '可以等待',value: '3'},
		       {label: '特别紧急',value: '4'}]" /></td>
                            <td>投诉日期</td>
                            <td colspan="2">
                                <input size="10" type="text" id="complaintsSet_startDate" style="width: 150px" editable="false" class="easyui-datebox"/>&nbsp;~&nbsp;
                                <input size="10" type="text" id="complaintsSet_endDate" style="width: 150px" editable="false" class="easyui-datebox"/>

                            </td>


                            <td>
                                <div class="submitBtns" style="text-align: left">
                                    <a href="javascript:complaintsSet_query();" >查找</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <a href="javascript:complaintsSet_clear();" >清空</a>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
            <div class="receiptInfo tabs-form withtool">
                <table id="complaintsSet"  cellspacing="0" cellpadding="0"></table>
            </div>
        </div>
        <%--<div title="投诉查询">--%>

            <%--<div class="receiptInfo tabs-form withtool">--%>
                <%--<table id="complaintsQuery"  cellspacing="0" cellpadding="0"></table>--%>
            <%--</div>--%>
        <%--</div>--%>
		<div title="协办反馈">
			<form id="cmpfbackForm" class="tabs-form" action="" method="post">

				<div class="baseInfo" style="margin: 0">
					<table class="" border="0" cellspacing="0" cellpadding="0"
						width="100%">
						<tr>
							<td>创建日期</td>
							<td colspan="5"><input size="10" type="text" id="cmpfback_startDate" style="width: 150px"   />&nbsp;~&nbsp;
                                <input size="10" type="text" id="cmpfback_endDate" style="width: 150px"   /></td>
						</tr>
						<tr>
							<td>客户编号</td>
							<td><input class="easyui-validatebox" id="cmpfback_customerID" type="text" value=""/></td>
							<td>创建人</td>
							<td><input class="easyui-validatebox" id="cmpfback_crusr" type="text" value="" /></td>
							<td>订单编号</td>
							<td><input class="easyui-validatebox" id="cmpfback_orderId" type="text" value=""/></td>

						</tr>
						<tr>
							<td>产品简码</td>
							<td><input class="easyui-validatebox" id="cmpfback_productdsc" type="text" value=""/></td>
							<td>部门</td>
							<td><input class="easyui-validatebox" id="cmpfback_yijian" type="text" value=""/></td>
							<td>结果</td>
                            <td><select id="cmpfback_result"  style="width: 140px"  >
                                <option selected="true" value="">请选择</option>
                                <option value="1">满意</option>
                                <option value="2">不满意</option>
                                <option value="0">未处理</option>
                                </select></td>
						</tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td colspan="2">
                                <div class="submitBtns" style="text-align: left">
                                    <a href="javascript:void(0)" id="btn_cmpfback_query">查找</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <a href="javascript:void(0)" id="btn_cmpfback_clear">清空</a>
                                </div>
                            </td>
                        </tr>
					</table>
				</div>
			</form>
			<div class="receiptInfo tabs-form withtool">
				<table id="table_cmpfback"  cellspacing="0" cellpadding="0" ></table>
			</div>
            <div style="display: none">
                <jsp:include page="../event/cmpfbackDialog.jsp"></jsp:include>
            </div>
		</div>


		<%--<div title="完成协办">--%>
			<%--<form id="auditTaskForm" class="tabs-form" action="" method="post">--%>

                <%--<div class="baseInfo" style="margin: 0">--%>
                    <%--<table class="" border="0" cellspacing="0" cellpadding="0"--%>
                           <%--width="100%">--%>
                        <%--<tr>--%>
                            <%--<td>处理日期</td>--%>
                            <%--<td colspan="5"><input size="10" type="text" id="startDate4Audit"--%>
                                                   <%--style="width: 150px" required="required"  />&nbsp;~&nbsp; <input--%>
                                    <%--size="10" type="text" id="endDate4Audit" style="width: 150px"--%>
                                    <%--required="required"  /> <label><input type="checkbox"/>有效</label></td>--%>

                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td>客户编号</td>--%>
                            <%--<td><input class="easyui-validatebox" id=""--%>
                                       <%--name="customerID" type="text" value="${customerID}"/></td>--%>
                            <%--<td>创建人</td>--%>
                            <%--<td><input class="easyui-validatebox" id=""--%>
                                       <%--name="customerName" type="text" value="" /></td>--%>
                            <%--<td>工单编号</td>--%>
                            <%--<td><input class="easyui-validatebox" id=""--%>
                                       <%--name="customerID" type="text" value=""/></td>--%>

                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td>产品简码</td>--%>
                            <%--<td><input class="easyui-validatebox" id=""--%>
                                       <%--name="customerID" type="text" value=""/></td>--%>
                            <%--<td>处理结果</td>--%>
                            <%--<td><input class="easyui-validatebox" id=""--%>
                                       <%--name="customerID" type="text" value=""/></td>--%>
                            <%--<td colspan="2">--%>
                                <%--<div class="submitBtns" style="text-align: left">--%>
                                    <%--<a href="javascript:void(0)" id="">查找</a>&nbsp;&nbsp;&nbsp;&nbsp;--%>
                                    <%--<a href="javascript:void(0)" id="">清空</a>--%>
                                <%--</div>--%>
                            <%--</td>--%>
                        <%--</tr>--%>
                    <%--</table>--%>
                <%--</div>--%>
			<%--</form>--%>
			<%--<div class="receiptInfo tabs-form withtool">--%>
				<%--<table id="table_auditTask" cellspacing="0" cellpadding="0"--%>
					<%--data-options=""></table>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div title="事件回访">
            <form id="eventReturnForm" class="tabs-form" method="post">

                <div class="baseInfo" style="margin: 0">
                    <table class="" border="0" cellspacing="0" cellpadding="0"
                           width="100%">
                        <tr>
                            <td>回访日期</td>
                            <td colspan="2">
                                <input size="10" type="text" value="${before30Day}" id="eventReturn_startDate" style="width: 150px" class="easyui-datebox" data-options="required:true" />&nbsp;~&nbsp;
                                <input size="10" type="text" value="${today}" id="eventReturn_endDate" style="width: 150px" class="easyui-datebox" data-options="required:true"  />
                            </td>
                            <td>
                                <div class="submitBtns" style="text-align: left">
                                    <a href="javascript:void(0)" id="btn_eventReturn_query">查找</a>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
            <div class="receiptInfo tabs-form withtool">
                <table id="eventReturn"  cellspacing="0" cellpadding="0"></table>
            </div>
		</div>
        <div title="主管处理">
            <form id="chargeDealForm" class="tabs-form" method="post">

                <div class="baseInfo" style="margin: 0">
                    <table class="" border="0" cellspacing="0" cellpadding="0"
                           width="100%">
                        <tr>
                            <td>事件日期</td>
                            <td colspan="2">
                                <input size="10" type="text" value="${before7Day}" id="chargeDeal_startDate" style="width: 150px" class="easyui-datebox" data-options="required:true" />&nbsp;~&nbsp;
                                <input size="10" type="text" value="${today}" id="chargeDeal_endDate" style="width: 150px" class="easyui-datebox" data-options="required:true"  />
                            </td>
                            <td>
                                <div class="submitBtns" style="text-align: left">
                                    <a href="javascript:void(0)" id="btn_chargeDeal_query">查找</a>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
            <div class="receiptInfo tabs-form withtool">
                <table id="chargeDeal"  cellspacing="0" cellpadding="0"></table>
            </div>
        </div>
        <div title="退款信息">
            <form id="refundsmsForm" class="tabs-form" action="" method="post">

                <div class="baseInfo" style="margin: 0">
                    <table class="" border="0" cellspacing="0" cellpadding="0"
                           width="100%">

                        <tr>
                            <td>订单编号</td>
                            <td><input class="easyui-validatebox" id="orderids" type="text" value=""/></td>
                            <td>退款分类</td>
                            <td>
                                <select class="easyui-combobox" id="refundTypes" type="text" value="">
                                    <option value="1">1.邮政退款</option>
                                    <option value="2">2.网银退款</option>
                                    <option value="3">3.信用卡退款</option>
                                    <option value="4">4.支付宝退款</option>
                                </select>

                            </td>
                            <td>创建日期</td>
                            <td colspan="2">
                                <input size="10" type="text" value="${before30Day}" id="refund_startDate" style="width: 150px" class="easyui-datebox" data-options="required:true,validType:'refundquerydatevalidator'" />&nbsp;~&nbsp;
                                <input size="10" type="text" value="${today}" id="refund_endDate" style="width: 150px" class="easyui-datebox" data-options="required:true,validType:'refundquerydatevalidator'"  />
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4"></td>
                            <td colspan="2">
                                <div class="submitBtns" style="text-align: left">
                                    <a href="javascript:void(0)" id="searchRefund"  >查询</a>
                                    <a href="javascript:void(0)" id="sendsms" onclick="sendSms()">保存并发送短信</a>
                                    <a href="javascript:void(0)" id="cancleRefund" onclick="cancleRefund()">取消退款</a>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </form>

            <div class="receiptInfo tabs-form withtool">
                <table id="refundInfo"  cellspacing="0" cellpadding="0"></table>
            </div>
        </div>
        <%@include file="mytask_cusnote.jsp"%>
        <div title="客服任务">

            <div class="receiptInfo tabs-form withtool">
                <table id="serviceTasks"  cellspacing="0" cellpadding="0"></table>
            </div>
        </div>
        <div title="修改审批">
			<form id="auditTaskForm" class="tabs-form" action="" method="post">

				<div class="baseInfo" style="margin: 0">
					<table class="" border="0" cellspacing="0" cellpadding="0"
						width="100%">
						<tr>
							<td>任务类型</td>
							<td><select id="auditTaskTypes" name="auditTaskTypes"
								style="width: 134px" class="easyui-combobox">
									<option value="">--请选择--</option>
									<c:forEach items="${auditTaskTypes }" var="l"
										varStatus="indexs">
										<option value="${indexs.index}">${l }</option>
									</c:forEach>
							</select></td>
							<td>任务状态</td>
							<td><select id="auditTaskStatus" name="auditTaskStatus"
								style="width: 134px" class="easyui-combobox">
									<option value="">--请选择--</option>
									<c:forEach items="${auditTaskStatus }" var="l"
										varStatus="indexs">
										<option value="${indexs.index}">${l }</option>
									</c:forEach>
							</select></td>
							<td>客户编号</td>
							<td><input id="contactID4Audit" name="contactID4Audit"
								type="text" value="" maxlength="20" /></td>
						</tr>
						<tr>
							<td>订单编号</td>
							<td><input id="orderID4Audit" name="orderID4Audit"
								type="text" maxlength="20" /></td>
							<td>订单创建座席</td>
							<td><input id="orderCreatedUserID4Audit"
								name="orderCreatedUserID4Audit" type="text" maxlength="20" /></td>
							<td>所在部门</td>
							<td><input id="department" name="department"
								value="${department}" type="text" maxlength="20"
								disabled="disabled" /></td>
						</tr>
						<tr>
							<td>申请座席</td>
							<td><input id="crUser4Audit" name="crUser4Audit" type="text"
								value="" maxlength="20" /></td>
							<td>生成时间</td>
							<td><input size="10" type="text" id="startDate4Audit"
								style="width: 95px" />&nbsp;~&nbsp; <input size="10"
								type="text" id="endDate4Audit" style="width: 95px" /></td>
							
							<td colspan="2">
								<div class="submitBtns" >
									<a href="javascript:void(0)" id="btn_auditTask">查找</a>&nbsp;&nbsp;&nbsp;&nbsp; <a
										href="javascript:void(0)" id="btn_auditClear">清空</a>
								</div>
							</td>
						</tr>

					</table>
				</div>
			</form>
			<div class="receiptInfo tabs-form">
				<table id="table_auditTask" cellspacing="0" cellpadding="0"
					data-options=""></table>
			</div>
		</div>

        <div title="任务列表">
            <form id="campaignTaskForm" class="tabs-form" action="" method="post">

                <div class="baseInfo" style="margin: 0">
                    <table class="" border="0" cellspacing="0" cellpadding="0"
                           width="100%">
                        <tr>
                            <td>营销名称</td>
                            <td><input class="easyui-validatebox" id="campaignName"
                                       name="campaignId" type="text" value="" /></td>

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
                            <td>任务来源</td>
                            <td><select id="campaignTaskSourceTypes" name="campaignTaskSourceTypes"
                                        style="width: 134px" class="easyui-combobox">
                                <option value="">--请选择--</option>
                                <c:forEach items="${campaignTaskSourceTypes}" var="item"
                                           varStatus="indexs">
                                    <option value="${indexs.index}">${item}</option>
                                </c:forEach>
                            </select></td>
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

	<div id="w_outphone" style="display: none;" style="width:500px;">
        <div style="padding:10px;">
            <table>
                <tr><td style="padding:5px;">呼出电话:</td><td style="padding:5px;"><input name="d_outPhone" id="d_outPhone" type="text" value="15036233666" /></td></tr>
            </table>
        </div>
        <input id="d_outInst" type="hidden" value=""/>
       <p class="winBtnsBar textC"><a class="window_btn"  data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:window.parent.outPhone2('15036233666','4232')">确定</a> </p>
	</div>
	
	<div id="window_campaignTasktip" style="display: none;">
		<div class="popWin_wrap">
			<p id="p_assign_tip">取到一条数据</p>
			<p>
				有效期为&nbsp;<span id="span_contactStartDate" class=""></span>&nbsp;到&nbsp;<span
					id="span_contactEndDate"></span>
			</p>
			<p>
				立即执行该任务&nbsp;<a id="a_taskId" class="link"></a><span id="span_contactId"
					style="display: none;"></span>
			</p>
		</div>
	</div>
	
	<div id="window_processOrderCancelAuditTask" style="display: none;">
	</div>

    <div id="revisitDialog" style="" data-options="closed:true,modal:true,draggable:false,top:100" >
    </div>

    <div id="drawbackDialog"  style="padding:10px" data-options="closed:true,modal:true,draggable:false,top:100" />


    <div id="addressConfirmDialog"  style="padding:10px" data-options="closed:true,modal:true,draggable:false,top:100" />
	 </body>

</html>