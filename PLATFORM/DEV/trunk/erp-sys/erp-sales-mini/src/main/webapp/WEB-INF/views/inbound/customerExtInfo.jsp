<%@page pageEncoding="UTF-8"%>
<div title="客户扩展信息">
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