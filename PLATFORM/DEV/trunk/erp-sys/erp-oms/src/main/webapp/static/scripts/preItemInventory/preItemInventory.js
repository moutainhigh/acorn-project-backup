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

	
    $('#dg').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height: 400,
        striped: true,
        border: true,
        collapsible:true,
        fitColumns:true,
        scrollbarSize:0,
        columns:[[
            {field:'id',title:'序列',width:60,editor:'text'},
            {field:'sourceId',title:'渠道',width:60,editor:'text',
                formatter: function(value) {
                    for(var i=0; i<sources.length; i++){
                        if (sources[i].id == value) return sources[i].name;
                    }
                    return value;
                }
            },
            {field:'numIid',title:'商品数字ID',width:115,editor:'text'},
            {field:'skuId',title:'渠道商品ID',width:80,editor:'text', formatter:function(val,rec){
            	if(val===undefined) return val;
            	if( val.length >8){
            	      return val.substr(0,8)+"...";	
            	}else{
            		 return val;
            	}
            }},
            {field:'outSkuId',title:'商品编码',width:60,editor:'text', formatter:function(val,rec){
            	if(val===undefined) return val;
            	if( val.length >5){
            	      return val.substr(0,5)+"...";	
            	}else{
            		 return val;
            	}
            }},
            {field:'skuProps',title:'商品属性',width:60,editor:'text', formatter:function(val,rec){
            	if(val===undefined) return val;
            	if( val.length >6){
            	      return val.substr(0,6)+"...";	
            	}else{
            		 return val;
            	}
            	}},
            {field:'skuTitle',title:'商品标题',width:60,editor:'text', formatter:function(val,rec){
            	if(val===undefined) return val;
            	if( val.length >4){
            	      return val.substr(0,4)+"...";	
            	}else{
            		 return val;
            	}
            	}},
            {field:'skuStatus',title:'商品状态',width:60,editor:'text'},
            {field:'skuPrice',title:'销售价格',width:60,editor:'text'},
            {field:'quantity',title:'库存数量',width:60,editor:'text'},
            {field:'st_status',title:'库存状态',width:60,editor:'text'},
            {field:'skuPrice',title:'销售价格',width:60,editor:'text'},
            {field:'customerId',title:'客户编码',width:60,editor:'text'},
            {field:'onlineTime',title:'上架时间',width:75,editor:'datebox',
                formatter: function(value) {
                    if(value != null){
                        var date = new Date(value);
                        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                    }
                   
                    return value;
                    
                    
                }
            },
            {field:'offlineTime',title:'下架时间',width:75,editor:'datebox',
                formatter: function(value) {
                    if(value != null){
                        var date = new Date(value);
                        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                    }
                    return value;
                }
            },
            {field:'created',title:'创建日期',width:75,editor:'datebox',
                formatter: function(value) {
                    if(value != null){
                        var date = new Date(value);
                        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                    }
                    return value;
                }
            }
        ]],
        frozenColumns:[[
            {field:'ck',checkbox:true}
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: false,
        pagination:true,
        rownumbers:true,
        queryParams: {
            skuId: $("#ti").val(),
            outSkuId: $("#si").val(),
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
    		if(value !="undefined"&&(field=="outSkuId" && value.length>8) || (field=="skuTitle" && value.length >4) || (field=="skuId" && value.length>8)|| (field=="skuProps" && value.length>4)){
        
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
            skuId: $("#ti").val(),
            outSkuId: $("#si").val(),
            startDate: $("#sd").datetimebox('getValue'),
            endDate: $("#ed").datetimebox('getValue')
        });
    });

    var d1 = new Date();
    $('#sd').datetimebox('setValue', d1.getFullYear()+'-'+(d1.getMonth()+1)+'-'+d1.getDate()+' 00:00:00');
    $('#ed').datetimebox('setValue', d1.getFullYear()+'-'+(d1.getMonth()+1)+'-'+d1.getDate()+' 23:59:59');
});
