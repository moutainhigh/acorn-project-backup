<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<html>
  <head>
    <title>配置SIMPLE触发器</title>
  </head>
  
  <body>
 
	<h1>配置SIMPLE触发器</h1>
	<h2>${className}</h2>
	<div>
		<form action="/task/createTrigger/${className}/SIMPLE" method="post">
			<table border="1">
				<tr>
					<td>触发器名称*：</td>
					<td><input id="triggerName" name="triggerName" type="text" style="width: 300px;"/></td>
					<td>必填</td>
				</tr>
				<tr>
					<td>任务名称*：</td>
					<td><input id="jobName" name="jobName" type="text" style="width: 300px;"/></td>
					<td>不能重复</td>
				</tr>
				<!-- 
				<tr>
					<td>延迟时间（秒）：</td>
					<td><input id="startDelay" name="startDelay" type="text" /></td>
				</tr>
				 -->
				<tr>
					<td>循环时间（秒）*：</td>
					<td><input id="loopDelay" name="loopDelay" type="text" style="width: 300px;"/></td>
					<td>必填</td>
				</tr>
				<tr>
					<td>运行次数：</td>
					<td><input id="cronExpression" name="cronExpression" type="text" style="width: 300px;"/></td>
					<td>不填为无限次</td>
				</tr>
				<tr>
					<td>描述：</td>
					<td><textarea id="description" name="description" style="width: 300px;"></textarea></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="center"><input type="submit" value="提交"/><input type="reset" value="重置"/></td>
					<td>&nbsp;</td>
				</tr>
			</table>
		</form>
	</div>

     
  </body>
</html>
