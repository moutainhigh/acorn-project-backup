$(document).ready(function() {
    $(function () {
       //$('#beginDate').datepicker({dateFormat:"yy-mm-dd"});
       //$('#endDate').datepicker({dateFormat:"yy-mm-dd"}); 
  	  var boarddiv = "";
    $('#dataTable').edatagrid({
        title:'',
        iconCls:'icon-edit',
        width:'100%',
        height: 390,
        nowrap: true,
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        scrollbarSize:0,
        url:'/orderPreprocessing/list' ,
        //url:'/orderPreprocessing/test',
        //updateUrl: '/orderPreprocessing/update',
        idField:'id',
        sortName: 'outCrdt',
        sortOrder: 'desc',
        columns:[[//订单当前状态、来源店铺/付款时间、商品、订单金额、客户信息（包含名字、电话、地址）、发货信息、审核反馈原因、卖家备注、买家备注、网上单号，橡果单号。
  
            {field:'orderState',title:'订单状态',width:60, 
                formatter:function(val,rec){
                	var impState = val.split(",")[0];
                	var freebackState = val.split(",")[1];
                    if (impState == 0 || impState == ""    ){
                        return "<font color='#00CCFF'>待审核</font>" ;
                    }else if((impState == 13 || impState == 14 || impState == 17) && (freebackState == 0  || freebackState == "")){
                        return "<font color='#33FF00'>已审核待发货</font>";
                    }else if(impState != 13 && impState != 14 && impState != 17 && impState !=99  && freebackState !=2 && freebackState !=4){
                    	//alert(impState != 13 || impState != 14);
                    	//return impState+","+freebackState;
                        return "<font color='#CC3300'>审核失败</font>";
                    }else if(impState ==99){
                        return "<font color='#666699'>前置压单</font>";
                    }else if(freebackState ==2){
                        return "<font color='#009966'>标记完成</font>";
                    }else if(freebackState ==4){
                    	return "<font color='#FF9966'>反馈失败</font>";
                    }else{
                    	return impState+","+freebackState;
                    	//return "";
                    }
                }
            	},            	            	
              {field:'refundStatus',title:'退款状态',width:58, 
        		formatter:function(val,rec){
                	if(jQuery.type(val) == "undefined"){
                	    return "<font color='#68228B'>无申请</font>";
                	}else{
                		return "<font color='#68228B'>有申请</font>";
                	}
            	}
        	  },
              {field:'refundStatusConfirm',title:'退款处理',width:58, 
          		formatter:function(val,rec){
                  	if(val == 1){
                  	    return "<font color='#68228B'>允许发货</font>";
                  	}else if(val==2){
                  		return "<font color='#68228B'>取消发货</font>";
                  	}else{
                  		return "未操作";
                  	}
              	}
          	  },

            	{field:'outCrdt',title:'付款时间',width:74},
                {field:'info',title:'商品信息',width:68,
                    formatter:function(val,rec){
                    	if(val===undefined) return val;
                    	if( val.length >4){
                    	      return val.substr(0,4)+"...";	
                    	}else{
                    		 return val;
                    	}
                    }
            	},
            	{field:'remark',title:'发票需求',width:90},
                {field:'orderMoney',title:'订单金额',width:90},
                {field:'nameAndPhone',title:'客户信息',width:158},
                {field:'sendInfo',title:'发货信息',width:100,
                	formatter:function(val,rec){
                		if(val===undefined) return val;
                	if( val.length >10){
              	      return val.substr(0,10)+"...";	
              	}else{
              		 return val;
              	}
              }},
            {field:'impStatusRemark',title:'审核反馈原因',width:83},
            {field:'sellerMessage',title:'卖家备注',width:52,
            	formatter:function(val,rec){
            		if(val===undefined) return val;
	                if(val.length >10){
	              	      return val.substr(0,10)+"...";	
	              	}else{
	              		 return val;
	              	}
                }
            },
              	{field:'buyerMessage',title:'买家备注',width:52,
              		formatter:function(val,rec){
              			if(val===undefined) return val;
		            	if(val.length >10){
		            	      return val.substr(0,10)+"...";	
		            	}else{
		            		 return val;
		            	}
            	}
            },
            {field:'tradeId',title:'网上单号',width:130},
            {field:'shipmentId',title:'橡果单号',width:103},
            {field:'treadTypeName',title:'订单类型',width:103},
            {field:'alipayTradeId',title:'',width:0,hidden:true},
            {field:'shippingFee',title:'',width:0,hidden:true},
            {field:'receiverMobile',title:'',width:0,hidden:true},
            {field:'receiverName',title:'客户信息',width:'auto',hidden:true},
            {field:'tmsCode',title:'',width:0,hidden:true},
            {field:'tradeFrom',title:'',width:0,hidden:true},
            {field:'tmsCode',title:'',width:0,hidden:true},
            {field:'isVaid',title:'',width:0,hidden:true},
            {field:'invoiceComment',title:'',width:0,hidden:true},
            {field:'invoiceTitle',title:'',width:0,hidden:true},
            {field:'id',title:'',width:0,hidden:true},
            {field:'receiverCounty',title:'',width:0,hidden:true},
            {field:'receiverProvince',title:'',width:0,hidden:true},
            {field:'receiverCity',title:'',width:0,hidden:true},
            {field:'receiverAddress',title:'',width:0,hidden:true}
        ]],
        remoteSort:false,
        idField:'tradeId',
        singleSelect:false,
        pagination:true,
        rownumbers:true,
        frozenColumns:[[
        {field:'ck',checkbox:true}
    ]],
    queryParams: {
    	alipayTradeId:$('#alipayTradeId').val(),
    	from:$('#from').val(),
    	tradeId: $("#tradeId").val(),
    	tradeFrom: $("#tradeFrom").val(),
    	shipmentId: $("#shipmentId").val(),
        beginDate: $("#beginDate").datebox('getValue'),
        endDate: $("#endDate").datebox('getValue'),
        min: $("#min").val(),
        max: $("#max").val(),
        receiverMobile: $("#receiverMobile").val(),
        buyerMessage: $("#buyerMessage").attr("checked"),
        sellerMessage: $("#sellerMessage").attr("checked"),
        isCombine: $("#isCombine").attr("checked"),
        state: $('#state').val(),
        refundStatus:$('#refundStatus').val(),
        refundStatusConfirm:$('#refundStatusConfirm').val(),
        treadType:$("#treadType").val()
        //state: $('input[name=state][checked]').val()
        },
        
        mouseoverCell: function(index,field,value,e){
        	if(typeof value !="undefined"){
        	     if((field=="info" && value.length>8) || (field=="sendInfo" && value.length >8) || (field=="sellerMessage" && value.length>8) || (field=="buyerMessage" && value.length>8) || (field=="skuProps" && value.length>4)||  (field=="shipmentId" && value.length>10)){
        	        
        	        	  //obj = e.srcElement?e.srcElement:e.target;
        	        	  //if(obj.tagName=="DIV"){obj=obj.parentNode;}
        	        	  
        	        	  var x = e.pageX-50;
        	              var y = e.pageY-80;
        	             // obj.setAttribute('title',value);
        	              
        	        	  if(boarddiv == ""){
        	        		boarddiv = "<div id='mydiv' style='left:"+x+";top:"+ y+";'>"+value+"</div>"; 
        	          	    $(document.body).append(boarddiv);
        	        	  }else{
        	        		  $("#mydiv").fadeIn(500);
        	        		  //$("#mydiv").css("display","block");
        	        		  $("#mydiv").html(value);
        	        		  $("#mydiv").css("left",x);
        	        		  $("#mydiv").css("top",y);
        	        		 // boarddiv = "<div id='mydiv' style='background:white;width:100;height:100;z-index:999;position:absolute;display:block;left:"+e.pageX+";top:"+ e.pageY+";margin-top:100px;'>"+value+"</div>"; 
        	        	   // $(document.body).append(boarddiv);
        	        	  }
        	        	  //alert(value);
        	        	  
        	          
        	    		}else{
        	            	//  if(boarddiv != "")
        	      		  //$("#mydiv").css("display","none");
        	      	  $("#mydiv").fadeOut(500);
        	        }
        	    	}else{
        	        	  $("#mydiv").fadeOut(500);
        	          }
        }
   
        
    });


    
    

    var p = $('#dataTable').datagrid('getPager');
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

    });
    
    
    $('#v_invoiceComment').combobox({
    	onSelect: function(){
    		var value = $('#v_invoiceComment').combobox('getValue');
    		if( value > 0){
    			 $('#v_invoiceTitle').validatebox({ required: true });  
    		}else{
    			 $('#v_invoiceTitle').validatebox({ required: false });
    		}   
    	}
    });
    
    $('#v_receiverMobile_mask').keydown(function(event) {
    	//46=delete 8==backspace
    	if(event.keyCode == 46 || event.keyCode == 8) {
    		$('#v_receiverMobile_mask').val('');
    		$('#v_receiverMobile').val('');
    	}
    });
    
    $('#v_receiverPhone_mask').keydown(function(event) {
    	//46=delete 8==backspace
    	if(event.keyCode == 46 || event.keyCode == 8) {
    		$('#v_receiverPhone_mask').val('');
    		$('#v_receiverPhone').val('');
    	}
    });
    
    $('#v_receiverMobile_mask').keypress(function() {
    	val = $(this).val();
    	$('#v_receiverMobile').val(eraseSpace(val));
    });
    
    $('#v_receiverMobile_mask').change(function() {
    	val = $(this).val();
    	$('#v_receiverMobile').val(eraseSpace(val));
    });
    
    $('#v_receiverPhone_mask').keypress(function() {
    	val = $(this).val();
    	$('#v_receiverPhone').val(eraseSpace(val));
    });
    
    $('#v_receiverPhone_mask').change(function() {
    	val = $(this).val();
    	$('#v_receiverPhone').val(eraseSpace(val));
    });
    

    //编辑
    $("#editRow").click(function(){
     	var boarddiv = "";
    	//清除相关编辑信息
    	$('#v_invoiceTitle').val("");
        $('#s1').val("");
        $('#s2').val("");
        $('#s3').val("");
        $('#v_remark1').text("");
        $('#v_remark2').text("");
        $('#v_receiverName').val("");
        $('#v_receiverMobile').val("");
        $('#v_receiverPhone').val("");
        $('#v_address').val("");
        
        var row = $('#dataTable').edatagrid('getChecked')[0];


        if (row){
        	if( row.impStatus != 13 && row.impStatus != 14 && row.impStatus != 17 ){
                if (typeof(row.invoiceComment) == "undefined")
                {
               	 $('#v_invoiceComment').combobox('setValue', "");
                }else{
               	 $('#v_invoiceComment').combobox('setValue',row.invoiceComment);
                }		

            	console.log($('#v_invoiceComment').combobox('getValue'));
            $('#v_tradeId').text(row.tradeId);
            $('#v_id').val(row.id);
            $('#v_payment').text(row.payment);
            $('#v_shippingFee').text(row.shippingFee);
            $('#v_remark1').text(row.sellerMessage);
            $('#v_remark2').text(row.buyerMessage);
            $('#v_address').val(row.receiverAddress);
            $('#v_receiverName').val(row.receiverName);
            
            $('#v_receiverMobile').val(row.receiverMobile);
            maskMobileNo = maskPhone(row.receiverMobile);
            $('#v_receiverMobile_mask').val(maskMobileNo);
            
            $('#v_receiverPhone').val(row.receiverPhone);
            maskPhoneNo = maskPhone(row.receiverPhone);
            $('#v_receiverPhone_mask').val(maskPhoneNo);
            
            $('#v_invoiceTitle').val(row.invoiceTitle);
            
            //初始化
            $('#v_shippingType').combobox({  
                url:'/orderPreprocessing/getcompany',  
                valueField:'companyid',  
                textField:'name'  
            });  
            $('#v_shippingType').combobox('setValue', row.tmsCode);
            $('#s1').val(row.receiverProvince);
            $('#s2').val(row.receiverCity);
            $('#s3').val(row.receiverCounty);
            $('#v_address').val(row.receiverAddress);
            $('#pretradeDetail').datagrid({       
            	  height:79,
                  url:'/orderPreprocessing/getDetails?preTradeID='+row.tradeId,
                  method:'get',
                  columns:[[                               
                      {field:'tradeId',title:'订单号',width:120},        
                      {field:'outSkuId',title:'橡果编号',width:120},   
                      {field:'skuName',title:'商品名称',width:80,
                    		formatter:function(val,rec){
            	            	if(val.length >6){
            	            	      return val.substr(0,6)+"...";
            	            	}else{
            	            		 return val;
            	            	}
                    	}},
                      {field:'qty',title:'商品数量',width:80},
                      {field:'promotionResultId',title:'促销类型',width:100,
                  		formatter:function(val,rec){
                  			return val;
                  			}
                  		},
                      {field:'upPrice',title:'商品单价',width:100},
                      {field:'payment',title:'合计金额',width:100}
                 ]],
                 mouseoverCell: function(index,field,value,e){
                	
                  	if(typeof value !="undefined"){
                  	     if(field=="skuName" && value.length>8){
                  	        	  //obj = e.srcElement?e.srcElement:e.target;
                  	        	  //if(obj.tagName=="DIV"){obj=obj.parentNode;}
                  	        	  
                  	        	  var x = e.pageX-50;
                  	              var y = e.pageY-80;
                  	             // obj.setAttribute('title',value);
                  	        	  if(boarddiv == ""){
                  	        		boarddiv = "<div id='mydiv2' style='left:"+x+";top:"+ y+";position:absolute;z-index:9020; '>"+value+"</div>";
                  	          	    $(document.body).append(boarddiv);
                  	        	  }else{
                  	        		  $("#mydiv2").fadeIn(500);
                  	        		  //$("#mydiv").css("display","block");
                  	        		  $("#mydiv2").html(value);
                  	        		  $("#mydiv2").css("left",x);
                  	        		  $("#mydiv2").css("top",y);
                  	        		 // boarddiv = "<div id='mydiv' style='background:white;width:100;height:100;z-index:999;position:absolute;display:block;left:"+e.pageX+";top:"+ e.pageY+";margin-top:100px;'>"+value+"</div>"; 
                  	        	   // $(document.body).append(boarddiv);
                  	        	  }
                  	        	  //alert(value);
                  	        	  
                  	          
                  	    		}else{
                  	    			
                  	           if(boarddiv != ""){ 
                  	        	 
                  	        	 $("#mydiv2").fadeOut(500);
                  	           }
                  	      	  
                  	      	  
                  	        }
                  	    		}else{
                  	    		  $("#mydiv2").css("display","none");
                  	        	  $("#mydiv2").fadeOut(500);
                  	          }
                  }
              });  
           
            // alert(row.receiverProvince+","+row.receiverCity+","+row.receiverCounty);
           //  setup(row.receiverProvince,row.receiverCity,row.receiverCounty);
             $('#s1').val(row.receiverProvince);
             $('#s2').val(row.receiverCity);  
             $('#s3').val(row.receiverCounty);
       
             //new PCAS("province3","city3","area3");
            $('#dlg').dialog({'title':'编辑数据'}).dialog('open');
        	}else{ 
        		alert("此订单不允许编辑");
        	}
    }else{
    	alert("请选择数据!");
    };
    });
    
    $('#dlg').dialog({
        onClose:function(){
        	var div = $("#mydiv2");
        	if(div){
        		div.remove();
        	}
        }
    });

    
    //批审
    $("#batchApproval").click(function(){
    
    	var posturl = "/orderPreprocessing/singleApproval";
    	if($("#isJson").attr("checked")){
    		posturl = "/orderPreprocessing/singleApproval/json";
    	}
    	
      var rows = $('#dataTable').datagrid('getChecked');
 	  var app_suc="";
	  var num_suc=""; //审核成功
	  var app_failed="";
	  var num_failed="" ; //审核失败
	  var app_not="";
	  var num_not=""; //不允许审核
	  var app_error="";
	  var num_error=""; //审核错误
	  
	  var str_not = "";
        if (rows != ""){
        	$('#num_not_v').html('');
        	$('#app_not_v').html('');
            $('#num_suc_v').html('');
            $('#app_suc_v').html('');
            $('#num_failed_v').html('');
            $('#app_failed_v').html('');
            $('#num_error_v').html('');
            $('#app_error_v').html('');
        	  $('#rows_length').html(rows.length);
        	  $('#dd').dialog('open'); 
        	  var value = $('#pppp').progressbar('getValue');  
        	  $.each(rows,function(n,row) {
        		  //有备注不允许批审
                  if(jQuery.type(row.sellerMessage) == "undefined" && jQuery.type(row.buyerMessage) == "undefined" && (row.impStatus != 13 && row.impStatus != 14 && row.impStatus != 17 )){
              		//console.log(row.tradeId+(row.refundStatusConfirm==2)+","+(row.refundStatus != undefined)+","+(row.refundStatus != undefined || row.refundStatusConfirm==2));
                	if(row.refundStatus != undefined && row.refundStatusConfirm != 1){
                		
                		if(row.refundStatusConfirm==2){
                			str_not=":已取消发货";
    	            	}else{
    	            		str_not=":申请退款";	
    	            	}
                		
                   	  num_not++;
                	  app_not += row.tradeId;
                	  app_not += str_not;
                	  app_not += "<br/>";
                	  $('#num_not_v').html("不允许审核的有"+num_not+"条");
                	  $('#app_not_v').html(app_not);
                	  
            			 value +=1/rows.length;  
                			$('#pppp').progressbar('setValue', Math.round(value*100));
                			console.log(value);
               		}else{
                	  $.ajax({    
                		    url:posturl,    
                		    data:{    
                		    	id:row.id
                		    },    
                		    timeout: 10000,
                		    type:'post',    
                		    cache:false,    
                		    dataType:'json',    
                		    success:function(data){    
  		                  	  if(data.impStatus == 13 || data.impStatus == 14 || data.impStatus == 17){
		                  		  num_suc++;
		                  		  app_suc += data.tradeId;
		                  		  app_suc += ":审核成功";
		                  		  app_suc += "<br/>";
		                  		 $('#num_suc_v').html("审核成功的有"+num_suc+"条");
		                  		 $('#app_suc_v').html(app_suc);
		                  	  }else{
		                  		  num_failed++;
		                  		  app_failed += data.tradeId;
		                  		  app_failed += ":"+data.impStatusRemark;
		                  		  app_failed += "<br/>";
		                  		 $('#num_failed_v').html("审核失败的有"+num_failed+"条");
		                  		 $('#app_failed_v').html(app_failed);
		                  	  }
		                  	  
		        				value += (1/rows.length);  
		        				console.log(value);
		        	       		$('#pppp').progressbar('setValue', Math.round(value*100));
                		     },    
                		     error : function() {    
                		    	 num_error++;
		                  		  app_error += row.tradeId;
		                  		  app_error += "审核超时";
		                  		  app_error += "<br/>";
		                  		 $('#num_error_v').html("审核错误的有"+num_error+"条");
		                  		 $('#app_error_v').html(app_error);
                		    	 value += (1/rows.length);  
 		        				console.log(value);
 		        	       		$('#pppp').progressbar('setValue', Math.round(value*100));
                		          // view("异常！");    
                		              
                		     }    
                		});  
               		}
                  }else{
                	  if(row.impStatus==13 || row.impStatus==14 || row.impStatus==17) str_not=":已审核";
                	  if(jQuery.type(row.sellerMessage)  != "undefined" || jQuery.type(row.buyerMessage) != "undefined") str_not=": 有备注信息" ;
                	  num_not++;
                	  app_not += row.tradeId;
                	  app_not += str_not;
                	  app_not += "<br/>";
                	  $('#num_not_v').html("不允许审核的有"+num_not+"条");
                	  $('#app_not_v').html(app_not);
                	  
            			 value +=1/rows.length;  
                			$('#pppp').progressbar('setValue', Math.round(value*100));
                			console.log(value);
                 }
                  
                  });
        	
         
             

    }else{
    	alert("请选择数据!");
    };
    });
    
    $('#dd').dialog({
    	onClose:function(){
    		try{
    			  app_error = " ";
 	    	      num_error = ""; //审核成功
    	      	   app_suc = " ";
    	    	   num_suc = ""; //审核成功
    	    	   app_failed = " ";
    	    	   num_failed = "" ; //审核失败
    	    	   app_not = " ";
    	    	   num_not = ""; //不允许审核
    	    	   str_not = " ";
    	    	   value=0;
    			}catch(exception){

    			}
    	   $('#pppp').progressbar('setValue',0);
    	   $('#dataTable').datagrid('unselectAll');
           $('#dataTable').datagrid('reload');
    	  
        }
    });
    
    //单审
    $("#singleApproval").click(function(){
    	$('#w_ok').linkbutton('disable');
    	$('#w_content').html("处理中 ....");
    	var posturl = "/orderPreprocessing/singleApproval";
    	if($("#isJson").attr("checked")){
    		posturl = "/orderPreprocessing/singleApproval/json";
    	}
    	var mark = true;
        var row = $('#dataTable').datagrid('getChecked')[0];
        if (row){
        	if( row.impStatus != 13 && row.impStatus != 14 && row.impStatus != 17 ){
        		if(row.refundStatus != undefined && row.refundStatusConfirm != 1){
                   mark= false;
        		}
	            if(mark){
	            	$.post("/orderPreprocessing/getCombine",{ id : row.id}, function(data) {	            
	                    if(eval(data.result)){
			            	//判断是否合单的数据
			            	$.messager.confirm('单审提示', '可以合单的数据:<br/>'+data.combineList+'<br/>要继续审核么?', function(r){
			    				if (r){
			    					$('#w').dialog('open');
			    	    	    	$.post(posturl,{ id : row.id,isCombine:r}, function(data) {
			    	    	    		
			    	    	    	//	$('#w').("订单号为"+data.tradeId+",的审核结果："+data.impStatusRemark );
			    	    	    		$('#w_content').html("订单号为"+data.tradeId+",的审核结果："+data.impStatusRemark);  
			    	                    $('#dataTable').datagrid('reload');
			    	                    $('#w_ok').linkbutton('enable');
			    				}).success(function() { 
			    					});
			    				}
			    			});
	                    }else{
	                    	$('#w').dialog('open');
	                    	$.post(posturl,{ id : row.id}, function(data) {
	                    		$('#w_content').html("订单号为"+data.tradeId+",的审核结果："+data.impStatusRemark);  
	    	                    $('#dataTable').datagrid('reload');
	    	            		$('#w_ok').linkbutton('enable');
	    				});

	                    }	
				});
				
	            }else{
	            	if(row.refundStatusConfirm==2){
	            		alert("订单"+row.tradeId+",已取消发货,不允许审核!");
	            	}else{
	            		alert("订单"+row.tradeId+",申请退款,不允许审核!");	
	            	}
	            	
	            }
        	}else{
        		alert("订单"+row.tradeId+",不允许审核!");
        	}
    }else{
    	alert("请选择数据!");
    };
    });
    
    //合单 ,“收货人”、“收货地址”、“收货电话”、“送货公司”，//“来源店铺”
    $("#combineOrders").click(function(){
    	
    	var posturl = "/orderPreprocessing/combineOrders";
    	if($("#isJson").attr("checked")){
    		posturl = "/orderPreprocessing/combineOrders/json";
    	}
    	var rows = $('#dataTable').datagrid('getChecked');
    	var rowids = "";
    	var tids = "";
    	var ptids="";
    	var payment=0;
    	var postFee=0;
    	var rname="";
    	var raddr="";
    	var rcompany="";
    	var rfrom="";
    	var rphone="";
    	var rinvoiceTitle ="";
    	var rinvoiceComment ="";
    	var myboolean=true ;
    	var msg=""
        var msg1="";
    	$.each(rows, function(i,val){
    	      rowids+=val.id+",";
    	      ptids+=val.tradeId+",";
    	      if(jQuery.type(val.alipayTradeId) == "undefined"){
    	    	  tids +=",";
    	      }else{
    	    	  tids +=val.alipayTradeId+",";  
    	      }
    	      
    	      payment += val.payment;
    	      
    	      msg1 += val.tradeId+":"+ val.shippingFee+",";
    	      if(val.shippingFee >0) postFee += val.shippingFee;
    	      
    	      //退款订单
    	      if(val.refundStatus != undefined && val.refundStatusConfirm != 1){
                   if(val.refundStatusConfirm == 2){
                	   msg ="取消发货的订单,";
                   }else{
                	   msg ="申请退款的订单,";
                   }
    	    	    
	    			  myboolean = false ;
    	      }
    	      if(i==0){
    	    	  rname=val.receiverName;
    	    	  raddr=val.sendInfo;
    	    	  rcompany=val.tmsCode;
    	    	  //rfrom=val.tradeFrom;	  
    	    	  rphone=val.receiverMobile;
    	    	  //发票
    	    	  rinvoiceTitle = val.invoiceTitle;
    	    	  rinvoiceComment = val.invoiceComment;
    	    	  //发票抬头
    	    	  
    	      }else if(i==1){
    	    	  if(rname == val.receiverName){
    	    		 // myboolean = true;
    	    	  }else{
    	    		  msg+="收货人,"
    	    			  myboolean = false
    	    	  }
    	    	  
    	    	  if($.trim(raddr) ==  $.trim(val.sendInfo)){
    	    		  //myboolean = true;
    	    	  }else{
    	    		  msg+="送货地址,"
    	    			  myboolean = false
    	    	  }
    	    	  
    	    	  if(rcompany == val.tmsCode){
    	    		  //myboolean = true;
    	    	  }else{
    	    		  msg+="送货公司,"
    	    			  myboolean = false
    	    	  }
    	    	  
    	    	  if(rphone == val.receiverMobile){
    	    		  //myboolean = true;
    	    	  }else{
    	    		  msg+="收货人电话,"
    	    			  myboolean = false
    	    	  }
    	    	  
    	       	  if(rinvoiceComment == val.invoiceComment){
    	    		  //myboolean = true;
    	    	  }else{
    	    		  msg+="发票需求,"
    	    			  myboolean = false
    	    	  }
    	    
    	    	  if(rinvoiceTitle == val.invoiceTitle){
    	    		  //myboolean = true;
    	    	  }else{
    	    		  msg+="发票抬头,"
    	    			  myboolean = false
    	    	  }
    	    	  
    	    	  
    	    	  if(val.impStatus != 13 && val.impStatus != 14 && val.impStatus != 17){
    	    		 // myboolean = true;
    	    	  }else{
    	    		  msg+="订单已审核,"
    	    			  myboolean = false
    	    	  }
    	    	      	
    	    	  //信用卡订单不允许合单
    	    	  if(val.sourceId =="17"){
    	    		  myboolean= false;
    	    		  msg = "信用卡订单,";
    	    	  }
    	    	  
    	      }
    	  });   
    	
    	
    	  if(! myboolean){
    		  alert("合单条件不满足,请查证:"+msg.substring(0, msg.length-1));
    	  }else{
    		  if (rows != "" && rows.length >1 ){
    			  
    			  var name =prompt("您每笔订单的运费为:"+msg1+"<br>合单的运费后为:", postFee); 
    			  if (name!=null && name!=""){ 
    			  if(IsNum(name)){  
    				  payment = payment + (name-postFee);
    				  postFee = name ;
    			      $.post(posturl,{ids:rowids,ptids:ptids,tids:tids,payment:payment,postFee:postFee,invoiceTitle:rinvoiceTitle,invoiceComment:rinvoiceComment}, function(msg) {
    			 	     	alert(msg);   
    			 	     	$('#dataTable').datagrid('reload');
    			 	  }).success(function() { 
    			 				});
    			    
    			  }else{
    				  alert("输入有误");
    			  }
    		   } 
    		  
    		  }else{
			    	if(myboolean) alert("至少选择2条数据!");
			    };
    	  }
    		

    });
    
    
    //删除
    $("#deleteRow").click(function(){
      	var row = $('#dataTable').datagrid('getChecked')[0];
        if (row){
	        	if(row.impStatus != 13 && row.impStatus != 14 && row.impStatus != 17){
	        		if(row.refundStatus != undefined && row.refundStatusConfirm != 2){
	        			if(row.refundStatusConfirm == 1){
                			alert("允许发货,不可删除");
    	            	}else{
    	            		alert("申请退款,不可删除");	
    	            	}
	         		}else{
	         			 var answer = confirm("确认要删除,订单编号为:"+row.tradeId+" 的订单么?");
	 	             	if(answer){
	 	        		$.post('/orderPreprocessing/deletePreTrade',{ id : row.id,treadid:row.tradeId}, 
	 	        				function(msg) {
	 	        				alert(msg);    
	 	        				 $('#dataTable').datagrid('reload');
	 	        			});
	 	            	}
	         		}
	        		
	        	}else{
	        		alert("此订单不允许删除！");
	        		//$('#dataTable').datagrid('reload');
	        	}
        
    }else{
    	alert("请选择数据!");
    };
    });
    
    //压单
    $("#detainOrder").click(function(){
    	 var row = $('#dataTable').datagrid('getChecked')[0];
         if (row){
        	 var answer = confirm("确认对订单编号为:"+row.tradeId+" 的订单压单么?");
        	 if(answer){
         	if(row.impStatus != 13 && row.impStatus != 14 && row.impStatus != 17  && row.refundStatus != 1 && row.refundStatus != 2){
         		$.post('/orderPreprocessing/detainOrder',{ id : row.id}, function(msg) {
      				alert(msg);       
      				 $('#dataTable').datagrid('reload');
         			}).success(function() { 
         				});
         	}else{
         		 alert("此订单不允许压单！");
         	}
        	 }
     }else{
    	 alert("请选择数据!");
     };
    });
    
    //反馈
    $("#feedback").click(function(){
        var row = $('#dataTable').datagrid('getChecked')[0];
        if (row){
        	if( (row.impStatus == 13 || row.impStatus == 14 || row.impStatus == 17) && row.feedbackStatus != 2){
             
        		$.post('/orderPreprocessing/getbw',{ id : row.shipmentId}, function(msg) {
        			//alert(msg);
        			if(msg >3){
        					
	        		     var answer = confirm(" 确认反馈么?");
		        		 if(answer){
		        			//反馈
		                 	 $.post('/orderPreprocessing/feedback',{ id : row.id}, function(msg) {
		               		  $('#dataTable').datagrid('reload');
		         				alert(msg);     
		               			}).success(function() {          			     				
		               				});
		        			  }
        			}else if(msg == 1){
            			alert("面单号为空!");
            		}else if (msg == 2){
            			alert("送货公司为空!");
            		}else if(msg == 3){
            			alert("面单号和送货公司都为空!");
            		}
	           			}).success(function() {          			     				
	           				});
        		
        	}else{
        		if(row.feedbackStatus == 2){
        			alert("此订单已反馈!");
        		}else{
        			alert("此订单未通过审核,不能反馈!");	
        		}
        		
        	}
    }else{
    	alert("请选择数据!");
    };
    });
    
    //修改
    $("#updatePreTrade").click(function(){
    
    	 var phone =  $('#v_receiverPhone').val().trim();
    	 var mobile = $('#v_receiverMobile').val().trim();
    	 if(phone =="" && mobile == "" ){
    		 alert("电话和备用电话,不能同时为空!");
    	 }else{
    	var answer = confirm("确认要修改订单信息么?");
    	 if(answer){
		      $('#fm').form('submit',{
		          url: '/orderPreprocessing/updatePreTrade',
		          onSubmit: function(){
		              return $(this).form('validate');
		          },
		          success: function(result){
		        	      alert(result);
		            	  d_close();      // close the dialog
		                  $('#dataTable').datagrid('reload');    // reload the user data
		          }
		      });
    	 }
    	 }
    });
    //重置
    $("#d_reset").click(function(){
   	 var row = $('#dataTable').datagrid('getChecked')[0];
	 var answer = confirm("确认要重置订单信息么?");
	 if(row || answer){
     $('#v_address').val(row.receiverAddress);
    // $("#v_shippingType").val(row.tmsCode);
    // $('#v_invoiceComment').val(row.invoiceComment);
     $('#v_invoiceTitle').val(row.invoiceTitle);
     $('#v_receiverMobile').val(row.receiverMobile);
     $('#v_receiverPhone').val(row.receiverPhone);
     $('#v_receiverName').val(row.receiverName);
     $('#v_shippingType').combobox('setValue', row.tmsCode);
     //setup(row.receiverProvince,row.receiverCity,row.receiverCounty);
     $('#s1').val(row.receiverProvince);
     $('#s2').val(row.receiverCity);
     $('#s3').val(row.receiverCounty);
     //new PCAS("province3","city3","area3");
	 }
         
    });
    
//允许发货   
$("#refund").click(function(){
	 var rows = $('#dataTable').datagrid('getChecked');
	 //订单已作退款处理
	 var refunded="";
	 var refundedNum=0;
	 //订单不是申请退款的订单
	 var notRefund="";
	 var notRefundNum=0;
	 
	 
	 
	 //可以作退款的订单
	 var isRefund=""; 
	 var isRefundNum=0;
	 var isRefundID="";
	 
	 var conMsg ="";
	 
	 
	  $.each(rows,function(n,row){
			 if(row.refundStatus == undefined || row.refundStatusConfirm != undefined){
			   	 if(row.refundStatusConfirm != undefined){
			   		refundedNum++;
			   		refunded +=row.tradeId+",";
			   		if((n+1)%2 == 0) refunded += "<br/>";
			   	 }else{
			   		notRefundNum++;
			   		notRefund +=row.tradeId+",";
			   	 if((n+1)%2 == 0) notRefund += "<br/>";
			   	 }
			 }else{
				 isRefundNum++;
				 isRefund += row.tradeId+",";
				 if((n+1)%2 == 0) isRefund += "<br/>";
				 isRefundID += row.id+",";
			 }
	  });
	  
	  if(refunded != ""){
		  conMsg +="<div style='font-weight:bold;'>已作退款处理的订单"+refundedNum+"条:</div>";
		  conMsg +=refunded;
	  }
	  if(notRefund != ""){
		  conMsg +="<div style='font-weight:bold;'>未申请退款的订单"+notRefundNum+"条:</div>";
		  conMsg +=notRefund;
	  }	  
	  
	  if (isRefund != ""){
		  conMsg +="<div style='font-weight:bold;'>符合允许发货的订单"+isRefundNum+"条:</div>";
		  conMsg += isRefund;
		  isRefund = isRefund.substring(0, isRefund.length-1);
		  isRefundID = isRefundID.substring(0, isRefundID.length-1);
		  conMsg = "<div style='padding-left:47px;text-align:left;'>"+conMsg+"</div></br><div tyle='font-weight:bold;'>您要继续相关操作么?"+"</div>";
			$.messager.confirm('允许发货', conMsg, function(r){
				if (isRefundID !="" && r){
					   		$.post('/orderPreprocessing/refund',{ ids:isRefundID,treadIds:isRefund}, function(msg) {
					   			alert(msg);  
					   		}).success(function() {   
					   		 $('#dataTable').datagrid('reload'); 
							});
					   	 }
			});
		  
	  }else{
		  alert("您选择的数据中,没有符合允许发货的数据");
	  } 

});



//同意退款   
$("#consent").click(function(){

	 var rows = $('#dataTable').datagrid('getChecked');
	 //订单已作退款处理
	 var refunded="";
	 var refundedNum=0;
	 //订单不是申请退款的订单
	 var notRefund="";
	 var notRefundNum=0;
	 
	 
	 //可以作退款的订单
	 var isRefund=""; 
	 var isRefundNum=0; 
	 var isRefundID="";
	 
	 var conMsg ="";
	 
	 
	  $.each(rows,function(n,row){
			 if(row.refundStatus == undefined || row.refundStatusConfirm != undefined){
			   	 if(row.refundStatusConfirm != undefined){
			   		refundedNum++; 
			   		refunded +=row.tradeId+",";
			   		if((n+1)%2 == 0) refunded += "<br/>";
			   	 }else{
			   		notRefundNum++; 
			   		notRefund +=row.tradeId+",";
			   	 if((n+1)%2 == 0) notRefund += "<br/>";
			   	 }
			 }else{
				 isRefundNum++;
				 isRefund += row.tradeId+",";
				 if((n+1)%2 == 0) isRefund += "<br/>";
				 isRefundID += row.id+",";
			 }
	  });
	  
	  if(refunded != ""){
		  conMsg +="<div style='font-weight:bold;'>已作退款处理的订单"+refundedNum+"条:</div>";
		  conMsg +=refunded;
	  }
	  if(notRefund != ""){
		  conMsg +="<div style='font-weight:bold;'>未申请退款的订单"+notRefundNum+"条:</div>";
		  conMsg +=notRefund;
	  }	  
	  
	  if (isRefund != ""){
		  conMsg +="<div style='font-weight:bold;'>符合取消发货的订单条"+isRefundNum+":</div>";
		  conMsg += isRefund;
		  isRefund = isRefund.substring(0, isRefund.length-1);
		  isRefundID = isRefundID.substring(0, isRefundID.length-1);
		  conMsg = "<div style='padding-left:47px;text-align:left;'>"+conMsg+"</div></br><div tyle='font-weight:bold;'>您要继续相关操作么?"+"</div>";
			$.messager.confirm('取消发货', conMsg, function(r){
				if (isRefundID !="" && r){
					   		$.post('/orderPreprocessing/consent',{ ids:isRefundID,treadIds:isRefund}, function(msg) {
					   			alert(msg);  
					   		}).success(function() {   
					   		 $('#dataTable').datagrid('reload'); 
							});
					   	 }
			});
		  
	  }else{
		  alert("您选择的数据中,没有符合取消发货的数据");
	  }
	
	 

});


//初始化下拉框


});




var queryData = function () {
	$('#dataTable').datagrid('load',{
		   	alipayTradeId:$('#alipayTradeId').val(),
	    	shipmentId:$('#shipmentId').val(),
	    	from: $("#from").val(),
	    	tradeId: $("#tradeId").val(),
	    	tradeFrom: $("#tradeFrom").val(),
	        beginDate: $("#beginDate").datebox('getValue'),
	        endDate: $("#endDate").datebox('getValue'),
	        min: $("#min").val(),
	        max: $("#max").val(),
	        receiverMobile: $("#receiverMobile").val(),
	        buyerMessage: $("#buyerMessage").attr("checked"),
	        sellerMessage: $("#sellerMessage").attr("checked"),
	        isCombine: $("#isCombine").attr("checked"),
	        state: $('#state').val(),
	        refundStatus:$('#refundStatus').val(),
	        refundStatusConfirm:$('#refundStatusConfirm').val(),
	        treadType:$("#treadType").val(),
	        pageNumber:1
	});
};

var queryData_combine = function(){
	
}



//设置发票需求


//关闭DIV层
function d_close(){
    $('#dlg').dialog('close');
}


function alert_br(){
	var os=getOs();
	if(os=='FF' || os=='SF'){  //FireFox、谷歌浏览器用这个
		return '\n';
	}else{   //IE系列用这个
		return '\r\n';
	}
}

function start(){  
    var value = $('#p').progressbar('getValue');  
    if (value < 100){  
        value += Math.floor(Math.random() * 10);  
        $('#p').progressbar('setValue', value);  
        setTimeout(arguments.callee, 200);  
    }  
};  


function IsNum(s)
{
    if(s!=null){
        var r,re;
        re = /\d*/i; //\d表示数字,*表示匹配多个数字
        r = s.match(re);
        return (r==s)?true:false;
    }
    return false;
}

function sleep(milliSeconds){
    var startTime = new Date().getTime(); // get the current time
    while (new Date().getTime() < startTime + milliSeconds); // hog cpu
}

function getGWidth(){return $('.container').width();}

function maskPhone(phoneNo) {
	if(undefined == phoneNo || null == phoneNo || '' == $.trim(phoneNo)) {
		return '';
	}
	
	phoneNo = $.trim(phoneNo);
	
	phoneLen = phoneNo.length;
	if (phoneLen > 5) {
		phoneNo = phoneNo.substring(0, phoneLen -4) + "****";
	}
	
	return eraseSpace(phoneNo);
}

function eraseSpace(val) {
	if(undefined == val || null == val || '' == $.trim(val) || 'null' == $.trim(val)) {
		return '';
	}
	
	return val.replace(new RegExp(' ', 'g'), '');
}


