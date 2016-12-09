  function checkAccount(){
  	var account=document.getElementById("account").value;
  	var exception=/^[a-zA-Z][a-zA-Z0-9]{3,15}$/;
  	var excep=/^\s*|\s*$/;
  	if(account==""){      
  			document.getElementById("accountInfo").innerHTML="<font color='red'>网盟账户名不能为空</font>"
  			return 1;
  	}
  	if(account.length>15){
  			document.getElementById("accountInfo").innerHTML="<font color='red'>网盟账户名长度不能超过15位</font>"
  			return 2;
  	}
  	if(account.length<5){
  			document.getElementById("accountInfo").innerHTML="<font color='red'>网盟账户名长度不能小于5位</font>"
  			return 3;
  	}
		if(!exception.test(account)){
  			document.getElementById("accountInfo").innerHTML="<font color='red'>账号只能以字母开头由字母、数字组成</font>"
  			return 4;
  	}
	return 0 ;
  }

  //验证账号输入
  function checkAccountOnblur(){
  	var account=document.getElementById("account").value;
  	var exception=/^[a-zA-Z][a-zA-Z0-9]{3,15}$/;
  	if(account==""){      
  			document.getElementById("accountInfo").innerHTML="<font color='red'>网盟账户名不能为空</font>"
  			return 1;
  	}
  	if(account.length>15){
  			document.getElementById("accountInfo").innerHTML="<font color='red'>网盟账户名长度不能超过15位</font>"
  			return 2;
  	}
  	if(account.length<5){
  			document.getElementById("accountInfo").innerHTML="<font color='red'>网盟账户名长度不能小于5位</font>"
  			return 3;
  	}
	if(!exception.test(account)){
  			document.getElementById("accountInfo").innerHTML="<font color='red'>账号只能以字母开头由字母、数字组成</font>"
  			return 4;
  	}else{
			//document.getElementById("accountInfo").innerHTML="" ;
			$.ajax({
				  type: 'POST',
				  url: 'validate_username.action',
				  data: 'union.username='+account,
				  beforeSend: function() { 
				  },
				  success: function(response){
					if(response==""){
						$('#accountInfo').html("该帐户可以使用");
					}else{
						$('#accountInfo').html("<font color='red'>"+response+"</font>");
						return 5 ; 
					}
				  },
				  complete:function(xhr,ts){
				  },
				  error:function(){
					$('#accountInfo').html("ajax请求错误!");
				  }
			});
  	}	
  }
  
  //验证第一次密码输入
  function check_pwd1(){
  	var password1=document.getElementById("user.passWord").value;
  	var password1Info=document.getElementById("passWordInfo");
  	var pattern = /^\w+$/;
  	if(password1==""){
  		password1Info.innerHTML="<font color='red'>密码不能为空</font>";
  		return 1;
  	}
		if(password1.length>16){
			password1Info.innerHTML="<font color='red'>密码长度不能大于16位</font>";
			return 2;
		}
    if(password1.length<6){
    	password1Info.innerHTML="<font color='red'>密码长度不能小于6位</font>";
    	return 3;
    }
    if(!pattern.test(password1)){
    	password1Info.innerHTML="<font color='red'>密码只能由英文和数字组成</font>";
    	document.getElementById("user.passWord").value="";
    	return 4;
    	
    }else{
    	password1Info.innerHTML="";
    }
  	
  	return 0;
  }
  
  //验证第二次密码输入
  function check_pwd2(){
  	var password1=document.getElementById("user.passWord").value;
		var password2=document.getElementById("password2").value;  
		var password2Info=document.getElementById("password2Info");
		if(password2==""){
			password2Info.innerHTML="<font color='red'>确认密码不能为空</font>";
			return 1;
		}
		if(password1!=password2){
			password2Info.innerHTML="<font color='red'>两次输入的密码不匹配</font>";
			return 2;
		}
		return 0;
  }
  
  
  //验证邮箱输入
  function checkEmailOnBlur(){				    
  	var pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
  	var email=document.getElementById("user.email").value;
  	if(email==""){
  		document.getElementById("emailInfo").innerHTML="<font color='red'>邮箱不能为空</font>";
  		return 1;
  	}
  	if(!pattern.test(email)){
  		document.getElementById("emailInfo").innerHTML="<font color='red'>请输入正确的Email地址</font>";
  		return 2;
  	}
  	if(email.length>40){
  		document.getElementById("emailInfo").innerHTML="<font color='red'>邮箱长度不能超过40位</font>";
  		return 3;
  	}else{
			document.getElementById("emailInfo").innerHTML="" ;
			$.ajax({
				  type: 'POST',
				  url: 'validate_email.action',
				  data: 'union.email='+email,
				  beforeSend: function() { 
				  },
				  success: function(response){
					if(response==""){
						$('#emailInfo').html("该邮箱可以使用");
					}else{
						$('#emailInfo').html("<font color='red'>"+response+"</font>");
						return 4 ; 
					}
				  },
				  complete:function(xhr,ts){
				  },
				  error:function(){
					$('#emailInfo').html("ajax请求错误!");
				  }
			});
	}
  }  

  //验证网站名称
  function check_siteName(){
    var pattern = /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/;
  	var siteName=document.getElementById("user.siteName").value;
  	var siteNameInfo=document.getElementById("siteNameInfo");
  	if(siteName==""){
			siteNameInfo.innerHTML="<font color='red'>网盟名称不能为空</font>";
			return 1;
		}
  	var siteNameLen = siteName.replace(/[^\x00-\xff]/g, "***").length;
		if(siteNameLen>10){
			siteNameInfo.innerHTML="<font color='red'>网盟名称长度不能超过10个字符</font>";
			return 2;
		}
		if(!pattern.test(siteName)){
			siteNameInfo.innerHTML="<font color='red'>网盟名称只允许包含中文、数字、字母、下滑线</font>";
			return 3;
		}else{
			//document.getElementById("siteNameInfo").innerHTML="" ;
			$.ajax({
				  type: 'POST',
				  url: 'validate_unionName.action',
				  data: 'union.unionName='+siteName,
				  beforeSend: function() { 
				  },
				  success: function(response){
					  if(response==""){
						$('#siteNameInfo').html("该联盟名可以使用");
					}else{
						$('#siteNameInfo').html("<font color='red'>"+response+"</font>");
						return 4; 
					}
				  },
				  complete:function(xhr,ts){
				  },
				  error:function(){
					$('#siteNameInfo').html("ajax请求错误!");
				  }
			});
	}
  }
  	//====================================
    //验证网站地址
  function check_siteURl(){
  	var siteURl=document.getElementById("user.siteURl").value;
  	var siteURLInfo=document.getElementById("siteURLInfo");
  	var Expression=/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/; 
  	if(siteURl==""){
			siteURLInfo.innerHTML="<font color='red'>网站地址不能为空</font>";
			return 1;
		}
	if(siteURl.length>100){
		siteURLInfo.innerHTML="<font color='red'>网站地址长度不能 超过100位</font>";
		return 2;
	}
	if(!Expression.test(siteURl)){
		siteURLInfo.innerHTML="<font color='red'>请输入正确的网址如：www.chinadrtv.com</font>";
		return 3;
	}else{
		siteURLInfo.innerHTML="输入正确!"
	}
  }
  
    //验证联系人姓名
  function check_name(){
		var name=document.getElementById("user.name").value;
		var nameInfo=document.getElementById("nameInfo");
		var Expression=/[^\u4E00-\u9FA5]/; 
		var exception=/^[赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡凌霍虞万支柯昝管卢莫柯房裘缪干解应宗丁宣贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊于惠甄曲家封芮羿储靳汲邴糜松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫宁仇栾暴甘钭历戎祖武符刘景詹束龙叶幸司韶郜黎蓟溥印宿白怀蒲邰从鄂索咸籍赖卓蔺屠蒙池乔阳郁胥能苍双闻莘党翟谭贡劳逄姬申扶堵冉宰郦雍却璩桑桂濮牛寿通边扈燕冀浦尚农温别庄晏柴瞿阎充慕连茹习宦艾鱼容向古易慎戈廖庾终暨居衡步都耿满弘匡国文寇广禄阙东欧殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空曾毋沙乜养鞠须丰巢关蒯相查后荆红游竺权逮盍益桓公上赫皇澹淳太轩令宇长盖况闫].{1,5}$/;
		if(name==""){
			nameInfo.innerHTML="<font color='red'>联系人姓名不能为空</font>";
			return 1;
		}
		if(name.length>10){
			nameInfo.innerHTML="<font color='red'>联系人姓名长度不能超过10位</font>";
			return 2;
		}
		if(name.length<2){
			nameInfo.innerHTML="<font color='red'>联系人姓名长度不能小于2位</font>";
			return 3;
		}
		if(Expression.test(name)&& !exception.test(name)){
			nameInfo.innerHTML="<font color='red'>请输入正确的中文姓名</font>";
			return 4;
		}else{
			nameInfo.innerHTML="输入正确!"
		}
  }
  
 function checkPhoneAndMoblie(){
  	var mobile=document.getElementById("user.mobile").value;
  	var phone=document.getElementById("phone").value;
  	var phoneInfo=document.getElementById("phoneInfo");
  	var mobileInfo=document.getElementById("mobileInfo");
    var exce_moblie=/^1[3|4|5|8][0-9]\d{4,8}$/;
    var exce_phone=/^(\d{3,4}-)?\d{6,8}$/; 
    if(mobile=="" && phone==""){
    	return 1
    }
    if(mobile!="") {
    	if(!exce_moblie.test(mobile) || mobile.length<11){
				mobileInfo.innerHTML="<font color='red'>手机号码输入错误</font>";
				return 2;
			}else{
				mobileInfo.innerHTML="输入正确!";
				if(phone != ""){
			    	if(!exce_phone.test(phone)){
			    		phoneInfo.innerHTML="<font color='red'>请输入正确的电话号码如:021-86591234</font>";
						return 3;
			    	}else{
				    	phoneInfo.innerHTML="输入正确!";
				    	return 0;
			    	}
			    }
				return 0;
			}
    }
    if(phone != ""){
    	if(!exce_phone.test(phone)){
    		phoneInfo.innerHTML="<font color='red'>请输入正确的电话号码如:021-86591234</font>";
				return 3;
    	}else{
	    	phoneInfo.innerHTML="输入正确!";
	    	 if(mobile!=""){
			    	if(!exce_moblie.test(mobile) || mobile.length<11){
							mobileInfo.innerHTML="<font color='red'>手机号码输入错误</font>";
							return 2;
						}else{
							mobileInfo.innerHTML="输入正确!";
							return 0;
						}
			    }
	    	return 0;
    	}
    }
  
  }  

  function doSubmit(){
  	clearMessage();
  	var check_account=checkAccount();
  	if(check_account==1){alert("网盟账户名不能为空");return false;}
  	if(check_account==2){alert("网盟账户名长度不能超过15位"); return false;}
  	if(check_account==3){alert("网盟账户名长度不能小于5位");return false;}
  	if(check_account==4){alert("账号只能以字母开头由字母、数字组成");return false;}
	if(check_account==5){alert("账号已被使用");return false;}
  	var check_pwd1_rs=check_pwd1()
  	if(check_pwd1_rs==1){alert("密码不能为空");return false;}
  	if(check_pwd1_rs==2){alert("密码长度不能大于16位");return false;}
  	if(check_pwd1_rs==3){alert("密码长度不能小于6位");return false;}
  	if(check_pwd1_rs==4){alert("密码只能由英文和数字组成");return false;}
  	var check_pwd2_rs=check_pwd2();
  	if(check_pwd2_rs==1){alert("确认密码不能为空");return false;}
  	if(check_pwd2_rs==2){alert("两次输入的密码不匹配");return false;}
  	var check_siteName_rs=check_siteName();
  	if(check_siteName_rs==1){alert("网盟名称不能为空");return false;}
  	if(check_siteName_rs==2){alert("网盟名称长度不能超过10个字符");return false;}
  	if(check_siteName_rs==3){alert("网盟名称只允许包含中文、数字、字母、下滑线");return false;}
	if(check_siteName_rs==4){alert("网盟名称已被使用");return false;}
  	var check_email_rs=checkEmailOnBlur();
  	if(check_email_rs==1){alert("邮箱不能为空");return false;}
  	if(check_email_rs==2){alert("请输入正确的Email地址");return false;}
  	if(check_email_rs==3){alert("邮箱长度不能超过40位");return false;}
	if(check_email_rs==4){alert("邮箱名已被占用");return false;}
	var check_siteURL_rs=check_siteURl();
  	if(check_siteURL_rs==1){alert("网站地址不能为空");return false;}
  	if(check_siteURL_rs==2){alert("网站地址长度不能超过100位");return false;}
  	if(check_siteURL_rs==3){alert("请输入正确的网址如：www.chinadrtv.com");return false;}
	var check_name_rs=check_name();
  	if(check_name_rs==1){alert("联系人姓名不能为空");return false;}
  	if(check_name_rs==2){alert("联系人姓名长度不能超过10位");return false;}
  	if(check_name_rs==3){alert("联系人姓名长度不能小于2位");return false;}
  	if(check_name_rs==4){alert("请输入正确的中文姓名");return false;}
   	var checkPhoneAndMoblie_rs=checkPhoneAndMoblie();
    if(checkPhoneAndMoblie_rs==1){alert("请输入联系电话或手机");return false;}
    if(checkPhoneAndMoblie_rs==2){alert("手机号码输入错误");return false;}
    if(checkPhoneAndMoblie_rs==3){alert("请输入中国大陆地区正确的电话号码如：021-86591234 或0531-8695456");return false;}
  	document.getElementById("registForm").submit();
  }
  
	var currSiteId = "1";
	var siteName = "";
	if(currSiteId && currSiteId == 2){
		siteName="橡果国际";
	}else{
		siteName="橡果国际";
	}
	var REG_MSG = {
		ACCOUNTINFO:"长度大于4位，小于16位，由字母开头，可由字母和数字组成",
		PASSWORDINFO:"长度大于5位，小于16位",
		EMAILINFO:"该邮箱将用来接收"+siteName+"联盟的通知！"
	}
  
	function clearMessage(){
		$("#accountInfo").html(REG_MSG.ACCOUNTINFO);
		$("#passWordInfo").html(REG_MSG.PASSWORDINFO);
		$("#password2Info").html("");
		$("#siteNameInfo").html("");
		$("#emailInfo").html(REG_MSG.EMAILINFO);
	}
