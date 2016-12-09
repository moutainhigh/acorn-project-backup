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
	</script>
	<script type="text/javascript" src="${ctx}/static/js/event/myevent.js?${rnd}"></script>

    <form id="fmSearch" class="tabs-form" method="post">
        <div class="baseInfo" style="margin: 0">
            <table class="" border="0" cellspacing="0" cellpadding="0"
                width="100%">
                <tr>
                    <td>录入日期</td>
                    <td><input size="10" type="text" name="begindate" value="${today}"  class="easyui-datebox" data-options="required:true" style="width:95px"/>&nbsp;~&nbsp;
                        <input size="10" type="text" name="enddate" value="${today}" class="easyui-datebox" data-options="required:true" style="width:95px"/></td>
                    <td>解决方案</td>
                    <td>
                        <select id="cbSolution" name="delivered" class="easyui-combobox" style="width:134px"></select>
                    </td>
                    <td>事件类型</td>
                    <td>
                        <select id="cbCaseType" name="casetypeid"  style="width:134px" class="easyui-combobox" >
                        </select>
                     </td>
                </tr>
                <tr>
                    <td>客户姓名</td>
                    <td><input class="fl" name="contactname" type="text" maxlength="20" />

                    </td>
                        <td>
                    产品简码
                    </td>
                    <td>
                        <input class="fl" name="scode" type="text" maxlength="20" />

                    </td>
                    <td>信息来源</td>
                    <td>
                        <select id="cbCaseSource" name="casesource" class="easyui-combobox" style="width:134px" >
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>购买渠道</td>
                    <td>
                        <select id="cbBuyChannel" name="buychannel"  style="width:134px" class="easyui-combobox" >
                        </select>
                    </td>
                    <td>处理方案调查</td>
                    <td>
                        <select  id="cbSatisfaction" name="satisfaction" style="width:134px" class="easyui-combobox">
                        </select>
                    </td>
                    <td>投诉原因</td>
                    <td>
                        <select id="cbReason" name="reason" style="width:134px" class="easyui-combobox" >
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>问题描述</td>
                    <td>
                        <input class="fl" name="probdsc" type="text" maxlength="20" />
                    </td>
                    <td>客户要求</td>
                    <td>
                        <input class="fl"  name="reqdsc" type="text" maxlength="20" />
                    </td>
                    <td>事件状态</td>
                    <td>
                        <select id="cbCaseState" name="status" class="easyui-combobox" style="width:134px" >
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>座席编号</td>
                    <td>
                        <input class="fl" name="crusr"  type="text" maxlength="20" />
                    </td>
                    <td>事件编号</td>
                    <td>
                        <input class="fl" name="caseid" type="text" maxlength="20" />
                    </td>
                    <td>优先级</td>
                    <td>
                        <select id="cbPriority" name="priorityid" style="width:134px" class="easyui-combobox" >
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>子类</td>
                    <td>
                        <select id="cbCategory" name="category" style="width:134px" class="easyui-combobox" >
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td width="25%">&nbsp;</td>
                    <td></td>
                    <td>
                    </td>
                    <td colspan="2">
                        <div class="submitBtns" style="text-align: left">
                            <a href="javascript:void(0);" id="lnkSearch">查找</a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:void(0);" id="lnkExport">导出</a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:void(0);" id="lnkClear">清空</a>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </form>

    <div class="receiptInfo tabs-form">
        <table id="dgCase" cellspacing="0" cellpadding="0"></table>
    </div>

</body>
</html>