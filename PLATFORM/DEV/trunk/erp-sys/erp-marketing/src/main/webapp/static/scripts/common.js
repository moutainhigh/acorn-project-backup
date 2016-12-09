   function get_time()
  {
    var date=new Date();
    var year="",month="",day="",week="",hour="",minute="",second="";
	var timetable=""; 
    year=date.getYear();
    month=add_zero(date.getMonth()+1);
    day=add_zero(date.getDate());
    week=date.getDay();
    switch (date.getDay()) {
    case 0:val="\u661f\u671f\u5929";break
    case 1:val="\u661f\u671f\u4e00";break
    case 2:val="\u661f\u671f\u4e8c";break
    case 3:val="\u661f\u671f\u4e09";break
    case 4:val="\u661f\u671f\u56db";break
    case 5:val="\u661f\u671f\u4e94";break
    case 6:val="\u661f\u671f\u516d";break
      }
    hour=add_zero(date.getHours());
    minute=add_zero(date.getMinutes());
    second=add_zero(date.getSeconds());
    timetable.innerText=" "+year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+" "+val;
  }
  
  function add_zero(temp)
  {
    if(temp<10) return "0"+temp;
    else return temp;
  }
  
  setInterval("get_time()",1000);

	function WenHou()
  	{
	  var wenhou="";
	  now = new Date(),hour = now.getHours() 
	if(hour < 6)wenhou="\u51cc\u6668\u597d\uff01";
	else if (hour < 9)wenhou="\u65e9\u4e0a\u597d\uff01";
	else if (hour < 12)wenhou="\u4e0a\u5348\u597d\uff01";
	else if (hour < 14)wenhou="\u4e2d\u5348\u597d\uff01";
	else if (hour < 17)wenhou="\u4e0b\u5348\u597d\uff01";
	else if (hour < 19)wenhou="\u508d\u665a\u597d\uff01";
	else if (hour < 22)wenhou="\u665a\u4e0a\u597d\uff01";
	wenhouyu.innerText=wenhou;
 	}

		function add_event(tr){
			tr.onmouseover = function(){
				tr.className += ' hover';
			};
			tr.onmouseout = function(){
				tr.className = tr.className.replace(' hover', '');
			};
		}

		function stripe(table) {
			var trs = table.getElementsByTagName("tr");
			for(var i=1; i<trs.length; i++){
				var tr = trs[i];
				tr.className = i%2 != 0? 'odd' : 'even';
				add_event(tr);
			}
		}

		window.onload = function(){
			var tables = document.getElementsByTagName('table');
			for(var i=0; i<tables.length; i++){
				var table = tables[i];
				if(table.className == 'datagrid1' || table.className == 'datagrid2'
					|| table.className == 'datagrid3' || table.className == 'datagrid4'){
					stripe(tables[i]);
				}
			}
		}

var MyWin = new Win();
var imgfile = "../images/";
var imgname=new Array();
var img = new Array();

imgname[0] = imgfile+"clo.gif";
imgname[1] = imgfile+"b1.png";
imgname[2] = imgfile+"l1.png";
imgname[3] = imgfile+"l2.png";
imgname[4] = imgfile+"r1.png";
imgname[5] = imgfile+"r2.png";
imgname[6] = imgfile+"t1.png";
imgname[7] = imgfile+"t2.png";
imgname[8] = imgfile+"t3.png";

for (i=0;i<=imgname.length-1;i++)
{
   img[i] = new Image();
   img[i].src = imgname[i];
}
var zIndex = 0;
var Winid  = 0;
var Ie = /msie/i.test(navigator.userAgent);
function $(Id){return(document.getElementById(Id))}
function Win()
{
	this.Create = function(mask,title, wbody, w, h, l, t)
 {
  Winid++;
  mask = mask;
  title = title || "\u52a0\u8f7d\u4e2d......";
  wbody = wbody || " <p align='center'>\u6b63\u5728\u8f7d\u5165......</p>";
  w = w || 350;
  h = h || 150;
  cw = document.documentElement.clientWidth;
  ch = document.documentElement.clientHeight;
  sw = document.documentElement.scrollWidth;
  sh = document.documentElement.scrollHeight;
        st = (document.documentElement.scrollTop || document.body.scrollTop);
  if (w > cw)
  ww = 0;
  else
  ww = (cw - w)/2;
  if (h > ch)
  hh = 0;
  else
  hh = (st + (ch - h)/2);
  l = l || ww;
  t = t || hh;
        if (mask != "no"){
    var ndiv = document.createElement("DIV");
    ndiv.setAttribute("id", "ndiv"+ Winid);
    ndiv.style.cssText = "width:"+ sw +"px;height:"+ sh +"px;left:0px;top:0px;position:absolute;overflow:hidden;background:#999;filter:alpha(opacity=80); opacity:0.2;-moz-opacity:0.2;";
    document.body.appendChild(ndiv);
    if (Ie)
    {
    var niframe = document.createElement("iframe");
    niframe.style.width = sw;
    niframe.style.height = sh;
          niframe.style.top = "0px";
          niframe.style.left = "0px";
          niframe.style.visibility = "inherit";
          niframe.style.filter = "alpha(opacity=0)";
          niframe.style.position = "absolute";
          niframe.style.zIndex = -1;
    ndiv.insertAdjacentElement("afterBegin",niframe);
    }
        }
  var mywin = document.createElement("DIV");
  mywin.setAttribute("id", "win"+ Winid);
  mywin.style.cssText = "width:"+ w +"px;height:"+ h +"px;left:0px;top:0px;position:absolute;overflow:hidden;padding:0px;font-family:Arial, 锟斤拷锟斤拷";
  mywin.style.zIndex = ++zIndex;
  document.body.appendChild(mywin);
  var mytie = document.createElement("DIV");
  var myboy = document.createElement("DIV");
  var mybom = document.createElement("DIV");
  mytie.style.cssText = "overflow:hidden;height:30px;font-weight:bold;font-size:14px;width:100%";
  myboy.style.cssText = "overflow:hidden;width:100%";
  mybom.style.cssText = "overflow:hidden;height:30px;width:100%";
  mywin.appendChild(mytie);
  mywin.appendChild(myboy);
  mywin.appendChild(mybom);
  var wintag = [[mytie, 30, 15, "t1"], [mytie, 30, w-32, "t2"], [mytie, 30, 15, "t3"], [myboy, h-45, 15, "l1"], [myboy, h-47, w-32], [myboy, h-45, 15, "r1"], [mybom, 15, 15, "l2"], [mybom, 15, w-32, "b1"], [mybom, 15, 15, "r2"]];
  for (var i = 0; i < 9; i++)
  {
   var temp = document.createElement("DIV");
   temp.setAttribute("Fid", "win"+ Winid);
   wintag[i][0].appendChild(temp);
      if (wintag[i][3])
   {
    temp.style.cssText = "float:left;height:"+ wintag[i][1] +"px;width:"+ wintag[i][2] +"px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+imgfile+""+ wintag[i][3] +".png', sizingMethod='scale');background:url('"+imgfile+""+ wintag[i][3] +".png') !important;background:;";
   }
   else
   {
    temp.style.cssText = "float:left;filter:alpha(Opacity=95,style=0);opacity:0.95;height:"+ wintag[i][1] +"px;width:"+ wintag[i][2] +"px;background:#f7f7f7;border:1px solid #666;overflow:hidden;padding:0px";
   }
  }
  mytie.childNodes[1].innerHTML = "<div style=\"position:absolute;overflow:hidden;height:15px;top:12px;padding-left:4px;padding-right:4px;\"></div><div style=\"position:absolute;background:url('"+imgfile+"clo.gif');overflow:hidden;width:43px;height:17px;top:7px !important;right:15px\" title=\"\" onclick=\"MyWin.Close('win"+ Winid +"',5); MyWin.ndiv('ndiv"+ Winid +"',5);\"></div>";
  this.Title("win"+ Winid, title);
  this.Body("win"+ Winid, wbody);
  this.Move_e("win"+ Winid, l, t, 0, 0);
  return(mywin);
 }
 this.Title = function(Id, title)
 {
     if (Id == null) return;
     var o = $(Id);
     if (!o) return;
     o.childNodes[0].childNodes[1].childNodes[0].innerHTML = title;
 }
 this.Body = function(Id, wbody)
 {
     if (Id == null) return;
     var o = $(Id);
        if (!o) return;
        if (wbody.slice(0, 4) == "[pg]")
            o.childNodes[1].childNodes[1].innerHTML = "<iframe onfocus=\"MyWin.Show('"+ Id +"',this)\" src='"+ wbody.slice(4) +"' frameBorder='0' marginHeight='0' marginWidth='0' scrolling='no' width='100%' height='100%'></iframe>";
        else
            o.childNodes[1].childNodes[1].innerHTML = wbody;
 }
 this.Show = function(Id)
    {
     if (Id == null) return;
     var o = $(Id);
        if (!o) return;
     o.style.zIndex = ++zIndex;
    }
    this.Move_e = function(Id, l , t, ll, tt)
    {
     if (typeof(window["ct"+ Id]) != "undefined") clearTimeout(window["ct"+ Id]);
     var o = $(Id);
     if (!o) return;
      o.style.left = l +"px";
      o.style.top = t +"px";
     window["ct"+ Id] = window.setTimeout("MyWin.Move_e('"+ Id +"', "+ l +" , "+ t +", "+ ll +", "+ tt +")", 1);
    }
    this.Close = function(Id, Opacity)
    {
     if (typeof(window["et"+ Id]) != "undefined") clearTimeout(window["et"+ Id]);
     var o = $(Id);
     if (!o) return;
     if (Opacity == 10) o.childNodes[0].childNodes[1].innerHTML = "";
     if (Ie)
     {
      o.style.filter = "alpha(opacity="+ Opacity +",style=0)";
     }
     else
     {
      o.style.opacity = Opacity / 10;
     }
     if (Opacity > 20)
      Opacity -= 10;
     else
      Opacity--;
     if (Opacity <= 0)
     {
         if (o.getElementsByTagName("IFRAME").length != 0)
         {
             o.getElementsByTagName("IFRAME").src = "about:blank";
         }
         o.innerHTML = "";
      document.body.removeChild(o);
      return;
     }
     window["et"+ Id] = window.setTimeout("MyWin.Close('"+ Id +"', "+ Opacity +")", 1);
    }
    this.ndiv = function(Id, Opacity)
    {
     var o = $(Id);
     if (!o) return;
     o.innerHTML = "";
  document.body.removeChild(o);
  return;
    }
}
function  abc(url){
parent.frames["mainFrame"].document.location=url;
}

// tab锟叫伙拷
	function setTab03Syn ( i )
	{
		selectTab03Syn(i);
	}
	
	function selectTab03Syn ( i )
	{
		switch(i){
			case 1:
			document.getElementById("TabCon1").style.display="block";
			document.getElementById("TabCon2").style.display="none";
			document.getElementById("font1").style.color="#ffffff";
			document.getElementById("font2").style.color="#000000";
			break;
			case 2:
			document.getElementById("TabCon1").style.display="none";
			document.getElementById("TabCon2").style.display="block";
			document.getElementById("font1").style.color="#000000";
			document.getElementById("font2").style.color="#ffffff";
			break;
		}
	}

// tab锟叫伙拷
	function setTab09Syn ( i )
	{
		selectTab09Syn(i);
	}
	
	function selectTab09Syn ( i )
	{
		switch(i){
			case 1:
			document.getElementById("TabCon3").style.display="block";
			document.getElementById("TabCon4").style.display="none";
			document.getElementById("fonta").style.color="#ffffff";
			document.getElementById("fontb").style.color="#000000";
			break;
			case 2:
			document.getElementById("TabCon3").style.display="none";
			document.getElementById("TabCon4").style.display="block";
			document.getElementById("fonta").style.color="#000000";
			document.getElementById("fontb").style.color="#ffffff";
			break;
		}
	}
	
// tab锟叫伙拷
	function setTab01Syn ( i )
	{
		selectTab01Syn(i);
	}
	
	function selectTab01Syn ( i )
	{
		switch(i){
			case 1:
			document.getElementById("TabCon1").style.display="block";
			document.getElementById("TabCon2").style.display="none";
			document.getElementById("TabCon3").style.display="none";
			document.getElementById("TabCon4").style.display="none";
			document.getElementById("TabCon5").style.display="none";
			document.getElementById("font1").style.color="#ffffff";
			document.getElementById("font2").style.color="#000000";
			document.getElementById("font3").style.color="#000000";
			document.getElementById("font4").style.color="#000000";
			document.getElementById("font5").style.color="#000000";
			break;
			case 2:
			document.getElementById("TabCon1").style.display="none";
			document.getElementById("TabCon2").style.display="block";
			document.getElementById("TabCon3").style.display="none";
			document.getElementById("TabCon4").style.display="none";
			document.getElementById("TabCon5").style.display="none";
			document.getElementById("font1").style.color="#000000";
			document.getElementById("font2").style.color="#ffffff";
			document.getElementById("font3").style.color="#000000";
			document.getElementById("font4").style.color="#000000";
			document.getElementById("font5").style.color="#000000";
			break;
			case 3:
			document.getElementById("TabCon1").style.display="none";
			document.getElementById("TabCon2").style.display="none";
			document.getElementById("TabCon3").style.display="block";
			document.getElementById("TabCon4").style.display="none";
			document.getElementById("TabCon5").style.display="none";
			document.getElementById("font1").style.color="#000000";
			document.getElementById("font2").style.color="#000000";
			document.getElementById("font3").style.color="#ffffff";
			document.getElementById("font4").style.color="#000000";
			document.getElementById("font5").style.color="#000000";
			break;
			case 4:
			document.getElementById("TabCon1").style.display="none";
			document.getElementById("TabCon2").style.display="none";
			document.getElementById("TabCon3").style.display="none";
			document.getElementById("TabCon4").style.display="block";
			document.getElementById("TabCon5").style.display="none";
			document.getElementById("font1").style.color="#000000";
			document.getElementById("font2").style.color="#000000";
			document.getElementById("font3").style.color="#000000";
			document.getElementById("font4").style.color="#ffffff";
			document.getElementById("font5").style.color="#000000";
			break;
			case 5:
			document.getElementById("TabCon1").style.display="none";
			document.getElementById("TabCon2").style.display="none";
			document.getElementById("TabCon3").style.display="none";
			document.getElementById("TabCon4").style.display="none";
			document.getElementById("TabCon5").style.display="block";
			document.getElementById("font1").style.color="#000000";
			document.getElementById("font2").style.color="#000000";
			document.getElementById("font3").style.color="#000000";
			document.getElementById("font4").style.color="#000000";
			document.getElementById("font5").style.color="#ffffff";
			break;
		}
	}

// 薪锟斤拷支锟斤拷
function setTab02Syn ( i )
	{
		selectTab02Syn(i);
	}
	
	function selectTab02Syn ( i )
	{
		switch(i){
			case 1:
			document.getElementById("TabCon1").style.display="block";
			document.getElementById("TabCon2").style.display="none";
			document.getElementById("TabCon3").style.display="none";
			document.getElementById("TabCon4").style.display="none";
			document.getElementById("font1").style.color="#ffffff";
			document.getElementById("font2").style.color="#000000";
			document.getElementById("font3").style.color="#000000";
			document.getElementById("font4").style.color="#000000";
			break;
			case 2:
			document.getElementById("TabCon1").style.display="none";
			document.getElementById("TabCon2").style.display="block";
			document.getElementById("TabCon3").style.display="none";
			document.getElementById("TabCon4").style.display="none";
			document.getElementById("font1").style.color="#000000";
			document.getElementById("font2").style.color="#ffffff";
			document.getElementById("font3").style.color="#000000";
			document.getElementById("font4").style.color="#000000";
			break;
			case 3:
			document.getElementById("TabCon1").style.display="none";
			document.getElementById("TabCon2").style.display="none";
			document.getElementById("TabCon3").style.display="block";
			document.getElementById("TabCon4").style.display="none";
			document.getElementById("font1").style.color="#000000";
			document.getElementById("font2").style.color="#000000";
			document.getElementById("font3").style.color="#ffffff";
			document.getElementById("font4").style.color="#000000"
			break;
			case 4:
			document.getElementById("TabCon1").style.display="none";
			document.getElementById("TabCon2").style.display="none";
			document.getElementById("TabCon3").style.display="none";
			document.getElementById("TabCon4").style.display="block";
			document.getElementById("font1").style.color="#000000";
			document.getElementById("font2").style.color="#000000";
			document.getElementById("font3").style.color="#000000";
			document.getElementById("font4").style.color="#ffffff";
			break;
		}
	}



// 锟斤拷锟斤拷织锟斤拷系锟叫伙拷
	function setTabSyn ( i )
	{
		selectTabSyn(i);
	}
	
	function selectTabSyn ( i )
	{
		switch(i){
			case 1:
			document.getElementById("TabCon1").style.display="block";
			document.getElementById("TabCon2").style.display="none";
			// document.getElementById("TabCon3").style.display="none";
			document.getElementById("font1").style.color="#075587";
			document.getElementById("font2").style.color="#000000";
			// document.getElementById("font3").style.color="#000000";
			break;
			case 2:
			document.getElementById("TabCon1").style.display="none";
			document.getElementById("TabCon2").style.display="block";
			// document.getElementById("TabCon3").style.display="none";
			document.getElementById("font1").style.color="#000000";
			document.getElementById("font2").style.color="#075587";
			// document.getElementById("font3").style.color="#000000";
			break;
			// case 3:
			// document.getElementById("TabCon1").style.display="none";
			// document.getElementById("TabCon2").style.display="none";
			// document.getElementById("TabCon3").style.display="block";
			// document.getElementById("font1").style.color="#000000";
			// document.getElementById("font2").style.color="#000000";
			// document.getElementById("font3").style.color="#075587";
			// break;
		}
	}
// 锟斤拷锟斤拷织锟斤拷系锟叫伙拷


// 锟斤拷锟斤拷锟斤拷撕殴锟斤拷锟�
	function setTab001Syn ( i )
	{
		selectTab001Syn(i);
	}
	
	function selectTab001Syn ( i )
	{
		switch(i){
			case 1:
			document.getElementById("TabCon1").style.display="block";
			document.getElementById("TabCon2").style.display="none";
			document.getElementById("TabCon3").style.display="none";
			document.getElementById("font1").style.color="#FFFFFF";
			document.getElementById("font2").style.color="#000000";
			document.getElementById("font3").style.color="#000000";
			break;
			case 2:
			document.getElementById("TabCon1").style.display="none";
			document.getElementById("TabCon2").style.display="block";
			document.getElementById("TabCon3").style.display="none";
			document.getElementById("font1").style.color="#000000";
			document.getElementById("font2").style.color="#FFFFFF";
            document.getElementById("font3").style.color="#000000";
			break;
			case 3:
			document.getElementById("TabCon1").style.display="none";
			document.getElementById("TabCon2").style.display="none";
			document.getElementById("TabCon3").style.display="block";
			document.getElementById("font1").style.color="#000000";
			document.getElementById("font2").style.color="#000000";
			document.getElementById("font3").style.color="#FFFFFF";
			break;
		}
	}

// 锟斤拷锟斤拷锟叫憋拷锟铰既★拷头锟窖�
function CheckAll(value,obj)  {
	var form=document.getElementsByTagName("form")
 	for(var i=0;i<form.length;i++){
		for (var j=0;j<form[i].elements.length;j++){
			if(form[i].elements[j].name!="checkbox"){ 
			var e = form[i].elements[j]; 
			e.checked=!e.checked;
			// if (value=="selectAll"){e.checked=obj.checked}
			// else{e.checked=!e.checked;}
			   }
		}
 	}
}

// 锟斤拷锟斤拷删锟斤拷锟叫讹拷只锟斤拷选锟斤拷一锟斤拷锟斤拷锟�
function DelSite(){
	var num=0;
	var form=document.getElementsByTagName("form")
 	for(var i=0;i<form.length;i++){
		for (var j=0;j<form[i].elements.length;j++){
			if(form[i].elements[j].type=="checkbox"){ 
			var e = form[i].elements[j]; 
			if (e.checked)num++;    
			}
		}
 	}
	if(num==0){
		alert("\u8bf7\u9009\u62e9\u4e00\u6761\u8bb0\u5f55\uff01");
	}else if(num>1){
		alert("\u53ea\u80fd\u9009\u62e9\u4e00\u6761\u8bb0\u5f55\uff01");
	}else{
		if (window.confirm("\u60a8\u786e\u5b9a\u8981\u5220\u9664\u6240\u9009\u62e9\u7684\u8bb0\u5f55\u5417\uff1f")){
			return;
		}
	}
	return;
}

function ReadAndUpdate(url){
	var num=0;
	var form=document.getElementsByTagName("form")
 	for(var i=0;i<form.length;i++){
		for (var j=0;j<form[i].elements.length;j++){
			if(form[i].elements[j].type=="checkbox"){ 
			var e = form[i].elements[j]; 
			if (e.checked)num++;    
			}
		}
 	}
	if(num==0){
		alert("\u8bf7\u9009\u62e9\u4e00\u6761\u8bb0\u5f55\uff01");
	}else if(num>1){
		alert("\u53ea\u80fd\u9009\u62e9\u4e00\u6761\u8bb0\u5f55\uff01");
	}else{
		  var result;
		  var boxForName = document.getElementsByName("serial");
		  for(var i = 0;i < boxForName.length;i++){
		   if(boxForName[i].type="checkbox"){
		    if(boxForName[i].checked){
		     result = boxForName[i].value ; 
		    }
		   }
		  }
		  document.location.href=url+result;
	}
	return;
}

function goURL() {
	var val = document.getElementById("keywords").value ;   
	content=encodeURIComponent(encodeURIComponent(val))
	document.location.href="pageByLike.action?keyword="+content ; 
}

function goOURL(){
	var val = document.getElementById("likeuname").value ;  
	if(val.length>0){
		document.location.href="npage.action?keyword="+val ; 
	}else{
		alert("请输入联盟名称!");
		return false; 
	}
}

function go() {
	var val = document.getElementById("likeuname").value ; 
	if(val.length>0){
		content=encodeURIComponent(encodeURIComponent(val)) ; 
		document.location.href="acpage.action?keyword="+content ; 
	}else{
		alert("请输入联盟名称!");
		return false; 
	}
}

//function query(){
//	var uname = document.getElementById("likeuname").value ;
//	var sdate = document.getElementById("sdate").value ;
//	var edate = document.getElementById("edate").value ;
//	document.location.href="bpage.action?sdate="+sdate+"&edate="+edate+"&keyword="+uname ; 
//}

function query(){
	var uname = document.getElementById("likeuname").value ;
	var year = document.getElementById("year").value ;
	var month = document.getElementById("month").value ;
	document.location.href="bpage.action?year="+year+"&month="+month+"&keyword="+uname ; 
}


function createPoi() {
	var year = document.getElementById("year").value ; 
	var month = document.getElementById("month").value ; 
	var likeuname = document.getElementById("likeuname").value ;
	if(year==""||month==""||likeuname==""){
		alert("无数据可被导出!");
	}else{
		document.location.href= "export.action?year="+year+"&month="+month+"&keyword="+likeuname ;
	}
}

function poiexport(year,month,keyword){
	if(year==""||month==""||keyword==""){
		alert("无数据可被导出!");
	}else{
		document.location.href="export.action?year="+year+"&month="+month+"&keyword="+keyword ; 
	}
}

function account(unionName,year,month,totalPrice,brokerage){
	var url = "asave.action?account.union.unionName="+unionName+"&account.actualTotalPrice="+totalPrice+"&account.brokerage="+brokerage+"&year="+year+"&month="+month; 
	
	$.ajax({
		type: "GET",
		url: url,
		beforeSend: function(XMLHttpRequest){
		},
		data: "",
		success: function(msg){
			alert("已生成帐单!");
		},
		complete: function(XMLHttpRequest, textStatus){
		}
	});

	//document.location.href=url ; 
}