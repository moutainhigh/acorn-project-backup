
;$(function(){
	
	//隐藏访问报表服务器的登录用户名
	var username = $("#r_username").val();
	$("#r_username").removeAttr("value");
	$("#r_username").val(username);
	
	//隐藏访问报表服务器的登录密码
	var password = $("#r_password").val();
	$("#r_password").removeAttr("value");
	$("#r_password").val(password);
	
});
