<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
	.QueryWrap li span.tips{color:#575765;margin-left:10px;}
	.datagrid-wrap{border:none;}
</style>
<table id="grid_department"></table>
<div id="tb_department" style="border:none;">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="new_department()">新增</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit_department()">编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del_department()">删除</a>
    <!-- hsh事件和图标 -->
    <a href="#" class="easyui-linkbutton" iconCls="icon-addUser" plain="true" onclick="add_department()">增加角色</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-removeUser" plain="true" onclick="remove_department()">删除角色</a>
</div>
<div id="id_dlg_new_department" >
    <div class="QueryWrap">
        <form>
            <ul>
                <li><label>部门名称</label><input id="id_department_new_name" class="text"  type="text" ></li>
                <li><label>编码</label><input id="id_department_new_name" class="text"  type="text" ></li>
                <li><label>区号</label><input id="id_department_new_description" class="text"  type="text" ></li>
            </ul>            
        </form>
    </div>
</div>
<div id="id_dlg_add_department" >
    <div class="QueryWrap">
        <form>
            <ul>               
            	<li><span class="tips">为该部门当前所有员工增加权限<span></span></li>
                <li><label>角色名</label><input id="id_department_new_name02" style="width:120px;"/></li>
            </ul>            
        </form>
    </div>
</div>
<div id="id_dlg_remove_department" >
    <div class="QueryWrap">
        <form>
            <ul>               
            	<li><span class="tips">为该部门当前所有员工删除权限</span></li>
                <li><label>角色名</label><input id="id_department_new_name03" style="width:120px;"/></li>
            </ul>            
        </form>
    </div>
</div>
<div id="id_dlg_edit_department" >
    <div class="QueryWrap">
        <form>
            <ul>                
                <li><label>系统</label><input id="id_department_edit_site_name"   ></li>
                <li><label>名称</label><input id="id_department_edit_name" class="text"  type="text" ></li>
                <li><label>描述</label><input id="id_department_edit_description" class="text"  type="text" ></li>
            </ul>
        </form>
    </div>
</div>
<div id="confirm_department" >
    <div class="QueryWrap">
        <form>
            <ul>
                <li><h3>确定删除？</h3></li>
            </ul>
        </form>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/js/archives/department.js" />"></script>