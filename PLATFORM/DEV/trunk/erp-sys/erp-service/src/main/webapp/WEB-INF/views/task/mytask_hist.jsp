<%@page pageEncoding="UTF-8"%>
<div title="任务分配历史">
    <div class="form-tabs">
        <div class="panel_con">
            <table width="90%" style="margin:0 auto;">
                <tr>
                    <%--<td></td>--%>

                    <td >
                        <label style="width:80px;display:inline-block">营销计划</label>
                        <select id="log-campaign" style="width:150px" class="easyui-combobox" data-options="editable:false">
                            <option value="">====请选择====</option>
                            <c:forEach items="${campaigns}" var="item">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <div>
                            <label style="width:80px;display:inline-block">客户群批次号</label>
                            <input id="log-batchCode" type="text" style="width:145px"/></div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label style="width:80px;display:inline-block">分配时间</label>
                        <input id="log-startdate" class="easyui-datetimebox" style="width:150px"/>&nbsp;~&nbsp;
                        <input id="log-enddate" class="easyui-datetimebox" style="width:150px"/></td>
                    <td><label style="width:80px;display:inline-block;">分配状态</label>
                        <select id="log-status" style="width:150px" class="easyui-combobox" data-options="editable:false">
                            <option selected value="">====请选择====</option>
                            <option value="0">未分配</option>
                            <option value="1">分配到组</option>
                            <option value="2">分配到座席</option>
                            <option value="3">任务已执行</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <div class="submitBtns" style="margin-left: 53px">
                            <a id="lnk-callback-log-submit" href="javascript:void(0)">查询</a>
                            <a id="lnk-callback-log-reset" class="ml10" href="javascript:void(0)">清空</a>
                        </div>
                    </td>
                </tr>
            </table>

        </div>

        <div class="mt10 q_r">数据量&nbsp;&nbsp;<span id="log_allotNum">0</span></div>

        <div class="sift">
            <span class="submitBtns"><a id="lnk-export-log" href="javascript:void(0)">导出数据</a>
            </span>
        </div>
        <table id="callback-log-grid" style="height:200px"></table>
    </div>

    <!--导出明细-->
    <form action="/task/exportHist" method="post" target="downFrame" id="downloadForm">
        <input type="hidden" id="d_log-campaign" name="campaignId" value=""/>
        <input type="hidden" id="d_log-batchCode" name="batchCode" value=""/>
        <input type="hidden" id="d_log-status" name="status" value=""/>
        <input type="hidden" id="d_log-startdate" name="assignStartTime" value=""/>
        <input type="hidden" id="d_log-enddate" name="assignEndTime" value=""/>
    </form>
    <!--下载隐藏frame-->
    <iframe style="display:none" id="downFrame" name="downFrame" src=""></iframe>
</div>