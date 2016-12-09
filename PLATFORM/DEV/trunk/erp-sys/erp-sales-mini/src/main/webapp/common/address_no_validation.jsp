<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<% 
String instId = null==request.getParameter("instId") ? "" : request.getParameter("instId").toString();
request.setAttribute("instId", instId);
boolean iszip = null==request.getParameter("iszip") ? false : true;
request.setAttribute("iszip", iszip);

boolean isFormSubmit = null==request.getParameter("isFormSubmit") ? false : true;
request.setAttribute("isFormSubmit", isFormSubmit);
%>

<script type="text/javascript">

var address_province_id=""; //省份ID
var address_city_id="";//市ID
var address_zip_county_code=""; //邮政编码
var address_zip_area_code=""; //邮政编码
var address_split="-";  //地址value 分割符
var iszip= ${iszip};
var isFormSubmit=${isFormSubmit};


$('#province${instId}').combobox({
	url : '/common/province',
	valueField : 'provinceId',
	textField : 'provinceName',
	filter: function(q, row){
		return myAddressfilter(q,row);
	},
	onSelect : function(param) {
		$("#cityId${instId}").combobox({
			url : '/common/city?provinceId=' + param.provinceId,
			valueField : 'cityId',
			textField : 'cityName',
			filter: function(q, row){
				return myAddressfilter(q,row);
			},
			onLoadSuccess: function(){
				$(this).combobox('clear');
			}
		});
		
		 $('#countyId${instId}').combobox("loadData", []);
		 $('#countyId${instId}').combobox('clear');
		 $('#areaId${instId}').combobox("loadData", []);
		 $('#areaId${instId}').combobox('clear');

         if(iszip){
        	 $('#zip').val('');
         }
         
         findCustomerByAddress();
	}
});


if(!$.jStorage.get("city.json")){
    console.info("cityJson:++++++++++++++++++++");
    $.post("/static/plugin/city.json", function(data) {
        $.jStorage.set("city.json",data);
        $('#cityId${instId}').combobox({data:$.jStorage.get("city.json")});
    });

}else{
    console.info("cityJson:----------------------");
    $('#cityId${instId}').combobox({data:$.jStorage.get("city.json")});
}

if(!$.jStorage.get("county.json")){
    console.info("countyJson:++++++++++++++++++++");
    $.post("/static/plugin/country.json", function(data) {
        $.jStorage.set("county.json",data);
        $('#countyId${instId}').combobox({data:$.jStorage.get("county.json")});
    });

}else{
    console.info("countyJson:----------------------");
    $('#countyId${instId}').combobox({data:$.jStorage.get("county.json")});
}


$('#cityId${instId}').combobox({
    //url : '/common/cityAll',
   // url:'/static/plugin/city.json',
    valueField : 'cityId',
    textField : 'cityName',
    filter: function(q, row){
        return myAddressfilter(q,row);
    },
    onLoadSuccess: function(){
        $(this).combobox('clear');
    }
});



$('#countyId${instId}').combobox({
   // url:'/static/plugin/country.json',
	//url : '/common/countyAll',
	valueField : 'countyId',
	textField : 'countyName',
	filter: function(q, row){
		return myAddressfilter(q,row);
	}
});

$('#cityId${instId}').combobox({
	onSelect : function(param) {
		 address_province_id = param.value.split(address_split)[2];
		  if(iszip){
		  address_zip_county_code = param.value.split(address_split)[1];
		  $('#zip').val(address_zip_county_code);
		  }
		 $('#province${instId}').combobox('setValue', address_province_id);
		$("#countyId${instId}").combobox({
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
		 $('#areaId${instId}').combobox('setValue',"");
		 $('#areaId${instId}').combobox("loadData", []);
		 
		 findCustomerByAddress();
	}
});

$('#countyId${instId}').combobox({
	onSelect : function(param) {
		
		  address_province_id = param.value.split(address_split)[2];
		  address_city_id = param.value.split(address_split)[3];
			 $('#province${instId}').combobox('setValue', address_province_id);
			 $('#cityId${instId}').combobox('setValue', address_city_id);
		  if(iszip){
	    		address_zip_county_code = param.value.split(address_split)[1];
	    		 $('#zip').val(address_zip_county_code);
	      }
		$("#areaId${instId}").combobox({
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
		
		findCustomerByAddress();
	}
});



$("#areaId${instId}").combobox({  
	 onSelect: function(param){
		  if(iszip){
			 address_zip_area_code = param.value.split(address_split)[1];
			 if(address_zip_area_code==''){
                 if(address_zip_county_code != '') $('#zip').val(address_zip_county_code);
			 }else{
				 $('#zip').val(address_zip_area_code);	 
			 }
		  }
		  
		  findCustomerByAddress();
	}
   });  
   



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
		return row.value.indexOf(q) >=0;
}



function findCustomerByAddress(){
	if(isFormSubmit){
		 var v_name=$("#name").val();
		  var v_phone=$("#phone").val();
		if(v_name.trim() !="" || v_phone.trim() != ""){
			findCustomer();
		}
	}
		
}

function myAddressfilter(q,row){
	   q= q.toUpperCase();
	   var searchvar=row.value.split(address_split)[0]; 
	   findCustomerByAddress();
	   return searchvar.indexOf(q) >=0;
}


function cancleAddressItem(){
    if(!$.jStorage.get("city.json")){
        console.info("cityJson:++++++++++++++++++++");
        $.post("/static/plugin/city.json", function(data) {
            $.jStorage.set("city.json",data);
            $('#cityId${instId}').combobox({data:$.jStorage.get("city.json")});
        });

    }else{
        console.info("cityJson:----------------------");
        $('#cityId${instId}').combobox({data:$.jStorage.get("city.json")});
    }


    if(!$.jStorage.get("county.json")){
        console.info("countyJson:++++++++++++++++++++");
        $.post("/static/plugin/country.json", function(data) {
            $.jStorage.set("county.json",data);
            $('#countyId${instId}').combobox({data:$.jStorage.get("county.json")});
        });

    }else{
        console.info("countyJson:----------------------");
        $('#countyId${instId}').combobox({data:$.jStorage.get("county.json")});
    }



    $('#cityId${instId}').combobox({
		//url : '/common/cityAll',
        //url:'/static/plugin/city.json',
		valueField : 'cityId',
		textField : 'cityName',
		filter: function(q, row){
			return myAddressfilter(q,row);
		}
	});


	$('#countyId${instId}').combobox({
		//url : '/common/countyAll',
        //url:'/static/plugin/country.json',
		valueField : 'countyId',
		textField : 'countyName',
		filter: function(q, row){
			return myAddressfilter(q,row);
		}
	});

     $('#countyId${instId}').combobox('setValue','');
     $('#cityId${instId}').combobox('setValue','');
     $('#province${instId}').combobox('setValue','');
	 $('#areaId${instId}').combobox("loadData", []);
	 
	
}

function formatItemCity(row){
    return row.areaCode+"|"+row.cityName;

}

function formatItemCounty(row){
    return row.areaCode+"|"+row.countyName;
}

</script>
<input id="province${instId}" name="province${instId}" class="province"  style="width:100px;" /> &nbsp;
<input id="cityId${instId}" name="cityId${instId}"  class="cityId" data-options="formatter:formatItemCity" />&nbsp;
<input id="countyId${instId}" name="countyId${instId}" class="countyId" data-options="formatter:formatItemCounty" />&nbsp;
<input id="areaId${instId}"  name="areaId${instId}" class="areaId" />
