/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-5
 * Time: 下午1:43
 * To change this template use File | Settings | File Templates.
 */
$(function(){

    var sources = [
        {id:'2',name:'淘宝'},
        {id:'9',name:'橡果官方直营店'},
        {id:'10',name:'淘宝C店'},
        {id:'3',name:'京东(SOP)'},
        {id:'6',name:'京东(FBP)'},
        {id:'4',name:'卓越(SOP)'},
        {id:'8',name:'卓越(FBP)'}
    ];

    var coins = [
        {id:'1',name:'收入'},
        {id:'2',name:'支出'}
    ];
 
	
    $('#dg').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height: 430,
        striped: true,
        border: true,
        collapsible:true,
        fitColumns:true,
        scrollbarSize:0,
        columns:[[
            {field:'ruId',title:'序列',width:100,editor:'text'},
            {field:'sourceId',title:'渠道',width:100,editor:'text',
                formatter: function(value) {
                    for(var i=0; i<sources.length; i++){
                        if (sources[i].id == value) return sources[i].name;
                    }
                    return value;
                }
            },
            {field:'tradeId',title:'外部订单号',width:200,editor:'text'},
            {field:'shipmentId',title:'Agent单号',width:150,editor:'text'},
            {field:'settlementFlag',title:'费用类型',width:100,editor:'text',
                formatter: function(value) {
                    for(var i=0; i<coins.length; i++){
                        if (coins[i].id == value) return coins[i].name;
                    }
                    return value;
                }},
            {field:'settlementDesc',title:'费用项目',width:140,editor:'text'},
            {field:'revenue',title:'收入费用',width:80,editor:'text'},
            {field:'expenditure',title:'支出费用',width:125,editor:'text'},
            {field:'isrecognition',title:'确认否',width:90,editor:'text',
                formatter: function(val) {
                    return val=="1" ? "是" : "否";
                }
            }
        ]],
        frozenColumns:[[
            {field:'ck',checkbox:true}
        ]],
        remoteSort:false,
        singleSelect:false,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination:true,
        rownumbers:true,
        queryParams: {
            tradeId: $("#ti").val(),
            shipmentId: $("#si").val(),
            startDate: $("#sd").datebox('getValue'),
            endDate: $("#ed").datebox('getValue')
        },
        onLoadSuccess: function(data){
            //alert(JSON.stringify(data));

        },
        onLoadError: function(){
            alert("加载失败!");
        },
        onUncheck:function(index,data){
            $(this).datagrid("clearSelections");
        },
        onDblClickRow:function(rowIndex, rowData){
            //$("#lbShow").click();
        }
    });

    var boarddiv = "";
    $('#dg').datagrid({
    	mouseoverCell: function(index,field,value,e){
    		//if(value===undefined) return value;
    		if(value !="undefined"&&(field=="tradeId" && value.length>10) || (field=="settlementDesc" && value.length >10)){
        
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
    	}
    });
    
    var p = $('#dg').datagrid('getPager');
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

    $("#btnSearch").click(function () {
        $("#ti").val($("#ti").val().replace(/^\s+|\s+$/g, ""));
        $("#si").val($("#si").val().replace(/^\s+|\s+$/g, ""));
        $('#dg').datagrid('load',{
            tradeId: $("#ti").val(),
            shipmentId: $("#si").val(),
            startDate: $("#sd").datetimebox('getValue'),
            endDate: $("#ed").datetimebox('getValue')
        });
    });

    $("#lbDelete").click(function(){
        var rows = $('#dg').datagrid('getSelections');
        if (rows && rows.length > 0){
            $.messager.confirm('确认','当前选中的'+rows.length+'条记录，真的要删除吗? ',function(r){
                if (r){
                    for(var i = 0; i< rows.length; i++){
                        var row = rows[i];
                        $.post($('#dg').attr("destroyUrl"),{ ruId:row.ruId },function(result){
                            if (result.success){
                                var rowIndex = $("#dg").datagrid("getRowIndex",row);
                                if(rowIndex > -1){
                                    $("#dg").datagrid("deleteRow",rowIndex);
                                }
                                $('#dg').datagrid('reload');    // reload the user data
                            } else {
//                                $.messager.show({   // show error message
//                                    title: 'Error',
//                                    msg: result.errorMsg
//                                });
                                $.messager.alert('错误',result.errorMsg,'error');
                            }
                        },'json');
                    }
                }
            });
        }
        else
        {
            $.messager.alert("请先选择一条结算单记录!");
        }
    });

    $("#lbApprove").click(function(){
        var rows = $('#dg').datagrid('getSelections');
        if (rows && rows.length > 0){
            $.messager.confirm('确认','当前选中的'+rows.length+'条记录，真的要确认吗? ',function(r){
                if (r){
                    for(var i = 0; i< rows.length; i++){
                        var row = rows[i];
                        $.post($('#dg').attr("approveUrl"),{ ruId:row.ruId },function(result){
                            if (result.success){
                                $('#dg').datagrid('reload');    // reload the user data
                            } else {
//                                $.messager.show({   // show error message
//                                    title: 'Error',
//                                    msg: result.errorMsg
//                                });
                                $.messager.alert('错误',result.errorMsg,'error');
                            }
                        },'json');
                    }
                }
            });
        }
        else
        {
            $.messager.alert("请先选择一条结算单记录!");
        }
    });

    var d1 = new Date();
    $('#sd').datetimebox('setValue', d1.getFullYear()+'-'+(d1.getMonth()+1)+'-'+d1.getDate()+' 00:00:00');
    $('#ed').datetimebox('setValue', d1.getFullYear()+'-'+(d1.getMonth()+1)+'-'+d1.getDate()+' 23:59:59');
});
