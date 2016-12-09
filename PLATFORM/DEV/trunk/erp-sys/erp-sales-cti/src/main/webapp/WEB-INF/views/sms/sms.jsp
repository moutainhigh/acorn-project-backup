<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	
</head>
<body>
	<link href="${ctx}/static/style/easyui.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/static/style/themes/icon.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/style/myOrders.css" rel="stylesheet" type="text/css" />
	
			<form id="smsForm" class="tabs-form" action="" method="post">
				<input type="hidden" name="leadId" id="leadId" value="${leadId }">
				<div class="baseInfo" style="margin: 0">
					<table class="" border="0" cellspacing="0" cellpadding="0"
						width="100%">
						<tr>
							<td>客户编号</td>
							<td><input id="customerId" name="customerId" value="${contactId }" type="text" maxlength="20" class="easyui-validatebox" data-options="required:true"/><a href="javascript:void(0);" id="queryContact" class="customerQuery s_btn ml10"></a></td>
							<td>
								<%--<div class="submitBtns" style="text-align: left">--%>
									<%--<a >查询</a>--%>
								<%--</div>--%>
							</td>
						</tr>
						<tr>
							<td>发送手机</td>
							<td>
								<select id="mobile" name="mobile"  style="width:134px" class="easyui-combobox" data-options="required:true">
								</select>
							</td>
							<td>


							</td>
						</tr>
						<tr>
							<td>模板名称</td>
							<td>
								<input class="easyui-combogrid" style="width:330px;height:22px"
									id="smsName"
									name="smsName"
									<%--width="200"--%>
									data-options="
											required:true,
											url:'${ctx }/sms/queryTemplateList',
											idField:'id',
											textField:'name',
											panelWidth:400,
											panelHeight:320,
											pagination:true,
											<%--rownumbers:false,--%>
											required:true,
											editable:false,
											mode: 'remote', 
											toolbar:'#tt4',
											columns:[[
												{field:'id',title:'编号',width:100},
												{field:'name',title:'短信主题',width:200}
											]],
											onChange:function(_param){
												$.ajax({
													url : ctx+'/sms/querySmsContent?smsTemplateId='+_param,
													type : 'POST',
													success: function(_data){
														if(_data.success){
															$('#smsContent').val(_data.smsTemplate.content);
														}
													}
												});
											}">
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td style="width:100px" valign="top">短信内容</td>
							<td style="width:310px"  valign="top">
								<textarea id="smsContent" name="smsContent" rows="6" cols="40" readonly="readonly"  class="readonly" 
									class="easyui-validatebox" data-options="required:true" style="width:320px"></textarea>
							</td>
							<td  valign="top">
								<c:if test="${isManager }">
                                	<a href="javascript:void(0);" id="sendSms" class="sendSms">发送</a>
                                </c:if>
							</td>
						</tr>
					</table>
				</div>
			</form>
			
			<div class="receiptInfo tabs-form">
				<table id="smsSendHistory" cellspacing="0" cellpadding="0" data-options=""></table>
			</div>
			
	<script type="text/javascript">
		var _contactId = '${contactId}';
	</script>
	<script type="text/javascript" src="${ctx}/static/js/sms/sms.js?${rnd}"></script>			
</body>
</html>