(function($){
	function init(target, arranger){
        arranger = arranger || [];
		var state = $.data(target, 'datagrid');
        var data = state.data;
		var opts = state.options;
        var timeout;
        createArranger(target);
		/**
		 * create filter component
		 */
		function createArranger(target){
			var dc = state.dc;
			var table = dc.header1.find('table.datagrid-htable');
            dc.view.addClass('arranger');
            var  menubutton = $('<a id="selectButton" style="height:18px;border-radius:0;" href="#" class="m-btn l-btn l-btn-plain" group="" id="">' +
                '<span style="height:18px">' +
                   '<span class="l-btn-text" style="top: 0px;">区间</span>' +
                '</span>' +

                '</a>');
            var menu =  $('<div id="indexWrap" style="width: 99px;padding:0;  z-index: 110007; display:none" class="menu-top menu">' +
            '<input id="inputIndex1" class="easyui-numberbox" type="text" style="width:40px"/>~<input id="inputIndex2" class="easyui-numberbox" type="text" style="width:40px"/>' +
                '</div>');
            table.find('.datagrid-header-rownumber').append(menubutton).append(menu);

            $('#inputIndex1').keyup(function(){

//                var index1 =  eval($('#inputIndex1').val()==""?1:$('#inputIndex1').val());
//                var index2 =  eval($('#inputIndex2').val());
//
//                       $(target).datagrid('unselectAll');
//                         for(var i=index1;i<index2+1;i++){
//                           $(target).datagrid('selectRow',i-1);
//                       }
//                $(target).datagrid('scrollTo',index1);
            })

            function  onChange(){
//                alert(1);
                $('#agentTb').attr("blockCheckable", "1");
                try{
                    var index1 =  eval($('#inputIndex1').val()==""?1:$('#inputIndex1').val());
                    var index2 =  eval($('#inputIndex2').val());

                    $(target).datagrid('clearSelections');
                    $(target).datagrid('clearChecked');
                    for(var i=index1;i<index2+1;i++){

                        $(target).datagrid('selectRow',i-1);
                        $(target).datagrid('checkRow',i-1);
                    }
                    $(target).datagrid('scrollTo',index1);
                }
                finally{
                    $('#agentTb').removeAttr("blockCheckable");
                    calcAverageCount();
                }
            }


            $('#inputIndex2').keydown(function(){
                clearTimeout(timeout);
                timeout = setTimeout(onChange, 400);


            })

            $('#selectButton,#indexWrap').bind('click', function(e){
                $('#indexWrap').show();
                e.stopPropagation();
            });
            $('body').bind('click', function(){
                $('#indexWrap').hide();
            });
		}

	}
	
	$.extend($.fn.datagrid.methods, {
		enableNumEx: function(jq, arranger){
			return jq.each(function(){
				init(this, arranger);
			});
		}
	});

})(jQuery);