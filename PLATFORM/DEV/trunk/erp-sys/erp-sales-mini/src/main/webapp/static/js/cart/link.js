//设置购物车中表格标题和内容列宽相等
(function($){
	 $.link = function(opt,callback){
		 
		 if (!opt) var opt = {};
		 var flag=true;
		 var array = $("[link]").toArray();
		 for(var i=0;i<array.length-1;i++)
		 {
		         for(var j=1;j<array.length;j++)
		         {
		                 if($(array[i]).attr('link')==$(array[j]).attr('link'))
		                 {
		                	 setCell($(array[i]),$(array[j]));
		                         flag=false;
		                         break;
		                 }
		         }    
		 }
		 
		 function setCell($obj1,$obj2){
//			 $obj2.width($obj1.width()+17);
			 $obj2.width($obj1.width());
			  var $cell1 = $obj1.find('tr:first div.table-cell');
//			  var $cell2 = $obj2.find('tr:first div.table-cell');
			  var $tr = $obj2.find('tr');
//			  $cell1.each(function(index){
////				  $(this).width($cell2.eq(index).width());
//				  $cell2.eq(index).width($(this).width());
//			  });
			  $tr.each(function(i){
				  
				  $(this).find('div.table-cell').each(function(index){
					 
					  $(this).width($cell1.eq(index).width());
//					  $(this).parent().width($cell1.eq(index).parent().width());
//					  $cell1.eq(index).width($(this).width());
				  });
				  
			  });
			  
		 }
		
		 
		
	 }
	
})(jQuery);

