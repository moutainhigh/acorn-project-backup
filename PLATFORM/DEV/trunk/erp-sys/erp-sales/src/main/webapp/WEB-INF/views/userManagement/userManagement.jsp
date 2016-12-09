<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>

<head>

    <script type="text/javascript" src="${ctx}/static/js/userMange.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/easyui-validatabox-extend.js"></script>
    <style type="text/css">
        .messager-window,.window-shadow{
            top:140px!important;
        }
        .datagrid-cell-c1-operator{
            line-height: 1em;
        }

    </style>
</head>
<body>




<div class="form-tabs">
    <div class="sift" style="vertical-align: middle;height: 46px;line-height: 23px;box-shadow: none;-webkit-box-shadow:none;border-color:silver;">



        登录用户:<input id="uid" name="uid" width="60px"/>
        登录Ip： <input id="ip" name="ip" />
        服务器地址: <input class="easyui-combobox" id="server" name="server" />

        <input type="button" name="culling"   value="查询" onclick="javascript:findInfo();" />
        <br/>
        <label for="cullingUserId" >用户Id:</label>
        <input name="cullingUserId" id="cullingUserId" value="" width="60px" />
        <input type="button" name="culling" id="culling"  value="强制退出" onclick="javascript:cullingSingleUser();" />
        <input type="button" name="culling" id="unlock2"  value="解锁" onclick="javascript:unlock2();" />
        <input type="button" name="culling" id="changePwd2"  value="重置密码" onclick="javascript:changePwdShow(2);" />
        <input name="viewUserId" id="viewUserId" type="hidden" value=""/>
    </div>


    <table id="loginUsers-grid"></table>


</div>
<div id="changePwdView" class="easyui-window" modal="true" title="Modal Window"
     data-options="modal:true,closed:true,iconCls:'icon-save',minimized:false"
     style="width:500px;height:200px;padding:10px;">
The window content.
</div>
<div id="resetWin" class="easyui-window"
     data-options="collapsible:false,minimizable:false,maximizable:false,top:150,closed:true,modal:true,shadow:false,title:'重置密码',draggable:false"
     style="width:280px;height: 160px">
    <div class="mpsw">
        <div  class="modifyPW">
            <form id="modifyPWForm"  action="" method="post" >
                <table width="100%">
                    <tbody>
                    <tr>
                        <td style="padding:5px;text-align: right;"><div class="lableWord">新密码</div></td>
                        <td style="padding:5px;">
                            <input id="password1" name="password1" value="" type="password" maxlength="20" validType="length[8,20]" class="easyui-validatebox" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="padding:5px;text-align: right;"><div class="lableWord">确认新密码</div></td>
                        <td style="padding:5px;">
                            <input id="password2" name="password2" value="" type="password" maxlength="20"  validType="equals['#password1']" class="easyui-validatebox" data-options="required:true"/>
                        </td>
                    </tr>
                    <%--<tr>--%>
                    <%--<td></td>--%>
                    <%--<td >&nbsp;</td>--%>
                    <%--</tr>--%>
                    </tbody>
                </table>
            </form>
        </div>
        <p class="winBtnsBar textC"><a class="window_btn"  href="javascript:void(0)" onclick="javascript:submitChangePwd();">确定</a><span id="cancle_btn1">
            <a class="window_btn ml10"  href="javascript:void(0)" onclick="javascript:clearChangePwd();">取消</a></span></p>
    </div>

</div>
</body>
</html>