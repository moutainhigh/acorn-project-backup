$(document).ready(function() {
    $(function () {
    $('#listAuditLogTable').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: 700,
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fit: true,
        url:'/union/accountHistory/json',
        //sortName: 'code',
        //sortOrder: 'desc',
        remoteSort:false,
        idField:'id',
        singleSelect:false,
        pagination:true,
        rownumbers:true,
        frozenColumns:[[
        {checkbox:false}
    ]],
    queryParams: {
    	status: $("#linkSelect").val()
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
    });
    
    
    $("#agreeSettlement").click(function(){
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
        	   url: "/admin/account", 
        	   data: "type=1&ids="+checkBoxVal, 
        	   success: function(o){
                     if(o.result){
                    	 alert("审核成功");
                    	 document.location.reload();
                   }
       	   } 
        	   }); 
          //-------------------------
      
    	});
});
    $("#disagreeSettlement").click(function(){
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
        	   url: "/admin/account", 
        	   data: "type=2&ids="+checkBoxVal, 
        	   success: function(o){
                     if(o.result){
                    	 alert("审核成功");
                    	 document.location.reload();
                   }
       	   } 
        	   }); 
          //-------------------------
      
    	});



var queryAuditData = function () {
    $('#listAuditLogTable').datagrid('load',{
    	status:  $("#linkSelect").val()
    });
    
    
    
};
