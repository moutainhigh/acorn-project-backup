function getQueryStringRegExp(name)
{
    var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");
    if (reg.test(location.href)) return unescape(RegExp.$2.replace(/\+/g, " ")); return "";
}

var ref=getQueryStringRegExp("tracker_u");
var uid=getQueryStringRegExp("uid");
var websiteid=getQueryStringRegExp("website_id");
var utype=getQueryStringRegExp("tracker_type");
                      

//alert("uid:"+uid);
var expire_time = new Date((new Date()).getTime() + 30 * 24 * 3600000).toGMTString();
var expire_time2= new Date((new Date()).getTime() + 30 * 24 * 3600000).toGMTString();
var expire_time3= new Date((new Date()).getTime()).toGMTString();
var expire_time_wangmeng= new Date((new Date()).getTime() + 1 * 24 * 3600000).toGMTString();
if(ref){
	if(ref!='')
	{    
	    document.cookie = 'unionKey=' + ref+";expires="+expire_time_wangmeng+";domain="+hostUrl+";path=/";	   
	}

}

if(utype){
	if(utype!=''){
		 document.cookie = 'unionType=' + utype+";expires="+expire_time2+";domain="+hostUrl+";path=/";
	}
}
if(uid)
{
	//alert(uid+"ok");
    document.cookie = 'uid=' + uid+";expires="+expire_time+";domain="+hostUrl+";path=/";
    //alert(uid+"okover");
}
//if(ref==141160||ref==127285)
//{
//    document.cookie = 'innerTracker=' + ref+";expires="+expire_time+";domain=www.yihaodian.com;path=/";
//}
if(websiteid)
{
    document.cookie = 'websiteid=' + websiteid+";expires="+expire_time+";domain="+hostUrl+";path=/";
}

//if(/^\/invite/.test(document.location.pathname)){
//	document.cookie = 'unionKey=' + ";expires="+expire_time3+";domain=www.yihaodian.com;path=/";
//}