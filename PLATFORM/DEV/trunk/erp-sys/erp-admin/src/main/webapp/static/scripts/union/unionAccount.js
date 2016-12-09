$(document).ready(function() {
    var bankNameData;
    $.ajax({
        url: '/basic/getAccountBanks',
        type: 'POST',
        dataType: 'json',
        async:false,
        success: function(data){
            bankNameData = data;
        }
    });

    $('#accountBankId').combobox({
        data:bankNameData,
        valueField:'id',
        textField:'name'
    });


    var typeNameData;
    $.ajax({
        url: '/basic/getAccountTypes',
        type: 'POST',
        dataType: 'json',
        async:false,
        success: function(data){
            typeNameData = data;
        }
    });

    $('#accountTypeId').combobox({
        data:typeNameData,
        valueField:'id',
        textField:'name'
    });


    var formData;
    $.ajax({
        url: '/union/accountJson',
        type: 'GET',
        dataType: 'json',
        async:false,
        success: function(data){
            formData = data;
        }
    });

    $("#unionAccountForm").form("load", formData);
    $('#unionAccountForm').form({
        url:'/union/saveUnionAccount',
        onSubmit:function(){
            return $(this).form('validate');
        },
        success:function(){
        	
        }
    });
    
    showSuccess = function(re){
    	//这里的 re 为服务器端的输出
    	if(re == 'true'){
    	alert('ok');
    	}else{
    	alert('error');
    	}
    	};

});
