<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	.QueryWrap li{padding:5px 0;}
	.datagrid-wrap{border:none;}
</style>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <table id="item1_datagrid"></table>
     <div id="tb_item1_pe">
         <div class="QueryWrap">
             <form>
                 <ul style="margin:5px;">                        
                    <li>
                   		<label>部门</label>
                        <select class="easyui-combobox" name="state" style="width:126px;"></select>  
                  	</li>
                  	<li>
                  	    <label>组</label>
                        <select class="easyui-combobox" name="state" style="width:126px;"></select>  
                  	</li>
                  	<li>
                  		<label>权限</label>
                        <select class="easyui-combobox" name="state" style="width:126px;"></select>  
                  	</li>
                  	<li>
                  		<label>姓名</label>
                      	<input id="id_new_site_name" class="text"  type="text" />
                  	</li>
                  	<li>
                  		<label>工号</label>
                      	<input id="id_new_site_workid" class="text"  type="text" />
                  	</li>
                  	<li>
                  		<a href="#" class="window_btn">查询</a>
                  	</li>
                 </ul>
             </form>
         </div>
         <a href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="new_user()">新增</a>
   	  	 <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit_user()">编辑</a>
         <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del_user()">删除</a>          
     </div>
     <!-- 点击新增按钮后的弹窗 -->
	<div id="id_dlg_new_user">
		<div class="QueryWrap">
			<form>
				<ul>
					<li><label>用户名</label><input id="id_user_new_site_name"
						style="width: 125px;" /></li>
					<li><label>工号</label><input id="id_user_new_name" class="text"
						type="text"></li>
					<li><label>所属组</label><input id="id_user_new_site_group"
						style="width: 125px;" /></li>
					<li><label>角色</label><input id="id_user_new_site_role"
						style="width: 125px;" /></li>
					<li><label>密码</label><input id="id_user_new_pwd" class="text"
						type="text"></li>
				</ul>
			</form>
		</div>
	</div>
<!-- 点击编辑按钮后的弹窗 -->
	<div id="id_dlg_edit_user" >
	    <div class="QueryWrap">
	        <form>
	            <ul>
	                <input id="id_user_edit_id" class="text"  type="hidden" >
	                <li><label>系统</label><input id="id_user_edit_site_name"  /></li>
	                <li><label>名称</label><input id="id_user_edit_name" class="text"  type="text" ></li>
	                <li><label>描述</label><input id="id_user_edit_description" class="text"  type="text" ></li>
	            </ul>
	        </form>
	    </div>
	</div>
	<!-- 点击删除按钮后的弹窗 -->
	<div id="confirm_user" >
	    <div class="QueryWrap">
	        <form>
	            <ul>
	                <li><h3>确定删除？</h3></li>
	            </ul>
	        </form>
	    </div>
	</div>
	<script type="text/javascript" src="<c:url value="/js/archives/user.js" />"></script>