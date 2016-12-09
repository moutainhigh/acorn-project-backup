
/*
 * 摘机状态   -1
 */
function onOffHook(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    console.info("onOffHook>>>");
    $('#wrapper').removeAttr("class").addClass("onoffhook");
    $('#state a.c_btn').html("摘机").attr('for','leave').unbind().bind('click',function(){changeS(this);});

    $('#callContext').html('<span class="incoming fl"></span>');

    $('#state .arrow').unbind().bind('click',function(){toggleS()});
    $('a.logout').unbind();
    $('#callback').unbind().bind('click',function(){ changeCS(this);});
    $('#state li').unbind().bind('click',function(){toggleSC(this);});
    $('#outbtn').unbind().bind('click',function(){
        outCall(phone);
    })
    $('table.keyboard a').unbind().bind('click',function(){ toggleSPE(this)});
    $('#softphoneWrap').unbind().bind('click',function(){ toggleSP();});
}


/*
 * 就绪状态  0 init()
 */
function onReady(ani,dnsi,tollFreeNum,isOutBoound,company,province){

    $('#callContext').html('<span class="incoming fl"></span>');
    $('#wrapper').removeAttr("class").addClass('onReady');


    $('#state a.c_btn').html('就绪').attr('for','leave').unbind().bind('click',function(){changeS(this);});
    console.log('5+就绪'+_source);
//    if(typeof(_source)== "undefined"){
//        ready(); console.info("ready:"+12);
//    }

    $('#state').unbind().bind('click',function(){toggleS()});
    $('a.logout').unbind();
    $('#callback').unbind().bind('click',function(){ changeCS(this);});
    $('#softphoneWrap').unbind().bind('click',function(){ toggleSP();});
    $('#state li').unbind().bind('click',function(){ toggleSC(this);});
    $('table.keyboard a').unbind().bind('click',function(){ toggleSPE(this)});
    $('#outbtn').unbind().bind('click',function(){
        outCall(phone);
    });
    $('#transferPhoneBtn').unbind();
//    $('#notelist').combo('onShowPanel',function(a,b){
//                  alert(a +"   "+b);
//    })
//    $('#pnum li').unbind().bind('click',function(){keyComboxE(this)});
//    $('#pnum a').eq(0).unbind().bind('click',function(){toggleSel()});
    //初始化商品搜索
    //clearRecommendItems();


}
/*
 * 就绪（可外呼）  1
 */
function onReadyOut(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    $('#wrapper').removeAttr("class").addClass('onReadyOut');
//    $('#callContext').html('');
    $('#callContext').html('<span class="incoming fl"></span>');
    $('#state a.c_btn').html('就绪(可外呼)').attr('for','ready').unbind().bind('click',function(){changeS(this);});;
    $('#state .arrow').unbind().bind('click',function(){toggleS()});
    $('#callback').unbind().bind('click',function(){ changeCS(this);});
    $('#softphoneWrap').unbind().bind('click',function(){ toggleSP();});
    $('table.keyboard a').unbind().bind('click',function(){ toggleSPE(this)});
    $('#outbtn').unbind().bind('click',function(){
        outCall(phone);
    })
    $('#transferPhoneBtn').unbind();
    //初始化商品搜索
    //clearRecommendItems();
}
/*
 * 离席状态   2
 */
function onLeaving(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    $('#wrapper').removeAttr("class").addClass("onLeaving");
    $('#state a.c_btn').html("离席").attr('for','ready').unbind().bind('click',function(){changeS(this);});
    if(phone.leavingReason)$('#state a.c_btn').html("离席("+phone.leavingReason+')').attr('for','ready').unbind().bind('click',function(){changeS(this);});
//    $('#state a.c_btn').attr('for','ready').unbind().bind('click',function(){changeS(this);});
    $('#callContext').html('<span class="incoming fl"></span>');

    $('#state .arrow').unbind().bind('click',function(){toggleS()});
    $('a.logout').unbind().bind('click',function(){logout()});
    $('#callback').unbind().bind('click',function(){ changeCS(this);});
    $('#state li').unbind().bind('click',function(){toggleSC(this);});
//    $('#pnum li').unbind().bind('click',function(){keyComboxE(this)});
    $('#outbtn').unbind().bind('click',function(){
        outCall(phone);
    })
    $('table.keyboard a').unbind().bind('click',function(){ toggleSPE(this)});
    $('#softphoneWrap').unbind().bind('click',function(){ toggleSP();});
}
/*
 * 振铃状态 3
 */
function onRinging(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    $('#callContext').css({'width':30,'padding-left':0,'padding-right':0,'margin-right':'0'});
    $('#callContext .incoming').addClass('incoming_ac').show();
    $('#transferPhoneBtn').unbind();
}
/*
 *进线通话状态  4
 */
function onTalking(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    $('#callContext').html('<span class="insure">'+ phone.insure+'</span><span class="incoming incoming_ac fl"></span>' +
        '<span class="callin fl">' +
        '<font id="time" class="time fl">' +
        '</font>' +
        '<div id="num" class="clearfix">' +
        '<span id="marquee" class="fl ml5 c_combo pl5"  style="height:25px;padding-right:18px">'+ani+'('+province+')<span class="arrow"></span></span>' +

        '<div class="marquee_hover pl5 pr5">' +
        '<div>'+ani+'('+province+')' +
        '</div>'+
        '<div class="">&nbsp;<a id="local" class="callback" onclick="callLocalhost(this);">本地</a>&nbsp;<a id="long" class="callback" onclick="callLongDistance(this);">长途</a>&nbsp;<a class="addBlacklist" onclick="addBlack(this);">加黑</a>&nbsp;</div>' +
        '</div>' +
        '</div>' +
        ''+company+'&nbsp;&nbsp;'+tollFreeNum+'&nbsp;'+phone.dnsi+'</span>' +
        '<a id="onHook" title="挂机" class="onHook" href="javascript:void(0)"></a>');
//        '<span id="end" class="end" title="结束会话" ></span>');
        if($('#callback').attr('value')==1)$('#onHook').replaceWith('<span class="onHook"></span>');
         //进线通话
         $('#callContext').animate({width:$('#callContext').find('.callin').width()+50,paddingLeft:'30px',paddingRight:0,marginRight:'35px'},0,function(){
             $(this).width('');
//                 .css('margin-right','55px');


             if(phone.customerId.length>1){
                 createRecommendTask(phone.dnsi,phone.ani,phone.customerId,phone.customerType,phone.leadId);
                 gotoInfoCustomer(phone.customerId,phone.customerType,phone.ani,false);

             }else{
                 //根据呼入号码查询客户
                 $.post("/customer/mycustomer/phone/find",{
                     phone:phone1==null?phone2:(phone1+phone2)
                 },function(data){
                     if(data.result){
                         if(data.total == 1){
                             var rows =data.rows;
                             var row =rows[0];
                             phone.customerId=row.contactId
                             phone.customerType = row.contactType;
                            //显示当前客户信息
                             if(row.contactType=="2"){
                                 createRecommendTask(phone.dnsi,phone.ani, row.contactId,"2");
                                 //callback模式
                                 if($("#callback").val()==1){
                                     gotoInfoCallBack(row.contactId,2,phoneNoMask(phone.ani));
                                 }else{
                                     gotoInfoCustomer(row.contactId,2,phoneNoMask(phone.ani),false);
                                 }

                             }else{
                                 createRecommendTask(phone.dnsi,phone.ani, row.contactId,"1");
                                 if($("#callback").val()==1){
                                     gotoInfoCallBack(row.contactId,1,row.name,false);
                                 }else{
                                     gotoInfoCustomer(row.contactId,1,row.name,false);
                                 }

                             }

                         }else{
                             //找到客户定位客户
                             queryCustomer('','createRecommendTaskCallback','incomingCall',phone1==null?phone2:(phone1+phone2));
                             findCustomer();
                         }
                     }else{
                         //库中不存在
                         $.post("/customer/mycustomer/potentialContact/add",{
                             phn1:phone1,
                             phn2:phone2,
                             phn3:phone3,
                             phonetype:phoneType
                         },function(data){
                             if(data.result){
                                 phone.customerId=data.potentialContactId;
                                 phone.customerType="2";
                                 //显示当前客户信息

                                 //进入客户中心 ，如果是callback模式
                                 createRecommendTask(phone.dnsi,phone.ani,data.potentialContactId,"2");
                                 if($("#callback").val()==1){
                                    gotoInfoCallBack(data.potentialContactId,2,phoneNoMask(phone.ani),false);
                                 }else{
                                    gotoInfoCustomer(data.potentialContactId,2,phoneNoMask(phone.ani),false);
                                 }

                             }else{
                                // alertWin('系统提示',date.msg);
                             }
                         });

                     }
                 });
             }
         });


    $('#onHook').one("click",function(){

        $('#transferPhoneBtn').unbind();
        isMyRelease = true;
        console.info("release:"+177);
        //如果是外呼
        if(phone.isOutBoound){
            document.getElementById(outphoneFrameId).contentWindow.loadingFadeOut();
        }
        release();

       //挂机后处理效果
       phone.changeStatus(7);



        //更新通话时长;
        $.get("/common/getVersion", function(result){
            $("#span_intradayTotalOutcallTime").html(result.totalOutcallTime+"秒");
        });

    });
    $('#num').hover(
        function(){
//            $('#num marquee').hide();
            $('.marquee_hover').fadeIn();
        },function(){
            $('.marquee_hover').fadeOut();
//            $('#num marquee').show();
        });
     if(countup!=null){countup.reset(); }
      countup = $.countup(timer);

     $('#state a.c_btn').html('通话中').attr('for','hold');
     $('#wrapper').removeAttr("class").addClass('onTalking');
    if(isOutBoound){$('#callContext .incoming').addClass('incoming_out');}
    $('#callback').unbind();
    $('a.logout').unbind();
    $('#softphoneWrap').unbind().bind('click',function(){ toggleSP();});
     $('table.keyboard a').unbind().bind('click',function(){ toggleSPE(this)});
    $('#transferPhoneBtn').css('opacity',1).unbind().bind('click',function(){
        $("#div_transferPhone").attr("class","c_combo throughconnect");
//        $("#div_transferPhone").attr("title","转接");
        transferPhone();//电话转接
    })
}
/*
 * 呼出中 5
 */
function onDialing(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    $('#callContext').css({'width':30,'padding-left':0,'padding-right':0,'margin-right':'0'});
    $('#callContext').html('<span class="insure">'+ phone.insure+'</span><span class="incoming incoming_ac fl"></span>' +
        '<span class="callin fl">' +
        '<font id="time" class="time fl">' +
        '</font>' +
        '<div id="num" class="clearfix">' +
        '<span id="marquee" class="fl ml5 c_combo pl5"  style="height:25px;padding-right:18px">'+ani+'('+province+')<span class="arrow"></span></span>' +

        '<div class="marquee_hover pl5 pr5">' +
        '<div>'+ani+'('+province+')' +
        '</div>'+
        '<div class="">&nbsp;<a id="local" class="callback" onclick="callLocalhost(this);">本地</a>&nbsp;<a id="long" class="callback" onclick="callLongDistance(this);">长途</a>&nbsp;<a class="addBlacklist" onclick="addBlack(this);">加黑</a>&nbsp;</div>' +
        '</div>' +
        '</div>' +
        ''+company+''+tollFreeNum+''+phone.dnsi+'</span>' +
        '<a id="onHook" title="挂机" class="onHook" href="javascript:void(0)"></a>');
//        '<span id="end" class="end"  title="结束会话"></span>');
    $('#callContext').animate({width:$('#callContext').find('.callin').width(),paddingLeft:'30px',paddingRight:0,marginRight:'35px'},'slow',function(){
        $(this).width('');
    });
    $('#onHook').one("click",function(){


        isMyRelease = true;
        console.info("release:"+238);
        release();
        //挂机后处理效果
        phone.changeStatus(7);
        $('#onHook').replaceWith('<span class="onHook"></span>');
//        $('#end').replaceWith('<a id="end" class="end" title="结束会话" onclick="javascript:openhookwin();"></a>');
    });
    $('#num').hover(
        function(){
//            $('#num marquee').hide();
            $('.marquee_hover').fadeIn();
        },function(){
            $('.marquee_hover').fadeOut();
//            $('#num marquee').show();
        });
    /*重置计时器*/
    countup = null;

    $('#state a.c_btn').html('呼出中').unbind();
    $('#state .arrow,#state').unbind();
    $('#callContext .incoming').addClass('incoming_out');
    $('#wrapper').removeAttr("class").addClass('onTalking').addClass('cOff');
}
/*
 *呼出通话  6
 */
function onTalkingOut(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    if(countup==null)countup = $.countup(timer);

    $('#state a.c_btn').html('呼出通话中').attr('for','hold').unbind().bind('click',function(){changeS(this);});;
    $('#state').unbind().bind('click',function(){toggleS()});
    $('#wrapper').removeClass('cOff');
    if(isOutBoound){
//        $('#callContext .incoming').addClass('incoming_out');
    }
    $('#callback').unbind()
    $('a.logout').unbind();
    $('#softphoneWrap').unbind().bind('click',function(){ toggleSP();});
    $('#transferPhoneBtn').css('opacity',1).unbind().bind('click',function(){
        $("#div_transferPhone").attr("class","c_combo throughconnect");
//        $("#div_transferPhone").attr("title","转接");
        transferPhone();//电话转接
    })
}

/*
 * 后处理状态  7
 */
function onProcessing(ani,dnsi,tollFreeNum,isOutBoound,company,province){
	if(countup !=null)
	{countup.stop(); }
    if($('#callback').attr('value')==0){
        $('#callContext').css('margin-right','55px').append('<a id="end" class="end" title="结束会话" onclick="javascript:openhookwin();"></a>')};

	$('#outcon').text('挂机后处理');
    $('#onHook').replaceWith('<span class="onHook"></span>');
//    if($('#callback').attr('value')==0){$('#end').replaceWith('<a id="end" class="end" title="结束会话" onclick="javascript:openhookwin();"></a>')};
	$('#callContext .incoming').removeClass('incoming_ac').addClass('loading');
	//$('#callContext').html('<span class="incoming loading fl"></span><font class=" fl">后处理...</font>');
	$('#wrapper').removeAttr("class").addClass('onProcessing');
	 $('#state a.c_btn').html('后处理').attr('for','talking');
//	$('#callContext').find('marquee').replaceWith('<span class="fl ml10" style="width:130px;height:25px;white-space: nowrap;">&nbsp;  '+ani+'('+province+')  &nbsp; </span> ');

    $('#transferPhoneBtn').css('opacity',0.4).unbind();
    $('#throughconnect-arrow').unbind();
    $('#state .arrow').unbind();
    $('#state').unbind();
    $('#softphoneWrap').unbind();
    $('body').click();

}

/*
 *保持通话状态  12
 */
function onHolding(ani,dnsi,tollFreeNum,isOutBoound,company,province){
	 $('#wrapper').removeAttr("class").addClass('onHolding');
	 $('#state a.c_btn').html('保持').attr('for','ready');
     $('#softphoneWrap').unbind();
        console.info("hold-293");
       hold();
}

/*
 *静音状态
 */
function mute(){
	 $('#wrapper').addClass('onmuting');
	 $('#state a.c_btn').html('静音').attr('for','unmute');
     $('#softphoneWrap').unbind();
     muteOn();
}

/*
 *取消静音状态
 */
function unmute(){
	 $('#wrapper').removeClass('onmuting');
    if(phone.status==4){
	 $('#state a.c_btn').html('通话中');
    }else if(phone.status==6){
     $('#state a.c_btn').html('呼出通话中');
    }
     $('#softphoneWrap').unbind();
     muteOff();
}
/*
 * 键盘拨号外呼振铃中   13
 */
function onOutringing(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    $('#callContext').css({'width':30,'padding-left':0,'padding-right':0,'margin-right':'0'});
    $('#callContext').html('<span class="incoming incoming_ac fl"></span>' +
        '<span class="callin fl">' +
        '<font id="time" class="time fl">' +
        '</font>' +
        '<div id="num" class="clearfix">' +
        '<span id="marquee" class="fl ml5 c_combo pl5"  style="height:25px;padding-right:18px">'+ani+''+province+'<span class="arrow"></span></span>' +

        '<div class="marquee_hover pl5 pr5">' +
        '<div>'+ani+''+province+'' +
        '</div>'+
        '<div class="">&nbsp;<a class="callback" onclick="callLocalhost(this);">本地</a>&nbsp;<a class="callback" onclick="callLongDistance(this);">长途</a>&nbsp;<a class="addBlacklist" onclick="addBlack(this);">加黑</a>&nbsp;</div>' +
        '</div>' +
        '</div>' +
        ''+company+''+tollFreeNum+''+dnsi+'</span>' +
        '<a id="onHook" title="挂机" class="onHook" href="javascript:void(0)"></a>');
//        '<span id="end" class="end"  title="结束会话"></span>');
    $('#callContext').animate({width:$('#callContext').find('.callin').width(),paddingLeft:'30px',paddingRight:0,marginRight:'35px'},'slow',function(){
        $(this).width('');
    });
    $('#onHook').one("click",function(){
        isMyRelease = true;
        console.info("release:"+238);
        release();
        phone.changeStatus(7);
        $('#onHook').replaceWith('<span class="onHook"></span>');
//        $('#end').replaceWith('<a id="end" class="end" title="结束会话" onclick="javascript:openhookwin();"></a>');
    });
    $('#num').hover(
        function(){
//            $('#num marquee').hide();
            $('.marquee_hover').fadeIn();
        },function(){
            $('.marquee_hover').fadeOut();
//            $('#num marquee').show();
        });
    $('#callContext .incoming').addClass('incoming_out');
    $('#state a.c_btn').html('呼出中');
    $('#wrapper').removeAttr("class").addClass('onTalking');
}

/*
 * 键盘拨号外呼通话中  14
 */
function onOutcall(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    if(countup!=null){countup.reset(); }
    countup = $.countup(timer);

    $('#state a.c_btn').html('呼出通话中').attr('for','hold');
    $('#wrapper').removeAttr("class").addClass('onTalking');
    if(isOutBoound){
//        $('#callContext .incoming').addClass('incoming_out');
    }
    $('#callback').unbind()
    $('a.logout').unbind();
}

/*
 * 软电话未登录状态  15
 */
function offline(ani,dnsi,tollFreeNum,isOutBoound,company,province){
    $('#wrapper').removeAttr("class").addClass('offline');
    $('#state a.c_btn').html('未登录');
    $('#softphoneWrap').unbind();
    $('#callback').unbind();
    $('#state').unbind();
    $('a.logout').unbind().bind('click',function(){logout()});
    if(eval($('#isLoadSoftPhone').val())){
        $('#state .arrow').unbind().bind('click',function(){toggleS()});
        $('#state li').unbind().bind('click',function(){toggleSC(this);});
        $('#state a.c_btn').attr('for','leave').unbind().bind('click',function(){changeS(this);});
    }else{
        $('#state').addClass('discombo');
    }

}
/*
 *callback通话状态
 */
function setCallBack(){
    switch ($('#callback').attr('value')){
        case '0':{
            $('#left,#right,#apptabs').find('.c_mask').remove();
            break;}
        case '1':{
            //关闭
            if($('#left .c_mask,#right .c_mask,#apptabs .c_mask').length==0){
               $('#left,#right').append('<div class="c_mask"></div>');
               $('#apptabs').append('<div class="c_mask" style="padding-top:3px;border-radius:5px 5px 0 0"></div>');
            }
            break;}
    }
}

/*
 *打开下拉框
 */
function toggleS(){
    $('#state').addClass('selected');
//  $('#state ul').slideDown();
    event.stopPropagation();
    colseSel($('#state'));
}

/*
 *切换软电话状态
 */
function changeS(obj){
    event.stopPropagation();
    switch ($(obj).attr('for')){
        case "ready":{
            if(phone.status==12){
                console.info("取回保持＞＞＞＞");
                retrieve();
                if(phone.isOutBoound){
                    $('#state a.c_btn').html('呼出通话中').attr('for','hold');
                    $('#softphoneWrap').unbind().bind('click',function(){ toggleSP();});
                    phone.status=6;
                }else{
                    $('#state a.c_btn').html('通话中').attr('for','hold');
                    $('#softphoneWrap').unbind().bind('click',function(){ toggleSP();});
                    phone.status=4;
                }
//                    phone.changeStatus(3);
                $('#wrapper').removeAttr("class").addClass('onTalking');

                break;
            }else{
                if(isDailSelf()) return;
                phone.changeStatus(0);
                ready(); console.info("ready:"+345);
            }

            break;
        }
        case "leave":{
            if(phone.status==15){
                ctilogin();
                break;
            }
            $('li.leave dl dt') .toggleClass('open').nextAll().show();
            toggleS();
            break;
        }
        case "hold":{
            phone.changeStatus(12);
            break;
        }

    }
}

/*
 *"离席"绑定折叠时间
 */
function flod(){

     $('li.leave dl dt').unbind().bind('click',function(){
         $(this).toggleClass('open').nextAll().slideToggle();
     });
    $('li.leave dl dd').unbind().bind('click',function(){
         console.info("click leave reson...");
        if(isDailSelf()) return;
        //离席 ： 传值尽可能用数字代替   如 ： 1:申请外出，2:主管找 等
        notReady($(this).attr("value")); console.info("notReady:375");
        event.stopPropagation();

        $('#state').removeClass('selected');
        $('#state ul').slideUp();
        $(this).parent().find('dd').hide();
        phone.leavingReason =  $(this).text();
        phone.changeStatus(2);

//        $('#state a.c_btn').html('离席('+$(this).text()+')');
    });
}
/*
 *切换callback状态
 */
function changeCS(obj){
//     var $obj =  $('#callback');
//    switch  (parseInt($obj.attr('value'))){
        switch  (parseInt($(obj).attr('value'))){
        case 0:{
            //开启callback
            $('#local,#long').hide();
            $(obj).removeAttr('class').addClass('callback_y').attr('value','1');
//          $('#w_callback').window('open');
            phone._expir = 40;
            break;}
        case 1:{
            //关闭callback
            $('#local,#long').show();
            $(obj).removeAttr('class').addClass('callback_n').attr('value','0');
            $('#left,#right,#apptabs').find('.c_mask').remove();
            phone._expir = 300;
            //关闭Callback标签
            destoryTab("CallbackTab");


            break;}
    }
}
function colseSel($obj){
$('body').click(function(){
       if($obj.hasClass('opened')){
           $obj.removeClass('opened');
           clearProvinceCity();
       }else if($obj.hasClass('selected')){
           $obj.removeClass('selected');
       }else if($obj.hasClass('on')){
        $obj.removeClass('on');
    }
       $('#div_transferPhone').removeClass('twoBtns');
});
}
/*
 *打开拨号软键盘
 */
function toggleSP(){
    $('#softphoneWrap').addClass('opened');
//    $('#pnum,#add1,#add2').removeClass('selected');
    event.stopPropagation();
    $('#delNum').unbind().bind('click',function(){$('#notelist').combobox('setValue','')});
    colseSel($('#softphoneWrap'));

}

//(function(){
//    $('ul.key_panel').click(function(){
//        $('#pnum,#add1,#add2').removeClass('selected');
//    });
//})();
/*
 *打开拨号下拉菜单
 */
//function toggleSel(){
//    $('#pnum').addClass('selected');
//    event.stopPropagation();
//    $('#add1,#add2').removeClass('selected');
////    colseSel($('#pnum'));
//}
/*
 *绑定拨号软键盘事件
 */
function toggleSPE(obj){
    if($('#notelist').combobox('getValue').length<5)
    $('#notelist').combobox('setValue',$('#notelist').combobox('getValue')+''+$(obj).text());
}

/*
 *拨号软键盘选择事件
 */
function keyComboxE(obj){
    $('#pnum').attr('value', $(obj).text());
    $('#pnum a').eq(0).html( $(obj).text()+'<span class="arrow"></span>');
    $('#pnum').removeClass('selected');
    $('#pnum a.delNum').show() ;
}
/*
 *外拨
 phoneNo--拨出号码，province--所属的省份 */
function outCall(phone){
    console.info("======================"+getDnStatus(null));

    //判断电话是否摘机

    if(checkSoftPhoneApplet()){
        if($("#ctiPhoneType").val()==1){
            if(callbackIsRelease == 1){
               // document.getElementById(outphoneFrameId).contentWindow.loadingFadeOut();
                //alertWin("系统提示","分机资源忙!");
                msgSlide("分机资源忙!");
                return;
            }else if(getDnStatus(null)){
                //document.getElementById(outphoneFrameId).contentWindow.loadingFadeOut();
                //alertWin("系统提示","电话没有摘机，请摘机！！");
                msgSlide("电话没有摘机，请摘机！！");
                return;
            }

        }
    }

    if(isDailSelf()) return;

    event.stopPropagation();
    console.info("phoneNo580:"+$('#citylist').combobox('getValue'));
    var phoneNo = $('#citylist').combobox('getValue').trim()+""+$('#notelist').combobox('getValue'),
        province =  $('#prolist').combobox('getValue')+ $('#citylist').combobox('getValue');
    console.info("phoneNo581:"+phoneNo);
    console.info("phoneNo582:"+province);
    if(phoneNo.trim().length==0){return;}
    console.info("phoneNo"+ phoneNo);
//    if( typeof(phoneOUT) != "undefined" ){
//        try{
//        if (checkSoftPhoneApplet() && phoneOUT.status == 14){
//            console.info("605");
//            sendDtmf(phoneNo);
//        }
//        }catch (err){
//            console.info("610");
//        }
//    }

    switch (phone.status){
        case 0:
//            //‘就绪’时点击键盘拨号 = 二方通话
//            phoneOUTfun(phoneNo);
//            phoneOUT.changeStatus(13);
//            phone.status=13;
//            if (checkSoftPhoneApplet()){
//                findoutphone = 1;
//                console.info("8"+phone);
//                dial("8"+phoneNo,null);
//            }

            alertWin("系统提示","就绪状态,不可通过软电话盘外呼!");

            break;
        case 1:
            //‘就绪可外呼’时点击键盘拨号 = 二方通话
            phoneOUTfun(phoneNo);
            phoneOUT.changeStatus(13);
            phone.status=13;
            if (checkSoftPhoneApplet()){
                findoutphone = 1;
                dial("8"+phoneNo,null);
            }
            break;
        case 2:
            //‘离席’时点击键盘拨号  = 二方通话
            phoneOUTfun(phoneNo);
            phoneOUT.changeStatus(13);
           // phone.status=13;
            if (checkSoftPhoneApplet()){
                findoutphone = 1;
                dial("8"+phoneNo,null);
            }

            break;
        case 4:
            //‘进线通话中’时点击键盘拨号 = 三方通话

            //$('#state .c_btn').text('三方通话中');
//            if (checkSoftPhoneApplet()){
//                           console.info("630");
//                sendDtmf(phoneNo);
//            }
            break;
        case 6:
            //‘外呼通话中’时点击键盘拨号,拨分机好
            //$('#state .c_btn').text('三方通话中');
            if (checkSoftPhoneApplet()){
                console.info("638");
                sendDtmf(phoneNo);
            }
        case 14:
            //‘外呼通话中’时点击键盘拨号,拨分机号
            //$('#state .c_btn').text('三方通话中');
            if (checkSoftPhoneApplet()){
                console.info("638");
                sendDtmf(phoneNo);
            }



            break;
    }
    $('#softphoneWrap').removeClass('opened');
    $('#notelist,#prolist,#citylist').combobox('setValue','');
//     $('body').click();
}


function phoneOUTcallback(){
    phoneOUT.changeStatus(13);
    phoneOUT.changeStatus(14);
    phone.status=14;
}

/*
 *拨号软键盘清空按钮
 */
function delCon(e){
//    e.stopPropagation();
    $('#pnum a.white').html('<span class="arrow"></span>');
    $('#pnum a.delNum').hide() ;
}


/*
 *下拉框点击切换状态
 */
function toggleSC(obj){
    event.stopPropagation();
    switch ($(obj).attr('class')){
        case "loginphone":{
            ctilogin();
            $('#state').removeClass('selected');
           // phone.changeStatus(2);
            break;
        }
        case "ready":{
            $('#state').removeClass('selected');
            if(phone.status==12){
                console.info("取回保持＞＞＞＞");
                retrieve();
                if(phone.isOutBoound){
                    $('#state a.c_btn').html('外呼通话中').attr('for','hold');
                    phone.status=6;
                }else{
                    $('#state a.c_btn').html('通话中').attr('for','hold');
                    phone.status=4;
                }

                $('#wrapper').removeAttr("class").addClass('onTalking');
                break;
            }else{
                // phone.onReady();
                phone.changeStatus(0);
                ready(); console.info("ready:"+503);

                break;
            }


        }
        case "readyout":{
            $('#state').removeClass('selected');
            notReady(1201);
           // phone.changeStatus(1);
            break;}
        case "cancelHold":{
            $('#state').removeClass('selected');
            if(phone.status==12){
                console.info("取回保持＞＞＞＞");
                retrieve();
                if(phone.isOutBoound){
                    $('#state a.c_btn').html('外呼通话中').attr('for','hold');
                    $('#softphoneWrap').unbind().bind('click',function(){ toggleSP();});
                    phone.status=6;
                }else{
                    $('#state a.c_btn').html('通话中').attr('for','hold');
                    $('#softphoneWrap').unbind().bind('click',function(){ toggleSP();});
                    phone.status=4;
                }

                $('#wrapper').removeAttr("class").addClass('onTalking');
            }
            break;
        }
        case "leave":{

//        phone.changeStatus(2);
            return;
            break;
        }
        case "hold":{
            // hold();
            console.info("hold-502");
            console.info("hold--list");
            $('#state').removeClass('selected');
            phone.changeStatus(12);
            break;
        }
        case "mute":{

            $('#state').removeClass('selected');
            mute();
            break;
        }
        case "unmute":{
            $('#state').removeClass('selected');
            unmute();
            break;
        }

    }
//    toggleS();
}

/*
 * 计时器
 */
function timer(s, d){
    var innerHtml ="<span id=\"min\">("+d.minute+"</span>:<span id=\"sec\">"+d.second+")</span>"
    if(phone.status == 4){
        if(countup!=null){
            if(d.total>phone._expir){
                $('#time').toggleClass('yel');
                setTimeout(function(){ $('#time').toggleClass('yel');},500) ;
            }
        }
    }
    if(d.total>3600){
        innerHtml ="<span id=\"hour\">("+d.hour+"</span>:<span id=\"min\">"+d.minute+"</span>:<span id=\"sec\">"+d.second+")</span>"
    }
    $('#time').html(innerHtml);

}

var  countup = null;
var phone = null;


$(function(){
    phone = new AcornPhone();
    phone.onOffHook= onOffHook;
    phone.onLeaving = onLeaving;
    phone.onReady = onReady;
    phone.onTalking = onTalking;
    phone.onProcessing = onProcessing;
    phone.onHolding = onHolding;
    phone.onRinging = onRinging;
    phone.onReadyOut = onReadyOut;
    phone.onOutringing = onOutringing;
    phone.onOutcall = onOutcall;
    phone.offline = offline;
    phone.init();

    flod();


    if($.browser.mozilla)
    {
        var $E = function(){var c=$E.caller; while(c.caller)c=c.caller; return c.arguments[0]};
        __defineGetter__("event", $E);
    }

    $('#add1 a').click(function(){
        event.stopPropagation();
        $('#add1').addClass('selected');
    });
    $('#add2 a').click(function(){
        event.stopPropagation();
        $('#add2').addClass('selected')});
    $('#add1 li,#add2 li').click(function(){
        $(this).parent().parent().attr('value',$(this).text());
        $(this).parent().prev().find('span.c_context').text($(this).text());
    });
    $('#interrupt').click(function(){

        //设置电话进线效果
        interrupt();



    });


    $('#inbb').click(function(){

        //设置电话进线效果
        inphone();


    });
	//挂机
    $('#hooksave').click(function(){
        $('#savetip').hide();
        var isselcontact= $("#d_selcontact").attr("checked")?true:false,
            isContact=$("#h_isContact").attr("checked")?true:false;

        if( isselcontact && isContact){

            if($("#d_seat").combobox("getValue").length==0){
                $("#d_seat").combobox("setValue","");
            }
        }

        phone.insure='';//赠险文字清空

    	var w_instId = "";


        //判断电话是否摘机　
        console.info(getDnStatus(null));
        $("#hookMsg").html("");
        $('#callResult').selectedIndex = 0;
        $('#callType').selectedIndex = 0;


        console.info("hooksave1:"+checkSoftPhoneApplet());
        if(checkSoftPhoneApplet()){
            console.info("hooksave2:"+getDnStatus(null));
            if(!getDnStatus(null)){               console.info("hooksave3:"+getDnStatus(null));
                $('#savetip').show();
              return;
            }
        }

    	if(phone.isOutBoound){
    		w_instId=phone.instId;
    	}else{
    		w_instId=getRecommandTaskId();
    	}
    	if (typeof(w_instId) == "undefined"){
    		w_indexId="";
    	}


    	if($("#h_hookForm").form('validate')){
            $.ajax({
                type: "post",
                async : false,
                url: "/common/outbound/hook",
                data:{
                    isContact:isContact,
                    contactTime:$('#h_contactTime').datetimebox("getValue"),
                    remark:$("#h_remark").val(),
                    h_instId:w_instId,
                    ani:phone.ani,
                    dani:phone.dnsi,
                    time_length:phone.seconds,
                    marketingPlan:$('#d_marketingPlan').combobox("getValue"),
                    d_reson:$('#d_reson').combobox("getValue"),
                    isOutbound:phone.isOutBoound,
                    d_selcontact:isselcontact,
                    d_dept:$("#d_dept").combobox("getValue"),
                    d_group:$("#d_group").combobox("getValue"),
                    d_seat:$("#d_seat").combobox("getValue"),
                    ctiedt:phone.ctiedt,
                    callid:phone.cticonnid,
                    callResult:$('#callResult').val(),
                    callType:$('#callType').val()
                },
                dataType:"json",
                success: function(data){

                    //alertWin('系统提示', data.result);
                    //清空Form
                    //if(!data.result){
                    //}
                    try{
                        var hook_st = parseInt($("input[name='hook_st']:checked").val());
                        phone.changeStatus(hook_st);
                        switch(hook_st){
                            case 0:
                                if(checkSoftPhoneApplet()){
                                    ready(); console.info("ready:"+629);
                                }else{
                                    phone.changeStatus(0);
                                }
                                break;
                            case 1:
                                notReadyState=1;
                                if(checkSoftPhoneApplet()){
                                    //notReady("就绪可外呼"); console.info("ready:"+632);
                                    notReady("1201"); console.info("ready:"+632);
                                }else{
                                    phone.changeStatus(1);
                                }

                                break;
                            default :
                                if(checkSoftPhoneApplet()){
                                    phone.leavingReason = $("#notReadyCode").combobox("getText");
                                    notReady($("#notReadyCode").combobox("getValue")); console.info("notReady 637");
                                }else{
                                    phone.changeStatus(2);

                                }


                        }


                    }catch (err){
                        console.info("保存通话结果异常::"+ err);
                    }finally{
                        if(phoneOUT)phoneOUT=null;
                        $('#hook').window('close');
                        $('#h_contactTime').datetimebox('setValue', '');
                        $('#h_contactTime').datetimebox('disable');
                        $('#d_selectTask').css("display","none");
                        //更新通话时长
                        $("#span_intradayTotalOutcallTime").html(data.totalOutcallTime+"秒");

                        closeAllTabs();

                        //是否重定位到上一个用户详细页面

                        if(isContact){
                            if($('#d_marketingPlan').combobox("getValue")=='10'){
                                if(phone.customerId.length>1){
                                    gotoInfoCustomer(phone.customerId,phone.customerType,phone.customerId,false);
                                }
                            }
                        }
                    }

                },
                error: function(){ //请求出错处理
                    $('#hook').window('close');
                    closeAllTabs();
                    console.info("post fail in url=/common/outbound/hook");
                    alertWin("系统提示","post failed in url=/common/outbound/hook" );
                    phone.changeStatus(2);
                    if(phoneOUT)phoneOUT=null;
                }
            });


        }

    });

});

function inphone(){
    $('#w_inphone').window('open');
}


var phone1="", phone2="",phone3="",phoneType="",isCloseFindC=true, taskId="",phoneOUT=null,isMyRelease,outphoneFrameId=null;//标记电话呼出类型;
var isSoftPhoneLogin =0;

/**
 * 外呼
 * phone:外呼电话号码
 * type: 1. 本地;2.长途
 * leadId:销售线索Id
 * instId:任务Id
 * pdCustomerId:是否有取数
 * customerId:客户ID ;
 *
 *
 *
 */
function outphone(phoneNo,type,instId,customerId,customerType,boundType,frameId){
    try{
    outphoneFrameId = frameId;
    }catch(error){

    }

    if(checkSoftPhoneApplet()){
        if($("#ctiPhoneType").val()==1){
            if(callbackIsRelease == 1){
                document.getElementById(outphoneFrameId).contentWindow.loadingFadeOut();
                //alertWin("系统提示","分机资源忙!");
                msgSlide("分机资源忙!");
                return;
            }else if(getDnStatus(null)){
                document.getElementById(outphoneFrameId).contentWindow.loadingFadeOut();
                //alertWin("系统提示","电话没有摘机，请摘机！！");
                msgSlide("电话没有摘机，请摘机！！");
                return;
            }

        }
    }

    if(isDailSelf()) return;

    //呼出号码待定
    phone = new AcornPhone(phoneNo,$("#d_dn").html(),"",true);
    phone.boundType = boundType;
    phone.onLeaving = onLeaving;
    phone.onOffHook= onOffHook;
    phone.onReady = onReady;
    phone.onTalking = onTalking;
    phone.onProcessing = onProcessing;
    phone.onHolding = onHolding;
    phone.onRinging = onRinging;

    //5:呼出中,
    phone.onDialing = onDialing;
    //6:呼出通话,
    phone.onTalkingOut = onTalkingOut;

    phone.onReadyOut = onReadyOut;
    phone.onOutringing = onOutringing;
    phone.onOutcall = onOutcall;
    phone.offline = offline;
    //phone.init();
    phone.instId=instId;
    phone.customerId=customerId;
    phone.customerType=customerType;
    //参数初始化
    cleanOnHook();
    //初始化电话呼出类型;


    //调用推荐产品
    outgoingCall("",phone.instId);

    //电话解析
    $.ajax({
        type : "post",
        url : "/common/splicePhone",
        data : "inPhone=" + phone.ani,
        async : false,
        dataType:"json",
        success : function(data){
            //  data = eval("(" + data + ")");
            if(data.result){
                phone.province = data.dto.provName+data.dto.cityName;

                insrueCheck(null,data.dto.provName,data.dto.cityName);
                phone1 = data.dto.phn1;
                phone2 = data.dto.phn2;
                phone3 = data.dto.phn3;
                phoneType=  data.dto.phonetypid;
            }else{
                phone.province ="";
                //alertWin('系统提示', data.msg);
                return;
            }
        }
    });




    //消费取数数据
    $.ajax({
        type : "post",
        url : "/common/fatch/message",
        data : "h_instId=" + phone.instId+"&ani="+phone.ani+"&dani="+phone.dnsi+"&boundType="+phone.boundType+"&ctisdt="+phone.ctisdt,
        async : false,
        dataType:"json",
        success : function(data){
//                   //  data = eval("(" + data + ")");
//                   if(!data.result){
//                       alertWin('系统提示', data.msg);
//                       return;
//                   }
        }
    });

    if (checkSoftPhoneApplet()){


        if(phone.ani.substring(0,1) == 1){
            if(type==2){
                console.info("dial1.............");
                dial("80"+phone.ani,null);
            }else{
                console.info("dial2.............");
                    dial("8"+phone.ani,null);
            }
        }else{

            if(type==2){
                console.info("dial1.............");
                dial("8"+phone.ani,null);
            }else{
                console.info("dial2.............");
                if(phoneType !=4){
                    dial("8"+phone2,null);
                }else{
                    dial("8"+phone.ani,null);
                }

            }
        }





    }else{
        console.info("没有加载软电话");

        phone.changeStatus(5);
        //setTimeout(function(){phone.changeStatus(6);},3000);
        phone.changeStatus(6);
        $('#h_instId').val(phone.instId);
//        //消费取数数据
//        $.ajax({
//            type : "post",
//            url : "/common/fatch/message",
//            data : "h_instId=" + phone.instId+"&ani="+phone.ani+"&dani="+phone.dnsi+"&boundType="+phone.boundType+"&ctisdt="+phone.ctisdt,
//            async : false,
//            dataType:"json",
//            success : function(data){
////                   //  data = eval("(" + data + ")");
////                   if(!data.result){
////                       alertWin('系统提示', data.msg);
////                       return;
////                   }
//            }
//        });
    }


}

function phoneNoMask(phoneno_m){
    if(phoneno_m){
        var newphoneNo =phoneno_m.substring(0,phoneno_m.length-4);

        return newphoneNo+"****";

    }else{
        return "";
    }

}



function dialCallback(result){

    console.info("外呼回调");
    phone.dnsi = result.thisDN;
    phone.changeStatus(6);
    phone.connStates = result.status;
    phone.cticonnid = result.connId;

    $('#h_instId').val(phone.instId);
    //消费取数数据
    $.ajax({
        type : "post",
        url : "/common/fatch/message/callback",
        data : "h_instId=" + phone.instId+"&ani="+phone.ani+"&dani="+phone.dnsi+"&boundType="+phone.boundType+"&connId="+result.connId+"&ctisdt="+phone.ctisdt,
        async : false,
        dataType:"json",
        success : function(data){
//              data = eval("(" + data + ")");
//            if(!data.result){
//                alertWin('系统提示', data.msg);
//                return;
//            }
        }
    });

}


function phoneOUTfun(phoneNo){

    //呼出号码待定
    phoneOUT = new AcornPhone(phoneNo,$("#d_dn").html(),"",true);
    phoneOUT.boundType = "";
    phoneOUT.onOffHook = onOffHook;
    phoneOUT.onLeaving = onLeaving;
    phoneOUT.onReady = onReady;
    phoneOUT.onTalking = onTalking;
    phoneOUT.onProcessing = onProcessing;
    phoneOUT.onHolding = onHolding;
    phoneOUT.onRinging = onRinging;
    //5:呼出中,
    phoneOUT.onDialing = onDialing;
    //6:呼出通话,
    phoneOUT.onTalkingOut = onTalkingOut;
    phoneOUT.onOutringing = onOutringing;
    phoneOUT.onOutcall = onOutcall;

    //return phoneOUT;

}



function cleanOnHook(){
    $('#h_remark').val('');
    $('#h_contactTime').datetimebox("setValue",'');
    $('#h_isContact').attr("checked",false);
    $('#h_instId').val('');
//	$('#h_pdCustomerId').val('');
//	$('#h_customerType').val('');
//	$('#h_customerId').val('');
//	$('#h_campaignId').val('');
}

function outPhone2(phoneNum, taskId) {

    //根据呼入号码查询客户
    $.post("/customer/mycustomer/phone/find",{
        phone:phoneNum
    },function(data){
        if(data.result){
            if(data.total == 1){
                var rows =data.rows;
                var row =rows[0];
                phone.customerId=row.contactId;
                phone.customerType = row.contactType;
                phone.ani=phoneNum;
               //显示当前客户信息
                if(taskId != null && taskId !="") {
                    relateContactWithTask(row.contactId, taskId, true);
                    taskId="";
                }
                gotoInfoCustomer(row.contactId,phone.customerType,phone.ani,false);

            }else{
                if(taskId != null && taskId !="") {
                	window.taskId = taskId;
                    queryCustomer('','relateContactWithTask','outCall',phoneNum);
                    findCustomer();
                }
            }
        }else{
            $.ajax({
                type : "post",
                url : "/common/splicePhone",
                data : "inPhone=" + phoneNum,
                async : false,
                dataType:"json",
                success : function(data){
                    if(data.result){
//                        insrueCheck(dnis,data.dto.provName,data.dto.cityName);


                        phone.province = data.dto.provName+data.dto.cityName;

                        phone1 = data.dto.phn1;
                        phone2 = data.dto.phn2;
                        phone3 = data.dto.phn3;
                        $('#d_provid').val(data.dto.provid);
                        $('#d_cityid').val(data.dto.cityid);
                        phoneType=  data.dto.phonetypid;
                    }else{
                        phone.province="";

                        alertWin('系统提示', data.msg);
                        return;
                    }
                    
                    //库中不存在
                    $.post("/customer/mycustomer/potentialContact/add",{
                        phn1:phone1,
                        phn2:phone2,
                        phn3:phone3,
                        phonetype:phoneType
                    },function(data){
                        if(data.result){
                            phone.customerId=data.potentialContactId;
                            phone.customerType="2";
                            //显示当前客户信息
                            if(taskId != null && taskId !="") {
                                relateContactWithTask(data.potentialContactId, taskId, true);
                                taskId="";
                            }
                           gotoInfoCustomer(data.potentialContactId,2,phone.ani,false);
                        }else{
                           // alertWin('系统提示',date.msg);
                        }
                    });
                }
            });

        }
    });


}

function comingIn(inPhone,dnis,connId,ctistatus,customerId,customerType,leadId){

/*    if(phone.status>0){
        alertWin("系统提示","电话还没有就绪");
        return;
    }*/
    phone.isOutBoound = false;
    phone.leadId="";
    phone.customerId="";
    phone.customerType="";
    phone.isOutBoound=false;
    if(! checkSoftPhoneApplet()){
        inPhone = $('#d_inPhone').val();//呼入号码
        dnis = $('#d_dnis').val();//落地号

    }else{

        phone.cticonnid =connId;

        phone.connStates = ctistatus;

        if(customerId != null && customerType != null){
            phone.customerId = customerId;
            phone.customerType= customerType;
            phone.leadId=leadId;
            if(phone.status != 2){
                istransferComing=1;
                return;
            }
        }else{
            istransferComing=0;
            phone.customerId = "";
            phone.customerType= "";
            phone.leadId = "";
        }
    }

    var company= "";
    phone.ani = inPhone;
        //解析电话号码
    $.ajax({
        type : "post",
        url : "/common/splicePhone",
        data : "inPhone=" + inPhone,
        async : false,
        dataType:"json",
        success : function(data){
            if(data.result){
                insrueCheck(dnis,data.dto.provName,data.dto.cityName);
                phone.province = data.dto.provName+data.dto.cityName;
                phone1 = data.dto.phn1;
                phone2 = data.dto.phn2;
                phone3 = data.dto.phn3;
                $('#d_provid').val(data.dto.provid);
                $('#d_cityid').val(data.dto.cityid);
                phoneType=  data.dto.phonetypid;
            }else{
                phone.province="";

                alertWin('系统提示', data.msg);
                return;
            }
        }
    });

    if(phoneType==4) phone.ani = phone2;


    phone.dnsi= dnis;
    //phone.tollFreeNum =  landingPhone;
    phone.isOutBoound = false;
    $.ajax({
        type : "post",
        url : "/common/get400",
        data : "dnis=" + dnis,
        async : false,
        dataType:"json",
        success : function(data){
            console.info("获取4000.....");
       //  data = eval("(" + data + ")");
          if(data.result){
          phone.tollFreeNum  = typeof(data.mediaPlan.tollFreeNum)=="undefined" ? "":data.mediaPlan.tollFreeNum;
          phone.company  = typeof(data.mediaPlan.mediaName)=="undefined" ? "":data.mediaPlan.mediaName;
          }else{
              phone.tollFreeNum="";
              phone.company="";
              //alertWin('系统提示', data.msg);

          }
        }    
      });




    phone.changeStatus(3);
    console.info("3333振铃");

    phone.changeStatus(4);
    console.info("444通话中");
    //清空挂机数据
    cleanOnHook();

    //调用推荐商品
    incomingCall(phone.dnsi);



    $('#w_inphone').window('close');
}

//大都会保险活动提示
function insrueCheck(dnis,provinceName,cityName){
    $.ajax({
        type : "post",
        url : "/cart/insureValidate",
        data : {'dnis':dnis,'provinceName':provinceName,'cityName':cityName},
        async : false,
        dataType:"json",
        success:function(data){
            phone.insure = "";
            if(data){
                    phone.insure="(大都会赠险活动)";
            }
        },
        error:function(msg){
            phone.insure="";
        }
     });

}

$(document).ready(function(){
    $("#h_isContact").bind('click',function(){
        $("#warnMsg").html("");

        changeIsContact();
    });
    $("#d_selcontact").bind('click',function(){
        changeSelConact();
    });
     //初始化软电话号码盘
    initNormalPhone();
    //初始化省市
    initProvinceCity();

    $('#d_marketingPlan').combobox({
        onSelect: function(param){
            if(param.id != 10){
                $('#v_reson').hide();
            }else{
                $('#v_reson').show();
            }
        }
    });


    //是否显示On/off


});

var changeSelConact=function(){
    var dselconact= $("#d_selcontact").attr("checked") ? true:false;
    if(dselconact){
        $("#d_seat").combobox({
            required:true
        });

    }else{
        $("#d_seat").combobox({
            required:false
        });

    }
}

var changeIsContact=function(){
    $("#warnMsg").html("");
    var h_iscontact =$("#h_isContact").attr("checked") ? true:false;
    $.ajax({
        type : "post",
        url : "/common/get/CurrentPlanAndPlanList",
        data : "h_instId=" + phone.instId+"&customerId="+phone.customerId,
        async : false,
        dataType:"json",
        success : function(data){
            if(! data.isfinished){
                $('#d_marketingPlan').combobox({disabled:true});
            }else{
                $('#d_marketingPlan').combobox({disabled:false});
            }

            //老客户判断
            if(h_iscontact){
                //是否有联系老客的权限
                if(data.boundCustomer){
                    if( $("#seatType").val()== 'IN') {
                        //是否有创建任务的权限  1,有权限,0,没有权限
                        if(data.hasOldCustomerRole =="0"){
                            $('#h_contactTime').datetimebox({required:true,validType:'istoday[true]'});
                        }else{
                            $('#h_contactTime').datetimebox({required:false,validType:'istoday[false]'});
                        }

                    }else{
                        if(data.hasOldCustomerRole =="0"){
                            $('#h_contactTime').datetimebox({required:false,validType:'istoday[false]'});
                            $("#h_isContact").attr("checked",false);
                            $("#h_isContact").attr("disabled",true);
                            $('#d_selectTask').css("display","none");
                            $("#warnMsg").html("此客户已被其他座席绑定");
                            return false;
                        }
                    }
                 }else{
                     $('#h_contactTime').datetimebox({required:false,validType:'istoday[false]'});
                 }

            }else{
                $('#h_contactTime').datetimebox({required:false,validType:'istoday[false]'});
            }

                $("#warnMsg").html("");
                $('#d_marketingPlan').combobox({
                    data:data.leadTypeList,
                    valueField:"id",
                    textField:"name",
                    onLoadSuccess: function(){
                        $.each(data.leadTypeList, function(i,val){
                            if(val.id == data.campaignId) {
                                $('#d_marketingPlan').combobox("setValue",data.campaignId);
                            }else{
                                if( i == 0){
                                    $('#d_marketingPlan').combobox("setValue",val.id);
                                }
                            }
                        });
                    }
                });

        }

    });
    if(h_iscontact){
        //获取当前任务的线索名称
        $("#h_contactTime").datetimebox('enable');
        $('#d_selectTask').css("display","block");

        if(phone.isOutBoound){
            $("#v_selcontact").hide();
        }else{
            $("#v_selcontact").show();
        }

    }else{

        $('#h_contactTime').datetimebox('setValue', '');
        $('#h_contactTime').datetimebox('disable');

        $('#d_selectTask').css("display","none");

    }

    //初始化座席数据
    $('#d_dept').combobox({
        url : '/common/getAllDept',
        valueField : 'id',
        textField : 'name',
        onSelect : function(param) {
            var url = '/common/getGroupByDept?deptId=' + param.id;
            $('#d_group').combobox('reload', url);
            $("#d_group").combobox('setValue',"");
            $("#d_seat").combobox('setValue',"");
        }
    });
    $("#d_group").combobox({
        url : '/common/getAllGroup',
        valueField : 'id',
        textField : 'name',
        onSelect : function(param) {
            var url = '/common/getSeatByGroup?groupId=' + param.id;
            $('#d_seat').combobox('reload', url);
            $("#d_seat").combobox('setValue',"");
        }
    });
    $("#d_seat").combobox({
//        url : '/common/getSeatByGroup?groupId=' + param.id,
        valueField : 'userId',
        textField : 'displayName',
        keyHandler: {
            query: function(q){console.info(q);
                if(q.length>=4){
                    $("#d_seat").combobox('reload','/common/getUserByUid?uid='+q);
                    $("#d_group").combobox('reload','/common/getAllGroup');
                }
            }
        },
        onSelect:function(record){
            $("#d_dept").combobox("setValue",record.department);
            $("#d_group").combobox("setValue",record.defGrp);
        }
    });

}

function formatItemSeat(row){
    return row.displayName+"|"+row.userId;

}



var addBlack=function (node) {
    $.ajax({
        url: '/blackList/addPhoneBlackList',
        contentType: "application/json",
        data: {"customerId":phone.customerId, "customerType": phone.customerType,"phoneNum":phone.ani, "instId": phone.instId}
    });
    //防止重复加黑 点击一次加黑后隐藏按钮
    $(node).hide();
}


var callLocalhost=function(node){
    if(phone.status == 7  ){
    if($("#callback").val()==1){
              console.info("callback:callLocalhost ");
    }else{
        outphone(phone.ani,'1',phone.instId,phone.customerId,phone.customerType,1);
    }

    }
}

var callLongDistance=function(node){
    if(phone.status == 7){
        if($("#callback").val()==1){
            console.info("callback:callLongDistance ");
        }else{
            boundType=1;
            outphone(phone.ani,'2',phone.instId,phone.customerId,phone.customerType,1);
        }

    }
}

var interrupt=function(status){
    console.log("interrupt--------"+status);
    if(status == 4 || status==6){


        if($("#callback").val()==1){
            $('#end').replaceWith('<span id="end" class="end" title="结束会话" ></span>');
            phone.changeStatus(7);
        }else{
            phone.changeStatus(7);
        }

        console.info(new Date());
        //消费取数数据
        if($("#callback").val()==1){

            console.info("callback........");

        }else{
        $.ajax({
            type : "post",
            url : "/common/phone/interrupt",
            data : "h_instId=" + phone.instId +"&time_length="+phone.seconds+"&ctiedt="+phone.ctiedt+"&connId="+phone.cticonnid,
            async : false,
            dataType:"json",
            success:function(data){
                //phone.changeStatus(7);
                //更新通话时长;
                try{
                $("#span_intradayTotalOutcallTime").html(data.totalOutcallTime+"秒");
                }catch(err){
                    console.info("error");
                }

            }
        });
        }

        console.info(new Date());

    }

}


var openhookwin = function(){
    $("#hookMsg").html("");
    $("#notReadyCode").combobox("setValue","1204");
    $("#callResult option").eq(0).attr('selected', 'true');
    $("#callType option").eq(0).attr('selected', 'true');
    $('#h_contactTime').datetimebox({required:false,validType:'istoday[false]'});
//    if(phone.isOutBoound){
//      $("#hookMsg").html("");
//    }else{
//      $("#hookMsg").html("如果你用的是话机，为了方便进线，请让话机处于挂机状态！");
//    }
    //时候将在联系不可用
    if(phoneOUT){
        $("#h_isContact").attr("disabled",true);
    }else{
        $("#h_isContact").attr("disabled",false);
    }
    //操作滚动条
    $("#content").animate({scrollTop:0},0);
     $("#warnMsg").html("");
    //判断任务是否已推荐
    if( phone.isOutBoound && phone.connStates != "Established" ){
        $('#hook').window('open');
        //设置初始化数据
        if($("#seatType").val() == "IN"){
            $("input[name='hook_st']:eq(2)").attr("checked",'checked');
        }else{
            $("input[name='hook_st']:eq(1)").attr("checked",'checked');
        }

    }else if(hasRecommendedItem() ){
        $('#hook').window('open');
        //设置初始化数据
        if($("#seatType").val() == "IN"){
            $("input[name='hook_st']:eq(2)").attr("checked",'checked');
        }else{
            $("input[name='hook_st']:eq(1)").attr("checked",'checked');
        }

    }else if(!phone.leadId){
        $('#hook').window('open');
        //设置初始化数据
        if($("#seatType").val() == "IN"){
            $("input[name='hook_st']:eq(2)").attr("checked",'checked');
        }else{
            $("input[name='hook_st']:eq(1)").attr("checked",'checked');
        }

    }
    else{
        //添加没有推荐提示
        recommendMsg('您还没有推荐产品!!', 'red');

    }



}


/**
 *， 可以双步转接


    * 电话转接
    *
    * @param callNum
    */




var transferPhone = function(){
   // var callNum = $("#notelist").combobox("getValue");
    //客服务中心号码
    var callNum =$("#d_csphone").val();//""; 62005
   //$("#div_transferPhone").attr("class","c_combo retrieve");
    if(callNum){
    switch (istransferPhone){
        case 0:
            istransferPhone=2;
//            $("#div_transferPhone").attr("class","c_combo retrieve");
//            $("#transferPhoneBtn").removeClass('icon').addClass('retrieve').attr("title","取回");
//            $("#div_transferPhone").attr("title","取回");
            //转接1
            initiateTransfer(callNum);
            //单步转接
            //singleStepTransfer(callNum);
            //phone.changeStatus(7);
            istransferPhone=0;
            console.info('电话转接:initiateTransfer');
            break;
        case 1:
            completeTransfer();
            istransferPhone=0;
            console.info('完成转接:completeTransfer');
            break
        case 2:    //取回
            $("#transferPhoneBtn").removeClass('retrieve').addClass('icon').attr("title","转接到客服中心");
            releaseLine2();
            retrieve();
            istransferPhone=0;
            console.info('电话转接:retrieve');
            break;

        }

    }
}


/**
 * 电话号码盘初始化
 */
var initNormalPhone = function(){

    $('#notelist').combobox({
        url : '/common/normalPhone',
        valueField : 'paraValue',
        textField : 'paraValue',
        filter: function(q, row){
            return normalPhonefilter(q,row);
        },
        formatter: function(row){
            return row.name+"("+row.paraName+")";
        },
        onSelect : function(row) {
            console.info(row.paraName.indexOf("-") ==-1);
            if(row.paraName.indexOf("-")== -1 ){
               $("#proAndCtili").show();
            }else{
                $("#proAndCtili").hide();
            }

        }
// ,
//        onLoadSuccess: function(){
//            $(this).combobox('clear');
//        } ,
//        onChange:function(){
//            if(!$('#province${instId}').combobox('getValue') && !$('#cityId${instId}').combobox('getValue')) {
//                cleanAddressItem('${instId}');
//            }
//        }
    });



}




var normalPhonefilter = function(q,row){
    var searchvar = row.name+row.paraName;
    return searchvar.indexOf(q) >=0;
}



var initProvinceCity  =function(){
    $('#prolist').combobox({
        url : '/static/plugin/province.json',
        valueField : 'provinceId',
        textField : 'provinceName',
        filter: function(q, row){
            return addressfilter(q,row);
        },
        onSelect : function(param) {
            $('#citylist').combobox({ onLoadSuccess : function(){}});
            $("#citylist").combobox({
                url : '/common/city?provinceId=' + param.provinceId,
                valueField : 'areaCode',
                textField : 'areaCode',
                data:[{"cityId":"","areaCode":""}],
                formatter: function(row){
                    return formatCity(row);
                },
                filter: function(q, row){
                    return addressfilter(q,row);
                }
            });

        }
    });

    $('#citylist').combobox({
        url:'/static/plugin/city.json',
        valueField : 'areaCode',
        textField : 'areaCode',
        filter: function(q, row){
            return addressfilter(q,row);
        },

        formatter: function(row){
            return formatCity(row);
        },
        onLoadSuccess: function(){
            $(this).combobox('clear');
        },onSelect : function(param) {
           var address_province_id = param.value.split(address_split)[2];

            $('#prolist').combobox('reload','/common/province');
            $('#prolist').combobox('setValue', address_province_id);

        }
    });
}

function clearProvinceCity(){
    $('#prolist').combobox("setValue","");
    $('#citylist').combobox("setValue","");
}


function addressfilter(q,row){
    q= q.toUpperCase();
    var searchvar=row.value.split(address_split)[0];
    return searchvar.indexOf(q) >=0;
}

function formatCity(row){
    return row.areaCode+"|"+row.cityName;

}

/**
 *提示软电话的状态
 * @param errorcode
 */
function showCtiError(result){


    var stu = eval('('+result+')');
    if(stu.errorCode == 1140){

        msgSlide("登陆失败,请重新登陆");
        if(phone) phone.changeStatus(15);
        return;
    }else if (stu.errorCode == 1161){
        msgSlide("不正确的状态对象");
        if(phone){
            if(ctiIsLogIn==0){
                phone.changeStatus(15);
                return;
            }
        }
    }else if (stu.errorCode == 10116){
        msgSlide("尚未与CTI建立连接");
        if(phone) phone.changeStatus(15);
        return;
    }else if (stu.errorCode == 10001){
        msgSlide("请求参数不正确");
        if(phone) phone.changeStatus(15);
        return;
    }else if (stu.errorCode == 1160){
        //msgSlide("电话没有摘机，请摘机后外拨");
    }else if (stu.errorCode == 1172){
        //msgSlide("分机资源忙");
        return;
    }else if (stu.errorCode == 1199){
        msgSlide("话机已签出,请签入");
    }else if(stu.errorCode == 10101){
        //console.info("error:"+result);

    }else if(stu.errorCode == 1152){
        //console.info("error:"+result);
        return;
    } else if(stu.errorCode == 1172){
        //console.info("error:"+result);
        //  if(phone) phone.changeStatus(7);
        return;
    }





    if(ctiIsLogIn ==0){
        if(phone){
            phone.changeStatus(15);
        }else{
            console.info("isInitiate_phone.....");
        }
    }else{

            if(isInitiate==1){
             console.info("isInitiate.....");
            }else{
                //if(phone) phone.changeStatus(2);
                msgSlide(stu.message);
            }

    }

}

/**
 * 软电话登录
 */
function ctilogin(){
    if (checkSoftPhoneApplet()){
        isSoftPhoneLogin=1;
        document.location.reload();
        //registerSoftPhone($("#cti_host").val(),$("#cti_port").val(),$("#cti_host_back").val(),$("#cti_port_back").val(),"voice",$("#cti_agentId").val(),"",$("#cti_dn").val());
    }
}

/**
 * 关闭浏览器时软电话退出
 */
(function(){
    window.onbeforeunload = function(e) {
         if(!$('.logout').data('isNor')){
             return '如果离开当前页面所做的修改数据将丢失！';
         }
        var isIE=document.all?true:false;
        if(isIE){//IE浏览器
            var n = window.event.screenX - window.screenLeft;
            var b = n > document.documentElement.scrollWidth-20;
            if(b && window.event.clientY<0 || window.event.altKey){
                closeSales();
                console.info("关闭");
            }else{
                console.info("刷新");
            }
        }
        else{//火狐浏览器
            if(document.documentElement.scrollWidth != 0 && isSoftPhoneLogin ==0){
                closeSales();
                console.info("关闭");

            }else{
                console.info("刷新");
            }

        }


        }


})()

function closeSales(){
    agentLogout();
    $.ajax({
        type : "get",
        url : "/logout",
        data : "name=" + $("#fn-bg").html().trim(),
        async : false,
        dataType:"json",
        success : function(data){

        }
    });
}

/**
 * 软电话专用弹出窗口
 * @method
 * @param msg 提示内容
 */
function msgSlide(msg){
    $.messager.show({
        title:'',
        msg:msg,
        showType:'slide',
        style:{
            right:'',
            top:document.body.scrollTop+document.documentElement.scrollTop+30,
            bottom:'',
            borderTopWidth:0,
//            boxShadow:'0 0 4px rgba(0,0,0,.2)',
            background:'-webkit-gradient(linear, 0 0, 0 100%, from(#fffcd2), to(#fff8bc))',
            borderRadius:'0 0 4px 4px'
        }
    });
}


function changeCtiOnAndOff_off(){
    $("#cti_onAndOff").attr("src","/static/images/phone-lamp-red.png");
}

function changeCtiOnAndOff_on(){
    $("#cti_onAndOff").attr("src","/static/images/phone-lamp-gray.png");
}

function hiddenCtiOnAndOff(){
    $("#cti_onAndOff").hide();
}

function isDailSelf(){
    if (checkSoftPhoneApplet()){
        if($("#ctiPhoneType").val()==1){
            if(dailSelf==1){
                console.info("dailSelf..."+1);
                msgSlide("系统资源忙,请稍候再试...");
                return true;
            }
        }
    }
    return false;

}

function bottomLeft(type,name,date,content){
//    type = "现场主控";
//    name = "卞月云";
//    date = "2014-02-11 13:20:00";
//    content = "2分钟后广告开播2分钟后广告开播2分钟后广告开播2分钟后广告开播2分钟后广告开播";
    $.messager.show({
        title:type+"【"+name+"】"+":",
        msg:"<div style='padding:5px'><span style='font-weight:bold;color:#666;'>"+date+"</span>"+"&nbsp;:&nbsp;"+content+"</div>",
        showType:'show',
        timeout:0,
        style:{
//            width:120,
            borderColor:'#2068b0',
            backgroundColor:'#2068b0',
            left:0,
            right:'',
            top:'',
            bottom:23
        }
    });
}
function bottomRight(type,name,date,content){
//    type = "业务主管";
//    name = "陆洪涛";
//    date = "2014-02-11 13:20:00";
//    content = "通知:下午2::00开业务会议，请勿缺席!";
    $.messager.defaults = { ok: "是", cancel: "否"};
    $.messager.show({
        title:type+"【"+name+"】"+":",
        msg:"<div style='padding:5px'><span style='font-weight:bold;color:#666;'>"+date+"</span>"+"&nbsp;:&nbsp;"+content+"</div>",
        showType:'show',
        timeout:0,
        style:{
            borderColor:'#2068b0',
            backgroundColor:'#2068b0',
            left:'',
            right:0,
            top:'',
            bottom:23
        }
    });


}

/**
 * 设置坐席等级
 * @param rmsg
 * @return {string}
 */

function getAagentLevel(rmsg){
    if(rmsg){
        console.info("设置坐席等级:"+rmsg);
        rmsg = rmsg.substring(rmsg.length-2,rmsg.length);
        if(rmsg == 20){
            return "A";
        }else if(rmsg == 15){
            return "B";
        }else if(rmsg == 10){
            return "C";
        }else if(rmsg == 5){
            return "D";
        }else if(rmsg == 1){
            return "B";
        }else{
            return "";
        }

    }else{
        return "";
    }
}