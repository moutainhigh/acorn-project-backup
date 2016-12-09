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
	
	//h1 绑定单击切换事件
	$('.lft_nav > h1').bind('click', function(){
		var _ul = $(this).next();
		var _state = $(_ul).is(':hidden');
		
		//隐藏所有的h1
		$('.lft_nav > ul').hide();		
		if(_state){
			$(_ul).show();
		}else{
			$(_ul).hide();
		}
	});

	var url = window.location.href;
	var domain = window.location.host;
	var target = url.substring(domain.length + 8 -1, url.length);
	
	if(null != target && ''!=target){
		var links = $('.lft_nav').find('a');
		for(var i=0; i<links.length; i++){
			var __url = $(links[i]).attr('href');
			if(target==__url){
				$(links[i]).css('color', '#48a140');
				$(links[i]).parents('ul').show();
				break;
			}
		}
	}
});
