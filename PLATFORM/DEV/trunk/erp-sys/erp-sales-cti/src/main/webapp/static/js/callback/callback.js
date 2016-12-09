$(document).ready(function(){
   console.info("+++++++"+1);
    $("#c_leadId").val(parent.phone.leadId);
    console.info("+++++++"+2+ $("#c_leadId").val());
    $("#c_leadInterId").val(parent.phone.leadInterId);
    $("#c_instId").val(parent.phone.instId);
    $("#c_acdgroup").val(parent.phone.acdId);
    $("#c_ani").val(parent.phone.ani);
    $("#c_dnsi").val(parent.phone.dnsi);
    $("#c_phn1").val(parent.phone1);
    $("#c_phn2").val(parent.phone2);
    $("#c_phn3").val(parent.phone3);
    $("#c_time_length").val(parent.phone.time_length);
    $("#show_callback_quit").hide();
    $("#show_callback_save").show();

});

function addCallbackProduct(nccode, spellcode) {
    $("#tbSpellcode").val(spellcode);
}

function savaCallBack(){
    console.info("+++++++"+3+ $("#c_leadId").val());
    $("#callbackHookMsg").html("");
    console.info("callback.ani:"+parent.phone.ani);
    if(parent.phone.ani == ""){
        console.info("callback.ani_return:"+parent.phone.ani);
        return;
    }
    if(parent.checkSoftPhoneApplet()){
        if ($("#ctiPhoneType", window.parent.document).val()==1){
            if(!parent.getDnStatus(null)){
                 parent.msgSlide("电话没有挂机，请挂机！！");
                //$("#callbackHookMsg").html("电话没有挂机，请挂机！！");
                return;
            }else{
                parent.phone.changeStatus(7);
            }
        }
    }
    window.parent.phone.insure='';//赠险文字清空
    console.info("+++++++"+4+ $("#c_leadId").val());
    if($("#c_leadId").val()){ $("#c_leadId").val(parent.phone.leadId); }

    if($("#c_leadId").val()){

        console.info("leadID:"+$("#c_leadId").val());
    }else{
        $("#show_callback_quit").show();
        $("#show_callback_save").hide();
        console.info("信息异常..");
        return ;
    }
    if (!$("#formCallback").form('validate')) {
        console.info("表单验证未通过..");
    } else {
        $.post("/common/callBack", {
            name: $("#name").val(),
            sex: $('input[name="sex"]:checked').val(),
            customerId: $("#customerId").val(),
            customerType: $("#customerType").val(),
            spellcode: $("#tbSpellcode").combobox('getValue'),
            callbackDt: $("#callbackDt").datetimebox("getValue"),
            callbackRemark: $("#callbackRemark").val(),
            callbackPriority: $("#callbackPriority").combobox('getValue'),
            h_instId:$("#c_instId").val(),
            leadInterId:$("#c_leadInterId").val(),
            leadId:$("#c_leadId").val(),
            acdgroup: $("#c_acdgroup").val(),
            ani:$("#c_ani").val(),
            dnsi:$("#c_dnsi").val(),
            phn1:$("#c_phn1").val(),
            phn2:$("#c_phn2").val(),
            phn3:$("#c_phn3").val(),
            time_length:$("#c_time_length").val()
    },function(data){
           if(data.result){
           //清空数据
            $("#c_instId").val("");
            $("#c_leadInterId").val("");
            $("#c_leadId").val("");
            $("#c_acdgroup").val("");
            $("#c_ani").val("");
            $("#c_dnsi").val("");
            $("#c_phn1").val("");
            $("#c_phn2").val("");
            $("#c_phn3").val("");
            $("#c_time_length").val("");
               clearCallbackForm();
           //重置软电话
              // parent.phone.changeStatus(0);
               if(parent.checkSoftPhoneApplet()){
                   parent.autoReady = true;
                   if(parent.callbackIsRelease != 0) {
                    parent.release();
                   }
                   parent.ready();

               }else{
                   parent.phone.changeStatus(0);
               }
               parent.phone.ani = "";
           }else{
              //alert(data.msg);
           }

        });
    }

}



function clearCallbackForm(){

    $("#name").val("");
    $("#customerId").val("");
    $("#customerType").val("");
    $("#callbackDt").datetimebox("setValue","");
    $("#callbackRemark").val("");
    $("#callbackPriority").combobox('setValue',"1");
    $("#tbSpellcode").combobox('clear');
}

function clearCallbackForm2(){
    parent.msgConfirm("操作提示", "真的要清除数据？", function(yes) {
        if(yes){
            clearCallbackForm();
        }
    });
}

function callback_findcustomer(){
    //打开客户查询界面
    parent.queryCustomer('tab_CallbackTab','callback_findcustomer_callback','callback',parent.phone2,true);
    parent.findCustomer();

}

function callback_findcustomer_callback(customerId,customerType,name,sex){
    if(name == "undefined"){
       name = "";
    }
    $("#name").val(name);
    $("#customerId").val(customerId);
    $("#customerType").val(customerType);
    if(sex == "undefined"){
        $('input:radio[name=sex]')[0].checked = false;
        $('input:radio[name=sex]')[1].checked = false;
    }else if(sex == 1){
        $('input:radio[name=sex]')[0].checked = true;
    }else if(sex == 2){
        $('input:radio[name=sex]')[1].checked = true;
    }else{
        $('input:radio[name=sex]')[0].checked = false;
        $('input:radio[name=sex]')[1].checked = false;
    }

    //性别


}


function formatPrice(price) {
    if(price==null||price=='')
        return '0.00';
    var prc=price;
    return prc.toFixed(2);
}

function formatNames(nameList,val) {
    for(var i=0;i<nameList.length;i++)
    {
        if(nameList[i].id.id==val)
            return nameList[i].dsc;
    }
    return val;
}

function viewOrder(listName, index, orderId,canedit) {
    var url='myorder/orderDetails/get/'+orderId+'?activable=false';
    window.parent.addTab('myorder_'+orderId,'订单：'+orderId,'false',url);
}

$(function(){
    //客户订单历史
    $('#orderHistory').datagrid({
        view: detailview,
        detailFormatter:function(index,row){
            return '<div style="padding:2px"><table id="ddv-' + index + '"></table></div>';
        },
        onExpandRow: function(index,row){
            $('#ddv-'+index).datagrid({
                fitColumns:true,
                singleSelect:true,
                scrollbarSize:-1,
                loadMsg:'',
                height:'auto',
                columns:[[
                    {field:'prodId',title:'商品编号',width:100,align:'center'},
                    {field:'prodName',title:'商品名称',width:200,align:'center'},
                    {field:'typeName',title:'规格',width:100,align:'center'},
                    {field:'price',title:'价格',width:100,align:'right',formatter:function(value){
                        return formatPrice(value);
                    }
                    },
                    {field:'quantity',title:'数量',width:100,align:'center'},
                    {field:'soldwith',title:'销售方式',width:100,align:'center'}
                ]],
                onResize:function(){
//                    $('#orderHistory').datagrid('fixDetailRowHeight',index);
                },
                onLoadSuccess:function(){
                    setTimeout(function(){
                        $('#orderHistory').datagrid('fixDetailRowHeight',index);
                    },200);
                }
            });
            $('#ddv-'+index).datagrid("loadData",{"total":row['contactOrderDetailFrontDtos'].length,"rows":row['contactOrderDetailFrontDtos']});
//            $('#dg').datagrid('fixDetailRowHeight',index);
        },
        width : '100%',
        height : 315,
        fitColumns : true,
        scrollbarSize:-1,
        url : '/myorder/myorder/query_contact',
        queryParams : {
            'contactId' : $('#customerId').val()
        },
        onBeforeLoad:function(data)
        {
            if($('#customerType').val()!='1')
                return false;
        },
        rowStyler: function(index,row)
        {

            var rowStyle='';
            switch(row.status)
            {
                case "0": //cancel
                case "10":
                    rowStyle = 'background-color:#cecece;text-decoration: line-through;'; break;
                case "8": //物流取消
                    rowStyle = 'background-color:#A2BADD;text-decoration: line-through;'; break;
                case "1": //new
                    rowStyle='background-color:#FFC0C0';break;
                case "5": //finish
                    rowStyle='background-color:#C0FFC0';break;

                default:rowStyle='';break;

            }
            return rowStyle;
        },
        columns : [ [
            {
                field : 'orderid',
                title : '订单编号',
                align:'center',
                width : 80 ,
                formatter:function (value,row,index){
                    var canedit=0;
                    if(row.right.indexOf("MODIFY")!=-1||row.right.indexOf("ALL")!=-1)
                    {
                        canedit=1;
                    }
                    var token='<a class="link" href="javascript:void(0);" onclick="javascript:viewOrder('+'\'myOrderlist\','+index+','+row.orderid+','+canedit+')" > ';
                    token+=value;
                    token+='</a>';

                    return token;
                }

            }, {
                field : 'crdt',
                title : '订单生成时间',
                width : 120
            },{
                field : 'paytype',
                title : '支付类型',
                align:'center',
                width : 80,
                formatter : function (value,row,index)
                {
                    return formatNames(orderPayTypeList,value);
                }
            }, {
                field : 'status',
                title : '订单状态',
                align:'center',
                width : 80,
                formatter : function (value,row,index)
                {
                    if(value==10)
                        return "作废";
                    return formatNames(orderStatusList,value);
                }
            }, {
                field : 'totalprice',
                title : '订单金额',
                width : 80,
                align:'right',
                formatter:function(value){
                    return formatPrice(value);
                }
            }, {
                field : 'crusr',
                title : '生成坐席',
                align:'center',
                width : 80
            }, {
                field : 'grpName',
                title : '坐席组',
                align:'center',
                width : 140
            }, {
                field : 'mailid',
                title : '包裹单号',
                align:'center',
                width : 120
            },
            {
                field : 'fbdt',
                title : '反馈日期',
                width : 150,
                align:'center'
            },
            {
                field : 'logisticsStatusId',
                title : '物流状态',
                align:'center',
                width : 150,
                formatter : function (value,row,index)
                {
                    return formatNames(orderLogisticsList,value)
                }
            },
            {
                field : 'contactOrderDetailFrontDtos',
                title : '商品明细',
                hidden:true
//                formatter:function (value,row,index){
//                    return '<a>'+row.id+'</a>';
//                }
            }
        ] ],
        remoteSort : false,
        singleSelect : true,
        pagination : true,
        onLoadSuccess: function(data){
            if(data.err!=null)
            {
                window.parent.alertWin('系统提示', data.err);
            }
        },
        onLoadError: function(){
            window.parent.alertWin('系统提示', "加载失败");
        }
    });

    $("#callbackHookMsg").html("");
    if(parent &&
       parent.phone &&
       parent.phone.dnsi) {
        $.post("/product/recommend/incoming", { tollFreeNum: parent.phone.dnsi }, function(data){
            var n = -1;
            $("#tbSpellcode").combobox({
                raws: data.details,
                data: data.details,
                valueField: 'spellcode',
                textField: "spellname",
                formatter: function(row){
                    n++;
                    return (n % 2 == 0) ?
                        "<p class='ac_even'>"+ $(row).attr("nccode") +" "+ $(row).attr("spellname") +"</p>":
                        "<p class='ac_odd'>"+ $(row).attr("nccode") +" "+ $(row).attr("spellname") +"</p>";
                },
                onBeforeLoad: function(){
                    n = -1;
                }
            });
        });
    }

    $("#tbSpellcode").parent().find(".combo-text").autocomplete("/product/search", {
        minChars: 2,
        max: 12,
        width: 130,
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
            return $(row).attr("nccode") +' '+$(row).attr("spellname");
        },
        formatResult: function(row) {
            //alert(JSON.stringify(row));
            return $(row).attr("spellname");
        }

    }).result(function (e, row){
        var options = $("#tbSpellcode").combobox("options");
        if(options && $.isArray(options.raws)) {
            var data = [];
            var exists = false;
            for(var i = 0; i < options.raws.length; i++){
                var spellcode = $(options.raws[i]).attr("spellcode");
                if(spellcode == $(row).attr("spellcode")){
                    exists = true;
                }
                data.push(options.raws[i])
            }
            if(!exists){
                data.push(row)
            }
            $("#tbSpellcode").combobox("clear");
            $("#tbSpellcode").combobox("loadData", data);
            $('#tbSpellcode').combobox('setValue', $(row).attr("spellcode"));
        }
    });
})

/**
 * 推出callback模式
 */
function qiutCallback(){
    if(parent.phone.status != 0){
        if(parent.phone.status == 4){
            parent.release();
        }else if(parent.phone.status ==6){
            parent.release();
        }
        parent.ready();
    }
    clearCallbackForm();
}