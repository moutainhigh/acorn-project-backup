<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
	.tb_padding td{
		padding:1px;
	}
	.td_padding2 td{
		padding:3px;
	}
	.main_tb td{
		border: 1px solid #00FF00;
	}
	</style>
	<script type="text/javascript" src="/static/scripts/json2.js"></script>
	<script type="text/javascript" src="/static/scripts/shipment/refundRegister.js?${rnd}"></script>
</head>

<body>
      
	<div id="condition">
		<table class="td_padding2" width="85%">
			<tr>
				<td><label>送货公司: </label></td>
				<td>
					<select id="entity" style="width: 150px">
						<option value="">------请选择------</option>
						<c:forEach items="${compnayList }" var="cm">
							<option value="${cm.companyid }">${cm.name }</option>
						</c:forEach>
					</select>
				</td>
				<td>邮件号:</td>
				<td><input name="mailId" id="mailId" /></td>
				<td>发运单号:</td>
				<td><input name="shipmentId" id="shipmentId" /></td>
				<td>
					<a href="#" id="queryBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save'" iconCls="icon-ok">查询</a>
					<a href="#" id="clearBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save'" iconCls="icon-ok">清空</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div style="height: 420px;">
		<input type="hidden" id="shipmentRefund" value=""/>
		<table class="td_padding2"  cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<td width="60%">
					<table>
						<tr>
							<td>
								发送单号：
							</td>
							<td id="rs_shipmentId" style="font-weight: 600;"></td>
						</tr>
						<tr>
							<td>拒收原因代码:</td>
							<td>
								<select id="rejectCode" name="rejectCode">
									<option value="">------请选择------</option>
									<c:forEach items="${rejectList }" var="n">
										<option value="${n.id.id }">${n.tdsc }</option>
									</c:forEach>
								</select>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="#" id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save'" iconCls="icon-ok">保存</a>
	    						<a href="#" id="deleteBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save'" iconCls="icon-ok">删除退包</a>
	    						<!-- <a href="#" id="closeBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save'" iconCls="icon-ok">关闭</a> -->
							</td>
						</tr>
					</table>
				</td>
				<td width="40%" valign="middle">
					<table class="td_padding2">
						<tr>
							<td>本人已校验包裹数：</td>
							<td style="font-weight: 600;" id="allCheckCount">0</td>
						</tr>
						<tr>
							<td>本次已校验包裹合计：</td>
							<td style="font-weight: 600;" id="selfCheckCount">0</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="height: 350px;">
					<table id="dataTable" style="height: 300px"></table>
				</td>
				<td>
					本人登记历史:<br/>
					<select name="" id="history" multiple="multiple" style="width:350px; height: 350px;">
					</select>
				</td>
			</tr>
		</table>
		
	</div>
    
      
</body>
</html>
