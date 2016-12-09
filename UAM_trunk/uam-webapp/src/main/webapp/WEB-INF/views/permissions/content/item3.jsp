<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="item1Wrap">
    <table id="item3_datagrid"
           data-options="title:'角色控件维护',toolbar:'#tb_item3',fitColumn:'true',singleSelect:true,fit:true,fitColumns:true">

    </table>
    <div id="tb_item3">
        <div class="QueryWrap">
            <form>
                <ul>
                    <li><label>ID</label><input class="text" type="text" ></li>
                    <li><label>控件名</label><input  class="text"  type="text" ></li>
                    <li><label>描述</label><input class="text"  type="text" ></li>
                    <li><label>现有控件</label>
                        <select class="easyui-combobox" name="state" style="width:126px;">

                        </select>   </li>
                    <li><label>最后修改时间</label><input  class="text"  type="text" ></li>
                    <li><label>活动的</label><input  class="text"  type="text" ></li>
                    <%--<li class="fr"><button>查询</button></li>--%>
                </ul>
            </form>
        </div>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="Edit_item3_Pe()">编辑</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
    </div>
</div>
<div id="dlg_item3_pe" >
    <div class="QueryWrap">
        <ul>
            <%--<li><label>菜单组</label>--%>
            <%--<select class="easyui-combobox"  style="width:126px;">--%>

            <%--</select>   </li>--%>
            <li><label>用户名</label><label id="">&nbsp;</label></li>
            <li><label>描述</label><input  class="text"  type="text" ></li>
            <%--<li class="fr"><button>查询</button></li>--%>
        </ul>

    </div>
    <div id="tag_pitem3" style="height:250px">
        <div title="首选项" style="padding:10px">
            <form>
                <fieldset>
                    <legend>系统</legend>
                    <label>菜单组</label>
                    <select class="easyui-combobox"  style="width:126px;">

                    </select>
                </fieldset>
            </form>


        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/js/permissions/item3.js" />"></script>
<script type="text/javascript">
    $(function(){
        $('#item3_datagrid').datagrid({
//            url:'datagrid_data.json',
            columns:[[
                {field:'a',title:'ID',width:100,align:'center'},
                {field:'b',title:'控件名',width:100,align:'center'},
                {field:'c',title:'描述',width:100,align:'center'},
                {field:'d',title:'现有控件',width:100,align:'center'},
                {field:'e',title:'最后修改时间',width:100,align:'center'},
                {field:'f',title:'活动的',width:100,align:'center'}
            ]]
        });
    });
</script>




