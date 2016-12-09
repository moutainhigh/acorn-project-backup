/**
 * 公用弹出窗口
 * @method
 * @param {iframeId} 所在iframe对象的ID
 * @param {winId} 弹出框DOM的ID
 * @param {options} 弹出框window的配置选项
 */
function popWin(iframeId,winId,options){
    $("#" + winId).remove();
	$("body").append($("#"+iframeId,parent.document.body).contents().find("#"+winId).clone(true));
    $("document").append($("#"+iframeId,parent.document.body).contents().find("#"+winId).clone(true));
	$("#"+winId).window(options).window('open').window('move',{top:($(window).height() - $("#"+winId).window('panel').height()-10) * 0.5});

//	$("#"+winId).window('setTitle', options.title);
}

/**
 * 公用alert弹出窗口
 * @method
 * @param {winTitle} 弹出框的标题内容
 * @param {winContent} 弹出框的body内容
 */
function alertWin(winTitle, winContent) {
    var options ={title:winTitle};
    $("#alertWin").html(winContent);
    $("#alertWin").window(options).window('open');
}

/**
 * 关闭window
 * @param winId 弹出框DOM的ID
 */
function closeWin(winId){
	$("#"+winId).window('close').window('destroy');
}

/**
 * 公用alert弹出窗口
 * @method
 */
function resetApptabsWidth() {
    var size = $("#apptabs li").length - 5;
    if (size > 0) {
        $("#apptabs").width(size * 92 + 350);
    }
}

/**
 * 调用子iframe函数
 * @param frameId	子frame ID
 * @param method	子frame 函数名称
 * @param paramArr	参数数组
 */
function subCallback(frameId, method){

    if(!typeof(frameId)=="undefined" || !method){
        return;
    }
    if(frameId==''){
    	var paramStr = '';

    	for(var i=2; i<arguments.length; i++){
    		if(i>2){
    			paramStr += ',';
    		}
    		paramStr += '"' + arguments[i] + '"';
    	}
    	//console.log('paramStr:', paramStr);
    	//eval(child.id+'.contentWindow.'+method+'('+ paramStr +')');
    	eval(method+'('+ paramStr +')');
    }else{
        var child = window.frames[frameId];
        var paramStr = '';

        for(var i=2; i<arguments.length; i++){
            if(i>2){
                paramStr += ',';
            }

                paramStr += '"' + arguments[i] + '"';


        }

        //console.log('paramStr:', paramStr);

        //
       if(frameId=="tab_CallbackTab"){
           eval('p_CallbackTab.'+method+'('+ paramStr +')');
       }else if(child!=null){
            child.eval(method+'('+ paramStr +')');
       }else{
            console.debug("null");
        }

    }
}


function getSubString(str){
    var old = str;
    if(old.substring(0,1)=="\"" ){
        old =  old.substring(1,old.length);
        old = old.substring(0,old.length -1);
    }
    return old;

}

function subContactCallback(contactId, method){
    var contactFrameId='p_'+contactId;
    subCallback(contactFrameId, method);
}
/**
 * 检测浏览器类型
 */
function detectBrowser(){
	var browser = -1;
	if($.browser.msie){
		browser = 1;
	}
	if($.browser.mozilla){
		browser = 2;
	}
	if($.browser.chrome){
		browser = 3;
	}
	if($.browser.safari){
		browser = 4;
	}
	if($.browser.opera){
		browser = 5;
	}

	//console.log('browser types: 1:IE, 2:FF, 3:Chrome, 4:Safari, 5:Opera, -1:Others');
	//console.log('your browser is : ', browser);

	return browser;
}

//使iframe高度动态改变
function SetCwinHeight(obj) {
	var cwin = obj;
	if (document.getElementById(cwin.id)) {
		if (cwin && !window.opera) {
			if (cwin.contentDocument && cwin.contentDocument.body.offsetHeight)
				cwin.height = cwin.contentDocument.body.offsetHeight;
			else if (cwin.Document && cwin.Document.body.scrollHeight)
				cwin.height = cwin.Document.body.scrollHeight;
		}
	}
	setPageH(cwin);
}


function setPageH(obj) {
//    $('#head').width($(window).width());
//    $('#scrollWarp').width($(window).width()-448-$('#apptabs').width()).css('min-width','473px');
    $('#scrollWarp').width($(window).width()-448-$('#apptabs').width());
//        if($(window).width()-790>0){
//            $('#scrollWarp').width($(window).width()-790);
//        }else{
//            $('#scrollWarp').css('min-width','473px');
//        }
	setTimeout(function() {
//		$('#left,#center,#right').height('auto');
		$('#center,#right').height('auto');
//		var h_arr = [ $('#left').height(), $('#center').height() + 25,
//				$('#right').height() ];
    if(obj) {
        var h_arr = [  parseInt(obj.height) + 25+33,
            $('#right').height() ];
		var h_max = Math.max.apply(null, h_arr);
		$('#left').height(h_max - 10);
		$('#right').height(h_max);
		$('#center').height(h_max + 5);
    }
	}, 100);

}

function toggleEv(){
     $('#fn-bg').bind('click',function(event){
         $('div.bpoint').toggleClass('on');
         event.stopPropagation();
     });
    $('body').bind('click',function(event){
        $('div.bpoint').removeClass('on');
    })
    $('div.bpoint').click(function(){return false;});

}
//为了 queryC 中调用callbackMethod，添加传入参数
 var  _callBackframeid ='';
 var  _callbackMethod='';
 var  _source='';
function queryC(frameid,callbackMethod,source) {
    _callBackframeid=frameid;
    _callbackMethod=callbackMethod;
    _source = source;
    cancleAddressItem();
	$('#name').val("");
	$('#phone').val("");
	$('#findValue').val("");
	$('#defaultTable').datagrid('loadData', { total: 0, rows: [] });
	$('#defaultTablem').datagrid('loadData', { total: 0, rows: [] });

	if(source=="incomingCall"){
		$('#queryC').window({
		    close:false
		});
	}
	//$('#dg').datagrid('load',{code: '01',name: 'name01'});
    $('#queryC').window({
				closable : true,
				left : ($(window).width() - 700) / 2
			});
	$('#queryC').window('open',{type:'fade'})

}

/**
 * 公用alert弹出窗口
 * @method
 * @param {frameid} 所在iframe的ID
 * @param {callbackMethod} 指定要回调的函数
 * @param {source} 源
 * @param {phoneNum} 需查询的电话号码
 * @param {isCloseable} 是否可以关闭
 */
function queryCustomer(frameid,callbackMethod,source,phoneNum,isCloseable){
    _callBackframeid=frameid;
    _callbackMethod=callbackMethod;
    _source = source;

	$('#name').val("");
	$('#phone').val("");
	$('#findValue').val("");
	$('#defaultTable').datagrid('loadData', { total: 0, rows: [] });
	$('#defaultTablem').datagrid('loadData', { total: 0, rows: [] });

	$('#phone').val(phoneNum);
    if(typeof(isCloseable)=="undefined"){
        $('#queryC').window({closable:false});
    }else if(isCloseable){
        $('#queryC').window({closable:true});
    }else{
        $('#queryC').window({closable:false});
    }

	$('#queryC').window('open');
    cancleAddressItem();
}

/*==================== 动态添加tab和panel========= start======================*/

var tabsMap = null;
var openedContact = new Array();
var panelsMap = null;
var currentTabObj = null;
var currentPanelObj = null;
var maxIndex = 999;
var startId = 1;
var showccBut=false; //是否显现切换客户的button

/**
 * 初始化系统
 */
function initMain(){
    //设置首页为默认页
    currentPanelObj = createPanel('home','false','/home/home');
    toggleEv();
	$("#center .tabPage").each(function(){

		$(this).click(function(){
			$('#apptabs a').removeClass('current');
			$('#tabs-active li').removeClass('current');
            $('#w_combox a').removeClass('sel');
			$(this).addClass("current");
			if (this.id == "home") {
				if (p_home) {
					p_home.window.refreshCampaignGrid(1);
					p_home.window.refreshAuditGrid(1);
				}

			}
			var id = $(this).attr("id");
			var url = $(this).attr("url");
			var pObj = panelsMap.get(id);
            currentTabObj =null;

			if(pObj==null){
				closeCurrentPanel();
				currentPanelObj = createPanel(id,'false',url);
			}else{
				closeCurrentPanel();
				currentPanelObj = pObj;
				openCurrentPanel();
			}
            SetCwinHeight(currentPanelObj.dom);
		});
	});
}

/**
 * 初始化左边的菜单
 */
function initLeftMenu(){

	$(".leftMenu").live("click",function(){
		var id = $(this).attr("id");
		var url = $(this).attr("name");
		var tabName = $(this).text();
		var multiTab = $(this).attr("multiTab");
		addTab(id,tabName,multiTab,url);
	});

}


/*
 *关闭所有活动tab页面
 */
function closeAllTabs(){
    if(tabsMap){
        var size =  tabsMap.size();
        var list = tabsMap.list().slice(0);
        for(var i=0;i<size;i++){
            destoryTab(list[i].key);
        }
    }
}


/**
 * 初始化系统
 * @param panelId
 */
function createPanel(panelId,isNew,url){
    setPageH();
	$("#appContent").append("<div><iframe id=\"p_"+panelId+"\"  name=\"p_"+panelId+"\" style=\"width:100%;height:10000px\" allowTransparency=\"true\" src="+url+"  frameborder=\"0\"  class=\"\" onload=\"javascript:SetCwinHeight(this);$(this).focus();\"></iframe></div>");

	var p = new panelObject(panelId,true,null);
	panelsMap.put(panelId,p);
	return p;
}

function addTab(tabId,tabName,isNew,url,showCallback,isCloseable){
    showccBut=false;
    tabId = tabId+'';
	if(isNew == 'true'){
		currentTabObj = newTab(tabId+"_"+(startId++),tabName,isNew,url,showCallback,isCloseable);
	}else{

		if(tabsMap == null){
			tabsMap = new Map();
		}

		currentTabObj = tabsMap.get(tabId);
//		changeTab();
		if(currentTabObj==null){
			currentTabObj = newTab(tabId,tabName,isNew,url,showCallback,isCloseable);

		}else{
//			$("#"+currentTabObj.tabId).css("z-index",999);
			var pObj = panelsMap.get(tabId);

			if(pObj){
				closeCurrentPanel();
				currentPanelObj = pObj;
				openCurrentPanel();
			}
		}

	}
    changeTab($("#"+currentTabObj.tabId));
}

function newTab(tabId,tabName,isNew,url, showCallback,isCloseable){
	if(tabsMap == null){
		tabsMap = new Map();
	}
	var idx = maxIndex - tabsMap.list().length;
    $("#w_combox").append('<a for="tab_'+tabId+'">'+tabName+'</a>').parent().addClass('comOn').unbind().bind('click',function(){event.stopPropagation();$(this).addClass('ope'); $("#w_combox").show()});
    $('body').click(function(){$('#w_combox').hide().parent().removeClass('ope')});
    $("#tabs-active").append('<li  id="tab_'+ tabId + '" name=' + tabId + ' title="'+tabName+'" style="z-index:' + idx + '"><span  class="tab_content"><span>' + tabName + '</span></span><a  class="tab-closeBtn">' +
        '<span  name="' + tabId + '" class="tab-closeBtn"></span></a></li>');
    if(typeof(isCloseable)!="undefined"){
        if(!isCloseable){
            $('#tab_'+ tabId).addClass('rell');
            $('#w_combox a[for="tab_'+tabId+'"]').css({color:"#007896",textShadow:"rgba(255, 255, 255, 0.8) 0px 0 2px"});
            showccBut=true;
        }
    }
//    $("#tab_"+tabId+" .tab_content").scroll('bindE');
	$("#tabs-active li").removeClass('current');
	closeCurrentPanel();
	currentPanelObj = createPanel(tabId,isNew,url);
    var tab = new panelObject(tabId,true,idx);
    tabsMap.put(tabId,tab);
    currentTabObj =  tab;
//    changeTab($('#tab_'+tabId));
	$("#tab_"+tabId).click(function(){
        tabsMap.end(tabId);
		changeTab($(this));
		var id = String($(this).attr("name"));
		//var url = $(this).attr("url");
		var pObj = panelsMap.get(id);

		if(pObj){
			closeCurrentPanel();
			currentPanelObj = pObj;
			openCurrentPanel();
		}
		/*  */
	    $('#apptabs a').removeClass('current');
	    $("#tabs-active li").removeClass('current');
//	    $(this).css("z-index",999);
		$(this).addClass('current');
		SetCwinHeight(currentPanelObj.dom);

        //xzk-start
        if(showCallback!=null)
        {
            var child = window.frames['p_'+tabId];
            child.eval(showCallback+'()');
        }
        //xzk-end

	})
    $("#w_combox a[for='tab_"+tabId+"']").click(function(){
        $("#w_combox a").removeClass('sel');
        $(this).addClass('sel');
        $("#tab_"+tabId).click();

    })
//        .addClass('current');

	$('#apptabs a').removeClass('current');

	$("#tab_"+tabId+" .tab-closeBtn").click(function(){
		var tabId = $(this).attr("name");
		if(tabId){
			destoryTab(tabId);
		}

	});
    var re=new RegExp('/contact/1/([0-9]*)');
    if (re.test(url)) openedContact.push(RegExp.$1);
	return tab;
}

function changeTab($obj){
	$('#apptabs a,#tabs-active li').removeClass('current');
    $("#w_combox a").removeClass('sel');
    if($obj!=null){
        $obj.addClass('current');
        $("#w_combox a[for='"+$obj.attr('id')+"']").addClass('sel');
    }
    var d = 0;
    var c =    Math.abs(parseInt($('#tabs-active').css('margin-left')));
    $obj.prevAll().each(function(){
        d+=$(this).width()+11;
    });
    if(d<c){
        $('#tabs-active').css('margin-left','-'+d+"px");
    }else if( c+$('#scrollWarp').width()<d+$obj.width()+27){
        $('#tabs-active').css('margin-left','-'+(d+$obj.width()+30-$('#scrollWarp').width())+"px");

    }
    var totalW =   $('#tabs-active li').length*85 ;
    var m_l =   parseInt($('#tabs-active').css('margin-left'));
    var wrapW = $('#scrollWarp').width();
    if(m_l<0&&totalW>-m_l+wrapW){
        $('#l_scroll').addClass('l_on');
        $('#r_scroll').addClass('r_on');
    }else if(m_l<0&&totalW<-m_l+wrapW){
        $('#l_scroll').addClass('l_on');
        $('#r_scroll').removeClass('r_on');
    }else if(m_l==0&&totalW>wrapW){
        $('#l_scroll').removeClass('l_on');
        $('#r_scroll').addClass('r_on');
    }
}

function destoryTab(tabId){
	$("#tab_"+tabId).remove();
    //$("#p_"+tabId).remove();
    $("#p_"+tabId).parent().remove();
    //$("#p_"+tabId).panel("destroy");
    $("#w_combox a[for='tab_"+tabId+"']").remove();
    tabsMap.remove(tabId);
    panelsMap.remove(tabId);

    var lastTab = tabsMap.last();
    if(currentTabObj!=null) {
    if(lastTab){
    	$("#"+lastTab.tabId).css("z-index",1000);
    	currentTabObj = lastTab;
        changeTab($('#'+currentTabObj.tabId));
    	closeCurrentPanel();
		currentPanelObj = panelsMap.get(lastTab.parentId);
		openCurrentPanel();


    }else{
            currentPanelObj = panelsMap.get('home');
            changeTab($('#home'));
            openCurrentPanel();
        if(p_home) {
        	p_home.window.refreshCampaignGrid(1);
        	p_home.window.refreshAuditGrid(1);
        }
         try{
        if(p_mytask) {
        	p_mytask.window.refreshCampaignGrid(2);
        	p_mytask.window.refreshAuditGrid(2);
        }
         }catch(err){
            console.info("===========");
         }
        $('#w_combox').parent().removeClass('comOn').unbind();
    }
    }
    if ($.inArray(tabId, openedContact) != -1) openedContact.splice($.inArray(tabId, openedContact),1);
}

function panelObject(id,status,idx){
	this.id="p_"+id;
	this.tabId="tab_"+id;
	this.parentId = id;
	this.status = status;
	this.idx = idx;
    this.dom = document.getElementById(this.id)  ;
}

function closeCurrentPanel(){
	if(currentPanelObj!=null){
		//$("#"+currentPanelObj.id).panel("close");
		$("#"+currentPanelObj.id).parent().hide();
	}
}

function openCurrentPanel(){
	if(currentPanelObj!=null){
		//$("#"+currentPanelObj.id).panel("open","true");
		$("#"+currentPanelObj.id).parent().show();
	}
    SetCwinHeight(currentPanelObj.dom);
}

function getParentDom(){
	return window.document;
}

/*==================== 动态添加tab和panel========= end======================*/

(function ($) {

	 $.fn.Scroll = function (opt, callback) {
        	if (!opt) var opt = {};
            var _btnLeft = $("#" + opt.left);
            var _btnRight = $("#" + opt.right);
            var _this = this.eq(0).find("ul:first");
            var _wrapW =$('#scrollWarp').width();
            var m = 0 ;
            var liW;
            var l;
            function scrollLeft() {

            	 var liW1 = 0;
            	_this.find("li").each(function(index){
            		liW1 += $(this).width()+12;
            	});
            	liW = liW1;

            	m = parseInt(_this.css('margin-left'));
            	l = liW - Math.abs(m);
          	 if (l- $('#scrollWarp').width()>0&&l- $('#scrollWarp').width()<88) {
//          		_this.animate({'margin-left':m-(l-$('#scrollWarp').width())-10},1000);
                   _this.animate({'margin-left':m-(l-$('#scrollWarp').width())-10},100,function(){attC();});
              }else if(l- $('#scrollWarp').width()>88){
                   _this.animate({'margin-left':m-85},100,function(){attC();});
               }


            }
            function scrollRight() {
            	var liW1 = 0;
            	_this.find("li").each(function(index){
            		liW1 += $(this).width()+27;
            	});
            	liW = liW1;
            	m = parseInt(_this.css('margin-left'));
          	 if (Math.abs(m)>0&&Math.abs(m)<88) {
          		_this.animate({'margin-left':0},100,function(){attC();});
//                   _this.animate({'margin-left':m+85},100);
              }else if(Math.abs(m)>88){
//                    _this.animate({'margin-left':0},100,function(){attC();});
                   _this.animate({'margin-left':m+85},100,function(){attC();});
                }
//                attC();
            }

            function stopAn(){
            	_this.stop();

            }
         function attC(){
             var totalW =   $('#tabs-active li').length*83 ;
             var m_l =   parseInt($('#tabs-active').css('margin-left'));
             var wrapW = $('#scrollWarp').width();
             if(m_l<0&&totalW>-m_l+wrapW){
                 $('#l_scroll').addClass('l_on');
                 $('#r_scroll').addClass('r_on');
             }else if(m_l<0&&totalW<-m_l+wrapW){
                 $('#l_scroll').addClass('l_on');
                 $('#r_scroll').removeClass('r_on');
             }else if(m_l==0&&totalW>wrapW){
                 $('#l_scroll').removeClass('l_on');
                 $('#r_scroll').addClass('r_on');
             }
         }
            _btnLeft.bind("click", scrollRight);
//            _btnLeft.bind("mouseup", stopAn);
            _btnRight.bind("click", scrollLeft);
//            _btnRight.bind("mouseup", stopAn);
        };

})(jQuery);

$(function(){
    $(".tabs-warp").capacityFixed();
    $("#scrollWarp").Scroll({left: "l_scroll", right: "r_scroll" });
    resetApptabsWidth();
    $("[aria='true']").focus(function(){
        $(this).addClass('text-focus');
    }).blur(function(){
            $(this).removeClass('text-focus');
        });


    $('#handlebarContainer').click(function(){
        $('#sidebarCell').toggleClass('sidebarCollapsed');
        window.frames[currentPanelObj.id]._handler();

    });
    $('#handlebarContainer2').click(function(){
        $('#sidebarCell2').toggleClass('sidebarCollapsed');
        var currentIframe = currentPanelObj.id;
        window.frames[currentIframe]._handler();
    });



//    $('#add1 a').click(function(){ event.stopPropagation();$('#add1').toggleClass('selected')});
//    $('#add2 a').click(function(){ event.stopPropagation();$('#add2').toggleClass('selected')});
//    $('#add1 li,#add2 li').click(function(){
//        $(this).parent().prev().find('span.c_context').text($(this).text());
//    });
//	$('ul.apptabs a').click(function(){$('ul.apptabs a').removeClass('current');$(this).addClass('current')});


    $('#leftPage a').click(function(){ setTimeout(function(){
        SetCwinHeight(currentPanelObj.dom);},1000);});




    //xzk-start
    $('#myorder').click(function(){
        var pObj = panelsMap.get('myorder');
        if(pObj!=null)
        {
            var child = window.frames['p_myorder'];
            child.eval('refreshMyorderlist()');
        }
    });
    //xzk-end
})



function openSms(){
	addTab('smsTab', '短信', false, ctx+'/sms/index');
}

function openReminder(){
	document.getElementById('myorder').click();
}

function openMessage(){
    addTab('messageTab', '消息管理', false, ctx+'/message/index','refreshMessage');
}

function waitforIframeLoadAndCallback(tabId,funcName,data,count)
{
    var child = window.frames['p_'+tabId];
    if(child!=null)
    {
        var func=null;
        try{func=child.eval(funcName);}catch(e){
            console.debug(e);

            if(count<5)
            {
                setTimeout(function(){waitforIframeLoadAndCallback(tabId,funcName,data,count++);},1000);
            }

            return;
        };
        if(func!=null)
        {
            //$('#apptabs a[id="'+tabId+'"]').click();
            func(data);
            return true;
        }
    }
}
function showTabAndCallFunc(tabId,funcName,data)
{

   var child = window.frames['p_'+tabId];
   if(child!=null)
   {
       $('#apptabs a[id="'+tabId+'"]').click();
       var func=null;
       try{func=child.eval(funcName);}catch(e){
           console.debug(e);
           return false;
       };
       if(func!=null)
       {
           //$('#apptabs a[id="'+tabId+'"]').click();
            func(data);
            return true;
       }
    }else
   {
       $('#apptabs a[id="'+tabId+'"]').click();
       setTimeout(function(){waitforIframeLoadAndCallback(tabId,funcName,data,0);},1000);

       return true;
   }
    return false;
}
/*


Object.prototype.equals = function(obj){
	if (this == obj)
		return true;

	if (typeof(obj) == "undefined" || obj == null || typeof(obj) != "object")
		return false;

	var length = 0;
	var length1=0;

	for (var ele in this) length++;
	for (var ele in obj) length1++;

	if (length != length1)
		return false;

	if (obj.constructor == this.constructor) {
		for(var ele in this){
			if (typeof(this[ele]) == "object") {
				if (!this[ele].equals(obj[ele]))
					return false;
		} else if (typeof(this[ele]) == "function") {
			if(!this[ele].toString().equals(obj[ele].toString()))
				return false;
		} else if (this[ele] != obj[ele])
			return false;
		}
		return true;
	}
	return false;
};
	*/
/*


Object.prototype.equals = function(obj){
	if (this == obj)
		return true;

	if (typeof(obj) == "undefined" || obj == null || typeof(obj) != "object")
		return false;

	var length = 0;
	var length1=0;

	for (var ele in this) length++;
	for (var ele in obj) length1++;

	if (length != length1)
		return false;

	if (obj.constructor == this.constructor) {
		for(var ele in this){
			if (typeof(this[ele]) == "object") {
				if (!this[ele].equals(obj[ele]))
					return false;
		} else if (typeof(this[ele]) == "function") {
			if(!this[ele].toString().equals(obj[ele].toString()))
				return false;
		} else if (this[ele] != obj[ele])
			return false;
		}
		return true;
	}
	return false;
};
	*/



//$(document).ready(function(){
//    showAllLoginUsers($("#fn-bg").html().trim());
//});

/*
function cullingUser(uid){
    if(uid != $("#fn-bg").html().trim().split("&")[0]) {

        $.messager.confirm('系统提示', '您确定要剔出用户'+uid+'?', function(r){
            if (r){
                    $.ajax({
                    type : "get",
                    url : "/cullingUser",
                    data : "name=" + uid,
                    async : false,
                    dataType:"json",
                    success : function(data){
                        var items = [];
                        $.each(data, function(key, val) {
                            items.push('<a href="javascript:cullingUser('+val.uid+');">' + val.uid+'->'+val.lastTime+'('+val.ip+  + ')</a>');
                        });

                        if($("#fn-bg").html().trim().split("&")[0]!='10001'){
                            items=[];
                        }
                        $("#v_showAllUsers").html(items.join(""));
                    }
                });
            }uid
        });

    }
}

//*/
//function showAllLoginUsers(userId){
////    $.getJSON('/getAllUsers', function(data) {
////
////    });
//
//   // $.post('/getAllUsers',{uid:userId});
//
//
//}

(function() {
    var oldajaxfuc = jQuery.ajax;
    jQuery.extend({
        ajax: function( url, options ){
            // If url is an object, simulate pre-1.5 signature
            if ( typeof url === "object" ) {
                options = url;
                url = undefined;
            }

            var oldSuccessFunc = options.success;
            options.success = function(ret) {
                 if(ret){
                        if(ret.sessionTimeout) {
                            console.info("esssionTimeOut..............................................");
                            window.location.href="/login"
                            return;
                        } else {
                            try{
                               oldSuccessFunc.apply(this, arguments);
                            }catch(e){

                            }
                        }
                 }else {
                     try{
                         oldSuccessFunc.apply(this, arguments);
                     }catch(e){

                     }
                 }
            }

            oldajaxfuc(url, options);
        }
    });
})();


//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
function banBackSpace(e){
    var ev = e || window.event;//获取event对象
    var obj = ev.target || ev.srcElement;//获取事件源

    var t = obj.type || obj.getAttribute('type');//获取事件源类型

    //获取作为判断条件的事件类型
    var vReadOnly = obj.getAttribute('readonly');
    var vEnabled = obj.getAttribute('enabled');
    //处理null值情况
    vReadOnly = (vReadOnly == null) ? false : vReadOnly;
    vEnabled = (vEnabled == null) ? true : vEnabled;

    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
    //并且readonly属性为true或enabled属性为false的，则退格键失效
    var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea") && (vReadOnly==true || vEnabled!=true))?true:false;

    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
    var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea")?true:false;

    //判断
    if(flag2){
        return false;
    }
    if(flag1){
        return false;
    }
}

//禁止后退键 作用于Firefox、Opera
document.onkeypress=banBackSpace;
//禁止后退键  作用于IE、Chrome
document.onkeydown=banBackSpace;

function addTabClickCallback(tabId, showCallback)
{
    if(showCallback!=null)
    {
        $("#tab_"+tabId).click(function(){
            var child = window.frames['p_'+tabId];
            if(child!=null)
            {
                console.info("inner add call back func!!!");
                child.eval(showCallback+'()');
            }
        });
    }
}

function complain(){
    $('#complain').window('open')
}

function msgConfirm(title, msg, fn){
    $.messager.confirm(title, msg, fn);
}
