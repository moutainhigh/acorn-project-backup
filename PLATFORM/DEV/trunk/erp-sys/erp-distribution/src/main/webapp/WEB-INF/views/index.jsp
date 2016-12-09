<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
</head>
<body>
	<script type="text/javascript" src="/static/js/datagrid-checkboxEx.js"></script>
	<script type="text/javascript" src="/static/js/datagrid-rowNumEx.js"></script>
	<script type="text/javascript" src="/static/js/json.min.js"></script>
	<script type="text/javascript" src="/static/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="/static/js/callbackAssign/callbackLogs.js"></script>
	<script type="text/javascript" src="/static/js/browser.js"></script>
	<link href="${ctx}/static/style/callbackAssign.css" rel="stylesheet" type="text/css" />
    <div class="loader"><span>请稍侯... </span><span id="loader-msg"></span></div>
    <div class="loader-mask"></div>
    <div id="tabs" class="easyui-tabs mt5" data-options="tabPosition:'left',fit:true" style="height: auto;">
		<div title="话务分配" style="padding: 10px;">
			<div class="form-tabs">
				<div class="panel_con">
					<form id="queryAvaliableForm" name="queryAvaliableForm" action="">
						<table width="90%" style="margin: 0 auto;">
							<tr>
								<td><label style="width: 80px; display: inline-block">分配类型</label> 
									<select id="assignType" style="width: 150px" class="easyui-combobox" required="true" data-options="valueField: 'id',  textField: 'text'">
										<option value="0">=====请选择=====</option>
                                        <option value="1">VM</option>
                                        <option value="2">放弃</option>
                                        <option value="3">接通</option>
                                        <option value="5">SNATCH IN</option>
                                       <%--
                                        <option value="11">IVR(井星)</option>
                                        <option value="12">放弃(井星)</option>
                                        <option value="13">接通(井星)</option>
                                        <option value="4">虚拟话务</option>
                                        <option value="15">Callback(井星)</option>
                                       --%>
								</select></td>
								<td>
									<div id="show_timeLength">
										<label style="width: 80px; display: inline-block">通话时长</label> 
										<select id="timeLength" style="width: 150px" class="easyui-combobox ml10" data-options="valueField:'id',textField:'text'" required="true">
											<option value="">=====请选择=====</option>
											<option value="A">A [0~20s]</option>
											<option value="B">B (20-180)</option>
											<option value="C">C [180,∞)</option>
										</select>
									</div>
									<div id="show_priority" style="display: none">
										<label style="width: 80px; display: inline-block">优先级</label> 
										<select id="v_priority" name="v_priority" style="width: 150px" class=" ml10">
											<option value="1">非常重要</option>
											<option value="2">重要</option>
											<option value="3">一般</option>
                                            <option value="4">售后</option>
                                            <option value="5">骚扰</option>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<td><label style="width: 80px; display: inline-block">呼入时间</label> 
									<input id="incomingLowDate" name="incomingLowDate" value="${incomingLowDate}" class="easyui-datetimebox" required="true" style="width: 150px" />&nbsp;~&nbsp; 
									<input id="incomingHighDate" name="incomingHighDate" value="${incomingHighDate}" class="easyui-datetimebox" required="true" style="width: 150px" /></td>
								<td><label style="width: 80px; display: inline-block;">ACD组</label> 
								<select id="acdGroup" name="acdGroup" style="width: 150px" class="easyui-combobox">
								</select></td>
							</tr>
							<tr>
								<td><label style="width: 80px; display: inline-block">被叫号码</label> 
									<input type="text" id="dnis" name="dnis" style="width: 145px;" /></td>
								<td><label style="width: 80px; display: inline-block">数据状态</label> 
									<select id="allocatedNumbers" name="allocatedNumbers" style="width: 150px" class="easyui-combobox">
										<option selected="" value="0">未分配</option>
										<option value="1">已分配1次</option>
								</select></td>
							</tr>
							<tr>
								<td><div class="red" id="deptAvailableQtyLable" style="display: none;">
									<label style="display: inline-block">可分配到部门数量</label>
                                   <span id="deptAvailableQty">0</span></div></td>
								<td>
									<div class="submitBtns" style="margin-left: -50px">
										<a href="javascript:void(0)" onclick="callback.queryAvaliableQty()">查询</a> 
										<a class="ml10" href="javascript:void(0)">清空</a> 
										<a class="ml10" style="padding: 2px 10px;" href="javascript:$('#fileImport').click()">导入座席等级</a> <input id="fileImport" class="ml10" style="display: none;" type="file" name="file" accept="application/vnd.ms-excel" onchange="javascript:ajaxFileUpload()" />
										<a class="ml10" href="javascript:void(0)" id="assignToDept" onclick="callback.assignToDept()" style="display: none;">按比例分配</a> 
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div class="mt10 q_r">
					可分配数据量&nbsp;&nbsp;<span id="v_allotNum">0</span>
                    <font color="#ff4500"><apan style="padding:0 0 0 200px;  " id="v_msg"></apan></font>
                    <b class="fr">剩余数据量&nbsp;&nbsp;<span id="v_overplusNum">0</span></b>
                </div>
				<div class="sift">
					<label style="width: 70px; display: inline-block">选择工作组</label><input id="v_selectGroup" type="text" style="width: 145px" />
                    <span id="totalAssignQtyBtn"><label style="width: 70px; display: inline-block">总分配数量</label><input id="tbTotalNum" type="text" style="width: 145px" /></span>
                    <label style="display: none;margin-right: 10px"><input id="v_allotGroup" type="checkbox" onclick="vallotGroup();" value="checkbox" style="vertical-align: top">平均分配模式</label> <span></span>
                    <span id="distrBtn" class="submitBtns" style="display: none"><a href="javascript:void(0)" id="groupAssignBtn" style="padding: 2px 18px;" onclick="callback.assignToGroup()">分配</a></span>
				</div>
				<div class="workgroupT">
					<table id="workgroupT" ></table>
				</div>
				<div id="agentPanel">
					<div class="sift mt10">
						<div class="ti">
							选择<br>座席<div class="omniNum" >(<span id="omniNum">0</span>)</div>
						</div>
						<p>
							<label style="width: 60px; display: inline-block">时间段</label>
	                        <input id="g_beginDate" class="easyui-datetimebox" style="width: 150px" />&nbsp;~&nbsp; <input id="g_endDate" class="easyui-datetimebox" style="width: 150px" />
	                        <label style="display: inline-block">
	                            <input id="g_todayRange" value="2" type="radio" name="queryType" style="vertical-align: top">
	                            只显示当天有进线数座席
	                        </label>
	                        <label style="display: inline-block">
	                            <input type="radio" value="1" checked="checked" name="queryType" style="vertical-align: top">
	                            只显示选择时间段有进线数座席
	                        </label>
	                        <label style="display: inline-block">
	                            <input id="g_allRange" type="radio" value="3" checked="checked" name="queryType" style="vertical-align: top">
	                            显示全部坐席
	                        </label>
						</p>
						<p>
							<label style="width: 60px; display: inline-block">座席等级</label> 
							<select id="g_level" style="width: 150px;">
								<option value="A">A</option>
								<option value="B">B</option>
								<option value="C">C</option>
								<option value="D">D</option>
								<option value="-1">其它</option>
							</select> <label style="width: 60px; display: inline-block" class="ml10">座席工号</label><input id="g_agentId" class="ml10" type="text" style="width: 91px" /> <label style="display: inline-block"> <input id="g_isAverage" style="vertical-align: top" type="checkbox" value="checkbox">平均分配模式
							</label> <span class="submitBtns" style="margin-left: 20px"><a style="padding: 2px 18px" href="javascript:void(0)" onclick="callback.queryAvaliableAgent();">查询</a> <a href="javascript:void(0)" style="padding: 2px 18px" onclick="callback.validateAssignment();">计算校验</a> <a href="javascript:void(0)" id="assignOperate" style="padding: 2px 18px; display: none;" onclick="callback.assignToAgent();">分配</a> </span>
						</p>
					</div>
					<div class="agentTb">
						<table id="agentTb"></table>
					</div>
				</div>
			</div>
			<div id="loading" class="" style="display: none;">
				<div style='position: absolute; width: 100%; background: rgba(255, 255, 255, 0.5); height: 100%; text-align: center; top: 0; font-size: 12px; margin: 0; padding: 0;'>
					<img alt='load' src='../static/images/loadingAnimation.gif' align='absMiddle' bold='0' />
				</div>
			</div>
		</div>
		<div title="分配历史查询" style="padding: 10px">
			<div class="form-tabs">
				<div class="panel_con">
					<table width="90%" style="margin: 0 auto;">
						<tr>
							<td><label style="width: 80px; display: inline-block">分配类型</label> <select id="log-calltype" style="width: 150px" class="easyui-combobox" data-options="valueField: 'id',  textField: 'text'">
									<option value="0">=====请选择=====</option>
									<option value="1">VM</option>
                                    <option value="2">放弃</option>
                                    <option value="3">接通</option>
                                    <option value="5">SNATCH IN</option>
                                <%--
                                <option value="11">IVR(井星)</option>
                                <option value="12">放弃(井星)</option>
                                <option value="13">接通(井星)</option>
                                <option value="4">虚拟话务</option>
								<option value="15">Callback(井星)</option>
								--%>
							</select></td>
							<td>
								<div id="show_timeLength2">
									<label style="width: 80px; display: inline-block">通话时长</label> <select id="log-callduration" style="width: 150px" class="easyui-combobox ml10">
										<option value="">=====请选择=====</option>
										<option value="A">A [0~20s]</option>
										<option value="B">B (20-180)</option>
										<option value="C">C [180,∞)</option>
									</select>
								</div>
								<div id="show_priority2" style="display: none">
									<label style="width: 80px; display: inline-block">优先级</label>
                                    <select id="log-priority" style="width: 150px" class="easyui-combobox ml10">
										<option value="1">非常重要</option>
										<option value="2">重要</option>
										<option value="3">一般</option>
                                        <option value="4">售后</option>
                                        <option value="5">骚扰</option>
									</select>
								</div>
							</td>
						</tr>
						<tr>
							<%--<td><label style="width:80px;display:inline-block">呼入时间</label></td>--%>
							<td><label style="width: 80px; display: inline-block">分配时间</label> <input id="log-startdate" value="${incomingLowDate}" class="easyui-datetimebox" data-options="required:false" style="width: 150px" />&nbsp;~&nbsp; <input id="log-enddate" value="${incomingHighDate}" class="easyui-datetimebox" data-options="required:false" style="width: 150px" /></td>
							<%--<td><label style="width:80px;display:inline-block">ACD组</label></td>--%>
							<td><label style="width: 80px; display: inline-block;">ACD组</label> <select id="log-acdGroup" style="width: 150px" class="easyui-combobox">
							</select></td>
						</tr>
						<tr>
							<%--<td><label style="width:80px;display:inline-block">被叫号码</label></td>--%>
							<td><label style="width: 80px; display: inline-block">被叫号码</label> <input id="log-dnis" type="text" style="width: 145px" /></td>
							<%--<td><label style="width:80px;display:inline-block">数据状态</label>--%>
							<%--</td>--%>
							<td><label style="width: 80px; display: inline-block">数据状态</label> <select id="log-allocatedNumber" style="width: 150px" class="easyui-combobox">
									<option selected="selected" value="1">已分配1次</option>
									<%--<option value="2">已分配2次</option>--%>
							</select></td>
						</tr>
						<tr>
							<%--<td><label style="width:80px;display:inline-block">被叫号码</label></td>--%>
							<td><label style="width: 80px; display: inline-block">座席工号</label> <input id="log-agentuser" type="text" style="width: 145px" /></td>
							<%--<td><label style="width:80px;display:inline-block">数据状态</label>--%>
							<%--</td>--%>
							<td><label style="width: 80px; display: inline-block">分配批次号</label> <input id="log-batchid" type="text" style="width: 145px" /></td>
						</tr>
						<tr>
							<td></td>
							<td>
								<div class="submitBtns" style="margin-left: 53px">
									<a id="lnk-callback-log-submit" href="javascript:void(0)">查询</a> <a id="lnk-callback-log-reset" class="ml10" href="javascript:void(0)">清空</a>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="mt10 q_r">
					数据量&nbsp;&nbsp;<span id="log_allotNum">-1</span>
				</div>
				<div class="sift">
					<span class="submitBtns"><a id="lnk-export-log" href="javascript:void(0)">导出数据</a> </span>
				</div>
				<table id="callback-log-grid" title="" style="height: 200px">
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
    	var curDate = '${curDate}';
    	var isIb = eval('${isIb}');
    	var isSupervisor = eval('${isSupervisor}');
    	var isDepartmentManager = eval('${isDepartmentManager}');
    	var deptAssignRight = eval('${deptAssignRight}');
    </script>
    <script type="text/javascript" src="/static/js/plugins/jquery.ajaxQueue.min.js"></script>
	<script type="text/javascript" src="/static/js/callbackAssign/callbackAssign.js"></script>
	<script type="text/javascript" src="/static/js/callbackAssign/callbackAssign.conn.js"></script>
</body>
</html>