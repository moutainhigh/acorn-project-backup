<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>

<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp" %>
<%@ include file="/common/default_jscss.jsp" %>


    <%
        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String sysdate = format.format(rightNow.getTime());

    %>
<script type="text/javascript">
var ctx = '${ctx}';
$(function() {
	panelsMap = new Map();
	initMain();
	initLeftMenu();
	<c:if test="${sessionScope.MUST_CHANGE_PWD != null}">
	modifyPwd();
	</c:if>
});

var preDate=null;
$(document).ready(function(){
    //获取软件版本
    $.get("/common/getVersion", function(result){
        $("#sales_version").html(result.version);
        $("#d_employeeType").html(result.employeeType);
        $("#d_workGrp").html(result.workGrp);
        $("#d_role").html(result.role.description);
        $("#span_intradayTotalOutcallTime").html(result.totalOutcallTime+"秒");
        $("#seatType").val(result.seatType);
        $("#d_csphone").val(result.csPhone);
        $("#d_address_check").val(result.names);
        $("#d_address_check_ext").val(result.namesext);
        if(result.seatType == 'OUT'){
            $("#isShowTotalOutcallTime").show();
        }else{
            $("#isShowTotalOutcallTime").hide();
        }

        $("#analoglines").val(result.analoglines);
        if(result.analoglines=="true"){
            console.info("result.analoglines:"+result.analoglines);
            phone.changeStatus(0);
         console.info("++++++++++++++++++++++++++++++++就绪....");
        }else{
            if(ctiIsLogIn==0){
                phone.changeStatus(15);
            }else{
                phone.changeStatus(2);
            }
        }
    });
    window.setInterval('my_run();', 1000);

    var width=$("#td_msg_content").width()+10;
    $('#msg_content_padding').width(width);

    $("#marquee_msg").marquee({scrollSpeed:24,aftershow: function ($marquee, $li){
//        console.log("msg is shown");
        $marquee.marquee("pause");

        if(preDate==null)
        {
            preDate=new Date();
            fetchSysMessageTips();
        }
        else
        {
            var currentDate=new Date();
            var timeDiff=parseInt((currentDate-preDate)/1000);
            if(timeDiff>30)//隔30秒读一次
            {
                preDate=currentDate;
                fetchSysMessageTips();
            }
        }

        $marquee.marquee("resume");
    }});
});

function logout(){
	if(phone && phone.status==3){
		$.messager.alert('提示','通话中不允许退出!','warning');
	}else{

		$.messager.defaults = { ok: "是", cancel: "否"};
//        $.messager.linkbutton({ plain:true});
		$.messager.confirm('提示', '<p style="font-size:14px;height:60px;line-height: 60px;color: #666;text-align: center;">确定退出?<p>', function(r){
			if (r){
                $('.logout').data('isNor',true);
                agentLogout(); console.info("logout");
				window.location.href='${ctx}/logout';
			}
		});

        <%--var confirm1 = $('<table id="exit"><p style="font-size:14px;height:60px;line-height: 60px;color: #666;" class="textC">确定退出?<p></table>');--%>
        <%--confirm1.dialog({--%>
            <%--title: '提示',--%>
            <%--width: 300,--%>
            <%--height: 100,--%>
            <%--shadow:false,--%>
<%--//            closed: false,--%>
<%--//            cache: false,--%>
            <%--modal: true,--%>
            <%--collapsible:false,--%>
            <%--minimizable:false,--%>
            <%--maximizable:false,--%>
            <%--draggable:false,--%>
            <%--buttons: [{--%>
            <%--text: '是',--%>
            <%--plain:true,--%>
            <%--handler: function() {--%>
                <%--window.location.href='${ctx}/logout';--%>
            <%--}--%>
        <%--}, {--%>
            <%--text: '否',--%>
            <%--plain:true,--%>
            <%--handler: function() {--%>
                <%--$('#exit').dialog('close');--%>
            <%--}--%>
        <%--} ]--%>
        <%--}).dialog('open');--%>
		
	}
}

function fetchSysMessageTips()
{
    //fetch sysmessage from db
    var showText="";
    $.ajax({
        type : "post",
        url : "/message/get",
        async : false,
        dataType:"json",
        success : function(data){
            if(data!=null){
                for(var i=0;i<data.length;i++)
                {
                    showText+='<span>';
                    showText+=data[i];
                    showText+='</span>';
                    showText+="  ";
                }
            }else{
            }
        }
    });
    if(showText=="")
    {
        showText="    ";
    }
    $('#msg_content').html(showText);


 //显示服务器的时间







}

var currentDate = new Date(<%=new java.util.Date().getTime()%>);
function my_run()
{
     currentDate.setSeconds(currentDate.getSeconds()+1);
     $("#dt").html(currentDate.toLocaleString());
}

function modifyPwd(){
	
	<c:if test="${sessionScope.MUST_CHANGE_PWD_FLAG != null}">
	$('#modifyPwdWin').window({'closable':false});
	</c:if>
	
	$('#modifyPwdWin').window({href:'${ctx}/user/modifyPwdInit'}); 
	$('#modifyPwdWin').window('open');
}
function closeModifyPwdWin(){
	$('#modifyPwdWin').window('close');
}


//定义了通话结果类型的二维数组，
var callType=[
    ["Full Call","Snatch Out","Call Back"],
    ["Full Call","Snatch In","Snatch Out","Call Back"],
    ["Full Call","Snatch Out","Call Back"],
    ["Full Call","Snatch Out","Call Back"],
    ["Full Call","Snatch Out","Call Back"],
    ["Full Call","Snatch Out","Call Back"],
    ["Full Call","Snatch Out","Call Back"],
    ["Full Call","Snatch In","Call Back"],
    ["Snatch In"],
    ["Snatch Out","Call Back"],
    ["Snatch Out","Call Back"]
];

function getCallType(){
    var sltCallResult=document.getElementById("callResult");
    var sltCallType=document.getElementById("callType");
    var resltType=callType[sltCallResult.selectedIndex - 1];
    sltCallType.length=1;
    if(resltType){
        for(var i=0;i<resltType.length;i++){
            sltCallType[i+1]=new Option(resltType[i],resltType[i]);
        }
    }
}
</script>
<decorator:head/>
</head>
<body>
<div id="wrapper" >
	<!-- 顶部软电话 -->
	<jsp:include page="/common/header.jsp" />
  
  <div id="content" class="fn-scroll">
    <table id="AcornPhone" class="appTable"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td id="sidebarCell" class="sidebarCell" rowspan="2" valign="top" width="150px" >
            <div id="left" class="lump" >
               <%--<div class="current_customer" id="current_customer" style="display: none" >--%>
                <%--<h3><span id="cc_customerId" onclick="javascript:location_cc();">921078245</span></h3>--%>
                <%--<div>--%>
                    <%--<p><label>姓名：</label><span id="cc_name">张振江</span></p>--%>
                    <%--<p><label>性别：</label><span id="cc_sex">男</span></p>--%>
                    <%--<p><label>电话：</label><span id="cc_hpone">1500000****</span></p>--%>
                    <%--<p><label>地址：</label><span id="cc_address">河北省张家口市怀安县王虎屯乡</span></p>--%>
                    <%--<p class="submitBtns"><a class="" onclick="javascript:cc_change();" href="#">切换客户</a></p>--%>
                <%--</div>--%>
            <%--</div>--%>
            <!-- 左边导航-->
            <jsp:include page="/common/left.jsp" />
            </div>
            <div title="单击关闭侧栏 " id="handlebarContainer" class="handlebarContainer"><div id="pinIndicator" class="indicator"></div><div id="pinIndicator2" class="indicator"></div><div id="handle" class="pinBox">&nbsp;</div></div>
        </td>
        <td valign="top"  height="25px" style="padding:0"><div id="msg">
            <table border="0" cellspacing="0" cellpadding="0" width="100%"  height="25px">
              <tr>
                <td width="88px"><div id="msg-title" class="msg-title"  style="">我的消息</div></td>
                <%--<td><marquee id="msg-marquee" behavior='scroll' scrollAmount=2 width=100% onmouseover=stop() onmouseout=start() onfinish=fetchSysMessageTips() loop='1'><div id="msg-content" class="msg-content"></div></marquee></td> --%>
                  <%--<td><marquee  behavior='scroll' scrollAmount=2 width=100% onmouseover=start() onmouseout=stop() onfinish=stop() ><div id="msg-content" class="msg-content">5645645645654</div></marquee></td>--%>
                 <td id="td_msg_content"><ul id="marquee_msg" class="marquee">
                        <li id="msg_content_li"><table><tr><td><div id="msg_content_padding" style="">&nbsp;</div></td><td><div id="msg_content" onclick="openMessage()">&nbsp;&nbsp;&nbsp;</div></td></tr></table></li>
                    </ul>
                 </td>
              </tr>
            </table>
          </div></td>
        <td id="sidebarCell2" class="sidebarCell" rowspan="2" valign="top" width="210px"  >
         <div title="单击关闭侧栏" id="handlebarContainer2" class="handlebarContainer2"><div id="pinIndicator3" class="indicator"></div><div id="pinIndicator4" class="indicator"></div><div id="handle2" class="pinBox">&nbsp;</div></div>
        <div id="right" class="lump">
       		 	 <!-- 右边商品区-->
       		 	 <jsp:include page="/common/recommend.jsp" />
       		 	 <jsp:include page="/common/info.jsp" />
       			 <jsp:include page="/common/description.jsp" />
        		</div></td>
      </tr>
      <tr>
        <td valign="top" height="" style="padding:12px 0 10px 0">
        
        <div id="center" class="lump"  >
        	<div class="tabs-warp">
             <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td width="360px" valign="top">
                <ul id="apptabs" class="apptabs clearfix">
            <li><a id="home" url="${ctx}/home/home" class="tabPage current" style="width:26px">首页</a></li>
            <li><a id="myevent" url="${ctx}/event/myevent" class="tabPage" style="width:52px">我的事件</a></li>
            <li><a id="mytask" url="${ctx}/task/mytask" class="tabPage" style="width:52px">我的任务</a></li>
            <li><a id="myorder" url="${ctx}/myorder/myorder" class="tabPage" style="width:52px">我的订单</a></li>
            <li><a id="mycustomer" url="${ctx}/customer/mycustomer" class="tabPage" style="width:52px">我的客户</a></li>
            <sec:authorize ifAllGranted="COMPLAIN_MANAGER">
            <li><a id="blacklist" url="${ctx}/blackList/blacklist" class="tabPage" style="width:52px">黑名单</a></li>
            </sec:authorize>
            <li class="rel"><div class="tab_shadow"></div></li>
            </ul>
            </td>
                <td>
                <div id="scrollWarp" style=" overflow:hidden;width:200px;">
                	
                <ul class="tabs-active clearfix" id="tabs-active">
                    <%--<li  name="smsTab" style="z-index:999" ><span class="tab_content">短信</span><a class="tab-closeBtn"><span name="smsTab" class="tab-closeBtn"></span></a></li>--%>
                    <%--<li  name="smsTab" style="z-index:998" ><span class="tab_content">短信</span><a class="tab-closeBtn"><span name="smsTab" class="tab-closeBtn"></span></a></li>--%>
                    <%--<li  name="smsTab" style="z-index:997" ><span class="tab_content">短信</span><a class="tab-closeBtn"><span name="smsTab" class="tab-closeBtn"></span></a></li>--%>
                    <%--<li  name="smsTab" style="z-index:996" ><span class="tab_content">短信</span><a class="tab-closeBtn"><span name="smsTab" class="tab-closeBtn"></span></a></li>--%>
                    <%--<li  name="smsTab" style="z-index:995" ><span class="tab_content">短信</span><a class="tab-closeBtn"><span name="smsTab" class="tab-closeBtn"></span></a></li>--%>
                    <%--<li  name="smsTab" style="z-index:994" ><span class="tab_content">短信</span><a class="tab-closeBtn"><span name="smsTab" class="tab-closeBtn"></span></a></li>--%>
                    <%--<li  name="smsTab" style="z-index:993" ><span class="tab_content">短信</span><a class="tab-closeBtn"><span name="smsTab" class="tab-closeBtn"></span></a></li>--%>
                    <%--<li  name="smsTab" style="z-index:992" ><span class="tab_content">短信</span><a class="tab-closeBtn"><span name="smsTab" class="tab-closeBtn"></span></a></li>--%>
                    <%--<li  name="smsTab" style="z-index:991" ><span class="tab_content">短信</span><a class="tab-closeBtn"><span name="smsTab" class="tab-closeBtn"></span></a></li>--%>
                    <%--<li  name="smsTab" style="z-index:990" ><span class="tab_content">短信</span><a class="tab-closeBtn"><span name="smsTab" class="tab-closeBtn"></span></a></li>--%>
                    <%--<li  name="smsTab" style="z-index:989" ><span class="tab_content">短信</span><a class="tab-closeBtn"><span name="smsTab" class="tab-closeBtn"></span></a></li>--%>
                    <%--<li  name="smsTab" style="z-index:988" ><span class="tab_content">短信</span><a class="tab-closeBtn"><span name="smsTab" class="tab-closeBtn"></span></a></li>--%>
                    <%--<li  name="smsTab" style="z-index:987" ><span class="tab_content">短信</span><a class="tab-closeBtn"><span name="smsTab" class="tab-closeBtn"></span></a></li>--%>
                    <%--<li  name="smsTab" style="z-index:986" ><span class="tab_content">短信</span><a class="tab-closeBtn"><span name="smsTab" class="tab-closeBtn"></span></a></li>--%>
                    <%--<li  name="smsTab" style="z-index:985" ><span class="tab_content">短信</span><a class="tab-closeBtn"><span name="smsTab" class="tab-closeBtn"></span></a></li>--%>

                </ul>
            </div>
                </td>
                 <td >
                     <div class="fr" style="width:30px">
                         <a id="l_scroll" class="l_scroll fl"><span></span></a>
                         <a  id="r_scroll" class="r_scroll fl"><span></span></a>
                         <div class="c_combox">
                             <div class="w_combox" id="w_combox">
                             </div>
                         </div>

                     </div>
                 </td>
              </tr>
            </table>
            </div>
            <div class="appContent" id="appContent" ></div>
            
        </div></td>
      </tr>
    </table>
  </div>
    <div class="state_wrap" style="position: static;display: none"></div>

</div>

<div id="hook" class="easyui-window" title="通话结果" data-options="modal:true,shadow:true,closable:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true,draggable:false" style="width:400px;">
    <%--<label id="hookMsg" style="color: #ff4500"></label>--%>
<form id="h_hookForm" method="post">  
 <div class="form-tabs">
      <div>
        <label class="mr10"> 通话结果:</label>
         <SELECT  NAME="callResult" id="callResult" onChange="javascript:getCallType();">
             <OPTION VALUE="0">请选择</OPTION>
             <OPTION VALUE="1">实际订购</OPTION>
             <OPTION VALUE="2">短线</OPTION>
             <OPTION VALUE="3">断线</OPTION>
             <OPTION VALUE="4">咨询</OPTION>
             <OPTION VALUE="5">预约回电</OPTION>
             <OPTION VALUE="6">查询</OPTION>
             <OPTION VALUE="7">售后、投诉</OPTION>
             <OPTION VALUE="8">骚扰</OPTION>
             <OPTION VALUE="9">有效Snatch-In</OPTION>
             <OPTION VALUE="10">占线、无人接听</OPTION>
             <OPTION VALUE="11">停机、查无此人</OPTION>
         </SELECT>
         <SELECT NAME="callType" id="callType">
             <OPTION VALUE="0">请选择</OPTION>
         </SELECT>


      </div>

    <div class="mt10">
	    <label class="mr10"><input id="h_isContact" name="h_isContact" class="fl" style="margin-left:0" type="checkbox" />再联系</label>
	    <span><label class="mr10">时间</label><input id="h_contactTime" name="contactTime" disabled="false"  style="width:140px;"  class="easyui-datetimebox"/> &nbsp;&nbsp;&nbsp;&nbsp;<font color="red"><label id="warnMsg" ></label></font></span>

     <div id="d_selectTask" class=""  style="display:none">
         <div class="clearfix" style="margin-left: 40px;">
             <label class=" mr10 fl" style="width:50px;">选择任务</label>
             <input id="d_marketingPlan" class="fl easyui-combobox"  editable=false  style="width:140px;" name="d_marketingPlan"  />

         </div>
         <div class="clearfix" id="v_reson" style="margin-left: 40px;">

             <label class=" mr10 fl" style="width:50px;">挂机原因</label>
             <SELECT  class="fl easyui-combobox" id="d_reson" style="width:140px;color:#666;">
                 <OPTION value="1" selected>对方忙</OPTION>
                 <OPTION value="2">无应答</OPTION>
                 <OPTION value="3">无效号码</OPTION>
                 <OPTION value="4">暂时无法接通</OPTION>
             </SELECT>


         </div>
     </div>
    </div>
	<p style="vertical-align:top" class="">备注:<textarea  id="h_remark" name="remark" style="width:363px;height:70px;vertical-align:top"></textarea></p>
     <div style="text-align:right;padding:5px 0 0" class="clearfix"><span class="fr ml5">
         <select id="notReadyCode"  class="easyui-combobox fr">
             <option value="1204">休息</option>
             <option value="1205">用餐</option>
             <option value="1206">主管沟通</option>
             <option value="1207">下线/学习</option>
             <option value="1208">开会/培训</option>
         </select></span><label class="fr"><input class="fl" name="hook_st" value="2" type="radio"/>离席</label><label class="fr"><input class="fl" name="hook_st" value="1" type="radio"/>就绪(可外呼)</label><label class="fr"><input name="hook_st" class="fl" value="0" type="radio" checked="true"/>就绪</label></div>


     <input id="h_leadId" name="h_leadId" type="hidden" />
	<input id="h_instId" name="h_instId" type="hidden" />
	<input id="h_pdCustomerId" name="h_pdCustomerId" type="hidden" />
	<input id="h_customerType" name="h_customerType" type="hidden" />
	<input id="h_customerId" name="h_customerId" type="hidden" />
	<input id="h_campaignId" name="h_campaignId" type="hidden" />
	</div>
    <div class="winBtnsBar textC" style="position: relative"><a id="hooksave" class="window_btn"  href="#" style="display: inline-block" >保存
    </a>
        <div id="savetip"  class="tooltip tooltip-right"
             style="left:240px; top: 5px; z-index: 9006;display: none ">
            <div class="tooltip-content">电话没有挂机，请挂机!</div>
            <div class="tooltip-arrow-outer" style="border-right-color: rgb(149, 184, 231);"></div>
            <div class="tooltip-arrow" style="border-right-color: rgb(255, 255, 255);"></div>
        </div></div>
	</form>
</div>


<div id="queryC" class="easyui-window" title="客户查询" data-options="modal:true,shadow:true,draggable:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true" style="width:700px;">
       <div class="easyui-tabs" style=" height:auto">
   		 <div title="查&nbsp;&nbsp;询">
           <div class="form-tabs">
           <input type="hidden" id="d_provid" />
           <input type="hidden" id="d_cityid" />
            <table class="win_table" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>电话号码</td>
    <td><input id="phone" name="" aria="true"   type="text" maxlength="255" /></td>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td>客户姓名</td>
    <td><input id="name" name="" value="" aria="true"   type="text" maxlength="255" /></td>
    <td><p><a class="window_btn" onclick="javascript:findCustomer()" href="javascript:void(0);">查&nbsp;&nbsp;找</a></p></td>
    <td>&nbsp;</td>
  </tr>
</table>
             <%--<p class="mb10"><a class="window_btn" onclick="javascript:findCustomer()" href="javascript:void(0);">查&nbsp;&nbsp;找</a>--%>
                 <!--
                 <a class="window_btn ml10" onclick="javascript:cancleCustomer()" href="javascript:void(0);">清&nbsp;&nbsp;空</a>
                 <a class="window_btn ml10" href="javascript:void(0);"   onclick="javascript:addCustomer()" >新&nbsp;&nbsp;增</a>
                 -->
             <%--</p>--%>
             
             <div class="sift" style="vertical-align: middle;height: 23px;line-height: 23px;">地区&nbsp;
             <jsp:include page="/common/address_no_validation.jsp">
              <jsp:param value="true" name="isFormSubmit"/>
             </jsp:include>
          </div>	      
<table   id="defaultTable" class="easyui-datagrid" title="" style="width:auto;height:200px"
            data-options="singleSelect:true,collapsible:true,fitColumns:true,scrollbarSize:-1">
        <thead>
            <tr>
                <th data-options="field:'customerId',width:100,align:'center'">客户编号</th>
                <th data-options="field:'name',width:80,align:'center'">客户名称</th>
                <th data-options="field:'level',width:80,align:'center'">会员等级</th>
                <th data-options="field:'address',width:200">客户地址</th>
                <th data-options="field:'detailedAddress',width:100">详细地址</th>
                <th data-options="field:'crusr',width:80,align:'center'">创建人</th>
                <th data-options="field:'crdt',width:140">创建时间</th>
                <th data-options="field:'customerType',width:100">客户类别</th>
            </tr>
        </thead>
    </table>                        
           </div>
                
         </div>
         <div title="更多查询">
         			<div class="form-tabs">

    	<div class="mb10">
    		<ul id="in_tab" class="in_tab clearfix">
    			<li class="current" id="find_customerId"><a>客户编号</a></li>
    			<li  id="find_orderId"><a>订单号</a></li>
    			<li id="find_shipmentId"><a >包裹单号</a></li>
    		</ul>
    		<div>
    		<input type="hidden" id="typeValue" name="typeValue" >
    		<input type="text" class="in_input" id="findValue" name="findValue"/><a class="window_btn ml10" href="javascript:void(0);" onclick="javascript:findCustomerm()" >查&nbsp;&nbsp;找</a>
                <!--
                <a class="window_btn ml10" href="javascript:void(0);" onclick="javascript:cancleCustomerm()">清&nbsp;&nbsp;空</a>
                <a class="window_btn ml10" href="javascript:void(0);"  onclick="javascript:addCustomerm()">新&nbsp;&nbsp;增</a>
                -->
            </div>
    	</div>
                   <script type="text/javascript">
               	$('#typeValue').val("customerId");
						$('#in_tab li').click(function(){
							$('#in_tab li').removeClass('current');$(this).addClass('current');
							$('#typeValue').val(this.id.replace("find_",""));
							});
					</script>
                 <table id="defaultTablem" class="easyui-datagrid" title="" style="width:auto;height:292px"
            data-options="singleSelect:true,collapsible:true,fitColumns:true,scrollbarSize:-1">
        <thead>  
            <tr>  
                <th data-options="field:'customerId',width:100">客户编号</th>  
                <th data-options="field:'name',width:100">客户名称</th>
                <th data-options="field:'level',width:80">会员等级</th>  
                <th data-options="field:'address',width:200">客户地址</th>
                <th data-options="field:'detailedAddress',width:100">地址详细</th>
                <th data-options="field:'crusr',width:80">创建人</th>
                <th data-options="field:'crdt',width:140">创建时间</th>
                <th data-options="field:'customerType',width:100">客户类别</th>
            </tr>
        </thead>  
    </table>    
          		 </div>
         </div>
       </div>
</div>
	<div id="w_inphone" class="easyui-window" title="电话呼入" data-options="collapsible:false,minimizable:false,maximizable:false,resizable:false,modal:true,closed:true,draggable:false" style="width:500px;">
        <div style="padding:10px;">
            <table>
                <tr><td style="padding:5px;">呼入电话:</td><td style="padding:5px;"><input name="d_inPhone" id="d_inPhone" type="text" value="15036233666" /></td></tr>
                <tr><td style="padding:5px;">落地号:</td><td style="padding:5px;"><input name="d_dnis" id="d_dnis" type="text" value="24100000" /></td></tr>
            </table>
        </div>
       <p class="winBtnsBar textC"><a class="window_btn"  data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:comingIn()">确定</a> </p>
       
	</div>

<%--<div id="w_callback" class="easyui-window" title="CallBack" data-options="modal:true,closed:true,maximizable:false,collapsible:false" style="">--%>
    <%--<div style="padding:10px;">--%>
        <%--<table>--%>
            <%--<tr><td style="padding:5px;">客户姓名</td><td style="padding:5px;"><input name="" id="" type="text" value="" /><a class="customerQuery s_btn ml10"></a></td></tr>--%>
            <%--<tr><td style="padding:5px;">客户编号</td><td style="padding:5px;"><input name="" id="" type="text" value="" /></td></tr>--%>
            <%--<tr><td style="padding:5px;">客户性别</td><td style="padding:5px;"><label><input name="sex" id="" type="radio" value="" />男</label><label><input name="sex" id="" type="radio" value="" />女</label></td></tr>--%>
            <%--<tr><td style="padding:5px;">咨询产品</td><td style="padding:5px;"><input name="" id="" type="text" value="" /></td></tr>--%>
            <%--<tr><td style="padding:5px;">回呼时间</td><td style="padding:5px;"><input class="easyui-datebox" name="" id="" type="text" value="" /></td></tr>--%>
            <%--<tr><td style="padding:5px;" valign="top">回呼备注</td><td style="padding:5px;"><textarea rows="5" cols="30"></textarea></td></tr>--%>
            <%--<tr><td style="padding:5px;">优先级别</td><td style="padding:5px;"><select class="easyui-combobox"> <OPTION selected value=非常重要>非常重要</OPTION> <OPTION value=重要>重要</OPTION> <OPTION value=一般>一般</OPTION></select></td></tr>--%>

        <%--</table>--%>
    <%--</div>--%>
    <%--<p class="winBtnsBar textC"><a class="window_btn mr10"  href="javascript:void(0)" onclick="javascript:void(0)">保存</a><a class="window_btn"   href="javascript:void(0)" onclick="javascript:void(0)">清空</a> </p>--%>

<%--</div>--%>
<div id="alertWin" class="easyui-window" data-options="modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true,draggable:false" style="width:400px;padding:10px;"></div>
<div class="state_wrap">
    <table>
        <tr>
            <td>
                <div class="bpoint ">座席工号:
                    <a class="fn-bg" id="fn-bg">
                        <sec:authentication property="name"/>
                    </a>

                    <div class="hg">
                          <ul>
                              <li onclick="modifyPwd()"><b><img src="static/images/notepad_32.png" width=16 height=16/></b><span>修改密码</span></li>
                          </ul>
                    </div>
                </div>
            </td>
            <td>
                <div>座席姓名:<span id="d_userId"><sec:authentication property="principal.displayName"/></span></div>
            </td>
            <td>
                <div>角色:<span id="d_role"></span></div>
            </td>
            <td>
                <div>座席组:<span id="d_workGrp"></span></div>
            </td>
            <td>
                <div>座席等级:<span id="d_employeeType"></span></div>
            </td>
            <td>
                <div>座席分机:<span id="d_dn">${sessionScope.cti_dn}</span></div>
            </td>

            <td id="isShowTotalOutcallTime">
                <div>当日外呼有效时长:<span id="span_intradayTotalOutcallTime"></span></div>
            </td>
            <td>
                <div id="dt"></div>
            </td>
            <td class="noborder">
                <div>版本:<span id="sales_version"></span></div>
            </td>

        </tr></table>

</div>
<div id="modifyPwdWin" class="easyui-window" data-options="collapsible:false,minimizable:false,maximizable:false,closed:true,modal:true,shadow:false,title:'修改密码',draggable:false" style="width:280px;height: 240px;"></div>
<!--inbound坐席：IN；outbound坐席：OUT-->
<input type="hidden" id="seatType" name="seatType" value=""/>
<!--加载软电话：true；不加载软电话：false-->
<input type="hidden" id="isLoadSoftPhone" name="isLoadSoftPhone" value="${sessionScope.isLoadCtiServer}"/>
<input type="hidden" id="analoglines" name="analoglines" />
<input type="hidden" id="ctiPhoneType" name="ctiPhoneType" value="${sessionScope.cti_phoneType}" />

<input type="hidden" id="d_csphone" name="d_csphone" value=""/>
<input type="hidden" id="d_address_check" name="d_address_check" value=""/>
<input type="hidden" id="d_address_check_ext" name="d_address_check_ext" value=""/>
</body>

</html>
