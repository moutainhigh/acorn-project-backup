/**
 * 承运上水单拍平
 * User: gaodejian
 * Date: 13-4-9
 * Time: 下午2:43
 * To change this template use File | Settings | File Templates.
 */
$(function(){

    var settleStates = [{id: 0, name: '未核销'},{id: 1, name: '部分核销'},{id: 2, name: '全部核销'}];
    var entities=[];

    $("#tbCompanyId").combobox({
        url:'/shipment/company/lookup/0',
        valueField: 'companyid',
        textField: 'name',
        onLoadSuccess:function(data){
            entities = data;

            $("#tbCompanyId2").combobox({
                data: data,
                valueField: 'companyid',
                textField: 'name'
            });
        }
    });

    function calcPayament(grid) {
        //计算数量
        var rows = $(grid).datagrid("getSelections");
        var paymentTotal = 0;
        for(var i = 0; i < rows.length; i++){
            paymentTotal += rows[i].amount - rows[i].settledAmount;
        }
        $("#lblPaymentTotal").text(paymentTotal);
        var settlementTotal = $("#lblSettlementTotal").text();
        if(isNaN(settlementTotal)){
            settlementTotal = 0;
            $("#lblSettlementTotal").text("0.00");
        }
        $("#lblBalanceTotal").text(paymentTotal - settlementTotal);
        if(paymentTotal < settlementTotal){
            $("#lblBalanceTotal").attr("style", "color:red");
        } else {
            $("#lblBalanceTotal").attr("style", "color:blue");
        }
    }

    $('#dg').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: '100%',
        height: '100%',
        striped: true,
        border: true,
        collapsible:true,
        fit: true,
        idField : 'id',
        columns : [ [ {
            field : 'paymentCode',
            title : '水单号',
            width : 120
        }, {
            field : 'companyContract',
            title : '承运商',
            width : 120,
            formatter: function(obj, row, index) {
                return obj ? obj.name : "";
            }
        }, {
            field : 'amount',
            title : '水单金额',
            width : 80,
            formatter: function(obj, row, index) {
                if(isNaN(row.settledAmount)){
                    row.settledAmount = 0;
                }
                row.balanceAmount = row.amount - row.settledAmount;
                return obj;
            }
        }, {
            field : 'settledAmount',
            title : '核销金额',
            width : 80
        }, {
            field : 'balanceAmount',
            title : '可核销余额',
            width : 80
        }, {
        }, {
            field : 'paymentDate',
            title : '打款时间',
            width : 100
        }, {
            field : 'isSettled',
            title : '状态',
            width : 60,
            formatter: function(value) {
                for(var i=0; i<settleStates.length; i++){
                    if (settleStates[i].id == value) return settleStates[i].name;
                }
                return value;
            }
        }, {
            field : 'companyAccountCode',
            title : '打款账号'
        }, {
            field : 'cpompanyAccountName',
            title : '户名',
            width : 100
        }, {
            field : 'createUser',
            title : '录入人员',
            width : 100
        }, {
            field : 'createDate',
            title : '录入时间',
            width : 100
        }
        ] ],
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
            calcPayament(this);
        },
        onLoadError: function(){
            alert("加载失败!");
        },
        onSelect:function(rowIndex, rowData){
            if(rowData.isSettled == "2"){
                alert("当前水单已经核销，不能再进行核销拍平操作！");
                $(this).datagrid("unselectRow", rowIndex);
                $(this).datagrid("uncheckRow", rowIndex);
                return;
            }
            calcPayament(this);
        },
        onUnselect:function(rowIndex, rowData){
            calcPayament(this);
        },
        onDblClickRow:function(rowIndex, rowData){

        }
    });

    var p = $('#dg').datagrid('getPager');
    $(p).pagination({
        pageSize: 100,
        pageList: [100,300,500],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    $("#btnPaymentSearch").click(function(){
        $("#tbPaymentCode").val($("#tbPaymentCode").val().replace(/^\s+|\s+$/g, ""));

        var params = {
            companyId: $("#tbCompanyId").combobox('getValue'),
            paymentCode: $("#tbPaymentCode").val(),
            paymentStart: $("#tbPaymentStart").datebox('getValue'),
            paymentEnd: $("#tbPaymentEnd").datebox('getValue'),
            isSettled: $("#tbIsSettled").attr('checked') == 'checked'
        }
        if(params.companyId && params.companyId != "" ||
            params.paymentCode && params.paymentCode != "" ||
            params.paymentStart && params.paymentStart != "" ||
            params.paymentEnd && params.paymentEnd != "" ||
            params.isSettled && params.isSettled != "" ){
            $('#dg').datagrid('load',params);
        }  else {
            alert("请至少选择一个查询条件");
        }
    });

    $("#btnConfirm").click(function(){


        var rows1 = $("#dg").datagrid("getSelections");
        if(rows1 == null || rows1.length == 0){
            alert("请至少选择一笔未核销水单");
            return;
        }

        var rows2 = $("#dg2").datagrid("getSelections");
        if(rows2 == null || rows2.length == 0){
            alert("请至少选择一笔未核销结算单");
            return;
        }
        //检查所以数据是否是同一送货公司


        if(!confirm("真的要进行核销吗？")) return;

        var comp = [];

        var ids1 = [];
        var amt1 = 0;
        for(var i = 0; i < rows1.length; i++){
            ids1.push(rows1[i].id);
            amt1 += (rows1[i].amount - rows1[i].settledAmount);

            if(comp.indexOf(rows1[i].companyContract.id) == -1){
                comp.push(rows1[i].companyContract.id);
            }
        }

        var ids2 = [];
        var amt2 = 0;
        for(var i = 0; i < rows2.length; i++){
            ids2.push(rows2[i].id);
            amt2 += rows2[i].arAmount;

            if(comp.indexOf(rows2[i].companyId) == -1){
                comp.push(rows2[i].companyId);
            }
        }

        if(comp.length > 1) {
            alert("所有核销水单和结算单的承运商必须是同一承运商");
            return;
        }

        if(amt1 < amt2){
            alert("当前选中水单合计金额("+amt1+")必须大于选中结算单合计金额("+amt2+")!");
            return;
        }

        $.post('/company/settle/confirm',{ ids1:ids1, ids2:ids2 },function(result){
            if (result.success){
                alert("水单核销拍平成功！");
                $('#dg').datagrid('reload');
                $('#dg2').datagrid('reload');
            } else {
//                $.messager.show({
//                    title: '错误',
//                    msg: result.errorMsg
//                });
                $.messager.alert('错误',result.errorMsg,'error');
            }
        },'json');
    });

});