<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="grid_menuG"></table>
<div id="tb_menuG">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="new_menuG()">新增</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit_menuG()">编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del_menuG()">删除</a>
</div>
<div id="id_dlg_new_menuG" >
    <div class="QueryWrap">
        <form>
            <ul>
                <li><label>名称</label><input id="id_menuG_new_name" class="text"  type="text" ></li>
                <li><label>描述</label><input id="id_menuG_new_description" class="text"  type="text" ></li>
            </ul>
        </form>
    </div>
</div>
<div id="id_dlg_eidt_menuG" >
    <div class="QueryWrap">
        <form>
            <ul>
                <input id="id_menuG_edit_id" class="text"  type="hidden" >
                <li><label>名称</label><input id="id_menuG_edit_name" class="text"  type="text" ></li>
                <li><label>描述</label><input id="id_menuG_edit_description" class="text"  type="text" ></li>
            </ul>
        </form>
    </div>
</div>
<div id="confirm_menuG" >
    <div class="QueryWrap">
        <form>
            <ul>
                <li><h3>确定删除？</h3></li>
            </ul>
        </form>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/js/archives/menuGroup.js" />"></script>