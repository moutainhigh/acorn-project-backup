<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>

</head>
<body>
	<link href="${ctx}/static/style/easyui.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/static/style/themes/icon.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/style/myOrders.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		var isINB = eval('${isINB}');
        var record_deleted = eval('${record_deleted}');
        document.body.className="withtool";
	</script>
    <script type="text/javascript" src="${ctx}/static/js/event/case.js?${rnd}"></script>
	<script type="text/javascript" src="${ctx}/static/js/event/eventmanage.js?${rnd}"></script>
	<script type="text/javascript" src="${ctx}/static/js/event/eventCmpfback.js?${rnd}"></script>

    <div class="form-tabs">
        <form id="fmCase" url="${urlString}" module="${module}" query="${queryString}" callback="${callback}" method="post" novalidate>
            <div class="baseInfo" style="margin: 0">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>事件编号</td>
                    <td>
                        <input  id="tbCaseId" name="caseid" class="fl" placeholder="自动编号"  type="text" maxlength="20" value="${caseId}" readonly="readonly" />
                    </td>
                    <td style="color: red">解决方案</td>
                    <td>
                        <select id="cbSolution" name="delivered" class="easyui-combobox" required="true" editable="false" style="width:134px" >
                        </select>
                    </td>
                    <td>处理时间</td>
                    <td>
                        <input  type="text" id="tbProcessTime" name="processtime" class="easyui-combobox"  style="width:130px"/>

                    </td>
                </tr>
                <tr>
                    <td>订单编号</td>
                    <td><input id="tbOrderId" name="orderid" class="fl" readonly="readonly" value="${orderId}" type="text" maxlength="20"/>

                    </td>
                    <td style="color: red">
                        产品简码
                    </td>
                    <td>
                        <input id="tbScode" name="scode" class="easyui-combogrid" class="fl" required="true" editable="true" type="text" maxlength="20" />

                    </td>
                    <td>事件状态</td>
                    <td>
                        <select id="cbCaseState" name="status" class="easyui-combobox" editable="false" data-options="required:${status_required}" style="width:134px">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="color: red">事件类型</td>
                    <td>
                        <select id="cbCaseType" name="casetypeid"  style="width:134px" editable="false" required="true" class="easyui-combobox" >
                        </select>
                    </td>
                    <td>客户编号</td>
                    <td>
                        <input  id="tbContactId" name="contactid" type="hidden" value="${contactId}" />
                        <a href="javascript:showContact('${contactId}');">${contactId} <label id="lblContactName" style="display:none">${contactName}</label></a>
                    </td>
                </tr>
            </table>
            <div class="clearfix">
                <div class="fl arr3">
                    <div class="fl ww1">
                        <ul id="lstCaseDet1">
                        </ul>
                    </div>
                    <div class="fl ww1">
                        <ul id="lstCaseDet2">
                        </ul>
                    </div>
                    <div class="fl ww1 no-rborder">
                        <ul id="lstCaseDet3">
                        </ul>
                    </div>
                </div>
                <div class="arr4">
                    <div><label style="color: red">问题描述</label><textarea id="tbProbdsc" class="easyui-validatebox" name="probdsc" required="true" maxlength="1000"></textarea></div>
                    <div><label>客户要求</label><textarea id="tbReqdsc" name="reqdsc" class="easyui-validatebox" maxlength="100"></textarea></div>
                    <div><label>处理描述</label><textarea id="tbExternal" name="external" class="easyui-validatebox" maxlength="1000"></textarea></div>

                </div>
                <div class="submitBtns" style="text-align: right">
                    <a href="javascript:void(0)" id="btnNotify" onclick="openCreateNewCusnote();">客服通知</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="javascript:void(0)" id="btnSave">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="javascript:void(0)" id="btnNew">清空</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="javascript:void(0)" id="btnClear" style="display: none">清空</a>
                </div>

            </div>
        </div>
        </form>
    </div>

    <div id="dgCase-container" class="receiptInfo tabs-form withtool" style="display: block">
        <table id="dgCase" cellspacing="0" cellpadding="0"></table>
    </div>

    <div id="prod_query">
        <input id="tbProdQuery" class="easyui-searchbox" minlength="2" data-options="prompt:'请输入产品简码'" style="width:130px"/>
    </div>

    <jsp:include page="cmpfbackDialog.jsp"></jsp:include>

    <div id="revisitDialog" style="" data-options="closed:true,modal:true,draggable:false,top:300" >


    </div>

    <!-- 退款 -->
    <div id="drawbackDialog"  style="padding:10px" data-options="closed:true,modal:true,draggable:false,top:300" >


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
                                    <input class="fl" disabled="true"  type="text" maxlength="20" id="customerNDialog_orderId"/>
                                    <input class="fl" value="" style="display: none;"  type="text" maxlength="20" id="customerNDialog_newNotice"/>
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
                                        <select id="customerNDialog_noticeType" style="width:134px">
                                        </select>
                                    </td>
                                    </tr>
                                <tr>
                                    <td valign="top">备注:</td>
                                    <td >
                                        <textarea style="width:550px;height: 50px" id="customerNDialog_notes"></textarea>
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
                                        <textarea style="width:550px;height: 50px" disabled="true" id="customerNDialog_feedBack"></textarea>
                                    </td>
                                </tr>
                            </table>
                            <%--<p style="vertical-align: top">回复内容: &nbsp;&nbsp;<textarea style="width:200px"></textarea></p>--%>


                        </div>
                        <p class="textC mb10">
                            <a href="javascript:void(0);" ondblclick="javascript:void(0);" onclick="saveNotice();" class="window_btn ml10" id="saveNoticeButton">保存</a>
                            <a href="javascript:void(0);" onclick="cleanNotice();" class="window_btn ml10">清空</a>
                        </p>
                        <table id="requisition"></table>
                    </div>




                </div>



    </div>

</body>
</html>