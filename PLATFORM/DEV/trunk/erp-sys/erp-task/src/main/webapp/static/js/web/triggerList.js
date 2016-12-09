
$(function(){
	
    $('#triggerGrid').datagrid({  
        title:'正在运行的触发器列表',  
        iconCls:'icon-save',  
		singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: true,
        fit:true,
        height:550,  
        nowrap: false,  
        autoRowHeight: false,  
        striped: true, 
        collapsible:false,
        collapsed:false,
        url:'/task/listTrigger',   
        sortOrder: 'desc',  
        remoteSort: false,  
        idField:'triggerName',  
        frozenColumns:[[
            {field:'ck',checkbox:true},  
        ]],  
		columns:[[
			{field:'triggerGroup',title:'分组',width:80,sortable:true,hidden:true},
			{field:'triggerName',title:'触发器名',width:150},
			{field:'jobGroup',title:'分组',width:80,hidden:true},
			{field:'jobName',title:'任务名',width:150,hidden:true},
			{field:'triggerType',title:'类型',width:50},
			{field:'startTime',title:'开始时间',width:80},
			{field:'endTime',title:'结束时间',width:80},
			{field:'prevFireTime',title:'上次时间',width:80},
			{field:'nextFireTime',title:'下次时间',width:120},
			{field:'triggerState',title:'状态',width:50},
			{field:'successCount',title:'成功数',width:50},
			{field:'failCount',title:'失败数',width:50},
			{field:'jobClass',title:'任务类',width:100},
			{field:'description',title:'描述',width:300}
		]],  
        pagination:true,  
        rownumbers:true,  
        toolbar:[{  
            id:'btnExe',  
            text:'运行一次',  
            iconCls:'icon-reload',  
            handler:function(){
            	exe();
            }  
        },{  
            id:'btnPause',  
            text:'暂停',  
            iconCls:'icon-remove',  
            handler:function(){
            	pause();
            }  
        },{  
            id:'btnResume',  
            text:'恢复',  
            iconCls:'icon-add',  
            handler:function(){
            	resume();
            }  
        },{  
            id:'btnEdit',  
            text:'编辑',  
            iconCls:'icon-edit',  
            handler:function(){
            	edit();
            }  
        },'-',{  
            id:'btnView',  
            text:'详细',  
            iconCls:'icon-save',  
            handler:function(){  
            	view();
            }  
        },'-',{  
            id:'btnConfig',  
            text:'配置',  
            iconCls:'icon-back',  
            handler:function(){  
            	window.location.href = '/jobClassList';
            }  
        }],
        onLoadSuccess: function(data){

    	},
    	onLoadError :function(result){
//    		alert("出错");
    	},
    });
    
    function getTriggerGridSelected(){  
        var selected = $('#triggerGrid').datagrid('getSelected');   
        return selected;
    }
    
    function exe(){  
    	var selected = getTriggerGridSelected();
    	if(selected){
        	var url = "/task/execute";
        	var param = { jobGroup:selected.jobGroup, jobName:selected.jobName }
        	$.post(url, param, function(data){
        		$.messager.show({
                    title: '提示 ',
                    msg: '运行了一次！运行需要时间，如果页面状态不正确，请稍等片刻后刷新！'
                });
        		$('#triggerGrid').datagrid('reload');
        	});
    	}else{
    		$.messager.alert('错误','请先选择一条数据！','error'); 
    	}
    }
    
    function pause(){
    	var selected = getTriggerGridSelected();
    	if(selected){
        	var url = "/task/pauseTrigger";
        	var param = { group:selected.triggerGroup, name:selected.triggerName }
        	$.post(url, param, function(data){
        		$.messager.show({
                    title: '提示 ',
                    msg: '已暂停！'
                });
        		$('#triggerGrid').datagrid('reload');
        	});
    	}else{
    		$.messager.alert('错误','请先选择一条数据！','error'); 
    	}
    }
    
    function resume(){  
    	var selected = getTriggerGridSelected();
    	if(selected){
        	var url = "/task/resumeTrigger";
        	var param = { group:selected.triggerGroup, name:selected.triggerName }
        	$.post(url, param, function(data){
        		$.messager.show({
                    title: '提示 ',
                    msg: '已恢复！恢复需要时间，如果页面状态不正确，请稍等片刻后刷新！'
                });
        		$('#triggerGrid').datagrid('reload');
        	});
    	}else{
    		$.messager.alert('错误','请先选择一条数据！','error'); 
    	}
    }
    
    function view(){  
    	var selected = getTriggerGridSelected();
    	$("#group").val(selected.triggerGroup);
    	$("#name").val(selected.triggerName);
    	
        var action = "/task/viewTrigger"
        var form = $("#opForm")
        form.attr('action',action)
        form.attr('method','post')
        form.submit()
        
    }
    
    function edit(){  
    	var selected = getTriggerGridSelected();
    	var triggerGroup = selected.triggerGroup;
    	var triggerName = selected.triggerName;
    	var type = selected.triggerType;
    	$.post("/task/getTrigger",{triggerGroup:triggerGroup, triggerName:triggerName},function(data){
    		$('#oldTriggerName').val(data.data.triggerName);
    		$('#oldTriggerGroup').val(data.data.triggerGroup);
    		
    		$('#classNameDiv').text(data.data.jobClass);
    		$('#className').val(data.data.jobClass);
    		$('#triggerName').val(data.data.triggerName);
    		$('#triggerGroup').val(data.data.triggerGroup);
    		$('#jobName').val(data.data.jobName);
    		$('#description').text(data.data.description);
    		if(type=='CRON'){
    			$("#cronExpression").parents(".tr").removeClass("dn");
    			$("#loopDelay,#repeatCount").parents(".tr").addClass("dn");
    			$('#cronExpression').val(data.data.cronExpression);
    		}else if(type=='SIMPLE'){
    			$("#loopDelay,#repeatCount").parents(".tr").removeClass("dn");
    			$("#cronExpression").parents(".tr").addClass("dn");
        		$('#loopDelay').val(data.data.repeatInterval);
        		$('#repeatCount').val(data.data.repeatCount);
    		}
    		buildParmsGrid(data.data.parms);
            $('#configTriggerDlg').dialog({ 
                buttons:[{  
                    text:'确定',  
                    iconCls:'icon-ok',  
                    handler:function(){
                    	var url = "/task/updateTrigger"
                    	savaTrigger(url);
                    }  
                },{  
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){  
                        $('#configTriggerDlg').dialog('close');  
                    }  
                }]  
            });
    	});
    }
    
    function savaTrigger(url){
        $('#configTriggerForm').form('submit',{
            url: url,
            onSubmit: function(param){
            	$('#parmGrid').datagrid('endEdit', lastIndex);
            	var rows = $('#parmGrid').datagrid('getRows');
            	if (rows && rows.length > 0){
                    var keys = new Array();
                    var vas = new Array();
                    for(var i = 0; i < rows.length; i++){
                    	keys.push(rows[i].key);
                    	vas.push(rows[i].va);
                    }
                    param.keys = keys;
                    param.vas = vas;
            	}
            	return $(this).form('validate');
            },
            success: function(result){
            	$('#triggerGrid').datagrid('reload');
            	var data = eval('('+result+')');
            	if(data.status!='SUCCESS'){
            		$.messager.alert('错误',data.msg,'error');
            	}else{
            		$('#configTriggerDlg').dialog('close'); 
                	$.messager.show({title: '提示 ', msg: '保存成功！'});
            	}
            },
            onLoadError :function(result){
        		alert(result);
        	}
        });
    }
    
    //运行参数设置
    var lastIndex;  
    function buildParmsGrid(page){
        $('#parmGrid').datagrid({  
            toolbar:[{  
                text:'添加',  
                iconCls:'icon-add',  
                handler:function(){  
                    $('#parmGrid').datagrid('endEdit', lastIndex);  
                    $('#parmGrid').datagrid('appendRow',{  
                        id:'',  
                        keys:'',  
                        values:'' 
                    });  
                    lastIndex = $('#parmGrid').datagrid('getRows').length-1;  
                    $('#parmGrid').datagrid('selectRow', lastIndex);  
                    $('#parmGrid').datagrid('beginEdit', lastIndex);  
                }  
            },'-',{  
                text:'删除',  
                iconCls:'icon-remove',  
                handler:function(){  
                    var row = $('#parmGrid').datagrid('getSelected');  
                    if (row){  
                        var index = $('#parmGrid').datagrid('getRowIndex', row);  
                        $('#parmGrid').datagrid('deleteRow', index);  
                    }  
                }  
            }],  
            onBeforeLoad:function(){  
                $(this).datagrid('rejectChanges');  
            },  
            onClickRow:function(rowIndex){  
                if (lastIndex != rowIndex){  
                    $('#parmGrid').datagrid('endEdit', lastIndex);  
                    $('#parmGrid').datagrid('beginEdit', rowIndex);  
                }  
                lastIndex = rowIndex;  
            }  
        }); 
        if(page){
            page = eval('('+page+')');
            $('#parmGrid').datagrid("loadData",page);
        }

    }
	
});