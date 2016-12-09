/**
 * Applet加载完成
**/
function appletReady(){
	onInitialized();
}

/**
 * 错误通知
 * @param result 错误信息
 */
function handleError(result) {
	onError(result);
}

/**
 * 坐席状态修改稿
 * @param result 当前坐席状态
 */
function handleAgentStatusChanged(result) {
	result = toJSON(result);
	onAgentStatusChanged(result);
}

/**
 * 与CTI连接的状态
 * @param result 当前状态
 */
function handleConnectionStatusChanged(result){
	
	result = toJSON(result);
	onConnectionStatusChanged(result);
}

/**
 * 新的会话加入(电话响铃等)
 * @param result 当前会话信息
 */
function handleInteractionAdded(result) {
	try {
		result = toJSON(result);
        callbackIsRelease = 1;
        if(result.thisDN != result.otherDN)	onInteractionAdded(result);
	} catch (e) {
		alert(e);
	}
}

/**
 * 会话状态改变(电话响铃等)
 * @param result 当前会话信息
 */
function handleInteractionUpdated(result) {
	try {
		result = toJSON(result);
        if(result.thisDN == result.otherDN){
            dailSelf =1;
            console.info("dailSelf..."+1);
        }
		onInteractionUpdated(result);
	} catch (e) {
		alert(e);
	}
}

/**
 * 会话离开(电话响铃等)
 * @param result 当前会话信息
 */
function handleInteractionRemoved(result) {
	try {
        isDial = 0;
		result = toJSON(result);
		onInteractionRemoved(result);
	} catch (e) {
		alert(e);
	}
}

function toJSON(result) {
	return eval('(' + result + ')');
}


/**
 * 坐席状态修改稿
 * @param result 当前坐席状态
 */
function handleUserEvent(result) {
    result = toJSON(result);
    onUserEvent(result);
}

/**
 * 坐席状态修改稿
 * @param result 当前坐席状态
 */
function handleHookStatusChanged(result) {
    result = toJSON(result);
    hookStatusChanged(result);
}