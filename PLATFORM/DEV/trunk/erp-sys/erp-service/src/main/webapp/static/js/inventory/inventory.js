function removeFavorite(favoriteId){
   var win1 = $.messager.show({
        title:'提示',
        msg:'确定删除？<p class="winBtnsBar textC"><a class="window_btn" id="ok1" href="javascript:void(0)" onclick="">确定</a><a id="can1" class="window_btn ml10" href="javascript:void(0)" onclick="">取消</a> </p>',
        showType:'fade',
        timeout:0,
        style:{
            right:'',
            top:200,
            bottom:''
        }
    });
    $('#ok1').click(function(){
        $.post("/inventory/removeFavorite", { favoriteId: favoriteId }, function(data){
            $('#table-favorites').datagrid("reload");
            window.parent.alertWin('系统提示', data);
            win1.window('close') ;
        }, 'text');
    });
    $('#can1').click(function(){
        win1.window('close') ;
    });
//    $.messager.confirm("提示","确定删除？",function(){}).window('move',{top:100} );

//    if(confirm("真的要删除吗？")){
//        $.post("/inventory/removeFavorite", { favoriteId: favoriteId }, function(data){
//            $('#table-favorites').datagrid("reload");
//            window.parent.alertWin('系统提示', data);
//        }, 'text');
//    }


}

function addFavorite(nccode, ncfreename){
    var win2 = $.messager.show({
        title:'提示',
        msg:'确定要加入收藏？<p class="winBtnsBar textC"><a class="window_btn" id="ok2" href="javascript:void(0)" onclick="">确定</a><a id="can2" class="window_btn ml10" href="javascript:void(0)" onclick="">取消</a> </p>',
        showType:'fade',
        timeout:0,
        style:{
            right:'',
            top:200,
            bottom:''
        }
    });
    $('#ok2').click(function(){
        $.post("/inventory/addFavorite", { nccode: nccode, ncfree:"", ncfreename:ncfreename }, function(result){
            window.parent.alertWin('系统提示', result);

            if(result.indexOf("成功") > -1){
                $('#table-favorites').datagrid("reload");

            }
            win2.window('close') ;
        }, 'text');
    });
    $('#can2').click(function(){
        win2.window('close') ;
    });
//    if(confirm("真的要加入收藏吗？")){
//        $.post("/inventory/addFavorite", { nccode: nccode, ncfree:"", ncfreename:ncfreename }, function(result){
//            window.parent.alertWin('系统提示', result);
//
//            if(result.indexOf("成功") > -1){
//                $('#table-favorites').datagrid("reload");
//            }
//        }, 'text');
//    }
}

function showIntransit(nccode, ncfreename){
    $.post("/inventory/intransit", { nccode: nccode, ncfreename:ncfreename }, function(data){
        $('#table-intransit').datagrid("loadData", data);
        $("#dlg-intransit").window("open");
    });
}

$(function(){
	//我的关注收藏
    $('#table-favorites').datagrid({
        striped: true,
        border: true,
        collapsible:true,
        fitColumns:true,
        scrollbarSize:17,
        url : '/inventory/favorites',
        columns:[[
            {field:'ncCode',title:'商品编号',width:90},
            {field:'ncName',title:'商品名称',width:160},
            {field:'ncfreeName',title:'规格',width:100},
            {field:'availableQty',title:'在库数量',width:100},
            {field:'weekAvgSoldQty',title:'一周内平均销量',width:100},
            {field:'yesterdaySoldQty',title:'昨日销量',width:100},
            {field:'inTransitQty',title:'在途数量',width:100},
            {field:'eta',title:'预计到货时间(最近到货时间)',width:100,hidden: true},
            {field:'o',title:'操作',width:80, align:'center',
                formatter: function(value, row){
                    var nccode = row.ncCode;
                    var ncfreeName = row.ncfreeName;
                    if(!ncfreeName) ncfreeName = "";
                    return '<a href="javascript:void(0)" name="favorite" title="删除关注" onclick="removeFavorite('+ row.favoriteId +')" class="del"></a>&nbsp;'+
                            '<a href="javascript:void(0)" class="p_det ml10" name="intransit" title="预计到货" onclick="showIntransit(\''+ nccode +'\',\''+ ncfreeName +'\')"></a>';
                }
            }
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination : false,
        onLoadSuccess: function(data){
            //alert(JSON.stringify(data));
        },
        onLoadError: function(){
            window.parent.alertWin('系统提示', "收藏商品加载失败!");
        }
    });

	//热点商品
	$('#table-hots').datagrid({
        striped: true,
        border: true,
        collapsible:true,
        fitColumns:true,
        scrollbarSize:17,
        url : '/inventory/topsale',
        columns:[[
            {field:'ncCode',title:'商品编号',width:90},
            {field:'ncName',title:'商品名称',width:160},
            {field:'ncfreeName',title:'规格',width:100},
            {field:'availableQty',title:'在库数量',width:100},
            {field:'weekAvgSoldQty',title:'一周内平均销量',width:100},
            {field:'yesterdaySoldQty',title:'昨日销量',width:100},
            {field:'inTransitQty',title:'在途数量',width:100},
            {field:'eta',title:'预计到货时间(最近到货时间)',width:100,hidden: true},
            {field:'o',title:'操作',width:80, align:'center',
                formatter: function(value, row){
                    var nccode = row.ncCode;
                    var ncfreeName = row.ncfreeName;
                    if(!ncfreeName) ncfreeName = "";
                    return '<a href="javascript:void(0)" class="add" name="favorite" title="添加关注" onclick="addFavorite(\''+ nccode +'\',\''+ ncfreeName +'\')"></a>&nbsp;'+
                            '<a href="javascript:void(0)" class="p_det ml10" name="intransit" title="预计到货" onclick="showIntransit(\''+ nccode +'\',\''+ ncfreeName +'\')"></a>';
                }
            }
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination : false,
        onLoadSuccess: function(data){
            //alert(JSON.stringify(data));
        },
        onLoadError: function(){
            window.parent.alertWin('系统提示', "热点商品加载失败!");
        }
	});

    $('#dlg-intransit').window({
        title:'库存在途明细',
        width: 750,
        height: 500,
        top:50,
        modal:true,
        shadow:false,
        collapsible:false,
        minimizable:false,
        maximizable:false,
        closed:true,
        draggable:false
    });

    //在途列表
    $('#table-intransit').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height: 400,
        striped: true,
        border: true,
        scrollbarSize:16,
        collapsible:true,
        fitColumns:true,
        fit:true,
        idField:'nccode',
        columns:[[
            {field:'nccode',title:'产品编码',width:100,editor:'text'},
            {field:'ncfreeName',title:'规格',width:120,editor:'text'},
            {field:'warehouseName',title:'仓库',width:120,editor:'text'},
            {field:'transitQty',title:'在途量',width:120,editor:'text'},
            {field:'arriveDate',title:'预计到货日',width:120,editor:'text'}
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: false,
        selectOnCheck: false,
        pagination:false,
        rownumbers:false,
        onLoadSuccess: function(data){
            //alert(JSON.stringify(data));
        },
        onLoadError: function(){
            window.parent.alertWin('系统提示',"在途库存加载失败!");
        }
    });
});



Date.prototype.format = function(format){
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(),    //day
	"h+" : this.getHours(),   //hour
 	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
 	"q+" : Math.floor((this.getMonth()+3)/3),  //quarter
 	"S" : this.getMilliseconds() //millisecond
	};

	if(/(y+)/.test(format)) {
		format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
	}

 	for(var k in o){
		if(new RegExp("("+ k +")").test(format)){
 			format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
		}
	}
	return format;
};
