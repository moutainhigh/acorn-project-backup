<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
	.QueryWrap li span.tips{color:#575765;margin-left:10px;}
	.datagrid-wrap{border:none;}
</style>
      <table id="item1_datagrid"></table>
      <div id="tb_item1_pe">
          <div class="QueryWrap">
              <form>
                  <ul style="margin:5px;">                        
                      <li>
                      	  <label>部门</label>
                          <select class="easyui-combobox" name="state" style="width:126px;"></select>  
                   	  </li>
                   	  <li><a href="#" class="window_btn">查询</a></li>
                  </ul>
              </form>
          </div>
          <a href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="new_group()">新增</a>
    	  <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit_group()">编辑</a>
	      <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del_group()">删除</a>
	      <!-- hsh事件和图标 -->
    	 <a href="#" class="easyui-linkbutton" iconCls="icon-addUser" plain="true" onclick="add_group()">增加角色</a>
    	 <a href="#" class="easyui-linkbutton" iconCls="icon-removeUser" plain="true" onclick="remove_group()">删除角色</a>
      </div>
      <div id="id_dlg_new_group" >
	    <div class="QueryWrap">
	        <form>
	            <ul>
	                <li><label>组名称</label><input id="id_group_new_name" class="text"  type="text" ></li>
	                <li><label>组类型</label><input id="id_group_new_kind" name="kind" style="width:125px;"/></li>
	                <li><label>区号</label><input id="id_group_new_description" class="text"  type="text" ></li>
	                <li><label>所属部门</label><input id="id_group_new_group" style="width:125px;"/></li>
	            </ul>            
	        </form>
	    </div>
	</div>
	<div id="id_dlg_edit_group" >
	    <div class="QueryWrap">
	        <form>
	            <ul>
	                <input id="id_group_edit_id" class="text"  type="hidden" >
	                <li><label>系统</label><input id="id_group_edit_site_name"   ></li>
	                <li><label>名称</label><input id="id_group_edit_name" class="text"  type="text" ></li>
	                <li><label>描述</label><input id="id_group_edit_description" class="text"  type="text" ></li>
	            </ul>
	        </form>
	    </div>
	</div>	
	<div id="confirm_group" >
	    <div class="QueryWrap">
	        <form>
	            <ul>
	                <li><h3>确定删除？</h3></li>
	            </ul>
	        </form>
	    </div>
	</div>
	<div id="id_dlg_add_group" >
	    <div class="QueryWrap">
	        <form>
	            <ul>               
	            	<li><span class="tips">为该组当前所有员工增加权限<span></span></li>
	                <li><label>角色名</label><input id="id_group_new_name02" style="width:120px;"/></li>
	            </ul>            
	        </form>
	    </div>
	</div>
	<div id="id_dlg_remove_group" >
	    <div class="QueryWrap">
	        <form>
	            <ul>               
	            	<li><span class="tips">为该组当前所有员工删除权限</span></li>
	                <li><label>角色名</label><input id="id_group_new_name03" style="width:120px;"/></li>
	            </ul>            
	        </form>
	    </div>
	</div>
	<script type="text/javascript" src="<c:url value="/js/archives/group.js" />"></script>