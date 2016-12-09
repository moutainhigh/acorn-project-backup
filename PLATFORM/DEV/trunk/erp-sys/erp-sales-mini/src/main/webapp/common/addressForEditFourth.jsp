<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<script type="text/javascript">
/**
 * 初始化四级地址
 * @param pv		省ID
 * @param citv		市ID
 * @param countv	乡镇ID
 * @param av		区域ID
 */
function addressInitForEditFourth(pv, citv, countv, av) {
    var instId = "ForEditFourth";
	$('#province'+instId).combobox({
		url : '/common/province',
		valueField : 'provinceId',
		textField : 'provinceName',
		onLoadSuccess:function(){
			$("#province"+instId).combobox('setValue', pv);
			pv='';
		}
	});
	
	$("#cityId"+instId).combobox({
		url : '/common/city?provinceId=' + pv,
		valueField : 'cityId',
		textField : 'cityName',
		onLoadSuccess:function(){
			$("#cityId"+instId).combobox('setValue', citv);
			citv='';
		}
	});

	$("#countyId"+instId).combobox({
		url : '/common/county?cityId=' + citv,
		valueField : 'countyId',
		textField : 'countyName',
		onLoadSuccess:function(){
			$("#countyId"+instId).combobox('setValue', countv);
			countv='';
		}
	});
	
	$("#areaId"+instId).combobox({
		url : '/common/area?countyId=' + countv,
		valueField : 'areaId',
		textField : 'areaName',
		onLoadSuccess:function(){
			$("#areaId"+instId).combobox('setValue', av);
			av='';
		}
	});	
}

</script>
<input disabled="disabled" class="easyui-combobox" id="provinceForEditFourth"  name="provinceForEditFourth"style="width:100px;" /> &nbsp;
<input disabled="disabled" class="easyui-combobox" id="cityIdForEditFourth"  name="cityIdForEditFourth"/>&nbsp;
<input disabled="disabled" class="easyui-combobox" id="countyIdForEditFourth"  name="countyIdForEditFourth"/>&nbsp;
<input class="easyui-combobox" id="areaIdForEditFourth" data-options="required:true"/>

