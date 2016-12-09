$(document).ready(function() {
    
	 $("#showCode").click(function(){
		 var patternUrl = /^http[s]?\:\/\/[a-zA-Z0-9\-]+(\.[a-zA-Z0-9\-]+)+/;
         var txturl = $("#txturl").val();
         var unionId = $("#unionId").val();
         var tar_url;
         //tar_url=".yihaodian.com";
         tar_url="chinadrtv.com";
         if (txturl.indexOf(tar_url)==-1) {
             alert("请填写正确的目标URL链接，目标地址必须是包含'"+tar_url+"'的链接！");
             return false;
         }

         var txttext = $("#txttext").val();
         
         var targetUrl ,baseUrl;
         if(txturl.indexOf('?')<0){
             targetUrl=txturl+'?track_u='+unionId+'&url=/home';
         }else{
             targetUrl=txturl+'&track_u='+unionId+'&url=/home';
         }

         var code;
         if ($("#selectType").val() == "text") {
             if($("#txttext").val()==""){
                 alert("文字内容不能为空！");return false;
             }else if($("#txttext").val().length>50){
                 alert("文字内容长度不能超过50个字符！");return false;
             }
             code = "<a href=\"" + targetUrl + "\" target=\"_blank\">" + txttext + "</a>";
             $("#urlThd").val(txttext+"</a>");
         } else {
             if (!patternUrl.test(txttext)) {
                 alert("请填写正确的图片URL链接，链接地址由 http:// 开头！");
                 return false;
             }
             code = "<a href=\"" + targetUrl + "\" target=\"_blank\"><img src=\"" + txttext + "\" border=\"0\" /></a>";
             $("#urlThd").val("<img src=\"" + txttext + "\" border=\"0\" /></a>");
         }
         $("#lblPreview").html(code);
         $("#picLink").val(code);
         $("#urlOne").val("<a href=\"http://www.chinadrtv.com/union/acorn?track_u="+ unionId);
         $("#urlTwo").val("url=/home\"  target=\"_blank\">");
        
         $(".pic_preview").show();
	 });
	
	
    $("#copy_url_save").click(function(){
    	 var txt=document.getElementById("picLink").value;
		 var unionId=document.getElementById("unionId").value;
		 var urlOne=document.getElementById("urlOne").value;
	   	 var urlTwo=document.getElementById("urlTwo").value;
	   	 var urlThd=document.getElementById("urlThd").value;
		 if(txt==''){
	      	return ;
	      }
		 if(txt==''){
	         	return ;
	         }
	         //----------------------
	         $.ajax({ 
	      	   type: "POST", 
	       	   url: "/union/link/save", 
	       	  data: "urlOne=" + urlOne +"&urlTwo="+urlTwo+"&urlThd="+urlThd+"&unionId="+unionId,
	       	   success: function(o){
	                    if(o.result){
	                   	 alert("成功");
	                   	 document.location.reload();
	                  }
	      	       } 
	       	   }); 
     
   	});

   $("#copy_url_btn").click(function(){
	   var txt=document.getElementById("picLink").value;
       var tempval = $("#picLink");
       tempval.focus();
       tempval.select();
       if(window.clipboardData) {
           window.clipboardData.setData("Text", txt);
           alert("广告代码复制成功！");
       } else if(navigator.userAgent.indexOf("Opera") != -1) {
           window.location = txt;
           alert("广告代码复制成功！");
       } else if (window.netscape) {
           try {
               netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
           } catch (e) {
               alert("您使用的浏览器是FireFox!\n复制到粘贴板的操作被浏览器拒绝！您可以直接复制广告代码或使用非fireFox浏览器\n您也可以这样设置您的fireFox\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
           }
           var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
           if (!clip)
               return;
           var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
           if (!trans)
               return;
           trans.addDataFlavor('text/unicode');
           var str = new Object();
           var len = new Object();
           var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
           var copytext = txt;
           str.data = copytext;
           trans.setTransferData("text/unicode",str,copytext.length*2);
           var clipid = Components.interfaces.nsIClipboard;
           if (!clip)
               return false;
           clip.setData(trans,null,clipid.kGlobalClipboard);
           alert("广告代码复制成功！");
       }
     });
});   
  

