//地址组件JS
var autoid="";
   if (typeof($("#addressAutoId").html()) != "undefined"){
	   autoid =$("#addressAutoId").html();
   }else{
	   autoid="";
   }

    $("#province"+autoid).combobox({  
        url:'/common/province',  
        valueField:'provinceid',  
        textField:'chinese'
    });  
    
    
    $('#province'+autoid).combobox({
    	onSelect: function(param){
    		 $("#cityId"+autoid).combobox({  
    		        url:'/common/city?provinceId='+param.provinceid,  
    		        valueField:'cityid',  
    		        textField:'cityname'  
    		    });  
    		   $('#countyId'+autoid).combobox('setValue',"");
    		   $('#areaId'+autoid).combobox('setValue',"");
    	}
 
    });
    
    $('#cityId'+autoid).combobox({
    	onSelect: function(param){
    		 $("#countyId"+autoid).combobox({  
    		        url:'/common/county?cityId='+param.cityid,  
    		        valueField:'countyid',  
    		        textField:'countyname'  
    		    });  
    		 $('#areaId'+autoid).combobox('setValue',"");
    	}
    });
    
    $('#countyId'+autoid).combobox({
    	onSelect: function(param){
    		 $("#areaId"+autoid).combobox({  
    		        url:'/common/area?countyId='+param.countyid,  
    		        valueField:'areaid',  
    		        textField:'areaname'  
    		    });  
    	}
    });
