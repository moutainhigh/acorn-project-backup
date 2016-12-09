<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="refund_baseInfo">
    <form id="refundForm" action="" >
            <table class="" border="1" cellspacing="0" cellpadding="0"
                   width="100%">
                <tr style="height: 30px">
                    <td>退款分类</td>
                    <td><select id="refundtype" name="refundtype" class="easyui-combobox" required="true" style="width:134px" >
                        <option value="1" <c:if test="${refundsms.refundtype==1}">selected="selected"</c:if>>1.邮政退款</option>
                        <option value="2" <c:if test="${refundsms.refundtype==2}">selected="selected"</c:if>>2.网银退款</option>
                        <option value="3" <c:if test="${refundsms.refundtype==3}">selected="selected"</c:if>>3.信用卡退款</option>
                        <option value="4" <c:if test="${refundsms.refundtype==4}">selected="selected"</c:if>>4.支付宝退款</option>
                    </select>
                    </td>
                    <td >持卡人姓名</td>
                    <td><input  type="text" id="refund_holdername" value="${refundsms.holdername}"   class="easyui-validatebox" required="true"  maxlength="20"  missingMessage="持卡人姓名不能为空"  /></td>

                </tr>
                <input type="hidden" id="refund_id"    value="${refundsms.id}"/>
                <input type="hidden" id="refund_orderid" name="orderid" value="${refundsms.orderid}"/>
                <input type="hidden" id="refund_contactid"  name="contactid"  value="${refundsms.contactid}"/>
                <tr id="refund_customer" style="height: 30px">
                    <td>客户地址</td>
                    <td>
                        <select id="refund_customerAdd" class="easyui-combobox" required="true" maxlength="100" style="width:134px">
                            <c:forEach var="item" items="${addressList}">
                                <option value="${item.address}" <c:if test="${item.address == refundsms.address}">selected="selected"</c:if>>${item.address}</option>

                            </c:forEach>
                        </select>
                    </td>
                    <td>邮编</td>
                    <td>
                        <input  type="text" class="easyui-numberbox" value="${refundsms.zipcode}" minLength="6" maxlength="6" data-options="required:true" missingMessage="邮编不能为空" invalidMessage="邮编格式不正确" id="refund_email" <%--style="width:134px" --%> required="true" />
                    </td>
                </tr>
                <tr id="refund_bank" style="height: 30px">
                    <td>开户行</td>
                    <td>
                        <input  type="text" id="refund_bankname" value="${refundsms.bankname}"  name="refund_bankname" class="easyui-validatebox"  maxlength="100" required="true"  missingMessage="开户行不能为空" />
                    </td>
                    <td>银行卡</td>
                    <td>
                        <select type="text" id="refund_bankcardnum" class="easyui-combobox"  name="refund_bankcardnum" data-options="required:true,validType:'numbervalidator[\'refund_bankcardnum\',16,50]'"  maxlength="50"  missingMessage="银行卡号不能为空" invalidMessage="卡号格式无效" style="width:134px">
                            <c:if test="${!empty refundsms.bankcardnumMap}">
                                <option value="${refundsms.bankcardnum}"selected="selected">${refundsms.bankcardnumMap}</option>
                            </c:if>
                        </select>
                    </td>
                </tr>
                <tr style="height: 30px">
                    <td>退款金额</td>
                    <td>
                        <input type="text"  id="refund_money" class="easyui-numberbox" value="${refundsms.money}"  name="refund_money" data-options="required:true,validType:'numberregvalidator[\'^\\\\d+(?=\\\\.{0,1}\\\\d+$|$)\']'" missingMessage="退款金额不能为空"  invalidMessage="退款金额应为正数"  style="width:80px;"/>

                    </td>
                    <td>电话号码</td>
                    <td>
                        <select id="refund_phone" class="easyui-combobox" data-options="required:true,validType:'numbervalidator[\'refund_phone\',11,11]'" invalidMessage="手机格式无效"  style="width:134px"
                               name="refund_phone">
                            <c:forEach var="item" items="${phoneList}">
                                <option value="${item.phoneMask}" <c:if test="${item.phoneMask == refundsms.phone}">selected="selected"</c:if>>${item.phn2}</option>

                            </c:forEach>
                            </select>
                    </td>
                </tr>
                <tr style="margin:2px;padding: 2px">
                    <td>退款状态</td>
                    <td>
                        <input  type="text" id="refund_status" name="refund_status" value="${refundsms.status}" disabled="disabled" />
                    </td>

                </tr>
            </table>
    </form>
        </div>


        <p class="textC mt10">
            <a href="javascript:void(0);" id="refund_saves" onclick="refund_saves('1')" class="window_btn ml10">保存</a>
            <a href="javascript:void(0);" id="refund_sendSms"onclick="refund_saves('2')" class="window_btn ml10">发送短信</a>
            <a href="javascript:void(0);" id="refund_cancel" onclick="refund_saves('-1')" class="window_btn ml10">取消退款</a>
            <a href="javascript:void(0);" id="refund_exit" onclick="refund_exit()" class="window_btn ml10">退出</a>
        </p>
<script>
    var refund_status='';
    <c:if test="${!empty refundsms.status}">
        var temp=${refundsms.status};
        if(temp==0)
        {
            refund_status='-1';
        }
        else
        {
            refund_status=temp;
        }
    </c:if>

    $("#refund_customerAdd").combobox({
        onChange:function(newValue,oldValue){
        }
    });

    $('#refund_email').numberbox({
        precision:0,
        filter:function(e)
        {
            if(e.which==46|| e.which==45)
                return false;
        }
    });

    $('#refund_bankname').validatebox({required: true});
        //982545228
    /*$('#refund_bankcardnum').numberbox({
        precision:0,
        filter:function(e)
        {
            if(e.which==46|| e.which==45)
                return false;
        }
    });*/


    $('#refund_money').numberbox({
        precision:2,
        filter:function(e)
        {
            if(/*e.which==46||*/ e.which==45)
                return false;
        }
    });

function switchButton(status)
{
    if(status==''||status==null)
    {
        $("#refund_status").attr("value","");

        $('#refund_saves ').css({display:''});
        $('#refund_sendSms').css({display:'none'});
        $('#refund_cancel').css({display:'none'});

    }
    else if(status=='0'||status=='-1')
    {
        $("#refund_status").attr("value","已取消");

        $('#refund_saves ').css({display:''});
        $('#refund_sendSms').css({display:'none'});
        $('#refund_cancel').css({display:'none'});
    }
    else if(status=='1')
    {
        $("#refund_status").attr("value","未退款");

        $('#refund_saves ').css({display:''});
        $('#refund_sendSms').css({display:''});
        $('#refund_cancel').css({display:''});
    }
    else if(status=='2')
    {
        $("#refund_status").attr("value","已退款");

        $('#refund_saves ').css({display:'none'});
        $('#refund_sendSms').css({display:'none'});
        $('#refund_cancel').css({display:'none'});
    }
}

function switchView(newValue)
{
   if (newValue=='2'){
       $("#refund_customer").hide();
       $("#refund_customerAdd").combobox({required:false});
       $("#refund_email").numberbox({required:false});

       $("#refund_bank").show();
       $("#refund_bankname").validatebox({required: true});
       $("#refund_bankcardnum").combobox({required:true});

   }else if (newValue=='3' || newValue=='4'){
       $("#refund_customer").hide();
       $("#refund_customerAdd").combobox({required:false});
       $("#refund_email").numberbox({required:false});

       $("#refund_bank").hide();
       $("#refund_bankname").validatebox({required: false});
       $("#refund_bankcardnum").combobox({required:false});

       $('#refund_sendSms').css({display:'none'});
   }
   else
   {
       $("#refund_bank").hide();
       $("#refund_bankname").validatebox({required: false});
       $("#refund_bankcardnum").combobox({required:false});


       $("#refund_customer").show();
       $("#refund_customerAdd").combobox({required:true});
       $("#refund_email").numberbox({required:true});
   }
};

    $('#refundtype').combobox({
        onChange:function(newValue,oldValue){
            switchButton(refund_status);
            switchView(newValue);
        }
    });

    switchButton(refund_status);
    switchView(${refundsms.refundtype});
function refund_exit (){
    $('#drawbackDialog').window('close');
};

function formartResult(refundStatus)
{
    var phone = $("#refund_phone").combobox('getValue');
    if (phone==null||phone=='') {
        phone =  $("#refund_phone").combobox('getText');
    }
    var address=$('#refund_customerAdd').combobox('getValue');
    if(address==null||address=='')
    {
       address=$('#refund_customerAdd').combobox('getText');
    }

    var cardNum=$('#refund_bankcardnum').combobox('getValue');
    if(cardNum==null||cardNum=='')
    {
        cardNum=$('#refund_bankcardnum').combobox('getText');
    }

    var refundObj={
        id:$('#refund_id').val(),
        orderid:$('#refund_orderid').val(),
        contactid:$('#refund_contactid').val(),
        holdername:$('#refund_holdername').val(),
        bankcardnum: cardNum,
        bankname:$('#refund_bankname').val(),
        phone:phone,
        money:$("#refund_money").val(),
        status:refundStatus=='-1'?'0':refundStatus,
        refundtype:$("#refundtype").combobox('getValue'),
        address:address,
        zipcode:$('#refund_email').val()
    }
    return refundObj;
}
function refund_saves (type) {
    if(type=='0'||type=='1'||type=='2'||type=='-1')
    {
        //保存时，首先将不显示的控件的校验去掉
        if(!$("#refundForm").form("validate"))
        {
            console.debug("invalidate data!");
            return;
        }
        else
        {
            console.debug("data is ok!");
        }
    }

    var status = type;
    var url = "${ctx}/refundsms/saves";
    if(type=="0"||type=="-1")
    {
        url = "${ctx}/refundsms/cancel";
    }
    else if (type == "2") {
        url = "${ctx}/refundsms/sendSms";
    }

    var refundObj=formartResult(status);
    $.post(url, refundObj, function(data) {
        if (data.succ=="succ") {
            if(type=='2')
            {
                window.parent.alertWin('系统提示', "发送成功");
            }
            else if(type=='0'||type=='-1')
            {
                window.parent.alertWin('系统提示', "取消成功");
            }
            else
            {
                if(data.id!=null)
                {
                   $('#refund_id').val(data.id);
                }
                window.parent.alertWin('系统提示', "保存成功");
            }
            refund_status=status;

            switchButton(refund_status);
            switchView(refundObj.refundtype);
        }else {
           window.parent.alertWin('系统提示', data.msg);
        }
    }, 'json');

};

    $.extend($.fn.validatebox.defaults.rules,{
        numbervalidator:{
            validator: function(value,params)
            {
                //从对应的列表中获取，如果有，那么合法
                //如果没有，那么验证为11位数字
                var obj=document.getElementById(params[0]);
                var objs=$(obj).combobox('getData');

                if(objs!=null)
                {
                    for(var i=0;i<objs.length;i++)
                    {
                        if(value==objs[i].text)
                        {
                            return true;
                        }
                    }
                }

                if(value!=null&&value!='')
                {
                    if(isNum(value))
                    {
                        if(params[1]!=null&&params[2]!=null)
                        {
                           if(value.length>=params[1]&&value.length<=params[2])
                                return true;
                        }
                        else
                        {
                            return true;
                        }
                    }
                }
                return false;
            },message: '{1}'
        }
    });

    function isNum(num){
        var reNum =/^[0-9]+$/;
        return (reNum.test(num));
    }

    $.extend($.fn.validatebox.defaults.rules,{
        numberregvalidator:{
            validator: function(value,params)
            {
                if(value!=null&&value!='')
                {
                    var regexp = new RegExp(params[0]);
                    //var regexp = new RegExp("^\\d+(?=\\.{0,1}\\d+$|$)");
                    //console.debug("---"+\\d+(?=\\.{0,1}\\d+$|$)");
                    //var regexp = /^\d+(?=\.{0,1}\d+$|$)/;
                    //var result=regexp.test(value);
                    //console.debug("result:"+result+"---reg:"+params[0]);
                    if(!regexp.test(value))
                    {
                        return false;
                    }
                }

                return true;
            },message: '{1}'
        }
    });

</script>

