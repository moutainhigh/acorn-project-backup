(function($){
	function init(target, arranger){
        arranger = arranger || [];
		var state = $.data(target, 'datagrid');
        var data = state.data;
		var opts = state.options;
        createArranger(target);
		/**
		 * create filter component
		 */
		function createArranger(target){
			var dc = state.dc;
			var table = dc.header2.find('table.datagrid-htable');
            dc.view.addClass('arranger');
            // clear the old checkbox component
            table.find('input[type=checkbox]').remove();
            var mm = $('<div id="mm" style="width:150px;">' +
//                '<div id="select" data-options="iconCls:\'icon-checked\'">选中</div>' +
//                '<div  class="menu-sep"></div>'+
                '<div id="select" data-options="iconCls:\'icon-reload\'">选中&nbsp;|&nbsp;非选中</div>').appendTo(table);

            var  menu = $('<a style="height:18px;border-radius:0" href="#">排列</a>');
            menu.menubutton({
                menu: '#mm'
            });
            var isUp  = true;
            $('#select').bind('click', function(){
                $('#agentTb').attr("blockCheckable", "1");
                try{
                    var checkRows = $(target).datagrid('getChecked');
                    var allRows =  $(target).datagrid('getRows');
                    var data=$.data(target,"datagrid").data;
                    var view=$.data(target,"datagrid").options.view;
                    var _5c7=$.data(target,"datagrid").insertedRows;
                    if(isUp){
                        for(var i=checkRows.length-1;i>=0;i--){
                            var index =   $(target).datagrid('getRowIndex',checkRows[i]);
                            $(target).datagrid('uncheckRow',index);
                            $(target).datagrid('deleteRow',index);
                            }
                        for(var j=0;j<checkRows.length;j++){
                            view.insertRow.call(view,target,j,checkRows[j]);
                            _5c7.push(checkRows[j]);
                            $(target).datagrid('checkRow',$(target).datagrid('getRowIndex',checkRows[j]));
                        }
                        isUp = false;
                    } else{
                        for(var i=checkRows.length-1;i>=0;i--){
                            var index =   $(target).datagrid('getRowIndex',checkRows[i]);
                            $(target).datagrid('uncheckRow',index);
                            $(target).datagrid('deleteRow',index);
                        }
                        for(var j=0;j<checkRows.length;j++){
                            $(target).datagrid('appendRow',checkRows[j]);
                            $(target).datagrid('checkRow',$(target).datagrid('getRowIndex',checkRows[j]));
                        }
                        isUp = true;
                    }
                }
                finally{
                    $('#agentTb').removeAttr("blockCheckable");
                    calcAverageCount();
                }
            });
//            $('#unselect').bind('click', function(){
//                var checkRows = $(target).datagrid('getChecked');
//                for(var i=checkRows.length-1;i>=0;i--){
//                    var index =   $(target).datagrid('getRowIndex',checkRows[i]);
//                    $(target).datagrid('deleteRow',index);
//                }
//                for(var j=0;j<checkRows.length;j++){
//                    $(target).datagrid('appendRow',checkRows[j]);
//                    $(target).datagrid('checkRow',$(target).datagrid('getRowIndex',checkRows[j]));
//                }
//            });
            menu.appendTo(table.find('.datagrid-header-check'));
            table.find('.l-btn-text').css('top',0);
		}

	}
	
	$.extend($.fn.datagrid.methods, {
		enableEx: function(jq, arranger){
			return jq.each(function(){
				init(this, arranger);
			});
		}
	});

})(jQuery);