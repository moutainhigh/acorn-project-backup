<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:if test="${sessionScope.isLoadCtiServer}" >



    <div id="div_appletFrame" style="width: 0px;height: 0px">
        <applet id="SoftPhone"
                code="com.esoon.softphone.core.Bootstrap.class"
                codebase="./static/plugin/"
                width=0
                height=0
                archive="
			log4j-1.2.15.jar,
 			apptemplate.jar,
            commons.jar,
			configurationprotocol.jar,
			connection.jar,
			kvlistbinding.jar,
			kvlists.jar,
			logging.jar,
			messagebrokerappblock.jar,
			packagedstatisticsdeprecated.jar,
			protocol.jar,
			protocolmanagerappblock.jar,
			switchpolicy.jar,
			system.jar,
			voiceprotocol.jar,
			managementprotocol.jar,
			warmstandbyappblock.jar,
			gson-2.2.2.jar,
			SoftPhone-13.12.20.02.jar,
			jackson-all-1.7.1.jar"
            >
            <param name="tServerHost" value="${sessionScope.cti_host}" />
            <param name="tServerPort" value="${sessionScope.cti_port}" />
            <param name="bkpTServerHost" value="${sessionScope.cti_host_back}" />
            <param name="bkpTServerPort" value="${sessionScope.cti_port_back}" />
            <param name="thisDN" value="${sessionScope.cti_dn}" />
            <param name="quere" value="" />
            <param name="agentId" value="${sessionScope.cti_agentId}" />
            <param name="type" value="application/x-java-applet;jpi-version=${sessionScope.cti_jreVersion}" />
            <param name="mediaType" value="voice" />
        </applet>

    </div>
</c:if>

<div id="head">
    <input type="hidden" id="cti_host" value="${sessionScope.cti_host}"/>
    <input type="hidden" id="cti_port" value="${sessionScope.cti_port}"/>
    <input type="hidden" id="cti_host_back" value="${sessionScope.cti_host_back}"/>
    <input type="hidden" id="cti_port_back" value="${sessionScope.cti_port_back}"/>
    <input type="hidden" id="cti_dn" value="${sessionScope.cti_dn}"/>
    <input type="hidden" id="cti_agentId" value="${sessionScope.cti_agentId}"/>

    <table class="appHeadBg" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                <img width="30" id="cti_onAndOff" height="30px" class="fl" style="vertical-align: middle" src="/static/images/phone-lamp-gray.png"/>
                <span class="logo" id="logo">橡果销售管理系统

                    <!--
                    <a href="javascript:alert(phone.status);">++</a>
                        -->
                    <button id="inbb">电话进线</button>
                    <button id="interrupt" onclick="javascript:interrupt(phone.status);">断线</button>
                    <button onclick="javascript: phone.changeStatus(-1);">摘机状态</button>

                    <span id="ctiStatus" name="ctiStatus"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <script type="text/javascript">
                        try{
                            if(document.SoftPhone.isActive()){
                                console.info("安装了JRE");
                            }
                        }catch(error){
                            console.info("没安装JRE");
                            document.write("  <a id='cti_jre' href='${ctx}/static/plugin/CTI_JRE.zip'>下载CTI_JRE插件</a>");
                        }
                    </script>


                    <!-- <button id="calling">外呼通话中</button> <button onclick="javascript:alert(phone.status);">getStatus</button> -->
                </span>

            </td>
            <td>
                <div class="callContext fr" id="callContext"><span class="incoming"></span></div>
            </td>
            <%--<td   width="30px"> <a id="onHook" title="挂机" class="onHook" href  ="javascript:void(0)"></a>--%> <%--</td>--%>
            <td width="30px" valign="top" style="">
                <div id="softphoneWrap" class="softphoneWrap"><a id="softphone" title="拨号" class="softphone" href="#"><span></span></a>
                    <ul class="key_panel">
                        <li class="clearfix">
                            <div style="position: relative">
                                <a id="delNum" class="delNum"></a>
                                <select id="notelist" class="easyui-combobox" name="state" style="width:175px;" data-options="
                                onBeforeLoad: function(){
                                  $('#notelist').combobox('textbox').attr('maxlength','5');
                                },
                                onShowPanel:function(){
                                      $('#notelist').combobox('panel')
                                         .css({borderRadius:'0 0 10px 10px',maxHeight:'150px',border:'1px #fff solid',borderBottom:'6px #fff solid',boxShadow: '#ccc 0px 1px 1px'})
                                         .children()
                                         .addClass('nobg');
                                     $('#notelist').combobox('textbox').parent().css({borderRadius:'10px 10px 0 0'});
                                },onHidePanel:function(){
                                     $('#notelist').combobox('textbox').parent().css({borderRadius:'10px'});
                                }">
                                    </select>
                            </div>
                            <%--<div class="c_combo" style="width: 195px;" id="pnum">--%>
                                <%--<a style="width: 175px;height: 23px;margin:9px 6px 5px 13px" class="white"><span--%>
                                    <%--class="arrow"></span></a>--%>
                                <%--<a class="delNum"  href="javascript:void(0)" onclick="delCon(this)"></a>--%>
                                <%--<ul style="width: 175px;margin-left:13px;top:30px" class="c_comboList">--%>
                                    <%--<div id="normal_phone">--%>
                                    <%--<li>11805</li>--%>
                                    <%--<li>12345</li>--%>
                                    <%--<li>66542</li>--%>
                                    <%--</div>--%>
                                <%--</ul>--%>
                            <%--</div>--%>
                        </li>
                        <li class="clearfix" id="proAndCtili">
                            <select id="prolist" class="easyui-combobox" name="state" style="width:85px;" data-options="
                                onShowPanel:function(){
                                      $('#prolist').combobox('panel')
                                         .css({borderRadius:'0 0 10px 10px',maxHeight:'150px',border:'1px #fff solid',borderBottom:'6px #fff solid'})
                                         .children()
                                         .addClass('nobg');
                                     $('#prolist').combobox('textbox').parent().css({borderRadius:'10px 10px 0 0'});
                                },onHidePanel:function(){
                                     $('#prolist').combobox('textbox').parent().css({borderRadius:'10px'});
                                }">

                            </select>
                            <select id="citylist" class="easyui-combobox" name="state" style="width:85px;" data-options="
                                onShowPanel:function(){
                                      $('#citylist').combobox('panel')
                                         .css({borderRadius:'0 0 10px 10px',maxHeight:'150px',border:'1px #fff solid',borderBottom:'6px #fff solid'})
                                         .children()
                                         .addClass('nobg');
                                     $('#citylist').combobox('textbox').parent().css({borderRadius:'10px 10px 0 0'});
                                },onHidePanel:function(){
                                     $('#citylist').combobox('textbox').parent().css({borderRadius:'10px'});
                                }">
                                <option value="">Alabama</option>
                            </select>
                            <%--<div class="c_combo" id="add1" style="width:76px"><a--%>
                                    <%--style="width: 55px;height: 23px;margin:0 6px 5px 13px" class="white combo_add"><span--%>
                                    <%--style="width:42px" class="c_context">江苏</span><span class="arrow"></span></a>--%>
                                <%--<ul style="width: 55px;margin-left:13px" class="c_comboList">--%>
                                    <%--<div id="normal_phone_p">--%>
                                    <%--<li>江苏</li>--%>
                                    <%--<li>12345</li>--%>
                                    <%--<li>66542</li>--%>
                                        <%--</div>--%>
                                <%--</ul>--%>
                            <%--</div>--%>
                            <%--<div class="c_combo" id="add2" style="width:63px"><a--%>
                                    <%--style="width: 55px;height: 23px;margin:0 6px 5px 0" class="white combo_add"><span--%>
                                    <%--style="width:42px" class="c_context">无锡</span><span class="arrow"></span></a>--%>
                                <%--<ul style="width: 55px;margin-left:0" class="c_comboList">--%>
                                    <%--<div id="normal_phone_c">--%>
                                    <%--<li>无锡</li>--%>
                                    <%--<li>12345</li>--%>
                                    <%--<li>66542</li>--%>
                                    <%--</div>--%>
                                <%--</ul>--%>
                            <%--</div>--%>

                        </li>
                        <li class="clearfix">
                            <div class="c_combo c_callBtn" title="拨号" style="width:55px"><a id="outbtn"></a></div>
                            <div id="div_transferPhone" class="c_combo throughconnect" title="转接" style="width:55px;margin: 0 4px"><a id="transferPhoneBtn"></a></div>
                            <div id="div_threePhone" class="c_combo threewaycalling" title="三方通话" style="width:55px;float: right"><a id="threePhoneBtn"></a></div></li>
                        <li style="margin: 0">
                            <table class="keyboard" width="173px" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="62px"><a>1</a></td>
                                    <td width="62px"><a>2</a></td>
                                    <td><a>3</a></td>
                                </tr>
                                <tr>
                                    <td><a>4</a></td>
                                    <td><a>5</a></td>
                                    <td><a>6</a></td>
                                </tr>
                                <tr>
                                    <td><a>7</a></td>
                                    <td><a>8</a></td>
                                    <td><a>9</a></td>
                                </tr>
                                <tr>
                                    <td><a style="margin: 0">*</a></td>
                                    <td><a style="margin: 0">0</a></td>
                                    <td><a style="margin: 0">#</a></td>
                                </tr>
                            </table>
                        </li>
                    </ul>
                    &nbsp;

                </div>
            </td>
            <td width="40px"><a id="callback" value="0" title="callback" class="callback_n"
                                href="javascript:void(0)"></a>
            </td>
            <td width="123px">
                <div id="state" class="c_combo">
                    <a class="c_btn" style="width: 88px;height: 23px" href="javascript:void(0)">离席</a>
                    <a class="arrow"></a>
                    <ul style="width: 111px" class="c_comboList">
                        <li class="loginphone"><!--<span class="blue"></span>-->登录</li>
                        <li class="ready"><span class="blue"></span>就绪</li>
                        <li class="cancelHold"><span class="blue"></span>取消保持</li>
                        <li class="hold"><span class="green"></span>保持</li>
                        <li class="leave">

                            <dl>
                                <dt><label><span class="red"></span>离席</label><span class="ar fr"></span></dt>
                                <%--<dd><label><input type="radio" name="leaveType"/>申请外出</label></dd>--%>
                                <%--<dd><label><input type="radio" name="leaveType"/>主管找</label></dd>--%>
                                <%--<dd><label><input type="radio" name="leaveType"/>下线学习</label></dd>--%>
                                <%--<dd><label><input type="radio" name="leaveType"/>听录音</label></dd>--%>
                                <%--<dd><label><input type="radio" name="leaveType"/>帮别人处理线</label></dd>--%>

                                <dd value="1204"><label><img src='/static/images/ch.png'/>申请外出</label></dd>
                                <dd value="1205"><label><img src='/static/images/ch.png'/>主管找</label></dd>
                                <dd value="1206"><label><img src='/static/images/ch.png'/>下线学习</label></dd>
                                <dd value="1207"><label><img src='/static/images/ch.png'/>听录音</label></dd>
                                <dd value="1208"><label><img src='/static/images/ch.png'/>帮别人处理线</label></dd>
                            </dl>
                        </li>

                    </ul>
                </div>
            </td>
            <td width="30px"><a class="logout" title="退出" href="javascript:void(0)"></a>
            </td>
        </tr>
    </table>
</div>