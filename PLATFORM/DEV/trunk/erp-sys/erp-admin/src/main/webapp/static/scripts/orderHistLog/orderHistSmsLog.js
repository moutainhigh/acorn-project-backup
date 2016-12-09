

function yulan(){

    var v_smsType=$("#v_smstype").val();//短信类型
    var v_smsName=$("#v_smsName").val();//短信名称
    var v_paytype=$("#v_paytype").combo('getValues')+""; //付款方式
    var v_shopName=$("#shopName").combobox('getText');//网点名字
    var v_setProId=$("#v_setProId").combobox('getText');//快递公司
    var v_sendDelyTime=$("#v_sendDelyTime").val();
    var v_smscontent=$("#v_smscontent").val();

    if(v_shopName==""){
        alert("请选择网店名称");
        return;
    }
    if(v_setProId==""){
        alert("请选择快递公司");
        return;
    }
    if(v_smsType=="2"){
       $("#v_smscontent").val(v_smscontent+"您在:("+v_shopName+")--所获宝贝,总金额：[P]元,订单号为[O],已经通过("+v_setProId+")快递公司传送到您身边。 快递件验证码：[R]如有疑问拨打4008886226询问.");

    }
    else
    {
        $("#v_smscontent").val(v_smscontent+"您在:("+v_shopName+")--所获宝贝,总金额：[P]元,订单号为[O],已经通过("+v_setProId+")快递公司传送到您身边。如有疑问拨打4008886226询问。");
    }
}

function SmstypeSave(){

     var v_id=$("#v_id").val();//编号
     var v_smsType=$("#v_smstype").val();//短信类型
     var v_smsName=$("#v_smsName").val();//短信名称
     var v_paytype=$("#v_paytype").combo('getValues')+""; //付款方式
     var v_ordertype=$("#v_ordertype").combo("getValues")+"";//订单类型
     var v_setProId=$("#LoginName").val();//创建人
    var v_setDate=$("#v_setDate").val();//修改时间
     var v_sendDelyTime=$("#v_sendDelyTime").val(); //延迟时间
     var v_smscontent=$("#v_smscontent").val();      //短信内容
    var strs= new Array(); //订单类型
    var strpay=  new Array(); //付款方式
    var upid=0;//是否可以添加
    var i= 0,p=0;
    if(confirm("确认保存当前信息")) {

    }else{return;}
    if(v_paytype.length==0){
        alert("请选择付款方式");
        return;
    } else
    {
        var strpaytype=v_paytype;//付款方式
        strpay=  strpaytype.split(",");
    }
    if(v_ordertype.length==0){
        alert("请选择订单类型");
        return;
    } else{
        var str=v_ordertype; //这是一字符串
        strs=str.split(","); //字符分割
    }
    if(v_sendDelyTime==""){
        alert("请填写延迟时间");
        return;
    }
    if(v_smsName==""){
        alert("短信名称不能为空");
        return;
    }
      if(v_smscontent==""){
          alert("短信内容不能为空");
          return;
      }

    var str =  $("#v_smscontent").val();
    var arrp = new Array();
    arrp = str.split("[P]");
    var arro=new Array();
    arro=str.split("[O]");
    var arr=new Array();
      arr=str.split("[R]");
    var result;
    if(arrp.length ==1 ){
        alert("请在内容信息中添加总金额参数[P](大写)");
        return;
    }
    if(arro.length ==1 ){
        alert("请在内容信息中添加订单编号参数[O](大写)");
        return;
    }
    if(arr.length ==1 && v_smsType==2){
        alert("请在内容信息中添加验证码参数[R](大写)");
        return;
    }
    var smscount=0;//当前订单类型信息的行数
    if(v_id==0) {

       /* for (i=0;i<strs.length ;i++ )
        {
            for(p=0;p<strpay.length;p++) {
            $.post("/admin/OrderTypeSmsLogCount", { v_ordertype:strs[i],v_paytype:strpay[i] } ,function(data){
                if(data.total>0){
                    smscount=1;
                    alert("订单类型信息所对应的付款方式已经存在,请重新选择！");
                }
              }) ;
            }
        }  */
         var sucrow=0;

        if(smscount==0){
         for (i=0;i<strs.length ;i++ )
          {
             for(p=0;p<strpay.length;p++) {
             $.ajax({
            type:"post", //请求方式
            url:"/admin/SaveOrderTypeSmsLog", //发送请求地址
            data:{ //发送给数据库的数据
                v_smstype:v_smsType,
                v_smsName:v_smsName,
                v_paytype:strpay[p], //付款方式
                v_ordertype:strs[i],//订单类型
                v_setProId:v_setProId,//快递公司
                v_sendDelyTime:v_sendDelyTime,// 延迟时间
                v_smscontent:v_smscontent     //短信内容
            },
            //请求成功后的回调函数有两个参数
            success:function(data,textStatus){
                if(textStatus=="success"){
                   sucrow+=1;
                }
             }
            });
           }
         }
            alert("保存成功!");
            closeinsert();
            queryOHistData();
        }


    }else{
        $.ajax({
            type:"post", //请求方式
            url:"/admin/UpdateOrderTypeSmsLog", //发送请求地址
            data:{ //发送给数据库的数据
                id:v_id,//编号
                v_smstype:v_smsType,
                v_smsName:v_smsName,
                v_paytype:v_paytype, //付款方式
                v_ordertype:v_ordertype,//订单类型
                v_setProId:v_setProId,//快递公司
                v_sendDelyTime:v_sendDelyTime,// 延迟时间
                v_smscontent:v_smscontent     //短信内容
            },
            //请求成功后的回调函数有两个参数
            success:function(data,textStatus){
                if(textStatus=="success"){
                    alert("修改成功!");
                }  else{
                    alert("修改失败!");
                }
            }
        });
        closeinsert();
        queryOHistData();

    }

}


