$(document).ready(function() {
   	$("#beginDate").datetimebox("setValue", currentTime());
	$("#endDate").datetimebox("setValue", currentTime()+" 23:59:59");
    	var v_result = eval('('+$('#v_result').html()+')'); //反馈结果
       	var v_laststatus = eval('('+$('#v_laststatus').html()+')'); //状态
       	var v_ordertype = eval('('+$('#v_ordertype').html()+')'); //类型
       	var v_saletype = eval('('+$('#v_saletype').html()+')'); //状态
       	var v_cardtype= eval('('+$('#v_cardtype').html()+')');
      	var v_paytype= eval('('+$('#v_paytype').html()+')');
      	var v_company = eval('('+$('#v_company').html()+')');
       	initOpt(v_cardtype,$('#cardtype'));
       	
        $('#dataTable').edatagrid({
            title:'',
            iconCls:'icon-edit',
            width: '100%',
            height: 365,
            nowrap: false,
            striped: true,
            border: true,
            collapsible:false,
            fitColumns:true,
            scrollbarSize:0,
            url:'/orderFreeback/list',
            idField:'orderid',
            sortName: 'orderid',
            sortOrder: 'desc',
            columns:[[
                {field:'orderid',title:'订单号',width:120},
                {field:'c_name',title:'客户名称',width:60},
                {field:'mailid',title:'邮件号',width:100},
                {field:'result',title:'反馈结果',width:60,formatter:function(val,rec){
            		return getDescById(v_result,val);
            	}},
            	 {field:'paytype',title:'付款方式',width:60,formatter:function(val,rec){
             		return getDescById(v_paytype,val);
             	}},
                {field:'status',title:'订单状态',width:60,formatter:function(val,rec){
            		return getDescById(v_laststatus,val);
            	}},
                {field:'crdt',title:'订购日期',width:70},
                {field:'c_city',title:'城市',width:60},
                {field:'ordertype',title:'订单类型',width:60,formatter:function(val,rec){
            		return getDescById(v_ordertype,val);
            	}},
                {field:'companyid',title:'送货公司',width:60,formatter:function(val,rec){
            		return getCompanyNameById(v_company,val);
            	}},
                {field:'cardtype',title:'信用卡类型',width:65,formatter:function(val,rec){
            		return getCompanyNameById(v_cardtype,val);
            	}},
                {field:'prodprice',title:'货款总额',width:0},
                {field:'clearfee',title:'实际付款',width:0},
                {field:'mailprice',title:'运费',width:0},
                {field:'totalprice',title:'总金额',width:60},
                {field:'postfee',title:'投递费',width:0},
                {field:'clearfee',title:'实际结算',width:0},
                {field:'accdt',title:'付款日期',width:0},
                {field:'outdt',title:'反馈日期',width:0},
    		    {field:'laststatus',title:'结帐',width:0}
            ]],
            remoteSort:false,
            idField:'orderid',
            singleSelect:false,
            pagination:true,
            rownumbers:true,
            frozenColumns:[[
           
        ]],
        queryParams: {
            state: $('input[name=state][checked]').val(),
            beginDate: $("#beginDate").datetimebox('getValue'),
            endDate: $("#endDate").datetimebox('getValue'),
            orderid: $("#orderid").val(),
            mailid: $("#mailid").val(),
            status: $("#status").val(),
            paytype:$('#paytype').val(),
            mailtype:$('#mailtype').val(),
            ordertype: $("#ordertype").val(),
            result: $("#result").val(),
            companyid: $("#companyid").val(),
            cardtype: $("#cardtype").val(),
            cityid: $("#cityid").val()
            },
            onDblClickRow:function(index,row){
    	    	if(row){
    	     	   showOrderdet(row.orderid);
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
//        onBeforeRefresh:function(){
//            $(this).pagination('loading');
//            $(this).pagination('loaded');
//            }
        });
    });

var showOrderdet = function(data){
	var v_prodstate = eval('('+$('#v_prodstate').html()+')'); //产品状态
	var v_saletype = eval('('+$('#v_saletype').html()+')'); //销售方式
	  $('#dataTable2').edatagrid({
	        title:'',
	        iconCls:'icon-edit',
	        width: 700,
	        height: 100,
	        nowrap: false,
	        striped: true,
	        border: true,
	        collapsible:false,
	        fitColumns:true,
	        url:'/orderFreeback/getOrderdet',
	        idField:'orderdetid',
	        sortName: 'orderdetid',
	        sortOrder: 'desc',
	        columns:[[
	              //	{field:'orderdetid',title:'订单详细ID',width:100},
	            	{field:'orderid',title:'订单ID',width:100},
	            	{field:'prodid',title:'产品ID',width:100},
	            	{field:'c_name',title:'订购人',width:100},
	            	{field:'prodscode',title:'产品代码',width:90},
	            	{field:'prodname',title:'产品名称',width:90},
	            	{field:'soldwith',title:'销售方式',width:90,formatter:function(val,rec){
	            		return getDescById(v_saletype,val);
	            	}},
	            	{field:'status',title:'产品状态',width:90,formatter:function(val,rec){
	            		return getDescById(v_prodstate,val);
	            	}},
	            	//{field:'reckoning',title:'是否结账',width:90},	
	            	//{field:'reckoningdt',title:'结账日期',width:90},	
//	            	{field:'fbdt',title:'反馈日期',width:90},	
//	            	{field:'uprice',title:'产品原价',width:90},	
//	            	{field:'upnum',title:'原价订购个数',width:90},	
//	            	{field:'sprice',title:'产品优惠价',width:90},	
//	            	{field:'spnum',title:'优惠价订购个数',width:90},	
//	            	{field:'payment',title:'实际付款',width:90},	
//	            	{field:'freight',title:'产品运费',width:90},	
//	            	{field:'postfee',title:'投递费',width:90},	
//	            	{field:'clearfee',title:'实际结算',width:90}
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
    $('#dataTable').datagrid('load',{
    	 state: $('input[name=state][checked]').val(),
         beginDate: $("#beginDate").datetimebox('getValue'),
         endDate: $("#endDate").datetimebox('getValue'),
         orderid: $("#orderid").val(),
         mailid: $("#mailid").val(),
         status: $("#status").val(),
         paytype:$('#paytype').val(),
         mailtype:$('#mailtype').val(),
         ordertype: $("#ordertype").val(),
         result: $("#result").val(),
         companyid: $("#companyid").val(),
         cardtype: $("#cardtype").val(),
         cityid: $("#cityid").val()
    });
};

function getDescById(result,id){
	var m_r= "";
	 $.each(result.rows, function(i,val){  
	      if(val.id.id == id) m_r= val.dsc;
	  });   
	return m_r;
}

function getCompanyNameById(result,id){
	var m_r= "";
	 $.each(result.rows, function(i,val){ 
	      if(val.companyid == id){
	    	  m_r= val.name;
	      }
	  });   
	return m_r;
}

function getCardTypeById(result,id){
	var m_r= "";
	 $.each(result.rows, function(i,val){ 
	      if(val.CARDTYPEID == id){
	    	  m_r= val.NAME;
	      }
	  });   
	return m_r;
}

function initOpt(result,obj){
	 obj.prepend("<option value=''>===请选择===</option>");
	 $.each(result.rows, function(i,val){  
		 obj.append("<option value="+val.CARDTYPEID+">"+val.NAME+"</option>");       
	  });   
	 
}

function currentTime(){
	var d = new Date(),str = '';
	str += d.getFullYear()+'-';
	str += d.getMonth() + 1+'-';
	str += d.getDate();
	//str += d.getHours()+':'; str  += d.getMinutes()+':';  str+= d.getSeconds();  
	return str; 
}