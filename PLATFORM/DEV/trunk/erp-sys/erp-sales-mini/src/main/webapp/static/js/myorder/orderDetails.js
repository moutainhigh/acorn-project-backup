var _id_data = [
	{"cardtypeid": "001", "cardname": "身份证"}, 
	{"cardtypeid": "002", "cardname": "护照"},
	{"cardtypeid": "011", "cardname": "军官证"}
];

var _paytype_data = [
	{"id":1,"text":"货到付款"},
	{"id":2,"text":"信用卡"},
	{"id":3,"text":"支票"},
	{"id":4,"text":"网上支付"},
	{"id":5,"text":"支付宝"},
	{"id":6,"text":"网上团购"},
	{"id":7,"text":"茶马古道"},
	{"id":8,"text":"京东SOP"},
	{"id":9,"text":"经销商送货"},
	{"id":10,"text":"卓越自营"},
	{"id":11,"text":"上门自提"},
	{"id":12,"text":"邮局汇款"},
	{"id":13,"text":"银行转账"}
];

$(document).ready(function(){
	initReceiptTable();
	if (payType == '2') initCreditTable();
	init();
	binding();
});

function init() {
	$("#notecount").text($("#note").val().length);
	calJifen();
	calTotalPrice();
	if (isDetChanged == "true") {
		var amor = '';
		if ($("#id_credit_table").length > 0) {
	    	var creditRows = $("#id_credit_table").datagrid("getRows");
	    	if (creditRows.length > 0) amor = creditRows[0].amortisation;
	    }
		calMailPrice(addressId, cartId, amor);
	}
	
	$("#select_paytype").combobox({
		valueField: 'id',  
        textField: 'text',
		data: _paytype_data 
	});
	$("#select_paytype").combobox("setValue", payType);
}

/**
 * 增加监听
 */
function binding() {
	$("#note").bind("keyup", function() {
		$("#notecount").text($("#note").val().length);
	});
	$("#mailprice").bind("keyup", function() {
		calTotalPrice();
	});
}

/**
 * 时时计算订单总价
 */
function calTotalPrice() {
	var tp = isNaN(parseFloat($('#producttotalprice').html())) ? 0 : parseFloat($('#producttotalprice').html());
	var mp = isNaN(parseFloat($('#mailprice').val())) ? 0 : parseFloat($('#mailprice').val());
	$('#totalprice').html((tp + mp).toFixed(2));
	$('#getjifen').html(Math.floor(parseFloat($('#producttotalprice').html())) / 100);
}

/**
 * 计算使用积分
 */
function calJifen() {
	var usedJifen = 0;
	$("#id_product_details_table tr td:nth-child(6)").each(function(){
		usedJifen += isNaN(parseFloat($(this).html())) ? 0 : parseFloat($(this).html());
	});
	$("#jifen").html(usedJifen);
}

function calMailPrice(addressId, cartId, amor) {
	var data = {};
	data.addressId = addressId;
	if (cartId != '') data.cartId = cartId;
	if (amor != '') data.amor = amor;
	$.ajax({
		type: 'POST',
		async: false,
		url: prepath + '/mailprice/' + orderId,
		data: data,
		dataType: 'text',
		success: function(res) {
			$("#mailprice").val(res);
			calTotalPrice();
		}
	});
}

/**
 * 修改按钮可用
 */
function enableButton() {
	if (isOrderInBmp()) return;
	$("#id_modify_credit_a").css("display", "inline-block");
	$("#id_modify_product_a").css("display", "inline-block");
	$("#id_modify_receipt_a").css("display", "inline-block");
	$("#id_modify_invoice_a").css("display", "inline-block");
	$("#id_modify_note_a").css("display", "inline-block");
	$("#id_modify_settle_a").css("display", "inline-block");
	$("#id_modify_shipment_a").css("display", "inline-block");
	$("#id_main_button_div").css("display", "inline");
    window.parent.SetCwinHeight(frameElement);
}

function disableButton() {
	$("#id_modify_credit_a").css("display", "none");
	$("#id_modify_product_a").css("display", "none");
	$("#id_modify_receipt_a").css("display", "none");
	$("#id_modify_invoice_a").css("display", "none");
	$("#id_modify_note_a").css("display", "none");
	$("#id_modify_settle_a").css("display", "none");
	$("#id_modify_shipment_a").css("display", "none");
	$("#id_main_button_div").css("display", "none");
    window.parent.SetCwinHeight(frameElement);
}

function showAuditState(value) {
	switch (value) {
		case 0 :
			return '<span class="exaa">待审核</span>';
		case 1 :
			return '<span class="exaa">审核中</span>';
		case 3 :
			return '<span class="exa">未通过</span>'
		default :
			return '';
	}
}

function initReceiptTable() {
	if (payType != '11') { // 非上门自提
		$("#id_address_show_table").datagrid({
			singleSelect: true,
			fitColumns: true,
            height: 59,
            scrollbarSize: -1,
			url: prepath + '/receipt?contactId=' + getContactId + '&addressId=' + addressId,
			columns: [[
				{field: 'contactName', title: '姓名',align:'center', width: 149, resizable: false},
				{field: 'prefixAddress', title: '收货地址', width: 221, resizable: false, formatter: formatAddressDesc},
				{field: 'addressDesc', title: '详细地址', width: 62, resizable: false, formatter: marquee},
				{field: 'zipcode', title: '邮编',align:'center', width: 95, resizable: false},
				{field: 'phone', title: '联系电话', width: 229, resizable: false, formatter: formatPhone}
			]]
		});
	} else { // 上门自提
		$("#id_address_show_table").datagrid({
			singleSelect: true,
			fitColumns: true,
			height: 59,
            scrollbarSize: -1,
			url: prepath + '/receipt?contactId=' + orderContactId + '&addressId=' + addressId,
			columns: [[
				{field: 'contactName', title: '姓名', width: 120, resizable: false},
				{field: 'prefixAddress', title: '收货地址', width: 120, resizable: false, formatter: formatAddressDesc},
				{field: 'addressDesc', title: '详细地址', width: 341, resizable: false},
				{field: 'zipcode', title: '邮编', width: 75, resizable: false},
				{field: 'phone', title: '联系电话', width: 100, resizable: false, formatter: formatPhone}
			]]
		});
	}
}

/**
 * 初始化地址列表
 * @param contactId
 */
function initReceiptListTable(contactId) {
	$("#id_address_list_table").datagrid({
        height: 100,
        url: prepath + '/receipts?contactId=' + contactId,
        singleSelect: true,
        fitColumns: true,
        scrollbarSize: -1,
        selectOnCheck: true,
        columns: [[
        	{field: 'auditState', title: '审批状态', width: 49, resizable: false, formatter: showAuditState},
            {field: 'contactName', title: '姓名',align:'center', width: 100, resizable: false},
            {field: 'prefixAddress',title: '收货地址', width: 221, resizable: false, formatter: formatAddressDesc},
            {field: 'addressDesc',title: '详细地址', width: 62, resizable: false, formatter: marquee},
            {field: 'zipcode',title: '邮编', width: 95, resizable: false},
            {field: 'phone',title: '联系电话', width: 188, resizable: false, formatter: formatPhone},
            {field: 'modify',title: '操作', width: 40, resizable: false, align:'center',
            	formatter: function(value, row, index){
            		return '<a class="modify" href="javascript:void(0)" onclick="editRecipt(\''+index+'\')"></a>';
            	}
            }
        ]],
        onClickCell: function(rowIndex, field, value) {
        	if (field != 'modify') {
        		var rowData = $('#id_address_list_table').datagrid('getRows')[rowIndex];
        		if (rowData.auditState == 1 || rowData.auditState == 3) {
        			showDialog("提示", "不能选择正在审核中或者未通过审核的地址");
        			return;
        		}
        		var isWrong = false;
        		$.ajax({
					url: prepath + '/checkwrong/' + rowData.addressId,
					async: false,
					dataType: 'text',
					success: function(data) {
						isWrong = data == "true"; 
					}
				});
				
				if (isWrong) {
					showDialog("提示", "所选的地址有误，请重新选择或者修改该地址");
					return;
				}
        		
        		doSelectReceipt(rowData);
        	}
        },
        onLoadSuccess: function(data) {
        	var contact = getContact(contactId);
			$("#receipt_contactName").html(contact.name);
			$("#receipt_contactId").html(contact.contactid);
        }
    });
}

function initPickUpSelfReceiptListTable(contactId) {
	$("#id_address_list_table").datagrid({
        height: 80,
        url: prepath + '/receipts?contactId=' + contactId + '&payType=' + payType,
        singleSelect: true,
        showHeader: false,
        fitColumns: true,
        scrollbarSize: -1,
        selectOnCheck: true,
        columns: [[
            {field: 'contactName', title: '姓名', width: 120, resizable: false},
            {field: 'prefixAddress', width: 120, resizable: false, formatter: formatAddressDesc},
            {field: 'addressDesc', width: 341, resizable: false},
            {field: 'zipcode', width: 75, resizable: false},
            {field: 'phone', width: 100, resizable: false, formatter: formatPhone}
        ]],
        onClickCell: function(rowIndex, field, value){
        	if (field != 'modify') {
        		var rowData = $('#id_address_list_table').datagrid('getRows')[rowIndex];
        		doSelectReceipt(rowData);
        	}
        },
        onLoadSuccess: function(data) {
        	var contact = getContact(contactId);
			$("#receipt_contactName").html(contact.name);
			$("#receipt_contactId").html(contact.contactid);
        }
    });
}

/**
 * 格式化详细地址
 * @param value
 * @param row
 * @returns
 */
function formatAddressDesc(value, row) {
	var str = "";
	if(row.provinceId=="01"||row.provinceId=="02"||row.provinceId=="14"||row.provinceId=="29") {
		str = "";
    }else if(row.provinceId=="25"){
    	str += row.provinceName + "壮族自治区";
    }else if(row.provinceId=="09"){
    	str += row.provinceName + "回族自治区";
    }else if(row.provinceId=="30"||row.provinceId=="05"){
    	str += row.provinceName + "自治区";
    }else if(row.provinceId=="13"){
    	str += row.provinceName + "维吾尔自治区";
    }else{
    	str += row.provinceName + "省";
    }
	str+= row.cityName;
	
	if (row.countyName!="其他区"&&row.countyName!="其他") {
		str+= row.countyName;
	}
	if (row.areaName!="其他"&&row.areaName!="城区") {
		str+= row.areaName;
	}
	return str;
}

/**
 * 详细地址跑马效果
 * @param value
 */
function marquee(value) {
	return '<div onselectstart="return false;"><marquee direction="left" scrollamount="3">'+value+'</marquee></div>';
}

/**
 * 格式化电话
 * @param value
 * @param row
 * @returns
 */
function formatPhone(value, row) {
	var size = row.phones.length;
	var str = mergePhones(row.phones, false);
	if (row.addedPhones != undefined) {
		str += "/" + mergePhones(row.addedPhones, false);
		size += row.addedPhones.length;
	}
	if (size <= 3) {
		return str;
	} else {
		return marquee(str);
	}
}

/**
 * 合并单个电话号码
 * @param phone
 * @returns
 */
function mergePhone(phone) {
	if (phone.phonetypid == "4") { // 手机
		return phone.phn2.substr(0,7) + "****";
	} else {
		var phoneStr = "";
		if (phone.phn1 != "0" && phone.phn1 != null && phone.phn1 != "") {
			phoneStr += phone.phn1 + "-";
		}
		if (phone.phn2 != null && phone.phn2 != "") {
			phoneStr += phone.phn2.substr(0,4) + "****";
		}
		return phoneStr;
	}
}

/**
 * 合并电话号码
 * @param phones
 * @param hide
 * @returns
 */
function mergePhones(phones, hide) {
	var phoneStr = "";
	$.each(phones, function(pos, phone) {
		if (phone.phonetypid == "4") { // 手机
			phoneStr += !hide ? phone.phn2 : phone.phn2.substr(0,7) + "****";
		} else {
			if (phone.phn1 != "0" && phone.phn1 != null && phone.phn1 != "") {
				phoneStr += phone.phn1 + "-";
			}
			if (phone.phn2 != null && phone.phn2 != "") {
				phoneStr += !hide ? phone.phn2 : phone.phn2.substr(0,4) + "****";
			}
			if (phone.phn3 != null && phone.phn3 != "" && !hide) {
				phoneStr += "-" + phone.phn3;
			}
		}
		phoneStr += "/";
	});
	return phoneStr.length > 0 ? phoneStr.substr(0, phoneStr.length - 1) : phoneStr;
}

/**
 * 开启a标签
 * @param _id
 */
function enable(_id) {
	$(_id).attr('disabled',false);
}

/**
 * 关闭a标签
 * @param _id
 */
function disable(_id) {
    $(_id).attr("disabled", true);
}

function toggleReceipts(contactId) {
	if ($("#id_address_list_div").parent().css("display") == "none") {
		showReceipts(contactId);
	} else {
		cancelReceipt();
	}
}

/**
 * 显示收货信息
 * @param contactId
 */
function showReceipts(contactId){
	clearReceiptInput();
	if (payType == '11') {
		initPickUpSelfReceiptListTable(orderContactId);
	} else {
		initReceiptListTable(contactId);
	}
	$("#id_receipt_button_div").show();
    $("#id_address_list_div").panel("open");
    window.parent.SetCwinHeight(frameElement);
}

/**
 * 取消收货信息
 */
function cancelReceipt() {
	clearReceiptInput();
    $("#id_receipt_button_div").hide();
    $("#id_address_list_div").panel("close");
    window.parent.SetCwinHeight(frameElement);
}

/**
 * 修改收货信息
 * @param index
 */
function editRecipt(index){
	clearReceiptInput();
    
    var rows = $('#id_address_list_table').datagrid('getRows');
    var row = rows[index];
    $("#id_address_table").show();
    
    initAddress(row.provinceId, row.cityId, row.countyId, row.areaId);
    
    $('#input_addressId').val(row.addressId);
    $("#input_addressDesc").val(maskAddressDesc(row.addressDesc));
    $("#input_addressDesc").attr('plaintext', row.addressDesc);
    $("#input_zip").val(row.zipcode);
    $("#input_addressDesc").validatebox("validate");
    $("#input_zip").validatebox("validate");
    
    loadPhones(row);
    
    $('#input_select_index').val(index);
    window.parent.SetCwinHeight(frameElement);
}

function maskAddressDesc(desc) {
	if (desc.length <= 12) {
		return desc;
	}
	var str = desc.substr(0,12);
	for (var i = 0; i < desc.length - 12; i++) {
		str += "*";
	}
	return str;
}

/**
 * 清除收货信息输入控件
 */
function clearReceiptInput() {
	$("#id_address_table").hide();
	$("#id_address_table").find("input").each(function(index, item) {
		$(this).attr("value", "");
	});
	$("#input_select_index").attr("value", "");
	$("#ul_phonelist_exist").html("");
	$("#ul_phonelist_added").html("");
	_existBackupPhoneCount = 0;
	
	$("#input_addressDesc").attr('plaintext', '');
}

/**
 * 增加收货信息
 */
function addReceipt(){
	clearReceiptInput();
    
    $("#id_address_table").show();
	
    initAllAddress();
    
    $("#input_addressDesc").validatebox("validate");
    $("#input_zip").validatebox("validate");
    
    var rows = $('#id_address_list_table').datagrid('getRows');
    loadPhones(rows[0]);
    window.parent.SetCwinHeight(frameElement);
}

/**
 * 取消新增收货信息
 */
function cancelAddReceipt() {
	clearReceiptInput();
    $("#id_address_table").hide();
    $("#ul_phonelist_added").html("");
    window.parent.SetCwinHeight(frameElement);
}

/**
 * 加载电话
 * @param row
 */
function loadPhones(row) {
	_existBackupPhoneCount = 0;
	var html = '';
	$.each(row.phones, function(i, val){
		var stateDisabled = val.state == 0 || val.state == 1 || val.state == 3;
	    html += '<li><table><tr><td width="20px"></td>';
	    html += '<td style="width:200pt">';
	    html += 	'<span name="fphone1" '+(val.phonetypid=="4"?'':'style="display:none"')+'>';
	    html +=		'<input type="text" size="20" disabled value="'+(val.phn2.substr(0,7)+"****")+'" />';
	    html += 	'</span>';
	    html += 	'<span name="fphone2" '+(val.phonetypid!="4"?'':'style="display:none"')+'>';
	    html += 	'<input type="text" size="5" disabled value="'+(val.phn1==null||val.phn1=="0"?"":val.phn1)+'" /> - ';
	    html +=		'<input type="text" size="10" disabled value="'+(val.phn2.substr(0,4)+"****")+'" /> - ';
	    html +=		'<input type="text" size="8" disabled value="'+(val.phn3==null?"":val.phn3)+'" />';
	    html += 	'</span>';
	    html += '</td>';
	    html += '<td style="width:50pt">';
	    html += 	'<input type="checkbox" '+(val.phonetypid!="4"?'checked':'')+' disabled>固话</input>';
	    html += '</td>';
	    html += '<td style="width:50pt">';
	    html += 	'<input type="checkbox" phoneid="'+val.phoneid+'" '+(val.prmphn=="Y"?'checked':'')+' '+(stateDisabled?'disabled':'')+' onchange="changePrmphone(this, false)">主电话</input>';
	    html += '</td>';
	    html += '<td style="width:60pt">';
	    html += 	'<input type="checkbox" phoneid="'+val.phoneid+'" '+(val.prmphn=="B"?'checked':'')+' '+(stateDisabled||val.prmphn=="Y"?'disabled':'')+' onchange="changeBckphone(this, false)">备选电话</input>';
	    html += '</td>';
	    html += '<td style="width:50pt">';
	    html += showAuditState(val.state);
	    html += '</td>';
	    html += '</tr></table></li>';
	    if (val.prmphn == "B") _existBackupPhoneCount++;
	});
	$("#ul_phonelist_exist").html(html);
	
	// 如果电话超过3个, 显示滚动条
	var height = $("#ul_phonelist_exist").height();
	if (height > 100) {
		$("#div_phonelist_exist").height(100);
		$("#div_phonelist_exist").css({
			"overflow-y":"auto", 
			"overflow-x":"hidden"
		});
	};
	
	$("#ul_phonelist_added").html(addPhoneStr(0));
}

function addPhoneStr(index) {
	var html = '<li index="'+index+'"><table><tr>';
	html += '<td width="20px">';
//	html += 	'<a onclick="doAddPhone(this)" class="addBtn"></a>';
	html += 	'<a onclick="doRemovePhone(this)" class="removeBtn" style="display:none"></a>';
	html += '</td>';
	html += '<td style="width:200pt"></td>';
	html += '<td style="width:50pt"></td>';
	html += '<td style="width:50pt"></td>';
	html += '<td style="width:60pt"></td>';
	html += '<td style="width:50pt"></td>';
	html += '</tr></table></li>';
	return html;
}

/**
 * 新增电话
 */
function doAddPhone(link){
	var lastLi = $("#ul_phonelist_added").children("li").last();
	
	if (lastLi.length > 0) {
		var prevLi = $(lastLi[0]).prev()[0];
		if (prevLi != undefined && $(prevLi).find("input").eq(0).val() == ""
				&& $(prevLi).find("input").eq(2).val() == "") { // 上个LI节点存在
			return;
		}
	}
	
	// idx只是用来区分radio name
	var idx = $(lastLi[0]).attr("index");
	
	var html = '<span name="fphone1"><input type="text" size="20" /></span>';
	html += '<span name="fphone2" style="display:none"><input type="text" size="5" /> - '
	html += '<input type="text" size="10" /> - <input type="text" size="8" /></span>';
	$(lastLi[0]).find("td").eq(1).html(html);
	
	html = '<input type="checkbox" onchange="changePhone(this)">固话</input>';
	$(lastLi[0]).find("td").eq(2).html(html);
	
	html = '<input type="checkbox" onchange="changePrmphone(this, true)">主电话</input>';
	$(lastLi[0]).find("td").eq(3).html(html);
	
	html = '<input type="checkbox" onchange="changeBckphone(this, true)">备选电话</input>';
	$(lastLi[0]).find("td").eq(4).html(html);
	
	var inpus = $(lastLi[0]).find("input");
	$(lastLi[0]).find("input").eq(0).validatebox({validType: ['validMobile', 'validInputMobileExist']});
	$(lastLi[0]).find("input").eq(1).validatebox({validType: 'validPhoneArea'});
	$(lastLi[0]).find("input").eq(2).validatebox({validType: 'validPhoneNum'});
	$(lastLi[0]).find("input").eq(3).validatebox({validType: 'validPhoneExt'});
	
	$(lastLi[0]).find("a").eq(0).show();
	
	$(lastLi[0]).after(addPhoneStr(++idx));
}

function doRemovePhone(link) {
	$($(link).parents("li")[0]).remove();
}

/**
 * 改变电话类型
 * @param input
 */
function changePhone(input) {
	if (input.checked) {
		$(input).parents("li").find("span").eq(0).css("display","none");
		$(input).parents("li").find("span").eq(1).css("display","inline-block");
	} else {
		$(input).parents("li").find("span").eq(0).css("display","inline-block");
		$(input).parents("li").find("span").eq(1).css("display","none");
	}
}

function changePrmphone(input, isNew) {
	if (input.checked) { // 没选中需要判断
		$.each($("#ul_phonelist_exist").find("li"), function() {
			$(this).find("input").eq(5).attr("checked", false);
			$(this).find("input").eq(6).attr("disabled", false);
		});
		$.each($("#ul_phonelist_added").find("li"), function() {
			$(this).find("input").eq(5).attr("checked", false);
			$(this).find("input").eq(6).attr("disabled", false);
		});
		$(input).attr("checked", true);
		$(input).parents("li").find("input").eq(6).attr("checked", false);
		$(input).parents("li").find("input").eq(6).attr("disabled", true);
	} else {
		$(input).attr("checked", true);
		return;
	}
	
	var rows = $('#id_address_list_table').datagrid('getRows');
	if (!isNew) {
		$.ajax({
			url: prepath + '/prmphn',
			data: {
				"contactId" : rows[0].contactId,
				"phoneId" : $(input).attr("phoneid")
			}
		});
	}
}

_existBackupPhoneCount = 0;
function changeBckphone(input, isNew) {
	var isUnset;
	var curPhoneId = $(input).attr("phoneid");
	if (input.checked) { // 选中备选电话需要判断是否已经有2个备选电话了
		if (_existBackupPhoneCount > 1) { // 如果已有2个备选电话，必须先去掉id小的那个
			var existCount = 0;
			var existMinPhoneId = '';
			var addedCount = isNew ? -1 : 0;
			// 计算已存在的电话列表中备选电话数量 以及最小phoneid
			$.each($("#ul_phonelist_exist").find("li"), function() {
				var phoneId = $(this).find("input").eq(6).attr("phoneid");
				if (phoneId != curPhoneId && $(this).find("input").eq(6).attr("checked") == "checked") {
					existCount++;
					if (existMinPhoneId == '' || phoneId < existMinPhoneId) {
						existMinPhoneId = phoneId;
					}
					if (existCount == 2) return false;
				}
			});
			// 计算新增电话列表中的备选电话数量
			$.each($("#ul_phonelist_added").find("li"), function() {
				if ($(this).find("input").eq(6).attr("checked") == "checked") {
					addedCount++;
					if (addedCount == 2) return false;
				}
			});
			
			if (addedCount > 1) { // 新增列表中备选电话有2个, 去掉第一个出现的
				$.each($("#ul_phonelist_added").find("li"), function() {
					if ($(this).find("input").eq(6).attr("checked") == "checked") {
						$(this).find("input").eq(6).attr("checked", false);
						return false;
					}
				});
			} else { // 已存在列表中备选电话有1个或者2个，都去掉最小phoneid那个
				$.each($("#ul_phonelist_exist").find("li"), function() {
					if ($(this).find("input").eq(6).attr("phoneid") == existMinPhoneId) {
						$(this).find("input").eq(6).attr("checked", false);
						return false;
					}
				});
			}
		} else {
			_existBackupPhoneCount++;
		}
		
		isUnset = false;
	} else { // 取消备选不需要判断
		isUnset = true;
		_existBackupPhoneCount--;
	}
	
	var rows = $('#id_address_list_table').datagrid('getRows');
	if (!isNew) {
		$.ajax({
			url: prepath + '/bckphn',
			data: {
				"isUnset" : isUnset,
				"contactId" : rows[0].contactId,
				"phoneId" : $(input).attr("phoneid")
			}
		});
	}
}

function saveReceipt() {
	var index = $("#input_select_index").val();
	var contactId = index == "" ? getContactId : $("#id_address_list_table").datagrid("getRows")[index].contactId;
	if (!validateAddress()) return;
	if (!validateAddedPhones(contactId)) return;
	
	var checkMobiles = [];
	var addedPhones = $("#ul_phonelist_added").find("li");
    var length = addedPhones.length;
    if (length > 1) {
    	$.each(addedPhones, function (pos, li) {
        	if (!$(li).find("input").eq(4).is(":checked") && $(li).find("input").eq(0).val() != "") {
    	    	var checkMobile = {};
    	    	checkMobile.phonetypid = "4";
    	    	checkMobile.phn2 = $(li).find("input").eq(0).val();
    	    	checkMobiles.push(checkMobile);
        	}
        	// 新增电话中存在一个无效li,所以数组最后个元素的下标为length-2
        	if (pos == length - 2) return false; 
        });
        
        $.ajax({
			type: 'POST',
			async: false,
			url: prepath + '/check/repeatMobile?contactId=' + contactId,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(checkMobiles),
			dataType: 'json',
			success: function(data) {
				switch (data.result) {
					case 0: doSaveReceipt(); break;
					case 1: showSingleRepeatMobile(data.contactId); break;
					default: showMultiRepeatMobile(data.rows); break;
				}
			}
		});
    } else {
    	doSaveReceipt();
    }
}

function showSingleRepeatMobile(contactId) {
	$("#repeat_mobile_contact_id").html(contactId);
	$("#id_single_repeat_mobile_window").window("open");
}

function showMultiRepeatMobile(rows) {
	$("#id_multi_repeat_mobile_table").datagrid({
        title:'',
        iconCls:'icon-edit',
        height: 200,
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        remoteSort:false,
        singleSelect:false,
        pagination:true,
        columns:[[
            {field: 'customerId', title: '客户编号', align: 'center', width: 100},
            {field: 'name', title: '客户名称', align: 'center', width: 80},
            {field: 'level', title: '会员等级', align: 'center', width: 80},
            {field: 'address', title: '客户地址',width: 100, formatter: marquee},
            {field: 'crusr', title: '创建人', align: 'center', width: 80},
            {field: 'crdtStr', title: '创建时间', width:140},
            {field: 'addressid', title: '地址编号', width:100, hidden: true}
        ]],
        loadFilter: function(data) {
        	if (typeof data.length == 'number' && typeof data.splice == 'function') {	// is array
				data = {total: data.length, rows: data}
			}
			var dg = $(this);
			var opts = dg.datagrid('options');
			var pager = dg.datagrid('getPager');
			pager.pagination({
				onSelectPage: function(pageNum, pageSize) {
					opts.pageNumber = pageNum;
					opts.pageSize = pageSize;
					pager.pagination('refresh', {pageNumber: pageNum, pageSize: pageSize});
					dg.datagrid('loadData', data);
				}
			});
			if (!data.originalRows){
				data.originalRows = (data.rows);
			}
			var start = (opts.pageNumber-1) * parseInt(opts.pageSize);
			var end = start + parseInt(opts.pageSize);
			data.rows = (data.originalRows.slice(start, end));
			return data;
        },
        onDblClickRow: function(index, row){
            if(row){
                window.parent.addTab("p_" + row.customerId, "客户详情", false, '/contact/1/' + row.customerId);
            }
        }
    });
    $("#id_multi_repeat_mobile_table").datagrid("loadData", rows);
	$("#id_multi_repeat_mobile_window").window("open");
}

/**
 * 保存收货信息
 */
function doSaveReceipt() { 
	var row = {};
	row.phones = [];
	
	var rows = $("#id_address_list_table").datagrid("getRows");
	var contact = getContact($("#receipt_contactId").html());
	row.contactName = contact.name;
	row.contactId = contact.contactid;
	row.phones = rows[0].phones;

    var addedPhones = $("#ul_phonelist_added").find("li");
    var length = addedPhones.length;
    if (length > 1) {
    	$.each(addedPhones, function (pos, li) {
        	if (!$(li).find("input").eq(4).is(":checked") && $(li).find("input").eq(0).val() != "") {
        		if (row.addedPhones == undefined) row.addedPhones = [];
        		row.addedPhones[pos] = {};
    	    	row.addedPhones[pos].phonetypid = "4";
    	    	row.addedPhones[pos].phn2 = $(li).find("input").eq(0).val();
    	    	row.addedPhones[pos].prmphn = $(li).find("input").eq(5).is(":checked") ? "Y" : ($(li).find("input").eq(6).is(":checked") ? "B" : "N");
        	} else if ($(li).find("input").eq(4).is(":checked") && $(li).find("input").eq(2).val() != "") {
        		if (row.addedPhones == undefined) row.addedPhones = [];
        		row.addedPhones[pos] = {};
    	    	row.addedPhones[pos].phonetypid = "1";
    	    	row.addedPhones[pos].phn1 = $(li).find("input").eq(1).val();
    	    	row.addedPhones[pos].phn2 = $(li).find("input").eq(2).val();
    	    	row.addedPhones[pos].phn3 = $(li).find("input").eq(3).val();
    	    	row.addedPhones[pos].prmphn = $(li).find("input").eq(5).is(":checked") ? "Y" : ($(li).find("input").eq(6).is(":checked") ? "B" : "N");
        	}
        	// 新增电话中存在一个无效li,所以数组最后个元素的下标为length-2
        	if (pos == length - 2) return false; 
        });
    }
    	
    row.provinceId = $("#input_province").combobox("getValue");
    row.provinceName = $("#input_province").combobox("getText");
    row.cityId = $("#input_city").combobox("getValue");
    row.cityName = $("#input_city").combobox("getText");
    row.countyId = $("#input_county").combobox("getValue");
    row.countyName = $("#input_county").combobox("getText");
    row.areaId = $("#input_area").combobox("getValue");
    row.areaName = $("#input_area").combobox("getText");
    row.zipcode = $("#input_zip").val();
    
    var addressDesc = $("#input_addressDesc").val();
    row.addressDesc = hasMask(addressDesc) ? $("#input_addressDesc").attr('plaintext') : addressDesc;
    
    var data = {};
	data.contactId = row.contactId;
	data.address = {};
	data.address.addressDesc = row.addressDesc;
	data.address.provinceId = row.provinceId;
	data.address.provinceName = row.provinceName;
	data.address.cityId = row.cityId;
	data.address.cityName = row.cityName;
	data.address.countyId = row.countyId;
	data.address.countyName = row.countyName;
	data.address.areaId = row.areaId;
	data.address.areaName = row.areaName;
	data.address.zipcode = row.zipcode;
	
	var rowIndex = $("#input_select_index").val();
	if (rowIndex == '') { // 新增地址
		row.addressId = remoteSaveAddress(data);
		if (row.addressId == undefined) {
			showDialog("提示", "无法保存错误的地址，请检查正确后重新保存");
			return;
		}
	} else if (isAddressChange(rowIndex, data)) { // 地址发生变化
		row.addressId = remoteSaveAddress(data);
	} else {
		row.addressId = $("#id_address_list_table").datagrid("getRows")[rowIndex].addressId;
	}
    
    doSelectReceipt(row);
    clearReceiptInput();
}

function remoteSaveAddress(data) {
	var addressId;
	$.ajax({
		type: 'POST',
		async: false,
		url: prepath + '/address/add',
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(data),
		dataType: 'text',
		success: function(res) {
			if (res) addressId = res;
		}
	});
	return addressId;
}

function isAddressChange(index, data) {
	var row = $("#id_address_list_table").datagrid("getRows")[index];
	return data.address.provinceId != row.provinceId
			|| data.address.cityId != row.cityId
			|| data.address.countyId != row.countyId
			|| data.address.areaId != row.areaId
			|| data.address.zipcode != row.zipcode
			|| data.address.addressDesc != row.addressDesc; 
}

/**
 * 选择收货信息
 */
function doSelectReceipt(rowData) {
	if (contactNameInBPM(rowData.contactId)) {
		showDialog("提示", "修改的联系姓名正在审批中");
	}
	var rows = [rowData];
	$("#id_address_show_table").datagrid("loadData", rows);
	
	var cid = isDetChanged == "true" ? cartId : '';
	var amor = '';
	if ($("#id_credit_table").length > 0) {
    	var creditRows = $("#id_credit_table").datagrid("getRows");
    	if (creditRows.length > 0) amor = creditRows[0].amortisation;
    }
	calMailPrice(rowData.addressId, cid, amor);
	
	$.ajax({
		type: 'GET',
		url: prepath + '/address/' + rowData.addressId + '/delivey/' + orderId,
		contentType: "application/text; charset=utf-8",
		dataType: 'text',
		success: function(data) {
			if (data) {
				$('#id_delivey_days').html(data + '天');
			}
		}
	});
	
	cancelReceipt();
}

/**
 * 验证收货信息
 * @returns {Boolean}
 */
function validateAddress() {
	return $("#input_province").combobox("isValid")
			&& $("#input_city").combobox("isValid")
			&& $("#input_county").combobox("isValid")
			&& $("#input_area").combobox("isValid")
			&& $("#input_addressDesc").validatebox("isValid")
			&& $("#input_zip").validatebox("isValid");
}

function validateAddedPhones(contactId) {
	var phoneValidation = true;
	var addedPhones = $("#ul_phonelist_added").find("li");
    var length = addedPhones.length;
    if (length > 1) {
    	$.each(addedPhones, function (pos, li) {
    		if (!$(li).find("input").eq(4).is(":checked") && $(li).find("input").eq(0).val() != "") {
    			if (!$(li).find("input").eq(0).validatebox("isValid")) {
        			phoneValidation = false;
        			return false;
        		}
        		if (isRemotePhoneExists(contactId, '4', '', $(li).find("input").eq(0).val(), '')) {
        			phoneValidation = false;
        			setMobileExistInvalid(li, $(li).find("input").eq(0).val());
        			return false;
        		}
    		} else if ($(li).find("input").eq(4).is(":checked") && $(li).find("input").eq(2).val() != "") {
    			if (!$(li).find("input").eq(1).validatebox("isValid")
        				|| !$(li).find("input").eq(2).validatebox("isValid")
        				|| !$(li).find("input").eq(3).validatebox("isValid")) {
        			phoneValidation = false;
        			return false;
        		}
        		if (isRemotePhoneExists(
        				contactId, '2', 
						$(li).find("input").eq(1).val(), 
						$(li).find("input").eq(2).val(), 
						$(li).find("input").eq(3).val())) {
					phoneValidation = false;
					setPhoneExistInvalid(li, $(li).find("input").eq(2).val(), $(li).find("input").eq(3).val());
					return false;
				}
    		}
    		if (pos == length - 2) return false;
    	});
    }
    return phoneValidation;
}

function isRemotePhoneExists(contactId, type, phn1, phn2, phn3) {
	var exist = true;
	var data = {};
	data.contactid = contactId;
	data.phonetypid = type;
	data.phn2 = phn2;
	if (phn1 != '') data.phn1 = phn1;
	if (phn3 != '') data.phn3 = phn3;
	$.ajax({
		type: 'POST',
		async: false,
		url: prepath + '/check/phone',
		contentType: 'application/json; charset=utf-8',
		data: JSON.stringify(data),
		dataType: 'text',
		success: function(data) {
			exist = data == "true";
		}
	});
	return exist;
}

function setMobileExistInvalid(li, mobileNum) {
	$(li).find("input").eq(0).addClass("validatebox-invalid");
	$(li).find("input").eq(0).validatebox({
		tipOptions: {	// the options to create tooltip
			showEvent: 'mouseenter',
			hideEvent: 'mouseleave',
			showDelay: 0,
			hideDelay: 0,
			zIndex: '',
			onShow: function(){
				if ($(this).val() == mobileNum){
					$(this).tooltip('update', '重复的手机号码');
				} else {
					$(this).tooltip('tip').css({
						color: '#000',
						borderColor: '#CC9933',
						backgroundColor: '#FFFFCC'
					});
				}
			},
			onHide: function(){
				$(this).tooltip('destroy');
			}
		}
	}).tooltip({
		position: 'right',
		content: '重复的手机号码',
		onShow: function(){
			$(this).tooltip('tip').css({
				color: '#000',
				borderColor: '#CC9933',
				backgroundColor: '#FFFFCC'
			});
		}
	});
}

function setPhoneExistInvalid(li, phn2, phn3) {
	$(li).find("input").eq(2).addClass("validatebox-invalid");
	$(li).find("input").eq(2).validatebox({
		tipOptions: {	// the options to create tooltip
			showEvent: 'mouseenter',
			hideEvent: 'mouseleave',
			showDelay: 0,
			hideDelay: 0,
			zIndex: '',
			onShow: function(){
				if ($(this).val() == phn2){
					$(this).tooltip('update', '重复固话号码');
				} else {
					$(this).tooltip('tip').css({
						color: '#000',
						borderColor: '#CC9933',
						backgroundColor: '#FFFFCC'
					});
				}
			},
			onHide: function(){
				$(this).tooltip('destroy');
			}
		}
	}).tooltip({
		position: 'right',
		content: '重复固话号码',
		onShow: function(){
			$(this).tooltip('tip').css({
				color: '#000',
				borderColor: '#CC9933',
				backgroundColor: '#FFFFCC'
			});
		}
	});
	
	if (phn3 != '') {
		$(li).find("input").eq(3).addClass("validatebox-invalid");
		$(li).find("input").eq(3).validatebox({
			tipOptions: {	// the options to create tooltip
				showEvent: 'mouseenter',
				hideEvent: 'mouseleave',
				showDelay: 0,
				hideDelay: 0,
				zIndex: '',
				onShow: function(){
					if ($(li).find("input").eq(2).val() == phn2 && $(this).val() == phn3){
						$(this).tooltip('update', '重复分机号码');
					} else {
						$(this).tooltip('tip').css({
							color: '#000',
							borderColor: '#CC9933',
							backgroundColor: '#FFFFCC'
						});
					}
				},
				onHide: function(){
					$(this).tooltip('destroy');
				}
			}
		}).tooltip({
			position: 'right',
			content: '重复分机号码',
			onShow: function(){
				$(this).tooltip('tip').css({
					color: '#000',
					borderColor: '#CC9933',
					backgroundColor: '#FFFFCC'
				});
			}
		});
	}
}

function openContact(){
     var contactId= $("#repeat_mobile_contact_id").html();
     window.parent.addTab("p_"+contactId,"客户详情",false,'/contact/1/'+contactId);
}

/**
 * 选择收货人,全局弹出框
 */
function selectContact(type){
	if (type == '1') {
    	window.parent.queryC(frameElement.id,'selectReceiptCallback','editOrder');
	} else if (type == '2') {
		window.parent.queryC(frameElement.id,'selectCreditCallback','editOrder');
	}
}

function getContact(contactId) {
	var contact;
	$.ajax({
		type: 'GET',
		async: false,
		url: prepath + '/contact/' + contactId,
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		success: function(data) {
			contact = data;
		}
	});
	return contact;
}

function getContactState(contactId) {
	return getContact(contactId).state;
}

/**
 * 选择完收货人后的回调方法
 * @param addressId
 */
function selectReceiptCallback(contactId) {
	var contactState = getContactState(contactId);
	if (contactState == 1 || contactState == 3) {
		showDialog("提示", "不能选择正在审核中或者未通过审核的联系人");
		return;
	}
	
	initReceiptListTable(contactId);
	// 提示联系人正在走流程
	if (contactNameInBPM(contactId)) {
		showDialog("提示", "修改的联系姓名正在审批中");
	}
	$("#tab_myorder_modify_"+orderId, window.parent.document).click();
}

function selectCreditCallback(contactId) {
	var contactState = getContactState(contactId);
	if (contactState == 1 || contactState == 3) {
		showDialog("提示", "不能选择正在审核中或者未通过审核的联系人");
		return;
	}
	initCreditListTable(contactId);
	window.parent.SetCwinHeight(frameElement);
	$("#tab_myorder_modify_"+orderId, window.parent.document).click();
}

function contactNameInBPM(contactId) {
	var result = false;
	$.ajax({
		type: 'GET',
		async: false,
		url: prepath + '/check/contactName?contactId=' + contactId,
		success: function(data) {
			result = data == "true";
		},
		dataType: 'text'
	});
	return result;
}

function initCreditTable() {
	$("#id_credit_table").datagrid({
		singleSelect: true,
		fitColumns: true,
        scrollbarSize:-1,
		height: 59,
		url: prepath + '/credit/' + orderId,
		columns: [[
			{field: 'contactName', title: '持卡人', width: 108},
			{field: 'identityTypeName', title: '证件类型', width: 108},
			{field: 'identityNumber', title: '证件号码', width: 162, formatter: enIdCard},
			{field: 'cardType', title: '信用卡类型', width: 140},
			{field: 'cardNumber', title: '信用卡卡号', width: 162, formatter: enCard},
			{field: 'validity', title: '有效期', width: 86},
			{field: 'amortisation', title: '分期数', width: 68,
            	editor: {
                    type:'combobox', options: {
                        valueField:'amortisation',
                        textField:'amortisation',
                        data:[],
                        required:true,
                        editable:false,
                        onSelect: function(record) {
                        	$("#id_credit_table").datagrid('endEdit', 0);
                        }
                    }
                }
			}
		]],
		onClickCell: clickAmorCell,
		onAfterEdit: onAfterEdit
	});
}

function initCreditListTable(contactId) {
	$('#id_credit_list_table').datagrid({
        width: '100%',
        height: 140,
        url:prepath + '/credits/'+contactId,
        singleSelect:true,
        fitColumns:true,
        columns:[[
        	{field: 'state', title: '审批', width: 50, formatter: showAuditState},
        	{field: 'contactName', title: '持卡人', width: 100},
            {field: 'cardtypeName', title: '卡类型', width: 160, formatter: formatCardtypeName},
            {field: 'cardNumber', title: '卡号', width: 150, formatter: enCredits},
            {field: 'validDate', title: '有效期', width: 80},
            {field: 'amor', title: '分期数', width: 100,
            	editor: {
                    type: 'combobox', options: {
                        valueField: 'amortisation',
                        textField: 'amortisation',
                        data: [],
                        required: true,
                        editable: false
                    }
                }
            },
            {field: 'modify', title: '操作', width: 70, align: 'center',
            	formatter: function(value, row, index){
            		return '<a onclick="editCredit(\'' + index + '\')">修改</a>';
            	}
            }
        ]],
        onDblClickRow: doSelectCredit,
        onClickCell: clickAmorCell,
        onLoadSuccess: function(data) {
        	var contact = getContact(contactId);
			$("#credit_contactName").html(contact.name);
			$("#credit_contactId").html(contact.contactid);
        }
    });
	$("#id_credit_list_div").panel("open");
	$('#id_credit_button_div').show();
}

function formatCardtypeName(value, row) {
	if(row.type == "001" || row.type == "002" || row.type == "011" || row.type == "014"){
        return value;
    }
    return row.bankName != "" || row.bankName != "其他" ? row.bankName + "-" + row.cardtypeName : row.cardtypeName;
}

function enIdCard(value, row) {
	return encrypt(row.identityType, value);
}

function enCard(value, row) {
	return encrypt(row.cardTypeId, value);
}

function enCredits(value, row) {
	return encrypt(row.type, value);
}

function encrypt(type, value) {
	if(type == "001"){
        return value.substr(0, 4) + "**********" + value.substr(value.length - 4, value.length);
    } else if (type == "002" || type == "011" || type == "014") {
    	return value.substr(0, value.length - 4) + "****";
    } else {
    	var str = value.substr(0, 4);
    	for (var i = 0; i < value.length - 8; i++) {
    		str += "*";
    	}
    	str += value.substr(value.length - 4, value.length);
    	return str;
    }
}

function toggleCredits(contactId) {
	if ($("#id_credit_list_div").parent().css("display") == "none") {
		showCredits(contactId);
	} else {
		cancelCredit();
	}
}

/**
 * 显示信息table
 */
function showCredits(contactId) {
	initCreditListTable(contactId);
    window.parent.SetCwinHeight(frameElement);
}

function cancelCredit() {
    $('#id_credit_button_div').hide();
    doCancelCredit();
    $("#id_credit_list_div").panel("close");
    $("#id_credit_create_table").hide();
	window.parent.SetCwinHeight(frameElement);
}

function clickAmorCell(rowIndex, field, value) {
	var row = undefined;
	if (field == "amor") {
		row = $("#id_credit_list_table").datagrid("getRows")[rowIndex];
		if(row.type == "001" || row.type == "002" || row.type == "011" || row.type == "014"){
	        return;
	    }
	}
	else if (field == "amortisation") {
		row = $("#id_credit_table").datagrid("getRows")[rowIndex];
	}
	
	if (row != undefined) {
		var lastIndex = $(this).attr("lastIndex");
	    if(lastIndex){
	        $(this).datagrid('endEdit', lastIndex);
	    }
	    $(this).datagrid('beginEdit', rowIndex);
	    var editors = $(this).datagrid('getEditors', rowIndex);
	    if(editors.length > 0){
	    	if (row.amortisation != undefined) $(editors[0].target).combobox("setValue", row.amortisation);
	        $(editors[0].target).combobox("loadData", row.amortisations);
	        $(editors[0].target).focus();
	    }
	    $(this).attr("lastIndex", rowIndex);
	}
}

function onAfterEdit(rowIndex, rowData, changes) {
	calMailPrice($('#id_address_show_table').datagrid('getRows')[0].addressId, cartId, changes.amortisation);
}

function doSelectCredit(rowIndex, row) {
	if(row.type == "001" || row.type == "002" || row.type == "011" || row.type == "014"){
        return;
    }
    if (row.state == 1 || row.state == 3) {
    	showDialog("提示", "不能选择正在审核中或者未通过审核的信用卡");
    	return;
    }
    $("#id_credit_list_table").datagrid('beginEdit', rowIndex);
    var editors = $("#id_credit_list_table").datagrid('getEditors', rowIndex);
    if(editors.length > 0){
    	if (!$(editors[0].target).combobox("isValid")) {
    		$(editors[0].target).combobox("loadData", row.amortisations);
    		$(editors[0].target).focus();
    		return;
    	}
    }
	var data = $("#id_credit_table").datagrid("getRows");
	if (data.length == 0) {
		data[0] = {};
		var idrow = undefined;
		$.each($("#id_credit_list_table").datagrid("getRows"), function(idx, row) {
			if (row.type == "001") {
				idrow = row;
				return false;
			}
			if (row.type == "002" || row.type == "011") {
				idrow = row;
				return true;
			}
		});
		if (idrow != undefined) {
			data[0].identityType = idrow.cardtypeName;
			data[0].identityNumber = idrow.cardNumber;
		} else {
			showDialog("提示", "请添加身份证、护照或者军官证");
			return;
		}
	}
	
	if (row.validDate != null && row.validDate != "") {
		if (parseInt(row.validDate.split("-")[0]) < new Date().getFullYear()) {
			showDialog("提示", "该信用卡已过期，请添加有效信用卡或者修改有效期");
			return;
		}
		if (parseInt(row.validDate.split("-")[0]) == new Date().getFullYear()
				&& parseInt(row.validDate.split("-")[1]) <= new Date().getMonth() + 1) {
			showDialog("提示", "该信用卡已过期，请添加有效信用卡或者修改有效期");
			return;
		}
	} else {
		showDialog("提示", "信用卡的有效期不能为空");
		return;
	}
	
	data[0].cardId = row.cardId;
	data[0].contactId = row.contactId;
	data[0].contactName = row.contactName;
	data[0].cardTypeId = row.type;
	data[0].cardType = row.bankName != "" || row.bankName != "其他" ? row.bankName + "-" + row.cardtypeName : row.cardtypeName;
	data[0].cardNumber = row.cardNumber;
	data[0].bankName = row.bankName;
	data[0].validity = row.validDate;
	data[0].extraCode = row.extraCode;
	data[0].amortisations = row.amortisations;
	var editors = $("#id_credit_list_table").datagrid('getEditors', rowIndex);
	if (editors.length > 0) data[0].amortisation = $(editors[0].target).combobox("getValue");
	$("#id_credit_table").datagrid('loadData', data);
	
	// 计算运费
	calMailPrice($('#id_address_show_table').datagrid('getRows')[0].addressId, cartId, data[0].amortisation);
	
	cancelCredit();
	$("#id_modify_credit_a").show();
}

/**
 * 新增卡
 * @param contactId
 */
function addCredit() {
	clearCredit();
	$("#id_credit_create_table").show();
	hideCreditInput();
	initCategory();
	window.parent.SetCwinHeight(frameElement);
}

function hideCreditInput() {
	$("#id_credit_create_table tr:eq(1) td").eq(0).hide();
	$("#id_credit_create_table tr:eq(1) td").eq(1).hide();
	$("#id_credit_create_table tr:eq(2) td").eq(0).hide();
	$("#id_credit_create_table tr:eq(2) td").eq(1).hide();
	$("#id_credit_create_table tr:eq(3) td").eq(0).hide();
	$("#id_credit_create_table tr:eq(3) td").eq(1).hide();
	$("#id_credit_create_table tr:eq(4) td").eq(0).hide();
	$("#id_credit_create_table tr:eq(4) td").eq(1).hide();
	$("#id_credit_create_table tr:eq(5) td").eq(0).hide();
	$("#id_credit_create_table tr:eq(5) td").eq(1).hide();
}

function showCreditInput() {
	$("#id_credit_create_table tr:eq(1) td").eq(0).show();
	$("#id_credit_create_table tr:eq(1) td").eq(1).show();
	$("#id_credit_create_table tr:eq(2) td").eq(0).show();
	$("#id_credit_create_table tr:eq(2) td").eq(1).show();
	$("#id_credit_create_table tr:eq(3) td").eq(0).show();
	$("#id_credit_create_table tr:eq(3) td").eq(1).show();
	$("#id_credit_create_table tr:eq(4) td").eq(0).show();
	$("#id_credit_create_table tr:eq(4) td").eq(1).show();
	$("#id_credit_create_table tr:eq(5) td").eq(0).show();
	$("#id_credit_create_table tr:eq(5) td").eq(1).show();
}

var _card_number_val = "";
/**
 * 修改卡
 * @param idx
 */
function editCredit(idx) {
	clearCredit();
	
	var rows = $('#id_credit_list_table').datagrid('getRows');
    var row = rows[idx];
    $("#id_credit_create_table").show();
    
    var category = (row.type == "001" || row.type == "002" 
    		|| row.type == "011" || row.type == "014" ? "1" : "2");
    if (category == "1" && row.state == 1) {
    	var cardTypeName = row.type == "001" ? "身份证" : (row.type == "011" ? "护照" : "军官证");
    	showDialog("提示", "正在审核中的" + cardTypeName + "不能被修改");
    	return;
    }
    
	dynAddedCreditTable(category);
    initCategory();
    $("#input_category").combobox("setValue", category);
    
	if (category == "1") {
		$("#input_cardtype").combobox("setValue", _id_data);
		$("#input_cardtype").combobox("setValue", row.type);
		$("#input_cardtype").combobox("validate");
		
		$("#input_cardnum").val(row.cardNumber);
		$("#input_cardnum").validatebox("validate");
	} else {
		initBanktype();
		initCardtype();
		$("#input_banktype").combobox("setValue", row.bankName != null ? row.bankName : "其他");
		$("#input_cardtype").combobox("setValue", row.type);
		$("#input_cardtype").combobox("validate");
		
		var options = $("#input_cardnum").validatebox("options");
		options.required = false;
		options.validType = "";
		$("#input_cardnum").validatebox("validate");
		_card_number_val = encrypt(row.type, row.cardNumber);
		$("#input_cardnum").val(encrypt(row.type, row.cardNumber));
		$("#input_cardnum").attr("plaintext", row.cardNumber);
		$("#input_cardnum").bind("keyup", function() {
			if ($("#input_cardnum").val() != _card_number_val) {
				_card_number_val = $("#input_cardnum").val();
				var options = $("#input_cardnum").validatebox("options");
				options.required = true;
				options.validType = "validBankCard";
				$("#input_cardnum").validatebox("validate");
			}
		});
	}
    $("#input_card_id").val(row.cardId);
    
    if (row.validDate != null && row.validDate != "") {
    	$("#input_vd_year").combobox("setValue", row.validDate.split("-")[0]);
    	$("#input_vd_month").combobox("setValue", row.validDate.split("-")[1]);
    }
    
	if (row.showextracode == '-1') { // 输入验证
		$("#input_extracode").validatebox({
			required: true,
			validType: 'validExtraCode'
		});
	} else {
		$("#input_extracode").validatebox({
			required: false,
			validType: 'validExtraCode'
		});
	}
	$("#input_extracode").validatebox("validate");
}

/**
 * 保存卡
 */
function doSaveCredit() {
	var cetagory = $("#input_category").combobox("getValue");
	if (!validateCredit(cetagory)) return;
	
	var row = {};
	
	row.type = $("#input_cardtype").combobox("getValue");
	var contact = getContact($("#credit_contactId").html())
	row.contactId = contact.contactid;
	row.contactName = contact.name;
	
	var plaintext = $("#input_cardnum").attr("plaintext");
	var cardnumInput = $("#input_cardnum").val();
	if (plaintext == undefined) {
		row.cardNumber = cardnumInput;
	} else {
		if (!hasMask(cardnumInput) && !isSame(plaintext, cardnumInput)) {
			row.cardNumber = cardnumInput;
		} else {
			row.cardNumber = plaintext;
		}
	}
	if ($("#input_card_id").val() != "") {
		row.cardId = $("#input_card_id").val();
	}
	
	var year = $("#input_vd_year").combobox("getValue");
	var month = $("#input_vd_month").combobox("getValue")
	if (year != "" && month != "") {
		row.validDate = year + "-" + month;
	}
	
	if (cetagory == "1") { // 只能修改身份信息
		$.ajax({
			type: 'POST',
			async: false,
			url: prepath + '/card/save',
			data: row,
			dataType: 'json',
			success: function(data) {
				if (data.st == "1") {
					showDialog("成功", "身份信息保存成功");
					$('#id_credit_list_table').datagrid('reload');
				} else {
					showDialog("失败", "失败原因：" + data.msg);
				}
			}
		});
	} else if (cetagory == "2") {
		// TODO 判断重复
		var isDuplicate = false;
		var card = {};
		card.type = row.type;
		card.cardNumber = row.cardNumber;
		card.validDate = row.validDate;
//		card.extraCode = $("#input_extracode").val();
		
		$.ajax({
			type: 'POST',
			async: false,
			url: prepath + '/credit/duplicate',
			data: card,
			dataType: 'text',
			success: function(res) {
				isDuplicate = res == 'true';
			}
		});
		
		if (isDuplicate) {
			showDialog("添加卡失败", "失败原因: 不能添加重复的卡");
			return;
		}
		
		var rows = $('#id_credit_list_table').datagrid('getRows');
		rows.unshift(row); // 在数组第一个元素添加新建的
		
		row.extraCode = $("#input_extracode").val();
		row.bankName = $("#input_banktype").combobox("getText");
		row.cardtypeName = $("#input_cardtype").combobox("getText");
		
		$.ajax({
			async: false,
			url: prepath + '/credit/amor?type=' + row.type,
			dataType: 'json',
			success: function(res) {
				row.amortisations = res;
			}
		});
		
		$("#id_credit_list_table").datagrid("loadData",rows);
	}
	
	doCancelCredit();
}

function doCancelCredit() {
	clearCredit();
	$("#id_credit_create_table").hide();
	window.parent.SetCwinHeight(frameElement);
}

/**
 * 清除卡输入控件
 */
function clearCredit() {
	$("#input_cardnum").unbind();
	$("#input_card_id").val("");
	$("#id_credit_create_table tr:eq(1) td").eq(0).empty();
	$("#id_credit_create_table tr:eq(1) td").eq(1).empty();
	$("#id_credit_create_table tr:eq(2) td").eq(0).empty();
	$("#id_credit_create_table tr:eq(2) td").eq(1).empty();
	$("#id_credit_create_table tr:eq(3) td").eq(0).empty();
	$("#id_credit_create_table tr:eq(3) td").eq(1).empty();
	$("#id_credit_create_table tr:eq(4) td").eq(0).empty();
	$("#id_credit_create_table tr:eq(4) td").eq(1).empty();
	$("#id_credit_create_table tr:eq(5) td").eq(0).empty();
	$("#id_credit_create_table tr:eq(5) td").eq(1).empty();
}

/**
 * 验证卡信息
 * @returns {Boolean}
 */
function validateCredit(cetagory) {
	var isValid = true;
	if (cetagory == '2') {
		isValid = $('#input_cardnum').validatebox("isValid") 
				&& $("#input_vd_year").combobox("isValid")
				&& $("#input_vd_month").combobox("isValid")
				&& $("#input_extracode").validatebox("isValid");
	}
	return $('#input_cardtype').combobox("isValid") 
			&& $('#input_cardnum').validatebox("isValid")
			&& isValid;
}

/**
 * 初始化类型
 */
function initCategory() {
	$("#input_category").combobox({
		width: 100,
		valueField: "category",
		textField: "name",
		data: [
			{"category": "1", "name": "身份卡"},
			{"category": "2", "name": "银行卡"}
		],
		onSelect: selectCategory
	});
}

/**
 * 根据类型初始化卡类型（银行信息）
 * @param category
 */
function initBanktype() {
	$("#input_banktype").combobox({
		width: 125,
		url: prepath + "/banktype",
		valueField: "value",
		textField: "name",
		onSelect: selectBanktype
	});
}

/**
 * 根据类型和卡类型（银行信息）初始化卡详细类型
 * @param category
 * @param bank
 */
function initCardtype(bnk) {
	var url = prepath + "/cardtype";
	if (bnk != undefined) url += "?bank=" + bnk;
	$("#input_cardtype").combobox({
		width: 150,
		required: true,
		url: url,
		valueField: "cardtypeid",
		textField: "cardname",
		onSelect: selectCardtype
	});
}

function selectCategory(record) {
    dynAddedCreditTable(record.category);
}

function selectBanktype(record) {
	initCardtype(record.value);
}

function selectCardtype(record) {
	if (record.showextracode == '-1') { // 输入验证
		$("#input_extracode").validatebox({
			required: true,
			validType: 'validExtraCode'
		});
	} else {
		$("#input_extracode").validatebox({
			required: false,
			validType: 'validExtraCode'
		});
	}
	$("#input_extracode").validatebox("validate");
}

/**
 * 动态创建卡输入控件
 * @param type
 */
function dynAddedCreditTable(type) {
	clearCredit();
	showCreditInput();
	if (type == "1") {// 身份证
		$("#id_credit_create_table tr:eq(1) td").eq(0).html('<div class="phoneNums-title">证件类型:</div>');
		$("#id_credit_create_table tr:eq(1) td").eq(1).html('<input id="input_cardtype" />');
		$("#id_credit_create_table tr:eq(2) td").eq(0).html('<div class="phoneNums-title">证件号码:</div>');
		$("#id_credit_create_table tr:eq(2) td").eq(1).html('<input id="input_cardnum" type="text" size="50" />');
		$("#id_credit_create_table tr:eq(3) td").eq(0).html('<div class="phoneNums-title">有效期:</div>');
		$("#id_credit_create_table tr:eq(3) td").eq(1).html('<input id="input_vd_year" /> 年 / <input id="input_vd_month" /> 月');
		$("#id_credit_create_table tr:eq(4) td").eq(0).hide();
		$("#id_credit_create_table tr:eq(4) td").eq(1).hide();
		$("#id_credit_create_table tr:eq(5) td").eq(0).hide();
		$("#id_credit_create_table tr:eq(5) td").eq(1).hide();
		$("#input_cardtype").combobox({
			width: 150,
			data: _id_data,
			valueField: "cardtypeid",
			textField: "cardname",
			required: true
		});
		$("#input_cardnum").validatebox({
			required: true,
			validType: "validIdCard"
		});
		$("#input_cardtype").combobox("validate");
		$("#input_cardnum").validatebox("validate");
	} else if (type == '2') {// 银行卡
		$("#id_credit_create_table tr:eq(1) td").eq(0).html('<div class="phoneNums-title">发卡银行:</div>');
		$("#id_credit_create_table tr:eq(1) td").eq(1).html('<input id="input_banktype" />');
		$("#id_credit_create_table tr:eq(2) td").eq(0).html('<div class="phoneNums-title">卡类型:</div>');
		$("#id_credit_create_table tr:eq(2) td").eq(1).html('<input id="input_cardtype" />');
		$("#id_credit_create_table tr:eq(3) td").eq(0).html('<div class="phoneNums-title">银行卡号:</div>');
		$("#id_credit_create_table tr:eq(3) td").eq(1).html('<input id="input_cardnum" type="text" size="50" />');
		$("#id_credit_create_table tr:eq(4) td").eq(0).html('<div class="phoneNums-title">有效期:</div>');
		$("#id_credit_create_table tr:eq(4) td").eq(1).html('<input id="input_vd_year" /> 年 / <input id="input_vd_month" /> 月');
		$("#id_credit_create_table tr:eq(5) td").eq(0).html('<div class="phoneNums-title">附加码:</div>');
		$("#id_credit_create_table tr:eq(5) td").eq(1).html('<input id="input_extracode" />');
		initBanktype();
		initCardtype();
		$("#input_cardnum").validatebox({
			required: true,
			validType: 'validBankCard'
		});
		$("#input_cardtype").combobox("validate");
		$("#input_cardnum").validatebox("validate");
	}
	
	var years = [];
	var now = new Date();
	for (var i = 0; i < 20; i++) {
		years[i] = {};
		years[i].y = now.getFullYear() + i;
	}
	$("#input_vd_year").combobox({
		width: 70,
		data: years,
		valueField: "y",
		textField: "y"
	});
	
	var months = [];
	for (var i = 1; i <= 12; i++) {
		months[i-1] = {};
		months[i-1].m = i < 10 ? "0" + i : i;
	}
	$("#input_vd_month").combobox({
		width: 57,
		data: months,
		valueField: "m",
		textField: "m"
	});
	
	if (type == '2') {
		$("#input_vd_year").combobox({
			required: true,
			validType: 'validYear'
		});
		$("#input_vd_year").combobox("validate");
		$("#input_vd_month").combobox({
			required: true,
			validType: ['validMonth', 'validSameYearMonth']
		});
		$("#input_vd_month").combobox("validate");
	}
	
	window.parent.SetCwinHeight(frameElement);
}

/**
 * 取消订单
 */
function cancelOrder() {
	var content = '<div class="popWin_wrap"><table><tr><td valign="top">订单号</td><td><span class="ml10">' + orderId + '</span></td></tr>'
			+ '<tr><td valign="top">取消原因</td><td><textarea  class="ml10" rows="3" style="width:300px" id="cancelRemark"></textarea></td></tr></table></div>';
	$("#id_dialog_content_div").html(content);
	$("#id_dialog_content_div").dialog({
        title: '取消订单',
        width: 400,
        top: 150,
        modal: true,
        draggable:false,
        buttons: [
            {
            	text: '确认',
                plain: true,
                handler: handleCancelOrder
            },
            {
            	text: '取消',
                plain:true,
            	handler: function () {
                    $("#id_dialog_content_div").dialog("close");
                }
            }
        ]
    });
    $("#id_dialog_content_div").dialog("open");
}

function handleCancelOrder() {
    $.ajax({
        url:"/myorder/myorder/cancel",
        contentType:"application/json; charset=utf-8",
        data: {
        	"orderId": orderId, 
        	"remark": encodeURI($("#cancelRemark").val())
        },
        success:function(data){
            $("#id_dialog_content_div").dialog("close");
            showDialog("取消订单", data.msg);
            if (data.succ == '1' && data.audit == '0') {
                // refresh contact order list
                window.parent.subContactCallback(orderContactId, 'refreshOrderHistory');
                // refresh my order list
                window.parent.subContactCallback('myorder', 'refreshOrderHistory');
                // hide editorder and removeorder
                disableEditOrder();
            }
        }
    });
}

function disableEditOrder() {
	// 隐藏订单修改和取消
	$("#id_order_edit_button").hide();
	$("#id_order_cancel_button").hide();
	
	// 隐藏商品明细修改
	$("#id_modify_product_a").hide();
	
	// 隐藏信用卡修改
	$("#id_modify_credit_a").hide();
	
	// 隐藏收货人修改
	$("#id_modify_receipt_a").hide();
	
	// 隐藏订单信息修改
	$("#id_modify_invoice_a").hide();
	$("#id_modify_note_a").hide();
	$("#id_modify_settle_a").hide();
	$("#id_modify_shipment_a").hide();
	
	// 隐藏提交修改按钮
	$("#id_main_button_div").hide();
}

/**
 * 显示修改确认弹出层
 */
function showConfirmWindow() {
	var options = {title: "修改确认", width: 1000, height: 550};
	if (triggerCompare()) {
		window.parent.popConfirm(frameElement.id, "id_edit_comfirm_div_" + orderId, options);
	}
}

/**
 * 触发比较
 */
function triggerCompare() {
	if (!validateBeforeTrigger()) {
		return false;
	}
	
	var row = $('#id_address_show_table').datagrid('getRows')[0];
    
	// 比较地址
	var preAddress = formatAddressDesc("", row);
	
	var isPhoneSame = true;
	var phonestr = "";
	if (!isSame(getContactId, row.contactId)) {
		isPhoneSame = false;
		$.each(row.phones, function(idx, phone) {
			phonestr += '<p>' + mergePhone(phone) + '</p>';
		});
	} else if (row.addedPhones != undefined) {
		isPhoneSame = false;
		phonestr = getOldValue("phoneString");
		$.each(row.addedPhones, function(idx, phone) {
			phonestr += '<p>' + mergePhone(phone) + '</p>';
		});
	} else {
		phonestr = getOldValue("phoneString");
	}
	
	// 收货人信息
	var isReceiptChanged = !isSame(getOldValue("contactName"), (payType == '11' ? orderContactName : row.contactName))
			|| !isSame(getOldValue("preAddress"), preAddress)
			|| !isSame(getOldValue("addressDesc"), row.addressDesc)
			|| !isSame(getOldValue("zipcode"), row.zipcode)
			|| !isPhoneSame;
	if (!isSame(getOldValue("contactName"), (payType == '11' ? orderContactName : row.contactName))) setHighlight("contactName");
	if (!isSame(getOldValue("preAddress"), preAddress)) setHighlight("preAddress");
	if (!isSame(getOldValue("addressDesc"), row.addressDesc)) setHighlight("addressDesc");
	if (!isSame(getOldValue("zipcode"), row.zipcode)) setHighlight("zipcode");
	if (!isPhoneSame) setHighlight("phoneString");
	setNewValue("contactName", isReceiptChanged ? (payType == '11' ? orderContactName : row.contactName) : "&nbsp;");
	setNewValue("preAddress", isReceiptChanged ? preAddress : "&nbsp;");
	setNewValue("addressDesc", isReceiptChanged ? row.addressDesc : "&nbsp;");
	setNewValue("zipcode", isReceiptChanged ? row.zipcode : "&nbsp;");
	setNewValue("phoneString", isReceiptChanged ? phonestr : "&nbsp;");
	
	// 比较发票抬头
	var isInvoiceTitleChanged = !isSame(getOldValue("invoicetitle"), $("#invoicetitle").val())
	if (!isInvoiceTitleChanged) {
		setNewValue("invoicetitle", "&nbsp;");
	} else {
		setNewValue("invoicetitle", $("#invoicetitle").val());
		setHighlight("invoicetitle");
	}
	
	// 比较备注
	var isNoteChanged = !isSame(getOldValue("note"), $("#note").val())
	if (!isNoteChanged) {
		setNewValue("note", "&nbsp;");
	} else {
		setNewValue("note", $("#note").val());
		setHighlight("note");
	}
	
	// 比较积分
	var isJifenChanged = !isSame(getOldValue("jifen"), $("#jifen").html())
	if (!isJifenChanged) {
		setNewValue("jifen", "&nbsp;");
	} else {
		setNewValue("jifen", $("#jifen").html());
		setHighlight("jifen");
	}
	
	// 比较ems
	var n_ems = $("#ems").attr("checked") == "checked" ? "是" : "否";
	var isEmsChanged = !isSame(getOldValue("ems"), n_ems)
	if (!isEmsChanged) {
		setNewValue("ems", "&nbsp;");
	} else {
		setNewValue("ems", n_ems);
		setHighlight("ems");
	}
	
	// 比较注释
	var isRemarkChanged = !isSame(getOldValue("remark"), $("#remark").val())
	if (!isRemarkChanged) {
		setNewValue("remark", "&nbsp;");
	} else {
		setNewValue("remark", $("#remark").val());
		setHighlight("remark");
	}
	
	// 比较金额
	$("#o_getjifen").html(parseFloat($("#o_producttotalprice").html()) / 100);
	var isPriceChanged = !isSame(getOldValue("mailprice"), $("#mailprice").val())
			|| !isSame(getOldValue("producttotalprice"), $("#producttotalprice").html())
			|| !isSame(getOldValue("totalprice"), $("#totalprice").html())
			|| !isSame(getOldValue("getjifen"), $("#getjifen").html());
	if (!isSame(getOldValue("mailprice"), $("#mailprice").val())) setHighlight("mailprice");
	if (!isSame(getOldValue("producttotalprice"), $("#producttotalprice").html())) setHighlight("producttotalprice");
	if (!isSame(getOldValue("totalprice"), $("#totalprice").html())) setHighlight("totalprice");
	if (!isSame(getOldValue("getjifen"), $("#getjifen").html())) setHighlight("getjifen");
	setNewValue("mailprice", isPriceChanged ? $("#mailprice").val() : "&nbsp;");
	setNewValue("producttotalprice", isPriceChanged ? $("#producttotalprice").html() : "&nbsp;");
	setNewValue("totalprice", isPriceChanged ? $("#totalprice").html() : "&nbsp;");
	setNewValue("getjifen", isPriceChanged ? $("#getjifen").html() : "&nbsp;");
	
	// 信用卡比较
	var isCreditChanged = false;
	if ($("#id_credit_table").length > 0 && $("#id_credit_table").datagrid("getRows").length > 0) {
		var cardRow = $("#id_credit_table").datagrid("getRows")[0];
		isCreditChanged = !isSame(getOldValue("cardholder"), cardRow.contactName)
				|| !isSame(getOldValue("cardtype"), cardRow.cardType)
				|| !isSame(getOldValue("cardnum"), cardRow.cardNumber)
				|| !isSame(getOldValue("validdate"), cardRow.validity)
				|| !isSame(getOldValue("amortisation"), cardRow.amortisation);
		if (!isSame(getOldValue("cardholder"), cardRow.contactName)) setHighlight("cardholder");
		if (!isSame(getOldValue("cardtype"), cardRow.cardType)) setHighlight("cardtype");
		if (!isSame(getOldValue("cardnum"), cardRow.cardNumber)) setHighlight("cardnum");
		if (!isSame(getOldValue("validdate"), cardRow.validity)) setHighlight("validdate");
		if (!isSame(getOldValue("amortisation"), cardRow.amortisation)) setHighlight("amortisation");
		setNewValue("cardholder", isCreditChanged ? cardRow.contactName : "&nbsp;");
		setNewValue("cardtype", isCreditChanged ? cardRow.cardType : "&nbsp;");
		setNewValue("cardnum", isCreditChanged ? cardRow.cardNumber : "&nbsp;");
		setNewValue("validdate", isCreditChanged ? cardRow.validity : "&nbsp;");
		setNewValue("amortisation", isCreditChanged ? cardRow.amortisation : "&nbsp;");
	}
	
	if (isReceiptChanged || isInvoiceTitleChanged || isNoteChanged || isJifenChanged 
			|| isEmsChanged || isRemarkChanged || isPriceChanged || isCreditChanged || isDetChanged == "true") {
		return true;
	} else {
		showDialog("提示", "订单未被修改，不能提交请求");
		return false;
	}
	
}

function validateBeforeTrigger() {
	// 判断是否有正在审批流程
	if (isOrderInBmp()) {
		return false;
	}
	
	if (!$("#mailprice").validatebox("isValid")) {
		return false;
	}
	
	if (confirm == "-1") {
		if (parseFloat($("#o_totalprice").html()) != parseFloat($("#totalprice").html())
				|| parseFloat($("#o_producttotalprice").html()) != parseFloat($("#producttotalprice").html())
				|| parseFloat($("#o_jifen").html()) != parseFloat($("#jifen").html())) { 
			showDialog("提示", "已索权的订单，订单总金额必须与索权金额相同");
    		return false;
		}
	}
	
	if (payType == "2") {
		if ($("#id_credit_table").datagrid("getRows").length == 0) {
			showDialog("提示", "支付类型为信用卡的订单，必须选择一张信用卡进行支付");
    		return false;	
		}
		
		var row = $('#id_address_show_table').datagrid('getRows')[0];
		var cardRow = $("#id_credit_table").datagrid("getRows")[0];
		
		if (row.contactId != cardRow.contactId) {
			showDialog("提示", "支付类型为信用卡的订单，收货人必须和付款人相同"	);
			return false;
		}
		
	    var editors = $("#id_credit_table").datagrid("getEditors", 0);
	    if(editors.length > 0){
	    	cardRow.amortisation = $(editors[0].target).combobox("getValue")
        	$(editors[0].target).combobox("loadData", cardRow.amortisations);
	    	
	    	if (!$(editors[0].target).combobox("isValid")) {
	    		$(editors[0].target).focus();
	    		return false;
	    	}
	    }
	    
		if (cardRow.amortisation) {
			for (var idx in cardRow.amortisations) {
				var am = cardRow.amortisations[idx];
				if (cardRow.amortisation == am.amortisation) {
					if (parseFloat($("#totalprice").html()) < parseFloat(am.lprice) || parseFloat($("#totalprice").html()) >= parseFloat(am.uprice)) {
						var errorMsg = "当前订单的金额[" + $("#totalprice").html() + "]不满足分期条件, " + cardRow.cardType + " 分[" 
								+ am.amortisation + "]期的金额必须在[" + am.lprice + "-" + am.uprice + "]以内";
						showDialog("分期失败", errorMsg);
						return false;			
					}
				}
			}
		}
	}
	
	return true;
}

function isOrderInBmp() {
	var isInBmp = false;
	$.ajax({
		type: 'POST',
		async: false,
		url: prepath + '/check/' + orderId + '/approving',
		dataType: 'json',
		success: function(data) {
			if (data.approve == 'false') {
				isInBmp = true;
				var html = '该订单正在审批中 批次号：'
						+ '<a class="link" onclick="openAudit(\'' + data.batchId + '\', \'' 
						+ orderId + '\', \'' + data.bpmType + '\')">' + data.batchId + '</a>';
				showDialog("提示", html);
			}
		}
	});
	return isInBmp;
}

/**
 * 是否带有*的字符串
 * @param str
 * @return {Boolean}
 */
function hasMask(str) {
	return str.indexOf('*') > -1;
}

/**
 * 比较两个值是否相等
 * @param oldVal
 * @param newVal
 * @returns {Boolean}
 */
function isSame(oldVal, newVal) {
	if ((oldVal == undefined || oldVal == null || oldVal == "") 
			&& (newVal == undefined || newVal == null || newVal == "")) {
		return true;
	}
	if (!isNaN(oldVal) && !isNaN(newVal)) {
		return parseFloat(oldVal) == parseFloat(newVal);
	}
	return oldVal == newVal;
}

/**
 * 得到旧值
 * @param id
 * @returns
 */
function getOldValue(id) {
	return $("#o_" + id).html();
}

/**
 * 设置新值
 * @param id
 * @param newVal
 */
function setNewValue(id, newVal) {
	$("#n_" + id).html(newVal);
}

/**
 * 设置高亮
 * @param id
 */
function setHighlight(id) {
	$("#n_" + id).css("color","red");
}

/**
 * 关联单确认
 */
function relatedConfirm() {
	var row = $('#id_address_show_table').datagrid('getRows')[0];
	
	var contactChanged = !isSame(payType == '11' ? '上门自提点' : getOldValue("contactName"), row.contactName);
	var addressChanged = !isSame(getOldValue("preAddress"), formatAddressDesc("", row))
			|| !isSame(getOldValue("addressDesc"), row.addressDesc)
			|| !isSame(getOldValue("zipcode"), row.zipcode);
	var phoneChanged = row.addedPhones != undefined;
	
	var orders = {};
	$.ajax({
		type: 'POST',
		async: false,
		url: prepath + '/check/' + orderId + '/related',
		data: {
			'contactChanged': contactChanged, 
			'addressChanged': addressChanged, 
			'phoneChanged': phoneChanged
		},
		dataType: 'json',
		success: function(data) {
			orders.total = data.length;
			orders.rows = data;
		}
	});
	
	if (orders.total != undefined && orders.total > 0) {
		showRelatedOrders(orders);
	} else {
		commitEdit();
	}
}

function validateRelatedChecked(orderId) {
	var relatedtb = $('#id_related_' + orderId, window.parent.document);
	if (relatedtb.length > 0 && $(relatedtb[0]).datagrid('getChecked').length == 0) {
		showDialog("关联单", "请选择需要修改的关联单，不修改关联单请按\"不需修改\"");
		return;
	}
	commitEdit();
}

/**
 * 提交修改订单请求
 */
function commitEdit() {
	var row = $("#id_address_show_table").datagrid("getRows")[0];
	
	var data = {};
	data.orderId = orderId;
	data.remark = $("#remark").val();
	if (batchId != "") 
		data.batchId = batchId;
	// 购物车
	data.cart = {};
	if (cartId != "") data.cart.cartId = cartId;
	
	// 订单属性
	data.order = {};
	data.order.payType = payType;
	data.order.note = $("#note").val();
	data.order.invoiceTitle = $("#invoicetitle").val();
	data.order.jifen = $("#jifen").val();
	data.order.mailPrice = $("#mailprice").val();
	data.order.productTotalPrice = $("#producttotalprice").html();
	data.order.productTotalNum = $("#producttotalnum").html();
	data.order.totalPrice = $("#totalprice").html();
	data.order.ems = $("#ems").attr("checked") == "checked" ? "Y" : "N";

    // 信用卡
    if ($("#id_credit_table").length > 0) {
    	var creditRows = $("#id_credit_table").datagrid("getRows");
    	if (creditRows.length > 0) {
	        data.order.amortisation = creditRows[0].amortisation;
	        data.card = {};
	        if (creditRows[0].cardId) data.card.cardId = creditRows[0].cardId;
	        data.card.contactId = creditRows[0].contactId;
	        data.card.type = creditRows[0].cardTypeId;
	        data.card.cardNumber = creditRows[0].cardNumber;
	        data.card.validDate = creditRows[0].validity;
	        data.card.extraCode = creditRows[0].extraCode;
    	}
    }

	// 收货人
	data.contactId = row.contactId;
	
	// 收货联系人地址
	data.address = {};
	data.address.addressId = row.addressId;
	data.address.addressDesc = row.addressDesc;
	data.address.provinceId = row.provinceId;
	data.address.provinceName = row.provinceName;
	data.address.cityId = row.cityId;
	data.address.cityName = row.cityName;
	data.address.countyId = row.countyId;
	data.address.countyName = row.countyName;
	data.address.areaId = row.areaId;
	data.address.areaName = row.areaName;
	data.address.zipcode = row.zipcode;
	
	// 收货电话
	if (row.addedPhones != undefined) {
		data.phones = [];
		$.each(row.addedPhones, function(pos, val) {
			data.phones[pos] = {};
			data.phones[pos].phoneType = val.phonetypid;
			data.phones[pos].prmphn = val.prmphn;
			data.phones[pos].phn2 = val.phn2;
			if (val.phn1 != undefined) data.phones[pos].phn1 = val.phn1;
			if (val.phn3 != undefined) data.phones[pos].phn3 = val.phn3;
		});
	}
	
	// 关联单
	var relatedtb = $('#id_related_table_' + orderId, window.parent.document);
	if (relatedtb.length > 0) {
		// 不提交关联单
//		data.relateds = [];
//		$.each($(relatedtb[0]).datagrid('getChecked'), function(pos, val){
//			data.relateds[pos] = val.orderId;
//		});
		window.parent.closeWin('id_related_' + orderId);
	}
	
	// 关闭比较弹窗
	window.parent.closeWin('id_edit_comfirm_div_' + orderId);
	
	var loading = new ol.loading({
		id : "loading_mask"
	});
	loading.show();
	
	$.ajax({
		type: 'POST',
		async: false,
		url: prepath + '/edit/submit',
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(res) {
			loading.hide();
			showDialog(res.code == '1' ? "成功" : "失败", res.msg);
			if (res.code == '1') {
				disableButton();
				window.parent.destoryTab("myorder_" + orderId);
			}
		},
		error: function(res) {
			loading.hide();
            showDialog("失败", "无法提交您的请求，请联系系统管理员");
		}
	});
	
}

function showContact(contactId) {
	window.parent.addTab(contactId, "我的客户", false, '/contact/1/' + contactId);
}

/**
 * 弹出对话框
 * @param title
 * @param content
 */
function showDialog(title, content) {
	window.parent.popAlert(title, content);
}

function consultOrder() {
    var text='订单：'+ orderId +' 确定要转咨询吗？';
	$('#span_consult_order_content').html(text);
    $('#dialog_consult_order').dialog({
    	title: '订单转咨询',
        width: 300,
        top: 200,
        iconCls: '',
        shadow: true,
        modal: true,
        closed: true,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        draggable: false,
        buttons: [{
            text: '确定',
            plain: true,
            handler: function() {
                $.ajax({
                    url: ctx + "/myorder/myorder/consult",
                    contentType: "application/json; charset=utf-8",
                    data: {"orderId": orderId},
                    success: function (data) {
                        if(data != null)  {
                            if (data.succ != null && data.succ == '1') {
                                $('#dialog_consult_order').dialog('close');
                                $("#id_order_op_div").hide();
                                disableButton();
                                 // refresh contact order list
				                window.parent.subContactCallback(orderContactId, 'refreshOrderHistory');
				                // refresh my order list
				                window.parent.subContactCallback('myorder', 'refreshOrderHistory');
                            }
                            window.parent.alertWin('系统提示', data.msg);
                        } else {
                            $('#dialog_consult_order').dialog('close');
                        }
                    }
                });
            }
        }, 
        {
            text: '取消',
            plain: true,
            handler: function() {
                $('#dialog_consult_order').dialog('close');
            }
        }]
    });
    $('#dialog_consult_order').window('open');
}

/***********************************************/

$.extend($.fn.validatebox.defaults.rules, {  
	validZip: {  
		validator: function(value, param) {  
			return /^[0-9]{6}$/.test(value); 
		},  
		message: '请输入正确的邮编'  
	},
	validMobile: {
		validator: function(value, param) {  
			return /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value);
		},  
		message: '请输入正确的手机号码' 
	},
	validPhoneArea:	{
		validator: function(value, param) {
			return /^\d{3,4}$/.test(value);
		},
		message: '请输入正确的区号'
	},
	validPhoneNum: {
		validator: function(value, param) {
			return /^[1-9]{1}\d{6,7}$/.test(value);
		},
		message: '请输入正确的电话号码'
	},
	validPhoneExt: {
		validator: function(value, param) {
			return /\d/.test(value);
		},
		message: '请输入正确的分机号'
	},
	validIdCard: {
		validator: function(value, param) {
			if ($("#input_cardtype").combobox("getValue") == "001")
				return idCardValidate(value);
			return true;
		},
		message: '您输入的身份证号码不合法'
	},
	validBankCard: {
		validator: function(value, param) {
			return /^\d{16,19}$/.test(value);
		},
		message: '信用卡必须为16-19位的数字'
	},
	validExtraCode: {
		validator: function(value, param) {
			return value == null || value == '' || /^\d{3}$/.test(value);
		},
		message: '附加码必须为3位的数字'
	},
	validYear : {
		validator : function(value, param) {
			if (!/^\d{4}$/.test(value)) {
				return false;
			}
			return parseInt(value) >= new Date().getFullYear();
		},
		message : '请输入有效的年份, 例如: 2013'
	},
	validMonth : {
		validator : function(value, param) {
			return /^\d{2}$/.test(value) && parseInt(value) >= 1
					&& parseInt(value) <= 12;
		},
		message : '请输入正确的月份, 例如: 01'
	},
	validSameYearMonth : {
		validator : function(value, param) {
			return $("#input_vd_year").combobox("getValue") > new Date().getFullYear()
					|| ($("#input_vd_year").combobox("getValue") == new Date().getFullYear() 
							&& parseInt(value) > new Date().getMonth() + 1);
		},
		message : '您输入的月份已过期'
	}
});
