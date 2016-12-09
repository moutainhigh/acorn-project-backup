<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<html>
  <head>
    <title>SIMPLE详细</title>
  </head>
  
  <body>
  
	<br />
	<br />
	<h1>操作</h1>
	<table>
		<tr>
			<td>
				<form action="/task/removeTrigger" method="post">
					<input name="group" type="hidden" value="${trigger.group}"/>
					<input name="name" type="hidden" value="${trigger.name}"/>
					<input type="submit" value="删除"/>
				</form>
			</td>
			<td>
				<form action="/task/fefreshTrigger" method="post">
					<input name="group" type="hidden" value="${trigger.group}"/>
					<input name="name" type="hidden" value="${trigger.name}"/>
					<input type="submit" value="刷新"/>
				</form>
			</td>
			<td>
				<form action="/triggerList" method="post">
					<input type="submit" value="返回"/>
				</form>
			</td>
		</tr>
	</table>
	
	<br />
	<br />
	<h1>任务类</h1>
	<span>${jobClass}</span>
	
	<br />
	<br />
	<h1>描述</h1>
	<span>${description}</span>
	
	<br />
	<br />
	<h1>SIMPLE触发器状态</h1>
	<table border="1">
		<thead>
			<tr>
				<td>组名</td>
				<td>触发器</td>
				<td>任务名</td>
				<td>首次启动时间</td>
				<td>最后一次运行时间</td>
				<td>下一次运行时间</td>
				<td>成功次数</td>
				<td>异常次数</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${ trigger.group }</td>
				<td>${ trigger.name }</td>
				<td>${ trigger.jobName }</td>
				<td><fmt:formatDate value="${ trigger.startTime }" type="both" /></td>
				<td><fmt:formatDate value="${ trigger.previousFireTime }" type="both" /></td>
				<td><fmt:formatDate value="${ trigger.nextFireTime }" type="both" /></td>
				<td>${ successCount }</td>
				<td>${ failCount }</td>
			</tr>
		</tbody>
	</table>
	
	<br />
	<br />
	<h1>运行参数</h1>
	<table border="1">
		<thead>
			<tr>
				<td>KEY</td>
				<td>VALUE</td>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${parms}" var="item">
			<tr>
				<td>${item.key}</td>
				<td>${item.value}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<br />
	<br />
	<h1>异常信息</h1>
	<span>${ exception }</span>
	
</body>
</html>
