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
				<form action="" id="scheduleForm" method="post">
				
				<input type="hidden" id="campaignId" name="campaignId" value="${campaignId }">
				
				<div class="clearfix">
				<div class="fl_w50">
					<label>刷新周期:</label> 
					 <span class="inputSpan">
					<input class="easyui-combobox" style="width:150px;"
						name="frequency" 
						<c:choose><c:when test="${jobCronSet.frequency==null }">value="0"</c:when><c:otherwise>value="${jobCronSet.frequency}"</c:otherwise></c:choose>
						data-options="
								url:'${ctx }/dict/getBaseConfig?code=CYCLE2',
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
						开始时间<input  class="easyui-datetimebox"  readonly="readonly" type="text"  name="dateOfStart" value="<c:choose><c:when test="${jobCronSet.dateOfStart!=null}">${jobCronSet.dateOfStart}</c:when></c:choose>" data-options="" style="width:140px">
						结束时间<input  class="easyui-datetimebox"  readonly="readonly" type="text"  name="endDay" value="<c:choose><c:when test="${jobCronSet.endDay!=null}">${jobCronSet.endDay}</c:when></c:choose>" data-options="" style="width:140px">
						</span>
						</fieldset>
					</div>
					
					<div  id="minuteFieldSet" style="display:<c:choose><c:when test="${jobCronSet.frequency=='7' }"></c:when><c:otherwise>none</c:otherwise></c:choose>;width: 420px;padding-left:130px">
					<fieldset>
				<legend>重复间隔:</legend>
						 <span >
						 每<input class="easyui-numberspinner" value="<c:choose><c:when test="${jobCronSet.receiverNum==null }">1</c:when><c:otherwise>${jobCronSet.receiverNum}</c:otherwise></c:choose>" name="receiverNum"  readonly="readonly"  data-options="min:1,max:59,editable:false" style="width: 50px"></input>分钟
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
							<option value="1">第一个</option>
							<option value="2">第二个</option>
							<option value="3">第三个</option>
							<option value="4">第四个</option>
							<option value="5">最后一个</option>
							</select>
							<select  class="easyui-combobox" name="theWeekday" value="<c:choose><c:when test="${jobCronSet.theWeekday==null }">1</c:when><c:otherwise>${jobCronSet.theWeekday}</c:otherwise></c:choose>" style="width: 70px" data-options="">
							<option value="1">星期日</option>
							<option value="2">星期一</option>
							<option value="3">星期二</option>
							<option value="4">星期三</option>
							<option value="5">星期四</option>
							<option value="6">星期五</option>
							<option value="7">星期六</option>
							</select>
						</span>
						</fieldset>
					</div>
					
					<div  id="yearFieldSet" style="display:<c:choose><c:when test="${jobCronSet.frequency=='4' }"></c:when><c:otherwise>none</c:otherwise></c:choose>;width: 420px;padding-left:130px">
					<fieldset>
				<legend>重复间隔:</legend>
				<span>
						<input type="radio"  name="everyYear" value="1" <c:choose><c:when test="${jobCronSet.everyYear=='1' || jobCronSet.everyYear==null }">checked</c:when></c:choose>/>
						每年 <select class="easyui-combobox" name="theMonthOfYear" value="<c:choose><c:when test="${jobCronSet.theMonthOfYear==null }">1</c:when><c:otherwise>${jobCronSet.theMonthOfYear}</c:otherwise></c:choose>"  style="width: 70px" data-options="">
							<option value="1">一月</option>
							<option value="2">二月</option>
							<option value="3">三月</option>
							<option value="4">四月</option>
							<option value="5">五月</option>
							<option value="6">六月</option>
							<option value="7">七月</option>
							<option value="8">八月</option>
							<option value="9">九月</option>
							<option value="10">十月</option>
							<option value="11">十一月</option>
							<option value="12">十二月</option>
							</select>
							<input  class="easyui-numberspinner" name="theDayOfMonth"  value="<c:choose><c:when test="${jobCronSet.theDayOfMonth==null }">1</c:when><c:otherwise>${jobCronSet.theDayOfMonth}</c:otherwise></c:choose>"  readonly="readonly"  data-options="min:1,max:31,editable:false" style="width:70px;"></input>日
							
						</span>
						<br/>
						<span>
							<input type="radio"  name="everyYear" value="2" <c:choose><c:when test="${jobCronSet.everyYear=='2'}">checked</c:when></c:choose>/>
						每年 <select class="easyui-combobox" name="theMonthOfYear2" value="<c:choose><c:when test="${jobCronSet.theMonthOfYear2==null }">1</c:when><c:otherwise>${jobCronSet.theMonthOfYear2}</c:otherwise></c:choose>"  style="width: 70px" data-options="">
							<option value="1">一月</option>
							<option value="2">二月</option>
							<option value="3">三月</option>
							<option value="4">四月</option>
							<option value="5">五月</option>
							<option value="6">六月</option>
							<option value="7">七月</option>
							<option value="8">八月</option>
							<option value="9">九月</option>
							<option value="10">十月</option>
							<option value="11">十一月</option>
							<option value="12">十二月</option>
							</select>
							<select class="easyui-combobox" name="theDayOfYear"  value="<c:choose><c:when test="${jobCronSet.theDayOfYear==null }">1</c:when><c:otherwise>${jobCronSet.theDayOfYear}</c:otherwise></c:choose>"  style="width: 60px" data-options="">
							<option value="1">第一个</option>
							<option value="2">第二个</option>
							<option value="3">第三个</option>
							<option value="4">第四个</option>
							<option value="5">最后一个</option>
							</select>
							<select  class="easyui-combobox" name="theWeekdayOfYear"  value="<c:choose><c:when test="${jobCronSet.theWeekdayOfYear==null }">1</c:when><c:otherwise>${jobCronSet.theWeekdayOfYear}</c:otherwise></c:choose>"  style="width: 60px" data-options="width:'60'">
							<option value="1">星期日</option>
							<option value="2">星期一</option>
							<option value="3">星期二</option>
							<option value="4">星期三</option>
							<option value="5">星期四</option>
							<option value="6">星期五</option>
							<option value="7">星期六</option>
							</select>
						</span>
			</fieldset>
					
					</div>					
				</div>
						
</form>
<div id="tipInfo" style="color: red;"></div>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;">
			<a href="javascript:editPrevPage('${sourceUrl }')" class="easyui-linkbutton" data-options="iconCls:'icon-prev'" onclick="">上一步</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:saveSchedule()" >确定</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:closeWindow('commonMyPopWindow')" >取消</a>	
			</div>
		</div>
<!-- 数据分配 end -->
</form>
<input type="hidden" id="groupExeTime" value=""/>
<script type="text/javascript">
$(function() {
	initGroupInfo();
	
});


function initGroupInfo(){
	//if($("#groupExeTime").val()==""){
		$.post("${ctx}/customer/getGroup",
				{groupCode:'${groupCode}'},
				function(data){
					var timeVar = "";
					if (data.group.executeCycel == '5') {
						timeVar = data.jobSet.timeStr;
					} else if (data.group.executeCycel == '0') {
						timeVar = data.group.upDate;
					}else{
						timeVar = data.jobSet.dateOfStart;
					}
			if(timeVar!=""){
				$("#tipInfo").html("友情提示: 建议营销计划执行时间不早于[ "+timeVar+" ]");
			}
		},'json');
	//}
}
function saveSchedule(){
	var r = $('#scheduleForm').form('validate');
	if(!r) {
		return;
	}

	var myDate = new Date();
	var times =  myDate.format("yyyy-MM-dd hh:mm:ss");
	var timeStr = $("#timeStr").datebox("getValue"); 

	if (timeStr!=null&&timeStr!="") {
		if (timeStr<times) {
			alerts("定时时间不能早于当前时间");
			return ;
		}
	}
	$.post("${ctx}/campaign/saveSchedule",
			$("#scheduleForm").serializeArray(),
			function(data){
		closeWindow('commonMyPopWindow'); 
		$('#campaignTable').datagrid("reload");
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
	}else if(fV=='7'){
		$("#minuteFieldSet").show();
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
	$("#minuteFieldSet").hide();
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
