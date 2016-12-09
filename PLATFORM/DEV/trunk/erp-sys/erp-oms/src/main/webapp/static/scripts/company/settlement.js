/**
 * 承运上水单拍平
 * User: gaodejian
 * Date: 13-4-9
 * Time: 下午2:43
 * To change this template use File | Settings | File Templates.
 */
$(function(){

    var settleStates2 = [{id: 0, name: '未核销'},{id: 1, name: '已核销'}];

    function calcSettlement(grid){
        var rows = $(grid).datagrid("getSelections");
        var settlementTotal = 0;
        for(var i = 0; i < rows.length; i++){
            settlementTotal += rows[i].arAmount;
        }
        $("#lblSettlementTotal").text(settlementTotal);

        var paymentTotal = $("#lblPaymentTotal").text();
        if(isNaN(paymentTotal)){
            paymentTotal = 0;
            $("#lblPaymentTotal").text("0.00");
        }
        $("#lblBalanceTotal").text(paymentTotal - settlementTotal);
        if(paymentTotal < settlementTotal){
            $("#lblBalanceTotal").attr("style", "color:red");
        }
        else{
            $("#lblBalanceTotal").attr("style", "color:blue");
        }
    }

    $('#dg2').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height: '100%',
        striped: true,
        border: true,
        collapsible:true,
        fit: true,
        idField : 'id',
        columns:[[
            {field:'createDate',title:'结账反馈日期',width:100,editor:'text'},
            /*{field:'shipmentHeader',title:'出运单',width:100,hidden: true,
                formatter: function(obj,row,index) {
                    row.rfoutdat = $(obj).attr("rfoutdat");
                    row.shipmentId = $(obj).attr("shipmentId");
                    row.mailId = $(obj).attr("mailId");
            }},*/
            {field:'rfoutdat',title:'扫描出库日期',width:100,editor:'text'},
            {field:'shipmentId',title:'发运单号',width:100,editor:'text'},
            {field:'mailId',title:'邮件号',width:100,editor:'text'},
            {field:'arAmount',title:'应收金额',width:100,editor:'text'},

            {field:'companyId',title:'送货公司ID',width:100,editor:'text'

            },
            {field:'paymentDate',title:'打款时间',width:100,editor:'text'},

            {field:'isSettled',title:'状态',width:100,editor:'text',
                formatter: function(value) {
                    for(var i=0; i<settleStates2.length; i++){
                        if (settleStates2[i].id == value) return settleStates2[i].name;
                    }
                    return value;
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
        showFooter: true,
        onLoadSuccess: function(data){
            //alert(JSON.stringify(data));
            $(this).datagrid("clearSelections");
            calcSettlement(this);
        },
        onLoadError: function(){
            alert("加载失败!");
        },
        onSelect:function(rowIndex, rowData){
            if(rowData.isSettled == "1"){
                alert("当前结算已经核销，不能再进行核销拍平操作！");
                $(this).datagrid("unselectRow", rowIndex);
                $(this).datagrid("uncheckRow", rowIndex);
                return;
            }
            calcSettlement(this);
        },
        onUnselect:function(rowIndex, rowData){
            calcSettlement(this);
        },
        onDblClickRow:function(rowIndex, rowData){

        }
    });

    var p = $('#dg2').datagrid('getPager');
    $(p).pagination({
        pageSize: 100,
        pageList: [100,300,500],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    $("#btnSettlementSearch").click(function(){

        $("#tbShipmentId").val($("#tbShipmentId").val().replace(/^\s+|\s+$/g, ""));
        $("#tbOrderId").val($("#tbOrderId").val().replace(/^\s+|\s+$/g, ""));

        var params = {
            companyId: $("#tbCompanyId2").combobox('getValue'),

            shipmentId: $("#tbShipmentId").val(),
            ftStart: $("#tbFbStart").datebox('getValue'),
            ftEnd: $("#tbFbEnd").datebox('getValue'),

            orderId: $("#tbOrderId").val(),
            rfStart: $("#tbRfStart").datebox('getValue'),
            rfEnd: $("#tbRfEnd").datebox('getValue'),

            isSettled: $("#tbIsSettled2").attr('checked') == 'checked'
        }

        if(params.companyId && params.companyId!="" ||
           params.shipmentId && params.shipmentId ||
            params.ftStart && params.ftStart ||
            params.ftEnd && params.ftEnd ||
            params.orderId && params.orderId ||
            params.rfStart && params.rfStart ||
            params.rfEnd && params.rfEnd ||
            params.isSettled && params.isSettled != ""
           )
        {
            $('#dg2').datagrid('load', params);
        } else {
            alert("请至少选择一个查询条件");
        }
    });
});