$(document).ready(function() {

    renderOptionUI();

    $('#rule').change(function(){
    	var rv = $('#rule').val();
    	var pv = $('#promotionid').val();
        renderOptionUI();
       if(pv=="" && rv != ""){
    	   refreshpromotionEdit(rv);
       }
      
    });

    $('#returnpromotionListBtn').bind('click',function(){
        returnpromotionList();
    });

    $('#refreshpromotionEditBtn').bind('click',function(){
        refreshpromotionEdit();
    });

    $('#deletepromotion').bind('click',function(){
        deletepromotion();
    });

    $('#save_promotion_btn').bind('click',function(){
        updatePromotion();
    });
    //添加赠品
    $('#addGiftBtn').bind('click',function(){
        addGift();
        
    });
    //添加排斥产品
    $('#addRejectBtn').bind('click',function(){
    	addReject();
    });
//    
//    if($('#promotionid').val() != ""){
//    	$('#rule').attr("disabled",true);
//    }else{
//    	$('#rule').attr("disabled",false);
//    }
    
    initPlubasInfo();
    
    $("#channel").combobox({
        url:'/admin/channelType/lookup',
        valueField: 'id',
        textField: 'name'
    });
});

var renderOptionUI = function(){
    $.get('ruleOptionUI/'+$('#rule').val(), function(data) {
        $('#p_ruleOptions').html(data.html);
        $.globalEval("var gids = $('#product').val();");
        $.globalEval(data.initScript);
        $.globalEval(data.validateScript);
        initOptionUIValue();
    });
};

var initOptionUIValue = function(){
    $.get('/admin/promotionOption?promotionId='+$('#promotionid').val(), function(data) {
        var ruleId = $('#rule').val();
        for(var i= 0;i<data.length;i++){
            var op = data[i];
            var key = op.optionKey + "-" + ruleId;
            if ( key.indexOf('date')>=0  || key.indexOf('Date')>=0){
                $('#'+key).datebox('setValue', op.optionValue);
            }else if ($('#'+key).attr("type") == 'checkbox'){
                if (op.optionValue == 'true') {
                    $('#'+key).attr("checked", true);
                } else {
                    $('#'+key).attr("checked", false);
                }
            }else {
                $('#'+key).val(op.optionValue);
            }
        }
    });
}

var getRuleOptionArray = function(){
    var formId = "form" + $('#rule').val();
    var $inputs = $('#frmRuleOption :input');
    var values = {};
    $inputs.each(function() {
        if (this.id.length >0){
            if (this.className && this.className.indexOf('easyui-date')>=0){
                values[this.id] = $(this).datebox('getValue');
            }else if (this.type == 'checkbox'){
                values[this.id] = $('#'+this.id).is(':checked');
            }else{
                values[this.id] = $(this).val();
            }
        }
    });
    return $.toJSON(values);
}

var returnpromotionList = function (p_oEvent) {
    window.location.href = "/admin/promotions";
};

var refreshpromotionEdit = function (value) {
	$("#pproducts").val("");
	$("#product").val("");
	window.location.href = "/admin/promotion?roleId="+value;
   
};

var updatePromotion = function (p_oEvent) {
    var isPValid =  document.forms["promotionForm"].onsubmit();
    var isRValid = document.forms["frmRuleOption"].onsubmit();
    var isStartDateValid = document.getElementsByName('startDate')[0].value;

    if (!isStartDateValid){
        alert("请输入开始时间");
    }
    var isEndDateValid =  document.getElementsByName('endDate')[0].value;
    if (!isEndDateValid){
        alert("请输入结束时间");
    }
    
    if(isEndDateValid && isStartDateValid ){
    	var now=new Date();
      var ds = Date.parse(isStartDateValid.replace(/-/g,"/"));
      var de = Date.parse(isEndDateValid.replace(/-/g,"/"));
      if( ds>de){
    	  alert("开始时间 大于 结束时间 ");
    	  isEndDateValid = false;
      }
      
      if( now>ds){
    	  alert("系统时间 大于 开始时间 ");
    	  isStartDateValid = false;
      }
    }
    
    
    if (isPValid && isRValid && isStartDateValid && isEndDateValid){
        document.getElementById("ruleOptions_json").value =  getRuleOptionArray();
        document.forms["promotionForm"].submit();
    }
};

var deletepromotion = function (p_oEvent) {
    var promotionId = document.forms["promotionForm"]["promotionid"].value;
    if (promotionId != "") {
        var jqxhr = $.get('/admin/promotions/delete/' + promotionId, function() {
            window.location.href = "/admin/promotions";
            })
            .fail(function() { alert("error"); })
            .always(function() { alert("finished"); });
    }
};


var initPlubasInfo = function(){
	//初始华
	$("#rejectList").html("");
	$("#giftList").html("");
	   $('#listPlubasInfoTable').datagrid({
	        title:'',
	        height:255,
	        width:500,
	        iconCls:'icon-edit',
	        nowrap: true,
	        striped: true,
	        border: true,
	        collapsible:true,
	        fit: true,
	        url:'/admin/plubasInfoJson',
	        columns:[[
	            {field:'ruid',title:'ID',width:60},
	            {field:'plucode',title:'商品编码',width:100},
	            {field:'pluname',title:'商品名称',width:200},
	            {field:'ncfreename',title:'NC自由项名称',width:100},
	            {field:'pkunit',title:'主计量单位',width:60}
	            //{field:'credat',title:'credat',width:100},
	            //{field:'crepsn',title:'crepsn',width:60},
	            //{field:'modidat',title:'modidat',width:100},
	            //{field:'prliid',title:'prliid',width:100}
	        ]],
	        remoteSort:false,
	        idField:'id',
	        singleSelect:false,
	        pagination:true,
	        rownumbers:true,
	        frozenColumns:[[
	            {field:'ck',checkbox:true}
	        ]],
	        queryParams: {
	        	catcode: $("#catcode").val(),
	        	pluname: $("#pluname").val()
	        },
	    });
	    var p = $('#listPlubasInfoTable').datagrid('getPager');
	    $(p).pagination({
	        pageSize: 10,
	        pageList: [5,10,15],
	        beforePageText: '',
	        afterPageText: '页    共 {pages}页',
	        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
	        /*onBeforeRefresh:function(){
	         $(this).pagination('loading');
	         alert('before refresh');
	         $(this).pagination('loaded');
	         }*/
	    });
	    
	    //初始化giftList
	    $.post("/admin/initGiftList",{ promotionId:$('#promotionid').val()}, function(data) {
	    	 $.each(data.rows, function(i,data){       
	    		 $("#giftList").append("<option value='"+data.ruid+"'>"+data.ruid+":"+data.pluname+"</option");
	    	  });   
	    	
		});
	    
	    //初始化rejectList
       $.post("/admin/initRejectList",{ promotionId:$('#promotionid').val()}, function(data) {
    	   $.each(data.rows, function(i,data){       
	    		 $("#rejectList").append("<option value='"+data.ruid+"'>"+data.ruid+":"+data.pluname+"</option");
	    	  });
       });
}
//
var addGift = function(){
	//获取已有数据
	var oldData ="";
	   $("#giftList option").each(function(){
        oldData += $(this).val()+',';
    });

	//获取数据
	var rows = $('#listPlubasInfoTable').edatagrid('getChecked');
	//将数据添加到列表
	if(rows != ""){
		$.each(rows,function(n,row) {
			if(oldData.indexOf(row.ruid) != -1){
				 $.messager.alert('系统提示','商品['+row.ruid+']已存在!','warning');
			}else{
				$("#giftList").append("<option value='"+row.ruid+"'>"+row.ruid+":"+row.pluname+"</option");
				oldData+=row.ruid+",";	
			}
		});
		
		$("#product").val(oldData);
		//刷新
		renderOptionUI();
   }else{
	   $.messager.alert('系统提示','请选择商品!','warning');
   }
	
	
}

var addReject = function(){
	//获取已有的数据
	var oldData ="";
	   $("#rejectList option").each(function() {
           oldData += $(this).val()+',';
       });
	
	//获取数据
	var rows = $('#listPlubasInfoTable').edatagrid('getChecked');
	//将数据添加到列表
	if(rows != ""){
		$.each(rows,function(n,row) {
		if(oldData.indexOf(row.ruid) != -1){
			$.messager.alert('系统提示','商品['+row.ruid+']已存在!','warning');
		}else{
			$("#rejectList").append("<option value='"+row.ruid+"'>"+row.ruid+":"+row.pluname+"</option");
			//保存到数据库
				oldData+=row.ruid+",";
		}
		});
		$("#pproducts").val(oldData);	
   }else{
	   $.messager.alert('系统提示','请选择商品!','warning');
   }
}

var initValue= function(name,row){
	var rIndex =name.substring(6, 7); 
    var selectName = "select"+rIndex+"-"+$('#rule').val();

    var g = $("#"+selectName).combogrid('grid');  
    var rows = g.datagrid('getChecked');	// get the selected row
    
    //
    var sukNameVal= "";
    var idSkuCodeVal= "";
    var idProdCodeVal= "";

    
	$.each(rows,function(n,row) {
		sukNameVal += row.pluname+",";
		idSkuCodeVal += row.catcode+",";
		idProdCodeVal += row.plucode+",";
	});
	
	sukNameVal =  sukNameVal.substring(0,sukNameVal.length-1);
	idSkuCodeVal =  idSkuCodeVal.substring(0,idSkuCodeVal.length-1);
	idProdCodeVal =  idProdCodeVal.substring(0,idProdCodeVal.length-1);

	sukNameVal = sukNameVal.replace(",",",\n");
	idSkuCodeVal = idSkuCodeVal.replace(",",",\n");
	idProdCodeVal = idProdCodeVal.replace(",",",\n");

    
    //skuName
    var idSkuName ="sku"+rIndex+"Name-"+$('#rule').val() ;
    
    $("#"+idSkuName).val(sukNameVal);
	//skucode
    var idSkuCode ="sku"+rIndex+"-"+$('#rule').val();
    
    $("#"+idSkuCode).val(idSkuCodeVal);
	//pcode
    var idProdCode ="prod"+rIndex+"-"+$('#rule').val() ;
    
    $("#"+idProdCode).val(idProdCodeVal);
      
	
}



var queryPlubasInfo = function(){
    $('#listPlubasInfoTable').datagrid('load',{
    	catcode: $("#catcode").val(),
    	pluname: $("#pluname").val()
    });
}

var delete_gift = function(){
	var giftSelect = $("#giftList").val();
    if(giftSelect == null){
    	$.messager.alert('系统提示','请选择要删除的项!','warning');
    }else{
    	$.each(giftSelect, function(index, value) {
    		$("#giftList option[value='"+value+"']").remove();
    		});
    	
    var newData ="";
 	   $("#giftList option").each(function() {
 		  newData += $(this).val()+',';
        });
 	  $("#product").val(newData);
    }
	   
}


var delete_reject = function(){
	var rejectSelect = $("#rejectList").val();
    if(rejectSelect == null){
    	$.messager.alert('系统提示','请选择要删除的项!','warning');
    }else{
    	$.each(rejectSelect, function(index, value) {
    		$("#rejectList option[value='"+value+"']").remove();
    		});
    	
    var newData ="";
 	   $("#rejectList option").each(function() {
 		  newData += $(this).val()+',';
        });
 	  $("#pproducts").val(newData);
    }
	
	
}

//添加自定义验证器
function DoCustomValidation(m){
  var r = true;	
  var qty1 = $("#qty1-"+$('#rule').val());
  var sku1 = $("#sku1-"+$('#rule').val());
  if(sku1.val() !=""){
	  r=ValidationSkuQty(sku1,qty1,r);
  }
  var qty2 = $("#qty2-"+$('#rule').val());
  var sku2 = $("#sku2-"+$('#rule').val());
  if(sku2.val() !=""){
	  r=ValidationSkuQty(sku2,qty2,r);
  }
  var qty3 = $("#qty3-"+$('#rule').val());
  var sku3 = $("#sku3-"+$('#rule').val());
  if(sku3.val() !=""){
	  r=ValidationSkuQty(sku3,qty3,r);
  }
  var qty4 = $("#qty4-"+$('#rule').val());
  var sku4 = $("#sku4-"+$('#rule').val());
  if(sku4.val() !=""){
	  r=ValidationSkuQty(sku4,qty4,r);
  }
  var qty5 = $("#qty5-"+$('#rule').val());
  var sku5 = $("#sku5-"+$('#rule').val());
  if(sku5.val() !=""){
	  r=ValidationSkuQty(sku5,qty5,r);
  }
   return r;
}

function ValidationSkuQty(sku,qty,r){
  var re =/^\d+(\,\d+)*$/; 
  if( qty.val().split(",").length == sku.val().split(",").length  && re.test(qty.val()))
  {	  
    return true && r  ;
  }
  else
  {
	    alert('数量'+qty.attr("name").substring(3,4)+'字段值格式不正确!');
    return false && r;
  }
}

function currentTime(){
	var d = new Date(),str = '';
	str += d.getFullYear()+'-';
	str += d.getMonth() + 1+'-';
	str += d.getDate();
	str += ' '+d.getHours()+':'; str  += d.getMinutes()+':';  str+= d.getSeconds();  
	return str; 
}




