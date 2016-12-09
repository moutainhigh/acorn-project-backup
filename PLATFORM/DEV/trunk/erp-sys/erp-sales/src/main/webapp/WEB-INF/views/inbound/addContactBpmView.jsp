<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
<title>主管审查页面</title>
</head>
<body>
	<link href="/static/style/inboundCall.css" rel="stylesheet"
		type="text/css" />
	<script type="text/javascript" src="/static/js/addContactBpmView.js?${rnd}"></script>

	<div id="compare">
			<input id="input_busiType" type="hidden" value="${auditTaskType}"/> 
			<input id="input_id" type="hidden" value="${id}" />
			<input id="input_batchId" type="hidden" value="${batchId}" />
			<input id="input_crUser" type="hidden" value="${crUser}" /> 
			<input id="input_isManager" type="hidden" value="${isManager}" />
			<input id="input_isConfirmAudit" type="hidden" value="${isConfirmAudit }"/>
			<input id="input_source" type="hidden" value="${source }" />

		<div class="c_content">
			<h2 class="h2_tabtitle">
				新增申请编号：<span id="batchId">${batchId}</span>
			</h2>
            <div style="">
                <table class="changeAuTa" border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tbody><tr style="background: #e2f2ff;">
                        <th scope="col">&nbsp;</th>
                        <th scope="col" class="big">新增前</th>
                        <th scope="col" class="big red">新增后</th>
                    </tr>
                    <tr><td class="no_r no_b"></td><td class="no_r no_b"></td><td class="no_r no_b"></td></tr>
                    <tr>
                        <td valign="top" width="90px">
                            <div class="t_head int_info">常用基本信息</div>
                        </td>
                        <td valign="top" style="opacity: 0.5">
                            <table class="layout">
                                <tbody><tr>
                                    <td>客户姓名</td>
                                    <td><input  type="text" class="readonly" readonly=""></td>
                                </tr>
                                <tr>
                                    <td>客户性别</td>
                                    <td><label><input  type="radio" disabled="disabled">男</label>&nbsp;<label><input  type="radio" disabled="disabled">女</label></td>
                                </tr>
                                </tbody></table>
                        </td>
                        <td class="no_r" valign="top">
                            <table class="layout">
                                <tr>
                                    <td>客户姓名</td>
                                    <td><input id="oldName" type="text" class="readonly"
                                               readonly /></td>
                                </tr>
                                <tr>
                                    <td>客户性别</td>
                                    <td><label><input id="oldSexMen" type="radio"
                                                      disabled="disabled" />男</label>&nbsp;<label><input
                                            id="oldSexWomen" type="radio" disabled="disabled" />女</label></td>
                                </tr>
                            </table>
<!--                             <div id='div_contact_baseinfo_command' style="display: none;">
                            </div> -->
                        </td>
                    </tr>
                    <tr>
                        <td valign="top" width="90px" class="">
                            <div class="t_head addr_info">常用通话<br>信息</div>
                        </td>
                        <td valign="top" class="" style="opacity: 0.5">
                            <table class="easyui-datagrid" data-options="fitColumns:true,scrollbarSize:0,
                            onBeforeLoad:function(){ $(this).datagrid('resize',{width:($(window).width()-166)*0.5,})}
                            ">
                                   <thead>
                                   <tr>
                                   <th data-options="field:'tel',width:40">客户电话</th>
                                   <th data-options="field:'check',width:20,align:'center'">默认</th>
                                   <th data-options="field:'sel',width:20,align:'center'">备选</th>
                                   </thead>
                                </tr>
                                    <tbody>
                                       <tr>
                                           <td> </td>
                                           <td> </td>
                                           <td> </td>
                                       </tr>

                                    </tbody>
                            </table>
                        </td>
                        <td class="no_r  " valign="top">
                            <table id="oldPhoneTable"></table>
                        </td>
                    </tr>
                    <tr>
                        <td valign="top" width="90px" class="">
                            <div class="t_head deliver_info">常用收货<br>信息</div>
                        </td>
                        <td valign="top" class="" style="opacity: 0.5">
                            <table class="easyui-datagrid" data-options="fitColumns:true,scrollbarSize:0,
                            onBeforeLoad:function(){ $(this).datagrid('resize',{width:($(window).width()-166)*0.5,})}
                            ">
                                <thead>
                                <tr>
                                <th data-options="field:'tel',width:50">收货地址</th>
                                <th data-options="field:'check',width:40">详细地址</th>
                                <th data-options="field:'sel',width:30,align:'center'">邮编</th>
                                <th data-options="field:'sell',width:20,align:'center'">默认</th>
                                </thead>
                                </tr>
                                <tbody>
                                <tr>
                                    <td> </td>
                                    <td> </td>
                                    <td> </td>
                                </tr>

                                </tbody>
                            </table>
                        </td>
                        <td class="no_r" valign="top">
                            <table id="oldAddressTable"></table>
                            <div id='div_contact_new_address_tip' style="display: none;"></div>
                        </td>
                    </tr>
                    </tbody></table>
            </div>
			<%--<table class="c_table" width="100%" border="0" cellspacing="5"--%>
				<%--cellpadding="0">--%>
				<%--<tr name="tr_base_1">--%>
					<%--<td colspan="2" class="c_context"></td>--%>
				<%--</tr>--%>
				<%--<tr name="tr_base_2">--%>
					<%--<td class="c_title" colspan="2">客户基本信息</td>--%>
				<%--</tr>--%>
				<%--<tr name="tr_base_3">--%>
					<%--<td colspan="2" class="c_context"></td>--%>
				<%--</tr>--%>
				<%--<tr name="tr_base_4">--%>
					<%--<td class="c_context rBorder" width="50%" valign="top">--%>

					<%--</td>--%>
					<%--<td class="c_context" width="50%" valign="top" name="td_contact_base_info">--%>
						<%--&lt;%&ndash;<table class="layout">&ndash;%&gt;--%>
							<%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<td>客户姓名</td>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<td><input id="oldName" type="text" class="readonly"&ndash;%&gt;--%>
									<%--&lt;%&ndash;readonly /></td>&ndash;%&gt;--%>
							<%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<td>客户性别</td>&ndash;%&gt;--%>
								<%--&lt;%&ndash;<td><label><input id="oldSexMen" type="radio"&ndash;%&gt;--%>
										<%--&lt;%&ndash;disabled="disabled" />男</label>&nbsp;<label><input&ndash;%&gt;--%>
										<%--&lt;%&ndash;id="oldSexWomen" type="radio" disabled="disabled" />女</label></td>&ndash;%&gt;--%>
							<%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
						<%--&lt;%&ndash;</table>&ndash;%&gt;--%>
						<%--&lt;%&ndash;<div id='div_contact_baseinfo_command' style="display: none;">&ndash;%&gt;--%>
						<%--&lt;%&ndash;</div>&ndash;%&gt;--%>
					<%--</td>--%>
				<%--</tr>--%>
				<%--<tr name="tr_phone_1">--%>
					<%--<td colspan="2" class="c_context"></td>--%>
				<%--</tr>--%>
				<%--<tr name="tr_phone_2">--%>
					<%--<td class="c_title" colspan="2">常用通话信息</td>--%>
				<%--</tr>--%>
				<%--<tr name="tr_phone_3">--%>
					<%--<td colspan="2" class="c_context"></td>--%>
				<%--</tr>--%>
				<%--<tr name="tr_phone_4">--%>
					<%--<td class="c_context rBorder" width="50%" valign="top">--%>
					<%--</td>--%>
					<%--<td class="c_context" width="50%" valign="top" name="td_new_phone_info">--%>
						<%--&lt;%&ndash;<table id="oldPhoneTable"></table>&ndash;%&gt;--%>
					<%--</td>--%>
				<%--</tr>--%>
				<%--<tr name="tr_address_1">--%>
					<%--<td colspan="2" class="c_context"></td>--%>
				<%--</tr>--%>
				<%--<tr name="tr_address_2">--%>
					<%--<td class="c_title" colspan="2">常用收货信息</td>--%>
				<%--</tr>--%>
				<%--<tr name="tr_address_3">--%>
					<%--<td colspan="2" class="c_context"></td>--%>
				<%--</tr>--%>
				<%--<tr name="tr_address_4">--%>
					<%--<td class="c_context rBorder" width="50%" valign="top">--%>
						<%----%>
					<%--</td>--%>
					<%--<td class="c_context" width="50%" valign="top" name="td_new_address_info">--%>
						<%--&lt;%&ndash;<table id="oldAddressTable"></table>&ndash;%&gt;--%>
					<%--</td>--%>
				<%--</tr>--%>
				<%--<tr>--%>
					<%--<td colspan="2" class="c_context"></td>--%>
				<%--</tr>--%>
			<%--</table>--%>

			<p class="mb10 mt10" align="center">
				<a id="a_approveBatch" class="window_btn" href="javascript:void(0)" onclick="contactAuditApprove('1')">批准</a>
				<a id="a_rejectBatch" class="window_btn ml10" href="javascript:void(0)" onclick="contactAuditReject()">驳回</a>
<!-- 				<a id="a_submitInstance" class="window_btn" href="javascript:void(0)">确定</a>  -->
				<a id="a_cancelInstance" class="window_btn ml10" href="javascript:void(0)">关闭</a>
			</p>
		</div>

	</div>
	<!-- 驳回对话框 -->
	<div id="div_reject_contact_comment" style="display: none;">
			<div class="popWin_wrap"><table id="detail" class="editTable">
				<tr>
					<td valign="top"><label class="ml10">批次号</label></td>
					<td>
                        <input id="input_reject_contact_batchid"  class="readonly ml10 mb10"  disabled="disabled" name="input_reject_contact_batchid">
                    </td>
				</tr>
				<tr>
					<td valign="top"><label class="ml10">驳回原因</label></td>
					<td><textarea id="textarea_reject_contact_comment" cols="50" rows="4" class='ml10 fr' name="textarea_reject_contact_comment"></textarea></td>
				</tr>
			</table>
            </div>
	</div>
		<div id="loading" class="" style="display: none;">
	        <div style='position:absolute;width:100%;background: rgba(255,255,255,0.5); height:100%; text-align:center;top:0;font-size:12px; margin:0; padding:0;'>
	            <img alt='load' src='../static/images/loadingAnimation.gif' align='absMiddle'  bold='0'  />
	        </div>
	    </div>
</body>
</html>