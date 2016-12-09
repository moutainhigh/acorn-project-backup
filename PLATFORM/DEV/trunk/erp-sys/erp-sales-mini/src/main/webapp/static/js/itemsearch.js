/**
 * 商品搜索
 * User: gdj
 * Date: 13-5-24
 * Time: 下午1:47
 * To change this template use File | Settings | File Templates.
 */
$().ready(function(){

    $('#tbNcfree').combobox({
        data: [],
        valueField: 'ncfree',
        textField: 'ncfreename',
        onSelect: function(row){
            var nccode = $("#tbNccode").val();
            var ncfree = $(row).attr("ncfree");
            var ncfreename = $(row).attr("ncfreename");
            $.post("/inventory/sku", { nccode: nccode, ncfree:ncfree, ncfreename:ncfreename  }, function(data){
                if($.isArray(data) && data.length > 0){
                    var row = data[0];
                    if(row != null){
                        $("#tbPlucode").val($(row).attr("productCode"));
                        $("#tbListPrice").val($(row).attr("listPrice"));
                        if($.isNumeric($(row).attr("groupPrice"))){
                            $("#tbPriceScope").val(
                                Math.min($(row).attr("groupPrice"),$(row).attr("minPrice"))+"-"+
                                Math.max($(row).attr("groupPrice"),$(row).attr("maxPrice"))
                            );
                        } else {
                            $("#tbPriceScope").val(
                                $(row).attr("minPrice")+"-"+$(row).attr("maxPrice")
                            );
                        }
                        $("#tbStockItem").val($(row).attr("availableQty")+" ("+ ($(row).attr("status") >= 0 ? "无限制":"按库存销售") +")");
                    }
                }
            });
        }
    });

    $("#tbKeyword").focus().autocomplete("/product/search", {
        minChars: 2,
        max:12,
        autoFill: false,
        matchContains: false,
        matchSubset: false,
        scrollHeight: 260,
        highlight: function(formatted, term){
            return formatted;
        },
        parse: function(data){
            var parsed = [];
            if($.isArray(data)) {
                for (var i=0; i < data.length; i++) {
                    var row = data[i];
                    if(row){
                        parsed[parsed.length] = {
                            data: row,
                            value: null,
                            result: this.formatResult && this.formatResult(row, null)
                        };
                    }
                }
            }
            return parsed;
        },
        formatItem: function(row, i, max) {
            return '<span title="'+$(row).attr("spellcode")+'">'+$(row).attr("nccode") +' '+$(row).attr("spellname") +'</span>';
        },
        formatResult: function(row) {
            return $(row).attr("nccode");
        }

    })
     .result(function (e, row){
        if(!row) return; //分页

        $("#tbNccode").val($(row).attr("nccode"));
        $("#tbSpellcode").val($(row).attr("spellcode"));
        $("#tbSpellname").val($(row).attr("spellname"));
        $("#tbStockItem").val("0");
        $("#tbListPrice").val($(row).attr("listPrice"));

        if($.isNumeric($(row).attr("groupPrice"))){
            $("#tbPriceScope").val(
                Math.min($(row).attr("groupPrice"),$(row).attr("minPrice"))+"-"+
                Math.max($(row).attr("groupPrice"),$(row).attr("maxPrice"))
            );
        } else if($(row).attr("minPrice") && $(row).attr("maxPrice")) {
            $("#tbPriceScope").val(
                $(row).attr("minPrice") +"-"+$(row).attr("maxPrice")
            );
        }
        else
        {
            $("#tbPriceScope").val("0-0");
        }
        $("#tbNcfree").combobox("clear");
        $("#tbNcfree").combobox("loadData", []);

        $.post("/product/attributes", { nccode: $(row).attr("nccode") }, function(data){
            $("#tbNcfree").combobox("clear");
            if($.isArray(data) && data.length > 0){
                $("#tbNcfree").combobox("loadData",data);
            }
            else
            {
                $("#tbNcfree").combobox("loadData", []);
            }
            //设置规格初始值
            /*
            if($.isArray(data) && data.length > 0){
                $("#tbNcfree").combobox("setValue", $(data[0]).attr("ncfree"));
            }
            */
        });

        $.post("/inventory/item", { nccode: $(row).attr("nccode"), instId:getRecommandTaskId() }, function(data){
            if($.isArray(data) && data.length > 0){
                $("#tbStockItem").val($(data[0]).attr("availableQty")+" ("+ ($(data[0]).attr("status") >= 0 ? "无限制":"按库存销售") +")");
            }
        });

        $.post("/product/discourse", { nccode: $(row).attr("nccode") }, function(data){
            var html = $(data).attr("discourseHtmlContent");
            $("#lblDiscourseInfo").html(html ? html : "没有找到任何信息！");
//            if($('#lblDiscourseInfo').height()>=253){
//                $('#colBtn').show() ;
//            }else{
//                $('#colBtn').hide()};
        });

        if(appendRecommendItem && $(row).attr("nccode")){
            appendRecommendItem($(row).attr("nccode"), $(row).attr("spellname"), false);
        }
    });



    $('#lnkStockItem').click(function(){
        var nccode = $("#tbNccode").val();
        if(nccode){
            var ncfree = $('#tbNcfree').combobox("getValue");
            var ncfreename =  $('#tbNcfree').combobox("getText");
            $.post("/warehouse/sku", { nccode: nccode, ncfree:ncfree, ncfreename:ncfreename }, function(data){
                $('#dgStockItem').datagrid("loadData", data);
                $("#dlgStockItem").window("open");
            });
        } else {
            window.parent.alertWin('系统提示',"请先指定产品");

        }
    });

    $('#dlgStockItem').window({
        title:'商品库存明细',
        width: 750,
        height: 500,
        modal:true,
        shadow:false,
        collapsible:false,
        minimizable:false,
        maximizable:false,
        closed:true,
        draggable:false
    });

    $('#dgStockItem').datagrid({
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
        idField:'productId',
        columns:[[
            {field:'productCode',title:'产品编码',width:100,editor:'text'},
            {field:'productName',title:'产品名称',width:100,editor:'text'},
            {field:'spellCode',title:'产品简码',width:120,editor:'text'},
            {field:'spellName',title:'产品简称',width:120,editor:'text',hidden:true},
            {field:'ncfree',title:'规格编码',width:120,editor:'text',hidden:true},
            {field:'ncfreeName',title:'规格名称',width:120,editor:'text'},
            {field:'warehouse',title:'仓库名称',width:120,editor:'text',hidden:true},
            {field:'warehouseName',title:'仓库名称',width:120,editor:'text'},
            {field:'onHandQty',title:'在库量',width:120,editor:'text',hidden:true},
            {field:'availableQty',title:'可用量',width:120,editor:'text'},
            {field:'status',title:'备注',width:120,editor:'text', formatter:function(value) {
                return value == "-1" ? "按库存销售" : "无限制";
            }}
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: false,
        selectOnCheck: false,
        pagination:false,
        rownumbers:false,
        onLoadSuccess: function(data){
            $(this).parent().find(".datagrid-btable").tableGroup({sumColumn: 10 ,groupColumn: 1, groupClass:"panel-header"});
        },
        onLoadError: function(){
            window.parent.alertWin('系统提示',"加载失败!");
        }
    });

    $("#lnkAddFavorite").click(function(){
        var nccode = $("#tbNccode").val();
        if(nccode){
            var ncfree = $('#tbNcfree').combobox("getValue");
            var ncfreename =  $('#tbNcfree').combobox("getText");
            $.post("/inventory/addFavorite", { nccode: nccode, ncfree:ncfree, ncfreename:ncfreename }, function(result){
                window.parent.alertWin('系统提示',result);
                if(result.indexOf("成功") > -1){
                    var p_inventory = window.frames["p_inventory"];
                    if(p_inventory){
                        p_inventory.location.reload();
                    }
                }
            }, 'text');
        }
        else{
            window.parent.alertWin('系统提示',"添加收藏前，请先搜索出相关商品");
            $("#tbKeyword").focus();
        }
    });
})

function addCallbackProduct(){
    var p_CallbackTab = window.frames["p_CallbackTab"];
    if(p_CallbackTab){
        if($(p_CallbackTab).parent("div:visible")){
            if(p_CallbackTab.addCallbackProduct){
                p_CallbackTab.addCallbackProduct($("#tbNccode").val(), $("#tbSpellcode").val());
                return true;
            }
        }
    }
    return false;
}

function addShoppingCartProduct(){
    if(!addCallbackProduct()){
        var _currentPanel = $("#content").find("#center").find(".current").attr("id");
        if(_currentPanel){
            var _currentFrameId = _currentPanel.replace("tab_","p_");
            if(!$("#tbNccode").val()){
                window.parent.alertWin('系统提示',"请输入加入购物车的产品");
                return;
            }
            var options =$("#tbNcfree").combobox("getData").length
            if(options>0 && !$("#tbNcfree").combobox("getValue")){
                window.parent.alertWin('系统提示',"请选择加入购物车的产品规格");
                return;
            }
            var child = window.frames[_currentFrameId];
            child.eval('addProduct'+'("'+ $("#tbNccode").val()+'","'+$("#tbNcfree").combobox('getText')+'","'+$("#tbListPrice").val()+'")');
        }
    }
}