<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
</head>
<body>
<link href="${ctx}/static/style/myOrders.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/datagrid-detailview.js"></script>
<script type="text/javascript" src="${ctx}/static/js/blacklist.js"></script>
<form id="formmessage" class="tabs-form" action="" method="post">

    <div class="baseInfo" style="margin: 0">
        <table class="" border="0" cellspacing="0" cellpadding="0"
               width="100%">
            <tr>
                <td>电话号码</td>
                <td ><input id="phoneNum" type="text"/></td>
                <td>加黑次数</td>
                <td ><input id="addTimes" type="text" class=""/></td>
                <td>状态</td>
                <td><select id="status" style="width: 127px;">
                    <option value="">全部</option>
                    <option value="1">未加黑</option>
                    <option value="2">已加黑</option>
                    <%--<option value="3">已去黑</option>--%>
                </select></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td colspan="2"><div class="submitBtns">
                    <a href="javascript:void(0);" id="messagesearch">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="javascript:void(0);" id="messagereset">清空</a>
                </div></td>
            </tr>
        </table>


    </div>
</form>
<div class="history tabs-form">
    <table id="blackPhoneTable">
    </table>
</div>
</body>
</html>