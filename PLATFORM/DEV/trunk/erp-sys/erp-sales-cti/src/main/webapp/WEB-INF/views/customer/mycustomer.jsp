<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	
</head>
<body>
	<link href="${ctx}/static/style/easyui.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/static/style/themes/icon.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/style/myOrders.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		var isINB = eval('${isINB}');
	</script>
	<script type="text/javascript" src="${ctx}/static/js/customer/mycustomer.js?${rnd}"></script>
	<div id="tabs" class="easyui-tabs" style="height: auto">
	
		<c:if test="${!isINB }">
			<div title="全部客户">
				<form id="allCustomerForm" class="tabs-form" action="" method="post">
	
					<div class="baseInfo" style="margin: 0">
						<table class="" border="0" cellspacing="0" cellpadding="0"
							width="100%">
							<tr>
								<td>客户姓名</td>
								<td><input id="name" name="name" type="text" maxlength="20" /></td>
								<td>客户编号</td>
								<td><input id="contactid" name="contactid" type="text" class="easyui-numberbox"  maxlength="20" /></td>
								<td>座席工号</td>
								<td>
									<c:choose>
										<c:when test="${isSupervisor==false}">
											<input id="agentId" name="agentId" type="text" maxlength="20" value="${agentId }" readonly="readonly" style="border-color: silver #D9D9D9 #D9D9D9;background-color: #F5F5F5; color:#ACA899;"/>
										</c:when>
										<c:when test="${isSupervisor==true}">
											<input id="agentId" name="agentId" type="text" maxlength="20" value="${agentId }"
												class="easyui-validatebox"  data-options="prompt:'输入座席工号', required:true"  validType="remote['/myorder/myorder/checkGrpMember','usrId']" invalidMessage="非本主管下的座席工号"/>
										</c:when>
									</c:choose>
									
								 </td>
							</tr>
							<tr>
								<td>客户电话</td>
								<td><input class="fl" id="phoneNo" name="phoneNo" type="text" maxlength="20" />
								
								</td>
									<td>
								电话类型
								</td>
								<td>
								<label class="fl"><input class="fl" name="phoneType" type="radio" maxlength="20" value="4" checked="checked"/>手机</label>
								<label class="fl ml10"><input class="fl" name="phoneType" type="radio" maxlength="20" value="-1"/>固话</label>
								
									
								</td>
								<td>客户来源</td>
								<td>
									<select id="customerFrom" class="easyui-combobox" required="true" style="width:134px" >
										<option value="1">老客户</option>
										<option value="2">自取客户</option>
										<option value="3">历史成单</option>
									</select>
								</td>
							</tr>
							<tr>
								<td>会员等级</td>
								<td>
									<select id="memberLevel" name="memberLevel"  style="width:134px" class="easyui-combobox" >
										<option value="">--请选择--</option>
										<c:forEach items="${memberLevelList }" var="l">
											<option value="${l.memberlevelid}">${l.memberlevel }</option>
										</c:forEach>
									</select>
								</td>
								<td>会员类型</td>
								<td>
									<select style="width:134px" class="easyui-combobox" id="customerType" name="customerType">
										<option value="">--请选择--</option>
										<c:forEach items="${memberTypeList }" var="t">
											<option value="${t.id }">${t.dsc }</option>
										</c:forEach>
									</select>
								</td>
								 <td><label id="dateLabel" class="datepart" style="display: none;">客户创建时间</label></td>
								<td width="25%">
									<div class="datepart" style="display: none;">
										<input size="10" type="text" id="beginDate" value="${beginDate }" class="easyui-datebox" required="true" style="width:95px" />&nbsp;~&nbsp;
										<input size="10" type="text" id="endDate" value="${endDate }" class="easyui-datebox" required="true" style="width:95px" />
									</div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td colspan="2">
									<div class="submitBtns" style="text-align: left">
										<a href="javascript:void(0);" id="allCustomerQuery">查找</a>&nbsp;&nbsp;&nbsp;&nbsp;
										<a href="javascript:void(0);" id="allCustomerRest">清空</a>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</form>
				
				<div class="receiptInfo tabs-form">
					<table id="AllCustomerDT" cellspacing="0" cellpadding="0" data-options=""></table>
				</div>
			</div>
		</c:if>
	
		<div title="修改客户申请" data-options="cache:false">
			<form id="customerAuditForm" class="tabs-form" action="" method="post">
				<div class="baseInfo" style="margin: 0">
					<table class="" border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td width="10%">客户姓名</td>
							<td width="20%"><input id="contactName_a" type="text" maxlength="50" /></td>
							<td width="10%">客户编号</td>
							<td width="20%"><input id="contactid_a" type="text" class="easyui-numberbox" maxlength="20" /></td>
							<td width="10%">提交审批时间</td>
							<td width="30%">
								<input size="10" type="text" id="beginFlowDate" value="${beginDate }"  class="easyui-datebox" data-options="required:true" style="width:95px"/>&nbsp;~&nbsp;
								<input size="10" type="text" id="endFlowDate" value="${endDate }" class="easyui-datebox" data-options="required:true" style="width:95px"/>
							</td>
						</tr>
						<tr>
							<td>座席工号</td>
							<td>
								<c:choose>
									<c:when test="${isSupervisor==false}">
										<input id="agentId_a" type="text" maxlength="50"  value="${agentId }" readonly="readonly" style="border-color: silver #D9D9D9 #D9D9D9;background-color: #F5F5F5; color:#ACA899;"/>
									</c:when>
									<c:when test="${isSupervisor==true}">
										<input id="agentId_a" type="text" maxlength="50"  value="${agentId }"
											class="easyui-validatebox"  data-options="prompt:'输入座席工号', required:true"  validType="remote['/myorder/myorder/checkGrpMember','usrId']" invalidMessage="非本主管下的座席工号" />
									</c:when>
								</c:choose>
							</td>
							<td>工作流状态</td>
							<td>
								<select id="instStatus"  style="width:134px" class="easyui-combobox" >
									<option value="">--请选择--</option>
									<c:forEach items="${auditTaskStatus }" var="l"
										varStatus="indexs">
										<option value="${indexs.index}">${l }</option>
									</c:forEach>
								</select>
							</td>
							<td colspan="2" align="right">
								<div class="submitBtns" style="text-align:left">
									<a href="javascript:void(0);" id="customerAuditQuery">查找</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="javascript:void(0);" id="customerAuditReset">清空</a>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</form>
			<div class="receiptInfo tabs-form">
				<table id="auditCustomerDt" cellspacing="0" cellpadding="0" data-options=""></table>
			</div>
		</div>

		<c:if test="${!isINB }">
	        <div title="新增客户申请" data-options="cache:false">
	            <form id="customerAddAuditForm" class="tabs-form" action="" method="post">
	                <div class="baseInfo" style="margin: 0">
	                    <table class="" border="0" cellspacing="0" cellpadding="0" width="100%">
	                        <tr>
	                            <td width="10%">客户姓名</td>
	                            <td width="20%"><input id="contactName_a_add" type="text" maxlength="50" /></td>
	                            <td width="10%">客户编号</td>
	                            <td width="20%"><input id="contactid_a_add" type="text" class="easyui-numberbox" maxlength="20" /></td>
	                            <td width="10%">提交审批时间</td>
	                            <td width="30%">
	                                <input size="10" type="text" id="beginFlowDate_add" value="${beginDate }"  class="easyui-datebox" data-options="required:true" style="width:95px"/>&nbsp;~&nbsp;
	                                <input size="10" type="text" id="endFlowDate_add" value="${endDate }" class="easyui-datebox" data-options="required:true" style="width:95px"/>
	                            </td>
	                        </tr>
	                        <tr>
	                            <td>座席工号</td>
	                            <td>
	                            	<c:choose>
										<c:when test="${isSupervisor==false}">
											<input id="agentId_a_add" type="text" maxlength="50" value="${agentId }" readonly="readonly" style="border-color: silver #D9D9D9 #D9D9D9;background-color: #F5F5F5; color:#ACA899;"/>
										</c:when>
										<c:when test="${isSupervisor==true}">
											<input id="agentId_a_add" type="text" maxlength="50" value="${agentId }" 
	                            				class="easyui-validatebox"  data-options="prompt:'输入座席工号', required:true"  validType="remote['/myorder/myorder/checkGrpMember','usrId']" invalidMessage="非本主管下的座席工号" />
										</c:when>
									</c:choose>
	                            </td>
	                            <td>工作流状态</td>
	                            <td>
	                                <select id="instStatus_add"  style="width:134px" class="easyui-combobox" >
	                                    <option value="">--请选择--</option>
	                                    <c:forEach items="${auditTaskStatus }" var="l"
	                                               varStatus="indexs">
	                                        <option value="${indexs.index}">${l }</option>
	                                    </c:forEach>
	                                </select>
	                            </td>
	                            <td colspan="2" align="right">
	                                <div class="submitBtns" style="text-align:left">
	                                    <a href="javascript:void(0);" id="customerAddAuditQuery">查找</a>&nbsp;&nbsp;&nbsp;&nbsp;
	                                    <a href="javascript:void(0);" id="customerAddAuditReset">清空</a>
	                                </div>
	                            </td>
	                        </tr>
	                    </table>
	                </div>
	            </form>
	            <div class="receiptInfo tabs-form">
	                <table id="auditAddCustomerDt" cellspacing="0" cellpadding="0" data-options=""></table>
	            </div>
	        </div>
		</c:if>

	</div>
	<div id="loading" class="" style="display: none;">
		<div style='position: absolute; width: 100%; background: rgba(255, 255, 255, 0.5); height: 100%; text-align: center; top: 0; font-size: 12px; margin: 0; padding: 0;'>
			<img alt='load' src='/static/images/loadingAnimation.gif' align='absMiddle' bold='0' />
		</div>
	</div>
	<!-- <div id="taskDialogWrap" style="display: none;"> -->
	<div id="taskDialog" class="easyui-window" data-options="closed:true,draggable:false" >
        <div class="form-tabs">
		<input type="hidden" id="customerId_hidden">
        <p>任务：
            <select id="campaignId"  name="campaignId" style="width:150px;">
            <option value="">--请选择--</option>
            <c:forEach items="${campaignList }" var="c">
                <option value='${c.json}'>${c.name }</option>
            </c:forEach>
            </select>
            <span id="message" style="color: red;"></span>
        </p>
        <p class="textC mt10"><a href="javascript:void(0);" id="createTask" onclick="subCallback('p_mycustomer', 'ajaxCreateTask')" class="window_btn ml10">创建</a>
            <a href="javascript:void(0);" id="cancelTask" onclick="closeWin('taskDialog')" class="window_btn ml10">取消</a></p>
        </div>

	</div>
	<!-- </div> -->
</body>
</html>