/**
 * author:ZHL 2013.05.27
**/
var notReadyState = 0; // 1:就绪后处理;
var mediaType = "voice";
var ctiIsLogIn = 0;//0未登录,1成功登录;
var isDial = 0;//是否外呼
function checkSoftPhoneApplet(){
	if(typeof(SoftPhone) !="undefined"){
		return true;
	}
	return false;
}

function callAnswer() {
    if(!checkSoftPhoneApplet()){
        return false;
    }
    var result = SoftPhone.answer(1,null);
    if (result == 1) {
        return true;
    }
    return false;
}


/**
 * 座席登入
 * @param mediaType(voice, media)
 * @param placeId
 * @return
 */
function login() {
	if(!checkSoftPhoneApplet()){
		return false;
	}
	var result = SoftPhone.login(mediaType);
	if (result == 1) {
		return true;
	}
	return false;
}

/**
 * 座席登出
 * @param mediaType(voice, media)
 * @return
 */
function agentLogout() {
	if(!checkSoftPhoneApplet()){
		return false;
	}
    try{
	var result = SoftPhone.logout(mediaType);
	if (result == 1) {
		return true;
	}
    }catch(e){

    }
	return false;
}

/**
 * 置座席状态为Ready
 * @param mediaType
 * @return
 */
function ready() {
	if(!checkSoftPhoneApplet()){
		return false;
	}


    if(!getDnStatus(null)){
        return false;
    }

    var result = SoftPhone.ready(mediaType);
    if (result == 1) {
        if(phone)phone.changeStatus(0);
        return true;
    }

    return false;

}

/**
 * 挂机
 * @return
 */
function release() {
    if(!checkSoftPhoneApplet()){
        return false;
    }
    var result = SoftPhone.release(1,"{params:{Reasons:{agentHangUp:'1'}}}");
    if (result == 1) {
        return true;
    }
    return false;
}

/**
 * 挂机
 * @return
 */
function releaseLine2() {
    if(!checkSoftPhoneApplet()){
        return false;
    }
    var result = SoftPhone.release(2,null);
    if (result == 1) {
        return true;
    }
    return false;
}

/**
 * 单不转接
 * @param phoneNo
 * @return {boolean}
 */

function singleStepTransfer(phoneNo) {
	
	if(!checkSoftPhoneApplet()){
		return false;
	}
    console.info("单步转接....");
	var result = SoftPhone.singleStepTransfer(1,phoneNo,null);
	if (result == 1) {
		return true;
	}
	return false;
}

/**
 * 开始转接
 * @param phoneNo
 * @return {boolean}
 */
function initiateTransfer(phoneNo) {

    if(!checkSoftPhoneApplet()){
        return false;
    }
    var params = "{'params' : {'UserData':{'ani' : "+phone.ani+",'customerId' : "+phone.customerId+",'customerType':"+phone.customerType+",'leadId':"+phone.leadId+"}}}";
    console.info("params:"+params);
    var result = SoftPhone.initiateTransfer(1,phoneNo,params);
    if (result == 1) {
        return true;
    }
    return false;
}

// 转接完成
function completeTransfer() {
    if(!checkSoftPhoneApplet()){
        return false;
    }
    SoftPhone.completeTransfer(null);

}

/**
 * 置座席状态为hold
 * @param mediaType
 * @return
 */
function hold() {
	if(!checkSoftPhoneApplet()){
		return false;
	}
	var result = SoftPhone.hold(1,null);
	if (result == 1) {
		return true;
	}
	return false;
}
/**
 * 取回电话
 * @param mediaType
 * @return
 */
function retrieve() {
	if(!checkSoftPhoneApplet()){
		return false;
	}
	var result = SoftPhone.retrieve(1,null);
	if (result == 1) {
		return true;
	}
	return false;
}

/**
 * 发起两步会议
 * @param mediaType
 * @return
 */
function initiateConference(otherDn) {
    if(!checkSoftPhoneApplet()){
        return false;
    }
    SoftPhone.initiateConference(1,otherDn,"{params:{UserData:{command:'1'}}}");

}
// 会议完成
function completeConference() {
    if(!checkSoftPhoneApplet()){
        return false;
    }
    SoftPhone.completeConference(null);

}

/**
 * 置座席状态为NotReady
 * @param mediaType
 * @param reasonCode
 * @param workMode
 * @return
 */
function notReady(reasonCode) {
    console.info("reasonCode:"+reasonCode);
   return notReady(reasonCode, 0);
}

function notReady(reasonCode, workModel) {
    console.info("notReady----1:"+reasonCode);
    if(reasonCode == null) reasonCode = "1203";
    console.info("notReady----2:"+reasonCode);
    if(!checkSoftPhoneApplet()){
        return false;
    }
    var result = SoftPhone.notReady(mediaType, reasonCode, workModel);
    if (result == 1) {
        if(phone)phone.changeStatus(2);
        return true;
    }
    return false;
}


/**
 * 转接会话到队列
 * @param interactionId
 * @param queue
 * @param transferType
 * @param transferAgent
 * @return
 */
function transferQueue(interactionId, queue, transferType, transferAgent) {
	if(!checkSoftPhoneApplet()){
		return false;
	}
	var result = SoftPhone.transferQueue(interactionId, queue, transferType, transferPerson);
	if (result == 1) {
		return true;
	}
	return false;
}

/**
 * 添加随路数据
 * @param interactionId
 * @param userData
 * @param mediaType
 * @return
 */
function addUserData(interactionId, userData, mediaType) {
	if(!checkSoftPhoneApplet()){
		return false;
	}
	var result = SoftPhone.addUserData(interactionId, userData, mediaType);
	if (result == 1) {
		return true;
	}
	return false;
}


/**
 *
 * @param phoneNo
 * @param attachedData (json)
 * @return {boolean}
 */
function dial(phoneNo,attachedData){
    if(!checkSoftPhoneApplet()){
        return false;
    }
    isDial = 1;
    var result = SoftPhone.dial(phoneNo,attachedData);
    if (result == 1) {
        return true;
    }
    return false;
}


/**
 * 添加随路数据
 * @param interactionId
 * @param userData
 * @param mediaType
 * @return
 */
function sendDtmf(dtmf) {
    if(!checkSoftPhoneApplet()){
        return false;
    }
    SoftPhone.sendDtmf(1, dtmf);

}

/**
 * 注册
 * @param tserverHost
 * @param tserverPort
 * @param tserverBackHost
 * @param tserverBackPort
 * @param mediaType
 * @param agentId
 * @param passwora
 * @param dn
 */
function registerSoftPhone(tserverHost,tserverPort,tserverBackHost,tserverBackPort,mediaType,agentId,passwora,dn){

    if(!checkSoftPhoneApplet()){
        return false;
    }
    SoftPhone.register(tserverHost,tserverPort,tserverBackHost,tserverBackPort,mediaType,agentId,passwora,dn);
}

function getDnStatus(dn) {

    if(!checkSoftPhoneApplet()){
        return false;
    }
    var result = SoftPhone.getDnStatus(dn);
    console.info("getDnStatus:"+result);
    return result == 0;

}