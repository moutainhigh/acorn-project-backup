<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>

</head>
<body>
	<link href="${ctx}/static/style/easyui.css" rel="stylesheet"
		type="text/css" />
	<link href="${ctx}/static/style/themes/icon.css" type="text/css"
		rel="stylesheet" />
	<link href="${ctx}/static/style/myOrders.css?${rnd}" rel="stylesheet"
		type="text/css" />
	<style type="text/css">
		.reminer_hidden{
			display: none;
		}
	</style>
	<script type="text/javascript" src="${ctx}/static/js/myorder.js?${rnd}"></script>
	<script type="text/javascript" src="${ctx}/static/js/myorder/problematicorder.js?${rnd}"></script>
    <script type="text/javascript" src="${ctx}/static/js/jstorage.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/jstorage-wrapper.js"></script>
    <script type="text/javascript">
        var orderStatuses=eval(${order_statuses });
        var payTypes=eval(${order_paytypes });
        var auditStatuses=eval(${audit_statuses });
        var problemStatuses=eval(${problem_statuses });
        var isManager=eval(${isManager });
        var isQualityInspector=eval(${isQualityInspector});
        var currentUsrId=eval(${currentUsrId});
        var orderAuditStatuses=eval(${order_audit_statuses});

        var itemDataList=[];
        var i=0;
        for(i=0;i<orderAuditStatuses.length;i++)
        {
            itemDataList[i]={"id":orderAuditStatuses[i].id, "val":orderAuditStatuses[i].dsc};
        }
        itemDataList[i]={"id":"", "val":"全部"};

        var resultStatusList=[];
        resultStatusList[0]= {"id":1, "val":"待付款"};
        resultStatusList[1]= {"id":2, "val":"成功"};
        resultStatusList[2]= {"id":3, "val":"全部"};
    </script>

	<div id="myorder_tabs" class="easyui-tabs" style="height: auto">
		<div title="全部订单">
			<form id="myOrderForm" class="tabs-form" action="" method="post">

				<div class="baseInfo" style="margin: 0">
					<table class="" border="0" cellspacing="0" cellpadding="0"
						width="100%">
						<tr>
							<td>订单编号</td>
							<td ><input id="orderid" name="orderid"
								type="text" value="" maxlength="255" /></td>

							<td>订单状态</td>
							<td><input style="width:134px" id="tbStatus" class="inputStyle easyui-combobox"
								name="status"></td>

							<td>包裹单号</td>
							<td><input id="tbMailid" name="mailid" type="text"
								maxlength="255" /></td>
						</tr>
						<tr>
							<td>客户编号</td>
							<td ><input id="tbContactid" name="contactid"
								type="text" value="" maxlength="255" /></td>

							<td>客户姓名</td>
							<td><input id="tbContactname" name="contactname" type="text"
								maxlength="255" /></td>
							<td>座席工号</td>
                            <c:if test="${isQualityInspector==true}">
                                <td><input id="tbCrusr" name="crusr" type="text" class="easyui-validatebox"  data-options="prompt:'输入座席工号', required:true"  validType="remote['/myorder/myorder/isValidUser','usrId']" invalidMessage="无效座席工号"
                                           maxlength="255"  value="${currentUsrId }"/></td>
                            </c:if>
                            <c:if test="${isQualityInspector==false}"><td><input id="tbCrusr" name="crusr" type="text" class="easyui-validatebox"  data-options="prompt:'输入座席工号', required:true"  validType="remote['/myorder/myorder/checkGrpMember','usrId']" invalidMessage="非本主管下的座席工号"
								maxlength="255"  value="${currentUsrId }"/></td>
                            </c:if>
						</tr>
						<tr class="u">



                            <td>订单有效性</td>
                            <td><input style="width:134px" id="tbCheckResult" class="inputStyle easyui-combobox"
                                       name="checkResult"></td>
                            <td>订单生成时间</td>
                            <td colspan="2"><input style="width:95px" class="easyui-datebox"
                                                   type="text" id="tbBeginCrdt" name="beginCrdt" value="${startDate }">&nbsp;~&nbsp;<input
                                    style="width:95px" class="easyui-datebox" type="text" id="tbEndCrdt"
                                    name="endCrdt" value="${endDate }"></td>
							<td align="left" valign="bottom" >
							<div style="width:140px" class="textR"><a href="javascript:void(0);" class="more" id="tbShowMoreCondition"><span  class="accordion-collapse"></span>更多查询条件</a></div>
							</td>
						</tr>
						<tr  id="morConditons1">
							<td>收货人姓名</td>
							<td ><input id="tbGetcontactname" name="getcontactname"
								type="text" maxlength="255" /></td>
							<td >收货人电话</td>
							<td ><input id="tbPhone" name="phone" type="text"
								maxlength="255" /></td>
							<td ></td>
							<td ></td>
						</tr>
						<tr id="morConditons2">
							<td >收货地址</td>
                            <td colspan="5"><div id="order_address"></div></td>
							<%--<td  colspan="5"><jsp:include page="/common/address_no_validation.jsp" >
                                <jsp:param value="001" name="instId"/>
                            </jsp:include></td> --%>
						</tr>
						<tr id="morConditons3">
                            <td>父订单号</td>
                            <td><input id="tbParentid" name="parentid" type="text"
                                       value="" maxlength="255" /></td>
							<td>订单出库时间</td>
							<td ><input style="width:95px" class="easyui-datebox"
								type="text" id="tbBeginOutdt" name="beginOutdt">&nbsp;~&nbsp;<input
								style="width:95px" class="easyui-datebox" type="text" id="tbEndOutdt"
								name="endOutdt"></td>


							<td></td>
							<td></td>
						</tr>
						<tr id="morConditons4">
							<td>支付方式</td>
							<td><input id="tbPaytype" class="inputStyle easyui-combobox"
								name="paytype"></td>

							<td>订购商品</td>
							<td><input id="tbProdname" name="prodname" type="text"
								value="" maxlength="255" /></td>
							<td colspan="2">

</td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td colspan="2"><div class="submitBtns" >
						        <a href="javascript:void(0);" id="myOrdersearch">查询</a>
                                <a href="javascript:void(0);" id="myOrderreset">清空</a>
                                <a href="javascript:void(0);" id="myOrderallot" style="padding: 2px 10px; display: none;">分配跟单任务</a>
					</div></td>
						</tr>
					</table>


				</div>
			</form>
			<div class="history tabs-form">
				<table id="myOrderlist" ></table>
			</div>
		</div>
		<div title="待付款订单">
			<form id="myOrderForm_pay" class="tabs-form" action="" method="post">

				<div class="baseInfo" style="margin: 0">
					<table class="" border="0" cellspacing="0" cellpadding="0"
						width="100%">
						<tr>
							<td >订单编号</td>
							<td><input id="orderid_pay" name="orderid_pay"
								type="text" value="" maxlength="255" /></td>

							<td>客户编号</td>
							<td ><input id="tbContactid_pay"
								name="contactid_pay" type="text" value="" maxlength="255" /></td>

							<td>座席工号</td>
							<td><input id="tbCrusr_pay" name="crusr_pay" type="text"  class="easyui-validatebox" data-options="prompt:'输入座席工号', required:true"  validType="remote['/myorder/myorder/checkGrpMember','usrId']" invalidMessage="非本主管下的座席工号"
								maxlength="255"  value="${currentUsrId }"/></td>

						</tr>

						<tr>
							<td>客户姓名</td>
							<td><input id="tbContactname_pay" name="contactname_pay"
								type="text" maxlength="255" /></td>
							<td>订单生成时间</td>
							<td><input class="easyui-datebox"style="width:100px"
								type="text" id="tbBeginCrdt_pay" name="beginCrdt_pay" value="${startDate }">&nbsp;~&nbsp;
                                <input class="easyui-datebox" type="text" id="tbEndCrdt_pay" style="width:100px"
								name="endCrdt_pay" value="${endDate }"></td>
							<td>支付方式</td>
							<td><input id="tbPaytype_pay"
								class="inputStyle easyui-combobox" name="paytype_pay"></td>
						</tr>
						<tr>
							<td>收货人姓名</td>
							<td><input id="tbGetcontactname_pay"
								name="getcontactname_pay" type="text" maxlength="255" /></td>
							<td>收货人电话</td>
							<td><input id="tbPhone_pay" name="phone" type="text"
								maxlength="255" /></td><td>订购商品</td>
							<td><input id="tbProdname_pay" name="prodname_pay"
								type="text" value="" maxlength="255" /></td>
						</tr>
						<tr>
							<td>收货地址</td>
                            <td  colspan="5">
                                <div id="pay_order_address" >
                                    <%--<input id="province_place" class="province"  style="width:100px;" /> &nbsp;
                                    <input id="cityId_place"  class="cityId" />&nbsp;
                                    <input id="countyId_place" class="countyId" />&nbsp;
                                    <input id="areaId_place"  class="areaId" />--%>
                                </div>
                            </td>
							<%--<td  colspan="5"><jsp:include page="/common/address_no_validation.jsp" >
                                <jsp:param value="002" name="instId"/>
                            </jsp:include></td>--%>

						</tr>
                        <%--<tr>--%>
                            <%--<td>支付结果</td>--%>
                            <%--<td><input id="tbPaytype_result"--%>
                                       <%--class="inputStyle easyui-combobox" name="paytype_result"></td>--%>
                        <%--</tr>--%>
						<tr>
                            <td>支付结果</td>
                            <td><input id="tbPaytype_result"
                                       class="inputStyle easyui-combobox" name="paytype_result"></td>
							<td  colspan="2"></td>
							<td colspan="2"><div class="submitBtns" >
						<a href="javascript:void(0);" id="myOrdersearch_pay">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" id="myOrderreset_pay">清空</a>
					</div></td>

						</tr>

					</table>

				</div>
			</form>
			<div class="history tabs-form">

				<table id="myOrderlist_pay" >
					<%--<thead>--%>
						<!--<tr>
                                        <th data-options="field:'itemid',width:80">订单编号</th>
                                        <th data-options="field:'productid',width:300">订单生成时间</th>
                                        <th data-options="field:'listprice',width:80">订单状态</th>
                                        <th data-options="field:'unitcost',width:80">订单金额</th>
                                        <th data-options="field:'attr1',width:80">生成座席</th>
                                         <th data-options="field:'unitcost',width:80">座席组</th>
                                         <th data-options="field:'unitcost',width:80">面单号</th>
                                        <th data-options="field:'attr1',width:80">物流状态</th>
                                    </tr>-->
					<%--</thead>--%>
				</table>
			</div>
		</div>
		<div title="问题单">
			<form id="myProblemOrderForm" class="tabs-form" action="" method="post">

				<div class="baseInfo" style="margin: 0">
					<table class="" border="0" cellspacing="0" cellpadding="0"
						width="100%">
						<tr>
							<td>订单编号</td>
							<td ><input id="input_problem_orderid" name="input_problem_orderid"
								type="text" value="" maxlength="255" /></td>

							<td>订单状态</td>
							<td><input style="width:134px" id="input_problem_order_tbStatus" class="inputStyle easyui-combobox"
								name="input_problem_order_status"></td>

							<td>包裹单号</td>
							<td><input id="input_problem_tbMailid" name="input_problem_mailid" type="text"
								maxlength="255" /></td>
						</tr>
						<tr>
							<td>客户编号</td>
							<td ><input id="input_problem_tbContactid" name="input_problem_contactid"
								type="text" value="" maxlength="255" /></td>

							<td>客户姓名</td>
							<td><input id="input_problem_tbContactname" name="input_problem_contactname" type="text"
								maxlength="255" /></td>
							<td>座席工号</td>
							<td><input id="input_problem_tbCrusr" name="input_problem_crusr" type="text" class="easyui-validatebox"  data-options="prompt:'输入座席工号', required:true" 
								validType="remote['/myorder/myorder/checkGrpMember','usrId']" invalidMessage="非本主管下的座席工号" 
								maxlength="255"  value="${currentUsrId }"/></td>
						</tr>
						<tr class="u" id="tr_my_problem_order_last">
							<td>订单生成时间</td>
							<td><input style="width:95px" class="easyui-datebox"
								type="text" id="input_problem_tbBeginCrdt" name="input_problem_beginCrdt" value="${startDate }">&nbsp;~&nbsp;<input
								style="width:95px" class="easyui-datebox" type="text" id="input_problem_tbEndCrdt"
								name="input_problem_endCrdt" value="${endDate }"></td>
							<td>处理状态</td>
							<td><input style="width:134px" id="input_problem_process_tbStatus" class="inputStyle easyui-combobox" name="input_problem_process_status">
							</td>
							<td align="left" valign="bottom" >
							<div style="width:140px" class="textR"><a href="javascript:void(0);" class="more" id="a_problem_tbShowMoreCondition"><span  class="accordion-collapse"></span>更多查询条件</a></div>
							</td>
							<td></td>
						</tr>
						
						<tr  name="tr_problem_morConditons1">
							<td>收货人姓名</td>
							<td ><input id="input_problem_tbGetcontactname" name="input_problem_getcontactname"
								type="text" maxlength="255" /></td>
							<td >收货人电话</td>
							<td ><input id="input_problem_tbPhone" name="input_problem_phone" type="text"
								maxlength="255" /></td>
							<td ></td>
							<td ></td>
						</tr>
						<tr name="tr_problem_morConditons2">
							<td >收货地址</td>
							<%--<td  colspan="5"><jsp:include page="/common/address_no_validation.jsp" >
                                <jsp:param value="_problematic_order" name="instId"/>
                            </jsp:include></td>--%>
                            <td  colspan="5">
                                <div id="problematic_order_address" />
                            </td>
						</tr>
						<tr name="tr_problem_morConditons3">
							<td>订单出库时间</td>
							<td ><input style="width:95px" class="easyui-datebox"
								type="text" id="input_problem_tbBeginOutdt" name="input_problem_beginOutdt">&nbsp;~&nbsp;<input
								style="width:95px" class="easyui-datebox" type="text" id="input_problem_tbEndOutdt"
								name="input_problem_endOutdt"></td>

							<td>父订单号</td>
							<td><input id="input_problem_tbParentid" name="input_problem_parentid" type="text"
								value="" maxlength="255" /></td>
							<td></td>
							<td></td>
						</tr>
						<tr name="tr_problem_morConditons4">
							<td>支付方式</td>
							<td><input id="input_problem_tbPaytype" class="inputStyle easyui-combobox"
								name="input_problem_paytype"></td>

							<td>订购商品</td>
							<td><input id="input_problem_tbProdname" name="input_problem_prodname" type="text"
								value="" maxlength="255" /></td>
							<td colspan="2">

</td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td colspan="2"><div class="submitBtns" >
						<a href="javascript:void(0);" id="a_myProblemOrdersearch">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" id="a_myProblemOrderreset">清空</a>
					</div></td>
						</tr>
					</table>


				</div>
			</form>
			<div class="history tabs-form">
				<table id="myProblemOrderlist" style="height: 350px">
					<thead>
					</thead>
				</table>
			</div>
		</div>
		<div title="待审核订单" data-options="cache:false">
			<form id="myOrderForm_audit" class="tabs-form" action="" method="post">

				<div class="baseInfo" style="margin: 0">
					<table class="" border="0" cellspacing="0" cellpadding="0"
						width="100%">
						<tr>
							<td>订单编号</td>
							<td width="150"><input id="orderid_audit"
								name="orderid_audit" type="text" value="" maxlength="255" /></td>

							<td>客户编号</td>
							<td width="200"><input id="tbContactid_audit"
								name="contactid_audit" type="text" value="" maxlength="255" /></td>

							<td>客户姓名</td>
							<td><input id="tbContactname_audit" name="contactname_audit"
								type="text" maxlength="255" /></td>

						</tr>
						<tr>
							<td>座席工号</td>
							<td><input id="tbCrusr_audit" name="crusr_audit" type="text"   class="easyui-validatebox" data-options="prompt:'输入座席工号', required:true" validType="remote['/myorder/myorder/checkGrpMember','usrId']" invalidMessage="非本主管下的座席工号"
								maxlength="255"  value="${currentUsrId }"/></td>
							<td>订单生成时间</td>
							<td colspan="4"><input style="width:95px" class="easyui-datebox"
								type="text" id="tbBeginCrdt_audit" name="beginCrdt_audit" value="${startDate }">&nbsp;~&nbsp;<input style="width:95px"
								class="easyui-datebox" type="text" id="tbEndCrdt_audit" name="endCrdt_audit" value="${endDate }"></td>
							<td align="right" valign="bottom" style="padding: 0">
						</tr>
						<tr>
							<td>支付方式</td>
							<td><input id="tbPaytype_audit"
								class="inputStyle easyui-combobox" name="paytype_audit"></td>
							<td>订购商品</td>
							<td><input id="tbProdname_audit" name="prodname_audit"
								type="text" value="" maxlength="255" /></td>
							<td>工作流状态</td>
							<td><input id="tbStatus_audit"
								class="inputStyle easyui-combobox" name="status_audit"></td>
						</tr>
					</table>
					<div class="submitBtns" style="text-align: right">
						<a href="javascript:void(0);" id="myOrdersearch_audit">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" id="myOrderreset_audit">清空</a>
					</div>
				</div>
			</form>

			<div class="history tabs-form">

				<table id="myOrderlist_audit" class="easyui-datagrid"
					style="height: 350px" data-options="singleSelect:true,url:''"
					pagination="true">
					<thead>
						<!--<tr>
                             <th data-options="field:'itemid',width:80">事件历史</th>
                             <th data-options="field:'productid',width:300">事件生成时间</th>
                             <th data-options="field:'listprice',width:80">生成座席</th>
                             <th data-options="field:'unitcost',width:80">解决方式</th>
                             <th data-options="field:'attr1',width:80">问题描述</th>
                              <th data-options="field:'unitcost',width:80">处理描述</th>
                              <th data-options="field:'unitcost',width:80">是否协办</th>
                         </tr>-->
					</thead>
				</table>
			</div>
		</div>
		<div title="催送货订单">
				<form class="tabs-form" id="reminderForm" action="" method="post">
					<div class="baseInfo" style="margin: 0">
						<table border="0" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td>订单编号</td>
								<td><input id="r_orderId" name="orderId" type="text" value="" maxlength="100" /></td>
								<td>订单状态</td>
								<td>
									<select id="r_orderStatus" name="status" class="easyui-combobox" style="width:134px"></select>
								</td>
								<td>包裹单号</td>
								<td><input id="r_mailId" name="mailId" type="text" maxlength="100" /></td>
							</tr>
							<tr>
								<td>客户编号</td>
								<td><input id="r_contactId" name="contactId" type="text" maxlength="100" /></td>
								<td>客户姓名</td>
								<td><input id="r_contactName" name="contactName" maxlength="100"></td>
								<td>座席工号</td>
								<td>
									<c:choose>
										<c:when test="${isSupervisor==false}">
											<input type="text" id="agentId" name="agentId" readonly="readonly" value="${currentUsrId }" style="border-color: silver #D9D9D9 #D9D9D9;background-color: #F5F5F5; color:#ACA899;"/>
										</c:when>
										<c:when test="${isSupervisor==true}">
											<input type="text" id="agentId" name="agentId" value="${currentUsrId}" class="easyui-validatebox" data-options="prompt:'输入座席工号', required:true" validType="remote['/myorder/myorder/checkGrpMember','usrId']" invalidMessage="非本主管下的座席工号" />
										</c:when>
									</c:choose>
								</td>
							</tr>
							<tr id="reminder_line" >
								<td>催送状态</td>
								<td>
									<select id="r_status" name="status" class="easyui-combobox" style="width:134px">
										<option value="">--请选择--</option>
										<option value="1">待处理</option>
										<option value="2">处理中</option>
										<option value="3"  selected="selected">已回复</option>
									</select>
								</td>
								<td>订单生成时间</td>
								<td colspan="2">
									<input size="10" type="text" id="r_beginCrDate" name="beginCrDate" class="easyui-datebox" value="${startDate }" style="width:95px" />&nbsp;~&nbsp;
									<input size="10" type="text" id="r_endCrDate" name="endCrDate" class="easyui-datebox" value="${endDate }" style="width:95px" />
								</td>
                                <td align="left" valign="bottom">
                                    <div style="width:140px" class="textR"><a href="javascript:void(0);" class="more" id="showMoreCondition"><span class="accordion-collapse"></span>更多查询条件</a></div>
                                </td>
							</tr>
							<tr class="reminer_hidden">
								<td>收货人姓名</td>
								<td><input id="r_receiverName" name="contactName" maxlength="100"></td>
								<td>收货人电话</td>
								<td><input id="r_receiverPhone" name="receiverPhone" type="text" maxlength="100" /></td>
								<td>父订单号</td>
								<td><input id="r_parentOrderId" name="parentOrderId" type="text" maxlength="100" /></td>
							</tr>
							<tr class="reminer_hidden">
								<td>收货地址</td>
								<%--<td colspan="3">
									<jsp:include page="/common/address_no_validation.jsp">
										<jsp:param value="_r" name="instId"/>
									</jsp:include>
								</td>--%>
                                <td colspan="3">
                                    <div id="remider_order_address" />
                                </td>
								<td>支付方式</td>
								<td>
									<select id="r_paytype" name="paytype" class="easyui-combobox" style="width:134px">
										<option value="">--请选择--</option>
										<option value="1">货到付款</option>
					                    <option value="2">信用卡</option>
					                    <option value="12">邮局汇款</option>
					                    <option value="13">银行转账</option>
					                    <option value="11">上门自提</option>
									</select>
								</td>
							</tr>
							<tr class="reminer_hidden">
								<td>订购商品</td>
								<td><input id="r_itemCode" name="itemCode" maxlength="100"></td>
								<td>订单出库时间</td>
								<td colspan="3">
									<input size="10" type="text" id="r_beginRfoutdt" name="beginRfoutdt" class="easyui-datebox" style="width:95px" />&nbsp;~&nbsp;
									<input size="10" type="text" id="r_endRfoutdt" name="endRfoutdt" class="easyui-datebox" style="width:95px" />
								</td>
							</tr>
							<tr>
								<td colspan="6" align="right">
									<div class="submitBtns" style="text-align: right">
										<a href="javascript:void(0);" id="queryReminder">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;
										<a href="javascript:void(0);" id="clearReminder">清空</a>
									</div>
								</td>
							</tr>
						</table>
					</div>	
				</form>
				
				<div class="history tabs-form">			
					<table id="reminderDataGrid" border="0" cellspacing="0" cellpadding="0" width="100%"></table>
				</div>
		</div>
        <c:if test="${isSupervisor}">
        <div title="分配跟单订单">
             <form class="tabs-form" id="fo_form" action="" method="post">
                <div class="baseInfo" style="margin: 0">
                    <table class="" border="0" cellspacing="0" cellpadding="0"
                           width="100%">
                        <tr>
                            <td>订单编号</td>
                            <td ><input id="fo_orderId" name="orderid"
                                        type="text" value="" maxlength="255" /></td>

                            <td>订单状态</td>
                            <td><input style="width:134px" id="fo_status" class="inputStyle easyui-combobox"
                                       name="status"></td>

                            <td>包裹单号</td>
                            <td><input id="fo_mailId" name="mailid" type="text"
                                       maxlength="255" /></td>
                        </tr>
                        <tr>
                            <td>客户编号</td>
                            <td ><input id="fo_contactId" name="contactid"
                                        type="text" value="" maxlength="255" /></td>

                            <td>客户姓名</td>
                            <td><input id="fo_contactName" name="contactname" type="text"
                                       maxlength="255" /></td>
                            <td>跟单人</td>
                            <td>
								<input id="fo_queryTrackUser" name="trackUser" type="text" />
                            </td>
                        </tr>
                        <tr class="u">
                            <td>分配跟单时间</td>
                            <td colspan="2"><input style="width:95px" class="easyui-datebox"
                                                   type="text" id="fo_beginDate" name="beginCrdt" value="${startDate }">&nbsp;~&nbsp;<input
                                    style="width:95px" class="easyui-datebox" type="text" id="fo_endDate"
                                    name="endCrdt" value="${endDate }"></td>
                            <td>&nbsp;</td>
                            <td>订单创建人</td>
                            <td align="left" valign="bottom" >
								<input id="fo_crusr" name="crusr" type="text" class="easyui-validatebox" />
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td colspan="2"><div class="submitBtns" >
                                <a href="javascript:void(0);" id="fo_query">查询</a>
                                <a href="javascript:void(0);" id="fo_reset">清空</a>
                            </div></td>
                        </tr>
                    </table>


                </div>
                 </form>
            <div class="tabs-form">
            	<table id="trackOrderDataGrid" style="height: 400px"> </table>
            </div>
        </div>
		</c:if>
	</div>

	<!--取消对话框-->
	<div id="dlgCancel" class="easyui-dialog"
               closed="true">
        <form id="fmCancel" method="post" class="popWin_wrap" novalidate>
            <table id="detail" class="editTable">
                <tr>
                    <td valign="top"><label class="ml10" for="txtOrderId">订单号</label></td>
                    <td><input id="txtOrderId" class="inputStyle ml10 mb10"
                               readonly="readonly" disabled="disabled" name="txtOrderId"></td>
                </tr>
                <tr>
                    <td  valign="top"><label class="ml10" for="txtRemark">取消原因</label></td>
                    <td><textarea id="txtRemark" rows="3" style="width:250px" class="datagrid-editable-input ml10" name="txtRemark"></textarea></td>
                </tr>
            </table>
        </form>
    </div>

    <input type="hidden" id="NCODPaytypes_hidden" value="${NCODPaytypes}"/>

	<%--<div id="dlg-buttons">--%>
		<%--<a href="javascript:void(0)" id="orderCancel_confirm"--%>
			<%--class="easyui-linkbutton" iconCls="icon-ok">确认</a> <a--%>
			<%--href="javascript:void(0)" id="orderCancel_cancel"--%>
			<%--class="easyui-linkbutton" iconCls="icon-cancel">取消</a>--%>
	<%--</div>--%>

    <!--转咨询对话框-->
    <div id="dlgConsult" class="easyui-dialog"
         closed="true" >
        <form id="fmConsult" method="post" class="popWin_wrap" novalidate>
            <table id="detail_Consult" class="editTable">
                <tr>
                    <td ><span id="consult_show_text"></span></td>
                    <td><input type="hidden" id="txtOrderId_Consult"/></td>
                </tr>
            </table>
        </form>
    </div>

    <!--跟单备注-->
    <div id="dlgNote" class="easyui-dialog"
         closed="true" >
        <form id="fmNote" method="post" class="popWin_wrap" novalidate>
            <table id="detail_Note" class="editTable">
                <tr>
                    <td ><span id="note_show_text"></span></td>
                    <td><input type="hidden" id="txtOrderId_Note"/></td>
                </tr>
                <tr>
                    <td  valign="top"><label class="ml10" for="txtAppend_note">备注：</label></td>
                    <td><textarea id="txtAppend_note" rows="3" style="width:250px" class="datagrid-editable-input ml10" name="txtRemark"></textarea></td>
                </tr>
            </table>
        </form>
    </div>

    <%--<div id="dlg-buttons-consult">--%>
        <%--<a href="javascript:void(0)" id="orderConsult_confirm"--%>
           <%--class="easyui-linkbutton" iconCls="icon-ok">确认</a> <a--%>
            <%--href="javascript:void(0)" id="orderConsult_cancel"--%>
            <%--class="easyui-linkbutton" iconCls="icon-cancel">取消</a>--%>
    <%--</div>--%>

    <div id="dlgAuditCancel" class="easyui-window" style=""
         closed="true" buttons="#dlg-buttons-audit">
        <form id="fmAuditCancel" method="post" class="popWin_wrap" novalidate>
            <table id="detailAudit" class="editTable" width="100%">
                <tr>
                    <td valign="top"><label for="txtAuditBatchId">工作流批号</label></td>
                    <td valign="top"><input id="txtAuditBatchId" class="inputStyle ml10 mb10"
                               readonly="readonly" disabled="disabled" name="txtAuditBatchId"></td>
                </tr>
                <tr>
                    <td valign="top"><label for="txtAuditRemark">取消原因</label></td>
                    <td><textarea id="txtAuditRemark" rows="3" style="width:200px" class="datagrid-editable-input ml10" name="txtAuditRemark"></textarea></td>
                </tr>
                <tr>
                    <td><input type="hidden" id="orderCancel_index_hidden"/></td>
                    <td><input type="hidden" id="orderCancel_contact_hidden"/></td>
                </tr>
            </table>
        </form>
    </div>
    <div id="op_list"  class="easyui-window" title="选择跟单人"   style="height:232px;"
         data-options="top:150,modal:true,shadow:false,draggable:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true">
        <form id="trackOrderFrom" class="tabs-form" action="" method="post">
	        <div class="form-tabs" >
	                <div class="clearfix ml10" id="v_selcontact">
	                    <table class="complain_table">
	                        <tr><td>座席中心</td><td><input id="d_dept"  class="easyui-combobox easyui-validatebox" required="true" name="d_dept" style="width:150px;height: 22px"  /></td></tr>
	                        <tr><td>座席组</td><td><input id="d_group"  class="easyui-combobox easyui-validatebox" required="true" name="d_group" style="width:150px;height: 22px"/></td> </tr>
	                        <tr><td>座席</td><td><input id="d_seat" class="easyui-combobox easyui-validatebox" validtype="validateAccount" required="true" name="d_seat" style="width:150px;height: 22px"/></td></tr>   </table>
	                </div>
	        </div>
	        <p class="winBtnsBar textC"><a class="window_btn mr10"  data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="followOrder()">确定</a><a class="window_btn"  data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="closeFollowWin();">取消</a> </p>
		</form>
    </div>


    <div id="dlgClaim" class="easyui-window" style="padding-top: 20px;"
         closed="true">
         <%--closed="true" buttons="#dlg-buttons-claim">--%>
        <form id="fmClaim" method="post" novalidate>
            <table id="detailClaim" class="editTable" style="margin:0 auto 20px">
                <tr>
                    <td><label class="ml10" for="txtClaimOrderId">在线索权订单号：</label></td>
                    <td><input id="txtClaimOrderId" class="inputStyle"
                               readonly="readonly" disabled="disabled" name="txtClaimOrderId"></td>
                </tr>
            </table>
            <p class="winBtnsBar textC"><a id="orderClaim_confirm" class="window_btn" href="javascript:void(0)" >确定</a><span ><a id="orderClaim_cancel" class="window_btn ml10" href="javascript:void(0)" >取消</a></span></p>
        </form>
    </div>

    <%--<div id="dlg-buttons-claim">--%>
        <%--<a href="javascript:void(0)" id="orderClaim_confirm"--%>
           <%--class="easyui-linkbutton" iconCls="icon-ok">确认</a> <a--%>
            <%--href="javascript:void(0)" id="orderClaim_cancel"--%>
            <%--class="easyui-linkbutton" iconCls="icon-cancel">取消</a>--%>
    <%--</div>--%>

	<div id="reminderReplay" class="easyui-window" data-options="closed:true" >
            <form class="form-tabs">
			<table  border="0" cellspacing="1" cellpadding="1" >
				<tr>
					<td style="padding:5px">提交座席</td>
					<td style="padding:5px"><input id="apppsn" class="" disabled="disabled" value="" style="width:100px;"/></td>
					<td style="padding:5px">催送货等级</td>
					<td style="padding:5px"><input id="class_" class="" disabled="disabled" value="" style="width:100px;"/></td>
				</tr>
				<tr>
					<td valign='top'style="padding:5px">提交原因</td>
					<td colspan="3"style="padding:5px">
						<textarea id="applicationreason" rows="3" cols="38" disabled="disabled"></textarea>
					</td>
				</tr>
				<tr>
					<td class="pl5">回复人</td>
					<td colspan="3" class="pl5"><input id="chkpsn" class="fl" disabled="disabled" value=""/>
					</td>
				</tr>
				<tr>
					<td valign='top' style="padding:5px">回复说明</td>
					<td colspan="3" style="padding:5px">
						<textarea id="chkreason" rows="3" cols="38" disabled="disabled"></textarea>
					</td>
				</tr>
			</table>
            </form>
	</div>
	
	<!--问题单对话框-->
	<div id="div_problematic_reply" style="display: none;">
			<div class="popWin_wrap"><table id="detail1" class="editTable">
				<tr>
					<td valign="top"><label class="ml10" for="txtOrderId">问题单号</label></td>
					<td>
                        <input id="input_problematic_reply_oid"  class="readonly ml10 mb10"  disabled="disabled" name="input_problematic_reply_oid">
                    </td>
				</tr>
				<tr>
					<td valign="top"><label class="ml10" for="txtRemark">回复内容</label></td>
					<td><textarea id="textarea_problematic_reply_reason" cols="50" rows="4" class='ml10 fr' name="textarea_problematic_reply_reason"></textarea></td>
				</tr>
			</table>
            </div>
	</div>
	
	<div id="div_open_audit" class="easyui-dialog" data-options="title:'订单审批中',top:180,width:400,height:100,modal:true,closed:true">
		<div class="c_content">
			<input id="id_input_bmp_type" type="hidden" value="" />
			<span style="margin-top: 10px; margin-left: 10px;">
				订单[<span id="id_audit_order"></span>]正在审批中 批次号： 
				<a class="link" onclick="openAudit()"><span id="id_audit_batch"></span></a>
			</span>
			<p class="mb10 mt10" align="center">
	        	<a class="window_btn" onclick="doModifyOrder()">确定</a>
	        </p>
        </div>
	</div>
	
		<div id="reminder" class="easyui-window"  data-options="closed:true,draggable:false">
			<form id="reminderApplyForm" class="form-tabs">
			<input type="hidden" id="sr_orderId" name="orderid" value=""/>
			<input type="hidden" id="frameId" name="frameId" value=""/>
			<input type="hidden" id="reminderSource" name="reminderSource" value="HEADER"/>

			<table  border="0" cellspacing="1" cellpadding="1">

				<tr>
					<td ><div style="width:65px">催送货等级</div></td>
					<td  >
						<select id="class_apply" name="class_">
							<option value="">--请选择--</option>
							<option value="1">普通</option>
							<option value="2">加急</option>
							<option value="3">紧急</option>
						</select>
                        <span id="message" class="fr" style="color: red;">&nbsp;</span>
					</td>
				</tr>
				<tr class="pt10">
					<td valign='top'class="pt10" >提交原因</td>
					<td class="pt10">
						<textarea id="applicationreason_apply" name="applicationreason" rows="5" cols="38" maxlength="200"></textarea>
					</td>
				</tr>
			</table>
			</form>
        <p class="winBtnsBar textC"><a class="window_btn mr10" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="subCallback($('#frameId').val(), 'submitReminder')">确定</a><a class="window_btn" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="closeWin('taskDialog')">取消</a> </p>

    </div>
	<script type="text/javascript">
		var startDate = '${startDate}';
	</script>
	<script type="text/javascript" src="${ctx}/static/js/json.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/myorder/reminder.js?${rnd}"></script>
	<script type="text/javascript" src="${ctx}/static/js/myorder/trackOrder.js?${rnd}"></script>

    <jsp:include page="orderProcess.jsp" ></jsp:include>
</body>

</html>