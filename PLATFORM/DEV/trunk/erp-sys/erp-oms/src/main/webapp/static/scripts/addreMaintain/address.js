$(function() {
    var statuses = [
        {id:'Y',name:'启用'},
        {id:'N',name:'停用'}
    ];
	$('#addressTable')
			.datagrid(
					{
						title : '',
						iconCls : 'icon-edit',
						width:'100%',
						height:390,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,
						fitColumns:true,
						scrollbarSize:0,
						url : '/addreMaintain/list',
						queryParams:{
                            areagroupId : $('#areagroupId').val(),
                            areagroupName : $("#areagroupName").val(),
                            priority : $("#priority").val(),
                            commonCarrier : $("#entityId").val(),
                            channelId:$("#channelId").val()
						},

						columns : [ [
								{
									field : 'areagroupId',
									title : '地址组ID',
									width : 80
								},
								{
									field : 'areagroupName',
									title : '地址组名称',
									width : 120
								},
								{
									field : 'channelId',
									title : '渠道ID',
									width : 80
								},
								{
									field : 'commonCarrier',
									title : '承运商',
									width : 100
								},
								{
									field : 'warehouseName',
									title : '出库仓库',
									width : 100
								},
								{
									field : 'priority',
									title : '优先级',
									width : 60
								},
								{
									field : 'crdt',
									title : '创建时间',
									width : 120
								},
								{
									field : 'cruser',
									title : '创建人',
									width : 100
								},
                               {
                                field : 'status',
                                title : '状态',
                                width : 100,
                                formatter: function(value) {
                                       for(var i=0; i<statuses.length; i++){
                                           if (statuses[i].id == value) return statuses[i].name;
                                       }
                                       return value;
                                   }
                              }] ],
                        frozenColumns:[[
                            {field:'ck',checkbox:true }
                        ]],
						remoteSort : false,
						singleSelect : true,
						pagination : true,
						rownumbers : true,
                        onDblClickRow:function(rowIndex, rowData){
                            $('#showAddressList').window({
                                title:'地址组明细',
                                width:600,
                                height:400,
                                iconCls:'',
                                top:($(window).height() - 200) * 0.5,
                                left:($(window).width() - 500) * 0.5,
                                shadow: true,
                                modal:true,
                                closed:true,
                                minimizable:false,
                                maximizable:false,
                                collapsible:false
                            })
                                .window('open');

                            $('#areaGroupDetailList')
                                .datagrid(
                                {
                                    title : '',
                                    iconCls : 'icon-edit',
                                    width : 560,
                                    height : 300,
                                    nowrap : false,
                                    striped : true,
                                    border : true,
                                    collapsible : false,
                                    fit : true,
                                    scrollbarSize:0,
                                    url : '/addreMaintain/areaGroupDetailList',
                                    queryParams:{
                                        areId:rowData.areagroupId
                                    } ,
                                    columns : [ [
                                        {
                                            field : 'areaGroupId',
                                            title : '地址组ID',
                                            width : 80
                                        },
                                        {
                                            field : 'provinceName',
                                            title : '省(province)',
                                            width : 100
                                        },
                                        {
                                            field : 'cityName',
                                            title : '市(city)',
                                            width : 100
                                        },
                                        {
                                            field : 'countyName',
                                            title : '县(county)',
                                            width : 100
                                        },
                                        {
                                            field : 'areaName',
                                            title : '区(area)',
                                            width : 100
                                        },
                                        {
                                            field : 'sendTime',
                                            title : '时效',
                                            width : 80
                                        }
                                    ] ],
                                    remoteSort : false,
                                    singleSelect : true,
                                    pagination : true,
                                    rownumbers : false
                                });

                            var p = $('#areaGroupDetailList').datagrid('getPager');
                            $(p).pagination({
                                pageSize : 10,
                                pageList : [ 5, 10, 15 ],
                                beforePageText : '第',
                                afterPageText : '页    共 {pages} 页',
                                displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
                                onBeforeRefresh : function() {
                                    $(this).pagination('loading');
                                    $(this).pagination('loaded');
                                }
                            });
                        }
					});

	var p = $('#addressTable').datagrid('getPager');
	$(p).pagination({
		pageSize : 10,
		pageList : [ 5, 10, 15 ],
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function() {
			$(this).pagination('loading');
			$(this).pagination('loaded');
		}
	});

    // 查询按钮
    $("#queryBtn").click(function() {
        $('#addressTable').datagrid('load', {
            areagroupId : $('#areagroupId').val(),
            areagroupName : $("#areagroupName").val(),
            priority : $("#priority").val(),
            commonCarrier : $("#entityId").combobox('getValue'),
            channelId:$("#channelId").val()
        });
    });

    //启用按钮
    $("#lbOn").click(function(){
        var rows = $('#addressTable').datagrid('getSelections');
        if(rows && rows.length > 0)
        {
            $.messager.confirm('确认','当前选中的'+rows.length+'条记录,确定启用吗?',function(r){
                if(r)
                {
                    var h = $('#addressTable').datagrid('getChecked')[0].areagroupId;
                    $.post('/addreMaintain/on',{ id:h },function(result){
                        if (result.success){
                            $('#addressTable').datagrid('reload');    // reload the user data
                        } else {
//                            $.messager.show({   // show error message
//                                title: 'Error',
//                                msg: result.errorMsg
//                            });
                        	$.messager.alert('错误',result.errorMsg,'error');
                        }
                    },'json');
                }
            })
        }
        else
        {
            alert("请选择一条数据!")
        }
    });
    //停用按钮
    $("#lbOff").click(function(){
        var rows = $('#addressTable').datagrid('getSelections');
        if(rows && rows.length > 0)
        {
            $.messager.confirm('确认','当前选中的'+rows.length+'条记录,确定停用吗?',function(r){
                if(r)
                {
                    var h = $('#addressTable').datagrid('getChecked')[0].areagroupId;
                    $.post('/addreMaintain/off',{ id:h },function(result){
                        if (result.success){
                            $('#addressTable').datagrid('reload');    // reload the user data
                        } else {
//                            $.messager.show({   // show error message
//                                title: 'Error',
//                                msg: result.errorMsg
//                            });
                        	$.messager.alert('错误',result.errorMsg,'error');
                        }
                    },'json');
                }
            })
        }
        else
        {
            alert("请选择一条数据!")
        }
    });
    //新增按钮
    $("#lbAdd").click(function(){
       $('#dlg').window({
           title:'新增地址组',
           width:420,
           height:260,
           iconCls:'',
           top:($(window).height() - 200) * 0.5,
           left:($(window).width() - 500) * 0.5,
           shadow: true,
           modal:true,
           closed:true,
           minimizable:false,
           maximizable:false,
           collapsible:false
       })
           .window('open');

    });
    //保存
    $("#lbSave").click(function(){
        $('#fm').form('submit',{
            url: "/addreMaintain/save",
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if (result.errorMsg){
                    alert(result.errorMsg);
                }
                $('#dlg').dialog('close');      // close the dialog
                $("#apName").val('');
                $("#apDesc").val('');
                $("#cid").val('');
                $('#addressTable').datagrid('reload');    // reload the user data
            }

        });
    });
    //取消新增
    $("#lbClose").click(function(){
        $('#dlg').dialog('close');
    });

    // 明细导入 窗口
    $("#btnExcelUpload").click(function() {
        $('#uploadFileDiv').window('open');
        $('#fileToUpload').val("");

        return;
    });
});


//导出明细
function exportOderList(){
    var rows = $('#addressTable').datagrid('getSelections');
    if(rows && rows.length > 0)
    {
        var h = $('#addressTable').datagrid('getChecked')[0].areagroupId;
        $('#d_areId').val(h);
        $("#downloadForm").submit();
    }
    else
    {
        alert("当前未选中任何数据!")
    }

}
//地址明细导入
function ajaxFileUpload()
{
    var file = $('#fileToUpload').val();
    //检查是否已经选择上传文件
    if(file != ''){
        var filename = file.replace(/.*(\/|\\)/, '');
        var fileext = (/[.]/.exec(filename)) ? /[^.]+$/.exec(filename.toLowerCase()) : '';
        //检查文件格式
        if(fileext == 'xls'){
            //展示等待信息
            $("#loading")
                .ajaxStart(function(){
                    $(this).show();
                })
                .ajaxComplete(function(){
                    $(this).hide();
                });
            $.ajaxFileUpload(
                {
                    url:'/addreMaintain/upload',
                    secureuri:false,
                    fileElementId:'fileToUpload',
                    dataType: 'json',
                    beforeSend:function()
                    {
                        $("#loading").show(); //该图用来显示上传图片ing
                    },
                    complete:function()
                    {
                        $("#loading").hide();//隐藏该图
                    },
                    success: function (data, status)
                    {
                        if(typeof(data.error) != 'undefined')
                        {
                            if(data.error != '')
                            {
                                alert("操作提示："+data.error);
                            }else
                            {
                                alert(data.msg);
                            }
                        }else{
                            alert("操作提示："+data.success);
                            $('#uploadFileDiv').dialog('close');
                        }
                    },
                    error: function (data, status, e)
                    {
                        alert("服务器响应失败:"+e);
                    }

                }
            );

        }else{
            alert('文件格式必须是*.xls');
        }
    }else{
        alert('请选择excel文件！');
    }
    //  返回
    return false;
}






