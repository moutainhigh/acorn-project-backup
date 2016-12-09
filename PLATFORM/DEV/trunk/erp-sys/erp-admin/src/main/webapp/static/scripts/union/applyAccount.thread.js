$(document).ready(function() {
    $(function () {
       $('#listAuditLogTable').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: 500,
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fit: true,
        url:'/union/applyAccount/json',
        //sortName: 'code',
        //sortOrder: 'desc',
        remoteSort:false,
        idField:'id',
        singleSelect:false,
        pagination:true,
        rownumbers:true,
        frozenColumns:[[
        {field:'ck',checkbox:true}
    ]],
    queryParams: {
    	type: $("#linkSelect").val()
        }
    });

    var p = $('#listAuditLogTable').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: 'page    total {pages}page',
        displayMsg: 'current {from} - {to} records total {total} records'
      });
    $("#applyAccountThread").click(function(){
    	var boxes = document.getElementsByName("ck");  
        var checkBoxVal="";
        
        for (var i = 0; i < boxes.length; i++)  
        {  
        if (boxes[i].checked)  
        {  
           checkBoxVal =checkBoxVal+ boxes[i].value+",";
        }
        }
        if(checkBoxVal=="" || checkBoxVal==null){
        	alert("请先选择!");
        	return ;
        }
           //----------------------
           $.ajax({ 
        	   type: "POST", 
        	   url: "/union/applyAccount/saveSettle", 
        	   data: "type=CPS&ids="+checkBoxVal, 
        	   success: function(o){
                     if(o.result){
                    	 alert("申请成功");
                    	 document.location.reload();
                     }
        	   } 
        	   }); 
          //-------------------------
      
    	});
    //-------------------------------------begin----------------------------------------
    $('#listSettleMentRequestTable').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: 500,
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fit: true,
        url:'/union/SettleMent/json',
        remoteSort:false,
        idField:'id',
        singleSelect:false,
        pagination:true,
        rownumbers:true,
        frozenColumns:[[
        {field:'settleMent_ck',checkbox:true}
    ]],
    queryParams: {
    	status: '0'
        }
    });

    var p = $('#listSettleMentRequestTable').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: 'page    total {pages}page',
        displayMsg: 'current {from} - {to} records total {total} records'
      });
      $("#applySettleMentThread").click(function(){
      	var boxes = document.getElementsByName("settleMent_ck");  
          var checkBoxVal="";
          
          for (var i = 0; i < boxes.length; i++)  
          {  
          if (boxes[i].checked)  
          {  
             checkBoxVal =checkBoxVal+ boxes[i].value+",";
          }
          }
          if(checkBoxVal=="" || checkBoxVal==null){
          	alert("请先选择!");
          	return ;
          }
             //----------------------
             $.ajax({ 
          	   type: "POST", 
          	   url: "/union/applyAccount/saveSettle", 
          	   data: "type=CPA&ids="+checkBoxVal, 
          	   success: function(o){
                       if(o.result){
                      	 alert("申请成功");
                      	 document.location.reload();
                       }
          	   } 
          	   }); 
            //-------------------------
        
      	});
    //---------------------------------------end--------------------------------------

    });
    
    
});


var queryAuditData = function () {
    $('#listAuditLogTable').datagrid('load',{
    	type: $("#linkSelect").val()
    });
};
