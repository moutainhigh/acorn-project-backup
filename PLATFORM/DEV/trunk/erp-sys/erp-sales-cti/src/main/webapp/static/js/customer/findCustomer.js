
var old_customerId="",old_customerType="";
var taskId;

function findCustomer(){
    var queryPhoneNum = $("#phone").val();
    if ('queryByPhone'==_source) $("#phone").val('');
	 $('#defaultTable').datagrid({
	        title:'',
	        iconCls:'icon-edit',
	        height: 200,
	        nowrap: false,
	        striped: true,
	        border: true,
	        collapsible:false,
	        fitColumns:true,
	        scrollbarSize:-1,
	        url:'/customer/mycustomer/find',
	        idField:'contactid',
	        sortName: 'crdt',
	        sortOrder: 'desc',
	        columns:[[
	                  {field:'customerId',title:'客户编号',align:'center',width:100},
		              {field:'name',title:'客户名称',align:'center',width:80},
		              {field:'level',title:'会员等级',align:'center',width:80},
		              {field:'address',title:'客户地址',width:200},
                      {field:'detailedAddress',title:'详细地址',width:100,formatter:function(value, rowData, rowIndex){
                          if(value == null || value == '') return "";
                          return "<div unselectable='on' onselectstart='return false;' style='-moz-user-select:none;'><marquee scrollamount='3'>"+value+"</marquee></div>";
                      }},
		              {field:'crusr',title:'创建人',align:'center',width:80},
		              {field:'crdt',title:'创建时间',width:140},
                      {field:'sex',title:'性别',width:100,hidden:true},
		              {field:'addressid',title:'地址编号',width:100,hidden:true},

		              {field:'customerType',title:'客户类别',width:100,
		            	  formatter:function(value, rowData, rowIndex){
		            		  if(value=="2"){
		            			  return "潜在客户";
		            		  }else if(value=="1"){
		            			  return  "正式客户";
		            		  }
		                       
		             }
		            	  }
	        ]],
	        remoteSort:false,
	        singleSelect:false,
	        pagination:true,
	        queryParams: {
	    	phone:queryPhoneNum,
			name:$("#name").val(),
			province:$("#province").combobox('getValue'),
			cityId:$("#cityId").combobox('getValue'),
			countyId:$("#countyId").combobox('getValue'),
			areaId:$("#areaId").combobox('getValue')
	    },
        onDblClickRow:function(index,row){
            if(row){
	    		if(typeof(_source) == "undefined")
	    		{
	    			gotoInfoCustomer(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name);	
	    		}else if(_source=="shoppingCart"){
	    			//saveFindToSession(row.customerId);
                    subCallback(_callBackframeid,_callbackMethod,row.customerId,typeof(row.name) == "undefined"?row.customerId:row.name);
	    		}else if(_source=="editOrder"){
	    			subCallback(_callBackframeid,_callbackMethod,row.customerId,typeof(row.name) == "undefined"?row.customerId:row.name);
	    		}else if(_source=="createComplain"){
                    if (row.customerType == "1") subCallback(_callBackframeid,_callbackMethod,row.customerId,typeof(row.name) == "undefined"?row.customerId:row.name);
                }else if(_source=="incomingCall"){
	    			isCloseFindC=false;
	    			phone.customerType=row.customerType;
	    			phone.customerId=row.customerId;

	    			subCallback(_callBackframeid,_callbackMethod,row.customerId);
                    if($("#callback").val()==1){
                        gotoInfoCallBack(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name,false);
                    }else{
                        gotoInfoCustomer(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name,false);
                    }

	    		}else if(_source=="changeCC"){
                    if(old_customerId ==row.customerId && old_customerType==row.customerType){
                       alertWin("系统提示","请选择不同的联系人");
                    }else{
                        phone.customerType=row.customerType;
                        phone.customerId=row.customerId;
                        closeAllTabs();
                        gotoInfoCustomer(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name,false);
                        subCallback(_callBackframeid,_callbackMethod);
                    }
                }else if(_source=="callback"){
                    subCallback(_callBackframeid,_callbackMethod,row.customerId,row.customerType,row.name,row.sex);
                }else if(_source='outCall') {
                    phone.customerType=row.customerType;
                    phone.customerId=row.customerId;

                	//绑定任务与客户ID
                	gotoInfoCustomer(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name);
                	var isPotential = true;
                	if(row.customerType=="2") {
                		isPotential = false;
                	}
                	relateContactWithTask(row.customerId, taskId, isPotential);
           		 	taskId="";
           		    _source = "";
                }
	    		$('#queryC').window('close');
                  //subCallback(_frameid,_callbackMethod);
	        }
        }
	   	        
	    });

	    var p = $('#defaultTable').datagrid('getPager');
	    $(p).pagination({
	        pageSize: 10,
	        pageList: [5,10,15],
	        beforePageText: '第',
	        afterPageText: '页    共 {pages} 页',
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
	    onBeforeRefresh:function(){
	        $(this).pagination('loading');
	        $(this).pagination('loaded');
	        }
	    });
//	$('#defaultTable').datagrid('load',{
//		phone:$("#phone").val(),
//		name:$("#name").val(),
//		province:$("#province").combobox('getValue'),
//		cityId:$("#cityId").combobox('getValue'),
//		countyId:$("#countyId").combobox('getValue'),
//		areaId:$("#areaId").combobox('getValue')
//});
}

function cancleCustomer(){
	$("#phone").attr("value",'');
	$("#name").attr("value",'');
    //清空地址组建
    cancleAddressItem();
	$('#defaultTable').datagrid('load');
}

function addCustomer(){
	$('#queryC').window('close');
	gotoAddCustomer();
}

function findCustomerm(){
	
	 $('#defaultTablem').datagrid({
	        title:'',
	        iconCls:'icon-edit',
	        height: 292,
	        nowrap: true,
	        striped: true,
	        border: false,
	        collapsible:false,
	        fitColumns:true,
            scrollbarSize:-1,
	        url:'/customer/mycustomer/more/find' ,
	        idField:'customerId',
	        sortName: 'crdt',
	        sortOrder: 'desc',
	        columns:[[
	                  {field:'customerId',title:'客户编号',width:100},  
		              {field:'name',title:'客户名称',width:80},
		              {field:'level',title:'会员等级',width:80,align:'right'},
                      {field:'address',title:'客户地址',width:200,align:'right'},
		              {field:'detailedAddress',title:'详细地址',width:100,align:'right',formatter:function(value, rowData, rowIndex){
                          if(value == null || value == '') return "";
		            	  return "<div unselectable='on' onselectstart='return false;' style='-moz-user-select:none;'><marquee scrollamount='3'>"+value+"</marquee></div>";
	                  }},
		              {field:'crusr',title:'创建人',width:100,align:'right'},
		              {field:'crdt',title:'创建时间',width:140,align:'right'},
                      {field:'sex',title:'性别',width:100,hidden:true},
		              {field:'addressid',title:'地址编号',width:100,hidden:true},
		              {field:'customerType',title:'客户类别',width:100,
		            	  formatter:function(value, rowData, rowIndex){
		            		  if(value=="2"){
		            			  return "潜在客户";
		            		  }else if(value="1"){
		            			  return  "正式客户";
		            		  }
		                       
		             }
		            	  }
	        ]],
	        remoteSort:false,
	        singleSelect:false,
	        pagination:true,
	       queryParams: {
	    	   typeValue: $("#typeValue").val(),
	    	   findValue:$.trim($("#findValue").val())
	    },
        onDblClickRow:function(index,row){
	    	if(row){
	    		if(typeof(_source) == "undefined")
	    		{
	    			
	    			gotoInfoCustomer(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name);	
	    		}else if(_source=="shoppingCart"){
	    		//	saveFindToSession(row.customerId);
                    subCallback(_callBackframeid,_callbackMethod,row.customerId,typeof(row.name) == "undefined"?row.customerId:row.name);
	    		}else if(_source=="editOrder"){
	    			subCallback(_callBackframeid,_callbackMethod,row.customerId,typeof(row.name) == "undefined"?row.customerId:row.name);
	    		}else if(_source=="incomingCall"){
	    			isCloseFindC=false;
	    			phone.customerType=row.customerType;
	    			phone.customerId=row.customerId;
                     if(old_customerId)
	    			subCallback(_callBackframeid,_callbackMethod,row.customerId);
                    if($("#callback").val()==1){
                        gotoInfoCallBack(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name,false);
                    }else{
                        gotoInfoCustomer(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name,false);
                    }
                }else if(_source=="createComplain"){
                    if (row.customerType == "1") subCallback(_callBackframeid,_callbackMethod,row.customerId,typeof(row.name) == "undefined"?row.customerId:row.name);
                }else if(_source=="changeCC"){
                    phone.customerType=row.customerType;
                    phone.customerId=row.customerId;
                    closeAllTabs();
                    gotoInfoCustomer(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name,false);
                    subCallback(_callBackframeid,_callbackMethod);
                }else if(_source=="callback"){
                    subCallback(_callBackframeid,_callbackMethod,row.customerId,row.customerType,row.name,row.sex);
                }else if(_source='outCall') {
                    phone.customerType=row.customerType;
                    phone.customerId=row.customerId;

                	//绑定任务与客户ID
                	gotoInfoCustomer(row.customerId,row.customerType,typeof(row.name) == "undefined"?row.customerId:row.name);
                	var isPotential = true;
                	if(row.customerType=="2") {
                		isPotential = false;
                	}
                	relateContactWithTask(row.customerId, taskId, isPotential);
           		 	taskId="";
                }
	    		$('#queryC').window('close');
	        }
        }
	   	        
	    });

	    var p = $('#defaultTablem').datagrid('getPager');
	    $(p).pagination({
	        pageSize: 10,
	        pageList: [5,10,15],
	        beforePageText: '第',
	        afterPageText: '页    共 {pages} 页',
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
	    onBeforeRefresh:function(){
	        $(this).pagination('loading');
	        $(this).pagination('loaded');
	        }
	    });
//	$('#defaultTablem').datagrid('load',{
//		customerId:$("#customerId").val(),
//		shipmentId:$("#shipmentId").val(),
//		orderId:$("#orderId").val()
//	});
}

function cancleCustomerm(){
	$("#findValue").attr("value",'');
	$('#defaultTablem').datagrid('load');
}

function addCustomerm(){
	$('#queryC').window('close');
	gotoAddCustomer();
}

function saveFindToSession(id){
	  $.post("/customer/mycustomer/save/find",{  
		     customerId:id
	  },function(data){
     window.parent.alertWin('系统提示',data.result);
 	$('#queryC').window('close');
		  });
	
}

//跳转到新增页面
function gotoAddCustomer(){
    if((_source=="incomingCall" && isCloseFindC) || _source == "callback"){
        //新建潜客并定位潜客
        $.post("/customer/mycustomer/potentialContact/add",{
            phn1:phone1,
            phn2:phone2,
            phn3:phone3,
            phonetype:phoneType
        },function(data){
            if(data.result){
                phone.customerId=data.potentialContactId;
                phone.customerType="2";
                //进入客户中心
                if($("#callback").val()==1){
                    gotoInfoCallBack(data.potentialContactId,2,phone.ani,false);
                }else{
                    gotoInfoCustomer(data.potentialContactId,2,phone.ani,false);
                }
                createRecommendTask(phone.dnsi,phone.ani,data.potentialContactId,"2");
            }else{
                window.parent.alertWin('系统提示', data.msg);
            }
        });
    } else{
        var id ="newCustomer";
        var url = "/inbound/inbound";
        var tabName ="新建客户";
        var multiTab = false;
        addTab(id,tabName,multiTab,url);
    }
	
}

//跳转到客户详细页面
function gotoInfoCustomer(customerId,customerType,name,isColseable){
	var url = "/contact/"+customerType+"/"+customerId+"?provid="+ $('#d_provid').val()+"&cityid="+$('#d_cityid').val();
	var tabName = name+"-详细信息";
	var multiTab = false;
	addTab(customerId,tabName,multiTab,url,null,isColseable);
    $('#d_provid').val("");
    $('#d_cityid').val("");
}

//跳转到CALLBACK页面
function gotoInfoCallBack(customerId,customerType,name,isColseable){
    var url = "/callback/callback/"+customerType+"/"+customerId;
    var tabName = "CallBack";
    var multiTab = false;
    //判断callbackTab是否打开

    var callbackTabObj = window.frames["tab_CallbackTab"];
    if(typeof(callbackTabObj) == "undefined"){
        addTab("CallbackTab",tabName,multiTab,url,null,isColseable);
    }else{
        destoryTab("CallbackTab");
        console.info("================================================");
        addTab("CallbackTab",tabName,multiTab,url,null,isColseable);
        console.info("==================================================++++");

    }
    //$('#d_provid').val("");
    //$('#d_cityid').val("");
}

//关联任务相关客户
function relateContactWithTask(customerId, taskId, isPotential) {
	 var url = "/task/updateContact?instId="+taskId+"&contactId="+customerId+"&isPotential="+isPotential;
		$.ajax({
			url : url
		});
	}

function callBack_product(){
//    window.parent.alertWin('系统提示', "123");

}




function cc_change(customerId,customerType){
    old_customerId=customerId;
    old_customerType=customerType;
    queryCustomer('','cc_change_callback','changeCC',phone.ani,true);
    findCustomer();

}




function cc_change_callback(){
    $.post("/common/changeCC",{
        customerId:phone.customerId,
        customerType:phone.customerType,
        instId:phone.instId
    },function(data){
       if(! data.result){
        //alertWin(data.msg);
       }
    });
}




