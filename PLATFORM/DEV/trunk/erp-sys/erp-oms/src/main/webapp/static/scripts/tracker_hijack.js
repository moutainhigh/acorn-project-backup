var refer=document.referrer?document.referrer:"";
if(refer!="")
    refer=encodeURIComponent(refer);
//alert(refer);
var pars=document.location.search;
//alert(pars);
//alert(pars.indexOf("?"));
//alert(pars.length>1);
var input=new Object();
if (pars && pars.indexOf("?")==0 && pars.length>1){
    //alert(pars);
    pars=pars.substr(1);
    //alert(pars);
    var list=pars.split("&");
    for(var n=0;n<list.length;n++){
        //alert(list[n]);
        var item=list[n].split("=");
        if(item.length==2){
            input[item[0]]=item[1];
        }
		
    }
}
//alert(input.tracker_u);
var tracker_u=input.tracker_u?input.tracker_u:"";
var tracker_type=input.tracker_type?input.tracker_type:"";
var tracker_pid=input.tracker_pid?input.tracker_pid:"";
var tracker_src=input.tracker_src?input.tracker_src:"";
var adgroupKeywordID=input.adgroupKeywordID?input.adgroupKeywordID:"";
trackerContainer.addParameter(new Parameter("adgroupKeywordID",adgroupKeywordID));
//alert(refer);
if(refer.indexOf("yihaodian.com")!=-1){
    refer="";
}

if(refer!="" && ""==tracker_u){
    tracker_type="0";
}





/********************构建网盟跟踪信息********************/
if (document.location.hostname=="yihaodian.house.sina.com") {
   trackerContainer.addParameter(new Parameter("tracker_u","44087"));
   trackerContainer.addParameter(new Parameter("tracker_type","1"));
  }
 else if(/^\/tianya/.test(document.location.pathname) || "tianya"==jQuery.cookie("ucocode")){
    trackerContainer.addParameter(new Parameter("tracker_u","46359"));
    trackerContainer.addParameter(new Parameter("tracker_type","1"));
    var x_expire_time= new Date((new Date()).getTime()).toGMTString();
    document.cookie = 'unionKey=' + ";expires="+x_expire_time+";domain=www.yihaodian.com;path=/";
}
else if(!jQuery.cookie("ucocode")||jQuery.cookie("ucocode")=="tencent"){
   
    var innerTracker=jQuery.cookie("innerTracker");
    if(innerTracker)
    {
        trackerContainer.addParameter(new Parameter("tracker_u",innerTracker));
        trackerContainer.addParameter(new Parameter("tracker_type","1"));
    }else
    {
  
        if (6258 == jQuery.cookie("unionKey")||181425 == jQuery.cookie("unionKey") || (4734 == jQuery.cookie("unionKey") && jQuery.cookie("LTINFO")) || 8363 == jQuery.cookie("unionKey") || 29070 == jQuery.cookie("unionKey")||44087== jQuery.cookie("unionKey") ||46359== jQuery.cookie("unionKey")||1787== jQuery.cookie("unionKey")||1786== jQuery.cookie("unionKey")||135503==jQuery.cookie("unionKey")||147819==jQuery.cookie("unionKey")||46359==jQuery.cookie("unionKey") || 409==jQuery.cookie("unionKey")||197057==jQuery.cookie("unionKey"))
            {
            trackerContainer.addParameter(new Parameter("tracker_u",jQuery.cookie("unionKey")));
            trackerContainer.addParameter(new Parameter("tracker_type","1"));
            }
        else if(7 == jQuery.cookie("unionType") && jQuery.cookie("unionKey"))
            {
            trackerContainer.addParameter(new Parameter("tracker_u",jQuery.cookie("unionKey")));
            trackerContainer.addParameter(new Parameter("tracker_type","7"));
            }
        else if(8 == jQuery.cookie("unionType")&& jQuery.cookie("unionKey"))
            {
            trackerContainer.addParameter(new Parameter("tracker_u",jQuery.cookie("unionKey")));
            trackerContainer.addParameter(new Parameter("tracker_type","8"));
            
            }
        else if(9 == jQuery.cookie("unionType")&& jQuery.cookie("unionKey"))
            {
            trackerContainer.addParameter(new Parameter("tracker_u",jQuery.cookie("unionKey")));
            trackerContainer.addParameter(new Parameter("tracker_type","9"));
            }
        else if(10 == jQuery.cookie("unionType")&& jQuery.cookie("unionKey"))
            {
            trackerContainer.addParameter(new Parameter("tracker_u",jQuery.cookie("unionKey")));
            trackerContainer.addParameter(new Parameter("tracker_type","10"));
            }
        else
           {
           trackerContainer.addParameter(new Parameter("tracker_u",tracker_u));
           trackerContainer.addParameter(new Parameter("tracker_type",tracker_type));
           trackerContainer.addParameter(new Parameter("tracker_first_link",refer));
           trackerContainer.addParameter(new Parameter("tracker_pid",tracker_pid));
           trackerContainer.addParameter(new Parameter("tracker_src",tracker_src));
            }
    }
}
/******************finish******************/


/**************************普通跟踪信息********************/
var info_refer=document.referrer?document.referrer:"";
if(info_refer!="")
    info_refer=encodeURIComponent(info_refer);
    
trackerContainer.addParameter(new Parameter("infoPreviousUrl",info_refer));//前一页地址
trackerContainer.addParameter(new Parameter("infoTrackerSrc",tracker_src));//页面来源
if(jQuery.cookie("yihaodian_uid"))
trackerContainer.addParameter(new Parameter("endUserId",jQuery.cookie("yihaodian_uid")));

var sendTrackerCookie="";
if(jQuery.cookie("msessionid"))
sendTrackerCookie="msessionid:"+jQuery.cookie("msessionid");
if(jQuery.cookie("uname"))
sendTrackerCookie+=",uname:"+jQuery.cookie("uname");
if(jQuery.cookie("unionKey"))
sendTrackerCookie+=",unionKey:"+jQuery.cookie("unionKey");
if(jQuery.cookie("unionType"))
sendTrackerCookie+=",unionType:"+jQuery.cookie("unionType");
if(jQuery.cookie("tracker"))
sendTrackerCookie+=",tracker:"+jQuery.cookie("tracker");if(jQuery.cookie("tracker"))
sendTrackerCookie+=",tracker:"+jQuery.cookie("tracker");
if(jQuery.cookie("LTINFO"))
sendTrackerCookie+=",LTINFO:"+jQuery.cookie("LTINFO");

trackerContainer.addParameter(new Parameter("cookie",sendTrackerCookie));///把cookie添加到trackerContainer中

/*trackerContainer.addStock("8486","1:34,56:45,67:56");
trackerContainer.addStock("4444","2:34,3:45,5:56");
trackerContainer.addCommonAttached("2","hello");*/



/**************************finish************************/
if(getQueryStringRegExp("fee")!=null){
trackerContainer.addParameter(new Parameter("fee",getQueryStringRegExp("fee")));
}

trackerContainer.addParameter(new Parameter("provinceId",jQuery.cookie("ip_province")));
trackerContainer.addParameter(new Parameter("cityId",jQuery.cookie("cityId")));


/**********************发送信息***************************/

function initHijack(){
	if(null!=jQuery.cookie("ip_province")&&'null'!=jQuery.cookie("ip_province")){
		jQuery.ajax({ 
		            async:false, 
		            url: trackerContainer.toUrl(),  
		            type: 'GET',   
		            dataType: 'jsonp', 
		            jsonp: 'jsoncallback'
		        });
	}
}
jQuery(document).ready(function(){
	if (1 == 1){
		initHijack();
	}
});
 
function callTracker(provinceId){
trackerContainer.addParameter(new Parameter("provinceId",provinceId));
trackerContainer.addParameter(new Parameter("cityId",jQuery.cookie("cityId")));
	jQuery.ajax({ 
            async:false, 
            url: trackerContainer.toUrl(),  
            type: 'GET',   
            dataType: 'jsonp', 
            jsonp: 'jsoncallback'
        });
}
