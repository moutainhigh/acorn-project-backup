<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<html>
  <head>
    <title>定时任务管理中心</title>
  </head>
  
  <body>
    欢迎访问定时任务服务器 <br>
    <a href="/triggerList">触发器列表</a><br>
    <a href="/jobClassList">配置定时任务</a><br>
    
	<br />
	<div style="color: red; font-size: 13px">
		开发步骤有三步：<br />
		1.编写继承SimpleJob的job(位于com.chinadrtv.erp.task.jobs内或子包内)。<br />
		2.在你的job上添加@Task注解。<br />
		3.在UI界面配置你的触发器。 
	</div>
  </body>
</html>
