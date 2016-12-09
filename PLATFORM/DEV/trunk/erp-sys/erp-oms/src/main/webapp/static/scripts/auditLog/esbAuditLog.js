$(document).ready(function() {
    $(function () {
    	$("#beginDate").datebox("setValue", currentTime());
    	$("#endDate").datebox("setValue", currentTime());
    	var v_company = eval('('+$('#v_company').html()+')');
        var boarddiv = "";
    	
    $('#listEsbAuditLogTable').edatagrid({
        title:'',
        iconCls:'icon-edit',
        width:'100%', 
        height: 420,
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fitColumns:true,
        scrollbarSize:0,
        url:'/admin/esbQueryAuditLog',
        destroyUrl: '/admin/esbQueryAuditLog',
        //sortName: 'code',
        //sortOrder: 'desc',
        columns:[[
            {field:'esbName',title:'ESB名称',width:150},
            {field:'esbDesc',title:'ESB描述',width:160,
                formatter:function(val,rec){
                	if(val===undefined) return val;
                	if( val.length >18){
                	      return val.substr(0,36)+"...";	
                	}else{
                		 return val;
                	}
                }},
            {field:'errorCode',title:'错误代码',width:180},
            {field:'errorDesc',title:'错误描述',width:200},
            {field:'companyId',title:'承运商',width:100,formatter:function(val,rec){
        		return getCompanyNameById(v_company,val);
        	}},
            {field:'crtDate',title:'产生日期',width:180},
            {field:'remark',title:'备注',width:200}
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
    	esbName: $("#esbName").val(),
    	errorCode: $("#errorCode").val(),
    	companyId: $("#companyId").val(),
    	beginDate: $("#beginDate").datebox('getValue'),
        endDate: $("#endDate").datebox('getValue')
        },
        mouseoverCell:function(index,field,value,e){
      		if(value===undefined) return value;
            if((field=="esbDesc" && value.length>18) ){
                var x = e.pageX -100;
                var y = e.pageY -100;
          	  if(boarddiv == ""){
          		boarddiv = "<div id='mydiv' style='background:lightgray;width:300;height:100;z-index:999;position:absolute;display:block;left:"+x+";top:"+ y+";margin-top:100px;'>"+value+"</div>"; 
            	    $(document.body).append(boarddiv);
          	  }else{
          		  $("#mydiv").css("display","block");
          		  $("#mydiv").html(value);
          		  $("#mydiv").css("left",x);
          		  $("#mydiv").css("top",y);
          		 // boarddiv = "<div id='mydiv' style='background:white;width:100;height:100;z-index:999;position:absolute;display:block;left:"+e.pageX+";top:"+ e.pageY+";margin-top:100px;'>"+value+"</div>"; 
          	   // $(document.body).append(boarddiv);
          	  }
          	  //alert(value);
            }else{
          	  if(boarddiv != "")  $("#mydiv").css("display","none");
            }
        }
    
    });
    

//    $('#listEsbAuditLogTable').datagrid({
//    	mouseoverCell: function(index,field,value,e){
//  
//    	}
//    });

    var p = $('#listEsbAuditLogTable').datagrid('getPager');
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

    });
});


var queryAuditData = function () {
    $('#listEsbAuditLogTable').datagrid('load',{
    	esbName: $("#esbName").val(),
    	errorCode: $("#errorCode").val(),
    	companyId: $("#companyId").val(),
    	beginDate: $("#beginDate").datebox('getValue'),
        endDate: $("#endDate").datebox('getValue')
    });
};

function getCompanyNameById(result,id){
	var m_r= "";
	 $.each(result.rows, function(i,val){ 
	      if(val.companyid == id){
	    	  m_r= val.name;
	      }
	  });   
	return m_r;
}

function currentTime(){
	var d = new Date(),str = '';
	str += d.getFullYear()+'-';
	str += d.getMonth() + 1+'-';
	str += d.getDate();
	//str += d.getHours()+'时'; str  += d.getMinutes()+'分';  str+= d.getSeconds()+'秒';  
	return str; 
}

