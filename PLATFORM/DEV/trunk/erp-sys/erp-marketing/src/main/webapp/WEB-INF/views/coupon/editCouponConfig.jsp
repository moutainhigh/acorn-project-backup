<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
</head>
<body>
	<div class="easyui-layout baseLayout" data-options="fit:true">
		<div data-options="region:'center',border:false"
			style="padding: 10px; background: #fff; border: 1px solid #ccc;">
			<form action="" id="couponForm" method="post">
				<!-- window content -->
				<c:if test="${couponConfig.id!=null }">
					<input type="hidden" name="id" value="${couponConfig.id}">
				</c:if>
				<div class="clearfix">
					<div class="fl_w50">
						<label>计划名称:</label> <span class="inputSpan"> <input
							class="easyui-validatebox input-text w150" name="couponConfigName"
							value="${couponConfig.couponConfigName }"
							data-options="required:true,validType:'length[1,50]'">
						</span>
					</div>
					<label>优惠券使用部门:</label>
						<span class="inputSpan"> 			
							<input class="easyui-combobox"  style="width:150px;"
						id = "couponTypes" name="useDeparment"
						data-options="
								url:'${ctx }/dict/getDepartment',
								idField:'id',
								textField:'dsc',
								multiple:true,
								valueField:'tmpId',
								onLoadSuccess:function(){
									var values = '${couponConfig.useDeparment}'
									if (values!='') {
										$('#couponTypes').combobox('setValues',values.split(','))									
									}
								}
						">
						</span>
				</div>	
				<input type="hidden" name="crdt" id ="crdt" value="${couponConfig.crdt}"/>
				<input type="hidden" name="crusr" id ="crusr" value="${couponConfig.crusr}"/>			
				<div class="clearfix">		
					<div class="fl_w50">			
						<label for="couponValue" style="vertical-align: top;">优惠券金额：</label>					
					 	<span class="inputSpan"> <input
							class="easyui-numberspinner  w150" name="couponValue" id="couponValue"
							value="${couponConfig.couponValue}" >
						</span>	
					</div>					
							<label for="useMoney" style="vertical-align: top;">优惠券使用限制：</label>					
					 	<span class="inputSpan"> <input
							class="easyui-numberspinner  w150" name="useMoney" id="useMoney"
							value="${couponConfig.useMoney}" >
						</span>									
				</div>
				<div class="clearfix" id="couponTime" >
					<label>优惠券有效时间:</label> <span class="dateSpan"> <input
						class="easyui-datebox" readonly="readonly" type="text"
						style="width: 150px; margin-left: 5px" name="stardt"
						value="${couponConfig.stardt }"  id="couponStartTimes" >
						至<input class="easyui-datebox" readonly="readonly" type="text"
						style="width: 150px" name="enddt"    id="enddt" value="${couponConfig.enddt }"
						>
					</span>
				</div>
				<div class="clearfix" >
					<div class="fl_w50">
						<label>优惠券类型:</label> <span class="dateSpan"> 
							<input class="easyui-combobox" style="width:150px;"
							name="couponType" 
							<c:choose><c:when test="${couponConfig.couponType!=null }">value="${couponConfig.couponType}"</c:when></c:choose>
							data-options="
									url:'${ctx }/dict/getDict?dictName=COUPONTYPE',
									valueField:'tmpId',
									textField:'dsc',
									multiple:false,
									editable:false,
									required:true,
									panelHeight:'auto'
							">
							</span>
					</div>
					<label>优惠券短信发送类型:</label> <span class="dateSpan"> 
							<input class="easyui-combobox" style="width:150px;"
							name="smsSendType" 
							<c:choose><c:when test="${couponConfig.smsSendType!=null }">value="${couponConfig.smsSendType}"</c:when></c:choose>
							data-options="
									url:'${ctx }/dict/getDict?dictName=COUPONSMSTYPE',
									valueField:'tmpId',
									textField:'dsc',
									multiple:false,
									editable:false,
									required:true,
									panelHeight:'auto',
									onChange:function(newValue,oldValue){
									if(newValue!=''){
										if (newValue!='1') {
									        $('#smsType').hide();
									    }else {
									    	$('#smsType').show();
									    }
									    if (newValue=='1') {									
	    									$('#smsType').attr('data-options','required:true');										    										    	
									    }else {									   	 
									    	$('#smsType').removeAttr('data-options');									   	 	
									    }									  
									}
								}
							">
							</span>
				</div>		
				<div class="clearfix" id="smsType"  style="display: none">
					<div class="fl_w50">
						<label>短信模板:</label>
						<span class="inputSpan">
							<input   id="templateName" name="templateName" class="easyui-combogrid" style="width:250px;"  value="${couponConfig.templateName}"
								data-options="
									panelWidth:200,
									idField:'id',
									textField:'name',
									url:'${ctx}/sms/smslist',
									panelWidth:450,
									panelHeight:320,
									pagination:true,
									rownumbers:true,
									
									editable:false,
									 mode: 'remote', 
									 queryParams :{
									 				themeTemplate:123
									},
									columns:[[
										{field:'id',title:'短信编号',width:220},
										{field:'name',title:'短信内容',width:200}
									]],
									 onSelect:function(index,row){
										 $('#templateContent').attr('value',row.content);
										 $('#templateContents').attr('value',row.content);
										 $('#templateSize')[0].innerHTML='短信字数'+row.content.length;
										 $('#varGrid').datagrid({
											 url:'${ctx}/sms/getStaticVar',
												queryParams:{
													content : row.content
												},
												onClickRow:function(rowIndex){
														$('#varGrid').datagrid('beginEdit', rowIndex);
												},onLoadSuccess:function(data){
													for(var i=0;i<data.rows.length;i++){
														$('#varGrid').datagrid('beginEdit', i);														
													}
												}
										 });
									}"
							/>
							<br>
							<textarea rows="2" cols="60" class="sm-tem" disabled="disabled" id="templateContent" ></textarea>
							<input id="templateContents" name="templateContent" type="hidden"/>
							<div id="templateSize" ></div>
						</span>	
					</div>				
				</div>										
			</form>
		</div>
		<div data-options="region:'south',border:false"
			style="text-align: right; padding: 5px 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-next'"
				href="javascript:saveCouponConfig()">保存</a>
		</div>
	</div>
	</form>
	<script type="text/javascript">	
	var smsSendType = '${couponConfig.smsSendType }';
	if (smsSendType == '1') {
		$('#smsType').show();
	}
		function saveCouponConfig() {				
			var stime = $("#couponStartTimes").datebox("getValue"); 
			var etime = $("#enddt").datebox("getValue"); 
			var couponValue = $("#couponValue").val();
			var useMoney = $("#useMoney").val();
			if (couponValue*1>useMoney*1) {
				alerts("优惠券金额必须少于使用金额");
				return;
			}
			if (etime < stime ) {
				alerts("结束时间小于起始时间");
				return;
			}			
			var r = $('#couponForm').form('validate');
			if (!r) {
				return;
			}
			$.post("${ctx}/coupon/saveCouponConfig", $("#couponForm")
					.serializeArray(), function(data) {
				if (data.result==true) {
					alerts("保存成功");
				}else {
					alerts("保存失败");
				}
				$('#commonMyPopWindow').window('close');
				$('#couponConfigTable').datagrid("reload");
			}, 'json');
		}
	</script>
</body>
</html>
