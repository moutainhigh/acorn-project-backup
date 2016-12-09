<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="item1Wrap">
    <table id="grid_item3_pa" data-options="title:'配置组',toolbar:'#tb_item3_pa',fitColumn:'true',singleSelect:true,fit:true,fitColumns:true">

    </table>
    <div id="tb_item3_pa">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="New_item3_Pa()">新增22</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="Edit_item3_Pa()">编辑</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="Del_item3_Pa()">删除</a>
    </div>
</div>
<div id="dlg_item3_pa" >
    <div class="QueryWrap">
        <form>
        	<input id="groupId" name="id" type="hidden" />
            <ul>
            	<li><label>配置组名称</label><input id="groupName" type="text" class="easyui-validatebox"/></li>
                <li><label>配置组描述</label><input id="descrition" type="text" class="text" /></li>
            </ul>
        </form>
    </div>
</div>
<div id="confirm_item3_pa" >
    <div class="QueryWrap">
        <form>
            <ul>
                <li><h3>确定删除？</h3></li>
            </ul>
        </form>
    </div>
</div>
<script type="text/javascript" src="js/param/group.js?1"></script>
<script type="text/javascript">
    $(function(){
        $('#grid_item3_pa').datagrid({
            url:'param/group/list',
            singleSelect:true,
            columns:[[
                {checkbox:true},
                {field:'id',title:'配置组ID',width:60,align:'center'},
                {field:'groupName',title:'配置组名称',width:100,align:'center'},
                {field:'descrition',title:'配置组描述',width:200,align:'center'}
            ]]
        });
        $("#groupName").validatebox({
        	required:true        	
        });
    });
</script>

