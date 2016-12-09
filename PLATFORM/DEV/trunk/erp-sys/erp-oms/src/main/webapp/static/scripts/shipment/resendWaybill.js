$(function(){
	
	$('.easyui-validatebox').validatebox();
	
	$('#query').click(function(){
		var orderid = $('#orderid').val();
		if(''==$.trim(orderid) || orderid.length==0){
			$('#message').html('请输入订单号');
			return false;
		}
		
		document.forms['waybillForm'].action='index';
		document.forms['waybillForm'].submit();
	});

	$('#resender').click(function(){
		var validate = $('#waybillForm').form('validate');
		if(!validate){
			return false;
		}
		var formData = $('#waybillForm').serialize();
		var orderId = $('#orderId').val();
		
		//$('#loading').show();
		
		$.ajax({
			url: 'resendWaybill',
			type: 'POST',
			data: formData,
			success:function(data){
				//$('#loading').hide();
				
				if(eval(data.success)){
					 
					$('#message').html('订单['+orderId+']重发成功');
					//清空表单
					$(':input','#waybillForm')
						 .not(':button, :submit, :reset, :hidden')
						 .val('')
						 .removeAttr('checked')
						 .removeAttr('selected');
					//隐藏重发按钮
					$('#resender').hide();
				}else{
					$('#message').html('订单['+orderId+']重发失败: ' + data.message);
				}
			},
			error:function(msg){
				//$('#loading').hide();
				alert('会话超时或网络错误');	
			}
		});
	});
	
	
});


function showProcess(isShow, title, msg) {
	if (!isShow) {
		$.messager.progress('close');
		return;
	}
	var win = $.messager.progress({
		title : title,
		msg : msg
	});
}