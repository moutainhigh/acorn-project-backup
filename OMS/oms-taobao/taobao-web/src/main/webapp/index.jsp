<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date, java.text.SimpleDateFormat, java.util.Calendar" %>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String currDate = sdf.format(new Date());
Calendar cal = Calendar.getInstance();
cal.add(Calendar.DATE, -1);
String beginDate = sdf.format(cal.getTime());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TaoBao Manual Import</title>
<script type="text/javascript" src="static/js/jquery-1.4.4.min.js"></script>
</head>
<body>

<p id="message"></p>
<p>
	开始时间： <input id="beginDate" type="text" value="<%=beginDate %>"/>
	结束时间: <input id="endDate" type="text" value="<%=currDate %>"/>
	<input type="button" value="导入" onclick="importOrder()">
</p>

<script type="text/javascript">
	function importOrder() {
		$.ajax({
			url: 'api/import',
			data: {
				'beginDate': $('#beginDate').val(), 
				'endDate': $('#endDate').val()
			},
			type: 'POST',
			cache: false,
			dataType: 'json',
			beforeSend: function() {
				$('#message').html('订单收录中，请稍候……');
			},
			success: function(rs) {
				if(rs.success) {
					$('#message').html('导入成功');
				}else{
					$('#message').html('导入失败: ' + rs.message);
					console.log('导入失败: ' + rs.message);
				}
			},
			error: function(xhr, msg) {
				$('#message').html('导入失败: ' + xhr.responseText);
			}
		});
	}
</script>
</body>
</html>