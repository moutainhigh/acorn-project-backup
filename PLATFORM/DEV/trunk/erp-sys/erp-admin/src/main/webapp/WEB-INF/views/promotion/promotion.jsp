<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>

    <title>促销规则管理</title>

    <script type="text/javascript" src="/static/scripts/jquery/locale/easyui-lang-zh_CN.js"></script>
    <link type="text/css" rel="stylesheet" href="/static/scripts/jquery/themes/icon.css"/>
    <link type="text/css" rel="stylesheet" href="/static/scripts/jquery/themes/default/easyui.css">
    <!--  
    <link rel="stylesheet" type="text/css" href="/static/scripts/assets/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="/static/scripts/assets/style.css" />
<link rel="stylesheet" type="text/css" href="/static/scripts/assets/prettify.css" />
<link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/ui-lightness/jquery-ui.css" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
<script type="text/javascript" src="/static/scripts/assets/jquery.multiselect.js"></script>
<script type="text/javascript" src="/static/scripts/assets/prettify.js"></script>
-->
<script type="text/javascript"
	src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
<script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.parser.js">
</script>
    <script type="text/javascript" src="/static/scripts/promotion/promotion.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery.json-2.4.js"></script>
    <script type="text/javascript" src="/static/scripts/gen_validatorv4.js"></script>
  
</head>
<body>
<form:form commandName="promotion" method="post" action="/admin/promotion" id="promotionForm"
           enctype="multipart/form-data">
    <form:hidden path="promotionid" id="promotionid"/>
    <form:hidden path="execOrder" id="execOrder"/>
    <form:hidden path="usedTimes" id="usedTimes"/>
     <form:hidden path="product" id="product"/>
    <input type="hidden" name="ruleOptions_json" id="ruleOptions_json"/>
    <input type="hidden" name="pproducts" id="pproducts"/>
    <c:set  var="promRuleID" value="${promotion.promotionRule.ruleId}" ></c:set>
    <table>
    <!--  
        <tr>
            <td colspan="2">&nbsp</td>
        </tr>
        -->
     
        <tr>
            <th><label for="channel">渠道:</label></th>
            <td>

        <input id="channel"  name="channel" value="${promotion.channel.id}" class="easyui-combobox" data-options="valueField: 'id',textField: 'name'" style="width:200px" />
            </td>
    
            <th><label for="rule">促销类型:</label></th>
            <td>
                <form:select path="promotionRule.ruleId"  id="rule" >
                    <form:option value="" label="请选择"/>
                    <form:options items="${promotionRules}" itemValue="ruleId" itemLabel="ruleName"/>
                </form:select>


            </td>
     
        </tr>
       
        <tr>
            <th><label for="name">名称:</label></th>
            <td>
                <form:input path="name" id="name" name="name" size="30"/>
            </td>
  
            <th><label for="description">描述:</label></th>
            <td>
                <form:input path="description" id="description" name="description" size="30"/>
            </td>
        </tr>

        <tr>
            <th><label for="startDate">开始时间:</label></th>
            <td>
                <form:input path="startDate" id="startDate" name="startDate" class="easyui-datetimebox"
                            size="50"/>
            </td>
            <th><label for="endDate">结束时间:</label></th>
            <td>
                <form:input path="endDate" id="endDate" name="endDate" class="easyui-datetimebox" size="50"/>
            </td>
        </tr>
       
        <tr>
            <th><label for="cumulative">可累积:</label></th>
            <td>
                <form:checkbox path="cumulative" id="cumulative" name="cumulative"/>
            </td>

            <th><label for="active">激活:</label></th>
            <td>
                <form:checkbox path="active" id="active" name="active"/>
            </td>
        </tr>
         <tr>
            <th><label for="maxuse">最大使用次数:</label></th>
            <td>
                <form:input path="maxuse" id="maxuse" name="maxuse" size="30"/> <label>大于0的数字表示促销可被使用次数，-1表示不限次数。</label>
            </td>
        </tr>
        <tr>
            <th><label for="calcRound">计算批次:</label></th>
            <td>
                <form:input path="calcRound" id="calcRound" name="calcRound" size="30"/><label>与实际支付金额相关的促销如送积分请填2，其余填1。</label>
            </td>
        </tr>
    </table>
</form:form>
<script language="JavaScript" type="text/javascript"
        xml:space="preserve">//<![CDATA[
var frmvalidator = new Validator("promotionForm");
// frmvalidator.EnableOnPageErrorDisplaySingleBox();
// frmvalidator.EnableMsgsTogether();
frmvalidator.addValidation("name", "req", "请输入名称");
frmvalidator.addValidation("description", "req", "请输入描述");
frmvalidator.addValidation("maxuse", "req", "请输入最大使用次数，-1表示不限次数");
frmvalidator.addValidation("calcRound", "req", "计算批次请输入1或2.");
//]]></script>

<div>
    <div id="new-promotion-div">
        <input type="button" id="returnpromotionListBtn" name="returnpromotionListBtn" value="返回列表">
        &nbsp;
        <input type="button" name="save_promotion_btn" value="保存" id="save_promotion_btn"/>

      
    <div id="toolbar">
        名称:<input id="pluname" name="pluname" type="text" size="15"></input>
        商品编码:<input id="catcode" name="catcode" type="text" size="15"></input>
    	<a href="javascript:void(0)" class="easyui-linkbutton"  plain="true" iconCls="icon-search" onclick="queryPlubasInfo()">查询</a>
    
    	<a href="javascript:void(0)" class="easyui-linkbutton"  plain="true" iconCls="icon-add" onclick="addGift()">添加<c:if test="${promRuleID ==2 }">赠品</c:if><c:if test="${promRuleID ==9 }">搭品</c:if>
    	</a>
    

		<a href="javascript:void(0)" class="easyui-linkbutton"  plain="true" iconCls="icon-add" onclick="addReject()">添加限制产品</a>
	</div>
    
    </div>

    	<div style="height:700px;border:1px solid #ccc;">
    	<c:if test="${promRuleID == 2 || promRuleID == 9 }">
		<div id="p" class="easyui-panel" title="促销产品配置" style="height:400px;padding:5px;"
				data-options="iconCls:'icon-save',collapsible:true,minimizable:false,maximizable:false,closable:false">
		<div class="easyui-layout" data-options="fit:true">
		      <div data-options="region:'west',tools:'#tt1'"   title='<c:if test="${promRuleID == 2}" >赠品</c:if><c:if test="${promRuleID == 9}" >搭品</c:if>' style="width:170px">  
               <select name="giftList" id='giftList' multiple='multiple' size='8' >  
        	   </select>  
        	   
            </div>  
            <div data-options="region:'center'" style="width:330px;padding:0px">  
              <table id="listPlubasInfoTable" cellspacing="0" cellpadding="0"  toolbar="#toolbar">
             </table>
            </div>
              <div data-options="region:'east',tools:'#tt2'"  title="限制产品" style="width:170px;">  
               <select name="rejectList" id='rejectList' multiple='multiple' size='8' >  		
        	   </select>  
            </div>
         <div id="tt1" >
		<a href="javascript:void(0)"  class="icon-remove"  onclick="javascript:delete_gift()"></a>
		
		</div>
		<div id="tt2" style="margin-left:50px;">
		<a href="javascript:void(0)"  class="icon-remove" onclick="javascript:delete_reject()"></a>
		</div>
           </div>
		
           
            

		</div>
		   </c:if>
		<div id="p" class="easyui-panel" title="促销参数设置" style="height:510px;padding:10px;"
				data-options="iconCls:'icon-save',collapsible:true,minimizable:false,maximizable:false,closable:false">
			<div id="p_ruleOptions" style="height: 300px">
                </div>
		</div>
	</div>
</div>

</body>
</html>
