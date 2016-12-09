var CONTEXT_PATH = jQuery('meta[name="context_path"]').attr('content') || '';
//弹出窗口  
function showWindow(options,winPop){
	if (options && options.width && options.height){
		options.top=($(window).height() - options.height) * 0.5;
		options.left=($(window).width() - options.width) * 0.5;
	}
	if(winPop){
		winPop="#"+winPop;
		jQuery(winPop).window(options);
		jQuery(winPop).window('open');
	}else{
		jQuery("#MyPopWindow").window(options);
		$('#MyPopWindow').window('open');
	}
}
/**
 * 封装alert弹出框
 * @param msg
 */
function alerts (msg) {
	 $.messager.alert('信息提示框',msg);
}
/** 
 * 时间对象的格式化; 
 */  
Date.prototype.format = function(format) {  
    /* 
     * eg:format="YYYY-MM-dd hh:mm:ss"; 
     */  
    var o = {  
        "M+" :this.getMonth() + 1, // month  
        "d+" :this.getDate(), // day  
        "h+" :this.getHours(), // hour  
        "m+" :this.getMinutes(), // minute  
        "s+" :this.getSeconds(), // second  
        "q+" :Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S" :this.getMilliseconds()  
    // millisecond  
    }  
  
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "")  
                .substr(4 - RegExp.$1.length));  
    }  
  
    for ( var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]  
                    : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
}  

function showWindow2(options,winPop){
	if (options && options.width && options.height){
		options.top=($(window).height() - options.height) * 0.8;
		options.left=($(window).width() - options.width) * 0.8;
	}
	if(winPop){
		winPop="#"+winPop;
		jQuery(winPop).window(options);
		jQuery(winPop).window('open');
	}else{
		jQuery("#MyPopWindow").window(options);
		$('#MyPopWindow').window('open');
	}
}

//关闭弹出窗口  
function closeWindow(winPop){
	if(winPop){
		winPop="#"+winPop;
		 $(winPop).window('close');  
	}else{
		 $("#MyPopWindow").window('close');  
	}
   
} 

function formatEnabled(value){
	if(value){
		return "有效";
	}else{
		return "无效";
	}
}

function jumpPage(url,target){
	target = "#"+target;
	$(target).layout("panel","center").panel('refresh',url);
}

function formatLevel(value,rec){
	if(rec.parentid>0){
		return "├─"+rec.name;
	}else{
		return rec.name;
	}
	
}

function genBtn(btnId,btnImg,btnName,appContext){
	
	var innerHTML='<td id="btn'+btnId+'"';		
	innerHTML+=' valign="top"><table border="0" onMouseOver="btnOver(this)" onMouseOut="btnOut(this)" class="btn" style="background:url(images/menu/bg/bg_line.jpg) no-repeat right center; width:100px"><tr>';
	innerHTML+='<td align="center" style="cursor:hand;"><img src="';
	if (!btnImg)
		innerHTML+='images/menu/btn_def-out.gif';			
	else
		innerHTML+=appContext+btnImg;		
	innerHTML+='" title="'+btnName+'"  border="0" onMouseOver="imgOver(this)" onMouseOut="imgOut()" onClick="changeLevel2('+btnId+');">';
	innerHTML+='</td></tr><tr><td class="btn_name">'+btnName+'</td></tr></table></td>';  	
	return innerHTML;
}

function btnOver(btnBg)
{
	btnBg.bgColor="";	
}

function btnOut(btnBg)
{
	btnBg.bgColor="";
}
var currentImg = null;
var pressedImg = null;
function imgOver(img) {	
	currentImg = img;
	if (currentImg != pressedImg) {
		changeImgSrc(currentImg, "-over");
	}
}

function imgOut() {
	if (currentImg != null && currentImg != pressedImg) {
		changeImgSrc(currentImg, "-out");
	}
}

function changeImgSrc(img, suffix) {
	img.src = img.src.substring(0, img.src.lastIndexOf("-")) + suffix + img.src.substr(img.src.lastIndexOf("."));
}

$(function(){
	//显示页面日期
	//var md = new Date();
	//var Week = ['日','一','二','三','四','五','六'];
	//var fullDate = md.getMonth() + 1;
	//var today = md.getFullYear()+'年'+fullDate+'月'+md.getDate()+'号  星期'+Week[md.getDay()];
	//$("._date").text(today);
//	$.post(CONTEXT_PATH+"/getServerTime",function(data){
//		$('#serverTime').jclock({withDate:true, withWeek:true,startDate:data.serverTime});
//	},'json');
	
	
	//页面宽度自适应浏览器
	var body = (document.compatMode&&document.compatMode.toLowerCase() == "css1compat")?document.documentElement:document.body;
	var bodyWidth = body.clientWidth;
	var bodyHeight = body.clientHeight;
	$("#wrap").css('min-width',bodyWidth-18);
	//alert(body.clientHeight);
	$('#content,.handlebar').css('min-height',bodyHeight-60);
	
	//$('#header').css('min-width',screen.width);
	//h1 绑定单击切换事件
	$('.lft_nav > h1').bind('click', function(){
		var _ul = $(this).next();
		var _state = $(_ul).is(':hidden');
		
		//隐藏所有的h1
		//$('.lft_nav > ul').hide();
		$('.lft_nav > ul').slideUp();
		$('.lft_nav > h1').removeClass("op");
		if(_state){
			//$(_ul).show();
			$(this).addClass("op");
			$(_ul).slideDown();
		}else{
			//$(_ul).hide();
			$(_ul).slideUp();
		}
	});

	
	//绑定tree的横向折叠事件
	$('#handlebar').bind('click', function(){
		var $this = $('#sidebar');
		var $that = $('#content');
		
		switch($this.offset().left)
		{
		case 0:$this.animate({left:'-200px'});$that.animate({'margin-left':'0'},'normal',function(){$("[data-options='']").each(function(){if($(this).parent('.datagrid-view').length==1){$(this).datagrid("resize")}}); });$("[fit='true']").parent().css('width',body.clientWidth-20);$("[fit='true']").tabs('resize'); break;
		case -200:$this.animate({left:'0'});$that.animate({'margin-left':'200'},"normal",function(){$("[data-options='']").each(function(){if($(this).parent('.datagrid-view').length==1){$(this).datagrid("resize")}});});$("[fit='true']").parent().css('width',body.clientWidth-220);$("[fit='true']").tabs('resize'); break;
		}
	});
	
	
	var url = window.location.href;
	var domain = window.location.host;
	var target = url.substring(domain.length + 8 -1, url.length);
	if(target.indexOf(';')>0){var target = target.substring(0, target.indexOf(';'));}
	if(null != target && ''!=target){
		var links = $('.lft_nav').find('a');
		for(var i=0; i<links.length; i++){
			var __url = $(links[i]).attr('href');
			if(target==__url){
				$(links[i]).css('color', '#48a140');
				$(links[i]).parents('ul').show();
				$('.currentPage span:eq(0)').text($(links[i]).parents('ul').prev().text()+' > ');
				$('.currentPage span:eq(1)').text($(links[i]).text());
				$(links[i]).parents('ul').prev().addClass("op");
				break;
			}
		}
	}
});

$.extend($.fn.datagrid.defaults.editors, {    
    timespinner: {    
        init: function(container, options){    
            var input = $('<input type="text">').appendTo(container);    
            return input.timespinner(options);    
        },    
        destroy: function(target){    
            $(target).timespinner('destroy');    
        },    
        getValue: function(target){    
            return $(target).timespinner('getValue');    
        },    
        setValue: function(target, value){    
            $(target).timespinner('setValue',value);    
        },    
        resize: function(target, width){    
            $(target).timespinner('resize',width);    
        }    
    }    
}); 
