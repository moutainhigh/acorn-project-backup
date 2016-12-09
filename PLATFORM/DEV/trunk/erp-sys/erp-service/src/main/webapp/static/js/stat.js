$(document).ready(function(){
	init();
});

function init() {
	$("#agentTable").datagrid({
		singleSelect: true,
		height: window.innerHeight - 50,
		scrollbarSize: -1,
		fitColumns: true,
		collapsible: true,
		url: $_ctx + '/stat/ajax/agents',
		method: 'get',
//		pagination: true,
//		pageSize: 10,
		columns: [[
			{field: 'uid', title: '坐席编号', width: 80},
			{field: 'orderCount', title: '成单数量',align:'center', width: 100},
			{field: 'callCount', title: '通话数量',align:'center', width: 100},
			{field: 'lastTime', title: '最后登录时间', width: 180},
			{field: 'ip', title: '最后登录IP',align:'center', width: 150},
			{field: 'serverIp', title: '会话所属IP',align:'center', width: 150},
			{field: 'oper', title: '操作', width:80,align:'center',
				formatter: function(value, row, index){
            		return '<a class="stop" title="下线" href="javascript:void(0);" onclick="cull(\'' + row.uid + '\')"></a>';
            	}
            }
		]]
	});
}

function cull(uid) {
	$.ajax({
		type: 'GET',
		url: $_ctx + '/stat/ajax/cull/' + uid,
		dataType: 'text',
		success: function(data) {
			if (data == "true") {
				$('#agentTable').datagrid('reload');
			}
		}
	});
}