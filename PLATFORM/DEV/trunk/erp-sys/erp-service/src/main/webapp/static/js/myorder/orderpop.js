var _callbackFrameId = "";
function popConfirm(iframeId, winId, options){
	_callbackFrameId = iframeId;
	$("#" + winId).remove();
	if($("#" + winId).length == 0) {
		var body = $("body");
		var contents = $("#" + iframeId, parent.document.body).contents();
		$("body").append($("#" + iframeId, parent.document.body).contents().find("#" + winId).clone(true));
	}
	$("#" + winId).window(options).window('refresh').window('open').window('move',{top:($(window).height() - $("#"+winId).window('panel').height()-10) * 0.5});;
	$("#" + winId).window('setTitle', options.title);
}

function popAlert(winTitle, winContent) {
	var _scrollHeight = $(document).scrollTop(); //获取当前窗口距离页面顶部高度 
	var _windowHeight = $(window).height(); //获取当前窗口高度 
	var _windowWidth = $(window).width(); //获取当前窗口宽度 
	var _posiTop = (_windowHeight - 90)/2 + _scrollHeight;
	var _posiLeft = (_windowWidth - 400)/2; 
	
    var options = {
		title : winTitle,
		top : _posiTop,
		left : _posiLeft
	};
    $("#alertWin").html(winContent);
    $("#alertWin").window(options).window('open').window('move',{top:($(window).height() - $("#alertWin").window('panel').height()+10) * 0.5});
}

function closeWin(winId){
	$("#" + winId).window('close').window('destroy');
}

function callRelatedConfirm(orderId) {
	subCallback(_callbackFrameId, 'relatedConfirm', orderId);
}

function relatedCallback(id, methodName) {
	subCallback(_callbackFrameId, methodName, id);
}

function openAudit(batchId, orderId, bmpType) {
	var url = 'task/processAuditTaskAjax?batchId=' + batchId + '&auditTaskType=' + bmpType + '&isConfirmAudit=0&id=' + orderId;
	window.parent.addTab('task_' + batchId, '我的订单', 'false', url);
	$("#alertWin").html("");
	$("#alertWin").window("close");
}

function openOrder(orderId, originalOrderId) {
    var url = 'myorder/orderDetails/get/' + orderId + '?activable=false';
    window.parent.addTab('myorder_' + orderId, '订单：' + orderId, 'false', url);
    closeWin("id_related_" + originalOrderId);
    closeWin("id_edit_comfirm_div_" + originalOrderId);
}