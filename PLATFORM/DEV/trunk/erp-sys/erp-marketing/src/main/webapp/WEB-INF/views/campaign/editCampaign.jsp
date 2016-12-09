<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>

</head>
<body>

	<!-- 数据分配 start -->

	<div class="easyui-layout baseLayout" data-options="fit:true">
		<div data-options="region:'center',border:false"
			style="padding: 10px; background: #fff; border: 1px solid #ccc;">
			<form action="" id="campaignForm" method="post">
				<!-- window content -->
				<input type="hidden" name="returnUrl" value="${returnUrl }">
				<c:if test="${campaign.id!=null }">
					<input type="hidden" name="id" value="${campaign.id}">
				</c:if>
				<div class="clearfix">
					<div class="fl_w50">
						<label>计划名称:</label> <span class="inputSpan"> <input
							class="easyui-validatebox input-text w150" name="name"
							value="${campaign.name }"
							data-options="required:true,validType:'length[1,50]'">
						</span>

					</div>
					<div class="fl_w50">
					
						<label>客户群:</label> <span class="inputSpan"> <input
							class="easyui-combogrid" style="width: 150px;" id="audience"
							name="audience" width="200" value="${campaign.audience}"
							data-options="
									url:'${ctx }/customer/list?status=Y',
									idField:'groupCode',
									textField:'groupName',
									panelWidth:450,
									panelHeight:320,
									pagination:true,
									queryParams: {
										groupCode: '${campaign.audience}'
									},
									rownumbers:true,
									required:true,
									editable:false,
									 mode: 'remote', 
									 toolbar:'#tt4',
									columns:[[
										{field:'groupCode',title:'客户群编号',width:220},
										{field:'groupName',title:'客户群名称',width:200}
									]],
									onLoadSuccess:function(data){
										$('#searchText4').val('${campaign.audience}')
									}
							">
						</span>						
					</div>
				</div>				
				<div class="clearfix">
				<div class="fl_w50">
				<input type="hidden" id="campaignTypeIds"/>
					<label>营销方式:</label> <span class="inputSpan">
						<input class="easyui-combobox" id="campaignTypeId" style="width:150px;" value="${campaign.campaignTypeId }"
						name="campaignTypeId"  data-options="
								url:'${ctx }/campaignType/getCampaignTypes',
								valueField:'id',
								textField:'name',
								multiple:false,
								editable:false,
								panelHeight:'auto',
								required:true,																													
								onChange:function(newValue,oldValue){
									if(newValue!=''){
										if (newValue!='5'&& newValue!='1') {
									        $('#setItem').hide();
									    }else  if (newValue=='5'||newValue=='1'){
									    	$('#setItem').show();
									    }
									    if (newValue=='5') {
									    	$('#campaignTypeIds').attr('value','5')									    
									    	$('#couponConfig').show();
									    }else {
									   	 	$('#couponConfig').hide();
									    }									  
									}
								}
						">
						</span>
						</div>
					
					<div class="fl_w50" style="display:none">
						<label>营销400号码:</label> <span class="inputSpan"> <input
							class="easyui-validatebox input-text w150" name="tollFreeNum"
							value="${campaign.tollFreeNum }" data-options="validType:'length[1,200]'">
						</span>
					</div>
					<div class="fl_w50" id="setItem">
						<label>营销400落地号:</label> <span class="inputSpan"> <input
							class="easyui-validatebox input-text w150" name="dnis"
							value="${campaign.dnis }"
							data-options="validType:'length[1,200]'">
						</span>
					</div>						
				</div>
				<div class="clearfix" style="display:none">
					<div class="fl_w50">
						<label>线索类型:</label> <span class="inputSpan">
						<input class="easyui-combobox" style="width:150px;" value="<c:if test="${campaign.leadTypeId==null }">2</c:if><c:if test="${campaign.leadTypeId!=null }">${campaign.leadTypeId }</c:if>"
						name="leadTypeId"  data-options="
								url:'${ctx }/leadType/getLeadTypes',
								valueField:'id',
								textField:'name',
								multiple:false,
								editable:false,
								panelHeight:'auto'
						">
						</span>					
				</div>			
				</div>				
					
				<div class="clearfix">				
					<label>负责人:</label> <span class="inputSpan"> <input
							class="easyui-validatebox input-text w150" name="owner"
							value="<c:if test="${campaign.owner==null }"><%=com.chinadrtv.erp.user.util.SecurityHelper.getLoginUser().getUserId() %></c:if><c:if test="${campaign.owner!=null }">${campaign.owner }</c:if>"
							data-options="required:true,validType:'length[1,50]'">
						</span>
					<div class="fl_w50" style="display:none">
						<label>预算客户数量:</label> <span class="inputSpan"> <input
							class="easyui-numberspinner  w150" name="leadTargeted"
							value="${campaign.leadTargeted }" data-options="">
						</span>
					</div>
					<div class="fl_w50">
						<label>预算成本:</label> <span class="inputSpan"> <input
							class="easyui-numberspinner w150" name="budgetedCost"
							value="${campaign.budgetedCost }"
							data-options="validType:'length[1,20]'">
						</span>
					</div>
				</div>
				<div class="clearfix">
				<div class="fl_w50">
					<label>营销部门:</label> <span class="dateSpan"> 
						<input class="easyui-combobox" style="width:150px;"
						name="department" 
						<c:choose><c:when test="${campaign.department!=null }">value="${campaign.department}"</c:when></c:choose>
						data-options="
								url:'${ctx }/dict/getBaseConfig?code=DEPARTMENT',
								valueField:'paraValue',
								textField:'paraName',
								multiple:false,
								editable:false,
								required:true,
								panelHeight:'auto'
						">
						</span>
					</div>
					<div id="couponConfig"  style="display: none" > 
						<label>优惠券配置:</label>
						<span class="inputSpan">
							<input   name="couponConfigId" class="easyui-combogrid" style="width:150px;"  value="${campaign.couponConfigId}"
								data-options="
									panelWidth:200,
									idField:'id',
									textField:'couponConfigName',
									valueField:'id',
									url:'${ctx}/coupon/list?smsSendType=2',
									panelWidth:450,
									panelHeight:320,
									pagination:true,
									rownumbers:true,
									editable:false,
									 mode: 'remote', 
									columns:[[
										{field:'id',title:'id',width:220},
										{field:'couponConfigName',title:'coupon配置名称',width:200}
									]]"
								/>
							</span>
					</div>				
				</div>	
				<div class="clearfix">
					<label>有效时间:</label> <span class="dateSpan"> <input
						class="easyui-datetimebox" readonly="readonly" type="text"
						style="width: 150px; margin-left: 5px" name="startDate" id="startDate"
						value="${campaign.startDate }" data-options="required:true">
						至<input class="easyui-datetimebox" readonly="readonly" type="text"
						style="width: 150px" name="endDate" id="endDate" value="${campaign.endDate }"
						data-options="required:true">
					</span>
				</div>
				<div>
					<label for="lastMdyTimeView" style="vertical-align: top;">描述：</label>
					<textarea rows="2" cols="40" class="area-text" style="width: 452px"
						name="description" id="description">${campaign.description}</textarea>
				</div>			
			</form>
		</div>
		<div data-options="region:'south',border:false"
			style="text-align: right; padding: 5px 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-next'"
				href="javascript:saveCampaign()">确定,下一步</a> <a class="easyui-linkbutton"
				data-options="iconCls:'icon-cancel'"
				href="javascript:closeWindow('commonMyPopWindow')">取消</a>
		</div>
	</div>
	<!-- 数据分配 end -->
	</form>

	<div id="tt4">
		<input type="text" id="searchText4" /><a href="#"
			id="queryProductBtn4" class="easyui-linkbutton"
			data-options="plain:true,iconCls:'icon-search'"></a>
	</div>

	<script type="text/javascript">
		var newValue= "${campaign.campaignTypeId }";	
	    if (newValue=='5') {
	    	$('#campaignTypeIds').show();
	    }else {
	   	 	$('#campaignTypeIds').hide();
	    }
		function saveCampaign() {
			var r = $('#campaignForm').form('validate');
			if (!r) {
				return;
			}
			var stime = $("#startDate").datebox("getValue"); 
			var etime = $("#endDate").datebox("getValue"); 
			if (stime>etime) {
				alerts('起始时间不能晚于结束时间');
				return ;
			}
		
			
			$.post("${ctx}/campaign/saveCampaign", $("#campaignForm")
					.serializeArray(), function(data) {
				//closeWindow('commonMyPopWindow');
				//alerts(data.next);
				editNextPage(data.campaignId,data.next,data.returnUrl);
				//$('#customerTable').datagrid("reload");
			}, 'json');
		}
		//查询按钮
		$("#queryProductBtn4").click(function() {
			var g = $('#audience').combogrid('grid'); // get datagrid object
			var r = g.datagrid('reload', {
				groupCode : $('#searchText4').val()
			});
		});
	</script>
</body>
</html>
