<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table id="grid_buttons" class="easyui-datagrid" data-options="title:'按钮维护',toolbar:'#tb_buttons',fitColumn:'true',border:false,singleSelect:true,fit:true,fitColumns:true"></table>
<div id="tb_buttons">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="New_buttons()">新增</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="Edit_buttons()">编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="Del_buttons()">删除</a>
</div>
<div id="dlg_buttons" >
    <div class="QueryWrap">
        <form>
            <ul>
                <li><label>菜单组</label>
                    <select class="easyui-combobox"  style="width:126px;"></select>
                </li>
                <li><label>URL</label><input  class="text"  type="text" ></li>
                <li><label>菜单名称</label><input  class="text"  type="text" ></li>
                <li><label>Menu Resource Key</label><input  class="text"  type="text" ></li>
                <%--<li class="fr"><button>查询</button></li>--%>
            </ul>
        </form>
    </div>
</div>
<div id="confirm_buttons" >
    <div class="QueryWrap">
        <form>
            <ul>
                <li><h3>确定删除？</h3></li>
            </ul>
        </form>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/js/archives/buttons.js" />"></script>
<script type="text/javascript">
    $(function(){
        $('#grid_buttons').datagrid({
            url:'datagrid_data.json',
            columns:[[
                {field:'a',title:'页面名称',width:100,align:'center'},
                {field:'b',title:'控件名称',width:100,align:'center'},
                {field:'c',title:'Control Resource Key',width:100,align:'center'},
                {field:'d',title:'顺序号',width:100,align:'center'},
                {field:'e',title:'活动的',width:100,align:'center'}
            ]]
        });
    });
</script>