Array.prototype.toTRACKERJSONString=function()
{
var jsonStr="[";
for(var i=0;i<this.length;i++)
 {
    if(this[i] instanceof Parameter)
    {
    if(this[i].value instanceof Array)
      {
      jsonStr+="{"+this[i].key+"="+this[i].value.toTRACKERJSONString()+"}"+",";
      }
    else {
      jsonStr+=this[i].toJSONString()+",";
      }
    }
 }
if(jsonStr.indexOf(",")>0)
jsonStr=jsonStr.substring(0,jsonStr.length-1);
return jsonStr+"]";

}

var trackerUrl="";

///////////////////定义封装对象//////////////////////////
function Parameter(key,value)
{

this.key=key;
if(this.key=="internalKeyword"){
	this.value=encodeURI(value);
}else{
this.value=value;
}

this.toJSONString=function()
{
return "{"+this.key+"="+this.value+"}";
};

}


///////////////////定义封装需跟踪库存对象//////////////////////////

function TrackerContainer(url)
{
this.url=url;
this.parameterArray=new Array();
this.stockArray=new Array();
this.commonAttached=new Array();//普通的附加对象（key->value）


this.addParameter=function(parameter)
  {
  
  this.parameterArray.push(parameter);
  };
 this.addStock=function(productId,stockInfo) //增加库存
  {
   this.stockArray.push(new Parameter(productId,stockInfo));
  };
  this.addCommonAttached=function(key,value) //增加普通的附加信息
  {
  this.commonAttached.push(new Parameter(key,value));
  };



//构建附加信息
this.buildAttached=function()
{
 if(this.stockArray.length>0)
 this.commonAttached.push(new Parameter("1",this.stockArray));
 if(this.commonAttached.length>0)
 this.addParameter(new Parameter("attachedInfo", this.commonAttached.toTRACKERJSONString("attachedInfo")));//增加库存信息
}


this.toUrl=function()
  {
   this.buildAttached();
   for(var i=0;i<this.parameterArray.length;i++)
      {
      var key=this.parameterArray[i].key;
      var value=this.parameterArray[i].value;
      this.url+="&"+key+"="+value;
      }
    trackerUrl=this.url;
    return this.url;  
  };  
}

var trackerContainer=new  TrackerContainer("http://tracker.yihaodian.com/tracker/info.do?1=1");

trackerContainer.addParameter(new Parameter("ieVersion",navigator.appVersion));
trackerContainer.addParameter(new Parameter("platform",navigator.platform));

function clearTrackPositionToCookie(name,value){	
		var date = new Date();
    	date.setTime(date.getTime() - 10000);
    	document.cookie = name+"="+value+";path=/;expires=" + date.toGMTString();
		
}

function addTrackPositionToCookie(type,position){

	if(type=="1"){
		document.cookie="linkPosition="+position+";path=/";
	}else if(type=="2"){
		document.cookie="buttonPosition="+position+";path=/";
	}
}
function getCookie(name){
    var strCookie=document.cookie;
    var arrCookie=strCookie.split("; ");
    for(var i=0;i<arrCookie.length;i++){
        var arr=arrCookie[i].split("=");
        if(arr[0]==name)return arr[1];
    }
    return null;
}

var linkPosition="";
var buttonPosition="";

if(getCookie("linkPosition")!=null){
linkPosition=getCookie("linkPosition");
trackerContainer.addParameter(new Parameter("linkPosition",linkPosition));
clearTrackPositionToCookie("linkPosition",linkPosition);

}else if(getCookie("buttonPosition")!=null){
buttonPosition=getCookie("buttonPosition");

trackerContainer.addParameter(new Parameter("buttonPosition",buttonPosition));
clearTrackPositionToCookie("buttonPosition",buttonPosition);
}

function gotracker(type,position,productId){
var url=trackerUrl;
//清除之前点击记录的参数
var reg=new RegExp("&linkPosition=\\w*","g"); 
url=url.replace(reg,'');
var reg=new RegExp("&buttonPosition=\\w*","g");
url=url.replace(reg,'')
var reg=new RegExp("&productId=\\w*","g");
url=url.replace(reg,'')
if(type!=null){
if(type==2){
	url=url+"&buttonPosition="+position;		
}else{
	url=url+"&linkPosition="+position;
}
}
if(productId!=null){
	url=url+"&productId="+productId;
}
jQuery.ajax({ 
            async:true, 
            url: url,  
            type: 'GET',   
            dataType: 'jsonp', 
            jsonp: 'jsoncallback'
        });
}

