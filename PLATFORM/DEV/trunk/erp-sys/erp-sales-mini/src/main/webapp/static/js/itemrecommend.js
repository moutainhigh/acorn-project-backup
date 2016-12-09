/**
 * 商品推荐
 * User: gdj
 * Date: 13-5-24
 * Time: 下午1:47
 * To change this template use File | Settings | File Templates.
 */


$().ready(function(){


    $("#recommend-button").click(function(){
        var instId = $("#recommend-button").attr("instId");
        if(instId){
            var nccodes = $("input[name='item-recomment']:checked").map(function () {
                return $(this).val();
            }).get().join(',');

            var tollFreeNum = $("#recommend-button").attr("tollFreeNum");
            var campainId = $("#recommend-button").attr("campainId");
            $.ajax({
                type: 'POST',
                url: "/product/recommend/submit",
                data:{
                        instId:instId,
                        tollFreeNum: tollFreeNum,
                        campainId: campainId,
                        nccodes:nccodes
                },
                beforeSend:function(){
                    recommendMsg('','', true);
                },
                success: function(result){
                    if(result.indexOf("失败") > -1){
                        recommendMsg(result, "red", false);
                    } else {
                        recommendMsg(result, "blue", false);
                    }
                },
                dataType: "text"
            });
        } else {
            recommendMsg("还没有创建任务", "red", false);
        }
    });

})

function clearRecommendItems(){
    $("#recommend-container").empty();
}

function search(nccode){
    $("#tbKeyword").val(nccode);
    $("#tbKeyword").search();
}

function incomingCall(tollFreeNum){
    clearRecommendItems();
    $.post("/product/recommend/incoming", { campainId: phone.campainId, tollFreeNum: tollFreeNum }, function(data){
        $("#recommend-title").text("("+data.name+")").attr('title',data.name).show();
        if($.isArray(data.details)) {
            for (var i = 0; i < data.details.length; i++) {
                var row = data.details[i];
                if(row){
                    appendRecommendItem($(row).attr("nccode"), $(row).attr("spellname"), true);
                }
            }
        }
    });
    //recommendMsg('','',false);
}

function outgoingCall(campainId, instId){
    clearRecommendItems();

    $("#recommend-button").attr("instId", instId);
    $("#recommend-button").attr("campainId", campainId);
    $("#recommend-button").attr("tollFreeNum", "");
    $("#recommend-button").attr("sourceType", "");
    $.get("/task/querySourceType", { instId: instId }, function(data){
        $("#recommend-button").attr("sourceType", data);
    });
    $.post("/product/recommend/outgoing", { campainId: campainId, instId:instId  }, function(data){
        $("#recommend-title").text("("+data.name+")").attr('title',data.name).show();
        if($.isArray(data.details)) {
            for (var i = 0; i < data.details.length; i++) {
                var row = data.details[i];
                if(row){
                    appendRecommendItem($(row).attr("nccode"), $(row).attr("spellname"), true);
                }
            }
        }
    });


    //recommendMsg('','',false);
}

function appendRecommendItem(nccode, spellname, recommended){
    if(recommended){
        $("#recommend-container").append("<p id='recommend-custom-item'><input class='checkbox fl' onchange='return changeItem(this)' name='item-recomment' type='checkbox' value='"+ nccode +"' /><a href =\"javascript:search('"+ nccode +"');\" title='"+spellname+"'>"+ spellname+"</a></p>");
    } else {
        var instId = $("#recommend-button").attr("instId");
        if(instId){
            var nccodes = $("input[name='item-recomment']").map(function () {
                return $(this).val();
            }).get();
            //alert(JSON.stringify(nccodes));
            if($.inArray(nccode, nccodes) == -1){
                $("#recommend-container-item").remove();
                $("#recommend-container").append("<p id='recommend-container-item'><input class='checkbox fl' onchange='return changeItem(this)' name='item-recomment' type='checkbox' value='"+ nccode +"' /><a href =\"javascript:search('"+ nccode +"');\" title='"+spellname+"'>"+ spellname+"</a></p>");
            }
        }
    }
}

function changeItem(ele){

    $("#recommend-button").click();
    var instId = $("#recommend-button").attr("instId");
    if(instId) {
        $(ele).attr("disabled", "disabled");
    } else{
        $(ele).removeAttr("checked");
    }
    return instId;
}

function hasRecommendedItem() {
    //跟踪订单
    if($("#recommend-button").attr("sourceType") == "5"){
        return true;
    }
    var items = $("input[name='item-recomment']:checked").map(function () {
        return $(this).val();
    }).get();
    if(items && items.length > 0) {
        return true; //已经推荐
    } else {
        //没有推荐商品则不需要推荐
        items = $("input[name='item-recomment']").map(function () {
            return $(this).val();
        }).get();
        if(items.length > 1) {
           return false; //没有推荐必须推荐
        }
        else if(items.length == 0) {
            return true; //空,可以不推荐
        }
        else if($("#recommend-container").has("#recommend-custom-item")) {
            return true; //自定义商品,可以不推荐
        }
        return false;
    }
}

function recommendMsg(msg, color, remote){
    if($.contains("#recommend-wrap", "div.tips")){
        $("#recommend-wrap").show();
    } else {
        $("#recommend-wrap").append('<div class="tips" ></div>');
    }
    if(remote){
        $("#recommend-container div.c_mask").html('<img src="static/images/loading.gif">');
    }else{
        $("#recommend-wrap div.tips").html('<span >'+msg+'</span>');
    }
    $("#recommend-wrap div.tips").fadeOut(2000);
}

function showIncomingCall(){
    window.queryC(frameElement.id,'returnIncomingCall','incomingCall');
}

function returnIncomingCall(tollFreeNum, telephone, contactId, contactType){
    createRecommendTask(tollFreeNum, telephone, contactId, contactType);
}

/**
 * 定位到一个客户,或新增客户创建任务
 * @param tollFreeNum
 * @param telephone
 * @param contactId
 * @param contactType
 * @param leadId
 */
function createRecommendTask(tollFreeNum, telephone, contactId, contactType, leadId){

    $("#recommend-button").attr("tollFreeNum", tollFreeNum);
    $("#recommend-button").attr("telephone", telephone);
    $("#recommend-button").attr("sourceType", $("#callback").attr("value") == "1" ? "4" : "0");  //CampaignTaskSourceType

    $.post("/product/recommend/lead", {
        tollFreeNum: tollFreeNum,
        telephone: telephone,
        leadId: leadId,
        contactId: contactId,
        contactType: contactType,
        sourceType: $("#recommend-button").attr("sourceType"), //CampaignTaskSourceType
        cticonnid: phone.cticonnid,
        begindate: phone.ctisdt,
        acdgroup:phone.acdId,
        callback: $("#callback").attr("value") == "1",
        campainId:phone.campainId
    }, function(result){
        result = $.isPlainObject(result) ? result : $.parseJSON(result);
        if(result){
            if(result.errorMsg){
                window.parent.alertWin('系统提示',result.errorMsg);
            }else {
                phone.instId=result.instId;
                phone.leadId =result.leadId;
                phone.leadInterId =result.leadInterId;
                $("#recommend-button").attr("instId", result.instId);
            }
        }
    },"json");
}

function getRecommandTaskId(){
    return $("#recommend-button").attr("instId");
}

/**
 * 定位多个客户后回调,创建任务.
 * @param contactId
 * @param sourceType
 */
function createRecommendTaskCallback(contactId,sourceType){
	
    $("#recommend-button").attr("tollFreeNum", phone.dnsi);
    $("#recommend-button").attr("telephone", phone.ani);
    $("#recommend-button").attr("sourceType", sourceType == "1" ? "4" : "0");

    $.post("/product/recommend/lead", {
        tollFreeNum: phone.dnsi,
        telephone: phone.ani,
        leadId: phone.leadId,
        contactId: contactId,
        contactType: phone.customerType ,
        sourceType: sourceType == "1" ? "4" : "0", //CampaignTaskSourceType
        cticonnid: phone.cticonnid,
        begindate: phone.ctisdt,
        acdgroup:phone.acdId,
        callback: $("#callback").attr("value") == "1",
        campainId:phone.campainId
    }, function(result){
        result = $.isPlainObject(result) ? result : $.parseJSON(result);
        if(result){
            if(result.errorMsg){
                window.parent.alertWin('系统提示',result.errorMsg);
            }else {
                phone.instId=result.instId;
                phone.leadId =result.leadId;
                phone.leadInterId =result.leadInterId;
                $("#recommend-button").attr("instId", result.instId);
            }
        }
    },"json");
}
