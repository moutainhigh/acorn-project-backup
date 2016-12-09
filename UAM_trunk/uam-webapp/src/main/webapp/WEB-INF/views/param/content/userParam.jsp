<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="item1Wrap">
    <table id="id_userParam_table"></table>
    <div id="id_userParam_linkbutton">
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit_userParam_Pa()">编辑</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del_userParam_Pa()">删除</a>
    </div>
</div>
<div id="id_dlg_userParam_pa" >
    <div class="QueryWrap">
        <form>
            <ul>
                <input id="id_edit_userParam_id" type="hidden">
                <li><label>用户名</label><input id="id_edit_userParam_name"  class="text"  type="text" ></li>
                <li><label>角色名</label><input id="id_edit_userParam_roleName"  ></li>
                <li><label>职务</label><input id="id_edit_userParam_userTitle" class="text"  type="text" ></li>
                <li><label>工作组</label><input id="id_edit_userParam_workGroup"  class="text"  type="text" ></li>
            </ul>
        </form>
    </div>
</div>

<div id="confirm_userParam_pa" >
    <div class="QueryWrap">
        <form>
            <ul>
                <input id="id_delete_userParam_id" type="hidden">
                <li><h3>确定删除？</h3></li>
            </ul>
        </form>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/js/param/userParam.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/json.min.js" />"></script>


