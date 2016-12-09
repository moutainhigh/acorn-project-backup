<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="grid_filter" class="easyui-datagrid" data-options="title:'数据过滤维护',toolbar:'#tb_filter',fitColumn:'true',border:false,singleSelect:true,fit:true,fitColumns:true"></table>
<div id="tb_filter">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="New_filter()">新增</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="Edit_filter()">编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="Del_filter()">删除</a>
</div>
<div id="dlg_filter" >
    <div class="QueryWrap">
        <form>
            <ul>
                <li>
                	<label>菜单组</label>
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
<div id="confirm_filter" >
    <div class="QueryWrap">
        <form>
            <ul>
                <li><h3>确定删除？</h3></li>
            </ul>
        </form>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/js/archives/datafilter.js" />"></script>
<script type="text/javascript">
    $(function(){
        $('#grid_filter').datagrid({
            url:'datagrid_data.json',
            columns:[[
                {field:'a',title:'暂定',width:100,align:'center'},
                {field:'b',title:'暂定',width:100,align:'center'},
                {field:'c',title:'暂定',width:100,align:'center'},
                {field:'d',title:'暂定',width:100,align:'center'},
                {field:'e',title:'暂定',width:100,align:'center'}
            ]]
        });
    });
</script>