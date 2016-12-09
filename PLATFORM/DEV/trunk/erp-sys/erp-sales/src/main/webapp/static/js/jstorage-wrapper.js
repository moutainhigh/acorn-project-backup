//如果超出上限，删除
function checkAndEliminationIfNeed() {
	var keySet = $.jStorage.index();
	if(keySet.length >= storageMaxSize) {
		$.jStorage.deleteKey(keySet[0]);
	}
}

//删除原有，插入到队尾，需要在get方法之后调用
function updateKeyIfNewAccessed(key) {
	var value = $.jStorage.get(key);
	$.jStorage.deleteKey(key);
	$.jStorage.set(key, value);
}

function storageFlush() {
	$.jStorage.flush();
}