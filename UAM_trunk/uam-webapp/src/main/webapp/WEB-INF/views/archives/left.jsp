<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="easyui-accordion"   border="false">   
	<div title="角色维护" data-options="iconCls:''" style="overflow:auto;padding:10px;">
        <ul>
            <li><a href="" data-sm="archives/role">角色维护</a></li>
        </ul>
    </div> 
    <div title="部门维护" data-options="iconCls:''" style="overflow:auto;padding:10px;">
        <ul>
            <li><a href="" data-sm="archives/department">部门维护</a></li>
        </ul>
    </div>
    <div title="组维护" data-options="iconCls:''" style="overflow:auto;padding:10px;">
        <ul>
            <li><a href="" data-sm="archives/group">组维护</a></li>
        </ul>
    </div>
    <div title="用户配置" data-options="iconCls:''" style="overflow:auto;padding:10px;">
        <ul>
            <li><a href="" data-sm="archives/user">用户配置</a></li>
        </ul>
    </div>
    <div title="用户维护" data-options="iconCls:''" style="overflow:auto;padding:10px;">
        <ul>
            <li><a href="" data-sm="archives/role">用户维护</a></li>
        </ul>
    </div>
   
    <div title="菜单维护"  style="overflow:auto;padding:10px;">
        <ul>
            <li><a href="" data-sm="archives/menuGroup">菜单组编辑</a></li>
            <li><a href="" data-sm="archives/menu">菜单编辑</a></li>
            <li><a href="" data-sm="archives/togroup">菜单归组</a></li>
        </ul>
    </div>
    <div title="控件维护"  style="overflow:auto;padding:10px;">
        <ul>
            <li><a href="" data-sm="archives/page">页面编辑</a></li>
            <li><a href="" data-sm="archives/buttons">按钮编辑</a></li>
        </ul>
    </div>
    <div title="数据过滤" border="false" style="overflow:auto;padding:10px;">
        <ul>
            <li><a href="" data-sm="archives/dataFilter">数据过滤</a></li>
        </ul>
    </div>
    <div title="系统维护" border="false" style="overflow:auto;padding:10px;">
        <ul>
            <li><a href="" data-sm="archives/sys">系统维护</a></li>
        </ul>
    </div>
</div>


<script type="text/javascript">
    $(function(){
        initMemus($('a[data-sm]'));
    });
</script>

