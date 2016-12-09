<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
    .addressConfirm_form-tabs addressConfirm table td{padding:5px;}
</style>
<div class="addressConfirm_form-tabs">
    <form id="addressConfirmForm" action="" >
    	<div class="baseInfo">
	        <table class="" border="0" cellspacing="1" cellpadding="1"
	               width="100%">
	            <tr>
	                <td style="padding:5px;">任务编号</td>
	                <td style="padding:5px;"><input type="text" disabled="disabled" value="${callbackTask.taskId}"/></td>
	                </td>
	                <td style="padding:5px;">分配时间</td>
	                <td style="padding:5px;"><input type="text" disabled="disabled" value="${callbackTask.dbdt}"/></td>
	            </tr>
	            <tr>
	                <td style="padding:5px;">客户编号</td>
	                <td style="padding:5px;">
	                    <%--<input id="addressConfirm_contactId" type="text" disabled="disabled" value="${contactId}"/>--%>
	                    <a class="link" id="addressConfirm_contactId" href="javascript:void(0);" onclick="addressConfirm_showContact('${contactId}')"> ${contactId}</a>
	                </td>
	                <td style="padding:5px;">客户姓名</td>
	                <td style="padding:5px;"><input type="text" disabled="disabled" value="${contactName}"/></td>
	            </tr>
	            <tr>
	                <td style="padding:5px;">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</td>
	                <td  style="padding:5px;" colspan="3"><input class="ellipsis" style="width: 355px" type="text" disabled="disabled" value="${callbackTask.note}"/>
	                </td>
	            </tr>
	            <tr>
	                <td style="padding:5px;">订单编号</td>
	                <td style="padding:5px;"><input type="text" disabled="disabled" value="${callbackTask.orderId}"/></td>
	                <td style="padding:5px;"></td>
	                <td style="padding:5px;">
	                   <a href="javascript:void(0);" id="addressConfirm_handle" class="window_btn ml10">开始处理</a>
	                   <a href="javascript:void(0);" id="addressConfirm_cancel" class="window_btn ml10">取消</a>
	                </td>
	            </tr>
	
	            <tr>
	                <td style="padding:5px;">省&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份</td>
	                <td style="padding:5px;"><input type="text" disabled="disabled" value="${address.provinceName}"/></td>
	                <td style="padding:5px;">城&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;市</td>
	                <td style="padding:5px;"><input type="text" disabled="disabled" value="${address.cityName}"/></td>
	            </tr>
	            <tr>
	                <td style="padding:5px;">区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;县</td>
	                <td style="padding:5px;"><input type="text" disabled="disabled" value="${address.countyName}"/></td>
	                <td style="padding:5px;">乡&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;镇</td>
	                <td style="padding:5px;"><input type="text" disabled="disabled" value="${address.areaName}"/></td>
	            </tr>
	            <tr>
	                <td style="padding:5px;">省市区镇</td>
	                <td style="padding:5px;" colspan="3"><input type="text" class="ellipsis" style="width: 355px" disabled="disabled" title="${address.receiveAddress}" value="${address.receiveAddress}"/>
	            </tr>
	            <tr>
	                <td style="padding:5px;">详细地址</td>
	                <td style="padding:5px;" colspan="3"><textarea onChange="addressConfirm_address_change()" style="width: 355px" cols="50" id="addressConfirm_addressDesc" title="${address.addressDesc}" value="">${address.addressDesc}</textarea>
	            </tr>
	
	        </table>
        </div>
    </form>

    <p class="textC mt10">
        <a href="javascript:void(0);" id="addressConfirm_btn_reset" onclick="addressConfirm_address_reset()" class="window_btn ml10">还原地址</a>&nbsp;&nbsp;&nbsp;
        <a href="javascript:void(0);" id="addressConfirm_btn_cancel" onclick="addressConfirm_order_cancel()" class="window_btn ml10">取消订单</a>
        <a href="javascript:void(0);" id="addressConfirm_btn_confirm" onclick="addressConfirm_confirm('1')" class="window_btn ml10">保存并确认</a>
        <a href="javascript:void(0);" id="addressConfirm_btn_confirm2" onclick="addressConfirm_confirm('2')" class="window_btn ml10">确认无误</a></p>
</div>
<script>
    var taskId='${callbackTask.taskId}';
    var flag='${callbackTask.flag}';
    var sourceType='${sourceType}';
    var userId='${userId}';
    var orderId='${callbackTask.orderId}';
    var orgAddress='${address.addressDesc}';
    addressConfirm_switchButton(flag);

$("#addressConfirm_handle").click(function(){
    addressConfirm_flagChange('2');
});

$("#addressConfirm_cancel").click(function(){
   addressConfirm_flagChange('1');
});
function addressConfirm_address_change()
{
    console.debug($('#addressConfirm_addressDesc').val());
}


function addressConfirm_address_reset()
{
    $('#addressConfirm_addressDesc').val(orgAddress);
}

function addressConfirm_flagChange(cFlag)
{
    var callbackObj=null;
    if(cFlag=='2')
    {
        callbackObj={
            taskId:taskId,
            flag:cFlag,
            taskType:'5',
            rdbusrid:userId
        }
    }
    else
    {
        callbackObj={
            taskId:taskId,
            flag:cFlag,
            taskType:'5',
            rdbusrid:''
        }
    };
    $.post("${ctx}/callbackTask/changeFlag", callbackObj, function(data) {
        if (data.succ=="succ") {
        {
            flag=cFlag;
            addressConfirm_switchButton(cFlag);
            if(sourceType=='1')
            {
                $('#serviceTasks').datagrid('reload');
            }
        }
        }else {
            window.parent.alertWin('系统提示', data.msg);
        }
    }, 'json');
}

function addressConfirm_switchButton(cFlag)
{
    if(cFlag=='1')
    {
        $('#addressConfirm_handle').css({display:''});
        $('#addressConfirm_cancel').css({display:'none'});
    }
    else if(cFlag=='2')
    {
        $('#addressConfirm_handle').css({display:'none'});
        $('#addressConfirm_cancel').css({display:''});
    }
    else
    {
        $('#addressConfirm_handle').css({display:'none'});
        $('#addressConfirm_cancel').css({display:'none'});
    }

    if(cFlag=='2')
    {
        $('#addressConfirm_btn_reset').css({display:''});
        $('#addressConfirm_btn_cancel').css({display:''});
        $('#addressConfirm_btn_confirm').css({display:''});
        $('#addressConfirm_btn_confirm2').css({display:''});
    }
    else
    {
        $('#addressConfirm_btn_reset').css({display:'none'});
        $('#addressConfirm_btn_cancel').css({display:'none'});
        $('#addressConfirm_btn_confirm').css({display:'none'});
        $('#addressConfirm_btn_confirm2').css({display:'none'});
    }
}

function addressConfirm_confirm(type)
{
    var newAddress=$('#addressConfirm_addressDesc').val();
    if(newAddress==null||newAddress=='')
    {
        window.parent.alertWin('系统提示', '地址不能未空');
        return;
    }
    var callbackObj=null;
    if(type=='1')
    {
        callbackObj={
            taskId:taskId,
            flag:'3',
            taskType:'5',
            rdbusrid:userId,
            orderId:orderId,
            address:$('#addressConfirm_addressDesc').val(),
            mainAddress:'${address.receiveAddress}',
            note:'已修正,原值：'+orgAddress
        }
    }
    else
    {
        var newAddress= $('#addressConfirm_addressDesc').val();
        if(newAddress!=orgAddress)
        {
            window.parent.alertWin('系统提示', '地址已发生修改，无法确认，请先还原地址');
            return;
        }
        callbackObj={
            taskId:taskId,
            flag:'3',
            taskType:'5',
            rdbusrid:userId,
            orderId:orderId,
            note:'确认无误'
        }
    };

    $.post("${ctx}/callbackTask/handleTask", callbackObj, function(data) {
        if (data.succ=="succ") {
            {
                flag='3';
                addressConfirm_switchButton(flag);
                $('#addressConfirmDialog').window('close');
                if(sourceType=='1')
                {
                    $('#serviceTasks').datagrid('reload');
                }
            }
        }else {
            if(sourceType=='1')
            {
                $('#serviceTasks').datagrid('reload');
            }
            window.parent.alertWin('系统提示', data.msg);
        }
    }, 'json');
}

function addressConfirm_order_cancel()
{
   var callbackObj={
       taskId:taskId,
       flag:'2',
       taskType:'5',
       rdbusrid:userId,
       orderId:orderId,
       note:'无法核实，订单被取消'
   }

   $.post("${ctx}/callbackTask/cancelTaskOrder", callbackObj, function(data) {
       if (data.succ=="succ")
       {
           flag='2';
           addressConfirm_switchButton(flag);
           $('#addressConfirmDialog').window('close');
           if(sourceType=='1')
           {
               $('#serviceTasks').datagrid('reload');
           }
           $('#addressConfirmDialog').window('close');
       }else {
           if(sourceType=='1')
           {
               $('#serviceTasks').datagrid('reload');
           }
           window.parent.alertWin('系统提示', data.msg);
       }
   }, 'json');
}


function addressConfirm_exit () {
    $('#addressConfirmDialog').window('close');
}

function addressConfirm_showContact(contactId) {
    window.parent.addTab(contactId, "我的客户", false, '/contact/1/' + contactId);
}

</script>

