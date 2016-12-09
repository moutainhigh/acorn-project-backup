<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
</head>
<body>
<link href="${ctx}/static/style/easyui.css" rel="stylesheet"
      type="text/css" />
<link href="${ctx}/static/style/themes/icon.css" type="text/css"
      rel="stylesheet" />
<link href="${ctx}/static/style/myOrders.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}/static/js/message/message.js"></script>
<script type="text/javascript">
    var messageStatuses=eval(${message_statuses });
    var messageMonthes=eval(${message_monthes });
    var messageTypes=eval(${message_types });
    var isManager=(${isManager });
</script>
    <form id="formmessage" class="tabs-form" action="" method="post">

        <div class="baseInfo" style="margin: 0">
            <table class="" border="0" cellspacing="0" cellpadding="0"
                   width="100%">
                <tr>
                    <td>消息状态</td>
                    <td ><input id="message_status" name="message_status"
                                class="inputStyle easyui-combobox"/></td>

                    <td>消息发送时间</td>
                    <td><input id="message_month" class="inputStyle easyui-combobox"
                               name="message_month"></td>

                    <td>消息类型</td>
                    <td><input id="message_type" name="message_type" class="inputStyle easyui-combobox"
                               /></td>
                </tr>
                <tr>
                    <td>接收座席</td>
                    <td><input id="message_recivier" name="message_recivier" type="text" class="easyui-validatebox" validType="remote['/myorder/myorder/checkGrpMember','usrId']" invalidMessage="非本主管下的座席工号"
                               maxlength="255"  value="${currentUsrId }"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td colspan="2"><div class="submitBtns" >
                        <a href="javascript:void(0);" id="messagesearch">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="javascript:void(0);" id="messagereset">清空</a>
                    </div></td>
                </tr>
            </table>


        </div>
    </form>
    <div class="history tabs-form">
        <table id="messagelist" class="easyui-datagrid"
               style="height: 350px" data-options="singleSelect:true,url:''"
               pagination="true">
            <thead>
            </thead>
        </table>
    </div>
</body>

</html>