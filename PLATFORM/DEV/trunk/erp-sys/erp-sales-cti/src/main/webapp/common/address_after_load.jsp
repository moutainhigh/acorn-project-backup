<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<% 
String instId = null==request.getParameter("instId") ? "" : request.getParameter("instId").toString();
request.setAttribute("instId", instId);
boolean iszip = null==request.getParameter("iszip") ? false : true;
request.setAttribute("iszip", iszip);
%>
<script type="text/javascript">

var address_province_id=""; //省份ID
var address_city_id="";//市ID
var address_zip_county_code=""; //邮政编码
var address_zip_area_code=""; //邮政编码
var address_split="-";  //地址value 分割符
var iszip= ${iszip};
function afterInitAddress(instId) {

    $('#province' + instId).combobox({
        url: '/common/province',
        valueField: 'provinceId',
        textField: 'provinceName',
        filter: function (q, row) {
            return myAddressfilter(q, row);
        },
        onSelect: function (param) {
            $("#cityId" + instId).combobox({
                url: '/common/city?provinceId=' + param.provinceId,
                valueField: 'cityId',
                textField: 'cityName',
                filter: function (q, row) {
                    return myAddressfilter(q, row);
                }
            });

            $('#countyId' + instId).combobox("loadData", []);
            $('#countyId' + instId).combobox('clear');
            $('#areaId' + instId).combobox("loadData", []);
            $('#areaId' + instId).combobox('clear');

            if (iszip) {
                $('#zip').val('');
            }
        },
        onChange: function () {
            if (!$('#province' + instId).combobox('getValue') && !$('#cityId' + instId).combobox('getValue')) {
                cleanAddressItem(instId);
            }
        }
    });

    $('#cityId' + instId).combobox({
        //url : '/common/cityAll',
        url: '/static/plugin/city.json',
        valueField: 'cityId',
        textField: 'cityName',
        filter: function (q, row) {
            return myAddressfilter(q, row);
        },
        onLoadSuccess: function () {
            console.info("cityId..............2"+(document.activeElement.id));
            $(this).combobox('clear');
            console.info("cityId..............3"+(document.activeElement.id));
        },
        onChange: function () {
            console.info("cityId..............1"+(document.activeElement.id));
            if (!$('#province' + instId).combobox('getValue') && !$('#cityId' + instId).combobox('getValue')) {
                console.info("cityId..............4"+(document.activeElement.id));
               // cleanAddressItem(instId);

                console.info("cityId..............5"+(document.activeElement.id));
            }
            console.info("cityId..............6"+(document.activeElement.id));
        }
    });

    $('#countyId' + instId).combobox({
        //url : '/common/countyAll',
        url: '/static/plugin/country.json',
        valueField: 'countyId',
        textField: 'countyName',
        filter: function (q, row) {
            return myAddressfilter(q, row);
        }
    });

    <%--$('#cityId' + instId).combobox({--%>

        <%--onSelect: function (param) {--%>
            <%--address_province_id = param.value.split(address_split)[2];--%>
            <%--if (iszip) {--%>
                <%--address_zip_county_code = param.value.split(address_split)[1];--%>
                <%--$('#zip${instId}').val(address_zip_county_code);--%>
            <%--}--%>
            <%--$('#province' + instId).combobox('setValue', address_province_id);--%>
            <%--$("#countyId" + instId).combobox({--%>
                <%--url: '/common/county?cityId=' + param.cityId,--%>
                <%--valueField: 'countyId',--%>
                <%--textField: 'countyName',--%>
                <%--filter: function (q, row) {--%>
                    <%--return myAddressfilter(q, row);--%>
                <%--},--%>
                <%--onLoadSuccess: function () {--%>
                    <%--$(this).combobox('clear');--%>
                <%--}--%>

            <%--});--%>

            <%--$('#areaId' + instId).combobox('setValue', "");--%>
            <%--$('#areaId' + instId).combobox("loadData", []);--%>
        <%--}--%>
    <%--});--%>

    $('#countyId' + instId).combobox({
        onSelect: function (param) {

            address_province_id = param.value.split(address_split)[2];
            address_city_id = param.value.split(address_split)[3];
            $('#province' + instId).combobox('setValue', address_province_id);
            $('#cityId' + instId).combobox('setValue', address_city_id);
            if (iszip) {
                address_zip_county_code = param.value.split(address_split)[1];
                $('#zip' + instId).val(address_zip_county_code);
            }
            $("#areaId" + instId).combobox({
                url: '/common/area?countyId=' + param.countyId,
                valueField: 'areaId',
                textField: 'areaName',
                filter: function (q, row) {
                    return myAddressfilter(q, row);
                },
                onLoadSuccess: function () {
                    $(this).combobox('clear');
                }
            });
        }
    });

    $("#areaId" + instId).combobox({
        onSelect: function (param) {
            console.info("=======================");
            if (iszip) {
                address_zip_area_code = param.value.split(address_split)[1];
                if (address_zip_area_code == '') {
                    if(address_zip_county_code != '') $('#zip' + instId).val(address_zip_county_code);
                } else {
                    $('#zip' + instId).val(address_zip_area_code);
                }
            }

        }
    });

}

/**
 * 初始化四级地址
 * @param instId	实例ID
 * @param pv		省ID
 * @param citv		市ID
 * @param countv	乡镇ID
 * @param av		区域ID
 */
function addressInit(instId, pv, citv, countv, av) {
	
	$('#province'+instId).combobox({
		url : '/common/province',
		valueField : 'provinceId',
		textField : 'provinceName',
		filter: function(q, row){
    		return myAddressfilter(q,row);
		},
        onSelect : function(param) {
            console.info("===-------------====");
            $("#cityId"+instId).combobox({
                url : '/common/city?provinceId=' + param.provinceId,
                valueField : 'cityId',
                textField : 'cityName',
                filter: function(q, row){
                    return myAddressfilter(q,row);
                },
                onLoadSuccess:function(){
                    $("#cityId"+instId).combobox('clear');
                    //citv='';
                }

            });
            console.info("+++++++++++++++++++++++++++++++++");
            $('#countyId'+instId).combobox("loadData", []);
            $('#countyId'+instId).combobox('clear');
            $('#areaId'+instId).combobox("loadData", []);
            $('#areaId'+instId).combobox('clear');

            if(iszip){
                $('#zip').val('');
            }
        },

		onLoadSuccess:function(){
			$("#province"+instId).combobox('setValue', pv);
			//pv='';
		}
	});
	
	$("#cityId"+instId).combobox({
		url : '/common/city?provinceId=' + pv,
		valueField : 'cityId',
		textField : 'cityName',
		filter: function(q, row){
    		return myAddressfilter(q,row);
		},
        onSelect : function(param) {
            console.info("===-------------====");
            address_province_id = param.value.split(address_split)[2];

            $('#province'+instId).combobox('setValue', address_province_id);
            $("#countyId"+instId).combobox({
                url : '/common/county?cityId=' + param.cityId,
                valueField : 'countyId',
                textField : 'countyName',
                filter: function(q, row){
                    return myAddressfilter(q,row);
                },
                onLoadSuccess: function(){
                    $(this).combobox('clear');
                }

            });

            $('#areaId'+instId).combobox('setValue',"");
            $('#areaId'+instId).combobox("loadData", []);
        },
		onLoadSuccess:function(){
			$("#cityId"+instId).combobox('setValue', citv);
			//citv='';
		}
	});

	$("#countyId"+instId).combobox({
		url : '/common/county?cityId=' + citv,
		valueField : 'countyId',
		textField : 'countyName',
		filter: function(q, row){
    		return myAddressfilter(q,row);
		},
        onSelect : function(param) {
            console.info("===-------------====");
            address_province_id = param.value.split(address_split)[2];
            address_city_id = param.value.split(address_split)[3];
            $('#province'+instId).combobox('setValue', address_province_id);
            $('#cityId'+instId).combobox('setValue', address_city_id);
            if(iszip){
                address_zip_county_code = param.value.split(address_split)[1];
                $('#zip'+instId).val(address_zip_county_code);
            }
            $("#areaId"+instId).combobox({
                url : '/common/area?countyId=' + param.countyId,
                valueField : 'areaId',
                textField : 'areaName',
                filter: function(q, row){
                    return myAddressfilter(q,row);
                },
                onLoadSuccess: function(){
                    $(this).combobox('clear');
                }
            });
        },
		onLoadSuccess:function(){
			$("#countyId"+instId).combobox('setValue', countv);
			//countv='';
		}
	});
	
	$("#areaId"+instId).combobox({
		url : '/common/area?countyId=' + countv,
		valueField : 'areaId',
		textField : 'areaName',
		filter: function(q, row){
    		return myAddressfilter(q,row);
		},
		onLoadSuccess:function(){
			$("#areaId"+instId).combobox('setValue', av);
			av='';
            pv='';
            citv='';
            countv='';
		}
	});	
}

function myAddressfilter(q,row){
	   q= q.toUpperCase();
	   var searchvar=row.value.split(address_split)[0]; 
		return searchvar.indexOf(q) >=0;
}

function cancleAddressItem(){
    $('#province${instId}').combobox({
        url : '/common/province',
        onSelect : function(param) {

            $("#cityId${instId}").combobox({
                url : '/common/city?provinceId=' + param.provinceId,
                valueField : 'cityId',
                textField : 'cityName',
                filter: function(q, row){
                    return myAddressfilter(q,row);
                }
            });

            $('#countyId${instId}').combobox("loadData", []);
            $('#countyId${instId}').combobox('clear');
            $('#areaId${instId}').combobox("loadData", []);
            $('#areaId${instId}').combobox('clear');

            if(iszip){
                $('#zip').val('');
            }
        } ,
        onChange:function(){
            if(!$('#province${instId}').combobox('getValue') && !$('#cityId${instId}').combobox('getValue')) {
                cleanAddressItem('${instId}');
            }
        }

    });

	$('#cityId${instId}').combobox({
		//url : '/common/cityAll',
        url:'/static/plugin/city.json',
		valueField : 'cityId',
		textField : 'cityName',
		filter: function(q, row){
			return myAddressfilter(q,row);
		}
	});
	$('#countyId${instId}').combobox({
		//url : '/common/countyAll',
        url:'/static/plugin/country.json',
		valueField : 'countyId',
		textField : 'countyName',
		filter: function(q, row){
			return myAddressfilter(q,row);
		}
	});
	 $('#areaId${instId}').combobox("loadData", []);
}

function cleanAddressItem(instId){
    $('#cityId'+instId).combobox({
       // url : '/common/cityAll',
        url:'/static/plugin/city.json',
        valueField : 'cityId',
        textField : 'cityName',
        filter: function(q, row){
            return myAddressfilter(q,row);
        }
    });
    $('#countyId'+instId).combobox({
        //url : '/common/countyAll',
        url:'/static/plugin/country.json',
        valueField : 'countyId',
        textField : 'countyName',
        filter: function(q, row){
            return myAddressfilter(q,row);
        }
    });
    $('#areaId'+instId).combobox("loadData", []);
}

function formatItemCity(row){
    return row.areaCode+"|"+row.cityName;

}

function formatItemCounty(row){
    return row.areaCode+"|"+row.countyName;
}
$(function(){
    $('#province').combobox('textbox').attr('tabindex',7);
    $('#cityId').combobox('textbox').attr('tabindex',8);
    $('#countyId').combobox('textbox').attr('tabindex',9);
    $('#areaId').combobox('textbox').attr('tabindex',10);
});
</script>
<input class="easyui-combobox" id="province${instId}"  class="province" name="province${instId}"  data-options="required:true" style="width:100px;" /> &nbsp;
<input class="easyui-combobox" id="cityId${instId}"  class="cityId" name="cityId${instId}"  data-options="formatter:formatItemCity,required:true"/>&nbsp;
<input class="easyui-combobox" id="countyId${instId}"  class="countyId" name="countyId${instId}"  data-options="formatter:formatItemCounty,required:true"/>&nbsp;
<input class="easyui-combobox" id="areaId${instId}"  class="areaId" name="areaId${instId}" data-options="required:true"/>

