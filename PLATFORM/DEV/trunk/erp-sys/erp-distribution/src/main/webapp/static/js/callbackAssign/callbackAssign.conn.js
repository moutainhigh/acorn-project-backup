/**
 * 接通类型按比例分配到部门
 */
callback.assignToDept = function() {
	ivrType = $('#timeLength').combobox('getValue');
	_data = callback.prepareParam(3);
	_data += '&ivrType='+ivrType;
	
	if(null==ivrType || ''==ivrType) {
		return;
	}
	
	$.ajax({
        global: false,
		type: 'POST',
		data: _data,
		url: ctx + '/callbackAssign/assignToDept',
		beforeSend : showLoading,
		complete : hideLoading,
		success: function(rs) {
			if(eval(rs.success)) {
				$.messager.alert('提示', '按比例分配成功');
				callback.queryAvaliableQty();
			}else{
				$.messager.alert('提示',  rs.message);
				console.error('按比例分配失败：' + rs.message);
			}
			$('#assignToDept').hide();
		},
		error: function(error) {
			 $.messager.alert('错误', '按比例分配失败');
			 console.error('按比例分配失败：' + error);
		}
	});
};

/**
 * 接通类型按比例分配到组
 */
callback.assignToGroup = function() {
	$('#workgroupT').datagrid('endEdit', editIndex);
    rows = $('#workgroupT').datagrid('getChecked');
    
    if(rows.length==0){
        $.messager.alert('错误', '请选择要分配的坐席组！') ;
        return;
    }
    
    for(i=0; i<rows.length; i++){
        var _assignCount = 0;
        if(null != rows[i].allotNum && undefined != rows[i].allotNum && '' != rows[i].allotNum){
            _assignCount = Number(rows[i].allotNum);
        }
        if(0==_assignCount){
        	$.messager.alert('错误', '坐席组['+rows[i].grpid+']分配数量不能为0！') ;
        	return;
        }
    }
    
    showLoading();
    
    data = this.prepareParam(3);
    
    //错误结果数组
    errArray = new Array();
    for(i=0; i<rows.length; i++) {
    	_param = {};
    	_param['userGroup'] = rows[i].grpid;
    	_param['assignCount'] = rows[i].allotNum;
    	obj = {};
        obj['agentUsersStr'] = JSON.stringify(_param);
        ajaxData = data + '&'+ $.param(obj);
        
        $.ajaxQueue({
            global: false,
        	type: 'POST',
        	url: URL_GROUP_ASSIGN,
        	data: ajaxData,
        	success: function(rs) {
        		if(!eval(rs.success)) {
        			errArray.push(rs.message);
        			console.error('分配失败：' + rs.message);
        		}
        	},
        	error: function() {
        		hideLoading();
        		$.messager.alert('分配失败，网络错误');
    			return;
        	}
        });
    }
    
    $.ajaxQueue().done(function(_data) {
    	callback.assignReload();
    	
    	hideLoading();
		msg = '成功分配到[' + (rows.length - errArray.length) + ']个用户，失败[' + errArray.length + ']个用户<br>';
		for(i=0; i<errArray.length; i++) {
			msg += errArray[i] + '<br>';
		}
		$.messager.alert('提示', msg);
    });
};


/**
 * 接通(井星)类型分配
 * @deprecated
 */
callback.assignToAgentConnIvr = function(formData, url, assignArr) {
	
	showLoading();
	//错误请求数统计
	errArray = new Array();
	
	for(i=0; i<assignArr.length; i++) {
		obj = {};
		obj['agentUsersStr'] = JSON.stringify(assignArr[i]);
		ajaxData = formData + '&agentUsersStr' + $.param(obj);
		
		$.ajaxQueue({
            global: false,
			type: 'POST',
			url: url,
			data: ajaxData,
			success: function(rs) {
				if(!eval(rs.success)) {
        			errArray.push(rs.message);
        			console.error('分配失败：' + rs.message);
        		}
			},
			error: function(){
				hideLoading();
        		$.messager.alert('分配失败，网络错误');
    			return;
			}
		});
	}
	
	$.ajaxQueue().done(function() {
		
		callback.assignReload();
    	
    	hideLoading();
    	msg = '成功分配到[' + (rows.length - errArray.length) + ']个用户，失败[' + errArray.length + ']个用户<br>';
		for(i=0; i<errArray.length; i++) {
			msg += errArray[i] + '<br>';
		}
		$.messager.alert('提示', msg);
	});
	
};

callback.clearAgentList = function(group) {
	 rows = $('#agentTb').datagrid('getRows');
	 
	 checkedRows = $('#agentTb').datagrid('getChecked');
	 count = 0;
	 for(var i=0; i<checkedRows.length; i++) {
		 if(checkedRows[i].userWorkGrp == group) {
			 count ++;
		 }
	 }
	 
	 for(i = rows.length -1; i >= 0 ; i--){
		 if(undefined != rows[i].userWorkGrp  && rows[i].userWorkGrp == group) {
			 $('#agentTb').datagrid('deleteRow', i);
		 } 
	 }
	 if(omniNum > 0){
         omniNum = omniNum - count;
         $('#omniNum').text(omniNum);
     }
};

//@ sourceURL=callbackAssign.conn.js
