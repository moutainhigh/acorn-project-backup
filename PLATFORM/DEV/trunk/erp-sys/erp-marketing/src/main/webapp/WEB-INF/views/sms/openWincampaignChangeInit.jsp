<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body>
     <form id="templateForm" action="campaignChange" method="post">
		<input type="hidden" name="id" value="${campaignMonitor.id }" id="id">
		<input type="hidden" name="createUser" value="${campaignMonitor.createUser }"  id="createUser">				
		<input type="hidden" name="campaignId"	  value="${campaignMonitor.campaignId }" id="campaignId">
		<input type="hidden" name="status"	  value="${campaignMonitor.status }" id="status">
		<input type="hidden" name="smsCount"	  value="${campaignMonitor.smsCount }" id="smsCount">		
		<input type="hidden" name="smsContent"	  value="${campaignMonitor.smsContent }" id="smsContent">
		<input type="hidden" name="receiveCount"	  value="${campaignMonitor.receiveCount }" id="receiveCount">
		<input type="hidden" 	   id="mincount_bake">				
		<table width="100%" class="spacing_tb">
			<tr>
				<td align="left">营销计划id：</td>
				<td>
					<input type="text" class="easyui-validatebox name input-text" style="width:150px;" maxlength="100" value='${campaignMonitor.campaignId}'   id="campaignId"  disabled="disabled" data-options="required:true"  />
				</td>
				<td align="left" style="width:90px;">状态：</td>
				<td>
					<select name="type"  id="type" style="width:150px;" data-options="required:true" onchange="changeType()">
						<option value="1">更改速率</option>
						<option value="0">恢复初始速率</option>					
					</select>
				</td>				
			</tr>
			<tr>
			<td align="left">发送速率：</td>
				<td>
					<input type="text" class="input easyui-numberbox"  value='${campaignMonitor.minuCount }'style="width:150px;" maxlength="100" name="minuCount" id="minuCount" data-options="required:true"/>					
				</td>				
			</tr>			
			<tr>
				<td colspan="4" align="center">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveCampaignMonitor()">保存</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeSaveWin()">关闭</a>
				</td>
			</tr>
		</table>
	</form>
	<script >
	
	
	function  changeType () {
		var type = $("#type").val();
		var  minuCount = $("#minuCount").val();
		var mincount_bake = $("#mincount_bake").val();
		if (type=="0") {
			$("#minuCount").attr("value","");
			$("#minuCount").attr("disabled","disabled");
			$("#mincount_bake").attr("value",minuCount);
		}else {
			$("#minuCount").removeAttr("disabled");
			$("#minuCount").attr("value",mincount_bake);
		}
	}
	function saveCampaignMonitor () {

		var falg = "${flag}";
		var minuCount = $("#minuCount").val();
		var type = $("#type").val();
		if (type !="0") {			
			if (falg ==true) {
				if (minuCount>=1200) {
					alert ("该营销计划有动态模板,速率上限不能超过1200条/分钟");
				}
			}else {
				if (minuCount>=5000) {
					alert ("该营销计划速率上限不能超过50000条/分钟");
				}	
			}		
			var r = $('#templateForm').form('validate');
			if (!r) {
				return;
			}
		}	
		var _date = new Date($("#createTime").val());
		$("#createTime").attr("value", _date.format('yyyy-MM-dd hh:mm:ss'));
		var _data = $('#templateForm').serializeArray();	
		$.ajax({
			type:'POST',
			url: 'campaignChange',
			data:_data,
			cache:false,
			success:function(data){
					if(eval(data.result)){
						alerts('保存成功');
						$('#addEditWin').window('close');
						$('#dataList').datagrid('load');
					}else{
						alerts('保存失败');
						$('#addEditWin').window('close');
						$('#dataList').datagrid('load');
					}
			},
			error:function(msg){
				alerts(msg);
			}
		}); 	
	}
	</script>
</body>
</html>