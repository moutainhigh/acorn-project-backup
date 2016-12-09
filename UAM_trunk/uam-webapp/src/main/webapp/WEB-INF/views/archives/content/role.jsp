<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="grid_role"></table>
<div id="tb_role">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="new_role()">新增</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit_role()">编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del_role()">删除</a>              
</div>
<div id="id_dlg_new_role" >
    <div class="QueryWrap">
        <form>
            <ul>
                <li><label>系统</label><input id="id_role_new_site_name" style="width:125px;" ></li>
                <li><label>名称</label><input id="id_role_new_name" class="text"  type="text" ></li>
                <li><label>描述</label><input id="id_role_new_description" class="text"  type="text" ></li>
            </ul>          
        </form>
    </div>
</div>
<div id="id_dlg_edit_role" >
    <div class="QueryWrap">
        <form>
            <ul>
                <input id="id_role_edit_id" class="text"  type="hidden" />
                <li><label>系统</label><input id="id_role_edit_site_name"   ></li>
                <li><label>名称</label><input id="id_role_edit_name" class="text"  type="text" ></li>
                <li><label>描述</label><input id="id_role_edit_description" class="text"  type="text" ></li>
            </ul>
        </form>
    </div>
</div>

<div id="confirm_role" >
    <div class="QueryWrap">
        <form>
            <ul>
                <li><h3>确定删除？</h3></li>
            </ul>
        </form>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/js/archives/role.js" />"></script>