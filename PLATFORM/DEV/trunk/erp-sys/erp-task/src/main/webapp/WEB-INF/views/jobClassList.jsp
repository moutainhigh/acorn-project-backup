<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/static/common/taglibs.jsp"%>

<html>
  <head>
    <title>定时任务管理中心</title>
	<script type="text/javascript" src="/static/js/web/jobClassList.js"></script>
	<style type="text/css">
		#className{
			padding:3px 2px;
			border-bottom:1px solid #ccc;
			height: 24px;
			line-height: 24px;
		}
		.tr{
			padding:5px;
			text-align: left;
		}
		.tr > label{
			width: 100px;
			display: block;
			float: left;
			height: 24px;
			line-height: 24px;
			text-align: right;
		}
		.tr > input{
			width: 240px;
			height: 24px;
			line-height: 24px;
		}
		.tr > textarea{
			width: 240px;
			height: 80px;
			line-height: 24px;
		}
	</style>
  </head>
  
  <body>
 
	<table id="jobClassGrid"></table>
	
	<div id="configTriggerDlg" title="配置触发器" data-options="iconCls:'icon-save'" style="padding:5px;width:450px;height:500px;">  
		<form id="configTriggerForm" method="post">  
			<div id="className"></div> 
			<div class="tr">
				<label>触发器名称:</label>
				<input id="triggerName" name="triggerName" type="text" />
			</div>
			<div class="tr">
				<label>任务名称:</label>
				<input id="jobName" name="jobName" type="text" />
			</div>
			<div class="tr">
				<label>运行参数:</label>
				<input id="cronExpression" name="cronExpression" type="text" />
			</div>
			<div class="tr">
				<label>循环时间:</label>
				<input id="loopDelay" name="loopDelay" type="text" />
			</div>
			<div class="tr">
				<label>运行次数:</label>
				<input id="repeatCount" name="repeatCount" type="text" />
			</div>
			<div class="tr">
				<label>描述:</label>
				<textarea id="description" name="description" ></textarea>
			</div> 
			<div class="tr">
				<table id="parmGrid" style="width:auto;height:auto" data-options="iconCls:'icon-edit',singleSelect:true,idField:'id'" title="运行参数">  
				    <thead>  
				        <tr>  
				            <th data-options="field:'id',hidden:true">ID</th>  
				            <th data-options="field:'key',width:100,editor:'text'">参数名</th>
				            <th data-options="field:'va',width:200,editor:'text'">参数值</th> 
				        </tr>  
				    </thead>  
				</table>
			</div>
		</form>  
    </div>  

  </body>
</html>
