$(document).ready(function(){
//	 getAvailableIntegral();
//	 findGetIntegral();
	 
	 $('#integralTabs').tabs({   
         onSelect:function(title){  
         	
          if(title=="积分获得详情"){
         	 //获取可用积分
         	 getAvailableIntegral();
         	 findGetIntegral();
          }else{
         	 //已扣减积分
         	 getSubtractionIntegral();
         	 findUseIntegral();
          }  
         }  
     });  
});

function findUseIntegral(){
	
	 $('#useIntegralTable').datagrid({
	        title:'',
	        iconCls:'icon-edit',
	        height: 215,
	        nowrap: true,
	        striped: true,
	        border: true,
	        collapsible:false,
	        fitColumns:true,
	        scrollbarSize:0,
	        url:'/integral/useIntegral',
	        sortName: 'crdt',
	        sortOrder: 'desc',
	        columns:[[
	                  {field:'contactid',title:'客户编号',width:80},  
		              {field:'corderid',title:'产生订单编号',width:80},  
		              {field:'actorderid',title:'使用积分订单编号 ',width:80,align:'left'},
		              {field:'pointvalue',title:'使用积分',width:60,align:'left'},
		              {field:'type',title:'使用类型',width:80,align:'left'},
		              {field:'crdt',title:'创建日期',width:100,align:'left'},
		              {field:'mddt',title:'修改日期',width:100,align:'left'},
		              {field:'crusr',title:'创建人',width:60,align:'left'}
		              ,{field:'mdusr',title:'修改人 ',width:60,align:'left'}
	        ]],
	        remoteSort:false,
	        singleSelect:false,
	        pagination:true,
	        rownumbers:true,
	       queryParams: {
	    	   customerId:$("#customerId").val(),
	    	   name:$("#name").val()
	    }
	   	        
	    });

	    var p = $('#useIntegralTable').datagrid('getPager');
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
//	$('#useIntegralTable').datagrid('load',{
//		customerId:$("#customerId").val(),
// 	   name:$("#name").val()
//});
	    
	    function findIntegral(){
	       
	    }
}


function findGetIntegral(){
	 $('#getIntegralTable').datagrid({
	        title:'',
	        iconCls:'icon-edit',
	        height: 215,
	        nowrap: true,
	        striped: true,
	        border: true,
	        collapsible:false,
	        fitColumns:true,
	        scrollbarSize:0,
	        url:'/integral/getIntegral',
	        sortName: 'crdt',
	        sortOrder: 'desc',
	        columns:[[
	                  {field:'contactid',title:'客户编号',width:100},  
	                  {field:'corderid',title:'订单编号',width:100},  
		              {field:'startdt',title:'积分有效期开始日期',width:100},  
		              {field:'enddt',title:'积分有效期结束日期 ',width:100,align:'right'},
		              //{field:'status',title:'积分状态',width:100,align:'right'},
		              {field:'pointvalue',title:'获得积分',width:100,align:'right'},
		              {field:'crusr',title:'创建人',width:100,align:'right'},
		              {field:'crdt',title:'创建时间',width:100,align:'right'}
	        ]],
	        remoteSort:false,
	        singleSelect:false,
	        pagination:true,
	        rownumbers:true,
	       queryParams: {
	    	   customerId:$("#customerId").val(),
	    	   name:$("#name").val()
	    }
	   	        
	    });

	    var p = $('#getIntegralTable').datagrid('getPager');
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
//	$('#getIntegralTable').datagrid('load',{
//		customerId:$("#customerId").val(),
// 	   name:$("#name").val()
//});
}

//获取可用积分
function getAvailableIntegral(){
	  $.post("/integral/getAvailableIntegral",{  
		     customerId:$("#customerId").val(),
		     name:$("#name").val()
	  },function(data){
		  $("#availableIntegral").html(data.integral);
      });
}

//获取已使用积分
function getSubtractionIntegral(){
	  $.post("/integral/getSubtractionIntegral",{  
		     customerId:$("#customerId").val(),
		     name:$("#name").val()
	  },function(data){
		  $("#subtractionIntegral").html(data.integral);
   });
}



function cancleIntegral(){
	$("#name").attr("value",'');
	$("#customerId").attr("value",'');
}