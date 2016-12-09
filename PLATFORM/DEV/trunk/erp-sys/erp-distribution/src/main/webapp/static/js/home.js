
function  removeFavorites (id,articleId) {
	$.post("favorite/removeFavotite", {
		"id":id,
		"articleId":articleId
	},function(data) {
		if (data.result=="1") {
			$('#myFavorite').datagrid('load', {
			});
			$('#hot').datagrid('load', {
			});
		}
		if (data.result=="0") {
			$.messager.alert('信息提示框',"取消异常");
		}
	},'json');
	
}

function  addFavorites (type,articleId,categoryId) {
	$.post("favorite/addFavotite", {
		"categoryId":categoryId,
		"type":type,
		"articleId":articleId
	},function(data) {
		if (data.result=="1") {
			$.messager.alert('信息提示框',"添加成功");
			$('#myFavorite').datagrid('load', {
			});
			$('#hot').datagrid('load', {
			});
		}
		if (data.result=="2") {
			$.messager.alert('信息提示框',"不可重复添加");
		}
		if (data.result=="0") {
			$.messager.alert('信息提示框',"添加异常");
		}
	},'json');
}

function view(id){
	if (id !=""&&id!=null) {
		$("#main-layout")
        .layout('panel','center').panel('refresh','/distribution/init?id='+id);	    
};      	
}
(function(){	
	/** 
	 * 时间对象的格式化; 
	 */  
	Date.prototype.format = function(format) {  
	    /* 
	     * eg:format="YYYY-MM-dd hh:mm:ss"; 
	     */  
	    var o = {  
	        "M+" :this.getMonth() + 1, // month  
	        "d+" :this.getDate(), // day  
	        "h+" :this.getHours(), // hour  
	        "m+" :this.getMinutes(), // minute  
	        "s+" :this.getSeconds(), // second  
	        "q+" :Math.floor((this.getMonth() + 3) / 3), // quarter  
	        "S" :this.getMilliseconds()  
	    // millisecond  
	    }  
	  
	    if (/(y+)/.test(format)) {  
	        format = format.replace(RegExp.$1, (this.getFullYear() + "")  
	                .substr(4 - RegExp.$1.length));  
	    }  
	  
	    for ( var k in o) {  
	        if (new RegExp("(" + k + ")").test(format)) {  
	            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]  
	                    : ("00" + o[k]).substr(("" + o[k]).length));  
	        }  
	    }  
	    return format;  
	}  
	

           $('#myFavorite').datagrid({
                url:'/favorite/myFavotite',
                singleSelect:true,
                fitColumns:true,
                scrollbarSize:-1,
                collapsible:true,
                method:'post',
               rowStyler: function(index,row){
//                       return "font-family: 'Microsoft Yahei',verdana;";
               },
				onDblClickRow:function(rowIndex, rowData) {
					view(rowData.articleId);
				},
                columns:[[
                   {field:'type',title:'模块',width:100,
                       styler: function(value,row,index){   
                           return 'padding:4px;';
                       },
                       formatter: function(value,row,index){
                    	   if (row.type == 1) {
                    		   return '<p>' +
                               '<b class="nui-ico ico ico-sjy"></b>&nbsp;&nbsp;' +
                               row.distributionArticle.distributionCategory.name+'</p>' +
                               '<p class="table-tips nui-txt-aside">产品的详细信息以及与其有关的话术</p>';
                    	   }else {
                    		   return '<p>' +
                               '<b class="nui-ico ico ico-sjy"></b>&nbsp;&nbsp;' +
                               row.distributionArticle.distributionCategory.name+'</p>' +
                               '<p class="table-tips nui-txt-aside">常见问答</p>';
                    	   }                         
                       }
                   },
                   {field:'productid',title:'标题',width:100,
                       styler: function(value,row,index){
                           return 'color:#3f3f3f;font-size:14px';
                       },
                       formatter: function(value,row,index){
                    	   if (row.type==1) {
                    		   return '<p>' +
                               '<strong>'+row.distributionArticle.productName+'</strong></p>' ;
                    	   }else {
                    		   return '<p>' +
                               '<strong>'+row.distributionArticle.title+'</strong></p>' ;
                    	   }                          
                       }},
                   {field:'createDate',title:'关注时间',width:40,align:'right',
                	   formatter : function(value,row,index) {
							var _sdate = new Date (row.createDate);
				        	return _sdate.format('yyyy-MM-dd hh:mm:ss');
						}},
                   {field:'unitcost',title:'操作',width:100,align:'center',
                       formatter: function(value,row,index){
                           return '<div id="nui-component2" tabindex="0" class="nui-btn">' +
                               '<span class="nui-btn-text" id="nui-component2-text" onclick="removeFavorites('+row.id+','+row.articleId+')">取消收藏</span></div>' ;
                       }

                   }
                ]]
           });
           
           

              
           
           $('#hot').datagrid({
                url:'/favorite/hotFavotite',
                singleSelect:true,
                fitColumns:true,
                scrollbarSize:-1,
                collapsible:true,
                method:'get',
               rowStyler: function(index,row){
                       return "font-family: 'Microsoft Yahei',verdana;";
               },
               onDblClickRow:function(rowIndex, rowData) {
					view(rowData.articleId);
				},
                columns:[[
                   {field:'type',title:'模块',width:100,
                       styler: function(value,row,index){
                           return 'padding:4px;';
                       },
                       formatter: function(value,row,index){
                    	   if (value == 1) {
                    		   return '<p>' +
                               '<b class="nui-ico ico ico-sjy"></b>&nbsp;&nbsp;' +
                               row.categoryName+'</p>' +
                               '<p class="table-tips nui-txt-aside">产品的详细信息以及与其有关的话术</p>';
                    	   }else {
                    		   return '<p>' +
                               '<b class="nui-ico ico ico-sjy"></b>&nbsp;&nbsp;' +
                               row.categoryName+'</p>' +
                               '<p class="table-tips nui-txt-aside">常见问答</p>';
                    	   }      
                       }
                   },
                   {field:'title',title:'标题',width:100,
                       styler: function(value,row,index){
                           return 'color:#222;font-size:14px';
                       },
                       formatter: function(value,row,index){
                           return '<p>' +
                               '<strong>'+value+'</strong></p>' ;
                       }},
                   {field:'times',title:'关注次数',width:40,align:'right'},
                   {field:'unitcost',title:'操作',width:100,align:'center',
                       formatter: function(value,row,index){
                    	   if (row.status==1){
                               return '<div id="nui-component2" tabindex="0" class="nui-btn">' +
                               '<span class="nui-btn-text" id="nui-component2-text" ">已收藏</span></div>' ;
                    	   }else {
                               return '<div id="nui-component2" tabindex="0" class="nui-btn">' +
                               '<span class="nui-btn-text" id="nui-component2-text" onclick="addFavorites('+row.type+','+row.articleId+','+row.categoryId+')">添加收藏</span></div>' ;
                    	   }
                       }

                   }
                ]]
           });


})()
