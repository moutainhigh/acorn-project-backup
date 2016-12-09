
$(function(){
	
    $('#jobClassGrid').datagrid({  
        title:'可以配置的任务列表',  
        iconCls:'icon-save',  
		singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: true,  
        fit : true,
        height:400,  
        nowrap: false,  
        autoRowHeight: false,  
        striped: true,  
        collapsible:false,  
        collapsed:false,
        url:'/task/listJobClass',  
        sortName: 'triggerName',  
        sortOrder: 'desc',  
        remoteSort: false,  
        idField:'triggerName',  
        frozenColumns:[[
            {field:'ck',checkbox:true},  
        ]],  
		columns:[[
			{field:'clazz',title:'类名',width:600,sortable:true},
			{field:'description',title:'描述',width:500}
		]],  
        pagination:true,  
        rownumbers:true,  
        toolbar:[{  
            id:'btnCron',  
            text:'配置CRON触发器',  
            iconCls:'icon-add',  
            handler:function(){
            	config("CRON");
            	$('#triggerName,#jobName').validatebox({required: true}); 
            	$('#cronExpression').validatebox({required: true});
                $('#loopDelay').validatebox({required: false}); 
                $("#configTriggerForm").form('validate');
            }  
        },{  
            id:'btnSimple',  
            text:'配置SIMPLE触发器',  
            iconCls:'icon-add',  
            handler:function(){
            	config("SIMPLE");
            	$('#triggerName,#jobName').validatebox({required: true}); 
            	$('#cronExpression').validatebox({required: false});
            	$('#loopDelay').validatebox({required: true});
            	$("#configTriggerForm").form('validate');
            }  
        },{  
            id:'btnTriggerList',  
            text:'触发器列表',  
            iconCls:'icon-back',  
            handler:function(){  
            	window.location.href = '/triggerList';
            }  
        },'-',{  
            id:'btnRemark',  
            text:'配置说明',  
            iconCls:'icon-help',  
            handler:function(){
            	window.location.href = "/task/configTriggerRemark";
            }  
        }],
        onLoadSuccess: function(data){

    	},
    	onLoadError :function(result){
    		alert("出错");
    	}
    });
    
    $('#configTriggerDlg').dialog({}).window('close');
    
    function getJobClassGridSelected(){  
        var selected = $('#jobClassGrid').datagrid('getSelected');   
        return selected;
    }
    
    function config(type){
    	var selected = getJobClassGridSelected();
    	if(selected){
    		$("#triggerName,#jobName,#cronExpression,#loopDelay,#repeatCount,#description").val("")
    		var url = "/task/createTrigger/" + selected.clazz + "/" + type;
    		$('#className').text(selected.clazz);
    		if(type=='CRON'){
    			$("#cronExpression").parents(".tr").removeClass("dn");
    			$("#loopDelay,#repeatCount").parents(".tr").addClass("dn");
    		}else if(type=='SIMPLE'){
    			$("#loopDelay,#repeatCount").parents(".tr").removeClass("dn");
    			$("#cronExpression").parents(".tr").addClass("dn");
    		}
    		buildParmsGrid();
            $('#configTriggerDlg').dialog({ 
                buttons:[{  
                    text:'确定',  
                    iconCls:'icon-ok',  
                    handler:function(){  
                    	configFormSubmit(url); 
                    }  
                },{  
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){  
                        $('#configTriggerDlg').dialog('close');  
                    }  
                }]  
            });  
    	}else{
    		$.messager.alert('错误','请先选择一条数据！','error'); 
    	}
    }
    
    function configFormSubmit(url){
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
            	var data = eval('('+result+')');
            	if(data.status!='SUCCESS'){
            		$.messager.alert('错误',data.msg,'error');
            	}else{
            		$('#configTriggerDlg').dialog('close'); 
                	$.messager.show({title: '提示 ', msg: '创建成功！'});
            	}
            },
            onLoadError :function(result){
        		alert(result);
        	}
        });
    }
    
    //运行参数设置
    var lastIndex;  
    function buildParmsGrid(){
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
    }
    
});