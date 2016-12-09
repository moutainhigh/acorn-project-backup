/**
 * 地址组件JS
 *
 * author:haoleitao
 * 
 * 
 */
//全局变量

var address_province_id=""; //省份ID
var address_zip_county_code=""; //邮政编码
var address_zip_area_code=""; //邮政编码
var address_split="-";  //地址value 分割符
var iszip= true;


    $("#province").combobox({  
        url:'/common/province',  
        valueField:'provinceId',  
        textField:'provinceName',
        	filter: function(q, row){
        		return myAddressfilter(q,row);
    		}
    });  
    
    $("#cityId").combobox({  
        url:'/common/cityAll',  
        valueField:'cityId',  
        textField:'cityName',
        	filter: function(q, row){
        		return myAddressfilter(q,row);
    		},
    	
    });  
    
    $('#province').combobox({
    	onSelect: function(param){
    		 $("#cityId").combobox({  
    		        url:'/common/city?provinceId='+param.provinceId,  
    		        valueField:'cityId',  
    		        textField:'cityName',
    		    	filter: function(q, row){
    		    		return myAddressfilter(q,row);
    	    		}
    		    });  
    		 
    		 
    		 $('#countyId').combobox("loadData", []);
    		 $('#countyId').combobox('clear');
    		 $('#areaId').combobox("loadData", []);
    		 $('#areaId').combobox('clear');
 
             if(iszip){
            	 $('#zip').val('');
             }
    	
    	}
    	
 
    });
    
    

    
    $('#cityId').combobox({
    	onSelect: function(param){
    		  address_province_id = param.value.split(address_split)[2];
    		  if(iszip){
    		  address_zip_county_code = param.value.split(address_split)[1];
    		  $('#zip').val(address_zip_county_code);
    		  }
    		 $('#province').combobox('setValue', address_province_id);
    		 $("#countyId").combobox({  
    		        url:'/common/county?cityId='+param.cityId,  
    		        valueField:'countyId',  
    		        textField:'countyName',
    		    	filter: function(q, row){
    		    		return myAddressfilter(q,row);
    	    		}
    		    });  
    		 $('#areaId').combobox('setValue',"");
    		 $('#areaId').combobox("loadData", []);
    	}
    });
    
    $('#countyId').combobox({
    	onSelect: function(param){
    		  if(iszip){
    		address_zip_county_code = param.value.split(address_split)[1];
    		 $('#zip').val(address_zip_county_code);
    		  }
    		 $("#areaId").combobox({  
    		        url:'/common/area?countyId='+param.countyId,  
    		        valueField:'areaId',  
    		        textField:'areaName',
    		    	filter: function(q, row){
    	    			return myAddressfilter(q,row);
    	    		}
    		    });  
    	}
    });

    
	 $("#areaId").combobox({  
		 onSelect: function(param){
			  if(iszip){
				 address_zip_area_code = param.value.split(address_split)[1];
				 if(address_zip_area_code==''){
					 $('#zip').val(address_zip_county_code);
				 }else{
					 $('#zip').val(address_zip_area_code);	 
				 }
			  }
    		 
 		}
	    });  
    
   function  AddressInitValue(pv,citv,countv,av){
 		 $("#cityId").combobox({  
		        url:'/common/city?provinceId='+pv,  
		        valueField:'cityId',  
		        textField:'cityName'  
		    });  
 		 
 		 $("#countyId").combobox({  
		        url:'/common/county?cityId='+citv,  
		        valueField:'countyId',  
		        textField:'countyName'  
		    });
 		 $("#areaId").combobox({  
		        url:'/common/area?countyId='+countv,  
		        valueField:'areaId',  
		        textField:'areaName'  
		    }); 
    }


var initAddress = function(provinceid, cityid, countyid, areaid) {
    $("#province").combobox('select', provinceid);
    $("#cityId").combobox('select',cityid);
    $("#countyId").combobox({
        url:'/common/county?cityId='+cityid,
        valueField:'countyid',
        textField:'countyname',
        onLoadSuccess:function() {
            $("#countyId").combobox('select',countyid);
        }
    });
    $("#areaId").combobox({
        url:'/common/area?countyId='+countyid,
        valueField:'areaid',
        textField:'areaname',
        onLoadSuccess:function() {
            $("#areaId").combobox('select',areaid);
        }
    });
}

   function myAddressfilter(q,row){
	   q= q.toUpperCase();
		return row.value.indexOf(q) >=0;
   }
   