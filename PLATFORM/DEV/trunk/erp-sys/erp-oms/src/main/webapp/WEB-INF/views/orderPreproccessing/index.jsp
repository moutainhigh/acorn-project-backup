<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
<!--  
<script type="text/javascript"
	src="/static/scripts/ssxq.js"></script>
	<script type="text/javascript"
	src="/static/scripts/PCASClass.js"></script>
	-->
	<%
	//java.util.Random random = new java.util.Random();
	//int rnd = random.nextInt();
	%>
<script type="text/javascript"
	src="/static/scripts/pretread/pretread.js"></script>
<script type="text/javascript"
	src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
<script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.parser.js">
</script>
	 <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!--  
      <c:set var="from1" value="${from}" /> 
   	<c:if test="${from1 == 'TAOBAO'}">
      	<div class="hd">淘宝直营订单</div>
	</c:if>
	<c:if test="${from1 == 'JINGDONG'}">
      <div class="hd">京东SOP订单</div>
	</c:if>
    <c:if test="${from1 == 'JINGDONGFBP'}">
      <div class="hd">京东FBP订单</div>
	</c:if>
    <c:if test="${from1 == 'CSV'}">
      <div class="hd">CSV导入订单</div>
	</c:if>
    <c:if test="${from1 == 'AMAZON'}">
      <div class="hd">亚马逊订单</div>
	</c:if>
	 <c:if test="${from1 == 'CHAMA'}">
      <div class="hd">茶马订单</div>
	</c:if>
	-->
   <input type="hidden" name="from" id="from" value="<%= session.getAttribute("from") %>" />
	<div id="condition">
		<div>
			<label class="labelTitle ml10" for="tradeId">网上单号:</label> 
			<input id="tradeId"  class="inputStyle" type="text"  name="tradeId" size="8" /> 
			 <label class="labelTitle ml10" for="receiverMobile">收货人电话:</label>
		<input id="receiverMobile"  class="inputStyle" type="text"  name="receiverMobile" size="20" />
			<label  class="labelTitle ml10" for="payment">订单金额:&nbsp;&nbsp;&nbsp;从:</label><!--  <label for="min"></label> -->
			<span style="width:130px;display:inline-block">
		<input id="min" class="easyui-numberbox" type="text" size="5" /> <label for="max" style="line-height:22px;">到:</label>
		<input id="max" class="easyui-numberbox" type="text" size="5" /> 
		</span>
		<label  class="min ml10" for="outCrdt"  style="line-height:22px;">付款时间:</label>
			<input id="beginDate" class="easyui-datebox" style="width:100px"/> <label style="line-height:22px;" for="">至:</label>
			<input id="endDate" type="text" class="easyui-datebox"style="width:100px"  />
				</div>
		<div>
		<label  class="labelTitle ml10" for="shipmentId">橡果单号:</label> 
			<input id="shipmentId"  class="inputStyle" type="text"  name="shipmentId" size="8" />
		<label class="labelTitle ml10" for="state">订单状态:</label> 
			<select	name="state" id="state" class="inputStyle">
				<option value="1" style="color:#00CCFF" >待审核</option>
				<option value="2" style="color:#CC3300">审核失败</option>
				<option value="3" style="color:#33FF00">已审核待发货</option>
				<option value="4" style="color:#009966">标记完成</option>
				<option value="5" style="color:#FF9966">反馈失败</option>
				<option value="6" style="color:#666699">前置压单</option>
				<option value="0">全部订单</option>
			</select> 
			<label class="labelTitle ml10" for="treadType">订单类型:</label> 
			<select class="inputStyle"  id="treadType"  name="treadType">
                    <option value="">====全部====</option>
                    <c:forEach var="preTreadType" items="${preTreadType}">
                        <option value="${preTreadType.tradeType}">${preTreadType.remark}</option>
                    </c:forEach>
                </select>
                
		<c:if test="${from1 == 'TAOBAO' || from1 == 'TAOBAOC' || from1 == 'TAOBAOZ' }">
			<label class="labelTitle ml10" for="alipayTradeId">订购账号:</label> 
		<input class="floatStyle" name="alipayTradeId"  id="alipayTradeId" type="text" />
		</c:if>
	    <c:if test="${isJson == 2}">
	    <label  class="ml10" for="isJson"><input name="isJson" class="" id="isJson" disabled="true" type="checkbox" checked="true"/>json审核接口</label>
	    </c:if>
	    <c:if test="${isJson == 3}">
	    <label  class="ml10" for="isJson"><input name="isJson" class="" id="isJson" type="checkbox"/>json审核接口</label>
	    </c:if>
		</div>
		<div>   
		<label class="labelTitle ml10" for="refundStatus">退款状态:</label> 
			<select	name="refundStatus" id="refundStatus" class="inputStyle">
				<option value="0">====全部====</option>
				<option value="7" style="color:#68228B">有申请</option>
				<option value="8" style="color:#8B0000">无申请</option>
			</select>
		<label class="labelTitle ml10" for="refundStatusConfirm">退款处理:</label> 
			<select	name="refundStatusConfirm" id="refundStatusConfirm" class="inputStyle">
				<option value="0">====全部====</option>
				<option value="-1" style="color:#68228B">未操作</option>
				<option value="1" style="color:#68228B">允许发货</option>
				<option value="2" style="color:#8B0000">取消发货</option>
			</select>

			<label class=" ml10" for="sellerMessage"><input name="sellerMessage" class="" id="sellerMessage" type="checkbox" />有卖家备注</label> 
			
			<label class="ml10" for="buyerMessage"><input name="buyerMessage" class="" id="buyerMessage" type="checkbox" >有买家备注</label> 
			
			<label class="ml10" for="isCombine"><input name="isCombine" class="" id="isCombine" type="checkbox" >选择合单数据</label> 
			 
            <input type="button" name="queryData" class="ml10 Btn" value='查找' id="queryData" onclick="queryData()" />

		</div>		
	</div>
	<div id="toolbar">
		<input type="button" id="editRow" plain="true" value="编辑"></input>|
		<input type="button" id="batchApproval" plain="true" value="批审"></input>|
		<input type="button" id="singleApproval" plain="true" value="单审"></input>|
		<input type="button" id="combineOrders" plain="true" value="合单"></input>|
		<!--  
		<input type="button" id="deleteRow" plain="true" value="删除"></input>|
		-->
		<input type="button" id="detainOrder" plain="true" value="压单"></input>|
		<input type="button" id="feedback" plain="true" value="反馈"></input>|
		<input type="button" id="refund" plain="true" value="允许发货"></input>|
		<input type="button" id="consent" plain="true" value="取消发货"></input>

		
	</div>
	<div class="container" >
		<table id="dataTable" class="con" cellspacing="0" cellpadding="0" data-options=""   >
		</table>
	</div>

	<!--编辑窗体-->
	<div id="dlg" class="easyui-dialog"
		style="width: 750px; height:auto; padding: 10px 10px" closed="true"
		buttons="#dlg-line-form-buttons" modal="true">
		<form id="fm" method="post" novalidate>
		<input type="hidden" id="v_id" name="v_id">
			<table id="editTable" style="width:90%;border: 0px;" >
				<tr>
					<td><label for="v_tradeId">外部订单号:</label></td>
					<td><span id="v_tradeId" ></span></td>
					<td align="left"><label >订单金额:</label></td>
					<td align="left"><span id="v_payment"></span></td>
					<td  align="left"><label for="v_shippingFee">运费:</label></td>
					<td  align="left"><span id="v_shippingFee"></span></td>
				</tr>
				<tr>
					<td  align="left"><label>卖家备注:</label></td>
					<td colspan="5" align="left">
					<textarea id="v_remark1"  name="" cols ="50" rows = "2"  readonly></textarea>
					</td>
				</tr>
				<tr>
					<td  align="left">
					<label>买家备注:</label>
					</td>
					<td colspan="5" align="left">
					<textarea id="v_remark2"  name="" cols ="50" rows = "2"  readonly></textarea>
					</td>
				</tr>
				<tr>
					<td align="left"><label>姓名:</label></td>
					<td align="left">
							<input type="text" id="v_receiverName" name="v_receiverName" size="12">
					</td>
					<td align="left">
						
							<label>电话:</label>
							
						
					</td>	
					<td align="left">
							<input type="text" id="v_receiverMobile_mask" maxlength="20" name="v_receiverMobile_mask" size="12">
							<input type="hidden" id="v_receiverMobile" maxlength="20" name="v_receiverMobile" size="12">
					</td>					
					<td align="left">
							<label>备用电话:</label>
					</td>
					<td align="left">
							<input type="text" id="v_receiverPhone_mask" maxlength="20" name="v_receiverPhone_mask" size="28">
							<input type="hidden" id="v_receiverPhone" maxlength="20" name="v_receiverPhone" size="28">
					</td>
				</tr>
				<tr>
					<td align="left">
						
							<label>省份:</label>
							<!--  
							<select class="select_one" name="province3" id="province3"></select>
							-->							
						
					</td>
					<td align="left">
						
							<input type="text" name="s1" id="s1" class="easyui-validatebox" data-options="required:true" size="12" />
							<!--  
							<select class="select_one" name="province3" id="province3"></select>
							-->							
						
					</td>
					<td align="left">
						
							<label>地级市:</label>
							
							<!--  
							<select class="select_one" name="city3" id="city3"></select>
							-->
						
					</td>
					<td align="left">
						
							
							<input type="text" name="s2" id="s2" class="easyui-validatebox" data-options="required:true" size="12"/>
							<!--  
							<select class="select_one" name="city3" id="city3"></select>
							-->
						
					</td>
					<td align="left">
						
							<label>县/区:</label>
							
							<!--  
							<select class="select_one" name="area3" id="area3"></select>
							-->
						
					</td>
					<td align="left">
						
						
							<input type="text" name="s3" id="s3" class="easyui-validatebox" data-options="required:true" size="28"/>
							<!--  
							<select class="select_one" name="area3" id="area3"></select>
							-->
						
					</td>
					</tr>
					<tr>
					<td align="left">
						
							<label>送货公司:</label>
						
						
					</td>
					<td align="left">
						
							
							    <select id="v_shippingType" name="v_shippingType" class="easyui-combobox" style="width:100px;" />  
						
					</td>
				<td align="left">
						
						<label>发票需求:</label>
							
							
						
					</td>
			<td align="left">
						
						
							<select name="v_invoiceComment" id="v_invoiceComment" class="easyui-combobox" style="width:100px;" >
							   <option value="">未指定</option>   
							   <option value="0">不需要</option> 
							   <option value="1">需要</option>
							   <option value="2">特殊要求</option>
							</select> 
							
						
					</td>
			<td align="left">
						
							<label>发票抬头:</label>
						
					</td>
					<td align="left">
						
							<input id="v_invoiceTitle" type="text"
								name="v_invoiceTitle" class="easyui-validatebox" size="28">
						
					</td>
				</tr>
				<tr>
					
			<td align="left">
						
							<label>详细地址:</label> 
							
						
					</td>
					<td colspan="5" align="left">
						
							
							<input id="v_address" type="text" name="v_address" class="easyui-validatebox" data-options="required:true"  size="60" />
						
					</td>
				</tr>
				<tr>
				 <td colspan="6">
				 <div id="toolbar"></div>
				 <div class="panel-noscroll">
				        <table id="pretradeDetail" cellspacing="0" cellpadding="0">
						</table>
				 </div>
				 </td>
				</tr>
				<tr>
				<td colspan="6" align="center">
				<a href="#" id="updatePreTrade" class="easyui-linkbutton" iconCls="icon-save" plain="false" >保存</a>
			    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="d_close()">取消</a>
			    <a href="#" id="d_reset" class="easyui-linkbutton" iconCls="icon-undo" plain="false" >重置</a>
				</td>
				
				</tr>
			</table>
		</form>
	</div>
	  <div id="dd" title="批审结果" class="easyui-dialog" modal="true"  style="width:450px;height:500px;" closed="true">
	    
	   <div id="pppp" class="easyui-progressbar" style="width:450px;"></div>
	    
	   <ul style='float:center'>此次共审核数据<label name="rows_length" id="rows_length"></label>条</ul>
       <li style="width:200px; height:22px; font-family:'宋体'; font-size:12px; text-align:center; line-height:22px;display:inline-block;list-style-type:none"><label id="num_not_v" name="num_not_v"></label></li>
       <div id="app_not_v" name="app_not_v"></div>
       <li style="width:200px; height:22px; font-family:'宋体'; font-size:12px; text-align:center; line-height:22px;display:inline-block;list-style-type:none"><label id="num_suc_v" name="num_suc_v"></label></li>
       <div id="app_suc_v" name="app_suc_v"></div>
       <li style="width:200px; height:22px; font-family:'宋体'; font-size:12px; text-align:center; line-height:22px;display:inline-block;list-style-type:none"><label id="num_failed_v" name="num_failed_v"></label></li>
       <div id="app_failed_v" name="app_failed_v"></div>
       <li style="width:200px; height:22px; font-family:'宋体'; font-size:12px; text-align:center; line-height:22px;display:inline-block;list-style-type:none"><label id="num_error_v" name="num_error_v"></label></li>
       <div id="app_error_v" name="app_error_v"></div>
	  </div>  
	  
	  <div id="w" modal="true" class="easyui-dialog" title="系统提示" style="width:400px;height:150px;padding:10px"
			data-options="
				iconCls: 'icon-save',
				closable:false
			" closed="true">
		       <div id="w_content" name="w_content"> </div>
		       </br>
		       </br>
		       <a href="#" class="easyui-linkbutton" id="w_ok" name="w_ok" onclick="javascript:$('#w').dialog('close')" data-options="iconCls:'icon-ok'">Ok</a>
		       
	</div>
	  

</body>
</html>
