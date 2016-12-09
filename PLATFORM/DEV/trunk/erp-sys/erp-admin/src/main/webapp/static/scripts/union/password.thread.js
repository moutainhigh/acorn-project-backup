YAHOO.namespace("union");
YAHOO.namespace("union.password");



YAHOO.util.Event.onDOMReady(function () {
	YAHOO.union.password.updatePassword = function () {
		  var password = document.getElementById('password').value;
	      var newPassword = document.getElementById('newPassword').value;
	      var confirmPassword =  document.getElementById('confirmPassword').value;
	     if(password=='' || password==null){
	    	document.getElementById("oldpasswordnullDive").style.visibility = "visible";//显示
	      	return ;
	      }else if($("#oldPassword").val()=='5'){
	    	  return;
	      }else if(newPassword==''||newPassword==null){
	    	  document.getElementById("newpasswordnullDive").style.visibility = "visible";//显示
		      	return ;
	      }else if((newPassword.length<6 ||newPassword.length>8)||(newPassword ==password) ){
	    	  document.getElementById("newpasswordDive").style.visibility = "visible";//显示
	    	  return ;
	      }else if(confirmPassword==''||confirmPassword==null){
	    	  document.getElementById("confirmpasswordnullDive").style.visibility = "visible";//显示
		      	return ;
	      }else if(confirmPassword !=newPassword){
	    	  document.getElementById("confirmpasswordDive").style.visibility = "visible";//显示
	    	  return ;
	      }
	      var request = YAHOO.util.Connect.asyncRequest('POST', '/union/password',
	          {
	              success:function (o) {
	                  var resultJson = YAHOO.lang.JSON.parse(o.responseText);
	                  var result = resultJson['result'];
	                  var val = resultJson['val']; 
	                  if (result) {
	                  	 $("#password").val("");
	                  	 $("#newPassword").val("");
	                  	 $("#confirmPassword").val("");
	                  	 alert("修改成功！");
	                  }

	              },
	              failure:function (o) {
	              	 alert(o.statusText);
	              }
	          }, 'password=' + password + '&newPassword=' + newPassword + '&confirmPassword=' + confirmPassword);
	};
	
    //instance validation engine
    jQuery('#submitForm').validationEngine({
        promptPosition:'topRight',
        scroll: false
    });

   
    
    YAHOO.util.Event.on("save_btn_", "click", function () {
    	if (jQuery('#submitForm').validationEngine('validate')) {
   		 YAHOO.union.password.updatePassword();
        } else {
            //do nothing
        }
      });
    YAHOO.util.Event.on("newPassword", "focus", function () {
      	 document.getElementById("newpasswordnullDive").style.visibility = "hidden";
      	 document.getElementById("newpasswordDive").style.visibility = "hidden";
      });
   
    YAHOO.util.Event.on("password", "focus", function () {
    	 document.getElementById("oldpasswordnullDive").style.visibility = "hidden";
    	 document.getElementById("oldpasswordDive").style.visibility = "hidden";
    });
    YAHOO.util.Event.on("confirmPassword", "focus", function () {
   	 document.getElementById("confirmpasswordnullDive").style.visibility = "hidden";
   	 document.getElementById("confirmpasswordDive").style.visibility = "hidden";
   });
    YAHOO.util.Event.on("password", "blur", function () {
    	 var password = $("#password").val();
    	 if(password==null || password==''){
    		 document.getElementById("oldpasswordnullDive").style.visibility = "visible";//显示
    		 return ;
    	 }
    	$.ajax({
			  type: 'POST',
			  url: '/union/viewpassword?password='+password,				
			  success: function(response){	
              if (response.valid != 1) {
            	  document.getElementById("oldpasswordDive").style.visibility = "visible";//显示
            	  $("#oldPassword").val("5");
					return 5 ; 
				}else{
					 $("#oldPassword").val("6");
				}
			  }
		});
    });

});
