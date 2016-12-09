$(document).ready(function() {
    	$("#beginDate").datetimebox("setValue", currentTime()+" 00:00:00");
    	$("#endDate").datetimebox("setValue", currentTime()+" 23:59:59");
    	if($("#v_settlType").val()==""){
    		$('#settleType').combobox('setValue', '0');
    	}else{
    		$('#settleType').combobox('setValue', $("#v_settlType").val());	
    	}
    	
    $('#dataTable').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height: 390,
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        scrollbarSize:0,
        url:'/orderFreeback/fblist',
        updateUrl: '',
        idField:'clearid',
        sortName: 'impdt',
        sortOrder: 'desc',
        columns:[[
            {field:'orderid',title:'订单号',width:100},
            {field:'clearid',title:'导入号',width:100},
            {field:'mailid',title:'邮件编号',width:150},
            {field:'senddt',title:'交寄日期',width:160},
            {field:'impdt',title:'导入日期',width:160},
           // {field:'status',title:'状态',width:50},
            {field:'reason',title:'失败原因',width:100},
            {field:'totalprice',title:'应收货款',width:100},
            {field:'postFee',title:'投递费',width:100},
            {field:'nowmoney',title:'实收货款',width:100},
            {field:'impusr',title:'结算人',width:100},
            {field:'type',title:'结账类型',width:100,
            	formatter:function(val,rec){
            		return getOrderType(val);
            	}
            },
            {field:'company',title:'送货公司',width:100
            },
            {field:'remark',title:'结算描述',width:100}
        ]],
        remoteSort:false,
        singleSelect:false,
        pagination:true,
        rownumbers:true,
        frozenColumns:[[
        {field:'ck',checkbox:true}
    ]],
    queryParams: {
        beginDate: $("#beginDate").datebox('getValue'),
        endDate: $("#endDate").datebox('getValue'),      
        state: $("input[name='state']:radio:checked").val(),
        settleType: $("#settleType").val(),
        remark: $("#cbRemark").attr('checked') == 'checked'
        },
    onDblClickRow: function(index,row){
            if(row){
         	   showOrderdet(row.orderid);   	
            }
	}

    });

    var p = $('#dataTable').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [100,300,500],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
//    onBeforeRefresh:function(){
//        $(this).pagination('loading');
//        $(this).pagination('loaded');
//        }
    });
   
    //提交上传
    $("#upload").click(function(){

    	$('#v_settlType').val($('#settleType').combobox('getValue'));
    	if($('#settleType').combobox('getValue') != 0 ){
    		
    		if($('#uploadfile').val() != ""){
		      $('#fm').form('submit',{
		          url: '/orderFreeback/fileupload',
		          
		          onSubmit: function(){
		        	  //alert($("#uploadfile").val()=="");
		        	  //settleType = $('#settleType').val();
		              return $(this).form('validate');
		          },
		          success: function(result){
		        	      $('#dd').dialog('close');      // close the dialog
		                  $('#dataTable').datagrid('reload');    // reload the user data
		                  $('#dataTable2').datagrid('reload');    // reload the user data
		          }
		      });
    		}else{
    			 alert("请选择，要上传的文件"); 
    		}
    	}else{
    		alert("请选择,结账类型");
    		 $('#dd').dialog('close'); 
    	}
    });
    
    //结账
    $("#settle").click(function(){
    	$('#w_ok').linkbutton('disable');
    	$('#w_content').html("结算处理中 ....");
    	var companyid = $('#companyid').combobox('getValue');
        var ids = [];
        $.each($('#dataTable').datagrid('getChecked'),function(n,row) {
        	  ids.push(row.clearid+"&"+row.orderid);
        });
        if(companyid == "" || ids.length == 0){
        	alert("请选择,送货公司和结账数据");
        }else{
        	$('#w').dialog('open');
    		$.post('/orderFreeback/settle/settle', {
    			//settleType : stype,
    			rows: ids.join(","),
    			companyid: $('#companyid').combobox('getValue')
    		}, function(msg) {
    			//msg = [{'dese':'更新失败','code':100,'clearid':2132131},{'dese':'更新失败','code':100,'clearid':2132131}];
    			msg=eval("("+msg+")");
    			var alertMsg =  "";
    			 $.each(msg, function(key, val) {
    				 if (typeof( val.clearid) == "undefined"){
    					 alertMsg="结账操作失败";
    				 }else{
    				 alertMsg += val.orderid;
    				 alertMsg += "["+val.clearid+"]";
    				 alertMsg +=":";
    				 alertMsg += val.desc;
    				 alertMsg +="<br/>";
    				 }
    				 });
    			 
    	        //$.messager.alert('系统提示',alertMsg,"");
    	        $('#w_content').html(alertMsg);  
    			$('#dataTable').datagrid('reload');
    			$('#w_ok').linkbutton('enable');
    		}).success(function() {
    	
    		}).error(function() {
    			$('#w_content').html("调用结帐服务失败");  
    			//$.messager.alert('系统提示',"调用结帐服务失败","");
    			$('#w_ok').linkbutton('enable');
    			}
    		);
        }
    });
  

    $("#importData").click(function(){
    	if($("#settleType").combobox('getValue')== "0"){
    		alert("请选择结账类型");
    	}else{
    		 var name =confirm("您导入订单类型为: "+ getOrderType($("#settleType").combobox('getValue'))); 
    		 if(name){
    			 $('#dd').dialog('open');
    		 }
    	}
    });
    
    //特能结账
    $("#mm_te_neng_settle").click(function(){
       
    });
    
    //特能结账
    $("#mm1_EMS").click(function(){
    	settleHandle(1);
    });
    
    //特能结账
    $("#mm1_tn").click(function(){
    	settleHandle(2);
    });
    
    //特能结账
    $("#mm1_sh").click(function(){
    	settleHandle(3);
    });
    
  //特能结账
    $("#mm1_ycc").click(function(){
    	settleHandle(4);
    });
    
  //特能结账
    $("#mm1_scEMS").click(function(){
    	settleHandle(5);
    });
    
    $("#mm1_yyk").click(function(){
    	settleHandle(6);
    });
    
    $("#mm1_jxEMS").click(function(){
    	settleHandle(7);
    });
    

    
    //
    $('#settleType').change(function(){
    	queryData();
    })
        
    $('#companyid').combobox({
    	url : '/orderFreeback/company',
    	valueField : 'companyid',
    	textField : 'name',
    	filter: function(q, row){
    		return row.name.indexOf(q)>=0;
    	}
    });
});



function show(){
	var date = new Date(); //日期对象
	var now = "";
	now = date.getFullYear()+"-"; //读英文就行了
	now = now + (date.getMonth()+1)+"-";//取月的时候取的是当前月-1如果想取当前月+1就可以了
	now = now + date.getDate()+" ";
	return now;
}

var showOrderdet = function(data){
	  $('#dataTable2').edatagrid({
	        title:'',
	        iconCls:'icon-edit',
	        width: 700,
	        height: 100,
	        nowrap: false,
	        striped: true,
	        border: true,
	        collapsible:false,
	        fit: true,
	        url:'/orderFreeback/getOrderdet',
	        idField:'orderdetid',
	        sortName: 'orderdetid',
	        sortOrder: 'desc',
	        columns:[[
	              	{field:'orderdetid',title:'订单详细ID',width:90},
	            	{field:'orderid',title:'订单ID',width:90},
	            	{field:'prodid',title:'产品ID',width:90},
	            	{field:'contactid',title:'订购人',width:90},
	            	{field:'prodscode',title:'产品代码',width:90},
	            	{field:'prodname',title:'产品名称',width:90},
	            	{field:'soldwith',title:'是否搭销',width:90},
	            	{field:'status',title:'产品状态',width:90},
	            	{field:'reckoning',title:'是否结账',width:90},	
	            	{field:'reckoningdt',title:'结账日期',width:90},	
	            	{field:'fbdt',title:'反馈日期',width:90},	
	            	{field:'uprice',title:'产品原价',width:90},	
	            	{field:'upnum',title:'原价订购个数',width:90},	
	            	{field:'sprice',title:'产品优惠价',width:90},	
	            	{field:'spnum',title:'优惠价订购个数',width:90},	
	            	{field:'payment',title:'实际付款',width:90},	
	            	{field:'freight',title:'产品运费',width:90},	
	            	{field:'postfee',title:'投递费',width:90},	
	            	{field:'clearfee',title:'实际结算',width:90}
	        ]],
	        remoteSort:false,
	        idField:'orderid',
	        singleSelect:false,
	        pagination:true,
	        rownumbers:true,
	        frozenColumns:[[
	       
	    ]],
	    queryParams: {
	    	orderid:data
	    }
	    });
}


var queryData = function () {
  $('#dataTable').datagrid('reload',{
      beginDate: $("#beginDate").datebox('getValue'),
      endDate: $("#endDate").datebox('getValue'),      
      state: $("input[name='state']:radio:checked").val(),
      settleType: $('#settleType').combobox('getValue'),
      remark: $("#cbRemark").attr('checked') == 'checked',
      companyid:$('#companyid').combobox('getValue')
  });
};

var getOrderType = function(val){
	
	if(val== 1){
		return "EMS结账";
	}else if(val==2){
		return "特能结账";
	}else if(val==3){
		return "送货结账";
	}else if(val==4){
		return "四川EMS结账";
	}else if(val==5){
		return "信用卡结账";
	}else if(val==6){
		return "江西EMS结账";
	}else{
		return "";
	}
}

function currentTime(){
	var d = new Date(),str = '';
	str += d.getFullYear()+'-';
	str += d.getMonth() + 1+'-';
	str += d.getDate();
	//str += d.getHours()+'时'; str  += d.getMinutes()+'分';  str+= d.getSeconds()+'秒';  
	return str; 
}

