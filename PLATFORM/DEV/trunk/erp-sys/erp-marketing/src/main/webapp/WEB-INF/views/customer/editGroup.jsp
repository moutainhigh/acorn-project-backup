<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>

</head>
<body>

<!-- 数据分配 start -->

		<div class="easyui-layout baseLayout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<form action="" id="customerGroupForm" method="post">
				<!-- window content -->
				<input type="hidden" id="ext2" name="ext2"  class="easyui-validatebox input-text w150"  />
				<c:if test="${group.groupCode!=null }">
				<input type="hidden" name="groupCode" value="${group.groupCode}">
				</c:if>
				<div class="clearfix">
				<div class="fl_w50">
					<label>客户群名称:</label>
					 <span class="inputSpan">
					<input class="easyui-validatebox input-text w150" name="groupName" value="${group.groupName }" data-options="required:true,validType:'length[1,50]'">
					</span>
					
				</div>
				<div class="fl_w50">
					<label>客户群编码:</label> 
					<span class="inputSpan">
					<input class="easyui-validatebox  input-text w150"  readonly="readonly" type="text" name="groupCodeTmp" value="<c:choose><c:when test="${group.groupCodeTmp==null }">系统自动生成</c:when><c:otherwise>${group.groupCodeTmp}</c:otherwise></c:choose>" data-options="">
					</span>
					</div>
					</div>
				<div>
		    	 	<label  for="lastMdyTimeView" style="vertical-align: top;">描述：</label> 
		    	 	<textarea rows="2" cols="40" class="area-text" style="width:452px" name="remark" id="descIdView">${group.remark}</textarea>
				</div>
<div>
					<label>客户群状态:</label> 
					
					<label class="ra">
					<input type="radio"  name="status" value="Y" <c:if test="${group.status=='Y' }">checked="checked"</c:if> <c:if test="${group.status==null }">checked="checked"</c:if>/>可用
					</label>
					<label class="ra">
					<input type="radio"  name="status" value="N" <c:if test="${group.status=='N' }">checked="checked"</c:if>/>停用
					</label>
					<label><input  id="ext1" onclick="changeSetItem()"  type="checkbox" name="ext1" <c:if test="${group.ext1=='on' }">checked="checked"</c:if> <c:if test="${flag=='0' }">checked="checked"</c:if>/>用于短信</label>
					
					<label>客户类型:</label> 
					 <select class="easyui-combobox" name="type" value="<c:choose><c:when test="${group.type==null }">1</c:when><c:otherwise>${group.type}</c:otherwise></c:choose>" style="width: 70px" data-options="panelHeight:'auto'">
							<option value="1" <c:if test="${group.type=='1'}">selected="selected"</c:if>>正式客户</option>
							<option value="2" <c:if test="${group.type=='2'}">selected="selected"</c:if>>潜在客户</option>
						</select>
										
				</div>
				<div class="clearfix" id="setItem" style="<c:if test="${group.ext1=='on' }">display:none</c:if> <c:if test="${flag=='0' }">display:none</c:if>">
			<div class="fl_w50" >
					<label>业务编号:</label> 
					 <span class="inputSpan">
					<input class="easyui-combobox" style="width:150px;"
						name="jobId"
						value="${group.jobId }"
						data-options="
								url:'${ctx }/dict/getJobs',
								valueField:'jobId',
								textField:'jobId',
								multiple:false,
								<c:if test="${group.jobId!=null }">
								editable:false,
								disabled:true,
								readonly:true,
								</c:if>
								onChange:function(newValue,oldValue){
									if(newValue!=''){
										$('#queneId').combobox({
											url:'${ctx }/dict/getQueues?jobId='+newValue,
											valueField:'queueId',
											textField:'name',
											multiple:false,
											required:true
											}).combobox('clear');
									}
								}
						">
					</span>
					</div>
					<div class="fl_w50">
				<label>数据分配方式:</label> 
					 <span class="inputSpan">
					<input class="easyui-combobox"  style="width:150px;"
						name="assignType"
						value="${group.assignType }"
						data-options="
								url:'${ctx }/dict/getBaseConfig?code=DATAASSIGN',
								valueField:'paraValue',
								textField:'paraName',
								multiple:false
						">
					</span>
					</div>
					<div class="fl_w50">
					<label>队列:</label> 
					 <span class="inputSpan">
						<input class="easyui-combobox"  style="width:150px;"
						id="queneId"
						name="queneId"
						value="${group.queneId}"
						data-options="
								url:'${ctx }/dict/getQueues?jobId=${group.jobId }',
								valueField:'queueId',
								textField:'name',
								multiple:false
						">
					</span>
				</div>
				<div class="fl_w50">
				<label>指定可执行部门:</label> 
					 <span class="inputSpan">
					 <input type="hidden" name="department"	id="departments"/>					 
					<input class="easyui-combobox"  style="width:150px;"
						id = "department"
						data-options="
								url:'${ctx }/dict/getDepartment',
								idField:'id',
								textField:'dsc',
								multiple:true,
								valueField:'tmpId',
								onLoadSuccess:function(){
									var values = '${group.department}'
									if (values!='') {
										$('#department').combobox('setValues',values.split(','))									
									}
								}
						">
					</span>
				</div>
				</div>
				<div class="clearfix">
					<label >有效时间:</label>
					<span class="dateSpan" >
					<input  class="easyui-datetimebox"   id="startDate" readonly="readonly" type="text"  style="width:150px;margin-left:5px" name="validStartDate" value="${group.validStartDate }" data-options="required:true">
					  至<input class="easyui-datetimebox" id="endDate"  readonly="readonly" type="text"  style="width:150px" name="validEndDate" value="${group.validEndDate }" data-options="required:true">
					</span>
				</div>
				
					<div>
					<label>剔除周期:</label> 
					 <span class="inputSpan">
					<input class="easyui-numberspinner" value="<c:choose><c:when test="${group.rejectCycle==null }">20</c:when><c:otherwise>${group.rejectCycle}</c:otherwise></c:choose>" name="rejectCycle"  readonly="readonly"  data-options="min:1,max:30,editable:false" style="width: 50px"></input>天
					</span>
					<label>批次有效期:</label> 
					 <span class="inputSpan">
					<input class="easyui-numberspinner" value="<c:choose><c:when test="${group.batchStatus==null }">7</c:when><c:otherwise>${group.batchStatus}</c:otherwise></c:choose>" name="batchStatus"  readonly="readonly"  data-options="min:1,max:30,editable:false" style="width: 50px"></input>天
					</span>	
					
				</div>
				<div class="clearfix">
				<div class="fl_w50">
					<label>刷新周期:</label> 
					 <span class="inputSpan">
					<input class="easyui-combobox" style="width:150px;"
						name="executeCycel" 
						<c:choose><c:when test="${group.groupCode==null }">value="0"</c:when><c:otherwise>value="${group.executeCycel }"</c:otherwise></c:choose>
						data-options="
								url:'${ctx }/dict/getBaseConfig?code=CYCLE',
								valueField:'paraValue',
								textField:'paraName',
								multiple:false,
								editable:false,
								panelHeight:'auto',
								onChange:function(newValue,oldValue){
									if(newValue!=''){
										changeCycleType(newValue);
									}
								}
						">
					</span>
					</div>
					<div class="clearfix" id="recoverId" <c:choose><c:when test="${group.executeCycel=='0' || group.executeCycel=='5' ||group.executeCycel==null}">style="display: none" </c:when></c:choose>>
					<label>当天数据回收:
					 
					<input type="checkbox" name="isRecover" <c:choose><c:when test="${group.isRecover=='1' || group.isRecover==null }">checked</c:when></c:choose>/>
					</label> 
					</div>
				</div>
				<div >
					<div  id="timeStrFieldSet"  style="display:<c:choose><c:when test="${jobCronSet.frequency=='5' }"></c:when><c:otherwise>none</c:otherwise></c:choose>;width: 420px;padding-left:130px">
					<fieldset>
					<legend>设定时间:</legend>
						<span id="timeStrSpan">
						<input class="easyui-datetimebox"  readonly="readonly" id="timeStr" name="timeStr" data-options="" value="<c:choose><c:when test="${jobCronSet.timeStr== null }"></c:when><c:otherwise>${jobCronSet.timeStr}</c:otherwise></c:choose>" style="width:140px">
						</span>
						</fieldset>
					</div>
					
					<div  id="timeRangeFieldSet" style="display:<c:choose><c:when test="${jobCronSet.frequency== null ||jobCronSet.frequency=='5'||jobCronSet.frequency=='0'}">none</c:when><c:otherwise></c:otherwise></c:choose>;width: 400px;padding-left:130px;margin-bottom:10px;">
					<fieldset>
				<legend>时间范围:</legend>
						 <span>
						开始时间<input id="stime"  class="easyui-datetimebox"  readonly="readonly" type="text"  name="dateOfStart" value="<c:choose><c:when test="${jobCronSet.dateOfStart!=null}">${jobCronSet.dateOfStart}</c:when></c:choose>" data-options="" style="width:140px">
						结束时间<input id="etime" class="easyui-datetimebox"  readonly="readonly" type="text"  name="endDay" value="<c:choose><c:when test="${jobCronSet.endDay!=null}">${jobCronSet.endDay}</c:when></c:choose>" data-options="" style="width:140px">
						</span>
						</fieldset>
					</div>
					
					<div  id="hourFieldSet" style="display:<c:choose><c:when test="${jobCronSet.frequency=='6' }"></c:when><c:otherwise>none</c:otherwise></c:choose>;width: 420px;padding-left:130px">
					<fieldset>
				<legend>重复间隔:</legend>
						 <span >
						 每<input class="easyui-numberspinner" value="<c:choose><c:when test="${jobCronSet.everyHourNum==null }">1</c:when><c:otherwise>${jobCronSet.everyHourNum}</c:otherwise></c:choose>" name="everyHourNum"  readonly="readonly"  data-options="min:1,max:30,editable:false" style="width: 50px"></input>小时
						</span>
						</fieldset>
					</div>
					
					<div  id="dayFieldSet" style="display:<c:choose><c:when test="${jobCronSet.frequency=='1' }"></c:when><c:otherwise>none</c:otherwise></c:choose>;width: 420px;padding-left:130px">
					<fieldset>
				<legend>重复间隔:</legend>
						 <span >
						 每<input class="easyui-numberspinner" value="<c:choose><c:when test="${jobCronSet.everyDayNum==null }">1</c:when><c:otherwise>${jobCronSet.everyDayNum}</c:otherwise></c:choose>" name="everyDayNum"  readonly="readonly"  data-options="min:1,max:30,editable:false" style="width: 50px"></input>天
						</span>
						</fieldset>
					</div>
					
					<div  id="weekFieldSet"  style="display:<c:choose><c:when test="${jobCronSet.frequency=='2' }"></c:when><c:otherwise>none</c:otherwise></c:choose>;width: 420px;padding-left:130px">
					<fieldset>
					<legend>重复间隔:</legend>
						 <span>
						 每<input class="easyui-numberspinner" name="weekNum" value="<c:choose><c:when test="${jobCronSet.weekNum==null }">1</c:when><c:otherwise>${jobCronSet.weekNum}</c:otherwise></c:choose>"  readonly="readonly"  data-options="min:1,max:10,editable:false" style="width: 50px"></input>周
						 <input type="checkbox" name="sunday" <c:choose><c:when test="${jobCronSet.sunday }">checked</c:when></c:choose>>日
		 				 <input type="checkbox" name="monday" <c:choose><c:when test="${jobCronSet.monday }">checked</c:when></c:choose>>一
						 <input type="checkbox" name="tuesday" <c:choose><c:when test="${jobCronSet.tuesday }">checked</c:when></c:choose>>二
						 <input type="checkbox" name="wednesday" <c:choose><c:when test="${jobCronSet.wednesday }">checked</c:when></c:choose>>三
						 <input type="checkbox" name="thursday" <c:choose><c:when test="${jobCronSet.thursday }">checked</c:when></c:choose>>四
						 <input type="checkbox" name="friday" <c:choose><c:when test="${jobCronSet.friday }">checked</c:when></c:choose>>五
						 <input type="checkbox" name="saturday" <c:choose><c:when test="${jobCronSet.saturday }">checked</c:when></c:choose>>六
						</span>
					</fieldset>
					</div>
					
					<div  id="monthFieldSet" style="display:<c:choose><c:when test="${jobCronSet.frequency=='3' }"></c:when><c:otherwise>none</c:otherwise></c:choose>;width: 420px;padding-left:130px">
					<fieldset>
				<legend>重复间隔:</legend>
						 <div  style="height:26px;">
						<input type="radio"  name="everyMonth" value="1" <c:choose><c:when test="${jobCronSet.everyMonth=='1' || jobCronSet.everyMonth==null }">checked</c:when></c:choose>/>
						每<input  class="easyui-numberspinner" name="everyMonthNum" value="<c:choose><c:when test="${jobCronSet.everyMonthNum==null }">1</c:when><c:otherwise>${jobCronSet.everyMonthNum}</c:otherwise></c:choose>" readonly="readonly"  data-options="min:1,max:12,editable:false" style="width: 50px"></input>月的第
						<input  class="easyui-numberspinner" name="everyMonthNumDay" value="<c:choose><c:when test="${jobCronSet.everyMonthNumDay==null }">1</c:when><c:otherwise>${jobCronSet.everyMonthNumDay}</c:otherwise></c:choose>" readonly="readonly"  data-options="min:1,max:31,editable:false" style="width: 50px"></input>天
						</div>
						
						 <span>
						<input type="radio"  name="everyMonth" value="2" <c:choose><c:when test="${jobCronSet.everyMonth=='2'}">checked</c:when></c:choose>/>
						每<input  class="easyui-numberspinner" name="everyMonthNum2" value="<c:choose><c:when test="${jobCronSet.everyMonthNum2==null }">1</c:when><c:otherwise>${jobCronSet.everyMonthNum2}</c:otherwise></c:choose>" readonly="readonly"  data-options="min:1,max:12,editable:false" style="width:50px;"></input>月的
						 <select class="easyui-combobox" name="theDay" value="<c:choose><c:when test="${jobCronSet.theDay==null }">1</c:when><c:otherwise>${jobCronSet.theDay}</c:otherwise></c:choose>" style="width: 70px" data-options="">
							<option value="1" <c:if test="${jobCronSet.theDay=='1'}">selected="selected"</c:if>>第一个</option>
							<option value="2" <c:if test="${jobCronSet.theDay=='2'}">selected="selected"</c:if>>第二个</option>
							<option value="3" <c:if test="${jobCronSet.theDay=='3'}">selected="selected"</c:if>>第三个</option>
							<option value="4" <c:if test="${jobCronSet.theDay=='4'}">selected="selected"</c:if>>第四个</option>
							<option value="5" <c:if test="${jobCronSet.theDay=='5'}">selected="selected"</c:if>>最后一个</option>
							</select>
							<select  class="easyui-combobox" name="theWeekday" value="<c:choose><c:when test="${jobCronSet.theWeekday==null }">1</c:when><c:otherwise>${jobCronSet.theWeekday}</c:otherwise></c:choose>" style="width: 70px" data-options="">
							<option value="1" <c:if test="${jobCronSet.theWeekday=='1'}">selected="selected"</c:if>>星期日</option>
							<option value="2" <c:if test="${jobCronSet.theWeekday=='2'}">selected="selected"</c:if>>星期一</option>
							<option value="3" <c:if test="${jobCronSet.theWeekday=='3'}">selected="selected"</c:if>>星期二</option>
							<option value="4" <c:if test="${jobCronSet.theWeekday=='4'}">selected="selected"</c:if>>星期三</option>
							<option value="5" <c:if test="${jobCronSet.theWeekday=='5'}">selected="selected"</c:if>>星期四</option>
							<option value="6" <c:if test="${jobCronSet.theWeekday=='6'}">selected="selected"</c:if>>星期五</option>
							<option value="7" <c:if test="${jobCronSet.theWeekday=='7'}">selected="selected"</c:if>>星期六</option>
							</select>
						</span>
						</fieldset>
					</div>
					
					<div  id="yearFieldSet" style="display:<c:choose><c:when test="${jobCronSet.frequency=='4' }"></c:when><c:otherwise>none</c:otherwise></c:choose>;width: 420px;padding-left:130px">
					<fieldset>
				<legend>重复间隔:</legend>
				<span>
						<input type="radio"  name="everyYear" value="1" <c:choose><c:when test="${jobCronSet.everyYear=='1' || jobCronSet.everyYear==null }">checked</c:when></c:choose>/>
						每年<select class="easyui-combobox" name="theMonthOfYear" value="<c:choose><c:when test="${jobCronSet.theMonthOfYear==null }">1</c:when><c:otherwise>${jobCronSet.theMonthOfYear}</c:otherwise></c:choose>"  style="width: 70px" data-options="">
							<option value="1" <c:if test="${jobCronSet.theMonthOfYear=='1'}">selected="selected"</c:if>>一月</option>
							<option value="2" <c:if test="${jobCronSet.theMonthOfYear=='2'}">selected="selected"</c:if>>二月</option>
							<option value="3" <c:if test="${jobCronSet.theMonthOfYear=='3'}">selected="selected"</c:if>>三月</option>
							<option value="4" <c:if test="${jobCronSet.theMonthOfYear=='4'}">selected="selected"</c:if>>四月</option>
							<option value="5" <c:if test="${jobCronSet.theMonthOfYear=='5'}">selected="selected"</c:if>>五月</option>
							<option value="6" <c:if test="${jobCronSet.theMonthOfYear=='6'}">selected="selected"</c:if>>六月</option>
							<option value="7" <c:if test="${jobCronSet.theMonthOfYear=='7'}">selected="selected"</c:if>>七月</option>
							<option value="8" <c:if test="${jobCronSet.theMonthOfYear=='8'}">selected="selected"</c:if>>八月</option>
							<option value="9" <c:if test="${jobCronSet.theMonthOfYear=='9'}">selected="selected"</c:if>>九月</option>
							<option value="10" <c:if test="${jobCronSet.theMonthOfYear=='10'}">selected="selected"</c:if>>十月</option>
							<option value="11" <c:if test="${jobCronSet.theMonthOfYear=='11'}">selected="selected"</c:if>>十一月</option>
							<option value="12" <c:if test="${jobCronSet.theMonthOfYear=='12'}">selected="selected"</c:if>>十二月</option>
							</select>
							<input  class="easyui-numberspinner" name="theDayOfMonth"  value="<c:choose><c:when test="${jobCronSet.theDayOfMonth==null }">1</c:when><c:otherwise>${jobCronSet.theDayOfMonth}</c:otherwise></c:choose>"  readonly="readonly"  data-options="min:1,max:31,editable:false" style="width:70px;"></input>日
							
						</span>
						<br/>
						<span>
							<input type="radio"  name="everyYear" value="2" <c:choose><c:when test="${jobCronSet.everyYear=='2'}">checked</c:when></c:choose>/>
						每年 <select class="easyui-combobox" name="theMonthOfYear2" value="<c:choose><c:when test="${jobCronSet.theMonthOfYear2==null }">1</c:when><c:otherwise>${jobCronSet.theMonthOfYear2}</c:otherwise></c:choose>"  style="width: 70px" data-options="">
							<option value="1" <c:if test="${jobCronSet.theMonthOfYear2=='1'}">selected="selected"</c:if>>一月</option>
							<option value="2" <c:if test="${jobCronSet.theMonthOfYear2=='2'}">selected="selected"</c:if>>二月</option>
							<option value="3" <c:if test="${jobCronSet.theMonthOfYear2=='3'}">selected="selected"</c:if>>三月</option>
							<option value="4" <c:if test="${jobCronSet.theMonthOfYear2=='4'}">selected="selected"</c:if>>四月</option>
							<option value="5" <c:if test="${jobCronSet.theMonthOfYear2=='5'}">selected="selected"</c:if>>五月</option>
							<option value="6" <c:if test="${jobCronSet.theMonthOfYear2=='6'}">selected="selected"</c:if>>六月</option>
							<option value="7" <c:if test="${jobCronSet.theMonthOfYear2=='7'}">selected="selected"</c:if>>七月</option>
							<option value="8" <c:if test="${jobCronSet.theMonthOfYear2=='8'}">selected="selected"</c:if>>八月</option>
							<option value="9" <c:if test="${jobCronSet.theMonthOfYear2=='9'}">selected="selected"</c:if>>九月</option>
							<option value="10" <c:if test="${jobCronSet.theMonthOfYear2=='10'}">selected="selected"</c:if>>十月</option>
							<option value="11" <c:if test="${jobCronSet.theMonthOfYear2=='11'}">selected="selected"</c:if>>十一月</option>
							<option value="12" <c:if test="${jobCronSet.theMonthOfYear2=='12'}">selected="selected"</c:if>>十二月</option>
							</select>
							<select class="easyui-combobox" name="theDayOfYear"  value="<c:choose><c:when test="${jobCronSet.theDayOfYear==null }">1</c:when><c:otherwise>${jobCronSet.theDayOfYear}</c:otherwise></c:choose>"  style="width: 60px" data-options="">
							<option value="1" <c:if test="${jobCronSet.theDayOfYear=='1'}">selected="selected"</c:if>>第一个</option>
							<option value="2" <c:if test="${jobCronSet.theDayOfYear=='2'}">selected="selected"</c:if>>第二个</option>
							<option value="3" <c:if test="${jobCronSet.theDayOfYear=='3'}">selected="selected"</c:if>>第三个</option>
							<option value="4" <c:if test="${jobCronSet.theDayOfYear=='4'}">selected="selected"</c:if>>第四个</option>
							<option value="5" <c:if test="${jobCronSet.theDayOfYear=='5'}">selected="selected"</c:if>>最后一个</option>
							</select>
							<select  class="easyui-combobox" name="theWeekdayOfYear"  value="<c:choose><c:when test="${jobCronSet.theWeekdayOfYear==null }">1</c:when><c:otherwise>${jobCronSet.theWeekdayOfYear}</c:otherwise></c:choose>"  style="width: 60px" data-options="width:'60'">
							<option value="1" <c:if test="${jobCronSet.theWeekdayOfYear=='1'}">selected="selected"</c:if>>星期日</option>
							<option value="2" <c:if test="${jobCronSet.theWeekdayOfYear=='2'}">selected="selected"</c:if>>星期一</option>
							<option value="3" <c:if test="${jobCronSet.theWeekdayOfYear=='3'}">selected="selected"</c:if>>星期二</option>
							<option value="4" <c:if test="${jobCronSet.theWeekdayOfYear=='4'}">selected="selected"</c:if>>星期三</option>
							<option value="5" <c:if test="${jobCronSet.theWeekdayOfYear=='5'}">selected="selected"</c:if>>星期四</option>
							<option value="6" <c:if test="${jobCronSet.theWeekdayOfYear=='6'}">selected="selected"</c:if>>星期五</option>
							<option value="7" <c:if test="${jobCronSet.theWeekdayOfYear=='7'}">selected="selected"</c:if>>星期六</option>
							</select>
						</span>
			</fieldset>
					
					</div>					
				</div>
			<c:if test="${flag=='0'}"> 
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:editDataSource('${group.groupCodeTmp}','1')" >配置数据源</a>
			</c:if>	
			<c:if test="${flag=='1'}"> 
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:editDataSource('${group.groupCode}','0')" >配置数据源</a>
			</c:if>				
</form>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;">
				<a id = "savesbutton"class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:saveGroup()" >保存</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:closeWindow('commonMyPopWindow')" >取消</a>	
			</div>
		</div>
<!-- 数据分配 end -->
</form>

<script type="text/javascript">


function saveGroup(){
	 $('#savesbutton').removeAttr('href');
	var department = $('#department').combobox('getValues');
	$('#departments').attr("value",department);
	var r = $('#customerGroupForm').form('validate');
	if(!r) {
		return;
	}
	if ('${group.sqlSource}'=="") {
		if ($("#ext2").val()==""||$("#ext2").val()==null) {
			alerts("请先设置数据源");
			return ;
		}
	}
	var startDate = $("#startDate").datebox("getValue"); 
	var endDate = $("#endDate").datebox("getValue"); 
	var myDate = new Date();
	var times =  myDate.format("yyyy-MM-dd hh:mm:ss");
	var timeStr = $("#timeStr").datebox("getValue");
	var stime = $("#stime").datebox("getValue"); 
	var etime = $("#etime").datebox("getValue"); 
	if (startDate > endDate) {
		alerts("结束时间不能早于开始时间");
		return ;
	}
	if (endDate<times) {
		alerts("结束时间不能早于当前时间");
		return ;
	}	
	if (etime<stime) {
		alerts("结束时间不能早于开始时间");
		return ;
	}
	if ($("#timeRangeFieldSet").css('display') == 'block') {
		if (stime!=null&&stime!=""&&etime!=null&&etime!="") {
			if (etime<times) {
				alerts("结束时间不能早于当前时间");
				return ;
			}
		}
	}
	

	if ($("#timeStrFieldSet").css('display') == 'block') {
		if (timeStr!=null&&timeStr!="") {
			if (timeStr<times) {
				alerts("定时时间不能早于当前时间");
				return ;
			}
		}
	}	
	var datas = $("#customerGroupForm").serializeArray(); 
	$.post("${ctx}/customer/saveGroup",
			$("#customerGroupForm").serializeArray(),
			function(data){
		closeWindow('commonMyPopWindow'); 
		$('#customerTable').datagrid("reload");
	},'json');
	
	
}

function changeCycleType(type){
	var fV = type;
	hideAllFieldSet();
	if(fV=='5'){
		$("#timeStrFieldSet").show();
	}else if(fV=='1'){
		$("#dayFieldSet").show();
	}else if(fV=='3'){
		$("#monthFieldSet").show();
	}else if(fV=='4'){
		$("#yearFieldSet").show();
	}else if(fV=='2'){
		$("#weekFieldSet").show();
	}else if(fV=='6'){
		$("#hourFieldSet").show();
	}
	
	if(fV!='0' && fV != '5'){
		$("#timeRangeFieldSet").show();
		$("#recoverId").show();
	}
}
var hideAllFieldSet = function (){
	$("#timeStrFieldSet").hide();
	$("#timeRangeFieldSet").hide();
	$("#dayFieldSet").hide();
	$("#monthFieldSet").hide();
	$("#yearFieldSet").hide();
	$("#weekFieldSet").hide();
	$("#hourFieldSet").hide();
	$("#recoverId").hide();
}

/** 
 * 时间对象的格式化; 
 */  
Date.prototype.format = function(format) {  
    /* 
     * eg:format="YYYY-MM-dd hh:mm:ss"; 
     */  
    var o = {  
        "M+" :this.getMonth() + 1, // month  
        "d+" :this.getDate(), // day  
        "h+" :this.getHours(), // hour  
        "m+" :this.getMinutes(), // minute  
        "s+" :this.getSeconds(), // second  
        "q+" :Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S" :this.getMilliseconds()  
    // millisecond  
    }  
  
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "")  
                .substr(4 - RegExp.$1.length));  
    }  
  
    for ( var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]  
                    : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
}  
</script>
</body>
</html>
