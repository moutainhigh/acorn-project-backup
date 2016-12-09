<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="item1Wrap">
    <!-- <table id="grid_item4_pa" data-options="title:'暂定4',toolbar:'#tb_item4_pa',fitColumn:'true',singleSelect:true,fit:true,fitColumns:true">
    </table> -->
    <table id="grid_item4" class="easyui-datagrid">
    	 <thead>
	        <tr>
	            <th field="groupId" width="100" data-options="align:'center'">配置组ID</th>
	            <th field="groupName" width="100" data-options="align:'center'">配置组名称</th>
	            <th field="configId" width="100" data-options="align:'center'">配置项ID</th>
	            <th field="configName" width="100" data-options="align:'center'">配置项名称</th>
	            <th field="op" width="100" data-options="align:'center'" formatter="formatAction">详细</th>	            
	        </tr>
	    </thead>
    </table>
    <div id="tb_item4_pa">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="New_item4_Pa()">新增</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="Edit_item4_Pa()">编辑</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="Del_item4_Pa()">删除</a>        
    </div>
</div>
<div id="dlg_item4_pa" >
    <div class="QueryWrap">
        <form>
        	<input id="id_config_id" type="hidden" />
            <ul>               
                <li><label>配置组</label><input id="id_select_gruop" class="easyui-combobox" style="width:124px;" /></li>
                <li><label>配置名</label><input id="id_config_name" type="text" class="easyui-validatebox" style="width:120px;" /></li>
            </ul>
        </form>
    </div>
</div>
<div id="confirm_item4_pa">
    <div class="QueryWrap">
        <form>
            <ul>
                <li><h3>确定删除？</h3></li>
            </ul>
        </form>
    </div>
</div>
<div id="dlg_property">
    <div class="QueryWrap">
        <form>
        	<input id="id_property_index" type="hidden" />
        	<input id="id_input_config_id" type="hidden" />
        	<input id="id_input_property_id" type="hidden" />
            <ul>               
                <li><label>Key</label><input id="property_key" type="text" class="easyui-validatebox" /></li>
                <li><label>Value</label><input id="property_value" type="text" class="easyui-validatebox" /></li>
            </ul>
        </form>
    </div>
</div>
<div id="del_property">
    <div class="QueryWrap">
        <form>
            <ul>
                <li><h3>确定删除？</h3></li>
            </ul>
        </form>
    </div>
</div>
<div class="easyui-dialog config-dialog" data-options="width:500,height:300,closed:true,shadow:true,title:'详细列表'">
	<table id="pg" class="easyui-propertygrid" style="width:300px" data-options="showGroup:true,scrollbarSize:0,fit:true"></table>
</div>
<script type="text/javascript" src="js/datagrid-detailview.js"></script>
<script type="text/javascript" src="js/param/config.js?"></script>
<script type="text/javascript">
	function formatAction(value,row,index){			
		return '<a href="#" onclick="detail()">详情</a> ';
	}
	function detail(){		
		$(".config-dialog").dialog({closed:false});
	}
    $(function(){               
    	$('#grid_item4').datagrid({
    		title:'系统配置',
    		//url:'param/config/list',
    		fitColumn:true,
    		height:480,
    		toolbar:'#tb_item4_pa',
    		singleSelect:true,            
    	    view: detailview,    	    
    	    detailFormatter:function(index,row){
    	        return '<div style="padding:10px"><table id="prop_' + index + '"></table></div>';
    	    },
    	    onExpandRow: function(index,row){
    	        $('#prop_' + index).datagrid({
                    url: 'param/config/' + row.configId + '/properties',
                    fitColumns: true,
                    singleSelect: true,
                    height: 'auto',
                    columns: [[
                        {checkbox: true},
                        {field: 'id', title: 'ID', hidden: true},
                        {field: 'key', title: '配置项键', width: 80},
                        {field: 'value', title: '配置项值', width: 150}
                    ]],
                    onResize: function() {
                    	$('#grid_item4').datagrid('fixDetailRowHeight',index);
                    },
                    onLoadSuccess:function(){
                        setTimeout(function(){
                        	$('#grid_item4').datagrid('fixDetailRowHeight',index);
                        },0);
                    },
                    toolbar: [{
                		iconCls: 'icon-add',
                		handler: function(){
                			$('#id_input_config_id').val(row.configId);
                			$('#id_property_index').val(index);
                			$('#dlg_property').dialog('setTitle','新增配置键值对').dialog('open');
                		}
                	},
                	{
                		iconCls: 'icon-edit',
                		handler: function(){
                			var propData = $('#prop_' + index).datagrid('getSelected');
                			if (propData) {
                				$('#id_input_config_id').val(row.configId);
                    			$('#id_input_property_id').val(propData.id);
                    			$('#id_property_index').val(index);
                    			$('#property_key').val(propData.key);
                    			$('#property_value').val(propData.value);
                    			$('#property_key').validatebox('validate');
                    			$('#property_value').validatebox('validate');                    			
                    			$('#dlg_property').dialog('setTitle','修改配置键值对').dialog('open');
                			}
                		}
                	},
                	{
                		iconCls: 'icon-remove',
                		handler: function(){
                			var propData = $('#prop_' + index).datagrid('getSelected');
                			if (propData != null) {
                				$('#id_property_index').val(index);
                				$('#del_property').dialog('open');
                			}
                		}
                	}]
                });
    	        
    	        $('#grid_item4').datagrid('fixDetailRowHeight',index);
    	    }
    	});
    	$('#grid_item4').datagrid({
    		data:[
    		 	{groupId:'value11',groupName:'value12',configId:'value13',configName:'value14'}     
 		    ]    		
    	});
    	$("#pg").propertygrid({
    		data:{"total":4,"rows":[
                {"name":"Name","value":"Bill Smith","group":"组名称","editor":"text"}               
            ]}    		
    	})
    	$('#id_select_gruop').combobox({
            url:'param/group/list',
            required:true,
            valueField:'id',
            textField:'groupName'
        });
    	
    	$("#id_config_name").validatebox({
        	required:true        	
        });
    	
    	$("#property_key").validatebox({
        	required:true        	
        });
    	
    	$("#property_value").validatebox({
        	required:true        	
        });
    	
    	$('#dlg_property').dialog({
            title:' ',
            width: 400,
            closed: true,
            cache: false,
            modal: true,
            buttons:[{
                text:'保存',
                iconCls:'icon-save',
                plain:true,
                handler:function(){
                	if (!$('#property_key').validatebox('isValid')
                			|| !$('#property_value').validatebox('isValid')) {
                		return;
                	}
                	
                	var data = {};
                	data.configId = $('#id_input_config_id').val();
                	if ($('#id_input_property_id').val()) {
                		data.id = $('#id_input_property_id').val();
                	}
                	data.key = $('#property_key').val();
                	data.value = $('#property_value').val();
                	
                	$.ajax({
            			url: 'param/property/save',
            			type: 'POST',
            			async: false,
            			data: data,
            			dataType: 'json',
            			success: function(data) {
            				if (data.res == '1') {
            					alert('操作成功');
            					$('#dlg_property').dialog('close');
            					$('#prop_' + $('#id_property_index').val()).datagrid('reload');
            				} else {
            					alert(data.msg);
            				}
            			}
            		});
                }
            },{
                text:'取消',
                plain:true,
                iconCls:'icon-cancel',
                handler:function(){
                	$('#dlg_item4_pa').dialog('close');
                }
            }]
        });    	
    	$('#del_property').dialog({
            title:'提示',
            width: 400,
            closed: true,
            cache: false,
            modal: true,
            buttons:[{
                text:'确定',
                iconCls:'icon-ok',
                plain:true,
                handler:function(){
                	var propData = $('#prop_' + $('#id_property_index').val()).datagrid('getSelected');
                	$.ajax({
            			url: 'param/property/remove/' + propData.id,
            			async: false,
            			dataType: 'text',
            			success: function(data) {
            				if (data == 'success') {
            					alert('删除成功');
            					$('#prop_' + $('#id_property_index').val()).datagrid('reload');
            					$('#del_property').dialog('close');
            				}
            			}
            		});
                }
            },{
                text:'取消',
                plain:true,
                iconCls:'icon-cancel',
                handler:function(){
                	$('#del_property').dialog('close');
                }
            }]
        });
    });
</script>

