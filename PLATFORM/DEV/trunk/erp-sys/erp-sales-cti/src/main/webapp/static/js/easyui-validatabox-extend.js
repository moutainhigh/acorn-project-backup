/**
 * author:haoleitao
 * 验证扩展
 */
/*手机号码验证*/
$.extend($.fn.validatebox.defaults.rules, {  
	 mobile:{// 验证手机号码
		 validator:function(value) {
		 return /^(13|14|15|18)\d{9}$/i.test(value);
		 },
		 message:'手机号码格式不正确'
		 },
     telephoneAreaCode:{//验证电话的区号
         validator:function(value) {
             return /^(\d{4}|\d{3})$/i.test(value);
         },
         message:'区号格式不正确'
     },
    telephone:{//验证电话号码
        validator:function(value) {
            return /^(\d{7,8})$/i.test(value);
        },
        message:'电话号码格式不正确'
    },
    telephoneExt:{//验证电话号码
        validator:function(value) {
            return /^(\d{4}|\d{3}|\d{2}|\d{1})$/i.test(value);
        },
        message:'分机号格式不正确'
    },
    zipCode:{//邮政编码
        validator:function(value) {
            return /^[0-9][0-9]{5}$/i.test(value);
        },
        message:'邮政编码格式不正确'
    },
    address_p:{
        validator:function(value,param) {
            var r=false;
            // alert($.fn.combobox.defaults.parser($(param[0]).combobox('getValue')));

            $.each($(param[0]).combobox('getData'), function(index,element){
                if(element.provinceName== value) r = true;
            });
            return r;
        },
        message:'地址无效'
    },
    address_c:{
        validator:function(value,param) {
            var r=false;
            // alert($.fn.combobox.defaults.parser($(param[0]).combobox('getValue')));

            $.each($(param[0]).combobox('getData'), function(index,element){
                if(element.cityName== value) r = true;
            });
            return r;
        },
        message:'地址无效'
    },
    address_t:{
        validator:function(value,param) {
            var r=false;
            // alert($.fn.combobox.defaults.parser($(param[0]).combobox('getValue')));

            $.each($(param[0]).combobox('getData'), function(index,element){
                if(element.countyName== value) r = true;
            });
            return r;
        },
        message:'地址无效'
    },
    address_a:{
        validator:function(value,param) {
            var r=false;
            // alert($.fn.combobox.defaults.parser($(param[0]).combobox('getValue')));

            $.each($(param[0]).combobox('getData'), function(index,element){
                if(element.areaName== value) r = true;
            });
            return r;
        },
        message:'地址无效'
    },
    istoday:{
        validator:function(value,param) {
            console.info("===="+param[0]);
            if(! param[0]){
                console.info("====7"+param[0]);
                return true;
            }
            var date1 = Date.parse(value.replace(/-/g,"/"));
            console.info("====date1:"+date1);
            var now = new Date();
            var date2Str= now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()+" 23:59:59";//
            console.info("====date2Str:"+date2Str);
            var date2 = Date.parse(date2Str.replace(/-/g,"/"));
            console.info("====date2:"+date2);
            console.info("====date2:"+(date1<date2));
            console.info("====date2:"+(now<date1));
            if(now<date1){
                if(date1<date2){
                    return true;
                }else{
                    return false;
                    console.info("====date3:"+11);
                }
            }else{
                console.info("====date4:"+11);
                return false;
            }

        },
        message:'时间格式不正确(只能选择当天)'
    },
    equals: {
        validator: function(value,param){

            return value == $(param[0]).val();
        },
        message: '两次密码输入不一致。'
    }




});

