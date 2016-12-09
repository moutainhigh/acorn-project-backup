$(document).ready(function() {
	   var feeTypeData;
	    $.ajax({
	        url: '/basic/getFeeTypes',
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
	    $('#feeTypeId').combobox('setValue',null);
	    
      $('#listFeeTypeTable').edatagrid({
        width:600,
        height:250,
        rownumbers:true,
        remoteSort:false,
        nowrap:false,
        fitColumns:true,
        url:'/admin/queryUnionFeeTypes',
        saveUrl:'/admin/saveUnionFeeType',
        updateUrl:'/admin/saveUnionFeeType',
        columns:[[
            {field:'id',title:'编号',width:40},
            {field:'feeTypeName',title:'业务名称',width:80,editor:'text'},
            {field:'isActive',title:'激活',width:40,
                editor:{
                    type:'checkbox',
                    options:{
                        on: 'true',
                        off: 'false'
                    }
                }
            },
            {field:'fee',title:'佣金费率',width:40,editor:"text"},
            {field:'trackUrl',title:'链接代码',width:100}
        ]],
        remoteSort:false,
        idField:'id',
        singleSelect:false,
        pagination:true,
        rownumbers:true,
        frozenColumns:[[

        ]],
        queryParams: {
          feeTypeId: document.getElementsByName("feeTypeId")[0].value,
 		   unionName: $("#unionName").val()
        }
    });
    var p = $('#listFeeTypeTable').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });
   doRefresh();
});
var doRefresh = function(){	
    $('#listFeeTypeTable').datagrid('load',{
    	feeTypeId: document.getElementsByName("feeTypeId")[0].value,
    	unionName: $("#unionName").val()
    });
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


var queryData = function () {
	   $('#listFeeTypeTable').datagrid('load',{
		   feeTypeId: document.getElementsByName("feeTypeId")[0].value,
		   unionName: $("#unionName").val()
	    });
	   doRefresh();
	};
