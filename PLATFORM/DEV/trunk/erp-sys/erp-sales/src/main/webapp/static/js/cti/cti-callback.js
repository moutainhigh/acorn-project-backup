/**
 * author:ZHL 2013.05.27
**/



var callstatus = 0;  //0,进线;1,外呼;
var isRemoved=false ; //是否是interactionRemoved 时的
var autoReady=false;
var findoutphone = 0; // 1.查询外呼
var istransferPhone =0; //0.非转接,1.转接
var istransferComing =0 ;//0.非转接进线;1.转接进线
var citOnOff = 0;//0:挂机;1:摘机
var callbackIsRelease = 0;//0:已挂机 1:通话中
var dailSelf= 0; //0:不是打自己,1:是打自己
//applet 初始化完成
function onInitialized(){
	
}
/**
 * 座席状态发生变化触发此函数（如Ready、NotReady）
 */
function onAgentStatusChanged(result){
    if(result.voice == "LoggedIn") {
        ctiIsLogIn=1;
        $("#cti_jre").hide();
       // $("#ctiStatus").html(result.voice);
        notReady(null);console.info("cti--notReady");
        phone.changeStatus(2);
    }else if(result.voice=="Ready"){
        phone.changeStatus(0);
    }else if(result.voice=="NotReady"){
         try{
              if(result.reason==1200){
                  if(phone.status != 7 ){
                      phone.changeStatus(-1);
                  }

                  notReadyState =0;
                  return;
               }
         }catch (err){

         }
        console.info("notReady>>>>>>>>>>>>>>>>>>>");
        if(notReadyState==1){
            phone.changeStatus(1);
        }else{
            if(isMyRelease||!isRemoved){
                if(phone){
                    if(result.reason == 1201){
                        phone.changeStatus(1);
                    }else{
                        console.info("reasonCode:"+result.reason);
                        phone.changeStatus(2);
                    }

                }
            }
        }


         notReadyState =0;
    }else if(result.voice=="LoggedOut"){
        ctiIsLogIn=0;
        phone.changeStatus(15);
    }
    isRemoved=false;
}

/**
 * 连接状态发生变化触发此函数
 * @param mediaType
 * @param result
 * @return
 */
function onConnectionStatusChanged(result){
	console.log(result);
	if(result.voice=="Registered"){
		login();
    }else if(result.voice=="Disconnected"){
       phone.changeStatus(15);
	}else{
        connStatus=0;
    }
	$(".connectionStatus").text(result.voice);
}

/**
 * 软电话处理异常触发此函数
 * @param result
 * @return
 */
function onError(result){
    showCtiError(result);
    console.log(result);
}

/**
 * 座席收到新会话触发此函数
 * @param result
 * @return
 */
function onInteractionAdded(result) {
    isMyRelease = false;
    autoReady = false;

	console.log("onInteractionAdded:"+result);
    if(result.status=="Ringing"){
        callstatus=0;
        isInitiate=0;
        phone.ctisdt = result.timeStamp;
        console.info("istransferComing:"+istransferComing);
        if(istransferComing==0){
            callAnswer();
        }else{
            istransferComing=1;
        }


        //设置坐席等级

        try{
            if(result.attachedData.RTargetRequested){
                console.info("设置坐席等级------");
                $("#d_employeeType").html(getAagentLevel(result.attachedData.RTargetRequested));
            }else{
                console.info("设置坐席等级------");
                $("#d_employeeType").html("--");
            }
        }catch(err){
            console.info("设置坐席等级------");
        }


        try{
          //ACD组
         if(result.attachedData.SKILL_GROUP){
             phone.acdId = result.attachedData.SKILL_GROUP;
             console.info("result.acdId------"+phone.acdId);
         }else{
             console.info("result.acdId------");
             phone.acdId ="";
         }
        }catch(err){
            console.info("result.acdId------");
            phone.acdId ="";
        }

        if(result.callType!="Consult") {
            console.info("正常进线...");
            comingIn(result.ani,
                result.dnis,
                result.connId,
                result.status,
                null,
                null,
                null);
        }else {

            try{
                //预测外呼
                if(result.attachedData.batch){
                    console.info("预测外呼");
                    phone.campainId = result.attachedData.batch;

                    comingIn(result.attachedData.GSW_PHONE.substr(1),
                        result.thisDN,
                        result.connId,
                        result.status,
                        null,
                        null,
                        null);
                }else{
                    phone.campainId ="";
                    comingIn(typeof(result.attachedData.ani) == "undefined" ? result.otherDN:result.attachedData.ani,
                        result.dnis,
                        result.connId,
                        result.status,
                        result.attachedData.customerId,
                        result.attachedData.customerType,
                        result.attachedData.leadId);
                }



            }catch(err){
                phone.campainId ="";
                console.info("result.campainId------");

            }


        }
    }else if(result.status=="Dialing" && result.callType!="Consult"){
        callstatus=1;
        isInitiate=0;
        //外呼回调
           console.info("外呼...");
        try{
            console.info(83);
            document.getElementById(outphoneFrameId).contentWindow.loadingFadeOut();
        }catch(err){

        }


        console.info("findoutphone:"+findoutphone);
      if(findoutphone!=1){
            phone.changeStatus(5);
        }


    }else if(result.attachedData.command == 2) {
        completeTransfer();
    }

}
/**
 * 会话状态更新（如会话被收回）触发此函数
 * @param result
 * @return
 */
function onInteractionUpdated(result) {
	console.log("onInteractionUpdated:"+result);
    //
    // callType":"Outbound"
    if(result.callType=="Outbound" &&  result.status=="Established" ){
        if(findoutphone == 1){
            phoneOUTcallback();
        }else{
            phone.ctisdt = result.timeStamp;
            dialCallback(result);
        }
        findoutphone =0;
    }
    // callType:"Consult"
    if(result.callType=="Consult" &&  result.status=="Established" ){
         try{
             if(result.attachedData.command == 1){
                 if(istransferPhone==0){ //会议
                     //completeConference();

                 }else{//转接
                     console.info(125);
                     istransferPhone = 1;
                     $("#div_transferPhone").attr("class","c_combo ok");
                     $("#div_transferPhone").attr("title","完成");
                 }
             }
         }catch(err){
             console.info("result.attachedData.command:"+err);
         }






    }else if(result.callType=="Consult"){
        console.info(132);

        try{
            if(result.attachedData.command == 1){
                //istransferPhone=2;
            }
        }catch(err){
            console.info("result.attachedData.command:"+err);
        }


    }




}



/**
 * 会话结束触发此函数
 * @param result
 * @return
 */
function onInteractionRemoved(result) {
    callbackIsRelease = 0;
    if($('#notelist').combobox('getValue')==result.otherDN) return;
    //if(isInitiate==1) return;
    if(result.thisDN != result.otherDN){

//    console.info("sssss:"+phoneOUT+$("#seatType").val())
//    if(phoneOUT){
//        phoneOUT=null;
//        if($("#seatType").val() == "IN"){
//            phone.changeStatus(0);
//        }else{
//            phone.changeStatus(1);
//        }
//
//    }else
        phone.ctiedt = result.timeStamp;
        phone.cticonnid=result.connId;
        try{
            if($("#callback").val()==1){
                $.get("/common/callback/setCtiInfo", {v_leadInterId:phone.leadInterId,ctiedt:phone.ctiedt,connId:phone.cticonnid });
            }else{
                $.get("/common/setCtiInfo", {h_instId:phone.instId,ctiedt:phone.ctiedt,connId:phone.cticonnid });
            }
        }catch(err){
            console.info("保存CtiInfo失败!!");
        }
    if(result.callType!="Consult") {
        console.info("phone.changeStatus:"+phone.status);
        phone.changeStatus(7);
        if(!isMyRelease){
            if(callstatus == 0){
                console.info("interrupt4") ;
                if(autoReady == false) {
                    notReady("1202",3); console.info("notReady:cti :113");
                    interrupt(4);
                }else {
                    ready();
                }
            }else{
                if(autoReady == false) {
                    notReady("1202",3); console.info("notReady:cti :116");
                    interrupt(6);
                }else {
                    ready();
                }
                console.info("interrupt6");
            }

        }else{
            notReady("1202",3); console.info("notReady:cti :210");
        }
        findoutphone =0;
        isRemoved=true;
        console.log("onInteractionUpdated:"+result);
    }else{

        try{


            if(result.attachedData.command == 1){
                return;
            }
        }catch (err){

        }
        phone.ctiedt = result.timeStamp;
        console.info("phone.changeStatus:"+phone.status);
        phone.changeStatus(7);
        if(!isMyRelease){
            if(callstatus == 0){
                console.info("interrupt4") ;
                if(autoReady == false) {
                    notReady("1202",3); console.info("notReady:cti :113");
                    interrupt(4);
                }else {
                    ready();
                }
            }else{
                if(autoReady == false) {
                    notReady("1202",3); console.info("notReady:cti :116");
                    interrupt(6);
                }else {
                    ready();
                }
                console.info("interrupt6");
            }

        }else{
            notReady("1202",3); console.info("notReady:cti :210");
        }
        findoutphone =0;
        isRemoved=true;
        console.log("onInteractionUpdated:"+result);
    }
    }else{
        console.info("dailSelf..."+0);
        dailSelf=0;
    }
}

/**
 *
 * @param result
 */
function onUserEvent(result){
    console.info("onUserEvent...."+result);

    switch(result.command){
        case "ready":
            ready();
            console.info("ready");
            break;
        case "notready":
            notReady();
            console.info("notready");
            break;
        case "releasecall":
            release();
            console.info("releasecall");
            break;
        case "bargein":
            console.info("bargein"+result.targetDN);
            singleStepConference(result.targetDN);
            console.info("bargein");
            break;
        case "message":
            console.info("message");

            //alertWin("系统消息",result.message);
            var v_type = result.type;

            var v_name = result.agentId;
            var v_date = date2str(new Date(),"yyyy-MM-dd hh:mm:ss");
            console.info(v_date);
            var v_content = result.message;
            if(v_type == 1){
                v_type="现场主管";
                bottomLeft(v_type,v_name,v_date,v_content);
            }else{
                v_type="业务主管";
                bottomRight(v_type,v_name,v_date,v_content);
            }
            break;

    }
}


function date2str(x,y) {
    var z = {M:x.getMonth()+1,d:x.getDate(),h:x.getHours(),m:x.getMinutes(),s:x.getSeconds()};
    y = y.replace(/(M+|d+|h+|m+|s+)/g,function(v) {return ((v.length>1?"0":"")+eval('z.'+v.slice(-1))).slice(-2)});
    return y.replace(/(y+)/g,function(v) {return x.getFullYear().toString().slice(-v.length)});
}

function hookStatusChanged(result) {
    console.info("onUserEvent...."+result);
    if(result=="onHook" ){
        citOnOff=0;
        console.info("onUserEvent....（０）："+result);
        if(getDnStatus(null)){
            changeCtiOnAndOff_on();
            if(phone.status == "-1"){
                phone.leavingReason="";
                notReady("1203");
                phone.changeStatus(2);
            }
        }else{
            changeCtiOnAndOff_off();
        }
    }else{
        citOnOff=1;
        changeCtiOnAndOff_off();
        if(callbackIsRelease == 0 && $("#ctiPhoneType").val()==1){
            if(isDial == 0) {
                notReady("1200");
            }
        }
        console.info("onUserEvent....（１）："+result);
    }
}

