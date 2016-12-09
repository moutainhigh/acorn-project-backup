<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="grid_togroup" data-options="title:'菜单归组',toolbar:'#tb_togroup',border:false,singleSelect:true,fit:true,fitColumns:true"></table>
<div id="tb_togroup">
    <%--<a href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="New_togroup()">新增</a>--%>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="Edit_togroup()">编辑</a>
    <%--<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="Del_togroup()">删除</a>--%>
</div>
<%--<div id="dlg_togroup" >--%>
    <%--<div class="QueryWrap">--%>
        <%--<form>--%>
            <%--<ul>--%>
                <%--<li><label>菜单组</label>--%>
                    <%--<select class="easyui-combobox"  style="width:126px;">--%>

                    <%--</select>   </li>--%>
                <%--<li><label>URL</label><input  class="text"  type="text" ></li>--%>
                <%--<li><label>菜单名称</label><input  class="text"  type="text" ></li>--%>
                <%--<li><label>Menu Resource Key</label><input  class="text"  type="text" ></li>--%>
                <%--&lt;%&ndash;<li class="fr"><button>查询</button></li>&ndash;%&gt;--%>
            <%--</ul>--%>
        <%--</form>--%>
    <%--</div>--%>
<%--</div>--%>
<div id="dlg_togroup"  style="padding:10px;" data-options="modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true">
    <div id="dlg_togroup_layout"  fit=true>
        <%--<div data-options="region:'north'" style="height:50px"></div>--%>
        <%--<div data-options="region:'south',split:true" style="height:50px;"></div>--%>
        <div data-options="region:'east',border:false" style="width:440px;">
        <table id="all_group"   data-options="
	            title:'所有菜单组',
	            width : '100%',
	            height : 430,
	            nowrap : false,
	            striped : true,
	            border : true,
	            fit:true,
	            fitColumns:true,
	            selectOnCheck:true,
	            checkOnSelect:true,
	            collapsible : false,
	            scrollbarSize:0,
	            url : '',
	          idField : 'id',
	          sortName : 'id',
	          pagination:true,
	          sortOrder : 'desc',
	          <%--toolbar:'#tagButtons',--%>
	          remoteSort : false,
	          rownumbers : true">
        </table>
        </div>
        <div data-options="region:'west',border:false" style="width:440px;">
        <table id="current_group"  data-options=" title : '当前菜单组',
            width : '100%',
            height : 430,
            nowrap : false,
            striped : true,
            border : true,
            fit:true,
            fitColumns:true,
            selectOnCheck:true,
            checkOnSelect:true,
            collapsible : false,
            scrollbarSize:0,
            url : '',
            queryParams:{
	          },
	          idField : 'id',
	          sortName : 'id',
	          sortOrder : 'desc',
	          toolbar:'#tagButtons',
	          remoteSort : false,
	          rownumbers : true
	          ">
        </table>
        </div>
        <div data-options="region:'center',border:false" style="width:100px;padding:180px 10px 0;text-align: center">
            <a id="undo" href="#"data-options="iconCls:'icon-undo',plain:true" style="margin-bottom: 20px" onclick="saveTags()"></a>
            <br>
            <a id="redo" href="#" data-options="iconCls:'icon-redo',plain:true" onclick="removeTag()"></a>
        </div>
    </div>
</div>
<div id="confirm_togroup" >
    <div class="QueryWrap">
        <form>
            <ul>
                <li><h3>确定删除？</h3></li>
            </ul>
        </form>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/js/archives/togroup.js" />"></script>
<script type="text/javascript">
    $(function(){
        $('#grid_togroup').datagrid({
            url:'datagrid_data.json',
            columns:[[
                {field:'a',title:'菜单组名称',width:100,align:'center'},
                {field:'b',title:'系统名称',width:100,align:'center'},
                {field:'c',title:'顺序',width:100,align:'center'}
            ]]
        });
    });
</script>