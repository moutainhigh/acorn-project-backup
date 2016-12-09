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
<script type="text/javascript" src="${ctx}/static/js/task/mytask.js?${rnd}"></script>
<script type="text/javascript" src="${ctx}/static/js/task/task.js?${rnd}"></script>
<script type="text/javascript" src="${ctx}/static/js/task/allocateTask.js?${rnd}"></script>
<script type="text/javascript" src="${ctx}/static/js/task/taskAssign.js?${rnd}"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.ajaxQueue.min.js"></script>
</head>
<body>
	<div id="mytask_tabs" class="easyui-tabs" style="height: auto">
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
		
		<div title="自助取数">
			<div class="receiptInfo tabs-form">
				<table id="table_jobRelationex" cellspacing="0" cellpadding="0"
					data-options="">
				</table>
			</div>
		</div>
		
		<c:if test="${assignRights}">
	        <div title="数据分配">
	            <div class="form-tabs task_allocation">
	                <div class="panel_con">
	                    <form id="queryAvaliableForm" name="queryAvaliableForm">
	                        <table width="80%" style="margin:0 auto;">
	                            <tr>
	                                <td>
	                                     <label   style="width:80px;display:inline-block">业务编号</label>
	                                     <select id="jobNum" name="jobNum" style="width:230px" class="easyui-combobox ml10"  data-options="required:true"></select>
	                                </td>
	                                <td >
	                                    <label style="width:80px;display:inline-block">营销计划</label>
	                                    <select id="campaignId" name="campaignId" style="width:230px" class="easyui-combobox"  data-options="required:true, valueField: 'id',  textField: 'name'">
	                                    </select>
	                                </td>
	
	                            </tr>
	                            <tr>
	                                <td>
	                                    <div id="show_priority">
	                                    <label   style="width:80px;display:inline-block">客户群批次</label>
	                                    <select id="customerBatch" name="customerBatch" style="width:230px" class="easyui-combobox ml10" data-options="required:true">
	
	                                    </select>
	                                    </div>
	                                </td>
	                                <td><label style="width: 80px; display: inline-block">数据状态</label>
										<select id="dataState" name="dataState" style="width: 230px" class="easyui-combobox">
											<option selected="" value="0">未分配</option>
<!-- 											<option value="1">分配到组</option>
											<option value="2">分配到座席</option> -->
	                                        <!--
											<option value="3">任务已执行</option>
											-->
										</select>
									</td>
	                            </tr>
	                            <tr>
	                                <td></td>
	                                <td>
	                                    <div class="submitBtns" style="margin-left: 52px">
	                                        <a href="javascript:void(0)" id="a_allo" onclick="javascript:findAvaliableQty()">查询</a><a class="ml10" id="a_alloClear" href="javascript:void(0)">清空</a>
	                                    </div>
	                                </td>
	                            </tr>
	                        </table>
	                    </form>
	                </div>
	
	                <div class="mt10 q_r">可分配数据量&nbsp;&nbsp;<span id="v_allotNum">0</span><font color="#ff4500"><apan style="padding:0 0 0 200px;" id="v_msg"></apan></font> <!-- <b class="fr">剩余数据量&nbsp;&nbsp;<span id="v_overplusNum">0</span></b> --></div>
	
	                <div class="sift" id="div_a_departmentManager">
	                    <label style="width:70px;display:inline-block">选择工作组</label><input id="v_selectGroup" type="text" style="width:145px" />
                        <!--
	                     <span class="rborder">数据分配策略
	                		 <label><input type="radio" name="assignStrategy" value="cycle">循环分配</label>
	                		 <label><input type="radio" name="assignStrategy" value="order">顺序分配</label>
	                	</span>
	                	-->
	                    <label style="display:inline-block"><input id="v_allotGroup"  type="checkbox" onclick="vallotGroup();" value="checkbox" style="vertical-align: top">平均分配</label>
	                    <span class="submitBtns ml10"><a id="allotToGroupBut" href="javascript:allotToGroup();" style="padding:2px 10px">分配</a>
	                    </span>
	                </div>
	                <table id="workgroupT"></table>
	               
	               <c:if test="${isSupervisor}"> 
		               <div class="sift mt10">
			                <div class="ti" style="padding-top:0;font-size: 12px;line-height: 32px;">选择座席</div>
			                <div style="margin-left: 80px">
			                	<span class="rborder">数据分配策略
			                		 <label><input type="radio" name="agentAssignStrategy" value="cycle" checked="checked">循环分配</label>
			                		 <label><input type="radio" name="agentAssignStrategy" value="order">顺序分配</label>
			                	</span>	 
			                   <label style="display:inline-block" >
			                        <input id="averageMode" style="vertical-align: top" type="checkbox" value="checkbox">平均分配模式</label>
				                <span class="submitBtns" style="margin-left: 20px" >
				                	<a href="javascript:void(0)" id="assignValidate" style="padding:2px 18px" >计算校验</a>
				                		<a href="javascript:void(0)" id="assignOperate" style="padding:2px 18px; display: none;">分配</a>
				                </span>
			                </div>
			            </div>
		            
		            	<table id="agentTb"></table>
	            	
	            	</c:if>
	            </div>
                <div id="loading" class="" style="display: none;">
                    <div style='position:absolute;width:100%;background: rgba(255,255,255,0.5); height:100%; text-align:center;top:0;font-size:12px; margin:0; padding:0;'>
                        <img alt='load' src='../static/images/loadingAnimation.gif' align='absMiddle'  bold='0'  />
                    </div>
                </div>
	        </div>
	        <%--<%@include file="mytask_hist.jsp"%>--%>
        </c:if>
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
	
	<%--<div id="loading" class="" style="display: none;">--%>
		<%--<div style='position:absolute;width:100%;background:#333;opacity:0.5; height:1000px; text-align:center;top:0;font-size:12px; margin:0; padding:0;'>--%>
	        <%--<img alt='load' src='../static/images/loadingAnimation.gif' align='absMiddle'  bold='0'  />--%>
	    <%--</div>--%>
	<%--</div>--%>
	 </body>

</html>