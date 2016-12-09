<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>


<div id="cmpfback_event_dialog" class="easyui-window" style="width: 664px" data-options="closed:true,modal:true,draggable:false,top:100" >

    <div id="cmpfback_event_tabs" class="easyui-tabs" style=" height:auto">
        <div title="基本信息/投诉内容">
            <div class="form-tabs">
                <div class="baseInfo" style="margin: 0">
                    <h3> — 基本信息 — </h3>
                    <table class="" border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td>事件编号</td>
                            <input id="cmpfback_event_contactId" type="hidden">
                            <td><input id="cmpfback_event_id" class="fl"  type="text" maxlength="20" disabled="true"/></td>
                            <td >地址</td>
                            <td colspan="3"><input id="cmpfback_event_address" class="fl"  type="text" size="40" disabled="true" /></td>
                        </tr>
                        <tr>
                            <td >客户姓名</td>
                            <td> <input id="cmpfback_event_contactName" class="fl"  type="text" maxlength="20" disabled="true" /></td>
                            <td>电话</td>
                            <td ><input id="cmpfback_event_phone" type="hidden" />
                                <input id="cmpfback_event_phoneMask" class="fl"  type="text" maxlength="20"  disabled="true" /></td>
                            <td>邮编</td>
                            <td><input id="cmpfback_event_zip" class="fl"  type="text" maxlength="20" disabled="true" /></td>
                        </tr>
                        <tr>
                            <td >订单号</td>
                            <td> <input id="cmpfback_event_orderid" class="fl"  type="text" maxlength="20" disabled="true" /></td>
                            <td >产品</td>
                            <td><input id="cmpfback_event_productdsc" class="fl"  type="text" maxlength="20" disabled="true" /></td>
                            <td>购买时间</td>
                            <td><input  id="cmpfback_event_ordercrdt"  class="easyui-datetimebox" type="text"  style="width:130px" disabled="true"/></td>
                        </tr>
                    </table>
                    <div class="complaint ">
                        <h3 class="mb5"> — 投诉内容 — </h3>
                        <textarea id="cmpfback_event_probdsc"></textarea>
                    </div>
                </div>
                <p class="textC mt10"><a href="javascript:void(0);" onclick="cmpfback_event_next()" class="window_btn ml10">下一步</a>
                    <a href="javascript:void(0);" onclick="cmpfback_event_closeWin()" class="window_btn ml10">取消</a></p>
            </div>
        </div>

        <div title="处理意见/相关部门处理情况/反馈/结果/原因">
            <div class="form-tabs">
                <div class="baseInfo" style="margin: 0">
                    <h3 class="mb5"> — 处理意见 — </h3>
                    <div class="clearfix">
                        <div class="fl arr5">
                            <input id="cmpfback_event_cate1" style="width:134px" >
                            <input id="cmpfback_event_cate2" style="width:134px" >


                        </div>
                        <div class="arr6">
                            <div><label>投诉受理人</label><input id="cmpfback_event_crusr" class="ml3"  type="text"  style="width:133px" disabled="true" /></div>
                            <div><label>时间</label>
                                <input  id="cmpfback_event_crdt_hide" type="hidden" />
                                <input  id="cmpfback_event_crdt" type="text"  class="easyui-datebox" disabled="true" /></div>
                        </div>
                    </div>
                    <h3 class="mb5"> — 相关部门处理情况 — </h3>
                    <div class="clearfix">
                        <div class="arr5 fl">
                            <textarea disabled="true" id="cmpfback_event_chuliqk" ></textarea>
                        </div>
                        <div class="arr6">
                            <div><label>处理人</label><input  id="cmpfback_event_chuliusr" class="ml3"  type="text"  style="width:133px" disabled="true" /></div>
                            <div><label>时间</label>
                                <input id="cmpfback_event_chulidt_hide" type="hidden">
                                <input id="cmpfback_event_chulidt"  type="text" class="easyui-datebox" disabled="true" /></div>
                        </div>
                    </div>  <h3 class="mb5"> — 反馈 — </h3>
                    <div class="clearfix">
                        <div class="arr5 fl">
                            <textarea id="cmpfback_event_feedbackdsc"></textarea>
                        </div>
                        <div class="arr6">
                            <div><label>反馈人</label><input id="cmpfback_event_fbusr" class="ml3"  type="text" style="width:133px" disabled="true" /></div>
                            <div><label>时间</label>
                                <input id="cmpfback_event_fbdt_hide" type="hidden">
                                <input id="cmpfback_event_fbdt"  type="text" class="easyui-datebox" disabled="true" /></div>
                        </div>
                    </div>
                    <div class="clearfix">
                        <div class="arr8 fl">
                            <!-- 满意-1；不满意-2；不选-0-->
                            <h3 class="mb5"> — 结果 — </h3>
                            <label><input type="radio" name="cmpfback_event_result" value="1"/>满意</label><br>
                            <label><input type="radio" name="cmpfback_event_result" value="2"/>不满意</label>
                        </div>
                        <div class="arr7">
                            <h3 class="mb5"> — 原因 — </h3>
                            <textarea id="cmpfback_event_reason"></textarea>
                        </div>
                    </div>
                </div>
                <p class="textC mt10">
                    <a href="javascript:void(0);" onclick="cmpfback_event_save()" class="window_btn ml10">保存</a>
                    <%--<a href="javascript:void(0);" onclick="cmpfback_event_print()" class="window_btn ml10">打印</a>--%>
                    <a href="javascript:void(0);" onclick="cmpfback_event_closeWin()" class="window_btn ml10">取消</a></p>
            </div>

        </div>
    </div>
</div>
<div id="cmpfback_print" style="display: none">
    <style type="text/css">
        .urgentTable {margin: 0 auto; font-size: 12px; }
        .urgentTable td {
            padding: 10px 0;
        }
        .urgentTable thead td {
            text-align: center;
        }
        .urgentTable tr td {
            border-bottom: 1px #000 solid;
        }</style>

    <table width="800" class="urgentTable" border="0" cellpadding="0" cellspacing="0">
        <thead>
        <tr>
            <td colspan="6"><h3>客户投诉加急协办单</h3></td>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>顾客姓名:</td><td id="cmpfback_print_contactName" style="text-align:left"></td>
            <td>订购日期：</td><td id="cmpfback_print_ordercrdt" style="text-align:left"></td>
            <td>订单号:</td><td id="cmpfback_print_orderid" style="text-align:left"></td>
        </tr>
        <tr>
            <td>日期：</td><td  id="cmpfback_print_date" style="text-align:left"></td>
            <td> 产品：</td> <td id="cmpfback_print_productdsc" style="text-align:left"></td><td></td><td></td>
        </tr>
        <tr>
            <td>联系电话：</td> <td colspan="5" id="cmpfback_print_phoneNum"></td>
        </tr>
        <tr>
            <td>投诉内容：</td><td colspan="5" id="cmpfback_print_probdsc"></td>
        </tr>
        <tr>
            <td>处理意见：</td> <td colspan="5" id="cmpfback_print_cate"></td>
        </tr>
        <tr>
            <td>受理人：</td><td colspan="5" id="cmpfback_print_crusr"></td>
        </tr>
        <tr>
            <td>处理情况：</td><td colspan="5" id="cmpfback_print_chuliqk"></td>
        </tr>
        <tr>
            <td>处理人：</td><td colspan="5" id="cmpfback_print_chuliusr"></td>
        </tr>
        </tbody>
    </table>

</div>
