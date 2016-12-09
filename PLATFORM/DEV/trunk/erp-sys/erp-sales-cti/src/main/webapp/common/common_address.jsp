<%@include file="/common/taglibs.jsp" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<!-- 
该组件具有四级联动的地址选择
需要的参数为：
参数名				是否必要		默认						说明
enableZip			false		'true'					是否同步更新邮编
zipInputId			false		'input_zip'				邮编控件的id
provinceInputId		false		'input_province'		省份控件的id
cityInputId			false		'input_city'			城市控件的id
countyInputId		false		'input_county'			乡镇控件的id
areaInputId			false		'input_area'			区域控件的id

使用方法：
新增地址时使用				initAllAddress()
已存在四级地址id时使用		initAddress(provinceId, cityId, countyId, areaId)
 -->

<script type="text/javascript">
var enableZip = "${empty enableZip ? 'true' : enableZip}";
var zipInputId = "${empty zipInputId ? 'input_zip' : zipInputId}";
var provinceInputId = "${empty provinceInputId ? 'input_province' : provinceInputId}";
var cityInputId = "${empty cityInputId ? 'input_city' : cityInputId}";
var countyInputId = "${empty countyInputId ? 'input_county' : countyInputId}";
var areaInputId = "${empty areaInputId ? 'input_area' : areaInputId}";
function initAllAddress() {
	initProvince();
	initAllCity();
	initAllCounty();
	$("#" + areaInputId).combobox("loadData", []);
}
function initAddress(provinceId, cityId, countyId, areaId) {
	initProvince();
	initCity(provinceId);
	initCounty(cityId);
	initArea(countyId);
	$("#" + provinceInputId).combobox("setValue", provinceId);
	$("#" + cityInputId).combobox("setValue", cityId);
	$("#" + countyInputId).combobox("setValue", countyId);
	$("#" + areaInputId).combobox("setValue", areaId);
}
function initProvince() {
	$("#" + provinceInputId).combobox({
		url: "/common/province",
		valueField: "provinceId",
		textField: "provinceName",
		filter: filter,
		onSelect: provinceSelect
	});
}
function initAllCity() {
	$("#" + cityInputId).combobox({
		url: "/common/cityAll",
		valueField: "cityId",
		textField: "cityName",
		filter: filter,
		onSelect: citySelect
	});
}
function initAllCounty() {
	$("#" + countyInputId).combobox({
		url : "/common/countyAll",
		valueField: "countyId",
		textField: "countyName",
		filter: filter,
		onSelect: countySelect
	});
}
function initCity(provinceId) {
	$("#" + cityInputId).combobox({
		url: "/common/city?provinceId=" + provinceId,
		valueField: "cityId",
		textField: "cityName",
		filter: filter,
		onSelect: citySelect
	});
}
function initCounty(cityId) {
	$("#" + countyInputId).combobox({
		url: "/common/county?cityId=" + cityId,
		valueField: "countyId",
		textField: "countyName",
		filter: filter,
		onSelect: countySelect
	});
}
function initArea(countyId) {
	$("#" + areaInputId).combobox({
		url: "/common/area?countyId=" + countyId,
		valueField: "areaId",
		textField: "areaName",
		filter: filter,
	});
}
function filter(val, row) {
	return row.value.split("-")[0].indexOf(val.toUpperCase()) >= 0;
}
function provinceSelect(record) {
	initCity(record.provinceId);
	$("#" + countyInputId).combobox("clear");
	$("#" + countyInputId).combobox("loadData", []);
	$("#" + areaInputId).combobox("clear");
	$("#" + areaInputId).combobox("loadData", []);
	if (enableZip == "true") {
		$("#" + zipInputId).val("");
		$("#" + zipInputId).validatebox("validate");
	}
}
function citySelect(record) {
	initCounty(record.cityId);
	$("#" + provinceInputId).combobox("setValue", record.value.split("-")[2]);
	$("#" + areaInputId).combobox("clear");
	$("#" + areaInputId).combobox("loadData", []);
	if (enableZip == "true") {
		$("#" + zipInputId).val(record.value.split("-")[1]);
		$("#" + zipInputId).validatebox("validate");
	}
}
function countySelect(record) {
	initArea(record.countyId);
	$("#" + provinceInputId).combobox("setValue", record.value.split("-")[2]);
	$("#" + cityInputId).combobox("setValue", record.value.split("-")[3]);
	if (enableZip == "true") {
		$("#" + zipInputId).val(record.value.split("-")[1]);
		$("#" + zipInputId).validatebox("validate");
	}
}
function formatItemCity(row){
    return row.areaCode+"|"+row.cityName;

}
function formatItemCounty(row){
    return row.areaCode+"|"+row.countyName;
}
</script>

<input id="${empty provinceInputId ? 'input_province' : provinceInputId}" class="easyui-combobox" data-options="required:true" /> &nbsp;
<input id="${empty cityInputId ? 'input_city' : cityInputId}" class="easyui-combobox" data-options="required:true,formatter:formatItemCity" />&nbsp;
<input id="${empty countyInputId ? 'input_county' : countyInputId}" class="easyui-combobox" data-options="required:true,formatter:formatItemCounty" />&nbsp;
<input id="${empty areaInputId ? 'input_area' : areaInputId}" class="easyui-combobox" data-options="required:true" /> 