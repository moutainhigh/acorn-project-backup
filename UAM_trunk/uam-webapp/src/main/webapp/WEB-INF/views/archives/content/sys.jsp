<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="grid_sys" ></table>
<div id="tb_sys">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="new_sys()">新增</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit_sys()">编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="Del_sys()">删除</a>
</div>
<div id="id_dlg_new_sys" >
    <div class="QueryWrap">
        <form>
            <ul>
                <li><label>名称</label><input id="id_new_site_name" class="text"  type="text" /></li>
                <li><label>路径</label><input id="id_new_site_serviceId" class="text"  type="text" /></li>
                <li><label>描述</label><input id="id_new_site_description" class="text"  type="text" /></li>
                <li><label>单点登录</label><input id="id_new_site_ssoEnabled" class="text"  type="checkbox" checked="checked" /></li>
                <li><label>匿名访问</label><input id="id_new_site_anonymousAccess" class="text"  type="checkbox" /></li>
                <li><label>允许代理</label><input id="id_new_site_allowedToProxy" class="text"  type="checkbox" checked="checked" /></li>
            </ul>
        </form>
    </div>
</div>

<div id="id_dlg_edit_sys" >
    <div class="QueryWrap">
        <form>
            <ul>
                <input id="id_edit_site_id" class="text"  type="hidden" />
                <li><label>名称</label><input id="id_edit_site_name" class="text"  type="text" /></li>
                <li><label>路径</label><input id="id_edit_site_serviceId" class="text"  type="text" /></li>
                <li><label>描述</label><input id="id_edit_site_description" class="text"  type="text" /></li>
                <li><label>是否启用</label><input id="id_edit_site_enabled" class="text" type="checkbox" checked="checked" /></li>
                <li><label>单点登录</label><input id="id_edit_site_ssoEnabled" class="text"  type="checkbox" checked="checked" /></li>
                <li><label>匿名访问</label><input id="id_edit_site_anonymousAccess" class="text"  type="checkbox" /></li>
                <li><label>允许代理</label><input id="id_edit_site_allowedToProxy" class="text"  type="checkbox" checked="checked" /></li>
            </ul>
        </form>
    </div>
</div>
<div id="confirm_sys" >
    <div class="QueryWrap">
        <form>
            <ul>
                <li><h3>确定删除？</h3></li>
            </ul>
        </form>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/js/archives/sys.js" />"></script>

