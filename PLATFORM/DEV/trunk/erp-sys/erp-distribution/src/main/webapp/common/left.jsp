<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<ul id="tt"></ul>
<div id="mm" class="easyui-menu" style="width:120px;">
    <div id="addBtns" data-options="iconCls:'icon-add',menu:'#mm1'"><span>添加</span>
        <div style="width:120px;">
            <div id="appendFile" onclick="appendFile()" data-options="iconCls:'icon-file'">文件</div>
            <div onclick="appendFolder()" data-options="iconCls:'icon-folder'">目录</div>
        </div>
    </div>
    <div id="editBtn" onclick="editit()" data-options="iconCls:'icon-edit'">编辑</div>
    <div id="removeBtn" onclick="confirmRemove()" data-options="iconCls:'icon-remove'">删除</div>
    <div class="menu-sep"></div>
    <div onclick="expand()">展开</div>
    <div onclick="collapse()">收缩</div>
</div>
<script type="text/javascript">
var $_ctx = "${ctx}";
</script>
<script type="text/javascript" src="${ctx}/static/js/left.js"></script>
