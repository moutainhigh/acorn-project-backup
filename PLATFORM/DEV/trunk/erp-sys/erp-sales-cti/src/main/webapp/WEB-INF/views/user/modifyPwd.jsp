<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	
</head>
<body>
<script type="text/javascript" src="${ctx}/static/js/easyui-validatabox-extend.js"></script>
<script type="text/javascript">

$(function() {
	
	<c:if test="${sessionScope.MUST_CHANGE_PWD_FLAG != null}">
	$('#cancle_btn1').hide();
	</c:if>
	
	<c:if test="${sessionScope.MUST_CHANGE_PWD != null}">
	$("#errorInfoModifyPwd").html("${sessionScope.USER_LOGIN_EXCEPTION_MSG}");
	<c:remove var="USER_LOGIN_EXCEPTION_MSG" scope="session" />
	<c:remove var="MUST_CHANGE_PWD" scope="session" />
	<c:remove var="MUST_CHANGE_PWD_FLAG" scope="session" />
	</c:if>
});

function checkPwd(v){  
    var numasc = 0;  
        var charasc = 0;  
            for (var i = 0; i < v.length; i++) {  
                var asciiNumber = v.substr(i, 1).charCodeAt();  
                if (asciiNumber >= 48 && asciiNumber <= 57) {  
                    numasc += 1;  
                }  
                if ((asciiNumber >= 65 && asciiNumber <= 90)||(asciiNumber >= 97 && asciiNumber <= 122)) {  
                    charasc += 1;  
                }  
            }  
            if(0==numasc)  {  
                return "密码必须含有数字";  
            }else if(0==charasc){  
                return "密码必须含有字母";  
            }else{  
                return "true";  
            }  
};  


function modifyPwdSubmit(){
	$("#errorInfoModifyPwd").html("");
	var r = $('#modifyPwdForm').form('validate');
	if(!r) {
		return false;
	}
	var v = $("#newPassword").val();
	var result = checkPwd(v);
	if(result!="true"){
		$("#errorInfoModifyPwd").html(result);
		return false;
	}
	$.post("${ctx}/user/modifyPwd",$("#modifyPwdForm").serializeArray(),function(data){
		
		if(data.status=="-1"){
			$("#errorInfoModifyPwd").html("登录失效，请重新登录后进行修改");
		}else if(data.status=="-2"){
			$("#errorInfoModifyPwd").html("原密码错误请重新输入");
			$("#oldPassword").val("");
		}else if(data.status=="0"){
			if(data.msg.indexOf("error code 19")>-1){
				$("#errorInfoModifyPwd").html("新密码不能与原密码相同");
				$("#newPassword").val("");
				$("#confirmNewPassword").val("");
			}else{
				$("#errorInfoModifyPwd").html(data.msg);
			}
			
		}else if(data.status=="1"){
			//$("#errorInfoModifyPwd").html("密码修改成功");
			$.messager.alert('提示','密码修改成功','info');
			//$.messager.confirm('提示', '密码修改成功', function(r){
			//	if (r){
			//		closeModifyPwdWin();
			//	}
			//});
			
			$('#cancle_btn1').show();
			$('#modifyPwdWin').window({'closable':true});
			
		}
		//$('#commonMyPopWindow').window('close');
		//$('#template').datagrid('reload');  
		//$.messager.alert('提示',data.mes,'info');
	});
}
</script>


<div class="mpsw">
<div  class="modifyPW">
    <form id="modifyPwdForm"  action="" method="post" >
    <table width="100%">
        <tbody>
        <tr>
            <td></td>
            <td   style="color: red;padding-left:7px" ><span id="errorInfoModifyPwd" class="errorWords"></span>&nbsp;</td>
        </tr>
        <tr>
            <td style="padding:5px;text-align: right;"><div class="lableWord">原密码</div></td>
            <td style="padding:5px;"><input id="oldPassword" name="oldPassword" value="" type="password" validType="length[6,20]" maxlength="20" class="easyui-validatebox" data-options="required:true"/></td>
        </tr>
        <tr>
            <td style="padding:5px;text-align: right;"><div class="lableWord">新密码</div></td>
            <td style="padding:5px;">
                <input id="newPassword" name="newPassword" value="" type="password" maxlength="20" validType="length[8,20]" class="easyui-validatebox" data-options="required:true"/>
            </td>
        </tr>
        <tr>
            <td style="padding:5px;text-align: right;"><div class="lableWord">确认新密码</div></td>
            <td style="padding:5px;">
                <input id="confirmNewPassword" name="confirmNewPassword" value="" type="password" maxlength="20"  validType="equals['#newPassword']" class="easyui-validatebox" data-options="required:true"/>
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
<p class="winBtnsBar textC"><a class="window_btn"  href="javascript:void(0)" onclick="javascript:modifyPwdSubmit()">确定</a><span id="cancle_btn1"><a class="window_btn ml10"  href="javascript:void(0)" onclick="javascript:closeModifyPwdWin()">取消</a></span></p>
</div>
</body>
</html>