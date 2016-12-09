<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type=text/javascript src="${ctx}/static/js/common-ui.js"></script> 
<script>
$(function() {    
    $('#favoriteTable').datagrid({
        url:'/favorite/queryFavotites',
        title : '',
		iconCls : 'icon-edit',
		width : '100%',
		height : 440,
		nowrap : false,
		striped : true,
		border : true,
        fit:true,
        pagination:true,
		fitColumns:true,
		collapsible : false,
		scrollbarSize:0,
		rowStyler: function(index,row){
//           return "font-family: 'Microsoft Yahei',verdana;";
   		},
		onDblClickRow:function(rowIndex, rowData) {
			view(rowData[0].articleId);
		},
		 columns:[[
                   {field:'type',title:'模块',width:100,
                       styler: function(value,row,index){   
                           return 'padding:4px;';
                       },
                       formatter: function(value,row,index){
                    	   if (row[0].type == 1) {
                    		   return '<p>' +
                               '<b class="nui-ico ico ico-sjy"></b>&nbsp;&nbsp;' +
                               row[0].knowledgeArticle.knowledgeCategory.name+'</p>' +
                               '<p class="table-tips nui-txt-aside">产品的详细信息以及与其有关的话术</p>';
                    	   }else {
                    		   return '<p>' +
                               '<b class="nui-ico ico ico-sjy"></b>&nbsp;&nbsp;' +
                               row[0].knowledgeArticle.knowledgeCategory.name+'</p>' +
                               '<p class="table-tips nui-txt-aside">常见问答</p>';
                    	   }                         
                       }
                   },
                   {field:'productid',title:'标题',width:100,
                       styler: function(value,row,index){
                           return 'color:#3f3f3f;font-size:14px';
                       },
                       formatter: function(value,row,index){
                    	   if (row[0].type==1) {
                    		   return '<p>' +
                               '<strong>'+row[0].knowledgeArticle.productName+'</strong></p>' ;
                    	   }else {
                    		   return '<p>' +
                               '<strong>'+row[0].knowledgeArticle.title+'</strong></p>' ;
                    	   }                          
                       }},
                   {field:'createDate',title:'关注时间',width:40,align:'right',
                	   formatter : function(value,row,index) {
							var _sdate = new Date (row[0].createDate);
				        	return _sdate.format('yyyy-MM-dd hh:mm:ss');
						}},
                   {field:'unitcost',title:'操作',width:100,align:'center',
                       formatter: function(value,row,index){
                           return '<div id="nui-component2" tabindex="0" class="nui-btn">' +
                               '<span class="nui-btn-text" id="nui-component2-text" onclick="removeFavorites('+row[0].id+','+row[0].articleId+')">取消收藏</span></div>' ;
                       }

                   }
                ]]
   });
	var p = $('#favoriteTable').datagrid('getPager');
	$(p).pagination({
		pageSize : 10,
		pageList : [ 5, 10, 15 ],
		beforePageText : '第',
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function() {
			$(this).pagination('loading');
			$(this).pagination('loaded');
		}
	});	
});

function view(id){
	if (id !=""&&id!=null) {
		$("#main-layout")
        .layout('panel','center').panel('refresh','/knowledge/init?id='+id);	    
};      	
}

function  removeFavorites (id,articleId) {
	$.post("favorite/removeFavotite", {
		"id":id,
		"articleId":articleId
	},function(data) {
		if (data.result=="1") {
			$('#favoriteTable').datagrid('load', {
			});
		}
		if (data.result=="0") {
			$.messager.alert('信息提示框',"取消异常");
		}
	},'json');
	
}
</script>
<div class="easyui-panel" fit="true" border="false" style="padding:10px">
<div class="easyui-layout" fit="true">
    <div  data-options="region:'north',border:false">
        <div   class="c_block" style="margin:0">
        <P class="title" style="margin:0" id="categoryName">我的收藏</P>
            </div>
        </div>
    <div  data-options="region:'center',border:false">
        <table id="favoriteTable" cellspacing="0" cellpadding="0" data-options=''>

        </table>
    </div>
    </div>

  </div>