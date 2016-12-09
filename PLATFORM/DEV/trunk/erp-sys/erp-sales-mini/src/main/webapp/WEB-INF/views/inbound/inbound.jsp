<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body >

<link href="/static/style/inboundCall.css" rel="stylesheet" type="text/css" />
<link href="/static/style/cart.css" rel="stylesheet" type="text/css" />
<link href="/static/style/cc.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/static/js/inbound.js?${rnd}"></script>
<script type="text/javascript" src="/static/js/datagrid-detailview.js"></script>
<!--  -->
<script type="text/javascript" src="/static/js/cart/link.js"></script>
<script type="text/javascript" src="/static/js/cart/cc.js"></script>
<script type="text/javascript" src="/static/js/cart/cart.js"></script>
<script type="text/javascript">
    var _contactId = '${contactId}';
    var orderPayTypeList=eval(${orderPayTypes});
</script>
<script type="text/javascript" src="/static/js/sms/sms.js?${rnd}"></script>
<script type="text/javascript" src="/static/js/easyui-validatabox-extend.js"></script>
<div style="padding:15px">
    <div class="baseInfo">
        <c:if test="${customer.state==0}"><span class="exa abs">待审核</span></c:if>
        <c:if test="${customer.state==1}"><span class="exaa abs">审核中</span></c:if>
        <c:if test="${customer.state==3}"><span class="exaa abs">未通过</span></c:if>
        <input id="input_batchId" type="hidden" value="${batchId}" />
        <form id="formAddCustomerInfo" action="/customer/mycustomer/add" method="post" onkeypress="if(event.keyCode==13||event.which==13){return false;}">
            <table border="0" cellspacing="0" cellpadding="0" width="90%" style="margin: 0 auto">
                <tr>
                    <td class="pt10" >客户姓名</td>
                    <td class="pt10" width="180px"><input value="${customer.name}" id="name" name="name" tabindex="1" class="easyui-validatebox" <c:if test="${customer.customerType != '2'}">data-options="prompt:'输入客户姓名', required:true,validType:'length[2,30]'"</c:if> type="text" value="" maxlength="255" />&nbsp;<c:if test="${customer.customerType != '2'}"><font class="mr10" color="#FF0000">*</font></c:if></td>

                    <td >客户性别</td>
                    <td ><label><input name="sex" type="radio" tabindex="2" value="1" checked="true"  <c:if test="${customer.sex == 1}">checked="checked"</c:if> />男</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <label><input name="sex" type="radio" value="2" <c:if test="${customer.sex == 2}">checked="checked"</c:if> />女</label></td>
                    <td >客户编号</td>
                    <td ><label id="showCustomerId">${customer.customerId}</label>
                    </td>
                </tr>

                <tr id="newCustomerHiddenTr">
                    <td>会员等级</td>
                    <td>${level}</td>
                    <td >待办任务</td>
                    <td ><a id="taskCount" href="javascript:void(0);" class="detailItem ml10">${taskCount}</a></td>
                    <td>可用积分</td>
                    <td><a id="pointsDetail" href="javascript:void(0);" class="detailItem ml10"> ${points}</a></td>

                </tr>
                <tr>
                    <td><div id="newCustomerHiddenTd1">绑定座席(组)</div></td>
                    <td><div id="newCustomerHiddenTd2"><c:if test="${bindAgentUser!=null}">${bindAgentUser.userId}-${bindAgentUser.name}-${bindAgentUser.defGrp}</c:if></div></td>
                    <td><div id="newCustomerHiddenTd3">购买总金额</div></td>
                    <td><div id="newCustomerHiddenTd4"><a href="javascript:void(0);">￥${totalAmount}</a></div></td>
                    <td colspan="2"><div class="submitBtns">
                        <div id="token">
                            <a  href="javascript:parent.cc_change(${customer.customerId},${customer.customerType});" id="cc_change" style="padding:2px 20px">切换客户</a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:void(0);" id="findAssign" onclick="queryAssignHistory();"  style="padding:2px 20px">查归属</a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <c:if test="${customer.state==null||customer.state==2}"><a href="javascript:void(0);"  tabindex="3" id="addCoustomerInfoBut">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
                            <a href="javascript:void(0);" id="clearCoustomerInfoBut">清空</a></div></div></td>
                </tr>
            </table>
        </form>
    </div></div>
<div id="inboundtabs"  style=" height:auto">
<div title="客户详细信息" data-options="">

<div class="receiptInfo">
    <div class="receiptInfo-title q_r">常用通话信息&nbsp;&nbsp;&nbsp;&nbsp;
        <%--<c:if test="${customer.state==null||customer.state==2}"><a class="delBtn" id="displayCoustomerPhoneBut" name="displayCoustomerPhoneBut" href="javascript:void(0);"><span></span></a></c:if>--%>
    </div>
    <div class="sift">
        <form id="formAddCustomerPhone" name="formAddCustomerPhone" action="" method="post">
            <label style="width: 80px;display:inline-block">新增客户电话</label>
                               <span id="fphone1" name="fphone1">
							    <input type="text" id="phn4" name="phn4" tabindex="4" class="easyui-validatebox" data-options="required:true,validType:'mobile'"  /></span>
                                        <span id="fphone2" name="fphone2" style="display: none;">
                                        <input  type="text" id="phn1" name="phn1" size="3"  class="easyui-validatebox" data-options="required:true,validType:'telephoneAreaCode'" />-
                                        <input type="text" id="phn2" name="phn2" size="7"  class="easyui-validatebox" data-options="required:true,validType:'telephone'" />-
                                        <input type="text" id="phn3" name="phn3" size="3"  class="easyui-validatebox" data-options="validType:'telephoneExt'"   />
                                        <input type="hidden" id="editRowIndex" name="editRowIndex" size="3"   />
                                        </span>
            <label style="width:50px"><input name="phonetypid" class="fl" id="phonetypid" type="checkbox"/>固话</label>
            <label id="isDefaultPhoneTR" style="width:90px;">
                <input type="checkbox" class="fl" id="isDefault" name="isDefault"/>设为主电话</label>

            <label><a href="javascript:void(0);" tabindex="6" name="addCoustomerPhoneBut" id="addCoustomerPhoneBut" class="phoneNums_btns small_btns">确定</a></label>
        </form>
    </div>
    <table id="phoneTable" name="phoneTable"></table>
</div>
<%--<form id="formAddCustomerPhone" name="formAddCustomerPhone" action="" method="post"  onkeypress="if(event.keyCode==13||event.which==13){return false;}">--%>
<%--<table class="cr_table" id="AddPhoneTab" name="AddPhoneTab" border="0" cellspacing="0" cellpadding="0" style="display:none">--%>
<%--<tr>--%>
<%--<td class="cr_title">新增客户电话</td>--%>
<%--<td></td>--%>
<%--<td></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>客户电话</td>--%>
<%--<td> <span id="fphone1" name="fphone1">--%>
<%--<input type="text" id="phn4" name="phn4" class="easyui-validatebox" data-options="required:true,validType:'mobile'"  /></span>--%>
<%--<span id="fphone2" name="fphone2" style="display:none">--%>
<%--<input  type="text" id="phn1" name="phn1" size="3"  class="easyui-validatebox" data-options="required:true,validType:'telephoneAreaCode'" />---%>
<%--<input type="text" id="phn2" name="phn2" size="7"  class="easyui-validatebox" data-options="required:true,validType:'telephone'" />---%>
<%--<input type="text" id="phn3" name="phn3" size="3"  class="easyui-validatebox" data-options="validType:'telephoneExt'"   />--%>
<%--<input type="hidden" id="editRowIndex" name="editRowIndex" size="3"   />--%>
<%--</span></td>--%>
<%----%>
<%--<td> <label class="acp_label">--%>
<%--<input type="hidden" id="potentialPhoneId"  name="potentialPhoneId"/>--%>
<%--<input name="phonetypid" class="" id="phonetypid" type="checkbox"/>--%>
<%--固话</label></td>--%>
<%--</tr>--%>
<%----%>
<%--<tr id="isDefaultPhoneTR"><td>设为主电话</td><td><input type="checkbox" id="isDefault" name="isDefault"/></td><td></td></tr>--%>
<%----%>
<%--<tr><td></td><td><a href="javascript:void(0);" name="addCoustomerPhoneBut" id="addCoustomerPhoneBut" class="phoneNums_btns">确定</a>--%>
<%--&lt;%&ndash;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" name="cancleCoustomerPhoneBut" id="cancleCoustomerPhoneBut" class="phoneNums_btns">取消</a>	&ndash;%&gt;--%>
<%--</td><td></td></tr>--%>
<%----%>
<%--</table>--%>
<%--</form>--%>
<div class="receiptInfo">
    <div class="receiptInfo-title q_r">常用收货信息&nbsp;&nbsp;&nbsp;&nbsp;
        <%--<c:if test="${customer.state==null||customer.state==2}"><a class="delBtn" id="displayCoustomerAddressBut" name="displayCoustomerAddressBut" href="javascript:void(0);"><span></span></a></c:if>--%>
    </div>
    <div class="sift" id="addAddressDiv">
        <form id="formAddCustomerAddress" name="formAddCustomerAddress">
            <div class="ti">新增<br>收货<br>地址</div>
            <p>
                <label>收货地址</label><jsp:include page="/common/address.jsp">
                <jsp:param value="" name="instId"/>
                <jsp:param value="true" name="iszip" />
            </jsp:include>
            </p>
            <p>
                <label>&nbsp;</label> <input tabindex="11" name="address" id="address" type="text" size="64" class="easyui-validatebox" data-options="required:true" />
            </p><p>
            <label>邮编</label> <input tabindex="12" name="zip" id="zip" type="text" class="easyui-validatebox" data-options="validType:'zipCode'" />
            <input name="editAddressRowIndex" id="editAddressRowIndex" type="hidden" />
            <label id="isDefaultAddressTR"><input class="fl" type="checkbox" id="isDefaultAddress" name="isDefaultAddress"/>设为默认</label>

            <label>
                <a href="javascript:void(0);" tabindex="13" class="phoneNums_btns small_btns" id="addCoustomerAddressBut">确定</a></label>
        </p>
            <input type="hidden" id="addressId" value=""/>
            <input type="hidden" id="addressRowIndex"/>
        </form>
    </div>
    <%-- 修改第四级地址 --%>
    <div class="sift" id="editFourthAddressTab" style=" display: none;">
        <%--<form id="formAddCustomerPhone" name="formAddCustomerPhone" action="" method="post"  onkeypress="if(event.keyCode==13||event.which==13){return false;}">--%>
        <label style="width: 100px;">修改第四级地址</label>
        <jsp:include page="/common/addressForEditFourth.jsp"></jsp:include>
        <input type="hidden" id="addressRowIndexForFourth"/>
        <a href="javascript:void(0);" class="phoneNums_btns small_btns" id="editFourthAddressBut">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:void(0);" id="cancleEditFourthAddressBut" class="phoneNums_btns small_btns">取消</a>
        <%--</form>--%>
    </div>
    <table id="addressTable"></table>
</div>
<%--<form id="formAddCustomerAddress" name="formAddCustomerAddress"  onkeypress="if(event.keyCode==13||event.which==13){return false;}">--%>

<%--<table class="cr_table" id="AddAdderssTab" name="AddAdderssTab" border="0" cellspacing="0" cellpadding="0" style="display:none">--%>
<%--<tr>--%>
<%--<td class="cr_title">新增收货地址</td>--%>
<%--<td></td>--%>
<%--<td></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>收货地址</td>--%>
<%--<td>  --%>
<%--<jsp:include page="/common/address_after_load.jsp">--%>
<%--<jsp:param value="" name="instId"/>--%>
<%--<jsp:param value="true" name="iszip" />--%>
<%--</jsp:include>--%>
<%--</td>--%>
<%--<td>  </td>--%>
<%--</tr>--%>
<%--<tr><td></td><td><input name="address" id="address" type="text" size="40" class="easyui-validatebox" data-options="required:true" /></td><td></td></tr>--%>
<%--<tr>--%>
<%--<td>邮编</td>--%>
<%--<td><input name="zip" id="zip" type="text" class="easyui-validatebox" data-options="validType:'zipCode'" />--%>
<%--<input name="editAddressRowIndex" id="editAddressRowIndex" type="hidden" />--%>
<%--</td>--%>
<%--<td></td>--%>
<%--</tr>--%>
<%----%>
<%--<tr id="isDefaultAddressTR"><td>设为默认</td><td> <input type="checkbox" id="isDefaultAddress" name="isDefaultAddress"/> </td><td></td></tr>--%>
<%--<tr><td></td><td><a href="javascript:void(0);" class="phoneNums_btns" id="addCoustomerAddressBut">确定</a>--%>
<%--&lt;%&ndash;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" id="cancleCoustomerAddressBut" class="phoneNums_btns">取消</a>&ndash;%&gt;--%>
<%--</td><td></td></tr>--%>

<%--</table>--%>
<%--<input type="hidden" id="addressId" value=""/>--%>
<%--<input type="hidden" id="addressRowIndex"/>--%>
<%--</form>--%>
<%--<table class="cr_table" id="editFourthAddressTab" name="editFourthAddressTab" border="0" cellspacing="0" cellpadding="0" style="display:none">--%>
<%--<tr>--%>
<%--<td class="cr_title">修改第四级地址</td>--%>
<%--<td></td>--%>
<%--<td></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>收货地址</td>--%>
<%--<td>--%>
<%--<jsp:include page="/common/addressForEditFourth.jsp"></jsp:include>--%>
<%--</td>--%>
<%--<td></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td></td>--%>
<%--<td>--%>
<%--<a href="javascript:void(0);" class="phoneNums_btns" id="editFourthAddressBut">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--<a href="javascript:void(0);" id="cancleEditFourthAddressBut" class="phoneNums_btns">取消</a>--%>
<%--</td>--%>
<%--<td></td></tr>--%>
<%--<input type="hidden" id="addressRowIndexForFourth"/>--%>
<%--</table>--%>
<div id="displayCard">
    <div class="creditCards">
        <div class="creditCards-title q_r">常用信用卡信息&nbsp;&nbsp;&nbsp;&nbsp;
        </div>
        <table id="cardTable"></table>
    </div>
    <table id="addCardTable" class="cr_table" border="0" cellspacing="0" cellpadding="0" hidden="hidden">
        <tr>
            <td class="cr_title">信用卡信息</td>
            <td></td>
        </tr>
        <tr>
            <td>信用卡类型</td>
            <td><select id="addCardType" name="">
                <option value="">请选择</option>
                <c:forEach items="${cardtypes}" var="cardtype">
                    <option value="${cardtype.cardtypeid}">${cardtype.cardname}</option>
                </c:forEach>
            </select></td>
            <td></td>
        </tr>
        <tr>
            <td>卡号</td>
            <td><input  id="addCardNumber" name="" type="text" /></td>
            <td></td>
        </tr>
        <tr>
            <td>有效期</td>
            <td><input id="addCardValidateDate" name="" type="text" /></td>
            <td></td>
        </tr>
        <input id="addCardRowIndex" type="hidden" />
        <input id="addCardId" type="hidden" value=""/>
        <tr><td></td><td><a id="saveEditCard"  href="javascript:void(0);" class="phoneNums_btns">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;<a id="cancelEditCard"  href="javascript:void(0);" class="phoneNums_btns">取消</a>	</td><td></td></tr>

    </table>
</div>
<c:if test="${customer.state==null||customer.state==2}">
    <div class="textC mt10" >
        <a tabindex="14" href="javascript:void(0);" id="compareEditPhoneAndAddress" class="phoneNums_btns" style="width:80px" >保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a tabindex="14" href="javascript:void(0);" id="compareEditPhoneAndAddress_hidden" class="phoneNums_btns" style="width:80px;display: none;" >保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a tabindex="14" href="javascript:void(0);" id="cancelEdit" class="phoneNums_btns" style="width:80px">取消</a>
    </div>
</c:if>
<!-- </form> -->
<div id="outphone_loading" class="" style="display: none;">
    <div style='position:absolute;width:100%;background: rgba(255,255,255,0.5); height:100%; text-align:center;top:0;font-size:12px; margin:0; padding:0; z-index: 19999'>
        <span style="position: absolute;bottom: 20px;color: #666; font-weight: bold; font-size: 14px;">拨号中，请稍等……</span>
        <img alt='load' src='../../../static/images/loadingAnimation.gif' align='absMiddle'  bold='0'  />
    </div>
</div>
</div>
<div title="客户订单历史">
    <div class="history tabs-form">
        <table id="orderHistory"></table>
    </div>
</div>
<%@include file="customerExtInfo.jsp"%>
<div title="客户服务历史">
    <div class="history tabs-form">
        <div id="" class="easyui-tabs" style=" height:auto">
            <div  title="服务历史"> <div class="l_i_h "> <table id="id_cases"></table></div></div>
            <c:if test="${customer.customerType != '2'}"><div  title="生成投诉历史"> <div class="m_h"><table id="complainTable"></table></div></div></c:if>
        </div>

    </div>
</div>
<div title="客户联系历史">
    <div class="history tabs-form">
        <div id="contactTab" class="easyui-tabs" style=" height:auto">
            <div  title="销售历史"> <div class="l_i_h "><table id="lead_interation_history" ></table></div></div>
            <div  title="营销历史" > <div   class="m_h"><table id="message_history" ></table></div></div>
        </div>
    </div>
</div>

<div title="客户购物车">
    <div class="history tabs-form">
        <div id="id_page_shoppingCart"  style="display: none" >
            <jsp:include page="../cart/cartContent.jsp" ></jsp:include>
        </div>
        <div id="id_page_shoppingCart_message" style="display: none">
            无客户编号无法显示购物车。
        </div>
    </div>
</div>
</div>

<div id="w" class="easyui-window" title="电话呼入" data-options="closed:true,modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,draggable:false" style="width:700px;height:450px;padding:10px;">
    <div class="easyui-tabs" style=" height:auto">
        <div title="查询">
            <div class="tabs-form">
                <table class="win_table" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td>电话号码</td>
                        <td><input name="" type="text" maxlength="255" /></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>客户姓名</td>
                        <td><input name="" type="text" maxlength="255" /></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
                <p class="mb10"><a class="window_btn" href="javascript:void(0)">查找</a><a class="window_btn ml10" href="javascript:void(0)">清空</a><a class="window_btn ml10" href="javascript:void(0)">新增</a></p>

                <div class="sift">地区&nbsp;<select style="width:100px" name=""><option>江苏</option></select>&nbsp;<select style="width:100px" name=""><option>无锡</option></select>&nbsp;<select style="width:100px" name=""><option>其它区</option></select></div>
                <table class="easyui-datagrid"  style="height:215px"
                       data-options="singleSelect:true" pagination="true">
                    <thead>
                    <tr>
                        <th data-options="field:'itemid',width:80">订单编号</th>
                        <th data-options="field:'productid',width:300">订单生成时间</th>
                        <th data-options="field:'listprice',width:80">订单状态</th>
                        <th data-options="field:'unitcost',width:80">订单金额</th>
                        <th data-options="field:'attr1',width:80">生成座席</th>
                        <th data-options="field:'unitcost',width:80">座席组</th>
                        <th data-options="field:'unitcost',width:80">运单号</th>
                        <th data-options="field:'attr1',width:80">物流状态</th>
                    </tr>
                    </thead>
                </table>
            </div>

        </div>
        <div title="更多查询">
            <div class="tabs-form">
                <table class="win_table" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td>客户编号</td>
                        <td><input name="" type="text" maxlength="255" /></td>
                        <td>运单号</td>
                        <td><input name="" type="text" maxlength="255" /></td>
                    </tr>
                    <tr>
                        <td>订单号</td>
                        <td><input name="" type="text" maxlength="255" /></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
                <p class="mb10"><a class="window_btn" href="javascript:void(0)">查找</a><a class="window_btn ml10" href="javascript:void(0)">清空</a><a class="window_btn ml10" href="javascript:void(0)">新增</a></p>

                <table class="easyui-datagrid"  style="height:248px"
                       data-options="singleSelect:true" pagination="true">
                    <thead>
                    <tr>
                        <th data-options="field:'itemid',width:80">订单编号</th>
                        <th data-options="field:'productid',width:300">订单生成时间</th>
                        <th data-options="field:'listprice',width:80">订单状态</th>
                        <th data-options="field:'unitcost',width:80">订单金额</th>
                        <th data-options="field:'attr1',width:80">生成座席</th>
                        <th data-options="field:'unitcost',width:80">座席组</th>
                        <th data-options="field:'unitcost',width:80">运单号</th>
                        <th data-options="field:'attr1',width:80">物流状态</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<div id="compareNameAndSex${customer.customerId}" class="easyui-window" title="客户基本信息修改确认" data-options="modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true,draggable:false" style="width:500px">
    <div style="padding: 10px">
        <table class="changeAuTa" border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <th scope="col">&nbsp;</th>
                <th scope="col" class="big">修改前</th>
                <th scope="col" class="big red">修改后</th>
            </tr>
            <tr>
                <td valign="top" width="90px" class="no_b">
                    <div class="t_head int_info">客户基本<br>信息</div>
                </td>
                <td valign="top" class="no_b" >
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td valign="top"><div style="width:50px">客户姓名</div></td>
                            <td><input type="text" class="readonly oldName" readonly style="width:80px"/></td>
                        </tr>
                        <tr>
                            <td valign="top"><div style="width:50px">客户性别</div></td>
                            <td><label><input class="oldSexMen" type="radio" disabled="disabled" />男</label>&nbsp;<label><input class="oldSexWomen" type="radio" disabled="disabled"/>女</label></td>
                        </tr>
                    </table>
                </td>
                <td class="no_r   no_b" valign="top">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr class="newNameTR">
                            <td valign="top" style="color:inherit"><div style="width:50px">客户姓名</div></td>
                            <td><input type="text" class="readonly newName" readonly style="width:80px"/></td>
                        </tr>
                        <tr class="newSexTR">
                            <td valign="top" style="color:inherit"><div style="width:50px">客户性别</div></td>
                            <td style="color:inherit"><label><input class="newSexMen" type="radio" disabled="disabled"/>男</label>&nbsp;<label><input class="newSexWomen" type="radio" disabled="disabled"/>女</label></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <p class="winBtnsBar" align="center"><a class="window_btn" href="javascript:void(0);" onclick="subCallback('p_'+${customer.customerId}, 'clickSubmitEditNameAndSex');">确定</a><a class="window_btn ml10" href="javascript:void(0);" onclick="subCallback('p_'+${customer.customerId}, 'clickCancelEditNameAndSex');">取消</a></p>
</div>
<div id="comparePhoneAndAddress${customer.customerId}" class="easyui-window" title="客户电话地址信息修改确认" data-options="modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true,draggable:false" style="width:830px;">
    <div style="padding: 10px">
        <table class="changeAuTa" border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <th scope="col">&nbsp;</th>
                <th scope="col" class="big">修改前</th>
                <th scope="col" class="big red">修改后</th>
            </tr>
            <tr>
                <td valign="top" width="90px" >
                    <div class="t_head addr_info" style="line-height: 40px">客户电话</div>
                </td>
                <td valign="top" >
                    <table class="oldPhoneTable"></table>
                </td>
                <td class="no_r" valign="top">
                    <table class="newPhoneTable"></table>
                </td>
            </tr>
            <tr>
                <td valign="top" width="90px" class="no_b">
                    <div class="t_head deliver_info" style="line-height: 40px">客户地址</div>
                </td>
                <td valign="top" class="no_b" >
                    <table class="oldAddressTable"></table>
                </td>
                <td class="no_r   no_b" valign="top">
                    <table class="newAddressTable"></table>
                </td>
            </tr>
        </table>
    </div>
    <p class="winBtnsBar" align="center">
        <a class="window_btn" href="javascript:void(0);" onclick="subCallback('p_'+${customer.customerId}, 'clickSubmitEditPhoneAndAddress');">确定</a><a class="window_btn ml10" href="javascript:void(0);" onclick="subCallback('p_'+${customer.customerId}, 'clickCancelEditPhoneAndAddress');">取消</a></p>
</div>
<div id="phoneBindOneContact${customer.customerId}" class="easyui-window" title="客户新增电话" data-options="top:50,modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true" style="width:400px;height:180px;padding:10px;">
    <div class="c_content">
        <span class="popWinPhoneNum"></span>已关联客户ID<a class="detailItem ml10" href="javascript:void(0);" onclick="subCallback('p_'+${customer.customerId==null?'\'newCustomer\'':customer.customerId}, 'clickPopWinContactId');"><span class="popWinContactId"></span></a><span class="popWinPhonesMsg"></span>
        <span class="popWinButtons"><p class="mb10 mt10" align="center"><a class="window_btn" href="javascript:void(0);" onclick="subCallback('p_'+${customer.customerId==null?'\'newCustomer\'':customer.customerId}, 'clickSubmitPhoneBind');">确定</a><a class="window_btn ml10" href="javascript:void(0);" onclick="subCallback('p_'+${customer.customerId==null?'\'newCustomer\'':customer.customerId}, 'clickCancelPhoneBind');">取消</a></p></span>
    </div>
</div>
<div id="phoneBindMultiContact${customer.customerId}" class="easyui-window" title="客户新增电话" data-options="modal:true,shadow:true,draggable:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true,top:100" style="width:700px;">
    <div class="c_content">
        <span class="popWinPhoneNum"></span><span class="popWinMultiPhonesMsg"></span>
        <span class="popWinButtons"><p class="mb10 mt10" align="center"><a class="window_btn" href="javascript:void(0);" onclick="clickSubmitPhoneMultiBind()">确定</a><a class="window_btn ml10" href="javascript:void(0);" onclick="clickCancelPhoneMultiBind()">取消</a></p></span>
    </div>
    <table class="multiContactTable">
    </table>
</div>
<div id="gg" class="easyui-window" title="查归属" data-options="modal:true,shadow:true,draggable:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true,top:100" style="width:500px;">
    <table id="assignHistory" border="0" cellspacing="0" cellpadding="0" width="100%">
    </table>
</div>
<input id="customerType" type="hidden" value="${customer.customerType}">
<input id="customerId" type="hidden" value="${customer.customerId}">
<input id="initCustomerId" type="hidden" value="${customer.customerId}">
<input id="instanceId" type="hidden" value="${instanceId}">
<input id="h_provid" type="hidden" value="${provid}">
<input id="h_cityid" type="hidden" value="${cityid}">
<input id="h_campaignId" type="hidden" value="${campaignId}">
<input id="page_refresh" type="hidden" value="${selectedTab}">
<input id="order_id" type="hidden" value="${orderId}">
<input id="customerState" type="hidden" value="${customer.state}">
<input id="agentType" type="hidden" value="${agentType}">
</body>
</html>