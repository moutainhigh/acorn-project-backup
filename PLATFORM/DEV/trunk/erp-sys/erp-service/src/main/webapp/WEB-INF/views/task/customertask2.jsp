<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
    <link href="${ctx}/static/style/myOrders.css" rel="stylesheet"
          type="text/css" />
    <link href="${ctx}/static/style/callbackAssign.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
        var isDepartmentManager = '${isDepartmentManager}';
    </script>
    <script type="text/javascript" src="${ctx}/static/js/task/taskAllocation.js?${rnd}"></script>
</head>
<body>
<div class="tabs-form" >
    <h2 class="mb5">-无效地址确认-</h2>
    <div class="baseInfo" style="margin: 0">
        <table class="" border="0" cellspacing="0" cellpadding="0"
               width="100%">
            <tr>
                <td>任务编号</td>
                <td><input type="text" disabled="true"/></td>
                <td>分配时间</td>
                <td ><input size="10" type="text" id=""
                            style="width: 150px"disabled="true"  class="easyui-datetimebox"  /></td>

                <td>客户编号</td>
                <td>  <input type="text" disabled="true"/></td>
            </tr>
            <tr>
                <td>客户姓名</td>
                <td ><input type="text" disabled="true"/>&nbsp;<span class="submitBtns" ><a href="javascript:void(0)" style="padding:2px 5px" id="">检索</a></span></td>
                <td>客户电话</td>
                <td colspan="2">
                    <div class="dial" id="num" style="height: auto">
                        <a  class="link fl ml5 c_combo pl5" >1503623****</a>

                        <div class="marquee_hover pl5 pr5" >
                            <div>1503623****</div>
                            <div class="">&nbsp;<a class="callback" onclick="callLocalhost(this);">本地</a>&nbsp;<a
                                    class="callback" onclick="callLongDistance(this);">长途</a>&nbsp;
                                <%--<a class="addBlacklist" onclick="addBlack(this);">加黑</a>&nbsp;--%>
                            </div>
                        </div>
                    </div>
                </td>
    </div>
    </tr>
    <tr>
        <td>订单编号</td>
        <td><input type="text" disabled="true"/></td>
        <td>订单状态</td>
        <td><input type="text" disabled="true"/></td>
        </tr>

    <tr>
        <td>备注</td>
        <td colspan="3">  <input type="text" size="75" disabled="true"/></td>
        <td colspan="2">
            <div class="submitBtns" style="text-align: left">
                <a href="javascript:void(0)" id="">开始处理</a>
            </div>
        </td>
    </tr>
    <%--<tr><td></td><td></td><td></td><td></td><td colspan="2">--%>
    <%--<div class="submitBtns" style="text-align: left">--%>
    <%--<a href="javascript:void(0)" id="">查询任务</a>&nbsp;&nbsp;&nbsp;&nbsp;--%>
    <%--<a href="javascript:void(0)" id="">清空</a>--%>
    <%--</div>--%>
    <%--</td></tr>--%>
    </table>

</div>
</div>

</body>

</html>