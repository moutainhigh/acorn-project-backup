<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body>
	<div class="easyui-layout baseLayout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<form action="" name="myform" method="post" id="myform">
				
				<div class="clearfix">
				<div class="fl_w50">
					<label class="w65" >批次id</label>
					 <span class="inputSpan">
					<input class="easyui-validatebox input-text w190" type="text" disabled="disabled" name="batchid" id="batchIds"  value="${batchid}" data-options="required:true,validType:'length[1,50]'">
					</span>
					
				</div>
				<div class="fl_w50">
					<label class="w65">总量</label>
					<span class="inputSpan">
					<input class="easyui-validatebox  input-text w190"  type="text" disabled="disabled" name="smsCount" id="smsCount"  value="${count}" data-options="">
					</span>
					</div>
					</div>
					<div class="clearfix">
				<div class="fl_w50">
					 <label class="w65">发送量</label>
					 <span class="inputSpan">
					<input class="easyui-validatebox input-text w190"  type="text" disabled="disabled" name="receiveCount" id="receiveCount" value="${receiveCount}"  data-options="required:true,validType:'length[1,50]'">
					</span>
					
				</div>
				<div class="fl_w50">
					<label class="w65">到达量</label>
					<span class="inputSpan">
					<input class="easyui-validatebox  input-text w190"  type="text" disabled="disabled" name="feedbackCount" id="feedbackCount"  value="${feedbackCount}" data-options="">
					</span>
					</div>
					</div>
				<input type="hidden" disabled="disabled" name="smsstopStatus" id="smsstopStatus" > 
	<c:if test="${fileName==''}">
		<p id='p1'>暂无数据！请稍后再查看 </p>
		<script type="text/javascript">
			$(function(){$('#smsSla').window("resize",{height:200}); });
		</script>
	</c:if>
	<c:if test="${fileName!=''}">
		<img id='img1' width="100%" src="${fileName}"></img>
	</c:if>
</form>
</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:smsSla('${batchid}')" >查看发送信息</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"  id ="smsStop" href="javascript:smsStops()" >停止发送</a>
			</div>
		</div>
<script>
var stopFlag = "${stopFlag}";
if (stopFlag=="1") {
	$('#smsStop').attr('disabled', 'disabled');
}

if ('${smssend.smsstopStatus}'=='1') {
	$('#smsStop').attr('disabled', 'disabled');
}
if ('${smssend.sendStatus}'=='3') {
	$('#smsStop').attr('disabled', 'disabled');
}

function smsSla(id){
	if(!id){
		id = "";
	}
	 showWindow2({  
	        title:'发送信息',  
	        href:'${ctx}/sms/openWinSmsSla?batchid='+id,  
	        width:680,  
	        height:400,
	        maximizable:false,
	        minimizable:false,
	        zIndex:-20
	    },"smsDetail"); 
	 $("#smsDetail").parent(".window").addClass("important");
}
</script>
</body>
</html>