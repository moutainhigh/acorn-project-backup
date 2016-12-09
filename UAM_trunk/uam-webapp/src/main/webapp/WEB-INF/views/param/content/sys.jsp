<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="item1Wrap">
<table id="grid_sys_pa"
       data-options="title:'系统维护',toolbar:'#tb_sys_pa',fitColumn:'true',singleSelect:true,fit:true,fitColumns:true">

</table>
<div id="tb_sys_pa">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="New_sys_Pa()">新增</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="Edit_sys_Pa()">编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="Del_sys_Pa()">删除</a>
</div>
    </div>
<div id="dlg_sys_pa" >
    <div class="QueryWrap">
        <form>
            <ul>
                <li><label>菜单组</label>
                    <select class="easyui-combobox"  style="width:126px;">

                    </select>   </li>
                <li><label>URL</label><input  class="text"  type="text" ></li>
                <li><label>菜单名称</label><input  class="text"  type="text" ></li>
                <li><label>Menu Resource Key</label><input  class="text"  type="text" ></li>
                <%--<li class="fr"><button>查询</button></li>--%>
            </ul>
        </form>
    </div>
</div>
<div id="confirm_sys_pa" >
    <div class="QueryWrap">
        <form>
            <ul>
                <li><h3>确定删除？</h3></li>
            </ul>
        </form>
    </div>
</div>
<div id="im_sys_pa" >
    <div class="QueryWrap" style="text-align: center">
        <form>
            <ul>
                <li><input type="file"/></li>
            </ul>
        </form>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/js/param/sys.js" />"></script>
<script type="text/javascript">
    $(function(){
        $('#grid_sys_pa').datagrid({
            url:'datagrid_data.json',
            columns:[[
                {field:'a',title:'系统ID',width:100,align:'center'},
                {field:'b',title:'系统名称',width:100,align:'center'},
                {field:'c',title:'描述',width:100,align:'center'}
            ]]
        });
    });
</script>

