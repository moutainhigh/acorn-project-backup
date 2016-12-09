<%@page pageEncoding="UTF-8"%>
<div title="客户扩展信息">
    <div class="history tabs-form">
    <div id="contact_ext" class="easyui-tabs" style="height:600px;">
        <div  title="扩展历史">
            <div class="l_i_h ">
                <div class="extendInfo tabs-form ">
                    <form name="formAddCustomerExt" id="formAddCustomerExt" method="post">
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td>出生年月</td>
                                <input id="birthdayHidden" type="hidden" value="${customer.birthdayStr}" />
                                <td><input style="width:95px" id="birthday" class="easyui-datebox" /></td>
                                <td>婚姻状况</td>
                                <input id="marriageHidden" type="hidden" value="${customer.marriage}" />
                                <td><select class="easyui-combobox"  id="marriage" style="width:95px" name="">
                                    <option value="">-请选择-</option>
                                    <c:forEach var="marriage" items="${marriages}">
                                        <option value="${marriage.id.id}">${marriage.dsc}</option>
                                    </c:forEach>
                                </select></td>
                                <td>家庭收入</td>
                                <!--
                <td><input style="width:89px" value="${customer.income}" id="income" type="text">&nbsp;万</td>
                -->
                                <input id="incomeHidden" type="hidden" value="${customer.income}" />
                                <td><select class="easyui-combobox"  id="income" style="width:95px" name="income">
                                    <option value="">-请选择-</option>
                                    <c:forEach var="income" items="${income}">
                                        <option value="${income.id.id}">${income.dsc}</option>
                                    </c:forEach>
                                </select></td>
                                <td>职业状态</td>
                                <input id="occupationStatusHidden" type="hidden" value="${customer.occupationStatus}" />
                                <td><select class="easyui-combobox" style="width:95px" id="occupationStatus"  name="">
                                    <option value="">-请选择-</option>
                                    <c:forEach var="occupationStatus" items="${occupationStatuss}">
                                        <option value="${occupationStatus.id.id}">${occupationStatus.dsc}</option>
                                    </c:forEach>
                                </select></td>
                            </tr>
                            <tr>
                                <td>教育程度</td>

                                <input id="educationHidden" type="hidden" value="${customer.education}" />
                                <td><select class="easyui-combobox" id="education" style="width:95px" name="">
                                    <option value="">-请选择-</option>
                                    <c:forEach var="education" items="${educations}">
                                        <option value="${education.id.id}">${education.dsc}</option>
                                    </c:forEach>
                                </select></td>
                                <td><label><input name="isCar" id="isCar" type="checkbox" onclick="javascript:clickIsCar();"  <c:if test="${customer.car == '-1'}">checked="checked"</c:if> />有车</label></td>
                                <td><input value="${customer.carmoney2}" id="carmoney2" type="text" style="width:89px" <c:if test="${customer.car != '-1'}">disabled="true"</c:if>/>&nbsp;万</td>
                                <td><label><input name="isChild" id="isChild"  type="checkbox" onclick="javascript:clickIsChild();" <c:if test="${customer.children == '-1'}">checked="checked"</c:if> />有小孩</label></td>
                                <input id="childrenageHidden" type="hidden" value="${customer.childrenageStr}" />
                                <td><input id="childrenage" class="easyui-datebox" style="width:95px" <c:if test="${customer.children != '-1'}">disabled="true"</c:if>/></td>
                                <td><label><input name="isParent" id="isParent" type="checkbox" onclick="javascript:clickIsParent();" <c:if test="${customer.hasElder == '-1'}">checked="checked"</c:if> />有老人</label></td>
                                <input id="elderBirthdayHidden" type="hidden" value="${customer.elderBirthdayStr}" />
                                <td><input id="elderBirthday" <c:if test="${customer.hasElder != '-1'}">disabled="true"</c:if> class="easyui-datebox" style="width:95px"/></td>

                            </tr>
                            <tr>
                                <td>邮箱地址</td>
                                <td colspan="3"><input type="text" id="email" value="${customer.email}"/><span id="emailError" style="color: #ff0000; display: none;">  邮箱格式不正确</span></td>
                                <td>微信号码</td>
                                <td colspan="3"><input type="text" id="weixin" value="${customer.weixin}"/></td>
                            </tr>
                        </table>
                        <c:if test="${customer.state==null||customer.state==2}">
                            <%--<div class="submitBtns" style="margin-top:10px">--%>
                            <p class="textC mt10">
                                <a href="javascript:void(0);" id="addCoustomerExtBut" class="phoneNums_btns" style="width:80px">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="javascript:void(0);" name="cancelCoustomerExtBut" id="cancleCoustomerExtBut" class="phoneNums_btns"  style="width:80px">取消</a>
                            </p>
                            <%--</div>--%>
                        </c:if>
                    </form>
                </div>
             </div>
        </div>
        <div id="contact_insure_div"  title="保险客户基本信息">
            <div class="m_h">
                <div class="extendInfo tabs-form ">
                    <input type="hidden" name="isrefuse" id="isrefuse" value="${customer.refuse}">
                    <input type="hidden" name="hasinsure" id="hasinsure" value="${customer.hasinsure}">
                    <input type="hidden" name="v_insureAge" id="v_insureAge" value="${customer.insureAge}">
                    <input type="hidden" name="v_insureStatus" id="v_insureStatus" value="${customer.insureStatus}">
                    <form name="insure_form" id="contact_insure_form" method="post">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>客户编号</td>
                                <td><input  id="contact_insure_id" type="text" class="" readonly="readonly" <c:if test="${customer.customerType!='2'}">value=${customer.customerId}</c:if> />
                                    <label class="mr10"><input name="refuse" id="refuse" type="checkbox" <c:if test="${customer.customerType=='2'}"> disabled="disabled" </c:if> <c:if test="${customer.refuse=='1'}"> checked="checked" </c:if> /> 拒绝接受免险</label>
                                </td>

                            </tr>
                            <tr>
                                <td colspan="2"><label class="mr10"><input name="isok" id="contact_isok" type="checkbox" <c:if test="${customer.customerType=='2'}"> disabled="disabled" </c:if> <c:if test="${customer.insureAge=='-1' and customer.insureExpire=='0' }">checked="checked"</c:if> />客户年龄在18-70岁</label>
                                    <label class="mr10"><input name="insure" id="contact_insure" type="checkbox" <c:if test="${customer.customerType=='2'}"> disabled="disabled" </c:if> <c:if test="${customer.insureStatus=='-1' and customer.insureExpire=='0' }" > checked="checked" </c:if> />该客户需要保险产品</label>
                                    <!--
                                    <input class="easyui-datebox" disabled="true"  id="refuseDateTime" name="refuseDateTime"  <c:if test="${customer.customerType!='2'}"> value=${customer.refuseDateTime}</c:if> ></input>
                                     -->
                                </td>


                            </tr>
                            <tr>
                                <td valign="top">保险备注</td>
                                <td><textarea id="contact_insure_note" name="insure_note" cols="50" rows="6" <c:if test="${customer.customerType=='2'}"> disabled="disabled" </c:if> >${customer.insureNote}</textarea></td>

                            </tr>
                            </table>
                        <p class="textC mt10">
                            <a href="javascript:void(0);" id="contact_insure_save" name="insure_save" class="phoneNums_btns" disabled="disabled"  style="width:80px" <c:if test="${customer.customerType=='2'}"></c:if>>保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:void(0);" id="contact_insure_cancel" name="insure_cancel" class="phoneNums_btns"  style="width:80px">取消</a>
                        </p>
                        </form>

                    </div>
                <%--<div class="extendInfo tabs-form ">--%>
                    <%--<form name="insure_info_form" id="contact_insure_info_form" method="post">--%>
                        <%--<table  border="0" cellspacing="0" cellpadding="0" width="100%">--%>
                            <%--<tr>   <td>--%>
                                <%--<p>顾客在赠险周期内已经接受的保险:</p>--%>
                                <%--&lt;%&ndash;<textarea cols="50" style="width:100%" rows="4"></textarea>&ndash;%&gt;--%>
                                <%--<table id="insureInfoTab" width="100%" class="border">--%>
                                    <%--&lt;%&ndash;<tr>--%>
                                        <%--<td width="50%">&nbsp;</td>--%>
                                        <%--<td width="50%">&nbsp;</td>--%>
                                    <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td width="50%">&nbsp;</td>--%>
                                        <%--<td width="50%">&nbsp;</td>--%>
                                    <%--</tr>&ndash;%&gt;--%>
                                <%--</table>--%>

                            <%--</td>--%>
                            <%--</tr>--%>
                            <%--<tr>    <td>--%>
                                <%--<p>顾客还可接受的保险:</p>--%>
                                <%--&lt;%&ndash;<textarea cols="50" style="width:100%" rows="4"></textarea>&ndash;%&gt;--%>
                                <%--<table id="insureInfoCanTab" width="100%" class="border">--%>
                                    <%--&lt;%&ndash;<tr>--%>
                                        <%--<td width="50%">&nbsp;</td>--%>
                                        <%--<td width="50%">&nbsp;</td>--%>
                                    <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td width="50%">&nbsp;</td>--%>
                                        <%--<td width="50%">&nbsp;</td>--%>
                                    <%--</tr>&ndash;%&gt;--%>
                                <%--</table>  </td>--%>
                            <%--</tr>--%>
                        <%--</table>--%>
                        <%--<p class="textC mt10">--%>
                            <%--&lt;%&ndash;<a href="javascript:void(0);" id="" name="" class="phoneNums_btns" style="width:80px">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                            <%--<a href="javascript:void(0);" id="" name="" class="phoneNums_btns"  style="width:80px">取消</a>&ndash;%&gt;--%>
                        <%--</p>--%>
                    <%--</form>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div  title="赠险情况">--%>
            <%--<div class="m_h">--%>
                <div class="extendInfo tabs-form ">
                    <form name="insure_info_form" id="contact_insure_info_form" method="post">
                        <table  border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>   <td>
                                <p>顾客在赠险周期内已经接受的保险:</p>
                                <%--<textarea cols="50" style="width:100%" rows="4"></textarea>--%>
                                <table id="insureInfoTab" width="100%" class="border">
                                    <%--<tr>
                                        <td width="50%">&nbsp;</td>
                                        <td width="50%">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width="50%">&nbsp;</td>
                                        <td width="50%">&nbsp;</td>
                                    </tr>--%>
                                </table>

                            </td>
                            </tr>
                            <tr>    <td>
                                <p>顾客还可接受的保险:</p>
                                <%--<textarea cols="50" style="width:100%" rows="4"></textarea>--%>
                                <table id="insureInfoCanTab" width="100%" class="border">
                                    <%--<tr>
                                        <td width="50%">&nbsp;</td>
                                        <td width="50%">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width="50%">&nbsp;</td>
                                        <td width="50%">&nbsp;</td>
                                    </tr>--%>
                                </table>  </td>
                            </tr>
                        </table>
                        <p class="textC mt10">
                            <%--<a href="javascript:void(0);" id="" name="" class="phoneNums_btns" style="width:80px">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:void(0);" id="" name="" class="phoneNums_btns"  style="width:80px">取消</a>--%>
                        </p>
                    </form>
                </div>
            </div>
        </div>
    </div>
        </div>

</div>