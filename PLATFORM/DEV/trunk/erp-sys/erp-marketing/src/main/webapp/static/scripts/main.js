YAHOO.namespace("pingmob");

function getRequestValueFromURL(url, requestName) {
    var requestValue = "";
    var paramCount = "";
    var name = new Array();
    var value = new Array();
    var totalParam = url;
    if (url.indexOf("?") != -1) {
        totalParam = url.substring(url.indexOf("?") + 1);
    }
    paramCount = totalParam.split("&").length;
    for (var i = 0; i < paramCount; i++) {
        name[name.length] = totalParam.split("&")[i].split("=")[0];
        value[value.length] = totalParam.split("&")[i].split("=")[1];
    }
    for (var j = 0; j < name.length; j++) {
        if (name[j] == requestName) {
            requestValue = value[j];
            break;
        }
    }
    return requestValue;
}

var getTableDataSourceArray = function(datatable){
    var rs = datatable.getRecordSet(),
        len = rs.getLength(),
        data = [];
    for (var index=0; index < len; index++) {
        data.push(rs.getRecord(index).getData());
    }
    return YAHOO.lang.JSON.stringify(data);
};


String.prototype.trim = function () {
    return this.replace(/^[\s\,]*/, "").replace(/[\s\,]*$/, ""); //for remove the space and comma at the begining/end of the tag.
};

function isInteger(number) {
    for (var i = 0; i < number.length; i++) {
        if (isNaN(number.charAt(i))) {
            return false;
        }
    }
    return true;
}

//passowrd validator;
function validatorPassword(password) {
    var filter = /^s*[A-Za-z0-9]{4,20}s*$/;
    return filter.test(password);
}

//email validator;
function validatorEmail(email) {
    var filter = /^([-_A-Za-z0-9\.]+)@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;
    return filter.test(email);
}

//username validator;
function validatorUsername(username) {
    var filter = /^([-_A-Za-z0-9\.]+)$/;
    return filter.test(username);
}

function showError(error_id) {
    document.getElementById(error_id).innerHTML = "&nbsp;<img src=\"/img/ico_cross_org.gif\" width=14 height=14><br />";
}
function showCheck(error_id) {
    document.getElementById(error_id).innerHTML = "&nbsp;<img src=\"/img/ico_check_blu.gif\" width=14 height=14><br /><span style=\"color:blue\">";
}


var handleFailure = function(o) {
    if (o.responseText !== undefined) {
        alert("Client RPC Request Error, please retry.");
    }
};

//focus on the form element.
function focusOn(elemName) {
    try {
        var elem = document.getElementById(elemName);
        if (elem) {
            elem.focus();
            elem.select();
        }
    } catch (e) {
    }
}

//make the option with specified value to be selected.
function selectByValue(selectoId, value) {
    var selectObj = document.getElementById(selectoId);
    for (var i = 0; i < selectObj.length; i++) {
        if (selectObj[i].value == value) {
            selectObj[i].selected = true;
            break;
        }
    }
}

//clear the options of selector.
function clearSelectOptions(selectoId) {
    var e = document.getElementById(selectoId);
    if (e == null) {
        return true;
    }
    var length = e.options.length;
    for (var i = 0; i < length; i++) {
        e.options[0] = null;
    }
    return true;
}

(function() {
    //Setup the click listeners on the folder list
    YAHOO.util.Event.on('folder_list', 'click', function(ev) {
        var tar = YAHOO.util.Event.getTarget(ev);
        if (tar.tagName.toLowerCase() != 'a') {
            tar = null;
        }
        //Make sure we are a link in the list's
        if (tar && YAHOO.util.Selector.test(tar, '#folder_list ul li a')) {
            //if the href is a '#' then select the proper tab and change it's label
            if (tar && tar.getAttribute('href', 2) == '#') {
                YAHOO.util.Dom.removeClass(YAHOO.util.Selector.query('#folder_list li'), 'selected');
                var feedName = tar.parentNode.className;
                YAHOO.util.Dom.addClass(tar.parentNode, 'selected');
                YAHOO.util.Event.stopEvent(ev);
                var title = tar.innerHTML;
            }
        }
    });
})();
