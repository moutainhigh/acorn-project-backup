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
		<input type="hidden" name="leadId" id="leadId" value="${leadId}">
		<div class="baseInfo" style="margin: 0">
			<table class="" border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td>客户编号</td>
					<td>
						<input id="contactId" name="customerId" type="text" maxlength="20" value="${contactId}" class="easyui-validatebox" data-options="required:true" />
						<a href="javascript:void(0);" id="queryContact" class="customerQuery s_btn ml10"></a>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>发送手机</td>
					<td>
						<select id="mobile" name="mobile" style="width: 134px" class="easyui-combobox" data-options="required:true"></select>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>订单编号</td>
					<td>
						<select id="orderId" name="orderId" style="width: 134px" class="easyui-combobox"></select>
						<a href="javascript:void(0);" id="queryOrderContact" class="customerQuery s_btn ml10"></a>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>模板名称</td>
					<td>
						<input id="smsName" class="easyui-combogrid" style="width: 330px; height: 22px" name="smsName" />
					</td>
					<td><span id="emmaCheck" style="color: red; display: none;"></span></td>
				</tr>
				<tr>
					<td style="width: 100px" valign="top">短信内容</td>
					<td style="width: 310px" valign="top">
						<textarea id="smsContent" name="smsContent" rows="6" cols="40" readonly="readonly"
							class="easyui-validatebox" data-options="required:true" style="width: 320px">
						</textarea>
					</td>
					<td valign="top">
						<a href="javascript:void(0);" id="sendSms" class="sendSms mt10">发送</a> 
						<c:if test="${isManager}">
							<a href="javascript:void(0);" id="editTem" class="sendSms mt10">维护模板</a>
						</c:if>
					</td>
				</tr>
			</table>
		</div>
	</form>

	<div class="receiptInfo tabs-form">
		<table id="smsSendHistory" cellspacing="0" cellpadding="0" data-options=""></table>
	</div>
	
	<div id="editTemWin" class="easyui-window" style="width: 700px" title="短信模板维护" data-options="closed:true,modal:true,draggable:false,top:50">
		<div class="form-tabs">
			<div class="baseInfo" style="margin: 0">
				<h3 class="mb10">— 模板内容 —</h3>
				<div class="complaint ">
					<table class="" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>类型:</td>
							<td colspan="2">
								<input id="emma_type" type="text" size="40" class="easyui-validatebox" data-options="required:true" />
								<label><input id="emma_status" type="checkbox" checked="checked" />有效</label>
							</td>
						</tr>
						<tr>
							<td valign="top"></td>
							<td colspan="2">
								<table>
									<tr>
										<td>内容中可替换部分</td>
										<td class="replacePart">[订单号]&nbsp;[姓名]&nbsp;[电话]&nbsp;[地址]&nbsp;[产品]&nbsp;[订单金额]&nbsp;[包裹单]&nbsp;[配送地址]</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td valign="top">内容:</td>
							<td colspan="2">
								<textarea id="emma_desc" style="width: 550px; height: 50px" class="easyui-validatebox" data-options="required:true">尊敬的[姓名]</textarea>
							</td>
						</tr>

					</table>
					<table id="grid_sms"></table>
					<p class="textC mt10">
						<a id="" href="javascript:void(0);" onclick="cleanTemplate()" class="window_btn ml10">清空</a> 
						<a id="" href="javascript:void(0);" onclick="saveTempleate()" class="window_btn ml10">保存</a> 
						<a id="" href="javascript:void(0);" onclick="removeTemplate()" class="window_btn ml10">删除</a>
					</p>
					
				</div>
			</div>
		</div>
		
	</div>
	
	<div id="removeConfirm" class="easyui-dialog" style="width: 400px; height: 100px" title="删除确认" 
		data-options="closed:true,modal:true,draggable:false,top:250,buttons:'#bb'">
		是否删除主题为&nbsp;[&nbsp;<label id="removeConfirmHolder" style="font-weight:bold;"></label>&nbsp;]&nbsp;的短信模板?
	</div>
	<div id="bb">
		<a href="javascript:void(0);" onclick="doRemoveTemplate()" class="window_btn ml10">确定</a> 
		<a href="javascript:void(0);" onclick="$('#removeConfirm').dialog('close')" class="window_btn ml10">取消</a>
	</div>

	<script type="text/javascript"> 
		var _contactId = '${contactId}';
	</script>
	<script type="text/javascript" src="${ctx}/static/js/sms/sms.js?${rnd}"></script>
</body>
</html>