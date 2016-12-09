$(document).ready(function() {
    $('#tt').datagrid({
        width:500,
        height:250,
        rownumbers:true,
        remoteSort:false,
        nowrap:false,
        fitColumns:true,
        url:'/union/queryUnionFeeTypes',
        columns:[[
            {field:'id',title:'id',width:40,sortable:true},
            {field:'feeTypeName',title:'业务名称',width:100,sortable:true},
            {field:'isActive',title:'激活',width:80,align:'right',sortable:true},
            {field:'fee',title:'佣金费率',width:80,align:'right',sortable:true},
            {field:'trackUrl',title:'链接代码',width:150,sortable:true}
        ]]
    });

    doRefresh();
});
var doRefresh = function(){
    var feeTypeData;
    $.ajax({
        url: '/basic/getUnApplyFeeTypes',
        type: 'POST',
        dataType: 'json',
        async:false,
        success: function(data){
            feeTypeData = data;
        }
    });

    $('#feeTypeId').combobox({
        data:feeTypeData,
        valueField:'id',
        textField:'name'
    })  ;
    $('#tt').datagrid('load',{});
};

var doApply = function () {
    var id = $('#feeTypeId').combobox('getValue');
    var feeType = {"feeTypeId": id};
    $.ajax({
        url: '/union/applyUnionFeeTypes',
        type: 'POST',
        dataType: 'json',
        data:feeType,
        async:false,
        success: function(data){
            alert("提交申请成功。");
            doRefresh();
        }
    });
};
