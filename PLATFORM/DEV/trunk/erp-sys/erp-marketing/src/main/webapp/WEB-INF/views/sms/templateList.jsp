<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.chinadrtv.erp.marketing.constants.SmsConstant" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String curtime = sdf.format(new Date());
Random random = new Random();
int rnd = random.nextInt();
%>
<style type="text/css">
<!--
.none{
	display: none;
}
.select{
	width: 150px;
}
.spacing_tb td{
	padding: 3px;
	font-size:12px;
}
-->
</style>

<div id="condition">
	<span>
		<label>短信编码： </label>
		<input type="text" name="code" class="input-text w150" id="code" />
		<label>短信名称：  </label>
		<input type="text" name="name" class="input-text w150" id="name"/>
	</span>
	
	<span class="btnBar" style="text-align: left;margin-left:20px;">
	    <a  class="Btn" id="queryBtn" >检索</a> 
		<a  class="Btn" id="clearBtn" > &nbsp;&nbsp;清空&nbsp;&nbsp;  </a>
	 	
	</span>
</div>

<div style="margin:0 10px">
	<div id="dataList"  cellspacing="0" cellpadding="0" data-options=''></div>
</div>



<div id="addEditWin"  style="padding:5px;">

	<form id="templateForm" action="templateSave" method="post">
		<input type="hidden" name="id" class="id">
		<input type="hidden" name="crtuid" class="crtuid">
		
		<table width="100%" class="spacing_tb">
			<tr>
				<td align="left">短信名称：</td>
				<td>
					<input type="text" class="easyui-validatebox name input-text" style="width:150px;" maxlength="100" name="name" data-options="required:true"/>
				</td>
				<td align="left" style="width:90px;">状态：</td>
				<td>
					<select name="status"  id="status" style="width:150px;" data-options="required:true">
						<option value="1">可用</option>
						<option value="0">停用</option>					
					</select>
				</td>				
			</tr>
			<tr>
			<td align="left">模板主题：</td>
				<td>
					<select name="themeTemplate"   id="themeTemplate" style="width:150px;" data-options="required:true">
						<option value="0">请选择</option>
						<c:forEach items="${baseList}" var="base">  					
							<option value="${base.paraValue}">${base.paraValue}</option>
						</c:forEach>				
					</select>
				</td>				
			</tr>
						<tr>
			<td align="left">执行部门：</td>
				<td>
					 <span class="inputSpan">
					 <input type="hidden" id = "exeDepartments"/>
					<input class="easyui-combobox"  style="width:150px;"
						id = "exeDepartment" name="exeDepartment"
						data-options="
								url:'${ctx }/dict/getDepartment',
								idField:'id',
								textField:'dsc',
								multiple:false,
								valueField:'tmpId'
						">
					</span>
				</td>				
			</tr>				
			<tr>
				<td align="left" valign="top" style="vertical-align: middle;">短信内容：</td>
				<td colspan="3" valign="top" style="vertical-align: top;">
					<div  class="content_hover" style="width:418px; height:20px;">已录入0个字</div>
				</td>
			</tr>
			<tr>
				<td align="left" valign="top" style="vertical-align: middle;"></td>
				<td colspan="3" align="left" valign="top" style="vertical-align: top;">
					<input type="hidden" name="code" id="code"/>
					<textarea  name="content"  style="width:430px;height:180px;" class="easyui-validatebox content area-text" 
						data-options="required:true, validType:'maxLength[300]'" ></textarea>
				</td>
			</tr>
			<tr>
				<td align="left" valign="top" style="width:90px;"> 可用参数说明：</td>
				<td colspan="3" align="left" valign="top" style="vertical-align: top;">
					<ul style="list-style:decimal outside none;padding:0;margin-left:20px">
					<li>静态参数在发送短信时自行配置，不能超过5个。方式如下：{400}</li>
					<li>动态参数可在发送短信时根据客户编号直接从客户资料中取出，现可用：{user.name}用户姓名，{user.gender}用户性别</li>
					<li>优惠券的动态参数配置现可用：有效期开始时间{user.startTime},结束日期为{user.endTime},金额{user.money}优惠券号{user.couponId}</li>
					</ul>
				</td>
				
			</tr>
			<tr>
				<td colspan="4" align="center">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveTemplate()">保存</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeSaveWin()">关闭</a>
				</td>
			</tr>
		</table>
		<input type="hidden" name="crttime" id="crttime" />
	</form>
</div>

<script type="text/javascript" src="${ctx}/static/scripts/sms/smsTemplate.js?=<%=rnd%>"></script>
