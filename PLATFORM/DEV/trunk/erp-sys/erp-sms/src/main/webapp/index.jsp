<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <script type="text/javascript" src="http://192.168.3.171:8080/static/scripts/jquery/jquery-1.7.2.js"></script>
</head>
<body>
  首页
<form action="http://localhost:8080/erp-sms/smsService/singleSmsSend" method="post">
<input type="hidden" name="creator"  value="cm" />
<input type="hidden" name="source"  value="test"/>
<input type="hidden" name="customerId"  value="123"/>
短信内容<input type="text" name="smsContent"  id="smsContent"/>
电话号码<input type="text"  id = "mobile"name="mobile"  />
部门号<input type="text" name="department" id="department"  value="8888"/>
主题<input type="text" name="theme" id="theme"  value="背背佳营销"/>
uuid<input type="text" name="uuid" id="uuid"/>
<input type="button"   onclick="sub()" value="发短信"/>
<input type="button"   onclick="sub2()" value="查询模板"/>
<input type="button"   onclick="sub3()" value="查询状态"/>
</form>
<script>
function sub () {
	var mobile = $("#mobile").val();
	var department = $("#department").val();
	var smsContent = $("#smsContent").val();
	$.post("smsService/singleSmsSend", {
		"creator" : "cm",
		"smsContent":smsContent,
		"source":"test",
		"customerId":"123",
		"mobile":mobile,
		"department":department
	},function(data) {
		alert(data.uuid);
	})	
}
function sub2 () {
	$.post("smsService/getTemplates", {
		"themeTemplate":"背背佳营销",
		"deptid":"8888"
	},function(data) {
		alert(data)
	})	
}
function sub3() {
	var uuid = $("#uuid").val();
	$.post("/getSmsByUuid/"+uuid, {
	},function(data) {		
	})	
}
</script>
</body>
</html>
