<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<div title="客服通知">
    <form class="tabs-form" action="" method="post">

        <div class="baseInfo" style="margin: 0">
            <table class="" border="0" cellspacing="0" cellpadding="0"
                   width="100%">
                <tr>
                    <td>是否回复</td>
                    <td><select style="width: 138px" id="isreplay">
                        <option value="">全部</option>
                        <option value="-1">是</option>
                        <option value="0">否</option>
                    </select></td>
                    <td>通知分类</td>
                    <td><select style="width: 138px" id="noteclass">
                        <option value="">全部分类</option>
                    </select></td>

                    <td colspan="2"></td>

                </tr>
                <tr>
                    <td>生成时间</td>
                    <td colspan="5"><input size="10" type="text" id="gendate_begin"
                                           style="width: 120px" class="easyui-datebox" data-options="required:true"/>&nbsp;~&nbsp; <input
                            size="10" type="text" id="gendate_end" style="width: 120px"
                            class="easyui-datebox" data-options="required:true"/></td>

                </tr>

                <tr>
                    <td>生成座席</td>
                    <td colspan="5">
                        <input class="easyui-validatebox" id="genseat" type="text"/>
                    </td>

                </tr>
                <tr>
                    <td>回复时间</td>
                    <td colspan="5"><input size="10" type="text" id="redate_begin"
                                           style="width: 140px" class="easyui-datetimebox"/>&nbsp;~&nbsp;
                        <input
                                size="10" type="text" id="redate_end" style="width: 140px"
                                class="easyui-datetimebox"/></td>

                </tr>
                <tr>
                    <td>回复座席</td>
                    <td colspan="3">
                        <c:choose>
                            <c:when test="${isSupervisor==false}">
                                <input type="text" id="reseat" name="agentId" class="easyui-validatebox" readonly="readonly" value="${userID }" style="border-color: silver #D9D9D9 #D9D9D9;background-color: #F5F5F5; color:#ACA899;"/>
                            </c:when>
                            <c:when test="${isSupervisor==true}">
                                <input type="text" id="reseat" name="agentId" value="${userID}" class="easyui-validatebox" data-options="prompt:'输入座席工号', required:true" validType="remote['/myorder/myorder/checkGrpMember','usrId']" invalidMessage="非本主管下的座席工号" />
                            </c:when>
                        </c:choose></td>
                    <td colspan="2">
                        <div class="submitBtns" style="text-align: left">
                            <a href="javascript:void(0)" onclick="findNotice();">查找</a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:void(0)" id="">清空</a>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </form>
    <div class="receiptInfo tabs-form withtool">
        <table id="msgNotification" cellspacing="0" cellpadding="0"></table>
    </div>

    <div id="customerNDialog" class="easyui-window" style="width: 700px" data-options="closed:true,modal:true,draggable:false,top:50" >
        <div class="form-tabs">
            <div class="baseInfo" style="margin: 0">
                <h3> — 基本信息 — </h3>
                <table class="" border="0" cellspacing="0" cellpadding="0"
                       width="100%">
                    <tr>
                        <td>客户姓名:</td>
                        <td>        <input class="fl" disabled="true"  type="text" maxlength="20" id="customerNDialog_contactName"/>
                        </td>
                        <td >订单编号:</td>
                        <td >
                            <a class="link" href="javascript:void(0);" onclick="javascript:viewOrder();" id="customerNDialog_orderId"></a>
                            <input style="display: none;" id="customerNDialog_featstr"/>
                        </td>
                    </tr>

                    <tr>
                        <td >订单生成时间:</td>
                        <td>
                            <input type="text" id="customerNDialog_orderTime" disabled="true" style="width:130px"/>
                        </td>
                        <td >
                            事件编号:
                        </td>
                        <td>

                            <input class="fl"  type="text" disabled="true" maxlength="20" id="customerNDialog_eventId"/>
                        </td>
                    </tr>
                </table>
                <div class="complaint ">
                    <h3 class="mb5"> — 通知内容 — </h3>

                    <table class="" border="0" cellspacing="0" cellpadding="0"
                            >
                        <tr>
                            <td>通知分类:</td>
                            <td>
                                <input id="customerNDialog_noticeType" style="width:134px" disabled="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td valign="top">备注:</td>
                            <td >
                                <textarea style="width:550px;height: 50px" id="customerNDialog_notes" disabled="true"></textarea>
                            </td>
                        </tr>
                    </table>

                </div>
                <div class="complaint ">
                    <h3 class="mb5"> — 通知回复 — </h3>
                    <table class="" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td valign="top">回复内容:</td>
                            <td >

                                <textarea style="width:550px;height: 50px" id="customerNDialog_feedBack"></textarea>
                            </td>
                        </tr>
                    </table>

                </div>
                <p class="textC mb10"><a href="javascript:void(0);" ondblclick="javascript:void(0);" onclick="updateNotice();" class="window_btn ml10" id="saveNoticeButton">保存</a>
                </p>
                <table id="requisition"></table>
            </div>
        </div>
    </div>

</div>

