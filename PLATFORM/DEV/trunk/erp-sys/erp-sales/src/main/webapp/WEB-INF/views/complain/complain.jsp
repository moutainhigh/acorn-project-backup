<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
    <script type="text/javascript" src="/static/js/complain/complain.js"></script>
</head>
<body>
<form action="">
    <table class="complain_table">
        <tr>
            <td>客户编号</td><td><input id="contactId" type="text" maxlength="20" disabled="disabled">
            <a href="javascript:void(0);" class="customerQuery s_btn ml10" id="queryContact"></a></td>
            <td>订单编号</td><td><div>
            <select id="orderId" style="width: 260px;"><option value="">-请选择-</option></select></div></td>
        </tr>
        <tr>
            <td>客户姓名</td>
            <td>
                <div>
                    <input id="name" type="text" maxlength="20" disabled="disabled">
                </div></td>
            <td>产品</td><td><div>
            <input id="product" type="text" maxlength="20">
            </div></td>
        </tr>
        <tr>
            <td>客户电话</td>
            <td>
                <div>
                    <input id="phone" type="text" maxlength="20">

                </div></td>
            <td>优先级</td><td><div>
            <select class="easyui-combobox" id="priority" style="width:129px" >
            <c:forEach var="priority" items="${priorityList}">
                <option value="${priority.priorityId}">${priority.dsc}</option>
            </c:forEach>
        </select>
        </div></td>
        </tr>

        <tr>
            <td>投诉内容</td><td><div><textarea id="content" cols="30" rows="4"></textarea>
        </div></td>
        </tr>

    </table>
    <p class="winBtnsBar textC"><a id="submitComplain" class="window_btn mr10"  data-options="iconCls:'icon-ok'" href="javascript:void(0)">保存</a><a id="cancelComplain" class="window_btn" data-options="iconCls:'icon-ok'" href="javascript:void(0)">取消</a></p>
</form>

</body>
</html>